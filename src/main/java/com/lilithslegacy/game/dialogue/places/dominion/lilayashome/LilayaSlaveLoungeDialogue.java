package com.lilithslegacy.game.dialogue.places.dominion.lilayashome;

import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.companions.CompanionManagement;
import com.lilithslegacy.game.dialogue.companions.OccupantManagementDialogue;
import com.lilithslegacy.game.dialogue.responses.Response;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.world.places.AbstractPlaceUpgrade;
import com.lilithslegacy.world.places.PlaceUpgrade;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Innoxia
 * @version 0.4.7.1
 * @since 0.4.7.1
 */
public class LilayaSlaveLoungeDialogue {

    public static final DialogueNode ROOM_SLAVE_LOUNGE = new DialogueNode("", "", false) {
        @Override
        public int getSecondsPassed() {
            return 10;
        }

        @Override
        public String getLabel() {
            return Main.game.getPlayer().getLocationPlace().getName();
        }

        @Override
        public String getContent() {
            StringBuilder sb = new StringBuilder();

            sb.append(LilayaHomeGeneric.getBaseRoomDescription());

            sb.append("<p>" + "<b style='color:").append(PlaceUpgrade.LILAYA_SLAVE_LOUNGE.getColour().toWebHexString()).append(";'>").append(PlaceUpgrade.LILAYA_SLAVE_LOUNGE.getName()).append("</b><br/>").append(PlaceUpgrade.LILAYA_SLAVE_LOUNGE.getRoomDescription(Main.game.getPlayerCell())).append("</p>");

            for (AbstractPlaceUpgrade up : Main.game.getPlayerCell().getPlace().getPlaceUpgrades()) {
                if (!up.isCoreRoomUpgrade()) {
                    sb.append("<p>" + "<b style='color:").append(up.getColour().toWebHexString()).append(";'>").append(up.getName()).append("</b><br/>").append(up.getRoomDescription(Main.game.getPlayerCell())).append("</p>");
                }
            }

            return sb.toString();
        }

        @Override
        public String getResponseTabTitle(int index) {
            return LilayaHomeGeneric.getLilayasHouseStandardResponseTabs(index);
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (responseTab == 1) {
                return LilayaHomeGeneric.getLilayasHouseFastTravelResponses(index);
            }

            List<NPC> charactersPresent = LilayaHomeGeneric.getSlavesAndOccupantsPresent();
            List<NPC> slavesAssignedToRoom = new ArrayList<>(charactersPresent);

            if (index == 0) {
                return null;

            } else if (index == 1) {
                if (Main.game.getPlayer().isAbleToAccessRoomManagement()) {
                    return new Response("Manage room", "Enter the management screen for this particular room.", OccupantManagementDialogue.ROOM_UPGRADES) {
                        @Override
                        public void effects() {
                            OccupantManagementDialogue.cellToInspect = Main.game.getPlayerCell();
                        }
                    };
                } else {
                    return new Response("Manage room", "You'll either need a slaver license, or permission from Lilaya to house your friends, before you can access this menu!", null);
                }

            } else if (index == 2) {
                if (Main.game.getPlayer().isAbleToAccessRoomManagement()) {
                    return new Response("Manage people", "Enter the management screen for your slaves and friendly occupants.", OccupantManagementDialogue.getSlaveryRoomListDialogue(null, null)) {
                        @Override
                        public void effects() {
                            CompanionManagement.initManagement(Main.game.getDefaultDialogue(), 0, null);
                        }
                    };
                } else {
                    return new Response("Manage people", "You'll either need a slaver license, or permission from Lilaya to house your friends, before you can access this menu!", null);
                }

            }

            int indexPresentStart = 3;
            if (index - indexPresentStart < slavesAssignedToRoom.size()) {
                NPC character = slavesAssignedToRoom.get(index - indexPresentStart);
                if (charactersPresent.contains(character) || (character.getHomeCell().equals(Main.game.getPlayerCell()) && Main.game.getPlayer().getCompanions().contains(character))) {
                    return LilayaHomeGeneric.interactWithNPC(character);

                } else {
                    return new Response(UtilText.parse(character, "[npc.Name]"), UtilText.parse(character, "Although this is [npc.namePos] room, [npc.sheIs] out at work at the moment."), null);
                }
            }

            return null;
        }
    };
}
