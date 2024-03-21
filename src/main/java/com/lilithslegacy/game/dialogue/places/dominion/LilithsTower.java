package com.lilithslegacy.game.dialogue.places.dominion;

import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.responses.Response;
import com.lilithslegacy.game.dialogue.utils.UtilText;

/**
 * @author Innoxia
 * @version 0.4
 * @since 0.1.0
 */
public class LilithsTower {

    public static final DialogueNode OUTSIDE = new DialogueNode("Lilith's Tower", "", false) {
        @Override
        public int getSecondsPassed() {
            return DominionPlaces.TRAVEL_TIME_STREET;
        }

        @Override
        public String getContent() {
            return UtilText.parseFromXMLFile("places/dominion/lilithsTower/exterior", "OUTSIDE");
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 1) {
                return new Response("Approach", "Approach the archway and see if you can enter the tower's grounds.", LILITHS_DISTRICT_APPROACH);
            }
            return null;
        }
    };

    public static final DialogueNode LILITHS_DISTRICT_APPROACH = new DialogueNode("Lilith's Tower", "", false, true) {
        @Override
        public int getSecondsPassed() {
            return 2 * 60;
        }

        @Override
        public String getContent() {
            return UtilText.parseFromXMLFile("places/dominion/lilithsTower/exterior", "LILITHS_DISTRICT_APPROACH");
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return null;
        }
    };
}
