package com.lilithslegacy.game.character.body.types;

import java.nio.file.Path;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lilithslegacy.game.character.body.abstractTypes.AbstractAntennaType;
import com.lilithslegacy.game.character.body.coverings.BodyCoveringType;
import com.lilithslegacy.game.character.race.AbstractRace;
import com.lilithslegacy.game.character.race.Race;
import com.lilithslegacy.utils.Util;

/**
 * @author Innoxia
 * @version 0.3.7
 * @since 0.1.83
 */
public class AntennaType {

    public static final AbstractAntennaType NONE = new AbstractAntennaType(
            BodyCoveringType.ANTENNA,
            Race.NONE,
            "none",
            "antenna",
            "antennae",
            new ArrayList<>(),
            new ArrayList<>(),
            "<br/>[npc.Name] now [npc.has] [style.boldTfGeneric(no antennae)].",
            "") {
    };

    private static final List<AbstractAntennaType> allAntennaTypes;
    private static final Map<AbstractAntennaType, String> antennaToIdMap = new HashMap<>();
    private static final Map<String, AbstractAntennaType> idToAntennaMap = new HashMap<>();
    private static final Map<AbstractRace, List<AbstractAntennaType>> typesMap = new HashMap<>();


    public static AbstractAntennaType getAntennaTypeFromId(String id) {
        id = Util.getClosestStringMatch(id, idToAntennaMap.keySet());
        return idToAntennaMap.get(id);
    }

    public static String getIdFromAntennaType(AbstractAntennaType antennaType) {
        return antennaToIdMap.get(antennaType);
    }

    public static List<AbstractAntennaType> getAllAntennaTypes() {
        return allAntennaTypes;
    }

    public static List<AbstractAntennaType> getAntennaTypes(AbstractRace r) {
        if (typesMap.containsKey(r)) {
            return typesMap.get(r);
        }

        List<AbstractAntennaType> types = new ArrayList<>();
        for (AbstractAntennaType type : AntennaType.getAllAntennaTypes()) {
            if (type.getRace() == r) {
                types.add(type);
            }
        }
        typesMap.put(r, types);
        if (types.isEmpty()) {
            types.add(AntennaType.NONE);
        }
        return types;
    }

    static {
        allAntennaTypes = new ArrayList<>();

        // Modded types:

        Map<String, Map<String, Path>> moddedFilesMap = Util.getExternalModFilesById("/race", "bodyParts", null);
        for (Entry<String, Map<String, Path>> entry : moddedFilesMap.entrySet()) {
            for (Entry<String, Path> innerEntry : entry.getValue().entrySet()) {
                if (Util.getXmlRootElementName(innerEntry.getValue()).equals("antenna")) {
                    try {
                        AbstractAntennaType type = new AbstractAntennaType(innerEntry.getValue(), entry.getKey(), true) {
                        };
                        String id = innerEntry.getKey().replaceAll("bodyParts_", "");
                        allAntennaTypes.add(type);
                        antennaToIdMap.put(type, id);
                        idToAntennaMap.put(id, type);
                    } catch (Exception ex) {
                        ex.printStackTrace(System.err);
                    }
                }
            }
        }

        // External res types:

        Map<String, Map<String, Path>> filesMap = Util.getExternalFilesById("/res/race", "bodyParts", null);
        for (Entry<String, Map<String, Path>> entry : filesMap.entrySet()) {
            for (Entry<String, Path> innerEntry : entry.getValue().entrySet()) {
                if (Util.getXmlRootElementName(innerEntry.getValue()).equals("antenna")) {
                    try {
                        AbstractAntennaType type = new AbstractAntennaType(innerEntry.getValue(), entry.getKey(), false) {
                        };
                        String id = innerEntry.getKey().replaceAll("bodyParts_", "");
                        allAntennaTypes.add(type);
                        antennaToIdMap.put(type, id);
                        idToAntennaMap.put(id, type);
                    } catch (Exception ex) {
                        ex.printStackTrace(System.err);
                    }
                }
            }
        }

        // Add in hard-coded antenna types:

        Field[] fields = AntennaType.class.getFields();

        for (Field f : fields) {
            if (AbstractAntennaType.class.isAssignableFrom(f.getType())) {

                AbstractAntennaType ct;
                try {
                    ct = ((AbstractAntennaType) f.get(null));

                    antennaToIdMap.put(ct, f.getName());
                    idToAntennaMap.put(f.getName(), ct);

                    allAntennaTypes.add(ct);

                } catch (IllegalArgumentException | IllegalAccessException e) {
                    System.getLogger("").log(System.Logger.Level.ERROR, e.getMessage(), e);
                }
            }
        }

        Collections.sort(allAntennaTypes, (t1, t2) ->
                t1.getRace() == Race.NONE
                        ? -1
                        : (t2.getRace() == Race.NONE
                        ? 1
                        : t1.getRace().getName(false).compareTo(t2.getRace().getName(false))));
    }
}
