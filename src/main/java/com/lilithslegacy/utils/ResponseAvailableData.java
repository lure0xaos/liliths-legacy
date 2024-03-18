package com.lilithslegacy.utils;

/**
 * @author Innoxia
 * @version 0.1.69
 * @since 0.1.69
 */
public class ResponseAvailableData {

    public final boolean available;
    public final String tooltipText;
    public final float corruptionIncrease;

    public ResponseAvailableData(boolean available, String tooltipText, float corruptionIncrease) {
        this.available = available;
        this.tooltipText = tooltipText;
        this.corruptionIncrease = corruptionIncrease;
    }

}
