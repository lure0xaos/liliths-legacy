package com.lilithslegacy.game.dialogue.utils;

import java.util.List;

import com.lilithslegacy.game.character.GameCharacter;

/**
 * @author Innoxia
 * @version 0.4.1
 * @since 0.4.1
 */
public abstract class AbstractParserTarget {

    private String description;
    private List<String> tags;

    public AbstractParserTarget(List<String> tags, String description) {
        this.tags = tags;
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getDescription() {
        return description;
    }

    public abstract GameCharacter getCharacter(String tag, List<GameCharacter> specialNPCList) throws NullPointerException;
}
