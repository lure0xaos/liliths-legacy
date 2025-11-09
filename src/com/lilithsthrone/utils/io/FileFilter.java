package com.lilithsthrone.utils.io;

@FunctionalInterface
public interface FileFilter {
    boolean accept(File pathname);
}
