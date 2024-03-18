package com.lilithslegacy.game.dialogue.companions;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.attributes.AffectionLevel;
import com.lilithslegacy.game.character.attributes.ObedienceLevel;
import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.dialogue.DialogueFlagValue;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.DialogueNodeType;
import com.lilithslegacy.game.dialogue.eventLog.SlaveryEventLogEntry;
import com.lilithslegacy.game.dialogue.responses.Response;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.occupantManagement.slave.SlaveJob;
import com.lilithslegacy.game.occupantManagement.slaveEvent.SlaveEventType;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.rendering.SVGImages;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.utils.comparators.SlaveNameComparator;
import com.lilithslegacy.utils.comparators.SlaveRoomComparator;
import com.lilithslegacy.utils.comparators.SlaveValueComparator;
import com.lilithslegacy.world.AbstractWorldType;
import com.lilithslegacy.world.Cell;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.AbstractPlaceUpgrade;
import com.lilithslegacy.world.places.GenericPlace;
import com.lilithslegacy.world.places.PlaceType;
import com.lilithslegacy.world.places.PlaceUpgrade;

/**
 * @author Innoxia
 * @version 0.3.9.2
 * @since 0.1.8?
 */
public class OccupantManagementDialogue {

    public static Cell cellToInspect;
    private static DialogueNode dialogueToExitTo = null;
    private static final StringBuilder miscDialogueSB = new StringBuilder();
    private static int dayNumber = 1;
    private static final DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    private static final List<SlaveEventType> eventTypeFilterExclusions = new ArrayList<>();
    private static final List<String> slaveIdFilterExclusions = new ArrayList<>();
    private static OccupantSortingMethod sortingMethod = OccupantSortingMethod.NONE;
    private static boolean reverseSortSlaves = false;
    private static List<Cell> importantCells = new ArrayList<>();
    private static final StringBuilder purchaseAvailability = new StringBuilder();

    static {
        decimalFormat.setRoundingMode(RoundingMode.HALF_EVEN);
    }

    public static NPC characterSelected() {
        return Main.game.getDialogueFlags().getManagementCompanion();
    }

    /**
     * @param dialogueToExitTo The DialogueNode which should be displayed when exiting out of the occupant management windows.
     * @return OCCUPANT_OVERVIEW
     */
    public static DialogueNode getSlaveryOverviewDialogue(DialogueNode dialogueToExitTo) {
        OccupantManagementDialogue.dialogueToExitTo = dialogueToExitTo;
        dayNumber = Main.game.getDayNumber();
        Main.game.getDialogueFlags().setSlaveTrader(null);
        return OCCUPANT_OVERVIEW;
    }

    /**
     * @param dialogueToExitTo The DialogueNode which should be displayed when exiting out of the occupant management windows. Pass in null to return to default dialogue.
     * @param slaveTrader      The character you are trading with.
     * @return SLAVE_LIST_MANAGEMENT
     */
    public static DialogueNode getSlaveryManagementDialogue(DialogueNode dialogueToExitTo, NPC slaveTrader) {
        OccupantManagementDialogue.dialogueToExitTo = dialogueToExitTo;
        dayNumber = Main.game.getDayNumber();
        Main.game.getDialogueFlags().setSlaveTrader(slaveTrader);
        return SLAVE_LIST_MANAGEMENT;
    }

    /**
     * @param dialogueToExitTo The DialogueNode which should be displayed when exiting out of the occupant management windows. Pass in null to return to default dialogue.
     * @param slaveTrader      The character you are trading with.
     * @return SLAVE_LIST
     */
    public static DialogueNode getSlaveryRoomListDialogue(DialogueNode dialogueToExitTo, NPC slaveTrader) {
        OccupantManagementDialogue.dialogueToExitTo = dialogueToExitTo;
        dayNumber = Main.game.getDayNumber();
        Main.game.getDialogueFlags().setSlaveTrader(slaveTrader);
        return SLAVE_LIST;
    }

    public static int getDayNumber() {
        return dayNumber;
    }

    public static void setDayNumber(int dayNumber) {
        OccupantManagementDialogue.dayNumber = Math.max(1, dayNumber);
    }

    public static OccupantSortingMethod setSlavesSortedBy() {
        return sortingMethod;
    }

    public static void setSlavesSortedBy(OccupantSortingMethod osm) {
        OccupantManagementDialogue.sortingMethod = osm;
    }

    public static boolean getSlavesAreInReverseOrder() {
        return OccupantManagementDialogue.reverseSortSlaves;
    }

    public static final DialogueNode OCCUPANT_OVERVIEW = new DialogueNode("Slavery Overview", ".", true) {

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.OCCUPANT_MANAGEMENT;
        }

        @Override
        public String getContent() {
            UtilText.nodeContentSB.setLength(0);

            int income = Main.game.getPlayer().getSlaveryTotalDailyIncome();
            int upkeep = Main.game.getPlayer().getSlaveryTotalDailyUpkeep();

            // Overview:
            UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>" + "<h6 style='color:").append(PresetColour.GENERIC_EXPERIENCE.toWebHexString()).append("; text-align:center;'>Totals</h6>").append("<div class='container-full-width' style='text-align:center; margin-bottom:0;'>").append("<div style='width:10%; float:left; font-weight:bold; margin:0; padding:0;'>").append("Slaves").append("</div>").append("<div style='float:left; width:15%; font-weight:bold; margin:0; padding:0;'>").append("<b style='color:").append(PresetColour.GENERIC_GOOD.toWebHexString()).append(";'>Income</b>").append("</div>").append("<div style='float:left; width:15%; font-weight:bold; margin:0; padding:0;'>").append("<b style='color:").append(PresetColour.GENERIC_BAD.toWebHexString()).append(";'>Upkeep</b>").append("</div>").append("<div style='float:left; width:15%; font-weight:bold; margin:0; padding:0;'>").append("<b style='color:").append(PresetColour.CURRENCY_GOLD.toWebHexString()).append(";'>Profit</b>").append("</div>").append("<div style='float:left; width:15%; font-weight:bold; margin:0; padding:0;'>").append("<b style='color:").append(PresetColour.GENERIC_GOOD.toWebHexString()).append(";'>Funds</b>").append("</div>").append("<div style='float:left; width:15%; font-weight:bold; margin:0; padding:0;'>").append("<b style='color:").append(PresetColour.GENERIC_BAD.toWebHexString()).append(";'>Payments</b>").append("</div>").append("<div style='float:left; width:15%; font-weight:bold; margin:0; padding:0;'>").append("<b style='color:").append(PresetColour.CURRENCY_GOLD.toWebHexString()).append(";'>Balance</b>").append("</div>").append("</div>").append("<div class='container-full-width inner' style='text-align:center;'>").append("<div style='width:10%; float:left; margin:0; padding:0;'>").append(Main.game.getPlayer().getSlavesOwned().size()).append("</div>").append("<div style='float:left; width:15%; margin:0; padding:0;'>").append(UtilText.formatAsMoney(income)).append("/day").append("</div>").append("<div style='float:left; width:15%; margin:0; padding:0;'>").append(UtilText.formatAsMoney(upkeep, "b", PresetColour.GENERIC_BAD)).append("/day").append("</div>").append("<div style='float:left; width:15%; margin:0; padding:0;'>").append(UtilText.formatAsMoney(income - upkeep, "b", (income - upkeep < 0 ? PresetColour.GENERIC_BAD : PresetColour.TEXT))).append("/day").append("</div>").append("<div style='width:15%; float:left; font-weight:bold; margin:0; padding:0;'>").append(UtilText.formatAsMoney(Main.game.getOccupancyUtil().getGeneratedIncome(), "b")).append("</div>").append("<div style='width:15%; float:left; font-weight:bold; margin:0; padding:0;'>").append(UtilText.formatAsMoney(Main.game.getOccupancyUtil().getGeneratedUpkeep(), "b", PresetColour.GENERIC_BAD)).append("</div>").append("<div style='width:15%; float:left; font-weight:bold; margin:0; padding:0;'>").append(Main.game.getOccupancyUtil().getGeneratedBalance() < 0
                    ? UtilText.formatAsMoney(Main.game.getOccupancyUtil().getGeneratedBalance(), "b", PresetColour.GENERIC_BAD)
                    : UtilText.formatAsMoney(Main.game.getOccupancyUtil().getGeneratedBalance(), "b")).append("</div>").append("</div>").append("</div>");

            // Logs:
            UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>" + "<h6 style='color:").append(PresetColour.GENERIC_ARCANE.toWebHexString()).append("; text-align:center;'>Activity Log</h6>");
            // Buttons:
            for (int i = 6; i >= 0; i--) {
                UtilText.nodeContentSB.append("<div id='SLAVE_DAY_").append(i).append("' class='normal-button' style='width:12%; margin:1%;").append(Main.game.getDayNumber() - i == dayNumber ? "color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";" : "").append("'>").append(i == 0
                        ? "Today"
                        : (i == 1
                        ? "Yesterday"
                        : Main.game.getDateNow().minusDays(i).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH))).append("</div>");
            }

            // Headers:
            UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center; margin-bottom:0;'>"
                    + "<div style='width:10%; float:left; font-weight:bold; margin:0; padding:0;'>"
                    + "Time"
                    + "</div>"
                    + "<div style='width:15%; float:left; font-weight:bold; margin:0; padding:0;'>"
                    + "Slave"
                    + "</div>"
                    + "<div style='float:left; width:15%; font-weight:bold; margin:0; padding:0;'>"
                    + "Event"
                    + "</div>"
                    + "<div style='float:left; width:60%; font-weight:bold; margin:0; padding:0;'>"
                    + "Description"
                    + "</div>"
                    + "</div>"
                    + "<div class='container-full-width' style='text-align:center; margin-bottom:0;'>");

            int count = 0;
            if (Main.game.getSlaveryEvents(dayNumber) != null) {
                List<SlaveryEventLogEntry> entries = new ArrayList<>(Main.game.getSlaveryEvents(dayNumber));
                int filtered = 0;
                for (SlaveryEventLogEntry entry : Main.game.getSlaveryEvents(dayNumber)) {
                    if (eventTypeFilterExclusions.contains(entry.getEvent().getType())
                            || (slaveIdFilterExclusions.contains(entry.getSlaveID()) && new HashSet<>(slaveIdFilterExclusions).containsAll(entry.getInvolvedSlaveIDs()))) {
                        filtered++;
                        entries.remove(entry);
                    }
                }
                if (filtered > 0) {
                    UtilText.nodeContentSB.append("<div class='container-full-width inner' style='background:").append(PresetColour.BACKGROUND_ALT.toWebHexString()).append(";'>[style.italicsBad(Filtered events: ").append(filtered).append(")]</div>");
                }
                for (SlaveryEventLogEntry entry : entries) {
                    if (count % 2 == 0) {
                        UtilText.nodeContentSB.append("<div class='container-full-width inner' style='background:").append(PresetColour.BACKGROUND.toWebHexString()).append(";'>");
                    } else {
                        UtilText.nodeContentSB.append("<div class='container-full-width inner' style='background:").append(PresetColour.BACKGROUND_ALT.toWebHexString()).append(";'>");
                    }

                    UtilText.nodeContentSB.append("<div style='width:10%; float:left; margin:0; padding:0;'>").append(String.format("%02d", entry.getTime())).append(":00<br/>").append("</div>").append("<div style='width:15%; float:left; margin:0; padding:0;'>").append(entry.getSlaveName()).append("</div>").append("<div style='width:15%; float:left; margin:0; padding:0;'>").append(entry.getName()).append("</div>").append("<div style='width:60%; float:left;  margin:0; padding:0;'>").append(entry.getDescription()).append("</div>").append("</div>");
                    count++;
                }
            }
            if (count == 0) {
                UtilText.nodeContentSB.append("<div class='container-full-width inner' style='background:").append(PresetColour.BACKGROUND.toWebHexString()).append(";'>[style.colourDisabled(No events for this day...)]</div>");
            }

            UtilText.nodeContentSB.append("</div>"
                    + "</div>");

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public String getResponseTabTitle(int index) {
            if (index == 0) {
                return "Room";
            } else if (index == 1) {
                return "Filter (type)";
            } else if (index == 2) {
                return "Filter (slave)";
            }
            return null;
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (responseTab == 0) {
                return getSlaveryResponse(index);

            } else if (responseTab == 1) {
                if (index == 0) {
                    return getSlaveryResponse(index);
                }
                if (index == 1) {
                    return new Response("Add all", "Add all types to the filter.", OCCUPANT_OVERVIEW) {
                        @Override
                        public Colour getHighlightColour() {
                            return PresetColour.GENERIC_GOOD;
                        }

                        @Override
                        public void effects() {
                            eventTypeFilterExclusions.clear();
                        }
                    };

                } else if (index == 2) {
                    return new Response("Clear all", "Remove all types from the filter.", OCCUPANT_OVERVIEW) {
                        @Override
                        public Colour getHighlightColour() {
                            return PresetColour.GENERIC_BAD;
                        }

                        @Override
                        public void effects() {
                            eventTypeFilterExclusions.clear();
                            Collections.addAll(eventTypeFilterExclusions, SlaveEventType.values());
                        }
                    };
                }
                if (index - 3 < SlaveEventType.values().length) {
                    SlaveEventType type = SlaveEventType.values()[index - 3];
                    return new Response(type.getName(), "Click to filter events by this type:<br/><i>" + type.getDescription() + "</i>", OCCUPANT_OVERVIEW) {
                        @Override
                        public Colour getHighlightColour() {
                            if (eventTypeFilterExclusions.contains(type)) {
                                return PresetColour.TEXT_GREY;
                            } else {
                                return PresetColour.GENERIC_MINOR_GOOD;
                            }
                        }

                        @Override
                        public void effects() {
                            if (eventTypeFilterExclusions.contains(type)) {
                                eventTypeFilterExclusions.remove(type);
                            } else {
                                eventTypeFilterExclusions.add(type);
                            }
                        }
                    };
                }

            } else if (responseTab == 2) {
                List<String> ownedSlaves = Main.game.getPlayer().getSlavesOwned();
                if (index == 0) {
                    return getSlaveryResponse(index);
                }
                if (index == 1) {
                    return new Response("Add all", "Add all slaves to the filter.", OCCUPANT_OVERVIEW) {
                        @Override
                        public Colour getHighlightColour() {
                            return PresetColour.GENERIC_GOOD;
                        }

                        @Override
                        public void effects() {
                            slaveIdFilterExclusions.clear();
                        }
                    };

                } else if (index == 2) {
                    return new Response("Clear all", "Remove all slaves from the filter.", OCCUPANT_OVERVIEW) {
                        @Override
                        public Colour getHighlightColour() {
                            return PresetColour.GENERIC_BAD;
                        }

                        @Override
                        public void effects() {
                            slaveIdFilterExclusions.clear();
                            slaveIdFilterExclusions.addAll(ownedSlaves);
                        }
                    };
                }
                if (index - 3 < ownedSlaves.size()) {
                    String slaveId = ownedSlaves.get(index - 3);
                    GameCharacter slave = null;
                    try {
                        slave = Main.game.getNPCById(slaveId);
                    } catch (Exception ex) {
                    }
                    if (slave == null) {
                        return null;
                    }
                    GameCharacter slaveInner = slave;
                    return new Response(UtilText.parse(slave, "[npc.Name]"), "Click to filter this slave in or out of displayed events.", OCCUPANT_OVERVIEW) {
                        @Override
                        public Colour getHighlightColour() {
                            if (slaveIdFilterExclusions.contains(slaveId)) {
                                return PresetColour.TEXT_GREY;
                            } else {
                                return slaveInner.getFemininity().getColour();
                            }
                        }

                        @Override
                        public void effects() {
                            if (slaveIdFilterExclusions.contains(slaveId)) {
                                slaveIdFilterExclusions.remove(slaveId);
                            } else {
                                slaveIdFilterExclusions.add(slaveId);
                            }
                        }
                    };
                }
            }
            return null;
        }
    };

    public static void setSlavesAreInReverseOrder(boolean truth) {
        OccupantManagementDialogue.reverseSortSlaves = truth;
    }

    public static final DialogueNode ROOM_MANAGEMENT = new DialogueNode("Room Management", ".", true) {

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.OCCUPANT_MANAGEMENT;
        }

        @Override
        public String getContent() {
            UtilText.nodeContentSB.setLength(0);

            Cell cell = Main.game.getPlayerCell();
            GenericPlace place = cell.getPlace();
            List<NPC> charactersPresent = Main.game.getCharactersTreatingCellAsHome(cell);
            float affectionChange = place.getHourlyAffectionChange();
            float obedienceChange = place.getHourlyObedienceChange();
            UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>" + "<h6 style='color:").append(PresetColour.GENERIC_GOOD.toWebHexString()).append("; text-align:center;'>Current Location</h6>").append(getRoomHeader()).append(getRoomEntry(!place.isAbleToBeUpgraded(), true, cell, charactersPresent, affectionChange, obedienceChange)).append("</div>");

            // Lilaya's home:
            UtilText.nodeContentSB.append(getWorldRooms(WorldType.LILAYAS_HOUSE_GROUND_FLOOR));
            UtilText.nodeContentSB.append(getWorldRooms(WorldType.LILAYAS_HOUSE_FIRST_FLOOR));

            // Slaver alley:
            UtilText.nodeContentSB.append(getWorldRooms(WorldType.SLAVER_ALLEY));

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 0) {
                return new Response("Back", "Return to the previous screen.", OCCUPANT_OVERVIEW);

            } else {
                return null;
            }
        }
    };

    private static Response getSlaveryResponse(int index) {
        if (index == 1) {
            return new Response("Room List", "View the management screen for all rooms.", ROOM_MANAGEMENT) {
                @Override
                public void effects() {
                    Main.game.getDialogueFlags().setManagementCompanion(null);
                }
            };

        } else if (index == 2) {
            return new Response("Occupant List", "Enter the management screen for all slaves and friendly occupants.", SLAVE_LIST_MANAGEMENT) {
                @Override
                public DialogueNode getNextDialogue() {
                    return OccupantManagementDialogue.getSlaveryManagementDialogue(dialogueToExitTo, Main.game.getDialogueFlags().getSlaveTrader());
                }

                @Override
                public void effects() {
                    Main.game.getDialogueFlags().setManagementCompanion(null);
                }
            };

        } else if (index == 5) {
            if (Main.game.getOccupancyUtil().getGeneratedBalance() == 0) {
                return new Response("Collect: " + UtilText.formatAsMoneyUncoloured(Main.game.getOccupancyUtil().getGeneratedBalance(), "span"), "Your current balance is 0...", null);

            } else if (Main.game.getOccupancyUtil().getGeneratedBalance() > 0) {
                return new Response("Collect: " + UtilText.formatAsMoney(Main.game.getOccupancyUtil().getGeneratedBalance(), "span"), "Collect the money that you've earned through your slaves' activities.", OCCUPANT_OVERVIEW) {
                    @Override
                    public DialogueNode getNextDialogue() {
                        return Main.game.getCurrentDialogueNode();
                    }

                    @Override
                    public void effects() {
                        Main.game.getOccupancyUtil().payOutBalance();
                    }
                };

            } else {
                if (Main.game.getPlayer().getMoney() < Math.abs(Main.game.getOccupancyUtil().getGeneratedBalance())) {
                    return new Response("Pay: " + UtilText.formatAsMoneyUncoloured(Math.abs(Main.game.getOccupancyUtil().getGeneratedBalance()), "span"),
                            "You don't have enough money to pay off the accumulated debt from the upkeep of your slaves and rooms.", null);
                }

                return new Response("Pay: " + UtilText.formatAsMoney(Math.abs(Main.game.getOccupancyUtil().getGeneratedBalance()), "span", PresetColour.GENERIC_BAD), "Pay off the accumulated debt from the upkeep of your slaves and rooms.", OCCUPANT_OVERVIEW) {
                    @Override
                    public DialogueNode getNextDialogue() {
                        return Main.game.getCurrentDialogueNode();
                    }

                    @Override
                    public void effects() {
                        Main.game.getOccupancyUtil().payOutBalance();
                    }
                };
            }

        } else if (index == 0) {
            return new Response("Back", "Exit the occupancy ledger.", dialogueToExitTo == null ? Main.game.getDefaultDialogue() : dialogueToExitTo) {
                @Override
                public void effects() {
                    Main.game.getDialogueFlags().setManagementCompanion(null);
                    Main.game.getDialogueFlags().setSlaveTrader(null);
                }
            };

        } else {
            return null;
        }
    }

    private static String getRoomHeader() {
        return "<div class='container-full-width' style='margin-bottom:0;'>"
                + "<div style='width:15%; float:left; font-weight:bold; margin:0; padding:0;'>"
                + "Room Name"
                + "</div>"
                + "<div style='width:20%; float:left; font-weight:bold; margin:0; padding:0;'>"
                + "Occupants"
                + "</div>"
                + "<div style='float:left; width:10%; font-weight:bold; margin:0; padding:0;'>"
                + "<b>Capacity</b>"
                + "</div>"
                + "<div style='float:left; width:15%; font-weight:bold; margin:0; padding:0;'>"
                + "<b style='color:" + PresetColour.AFFECTION.toWebHexString() + ";'>Affection</b>"
                + "</div>"
                + "<div style='float:left; width:15%; font-weight:bold; margin:0; padding:0;'>"
                + "<b style='color:" + PresetColour.OBEDIENCE.toWebHexString() + ";'>Obedience</b>"
                + "</div>"
                + "<div style='float:left; width:15%; font-weight:bold; margin:0; padding:0;'>"
                + "<b style='color:" + PresetColour.CURRENCY_GOLD.toWebHexString() + ";'>Upkeep</b>"
                + "</div>"
                + "<div style='float:left; width:10%; font-weight:bold; margin:0; padding:0;'>"
                + "Actions"
                + "</div>"
                + "</div>";
    }

    private static String getRoomEntry(boolean disabled, boolean currentLocation, Cell cell, List<NPC> occupants, float affectionChange, float obedienceChange) {
        miscDialogueSB.setLength(0);

        GenericPlace place = cell.getPlace();

        miscDialogueSB.append("<div class='container-full-width inner' style='margin-bottom:4px; margin-top:4px; ").append(!occupants.isEmpty() ? "background:" + PresetColour.BACKGROUND_ALT.toWebHexString() + ";'" : "'").append("'>").append("<div style='width:15%; float:left; margin:0; padding:0;'>").append("<span style='color:").append(place.getColour().toWebHexString()).append(";'>").append(place.getName()).append("</span><br/>").append("</div>").append("<div style='width:20%; float:left; margin:0; padding:0;'>");

        int i = 0;
        for (NPC occupant : occupants) {
            if (occupant.isSlave()) {
                miscDialogueSB.append("<b style='color:").append(occupant.getFemininity().getColour().toWebHexString()).append(";'>").append(occupant.getName(true)).append("</b>").append(i + 1 == occupants.size() ? "" : "<br/>");
                i++;
            }
        }
        if (i == 0) {
            miscDialogueSB.append("<b style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>Empty</b>");
        }

        miscDialogueSB.append("</div>" + "<div style='float:left; width:10%; margin:0; padding:0;'>").append(i).append("/").append(place.getCapacity()).append("</div>").append("<div style='float:left; width:15%; margin:0; padding:0;'>").append("<span style='color:").append((affectionChange == 0 ? PresetColour.BASE_GREY : (affectionChange > 0 ? PresetColour.GENERIC_GOOD : PresetColour.GENERIC_BAD)).toWebHexString()).append(";'>").append(affectionChange > 0 ? "+" : "").append(decimalFormat.format(affectionChange)).append("</span>/hour").append("</div>").append("<div style='float:left; width:15%; margin:0; padding:0;'>").append("<span style='color:").append((obedienceChange == 0 ? PresetColour.BASE_GREY : (obedienceChange > 0 ? PresetColour.GENERIC_GOOD : PresetColour.GENERIC_BAD)).toWebHexString()).append(";'>").append(obedienceChange > 0 ? "+" : "").append(decimalFormat.format(obedienceChange)).append("</span>/hour").append("</div>").append("<div style='float:left; width:15%; margin:0; padding:0;'>").append(disabled
                ? "[style.colourDisabled(N/A)]"
                : (place.getUpkeep() > 0
                ? UtilText.formatAsMoney(-place.getUpkeep(), "span", PresetColour.GENERIC_BAD)
                : (place.getUpkeep() == 0
                ? UtilText.formatAsMoney(-place.getUpkeep(), "span", PresetColour.TEXT_GREY)
                : UtilText.formatAsMoney(-place.getUpkeep(), "span", PresetColour.GENERIC_GOOD)))).append("/day").append("</div>").append("<div style='float:left; width:10%; margin:0 auto; padding:0; display:inline-block; text-align:center;'>").append(disabled
                ? "<div id='" + cell.getId() + (currentLocation ? "_PRESENT" : "") + "_DISABLED' class='square-button solo disabled'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getSlaveInspectDisabled() + "</div></div>"
                : "<div id='" + cell.getId() + (currentLocation ? "_PRESENT" : "") + "' class='square-button solo'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getSlaveInspect() + "</div></div>").append("</div>").append("</div>");

        return miscDialogueSB.toString();
    }

    public static void resetImportantCells() {
        importantCells = new ArrayList<>();
    }

    public static List<Cell> getImportantCells() {
        if (importantCells.isEmpty()) {
            AbstractWorldType[] importantWorlds = new AbstractWorldType[]{WorldType.LILAYAS_HOUSE_GROUND_FLOOR, WorldType.LILAYAS_HOUSE_FIRST_FLOOR, WorldType.getWorldTypeFromId("acexp_dungeon")};
            for (AbstractWorldType wt : importantWorlds) {
                Cell[][] cellGrid = Main.game.getWorlds().get(wt).getCellGrid();
                for (int i = 0; i < cellGrid.length; i++) {
                    for (int j = 0; j < cellGrid[0].length; j++) {
                        if (!cellGrid[i][j].getPlace().getPlaceType().equals(PlaceType.LILAYA_HOME_CORRIDOR)
                                && !cellGrid[i][j].getPlace().getPlaceType().equals(PlaceType.GENERIC_IMPASSABLE)
                                && !cellGrid[i][j].getPlace().getPlaceType().equals(PlaceType.getPlaceTypeFromId("acexp_dungeon_corridor"))
                                && !cellGrid[i][j].getPlace().getPlaceType().equals(PlaceType.getPlaceTypeFromId("acexp_dungeon_stairs"))
                                && !cellGrid[i][j].getPlace().getPlaceType().equals(PlaceType.getPlaceTypeFromId("acexp_dungeon_stairs_garden"))) {
                            importantCells.add(cellGrid[i][j]);
                        }
                    }
                }
            }
        }

        return importantCells;
    }


    private static String getWorldRooms(AbstractWorldType worldType) {
        StringBuilder worldRoomSB = new StringBuilder();

        worldRoomSB.append("<div class='container-full-width' style='text-align:center;'>" + "<h6 style='color:").append(worldType.getColour().toWebHexString()).append("; text-align:center;'>").append(worldType.getName()).append("</h6>").append(getRoomHeader());

        Cell[][] cellGrid = Main.game.getWorlds().get(worldType).getCellGrid();
        List<Cell> sortingCells = new ArrayList<>();
        for (int i = 0; i < cellGrid.length; i++) {
            for (int j = 0; j < cellGrid[0].length; j++) {
                if (!cellGrid[i][j].getPlace().getPlaceUpgrades().isEmpty()) {
//					importantCells.add(cellGrid[i][j]);
                    sortingCells.add(cellGrid[i][j]);
                }
            }
        }

        sortingCells.sort(Comparator.comparing(Cell::getPlaceName));

        for (Cell c : sortingCells) {
            GenericPlace place = c.getPlace();
            worldRoomSB.append(getRoomEntry(!place.isAbleToBeUpgraded(), false, c, Main.game.getCharactersTreatingCellAsHome(c), place.getHourlyAffectionChange(), place.getHourlyObedienceChange()));
        }

        worldRoomSB.append("</div>");

        return worldRoomSB.toString();
    }

    private static String getRoomUpgradeHeader() {
        return "<div class='container-full-width' style='margin-bottom:0;'>"
                + "<div style='width:30%; float:left; font-weight:bold; margin:0; padding:0;'>"
                + "Upgrade"
                + "</div>"
                + "<div style='float:left; width:10%; font-weight:bold; margin:0; padding:0;'>"
                + "Capacity"
                + "</div>"
                + "<div style='float:left; width:15%; font-weight:bold; margin:0; padding:0;'>"
                + "<span style='color:" + PresetColour.AFFECTION.toWebHexString() + ";'>Affection</span>"
                + "</div>"
                + "<div style='float:left; width:15%; font-weight:bold; margin:0; padding:0;'>"
                + "<span style='color:" + PresetColour.OBEDIENCE.toWebHexString() + ";'>Obedience</span>"
                + "</div>"
                + "<div style='float:left; width:10%; font-weight:bold; margin:0; padding:0;'>"
                + "<span style='color:" + PresetColour.CURRENCY_GOLD.toWebHexString() + ";'>Upkeep</span>"
                + "</div>"
                + "<div style='float:left; width:10%; font-weight:bold; margin:0; padding:0;'>"
                + "<span style='color:" + PresetColour.CURRENCY_GOLD.toWebHexString() + ";'>Cost</span>"
                + "</div>"
                + "<div style='float:left; width:10%; font-weight:bold; margin:0; padding:0;'>"
                + "Actions"
                + "</div>"
                + "</div>";
    }

    private static String getUpgradeEntry(Cell cell, AbstractPlaceUpgrade upgrade) {
        miscDialogueSB.setLength(0);
        GenericPlace place = cell.getPlace();
        float affectionChange = upgrade.getHourlyAffectionGain();
        float obedienceChange = upgrade.getHourlyObedienceGain();
        boolean owned = place.getPlaceUpgrades().contains(upgrade);
        boolean availableForPurchase = upgrade.isPrerequisitesMet(place) && upgrade.getAvailability(cell).getKey() && (owned ? Main.game.getPlayer().getMoney() >= upgrade.getRemovalCost() : Main.game.getPlayer().getMoney() >= upgrade.getInstallCost());
        boolean canBuy = availableForPurchase;

        miscDialogueSB.append("<div class='container-full-width inner' style='margin-bottom:4px; margin-top:4px;").append(owned ? "background:" + PresetColour.BACKGROUND_ALT.toWebHexString() + ";'" : "'").append("'>").append("<div style='width:5%; float:left; margin:0; padding:0;'>").append("<div class='title-button no-select' id='ROOM_MOD_INFO_").append(PlaceUpgrade.getIdFromPlaceUpgrade(upgrade)).append("' style='position:relative; top:0;'>").append(SVGImages.SVG_IMAGE_PROVIDER.getInformationIcon()).append("</div>").append("</div>").append("<div style='width:25%; float:left; margin:0; padding:0;'>").append(owned
                ? "<b style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>" + Util.capitaliseSentence(upgrade.getName()) + "</b>"
                : (!availableForPurchase
                ? "<b style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>" + Util.capitaliseSentence(upgrade.getName()) + "</b>"
                : "<b>" + Util.capitaliseSentence(upgrade.getName()) + "</b>")).append("</div>").append("<div style='width:10%; float:left; margin:0; padding:0;'>").append(upgrade.getCapacity() > 0
                ? "<b style='color:" + PresetColour.GENERIC_EXCELLENT.toWebHexString() + ";'>+" + upgrade.getCapacity() + "</b>"
                : (upgrade.getCapacity() < 0
                ? "<b style='color:" + PresetColour.GENERIC_TERRIBLE.toWebHexString() + ";'>" + upgrade.getCapacity() + "</b>"
                : "[style.colourDisabled(0)]")).append("</div>").append("<div style='float:left; width:15%; margin:0; padding:0;'>").append(affectionChange > 0
                ? "<b style='color:" + PresetColour.AFFECTION.toWebHexString() + ";'>+" + decimalFormat.format(affectionChange) + "</b>/hour"
                : (affectionChange < 0
                ? "<b style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>" + decimalFormat.format(affectionChange) + "</b>/hour"
                : "[style.colourDisabled(0)]/hour")).append("</div>").append("<div style='float:left; width:15%; margin:0; padding:0;'>").append(obedienceChange > 0
                ? "<b style='color:" + PresetColour.OBEDIENCE.toWebHexString() + ";'>+" + decimalFormat.format(obedienceChange) + "</b>/hour"
                : (obedienceChange < 0
                ? "<b style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>" + decimalFormat.format(obedienceChange) + "</b>/hour"
                : "[style.colourDisabled(0)]/hour")).append("</div>").append("<div style='float:left; width:10%; margin:0; padding:0;'>").append(upgrade.getUpkeep() > 0
                ? UtilText.formatAsMoney(upgrade.getUpkeep(), "b", PresetColour.GENERIC_BAD)
                : UtilText.formatAsMoney(upgrade.getUpkeep(), "b", PresetColour.GENERIC_GOOD)).append("/day").append("</div>").append("<div style='float:left; width:10%; margin:0; padding:0;'>").append(owned
                ? (upgrade.getRemovalCost() < 0
                ? UtilText.formatAsMoney(upgrade.getRemovalCost(), "b", PresetColour.GENERIC_GOOD)
                : (upgrade.getRemovalCost() < Main.game.getPlayer().getMoney()
                ? UtilText.formatAsMoney(upgrade.getRemovalCost(), "b")
                : UtilText.formatAsMoney(upgrade.getRemovalCost(), "b", PresetColour.GENERIC_BAD)))
                : (upgrade.getInstallCost() < 0
                ? UtilText.formatAsMoney(upgrade.getInstallCost(), "b", PresetColour.GENERIC_GOOD)
                : (upgrade.getInstallCost() < Main.game.getPlayer().getMoney()
                ? UtilText.formatAsMoney(upgrade.getInstallCost(), "b")
                : UtilText.formatAsMoney(upgrade.getInstallCost(), "b", PresetColour.GENERIC_BAD)))).append("</div>").append("<div style='float:left; width:10%; margin:0 auto; padding:0; display:inline-block; text-align:center;'>");

        if (owned) {
            if (Main.game.getPlayer().getMoney() < upgrade.getRemovalCost() || !upgrade.getRemovalAvailability(cell).getKey()) {
                miscDialogueSB.append("<div id='").append(PlaceUpgrade.getIdFromPlaceUpgrade(upgrade)).append("_SELL_DISABLED' class='square-button solo disabled'><div class='square-button-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getTransactionSellDisabled()).append("</div></div>");
            } else {
                miscDialogueSB.append("<div id='").append(PlaceUpgrade.getIdFromPlaceUpgrade(upgrade)).append("_SELL' class='square-button solo'><div class='square-button-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getTransactionSell()).append("</div></div>");
            }

        } else {
            if (Main.game.getPlayer().getMoney() < upgrade.getInstallCost() || Main.game.getOccupancyUtil().getGeneratedBalance() < 0) {
                canBuy = false;
            }
            if (canBuy) {
                if (!upgrade.getPrerequisites().isEmpty()) {
                    for (AbstractPlaceUpgrade prereq : upgrade.getPrerequisites()) {
                        if (!place.getPlaceUpgrades().contains(prereq)) {
                            canBuy = false;
                            break;
                        }
                    }
                }
            }

            if (canBuy) {
                miscDialogueSB.append("<div id='").append(PlaceUpgrade.getIdFromPlaceUpgrade(upgrade)).append("_BUY' class='square-button solo'><div class='square-button-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getTransactionBuy()).append("</div></div>");
            } else {
                miscDialogueSB.append("<div id='").append(PlaceUpgrade.getIdFromPlaceUpgrade(upgrade)).append("_BUY_DISABLED' class='square-button solo disabled'><div class='square-button-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getTransactionBuyDisabled()).append("</div></div>");
            }
        }

        miscDialogueSB.append(
                "</div>"
//					+ "<p>"
//						+ "<i>"
//							+(!owned
//								?"[style.colourDisabled("+upgrade.getDescriptionForPurchase()+")]"
//								:upgrade.getDescriptionAfterPurchase())
//						+"</i>"
//					+ "</p>"
//					+  (upgrade.isCoreRoomUpgrade() && !owned
//							?"<p>This is a [style.boldArcane(core modification)], and will [style.boldBad(remove all other modifications in this room when purchased)].</p>"
//							:"")
        );

        if (!canBuy) {
            miscDialogueSB.append("<p>" + "<i>" + "[style.colourBad(").append(getPurchaseAvailabilityTooltipText(OccupantManagementDialogue.cellToInspect, upgrade)).append(")]").append("</i>").append("</p>");
        }

        miscDialogueSB.append("</div>");

        return miscDialogueSB.toString();
    }

    public static final DialogueNode ROOM_UPGRADES = new DialogueNode("Room Management", ".", true) {

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.OCCUPANT_MANAGEMENT;
        }

        @Override
        public String getLabel() {
            return cellToInspect.getPlace().getName() + " Management";
        }

        @Override
        public String getContent() {
            UtilText.nodeContentSB.setLength(0);

            UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>" + "<h6 style='color:").append(PresetColour.GENERIC_EXCELLENT.toWebHexString()).append("; text-align:center;'>Overview (Total Values for this Room)</h6>").append("<div class='container-full-width' style='margin-bottom:0;'>").append("<div style='width:20%; float:left; font-weight:bold; margin:0; padding:0;'>").append("Name").append("</div>").append("<div style='width:20%; float:left; font-weight:bold; margin:0; padding:0;'>").append("Occupants").append("</div>").append("<div style='float:left; width:15%; font-weight:bold; margin:0; padding:0;'>").append("<b>Capacity</b>").append("</div>").append("<div style='float:left; width:15%; font-weight:bold; margin:0; padding:0;'>").append("<b style='color:").append(PresetColour.AFFECTION.toWebHexString()).append(";'>Affection</b>").append("</div>").append("<div style='float:left; width:15%; font-weight:bold; margin:0; padding:0;'>").append("<b style='color:").append(PresetColour.OBEDIENCE.toWebHexString()).append(";'>Obedience</b>").append("</div>").append("<div style='float:left; width:15%; font-weight:bold; margin:0; padding:0;'>").append("<b style='color:").append(PresetColour.CURRENCY_GOLD.toWebHexString()).append(";'>Upkeep</b>").append("</div>").append("</div>");


            GenericPlace place = cellToInspect.getPlace();
            float affectionChange = place.getHourlyAffectionChange();
            float obedienceChange = place.getHourlyObedienceChange();

            UtilText.nodeContentSB.append("<div class='container-full-width inner' style='margin-bottom:0;'>" + "<div style='width:20%; float:left; font-weight:bold; margin:0; padding:0;'>" + "<form style='float:left; width:85%; margin:0; padding:0;'><input type='text' id='nameInput' value='").append(UtilText.parseForHTMLDisplay(cellToInspect.getPlace().getName())).append("' style='width:100%; margin:0; padding:0;'></form>").append("<div class='SM-button' id='rename_room_button' style='float:left; width:15%; height:28px; line-height:28px; margin:0; padding:0;'>").append("&#10003;").append("</div>").append("</div>").append("<div style='width:20%; float:left; margin:0; padding:0;'>");

            int i = 0;
            List<NPC> occupants = Main.game.getCharactersTreatingCellAsHome(cellToInspect);
            for (NPC occupant : occupants) {
                if (occupant.isSlave()) {
                    UtilText.nodeContentSB.append("<b style='color:").append(occupant.getFemininity().getColour().toWebHexString()).append(";'>").append(occupant.getName(true)).append("</b>").append(i + 1 == occupants.size() ? "" : "<br/>");
                    i++;
                }
            }
            if (i == 0) {
                UtilText.nodeContentSB.append("<b style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>Empty</b>");
            }


            UtilText.nodeContentSB.append("</div>" + "<div style='float:left; width:15%; margin:0; padding:0;'>").append(i).append("/").append(place.getCapacity()).append("</div>").append("<div style='float:left; width:15%; margin:0; padding:0;'>").append("<span style='color:").append((affectionChange == 0 ? PresetColour.BASE_GREY : (affectionChange > 0 ? PresetColour.GENERIC_GOOD : PresetColour.GENERIC_BAD)).toWebHexString()).append(";'>").append(affectionChange > 0 ? "+" : "").append(decimalFormat.format(affectionChange)).append("</span>/hour").append("</div>").append("<div style='float:left; width:15%; margin:0; padding:0;'>").append("<span style='color:").append((obedienceChange == 0 ? PresetColour.BASE_GREY : (obedienceChange > 0 ? PresetColour.GENERIC_GOOD : PresetColour.GENERIC_BAD)).toWebHexString()).append(";'>").append(obedienceChange > 0 ? "+" : "").append(decimalFormat.format(obedienceChange)).append("</span>/hour").append("</div>").append("<div style='float:left; width:15%; margin:0; padding:0;'>").append(place.getUpkeep() > 0
                    ? UtilText.formatAsMoney(-place.getUpkeep(), "span", PresetColour.GENERIC_BAD)
                    : UtilText.formatAsMoney(-place.getUpkeep(), "span", PresetColour.GENERIC_GOOD)).append("/day").append("</div>").append("</div>").append("</div>");


            // Normal upgrades:
            UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>" + "<h6 style='color:").append(PresetColour.GENERIC_GOOD.toWebHexString()).append("; text-align:center;'>Modifications</h6>").append(getRoomUpgradeHeader());

            List<AbstractPlaceUpgrade> coreUpgrades = new ArrayList<>();
            for (AbstractPlaceUpgrade upgrade : place.getPlaceType().getAvailablePlaceUpgrades(place.getPlaceUpgrades())) {
                if (upgrade.getAvailability(cellToInspect).getKey() || (!upgrade.getAvailability(cellToInspect).getValue().isEmpty())) { // Do not display upgrades that have no explanation as to why they're banned.
                    if (upgrade.isCoreRoomUpgrade()) {
                        coreUpgrades.add(upgrade);
                    } else {
                        UtilText.nodeContentSB.append(getUpgradeEntry(cellToInspect, upgrade));
                        i++;
                    }
                }
            }
            if (i == 0) {
                UtilText.nodeContentSB.append("<div class='container-full-width inner' style='background:").append(PresetColour.BACKGROUND_ALT.toWebHexString()).append(";'>").append("<b style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>No Modifications Available</b>").append("</div>");
            }

            UtilText.nodeContentSB.append("</div>");

            // Core upgrades:
            UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>" + "<h6 style='color:").append(PresetColour.GENERIC_ARCANE.toWebHexString()).append("; text-align:center;'>Core Modifications</h6>").append("<p><i>Purchasing a [style.boldArcane(core modification)] will remove [style.boldBad(all)] other modifications in this room!</i></p>").append(getRoomUpgradeHeader());


//			for (PlaceUpgrade upgrade : place.getPlaceUpgrades()) {
//				if(upgrade.isCoreRoomUpgrade()) {
//					UtilText.nodeContentSB.append(getUpgradeEntry(cellToInspect, upgrade));
//				}
//			}

            i = 0;
            for (AbstractPlaceUpgrade upgrade : coreUpgrades) {
                UtilText.nodeContentSB.append(getUpgradeEntry(cellToInspect, upgrade));
                i++;
            }
            if (i == 0) {
                UtilText.nodeContentSB.append("<div class='container-full-width inner' style='background:").append(PresetColour.BACKGROUND_ALT.toWebHexString()).append(";'>").append("<b style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>No Core Modifications Available</b>").append("</div>");
            }

            UtilText.nodeContentSB.append("</div>"
                    + "<p id='hiddenFieldName' style='display:none;'></p>");

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 5) {
                if (Main.game.getOccupancyUtil().getGeneratedBalance() == 0) {
                    return new Response("Collect: " + UtilText.formatAsMoneyUncoloured(Main.game.getOccupancyUtil().getGeneratedBalance(), "span"), "Your current balance is 0...", null);

                } else if (Main.game.getOccupancyUtil().getGeneratedBalance() > 0) {
                    return new Response("Collect: " + UtilText.formatAsMoney(Main.game.getOccupancyUtil().getGeneratedBalance(), "span"),
                            "Collect the money that you've earned through your slaves' activities.", ROOM_UPGRADES) {
                        @Override
                        public DialogueNode getNextDialogue() {
                            return Main.game.getCurrentDialogueNode();
                        }

                        @Override
                        public void effects() {
                            Main.game.getOccupancyUtil().payOutBalance();
                        }
                    };

                } else {
                    if (Main.game.getPlayer().getMoney() < Math.abs(Main.game.getOccupancyUtil().getGeneratedBalance())) {
                        return new Response("Pay: " + UtilText.formatAsMoneyUncoloured(Math.abs(Main.game.getOccupancyUtil().getGeneratedBalance()), "span"),
                                "You don't have enough money to pay off the accumulated debt from the upkeep of your slaves and rooms.", null);
                    }

                    return new Response("Pay: " + UtilText.formatAsMoney(Math.abs(Main.game.getOccupancyUtil().getGeneratedBalance()), "span", PresetColour.GENERIC_BAD),
                            "Pay off the accumulated debt from the upkeep of your slaves and rooms.", ROOM_UPGRADES) {
                        @Override
                        public DialogueNode getNextDialogue() {
                            return Main.game.getCurrentDialogueNode();
                        }

                        @Override
                        public void effects() {
                            Main.game.getOccupancyUtil().payOutBalance();
                        }
                    };
                }

            } else if (index == 0) {
                return new Response("Back", "Return to the previous screen.", ROOM_UPGRADES) {
                    @Override
                    public DialogueNode getNextDialogue() {
                        return Main.game.getDefaultDialogue(false);
                    }
                };
            } else {
                return null;
            }
        }
    };

    public static String getPurchaseAvailabilityTooltipText(Cell cell, AbstractPlaceUpgrade upgrade) {
        GenericPlace place = cell.getPlace();
        boolean owned = place.getPlaceUpgrades().contains(upgrade);

        purchaseAvailability.setLength(0);

        if (owned) {
            if (Main.game.getPlayer().getMoney() < upgrade.getRemovalCost()) {
                purchaseAvailability.append("<span style='color:").append(PresetColour.GENERIC_BAD.toWebHexString()).append(";'>You cannot afford to remove this modification.</span>");
            }

        } else {
            if (Main.game.getOccupancyUtil().getGeneratedBalance() < 0) {
                purchaseAvailability.append("<b style='color:").append(PresetColour.GENERIC_BAD.toWebHexString()).append(";'>You cannot purchase any modifications while you are in debt!</b>");
            }

            if (Main.game.getPlayer().getMoney() < upgrade.getInstallCost()) {
                purchaseAvailability.append("<span style='color:").append(PresetColour.GENERIC_BAD.toWebHexString()).append(";'>You cannot afford this modification.</span>");
            }

            if (!upgrade.getPrerequisites().isEmpty()) {
                purchaseAvailability.append("You need to purchase the following first:");
                for (AbstractPlaceUpgrade prereq : upgrade.getPrerequisites()) {
                    if (place.getPlaceUpgrades().contains(prereq)) {
                        purchaseAvailability.append("<br/><span style='color:").append(PresetColour.GENERIC_GOOD.toWebHexString()).append(";'>").append(prereq.getName()).append("</span>");
                    } else {
                        purchaseAvailability.append("<br/><span style='color:").append(PresetColour.GENERIC_BAD.toWebHexString()).append(";'>").append(prereq.getName()).append("</span>");
                    }
                }
            }
        }

        String availabilityDescription = upgrade.getAvailability(OccupantManagementDialogue.cellToInspect).getValue();
        if (availabilityDescription != null && !availabilityDescription.isEmpty()) {
            purchaseAvailability.append("<br/><span style='color:").append(PresetColour.GENERIC_BAD.toWebHexString()).append(";'>").append(availabilityDescription).append("</span>");
        }

        return purchaseAvailability.toString();
    }

    private static String getSlaveryHeader() {
        return "<div class='container-full-width' style='margin-bottom:0;'>"
                + "<div style='width:20%; float:left; font-weight:bold; margin:0; padding:0;'>"
                + "Slave"
                + "</div>"
                + "<div style='width:20%; float:left; font-weight:bold; margin:0; padding:0;'>"
                + "Location"
                + "</div>"
                + "<div style='float:left; width:15%; font-weight:bold; margin:0; padding:0;'>"
                + "<b style='color:" + PresetColour.AFFECTION.toWebHexString() + ";'>Affection</b>"
                + "</div>"
                + "<div style='float:left; width:15%; font-weight:bold; margin:0; padding:0;'>"
                + "<b style='color:" + PresetColour.OBEDIENCE.toWebHexString() + ";'>Obedience</b>"
                + "</div>"
                + "<div style='float:left; width:15%; font-weight:bold; margin:0; padding:0;'>"
                + "<b style='color:" + PresetColour.CURRENCY_GOLD.toWebHexString() + ";'>Value</b>"
                + "</div>"
                + "<div style='float:left; width:15%; font-weight:bold; margin:0; padding:0;'>"
                + "Actions"
                + "</div>"
                + "</div>";
    }

    private static String getOccupantHeader() {
        return "<div class='container-full-width' style='margin-bottom:0;'>"
                + "<div style='width:20%; float:left; font-weight:bold; margin:0; padding:0;'>"
                + "Occupant"
                + "</div>"
                + "<div style='width:20%; float:left; font-weight:bold; margin:0; padding:0;'>"
                + "Location"
                + "</div>"
                + "<div style='float:left; width:15%; font-weight:bold; margin:0; padding:0;'>"
                + "<b style='color:" + PresetColour.AFFECTION.toWebHexString() + ";'>Affection</b>"
                + "</div>"
                + "<div style='float:left; width:15%; font-weight:bold; margin:0; padding:0;'>"
                + "<b style='color:" + PresetColour.OBEDIENCE.toWebHexString() + ";'>Obedience</b>"
                + "</div>"
                + "<div style='float:left; width:15%; font-weight:bold; margin:0; padding:0;'>"
                + "<b style='color:" + PresetColour.CURRENCY_GOLD.toWebHexString() + ";'>Value</b>"
                + "</div>"
                + "<div style='float:left; width:15%; font-weight:bold; margin:0; padding:0;'>"
                + "Actions"
                + "</div>"
                + "</div>";
    }

    private static String getSlaveryEntry(boolean slaveOwned, GenericPlace place, NPC slave, AffectionLevel affection, float affectionChange, ObedienceLevel obedience, float obedienceChange, boolean alternateBackground) {
        boolean showWinged = (slave.hasWings() || slave.isArmWings()) && !slave.getFleshSubspecies().isWinged();

        miscDialogueSB.setLength(0);
        miscDialogueSB.append("<div class='container-full-width inner' style='margin-bottom:0;").append(alternateBackground ? "background:" + PresetColour.BACKGROUND_ALT.toWebHexString() + ";'" : "'").append("'>").append("<div style='width:20%; float:left; margin:0; padding:0;'>").append("<b style='color:").append(slave.getFemininity().getColour().toWebHexString()).append(";'>").append(slave.getName(true)).append("</b><br/>").append("<span style='color:").append(slave.getRace().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence((showWinged ? "winged " : "") + (slave.isFeminine() ? slave.getSubspecies().getSingularFemaleName(slave.getBody()) : slave.getSubspecies().getSingularMaleName(slave.getBody())))).append("</span><br/>").append("<span style='color:").append(slave.getFemininity().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(slave.getGender().getName())).append("</span>").append("</div>").append("<div style='width:20%; float:left; margin:0; padding:0;'>").append("<b style='color:").append(slave.getLocationPlace().getColour().toWebHexString()).append(";'>").append(slave.getLocationPlace().getName()).append("</b>").append(",<br/>").append("<span style='color:").append(slave.getWorldLocation().getColour().toWebHexString()).append(";'>").append(slave.getWorldLocation().getName()).append("</span>").append("</div>").append("<div style='float:left; width:15%; margin:0; padding:0;'>").append("<b style='color:").append(affection.getColour().toWebHexString()).append(";'>").append(slave.getAffection(Main.game.getPlayer())).append("</b>").append("<br/><span style='color:").append((affectionChange == 0 ? PresetColour.BASE_GREY : (affectionChange > 0 ? PresetColour.GENERIC_GOOD : PresetColour.GENERIC_BAD)).toWebHexString()).append(";'>").append(affectionChange > 0 ? "+" : "").append(decimalFormat.format(affectionChange)).append("</span>/day").append("<br/>").append("<span style='color:").append(affection.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(affection.getName())).append("</span>").append("</div>").append("<div style='float:left; width:15%; margin:0; padding:0;'>").append("<b style='color:").append(obedience.getColour().toWebHexString()).append(";'>").append(slave.getObedienceValue()).append("</b>").append("<br/><span style='color:").append((obedienceChange == 0 ? PresetColour.BASE_GREY : (obedienceChange > 0 ? PresetColour.GENERIC_GOOD : PresetColour.GENERIC_BAD)).toWebHexString()).append(";'>").append(obedienceChange > 0 ? "+" : "").append(decimalFormat.format(obedienceChange)).append("</span>/day").append("<br/>").append("<span style='color:").append(obedience.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(obedience.getName())).append("</span>").append("</div>").append("<div style='float:left; width:15%; margin:0; padding:0;'>").append(Main.game.getDialogueFlags().getSlaveTrader() != null
                ? (slaveOwned
                ? UtilText.formatAsMoney((int) (slave.getValueAsSlave(true) * Main.game.getDialogueFlags().getSlaveTrader().getBuyModifier()), "b", PresetColour.GENERIC_ARCANE)
                : UtilText.formatAsMoney((int) (slave.getValueAsSlave(true) * Main.game.getDialogueFlags().getSlaveTrader().getSellModifier(null)), "b", PresetColour.GENERIC_ARCANE))
                : UtilText.formatAsMoney(slave.getValueAsSlave(true))).append("<br/>").append("<b>").append(Util.capitaliseSentence(slave.getSlaveJob(Main.game.getHourOfDay()).getName(slave))).append(" (now)</b><br/>").append(UtilText.formatAsMoney(SlaveJob.getFinalDailyIncomeAfterModifiers(slave))).append("/day").append("</div>");

        miscDialogueSB.append("<div style='float:left; width:15%; margin:0 auto; padding:0; display:inline-block; text-align:center;'>");
        if (slaveOwned) {
            miscDialogueSB.append("<div id='").append(slave.getId()).append("' class='square-button big'><div class='square-button-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getSlaveInspect()).append("</div></div>");

            if (Main.game.getDialogueFlags().getSlaveTrader() == null) { // Only show these buttons if you aren't in a trade screen:
                miscDialogueSB.append("<div id='").append(slave.getId()).append("_JOB' class='square-button big'><div class='square-button-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getSlaveJob()).append("</div></div>");

                miscDialogueSB.append("<div id='").append(slave.getId()).append("_PERMISSIONS' class='square-button big'><div class='square-button-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getSlavePermissions()).append("</div></div>");

                miscDialogueSB.append("<div id='").append(slave.getId()).append("_INVENTORY' class='square-button big'><div class='square-button-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getInventoryIcon()).append("</div></div>");

                miscDialogueSB.append("<div ").append((place.getCapacity() <= Main.game.getCharactersTreatingCellAsHome(Main.game.getPlayerCell()).size())
                        || !place.isSlaveCell()
                        || (slave.getLocation().equals(Main.game.getPlayer().getLocation()) && slave.getWorldLocation().equals(Main.game.getPlayer().getWorldLocation()))
                        ? " id='" + slave.getId() + "_TRANSFER_DISABLED' class='square-button big disabled'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getSlaveTransferDisabled() + "</div></div>"
                        : " id='" + slave.getId() + "_TRANSFER' class='square-button big'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getSlaveTransfer() + "</div></div>");
            }

            if (Main.game.getDialogueFlags().getSlaveTrader() == null || !slave.isAbleToBeSold()) {
                miscDialogueSB.append("<div id='").append(slave.getId()).append("_SELL_DISABLED' class='square-button big disabled'><div class='square-button-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getTransactionSellDisabled()).append("</div></div>");
            } else {
                miscDialogueSB.append("<div id='").append(slave.getId()).append("_SELL' class='square-button big'><div class='square-button-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getTransactionSell()).append("</div></div>");
            }

            if (Main.game.getDialogueFlags().getSlaveTrader() == null) { // Only show these buttons if you aren't in a trade screen:
                if (Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.kateIntroduced)) {
                    miscDialogueSB.append("<div id='").append(slave.getId()).append("_COSMETICS' class='square-button big'><div class='square-button-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getSlaveCosmetics()).append("</div></div>");
                } else {
                    miscDialogueSB.append("<div id='").append(slave.getId()).append("_COSMETICS_DISABLED' class='square-button big disabled'><div class='square-button-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getSlaveCosmeticsDisabled()).append("</div></div>");
                }
            }

        } else { // Slave trader's slave:
            miscDialogueSB.append("<div id='").append(slave.getId()).append("_TRADER' class='square-button big'><div class='square-button-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getSlaveInspect()).append("</div></div>");

//			miscDialogueSB.append("<div id='"+slave.getId()+"_TRADER_JOB' class='square-button big disabled'><div class='square-button-content'>"+SVGImages.SVG_IMAGE_PROVIDER.getSlaveJobDisabled()+"</div></div>");
//
//			miscDialogueSB.append("<div id='"+slave.getId()+"_TRADER_PERMISSIONS' class='square-button big disabled'><div class='square-button-content'>"+SVGImages.SVG_IMAGE_PROVIDER.getSlavePermissionsDisabled()+"</div></div>");
//
//			miscDialogueSB.append("<div id='"+slave.getId()+"_TRADER_INVENTORY' class='square-button big disabled'><div class='square-button-content'>"+SVGImages.SVG_IMAGE_PROVIDER.getInventoryIconDisabled()+"</div></div>");
//
//			miscDialogueSB.append("<div id='"+slave.getId()+"_TRADER_TRANSFER' class='square-button big disabled'><div class='square-button-content'>"+SVGImages.SVG_IMAGE_PROVIDER.getSlaveTransferDisabled()+"</div></div>");

            if (Main.game.getPlayer().getMoney() < ((int) (slave.getValueAsSlave(true) * Main.game.getDialogueFlags().getSlaveTrader().getSellModifier(null)))) {
                miscDialogueSB.append("<div id='").append(slave.getId()).append("_BUY_DISABLED' class='square-button big disabled'><div class='square-button-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getTransactionBuyDisabled()).append("</div></div>");
            } else {
                miscDialogueSB.append("<div id='").append(slave.getId()).append("_BUY' class='square-button big'><div class='square-button-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getTransactionBuy()).append("</div></div>");
            }

//			miscDialogueSB.append("<div id='"+slave.getId()+"_TRADER_COSMETICS' class='square-button big disabled'><div class='square-button-content'>"+SVGImages.SVG_IMAGE_PROVIDER.getSlaveCosmeticsDisabled()+"</div></div>");
        }

        miscDialogueSB.append("</div></div>");

        return miscDialogueSB.toString();
    }

    private static String getOccupantEntry(GenericPlace place, NPC occupant, AffectionLevel affection, float affectionChange, ObedienceLevel obedience, float obedienceChange, boolean alternateBackground) {
        boolean showWinged = (occupant.hasWings() || occupant.isArmWings()) && !occupant.getFleshSubspecies().isWinged();

        miscDialogueSB.setLength(0);
        miscDialogueSB.append("<div class='container-full-width inner' style='margin-bottom:0;").append(alternateBackground ? "background:" + PresetColour.BACKGROUND_ALT.toWebHexString() + ";'" : "'").append("'>").append("<div style='width:20%; float:left; margin:0; padding:0;'>").append("<b style='color:").append(occupant.getFemininity().getColour().toWebHexString()).append(";'>").append(occupant.getName(true)).append("</b><br/>").append("<span style='color:").append(occupant.getRace().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence((showWinged ? "winged " : "") + (occupant.isFeminine() ? occupant.getSubspecies().getSingularFemaleName(occupant.getBody()) : occupant.getSubspecies().getSingularMaleName(occupant.getBody())))).append("</span><br/>").append("<span style='color:").append(occupant.getFemininity().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(occupant.getGender().getName())).append("</span>").append("</div>").append("<div style='width:20%; float:left; margin:0; padding:0;'>").append(occupant.getWorldLocation() == WorldType.EMPTY
                ? "<b style='color:" + PresetColour.BASE_GREY.toWebHexString() + ";'>At Work</b>"
                + ",<br/>"
                + "<span style='color:" + WorldType.DOMINION.getColour().toWebHexString() + ";'>" + WorldType.DOMINION.getName() + "</span>"
                : "<b style='color:" + occupant.getLocationPlace().getColour().toWebHexString() + ";'>" + occupant.getLocationPlace().getName() + "</b>"
                + ",<br/>"
                + "<span style='color:" + occupant.getWorldLocation().getColour().toWebHexString() + ";'>" + occupant.getWorldLocation().getName() + "</span>").append("</div>").append("<div style='float:left; width:15%; margin:0; padding:0;'>").append("<b style='color:").append(affection.getColour().toWebHexString()).append(";'>").append(occupant.getAffection(Main.game.getPlayer())).append("</b>").append("<br/><span style='color:").append((affectionChange == 0 ? PresetColour.BASE_GREY : (affectionChange > 0 ? PresetColour.GENERIC_GOOD : PresetColour.GENERIC_BAD)).toWebHexString()).append(";'>").append(affectionChange > 0 ? "+" : "").append(decimalFormat.format(affectionChange)).append("</span>/day").append("<br/>").append("<span style='color:").append(affection.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(affection.getName())).append("</span>").append("</div>").append("<div style='float:left; width:15%; margin:0; padding:0;'>").append("<b style='color:").append(obedience.getColour().toWebHexString()).append(";'>").append(occupant.getObedienceValue()).append("</b>").append("<br/><span style='color:").append((obedienceChange == 0 ? PresetColour.BASE_GREY : (obedienceChange > 0 ? PresetColour.GENERIC_GOOD : PresetColour.GENERIC_BAD)).toWebHexString()).append(";'>").append(obedienceChange > 0 ? "+" : "").append(decimalFormat.format(obedienceChange)).append("</span>/day").append("<br/>").append("<span style='color:").append(obedience.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(obedience.getName())).append("</span>").append("</div>").append("<div style='float:left; width:15%; margin:0; padding:0;'>").append("<b>").append(Util.capitaliseSentence(occupant.getHistory().getName(occupant))).append("</b><br/>").append(UtilText.formatAsMoney(occupant.hasJob() ? PlaceUpgrade.LILAYA_GUEST_ROOM.getUpkeep() : 0)).append("/day").append("</div>").append("<div style='float:left; width:15%; margin:0 auto; padding:0; display:inline-block; text-align:center;'>").append("<div id='").append(occupant.getId()).append("' class='square-button big'><div class='square-button-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getSlaveInspect()).append("</div></div>").append(occupant.hasJob()
                ? "<div id='" + occupant.getId() + "_JOB' class='square-button big disabled'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getSlaveJobDisabled() + "</div></div>"
                : "<div id='" + occupant.getId() + "_JOB' class='square-button big'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getSlaveJob() + "</div></div>").append("<div id='").append(occupant.getId()).append("_PERMISSIONS' class='square-button big disabled'><div class='square-button-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getSlavePermissionsDisabled()).append("</div></div>").append("<div id='").append(occupant.getId()).append("_INVENTORY' class='square-button big'><div class='square-button-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getInventoryIcon()).append("</div></div>").append("<div ").append((place.getCapacity() <= Main.game.getCharactersTreatingCellAsHome(Main.game.getPlayerCell()).size())
                || place.isSlaveCell()
                || occupant.getHomeWorldLocation() == WorldType.DOMINION
                || (occupant.getLocation().equals(Main.game.getPlayer().getLocation()) && occupant.getWorldLocation().equals(Main.game.getPlayer().getWorldLocation()))
                ? " id='" + occupant.getId() + "_TRANSFER_DISABLED' class='square-button big disabled'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getSlaveTransferDisabled() + "</div></div>"
                : " id='" + occupant.getId() + "_TRANSFER' class='square-button big'><div class='square-button-content'>" + SVGImages.SVG_IMAGE_PROVIDER.getSlaveTransfer() + "</div></div>");

        if (Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.kateIntroduced)) {
            miscDialogueSB.append("<div id='").append(occupant.getId()).append("_COSMETICS' class='square-button big'><div class='square-button-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getSlaveCosmetics()).append("</div></div>");
        } else {
            miscDialogueSB.append("<div id='").append(occupant.getId()).append("_COSMETICS_DISABLED' class='square-button big disabled'><div class='square-button-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getSlaveCosmeticsDisabled()).append("</div></div>");
        }

        miscDialogueSB.append("</div></div>");

        return miscDialogueSB.toString();
    }

    public static final DialogueNode SLAVE_LIST = new DialogueNode("Slave & Occupant Management", ".", true) {

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.OCCUPANT_MANAGEMENT;
        }

        @Override
        public String getContent() {
            UtilText.nodeContentSB.setLength(0);

            if (Main.game.getDialogueFlags().getSlaveTrader() != null) {
                // Append for sale first:
                UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>" + "<h6 style='color:").append(PresetColour.CURRENCY_GOLD.toWebHexString()).append("; text-align:center;'>Slaves For Sale</h6>").append(getSlaveryHeader());
                int i = 0;
                List<NPC> npcsPresent = new ArrayList<>(Main.game.getCharactersTreatingCellAsHome(Main.game.getPlayerCell()));

                npcsPresent.removeIf((npc) -> !npc.isSlave());

                for (NPC slave : npcsPresent) {
                    if (!slave.getOwner().isPlayer()) {
                        AffectionLevel affection = AffectionLevel.getAffectionLevelFromValue(slave.getAffection(Main.game.getPlayer()));
                        ObedienceLevel obedience = ObedienceLevel.getObedienceLevelFromValue(slave.getObedienceValue());
                        float affectionChange = slave.getDailyAffectionChange();
                        float obedienceChange = slave.getDailyObedienceChange();
                        GenericPlace place = Main.game.getPlayerCell().getPlace();

                        UtilText.nodeContentSB.append(getSlaveryEntry(false, place, slave, affection, affectionChange, obedience, obedienceChange, i % 2 == 0));
                        i++;
                    }
                }
                if (i == 0) {
                    UtilText.nodeContentSB.append("<div class='container-full-width inner'><h4 style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>No slaves for sale!</h4></div>");
                }
                UtilText.nodeContentSB.append("</div>");

            } else { // Show friendly occupants if not trading slaves:
                UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>" + "<h6 style='color:").append(PresetColour.GENERIC_GOOD.toWebHexString()).append("; text-align:center;'>Friendly Occupants</h6>").append(getOccupantHeader());

                if (Main.game.getPlayer().getFriendlyOccupants().isEmpty()) {
                    UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>" + "<p style='color:").append(PresetColour.BASE_GREY.toWebHexString()).append(";'>You do not have anyone living with you...</p>").append("</div>");

                } else {
                    int i = 0;
                    for (String id : Main.game.getPlayer().getFriendlyOccupants()) {
                        try {
                            NPC occupant = (NPC) Main.game.getNPCById(id);
                            if (occupant.getHomeWorldLocation() != WorldType.DOMINION) {
                                AffectionLevel affection = AffectionLevel.getAffectionLevelFromValue(occupant.getAffection(Main.game.getPlayer()));
                                ObedienceLevel obedience = ObedienceLevel.getObedienceLevelFromValue(occupant.getObedienceValue());
                                float affectionChange = occupant.getDailyAffectionChange();
                                float obedienceChange = occupant.getDailyObedienceChange();
                                GenericPlace place = Main.game.getPlayerCell().getPlace();

                                UtilText.nodeContentSB.append(getOccupantEntry(place, occupant, affection, affectionChange, obedience, obedienceChange, i % 2 == 0));
                                i++;
                            }
                        } catch (Exception e) {
                            Util.logGetNpcByIdError("SLAVE_LIST.getResponse()", id);
                        }
                    }
                }

                UtilText.nodeContentSB.append(
                        "</div>");
            }


            // Your slaves:
            UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>" + "<h6 style='color:").append(PresetColour.GENERIC_GOOD.toWebHexString()).append("; text-align:center;'>Slaves Owned</h6>");

            //UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>");
            //UtilText.nodeContentSB.append(  "<h6 style='color:"+PresetColour.GENERIC_ARCANE.toWebHexString()+"'>Sorting</h6>");
            String buttonStyle = "margin:2px; width:16%;";
            UtilText.nodeContentSB.append("<div class='container-full-width inner' style='text-align:center'>");
            UtilText.nodeContentSB.append("<div style='width:100%;font-weight:bold;margin-top:8px'>Sort By</div>");
            UtilText.nodeContentSB.append("<div id='SORT_SLAVES_BY_NONE' class='normal-button").append((sortingMethod == OccupantSortingMethod.NONE) ? " selected" : "").append("' style='").append(buttonStyle).append("'>None</div>");
            UtilText.nodeContentSB.append("<div id='SORT_SLAVES_BY_NAME' class='normal-button").append((sortingMethod == OccupantSortingMethod.NAME) ? " selected" : "").append("' style='").append(buttonStyle).append("'>Name</div>");
            UtilText.nodeContentSB.append("<div id='SORT_SLAVES_BY_ROOM' class='normal-button").append((sortingMethod == OccupantSortingMethod.ROOM) ? " selected" : "").append("' style='").append(buttonStyle).append("'>Room</div>");
            UtilText.nodeContentSB.append("<div id='SORT_SLAVES_BY_VALUE' class='normal-button").append((sortingMethod == OccupantSortingMethod.VALUE) ? " selected" : "").append("' style='").append(buttonStyle).append("'>Value</div>");
//			UtilText.nodeContentSB.append(    "<div id='SORT_SLAVES_BY_CUSTOM_CATEGORY' class='normal-button"+((sortingMethod==OccupantSortingMethod.CUSTOM_CATEGORY)?" selected":"")+"' style='"+buttonStyle+"'>Category</div>");
//			UtilText.nodeContentSB.append(  "</div>");
//			UtilText.nodeContentSB.append(  "<div class='container-full-width inner' style='text-align:center'>");
//			UtilText.nodeContentSB.append(    "<div style='width:100%;font-weight:bold;margin-top:8px'>Order</div>");
            UtilText.nodeContentSB.append("<div style='width:100%; height:0;'></div>");
            UtilText.nodeContentSB.append("<div id='SORT_SLAVES_ASC' class='normal-button").append((!reverseSortSlaves) ? " selected" : "").append("' style='").append(buttonStyle).append("'>Ascending</div>");
            UtilText.nodeContentSB.append("<div id='SORT_SLAVES_DESC' class='normal-button").append((reverseSortSlaves) ? " selected" : "").append("' style='").append(buttonStyle).append("'>Descending</div>");
            UtilText.nodeContentSB.append("</div>");
            //UtilText.nodeContentSB.append("<div>");

            UtilText.nodeContentSB.append(getSlaveryHeader());

            if (Main.game.getPlayer().getSlavesOwned().isEmpty()) {
                UtilText.nodeContentSB.append("<div class='container-full-width' style='text-align:center;'>" + "<p style='color:").append(PresetColour.BASE_GREY.toWebHexString()).append(";'>You do not own any slaves...</p>").append("</div>");

            } else {
                int i = 0;
                List<NPC> slaves = Main.game.getPlayer().getSlavesOwned().stream()
                        .filter(npcid -> Main.game.isCharacterExisting(npcid))
                        .map(npcid -> {
                            try {
                                return (NPC) Main.game.getNPCById(npcid);
                            } catch (Exception e) {
                                // Should never happen. Just satisfying Java's pickiness.
                                System.getLogger("").log(System.Logger.Level.ERROR, "Main.game.getNPCById(" + npcid + ") returning null 2nd instance in method: SLAVE_LIST.getResponse()");
                                return null;
                            }
                        })
                        .filter(npc -> npc != null)
                        .collect(Collectors.toList());
                Comparator<NPC> ssm = null;
                switch (sortingMethod) {
                    case NAME:
                        ssm = new SlaveNameComparator();
                        break;
                    case ROOM:
                        ssm = new SlaveRoomComparator();
                        break;
                    case VALUE:
                        ssm = new SlaveValueComparator();
                        break;
                    default:
                        break;
                }
                if (ssm != null) {
                    if (reverseSortSlaves) {
                        ssm = Collections.reverseOrder(ssm);
                    }
                    Collections.sort(slaves, ssm);
                }
                for (NPC slave : slaves) {
                    AffectionLevel affection = AffectionLevel.getAffectionLevelFromValue(slave.getAffection(Main.game.getPlayer()));
                    ObedienceLevel obedience = ObedienceLevel.getObedienceLevelFromValue(slave.getObedienceValue());
                    float affectionChange = slave.getDailyAffectionChange();
                    float obedienceChange = slave.getDailyObedienceChange();
                    GenericPlace place = Main.game.getPlayerCell().getPlace();

                    UtilText.nodeContentSB.append(getSlaveryEntry(true, place, slave, affection, affectionChange, obedience, obedienceChange, i % 2 == 0));
                    i++;
                }
            }

            UtilText.nodeContentSB.append(
                    "</div>");

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return CompanionManagement.getManagementResponses(index);
        }
    };


    public static final DialogueNode SLAVE_LIST_MANAGEMENT = new DialogueNode("Slave Management", ".", true) {

        @Override
        public DialogueNodeType getDialogueNodeType() {
            return DialogueNodeType.OCCUPANT_MANAGEMENT;
        }

        @Override
        public String getContent() {
            return SLAVE_LIST.getContent();
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 0) {
                if (Main.game.getCurrentDialogueNode() == SLAVE_LIST_MANAGEMENT) {
                    return new Response("Back", "Exit the slave management screen.", dialogueToExitTo == null ? OCCUPANT_OVERVIEW : dialogueToExitTo);
                } else {
                    return new Response("Back", "Return to the management screen.", SLAVE_LIST_MANAGEMENT);
                }
            }
            if (Main.game.getDialogueFlags().getSlaveTrader() == null) {
                return SLAVE_LIST.getResponse(responseTab, index);
            }
            return null;
        }
    };


}
