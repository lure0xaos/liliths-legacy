package com.lilithslegacy.game.dialogue.places.fields;

import com.lilithslegacy.game.character.npc.submission.DarkSiren;
import com.lilithslegacy.game.dialogue.DialogueFlagValue;
import com.lilithslegacy.game.dialogue.DialogueManager;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.responses.Response;
import com.lilithslegacy.game.dialogue.responses.ResponseEffectsOnly;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Vector2i;
import com.lilithslegacy.world.Bearing;
import com.lilithslegacy.world.Cell;
import com.lilithslegacy.world.Season;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.PlaceType;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Innoxia
 * @version 0.4
 * @since 0.3.1
 */
public class FieldsDialogue {

    public static final DialogueNode DOMINION_EXTERIOR_FIRST_TIME_LEAVING_MERAXIS_DECLINED = new DialogueNode("", "", true, true) {
        @Override
        public void applyPreParsingEffects() {
            Main.game.getNpc(DarkSiren.class).setLocation(WorldType.getWorldTypeFromId("innoxia_fields_elis_town"), PlaceType.getPlaceTypeFromId("innoxia_fields_elis_town_town_hall")); //TODO move to tavern
        }

        @Override
        public int getSecondsPassed() {
            return 5 * 60;
        }

        @Override
        public String getContent() {
            return UtilText.parseFromXMLFile("places/global/globalPlaces", "DOMINION_EXTERIOR_FIRST_TIME_LEAVING_MERAXIS_DECLINED");
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 1) {
                return new Response("Continue", "With Meraxis having given her warning and departed, you're free to continue on your way out into the Fields.", Main.game.getDefaultDialogue(false));
            }
            return null;
        }
    };
    public static final DialogueNode DOMINION_EXTERIOR_FIRST_TIME_LEAVING_MERAXIS = new DialogueNode("", "", true, true) {
        @Override
        public void applyPreParsingEffects() {
            Main.game.getDialogueFlags().setFlag(DialogueFlagValue.leftDominionFirstTime, true);
            Main.game.getNpc(DarkSiren.class).setLocation(Main.game.getPlayer(), false);
        }

        @Override
        public int getSecondsPassed() {
            return 5 * 60;
        }

        @Override
        public String getContent() {
            return UtilText.parseFromXMLFile("places/global/globalPlaces", "DOMINION_EXTERIOR_FIRST_TIME_LEAVING_MERAXIS");
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 1) {
                return new Response("Decline", "Tell Meraxis that you aren't interested in duelling her.", DOMINION_EXTERIOR_FIRST_TIME_LEAVING_MERAXIS_DECLINED);

            } else if (index == 2) {
                //TODO chuuni variation
                return new Response("Accept", "Agree to duel Meraxis.", null);

            } else if (index == 3) {//TODO need more ways to have sex with Meraxis
                return new Response("Sex duel", "Offer Meraxis an alternative way to duel; the two of you have sex, and whoever orgasms first loses!", null);
            }
            return null;
        }
    };
    public static final DialogueNode DOMINION_EXTERIOR_FIRST_TIME_LEAVING = new DialogueNode("", "", true) {
        @Override
        public int getSecondsPassed() {
            return 30 * 60;
        }

        @Override
        public String getContent() {
            return UtilText.parseFromXMLFile("places/global/globalPlaces", "DOMINION_EXTERIOR_FIRST_TIME_LEAVING");
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 1) {
                return new Response("Prepare", "Prepare yourself for whatever is about to appear!", DOMINION_EXTERIOR_FIRST_TIME_LEAVING_MERAXIS);
            }
            return null;
        }
    };
    public static final DialogueNode DOMINION_EXTERIOR = new DialogueNode("Dominion", "", false) {
        @Override
        public int getSecondsPassed() {
            return 60 * 60;
        }

        @Override
        public String getContent() {
            return UtilText.parseFromXMLFile("places/global/globalPlaces", "DOMINION_EXTERIOR");
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 1) {
                return new ResponseEffectsOnly("City centre", "Head into the centre of Dominion.") {
                    @Override
                    public void effects() {
                        Main.game.getPlayer().setLocation(
                                WorldType.DOMINION,
                                PlaceType.DOMINION_PLAZA,
                                false);

                        Main.game.getTextStartStringBuilder().append(UtilText.parseFromXMLFile("places/global/globalPlaces", "ENTERING_DOMINION"));

                        Main.game.setContent(new Response("", "", Main.game.getDefaultDialogue(false)));
                    }
                };
            }
            return null;
        }
    };
    public static final DialogueNode FOLOI_FIELDS = new DialogueNode("Foloi Fields", "", false) {
        @Override
        public int getSecondsPassed() {
            if (Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.hasSnowedThisWinter)
                    && Main.game.getSeason() == Season.WINTER
                    && !Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.lunetteTerrorEnded)) {
                return 60 * 60;
            }
            return 30 * 60;
        }

        @Override
        public String getContent() {
            StringBuilder sb = new StringBuilder();

            sb.append(UtilText.parseFromXMLFile("places/global/globalPlaces", "FOLOI_FIELDS"));
            sb.append(getUnavailableAreaText());

            if (Main.game.getPlayer().getLocation().increment(0, -1).equals(Main.game.getWorlds().get(WorldType.WORLD_MAP).getCell(PlaceType.WORLD_MAP_DOMINION).getLocation())) {
                sb.append(UtilText.parseFromXMLFile("places/fields/centaur_transport", "UNEXPLORED"));
            }

            return sb.toString();
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (Main.game.getPlayer().getLocation().increment(0, -1).equals(Main.game.getWorlds().get(WorldType.WORLD_MAP).getCell(PlaceType.WORLD_MAP_DOMINION).getLocation())) {
                if (index == 1) {
                    return new Response("Depot", "Approach the 'Centaur Carriage Depot' and see if any businesses are still operating out of it.", DialogueManager.getDialogueFromId("innoxia_places_fields_centaur_transport_approach")) {
                        @Override
                        public void effects() {
                            Main.game.getPlayerCell().getPlace().setPlaceType(PlaceType.getPlaceTypeFromId("innoxia_fields_centaur_transport"));
                        }
                    };
                }

            } else {
                if (index == 1) {
                    return new ResponseEffectsOnly(
                            "Explore",
                            "Take some time to explore the fields. Although you don't think you're any more or less likely to find anything by doing this, at least you won't have to keep travelling back and forth...") {
                        @Override
                        public int getSecondsPassed() {
                            return 30 * 60;
                        }

                        @Override
                        public void effects() {
                            DialogueNode dn = Main.game.getActiveWorld().getCell(Main.game.getPlayer().getLocation()).getDialogue(true, true);
                            Main.game.setContent(new Response("", "", dn));
                        }
                    };
                }
            }
            return null;
        }
    };
    public static final DialogueNode FOLOI_FOREST = new DialogueNode("Foloi Forest", "", false) {
        @Override
        public int getSecondsPassed() {
            return 60 * 60;
        }

        @Override
        public String getContent() {

            String sb = UtilText.parseFromXMLFile("places/global/globalPlaces", "FOLOI_FOREST") +
                    getUnavailableAreaText();

            return sb;
        }

        @Override
        public Response getResponse(int responseTab, int index) {
//			if (index == 1) {
//				return new Response("Explore", "Take some time to explore this area of the forest and see what you can find.<br/>[style.italicsBad(Will be added soon!)]", null) {
//					@Override
//					public void effects() {
//						//TODO generate world
//					}
//				};
//			}
            return null;
        }
    };
    public static final DialogueNode GRASSLAND_WILDERNESS = new DialogueNode("Grassland Wilderness", "", false) {
        @Override
        public int getSecondsPassed() {
            return 60 * 60;
        }

        @Override
        public String getContent() {

            String sb = UtilText.parseFromXMLFile("places/global/globalPlaces", "GRASSLAND_WILDERNESS") +
                    getUnavailableAreaText();

            return sb;
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 1) {
                return new ResponseEffectsOnly(
                        "Explore",
                        "Explore some of the valleys and see what you can find. Although you don't think you're any more or less likely to find anything by doing this, at least you won't have to keep travelling back and forth...") {
                    @Override
                    public int getSecondsPassed() {
                        return 30 * 60;
                    }

                    @Override
                    public void effects() {
                        DialogueNode dn = Main.game.getActiveWorld().getCell(Main.game.getPlayer().getLocation()).getDialogue(true, true);
                        Main.game.setContent(new Response("", "", dn));
                    }
                };

            } else {
                return null;
            }
        }
    };
    public static final DialogueNode RIVER_HUBUR = new DialogueNode("River Hubur", "", false) {
        @Override
        public int getSecondsPassed() {
            return 30 * 60;
        }

        @Override
        public String getContent() {

            String sb = UtilText.parseFromXMLFile("places/global/globalPlaces", "RIVER_HUBUR") +
                    getUnavailableAreaText();

            return sb;
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 1) {
                return new ResponseEffectsOnly(
                        "Explore",
                        "Take some time to explore the shores of the river. Although you don't think you're any more or less likely to find anything by doing this, at least you won't have to keep travelling back and forth...") {
                    @Override
                    public int getSecondsPassed() {
                        return 30 * 60;
                    }

                    @Override
                    public void effects() {
                        DialogueNode dn = Main.game.getActiveWorld().getCell(Main.game.getPlayer().getLocation()).getDialogue(true, true);
                        Main.game.setContent(new Response("", "", dn));
                    }
                };

            } else {
                return null;
            }
        }
    };
    public static final DialogueNode ELIS = new DialogueNode("Elis", "", false) {
        @Override
        public int getSecondsPassed() {
            return 30 * 60;
        }

        @Override
        public String getContent() {

            String sb = UtilText.parseFromXMLFile("places/global/globalPlaces", "ELIS") +
                    getUnavailableAreaText();

            return sb;
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 1) {
                return new Response("Enter (main)", "Approach Elis from the East so that you can enter the town via its main gatehouse.", DialogueManager.getDialogueFromId("innoxia_places_fields_elis_generic_road_east")) {
                    @Override
                    public void effects() {
                        Main.game.getPlayer().setLocation(WorldType.getWorldTypeFromId("innoxia_fields_elis_town"), PlaceType.getPlaceTypeFromId("innoxia_fields_elis_town_entry_east"));
                        Main.game.getPlayer().setNearestLocation(WorldType.getWorldTypeFromId("innoxia_fields_elis_town"), PlaceType.getPlaceTypeFromId("innoxia_fields_elis_town_road_east"));
                        Main.game.appendToTextStartStringBuilder(UtilText.parseFromXMLFile("places/fields/elis/generic", "ENTER_ELIS"));
                    }

                    public boolean isStripContent() {
                        return true;
                    }
                };

            } else if (index == 2) {
                return new Response("Enter (rear)", "Approach Elis from the West so that you can enter the town via its rear gatehouse.", DialogueManager.getDialogueFromId("innoxia_places_fields_elis_generic_road_west")) {
                    @Override
                    public void effects() {
                        Main.game.getPlayer().setLocation(WorldType.getWorldTypeFromId("innoxia_fields_elis_town"), PlaceType.getPlaceTypeFromId("innoxia_fields_elis_town_entry_west"));
                        Main.game.getPlayer().setNearestLocation(WorldType.getWorldTypeFromId("innoxia_fields_elis_town"), PlaceType.getPlaceTypeFromId("innoxia_fields_elis_town_road_west"));
                        Main.game.appendToTextStartStringBuilder(UtilText.parseFromXMLFile("places/fields/elis/generic", "ENTER_ELIS"));
                    }

                    public boolean isStripContent() {
                        return true;
                    }
                };

            } else {
                return null;
            }
        }
    };

    private static String getUnavailableAreaText() {
        Vector2i playerLoc = Main.game.getPlayer().getLocation();
        Map<Bearing, Cell> dirMap = new HashMap<>();
        dirMap.put(Bearing.NORTH, Main.game.getWorlds().get(WorldType.WORLD_MAP).getCell(playerLoc.getX(), playerLoc.getY() + 1));
        dirMap.put(Bearing.EAST, Main.game.getWorlds().get(WorldType.WORLD_MAP).getCell(playerLoc.getX() + 1, playerLoc.getY()));
        dirMap.put(Bearing.SOUTH, Main.game.getWorlds().get(WorldType.WORLD_MAP).getCell(playerLoc.getX(), playerLoc.getY() - 1));
        dirMap.put(Bearing.WEST, Main.game.getWorlds().get(WorldType.WORLD_MAP).getCell(playerLoc.getX() - 1, playerLoc.getY()));

        StringBuilder sb = new StringBuilder();

        for (Entry<Bearing, Cell> entry : dirMap.entrySet()) {
            if (entry.getValue() != null && entry.getValue().getDialogue(false) == null) {
                if (!sb.isEmpty()) {
                    sb.append("<br/>");
                } else {
                    sb.append("<p style='text-align:center;'>[style.boldBad(Travel Restricted)]<br/>[style.italicsMinorBad(");
                }
                sb.append("You cannot currently travel <b>").append(entry.getKey().getName()).append("</b> into the ").append(entry.getValue().getPlace().getName()).append("!");

            }
        }
        if (!sb.isEmpty()) {
            sb.append(")]</p>");
        }

        return sb.toString();
    }
}
