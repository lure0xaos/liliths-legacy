package com.lilithslegacy.game.dialogue.places.submission.dicePoker;

import com.lilithslegacy.utils.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Innoxia
 * @version 0.2.6
 * @since 0.2.6
 */
public class Dice {

    private DiceFace face;
    private final Map<DiceFace, Float> diceBias;

    public Dice() {
        face = DiceFace.SIX;
        diceBias = new HashMap<>();
        for (DiceFace face : DiceFace.values()) {
            diceBias.put(face, 1f);
        }
    }

    public Dice(Map<DiceFace, Float> diceBias) {
        this();
        this.diceBias.putAll(diceBias);
    }

    public void roll() {
        face = Util.getRandomObjectFromWeightedFloatMap(diceBias);
    }

    public DiceFace getFace() {
        return face;
    }

    public void setFace(DiceFace face) {
        this.face = face;
    }

    public Map<DiceFace, Float> getDiceBias() {
        return diceBias;
    }
}
