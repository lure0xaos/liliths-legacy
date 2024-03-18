package com.lilithslegacy.game.inventory.clothing;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import com.lilithslegacy.game.inventory.InventorySlot;
import com.lilithslegacy.game.inventory.ItemTag;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.colours.Colour;

/**
 * @author Innoxia
 * @version 0.3.9.5
 * @since 0.3.9.5
 */
public class Sticker {

    private final String id;
    private final String name;
    private final int priority;
    private final boolean defaultSticker;
    private final Colour colourDisabled;
    private final Colour colourSelected;

    private final String namePrefix;
    private final int namePrefixPriority;

    private final String namePostfix;
    private final int namePostfixPriority;

    private final String description;
    private final boolean descriptionFullReplacement;
    private final int descriptionPriority;

    private final Map<InventorySlot, Map<Integer, Path>> svgPaths;

    private final List<ItemTag> tagsApplied;
    private final List<ItemTag> tagsRemoved;

    private final boolean showDisabledButton;
    private final String unavailabilityText;
    private final String availabilityText;


    public Sticker(String id, String name, int priority, boolean defaultSticker,
                   Colour colourDisabled, Colour colourSelected,
                   String namePrefix, int namePrefixPriority,
                   String namePostfix, int namePostfixPriority,
                   String description, boolean descriptionFullReplacement, int descriptionPriority,
                   Map<InventorySlot, Map<Integer, Path>> svgPaths,
                   List<ItemTag> tagsApplied,
                   List<ItemTag> tagsRemoved,
                   boolean showDisabledButton, String unavailabilityText, String availabilityText) {
        this.id = id.replaceAll("'", "").replaceAll("\"", "").toLowerCase();
        this.name = name;
        this.priority = priority;
        this.defaultSticker = defaultSticker;
        this.colourDisabled = colourDisabled;
        this.colourSelected = colourSelected;

        this.namePrefix = namePrefix;
        this.namePrefixPriority = namePrefixPriority;
        this.namePostfix = namePostfix;
        this.namePostfixPriority = namePostfixPriority;

        this.description = description;
        this.descriptionFullReplacement = descriptionFullReplacement;
        this.descriptionPriority = descriptionPriority;

        this.svgPaths = svgPaths;

        this.tagsApplied = tagsApplied;
        this.tagsRemoved = tagsRemoved;

        this.showDisabledButton = showDisabledButton;
        this.unavailabilityText = unavailabilityText;
        this.availabilityText = availabilityText;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isDefaultSticker() {
        return defaultSticker;
    }

    public Colour getColourDisabled() {
        return colourDisabled;
    }

    public Colour getColourSelected() {
        return colourSelected;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public int getNamePrefixPriority() {
        return namePrefixPriority;
    }

    public String getNamePostfix() {
        return namePostfix;
    }

    public int getNamePostfixPriority() {
        return namePostfixPriority;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDescriptionFullReplacement() {
        return descriptionFullReplacement;
    }

    public int getDescriptionPriority() {
        return descriptionPriority;
    }

    /**
     * Mapping InventorySlot as the equipped slot to a Map of Integers (representing zLayers) mapped to svg Strings.
     */
    public Map<InventorySlot, Map<Integer, Path>> getSvgPaths() {
        return svgPaths;
    }

    public List<ItemTag> getTagsApplied() {
        return tagsApplied;
    }

    public List<ItemTag> getTagsRemoved() {
        return tagsRemoved;
    }

    public boolean isShowDisabledButton() {
        return showDisabledButton;
    }

    public String getUnavailabilityText() {
        if (Main.game.isAllStickersUnlocked()) {
            return "";
        }
        return unavailabilityText;
    }

    public String getAvailabilityText() {
        if (Main.game.isAllStickersUnlocked()) {
            return "";
        }
        return availabilityText;
    }
}
