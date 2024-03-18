package com.lilithslegacy.game.dialogue.eventLog;

import com.lilithslegacy.game.inventory.AbstractCoreItem;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.inventory.item.AbstractItem;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.1.85
 * @since 0.1.85
 */
public class EventLogEntryGainItem extends EventLogEntry {


    public EventLogEntryGainItem(AbstractCoreItem item) {
        super((item instanceof AbstractItem ? "Gained Item" : (item instanceof AbstractClothing ? "Gained Clothing" : "Gained Weapon")),
                "<span style='color:" + item.getRarity().getColour().toWebHexString() + ";'>" + Util.capitaliseSentence(item.getName()) + "</span>");
    }

    @Override
    public String getFormattedEntry() {
        return "<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>" + name + "</span>: " + description;
    }
}
