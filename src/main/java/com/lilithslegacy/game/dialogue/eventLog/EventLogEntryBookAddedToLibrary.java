package com.lilithslegacy.game.dialogue.eventLog;

import com.lilithslegacy.game.inventory.item.AbstractItemType;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.1.85
 * @since 0.1.85
 */
public class EventLogEntryBookAddedToLibrary extends EventLogEntry {


    public EventLogEntryBookAddedToLibrary(AbstractItemType book) {
        super("Added to Library", "<span style='color:" + book.getRarity().getColour().toWebHexString() + ";'>" + Util.capitaliseSentence(book.getName(false)) + "</span>");
    }

    @Override
    public String getFormattedEntry() {
        return "<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>" + name + "</span>: " + description;
    }

    @Override
    public String getMainDialogueDescription() {
        return "<p style='text-align:center;'>"
                + "<b style='color:" + PresetColour.GENERIC_EXCELLENT.toWebHexString() + ";'>Book added to Lilaya's library</b>"
                + "<br/>"
                + description
                + "</p>";
    }
}
