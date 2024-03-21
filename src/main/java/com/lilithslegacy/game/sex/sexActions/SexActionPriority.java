package com.lilithslegacy.game.sex.sexActions;

/**
 * @author Innoxia
 * @version 0.3.5.1
 * @since 0.1.69
 */
public enum SexActionPriority {
    LOW(0),

    NORMAL(1),

    HIGH(2),

    UNIQUE_MAX(3);

    private int value;

    private SexActionPriority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
