package com.lilithsthrone.utils.log;

import java.time.Instant;

public final class LoggerEvent {
    private final String loggerName;
    private final Level level;
    private final Instant time;
    private final String message;
    private final Throwable exception;
    private final String threadName;
    private final String sourceClassName;
    private final String sourceMethodName;
    private final String sourceFileName;
    private final int sourceLineNumber;
    private final String sourceModuleName;
    private final String sourceModuleVersion;

    public LoggerEvent(String loggerName,
                       Level level,
                       Instant time,
                       String message,
                       Throwable exception,
                       String threadName,
                       String sourceClassName,
                       String sourceMethodName,
                       String sourceFileName,
                       int sourceLineNumber,
                       String sourceModuleName,
                       String sourceModuleVersion) {
        this.loggerName = loggerName;
        this.level = level;
        this.time = time;
        this.message = message;
        this.exception = exception;
        this.threadName = threadName;
        this.sourceClassName = sourceClassName;
        this.sourceMethodName = sourceMethodName;
        this.sourceFileName = sourceFileName;
        this.sourceLineNumber = sourceLineNumber;
        this.sourceModuleName = sourceModuleName;
        this.sourceModuleVersion = sourceModuleVersion;
    }

    public LoggerEvent(String loggerName,
                       Level level,
                       Instant time,
                       String message,
                       Throwable exception) {
        Thread thread = Thread.currentThread();
        StringBuilder threadName = new StringBuilder(thread.getName());
        ThreadGroup threadGroup = thread.getThreadGroup();
        while (threadGroup != null) {
            threadName.insert(0, "/").insert(0, threadGroup.getName());
            threadGroup = threadGroup.getParent();
        }
        this.threadName = threadName.toString();
        StackTraceElement caller = LoggerUtil.getCaller();
        this.loggerName = loggerName;
        this.level = level;
        this.time = time;
        this.message = message;
        this.exception = exception;
        this.sourceClassName = caller.getClassName();
        this.sourceMethodName = caller.getMethodName();
        this.sourceFileName = caller.getFileName();
        this.sourceLineNumber = caller.getLineNumber();
        this.sourceModuleName = caller.getModuleName();
        this.sourceModuleVersion = caller.getModuleVersion();
    }

    public String getLoggerName() {
        return loggerName;
    }

    public Level getLevel() {
        return level;
    }

    public Instant getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getException() {
        return exception;
    }

    public String getThreadName() {
        return threadName;
    }

    public String getSourceClassName() {
        return sourceClassName;
    }

    public String getSourceMethodName() {
        return sourceMethodName;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public int getSourceLineNumber() {
        return sourceLineNumber;
    }

    public String getSourceModuleName() {
        return sourceModuleName;
    }

    public String getSourceModuleVersion() {
        return sourceModuleVersion;
    }
}
