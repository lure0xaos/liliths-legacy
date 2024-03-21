package com.lilithslegacy.game.combat.moves;

/**
 * Category of CombatType for use in organising CombatMoves in menus.
 *
 * @author Innoxia
 * @version 0.4
 * @since 0.4
 */
public enum CombatMoveCategory {

    /**
     * Moves available to everyone by default.
     */
    BASIC,

    /**
     * Spells.
     */
    SPELL,

    /**
     * Any move derived from body parts, weapons, fetishes, etc.
     */
    SPECIAL;
}
