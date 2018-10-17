package com.gcaa.resource.metrics.collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.gcaa.metrics.domain.model.CPU;
import com.gcaa.metrics.domain.model.Resource;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem.ProcessSort;

import static com.gcaa.metrics.domain.common.util.NumberUtils.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

@Component
public class PerformanceCollector implements Collector {

	private SystemInfo systemInfo ;
	
	private OperatingSystemMXBean operatingSystemMXBean;
	
	private static Logger LOGGER = LoggerFactory.getLogger(PerformanceCollector.class);
	
	public static Map<Integer,Long> processPreviousTime = new ConcurrentHashMap<Integer,Long>();
	
	public static Map<Integer,Long> childProcessPreviousTime = new ConcurrentHashMap<Integer,Long>();
	
	private final String UNIX_SHELL = "/bin/ps";
	private final String PROCESS_COMMAND = "-p";
	private final String SELECT_OPTION = "-o";
	private final String PROCESS_COLUMN = "pid,user:40,%cpu";
	private final String PROCESS_SORT = "--sort=-%cpu";
	private final String PROCESS_CHILD_COMMAND = "--ppid";
	private final String PROCESS_CHILD_PID = "pid,%cpu";
	
	public static transient long scheduleTime;

	@Autowired
	public PerformanceCollector(SystemInfo systemInfo) {
		this.systemInfo = systemInfo;
		this.operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();;
	}
	
	@Override
	public Optional<Resource> collect() {

		LOGGER.info("{ Performance/CPU Metrics Collector Started }");
		double used = cpuConsumption(operatingSystemMXBean,systemInfo) ;
		CPU cpu = new CPU(doubleFormatter(used), doubleFormatter(HUNDRED_PERCENT));
		LOGGER.info("{ Performance/CPU Metrics Collector Finished { " + cpu + " }}");
		return Optional.ofNullable(cpu);

	}

	@Override
	public Optional<Resource> collectByProcessId(int processId) {
		
		Optional<Resource> cpu = Optional.empty();
		LOGGER.info("{ Performance/CPU Metrics Collector Started With Process { " + processId + " }}");
		if(isWindows(systemInfo)) {
			cpu = windowsResource(processId);
		}else {
			cpu = unixResource(processId);
		}
	
		LOGGER.info("{ Performance/CPU Metrics Collector Finished. { " + cpu + " }}");
		return cpu;

	}
	
	private Optional<Resource> windowsResource(int processId) {
		Optional<Resource> cpu = Optional.empty();
		OSProcess process = systemInfo.getOperatingSystem().getProcess(processId);
		if(null == process) {
			LOGGER.info("{ Performance/CPU metrics not found with process { "+ processId +" }}");
		}else {
			
			CentralProcessor processor = systemInfo.getHardware().getProcessor();
		    int cpuNumber = processor.getLogicalProcessorCount();
			double total = cpuConsumption(operatingSystemMXBean,systemInfo);
			
			long currentTime = process.getKernelTime() + process.getUserTime();
			long timeDifference = currentTime - (processPreviousTime.get(processId) !=null ?processPreviousTime.get(processId) : 0) ;
			
			double processCpu = ( HUNDRED_PERCENT * ( (double)timeDifference / (double)scheduleTime ) ) / cpuNumber;
			
			processPreviousTime.put(processId,currentTime);
			
			OSProcess[] osProcesses = systemInfo.getOperatingSystem().getChildProcesses(processId, 0, ProcessSort.CPU);
			for (OSProcess osProcess : osProcesses) {
					long childCurrentTime = osProcess.getKernelTime() + osProcess.getUserTime() ;
					Long childPrevioustime = childProcessPreviousTime.get(osProcess.getProcessID());
					long childTimeDifference = childCurrentTime - (childPrevioustime != null ? childPrevioustime : 0);
					processCpu =  processCpu + (HUNDRED_PERCENT * ( (double)childTimeDifference / (double)scheduleTime) ) / cpuNumber ;
					
					childProcessPreviousTime.put(osProcess.getProcessID(), childCurrentTime);
			}
			
			double used = doubleFormatter(processCpu);
			cpu = Optional.ofNullable(new CPU(doubleFormatter(used) ,doubleFormatter(total)));
		}
		return cpu;
	}
	
	private Optional<Resource> unixResource(int processId){
		Optional<Resource> resource = Optional.empty();
		double totalCpu = cpuConsumption(operatingSystemMXBean,systemInfo);
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		Process process = null;
		CPU cpu = null;
		
		try {
			String[] command ={UNIX_SHELL,PROCESS_COMMAND,String.valueOf(processId),SELECT_OPTION,PROCESS_COLUMN,PROCESS_SORT};
			Runtime runtime = Runtime.getRuntime();
			process = runtime.exec(command);
			process.waitFor();
			inputStreamReader = new InputStreamReader(process.getInputStream());
			reader = new BufferedReader(inputStreamReader);
			
			String processLine;
			int lineNumber = 0;
			while ((processLine = reader.readLine() )!=null && lineNumber <= 1) {
				if(lineNumber ==0) {
					++lineNumber;
					continue;
				}
	    	 	processLine = processLine.trim().replaceAll("\\s{2,}"," ");
	    	    String[] splitLine =  Pattern.compile("\\s+").split(processLine);
	    	    lineNumber++;
	    	    
	    	    cpu = new CPU(doubleFormatter(Double.valueOf(splitLine[2])), doubleFormatter(totalCpu));
	    	    cpu.setName(splitLine[1] + " | " + processId);
	       }
			
			/* As we are getting process id from file '*.pid' so it will have parent process id.
			 * In case some application like nginx spans child processes as well so now fetch all child processes 
			 * and calculate CPU utilized by them.
			 **/
				
			String[] childPorcessIdCmd ={UNIX_SHELL,PROCESS_CHILD_COMMAND,String.valueOf(processId),SELECT_OPTION,PROCESS_CHILD_PID};
			process = runtime.exec(childPorcessIdCmd);
			process.waitFor();
			inputStreamReader = new InputStreamReader(process.getInputStream());
			reader = new BufferedReader(inputStreamReader);
			
		//	BufferedReader errorReader = new BufferedReader(new InputStreamReader(in, cs));
			
			String childrenProcessIdLine;
			int childrenProcessIdLineNumber = 0;
			double childrenCpuUtilization = 0;
			while ((childrenProcessIdLine = reader.readLine() )!=null && childrenProcessIdLineNumber <= 1) {
				if(childrenProcessIdLineNumber ==0) {
					++childrenProcessIdLineNumber;
					continue;
				}
				childrenProcessIdLine = childrenProcessIdLine.trim().replaceAll("\\s{2,}"," ");
	    	    String[] splitLine =  Pattern.compile("\\s+").split(childrenProcessIdLine);
	    	    childrenCpuUtilization = childrenCpuUtilization + Double.parseDouble(splitLine[1]);
	    	    
	    	    childrenProcessIdLineNumber++;
			}
			   
			cpu.setUsed(cpu.getUsed() + doubleFormatter(childrenCpuUtilization));
    	    resource = Optional.ofNullable(cpu);
	   	
		}catch (Exception e) {
			LOGGER.error("Something wrong happened while collecting cpu metrics with unix command",e);
		}finally {
			process.destroy();
			try {
				if(null != inputStreamReader)
					inputStreamReader.close();
				if(null != reader)
					reader.close();
			}catch (Exception e) {
				LOGGER.error("Something wrong happened while trying to close the streams",e);
			}
		}
		
	
		return resource;
	}

}

	
