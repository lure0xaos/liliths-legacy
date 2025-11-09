package com.lilithsthrone.utils.log;

public interface LoggerAppender extends Named {

    default boolean isLoggable(LoggerEvent event) {
        return true;
    }

    void log(String text);

}
