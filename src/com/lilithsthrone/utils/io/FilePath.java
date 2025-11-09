package com.lilithsthrone.utils.io;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

class FilePath implements Path {
    final MountInfo info;
    private final String[] path;

    FilePath(MountInfo info, String[] path) {
        this.info = info;
        this.path = Arrays.copyOf(path, path.length);
    }

    @Override
    public String toString() {
        return String.join(File.separator, path);
    }

    @Override
    public java.io.File toFile() {
        if (info.resources()) throw new FileSystemReadOnlyException(this);
        return FileUtil.root(path);
    }

    @Override
    public FileSystem getFileSystem() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAbsolute() {
        if (info.resources() && Objects.equals(path[0], info.mount())) return true;
        return Objects.equals("", path[0]);
    }

    @Override
    public Path getRoot() {
        return new FilePath(info, Arrays.copyOf(path, 2));
    }

    @Override
    public Path getFileName() {
        return new FilePath(info, Arrays.copyOfRange(path, path.length - 1, path.length));
    }

    @Override
    public Path getParent() {
        return new FilePath(info, Objects.equals("", path[0]) ? (path.length > 2 ? Arrays.copyOf(path, path.length - 1) : Arrays.copyOf(path, path.length)) : Arrays.copyOf(path, path.length - 1));
    }

    @Override
    public int getNameCount() {
        return path.length;
    }

    @Override
    public Path getName(int index) {
        return new FilePath(info, Arrays.copyOfRange(path, index, index + 1));
    }

    @Override
    public Path subpath(int beginIndex, int endIndex) {
        return new FilePath(info, Arrays.copyOfRange(path, beginIndex, endIndex));
    }

    @Override
    public boolean startsWith(Path other) {
        for (int i = 0; i < other.getNameCount(); i++) {
            if (!Objects.equals(path[i], other.getName(i).toString())) return false;
        }
        return true;
    }

    @Override
    public boolean endsWith(Path other) {
        for (int i = other.getNameCount() - 1; i >= 0; i--) {
            if (!Objects.equals(path[i], other.getName(i).toString())) return false;
        }
        return true;
    }

    @Override
    public Path normalize() {
        throw new NotImplementedException();
    }

    @Override
    public Path resolve(Path other) {
        if (!(other instanceof FilePath)) throw new FileSystemWrongException(other);
        if (other.isAbsolute()) return other;
        return new FilePath(info, Stream.concat(Arrays.stream(this.path), Arrays.stream(((FilePath) other).path))
                .toArray(String[]::new));
    }

    @Override
    public Path relativize(Path other) {
        if (!(other instanceof FilePath)) throw new FileSystemWrongException(other);
        throw new NotImplementedException();
    }

    @Override
    public URI toUri() {
        if (info.resources()) {
            try {
                return info.loader().apply(FileUtil.resourcesPath(this)).toURI();
            } catch (URISyntaxException e) {
                throw new UnderlyingException("cannot convert path " + this + "to uri:" + e.getInput() + e.getReason(), e);
            }
        } else
            return toFile().toURI();
    }

    @Override
    public Path toAbsolutePath() {
        if (isAbsolute()) return this;
        throw new NotImplementedException();
    }

    @Override
    public Path toRealPath(LinkOption... options) {
        return toFile().toPath();
    }

    @Override
    public WatchKey register(WatchService watcher, WatchEvent.Kind<?>[] events, WatchEvent.Modifier... modifiers) throws IOException {
        throw new NotImplementedException();
    }

    @Override
    public int compareTo(Path other) {
        if (!(other instanceof FilePath)) throw new FileSystemWrongException(other);
        return String.join(File.separator, path).compareTo(String.join(File.separator, ((FilePath) other).path));
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof FilePath && compareTo((FilePath) o) == 0;
    }
}
