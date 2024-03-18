package com.lilithslegacy.game.character.markings;

import com.lilithslegacy.utils.Util;

/**
 * @author Innoxia
 * @version 0.2.6
 * @since 0.2.6
 */
public enum TattooCountType {

    NUMBERS("numbers") {
        @Override
        public String convertInt(int input) {
            return String.valueOf(input);
        }
    },
    TALLY("tally markings") {
        @Override
        public String convertInt(int input) {
            return Util.intToTally(input, 50);
        }
    },
    NUMERALS("Roman numerals") {
        @Override
        public String convertInt(int input) {
            return Util.intToNumerals(input);
        }
    },
    WRITTEN("written out") {
        @Override
        public String convertInt(int input) {
            return Util.intToString(input);
        }
    };

    private final String name;

    TattooCountType(String name) {
        this.name = name;
    }

    public abstract String convertInt(int input);

    public String getName() {
        return name;
    }
}
