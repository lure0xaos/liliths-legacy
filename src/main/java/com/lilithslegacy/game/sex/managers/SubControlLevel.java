package com.lilithslegacy.game.sex.managers;

/**
 * @author Innoxia
 * @version 0.3
 * @since 0.3
 */
public enum SubControlLevel {

    /**
     * The submissive partner can perform all actions.
     */
    EQUAL,

    /**
     * The submissive partner cannot perform penetrative actions, unless they are the player, in which case they can perform non-virginity-taking penetrative actions.
     */
    LIMITED,

    /**
     * The submissive partner cannot perform any penetrative actions.
     */
    RESTRICTED;
}
