package com.lilithsthrone.utils.log;

public interface ILoggerFactory {

    LogConfiguration getConfiguration();

    ILogger getLogger(String name);

    default ILogger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }

    default ILogger getRootLogger() {
        return getLogger("");
    }

}
