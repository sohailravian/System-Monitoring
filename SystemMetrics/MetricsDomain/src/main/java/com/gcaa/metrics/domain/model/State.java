package com.gcaa.metrics.domain.model;

public enum State {
    /**
     * Intermediate state in process creation
     */
    NEW,
    /**
     * Actively executing process
     */
    RUNNING,
    /**
     * Interruptible sleep state
     */
    SLEEPING,
    /**
     * Blocked, uninterruptible sleep state
     */
    WAITING,
    /**
     * Intermediate state in process termination
     */
    ZOMBIE,
    /**
     * Stopped by the user, such as for debugging
     */
    STOPPED,
    /**
     * Other or unknown states not defined
     */
    OTHER
}
