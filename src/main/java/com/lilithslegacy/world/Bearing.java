package com.lilithslegacy.world;

/**
 * @author Innoxia
 * @version 0.4
 * @since 0.1.0
 */
public enum Bearing {
    NORTH("north"),
    NORTH_EAST("north-east"),
    EAST("east"),
    SOUTH_EAST("south-east"),
    SOUTH("south"),
    SOUTH_WEST("south-west"),
    WEST("west"),
    NORTH_WEST("north-west"),
    RANDOM("random");

    private final String name;

    Bearing(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
