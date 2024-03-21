package com.lilithslegacy.rendering;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.utils.fs.FS;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

/**
 * @author Innoxia
 * @version 0.3.7.3
 * @since 0.2.2
 */
public class Artwork {

    private final GameCharacter character;
    private final Artist artist;

    private int index;

    private final List<Path> clothedImages;
    private final List<Path> partialImages;
    private final List<Path> nakedImages;

    public static List<Artist> allArtists;

    public static Artist customArtist;

    static {
        allArtists = new ArrayList<>();

        customArtist = new Artist("Custom", PresetColour.BASE_GREY, "custom", new ArrayList<>());

        Path dir = FS.newPath("/res/images/characters");

        if (Files.exists(dir)) {

            Path[] result;
            try (Stream<java.nio.file.Path> stream = Files.list(dir)) {
                result = stream.filter(p -> ((BiPredicate<java.nio.file.Path, String>) (dir1, name) -> name.toLowerCase().endsWith(".xml")).test(p, p.getFileName().toString())).map(p -> p).toList().toArray(new Path[0]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (Path subFile : result) {
                if (Files.exists(subFile)) {
                    try {
                        Document result1;
                        try {
                            result1 = Main.getDocBuilder().parse(Files.newInputStream(subFile));
                        } catch (IOException | SAXException e) {
                            throw new RuntimeException(e);
                        }
                        Document doc = result1;

                        // Cast magic:
                        doc.getDocumentElement().normalize();

                        Element artistElement = (Element) doc.getElementsByTagName("artist").item(0);

                        String artistName = artistElement.getAttribute("name");
                        String colourId = artistElement.getAttribute("colour");
                        Colour colour;
                        if (colourId.startsWith("#")) {
                            colour = new Colour(false, Util.newColour(colourId), Util.newColour(colourId), "");
                        } else {
                            colour = PresetColour.getColourFromId(colourId);
                        }
                        String folderName = artistElement.getAttribute("folderName");

                        List<ArtistWebsite> websites = new ArrayList<>();

                        NodeList nodes = artistElement.getElementsByTagName("website");
                        for (int i = 0; i < nodes.getLength(); i++) {
                            Element websiteNode = (Element) nodes.item(i);
                            websites.add(new ArtistWebsite(websiteNode.getAttribute("title"), websiteNode.getAttribute("url")));
                        }

                        allArtists.add(new Artist(artistName, colour, folderName, websites));

                    } catch (Exception ex) {
                        System.getLogger("").log(System.Logger.Level.ERROR, ex);
                    }
                }
            }

            // Add artist template for custom art
            allArtists.add(customArtist);
        }
    }

    public Artwork(GameCharacter character, Path folder, Artist artist) {
        this.character = character;
        this.artist = artist;

        index = 0;

        this.clothedImages = new ArrayList<>();
        this.partialImages = new ArrayList<>();
        this.nakedImages = new ArrayList<>();

        // Add all images to their respective lists
        Path[] result;
        try (Stream<java.nio.file.Path> stream = Files.list(folder)) {
            result = stream.filter(p -> ((BiPredicate<java.nio.file.Path, String>) (dir, name) -> name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".gif")).test(p, p.getFileName().toString())).map(p -> p).toList().toArray(new Path[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Path f : result) {
            if (f.getFileName().toString().startsWith("partial")) {
                partialImages.add(f);

            } else if (f.getFileName().toString().startsWith("naked")) {
                nakedImages.add(f);

            } else {
                clothedImages.add(f);
            }
        }
    }

    public Artist getArtist() {
        return artist;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        index = index % getTotalArtworkCount();
        if (index < 0) {
            index = getTotalArtworkCount() + index;
        }
        this.index = index;
    }

    public void incrementIndex(int increment) {
        setIndex(this.index + increment);
    }

    public int getTotalArtworkCount() {
        return getFilteredImages(clothedImages).size() + getFilteredImages(partialImages).size() + getFilteredImages(nakedImages).size();
    }

    public boolean isCurrentImageClothed() {
        return index < getFilteredImages(clothedImages).size();
    }

    public Path getCurrentImage() {
        if (getTotalArtworkCount() == 0) {
            return null;
        }
        Path path;
        try {
            if (index < getFilteredImages(clothedImages).size()) {
                path = getFilteredImages(clothedImages).get(index);

            } else if (index < getFilteredImages(clothedImages).size() + getFilteredImages(partialImages).size()) {
                path = getFilteredImages(partialImages).get(index - getFilteredImages(clothedImages).size());

            } else {
                path = getFilteredImages(nakedImages).get(index - getFilteredImages(clothedImages).size() - getFilteredImages(partialImages).size());
            }
        } catch (IndexOutOfBoundsException ex) {
            path = getFilteredImages(clothedImages).get(0);
        }

        if (path.getNameCount() == 0) {
            return null;
        }
        return path;
    }

    private List<Path> getFilteredImages(List<Path> images) {
        List<Path> filteredImages = new ArrayList<>(images);
        filteredImages.removeIf(s -> s.toString().contains("penis") && !character.hasPenisIgnoreDildo());
        filteredImages.removeIf(s -> s.toString().contains("vagina") && !character.hasVagina());
        return filteredImages;
    }

    public List<Path> getAllImagePaths() {
        List<Path> imagePaths = new ArrayList<>();
        imagePaths.addAll(clothedImages);
        imagePaths.addAll(partialImages);
        imagePaths.addAll(nakedImages);
        return imagePaths;
    }
}
