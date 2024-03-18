package com.lilithslegacy.utils;

import com.lilithslegacy.game.character.race.AbstractSubspecies;

/**
 * @author Innoxia
 * @version 0.3.3
 * @since 0.1.82
 */
public class CreditsSlot {

    private final String name;
    private final String tagLine;

    private final int uncommonCount;
    private final int rareCount;
    private final int epicCount;
    private final int legendaryCount;

    private AbstractSubspecies subspeciesTier;

    public CreditsSlot(String name, String tagLine, int uncommonCount, int rareCount, int epicCount, int legendaryCount) {
        this.name = name;
        this.tagLine = tagLine;
        this.uncommonCount = uncommonCount;
        this.rareCount = rareCount;
        this.epicCount = epicCount;
        this.legendaryCount = legendaryCount;
    }

    public CreditsSlot(String name, String tagLine, int uncommonCount, int rareCount, int epicCount, int legendaryCount, AbstractSubspecies subspeciesTier) {
        this(name, tagLine, uncommonCount, rareCount, epicCount, legendaryCount);
        this.subspeciesTier = subspeciesTier;
    }

    public String getName() {
        return name;
    }

    public String getTagLine() {
        return tagLine;
    }

    public int getUncommonCount() {
        return uncommonCount;
    }

    public int getRareCount() {
        return rareCount;
    }

    public int getEpicCount() {
        return epicCount;
    }

    public int getLegendaryCount() {
        return legendaryCount;
    }

    public AbstractSubspecies getSubspeciesTier() {
        return subspeciesTier;
    }

}
