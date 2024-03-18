package com.lilithslegacy.game.dialogue.responses;

import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.dialogue.utils.InventoryInteraction;
import com.lilithslegacy.main.Main;

/**
 * @author Innoxia
 * @version 0.1.85
 * @since 0.1.85
 */
public class ResponseManageNPC extends Response {

    private final NPC npcToManage;

    public ResponseManageNPC(String title, String tooltipText, NPC npcToManage) {
        super(title, tooltipText, null);

        this.npcToManage = npcToManage;
    }

    public void openNPCManagement() {
        Main.mainController.openInventory(npcToManage, InventoryInteraction.FULL_MANAGEMENT);
    }


    @Override
    public boolean disabledOnNullDialogue() {
        return false;
    }
}
