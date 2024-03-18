package com.lilithslegacy.game.character.persona;

/**
 * @author Irbynx
 * @version 0.2.4
 * @since 0.2.4
 */
public enum MoralityValue {

    SURRENDER("Surrendering", "A stance on avoiding fighting to bitter end."),

    VIOLENCE("Violence", "A stance on excessive violence."),

    DISHONESTY("Dishonesty", "A stance on lies, half-truths and various obscurities of information."),

    SLAVERY("Slavery", "A stance on ownership of slaves.");

    private final String name;
    private final String description;

    MoralityValue(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}
