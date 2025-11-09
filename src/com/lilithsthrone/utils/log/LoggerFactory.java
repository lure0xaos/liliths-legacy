package com.lilithsthrone.utils.log;

import java.util.Objects;

public final class LoggerFactory implements ILoggerFactory {
    private final LogConfiguration configuration;

    public LoggerFactory() {
        this(Registry.getFirst(LogConfigurationProvider.class, Objects::nonNull, DefaultLogConfigurationProvider::new).get());
    }

    public LoggerFactory(LogConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public LogConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public ILogger getLogger(String name) {
        return configuration.getLogger(name, named -> new Logger(configuration, named));
    }

}
