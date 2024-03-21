package com.lilithslegacy.rendering;

import com.lilithslegacy.controller.xmlParsing.Element;
import com.lilithslegacy.controller.xmlParsing.XMLLoadException;
import com.lilithslegacy.controller.xmlParsing.XMLMissingTagException;
import com.lilithslegacy.game.inventory.ColourReplacement;
import com.lilithslegacy.utils.SvgUtil;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.colours.Colour;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Can't find a good place to put the list for the future, so I'll put it here:<br/>
 * Items supporting patterns as of the creation of the system:<br/>
 * <i>
 * Bikini Bottoms<br/>
 * Backless Panties<br/>
 * Boxers<br/>
 * Boyshorts<br/>
 * Thigh highs<br/>
 * Work boots<br/>
 * Swimsuit<br/>
 * Bikini<br/>
 * Briefs<br/>
 * Crotchless briefs<br/>
 * Crotchless panties<br/>
 * Crotchless thong<br/>
 * Panties<br/>
 * Vstring<br/>
 * Elbow Length gloves<br/>
 * Fingerless gloves<br/>
 * Gloves<br/>
 * Cargo trousers<br/>
 * Thigh high socks<br/>
 * Knee high socks<br/>
 * Tshirt<br/>
 * </i>
 *
 * @author Irbynx, Innoxia, AceXp
 * @version 0.3.9.8
 * @since 0.2.6
 */
public class Pattern {

    private static Map<String, Pattern> allPatterns;
    private static Map<String, Pattern> defaultPatterns;

    private String id;
    private String name;
    private String displayName;

    private String baseSVGString;
    private Map<String, String> SVGStringMap;

    static {
        allPatterns = new TreeMap<>();
        defaultPatterns = new TreeMap<>();

        Pattern nonePattern = new Pattern("none", "none", null);
        allPatterns.put("none", nonePattern); // Adding empty pattern
        defaultPatterns.put("none", nonePattern);

        // Modded patterns:

        Map<String, Map<String, Path>> moddedFilesMap = Util.getExternalModFilesById("/items/patterns");
        for (Entry<String, Map<String, Path>> entry : moddedFilesMap.entrySet()) {
            for (Entry<String, Path> innerEntry : entry.getValue().entrySet()) {
                try {
                    loadFromFile(innerEntry.getKey(), innerEntry.getValue());
                } catch (Exception ex) {
                    System.getLogger("").log(System.Logger.Level.ERROR, "Loading modded pattern failed at 'Pattern'. FSPath path: " + innerEntry.getValue());
                    System.getLogger("").log(System.Logger.Level.ERROR, "Actual exception: ");
                    ex.printStackTrace(System.err);
                }
            }
        }

        // External res patterns:

        Map<String, Map<String, Path>> filesMap = Util.getExternalFilesById("/res/patterns");
        for (Entry<String, Map<String, Path>> entry : filesMap.entrySet()) {
            for (Entry<String, Path> innerEntry : entry.getValue().entrySet()) {
                try {
                    loadFromFile(innerEntry.getKey(), innerEntry.getValue());
//					System.out.println("P: "+innerEntry.getKey());
                } catch (Exception ex) {
                    System.getLogger("").log(System.Logger.Level.ERROR, "Loading pattern failed at 'Pattern'. FSPath path: " + innerEntry.getValue());
                    System.getLogger("").log(System.Logger.Level.ERROR, "Actual exception: ");
                    ex.printStackTrace(System.err);
                }
            }
        }

    }

    public Pattern(String id, String name, Path xmlFile) {
        this.id = id;
        this.name = name;
        SVGStringMap = new HashMap<>();

        baseSVGString = "";
        if (!name.equals("none")) {
            try {
                Path fileName = xmlFile.resolveSibling(name + ".svg");
                Path patternFile = fileName;
                List<String> lines = Files.readAllLines(patternFile);
                StringBuilder sb = new StringBuilder();
                for (String line : lines) {
                    sb.append(line);
                }
                baseSVGString = sb.toString();
            } catch (IOException e) {
                System.getLogger("").log(System.Logger.Level.ERROR, e);
            }
        }

    }

    private static Pattern loadFromFile(String id, Path patternXMLFile) {
        try {
            Element patternElement = Element.getDocumentRootElement(patternXMLFile);
            String loadedDisplayName = patternElement.getMandatoryFirstOf("name").getTextContent();
            boolean loadedDefaultPattern = Boolean.valueOf(patternElement.getMandatoryFirstOf("defaultPattern").getTextContent());
            String loadedPatternName = patternElement.getMandatoryFirstOf("patternName").getTextContent().replace(".svg", "");

            Pattern pattern = new Pattern(id, loadedPatternName, patternXMLFile);
            pattern.displayName = loadedDisplayName;

            allPatterns.put(id, pattern);
            if (loadedDefaultPattern) {
                defaultPatterns.put(id, pattern);
            }

            return pattern;

        } catch (XMLLoadException e) {
            System.getLogger("").log(System.Logger.Level.ERROR, e);
        } catch (XMLMissingTagException e) {
            System.getLogger("").log(System.Logger.Level.ERROR, e);
        }


        return null;
    }

    /**
     * Lookup pattern by id, return null if no match found.
     */
    public static Pattern getPattern(String id) {
        return allPatterns.get(id);
    }

    /**
     * Lookup pattern by name, return null if no match found.
     */
    public static Pattern getPatternByName(String name) {
        for (Pattern pattern : allPatterns.values()) {
            if (Objects.equals(pattern.getName(), name)) {
                return pattern;
            }
        }
        return null;
    }

    /**
     * Lookup pattern by id, else by name, return null if no match found.
     */
    public static Pattern getPatternByIdOrName(String idOrName) {
        if (allPatterns.containsKey(idOrName)) {
            return getPattern(idOrName);
        } else {
            return getPatternByName(idOrName);
        }
    }

    /**
     * Checks all found patterns and returns the id of one if available.
     */
    public static String getPatternIdByName(String name) {
        for (Pattern pattern : allPatterns.values()) {
            if (Objects.equals(pattern.getName(), name)) {
                return pattern.getId();
            }
        }
        // unable to find the pattern id, just return the name as-is
        return name;
    }

    /**
     * Returns all available patterns
     */
    public static List<Pattern> getAllPatterns() {
        return new ArrayList<>(allPatterns.values());
    }

    /**
     * Returns all default patterns which clothing can spawn with
     */
    public static List<Pattern> getAllDefaultPatterns() {
        return new ArrayList<>(defaultPatterns.values());
    }

    /**
     * Returns id of the pattern
     */
    public String getId() {
        return this.id;
    }

    /**
     * Returns name of the pattern
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns human readable name.
     *
     * @return
     */
    public String getNiceName() {
        if (displayName != null && displayName.length() > 0) {
            return displayName;
        }
        return this.name.replace('_', ' ');
    }

    public boolean isRecolourAvailable(ColourReplacement colourReplacement) {
        for (String s : colourReplacement.getColourReplacements()) {
            if (baseSVGString.contains(s)) {
                return true;
            }
        }
        return false;
    }

    private String generateIdentifier(List<Colour> colours) {
        StringBuilder sb = new StringBuilder(this.getId());
        for (Colour c : colours) {
            sb.append(c.getId());
        }
        return sb.toString();
    }

    public String getSVGString(List<Colour> patternColours, List<ColourReplacement> patternColourReplacements) {
        if (!SVGStringMap.containsKey(generateIdentifier(patternColours))) {
            generateSVGImage(patternColours, patternColourReplacements);
        }
        return SVGStringMap.get(generateIdentifier(patternColours));
    }

    private void generateSVGImage(List<Colour> patternColours, List<ColourReplacement> patternColourReplacements) {
        SVGStringMap.put(generateIdentifier(patternColours), SvgUtil.colourReplacementPattern(this.getId(), patternColours, patternColourReplacements, baseSVGString));
    }
}
