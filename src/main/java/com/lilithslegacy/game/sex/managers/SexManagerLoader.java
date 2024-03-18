package com.lilithslegacy.game.sex.managers;

import com.lilithslegacy.utils.Util;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Handles the loading and id generation of sex managers from external files.
 *
 * @author Innoxia
 * @version 0.4.1
 * @since 0.4.1
 */
public class SexManagerLoader {

    private static final List<SexManagerExternal> allSexManagers = new ArrayList<>();
    private static final Map<SexManagerExternal, String> sexManagerToIdMap = new HashMap<>();
    private static final Map<String, SexManagerExternal> idToSexManagerMap = new HashMap<>();


    public static List<SexManagerExternal> getAllSexManagers() {
        return allSexManagers;
    }

    public static SexManagerExternal getSexManagerFromId(String id) {
        id = id.trim(); // Just make sure that any parsed ids have been trimmed
        id = Util.getClosestStringMatch(id, idToSexManagerMap.keySet());
        return idToSexManagerMap.get(id);
    }

    public static String getIdFromSexManager(SexManagerExternal sexManager) {
        return sexManagerToIdMap.get(sexManager);
    }

    static {
        // Modded sexManager types:

        Map<String, Map<String, Path>> moddedFilesMap = Util.getExternalModFilesById("/sex/managers");
        for (Entry<String, Map<String, Path>> entry : moddedFilesMap.entrySet()) {
            for (Entry<String, Path> innerEntry : entry.getValue().entrySet()) {
                try {
                    SexManagerExternal sexManager = new SexManagerExternal(innerEntry.getValue(), innerEntry.getKey(), true);
                    String id = innerEntry.getKey();
                    id = id.replaceAll("sex_managers_", "");
                    allSexManagers.add(sexManager);
                    sexManagerToIdMap.put(sexManager, id);
                    idToSexManagerMap.put(id, sexManager);
//					System.out.println("modded sex manager: "+innerEntry.getKey());
                } catch (Exception ex) {
                    System.getLogger("").log(System.Logger.Level.ERROR, "Loading modded sexManager type failed at 'SexManager'. File path: " + innerEntry.getValue());
                    System.getLogger("").log(System.Logger.Level.ERROR, "Actual exception: ");
                    ex.printStackTrace(System.err);
                }
            }
        }

        // External res sexManager types:

        Map<String, Map<String, Path>> filesMap = Util.getExternalFilesById("/res/sex");
        for (Entry<String, Map<String, Path>> entry : filesMap.entrySet()) {
            for (Entry<String, Path> innerEntry : entry.getValue().entrySet()) {
                if (Util.getXmlRootElementName(innerEntry.getValue()).equals("sexManager")) {
                    try {
                        SexManagerExternal sexManager = new SexManagerExternal(innerEntry.getValue(), innerEntry.getKey(), false);
                        String id = innerEntry.getKey();
                        id = id.replaceAll("managers_", "");
                        allSexManagers.add(sexManager);
                        sexManagerToIdMap.put(sexManager, id);
                        idToSexManagerMap.put(id, sexManager);
//						System.out.println("Added sex manager: "+id);

                    } catch (Exception ex) {
                        System.getLogger("").log(System.Logger.Level.ERROR, "Loading sexManager type failed at 'SexManager'. File path: " + innerEntry.getValue());
                        System.getLogger("").log(System.Logger.Level.ERROR, "Actual exception: ");
                        ex.printStackTrace(System.err);
                    }
                }
            }
        }
    }

}
