package com.lilithslegacy.world.places;

/**
 * @author Innoxia
 * @version 0.4.0
 * @since 0.4
 */
public enum Aquatic {

    /**
     * This area is a land tile.
     */
    LAND(true, false),

    /**
     * This area is both a land tile and a surface water tile.
     */
    MIXED(true, true),

    /**
     * This area is a surface water tile.
     */
    WATER_SURFACE(false, true),

    /**
     * This area is an underwater tile.
     */
    WATER_UNDER(false, true);

    private final boolean land;
    private final boolean water;

    Aquatic(boolean land, boolean water) {
        this.land = land;
        this.water = water;
    }

    public boolean isLand() {
        return land;
    }

    public boolean isWater() {
        return water;
    }
}
