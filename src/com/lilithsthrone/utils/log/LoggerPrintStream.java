package com.lilithsthrone.utils.log;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.charset.Charset;

public class LoggerPrintStream extends PrintStream {
    public LoggerPrintStream() {
        super(new LogOutputStream(Level.DEBUG, Charset.defaultCharset()));
    }

    public LoggerPrintStream(String fileName) throws FileNotFoundException {
//        super(new com.lilithsthrone.utils.io.File(fileName).toFile());
        this();
    }

    static final PrintStream stdErr = System.err;
}
