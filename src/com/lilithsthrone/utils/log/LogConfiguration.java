package com.lilithsthrone.utils.log;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class LogConfiguration {

    private final List<LoggerFilter> filters = LoggerUtil.newList();
    private final List<LoggerSubstitutor> substitutors = LoggerUtil.newList();
    private final Map<String, ILogger> loggers = LoggerUtil.newMap();
    private final Map<String, List<LoggerFilter>> loggerFilters = LoggerUtil.newMap();
    private final Map<String, Level> levels = LoggerUtil.newMap();
    private final Map<String, List<LoggerAppender>> appenders = LoggerUtil.newMap();
    private final Map<String, List<LoggerFilter>> appenderFilters = LoggerUtil.newMap();
    private final Map<String, LoggerFormatter> formatters = LoggerUtil.newMap();
    private final Map<String, List<LoggerSubstitutor>> loggerSubstitutors = LoggerUtil.newMap();
    private final LoggerFormatter defaultFormatter = new SimpleLoggerFormatter();

    public ILogger getLogger(String name, Function<String, ILogger> loggerCreator) {
        return loggers.computeIfAbsent(name, named -> {
            ILogger parentLogger = findLogger(named);
            if (parentLogger == null) {
                ILogger rootLogger = loggerCreator.apply("");
                ILogger newLogger = loggerCreator.apply(named);
                copyLogger(named, rootLogger.getName());
                return newLogger;
            } else {
                ILogger newLogger = loggerCreator.apply(named);
                copyLogger(named, parentLogger.getName());
                return newLogger;
            }
        });

    }

    private void copyLogger(String name, String parentName) {
        copyLoggerConfig(name, parentName, loggerFilters);
        copyLoggerConfig(name, parentName, levels);
        copyLoggerConfig(name, parentName, appenders);
    }

    private static <V> void copyLoggerConfig(String name, String parentName, Map<String, V> map) {
        if (!map.containsKey(name) && map.containsKey(parentName)) map.put(name, map.get(parentName));
    }

    private ILogger findLogger(String name) {
        return LoggerUtil.find(loggers, name, () -> null);
    }

    public void addLogger(ILogger logger) {
        loggers.put(logger.getName(), logger);
    }

    public List<LoggerFilter> getFilters() {
        return Collections.unmodifiableList(filters);
    }

    public void addFilters(List<LoggerFilter> filter) {
        filters.addAll(filter);
    }

    public void addFilter(LoggerFilter filter) {
        filters.add(filter);
    }

    public List<LoggerSubstitutor> getSubstitutors() {
        return Collections.unmodifiableList(substitutors);
    }

    public void addSubstitutors(List<LoggerSubstitutor> substitutor) {
        substitutors.addAll(substitutor);
    }

    public void addSubstitutor(LoggerSubstitutor substitutor) {
        substitutors.add(substitutor);
    }

    public Map<String, List<LoggerSubstitutor>> getLoggerSubstitutors() {
        return Collections.unmodifiableMap(loggerSubstitutors);
    }

    public List<LoggerSubstitutor> getLoggerSubstitutors(LoggerAppender appender) {
        return LoggerUtil.getAll(loggerSubstitutors, appender.getName());
    }

    public void addLoggerSubstitutors(LoggerAppender appender, List<LoggerSubstitutor> loggerSubstitutor) {
        LoggerUtil.getValues(loggerSubstitutors, appender.getName()).addAll(loggerSubstitutor);
    }

    public void addLoggerSubstitutor(LoggerAppender appender, LoggerSubstitutor loggerSubstitutor) {
        LoggerUtil.getValues(loggerSubstitutors, appender.getName()).add(loggerSubstitutor);
    }

    public Map<String, LoggerFormatter> getFormatters() {
        return Collections.unmodifiableMap(formatters);
    }

    public LoggerFormatter getFormatter(LoggerAppender appender) {
        return formatters.getOrDefault(appender.getName(), defaultFormatter);
    }

    public void setFormatter(LoggerAppender appender, LoggerFormatter formatter) {
        formatters.put(appender.getName(), formatter);
    }

    public Level getLevel(ILogger logger) {
        return levels.get(logger.getName());
    }

    public void setLevel(ILogger logger, Level level) {
        levels.put(logger.getName(), level);
    }

    public List<LoggerFilter> getLoggerFilters(ILogger logger) {
        return LoggerUtil.getValues(loggerFilters, logger.getName());
    }

    public void addLoggerFilters(ILogger logger, List<LoggerFilter> filters) {
        LoggerUtil.getValues(loggerFilters, logger.getName()).addAll(filters);
    }

    public void addLoggerFilter(ILogger logger, LoggerFilter filter) {
        LoggerUtil.getValues(loggerFilters, logger.getName()).add(filter);
    }

    public Map<String, List<LoggerFilter>> getAppenderFilters() {
        return Collections.unmodifiableMap(appenderFilters);
    }

    public List<LoggerFilter> getAppenderFilters(LoggerAppender appender) {
        return LoggerUtil.getAll(appenderFilters, appender.getName());
    }

    public void addAppenderFilters(LoggerAppender appender, List<LoggerFilter> filters) {
        LoggerUtil.getValues(appenderFilters, appender.getName()).addAll(filters);
    }

    public void addAppenderFilter(LoggerAppender appender, LoggerFilter filter) {
        LoggerUtil.getValues(appenderFilters, appender.getName()).add(filter);
    }

    public Map<String, List<LoggerAppender>> getAppenders() {
        return Collections.unmodifiableMap(appenders);
    }

    public List<LoggerAppender> getAppenders(ILogger logger) {
        return LoggerUtil.getValues(appenders, logger.getName());
    }

    public void addAppender(ILogger logger, LoggerAppender appender) {
        LoggerUtil.getValues(appenders, logger.getName()).add(appender);
    }

    public void addAppenders(ILogger logger, List<LoggerAppender> appenders1) {
        LoggerUtil.getValues(appenders, logger.getName()).addAll(appenders1);
    }

    public void addAppenders(ILogger logger, LoggerAppender appender) {
        LoggerUtil.getValues(appenders, logger.getName()).add(appender);
    }
}
