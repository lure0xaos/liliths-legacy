package com.lilithslegacy.game.dialogue.places.dominion.shoppingArcade;

import com.lilithslegacy.game.dialogue.DialogueManager;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.responses.Response;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.PlaceType;

/**
 * @author Innoxia
 * @version 0.4.7.8
 * @since 0.1.66
 */
public class PixsPlayground {

    public static final DialogueNode GYM_EXTERIOR = new DialogueNode("Pix's Playground (Exterior)", "", false) {
        @Override
        public String getContent() {
            return UtilText.parseFromXMLFile("places/dominion/shoppingArcade/pixsPlayground", "GYM_EXTERIOR");
        }

        @Override
        public String getResponseTabTitle(int index) {
            return ShoppingArcadeDialogue.getCoreResponseTab(index);
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (responseTab == 0) {
                if (index == 1) {
                    return new Response("Enter",
                            "Enter the gym.",
                            DialogueManager.getDialogueFromId("innoxia_places_dominion_shopping_arcade_gym_exit_initial_entry")) {
                        @Override
                        public void effects() {
                            Main.game.getPlayer().setLocation(WorldType.getWorldTypeFromId("innoxia_dominion_shopping_arcade_gym"), PlaceType.getPlaceTypeFromId("innoxia_dominion_shopping_arcade_gym_exit"));
                        }
                    };
                }
            }
            return ShoppingArcadeDialogue.getFastTravelResponses(responseTab, index);
        }
    };

}
