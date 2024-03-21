package com.lilithslegacy.game.inventory.weapon;

import com.lilithslegacy.game.inventory.AbstractSetBonus;
import com.lilithslegacy.utils.Util;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Innoxia
 * @version 0.3.7.4
 * @since 0.1.84
 */
public class WeaponType {

    private static List<AbstractWeaponType> allWeapons = new ArrayList<>();
    public static List<AbstractWeaponType> moddedWeapons = new ArrayList<>();

    public static Map<AbstractSetBonus, List<AbstractWeaponType>> setWeapons = new HashMap<>();

    public static Map<AbstractWeaponType, String> weaponToIdMap = new HashMap<>();
    public static Map<String, AbstractWeaponType> idToWeaponMap = new HashMap<>();


    public static AbstractWeaponType getWeaponTypeFromId(String id) {
        return getWeaponTypeFromId(id, true);
    }

    /**
     * @param closestMatch Pass in true if you want to get whatever WeaponType has the closest match to the provided id, even if it's not exactly the same.
     */
    public static AbstractWeaponType getWeaponTypeFromId(String id, boolean closestMatch) {
//		System.out.print("ID: "+id);

        if (id.equals("RANGED_MUSKET")) {
            id = "innoxia_gun_arcane_musket";
        }
        if (id.equals("MAIN_WESTERN_KKP") || id.equals("innoxia_western_kkp_western_kkp")) {
            id = "innoxia_gun_western_kkp";
        }
        if (id.equals("innoxia_revolver_revolver")) {
            id = "innoxia_gun_revolver";
        }

        if (id.equals("innoxia_pistolCrossbow") || id.equals("innoxia_pistolCrossbow_pistol_crossbow")) {
            id = "innoxia_bow_pistol_crossbow";
        }

        if (id.equals("MAIN_FEATHER_DUSTER")) {
            id = "innoxia_cleaning_feather_duster";
        }
        if (id.equals("MAIN_WITCH_BROOM")) {
            id = "innoxia_cleaning_witch_broom";
        }

        if (id.equals("OFFHAND_BUCKLER") || id.equals("innoxia_buckler_buckler")) {
            id = "innoxia_shield_buckler";
        }
        if (id.equals("innoxia_crudeShield_crude_shield")) {
            id = "innoxia_shield_crude_wooden";
        }

        if (id.equals("OFFHAND_BOW_AND_ARROW")) {
            id = "innoxia_bow_shortbow";
        }

        if (id.equals("MELEE_KNIGHTLY_SWORD")) {
            id = "innoxia_europeanSwords_arming_sword";
        }
        if (id.equals("MELEE_ZWEIHANDER")) {
            id = "innoxia_europeanSwords_zweihander";
        }

        if (id.equals("OFFHAND_CHAOS_RARE")) {
            id = "innoxia_feather_rare";
        }
        if (id.equals("OFFHAND_CHAOS_EPIC")) {
            id = "innoxia_feather_epic";
        }

        if (id.equals("MELEE_CHAOS_RARE")) {
            id = "innoxia_crystal_rare";
        }
        if (id.equals("MELEE_CHAOS_EPIC")) {
            id = "innoxia_crystal_epic";
        }
        if (id.equals("MELEE_CHAOS_LEGENDARY")) {
            id = "innoxia_crystal_legendary";
        }

        if (closestMatch) {
            id = Util.getClosestStringMatch(id, idToWeaponMap.keySet());
        }

        return idToWeaponMap.get(id);
    }

    public static String getIdFromWeaponType(AbstractWeaponType weaponType) {
        return weaponToIdMap.get(weaponType);
    }

    static {

        // Modded weapon types:

        moddedWeapons = new ArrayList<>();

        Map<String, Map<String, Path>> moddedFilesMap = Util.getExternalModFilesById("/items/weapons");
        for (Entry<String, Map<String, Path>> entry : moddedFilesMap.entrySet()) {
            for (Entry<String, Path> innerEntry : entry.getValue().entrySet()) {
                try {
                    String id = innerEntry.getKey();
                    AbstractWeaponType ct = new AbstractWeaponType(innerEntry.getValue(), entry.getKey(), true) {
                    };
                    moddedWeapons.add(ct);
                    weaponToIdMap.put(ct, id);
                    idToWeaponMap.put(id, ct);
                } catch (Exception ex) {
                    System.getLogger("").log(System.Logger.Level.ERROR, "Loading modded weapon failed at 'WeaponType'. FSPath path: " + innerEntry.getValue());
                    System.getLogger("").log(System.Logger.Level.ERROR, "Actual exception: ");
                    ex.printStackTrace(System.err);
                }
            }
        }

        allWeapons.addAll(moddedWeapons);

        // External res weapon types:

        Map<String, Map<String, Path>> filesMap = Util.getExternalFilesById("/res/weapons");
        for (Entry<String, Map<String, Path>> entry : filesMap.entrySet()) {
            for (Entry<String, Path> innerEntry : entry.getValue().entrySet()) {
                try {
                    String id = innerEntry.getKey();
                    AbstractWeaponType ct = new AbstractWeaponType(innerEntry.getValue(), entry.getKey(), false) {
                    };
                    allWeapons.add(ct);
                    weaponToIdMap.put(ct, id);
                    idToWeaponMap.put(id, ct);
//					System.out.println("WT: "+innerEntry.getKey());
                } catch (Exception ex) {
                    System.getLogger("").log(System.Logger.Level.ERROR, "Loading weapon failed at 'WeaponType'. FSPath path: " + innerEntry.getValue());
                    System.getLogger("").log(System.Logger.Level.ERROR, "Actual exception: ");
                    ex.printStackTrace(System.err);
                }
            }
        }

        setWeapons = new HashMap<>();
        for (AbstractWeaponType wt : allWeapons) {
            if (wt.getClothingSet() != null) {
                setWeapons.putIfAbsent(wt.getClothingSet(), new ArrayList<>());
                setWeapons.get(wt.getClothingSet()).add(wt);
            }
        }
    }

    public static List<AbstractWeaponType> getAllWeapons() {
        return allWeapons;
    }

    public static List<AbstractWeaponType> getAllWeaponsInSet(AbstractSetBonus setBonus) {
        return setWeapons.get(setBonus);
    }
}
