package com.lilithslegacy.game.inventory.clothing;

/**
 * @author Innoxia
 * @version 0.1.96
 * @since 0.1.0
 */
public enum DisplacementType {

    REMOVE_OR_EQUIP("unequip", "", "", "", ""),

    OPEN("open", "opens", "opened", "close", "closes"),

    PULLS_UP("pull up", "pulls up", "pulled up", "pull down", "pulls down"),

    PULLS_DOWN("pull down", "pulls down", "pulled down", "pull up", "pulls up"),

    PULLS_OUT("pull out", "pulls out", "pulled out", "push in", "pushes in"),

    PULLS_OFF("pull off", "pulls off", "pulled off", "pull back on", "pulls back on"),

    SHIFTS_ASIDE("shift aside", "shifts aside", "shifted aside", "replace", "replaces"),

    UNZIPS("unzip", "unzips", "unzipped", "zip up", "zips up"),

    UNTIE("untie", "unties", "untied", "tie-up", "ties-up"),

    UNBUCKLE("unbuckle", "unbuckles", "unbuckled", "buckle up", "buckles up"),

    UNBUTTONS("unbutton", "unbuttons", "unbuttoned", "button up", "buttons up");

    private final String description;
    private final String descriptionThirdPerson;
    private final String descriptionPast;
    private final String oppositeDescription;
    private final String oppositeDescriptionThirdPerson;

    DisplacementType(String description, String descriptionThirdPerson, String descriptionPast, String oppositeDescription, String oppositeDescriptionThirdPerson) {
        this.description = description;
        this.descriptionThirdPerson = descriptionThirdPerson;
        this.descriptionPast = descriptionPast;
        this.oppositeDescription = oppositeDescription;
        this.oppositeDescriptionThirdPerson = oppositeDescriptionThirdPerson;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionThirdPerson() {
        return descriptionThirdPerson;
    }

    public String getDescriptionPast() {
        return descriptionPast;
    }

    public String getOppositeDescription() {
        return oppositeDescription;
    }

    public String getOppositeDescriptionThirdPerson() {
        return oppositeDescriptionThirdPerson;
    }
}
