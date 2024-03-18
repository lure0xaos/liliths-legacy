package com.lilithslegacy.game.dialogue.utils;

import com.lilithslegacy.game.character.EquipClothingSetting;
import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.character.npc.misc.NPCOffspring;
import com.lilithslegacy.game.character.npc.misc.OffspringSeed;
import com.lilithslegacy.game.character.race.Race;
import com.lilithslegacy.game.character.race.RaceStage;
import com.lilithslegacy.game.character.race.Subspecies;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.encounters.AbstractEncounter;
import com.lilithslegacy.game.dialogue.encounters.Encounter;
import com.lilithslegacy.game.dialogue.responses.Response;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.world.AbstractWorldType;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.AbstractPlaceType;

import java.util.List;

/**
 * @author Innoxia
 * @version 0.4.4
 * @since 0.3.2
 */
public class OffspringMapDialogue {

    public static final DialogueNode OFFSPRING_CHOICE = new DialogueNode("Offspring list", "-", true) {
        List<OffspringSeed> offspringList;

        @Override
        public void applyPreParsingEffects() {
            offspringList = getOffspringList();
        }

        @Override
        public String getContent() {
            UtilText.nodeContentSB.setLength(0);

            boolean noOffspring = offspringList.isEmpty();

            UtilText.nodeContentSB.append("<p>" + "The arcane enchantment in the map causes a list of names of all your unmet offspring to be projected into the air in front of you, with corresponding dots showing their exact location on the physical map itself.").append(noOffspring
                    ? " It doesn't look like you have any unmet offspring to be found right now..."
                    : " Luckily, it looks like one of your offspring is in this very area."
                    + " As you take a look at the key, you see just who it is...").append("</p>").append("<p style='text-align:center;'>");

            if (Main.game.getOffspringNotSpawned(os -> true).isEmpty()) {
                UtilText.nodeContentSB.append("[style.colourDisabled(No offspring available)]");

            } else {
                boolean foundAnyInArea = false;
                List<OffspringSeed> offspringToShow = Main.game.getOffspringNotSpawned(npc -> offspringList.contains(npc));
                if (!offspringToShow.isEmpty()) {
                    foundAnyInArea = true;
                    UtilText.nodeContentSB.append("Offspring [style.colourMinorGood(possibly in this area)]:<br/>");
                }
                for (OffspringSeed os : offspringToShow) {
                    UtilText.nodeContentSB.append("<span style='color:").append(os.getFemininity().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(os.getName())).append("</span>");

                    String unknownMotherName = "Unknown";
                    if (os.getMother() == null && !os.getMotherName().equals("???")) {
                        unknownMotherName = os.getMotherName();
                    }
                    String unknownFatherName = "Unknown";
                    if (os.getFather() == null && !os.getFatherName().equals("???")) {
                        unknownFatherName = os.getFatherName();
                    }

                    UtilText.nodeContentSB.append(" (").append(os.isFeral() ? "<span style='color:" + RaceStage.FERAL.getColour().toWebHexString() + ";'>" + Util.capitaliseSentence(RaceStage.FERAL.getName()) + "</span> " : "").append("<span style='color:").append(os.getSubspecies().getColour(null).toWebHexString()).append(";'>").append(Util.capitaliseSentence(os.getSubspecies().getName(os.getBody()))).append("</span>)").append(" Mother: ").append(os.getMother() == null ? unknownMotherName : (os.getMother().isPlayer() ? "[style.colourExcellent(You)]" : os.getMother().getName(true))).append(" Father: ").append(os.getFather() == null ? unknownFatherName : (os.getFather().isPlayer() ? "[style.colourExcellent(You)]" : os.getFather().getName(true))).append("<br/>");
                }

                offspringToShow = Main.game.getOffspringNotSpawned(npc -> !offspringList.contains(npc));
                if (!offspringToShow.isEmpty()) {
                    if (foundAnyInArea) {
                        UtilText.nodeContentSB.append("<br/>");
                    }
                    UtilText.nodeContentSB.append("Offspring [style.colourMinorBad(not in this area)]:<br/>");
                }
                for (OffspringSeed os : offspringToShow) {
                    UtilText.nodeContentSB.append("[style.colourDisabled(").append(Util.capitaliseSentence(os.getName())).append(")]");

                    String unknownMotherName = "Unknown";
                    if (os.getMother() == null && !os.getMotherName().equals("???")) {
                        unknownMotherName = os.getMotherName();
                    }
                    String unknownFatherName = "Unknown";
                    if (os.getFather() == null && !os.getFatherName().equals("???")) {
                        unknownFatherName = os.getFatherName();
                    }

                    UtilText.nodeContentSB.append(" (<span style='color:").append(os.getSubspecies().getColour(null).toWebHexString()).append(";'>").append(Util.capitaliseSentence(os.getSubspecies().getName(os.getBody()))).append("</span>)").append(" Mother: ").append(os.getMother() == null ? unknownMotherName : (os.getMother().isPlayer() ? "[style.colourExcellent(You)]" : os.getMother().getName(true))).append(" Father: ").append(os.getFather() == null ? unknownFatherName : (os.getFather().isPlayer() ? "[style.colourExcellent(You)]" : os.getFather().getName(true))).append("<br/>");
                }
            }

            UtilText.nodeContentSB.append("</p>");

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            List<OffspringSeed> offspringToShow = Main.game.getOffspringNotSpawned(npc -> offspringList.contains(npc));
            offspringToShow.addAll(Main.game.getOffspringNotSpawned(npc -> !offspringList.contains(npc)));

            if (index == 0) {
                return new Response("Back", "Decide not to look for any of your offspring in this location after all.", Main.game.getDefaultDialogue(false));

            } else if (index - 1 < offspringToShow.size()) {
                OffspringSeed offspring = offspringToShow.get(index - 1);

                if (!offspringList.contains(offspring)) {
                    return new Response(offspring.getName(),
                            offspring.getName() + " cannot be found in this area, due to " + offspring.hisHer() + " subspecies...",
                            null);
                }

                return new Response(offspring.getName(),
                        "With the help of the enchanted map, you know that you'll soon be able to find " + offspring.getName() + " in this area...",
                        offspring.getEncounterDialogue()) {
                    @Override
                    public Colour getHighlightColour() {
                        return offspring.getFemininity().getColour();
                    }

                    @Override
                    public void effects() {
                        NPC npc = new NPCOffspring(offspring);

                        npc.setLocation(Main.game.getPlayer(), true);

                        npc.equipClothing(EquipClothingSetting.getAllClothingSettings());

                        Main.game.setActiveNPC(npc);

                        Main.game.getTextStartStringBuilder().append("<p><i>").append(UtilText.parse(npc,
                                "Consulting the map, you soon find that your [npc.daughter], [npc.name], is living in this particular area of the alleyways."
                                        + " Putting away your map, you start to search for [npc.herHim]...")).append("</i></p>");
                    }
                };

            } else {
                return null;
            }
        }
    };

    private static List<OffspringSeed> getOffspringList() {
        AbstractWorldType worldType = Main.game.getPlayer().getWorldLocation();
        AbstractPlaceType placeType = Main.game.getPlayer().getLocationPlace().getPlaceType();

        AbstractEncounter enc = Main.game.getPlayer().getLocationPlace().getPlaceType().getEncounterType();
        return Main.game.getOffspringNotSpawned(os ->
                os.getSubspecies().isAbleToNaturallySpawnInLocation(worldType, placeType)
                        && (worldType == WorldType.HARPY_NEST
                        ? (os.getHalfDemonSubspecies() == null || os.getHalfDemonSubspecies().getRace() == Race.HARPY)
                        : (os.getHalfDemonSubspecies() == null || os.getHalfDemonSubspecies().getRace() != Race.HARPY))
                        // Allow youko in Elis alleyways:
                        || (enc == Encounter.getEncounterFromId("innoxia_elis_alleyway")
                        && (os.getSubspecies() == Subspecies.FOX_ASCENDANT
                        || os.getSubspecies() == Subspecies.FOX_ASCENDANT_ARCTIC
                        || os.getSubspecies() == Subspecies.FOX_ASCENDANT_FENNEC))
                        // Allow Angels in Dominion:
                        || (enc == Encounter.DOMINION_ALLEY
                        && (os.getSubspecies() == Subspecies.ANGEL))
                        // Allow alligators, slimes, and rats in Dominion canals:
                        || (enc == Encounter.DOMINION_CANAL
                        && (os.getSubspecies() == Subspecies.ALLIGATOR_MORPH
                        || os.getSubspecies() == Subspecies.SLIME
                        || os.getSubspecies() == Subspecies.RAT_MORPH))
        );
    }
}
