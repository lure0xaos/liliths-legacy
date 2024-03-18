package com.lilithslegacy.utils.comparators;

import com.lilithslegacy.game.character.npc.NPC;

public class SlaveRoomComparator extends BaseSlaveComparator {

    @Override
    public int compare(NPC a, NPC b) {
        return a.getCell().getPlaceName().compareTo(b.getCell().getPlaceName());
    }

}
