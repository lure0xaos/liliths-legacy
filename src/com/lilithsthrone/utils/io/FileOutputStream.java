package com.lilithsthrone.utils.io;

import java.io.IOException;

public final class FileOutputStream extends java.io.FilterOutputStream {
    public FileOutputStream(String name) throws IOException {
        this(new File(name));
    }

    public FileOutputStream(File file) throws IOException {
        super(File.newOutputStream(file.toPath()));
    }
}
