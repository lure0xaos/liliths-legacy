package com.lilithsthrone.utils.log;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

final class LoggerUtil {
    private LoggerUtil() {
    }

    private static final String logPackageName = packageName(Log.class.getName());
    private static final String[] blackedModules = new String[]{"java.base"};
    private static final String[] blackedPackages = new String[]{
            "com.sun.",
            "sun.",
            "java.",
            "javax.",
            "javafx.",
    };

    static StackTraceElement getCaller() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (elements.length > 1) {
            boolean first = true;
            for (int i = elements.length - 2; i >= 0; i--) {
                StackTraceElement element = elements[i];
                if (isBlacked(element)) {
                    if (first) {
                        if (Arrays.stream(elements).allMatch(LoggerUtil::isBlacked)) {
                            return new StackTraceElement("", "", "", -1);
                        }
                    } else {
                        return elements[i + 1];
                    }
                }
                first = false;
            }
        }
        return elements.length > 0 ? elements[0] : new StackTraceElement("", "", "", -1);
    }

    public static boolean isEmpty(LoggerEvent event) {
        return event.getSourceLineNumber() == -1;
    }

    private static boolean isBlacked(StackTraceElement element) {
        String elementClassName = element.getClassName();
        if (packageName(elementClassName).startsWith(logPackageName)) return true;
        for (String blackedModule : blackedModules) {
            if (Objects.equals(element.getModuleName(), blackedModule)) return true;
        }
        for (String blackedPackage : blackedPackages) {
            if (elementClassName.startsWith(blackedPackage)) return true;
        }
        return false;
    }

    private static String packageName(String className) {
        int i = className.lastIndexOf('.');
        return i < 0 ? "" : className.substring(0, i + 1);
    }

    public static <K, V> List<V> getValues(Map<K, List<V>> source, K key) {
        return source.computeIfAbsent(key, a -> newList());
    }

    public static <V> V find(Map<String, V> source, String name, Supplier<V> defaultValue) {
        if (source.containsKey(name)) return source.get(name);
        if (name.isEmpty()) defaultValue.get();
        int i = name.lastIndexOf('.');
        String parentName = i < 0 ? "" : name.substring(0, i);
        return find(source, parentName, defaultValue);
    }

    public static <V> List<V> getAll(Map<String, List<V>> source, String name) {
        return source.containsKey(name) ? Collections.unmodifiableList(source.get(name)) : Collections.emptyList();
    }

    static boolean allFiltersMatch(Collection<LoggerFilter> filters, LoggerEvent event) {
        return filters.stream().allMatch(filter -> filter.isLoggable(event));
    }

    static String substitute(List<LoggerSubstitutor> substitutors, String formatted) {
        String s = formatted;
        for (LoggerSubstitutor substitutor : substitutors) {
            String substituted = substitutor.substitute(s);
            if (substituted != null) s = substituted;
        }
        return s;
    }

    public static <T> List<T> newList() {
        return new CopyOnWriteArrayList<>();
    }

    public static <K, V> Map<K, V> newMap() {
        return new ConcurrentHashMap<>();
    }

    public static String parentName(String loggerName) {
        int i = loggerName.lastIndexOf('.');
        return i < 0 ? "" : loggerName.substring(0, i);
    }
}
