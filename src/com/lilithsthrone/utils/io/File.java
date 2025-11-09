package com.lilithsthrone.utils.io;

import com.lilithsthrone.utils.log.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class File implements Comparable<File> {

    public static final String separator = "/";

    public static void mount(String mount, java.io.File root) {
        FileUtil.mount(mount, root, false);
    }

    public static void mount(String mount, java.io.File root, boolean readonly) {
        FileUtil.mount(mount, root, readonly);
    }

    public static void mountResources(String mount, Function<String, URL> loader) {
        FileUtil.mountResources(mount, loader);
    }

    public static void mountMeta(String mount) {
        FileUtil.mountMeta(mount, false);
    }

    public static void mountMeta(String mount, boolean readonly) {
        FileUtil.mountMeta(mount, readonly);
    }

    public static List<File> mapFrom(List<java.io.File> files) {
        return FileUtil.mapFrom(files);
    }

    private final FilePath path;

    private File(String[] path) {
        MountInfo info = FileUtil.findMountInfo(path);
        if (info.resources()) {
            if (path[0].isEmpty())
                this.path = new FilePath(info, Arrays.copyOf(path, path.length));
            else
                this.path = new FilePath(info, FileUtil.prepend(Arrays.copyOf(path, path.length)));
        } else {
            this.path = new FilePath(info, Arrays.copyOf(path, path.length));
        }
    }

    File(java.io.File file) {
        this(FileUtil.unroot(file));
    }

    public File(String path) {
        this(FileUtil.unroot(new java.io.File(path)));
    }

    public File(String path, String name) {
        this(path + separator + name);
    }

    public File(String path, String... name) {
        this(path + separator + String.join(separator, name));
    }

    public File(File parent, String child) {
        this(parent.toPath() + separator + child);
    }

    public static void checkReadonly(Path path) {
        if (path instanceof FilePath) {
            MountInfo info = ((FilePath) path).info;
            if (info.readonly() || info.resources()) {
                throw new FileSystemReadOnlyException(path);
            }
        }
    }

    public static OutputStream newOutputStream(Path path) throws IOException {
        return FileUtil.newOutputStream(path);
    }

    public static InputStream newInputStream(Path path) throws IOException {
        return FileUtil.newInputStream(path);
    }

    public static List<String> readAllLines(Path path) throws IOException {
        if (path instanceof FilePath)
            return FileUtil.readAllLines(path);
        else
            return java.nio.file.Files.readAllLines(path);
    }

    public static byte[] readAllBytes(Path path) throws IOException {
        if (path instanceof FilePath)
            return FileUtil.readAllBytes(File.toRealPath(path));
        else
            return java.nio.file.Files.readAllBytes(path);
    }

    public java.io.File toFile() {
        if (path.info.resources()) throw new FileSystemReadOnlyException(path);
        return path.toFile();
    }

    public URI toUri() {
        return path.toUri();
    }

    public File getParentFile() {
        return new File(path.getParent().toString());
    }

    public void delete() {
        checkReadonly();
        if (!toFile().delete()) {
            Log.warn(() -> "cannot delete " + toFile());
        }
    }

    public String getName() {
        return path.getFileName().toString();
    }

    public boolean exists() {
        if (path.info.resources()) {
            Map<String, List<String>> map = path.info.listing();
            String resourcesPath = FileUtil.resourcesPath(path);
            if (resourcesPath.isEmpty()) return true;
            if (map.containsKey(resourcesPath)) return true;
            int i = resourcesPath.lastIndexOf('/');
            if (i < 0) return false;
            String parent = resourcesPath.substring(0, i);
            List<String> list = map.get(parent);
            if (list == null) return false;
            return list.contains(resourcesPath);
        }
        return toFile().exists();
    }

    public String getAbsolutePath() {
        return path.toAbsolutePath().toString();
    }

    public boolean isDirectory() {
        if (path.info.resources()) {
            Map<String, List<String>> map = path.info.listing();
            String resourcesPath = FileUtil.resourcesPath(path);
            return map.containsKey(resourcesPath);
        }
        return toFile().isDirectory();
    }

    public File[] listFiles() {
        if (path.info.resources()) {
            Map<String, List<String>> map = path.info.listing();
            String resourcesPath = FileUtil.resourcesPath(path);
            boolean containsKey = map.containsKey(resourcesPath);
            if (containsKey) {
                return map.get(resourcesPath).stream().map(f -> new File("/" + path.info.mount() + '/' + f))
                        .filter(f -> !f.path.toString().equals(path.toString()))
                        .toArray(File[]::new);
            } else {
                return null;
            }
        }
        return FileUtil.mapFrom(toFile().listFiles());
    }

    public File[] listFiles(FilenameFilter o) {
        if (path.info.resources()) {
            Map<String, List<String>> map = path.info.listing();
            String resourcesPath = FileUtil.resourcesPath(path);
            boolean containsKey = map.containsKey(resourcesPath);
            if (containsKey) {
                return map.get(resourcesPath).stream().map(f -> new File("/" + path.info.mount() + '/' + f))
                        .filter(f -> !f.path.toString().equals(path.toString()))
                        .filter(f -> o.accept(f, f.getName()))
                        .toArray(File[]::new);
            } else {
                return null;
            }
        }
        return FileUtil.mapFrom(toFile().listFiles((dir, name) -> o.accept(new File(dir), name)));
    }

    public File[] listFiles(FileFilter o) {
        if (path.info.resources()) {
            Map<String, List<String>> map = path.info.listing();
            String resourcesPath = FileUtil.resourcesPath(path);
            boolean containsKey = map.containsKey(resourcesPath);
            if (containsKey) {
                return map.get(resourcesPath).stream().map(f -> new File("/" + path.info.mount() + '/' + f))
                        .filter(f -> !f.path.toString().equals(path.toString()))
                        .filter(o::accept)
                        .toArray(File[]::new);
            } else {
                return null;
            }
        }
        return FileUtil.mapFrom(toFile().listFiles(dir -> o.accept(new File(dir))));
    }

    public void mkdir() {
        checkReadonly();
        if (!toFile().mkdir()) {
            Log.warn(() -> "cannot make dir " + toFile());
        }
    }

    public long lastModified() {
        return toFile().lastModified();
    }

    public Path toPath() {
        int nameCount = this.path.getNameCount();
        String[] path = new String[nameCount];
        for (int i = 0; i < nameCount; i++) {
            path[i] = this.path.getName(i).toString();
        }
        return new FilePath(this.path.info, Arrays.copyOf(path, path.length));
    }

    public String[] list() {
        File[] files = listFiles();
        if (files == null) return null;
        return Arrays.stream(files).map(File::getName).toArray(String[]::new);
    }

    public long length() {
        return toFile().length();
    }

    public String getParent() {
        return new File(toPath().getParent().toString()).getAbsolutePath();
    }

    public void mkdirs() {
        checkReadonly();
        if (!toFile().mkdirs()) {
            Log.warn(() -> "cannot make dirs " + toFile());
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("File(");
        int nameCount = path.getNameCount();
        for (int i = 0; i < nameCount; i++) {
            if (i != 0) builder.append(separator);
            builder.append(path.getName(i));
        }
        builder.append(")");
        if (!path.info.resources()) {
            builder.append("[");
            builder.append(toFile());
            builder.append("]");
        }
        return builder.toString();
    }

    public static Path toRealPath(Path path) {
        if (!(path instanceof FilePath)) throw new FileSystemWrongException(path);
        return path.toFile().toPath();
    }

    @Override
    public int compareTo(File o) {
        return toPath().compareTo(o.toPath());
    }

    private void checkReadonly() {
        checkReadonly(path);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof File && compareTo((File) o) == 0;
    }
}
