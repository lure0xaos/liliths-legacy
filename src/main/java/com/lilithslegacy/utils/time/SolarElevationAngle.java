package com.lilithslegacy.utils.time;

/**
 * The altitude of the sun (solar elevation angle) at different moments
 *
 * @author Innoxia
 * @version 0.3.5.5
 * @since 0.3.5.5
 */
public enum SolarElevationAngle {

    SUN_ALTITUDE_SUNRISE_SUNSET(-0.833),

    SUN_ALTITUDE_CIVIL_TWILIGHT(-6.0),

    SUN_ALTITUDE_NAUTICAL_TWILIGHT(-12.0),

    SUN_ALTITUDE_ASTRONOMICAL_TWILIGHT(-18.0);

    private double angle;

    private SolarElevationAngle(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }
}
