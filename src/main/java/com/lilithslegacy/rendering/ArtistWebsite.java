package com.lilithslegacy.rendering;

/**
 * @author Innoxia
 * @version 0.2.2
 * @since 0.2.2
 */
public class ArtistWebsite {

    private final String name;
    private final String URL;

    public ArtistWebsite(String name, String URL) {
        this.name = name;
        this.URL = URL;
    }

    public String getName() {
        return name;
    }

    public String getURL() {
        return URL;
    }
}
