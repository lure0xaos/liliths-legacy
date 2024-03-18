package com.lilithslegacy.game.dialogue.companions;

/**
 * @author Anonymous-BCFED
 * @version 0.4.4.1
 * @since 0.4.4.1
 */
public enum OccupantSortingMethod {
    NONE("none", "Reset the sorting order, resulting in values being ordered by their default values."),

    NAME("name", "Sort slaves in alphabetical order of their first name."),

    ROOM("room", "Sort slaves in alphabetical order of the room in which they're located."),

    VALUE("value", "Sort slaves in order of their monetary value.");


    private final String name;
    private final String sortingDescription;

    OccupantSortingMethod(String name, String sortingDescription) {
        this.name = name;
        this.sortingDescription = sortingDescription;
    }

    public String getName() {
        return name;
    }

    public String getSortingDescription() {
        return sortingDescription;
    }
}
