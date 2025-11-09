package com.lilithsthrone.utils.nio.file;

import com.lilithsthrone.utils.io.File;

import java.nio.file.Path;

public final class Paths {
    private Paths() {
    }

    public static Path get(String path) {
        try {
            File file = new File(path);
            if (file.exists()) return file.toPath();
        } catch (RuntimeException ignored) {
        }
        return java.nio.file.Paths.get(path);
    }

    public static Path get(String first, String... other) {
        try {
            File file = new File(first, other);
            if (file.exists()) return file.toPath();
        } catch (RuntimeException ignored) {
        }
        return java.nio.file.Paths.get(first, other);
    }
}
