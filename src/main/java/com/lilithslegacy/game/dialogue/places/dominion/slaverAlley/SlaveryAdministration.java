package com.lilithslegacy.game.dialogue.places.dominion.slaverAlley;

import com.lilithslegacy.game.character.npc.dominion.Finch;
import com.lilithslegacy.game.character.quests.Quest;
import com.lilithslegacy.game.character.quests.QuestLine;
import com.lilithslegacy.game.dialogue.DialogueFlagValue;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.companions.CompanionManagement;
import com.lilithslegacy.game.dialogue.companions.OccupantManagementDialogue;
import com.lilithslegacy.game.dialogue.responses.Response;
import com.lilithslegacy.game.dialogue.responses.ResponseTrade;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.item.ItemType;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.4
 * @since 0.3.2 (content from 0.1.0)
 */
public class SlaveryAdministration {

    private static int slaverLicenseCost = 5000;

    private static Finch getFinch() {
        return (Finch) Main.game.getNpc(Finch.class);
    }

    public static final DialogueNode SLAVERY_ADMINISTRATION_EXTERIOR = new DialogueNode("Slavery Administration", ".", false) {
        @Override
        public int getSecondsPassed() {
            return 60;
        }

        @Override
        public String getContent() {
            return UtilText.parseFromXMLFile("places/dominion/slaverAlley/slaveryAdministration", "SLAVERY_ADMINISTRATION_EXTERIOR");
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 1) {
                return new Response("Enter", "Step inside the 'Slavery Administration' building.", SLAVERY_ADMINISTRATION);

            } else if (index == 2) {
                if (Main.game.getCurrentDialogueNode() == SLAVERY_ADMINISTRATION_POSTERS) {
                    return new Response("Posters", "You're already taking a closer look at the posters...", null);
                }
                return new Response("Posters", "Take a closer look at the posters which are plastered over the wall of the Slavery Administation building.", SLAVERY_ADMINISTRATION_POSTERS);
            }
            return null;
        }
    };

    public static final DialogueNode SLAVERY_ADMINISTRATION_POSTERS = new DialogueNode("Slavery Administration", ".", false) {
        @Override
        public int getSecondsPassed() {
            return 2 * 60;
        }

        @Override
        public String getContent() {
            return UtilText.parseFromXMLFile("places/dominion/slaverAlley/slaveryAdministration", "SLAVERY_ADMINISTRATION_POSTERS");
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return SLAVERY_ADMINISTRATION_EXTERIOR.getResponse(responseTab, index);
        }
    };

    public static final DialogueNode SLAVERY_ADMINISTRATION = new DialogueNode("Slavery Administration", ".", true) {
        @Override
        public int getSecondsPassed() {
            return 2 * 60;
        }

        @Override
        public String getContent() {
            return UtilText.parseFromXMLFile("places/dominion/slaverAlley/slaveryAdministration", "SLAVERY_ADMINISTRATION");
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (Main.game.getPlayer().isHasSlaverLicense()) {
                if (index == 1) {
                    return new ResponseTrade("Trade", "Buy slavery-related items.", getFinch());

                } else if (index == 2 && !Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.finchFreedomTalk)) {
                    return new Response("Freeing slaves", "Ask Finch about what sort of process you'd need to go through in order to set slaves free.", SLAVE_FREEDOM_TALK) {
                        @Override
                        public void effects() {
                            getFinch().addItem(Main.game.getItemGen().generateItem("innoxia_slavery_freedom_certification"), 10, false, false);
                            Main.game.getDialogueFlags().setFlag(DialogueFlagValue.finchFreedomTalk, true);
                        }
                    };

                } else if (index == 5) {
                    return new Response("Slave Manager", "Open the slave management screen.", SLAVERY_ADMINISTRATION) {
                        @Override
                        public boolean isTradeHighlight() {
                            return true;
                        }

                        @Override
                        public DialogueNode getNextDialogue() {
                            CompanionManagement.initManagement(null, 0, null);
                            return OccupantManagementDialogue.getSlaveryManagementDialogue(null, null);
                        }
                    };

                } else if (index == 0) {
                    return new Response("Leave", "Step back outside.", SLAVERY_ADMINISTRATION_EXTERIOR);

                } else {
                    return null;
                }
            } else {
                if (index == 1) {
                    if (!Main.game.getPlayer().hasQuest(QuestLine.SIDE_SLAVERY)) {
                        return new Response("Slaver license", "Ask Finch about obtaining a slaver license.", SLAVERY_ADMINISTRATION_ASK_ABOUT_SLAVER_LICENSE) {
                            @Override
                            public void effects() {
                                Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().startQuest(QuestLine.SIDE_SLAVERY));
                                Main.game.getDialogueFlags().values.add(DialogueFlagValue.finchIntroduced);
                            }
                        };

                    } else if (Main.game.getPlayer().getQuest(QuestLine.SIDE_SLAVERY) == Quest.SIDE_SLAVER_RECOMMENDATION_OBTAINED) {
                        if (Main.game.getPlayer().getMoney() >= slaverLicenseCost) {
                            return new Response("Present letter (<span style='color:" + PresetColour.CURRENCY_GOLD.toWebHexString() + ";'>" + UtilText.getCurrencySymbol() + "</span> " + slaverLicenseCost + ")",
                                    "Show Finch the letter of recommendation you obtained from Lilaya, and then pay " + slaverLicenseCost + " flames to obtain a slaver license.",
                                    SLAVERY_ADMINISTRATION_SLAVER_LICENSE_OBTAINED) {
                                @Override
                                public void effects() {
                                    Main.game.getPlayer().incrementMoney(-slaverLicenseCost);
                                }
                            };
                        } else {
                            return new Response("Present letter (" + UtilText.getCurrencySymbol() + " " + slaverLicenseCost + ")", "You don't have enough money to buy a slaver license! You need at least " + slaverLicenseCost + " flames.", null);
                        }

                    } else {
                        return new Response("Present letter (" + UtilText.getCurrencySymbol() + " " + slaverLicenseCost + ")", "You need to obtain a letter of recommendation from Lilaya first!", null);

                    }

                } else if (index == 0) {
                    return new Response("Leave", "Step back outside.", SLAVERY_ADMINISTRATION_EXTERIOR) {
                        @Override
                        public void effects() {
                            Main.game.getDialogueFlags().values.add(DialogueFlagValue.finchIntroduced);
                        }
                    };

                } else {
                    return null;
                }
            }
        }
    };

    public static final DialogueNode SLAVERY_ADMINISTRATION_ASK_ABOUT_SLAVER_LICENSE = new DialogueNode("Slavery Administration", ".", true) {
        @Override
        public int getSecondsPassed() {
            return 5 * 60;
        }

        @Override
        public String getContent() {
            return UtilText.parseFromXMLFile("places/dominion/slaverAlley/slaveryAdministration", "SLAVERY_ADMINISTRATION_ASK_ABOUT_SLAVER_LICENSE");
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return SLAVERY_ADMINISTRATION.getResponse(0, index);
        }
    };

    public static final DialogueNode SLAVERY_ADMINISTRATION_SLAVER_LICENSE_OBTAINED = new DialogueNode("Slavery Administration", ".", true) {
        @Override
        public int getSecondsPassed() {
            return 5 * 60;
        }

        @Override
        public String getContent() {
            return UtilText.parseFromXMLFile("places/dominion/slaverAlley/slaveryAdministration", "SLAVERY_ADMINISTRATION_SLAVER_LICENSE_OBTAINED");
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 1) {
                return new Response("Rules", "Allow [finch.name] to explain the rules to you.", SLAVERY_ADMINISTRATION_SLAVER_LICENSE_OBTAINED_RULES) {
                    @Override
                    public void effects() {
                        Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().setQuestProgress(QuestLine.SIDE_SLAVERY, Quest.SIDE_UTIL_COMPLETE));
                        Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().addItem(Main.game.getItemGen().generateItem(ItemType.SLAVER_LICENSE), false));
                    }
                };

            } else {
                return null;
            }
        }
    };

    public static final DialogueNode SLAVERY_ADMINISTRATION_SLAVER_LICENSE_OBTAINED_RULES = new DialogueNode("Slavery Administration", ".", true, true) {
        @Override
        public int getSecondsPassed() {
            return 5 * 60;
        }

        @Override
        public String getContent() {
            return UtilText.parseFromXMLFile("places/dominion/slaverAlley/slaveryAdministration", "SLAVERY_ADMINISTRATION_SLAVER_LICENSE_OBTAINED_RULES");
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return SLAVERY_ADMINISTRATION.getResponse(0, index);
        }
    };

    public static final DialogueNode SLAVE_FREEDOM_TALK = new DialogueNode("", "", true) {
        @Override
        public void applyPreParsingEffects() {
            if (Main.game.getPlayer().hasQuest(QuestLine.SIDE_ACCOMMODATION)) {
                Main.game.getTextEndStringBuilder().append(UtilText.parseFromXMLFile("places/dominion/slaverAlley/slaveryAdministration", "SLAVE_FREEDOM_TALK_END"));
            } else {
                Main.game.getTextEndStringBuilder().append(UtilText.parseFromXMLFile("places/dominion/slaverAlley/slaveryAdministration", "SLAVE_FREEDOM_TALK_END_START_ACCOMMODATION_QUEST"));
                Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().startQuest(QuestLine.SIDE_ACCOMMODATION));
            }
        }

        @Override
        public int getSecondsPassed() {
            return 5 * 60;
        }

        @Override
        public String getContent() {
            UtilText.addSpecialParsingString(Util.intToString(ItemType.getItemTypeFromId("innoxia_slavery_freedom_certification").getValue()), true);
            return UtilText.parseFromXMLFile("places/dominion/slaverAlley/slaveryAdministration", "SLAVE_FREEDOM_TALK");
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return SLAVERY_ADMINISTRATION.getResponse(0, index);
        }
    };

}
