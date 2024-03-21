package com.lilithslegacy.game.dialogue;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.dialogue.npcDialogue.QuickTransformations;
import com.lilithslegacy.game.dialogue.npcDialogue.dominion.AlleywayAttackerDialogue;
import com.lilithslegacy.game.dialogue.npcDialogue.dominion.AlleywayDemonDialogue;
import com.lilithslegacy.game.dialogue.npcDialogue.dominion.HarpyAttackerDialogue;
import com.lilithslegacy.game.dialogue.npcDialogue.offspring.GenericOffspringDialogue;
import com.lilithslegacy.game.dialogue.npcDialogue.submission.BatCavernDialogue;
import com.lilithslegacy.game.dialogue.npcDialogue.submission.TunnelAttackDialogue;
import com.lilithslegacy.game.dialogue.places.dominion.harpyNests.HarpyNestBimbo;
import com.lilithslegacy.game.dialogue.places.dominion.harpyNests.HarpyNestDominant;
import com.lilithslegacy.game.dialogue.places.dominion.harpyNests.HarpyNestNympho;
import com.lilithslegacy.game.dialogue.places.dominion.lilayashome.RoomPlayer;
import com.lilithslegacy.game.dialogue.places.dominion.slaverAlley.SlaverAlleyDialogue;
import com.lilithslegacy.game.dialogue.places.submission.LyssiethPalaceDialogue;
import com.lilithslegacy.game.dialogue.places.submission.dicePoker.DicePoker;
import com.lilithslegacy.game.dialogue.utils.BodyChanging;
import com.lilithslegacy.game.dialogue.utils.CosmeticsDialogue;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.fs.FS;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Handles the loading and id generation of DialogueNodes from external files.
 *
 * @author Innoxia
 * @version 0.4
 * @since 0.4
 */
public class DialogueManager {

    private static List<DialogueNode> allDialogues = new ArrayList<>();
    private static Map<DialogueNode, String> dialogueToIdMap = new HashMap<>();
    private static Map<String, DialogueNode> idToDialogueMap = new HashMap<>();

    public static Map<DialogueNode, String> getDialogueToIdMap() {
        return dialogueToIdMap;
    }

    public static Map<String, DialogueNode> getIdToDialogueMap() {
        return idToDialogueMap;
    }

    /**
     * For use in external res files as a way to get a hook to UtilText.parseFromXMLFile
     */
    public String getDialogueFromFile(String pathName, String tag, GameCharacter... specialNPCs) {
        return UtilText.parseFromXMLFile(new ArrayList<>(), FS.newPath("/res"), pathName, tag, Arrays.asList(specialNPCs));
    }

    public static List<DialogueNode> getAllDialogues() {
        return allDialogues;
    }

    public static DialogueNode getDialogueFromId(String id) {
        id = id.trim(); // Just make sure that any parsed ids have been trimmed
        if (id == null || id.equalsIgnoreCase("null") || id.equalsIgnoreCase("empty") || id.isEmpty()) {
            return null;

        } else if (id.equalsIgnoreCase("default")) {
            return Main.game.getDefaultDialogue(false);

        } else if (id.equalsIgnoreCase("defaultWithEncounter")) {
            return Main.game.getDefaultDialogue(true);

        } else if (id.equalsIgnoreCase("defaultForceEncounter")) {
            DialogueNode dn = Main.game.getDefaultDialogue(true, true);
//            System.out.println("Manager returning: "+dn.getId());
            return dn;

        }

        id = Util.getClosestStringMatch(id, idToDialogueMap.keySet());
        return idToDialogueMap.get(id);
    }

    public static String getIdFromDialogue(DialogueNode placeType) {
        return dialogueToIdMap.get(placeType);
    }

    private static void addHardCodedDialogueId(String id, DialogueNode node) {
        allDialogues.add(node);
        dialogueToIdMap.put(node, id);
        idToDialogueMap.put(id, node);
    }

    static {
        // Special hard-coded dialogues which need to be accessed in external files:
        addHardCodedDialogueId("MERAXIS_DEMON_TF_START", LyssiethPalaceDialogue.MERAXIS_DEMON_TF_START);

        addHardCodedDialogueId("ROOM_SET_ALARM", RoomPlayer.ROOM_SET_ALARM);

        addHardCodedDialogueId("BEAUTICIAN_START", CosmeticsDialogue.BEAUTICIAN_START);

        addHardCodedDialogueId("BODY_CHANGING_CORE", BodyChanging.BODY_CHANGING_CORE);
        addHardCodedDialogueId("QUICK_TRANSFORMATIONS_START", QuickTransformations.QUICK_TRANSFORMATIONS_FEMININITY);

        addHardCodedDialogueId("OFFSPRING_ENCOUNTER", GenericOffspringDialogue.OFFSPRING_ENCOUNTER);

        addHardCodedDialogueId("HARPY_NEST_BIMBO_TALK", HarpyNestBimbo.HARPY_NEST_BIMBO_TALK);
        addHardCodedDialogueId("HARPY_NEST_BIMBO_QUEEN", HarpyNestBimbo.HARPY_NEST_BIMBO_QUEEN);
        addHardCodedDialogueId("HARPY_NEST_BIMBO_UGLY", HarpyNestBimbo.HARPY_NEST_BIMBO_UGLY);

        addHardCodedDialogueId("HARPY_NEST_DOMINANT_TALK", HarpyNestDominant.HARPY_NEST_DOMINANT_TALK);
        addHardCodedDialogueId("HARPY_NEST_DOMINANT_QUEEN", HarpyNestDominant.HARPY_NEST_DOMINANT_QUEEN);
        addHardCodedDialogueId("HARPY_NEST_DOMINANT_UGLY", HarpyNestDominant.HARPY_NEST_DOMINANT_UGLY);

        addHardCodedDialogueId("HARPY_NEST_NYMPHO_TALK", HarpyNestNympho.HARPY_NEST_NYMPHO_TALK);
        addHardCodedDialogueId("HARPY_NEST_NYMPHO_QUEEN", HarpyNestNympho.HARPY_NEST_NYMPHO_QUEEN);
        addHardCodedDialogueId("HARPY_NEST_NYMPHO_UGLY", HarpyNestNympho.HARPY_NEST_NYMPHO_UGLY);

        addHardCodedDialogueId("DICE_POKER_CORE", DicePoker.GAMBLING);

        addHardCodedDialogueId("HC_encounter_alleyway_defeat", AlleywayAttackerDialogue.AFTER_COMBAT_DEFEAT);
        addHardCodedDialogueId("HC_encounter_alleyway_victory", AlleywayAttackerDialogue.AFTER_COMBAT_VICTORY);

        addHardCodedDialogueId("HC_encounter_alleyway_demon_defeat", AlleywayDemonDialogue.AFTER_COMBAT_DEFEAT);
        addHardCodedDialogueId("HC_encounter_alleyway_demon_victory", AlleywayDemonDialogue.AFTER_COMBAT_VICTORY);

        addHardCodedDialogueId("HC_encounter_harpy_nest_defeat", HarpyAttackerDialogue.AFTER_COMBAT_DEFEAT);
        addHardCodedDialogueId("HC_encounter_harpy_nest_victory", HarpyAttackerDialogue.AFTER_COMBAT_VICTORY);

        addHardCodedDialogueId("HC_encounter_submission_defeat", TunnelAttackDialogue.AFTER_COMBAT_DEFEAT);
        addHardCodedDialogueId("HC_encounter_submission_victory", TunnelAttackDialogue.AFTER_COMBAT_VICTORY);

        addHardCodedDialogueId("HC_encounter_bat_caverns_defeat", BatCavernDialogue.AFTER_COMBAT_DEFEAT);
        addHardCodedDialogueId("HC_encounter_bat_caverns_victory", BatCavernDialogue.AFTER_COMBAT_VICTORY);

        addHardCodedDialogueId("MARKET_STALL_CAFE_INTERIOR_NO_CONTENT", SlaverAlleyDialogue.MARKET_STALL_CAFE_INTERIOR_NO_CONTENT);

        // Modded dialogue types:

        Map<String, Map<String, Path>> moddedFilesMap = Util.getExternalModFilesById("/dialogue");
        for (Entry<String, Map<String, Path>> entry : moddedFilesMap.entrySet()) {
            for (Entry<String, Path> innerEntry : entry.getValue().entrySet()) {
                try {
                    List<DialogueNode> nodes = DialogueNode.loadDialogueNodesFromFile(innerEntry.getKey(), innerEntry.getValue(), entry.getKey(), true);
//                    System.out.println("size: "+nodes.size());
                    for (DialogueNode node : nodes) {
//                        System.out.println("modded dialogue: "+node.getId());
                        allDialogues.add(node);
                        dialogueToIdMap.put(node, node.getId());
                        idToDialogueMap.put(node.getId(), node);
                    }
                } catch (Exception ex) {
                    System.getLogger("").log(System.Logger.Level.ERROR, "Loading modded dialogue type failed at 'Dialogue'. FSPath path: " + innerEntry.getValue());
                    System.getLogger("").log(System.Logger.Level.ERROR, "Actual exception: ");
                    ex.printStackTrace(System.err);
                }
            }
        }

        // External res dialogue types:

        Map<String, Map<String, Path>> filesMap = Util.getExternalFilesById("/res/dialogue");
        for (Entry<String, Map<String, Path>> entry : filesMap.entrySet()) {
            for (Entry<String, Path> innerEntry : entry.getValue().entrySet()) {
                if (Util.getXmlRootElementName(innerEntry.getValue()).equals("dialogueNodes")) {
                    try {
                        List<DialogueNode> nodes = DialogueNode.loadDialogueNodesFromFile(innerEntry.getKey(), innerEntry.getValue(), entry.getKey(), false);
//                        System.out.println("size: "+nodes.size());
                        for (DialogueNode node : nodes) {
//                            System.out.println("res dialogue: "+node.getId());
                            allDialogues.add(node);
                            dialogueToIdMap.put(node, node.getId());
                            idToDialogueMap.put(node.getId(), node);
                        }
                    } catch (Exception ex) {
                        System.getLogger("").log(System.Logger.Level.ERROR, "Loading dialogue type failed at 'Dialogue'. FSPath path: " + innerEntry.getValue());
                        System.getLogger("").log(System.Logger.Level.ERROR, "Actual exception: ");
                        ex.printStackTrace(System.err);
                    }
                }
            }
        }
    }

}
