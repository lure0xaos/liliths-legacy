package com.lilithslegacy.game.character.effects;

/**
 * @author Innoxia
 * @version 0.4
 * @since 0.4
 */
public enum StatusEffectCategory {

    /**
     * The standard category which all StatusEffects are set to by default. It has no special effects.
     */
    DEFAULT,

    /**
     * This defines the Status Effect as only needing an update check after the character's attributes have been modified.
     */
    ATTRIBUTE,

    /**
     * This defines the Status Effect as only needing an update check after the character's inventory has been modified.
     */
    INVENTORY
}
