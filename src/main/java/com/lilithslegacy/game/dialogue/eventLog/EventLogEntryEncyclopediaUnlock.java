package com.lilithslegacy.game.dialogue.eventLog;

import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.colours.BaseColour;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.1.85
 * @since 0.1.85
 */
public class EventLogEntryEncyclopediaUnlock extends EventLogEntry {


    public EventLogEntryEncyclopediaUnlock(String description, Colour highlightDescriptionColour) {
        super("Encyclopedia", "<span style='color:" + highlightDescriptionColour.toWebHexString() + ";'>" + Util.capitaliseSentence(description) + "</span>");
    }

    public EventLogEntryEncyclopediaUnlock(String description, BaseColour highlightDescriptionColour) {
        super("Encyclopedia", "<span style='color:" + highlightDescriptionColour.toWebHexString() + ";'>" + Util.capitaliseSentence(description) + "</span>");
    }

    @Override
    public String getFormattedEntry() {
        return "<span style='color:" + PresetColour.GENERIC_EXCELLENT.toWebHexString() + ";'>Encyclopedia</span>: " + description;
    }

    @Override
    public String getMainDialogueDescription() {
        return "<p style='text-align:center;'>"
                + "<b style='color:" + PresetColour.GENERIC_EXCELLENT.toWebHexString() + ";'>New entry in your phone's encyclopedia</b>"
                + "<br/>"
                + description
                + "</p>";
    }

}
