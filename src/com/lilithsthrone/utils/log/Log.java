package com.lilithsthrone.utils.log;

import java.util.function.Supplier;

public final class Log {
    private Log() {
    }

    private static final ILoggerFactory defaultFactory = Registry.getFirst(ILoggerFactory.class, LoggerFactory::new);

    public static ILogger getLogger(String name) {
        return defaultFactory.getLogger(name);
    }

    public static ILogger getLogger(Class<?> name) {
        return getLogger(name.getName());
    }

    public static void log(LoggerEvent event) {
        ILogger logger = getLogger();
        if (logger.isLoggable(event)) logger.log(event);
    }

    public static void log(Level level, Supplier<String> message, Throwable exception) {
        ILogger logger = getLogger();
        if (logger.isLoggable(level)) logger.log(level, message, exception);
    }

    public static void log(Level level, Supplier<String> message) {
        log(level, message, null);
    }

    public static void error(Supplier<String> message) {
        log(Level.ERROR, message);
    }

    public static void warn(Supplier<String> message) {
        log(Level.WARN, message);
    }

    public static void info(Supplier<String> message) {
        log(Level.INFO, message);
    }

    public static void debug(Supplier<String> message) {
        log(Level.DEBUG, message);
    }

    public static void trace(Supplier<String> message) {
        log(Level.TRACE, message);
    }

    public static void error(Supplier<String> message, Throwable exception) {
        log(Level.ERROR, message, exception);
    }

    public static void warn(Supplier<String> message, Throwable exception) {
        log(Level.WARN, message, exception);
    }

    public static void info(Supplier<String> message, Throwable exception) {
        log(Level.INFO, message, exception);
    }

    public static void debug(Supplier<String> message, Throwable exception) {
        log(Level.DEBUG, message, exception);
    }

    public static void trace(Supplier<String> message, Throwable exception) {
        log(Level.TRACE, message, exception);
    }

    private static ILogger getLogger() {
        StackTraceElement caller = LoggerUtil.getCaller();
        return defaultFactory.getLogger(caller.getClassName());
    }

}
