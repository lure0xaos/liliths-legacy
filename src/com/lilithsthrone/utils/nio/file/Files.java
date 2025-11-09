package com.lilithsthrone.utils.nio.file;

import com.lilithsthrone.utils.io.File;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.List;

public final class Files {
    private Files() {
    }

    public static List<String> readAllLines(Path path) throws IOException {
        return File.readAllLines(path);
    }

    public static byte[] readAllBytes(Path path) throws IOException {
        return File.readAllBytes(File.toRealPath(path));
    }

    public static void createDirectories(Path path) throws IOException {
        File.checkReadonly(path);
        java.nio.file.Files.createDirectories(File.toRealPath(path));
    }

    public static void copy(Path source, Path target, CopyOption... options) throws IOException {
        File.checkReadonly(target);
        java.nio.file.Files.copy(File.toRealPath(source), File.toRealPath(target), options);
    }

    public static void move(Path source, Path target, CopyOption... options) throws IOException {
        File.checkReadonly(source);
        File.checkReadonly(target);
        java.nio.file.Files.move(File.toRealPath(source), File.toRealPath(target), options);
    }

    public static FileTime getLastModifiedTime(Path path) throws IOException {
        return java.nio.file.Files.getLastModifiedTime(File.toRealPath(path));
    }
}
