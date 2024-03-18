package com.lilithslegacy.game.character.quests;

import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.1.69
 * @since 0.1.1
 */
public enum QuestType {
    MAIN(PresetColour.QUEST_MAIN),
    SIDE(PresetColour.QUEST_SIDE),
    RELATIONSHIP(PresetColour.QUEST_RELATIONSHIP);

    private final Colour colour;

    QuestType(Colour colour) {
        this.colour = colour;
    }

    public Colour getColour() {
        return colour;
    }
}
