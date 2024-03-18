package com.lilithslegacy.game.inventory.item;

import java.nio.file.Path;
import java.util.Map;

/**
 * @author Innoxia
 * @version 0.4.0
 * @since 0.4
 */
public class SvgInformation {

    private final int zLayer;
    private final String pathName;
    private final int imageSize;
    private final int imageRotation;
    private final Map<String, String> replacements;

    public SvgInformation(int zLayer, String pathName, int imageSize, int imageRotation, Map<String, String> replacements) {
        this.zLayer = zLayer;
        this.pathName = pathName;
        this.imageSize = imageSize;
        this.imageRotation = imageRotation;
        this.replacements = replacements;
    }

    public int getZLayer() {
        return zLayer;
    }

    public String getPathName() {
        return pathName;
    }

    public int getImageSize() {
        return imageSize;
    }

    public int getImageRotation() {
        return imageRotation;
    }

    public Map<String, String> getReplacements() {
        return replacements;
    }

}
