package com.lilithslegacy.rendering;

import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

/**
 * @author Addi
 * @version 0.3.0
 * @since 0.3.0
 */
public class CachedGif extends CachedImage {
    private String thumbnailString;

    @Override
    public boolean load(Path f) {
        // Load the first frame of the image
        CachedImage firstFrame = new CachedImage();
        if (!firstFrame.load(f)) return false;
        thumbnailString = firstFrame.getThumbnailString();
        percentageWidth = firstFrame.getPercentageWidth();
        width = firstFrame.getWidth();
        height = firstFrame.getHeight();

        long result;
        try {
            result = Files.size(f);
        } catch (IOException e1) {
            result = -1;
        }
        if (result / 1024 > 10240) {
            // Animated image is too large, use the first frame instead
            imageString = firstFrame.getImageString();
            System.getLogger("").log(System.Logger.Level.ERROR, "Warning: Animated image " + f.getFileName().toString() + " is too large. Using first frame instead.");
        } else {
            // Load the animation
            try {
                imageString = "data:image/gif;base64,"
                        + Base64.getEncoder().encodeToString(Files.readAllBytes(f));
            } catch (IOException e) {
                System.getLogger("").log(System.Logger.Level.ERROR, e.getMessage(), e);
                return false;
            }
        }
        return true;
    }

    @Override
    public String getThumbnailString() {
        return thumbnailString;
    }
}
