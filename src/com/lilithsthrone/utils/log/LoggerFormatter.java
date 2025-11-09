package com.lilithsthrone.utils.log;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public interface LoggerFormatter {
    static String stackTraceString(Throwable exception) {
        String exceptionString = "";
        if (exception != null) {
            try (StringWriter sw = new StringWriter()) {
                try (PrintWriter pw = new PrintWriter(sw)) {
                    exception.printStackTrace(pw);
                }
                exceptionString = sw.toString();
            } catch (IOException ignored) {
            }
        }
        return exceptionString;
    }

    String format(LoggerEvent event);
}
