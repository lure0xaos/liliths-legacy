package com.lilithslegacy.game.dialogue.places.dominion.cityHall;

import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.responses.Response;
import com.lilithslegacy.game.dialogue.utils.UtilText;

/**
 * @author Innoxia
 * @version 0.3.2
 * @since 0.3.2
 */
public class CityHallProperty {

    public static final DialogueNode CITY_HALL_PROPERTY_ENTRANCE = new DialogueNode("Bureau of Property and Commerce", "-", false) {

        @Override
        public String getContent() {
            return UtilText.parseFromXMLFile("places/dominion/cityHall/property", "CITY_HALL_PROPERTY_ENTRANCE");
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return null;
        }
    };
}
