package com.lilithslegacy.world.population;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;

/**
 * @author Innoxia
 * @version 0.3.9
 * @since 0.2.12
 */
public class PopulationType {

    public static final AbstractPopulationType PERSON = new AbstractPopulationType("person", "people") {
    };

    public static final AbstractPopulationType FAN = new AbstractPopulationType("fan", "fans") {
    };

    public static final AbstractPopulationType HARPY = new AbstractPopulationType("harpy", "harpies") {
        @Override
        public String getName() {
            if (Main.game.isSillyModeEnabled()) {
                return "birb";
            }
            return "harpy";
        }

        @Override
        public String getNamePlural() {
            if (Main.game.isSillyModeEnabled()) {
                return "birbs";
            }
            return "harpies";
        }
    };

    public static final AbstractPopulationType CROWD = new AbstractPopulationType("crowd", "crowds") {
    };

    public static final AbstractPopulationType PRIVATE_SECURITY_GUARD = new AbstractPopulationType("private security guard", "private security guards") {
    };

    public static final AbstractPopulationType ENFORCER = new AbstractPopulationType("Enforcer", "Enforcers") {
    };

    public static AbstractPopulationType SWORD = new AbstractPopulationType("SWORD Enforcer", "SWORD Enforcers") {
    };

    public static final AbstractPopulationType CENTAUR_CARTS = new AbstractPopulationType("centaur-pulled cart", "centaur-pulled carts") {
    };

    public static final AbstractPopulationType SHOPPER = new AbstractPopulationType("shopper", "shoppers") {
    };

    public static final AbstractPopulationType DINER = new AbstractPopulationType("diner", "diners") {
    };

    public static final AbstractPopulationType VIP = new AbstractPopulationType("VIP", "VIPs") {
    };

    public static final AbstractPopulationType GUARD = new AbstractPopulationType("guard", "guards") {
    };

    public static AbstractPopulationType SECURITY_GUARD = new AbstractPopulationType("security guard", "security guards") {
    };

    public static final AbstractPopulationType MAID = new AbstractPopulationType("maid", "maids") {
    };

    public static final AbstractPopulationType CHEF = new AbstractPopulationType("chef", "chefs") {
    };

    public static final AbstractPopulationType SLAVE = new AbstractPopulationType("slave", "slaves") {
    };

    public static final AbstractPopulationType OFFICE_WORKER = new AbstractPopulationType("office worker", "office workers") {
    };

    public static final AbstractPopulationType TEXTILE_WORKER = new AbstractPopulationType("textile worker", "textile workers") {
    };

    public static final AbstractPopulationType CONSTRUCTION_WORKER = new AbstractPopulationType("construction worker", "construction workers") {
    };

    public static final AbstractPopulationType RECEPTIONIST = new AbstractPopulationType("receptionist", "receptionists") {
    };

    public static final AbstractPopulationType GANG_MEMBER = new AbstractPopulationType("gang member", "gang members") {
    };

    public static AbstractPopulationType STALL_HOLDER = new AbstractPopulationType("stallholder", "stallholders") {
    };

    public static AbstractPopulationType MILKER = new AbstractPopulationType("milker", "milkers") {
    };

    public static AbstractPopulationType CASHIER = new AbstractPopulationType("cashier", "cashiers") {
    };

    public static AbstractPopulationType CLERK = new AbstractPopulationType("clerk", "clerks") {
    };

    public static AbstractPopulationType MASSEUSE = new AbstractPopulationType("masseuse", "masseuses") {
    };

    public static AbstractPopulationType AMAZON = new AbstractPopulationType("Amazon", "Amazons") {
    };

    public static AbstractPopulationType AMAZON_GUARD = new AbstractPopulationType("Amazon guard", "Amazon guards") {
    };

    public static AbstractPopulationType LUNETTE_DAUGTHER = new AbstractPopulationType("Lunette's daughter", "Lunette's daughters") {
    };

    public static AbstractPopulationType COCK_SLEEVE = new AbstractPopulationType("cock-sleeve", "cock-sleeves") {
    };

    public static AbstractPopulationType DOLL = new AbstractPopulationType("doll", "dolls") {
    };


    private static final List<AbstractPopulationType> allPopulationTypes = new ArrayList<>();
    private static final Map<AbstractPopulationType, String> populationToIdMap = new HashMap<>();
    private static final Map<String, AbstractPopulationType> idToPlaceMap = new HashMap<>();


    public static List<AbstractPopulationType> getAllPopulationTypes() {
        return allPopulationTypes;
    }

    public static boolean hasId(String id) {
        return idToPlaceMap.containsKey(id);
    }

    public static AbstractPopulationType getPopulationTypeFromId(String id) {
        id = Util.getClosestStringMatch(id, idToPlaceMap.keySet());
        return idToPlaceMap.get(id);
    }

    public static String getIdFromPopulationType(AbstractPopulationType populationType) {
        return populationToIdMap.get(populationType);
    }

    static {
        // Hard-coded population types (all those up above):

        Field[] fields = PopulationType.class.getFields();

        for (Field f : fields) {
            if (AbstractPopulationType.class.isAssignableFrom(f.getType())) {
                AbstractPopulationType populationType;
                try {
                    populationType = ((AbstractPopulationType) f.get(null));

                    populationToIdMap.put(populationType, f.getName());
                    idToPlaceMap.put(f.getName(), populationType);
                    allPopulationTypes.add(populationType);

                } catch (IllegalArgumentException | IllegalAccessException e) {
                    System.getLogger("").log(System.Logger.Level.ERROR, e.getMessage(), e);
                }
            }
        }
    }
}
