package com.lilithsthrone.utils.log;

import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

final class Registry {
    private Registry() {
    }

    public static <T> List<T> getAll(Class<T> type) {
        return ServiceLoader.load(type).stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
    }

    public static <T> List<T> getAll(Class<T> type, Predicate<T> filter) {
        return ServiceLoader.load(type).stream().map(ServiceLoader.Provider::get).filter(filter).collect(Collectors.toList());
    }

    public static <T> T getFirst(Class<T> type, Supplier<? extends T> defaults) {
        return ServiceLoader.load(type).findFirst().orElseGet(defaults);
    }

    public static <T> T getFirst(Class<T> type, Predicate<T> filter, Supplier<? extends T> defaults) {
        return ServiceLoader.load(type).stream().map(ServiceLoader.Provider::get).filter(filter).findFirst().orElseGet(defaults);
    }
}
