package com.lilithslegacy.utils.comparators;

import com.lilithslegacy.game.character.npc.NPC;

public class SlaveNameComparator extends BaseSlaveComparator {

    @Override
    public int compare(NPC a, NPC b) {
        return a.getName().compareTo(b.getName());
    }

}
