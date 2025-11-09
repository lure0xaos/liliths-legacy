package com.lilithsthrone.utils.io;

import java.nio.file.Path;

public class FileSystemReadOnlyException extends RuntimeException {
    public FileSystemReadOnlyException(Path path) {
        super("cannot write to " + path + " of readonly filesystem");
    }
}
