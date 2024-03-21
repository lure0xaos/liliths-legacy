package com.lilithslegacy.utils;

/**
 * @author Innoxia
 * @version 0.1.69
 * @since 0.1.69
 */
public class ResponseAvailableData {

    public boolean available;
    public String tooltipText;
    public float corruptionIncrease;

    public ResponseAvailableData(boolean available, String tooltipText, float corruptionIncrease) {
        this.available = available;
        this.tooltipText = tooltipText;
        this.corruptionIncrease = corruptionIncrease;
    }

}
