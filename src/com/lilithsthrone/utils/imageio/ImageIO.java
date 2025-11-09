package com.lilithsthrone.utils.imageio;

import com.lilithsthrone.utils.io.File;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

public final class ImageIO {
    private ImageIO() {
    }

    public static BufferedImage read(File file) throws IOException {
        return javax.imageio.ImageIO.read(file.toUri().toURL());
    }

    public static BufferedImage read(URL resource) throws IOException {
        return javax.imageio.ImageIO.read(resource);
    }

    public static void write(BufferedImage image, String png, ByteArrayOutputStream byteStream) throws IOException {
        javax.imageio.ImageIO.write(image, png, byteStream);
    }

    public static void setUseCache(boolean b) {
        javax.imageio.ImageIO.setUseCache(b);
    }
}
