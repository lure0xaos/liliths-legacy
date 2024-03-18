package com.lilithslegacy.game.combat.spells;

/**
 * @author Innoxia
 * @version 0.3.5.5
 * @since 0.2.1
 */
public enum SpellType {

    // Offensive:

    /**
     * A spell that has the primary purpose of dealing damage.
     **/
    OFFENSIVE(false),

    /**
     * A spell that has the primary purpose of applying a negative status effect, but which also deals some damage.
     **/
    OFFENSIVE_STATUS_EFFECT_MINOR_DAMAGE(true),

    /**
     * A spell that has the sole purpose of applying a negative status effect.
     **/
    OFFENSIVE_STATUS_EFFECT(true),

    // Defensive:

    /**
     * A spell that has the sole purpose of applying a defensive status effect.
     **/
    DEFENSIVE_STATUS_EFFECT(true),

    /**
     * A spell that has the primary purpose of healing an ally.
     **/
    DEFENSIVE_HEAL(false),

    /**
     * A spell that has the primary purpose of clearing status effects.
     **/
    DEFENSIVE_STATUS_EFFECT_CLEAR(false),

    // Misc.:

    /**
     * A spell that has the sole purpose of summoning a companion.
     **/
    SUMMON(false),

    /**
     * A miscellaneous spell which should have special conditions for being used.
     **/
    MISC(false);

    final boolean statusEffectFocus;

    SpellType(boolean statusEffectFocus) {
        this.statusEffectFocus = statusEffectFocus;
    }

    public boolean isStatusEffectFocus() {
        return statusEffectFocus;
    }

}
