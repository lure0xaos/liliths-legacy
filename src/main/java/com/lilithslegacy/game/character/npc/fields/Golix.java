package com.lilithslegacy.game.character.npc.fields;

import com.lilithslegacy.game.character.npc.misc.Elemental;

/**
 * @author Innoxia
 * @version 0.4.9
 * @since 0.4.9
 */
public class Golix extends Elemental {

    public Golix() {
        this(false);
    }

    public Golix(boolean isImported) {
        super(isImported);
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public String getArtworkFolderName() {
        return "Golix";
    }
}
