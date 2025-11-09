package com.lilithsthrone.utils.io;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

final class MountInfo {
    private final String mount;
    private final java.io.File root;
    private final Function<String, URL> loader;
    private final boolean readonly;
    private final boolean resources;
    private final Map<String, List<String>> listing;

    MountInfo(String mount, File root, Function<String, URL> loader, boolean readonly, boolean resources) {
        this.mount = mount;
        this.root = root;
        this.loader = loader;
        this.readonly = readonly;
        this.resources = resources;
        if (resources)
            listing = FileUtil.readResources();
        else
            listing = null;
    }

    public String mount() {
        return mount;
    }

    public java.io.File root() {
        return root;
    }

    public Function<String, URL> loader() {
        return loader;
    }

    public boolean readonly() {
        return readonly;
    }

    public boolean resources() {
        return resources;
    }

    public Map<String, List<String>> listing() {
        return listing;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (MountInfo) obj;
        return Objects.equals(this.mount, that.mount) &&
                Objects.equals(this.root, that.root) &&
                Objects.equals(this.loader, that.loader) &&
                this.readonly == that.readonly &&
                this.resources == that.resources;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mount, root, loader, Boolean.valueOf(readonly), Boolean.valueOf(resources));
    }

    @Override
    public String toString() {
        return "MountInfo[" +
                "mount=" + mount + ", " +
                "root=" + root + ", " +
                "loader=" + loader + ", " +
                "readonly=" + readonly + ", " +
                "resources=" + resources + ']';
    }
}
