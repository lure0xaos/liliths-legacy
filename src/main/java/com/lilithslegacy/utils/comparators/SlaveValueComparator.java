package com.lilithslegacy.utils.comparators;

import com.lilithslegacy.game.character.npc.NPC;

public class SlaveValueComparator extends BaseSlaveComparator {

    @Override
    public int compare(NPC a, NPC b) {
        return a.getValueAsSlave(false) - b.getValueAsSlave(false);
    }

}
