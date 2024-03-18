package com.lilithslegacy.game.dialogue.responses;

import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.dialogue.DialogueFlagValue;
import com.lilithslegacy.game.dialogue.utils.InventoryInteraction;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.main.Main;

/**
 * @author Innoxia
 * @version 0.4.1
 * @since 0.1.7?
 */
public class ResponseTrade extends Response {

    private NPC tradePartner;

    // For use in responses generated from external files:

    private final String traderString;

    public ResponseTrade(String title, String tooltipText, NPC tradePartner) {
        super(title, tooltipText, null);

        this.tradePartner = tradePartner;
        this.traderString = null;
    }

    public ResponseTrade(String title, String tooltipText, String traderString, String effectsResponse) {
        super(title, tooltipText, null);
        this.fromExternalFile = true;

        this.traderString = traderString;

        this.effectsString = effectsResponse;
    }

    public void openTrade() {
        Main.game.getDialogueFlags().setFlag(DialogueFlagValue.removeTraderDescription, false); // Set this to false so that initial trader descriptions are always show
        if (traderString != null && !traderString.isEmpty()) {
            Main.mainController.openInventory((NPC) UtilText.findFirstCharacterFromParserTarget(UtilText.parse(traderString).trim()), InventoryInteraction.TRADING);
        } else {
            Main.mainController.openInventory(tradePartner, InventoryInteraction.TRADING);
        }
    }

    @Override
    public boolean disabledOnNullDialogue() {
        return false;
    }

    @Override
    public boolean isTradeHighlight() {
        return true;
    }
}
