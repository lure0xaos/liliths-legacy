package com.lilithsthrone.utils.io;

@FunctionalInterface
public interface FilenameFilter {
    boolean accept(File dir, String name);
}