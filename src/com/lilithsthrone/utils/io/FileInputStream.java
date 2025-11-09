package com.lilithsthrone.utils.io;

import java.io.IOException;

public final class FileInputStream extends java.io.FilterInputStream {
    public FileInputStream(String name) throws IOException {
        this(new File(name));
    }

    public FileInputStream(File file) throws IOException {
            super(File.newInputStream(file.toPath()));
    }
}
