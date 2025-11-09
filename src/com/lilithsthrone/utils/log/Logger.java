package com.lilithsthrone.utils.log;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

final class Logger implements ILogger {
    private static final ExecutorService executor = Executors.newFixedThreadPool(10, r -> {
        Thread t = Executors.defaultThreadFactory().newThread(r);
        t.setDaemon(true);
        t.setName("logger-" + UUID.randomUUID());
        return t;
    });

    private final LogConfiguration configuration;
    private final String name;

    Logger(LogConfiguration configuration, String name) {
        this.configuration = configuration;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isLoggable(Level level) {
        return level.isLoggable(configuration.getLevel(this));
    }

    @Override
    public void log(LoggerEvent event) {
        executor.submit(() -> {
            if (isLoggable(event.getLevel()) && isLoggable(event) &&
                    !LoggerUtil.isEmpty(event) &&
                    LoggerUtil.allFiltersMatch(configuration.getFilters(), event) &&
                    LoggerUtil.allFiltersMatch(configuration.getLoggerFilters(this), event)) {
                for (LoggerAppender appender : configuration.getAppenders(this)) {
                    if (appender.isLoggable(event) &&
                            LoggerUtil.allFiltersMatch(configuration.getAppenderFilters(appender), event)) {
                        appender.log(
                                LoggerUtil.substitute(configuration.getLoggerSubstitutors(appender),
                                        LoggerUtil.substitute(configuration.getSubstitutors(),
                                                configuration.getFormatter(appender).format(event))));
                    }
                }
            }
        });
    }

    @Override
    public String toString() {
        return name;
    }

}
