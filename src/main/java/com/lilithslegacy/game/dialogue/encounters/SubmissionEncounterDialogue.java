package com.lilithslegacy.game.dialogue.encounters;

import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.responses.Response;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.item.AbstractItem;
import com.lilithslegacy.main.Main;

/**
 * @author Innoxia
 * @version 0.3.7.3
 * @since 0.2.1
 */
public class SubmissionEncounterDialogue {

    public static final DialogueNode FIND_ITEM = new DialogueNode("Rubbish Pile", "", true) {
        @Override
        public int getSecondsPassed() {
            return 2 * 60;
        }

        @Override
        public String getContent() {
            return UtilText.parseFromXMLFile("places/submission/submissionPlaces", "FIND_ITEM")
                    + "<p style='text-align:center;'>"
                    + "<b>"
                    + AbstractEncounter.getRandomItem().getDisplayName(true)
                    + "</b>"
                    + "</p>";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 1) {
                return new Response("Take", "Add the " + AbstractEncounter.getRandomItem().getName() + " to your inventory.", Main.game.getDefaultDialogue(false)) {
                    @Override
                    public void effects() {
                        Main.game.getTextStartStringBuilder().append(Main.game.getPlayer().addItem((AbstractItem) AbstractEncounter.getRandomItem(), true, true));
                    }
                };

            } else if (index == 2) {
                return new Response("Leave", "Leave the " + AbstractEncounter.getRandomItem().getName() + " on the floor.", Main.game.getDefaultDialogue(false));

            } else {
                return null;
            }
        }
    };
}
