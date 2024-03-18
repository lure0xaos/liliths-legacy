package com.lilithslegacy.game.character.gender;

/**
 * @author Innoxia
 * @version 0.1.86
 * @since 0.1.67
 */
public enum GenderPronoun {

    NOUN("Noun", "woman", "man", "person"),
    YOUNG_NOUN("Young noun", "girl", "boy", "person"),

    SECOND_PERSON("Second person", "she", "he", "they"),
    THIRD_PERSON("Third person", "her", "him", "them"),
    POSSESSIVE_BEFORE_NOUN("Possessive before noun", "her", "his", "their"),
    POSSESSIVE_ALONE("Possessive alone", "hers", "his", "theirs");

    private final String name;
    private final String feminine;
    private final String masculine;
    private String neutral;

    GenderPronoun(String name, String feminine, String masculine, String neutral) {
        this.name = name;
        this.feminine = feminine;
        this.masculine = masculine;
    }

    public String getName() {
        return name;
    }

    public String getFeminine() {
        return feminine;
    }

    public String getMasculine() {
        return masculine;
    }

    public String getNeutral() {
        return neutral;
    }
}
