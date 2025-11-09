package com.lilithsthrone.utils.log;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class SimpleLoggerFormatter implements LoggerFormatter {
    private static final String PATTERN_FORMAT = "dd.MM.yyyy HH:mm:ss.SSS";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT).withZone(ZoneId.systemDefault());

    @Override
    public String format(LoggerEvent event) {
        StringBuilder s = new StringBuilder();
        s.append("[").append(formatter.format(event.getTime())).append("]");
        s.append(" ").append(fixed(5, getLevelString(event.getLevel())));
        {
            String loggerName = event.getLoggerName();
            if (loggerName != null && !loggerName.isBlank())
                s.append(" ").append(loggerName);
        }
        {
            String threadName = event.getThreadName();
            if (threadName != null && !threadName.isBlank())
                s.append(" ").append("[").append(threadName).append("]");
        }
        {
            String sourceClassName = event.getSourceClassName();
            String sourceMethodName = event.getSourceMethodName();
            if (sourceClassName != null && !sourceClassName.isBlank()) {
                String compressClassName = compressClassName(sourceClassName, 0, 2);
                if (sourceMethodName != null && !sourceMethodName.isBlank()) {
                    s.append("(").append(compressClassName).append(".").append(sourceMethodName).append(")");
                } else {
                    s.append("(").append(compressClassName).append(")");
                }
            }
        }
        {
            String sourceFileName = event.getSourceFileName();
            int sourceLineNumber = event.getSourceLineNumber();
            if (sourceFileName != null && !sourceFileName.isBlank()) {
                if (sourceLineNumber > 0) {
                    s.append("(").append(sourceFileName).append(":").append(sourceLineNumber).append(")");
                } else {
                    s.append("(").append(sourceFileName).append(")");
                }
            }
        }
        {
            String message = event.getMessage();
            if (message != null && !message.isBlank())
                s.append(": ").append(message);
        }
        {
            Throwable exception = event.getException();
            if (exception != null)
                s.append(" ").append(LoggerFormatter.stackTraceString(exception));
        }
        s.append("\n");
        return s.toString();
    }

    private static String getLevelString(Level level) {
        return level.getName();
    }

    private static String compressClassName(String sourceClassName, int start, int end) {
        String[] packages = sourceClassName.split("\\.");
        int finish = Math.min(packages.length - 1, Math.max(start, packages.length - 1 - end));
        for (int i = start; i < finish; i++) {
            packages[i] = packages[i].substring(0, 1);
        }
        return String.join(".", packages);
    }

    private static String fixed(int len, String text) {
        if (text == null || text.isBlank()) return " ".repeat(len);
        int length = text.length();
        if (length == len) return text;
        if (length > len) return text.substring(0, len);
        return " ".repeat(len - length) + text;
    }

}
