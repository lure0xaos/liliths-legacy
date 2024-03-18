package com.lilithslegacy.rendering;

import java.util.List;

import com.lilithslegacy.utils.colours.Colour;

/**
 * @author Innoxia
 * @version 0.2.2
 * @since 0.2.2
 */
public class Artist {

    private final String name;
    private final Colour colour;
    private final String folderName;
    private final List<ArtistWebsite> websites;

    public Artist(String name, Colour colour, String folderName, List<ArtistWebsite> websites) {
        this.name = name;
        this.colour = colour;
        this.folderName = folderName;
        this.websites = websites;
    }

    public String getName() {
        return name;
    }

    public Colour getColour() {
        return colour;
    }

    public String getFolderName() {
        return folderName;
    }

    public List<ArtistWebsite> getWebsites() {
        return websites;
    }

}
