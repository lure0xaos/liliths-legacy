package com.lilithsthrone.utils.log;

import java.io.Serializable;

public final class Level implements Serializable, Comparable<Level> {
    public static final Level ERROR = new Level("ERROR", 100);
    public static final Level WARN = new Level("WARN", 200);
    public static final Level INFO = new Level("INFO", 300);
    public static final Level DEBUG = new Level("DEBUG", 400);
    public static final Level TRACE = new Level("TRACE", 500);

    private final String name;
    private final int value;

    public Level(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public boolean isLoggable(Level loggerLevel) {
        return loggerLevel.value >= value;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Level level = (Level) o;
        return value == level.value;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public int compareTo(Level o) {
        return Integer.compare(value, o.value);
    }
}
