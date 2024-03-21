package com.lilithslegacy.game.inventory.outfit;

import java.util.ArrayList;
import java.util.List;

import com.lilithslegacy.game.inventory.clothing.AbstractClothingType;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.colours.Colour;

/**
 * @author Innoxia
 * @version 0.3.7.7
 * @since 0.3.1
 */
public class OutfitPotential {

    private List<AbstractClothingType> types;

    private List<List<Colour>> colours;

    public OutfitPotential(List<AbstractClothingType> types, List<List<Colour>> colours) {
        if (types != null) {
            this.types = types;
        } else {
            types = new ArrayList<>();
        }
        this.colours = colours;
    }


    public List<AbstractClothingType> getTypes() {
        return types;
    }

    public List<Colour> getColoursForClothingGeneration() {
        List<Colour> coloursForGeneration = new ArrayList<>();
        for (List<Colour> c : getColourLists()) {
            coloursForGeneration.add(Util.randomItemFrom(c));
        }
        return coloursForGeneration;
    }

    public List<List<Colour>> getColourLists() {
        return colours;
    }

    public List<Colour> getColours(int index) {
        try {
            return getColourLists().get(index);
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }
}
