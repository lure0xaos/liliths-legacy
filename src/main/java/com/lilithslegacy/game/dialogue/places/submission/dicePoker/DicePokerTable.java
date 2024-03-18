package com.lilithslegacy.game.dialogue.places.submission.dicePoker;

import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.2.6
 * @since 0.2.6
 */
public enum DicePokerTable {

    COPPER("copper", 500, 250, PresetColour.CLOTHING_COPPER),
    SILVER("silver", 2500, 1000, PresetColour.CLOTHING_SILVER),
    GOLD("gold", 10000, 5000, PresetColour.CLOTHING_GOLD);

    private final String name;
    private final Colour colour;
    private final int initialBet;
    private final int raiseAmount;

    DicePokerTable(String name, int initialBet, int raiseAmount, Colour colour) {
        this.name = name;
        this.colour = colour;
        this.initialBet = initialBet;
        this.raiseAmount = raiseAmount;
    }

    public String getName() {
        return name;
    }

    public int getInitialBet() {
        return initialBet;
    }

    public int getRaiseAmount() {
        return raiseAmount;
    }

    public Colour getColour() {
        return colour;
    }
}
