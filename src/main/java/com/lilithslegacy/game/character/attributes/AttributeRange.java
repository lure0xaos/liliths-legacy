package com.lilithslegacy.game.character.attributes;

/**
 * @author Innoxia
 * @version 0.1.99
 * @since 0.1.99
 */
public class AttributeRange {
    private float minimum, maximum;

    public AttributeRange(float minimum, float maximum) {
        super();
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public float getMinimum() {
        return minimum;
    }

    public float getMaximum() {
        return maximum;
    }

    public float getMedian() {
        return (maximum + minimum) / 2;
    }

    public float getRandomVariance() {
        return (float) (Math.random() * (maximum - minimum));
    }
}
