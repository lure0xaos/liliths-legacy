package com.lilithsthrone.utils.log;

import java.time.Instant;
import java.util.function.Supplier;

public interface ILogger extends Named {

    void log(LoggerEvent event);

    default LoggerEvent createEvent(Level level, Supplier<String> message, Throwable exception) {
        return new LoggerEvent(getName(), level, Instant.now(), message.get(), exception);
    }

    default boolean isLoggable(LoggerEvent event) {
        return true;
    }

    default boolean isLoggable(Level level) {
        return true;
    }

    default boolean isErrorLoggable() {
        return isLoggable(Level.ERROR);
    }

    default boolean isWarningLoggable() {
        return isLoggable(Level.WARN);
    }

    default boolean isInfoLoggable() {
        return isLoggable(Level.INFO);
    }

    default boolean isDebugLoggable() {
        return isLoggable(Level.DEBUG);
    }

    default boolean isTraceLoggable() {
        return isLoggable(Level.TRACE);
    }

    default void log(Level level, Supplier<String> message, Throwable exception) {
        if (isLoggable(level)) log(createEvent(level, message, exception));
    }

    default void log(Level level, Supplier<String> message) {
        if (isLoggable(level)) log(level, message, null);
    }

    default void error(Supplier<String> message) {
        if (isErrorLoggable()) log(Level.ERROR, message);
    }

    default void warn(Supplier<String> message) {
        if (isWarningLoggable()) log(Level.WARN, message);
    }

    default void info(Supplier<String> message) {
        if (isInfoLoggable()) log(Level.INFO, message);
    }

    default void debug(Supplier<String> message) {
        if (isDebugLoggable()) log(Level.DEBUG, message);
    }

    default void trace(Supplier<String> message) {
        if (isTraceLoggable()) log(Level.TRACE, message);
    }

    default void error(Supplier<String> message, Throwable exception) {
        if (isErrorLoggable()) log(Level.ERROR, message, exception);
    }

    default void warn(Supplier<String> message, Throwable exception) {
        if (isWarningLoggable()) log(Level.WARN, message, exception);
    }

    default void info(Supplier<String> message, Throwable exception) {
        if (isInfoLoggable()) log(Level.INFO, message, exception);
    }

    default void debug(Supplier<String> message, Throwable exception) {
        if (isDebugLoggable()) log(Level.DEBUG, message, exception);
    }

    default void trace(Supplier<String> message, Throwable exception) {
        if (isTraceLoggable()) log(Level.TRACE, message, exception);
    }
}
