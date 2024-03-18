package com.lilithslegacy.game.character.persona;

/**
 * @author Innoxia
 * @version 0.3
 * @since 0.1.75
 */
public class NameTriplet {

    private final String masculine;
    private final String androgynous;
    private final String feminine;

    public NameTriplet(String masculine, String androgynous, String feminine) {
        this.masculine = masculine;
        this.androgynous = androgynous;
        this.feminine = feminine;
    }

    public NameTriplet(String name) {
        this.masculine = name;
        this.androgynous = name;
        this.feminine = name;
    }

    public String toString() {
        return masculine + "-" + androgynous + "-" + feminine;
    }

    public String getMasculine() {
        return masculine;
    }

    public String getAndrogynous() {
        return androgynous;
    }

    public String getFeminine() {
        return feminine;
    }
}
