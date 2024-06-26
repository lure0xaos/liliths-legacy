package com.lilithslegacy.game.dialogue.places;

import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.responses.Response;

/**
 * @author Innoxia
 * @version 0.1.69
 * @since 0.1.0
 */
public class JunglePlaces {

    public static final DialogueNode PATH = new DialogueNode("Jungle path", "", false) {
        /**
         *
         */

        @Override
        public String getContent() {
            return "TODO Walking down a narrow path...";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return null;
        }
    };

    public static final DialogueNode CLUB = new DialogueNode("Club", "A place.", false) {
        /**
         *
         */

        @Override
        public String getContent() {
            return "TODO";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return null;
        }
    };

    public static final DialogueNode BROTHEL = new DialogueNode("Brothel", "A place.", false) {
        /**
         *
         */

        @Override
        public String getContent() {
            return "TODO";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return null;
        }
    };

    public static final DialogueNode TENTACLE_QUEENS_LAIR = new DialogueNode("Tentacle Queen's Lair", "A place.", false) {
        /**
         *
         */

        @Override
        public String getContent() {
            return "TODO";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return null;
        }
    };

    public static final DialogueNode DENSE_JUNGLE = new DialogueNode("Dense Jungle", "A place.", false) {
        /**
         *
         */

        @Override
        public String getContent() {
            return "TODO";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return null;
        }
    };

    // Entrances and exits:

    public static final DialogueNode JUNGLE_ENTRANCE = new DialogueNode("", "Travel to Dominion.", false) {
        /**
         *
         */

        @Override
        public String getContent() {
            return "A half-overgrown sign near the road tells you that this path leads back to Dominion.";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 1) {
                return new Response("Dominion", "Travel to Dominion. (This will be added later!)", null);

            } else {
                return null;
            }
        }
    };
}
