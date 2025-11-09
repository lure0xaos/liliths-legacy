package com.lilithsthrone.utils.io;

import java.nio.file.Path;

public class FileSystemWrongException extends RuntimeException {
    public FileSystemWrongException(Path path) {
        super("using wrong filesystem of " + path);
    }
}
