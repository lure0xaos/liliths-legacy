package com.lilithsthrone.utils.log;

@FunctionalInterface
public interface LoggerFilter {
    boolean isLoggable(LoggerEvent event);
}
