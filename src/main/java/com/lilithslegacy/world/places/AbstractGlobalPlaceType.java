package com.lilithslegacy.world.places;

import java.io.IOException;
import java.io.InputStream;

import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.encounters.AbstractEncounter;
import com.lilithslegacy.utils.SvgUtil;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.utils.fs.FS;
import com.lilithslegacy.world.AbstractWorldType;
import com.lilithslegacy.world.WorldRegion;

/**
 * @author Innoxia
 * @version 0.4
 * @since 0.3.1
 */
public abstract class AbstractGlobalPlaceType extends AbstractPlaceType {

    public AbstractGlobalPlaceType(WorldRegion worldRegion,
                                   String name,
                                   String SVGPath,
                                   String tooltipDescription,
                                   Colour colour,
                                   DialogueNode dialogue,
                                   AbstractEncounter encounterType,
                                   String virginityLossDescription) {
        this(worldRegion, name, tooltipDescription, SVGPath, colour, colour, dialogue, encounterType, virginityLossDescription);
    }

    public AbstractGlobalPlaceType(WorldRegion worldRegion,
                                   String name,
                                   String tooltipDescription,
                                   String SVGPath,
                                   Colour colour,
                                   Colour backgroundColour,
                                   DialogueNode dialogue,
                                   AbstractEncounter encounterType,
                                   String virginityLossDescription) {
        super(worldRegion, name, tooltipDescription, null, null, dialogue, Darkness.DAYLIGHT, encounterType, virginityLossDescription);

        this.name = name;

        this.colour = colour;

        if (backgroundColour == null) {
            this.backgroundColour = PresetColour.MAP_BACKGROUND;
        } else {
            this.backgroundColour = backgroundColour;
        }

        this.encounterType = encounterType;
        this.virginityLossDescription = virginityLossDescription;
        this.dialogue = dialogue;

        this.globalMapTile = true;

        if (SVGPath != null) {
            try {
                InputStream is = FS.newResourceAsStream("/com/lilithslegacy/res/map/" + SVGPath + ".svg");
                if (is == null) {
                    System.getLogger("").log(System.Logger.Level.ERROR, "Error! PlaceType icon file does not exist (Trying to read from '" + SVGPath + "')! (Code 1)");
                }
                String s = Util.inputStreamToString(is);

                try {
                    s = SvgUtil.colourReplacement("placeColour" + colourReplacementId, colour, s);
                    colourReplacementId++;
                } catch (Exception ex) {
                    System.getLogger("").log(System.Logger.Level.ERROR, SVGPath + " error!");
                }

                SVGString = s;

                is.close();

            } catch (IOException e1) {
                System.getLogger("").log(System.Logger.Level.ERROR, e1.getMessage(), e1);
            }
        } else {
            SVGString = null;
        }
    }

    @Override
    public AbstractGlobalPlaceType initDangerous() {
        this.dangerous = true;
        return this;
    }

    @Override
    public AbstractGlobalPlaceType initAquatic(Aquatic aquatic) {
        this.aquatic = aquatic;
        return this;
    }

    @Override
    public AbstractGlobalPlaceType initItemsPersistInTile() {
        this.itemsDisappear = false;
        return this;
    }

    public abstract AbstractWorldType getGlobalLinkedWorldType();
}
