package com.lilithslegacy.game.character.body.types;

import java.nio.file.Path;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lilithslegacy.game.character.body.abstractTypes.AbstractNippleType;
import com.lilithslegacy.game.character.body.coverings.BodyCoveringType;
import com.lilithslegacy.game.character.race.AbstractRace;
import com.lilithslegacy.game.character.race.Race;
import com.lilithslegacy.utils.Util;

/**
 * @author Innoxia
 * @version 0.3.8.2
 * @since 0.1.83
 */
public class NippleType {

    public static final AbstractNippleType HUMAN = new AbstractNippleType(BodyCoveringType.NIPPLES,
            Race.HUMAN,
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues()) {
    };

    public static final AbstractNippleType ANGEL = new AbstractNippleType(BodyCoveringType.NIPPLES,
            Race.ANGEL,
            Util.newArrayListOfValues("perfect", "flawless"),
            Util.newArrayListOfValues("perfect", "flawless"),
            Util.newArrayListOfValues()) {
    };

    public static final AbstractNippleType DEMON = new AbstractNippleType(BodyCoveringType.NIPPLES,
            Race.DEMON,
            Util.newArrayListOfValues("perfect", "flawless"),
            Util.newArrayListOfValues("perfect", "flawless"),
            Util.newArrayListOfValues()) {
    };

    public static final AbstractNippleType DOG_MORPH = new AbstractNippleType(BodyCoveringType.NIPPLES,
            Race.DOG_MORPH,
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues()) {
    };

    public static final AbstractNippleType WOLF_MORPH = new AbstractNippleType(BodyCoveringType.NIPPLES,
            Race.WOLF_MORPH,
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues()) {
    };

    public static final AbstractNippleType FOX_MORPH = new AbstractNippleType(BodyCoveringType.NIPPLES,
            Race.FOX_MORPH,
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues()) {
    };

    public static final AbstractNippleType CAT_MORPH = new AbstractNippleType(BodyCoveringType.NIPPLES,
            Race.CAT_MORPH,
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues()) {
    };

    public static final AbstractNippleType COW_MORPH = new AbstractNippleType(BodyCoveringType.NIPPLES,
            Race.COW_MORPH,
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues()) {
    };

    public static final AbstractNippleType SQUIRREL_MORPH = new AbstractNippleType(BodyCoveringType.NIPPLES,
            Race.SQUIRREL_MORPH,
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues()) {
    };

    public static final AbstractNippleType RAT_MORPH = new AbstractNippleType(BodyCoveringType.NIPPLES,
            Race.RAT_MORPH,
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues()) {
    };

    public static final AbstractNippleType BAT_MORPH = new AbstractNippleType(BodyCoveringType.NIPPLES,
            Race.BAT_MORPH,
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues()) {
    };

    public static final AbstractNippleType RABBIT_MORPH = new AbstractNippleType(BodyCoveringType.NIPPLES,
            Race.RABBIT_MORPH,
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues()) {
    };

    public static final AbstractNippleType ALLIGATOR_MORPH = new AbstractNippleType(BodyCoveringType.NIPPLES,
            Race.ALLIGATOR_MORPH,
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues()) {
    };

    public static final AbstractNippleType HORSE_MORPH = new AbstractNippleType(BodyCoveringType.NIPPLES,
            Race.HORSE_MORPH,
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues()) {
    };

    public static final AbstractNippleType REINDEER_MORPH = new AbstractNippleType(BodyCoveringType.NIPPLES,
            Race.REINDEER_MORPH,
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues()) {
    };

    public static final AbstractNippleType HARPY = new AbstractNippleType(BodyCoveringType.NIPPLES,
            Race.HARPY,
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues(""),
            Util.newArrayListOfValues()) {
    };


//	/**
//	 * Use instead of <i>valueOf()</i>.
//	 */
//	public static NippleType getTypeFromString(String value) {
//		if(value.equals("IMP")) {
//			value = "DEMON_COMMON";
//		}
//		return valueOf(value);
//	}

    private static final List<AbstractNippleType> allNippleTypes;
    private static final Map<AbstractNippleType, String> nippleToIdMap = new HashMap<>();
    private static final Map<String, AbstractNippleType> idToNippleMap = new HashMap<>();
    private static final Map<AbstractRace, List<AbstractNippleType>> typesMap = new HashMap<>();


    public static AbstractNippleType getNippleTypeFromId(String id) {
        if (id.equals("IMP") || id.equals("DEMON_COMMON")) {
            return NippleType.DEMON;
        }

        id = Util.getClosestStringMatch(id, idToNippleMap.keySet());
        return idToNippleMap.get(id);
    }

    public static String getIdFromNippleType(AbstractNippleType nippleType) {
        return nippleToIdMap.get(nippleType);
    }

    public static List<AbstractNippleType> getAllNippleTypes() {
        return allNippleTypes;
    }

    public static List<AbstractNippleType> getNippleTypes(AbstractRace r) {
        if (typesMap.containsKey(r)) {
            return typesMap.get(r);
        }

        List<AbstractNippleType> types = new ArrayList<>();
        for (AbstractNippleType type : NippleType.getAllNippleTypes()) {
            if (type.getRace() == r) {
                types.add(type);
            }
        }
        typesMap.put(r, types);
        return types;
    }

    static {
        allNippleTypes = new ArrayList<>();

        // Modded types:

        Map<String, Map<String, Path>> moddedFilesMap = Util.getExternalModFilesById("/race", "bodyParts", null);
        for (Entry<String, Map<String, Path>> entry : moddedFilesMap.entrySet()) {
            for (Entry<String, Path> innerEntry : entry.getValue().entrySet()) {
                if (Util.getXmlRootElementName(innerEntry.getValue()).equals("nipple")) {
                    try {
                        AbstractNippleType type = new AbstractNippleType(innerEntry.getValue(), entry.getKey(), true) {
                        };
                        String id = innerEntry.getKey().replaceAll("bodyParts_", "");
                        allNippleTypes.add(type);
                        nippleToIdMap.put(type, id);
                        idToNippleMap.put(id, type);
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
                if (Util.getXmlRootElementName(innerEntry.getValue()).equals("nipple")) {
                    try {
                        AbstractNippleType type = new AbstractNippleType(innerEntry.getValue(), entry.getKey(), false) {
                        };
                        String id = innerEntry.getKey().replaceAll("bodyParts_", "");
                        allNippleTypes.add(type);
                        nippleToIdMap.put(type, id);
                        idToNippleMap.put(id, type);
                    } catch (Exception ex) {
                        ex.printStackTrace(System.err);
                    }
                }
            }
        }

        // Add in hard-coded nipple types:

        Field[] fields = NippleType.class.getFields();

        for (Field f : fields) {
            if (AbstractNippleType.class.isAssignableFrom(f.getType())) {

                AbstractNippleType ct;
                try {
                    ct = ((AbstractNippleType) f.get(null));

                    nippleToIdMap.put(ct, f.getName());
                    idToNippleMap.put(f.getName(), ct);

                    allNippleTypes.add(ct);

                } catch (IllegalArgumentException | IllegalAccessException e) {
                    System.getLogger("").log(System.Logger.Level.ERROR, e.getMessage(), e);
                }
            }
        }

        Collections.sort(allNippleTypes, (t1, t2) ->
                t1.getRace() == Race.NONE
                        ? -1
                        : (t2.getRace() == Race.NONE
                        ? 1
                        : t1.getRace().getName(false).compareTo(t2.getRace().getName(false))));
    }
}
