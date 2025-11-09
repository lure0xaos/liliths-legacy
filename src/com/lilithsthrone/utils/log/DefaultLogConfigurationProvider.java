package com.lilithsthrone.utils.log;

public class DefaultLogConfigurationProvider implements LogConfigurationProvider {
    @Override
    public LogConfiguration get() {
        LogConfiguration configuration = new LogConfiguration();
        ILogger rootLogger = new Logger(configuration, "");
        configuration.setLevel(rootLogger, Level.INFO);
        configuration.addLogger(rootLogger);
        configuration.addSubstitutors(Registry.getAll(LoggerSubstitutor.class));
        LoggerAppender loggerAppender = new ConsoleLoggerAppender();
        configuration.addAppender(rootLogger, loggerAppender);
        configuration.setFormatter(loggerAppender, new SimpleLoggerFormatter());
        return configuration;
    }
}
