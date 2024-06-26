package com.lilithslegacy.game.sex.sexActions;

import com.lilithslegacy.game.sex.sexActions.baseActions.*;
import com.lilithslegacy.game.sex.sexActions.baseActionsMisc.*;
import com.lilithslegacy.game.sex.sexActions.baseActionsSelf.*;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Util.Value;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Handles the loading and id generation of SexActions from both internal and external files.
 *
 * @author Innoxia
 * @version 0.4.1
 * @since 0.4.1
 */
public class SexActionManager {

    private static List<SexAction> allSexActions = new ArrayList<>();
    private static Map<SexAction, String> sexActionToIdMap = new HashMap<>();
    private static Map<String, SexAction> idToSexActionMap = new HashMap<>();

    public static List<SexAction> getAllSexActions() {
        return allSexActions;
    }

    public static SexAction getSexActionFromId(String id) {
        id = id.trim(); // Just make sure that any parsed ids have been trimmed
        id = Util.getClosestStringMatch(id, idToSexActionMap.keySet());
        return idToSexActionMap.get(id);
    }

    public static String getIdFromSexAction(SexAction sexAction) {
        return sexActionToIdMap.get(sexAction);
    }

    static {
        // Modded sexActions:

        Map<String, Map<String, Path>> moddedFilesMap = Util.getExternalModFilesById("/sex/actions");
        for (Entry<String, Map<String, Path>> entry : moddedFilesMap.entrySet()) {
            for (Entry<String, Path> innerEntry : entry.getValue().entrySet()) {
                try {
                    SexActionExternal sexAction = new SexActionExternal(innerEntry.getValue(), innerEntry.getKey(), false);
                    String id = innerEntry.getKey();
                    id = id.replaceAll("sex_actions_", "");
                    allSexActions.add(sexAction);
                    sexActionToIdMap.put(sexAction, id);
                    idToSexActionMap.put(id, sexAction);
//                    System.out.println("modded sexAction: "+innerEntry.getKey());
                } catch (Exception ex) {
                    System.getLogger("").log(System.Logger.Level.ERROR, "Loading modded sexAction type failed at 'SexAction'. FSPath path: " + innerEntry.getValue());
                    System.getLogger("").log(System.Logger.Level.ERROR, "Actual exception: ");
                    ex.printStackTrace(System.err);
                }
            }
        }

        // External res SexActions:

        // Ids are generated in the format 'author_folders_fileName'
        // Example: innoxia_meraxis_masturbation_orgasm_panties_cum
        Map<String, Map<String, Path>> filesMap = Util.getExternalFilesById("/res/sex");
        for (Entry<String, Map<String, Path>> entry : filesMap.entrySet()) {
            for (Entry<String, Path> innerEntry : entry.getValue().entrySet()) {
                if (Util.getXmlRootElementName(innerEntry.getValue()).equals("sexAction")) {
                    try {
                        SexActionExternal sexAction = new SexActionExternal(innerEntry.getValue(), innerEntry.getKey(), false);
                        String id = innerEntry.getKey();
                        id = id.replaceAll("actions_", "");
                        allSexActions.add(sexAction);
                        sexActionToIdMap.put(sexAction, id);
                        idToSexActionMap.put(id, sexAction);
//                        System.out.println("res sexAction: "+node.getId());

                    } catch (Exception ex) {
                        System.getLogger("").log(System.Logger.Level.ERROR, "Loading sexAction type failed at 'SexAction'. FSPath path: " + innerEntry.getValue());
                        System.getLogger("").log(System.Logger.Level.ERROR, "Actual exception: ");
                        ex.printStackTrace(System.err);
                    }
                }
            }
        }

        // Add in hard-coded sex actions:

        Map<String, Field[]> sexActionClassIdToFields = Util.newHashMapOfValues(
                new Value<>("PositioningMenu", PositioningMenu.class.getFields()),
                new Value<>("GenericPositioning", GenericPositioning.class.getFields()),

                new Value<>("GenericActions", GenericActions.class.getFields()),
                new Value<>("GenericOrgasms", GenericOrgasms.class.getFields()),
                new Value<>("PlayerTalk", PlayerTalk.class.getFields()),
                new Value<>("PartnerTalk", PartnerTalk.class.getFields()),
                new Value<>("GenericTalk", GenericTalk.class.getFields()),

                // Sadistic actions:
                new Value<>("SadisticActions", SadisticActions.class.getFields()),

                // Gentle actions:
                new Value<>("LovingActions", LovingActions.class.getFields()),

                // Finger actions:
                new Value<>("FingerAnus", FingerAnus.class.getFields()),
                new Value<>("FingerBreasts", FingerBreasts.class.getFields()),
                new Value<>("FingerBreastsCrotch", FingerBreastsCrotch.class.getFields()),
                new Value<>("FingerMouth", FingerMouth.class.getFields()),
                new Value<>("FingerNipple", FingerNipple.class.getFields()),
                new Value<>("FingerNippleCrotch", FingerNippleCrotch.class.getFields()),
                new Value<>("FingerVagina", FingerVagina.class.getFields()),
                new Value<>("FingerClit", FingerClit.class.getFields()),
                new Value<>("FingerPenis", FingerPenis.class.getFields()),
                new Value<>("FingerFinger", FingerFinger.class.getFields()),

                // Oral actions:
                new Value<>("TongueMouth", TongueMouth.class.getFields()),
                new Value<>("TongueAnus", TongueAnus.class.getFields()),
                new Value<>("TongueVagina", TongueVagina.class.getFields()),
                new Value<>("ClitMouth", ClitMouth.class.getFields()),
                new Value<>("TongueMound", TongueMound.class.getFields()),
                new Value<>("TongueBreasts", TongueBreasts.class.getFields()),
                new Value<>("TongueBreastsCrotch", TongueBreastsCrotch.class.getFields()),
                new Value<>("TongueNipple", TongueNipple.class.getFields()),
                new Value<>("TongueNippleCrotch", TongueNippleCrotch.class.getFields()),
                new Value<>("FootMouth", FootMouth.class.getFields()),
                new Value<>("PenisMouth", PenisMouth.class.getFields()),
                new Value<>("TongueArmpits", TongueArmpit.class.getFields()),

                // Tail actions:
                new Value<>("TailAnus", TailAnus.class.getFields()),
                new Value<>("TailVagina", TailVagina.class.getFields()),
                new Value<>("TailMouth", TailMouth.class.getFields()),

                // Tentacle actions:
                new Value<>("TentacleAnus", TentacleAnus.class.getFields()),
                new Value<>("TentacleVagina", TentacleVagina.class.getFields()),
                new Value<>("TentacleMouth", TentacleMouth.class.getFields()),

                // Penis actions:
                new Value<>("PenisAss", PenisAss.class.getFields()),
                new Value<>("PenisAnus", PenisAnus.class.getFields()),
                new Value<>("PenisNipple", PenisNipple.class.getFields()),
                new Value<>("PenisNippleCrotch", PenisNippleCrotch.class.getFields()),
                new Value<>("PenisVagina", PenisVagina.class.getFields()),
                new Value<>("PenisBreasts", PenisBreasts.class.getFields()),
                new Value<>("PenisBreastsCrotch", PenisBreastsCrotch.class.getFields()),
                new Value<>("PenisThighs", PenisThighs.class.getFields()),
                new Value<>("PenisFoot", PenisFoot.class.getFields()),
                new Value<>("PenisFeet", PenisFeet.class.getFields()),
                new Value<>("PenisUrethraVagina", PenisUrethraVagina.class.getFields()),
                new Value<>("PenisUrethraPenis", PenisUrethraPenis.class.getFields()),
                new Value<>("PenisSpinneret", PenisSpinneret.class.getFields()),
                new Value<>("PenisArmpit", PenisArmpit.class.getFields()),

                // Vagina/clit actions:
                new Value<>("ClitClit", ClitClit.class.getFields()),
                new Value<>("ClitVagina", ClitVagina.class.getFields()),
                new Value<>("ClitAnus", ClitAnus.class.getFields()),
                new Value<>("ClitNipple", ClitNipple.class.getFields()),
                new Value<>("ClitNippleCrotch", ClitNippleCrotch.class.getFields()),

                // Self actions
                new Value<>("SelfNoPen", SelfNoPen.class.getFields()),
                new Value<>("SelfFingerAnus", SelfFingerAnus.class.getFields()),
                new Value<>("SelfFingerBreasts", SelfFingerBreasts.class.getFields()),
                new Value<>("SelfFingerCrotchNipple", SelfFingerCrotchNipple.class.getFields()),
                new Value<>("SelfFingerMouth", SelfFingerMouth.class.getFields()),
                new Value<>("SelfFingerNipple", SelfFingerNipple.class.getFields()),
                new Value<>("SelfFingerPenis", SelfFingerPenis.class.getFields()),
                new Value<>("SelfFingerVagina", SelfFingerVagina.class.getFields()),

                new Value<>("SelfPenisAnus", SelfPenisAnus.class.getFields()),
                new Value<>("SelfPenisMouth", SelfPenisMouth.class.getFields()),
                new Value<>("SelfPenisNipple", SelfPenisNipple.class.getFields()),
                new Value<>("SelfPenisVagina", SelfPenisVagina.class.getFields()),

                new Value<>("SelfTailAnus", SelfTailAnus.class.getFields()),
                new Value<>("SelfTailMouth", SelfTailMouth.class.getFields()),
                new Value<>("SelfTailNipple", SelfTailNipple.class.getFields()),
                new Value<>("SelfTailVagina", SelfTailVagina.class.getFields()),

                new Value<>("SelfTongueAnus", SelfTongueAnus.class.getFields()),
                new Value<>("SelfTongueMouth", SelfTongueMouth.class.getFields()),
                new Value<>("SelfTongueNipple", SelfTongueNipple.class.getFields()),
                new Value<>("SelfTongueVagina", SelfTongueVagina.class.getFields()));

        for (Entry<String, Field[]> entry : sexActionClassIdToFields.entrySet()) {
            for (Field f : entry.getValue()) {
                if (SexAction.class.isAssignableFrom(f.getType())) {
                    SexAction sexAction;
                    try {
                        sexAction = ((SexAction) f.get(null));

                        String id = entry.getKey() + "_" + f.getName();

                        sexActionToIdMap.put(sexAction, id);
                        idToSexActionMap.put(id, sexAction);

                        allSexActions.add(sexAction);

                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        System.getLogger("").log(System.Logger.Level.ERROR, e);
                    }
                }
            }
        }
    }

}
