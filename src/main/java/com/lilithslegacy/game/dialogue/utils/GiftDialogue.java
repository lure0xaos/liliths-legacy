package com.lilithslegacy.game.dialogue.utils;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.DialogueNodeType;
import com.lilithslegacy.game.dialogue.responses.Response;
import com.lilithslegacy.game.dialogue.responses.ResponseEffectsOnly;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.rendering.RenderingEngine;

/**
 * @author Innoxia
 * @version 0.3.7.3
 * @since 0.1.99
 */
public class GiftDialogue {

    private static GameCharacter receiver;
    public static final DialogueNode GIFT_DIALOGUE = new DialogueNode("Choose Gift", "-", true) {
        @Override
        public String getContent() {
            return UtilText.parse(receiver,
                    "<p>"
                            + "The following items are suitable to be given as a gift to [npc.name]. Select the one that you'd like to give to [npc.herHim]."
                            + "</p>")
                    + RenderingEngine.ENGINE.getGiftDiv(receiver);
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 0) {
                return new ResponseEffectsOnly("Back", "Return to the previous screen") {
                    @Override
                    public void effects() {
                        Main.game.restoreSavedContent(false);
                    }
                };
            }
            return null;
        }

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.GIFT;
        }
    };
    private static DialogueNode dialogueToProceedTo;
    private static int proceedDialogueTab;

    /**
     * @param receiver            The NPC to receive the gift.
     * @param dialogueToProceedTo The DialogueNode that should be returned if the player gives a gift to the receiver.
     * @param proceedDialogueTab  The tab which should be selected when proceeding to dialogueToProceedTo.
     * @return
     */
    public static DialogueNode getGiftDialogue(GameCharacter receiver, DialogueNode dialogueToProceedTo, int proceedDialogueTab) {
        GiftDialogue.receiver = receiver;
        GiftDialogue.dialogueToProceedTo = dialogueToProceedTo;
        GiftDialogue.proceedDialogueTab = proceedDialogueTab;

        Main.game.saveDialogueNode();

        return GIFT_DIALOGUE;
    }

    public static GameCharacter getReceiver() {
        return receiver;
    }

    public static DialogueNode getDialogueToProceedTo() {
        return dialogueToProceedTo;
    }

    public static int getProceedDialogueTab() {
        return proceedDialogueTab;
    }
}
