package com.lilithsthrone.utils.log;

public class ConsoleLoggerAppender implements LoggerAppender {

    @Override
    public String getName() {
        return "Console";
    }

    @Override
    public void log(String text) {
        if (System.err instanceof LoggerPrintStream) {
            LoggerPrintStream.stdErr.print(text);
        } else {
            System.err.print(text);
        }
    }

    @Override
    public String toString() {
        return getName();
    }
}
