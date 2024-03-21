package com.lilithslegacy.utils.fs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.Collections;
import java.util.Objects;

public class FS {
    private static final Path DATA_ROOT;
    private static final FileSystem JAR_FILE_SYSTEM;

    static {
        try {
            URL archive = Objects.requireNonNull(FS.class.getResource("/com/lilithslegacy/utils/fs/res.jar"));
            DATA_ROOT = Path.of(System.getProperty("user.home")).resolve("lilithslegacy");
            if (!Files.exists(DATA_ROOT)) {
                Files.createDirectories(DATA_ROOT);
            }

            Path path = DATA_ROOT.resolve("res.jar");
            try (InputStream stream = archive.openStream()) {
                Files.copy(stream, path, StandardCopyOption.REPLACE_EXISTING);
            }
            String url = "jar:" + path.toUri().toURL().toExternalForm() + "!/";
            JAR_FILE_SYSTEM = FileSystems.newFileSystem(new URI(url), Collections.emptyMap());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path newPath(String... pathname) {
        return newPath(String.join("/", pathname));
    }

    public static Path newPath(String pathname) {
        String path = pathname.replace(File.separatorChar, '/');
        if (path.startsWith("data/")) return DATA_ROOT.resolve(path);
        if (path.startsWith("/res/") || path.startsWith("/com/lilithslegacy/res/")) {
            return JAR_FILE_SYSTEM.getPath("/" + path);
        }
        throw new UnsupportedOperationException("Cannot access files outside of DATA");
    }


    public static URL newResource(String name) {
        Path path = newPath(name);
        if (path != null) {
            try {
                return path.toUri().toURL();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static InputStream newResourceAsStream(String name) {
        Path path = newPath(name);
        if (path != null) {
            try {
                return Files.newInputStream(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

}
