package com.lilithsthrone.utils.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class FileUtil {
    private FileUtil() {
    }

    private static final Map<String, MountInfo> mountPoints = FileUtil.newMap();

    public static void mount(String mount, java.io.File root, boolean readonly) {
        if (mountPoints.containsKey(mount))
            throw new FileSystemMountException("mount point " + mount + " already exists");
        for (Map.Entry<String, MountInfo> entry : mountPoints.entrySet()) {
            String entryMount = entry.getKey();
            MountInfo info = entry.getValue();
            java.io.File entryRoot = info.root();
            if (info.resources()) continue;
            if (contains(root, entryRoot))
                throw new FileSystemMountException("mount point " + entryMount + " intersects with mount point " + mount);
            if (contains(entryRoot, root))
                throw new FileSystemMountException("mount point " + mount + " intersects with mount point " + entryMount);
        }
        mountPoints.put(mount, new MountInfo(mount, root.getAbsoluteFile(), null, readonly, false));
    }

    public static void mountResources(String mount, Function<String, URL> loader) {
        if (mountPoints.containsKey(mount))
            throw new FileSystemMountException("mount point " + mount + " already exists");
        mountPoints.put(mount, new MountInfo(mount, null, loader, true, true));
    }

    public static void mountMeta(String mount, boolean readonly) {
        Map<String, String> map = FileUtil.readMeta();
        String home = System.getProperty("user.home", System.getProperty("user.dir"));
        String name = map.get("name");
        java.io.File root = new java.io.File(home, name + java.io.File.separator + mount).getAbsoluteFile();
        if (!root.exists() && !root.mkdirs()) throw new FileSystemWrongException(root.toPath());
        FileUtil.mount(mount, root, readonly);
    }

    public static String[] unroot(java.io.File file) {
        for (Map.Entry<String, MountInfo> entry : mountPoints.entrySet()) {
            String point = entry.getKey();
            MountInfo info = entry.getValue();
            if (info.resources()) continue;
            java.io.File root = info.root();
            if (contains(root, file.getAbsoluteFile())) {
                Path relative = root.toPath().relativize(file.toPath());
                return prepend(toParts(Paths.get(point).resolve(relative)));
            }
        }
        if (!file.getPath().isEmpty()) {
            for (Map.Entry<String, MountInfo> entry : mountPoints.entrySet()) {
                String point = entry.getKey();
                MountInfo info = entry.getValue();
                if (!info.resources()) continue;
                if (Objects.equals(file.toPath().getName(0).toString(), point)) {
                    Path relative = file.toPath();
                    return prepend(toParts(relative));
                }
                if (file.toPath().getName(0).toString().isEmpty() && Objects.equals(file.toPath().getName(1).toString(), point)) {
                    Path relative = file.toPath();
                    return toParts(relative);
                }
            }
        }
        for (Map.Entry<String, MountInfo> entry : mountPoints.entrySet()) {
            String point = entry.getKey();
            Path pointPath = Paths.get(point);
            if (Objects.equals(point, file.getPath())) {
                return prepend(toParts(pointPath));
            }
            if (Objects.equals(File.separator + point, file.getPath())) {
                return toParts(pointPath);
            }
        }
        for (Map.Entry<String, MountInfo> entry : mountPoints.entrySet()) {
            String point = entry.getKey();
            Path pointPath = Paths.get(point);
            if (file.getPath().startsWith(pointPath.toString())) {
                return prepend(toParts(pointPath.resolve(file.toPath().subpath(1, file.toPath().getNameCount()))));
            }
        }
        throw new FileSystemMountException("cannot unroot " + file);
    }

    public static java.io.File root(String[] path) {
        for (Map.Entry<String, MountInfo> entry : mountPoints.entrySet()) {
            String point = entry.getKey();
            MountInfo info = entry.getValue();
            java.io.File root = info.root();
            if (info.resources()) continue;
            if (Objects.equals("", path[0]) && Objects.equals(point, path[1])) {
                return new java.io.File(root, String.join(java.io.File.separator, slice(slice(path))));
            }
            if (Objects.equals(point, path[0])) {
                return new java.io.File(root, String.join(java.io.File.separator, slice(path)));
            }
        }
        throw new FileSystemMountException("cannot root " + Arrays.toString(path));
    }

    public static MountInfo findMountInfo(String[] path) {
        for (Map.Entry<String, MountInfo> entry : mountPoints.entrySet()) {
            String point = entry.getKey();
            MountInfo info = entry.getValue();
            if (Objects.equals("", path[0]) && Objects.equals(point, path[1])) {
                return info;
            }
            if (Objects.equals(point, path[0])) {
                return info;
            }
        }
        throw new FileSystemMountException("cannot find mount " + Arrays.toString(path));
    }

    private static boolean contains(java.io.File root, java.io.File sub) {
        return sub.getAbsolutePath().startsWith(root.getAbsolutePath());
    }

    private static String[] toParts(Path path) {
        String[] paths = new String[path.getNameCount()];
        for (int i = 0; i < path.getNameCount(); i++) {
            paths[i] = path.getName(i).toString();
        }
        return paths;
    }

    private static String[] slice(String[] strings) {
        return Arrays.copyOfRange(strings, 1, strings.length);
    }

    static String[] prepend(String[] strings) {
        return Stream.concat(Arrays.stream(new String[]{""}), Arrays.stream(strings))
                .toArray(String[]::new);
    }

    public static File[] mapFrom(java.io.File[] files) {
        if (files == null) return null;
        return Arrays.stream(files).map(File::new).toArray(File[]::new);
    }

    public static List<File> mapFrom(Collection<java.io.File> files) {
        if (files == null) return null;
        return files.stream().map(File::new).collect(Collectors.toList());
    }

    public static <T> List<T> newList() {
        return new CopyOnWriteArrayList<>();
    }

    public static <K, V> Map<K, V> newMap() {
        return new ConcurrentHashMap<>();
    }

    public static Map<String, List<String>> readResources() {
        return readProperties("resources", s -> Arrays.asList(s.split(Pattern.quote(java.io.File.pathSeparator))));
    }

    public static Map<String, String> readMeta() {
        return readProperties("meta", s -> s);
    }

    private static <V> Map<String, V> readProperties(String name, Function<String, V> transformValues) {
        Map<String, V> map = newMap();
        String location = "META-INF/" + name + ".properties";
        try (InputStream stream = FileUtil.class.getClassLoader().getResourceAsStream(location)) {
            Properties properties = new Properties();
            properties.load(stream);
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                map.put(entry.getKey().toString().trim(), transformValues.apply(entry.getValue().toString().trim()));
            }
        } catch (IOException e) {
            throw new UnderlyingException("cannot read properties from " + location, e);
        }
        return Collections.unmodifiableMap(map);
    }


    public static java.io.File toFile(Path path) {
        if (!(path instanceof FilePath)) throw new FileSystemWrongException(path);
        MountInfo info = ((FilePath) path).info;
        if (info.resources()) throw new FileSystemReadOnlyException(path);
        return path.toFile();
    }

    public static String resourcesPath(Path path) {
        if (!(path instanceof FilePath)) throw new FileSystemWrongException(path);
        MountInfo info = ((FilePath) path).info;
        if (!info.resources()) throw new FileSystemReadOnlyException(path);
        String pathString = path.toString();
        if (pathString.startsWith(info.mount() + '/')) {
            return pathString.substring(info.mount().length() + 1);
        }
        if (pathString.startsWith("/" + info.mount() + '/')) {
            return pathString.substring(info.mount().length() + 2);
        }
        if (Objects.equals(pathString, "/" + info.mount())) {
            return "";
        }
        throw new FileSystemWrongException(path);
    }

    public static BufferedReader newBufferedReader(Path path) throws IOException {
        if (!(path instanceof FilePath)) throw new FileSystemWrongException(path);
        return new BufferedReader(new InputStreamReader(newInputStream(path)));
    }

    public static InputStream newInputStream(Path path) throws IOException {
        if (!(path instanceof FilePath)) throw new FileSystemWrongException(path);
        MountInfo info = ((FilePath) path).info;
        if (info.resources()) {
            return info.loader().apply(FileUtil.resourcesPath(path)).openStream();
        } else {
            return new FileInputStream(path.toFile());
        }
    }

    public static OutputStream newOutputStream(Path path) throws IOException {
        if (!(path instanceof FilePath)) throw new FileSystemWrongException(path);
        File.checkReadonly(path);
        return new FileOutputStream(new File(path.toFile()));
    }

    public static List<String> readAllLines(Path path) throws IOException {
        if (!(path instanceof FilePath)) throw new FileSystemWrongException(path);
        try (BufferedReader reader = newBufferedReader(path)) {
            List<String> result = newList();
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                result.add(line);
            }
            return result;
        }
    }

    public static byte[] readAllBytes(Path path) throws IOException {
        if (!(path instanceof FilePath)) throw new FileSystemWrongException(path);
        try (InputStream stream = newInputStream(path)) {
            return stream.readAllBytes();
        }
    }
}
