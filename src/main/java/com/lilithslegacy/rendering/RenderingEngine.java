package com.lilithslegacy.rendering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.lilithslegacy.game.PropertyValue;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.attributes.ArousalLevel;
import com.lilithslegacy.game.character.attributes.Attribute;
import com.lilithslegacy.game.character.attributes.CorruptionLevel;
import com.lilithslegacy.game.character.attributes.IntelligenceLevel;
import com.lilithslegacy.game.character.attributes.LustLevel;
import com.lilithslegacy.game.character.attributes.PhysiqueLevel;
import com.lilithslegacy.game.character.body.coverings.Covering;
import com.lilithslegacy.game.character.body.valueEnums.Femininity;
import com.lilithslegacy.game.character.effects.AbstractStatusEffect;
import com.lilithslegacy.game.character.effects.StatusEffect;
import com.lilithslegacy.game.character.fetishes.AbstractFetish;
import com.lilithslegacy.game.character.fetishes.Fetish;
import com.lilithslegacy.game.character.markings.Tattoo;
import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.character.npc.misc.Elemental;
import com.lilithslegacy.game.character.persona.Occupation;
import com.lilithslegacy.game.character.race.AbstractSubspecies;
import com.lilithslegacy.game.combat.moves.AbstractCombatMove;
import com.lilithslegacy.game.combat.spells.Spell;
import com.lilithslegacy.game.dialogue.DialogueFlagValue;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.DialogueNodeType;
import com.lilithslegacy.game.dialogue.eventLog.EventLogEntry;
import com.lilithslegacy.game.dialogue.utils.CharactersPresentDialogue;
import com.lilithslegacy.game.dialogue.utils.InventoryDialogue;
import com.lilithslegacy.game.dialogue.utils.InventoryInteraction;
import com.lilithslegacy.game.dialogue.utils.MapTravelType;
import com.lilithslegacy.game.dialogue.utils.PhoneDialogue;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.AbstractCoreItem;
import com.lilithslegacy.game.inventory.CharacterInventory;
import com.lilithslegacy.game.inventory.InventorySlot;
import com.lilithslegacy.game.inventory.Rarity;
import com.lilithslegacy.game.inventory.ShopTransaction;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.inventory.clothing.BodyPartClothingBlock;
import com.lilithslegacy.game.inventory.clothing.ClothingType;
import com.lilithslegacy.game.inventory.item.AbstractItem;
import com.lilithslegacy.game.inventory.weapon.AbstractWeapon;
import com.lilithslegacy.game.inventory.weapon.AbstractWeaponType;
import com.lilithslegacy.game.settings.KeyboardAction;
import com.lilithslegacy.game.sex.SexAreaInterface;
import com.lilithslegacy.game.sex.SexAreaOrifice;
import com.lilithslegacy.game.sex.SexAreaPenetration;
import com.lilithslegacy.game.sex.positions.slots.SexSlotGeneric;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Pathing;
import com.lilithslegacy.utils.SizedStack;
import com.lilithslegacy.utils.Units;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Vector2i;
import com.lilithslegacy.utils.colours.BaseColour;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.world.AbstractWorldType;
import com.lilithslegacy.world.Cell;
import com.lilithslegacy.world.World;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.AbstractGlobalPlaceType;
import com.lilithslegacy.world.places.AbstractPlaceType;
import com.lilithslegacy.world.places.PlaceType;
import com.lilithslegacy.world.population.Population;

/**
 * @author Innoxia
 * @version 0.4
 * @since 0.1.0
 */
public enum RenderingEngine {
    ENGINE;

    public static final int INVENTORY_PAGES = 5;
    public static final int ITEMS_PER_PAGE = 24 + 6;
    public static final Colour[] orgasmColours = new Colour[]{
            PresetColour.AROUSAL_STAGE_ZERO,
            PresetColour.AROUSAL_STAGE_ONE,
            PresetColour.AROUSAL_STAGE_TWO,
            PresetColour.AROUSAL_STAGE_THREE,
            PresetColour.AROUSAL_STAGE_FOUR,
            PresetColour.AROUSAL_STAGE_FIVE,
            PresetColour.GENERIC_ARCANE};
    public static final InventorySlot[] mainInventorySlots = {
            InventorySlot.EYES, InventorySlot.HEAD, InventorySlot.HAIR, InventorySlot.HORNS,
            InventorySlot.MOUTH, InventorySlot.TORSO_OVER, InventorySlot.NECK, InventorySlot.WINGS,
            InventorySlot.WRIST, InventorySlot.TORSO_UNDER, InventorySlot.CHEST, InventorySlot.NIPPLE,
            InventorySlot.HAND, InventorySlot.HIPS, InventorySlot.STOMACH, InventorySlot.FINGER,
            InventorySlot.ANKLE, InventorySlot.LEG, InventorySlot.GROIN, InventorySlot.TAIL};
    public static final InventorySlot[] secondaryInventorySlots = {InventorySlot.SOCK, InventorySlot.FOOT, InventorySlot.ANUS, InventorySlot.PENIS, InventorySlot.VAGINA};
    private static boolean zoomedIn = true;
    private static boolean renderedDisabledMap = false;
    private static int pageLeft = 0;
    private static int pageRight = 0;
    private static final InventorySlot[] piercingSlots = {
            InventorySlot.PIERCING_EAR, InventorySlot.PIERCING_NOSE,
            InventorySlot.PIERCING_LIP, InventorySlot.PIERCING_TONGUE,
            InventorySlot.PIERCING_NIPPLE, InventorySlot.PIERCING_STOMACH,
            InventorySlot.PIERCING_PENIS, InventorySlot.PIERCING_VAGINA};
    private static final StringBuilder pageSB = new StringBuilder();
    private static final StringBuilder itemSB = new StringBuilder();
    private boolean renderingTattoosLeft = false;
    private boolean renderingTattoosRight = false;
    private final StringBuilder inventorySB = new StringBuilder();
    private final StringBuilder equippedPanelSB = new StringBuilder();
    // DecimalFormat decimalFormatter = new DecimalFormat("#,###");
    private final StringBuilder uiAttributeSB = new StringBuilder();
    private DialogueNode renderedDialogueNode = null;
    private final StringBuilder mapSB = new StringBuilder();

    RenderingEngine() {
    }

    private static String getEmptyWeaponDiv(GameCharacter charactersInventoryToRender, boolean disabled, InventorySlot slot, String weaponStyle) {
        BodyPartClothingBlock block = slot.getBodyPartClothingBlock(charactersInventoryToRender);

        if (!disabled && block != null) {
            return "<div class='inventory-item-slot " + (block.getRace() != null ? "disabled-light" : "disabled") + "' style='" + weaponStyle + "'>"
                    + (block.getRace() != null
                    ? "<div class='raceBlockIcon'>" + AbstractSubspecies.getMainSubspeciesOfRace(block.getRace()).getSVGStringDesaturated(charactersInventoryToRender, PresetColour.BASE_BLACK) + "</div>"
                    : "")
                    + "<div class='overlay-inventory' id='" + slot + "Slot' style='cursor:default;'></div>"
                    + "</div>";

        } else {
            return "<div class='inventory-item-slot" + (disabled ? " disabled" : "") + "' " + (disabled ? "id='" + slot + "Slot'" : "") + " style='" + weaponStyle + "'>"
                    + (!disabled
                    ? "<div class='inventory-icon-content' style='width:75%; margin:12.5%;'>" + SVGImages.SVG_IMAGE_PROVIDER.getFist() + "</div>"
                    + "<div class='overlay-inventory' id='" + slot + "Slot' style='cursor:default;'></div>"
                    : "")
                    + "</div>";
        }
    }

    private static String lipstickMarkingsString(GameCharacter character, InventorySlot slot) {
        SizedStack<Covering> lipstickMarkings = character.getLipstickMarkingsInSlot(slot);
        StringBuilder sb = new StringBuilder();
        if (lipstickMarkings != null) {
            int lipstickMarkingCount = lipstickMarkings.size();
            for (int i = 0; i < lipstickMarkingCount; i++) {
                sb.append("<div class='lipstickIcon' style='width:calc(40 - ").append(lipstickMarkingCount * 10).append(")%; height:calc(40 - ").append(lipstickMarkingCount * 10).append(")%; right:").append(i * 10).append("%;'>");
                sb.append(SVGImages.SVG_IMAGE_PROVIDER.getLipstickIcon(lipstickMarkings.get(i)));
                sb.append("</div>");
            }
        }
        return sb.toString();
    }

    private static String getInventoryIconsForPage(int page, GameCharacter charactersInventoryToRender, String idModifier) {
        int uniqueItemCount = 0;
        pageSB.setLength(0);

        if (charactersInventoryToRender == null) {
            if (page == 5) { // Quest:
                for (Entry<AbstractWeapon, Integer> entry : Main.game.getPlayerCell().getInventory().getAllWeaponsInInventory().entrySet()) {
                    if (entry.getKey().getRarity() == Rarity.QUEST) {
                        pageSB.append(getInventoryItemDiv(Main.game.getPlayerCell().getInventory(), entry.getKey(), entry.getValue(), idModifier + "WEAPON_"));
                        uniqueItemCount++;
                    }
                }

                for (Entry<AbstractClothing, Integer> entry : Main.game.getPlayerCell().getInventory().getAllClothingInInventory().entrySet()) {
                    if (entry.getKey().getRarity() == Rarity.QUEST) {
                        pageSB.append(getInventoryItemDiv(Main.game.getPlayerCell().getInventory(), entry.getKey(), entry.getValue(), idModifier + "CLOTHING_"));
                        uniqueItemCount++;
                    }
                }

                for (Entry<AbstractItem, Integer> entry : Main.game.getPlayerCell().getInventory().getAllItemsInInventory().entrySet()) {
                    if (entry.getKey().getRarity() == Rarity.QUEST) {
                        pageSB.append(getInventoryItemDiv(Main.game.getPlayerCell().getInventory(), entry.getKey(), entry.getValue(), idModifier + "ITEM_"));
                        uniqueItemCount++;
                    }
                }

            } else {
                for (Entry<AbstractWeapon, Integer> entry : Main.game.getPlayerCell().getInventory().getAllWeaponsInInventory().entrySet()) {
                    if (entry.getKey().getRarity() != Rarity.QUEST) {
                        if (uniqueItemCount >= page * ITEMS_PER_PAGE && uniqueItemCount < (page + 1) * ITEMS_PER_PAGE) {
                            pageSB.append(getInventoryItemDiv(Main.game.getPlayerCell().getInventory(), entry.getKey(), entry.getValue(), idModifier + "WEAPON_"));
                        }
                        uniqueItemCount++;
                    }
                }

                for (Entry<AbstractClothing, Integer> entry : Main.game.getPlayerCell().getInventory().getAllClothingInInventory().entrySet()) {
                    if (entry.getKey().getRarity() != Rarity.QUEST) {
                        if (uniqueItemCount >= page * ITEMS_PER_PAGE && uniqueItemCount < (page + 1) * ITEMS_PER_PAGE) {
                            pageSB.append(getInventoryItemDiv(Main.game.getPlayerCell().getInventory(), entry.getKey(), entry.getValue(), idModifier + "CLOTHING_"));
                        }
                        uniqueItemCount++;
                    }
                }

                for (Entry<AbstractItem, Integer> entry : Main.game.getPlayerCell().getInventory().getAllItemsInInventory().entrySet()) {
                    if (entry.getKey().getRarity() != Rarity.QUEST) {
                        if (uniqueItemCount >= page * ITEMS_PER_PAGE && uniqueItemCount < (page + 1) * ITEMS_PER_PAGE) {
                            pageSB.append(getInventoryItemDiv(Main.game.getPlayerCell().getInventory(), entry.getKey(), entry.getValue(), idModifier + "ITEM_"));
                        }
                        uniqueItemCount++;
                    }
                }
            }

        } else {
            if (page == 5) { // Quest:
                for (Entry<AbstractWeapon, Integer> entry : charactersInventoryToRender.getAllWeaponsInInventory().entrySet()) {
                    if (entry.getKey().getRarity() == Rarity.QUEST) {
//						if(uniqueItemCount < ITEMS_PER_PAGE) {
                        pageSB.append(getInventoryItemDiv(charactersInventoryToRender, entry.getKey(), entry.getValue(), idModifier + "WEAPON_"));
//						}
                        uniqueItemCount++;
                    }
                }

                for (Entry<AbstractClothing, Integer> entry : charactersInventoryToRender.getAllClothingInInventory().entrySet()) {
                    if (entry.getKey().getRarity() == Rarity.QUEST) {
//						if(uniqueItemCount < ITEMS_PER_PAGE) {
                        pageSB.append(getInventoryItemDiv(charactersInventoryToRender, entry.getKey(), entry.getValue(), idModifier + "CLOTHING_"));
//						}
                        uniqueItemCount++;
                    }
                }

                for (Entry<AbstractItem, Integer> entry : charactersInventoryToRender.getAllItemsInInventory().entrySet()) {
                    if (entry.getKey().getRarity() == Rarity.QUEST) {
//						if(uniqueItemCount < ITEMS_PER_PAGE) {
                        pageSB.append(getInventoryItemDiv(charactersInventoryToRender, entry.getKey(), entry.getValue(), idModifier + "ITEM_"));
//						}
                        uniqueItemCount++;
                    }
                }

            } else {
                for (Entry<AbstractWeapon, Integer> entry : charactersInventoryToRender.getAllWeaponsInInventory().entrySet()) {
                    if (entry.getKey().getRarity() != Rarity.QUEST) {
                        if (uniqueItemCount >= page * ITEMS_PER_PAGE && uniqueItemCount < (page + 1) * ITEMS_PER_PAGE) {
                            pageSB.append(getInventoryItemDiv(charactersInventoryToRender, entry.getKey(), entry.getValue(), idModifier + "WEAPON_"));
                        }
                        uniqueItemCount++;
                    }
                }

                for (Entry<AbstractClothing, Integer> entry : charactersInventoryToRender.getAllClothingInInventory().entrySet()) {
                    if (entry.getKey().getRarity() != Rarity.QUEST) {
                        if (uniqueItemCount >= page * ITEMS_PER_PAGE && uniqueItemCount < (page + 1) * ITEMS_PER_PAGE) {
                            pageSB.append(getInventoryItemDiv(charactersInventoryToRender, entry.getKey(), entry.getValue(), idModifier + "CLOTHING_"));
                        }
                        uniqueItemCount++;
                    }
                }

                for (Entry<AbstractItem, Integer> entry : charactersInventoryToRender.getAllItemsInInventory().entrySet()) {
                    if (entry.getKey().getRarity() != Rarity.QUEST) {
                        if (uniqueItemCount >= page * ITEMS_PER_PAGE && uniqueItemCount < (page + 1) * ITEMS_PER_PAGE) {
                            pageSB.append(getInventoryItemDiv(charactersInventoryToRender, entry.getKey(), entry.getValue(), idModifier + "ITEM_"));
                        }
                        uniqueItemCount++;
                    }
                }
            }
        }

        // Fill space:
        for (int i = uniqueItemCount - (page == 5 ? 0 : page) * ITEMS_PER_PAGE; i < ITEMS_PER_PAGE; i++) {
            pageSB.append("<div class='inventory-item-slot'></div>");
        }

        return pageSB.toString();
    }

    private static String getInventoryItemDiv(GameCharacter charactersInventoryToRender, AbstractCoreItem item, int count, String idPrefix) {
        itemSB.setLength(0);
        boolean known = true;
        if (item instanceof AbstractClothing) {
            known = ((AbstractClothing) item).isEnchantmentKnown();
        }
        itemSB.append("<div class='inventory-item-slot' style='background-color:").append((known ? item.getRarity().getBackgroundColour() : PresetColour.RARITY_UNKNOWN_BACKGROUND).toWebHexString()).append(";'>").append("<div class='inventory-icon-content'>").append(item.getSVGString()).append("</div>");

        if (item instanceof AbstractClothing && ((AbstractClothing) item).isDirty()) {
            itemSB.append("<div class='cummedIcon'>").append(SVGImages.SVG_IMAGE_PROVIDER.getDirtyIcon()).append("</div>");
        }
        String overlay = "<div class='overlay";
        String last_layer = "";

        if (charactersInventoryToRender != null && InventoryDialogue.getNPCInventoryInteraction() == InventoryInteraction.TRADING) {
            NPC npc = InventoryDialogue.getInventoryNPC();
            if (charactersInventoryToRender.isPlayer()) {
                boolean isAbleToBeSold = true;

                if (item instanceof AbstractItem) {
                    AbstractItem abItem = (AbstractItem) item;
                    isAbleToBeSold = abItem.getItemType().isAbleToBeSold();
                } else if (item instanceof AbstractWeapon) {
                    AbstractWeapon abItem = (AbstractWeapon) item;
                    isAbleToBeSold = abItem.getWeaponType().isAbleToBeSold();
                } else if (item instanceof AbstractClothing) {
                    AbstractClothing abItem = (AbstractClothing) item;
                    isAbleToBeSold = abItem.getClothingType().isAbleToBeSold();
                }

                if (npc.willBuy(item) && isAbleToBeSold) {
                    last_layer = getItemPriceDiv(item.getPrice(npc.getBuyModifier()));
                } else {
                    overlay += " dark";
                }
            } else {
                last_layer = getItemPriceDiv(item.getPrice(npc.getSellModifier(item)));
            }

        } else {
            boolean nonPlayerInv = charactersInventoryToRender != null && !charactersInventoryToRender.isPlayer();
            boolean isTraderInv = nonPlayerInv && ((NPC) charactersInventoryToRender).isTrader();

            if (item instanceof AbstractItem) {
                AbstractItem abItem = (AbstractItem) item;
                if ((nonPlayerInv && InventoryDialogue.getNPCInventoryInteraction() != InventoryInteraction.FULL_MANAGEMENT)
                        || (Main.game.isInSex() && (isTraderInv || !abItem.isAbleToBeUsedInSex() || !Main.sex.isItemUseAvailable()))
                        || (Main.game.isInCombat() && ((!abItem.isAbleToBeUsedInCombatAllies() && !abItem.isAbleToBeUsedInCombatEnemies()) || Main.game.getPlayer().isStunned() || Main.combat.isCombatantDefeated(Main.game.getPlayer())))) {
                    overlay += " disabled";
                }

            } else if (item instanceof AbstractClothing) {
                AbstractClothing clothing = (AbstractClothing) item;
                if (Main.game.isInCombat()
                        || (Main.game.isInSex()
                        && (isTraderInv
                        || !clothing.isAbleToBeEquippedDuringSexInAnySlot().getKey()
                        || (!Main.sex.getInitialSexManager().isAbleToEquipSexClothing(Main.game.getPlayer(), Main.game.getPlayer(), clothing)
                        && (InventoryDialogue.getInventoryNPC() == null || !Main.sex.getInitialSexManager().isAbleToEquipSexClothing(Main.game.getPlayer(), InventoryDialogue.getInventoryNPC(), clothing)))))) {
                    overlay += " disabled";
                }

            } else if (item instanceof AbstractWeapon
                    && (Main.game.isInCombat() || Main.game.isInSex())) {
                overlay += " disabled";
            }
        }
        itemSB.append(overlay).append("' id='").append(idPrefix).append(item.hashCode()).append("'>").append(getItemCountDiv(count)).append(last_layer).append("</div></div>");
        return itemSB.toString();
    }

    private static String getInventoryItemDiv(CharacterInventory inventory, AbstractCoreItem item, int count, String idPrefix) {
        itemSB.setLength(0);
        boolean known = true;
        if (item instanceof AbstractClothing) {
            known = ((AbstractClothing) item).isEnchantmentKnown();
        }
        itemSB.append("<div class='inventory-item-slot' style='background-color:").append((known ? item.getRarity().getBackgroundColour() : PresetColour.RARITY_UNKNOWN_BACKGROUND).toWebHexString()).append(";'>").append("<div class='inventory-icon-content'>").append(item.getSVGString()).append("</div>");

        if (item instanceof AbstractClothing && ((AbstractClothing) item).isDirty()) {
            itemSB.append("<div class='cummedIcon'>").append(SVGImages.SVG_IMAGE_PROVIDER.getDirtyIcon()).append("</div>");
        }
        String overlay = "<div class='overlay";
        String last_layer = "";

        if (item instanceof AbstractItem) {
            AbstractItem abItem = (AbstractItem) item;
            if ((Main.game.isInSex() && !abItem.isAbleToBeUsedInSex())
                    || (Main.game.isInCombat() && ((!abItem.isAbleToBeUsedInCombatAllies() && !abItem.isAbleToBeUsedInCombatEnemies()) || Main.game.getPlayer().isStunned() || Main.combat.isCombatantDefeated(Main.game.getPlayer())))) {
                overlay += " disabled";
            }

        } else if (item instanceof AbstractClothing) {
            AbstractClothing clothing = (AbstractClothing) item;
            if (Main.game.isInCombat() || (Main.game.isInSex() && !clothing.isAbleToBeEquippedDuringSexInAnySlot().getKey())) {
                overlay += " disabled";
            }

        } else if (item instanceof AbstractWeapon && (Main.game.isInCombat() || Main.game.isInSex())) {
            overlay += " disabled";
        }

        itemSB.append(overlay).append("' id='").append(idPrefix).append(item.hashCode()).append("'>").append(getItemCountDiv(count)).append(last_layer).append("</div></div>");
        return itemSB.toString();
    }

    private static void appendDivsForGiftsToInventory(StringBuilder stringBuilder, Map<AbstractCoreItem, Integer> map, String idPrefix) {
        for (Entry<? extends AbstractCoreItem, Integer> entry : map.entrySet()) {
            boolean known = true;
            if (entry.getKey() instanceof AbstractClothing) {
                known = ((AbstractClothing) entry.getKey()).isEnchantmentKnown();
            }
            stringBuilder.append("<div class='inventory-item-slot unequipped' style='background-color:").append((known ? entry.getKey().getRarity().getBackgroundColour() : PresetColour.RARITY_UNKNOWN_BACKGROUND).toWebHexString()).append(";'>").append("<div class='inventory-icon-content'>").append(entry.getKey().getSVGString()).append("</div>").append("<div class='overlay' id='").append(idPrefix).append(entry.getKey().hashCode()).append("'>").append(getItemCountDiv(true, entry.getValue())).append("</div>").append("</div>");
        }
    }

    private static String getItemCountDiv(int amount) {
        return getItemCountDiv(false, amount);
    }

    private static String getItemCountDiv(boolean countSingles, int amount) {
        if (amount > 1 || countSingles) {
            return "<div class='item-count'>x" + amount + "</div>";
        }
        return "";
    }

    private static String getThrownWeaponCountDiv(AbstractWeaponType weaponType, int amount) {
        if (!weaponType.isOneShot()) {
            return "";
        }
        return "<div class='item-count' " + (amount == 0 ? "style='opacity:0.5;'" : "") + ">+" + amount + "</div>";
    }

    private static String getItemPriceDiv(int price) {
        return "<div class='item-price'>"
                + UtilText.formatAsItemPrice(price)
                + "</div>";
    }

    private static String getBuybackItemPanel(ShopTransaction itemBuyback, String id) {
        return "<div class='inventory-item-slot' style='background-color:" + itemBuyback.getAbstractItemSold().getRarity().getBackgroundColour().toWebHexString() + ";'>"
                + "<div class='inventory-icon-content'>" + itemBuyback.getAbstractItemSold().getSVGString() + "</div>"
                + "<div class='overlay' id='" + id + "'>"
                + getItemCountDiv(itemBuyback.getCount()) + getItemPriceDiv(itemBuyback.getPrice())
                + "</div>"
                + "</div>";
    }

    public static GameCharacter getCharacterToRender() {
        if (Main.game.getCurrentDialogueNode().getDialogueNodeType() == DialogueNodeType.CHARACTERS_PRESENT || Main.game.getCurrentDialogueNode() == PhoneDialogue.CONTACTS_CHARACTER) {
            return CharactersPresentDialogue.characterViewed;
        }

        if (Main.game.isInSex()) {
            if (Main.sex.isMasturbation()) {
                return null;
            }
            return Main.sex.getTargetedPartner(Main.game.getPlayer());
        }

        if (Main.game.isInCombat()) {
            return Main.combat.getTargetedCombatant();
        }

        if (InventoryDialogue.getInventoryNPC() != null && Main.game.getCurrentDialogueNode().getDialogueNodeType() == DialogueNodeType.INVENTORY) {
            return InventoryDialogue.getInventoryNPC();
        }

        if (Main.game.getDialogueFlags().getManagementCompanion() != null) {
            return Main.game.getDialogueFlags().getManagementCompanion();
        }

        return Main.game.getActiveNPC();
    }

    public static String getEntryBackgroundColour(boolean alternative) {
        if (alternative) {
            return PresetColour.BACKGROUND_ALT.toWebHexString();
        }
        return PresetColour.BACKGROUND.toWebHexString();
    }

    private static String getDangerousBackground(AbstractPlaceType placeType) {
        if (placeType instanceof AbstractGlobalPlaceType) {
            return "background:" + placeType.getBackgroundColour().toWebHexString() + ";";
        }

        String backgroundColour1;
        String backgroundColour2;
        try {
            backgroundColour1 = placeType.getBackgroundColour().getShades()[0];
            backgroundColour2 = placeType.getBackgroundColour().toWebHexString();
        } catch (Exception e) {
            backgroundColour1 = PresetColour.TEXT_GREY_DARK.toWebHexString();
            backgroundColour2 = placeType.getBackgroundColour().toWebHexString();
        }

        return "background: repeating-linear-gradient("
                + "135deg,"
                + backgroundColour1 + ","
                + backgroundColour1 + " 20%,"

                + backgroundColour2 + " 20%,"
                + backgroundColour2 + " 40%"
                + ");";
    }

    public static boolean isZoomedIn() {
        return zoomedIn;
    }

    public static void setZoomedIn(boolean zoomedIn) {
        RenderingEngine.zoomedIn = zoomedIn;
    }

    public static boolean isRenderedDisabledMap() {
        return renderedDisabledMap;
    }

    public static void setRenderedDisabledMap(boolean renderedDisabledMap) {
        RenderingEngine.renderedDisabledMap = renderedDisabledMap;
    }

    private static String getClassRarityIdentifier(Rarity rarity) {
        return (rarity == Rarity.COMMON ? " common" : "")
                + (rarity == Rarity.UNCOMMON ? " uncommon" : "")
                + (rarity == Rarity.RARE ? " rare" : "")
                + (rarity == Rarity.EPIC ? " epic" : "")
                + (rarity == Rarity.LEGENDARY ? " legendary" : "")
                + (rarity == Rarity.QUEST ? " quest" : "")
                + (rarity == Rarity.JINXED ? " jinxed" : "");
    }

    private static String getAttributeBar(String SVGImage, Colour barColour, float attributeValue, float attributeMaximum, String id) {
        float width;
        if (attributeMaximum == 0) {
            width = 0;
        } else {
            width = (attributeValue / attributeMaximum) * 100;
        }
        return "<div class='full-width-container' style='margin:8 0 0 0; margin:0; padding:0;'>"
                + "<div class='icon small'>"
                + "<div class='icon-content'>"
                + SVGImage
                + "</div>"
                + "</div>"
                + "<div class='barBackgroundAtt'>"
                + "<div style='width:" + width + "%; height:5vw; background:" + barColour.toWebHexString() + "; float:left; border-radius: 2px;'></div>"
                + "</div>"
                + "<p style='text-align:center; margin:0; padding:0; line-height:12vw;'>"
                + (int) Math.ceil(attributeValue)
                + "</p>"
                + "<div class='overlay' id='" + id + "'></div>"
                + "</div>";
    }

    private static String getAttributeBarHalf(String SVGImage, Colour barColour, float attributeValue, float attributeMaximum, String id) {
        float width;
        if (attributeMaximum == 0) {
            width = 0;
        } else {
            width = (attributeValue / attributeMaximum) * 100;
        }
        return "<div class='half-width-container' style='margin:8 0 0 0; margin:0; padding:0;'>"
                + "<div class='icon small' style='width:20%;'>"
                + "<div class='icon-content'>"
                + SVGImage
                + "</div>"
                + "</div>"
                + "<div class='barBackgroundAtt' style='width:50%;'>"
                + "<div style='width:" + width + "%; height:5vw; background:" + barColour.toWebHexString() + "; float:left; border-radius: 2px;'></div>"
                + "</div>"
                + "<p style='text-align:center; margin:0; padding:0; line-height:12vw; color:" + barColour.toWebHexString() + ";'>"
                + (int) Math.ceil(attributeValue)
                + "</p>"
                + "<div class='overlay' id='" + id + "'></div>"
                + "</div>";
    }

    private static String getAttributeBarThird(String SVGImage, Colour barColour, float attributeValue, float attributeMaximum, String id) {
        float width;
        if (attributeMaximum == 0) {
            width = 0;
        } else {
            width = (attributeValue / attributeMaximum) * 100;
        }
        return "<div class='half-width-container' style='width:33.3%; margin:8 0 0 0; margin:0; padding:0;'>"
                + "<div class='icon small' style='width:30%;'>"
                + "<div class='icon-content'>"
                + SVGImage
                + "</div>"
                + "</div>"
                + "<div class='barBackgroundAtt' style='width:30%;'>"
                + "<div style='width:" + width + "%; height:5vw; background:" + barColour.toWebHexString() + "; float:left; border-radius: 2px;'></div>"
                + "</div>"
                + "<p style='text-align:center; margin:0; padding:0; line-height:12vw; color:" + barColour.toWebHexString() + ";'>"
                + (int) Math.ceil(attributeValue)
                + "</p>"
                + "<div class='overlay' id='" + id + "'></div>"
                + "</div>";
    }

    private static String getCharacterPanelDiv(boolean compact, String idPrefix, GameCharacter character) {
        StringBuilder panelSB = new StringBuilder();

        panelSB.append("<div class='attribute-container' style='").append(Main.game.isInCombat()
                ? (Main.combat.getTargetedCombatant().equals(character)
                ? "border:2px solid " + PresetColour.GENERIC_COMBAT.toWebHexString() + ";"
                : (Main.combat.getTargetedAlliedCombatant().equals(character)
                ? "border:2px solid " + PresetColour.GENERIC_MINOR_GOOD.toWebHexString() + ";"
                : "border:1px solid " + PresetColour.TEXT_GREY_DARK.toWebHexString() + ";"))
                : "border:1px solid " + PresetColour.TEXT_GREY_DARK.toWebHexString() + ";").append("'>").append(character.getHistory() == Occupation.TOURIST
                ? "<div class='full-width-container' style='position:absolute; width:12%; padding:0; right:2%; opacity:0.75;'>"
                + SVGImages.SVG_IMAGE_PROVIDER.getFlagUs()
                + "</div>"
                : "").append("<div class='full-width-container' style='margin-bottom:4px;'>").append("<div class='icon' style='width:12%'>").append("<div class='icon-content'>").append(character.isRaceConcealed() ? SVGImages.SVG_IMAGE_PROVIDER.getRaceUnknown() : character.getMapIcon()).append("</div>").append("<div class='overlay' id='").append(idPrefix).append(Attribute.EXPERIENCE.getName()).append("' style='cursor:pointer;'></div>").append("</div>").append("<div class='full-width-container' style='text-align:center;padding:0;margin:0;float:left;width:86%;'>").append("<div class='full-width-container' style='text-align:center;padding:0;margin:0;'>").append("<b style='color:").append(Femininity.valueOf(character.getFemininityValue()).getColour().toWebHexString()).append(";'>").append(character.getName(true).isEmpty()
                ? Util.capitaliseSentence(character.isFeminine() ? character.getSubspecies().getSingularFemaleName(character.getBody()) : character.getSubspecies().getSingularMaleName(character.getBody()))
                : (character.isPlayer()
                ? character.getName(true)
                : UtilText.parse(character, "[npc.Name]"))).append("</b>").append(" - Level ").append(character.getLevel()).append("</div>").append("<div class='full-width-container' style='text-align:center;padding:0;margin:0;background:").append(PresetColour.BACKGROUND_ALT.toWebHexString()).append("; border-radius: 2px;'>").append(character.getLevel() != GameCharacter.LEVEL_CAP
                ? "<div style='mix-blend-mode: difference; width:" + (character.getExperience() / (character.getLevel() * 10f)) * 100 + "%; height:2vw; background:" + PresetColour.CLOTHING_BLUE_LIGHT.toWebHexString()
                + "; float:left; border-radius: 2px;'></div>"
                : "<div style=' mix-blend-mode: difference; width:100%; height:2vw; background:" + PresetColour.GENERIC_EXCELLENT.toWebHexString() + "; float:left; border-radius: 2px;'></div>").append("</div>").append("<div class='overlay' id='").append(idPrefix).append("ATTRIBUTES' style='cursor:pointer;'></div>").append("</div>").append(character.isPlayer()
                ? "<div class='full-width-container' style='text-align:center;'>"
                + "<div class='half-width-container' style='padding:0 8px;'>" + UtilText.formatAsMoney(character.getMoney(), "b") + "</div>"
                + "<div class='half-width-container' style='padding:0 8px;'>" + UtilText.formatAsEssences(character.getEssenceCount(), "b", true) + "</div>"
                + "</div>"
                : "").append("</div>");

        if (!Main.game.isInCombat()) {
            panelSB.append("<hr style='border:1px solid ").append(PresetColour.TEXT_GREY_DARK.toWebHexString()).append("; margin: 2px 0;'/>");

            panelSB.append(getAttributeBarThird(PhysiqueLevel.getPhysiqueLevelFromValue(character.getAttributeValue(Attribute.MAJOR_PHYSIQUE)).getRelatedStatusEffect().getSVGString(character),
                    Attribute.MAJOR_PHYSIQUE.getColour(),
                    character.getAttributeValue(Attribute.MAJOR_PHYSIQUE),
                    100,
                    idPrefix + Attribute.MAJOR_PHYSIQUE.getName())).append(getAttributeBarThird(IntelligenceLevel.getIntelligenceLevelFromValue(character.getAttributeValue(Attribute.MAJOR_ARCANE)).getRelatedStatusEffect().getSVGString(character),
                    Attribute.MAJOR_ARCANE.getColour(),
                    character.getAttributeValue(Attribute.MAJOR_ARCANE),
                    100,
                    idPrefix + Attribute.MAJOR_ARCANE.getName())).append(getAttributeBarThird(CorruptionLevel.getCorruptionLevelFromValue(character.getAttributeValue(Attribute.MAJOR_CORRUPTION)).getRelatedStatusEffect().getSVGString(character),
                    Attribute.MAJOR_CORRUPTION.getColour(),
                    character.getAttributeValue(Attribute.MAJOR_CORRUPTION),
                    100,
                    idPrefix + Attribute.MAJOR_CORRUPTION.getName()));
        }

        panelSB.append("<hr style='border:1px solid ").append(PresetColour.TEXT_GREY_DARK.toWebHexString()).append("; margin: 2px 0;'/>");

        if (compact) {
            panelSB.append(getAttributeBarThird(Attribute.HEALTH_MAXIMUM.getSVGString(),
                    PresetColour.ATTRIBUTE_HEALTH,
                    character.getHealth(),
                    character.getAttributeValue(Attribute.HEALTH_MAXIMUM),
                    idPrefix + Attribute.HEALTH_MAXIMUM.getName())).append(getAttributeBarThird(Attribute.MANA_MAXIMUM.getSVGString(),
                    PresetColour.ATTRIBUTE_MANA,
                    character.getMana(),
                    character.getAttributeValue(Attribute.MANA_MAXIMUM),
                    idPrefix + Attribute.MANA_MAXIMUM.getName())).append(getAttributeBarThird(LustLevel.getLustLevelFromValue(character.getLust()).getRelatedStatusEffect().getSVGString(character),
                    PresetColour.ATTRIBUTE_LUST,
                    character.getLust(),
                    100,
                    idPrefix + Attribute.LUST.getName()));

        } else {
            panelSB.append(getAttributeBar(Attribute.HEALTH_MAXIMUM.getSVGString(),
                    PresetColour.ATTRIBUTE_HEALTH,
                    character.getHealth(),
                    character.getAttributeValue(Attribute.HEALTH_MAXIMUM),
                    idPrefix + Attribute.HEALTH_MAXIMUM.getName())).append(getAttributeBar(Attribute.MANA_MAXIMUM.getSVGString(),
                    PresetColour.ATTRIBUTE_MANA,
                    character.getMana(),
                    character.getAttributeValue(Attribute.MANA_MAXIMUM),
                    idPrefix + Attribute.MANA_MAXIMUM.getName())).append(getAttributeBar(LustLevel.getLustLevelFromValue(character.getLust()).getRelatedStatusEffect().getSVGString(character),
                    PresetColour.ATTRIBUTE_LUST,
                    character.getLust(),
                    100,
                    idPrefix + Attribute.LUST.getName()));
        }


        // Status effects:
        panelSB.append("<hr style='border:1px solid ").append(PresetColour.TEXT_GREY_DARK.toWebHexString()).append("; margin: 2px 0;'/>");
        panelSB.append("<div class='attribute-container' style='padding:0; overflow-y:auto;'>");

        if (!Main.game.isInCombat()) {
            // Infinite duration:
            for (AbstractStatusEffect se : character.getStatusEffects()) {
                if (!se.isCombatEffect() && character.getStatusEffectDuration(se) == -1 && se.renderInEffectsPanel())
                    panelSB.append("<div class='icon effect'>" + "<div class='icon-content'>").append(se.getSVGString(character)).append("<div class='overlay' id='SE_").append(idPrefix).append(se).append("'></div>").append("</div>").append("</div>");
            }
            // Timed:
            for (AbstractStatusEffect se : character.getStatusEffects()) {
                if (!se.isCombatEffect() && character.getStatusEffectDuration(se) != -1 && se.renderInEffectsPanel()) {
                    int timerHeight = (int) ((character.getStatusEffectDuration(se) / (60 * 60 * 6f)) * 100);

                    Colour timerColour = PresetColour.STATUS_EFFECT_TIME_HIGH;

                    if (timerHeight > 100) {
                        timerHeight = 100;
                        timerColour = PresetColour.STATUS_EFFECT_TIME_OVERFLOW;
                    } else if (timerHeight < 15) {
                        timerColour = PresetColour.STATUS_EFFECT_TIME_LOW;
                    } else if (timerHeight < 50) {
                        timerColour = PresetColour.STATUS_EFFECT_TIME_MEDIUM;
                    }

                    panelSB.append("<div class='icon effect'>" + "<div class='timer-background' style='width:").append(timerHeight).append("%; background:").append(timerColour.toWebHexString()).append(";'></div>").append("<div class='icon-content'>").append(se.getSVGString(character)).append("<div class='overlay' id='SE_").append(idPrefix).append(se).append("'></div>").append("</div>").append("</div>");
                }
            }

            if (!character.isPlayer()) {
                for (AbstractFetish f : character.getFetishes(true)) {
                    panelSB.append("<div class='icon effect'>" + "<div class='icon-content'>").append(f.getSVGString(character)).append("<div class='overlay' id='FETISH_").append(idPrefix).append(Fetish.getIdFromFetish(f)).append("'></div>").append("</div>").append("</div>");
                }
            }

        } else {
            // Infinite duration:
            for (AbstractStatusEffect se : character.getStatusEffects()) {
                if (character.getStatusEffectDuration(se) == -1 && se.renderInEffectsPanel())
                    panelSB.append("<div class='icon effect' ").append(se.isCombatEffect() ? "style='border:1px solid " + se.getBeneficialStatus().getColour().toWebHexString() + ";" : "").append("'>").append("<div class='icon-content'>").append(se.getSVGString(character)).append("<div class='overlay' id='SE_").append(idPrefix).append(se).append("'></div>").append("</div>").append("</div>");
            }
            // Timed:
            for (AbstractStatusEffect se : character.getStatusEffects()) {
                if (character.getStatusEffectDuration(se) != -1 && se.renderInEffectsPanel() && se.isCombatEffect()) {
                    int timerHeight = (int) ((character.getStatusEffectDuration(se) / (60 * 60 * 6f)) * 100);

                    Colour timerColour = PresetColour.STATUS_EFFECT_TIME_HIGH;

                    if (timerHeight > 100) {
                        timerHeight = 100;
                        timerColour = PresetColour.STATUS_EFFECT_TIME_OVERFLOW;
                    } else if (timerHeight < 15) {
                        timerColour = PresetColour.STATUS_EFFECT_TIME_LOW;
                    } else if (timerHeight < 50) {
                        timerColour = PresetColour.STATUS_EFFECT_TIME_MEDIUM;
                    }

                    panelSB.append("<div class='icon effect' style='border:1px solid ").append(se.getBeneficialStatus().getColour().toWebHexString()).append(";'>").append("<div class='timer-background' style='width:").append(timerHeight).append("%; background:").append(timerColour.toWebHexString()).append(";'></div>").append("<div class='icon-content'>").append(se.getSVGString(character)).append("<div class='overlay' id='SE_").append(idPrefix).append(se).append("'></div>").append("</div>").append("</div>");
                }
            }

            for (AbstractCombatMove combatMove : character.getAvailableMoves()) {
                int cooldown = character.getMoveCooldown(combatMove.getIdentifier());
                if (cooldown > 0) {
                    int timerHeight = (int) ((Math.min(5, cooldown) / 5f) * 100);

                    Colour timerColour = PresetColour.STATUS_EFFECT_TIME_MEDIUM;

                    if (timerHeight >= 100) {
                        timerHeight = 100;
                        timerColour = PresetColour.STATUS_EFFECT_TIME_LOW;
                    } else if (cooldown < 2) {
                        timerColour = PresetColour.STATUS_EFFECT_TIME_OVERFLOW;
                    } else if (cooldown < 4) {
                        timerColour = PresetColour.STATUS_EFFECT_TIME_HIGH;
                    }

                    panelSB.append("<div class='icon effect' style='border:2px solid ").append(PresetColour.GENERIC_TERRIBLE.toWebHexString()).append("'>").append("<div class='timer-background' style='width:").append(timerHeight).append("%; background:").append(timerColour.toWebHexString()).append(";'></div>").append("<div class='icon-content'>").append(combatMove.getSVGString()).append("</div>"
//									+ "<div style='width:100%;height:100%;position:absolute;right:0; top:0;'>"+SVGImages.SVG_IMAGE_PROVIDER.getStopwatch()+"</div>"
                    ).append("<div class='overlay' id='CM_").append(idPrefix).append(combatMove.getIdentifier()).append("'></div>").append("</div>");

                }
            }
        }

        panelSB.append("</div>");

        if (character.isElementalSummoned() && !character.isElementalActive()) {
            Elemental elemental = character.getElemental();
            panelSB.append("<hr style='border:1px solid ").append(PresetColour.TEXT_GREY_DARK.toWebHexString()).append("; margin:2px 0;'/>");
            panelSB.append("<div class='attribute-container' style='padding:0; overflow-y:auto; margin-bottom:4px;'>"
//					"<div class='full-width-container' style='margin-bottom:4px;'>"
                    + "<div class='icon' style='width:12%'>" + "<div class='icon-content'>").append(elemental.getMapIcon()).append("</div>").append("<div class='overlay' id='").append(idPrefix).append("ELEMENTAL_").append(Attribute.EXPERIENCE.getName()).append("' style='cursor:pointer;'></div>").append("</div>").append("<div class='full-width-container' style='text-align:center;padding:0;margin:0;float:left;width:86%;'>").append("<div class='full-width-container' style='text-align:center;padding:0;margin:0;'>").append("<b style='color:").append(Femininity.valueOf(elemental.getFemininityValue()).getColour().toWebHexString()).append(";'>").append(elemental.getName(true).isEmpty()
                    ? Util.capitaliseSentence(elemental.isFeminine() ? elemental.getSubspecies().getSingularFemaleName(elemental.getBody()) : elemental.getSubspecies().getSingularMaleName(elemental.getBody()))
                    : (elemental.isPlayer()
                    ? elemental.getName(true)
                    : UtilText.parse(elemental, "[npc.Name]"))).append("</b>"
//										+ " - Level "+ elemental.getLevel()
            ).append("</div>").append("<div class='full-width-container' style='text-align:center;padding:0;margin:0;background:").append(PresetColour.BACKGROUND_ALT.toWebHexString()).append("; border-radius: 2px;'>").append(elemental.getLevel() != GameCharacter.LEVEL_CAP
                    ? "<div style='mix-blend-mode: difference; width:" + (elemental.getExperience() / (elemental.getLevel() * 10f)) * 100 + "%; height:2vw; background:" + PresetColour.CLOTHING_BLUE_LIGHT.toWebHexString()
                    + "; float:left; border-radius: 2px;'></div>"
                    : "<div style=' mix-blend-mode: difference; width:100%; height:2vw; background:" + PresetColour.GENERIC_EXCELLENT.toWebHexString() + "; float:left; border-radius: 2px;'></div>").append("</div>").append("<div class='overlay' id='").append(idPrefix).append("ELEMENTAL_ATTRIBUTES' style='cursor:pointer;'></div>").append("</div>").append("</div>");

            // Effects:
//			panelSB.append("<hr style='border:1px solid "+PresetColour.BASE_PITCH_BLACK.toWebHexString()+"; margin:2px 0;'/>");

            panelSB.append("<div class='attribute-container' style='padding:0; margin-bottom:0; overflow-y:auto;'>");

            float iconWidth = 100 / 9f - 2;  // usual is 12.28 (7 per row)
            // Infinite duration:
            for (AbstractStatusEffect se : elemental.getStatusEffects()) {
                if (!se.isCombatEffect() && elemental.getStatusEffectDuration(se) == -1 && se.renderInEffectsPanel())
                    panelSB.append("<div class='icon effect' style='width:").append(iconWidth).append("%;'>").append("<div class='icon-content'>").append(se.getSVGString(elemental)).append("<div class='overlay' id='SE_ELEMENTAL_").append(idPrefix).append(se).append("'></div>").append("</div>").append("</div>");
            }
            // Timed:
            for (AbstractStatusEffect se : elemental.getStatusEffects()) {
                if (!se.isCombatEffect() && elemental.getStatusEffectDuration(se) != -1 && se.renderInEffectsPanel()) {
                    int timerHeight = (int) ((elemental.getStatusEffectDuration(se) / (60 * 60 * 6f)) * 100);

                    Colour timerColour = PresetColour.STATUS_EFFECT_TIME_HIGH;

                    if (timerHeight > 100) {
                        timerHeight = 100;
                        timerColour = PresetColour.STATUS_EFFECT_TIME_OVERFLOW;
                    } else if (timerHeight < 15) {
                        timerColour = PresetColour.STATUS_EFFECT_TIME_LOW;
                    } else if (timerHeight < 50) {
                        timerColour = PresetColour.STATUS_EFFECT_TIME_MEDIUM;
                    }

                    panelSB.append("<div class='icon effect' style='width:").append(iconWidth).append("%;'>").append("<div class='timer-background' style='width:").append(timerHeight).append("%; background:").append(timerColour.toWebHexString()).append(";'></div>").append("<div class='icon-content'>").append(se.getSVGString(elemental)).append("<div class='overlay' id='SE_ELEMENTAL_").append(idPrefix).append(se).append("'></div>").append("</div>").append("</div>");
                }
            }

            panelSB.append("</div>");
        }

        panelSB.append("</div>");

        return panelSB.toString();
    }

    private static boolean isLimitedSpectatorPanel(GameCharacter character) {
        return Main.sex.getSexPositionSlot(character) == SexSlotGeneric.MISC_WATCHING && !character.isPlayer();
    }

    private static String getCharacterPanelSexDiv(boolean compact, String idPrefix, GameCharacter character) {
        StringBuilder panelSB = new StringBuilder();

        panelSB.append("<div class='attribute-container' style='overflow: auto;").append(Main.sex.getTargetedPartner(Main.game.getPlayer()) != null && Main.sex.getTargetedPartner(Main.game.getPlayer()).equals(character)
                ? "border:2px solid " + PresetColour.GENERIC_ARCANE.toWebHexString() + ";"
                : "border:1px solid " + PresetColour.TEXT_GREY_DARK.toWebHexString() + ";").append("'>").append("<div class='full-width-container' style='margin-bottom:4px;'>").append("<div class='icon' style='width:12%'>").append("<div class='icon-content'>").append(character.isRaceConcealed() ? SVGImages.SVG_IMAGE_PROVIDER.getRaceUnknown() : character.getMapIcon()).append("</div>").append("<div class='overlay' id='").append(idPrefix).append(Attribute.EXPERIENCE.getName()).append("' style='cursor:pointer;'></div>").append("</div>").append("<div class='full-width-container' style='text-align:center;padding:0;margin:0;float:left;width:86%;'>").append("<div class='full-width-container' style='text-align:center;padding:0;margin:0;'>").append("<b style='color:").append(Femininity.valueOf(character.getFemininityValue()).getColour().toWebHexString()).append(";'>").append(character.getName(true).isEmpty()
                ? Util.capitaliseSentence(character.isFeminine() ? character.getSubspecies().getSingularFemaleName(character.getBody()) : character.getSubspecies().getSingularMaleName(character.getBody()))
                : (character.isPlayer()
                ? character.getName(true)
                : UtilText.parse(character, "[npc.Name]"))).append("</b>").append(isLimitedSpectatorPanel(character)
                ? ""
                : " - <span style='color:" + Main.sex.getSexPace(character).getColour().toWebHexString() + ";'>" + Util.capitaliseSentence(Main.sex.getSexPace(character).getName()) + "</span>").append("</div>").append("<div class='full-width-container' style='text-align:center;padding:0;margin:0;background:").append(PresetColour.BACKGROUND_ALT.toWebHexString()).append("; border-radius: 2px;'>").append(character.getLevel() != GameCharacter.LEVEL_CAP
                ? "<div style='mix-blend-mode: difference; width:" + (character.getExperience() / (character.getLevel() * 10f)) * 100 + "%; height:2vw; background:" + PresetColour.CLOTHING_BLUE_LIGHT.toWebHexString()
                + "; float:left; border-radius: 2px;'></div>"
                : "<div style=' mix-blend-mode: difference; width:100%; height:2vw; background:" + PresetColour.GENERIC_EXCELLENT.toWebHexString() + "; float:left; border-radius: 2px;'></div>").append("</div>").append("<div class='overlay' id='").append(idPrefix).append("ATTRIBUTES' style='cursor:pointer;'></div>").append("</div>").append(isLimitedSpectatorPanel(character)
                ? ""
                : "<div class='full-width-container' style='text-align:center;'>"
                + Util.capitaliseSentence(Main.sex.getSexPositionSlot(character).getName(character))
                + "</div>").append("</div>");

        if (isLimitedSpectatorPanel(character)) {
            panelSB.append("<p style='padding:0;margin:auto 0;text-align:center;color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>").append(Util.capitaliseSentence(Main.sex.getSexPositionSlot(character).getName(character))).append("</p>");

            panelSB.append("<hr style='border:1px solid ").append(PresetColour.TEXT_GREY_DARK.toWebHexString()).append("; margin: 2px 0;'/>");

            panelSB.append(getAttributeBar(ArousalLevel.getArousalLevelFromValue(character.getAttributeValue(Attribute.AROUSAL)).getRelatedStatusEffect().getSVGString(character),
                    PresetColour.ATTRIBUTE_AROUSAL,
                    character.getArousal(),
                    100,
                    idPrefix + Attribute.AROUSAL.getName()));

            panelSB.append("</div>");

        } else {
            panelSB.append("<hr style='border:1px solid ").append(PresetColour.TEXT_GREY_DARK.toWebHexString()).append("; margin: 2px 0;'/>");

            panelSB.append(getAttributeBarHalf(CorruptionLevel.getCorruptionLevelFromValue(character.getAttributeValue(Attribute.MAJOR_CORRUPTION)).getRelatedStatusEffect().getSVGString(character),
                    Attribute.MAJOR_CORRUPTION.getColour(),
                    character.getAttributeValue(Attribute.MAJOR_CORRUPTION),
                    100,
                    idPrefix + Attribute.MAJOR_CORRUPTION.getName())).append(getAttributeBarHalf(LustLevel.getLustLevelFromValue(character.getLust()).getRelatedStatusEffect().getSVGString(character),
                    PresetColour.ATTRIBUTE_LUST,
                    character.getLust(),
                    100,
                    idPrefix + Attribute.LUST.getName())).append(getAttributeBar(ArousalLevel.getArousalLevelFromValue(character.getAttributeValue(Attribute.AROUSAL)).getRelatedStatusEffect().getSVGString(character),
                    PresetColour.ATTRIBUTE_AROUSAL,
                    character.getArousal(),
                    100,
                    idPrefix + Attribute.AROUSAL.getName()));


            // Status effects:
            panelSB.append("<hr style='border:1px solid ").append(PresetColour.TEXT_GREY_DARK.toWebHexString()).append("; margin: 2px 0;'/>").append("<div class='attribute-container' style='padding:0; overflow-y: auto;'>");

            //		// Traits:
            //		for (Perk trait : character.getTraits()) {
            //			panelSB.append(
            //					"<div class='icon effect' style='border:1px solid "+PresetColour.TRAIT.toWebHexString()+"'>"
            //							+ "<div class='icon-content'>"
            //								+ trait.getSVGString()
            //								+ "<div class='overlay' id='TRAIT_" + idPrefix + trait + "'></div>"
            //							+ "</div>"
            //					+ "</div>");
            //		}

            // Infinite duration:
            for (AbstractStatusEffect se : character.getStatusEffects()) {
                boolean pointer = false;
                SexAreaInterface si = null;
                if (se == StatusEffect.PENIS_STATUS) {
                    si = SexAreaPenetration.PENIS;
                } else if (se == StatusEffect.ANUS_STATUS) {
                    si = SexAreaOrifice.ANUS;
                } else if (se == StatusEffect.ASS_STATUS) {
                    si = SexAreaOrifice.ASS;
                } else if (se == StatusEffect.MOUTH_STATUS) {
                    si = SexAreaOrifice.MOUTH;
                } else if (se == StatusEffect.BREAST_STATUS) {
                    si = SexAreaOrifice.BREAST;
                } else if (se == StatusEffect.NIPPLE_STATUS) {
                    si = SexAreaOrifice.NIPPLE;
                } else if (se == StatusEffect.THIGH_STATUS) {
                    si = SexAreaOrifice.THIGHS;
                } else if (se == StatusEffect.URETHRA_PENIS_STATUS) {
                    si = SexAreaOrifice.URETHRA_PENIS;
                } else if (se == StatusEffect.URETHRA_VAGINA_STATUS) {
                    si = SexAreaOrifice.URETHRA_VAGINA;
                } else if (se == StatusEffect.VAGINA_STATUS) {
                    si = SexAreaOrifice.VAGINA;
                }
                if (Main.game.isInSex() && si != null) {
                    if (!Main.sex.getCharactersHavingOngoingActionWith(character, si).isEmpty()) {
                        pointer = true;
                    }
                }

                if (se.isSexEffect() && character.getStatusEffectDuration(se) == -1 && se.renderInEffectsPanel()) {
                    panelSB.append("<div class='icon effect' ").append(pointer ? "style='cursor:pointer;'" : "").append(">").append("<div class='icon-content'>").append(se.getSVGString(character)).append("<div class='overlay' id='SE_").append(idPrefix).append(se).append("'></div>").append("</div>").append("</div>");
                }
            }
            // Timed:
            for (AbstractStatusEffect se : character.getStatusEffects()) {
                if (se.isSexEffect() && character.getStatusEffectDuration(se) != -1 && se.renderInEffectsPanel()) {
                    int timerHeight = (int) ((character.getStatusEffectDuration(se) / (60 * 60 * 6f)) * 100);

                    Colour timerColour = PresetColour.STATUS_EFFECT_TIME_HIGH;

                    if (timerHeight > 100) {
                        timerHeight = 100;
                        timerColour = PresetColour.STATUS_EFFECT_TIME_OVERFLOW;
                    } else if (timerHeight < 15) {
                        timerColour = PresetColour.STATUS_EFFECT_TIME_LOW;
                    } else if (timerHeight < 50) {
                        timerColour = PresetColour.STATUS_EFFECT_TIME_MEDIUM;
                    }

                    panelSB.append("<div class='icon effect'>" + "<div class='timer-background' style='width:").append(timerHeight).append("%; background:").append(timerColour.toWebHexString()).append(";'></div>").append("<div class='icon-content'>").append(se.getSVGString(character)).append("<div class='overlay' id='SE_").append(idPrefix).append(se).append("'></div>").append("</div>").append("</div>");
                }
            }

            if (!character.isPlayer()) {
                for (AbstractFetish f : character.getFetishes(true)) {
                    panelSB.append("<div class='icon effect'>" + "<div class='icon-content'>").append(f.getSVGString(character)).append("<div class='overlay' id='FETISH_").append(idPrefix).append(Fetish.getIdFromFetish(f)).append("'></div>").append("</div>").append("</div>");
                }
            }

            panelSB.append("</div></div>");
        }

        return panelSB.toString();
    }

    public static int getPageLeft() {
        return pageLeft;
    }

    public static void setPageLeft(int pageLeft) {
        RenderingEngine.pageLeft = pageLeft;
    }

    public static void setPage(GameCharacter charactersInventoryToRender, AbstractCoreItem item) {
        int uniqueItemCount = 0;

        if (item.getRarity() == Rarity.QUEST) {
            if (charactersInventoryToRender != null && charactersInventoryToRender.isPlayer()) {
                setPageLeft(5);
                return;
            } else {
                setPageRight(5);
                return;
            }
        }

        if (charactersInventoryToRender == null) {
            for (Entry<AbstractWeapon, Integer> entry : Main.game.getPlayer().getCell().getInventory().getAllWeaponsInInventory().entrySet()) {
                if (entry.getKey().getRarity() != Rarity.QUEST) {
                    if (entry.getKey().equals(item)) {
                        setPageRight(uniqueItemCount / ITEMS_PER_PAGE);
                        return;
                    }
                    uniqueItemCount++;
                }
            }
            for (Entry<AbstractClothing, Integer> entry : Main.game.getPlayer().getCell().getInventory().getAllClothingInInventory().entrySet()) {
                if (entry.getKey().getRarity() != Rarity.QUEST) {
                    if (entry.getKey().equals(item)) {
                        setPageRight(uniqueItemCount / ITEMS_PER_PAGE);
                        return;
                    }
                    uniqueItemCount++;
                }
            }
            for (Entry<AbstractItem, Integer> entry : Main.game.getPlayer().getCell().getInventory().getAllItemsInInventory().entrySet()) {
                if (entry.getKey().getRarity() != Rarity.QUEST) {
                    if (entry.getKey().equals(item)) {
                        setPageRight(uniqueItemCount / ITEMS_PER_PAGE);
                        return;
                    }
                    uniqueItemCount++;
                }
            }

        } else {
            for (Entry<AbstractWeapon, Integer> entry : charactersInventoryToRender.getAllWeaponsInInventory().entrySet()) {
                if (entry.getKey().getRarity() != Rarity.QUEST || !charactersInventoryToRender.isPlayer()) {
                    if (entry.getKey().equals(item)) {
                        if (charactersInventoryToRender.isPlayer()) {
                            setPageLeft(uniqueItemCount / ITEMS_PER_PAGE);
                            return;
                        } else {
                            setPageRight(uniqueItemCount / ITEMS_PER_PAGE);
                            return;
                        }
                    }
                    uniqueItemCount++;
                }
            }
            for (Entry<AbstractClothing, Integer> entry : charactersInventoryToRender.getAllClothingInInventory().entrySet()) {
                if (entry.getKey().getRarity() != Rarity.QUEST || !charactersInventoryToRender.isPlayer()) {
                    if (entry.getKey().equals(item)) {
                        if (charactersInventoryToRender.isPlayer()) {
                            setPageLeft(uniqueItemCount / ITEMS_PER_PAGE);
                            return;
                        } else {
                            setPageRight(uniqueItemCount / ITEMS_PER_PAGE);
                            return;
                        }
                    }
                    uniqueItemCount++;
                }
            }
            for (Entry<AbstractItem, Integer> entry : charactersInventoryToRender.getAllItemsInInventory().entrySet()) {
                if (entry.getKey().getRarity() != Rarity.QUEST || !charactersInventoryToRender.isPlayer()) {
                    if (entry.getKey().equals(item)) {
                        if (charactersInventoryToRender.isPlayer()) {
                            setPageLeft(uniqueItemCount / ITEMS_PER_PAGE);
                            return;
                        } else {
                            setPageRight(uniqueItemCount / ITEMS_PER_PAGE);
                            return;
                        }
                    }
                    uniqueItemCount++;
                }
            }
        }

    }

    public static int getPageRight() {
        return pageRight;
    }

    public static void setPageRight(int pageRight) {
        RenderingEngine.pageRight = pageRight;
    }

    public String getInventoryPanel(GameCharacter charactersInventoryToRender, boolean buyback) {
        return "<div class='container-full-width' style='background:" + PresetColour.BACKGROUND_DARK.toWebHexString() + "'>"
                + getInventoryDiv(Main.game.getPlayer(), false) + getInventoryDiv(charactersInventoryToRender, buyback)
                + "</div>";
    }

    private String getInventoryEquippedPanel(GameCharacter charactersInventoryToRender) {
        equippedPanelSB.setLength(0);

        if (charactersInventoryToRender == null) {
            equippedPanelSB.append("<div class='inventory-equipped'>");

            for (InventorySlot invSlot : mainInventorySlots) {
                equippedPanelSB.append("<div class='inventory-item-slot' style='background-color:").append(PresetColour.BACKGROUND.toWebHexString()).append(";' id='").append(invSlot.toString()).append("Slot'></div>");
            }

            String weaponStyle = "width:46%; margin:2%; background-color:" + PresetColour.BACKGROUND.toWebHexString() + ";";
            String piercingStyle = "width:40%; margin:5%; background-color:" + PresetColour.BACKGROUND.toWebHexString() + ";";

            equippedPanelSB.append("</div>"
                    + "<div class='inventory-equipped accessories'>");

            equippedPanelSB.append("<div class='inventory-item-slot' style='").append(weaponStyle).append("' id='").append(InventorySlot.WEAPON_MAIN_1).append("Slot'></div>");

            equippedPanelSB.append("<div class='inventory-item-slot' style='").append(weaponStyle).append("' id='").append(InventorySlot.WEAPON_OFFHAND_1).append("Slot'></div>");

            for (InventorySlot invSlot : piercingSlots) {
                equippedPanelSB.append("<div class='inventory-item-slot' style='").append(piercingStyle).append("'  id='").append(invSlot).append("Slot'></div>");
            }
            equippedPanelSB.append("</div>");


            equippedPanelSB.append("<div class='inventory-equipped' style='width:100%;'>");

            for (InventorySlot invSlot : secondaryInventorySlots) {
                equippedPanelSB.append("<div class='inventory-item-slot secondary' style='background-color:").append(PresetColour.BACKGROUND.toWebHexString()).append(";' id='").append(invSlot.toString()).append("Slot'></div>");
            }

            equippedPanelSB.append("<div class='inventory-item-slot secondary' style='background-color:").append(PresetColour.BACKGROUND.toWebHexString()).append(";'>").append("<div class='inventory-icon-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getTattooSwitchClothing()).append("</div>").append("<div class='overlay-inventory disabled'></div></div>");

            equippedPanelSB.append("</div>");

            return equippedPanelSB.toString();
        }


        Set<InventorySlot> blockedSlots = new HashSet<>();

        for (AbstractClothing c : charactersInventoryToRender.getClothingCurrentlyEquipped()) {
            if (c.getIncompatibleSlots(charactersInventoryToRender, c.getSlotEquippedTo()) != null) {
                blockedSlots.addAll(c.getIncompatibleSlots(charactersInventoryToRender, c.getSlotEquippedTo()));
            }
        }

        // EQUIPPED:
        equippedPanelSB.append("<div class='inventory-equipped'>");

        Map<InventorySlot, List<AbstractClothing>> concealedSlots = charactersInventoryToRender.getInventorySlotsConcealed(Main.game.getPlayer());

        for (InventorySlot invSlot : mainInventorySlots) {
            appendEquippedClothingSlot(charactersInventoryToRender, invSlot, blockedSlots, concealedSlots, false);
        }

        equippedPanelSB.append("</div>");

        // Render weapons & piercings:
        equippedPanelSB.append("<div class='inventory-equipped accessories'>");

        String weaponStyle = "width:46%; margin:2%;";
        String weaponStyleNoEssences = "width:46%; margin:2%; border:2px solid " + PresetColour.GENERIC_BAD.toWebHexString() + ";";
        String piercingStyle = "width:40%; margin:5%;";
        if (charactersInventoryToRender.getArmRows() > 1) {
            weaponStyle = "width:31%; margin:1%;";
            weaponStyleNoEssences = "width:31%; margin:1%; border:1% solid " + PresetColour.GENERIC_BAD.toWebHexString() + ";";
            piercingStyle = "width:35%; margin:5.5% 7.5%;";
        }

        int essenceCount = charactersInventoryToRender.getEssenceCount();

        // Main weapon:
        AbstractWeapon weaponInSlot = charactersInventoryToRender.getMainWeapon(0);
        if (weaponInSlot != null) {
            String weaponCount = getThrownWeaponCountDiv(weaponInSlot.getWeaponType(), charactersInventoryToRender.getWeaponCount(weaponInSlot));
            equippedPanelSB.append("<div class='inventory-item-slot").append(getClassRarityIdentifier(weaponInSlot.getRarity())).append("' style='").append(essenceCount < weaponInSlot.getWeaponType().getArcaneCost() ? weaponStyleNoEssences : weaponStyle).append("'>").append("<div class='inventory-icon-content'>").append(weaponInSlot.getSVGEquippedString(charactersInventoryToRender)).append("</div>").append("<div class='overlay-inventory' id='").append(InventorySlot.WEAPON_MAIN_1).append("Slot'>").append(weaponCount).append("</div>").append("</div>");

        } else if (Main.game.isInCombat() && Main.combat.getThrownWeaponsDepleted(charactersInventoryToRender, InventorySlot.WEAPON_MAIN_1) != null) {
            AbstractWeaponType depletedWeapon = Main.combat.getThrownWeaponsDepleted(charactersInventoryToRender, InventorySlot.WEAPON_MAIN_1);
            String weaponCount = getThrownWeaponCountDiv(depletedWeapon, 0);
            equippedPanelSB.append("<div class='inventory-item-slot").append(getClassRarityIdentifier(depletedWeapon.getRarity())).append("' style='").append(weaponStyle).append("'>").append("<div class='inventory-icon-content' style='opacity:0.5;'>").append(depletedWeapon.getSVGEquippedImageDesaturated(charactersInventoryToRender)).append("</div>").append("<div class='overlay-inventory' id='").append(InventorySlot.WEAPON_MAIN_1).append("Slot'>").append(weaponCount).append("</div>").append("</div>");

        } else {
            equippedPanelSB.append(getEmptyWeaponDiv(charactersInventoryToRender, false, InventorySlot.WEAPON_MAIN_1, weaponStyle));
        }

        // Multiple arms:
        if (charactersInventoryToRender.getArmRows() > 1) {
            // Weapon in second slot:
            weaponInSlot = charactersInventoryToRender.getMainWeapon(1);
            if (weaponInSlot != null) {
                String weaponCount = getThrownWeaponCountDiv(weaponInSlot.getWeaponType(), charactersInventoryToRender.getWeaponCount(weaponInSlot));
                equippedPanelSB.append("<div class='inventory-item-slot").append(getClassRarityIdentifier(weaponInSlot.getRarity())).append("' style='").append(essenceCount < weaponInSlot.getWeaponType().getArcaneCost() ? weaponStyleNoEssences : weaponStyle).append("'>").append("<div class='inventory-icon-content'>").append(weaponInSlot.getSVGEquippedString(charactersInventoryToRender)).append("</div>").append("<div class='overlay-inventory' id='").append(InventorySlot.WEAPON_MAIN_2).append("Slot'>").append(weaponCount).append("</div>").append("</div>");

            } else if (Main.game.isInCombat() && Main.combat.getThrownWeaponsDepleted(charactersInventoryToRender, InventorySlot.WEAPON_MAIN_2) != null) {
                AbstractWeaponType depletedWeapon = Main.combat.getThrownWeaponsDepleted(charactersInventoryToRender, InventorySlot.WEAPON_MAIN_2);
                String weaponCount = getThrownWeaponCountDiv(depletedWeapon, 0);
                equippedPanelSB.append("<div class='inventory-item-slot").append(getClassRarityIdentifier(depletedWeapon.getRarity())).append("' style='").append(weaponStyle).append("'>").append("<div class='inventory-icon-content' style='opacity:0.5;'>").append(depletedWeapon.getSVGEquippedImageDesaturated(charactersInventoryToRender)).append("</div>").append("<div class='overlay-inventory' id='").append(InventorySlot.WEAPON_MAIN_2).append("Slot'>").append(weaponCount).append("</div>").append("</div>");

            } else {
                equippedPanelSB.append(getEmptyWeaponDiv(charactersInventoryToRender, false, InventorySlot.WEAPON_MAIN_2, weaponStyle));
            }

            // Weapon in third slot:
            weaponInSlot = charactersInventoryToRender.getMainWeapon(2);
            if (weaponInSlot != null) {
                String weaponCount = getThrownWeaponCountDiv(weaponInSlot.getWeaponType(), charactersInventoryToRender.getWeaponCount(weaponInSlot));
                equippedPanelSB.append("<div class='inventory-item-slot").append(getClassRarityIdentifier(weaponInSlot.getRarity())).append("' style='").append(essenceCount < weaponInSlot.getWeaponType().getArcaneCost() ? weaponStyleNoEssences : weaponStyle).append("'>").append("<div class='inventory-icon-content'>").append(weaponInSlot.getSVGEquippedString(charactersInventoryToRender)).append("</div>").append("<div class='overlay-inventory' id='").append(InventorySlot.WEAPON_MAIN_3).append("Slot'>").append(weaponCount).append("</div>").append("</div>");

            } else if (Main.game.isInCombat() && Main.combat.getThrownWeaponsDepleted(charactersInventoryToRender, InventorySlot.WEAPON_MAIN_3) != null) {
                AbstractWeaponType depletedWeapon = Main.combat.getThrownWeaponsDepleted(charactersInventoryToRender, InventorySlot.WEAPON_MAIN_3);
                String weaponCount = getThrownWeaponCountDiv(depletedWeapon, 0);
                equippedPanelSB.append("<div class='inventory-item-slot").append(getClassRarityIdentifier(depletedWeapon.getRarity())).append("' style='").append(weaponStyle).append("'>").append("<div class='inventory-icon-content' style='opacity:0.5;'>").append(depletedWeapon.getSVGEquippedImageDesaturated(charactersInventoryToRender)).append("</div>").append("<div class='overlay-inventory' id='").append(InventorySlot.WEAPON_MAIN_3).append("Slot'>").append(weaponCount).append("</div>").append("</div>");

            } else if (charactersInventoryToRender.getArmRows() == 2) {
                equippedPanelSB.append(getEmptyWeaponDiv(charactersInventoryToRender, true, InventorySlot.WEAPON_MAIN_3, weaponStyle));

            } else {
                equippedPanelSB.append(getEmptyWeaponDiv(charactersInventoryToRender, false, InventorySlot.WEAPON_MAIN_3, weaponStyle));
            }
        }

        // Offhand weapon:
        weaponInSlot = charactersInventoryToRender.getOffhandWeapon(0);
        if (weaponInSlot != null) {
            String weaponCount = getThrownWeaponCountDiv(weaponInSlot.getWeaponType(), charactersInventoryToRender.getWeaponCount(weaponInSlot));
            equippedPanelSB.append("<div class='inventory-item-slot").append(getClassRarityIdentifier(weaponInSlot.getRarity())).append("' style='").append(essenceCount < weaponInSlot.getWeaponType().getArcaneCost() ? weaponStyleNoEssences : weaponStyle).append("'>").append("<div class='inventory-icon-content'>").append(weaponInSlot.getSVGEquippedString(charactersInventoryToRender)).append("</div>").append("<div class='overlay-inventory' id='").append(InventorySlot.WEAPON_OFFHAND_1).append("Slot'>").append(weaponCount).append("</div>").append("</div>");

        } else if (Main.game.isInCombat() && Main.combat.getThrownWeaponsDepleted(charactersInventoryToRender, InventorySlot.WEAPON_OFFHAND_1) != null) {
            AbstractWeaponType depletedWeapon = Main.combat.getThrownWeaponsDepleted(charactersInventoryToRender, InventorySlot.WEAPON_OFFHAND_1);
            String weaponCount = getThrownWeaponCountDiv(depletedWeapon, 0);
            equippedPanelSB.append("<div class='inventory-item-slot").append(getClassRarityIdentifier(depletedWeapon.getRarity())).append("' style='").append(weaponStyle).append("'>").append("<div class='inventory-icon-content' style='opacity:0.5;'>").append(depletedWeapon.getSVGEquippedImageDesaturated(charactersInventoryToRender)).append("</div>").append("<div class='overlay-inventory' id='").append(InventorySlot.WEAPON_OFFHAND_1).append("Slot'>").append(weaponCount).append("</div>").append("</div>");

        } else if (charactersInventoryToRender.getMainWeapon(0) != null && charactersInventoryToRender.getMainWeapon(0).getWeaponType().isTwoHanded()) {
            equippedPanelSB.append(getEmptyWeaponDiv(charactersInventoryToRender, true, InventorySlot.WEAPON_OFFHAND_1, weaponStyle));

        } else {
            equippedPanelSB.append(getEmptyWeaponDiv(charactersInventoryToRender, false, InventorySlot.WEAPON_OFFHAND_1, weaponStyle));
        }

        // Multiple arms:
        if (charactersInventoryToRender.getArmRows() > 1) {
            // Weapon in second slot:
            weaponInSlot = charactersInventoryToRender.getOffhandWeapon(1);
            if (weaponInSlot != null) {
                String weaponCount = getThrownWeaponCountDiv(weaponInSlot.getWeaponType(), charactersInventoryToRender.getWeaponCount(weaponInSlot));
                equippedPanelSB.append("<div class='inventory-item-slot").append(getClassRarityIdentifier(weaponInSlot.getRarity())).append("' style='").append(essenceCount < weaponInSlot.getWeaponType().getArcaneCost() ? weaponStyleNoEssences : weaponStyle).append("'>").append("<div class='inventory-icon-content'>").append(weaponInSlot.getSVGEquippedString(charactersInventoryToRender)).append("</div>").append("<div class='overlay-inventory' id='").append(InventorySlot.WEAPON_OFFHAND_2).append("Slot'>").append(weaponCount).append("</div>").append("</div>");

            } else if (Main.game.isInCombat() && Main.combat.getThrownWeaponsDepleted(charactersInventoryToRender, InventorySlot.WEAPON_OFFHAND_2) != null) {
                AbstractWeaponType depletedWeapon = Main.combat.getThrownWeaponsDepleted(charactersInventoryToRender, InventorySlot.WEAPON_OFFHAND_2);
                String weaponCount = getThrownWeaponCountDiv(depletedWeapon, 0);
                equippedPanelSB.append("<div class='inventory-item-slot").append(getClassRarityIdentifier(depletedWeapon.getRarity())).append("' style='").append(weaponStyle).append("'>").append("<div class='inventory-icon-content' style='opacity:0.5;'>").append(depletedWeapon.getSVGEquippedImageDesaturated(charactersInventoryToRender)).append("</div>").append("<div class='overlay-inventory' id='").append(InventorySlot.WEAPON_OFFHAND_2).append("Slot'>").append(weaponCount).append("</div>").append("</div>");

            } else if (charactersInventoryToRender.getMainWeapon(1) != null && charactersInventoryToRender.getMainWeapon(1).getWeaponType().isTwoHanded()) {
                equippedPanelSB.append(getEmptyWeaponDiv(charactersInventoryToRender, true, InventorySlot.WEAPON_OFFHAND_2, weaponStyle));

            } else {
                equippedPanelSB.append(getEmptyWeaponDiv(charactersInventoryToRender, false, InventorySlot.WEAPON_OFFHAND_2, weaponStyle));
            }

            // Weapon in third slot:
            weaponInSlot = charactersInventoryToRender.getOffhandWeapon(2);
            if (weaponInSlot != null) {
                String weaponCount = getThrownWeaponCountDiv(weaponInSlot.getWeaponType(), charactersInventoryToRender.getWeaponCount(weaponInSlot));
                equippedPanelSB.append("<div class='inventory-item-slot").append(getClassRarityIdentifier(weaponInSlot.getRarity())).append("' style='").append(essenceCount < weaponInSlot.getWeaponType().getArcaneCost() ? weaponStyleNoEssences : weaponStyle).append("'>").append("<div class='inventory-icon-content'>").append(weaponInSlot.getSVGEquippedString(charactersInventoryToRender)).append("</div>").append("<div class='overlay-inventory' id='").append(InventorySlot.WEAPON_OFFHAND_3).append("Slot'>").append(weaponCount).append("</div>").append("</div>");

            } else if (Main.game.isInCombat() && Main.combat.getThrownWeaponsDepleted(charactersInventoryToRender, InventorySlot.WEAPON_OFFHAND_3) != null) {
                AbstractWeaponType depletedWeapon = Main.combat.getThrownWeaponsDepleted(charactersInventoryToRender, InventorySlot.WEAPON_OFFHAND_3);
                String weaponCount = getThrownWeaponCountDiv(depletedWeapon, 0);
                equippedPanelSB.append("<div class='inventory-item-slot").append(getClassRarityIdentifier(depletedWeapon.getRarity())).append("' style='").append(weaponStyle).append("'>").append("<div class='inventory-icon-content' style='opacity:0.5;'>").append(depletedWeapon.getSVGEquippedImageDesaturated(charactersInventoryToRender)).append("</div>").append("<div class='overlay-inventory' id='").append(InventorySlot.WEAPON_OFFHAND_3).append("Slot'>").append(weaponCount).append("</div>").append("</div>");

            } else if (charactersInventoryToRender.getArmRows() == 2 || (charactersInventoryToRender.getMainWeapon(2) != null && charactersInventoryToRender.getMainWeapon(2).getWeaponType().isTwoHanded())) {
                equippedPanelSB.append(getEmptyWeaponDiv(charactersInventoryToRender, true, InventorySlot.WEAPON_OFFHAND_3, weaponStyle));

            } else {
                equippedPanelSB.append(getEmptyWeaponDiv(charactersInventoryToRender, false, InventorySlot.WEAPON_OFFHAND_3, weaponStyle));
            }
        }


        //piercingSlots
        for (InventorySlot invSlot : piercingSlots) {

            AbstractClothing clothing = charactersInventoryToRender.getClothingInSlot(invSlot);

            if (!charactersInventoryToRender.isPlayer() && concealedSlots.containsKey(invSlot)) {
                equippedPanelSB.append("<div class='inventory-item-slot concealed' style='").append(piercingStyle).append("' id='").append(invSlot).append("Slot'>").append("<div class='concealedIcon'>").append(SVGImages.SVG_IMAGE_PROVIDER.getConcealedIcon()).append("</div>").append("</div>");

            } else {
                if (clothing != null) {
                    // add to content:
                    equippedPanelSB.append("<div class='inventory-item-slot ").append(getClassRarityIdentifier(clothing.getRarity())).append("'").append(clothing.isSealed()
                            ? "style='" + piercingStyle + " border-width:2px; border-color:" + PresetColour.SEALED.toWebHexString() + "; border-style:solid;'"
                            : "style='" + piercingStyle + "'").append(">");

                    equippedPanelSB.append("<div class='inventory-icon-content'>").append(clothing.getSVGEquippedString(charactersInventoryToRender)).append("</div>");

                    equippedPanelSB.append((!clothing.getDisplacedList().isEmpty() ? "<div class='displacedIcon'>" + SVGImages.SVG_IMAGE_PROVIDER.getDisplacedIcon() + "</div>" : ""));
                    equippedPanelSB.append((clothing.isDirty() ? "<div class='cummedIcon'>" + SVGImages.SVG_IMAGE_PROVIDER.getDirtyIcon() + "</div>" : ""));
                    equippedPanelSB.append((clothing.getClothingType().getFemininityMaximum() < charactersInventoryToRender.getFemininityValue() ? "<div class='femininityIcon'>" + SVGImages.SVG_IMAGE_PROVIDER.getMasculineWarningIcon() + "</div>" : ""));
                    equippedPanelSB.append((clothing.getClothingType().getFemininityMinimum() > charactersInventoryToRender.getFemininityValue() ? "<div class='femininityIcon'>" + SVGImages.SVG_IMAGE_PROVIDER.getFeminineWarningIcon() + "</div>" : ""));

                    // Only append the most important of these effects:
                    if (charactersInventoryToRender.getSexToyOrificeStretching().containsValue(clothing)) {
                        equippedPanelSB.append("<div class='stretchIcon'>").append(SVGImages.SVG_IMAGE_PROVIDER.getCombinationStretching()).append("</div>");
                    } else if (charactersInventoryToRender.getSexToyOrificeTooDeep().containsValue(clothing)) {
                        equippedPanelSB.append("<div class='stretchIcon'>").append(SVGImages.SVG_IMAGE_PROVIDER.getCombinationDepthMaximum()).append("</div>");
                    } else if (charactersInventoryToRender.getSexToyOrificePreventingStretchRecovery().containsValue(clothing)) {
                        equippedPanelSB.append("<div class='stretchIcon'>").append(SVGImages.SVG_IMAGE_PROVIDER.getCombinationStretchRecoveryPrevented()).append("</div>");
                    }

                    equippedPanelSB.append("<div class='overlay-inventory' id='").append(invSlot).append("Slot'>").append("</div>");

                    equippedPanelSB.append("</div>");


                } else {
                    // add to content:
                    if (blockedSlots.contains(invSlot)) {
                        equippedPanelSB.append("<div class='inventory-item-slot disabled' style='").append(piercingStyle).append("' id='").append(invSlot).append("Slot'>").append("</div>");

                    } else {
                        boolean bypassesPiercing = !charactersInventoryToRender.getBody().getBodyMaterial().isRequiresPiercing();

                        switch (invSlot) {
                            case PIERCING_VAGINA:
                                if (!charactersInventoryToRender.hasVagina() || (!bypassesPiercing && !charactersInventoryToRender.isPiercedVagina())) {
                                    equippedPanelSB.append("<div class='inventory-item-slot disabled' style='").append(piercingStyle).append("' id='").append(invSlot).append("Slot'>").append("</div>");
                                } else {
                                    equippedPanelSB.append("<div class='inventory-item-slot' style='").append(piercingStyle).append("' id='").append(invSlot).append("Slot'></div>");
                                }
                                break;
                            case PIERCING_EAR:
                                if (!bypassesPiercing && !charactersInventoryToRender.isPiercedEar()) {
                                    equippedPanelSB.append("<div class='inventory-item-slot disabled' style='").append(piercingStyle).append("' id='").append(invSlot).append("Slot'>").append("</div>");
                                } else {
                                    equippedPanelSB.append("<div class='inventory-item-slot' style='").append(piercingStyle).append("' id='").append(invSlot).append("Slot'></div>");
                                }
                                break;
                            case PIERCING_LIP:
                                if (!bypassesPiercing && !charactersInventoryToRender.isPiercedLip()) {
                                    equippedPanelSB.append("<div class='inventory-item-slot disabled' style='").append(piercingStyle).append("' id='").append(invSlot).append("Slot'>").append("</div>");
                                } else {
                                    equippedPanelSB.append("<div class='inventory-item-slot' style='").append(piercingStyle).append("' id='").append(invSlot).append("Slot'></div>");
                                }
                                break;
                            case PIERCING_NIPPLE:
                                if (!bypassesPiercing && !charactersInventoryToRender.isPiercedNipple()) {
                                    equippedPanelSB.append("<div class='inventory-item-slot disabled' style='").append(piercingStyle).append("' id='").append(invSlot).append("Slot'>").append("</div>");
                                } else {
                                    equippedPanelSB.append("<div class='inventory-item-slot' style='").append(piercingStyle).append("' id='").append(invSlot).append("Slot'></div>");
                                }
                                break;
                            case PIERCING_NOSE:
                                if (!bypassesPiercing && !charactersInventoryToRender.isPiercedNose()) {
                                    equippedPanelSB.append("<div class='inventory-item-slot disabled' style='").append(piercingStyle).append("' id='").append(invSlot).append("Slot'>").append("</div>");
                                } else {
                                    equippedPanelSB.append("<div class='inventory-item-slot' style='").append(piercingStyle).append("' id='").append(invSlot).append("Slot'></div>");
                                }
                                break;
                            case PIERCING_PENIS:
                                if (!charactersInventoryToRender.hasPenis() || (!bypassesPiercing && !charactersInventoryToRender.isPiercedPenis())) {
                                    equippedPanelSB.append("<div class='inventory-item-slot disabled' style='").append(piercingStyle).append("' id='").append(invSlot).append("Slot'>").append("</div>");
                                } else {
                                    equippedPanelSB.append("<div class='inventory-item-slot' style='").append(piercingStyle).append("' id='").append(invSlot).append("Slot'></div>");
                                }
                                break;
                            case PIERCING_STOMACH:
                                if (!bypassesPiercing && !charactersInventoryToRender.isPiercedNavel()) {
                                    equippedPanelSB.append("<div class='inventory-item-slot disabled' style='").append(piercingStyle).append("' id='").append(invSlot).append("Slot'>").append("</div>");
                                } else {
                                    equippedPanelSB.append("<div class='inventory-item-slot' style='").append(piercingStyle).append("' id='").append(invSlot).append("Slot'></div>");
                                }
                                break;
                            case PIERCING_TONGUE:
                                if (!bypassesPiercing && !charactersInventoryToRender.isPiercedTongue()) {
                                    equippedPanelSB.append("<div class='inventory-item-slot disabled' style='").append(piercingStyle).append("' id='").append(invSlot).append("Slot'>").append("</div>");
                                } else {
                                    equippedPanelSB.append("<div class='inventory-item-slot' style='").append(piercingStyle).append("' id='").append(invSlot).append("Slot'></div>");
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
        equippedPanelSB.append("</div>");


        equippedPanelSB.append("<div class='inventory-equipped' style='width:100%;'>");

        for (InventorySlot invSlot : secondaryInventorySlots) {
            appendEquippedClothingSlot(charactersInventoryToRender, invSlot, blockedSlots, concealedSlots, true);
        }

        equippedPanelSB.append("<div class='inventory-item-slot secondary ").append(getClassRarityIdentifier(Rarity.COMMON)).append("'>");
        if (charactersInventoryToRender.isPlayer()) {
            equippedPanelSB.append("<div class='inventory-icon-content'>").append(this.isRenderingTattoosLeft()
                    ? SVGImages.SVG_IMAGE_PROVIDER.getTattooSwitchTattoo()
                    : SVGImages.SVG_IMAGE_PROVIDER.getTattooSwitchClothing()).append("</div>").append("<div class='overlay-inventory' id='TATTOO_SWITCH_LEFT'></div>");

        } else {
            equippedPanelSB.append("<div class='inventory-icon-content").append(getClassRarityIdentifier(Rarity.COMMON)).append("'>").append(this.isRenderingTattoosRight()
                    ? SVGImages.SVG_IMAGE_PROVIDER.getTattooSwitchTattoo()
                    : SVGImages.SVG_IMAGE_PROVIDER.getTattooSwitchClothing()).append("</div>").append("<div class='overlay-inventory' id='TATTOO_SWITCH_RIGHT'></div>");
        }
        equippedPanelSB.append("</div>");

        equippedPanelSB.append("</div>");

        return equippedPanelSB.toString();
    }

    private void appendEquippedClothingSlot(GameCharacter charactersInventoryToRender, InventorySlot invSlot, Set<InventorySlot> blockedSlots, Map<InventorySlot, List<AbstractClothing>> concealedSlots, boolean isSecondary) {
        String inventorySlotId = "inventory-item-slot";
        if (isSecondary) {
            inventorySlotId = "inventory-item-slot secondary";
        }

        if (!charactersInventoryToRender.isPlayer() && concealedSlots.containsKey(invSlot)) {
            equippedPanelSB.append("<div class='").append(inventorySlotId).append(" concealed' id='").append(invSlot.toString()).append("Slot'>").append("<div class='concealedIcon'>").append(SVGImages.SVG_IMAGE_PROVIDER.getConcealedIcon()).append("</div>").append("</div>");

        } else {
            if (((charactersInventoryToRender.isPlayer() && !this.isRenderingTattoosLeft()) || (!charactersInventoryToRender.isPlayer() && !this.isRenderingTattoosRight()))
                    && !invSlot.isJewellery()) {
                AbstractClothing clothing = charactersInventoryToRender.getClothingInSlot(invSlot);

                BodyPartClothingBlock block = invSlot.getBodyPartClothingBlock(charactersInventoryToRender);
                if (clothing != null) {
                    equippedPanelSB.append("<div class='").append(inventorySlotId).append(getClassRarityIdentifier(clothing.getRarity())).append("'").append(clothing.isSealed() ? "style='border-width:2px; border-color:" + PresetColour.SEALED.toWebHexString() + "; border-style:solid;'" : "").append(">");

                    equippedPanelSB.append(
                            (block != null && block.getRace() != null
                                    ? "<div class='raceBlockIcon'>" + AbstractSubspecies.getMainSubspeciesOfRace(block.getRace()).getSVGStringDesaturated(charactersInventoryToRender, PresetColour.BASE_BLACK) + "</div>"
                                    : ""));

                    equippedPanelSB.append("<div class='inventory-icon-content'>").append(clothing.getSVGEquippedString(charactersInventoryToRender)).append("</div>");

                    equippedPanelSB.append((!clothing.getDisplacedList().isEmpty() ? "<div class='displacedIcon'>" + SVGImages.SVG_IMAGE_PROVIDER.getDisplacedIcon() + "</div>" : ""));
                    equippedPanelSB.append((clothing.isDirty() ? "<div class='cummedIcon'>" + SVGImages.SVG_IMAGE_PROVIDER.getDirtyIcon() + "</div>" : ""));
                    equippedPanelSB.append((clothing.getClothingType().getFemininityMaximum() < charactersInventoryToRender.getFemininityValue() ? "<div class='femininityIcon'>" + SVGImages.SVG_IMAGE_PROVIDER.getMasculineWarningIcon() + "</div>" : ""));
                    equippedPanelSB.append((clothing.getClothingType().getFemininityMinimum() > charactersInventoryToRender.getFemininityValue() ? "<div class='femininityIcon'>" + SVGImages.SVG_IMAGE_PROVIDER.getFeminineWarningIcon() + "</div>" : ""));

                    // Only append the most important of these effects:
                    if (charactersInventoryToRender.getSexToyOrificeStretching().containsValue(clothing)) {
                        equippedPanelSB.append("<div class='stretchIcon'>").append(SVGImages.SVG_IMAGE_PROVIDER.getCombinationStretching()).append("</div>");
                    } else if (charactersInventoryToRender.getSexToyOrificeTooDeep().containsValue(clothing)) {
                        equippedPanelSB.append("<div class='stretchIcon'>").append(SVGImages.SVG_IMAGE_PROVIDER.getCombinationDepthMaximum()).append("</div>");
                    } else if (charactersInventoryToRender.getSexToyOrificePreventingStretchRecovery().containsValue(clothing)) {
                        equippedPanelSB.append("<div class='stretchIcon'>").append(SVGImages.SVG_IMAGE_PROVIDER.getCombinationStretchRecoveryPrevented()).append("</div>");
                    }

                    equippedPanelSB.append("<div class='overlay-inventory' id='").append(invSlot).append("Slot'>").append("</div>");
                    equippedPanelSB.append("</div>");

                } else {
                    // add to content:
                    if (blockedSlots.contains(invSlot)) {
                        equippedPanelSB.append("<div class='").append(inventorySlotId).append(" disabled'>").append(charactersInventoryToRender.isDirtySlot(invSlot) ? "<div class='cummedIcon'>" + SVGImages.SVG_IMAGE_PROVIDER.getDirtyIcon() + "</div>" : "").append("<div class='overlay' id='").append(invSlot).append("Slot'></div>").append("</div>");

                    } else if (block != null) {
                        equippedPanelSB.append("<div class='").append(inventorySlotId).append(" disabled'>").append(charactersInventoryToRender.isDirtySlot(invSlot) ? "<div class='cummedIcon'>" + SVGImages.SVG_IMAGE_PROVIDER.getDirtyIcon() + "</div>" : "").append("<div class='overlay' id='").append(invSlot).append("Slot'></div>").append(block.getRace() != null
                                ? "<div class='raceBlockIcon'>" + AbstractSubspecies.getMainSubspeciesOfRace(block.getRace()).getSVGStringDesaturated(charactersInventoryToRender, PresetColour.BASE_BLACK) + "</div>"
                                : "").append("</div>");

                    } else {
                        boolean disabled = !invSlot.isPhysicallyAvailable(charactersInventoryToRender);

                        equippedPanelSB.append("<div class='").append(inventorySlotId).append(disabled ? " disabled" : "").append("' id='").append(invSlot).append("Slot'>").append(charactersInventoryToRender.isDirtySlot(invSlot) ? "<div class='cummedIcon'>" + SVGImages.SVG_IMAGE_PROVIDER.getDirtyIcon() + "</div>" : "").append("</div>");
                    }
                }

            } else { // Tattoos:
                boolean disabled = !invSlot.isPhysicallyAvailable(charactersInventoryToRender) && invSlot != InventorySlot.HAIR; // Exception for hair as this slot corresponds to the 'ears' slot for tattoos

                if (disabled) {
                    equippedPanelSB.append("<div class='").append(inventorySlotId).append(" disabled' id='").append(invSlot).append("Slot'></div>");

                } else {
                    Tattoo tattoo = charactersInventoryToRender.getTattooInSlot(invSlot);
                    inventorySlotId = "inventory-item-slot dark";
                    if (isSecondary) {
                        inventorySlotId = "inventory-item-slot secondary dark";
                    }
                    if (tattoo != null) {
                        equippedPanelSB.append("<div class='").append(inventorySlotId).append(getClassRarityIdentifier(tattoo.getRarity())).append("'>");
                        equippedPanelSB.append("<div class='inventory-icon-content'>").append(tattoo.getSVGImage(charactersInventoryToRender)).append("</div>");
                        if (charactersInventoryToRender.getScarInSlot(invSlot) != null) {
                            equippedPanelSB.append("<div class='scarIcon'>").append(SVGImages.SVG_IMAGE_PROVIDER.getScarIcon()).append("</div>");
                        }
                        equippedPanelSB.append(lipstickMarkingsString(charactersInventoryToRender, invSlot));
                        equippedPanelSB.append("<div class='overlay-inventory no-pointer' id='").append(invSlot).append("Slot'></div>");
                        equippedPanelSB.append("</div>");

                    } else {
                        if (blockedSlots.contains(invSlot)) {
                            equippedPanelSB.append("<div class='").append(inventorySlotId).append(" disabled'>");
                            if (charactersInventoryToRender.getScarInSlot(invSlot) != null) {
                                equippedPanelSB.append("<div class='scarIcon'>").append(SVGImages.SVG_IMAGE_PROVIDER.getScarIcon()).append("</div>");
                            }
                            equippedPanelSB.append(lipstickMarkingsString(charactersInventoryToRender, invSlot));
                            equippedPanelSB.append("<div class='overlay' id='").append(invSlot).append("Slot'></div>");
                            equippedPanelSB.append("</div>");

                        } else {
                            equippedPanelSB.append("<div class='").append(inventorySlotId).append("' id='").append(invSlot).append("Slot'>");
                            if (charactersInventoryToRender.getScarInSlot(invSlot) != null) {
                                equippedPanelSB.append("<div class='scarIcon'>").append(SVGImages.SVG_IMAGE_PROVIDER.getScarIcon()).append("</div>");
                            }
                            equippedPanelSB.append(lipstickMarkingsString(charactersInventoryToRender, invSlot));
                            equippedPanelSB.append("</div>");
                        }
                    }
                }

            }
        }
    }

    private String getInventoryDiv(GameCharacter charactersInventoryToRender, boolean buyback) {
        inventorySB.setLength(0);

        String idModifier = charactersInventoryToRender == null ? "FLOOR_" : (charactersInventoryToRender.isPlayer() ? "PLAYER_" : "NPC_" + charactersInventoryToRender.getId() + "_");

        inventorySB.append("<div class='inventory-container'>");

        if (charactersInventoryToRender != null) {
            inventorySB.append("<p style='width:100%; text-align:center; padding:0; margin:0;'>" + "<b style='color:").append(Femininity.valueOf(charactersInventoryToRender.getFemininityValue()).getColour().toWebHexString()).append(";'>").append(charactersInventoryToRender.isPlayer()
                    ? "Your</b> <b>Inventory | Page " + (pageLeft + 1) + "</b>"
                    : (UtilText.parse(charactersInventoryToRender, "[npc.NamePos]")) + "</b> <b>Inventory | " + (buyback ? "[style.colourCurrency(Buyback)]" : "Page " + (pageRight + 1)) + "</b>").append("</p>");

        } else {
            inventorySB.append("<p style='width:100%; text-align:center; padding:0; margin:0;'>").append(InventoryDialogue.getNPCInventoryInteraction() == InventoryInteraction.CHARACTER_CREATION
                    ? "<b style='color:" + PresetColour.BASE_TAN.toWebHexString() + ";'>Your wardrobe</b>"
                    : "<b style='color:" + PresetColour.BASE_TAN.toWebHexString() + ";'>In this Area</b>").append("<b> | Page ").append(pageRight + 1).append("</b>").append("</p>");
        }

        // Buttons:
        int totalUniques = 0;
        String pageIdMod = "";
        int currentPage = 0;
        boolean renderQuestTab = true;
        boolean hasQuestItems = false;
        if (charactersInventoryToRender == null) {
            hasQuestItems = Main.game.getPlayerCell().getInventory().isAnyQuestItemPresent();
            totalUniques = Main.game.getPlayerCell().getInventory().getUniqueItemCount() - Main.game.getPlayerCell().getInventory().getUniqueQuestItemCount()
                    + Main.game.getPlayerCell().getInventory().getUniqueClothingCount() - Main.game.getPlayerCell().getInventory().getUniqueQuestClothingCount()
                    + Main.game.getPlayerCell().getInventory().getUniqueWeaponCount() - Main.game.getPlayerCell().getInventory().getUniqueQuestWeaponCount();
            pageIdMod = "INV_PAGE_RIGHT_";
            // Reset page index if the number of items is too low to be displayed on that index:
            if (pageRight != 5) { // So long as current page is not uniques
                while (totalUniques <= pageRight * ITEMS_PER_PAGE && pageRight > 0) {
                    pageRight--;
                }
            }
            currentPage = pageRight;

        } else {
//			renderQuestTab = charactersInventoryToRender.isPlayer();
            hasQuestItems = charactersInventoryToRender.isCarryingQuestItems();
            totalUniques = charactersInventoryToRender.getUniqueItemCount() - charactersInventoryToRender.getUniqueQuestItemCount()
                    + charactersInventoryToRender.getUniqueClothingCount() - charactersInventoryToRender.getUniqueQuestClothingCount()
                    + charactersInventoryToRender.getUniqueWeaponCount() - charactersInventoryToRender.getUniqueQuestWeaponCount();
            pageIdMod = (charactersInventoryToRender.isPlayer() ? "INV_PAGE_LEFT_" : "INV_PAGE_RIGHT_");
            // Reset page index if the number of items is too low to be displayed on that index:
            if (charactersInventoryToRender.isPlayer() ? pageLeft != 5 : pageRight != 5) { // So long as current page is not uniques
                if (charactersInventoryToRender.isPlayer()) {
                    while (totalUniques <= pageLeft * ITEMS_PER_PAGE && pageLeft > 0) {
                        pageLeft--;
                    }
                } else {
                    while (totalUniques <= pageRight * ITEMS_PER_PAGE && pageRight > 0) {
                        pageRight--;
                    }
                }
            }
            currentPage = (charactersInventoryToRender.isPlayer() ? pageLeft : pageRight);
        }

        inventorySB.append("<div class='container-full-width' style='width:15%; margin:0; text-align:center;'>" + "<div class='square-button max").append(currentPage == 0 ? " selected" : "").append("'>").append("<div style='width:80%;height:80%;position:absolute;left:0; bottom:0;'>").append(SVGImages.SVG_IMAGE_PROVIDER.getInventoryIcon()).append("</div>").append("<div style='width:50%;height:50%;position:absolute;right:4px; top:0;'>").append(currentPage == 0 ? SVGImages.SVG_IMAGE_PROVIDER.getCounterOne() : SVGImages.SVG_IMAGE_PROVIDER.getCounterOneDisabled()).append("</div>"
//							+ (!buyback
//									?
        ).append("<div class='overlay' ").append(currentPage == 0 ? "" : "id='" + pageIdMod + "0'").append("></div>"
//									:"<div class='overlay disabled'></div>")
        ).append("</div>").append("<div class='square-button max").append(currentPage == 1 ? " selected" : "").append("'>").append("<div style='width:80%;height:80%;position:absolute;left:0; bottom:0;'>").append(totalUniques > 1 * ITEMS_PER_PAGE && !buyback ? SVGImages.SVG_IMAGE_PROVIDER.getInventoryIcon() : SVGImages.SVG_IMAGE_PROVIDER.getInventoryIconDisabled()).append("</div>").append("<div style='width:50%;height:50%;position:absolute;right:4px; top:0;'>").append(currentPage == 1 ? SVGImages.SVG_IMAGE_PROVIDER.getCounterTwo() : SVGImages.SVG_IMAGE_PROVIDER.getCounterTwoDisabled()).append("</div>").append(totalUniques > 1 * ITEMS_PER_PAGE && !buyback
                ? "<div class='overlay' " + (currentPage == 1 ? "" : "id='" + pageIdMod + "1'") + "></div>"
                : "<div class='overlay disabled'></div>").append("</div>").append("<div class='square-button max").append(currentPage == 2 ? " selected" : "").append("'>").append("<div style='width:80%;height:80%;position:absolute;left:0; bottom:0;'>").append(totalUniques > 2 * ITEMS_PER_PAGE && !buyback ? SVGImages.SVG_IMAGE_PROVIDER.getInventoryIcon() : SVGImages.SVG_IMAGE_PROVIDER.getInventoryIconDisabled()).append("</div>").append("<div style='width:50%;height:50%;position:absolute;right:4px; top:0;'>").append(currentPage == 2 ? SVGImages.SVG_IMAGE_PROVIDER.getCounterThree() : SVGImages.SVG_IMAGE_PROVIDER.getCounterThreeDisabled()).append("</div>").append(totalUniques > 2 * ITEMS_PER_PAGE && !buyback
                ? "<div class='overlay' " + (currentPage == 2 ? "" : "id='" + pageIdMod + "2'") + "></div>"
                : "<div class='overlay disabled'></div>").append("</div>").append("<div class='square-button max").append(currentPage == 3 ? " selected" : "").append("'>").append("<div style='width:80%;height:80%;position:absolute;left:0; bottom:0;'>").append(totalUniques > 3 * ITEMS_PER_PAGE && !buyback ? SVGImages.SVG_IMAGE_PROVIDER.getInventoryIcon() : SVGImages.SVG_IMAGE_PROVIDER.getInventoryIconDisabled()).append("</div>").append("<div style='width:50%;height:50%;position:absolute;right:4px; top:0;'>").append(currentPage == 3 ? SVGImages.SVG_IMAGE_PROVIDER.getCounterFour() : SVGImages.SVG_IMAGE_PROVIDER.getCounterFourDisabled()).append("</div>").append(totalUniques > 3 * ITEMS_PER_PAGE && !buyback
                ? "<div class='overlay' " + (currentPage == 3 ? "" : "id='" + pageIdMod + "3'") + "></div>"
                : "<div class='overlay disabled'></div>").append("</div>").append("<div class='square-button max").append(currentPage == 4 ? " selected" : "").append("'>").append("<div style='width:80%;height:80%;position:absolute;left:0; bottom:0;'>").append(totalUniques > 4 * ITEMS_PER_PAGE && !buyback ? SVGImages.SVG_IMAGE_PROVIDER.getInventoryIcon() : SVGImages.SVG_IMAGE_PROVIDER.getInventoryIconDisabled()).append("</div>").append("<div style='width:50%;height:50%;position:absolute;right:4px; top:0;'>").append(currentPage == 4 ? SVGImages.SVG_IMAGE_PROVIDER.getCounterFive() : SVGImages.SVG_IMAGE_PROVIDER.getCounterFiveDisabled()).append("</div>").append(totalUniques > 4 * ITEMS_PER_PAGE && !buyback
                ? "<div class='overlay' " + (currentPage == 4 ? "" : "id='" + pageIdMod + "4'") + "></div>"
                : "<div class='overlay disabled'></div>").append("</div>").append(renderQuestTab
                ? "<div class='square-button max" + (currentPage == 5 ? " selected" : "") + "'>"
                + "<div style='width:100%;height:100%;position:absolute;left:0; bottom:0;'>" + (hasQuestItems ? SVGImages.SVG_IMAGE_PROVIDER.getQuestInventoryIcon() : SVGImages.SVG_IMAGE_PROVIDER.getQuestInventoryIconDisabled()) + "</div>"
                + (hasQuestItems && !buyback
                ? "<div class='overlay' id='" + pageIdMod + "5'></div>"
                : "<div class='overlay disabled' id='" + pageIdMod + "5'></div>")
                + "</div>"
                : "").append("</div>");

        inventorySB.append("<div class='container-full-width' style='width:85%; margin:0;'>");
        if (buyback) {
            for (int i = Main.game.getPlayer().getBuybackStack().size() - 1; i >= 0; i--) {
                ShopTransaction itemBuyback = Main.game.getPlayer().getBuybackStack().get(i);

                if (itemBuyback != null) {
                    // Clothing:
                    if (itemBuyback.getAbstractItemSold() instanceof AbstractClothing) {
                        inventorySB.append(getBuybackItemPanel(itemBuyback, "CLOTHING_" + i));

                        // Weapon:
                    } else if (itemBuyback.getAbstractItemSold() instanceof AbstractWeapon) {
                        inventorySB.append(getBuybackItemPanel(itemBuyback, "WEAPON_" + i));

                        // Item:
                    } else {
                        inventorySB.append(getBuybackItemPanel(itemBuyback, "ITEM_" + i));
                    }
                }
            }

            // Fill space:
            for (int i = ITEMS_PER_PAGE; i > Main.game.getPlayer().getBuybackStack().size(); i--) {
                inventorySB.append("<div class='inventory-item-slot'></div>");
            }

        } else {
            inventorySB.append(getInventoryIconsForPage(currentPage, charactersInventoryToRender, idModifier));
        }
        inventorySB.append("</div>");

        String style = "box-sizing:border-box; float:left; background-color:" + PresetColour.BACKGROUND_ALT.toWebHexString() + "; border-radius:5px; padding:0 2px 0 2px; margin:0 1%; text-align:center;";
        String buttonStyle = "width:5.25%; border:0; text-align:center; padding:0; margin:0 0.75%; float:left;";
        String styleWidth = "calc(38.5% - 4px)";
        String styleWidthDouble = "calc(77% - 4px)";
        boolean transferDisabled = InventoryDialogue.getNPCInventoryInteraction() != InventoryInteraction.FULL_MANAGEMENT;
        if (charactersInventoryToRender != null) {
            // 85% is the encompassing width
            // Changed to 100% in 0.4.2 | Old values: width:26.3%; width:33.416%;
            if (charactersInventoryToRender.isPlayer()) {
                inventorySB.append("<div style='").append(style).append(" width:").append(styleWidth).append(";'>").append(UtilText.formatAsEssences(charactersInventoryToRender.getEssenceCount(), "b", true)).append("</div>").append("<div style='").append(style).append(" width:").append(styleWidth).append(";'>").append(UtilText.formatAsMoney(charactersInventoryToRender.getMoney(), "b")).append("</div>").append("<div class='normal-button").append(transferDisabled ? " disabled" : "").append("' id='").append(idModifier).append("MONEY_TRANSFER_SMALL' style='").append(buttonStyle).append("'>[style.").append(transferDisabled ? "colourDisabled" : "colourMinorGood").append("(&gt;)]</div>").append("<div class='normal-button").append(transferDisabled ? " disabled" : "").append("' id='").append(idModifier).append("MONEY_TRANSFER_AVERAGE' style='").append(buttonStyle).append("'>[style.").append(transferDisabled ? "colourDisabled" : "colourGood").append("(&gt;)]</div>").append("<div class='normal-button").append(transferDisabled ? " disabled" : "").append("' id='").append(idModifier).append("MONEY_TRANSFER_BIG' style='").append(buttonStyle).append("'>[style.").append(transferDisabled ? "colourDisabled" : "colourExcellent").append("(&gt;)]</div>");

            } else {
                inventorySB.append("<div class='normal-button").append(transferDisabled ? " disabled" : "").append("' id='").append(idModifier).append("MONEY_TRANSFER_BIG' style='").append(buttonStyle).append("'>[style.").append(transferDisabled ? "colourDisabled" : "colourExcellent").append("(&lt;)]</div>").append("<div class='normal-button").append(transferDisabled ? " disabled" : "").append("' id='").append(idModifier).append("MONEY_TRANSFER_AVERAGE' style='").append(buttonStyle).append("'>[style.").append(transferDisabled ? "colourDisabled" : "colourGood").append("(&lt;)]</div>").append("<div class='normal-button").append(transferDisabled ? " disabled" : "").append("' id='").append(idModifier).append("MONEY_TRANSFER_SMALL' style='").append(buttonStyle).append("'>[style.").append(transferDisabled ? "colourDisabled" : "colourMinorGood").append("(&lt;)]</div>").append("<div style='").append(style).append(" width:").append(styleWidth).append(";'>").append(transferDisabled ? UtilText.formatAsMoney("[style.colourUnknown(Unknown)]", "b") : UtilText.formatAsMoney(charactersInventoryToRender.getMoney(), "b")).append("</div>").append("<div style='").append(style).append(" width:").append(styleWidth).append(";'>").append(UtilText.formatAsEssences(charactersInventoryToRender.getEssenceCount(), "b", true)).append("</div>");
            }

        } else {
            inventorySB.append("<div class='normal-button").append(transferDisabled ? " disabled" : "").append("' id='").append(idModifier).append("MONEY_TRANSFER_BIG' style='").append(buttonStyle).append(" margin:0 0.75% 0 2%;'>[style.").append(transferDisabled ? "colourDisabled" : "colourExcellent").append("(&lt;)]</div>").append("<div class='normal-button").append(transferDisabled ? " disabled" : "").append("' id='").append(idModifier).append("MONEY_TRANSFER_AVERAGE' style='").append(buttonStyle).append("'>[style.").append(transferDisabled ? "colourDisabled" : "colourGood").append("(&lt;)]</div>").append("<div class='normal-button").append(transferDisabled ? " disabled" : "").append("' id='").append(idModifier).append("MONEY_TRANSFER_SMALL' style='").append(buttonStyle).append("'>[style.").append(transferDisabled ? "colourDisabled" : "colourMinorGood").append("(&lt;)]</div>").append("<div style='").append(style).append(" width:").append(styleWidthDouble).append(";'>").append(UtilText.formatAsMoney(Main.game.getPlayerCell().getInventory().getMoney(), "b")).append("</div>");
        }

        inventorySB.append("</div>");

        return inventorySB.toString();
    }

    public String getGiftDiv(GameCharacter receiver) {
        inventorySB.setLength(0);

        Map<AbstractCoreItem, Integer> giftsAvailable = new HashMap<>();
        for (Entry<AbstractItem, Integer> entry : Main.game.getPlayer().getAllItemsInInventory().entrySet()) {
            if (receiver.getGiftReaction(entry.getKey(), false) != null) {
                giftsAvailable.put(entry.getKey(), entry.getValue());
            }
        }
        for (Entry<AbstractClothing, Integer> entry : Main.game.getPlayer().getAllClothingInInventory().entrySet()) {
            if (receiver.getGiftReaction(entry.getKey(), false) != null) {
                giftsAvailable.put(entry.getKey(), entry.getValue());
            }
        }
        for (Entry<AbstractWeapon, Integer> entry : Main.game.getPlayer().getAllWeaponsInInventory().entrySet()) {
            if (receiver.getGiftReaction(entry.getKey(), false) != null) {
                giftsAvailable.put(entry.getKey(), entry.getValue());
            }
        }

        if (giftsAvailable.isEmpty()) {
            inventorySB.append("<div class='container-full-width'>" + "<p style='width:100%; text-align:center; padding:0 margin:0;'>" + "<b style='color:").append(PresetColour.GENERIC_MINOR_BAD.toWebHexString()).append(";'>No Suitable Gifts In Inventory</b>").append("</p>").append("</div>");
        } else {
            inventorySB.append("<div class='container-full-width'>" + "<p style='width:100%; text-align:center; padding:0 margin:0;'>" + "<b style='color:").append(PresetColour.GENERIC_MINOR_GOOD.toWebHexString()).append(";'>Available Gifts</b>").append("</p>").append("<div class='inventory-not-equipped'>");

            appendDivsForGiftsToInventory(inventorySB, giftsAvailable, "GIFT_");

            for (int i = 8 - giftsAvailable.size(); i > 0; i--) {
                inventorySB.append("<div class='inventory-item-slot unequipped'></div>");
            }

            inventorySB.append("</div>"
                    + "</div>");
        }

        return inventorySB.toString();
    }

    private String getDefaultAttributeColumnHeader(boolean rightColumn) {
        if (rightColumn) {
            if (Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.badEnd)) {
                return "<div class='full-width-container'>"
                        + "<p class='character-name' style='color:" + PresetColour.GENERIC_BAD_END.toWebHexString() + ";'>"
                        + "Bad End"
                        + "</p>"
                        + "</div>"
                        + "<div class='full-width-container' style='margin:0;padding:0;'>"
                        + "<p style='text-align:center;'>"
                        + "<i>" + Main.getProperties().badEndTitle + "</i>"
                        + "</p>"
                        + "</div>";

            } else {
                AbstractPlaceType place = Main.game.getPlayer().getWorldLocation().getStandardPlace();

                if (Main.game.getPlayer().getLocationPlace() != null) {
                    place = Main.game.getPlayer().getLocationPlace().getPlaceType();
                }

                String mainTitle = "";
                if (place.isLand()) {
                    if (place.isWater()) {
                        mainTitle = "[style.colourTan(Land)] &amp; [style.colourBlueLight(Water)]";
                    } else {
                        mainTitle = "[style.colourTan(Land)]";
                    }

                } else {
                    mainTitle = "[style.colourBlueLight(Water)]";
                }

                String placeTitle = place.isDangerous() ? "[style.colourBad(Dangerous)]" : "[style.colourGood(Safe)]";

                return "<div class='full-width-container'>"
                        + "<p class='character-name'>"
                        + UtilText.parse(mainTitle)
                        + "</p>"
                        + "</div>"
                        + "<div class='full-width-container' style='margin:0;padding:0;'>"
                        + "<p style='text-align:center;'>"
                        + UtilText.parse(placeTitle)
                        + "</p>"
                        + "</div>";
            }

        } else {
            String mainTitle = Main.game.getPlayer().getWorldLocation().getName();
            String mainTitleColour = Main.game.getPlayer().getWorldLocation().getColour().toWebHexString();

            AbstractPlaceType place = Main.game.getPlayer().getWorldLocation().getStandardPlace();

            if (Main.game.getPlayer().getLocationPlace() != null) {
                place = Main.game.getPlayer().getLocationPlace().getPlaceType();
            }

            String placeTitle = place.getName();
            String placeColour = place.getColour().toWebHexString();

//			if(Main.game.isInGlobalMap()) {
//				mainTitle = place.getName();
//				mainTitleColour = place.getColour().toWebHexString();
//
//				placeTitle = place.isLand()?"Terrestrial":"Aquatic";
//				placeColour = place.isLand()?PresetColour.SPELL_SCHOOL_EARTH.toWebHexString():PresetColour.SPELL_SCHOOL_WATER.toWebHexString();
//			}

            return "<div class='full-width-container'>"
                    + "<p class='character-name' style='color:" + mainTitleColour + ";'>"
                    + Util.capitaliseSentence(UtilText.parse(mainTitle))
                    + "</p>"
                    + "</div>"
                    + "<div class='full-width-container' style='margin:0;padding:0;'>"
                    + "<p style='text-align:center;" + (placeColour.isEmpty() ? "" : " color:" + placeColour + ";") + "'>"
                    + Util.capitaliseSentence(UtilText.parse(placeTitle))
                    + "</p>"
                    + "</div>";
        }
    }

    public void renderAttributesPanelLeft() {
        uiAttributeSB.setLength(0);

        uiAttributeSB.append(
                "<body onLoad='scrollEventLogToBottom()'>");

        if (Main.game.isInSex()) {
            // Name box:
            uiAttributeSB.append("<div class='full-width-container' style='background-color:").append(PresetColour.BACKGROUND_DARK.toWebHexString()).append("; border-radius:5px; margin-bottom:8px;'>").append("<div class='full-width-container'>").append("<p class='character-name' style='color:").append(Main.sex.isDom(Main.game.getPlayer())
                    ? PresetColour.GENERIC_SEX_AS_DOM.toWebHexString() + ";'>Dominant" + (Main.sex.getDominantParticipants(true).size() > 1 ? "s" : "") + "</b>"
                    : PresetColour.GENERIC_SEX.toWebHexString() + ";'>Submissive" + (Main.sex.getSubmissiveParticipants(true).size() > 1 ? "s" : "") + "</b>").append("</p>").append("</div>").append("</div>").append("<div class='full-width-container' style='height: calc(100% - ").append(Main.game.getCurrentDialogueNode().getDialogueNodeType() == DialogueNodeType.INVENTORY && Main.game.isEnchantmentCapacityEnabled()
                    ? "128"
                    : "134").append("vw); overflow-y: auto;'>");

            if (Main.sex.isDom(Main.game.getPlayer())) {
                for (GameCharacter character : Main.sex.getDominantParticipants(true).keySet()) {
                    uiAttributeSB.append(getCharacterPanelSexDiv(Main.sex.getDominantParticipants(true).size() > 1, character.isPlayer() ? "PLAYER_" : "NPC_" + character.getId() + "_", character));
                }

            } else {
                for (GameCharacter character : Main.sex.getSubmissiveParticipants(true).keySet()) {
                    uiAttributeSB.append(getCharacterPanelSexDiv(Main.sex.getSubmissiveParticipants(true).size() > 1, character.isPlayer() ? "PLAYER_" : "NPC_" + character.getId() + "_", character));
                }
            }

            uiAttributeSB.append("</div>");

        } else if (Main.game.isInCombat()) {
            // Name box:
            uiAttributeSB.append("<div class='full-width-container' style='background-color:").append(PresetColour.BACKGROUND_DARK.toWebHexString()).append("; border-radius:5px; margin-bottom:8px;'>").append("<div class='full-width-container'>").append("<p class='character-name' style='color:").append(PresetColour.BASE_GREEN.toWebHexString()).append(";'>").append("Allies").append("</p>").append("</div>").append("</div>").append("<div class='full-width-container' style='height: calc(100% - ").append(Main.game.getCurrentDialogueNode().getDialogueNodeType() == DialogueNodeType.INVENTORY && Main.game.isEnchantmentCapacityEnabled()
                    ? "128"
                    : "134").append("vw); overflow-y: auto;'>");

            uiAttributeSB.append(getCharacterPanelDiv(!Main.combat.getAllies(Main.game.getPlayer()).isEmpty(), "PLAYER_", Main.game.getPlayer()));

            for (GameCharacter character : Main.combat.getAllies(Main.game.getPlayer())) {
                uiAttributeSB.append(getCharacterPanelDiv(true, "NPC_" + character.getId() + "_", character));
            }

            uiAttributeSB.append("</div>");

        } else {
            // Default place name box:
            uiAttributeSB.append("<div class='full-width-container' style='background-color:").append(PresetColour.BACKGROUND_DARK.toWebHexString()).append("; border-radius:5px; margin-bottom:8px;'>").append(getDefaultAttributeColumnHeader(false)).append("</div>").append("<div class='full-width-container' style='height:calc(100% - ").append(Main.game.getCurrentDialogueNode().getDialogueNodeType() == DialogueNodeType.INVENTORY && Main.game.isEnchantmentCapacityEnabled()
                    ? "136"
                    : "142").append("vw); overflow-y: auto;'>");

            uiAttributeSB.append(getCharacterPanelDiv(!Main.game.getPlayer().getCompanions().isEmpty(), "PLAYER_", Main.game.getPlayer()));

            for (GameCharacter character : Main.game.getPlayer().getCompanions()) {
                uiAttributeSB.append(getCharacterPanelDiv(true, "NPC_" + character.getId() + "_", character));
//				for(GameCharacter characterCompanion : character.getCompanions()) {
//					uiAttributeSB.append(getCharacterPanelDiv(true, "NPC_"+characterCompanion.getId()+"_", characterCompanion));
//
//				}
            }

            uiAttributeSB.append("</div>");
        }

        uiAttributeSB.append("</div>");


        Colour background = PresetColour.BACKGROUND_DAY;
        switch (Main.game.getCurrentDayPeriod()) {
            case CIVIL_TWILIGHT:
                background = PresetColour.BACKGROUND_TWILIGHT;
                break;
            case DAY:
                break;
            case ASTRONOMICAL_TWILIGHT:
            case NAUTICAL_TWILIGHT:
            case NIGHT:
                background = PresetColour.BACKGROUND_NIGHT;
                break;
        }

        uiAttributeSB.append("<div class='full-width-container' style='background-color:").append(background.toWebHexString()).append("; border-radius:5px; margin-bottom:1px; padding:4px;'>");
        if (Main.game.getCurrentDialogueNode().getDialogueNodeType() == DialogueNodeType.INVENTORY && Main.game.isEnchantmentCapacityEnabled()) {
            int enchantmentPointsUsed = Main.game.getPlayer().getEnchantmentPointsUsedTotal();
            uiAttributeSB.append(UtilText.parse(
                    "<div class='full-width-container' style='text-align:center;'>"
                            + "<div class='overlay-alt' id='INVENTORY_ENCHANTMENT_LIMIT' style='cursor:default;'>"
                            + "[style.colourEnchantment(" + Util.capitaliseSentence(Attribute.ENCHANTMENT_LIMIT.getName()) + ")]: "
                            + (enchantmentPointsUsed > Main.game.getPlayer().getAttributeValue(Attribute.ENCHANTMENT_LIMIT)
                            ? "[style.colourBad("
                            : (enchantmentPointsUsed == Main.game.getPlayer().getAttributeValue(Attribute.ENCHANTMENT_LIMIT)
                            ? "[style.colourGood("
                            : "[style.colourMinorGood("))
                            + enchantmentPointsUsed + ")]" + "/" + Math.round(Main.game.getPlayer().getAttributeValue(Attribute.ENCHANTMENT_LIMIT))
                            + "</div>"
                            + "</div>"));

        } else {
            uiAttributeSB.append("<div class='half-width-container' style='text-align:center; float:left; width:60%'>");
            uiAttributeSB.append("<div class='overlay-alt' style='float:left;' id='DATE_DISPLAY_TOGGLE'>");
            uiAttributeSB.append("<div class='item-inline' style='float:left;'>").append(SVGImages.SVG_IMAGE_PROVIDER.getCalendarIcon()).append("</div>");

            uiAttributeSB.append((Main.getProperties().hasValue(PropertyValue.calendarDisplay)
                    ? Main.game.getDisplayDate(false)
                    : "Day " + Main.game.getDayNumber()));

            // Day-of-week indicator:
            uiAttributeSB.append("<div class='full-width-container' style='text-align:center; float:left; margin:0; padding:0; width:100%;'>");
            String[] weekDays = new String[]{"M", "T", "W", "T", "F", "S", "S"};
            for (int i = 0; i < 7; i++) {
                if (!Main.game.isBadEnd() && Main.game.getDateNow().getDayOfWeek().getValue() == i + 1) {
                    uiAttributeSB.append("<div class='full-width-container' style='height:12px; box-sizing:border-box; text-align:center; border-radius:5px; font-size:10px;" + " border:1px solid ").append(PresetColour.TEXT.toWebHexString()).append("; float:left; margin:0; padding:0; width:14.28%;'>");
                } else {
                    uiAttributeSB.append("<div class='full-width-container' style='height:12px; text-align:center; font-size:10px; float:left; margin:0; padding:0; width:14.28%; color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>");
                }
                uiAttributeSB.append(weekDays[i]);
                uiAttributeSB.append("</div>");
            }
            uiAttributeSB.append("</div>");
            uiAttributeSB.append("</div>");
            uiAttributeSB.append("</div>");


            uiAttributeSB.append("<div class='half-width-container' style='text-align:center; float:left; width:40%;'>"
                    + "<div class='overlay-alt' style='padding: 6px 0;' id='TWENTY_FOUR_HOUR_TIME_TOGGLE'>");

            if (Main.game.getPlayer().getClothingInSlot(InventorySlot.WRIST) != null
                    && (Main.game.getPlayer().getClothingInSlot(InventorySlot.WRIST).getClothingType().equals(ClothingType.WRIST_WOMENS_WATCH)
                    || Main.game.getPlayer().getClothingInSlot(InventorySlot.WRIST).getClothingType().equals(ClothingType.WRIST_MENS_WATCH))) {
                uiAttributeSB.append("<div class='item-inline' style='float:left;'>").append(Main.game.getPlayer().getClothingInSlot(InventorySlot.WRIST).getSVGEquippedString(Main.game.getPlayer())).append("</div>");

            } else {
                uiAttributeSB.append("<div class='item-inline' style='float:left;'>").append(SVGImages.SVG_IMAGE_PROVIDER.getJournalIcon()).append("</div>");
            }

            if (Main.game.isBadEnd()) {
                uiAttributeSB.append("<span style='color:").append(PresetColour.GENERIC_BAD.toWebHexString()).append(";'>??:??</span>");
            } else {
                uiAttributeSB.append(Units.time(Main.game.getDateNow()));
            }

            uiAttributeSB.append("</div>"
                    + "</div>");

        }
        uiAttributeSB.append("</div>");

        if (Main.game.getCurrentDialogueNode().getDialogueNodeType() == DialogueNodeType.INVENTORY || Main.game.isInCombat() || Main.game.isInSex()) {
            uiAttributeSB.append(getInventoryEquippedPanel(Main.game.getPlayer()));

        } else {
            uiAttributeSB.append("<div>").append(renderedHTMLMap()).append("</div>");
        }

        uiAttributeSB.append("</body>");

        if (Main.mainController != null) {
            if (Main.game.getCurrentDialogueNode() != null) {
                if (renderedDialogueNode != Main.game.getCurrentDialogueNode()) {
                    renderedDialogueNode = Main.game.getCurrentDialogueNode();

                }
            }
        }

        Main.mainController.setAttributePanelContent(uiAttributeSB.toString());
    }

    public boolean isRenderingCharactersRightPanel() {
        return Main.game.isInSex()
                || Main.game.isInCombat()
                || (getCharacterToRender() != null
                && (Main.game.getCurrentDialogueNode().getDialogueNodeType() == DialogueNodeType.CHARACTERS_PRESENT
                || Main.game.getCurrentDialogueNode() == PhoneDialogue.CONTACTS_CHARACTER
                || Main.game.getDialogueFlags().getManagementCompanion() != null
                || (Main.game.getCurrentDialogueNode().getDialogueNodeType() == DialogueNodeType.INVENTORY && InventoryDialogue.getInventoryNPC() != null)));
    }

    public void renderAttributesPanelRight() {
        uiAttributeSB.setLength(0);

        uiAttributeSB.append(
                "<body onLoad='scrollEventLogToBottom()'>"
                        + " <script>"
                        + "function scrollEventLogToBottom() {document.getElementById('event-log-inner-id').scrollTop = document.getElementById('event-log-inner-id').scrollHeight;}"
                        + "</script>");

        if (isRenderingCharactersRightPanel()) {
            if (Main.game.isInSex()) {
                // Name box:
                uiAttributeSB.append("<div class='full-width-container' style='background-color:").append(PresetColour.BACKGROUND_DARK.toWebHexString()).append("; border-radius:5px; margin-bottom:8px;'>").append("<div class='full-width-container'>").append("<p class='character-name' style='color:").append(!Main.sex.isDom(Main.game.getPlayer())
                        ? PresetColour.GENERIC_SEX_AS_DOM.toWebHexString() + ";'>Dominant" + (Main.sex.getDominantParticipants(true).size() > 1 ? "s" : "")
                        : PresetColour.GENERIC_SEX.toWebHexString() + ";'>Submissive" + (Main.sex.getSubmissiveParticipants(true).size() > 1 ? "s" : "")).append("</p>").append("</div>").append("</div>").append("<div class='full-width-container' style='height: calc(100% - 128vw); overflow-y: auto;'>");

                if (!Main.sex.isDom(Main.game.getPlayer())) {
                    for (GameCharacter character : Main.sex.getDominantParticipants(true).keySet()) {
                        uiAttributeSB.append(getCharacterPanelSexDiv(Main.sex.getDominantParticipants(true).size() > 1, "NPC_" + character.getId() + "_", character));
                    }

                } else {
                    for (GameCharacter character : Main.sex.getSubmissiveParticipants(true).keySet()) {
                        uiAttributeSB.append(getCharacterPanelSexDiv(Main.sex.getSubmissiveParticipants(true).size() > 1, "NPC_" + character.getId() + "_", character));
                    }
                }
                uiAttributeSB.append("</div>");


            } else if (Main.game.isInCombat()) {

                // Name box:
                uiAttributeSB.append("<div class='full-width-container' style='background-color:").append(PresetColour.BACKGROUND_DARK.toWebHexString()).append("; border-radius:5px; margin-bottom:8px;'>").append("<div class='full-width-container'>").append("<p class='character-name' style='color:").append(PresetColour.BASE_CRIMSON.toWebHexString()).append(";'>").append(Main.combat.getEnemies(Main.game.getPlayer()).size() > 1 ? "Enemies" : "Enemy").append("</p>").append("</div>").append("</div>").append("<div class='full-width-container' style='height: calc(100% - 128vw); overflow-y: auto;'>");

                for (GameCharacter character : Main.combat.getEnemies(Main.game.getPlayer())) {
                    uiAttributeSB.append(getCharacterPanelDiv(Main.combat.getEnemies(Main.game.getPlayer()).size() > 1, "NPC_" + character.getId() + "_", character));
                }


                uiAttributeSB.append("</div>");


            } else {
                // Default place name box:
                uiAttributeSB.append("<div class='full-width-container' style='background-color:").append(PresetColour.BACKGROUND_DARK.toWebHexString()).append("; border-radius:5px; margin-bottom:8px;'>").append(getDefaultAttributeColumnHeader(true)).append("</div>").append("<div class='full-width-container' style='height: calc(100% - 138vw); overflow-y: auto;'>");

                uiAttributeSB.append(getCharacterPanelDiv(true, "NPC_" + getCharacterToRender().getId() + "_", getCharacterToRender()));

                uiAttributeSB.append("</div>");

            }

            uiAttributeSB.append("<div class='full-width-container' style='background-color:").append(PresetColour.BACKGROUND_DARK.toWebHexString()).append("; border-radius:5px; margin-bottom:1px; padding:4px;'>");
            if (Main.game.getCurrentDialogueNode().getDialogueNodeType() == DialogueNodeType.INVENTORY && Main.game.isEnchantmentCapacityEnabled() && getCharacterToRender() != null) {
                int enchantmentPointsUsed = getCharacterToRender().getEnchantmentPointsUsedTotal();
                uiAttributeSB.append(UtilText.parse(
                        "<div class='full-width-container' style='text-align:center;'>"
                                + "<div class='overlay-alt' id='INVENTORY_ENCHANTMENT_LIMIT_NPC' style='cursor:default;'>"
                                + "[style.colourEnchantment(" + Util.capitaliseSentence(Attribute.ENCHANTMENT_LIMIT.getName()) + ")]: "
                                + (enchantmentPointsUsed > getCharacterToRender().getAttributeValue(Attribute.ENCHANTMENT_LIMIT)
                                ? "[style.colourBad("
                                : (enchantmentPointsUsed == getCharacterToRender().getAttributeValue(Attribute.ENCHANTMENT_LIMIT)
                                ? "[style.colourGood("
                                : "[style.colourMinorGood("))
                                + enchantmentPointsUsed + ")]" + "/" + Math.round(getCharacterToRender().getAttributeValue(Attribute.ENCHANTMENT_LIMIT))
                                + "</div>"
                                + "</div>"));

            } else {
                uiAttributeSB.append("<div class='full-width-container' style='text-align:center;'>" + "<p style='white-space: nowrap;  overflow: hidden;  text-overflow: ellipsis;'>").append(getCharacterToRender() == null
                        ? "No Character"
                        : UtilText.parse(getCharacterToRender(), "[npc.NamePos] " + (isRenderingTattoosRight() ? "Markings" : "Inventory"))).append("</p>").append("</div>");
            }
            uiAttributeSB.append("</div>");

            uiAttributeSB.append(getInventoryEquippedPanel(getCharacterToRender()));

        } else {
            uiAttributeSB.append("<div class='full'>");

            AbstractPlaceType place = Main.game.getPlayer().getWorldLocation().getStandardPlace();
            if (Main.game.getPlayer().getLocationPlace() != null) {
                place = Main.game.getPlayer().getLocationPlace().getPlaceType();
            }
            // Default place name box:
            uiAttributeSB.append("<div class='full-width-container' style='background-color:").append(PresetColour.BACKGROUND_DARK.toWebHexString()).append("; border-radius:5px; margin-bottom:8px;'>").append(getDefaultAttributeColumnHeader(true)).append("</div>");

            // Characters Present:
            uiAttributeSB.append("<div class='attribute-container effects'>"
                    + "<p style='text-align:center;padding:0;margin:0;'><b>Characters Present</b></p>");
            List<NPC> charactersPresent = Main.game.getCharactersPresent();
            for (GameCharacter c : Main.game.getCharactersPresent()) {
                if (c.isElementalSummoned() && !c.getElemental().isActive()) {
                    charactersPresent.add(c.getElemental());
                }
            }
            Set<AbstractSubspecies> subspeciesSet = new HashSet<>();
            if (place.getPopulation() != null) {
                for (Population pop : place.getPopulation()) {
                    subspeciesSet.addAll(pop.getSpecies().keySet());
                }
            }
            if (charactersPresent.isEmpty() && (place.getPopulation() == null || place.getPopulation().isEmpty() || subspeciesSet.isEmpty())) {
                uiAttributeSB.append("<p style='text-align:center;padding:0;margin:0;'><span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>None...</span></p>");

            } else {
                int count = 0;
                for (NPC character : charactersPresent) {
//					if(!character.isRaceConcealed()) {
                    uiAttributeSB.append("<div class='event-log-entry' style='background:").append(getEntryBackgroundColour(count % 2 == 0)).append(";'>").append("<div class='icon' style='width:11%; left:0; top:0; margin:0 8px 0 0; padding:0;'>").append("<div class='icon-content'>").append(character.isRaceConcealed() ? SVGImages.SVG_IMAGE_PROVIDER.getRaceUnknown() : character.getMapIcon()).append("</div>").append("</div>").append(" <div style='color:").append(character.getFemininity().getColour().toWebHexString()).append(";'>").append(!character.getArtworkList().isEmpty() && Main.getProperties().hasValue(PropertyValue.artwork) ? "&#128247; " : "").append(UtilText.parse(character, "[npc.Name(A)]")).append("<div class='overlay-inventory' id='NPC_").append(character.getId()).append("_").append(Attribute.EXPERIENCE.getName()).append("' style='width:calc(11% + 8px);'></div>").append("<div class='overlay-inventory' id='NPC_").append(character.getId()).append("_ATTRIBUTES' style='width:calc(89% - 8px); left:calc(11% + 8px);'></div>").append("</div>").append("</div>");
//					} else {
//						uiAttributeSB.append(
//								"<div class='event-log-entry' style='background:"+getEntryBackgroundColour(count%2==0)+";'>"
//									+ "<div class='icon' style='width:11%; left:0; top:0; margin:0 8px 0 0; padding:0;'>"
//										+ "<div class='icon-content'>"
//											+ (character.isRaceConcealed()?SVGImages.SVG_IMAGE_PROVIDER.getRaceUnknown():character.getMapIcon())
//										+ "</div>"
//									+ "</div>"
//									+" <div style='color:"+PresetColour.BASE_GREY_DARK.toWebHexString()+";'>"
//										+(!character.getArtworkList().isEmpty() && Main.getProperties().hasValue(PropertyValue.artwork)?"&#128247; ":"")+ (character.isPlayerKnowsName()?character.getName("A"):"Unknown person")
//										+ "<div class='overlay-inventory' id='NPC_" + character.getId() + "_" + Attribute.EXPERIENCE.getName() + "' style='width:calc(11% + 8px);'></div>"
//										+ "<div class='overlay-inventory' id='NPC_"+character.getId()+"_ATTRIBUTES' style='width:calc(89% - 8px); left:calc(11% + 8px);'></div>"
//									+"</div>"
//								+ "</div>");
//					}//The commented section remove the ability to click the character to see the character present dialogue and the overlay that contain the stats. Clicking on the icon will still bring you to the character present dialogue
                    count++;
                }

                int i = 0;
                List<Population> placePopulation = Main.game.getPlayer().getLocationPlace().getPlaceType().getPopulation();
                if (placePopulation != null) {
                    for (Population pop : placePopulation) {
                        if (pop != null && !pop.getSpecies().isEmpty()) {
                            uiAttributeSB.append("<div class='event-log-entry' style='background:").append(getEntryBackgroundColour(count % 2 == 0)).append(";'>").append("<div class='icon' style='width:11%; left:0; top:0; margin:0 8px 0 0; padding:0;'>").append("<div class='icon-content'>").append(pop.getSpecies().size() > 1
                                    ? SVGImages.SVG_IMAGE_PROVIDER.getPeopleIcon()
                                    : pop.getSpecies().keySet().iterator().next().getSVGString(null)).append("</div>").append("</div>").append(" <div style='color:").append(PresetColour.BASE_GREY.toWebHexString()).append(";'>").append(Util.capitaliseSentence(pop.getDescription(true))).append("<div class='overlay-inventory' id='PLACE_POPULATION_").append(i).append("' style='width:100%;'></div>").append("</div>").append("</div>");
                        }
                        i++;
                    }
                }
            }
            uiAttributeSB.append("</div>");


            // Items Present:
            uiAttributeSB.append("<div class='attribute-container effects'>"
                    + "<p style='text-align:center;padding:0;margin:0;'><b>Items Present</b></p>");

            int count = 0;
            if (Main.game.isInNewWorld()) {
                if (Main.game.getPlayerCell().getInventory().getMoney() > 0) {
                    uiAttributeSB.append("<div class='event-log-entry' style='background:").append(getEntryBackgroundColour(false)).append(";'>").append(UtilText.formatAsMoney(Main.game.getPlayerCell().getInventory().getMoney(), "span")).append("<div class='overlay-inventory' id='MONEY_ON_FLOOR'></div>").append("</div>");
                    count++;
                }

                for (Entry<AbstractWeapon, Integer> entry : Main.game.getPlayerCell().getInventory().getAllWeaponsInInventory().entrySet()) {
                    if (count % 2 == 0) {
                        uiAttributeSB.append("<div class='event-log-entry' style='background:").append(getEntryBackgroundColour(false)).append(";'>").append(entry.getValue()).append("x ").append(Util.capitaliseSentence(UtilText.parse(entry.getKey().getName(false, true)))).append("<div class='overlay-inventory' id='WEAPON_FLOOR_").append(entry.getKey().hashCode()).append("'></div>").append("</div>");
                    } else {
                        uiAttributeSB.append("<div class='event-log-entry' style='background:").append(getEntryBackgroundColour(true)).append(";'>").append(entry.getValue()).append("x ").append(Util.capitaliseSentence(UtilText.parse(entry.getKey().getName(false, true)))).append("<div class='overlay-inventory' id='WEAPON_FLOOR_").append(entry.getKey().hashCode()).append("'></div>").append("</div>");
                    }
                    count++;
                }
                for (Entry<AbstractClothing, Integer> entry : Main.game.getPlayerCell().getInventory().getAllClothingInInventory().entrySet()) {
                    if (count % 2 == 0) {
                        uiAttributeSB.append("<div class='event-log-entry' style='background:").append(getEntryBackgroundColour(false)).append(";'>").append(entry.getValue()).append("x ").append(Util.capitaliseSentence(UtilText.parse(entry.getKey().getName(false, true)))).append("<div class='overlay-inventory' id='CLOTHING_FLOOR_").append(entry.getKey().hashCode()).append("'></div>").append("</div>");
                    } else {
                        uiAttributeSB.append("<div class='event-log-entry' style='background:").append(getEntryBackgroundColour(true)).append(";'>").append(entry.getValue()).append("x ").append(Util.capitaliseSentence(UtilText.parse(entry.getKey().getName(false, true)))).append("<div class='overlay-inventory' id='CLOTHING_FLOOR_").append(entry.getKey().hashCode()).append("'></div>").append("</div>");
                    }
                    count++;
                }
                for (Entry<AbstractItem, Integer> entry : Main.game.getPlayerCell().getInventory().getAllItemsInInventory().entrySet()) {
                    if (count % 2 == 0) {
                        uiAttributeSB.append("<div class='event-log-entry' style='background:").append(getEntryBackgroundColour(false)).append(";'>").append(entry.getValue()).append("x ").append(Util.capitaliseSentence(UtilText.parse(entry.getKey().getName(false, true)))).append("<div class='overlay-inventory' id='ITEM_FLOOR_").append(entry.getKey().hashCode()).append("'></div>").append("</div>");
                    } else {
                        uiAttributeSB.append("<div class='event-log-entry' style='background:").append(getEntryBackgroundColour(true)).append(";'>").append(entry.getValue()).append("x ").append(Util.capitaliseSentence(UtilText.parse(entry.getKey().getName(false, true)))).append("<div class='overlay-inventory' id='ITEM_FLOOR_").append(entry.getKey().hashCode()).append("'></div>").append("</div>");
                    }
                    count++;
                }
            }
            if (count == 0) {
                uiAttributeSB.append("<p style='text-align:center;padding:0;margin:0;'><span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>None...</span></p>");
            }
            uiAttributeSB.append(
                    "</div>");


            // Event log:
            uiAttributeSB.append(
                    "</div>"
                            + "<div class='event-log'>"
                            + "<p style='text-align:center;padding:0;margin:0;'><b>Event Log</b></p>"
                            + "<div class='event-log-inner' id='event-log-inner-id'>");

            if (Main.game.getEventLog().isEmpty()) {
                uiAttributeSB.append("<p style='text-align:center;padding:0;margin:0;'><span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>No events yet...</span></p>");
            }

            count = 0;

            List<EventLogEntry> youveCatToBeKittenMeRightMeow = new ArrayList<>(Main.game.getEventLog());
            Collections.reverse(youveCatToBeKittenMeRightMeow);
            for (EventLogEntry event : youveCatToBeKittenMeRightMeow) {
                if (count % 2 == 0) {
                    uiAttributeSB.append("<div class='event-log-entry' style='background:").append(getEntryBackgroundColour(false)).append(";'>").append(UtilText.parse(event.getFormattedEntry())).append("</div>");
                } else {
                    uiAttributeSB.append("<div class='event-log-entry' style='background:").append(getEntryBackgroundColour(true)).append(";'>").append(UtilText.parse(event.getFormattedEntry())).append("</div>");
                }
                count++;
            }
        }
        uiAttributeSB.append("</div>"
                + "</div>");

        uiAttributeSB.append("</body>");

        if (Main.mainController != null) {
            if (Main.game.getCurrentDialogueNode() != null) {
                if (renderedDialogueNode != Main.game.getCurrentDialogueNode()) {
                    renderedDialogueNode = Main.game.getCurrentDialogueNode();
                }
            }
        }

        Main.mainController.setRightPanelContent(uiAttributeSB.toString());
    }

    private Colour getPlayerIconColour(boolean isDangerous) {
        if (isDangerous) {
            return PresetColour.BASE_RED;
        } else {
            switch (Main.game.getPlayer().getFemininity()) {
                case ANDROGYNOUS:
                    return Femininity.ANDROGYNOUS.getColour();
                case FEMININE:
                case FEMININE_STRONG:
                    return Femininity.FEMININE_STRONG.getColour();
                case MASCULINE:
                case MASCULINE_STRONG:
                    return Femininity.MASCULINE_STRONG.getColour();
            }
            return Main.game.getPlayer().getFemininity().getColour();
        }
    }

    private String generateBackgroundStyle(AbstractPlaceType placeType, boolean dangerousTile, boolean discovered, double alpha) {
        if (placeType.equals(PlaceType.GENERIC_IMPASSABLE)) {
            return "background:transparent;";

        } else {
            return dangerousTile && discovered //&& !worldMap
                    ? getDangerousBackground(placeType)
                    : discovered
                    ? "background-color:" + placeType.getBackgroundColour().toRGBA(alpha) + ";"
                    : "background-color:" + PresetColour.MAP_BACKGROUND_UNEXPLORED.toRGBA(alpha) + ";";
        }
    }

    public String getFullMap(AbstractWorldType world, boolean withFastTravel, boolean withNPCIcons) {

//		long t1 = System.nanoTime();

        mapSB.setLength(0);

        Cell[][] grid = Main.game.getWorlds().get(world).getGrid();
        int divWidth = (Math.min(70, grid.length * 10));
        if (withFastTravel) {
            mapSB.append("<div class='container-full-width' style='background:transparent; display: flex;'>" + "<div class='container-full-width' style='width:").append(divWidth).append("%; margin:0; padding:0; float:left;'>");
        } else {
            mapSB.append("<div class='container-full-width' style='width:").append(divWidth).append("%; margin:2% ").append((100 - divWidth) / 2).append("%; float:left;'>");
        }

        float width = 100f / grid.length;
        for (int i = grid[0].length - 1; i >= 0; i--) {
            for (int j = 0; j < grid.length; j++) {
                Cell c = grid[j][i];

                boolean worldMap = world == WorldType.WORLD_MAP;
                boolean discovered = c.isDiscovered() || Main.game.isMapReveal();
                boolean showPathing = Main.game.getPlayer().getWorldLocation().equals(world) || Pathing.getMapTravelType() == MapTravelType.TELEPORT;
                boolean path = Pathing.getPathingCells().contains(c);
                boolean endPath = Pathing.getEndPoint() != null && new Vector2i(j, i).equals(Pathing.getEndPoint());
                boolean dangerousTile = c.getPlace().getPlaceType().isDangerous();
                AbstractPlaceType placeType = c.getPlace().getPlaceType();


                if (!discovered || placeType.equals(PlaceType.GENERIC_IMPASSABLE)) {
                    mapSB.append("<div class='map-icon' style='width:").append(width - 0.5).append("%; margin:0.25%; ").append(generateBackgroundStyle(placeType, dangerousTile, discovered, 1.0)).append("'></div>");

                } else {
                    String border = (c.getPlace() != null && placeType.getColour() != null
                            ? "border:1px solid " + placeType.getColour().getShadesRgbaFormat(0.5f)[2] + ";"
                            : "border:1px solid #ffffff;")
                            + (grid.length > 25
                            ? "width:" + (width) + "%; margin:0; border-radius:0; border-width:0.5px 0.5px 0.5px 0.5px; border-style:solid; border-color:"
//												+(dangerousTile
//														?BaseColour.RED_DARK.toWebHexString()
//														:PresetColour.GENERIC_GOOD.toWebHexString())
                            + "#222"
                            + ";"
                            : "");

                    boolean playerOnTile = Main.game.getPlayer().getWorldLocation() == world && Main.game.getPlayer().getLocation().getX() == j && Main.game.getPlayer().getLocation().getY() == i;
                    if (worldMap && Main.game.getPlayer().getWorldLocation() != WorldType.WORLD_MAP) {
                        playerOnTile = Main.game.getPlayer().getGlobalLocation().getX() == j && Main.game.getPlayer().getGlobalLocation().getY() == i;
                    }

                    boolean canTeleportToTile = !placeType.equals(PlaceType.GENERIC_IMPASSABLE)
                            && ((Main.game.getPlayer().getMana() >= Spell.TELEPORT.getModifiedCost(Main.game.getPlayer()) && c.isTravelledTo() && Main.game.getPlayer().isAbleToTeleport()) || Main.game.isDebugMode());

//					if(path) {
//						background = "background-color: "+(dangerousTile?PresetColour.GENERIC_MINOR_BAD:PresetColour.GENERIC_MINOR_GOOD).toWebHexString()+";";
//					}

                    mapSB.append("<div class='map-icon' style='width:").append(width - 0.5).append("%; margin:0.25%; ").append(border).append(" ").append(generateBackgroundStyle(placeType, dangerousTile, discovered, 1.0)).append(canTeleportToTile ? "cursor:pointer;" : "").append("' id='MAP_NODE_").append(i).append("_").append(j).append("'>").append(playerOnTile ? "<div class='overlay map-player' style='background-color:" + BaseColour.AQUA.toWebHexString() + ";'></div>" : "").append(showPathing && endPath && !playerOnTile ? "<div class='overlay map-player' style='background-color:" + (dangerousTile ? BaseColour.ORANGE : BaseColour.YELLOW).toWebHexString() + ";'></div>" : "").append(showPathing && path && !endPath && !playerOnTile
                            ? "<div class='overlay map-player' style='background-color:" + (dangerousTile ? PresetColour.GENERIC_BAD : PresetColour.GENERIC_GOOD).toWebHexString() + ";" + (worldMap ? "border-radius:0;" : "") + "'></div>"
                            : "").append(c.getPlace() != null && c.getPlace().getSVGString() != null
                            ? "<div class='map-icon-content' style='background-color:" + placeType.getColour().toWebHexString() + "; width:75%; height:75%; margin:12.5%; border-radius:50%;"
                            + (playerOnTile ? " border:2px solid " + getPlayerIconColour(dangerousTile).toWebHexString() + ";" : "") + "'>"
                            + c.getPlace().getSVGString() + "</div>"
                            : "").append(playerOnTile && (c.getPlace() == null || c.getPlace().getSVGString() == null)
                            ? getPlayerIcon(dangerousTile)
                            : "");

                    if (withNPCIcons) {
                        appendNPCIcon(Main.game.getWorlds().get(world), j, i, width);
                        appendItemsInAreaIcon(Main.game.getWorlds().get(world), j, i);
                    }
                    appendNotVisitedLayer(Main.game.getWorlds().get(world), j, i);

                    mapSB.append("</div>");
                }
            }
        }

        if (withFastTravel) {
            String buttonStyle = "width:60%; min-width:0; margin:0 20% 4px 20%;";
            mapSB.append("</div>"
                    + "<div class='container-full-width' style='width:28%; margin:0 0 0 2%; padding:0; float:left;'>"
                    + "<p style='text-align:center;'>[style.bold(Fast Travel)]</p>");

            for (MapTravelType type : MapTravelType.values()) {
                if (!type.isAvailable(null, Main.game.getPlayer())) {
                    mapSB.append("<div id='").append(type).append("' class='cosmetics-button disabled' style='").append(buttonStyle).append("'>").append(Util.capitaliseSentence(type.getName())).append("</div>");

                } else if (Pathing.getMapTravelType() == type) {
                    mapSB.append("<div id='").append(type).append("' class='cosmetics-button active' style='").append(buttonStyle).append("'>").append("<span style='color:").append(type.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(type.getName())).append("</span>").append("</div>");

                } else {
                    mapSB.append("<div id='").append(type).append("' class='cosmetics-button' style='").append(buttonStyle).append("'>").append("<span style='color:").append(type.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(type.getName())).append("</span>").append("</div>");
                }
            }

            mapSB.append(
                    "<br/>"
                            + "<p>");//style='margin-bottom:28px;'

            boolean travelDestinationSelected = !Pathing.getEndPoint().equals(Main.game.getPlayer().getLocation())
                    || (Pathing.getMapTravelType() == MapTravelType.TELEPORT && !Main.game.getPlayer().getWorldLocation().equals(Pathing.getDestinationWorld()));

            if (!travelDestinationSelected) {
                if (!world.equals(Main.game.getPlayer().getWorldLocation()) && Pathing.getMapTravelType() != MapTravelType.TELEPORT) {
                    mapSB.append("[style.italicsBad(You cannot use this fast travel method to travel between different maps.)]");

                } else if (Main.game.getSavedDialogueNode() != null && Main.game.getSavedDialogueNode().isTravelDisabled()) {
                    mapSB.append("[style.italicsBad(You cannot fast travel while in the middle of a scene!)]");

                } else if (!Pathing.getMapTravelType().isAvailable(null, Main.game.getPlayer())) {
                    mapSB.append("[style.italicsBad(").append(Pathing.getMapTravelType().getUnavailablilityDescription(null, Main.game.getPlayer())).append(")]");

                } else {
                    mapSB.append(Pathing.getMapTravelType().getUseInstructions());
                }

            } else {
                int minutes = Pathing.getTravelTime() / 60;
                int hours = minutes / 60;
                String time =
                        hours > 0
                                ? Util.capitaliseSentence(Util.intToString(hours)) + " hour" + (hours == 1 ? "" : "s")
                                + (minutes % 60 == 0
                                ? ""
                                : ", " + Util.intToString(minutes % 60) + " minute" + (minutes == 1 ? "" : "s"))
                                : (minutes == 0
                                ? "Less than a minute"
                                : Util.capitaliseSentence(Util.intToString(minutes % 60)) + " minute" + (minutes == 1 ? "" : "s"));

                int dangerousTiles = Pathing.getDangerousTilesCrossed();

                switch (Pathing.getMapTravelType()) {
                    case FLYING:
                        mapSB.append("Estimated travel time:<br/>").append(hours > 0 ? "[style.boldMinorBad(" : "[style.boldMinorGood(").append(time).append(")]").append("<br/><br/>").append(dangerousTiles == 0
                                ? "[style.colourGood(Zero)] dangerous tiles will be encountered, as you fly over any in your way!"
                                : "[style.colourBad(One)] dangerous tile will be encountered (that being your destination).");
                        break;
                    case TELEPORT:
                        mapSB.append("Estimated travel time:<br/>[style.boldExcellent(Five seconds)]" + "<br/><br/>").append(dangerousTiles == 0
                                ? "[style.colourGood(Zero)] dangerous tiles will be encountered, as you teleport past them all!"
                                : "[style.colourBad(One)] dangerous tile will be encountered (that being your destination).");
                        break;
                    case WALK_DANGEROUS:
                    case WALK_SAFE:
                        mapSB.append("Estimated travel time:<br/>").append(hours > 0 ? "[style.boldMinorBad(" : "[style.boldMinorGood(").append(time).append(")]").append("<br/><br/>").append(dangerousTiles > 0 ? "[style.colourBad(" : "[style.colourGood(").append(Util.capitaliseSentence(Util.intToString(dangerousTiles))).append(")] dangerous tile").append(dangerousTiles == 1 ? "" : "s").append(" will be encountered!");
                        break;
                }
            }

            mapSB.append("</p>");

//			if(travelDestinationSelected) {
//				mapSB.append("<div id='TRAVEL_EXECUTE' class='cosmetics-button' style='"+buttonStyle+" position:absolute; bottom:0;'>"
//						+ "<span style='color:"+PresetColour.GENERIC_GOOD.toWebHexString()+";'>Go</span>"
//					+ "</div>");
//			} else {
//				mapSB.append("<div id='TRAVEL_EXECUTE' class='cosmetics-button disabled' style='"+buttonStyle+" position:absolute; bottom:0;'>"
//						+ "Go"
//					+ "</div>");
//			}

            mapSB.append("</div>");
        }

        mapSB.append("</div>");

//		long t2 = System.nanoTime();
//		System.out.println(Main.game.getAllNPCs().size()+" Full map: "+(t2-t1)/1000000000f);

        return mapSB.toString();
    }

    private String getPlayerIcon(boolean dangerous) {
        if (dangerous) {
            return ("<div class='map-icon-content' style='width:75%; height:75%; margin:12.5%; border-radius:50%;'>" + SVGImages.SVG_IMAGE_PROVIDER.getPlayerMapDangerousIcon() + "</div>");
        } else {
            if (Main.game.getPlayer().getFemininityValue() <= Femininity.MASCULINE.getMaximumFemininity()) {
                return ("<div class='map-icon-content' style='width:75%; height:75%; margin:12.5%; border-radius:50%;'>" + SVGImages.SVG_IMAGE_PROVIDER.getPlayerMapIconMasculine() + "</div>");

            } else if (Main.game.getPlayer().getFemininityValue() <= Femininity.ANDROGYNOUS.getMaximumFemininity()) {
                return ("<div class='map-icon-content' style='width:75%; height:75%; margin:12.5%; border-radius:50%;'>" + SVGImages.SVG_IMAGE_PROVIDER.getPlayerMapIconAndrogynous() + "</div>");

            } else {
                return ("<div class='map-icon-content' style='width:75%; height:75%; margin:12.5%; border-radius:50%;'>" + SVGImages.SVG_IMAGE_PROVIDER.getPlayerMapIconFeminine() + "</div>");
            }
        }
    }

    public String renderedHTMLMap() {

//		long t1 = System.nanoTime();

        mapSB.setLength(0);

        Colour background = PresetColour.BACKGROUND_DAY;
        String mapCornerGlow = "";
        switch (Main.game.getCurrentDayPeriod()) {
            case CIVIL_TWILIGHT:
            case NAUTICAL_TWILIGHT:
                background = PresetColour.BACKGROUND_TWILIGHT;
                mapCornerGlow = SVGImages.SVG_IMAGE_PROVIDER.getCornerGlowTwilight();
                break;
            case DAY:
                break;
            case ASTRONOMICAL_TWILIGHT:
            case NIGHT:
                mapCornerGlow = SVGImages.SVG_IMAGE_PROVIDER.getCornerGlowNight();
                background = PresetColour.BACKGROUND_NIGHT;
                break;
        }
        if (mapCornerGlow.isEmpty() && !Main.game.getPlayer().getCell().isLight()) {
            mapCornerGlow = SVGImages.SVG_IMAGE_PROVIDER.getCornerGlowNight();

        } else if (!mapCornerGlow.isEmpty() && Main.game.getPlayer().getCell().isLight()) {
            mapCornerGlow = SVGImages.SVG_IMAGE_PROVIDER.getCornerGlowAlwaysLight();
        }

        mapSB.append("<div class='map-container' style='background-color:").append(background.toWebHexString()).append(";'>");

//		if(!Main.game.isInNewWorld()) {
//			mapSB.append("<div style='left:0; top:0; margin:0; padding:0; width:100%; height:100vw; background-color:"+PresetColour.BACKGROUND_DARK.toWebHexString()+"; border-radius:5px;'></div>");
//			renderedDisabledMap = true;
//		}

        int mapSize = zoomedIn ? 2 : 3;
        float unit = zoomedIn ? 18f : 13.25f;

        String tileWidthStyle = "width:" + unit + "%; border-width:1%; margin:" + (zoomedIn ? 1 : 0.5) + "%;";
        String tileMovementDisabledStyle = "border-color:#111;";
        Vector2i playerPosition = Main.game.getPlayer().getLocation();

        // It looks messy, but it works...
        for (int y = playerPosition.getY() + mapSize; y >= playerPosition.getY() - mapSize; y--) {
            for (int x = playerPosition.getX() - mapSize; x <= playerPosition.getX() + mapSize; x++) {
                Cell cellFocused = Main.game.getActiveWorld().getCell(x, y);
                boolean cellTravelDisabled = cellFocused != null && cellFocused.getDialogue(false) == null && Main.game.isInNewWorld();

                if (x < Main.game.getActiveWorld().WORLD_WIDTH && x >= 0 && y < Main.game.getActiveWorld().WORLD_HEIGHT && y >= 0) {// If within  bounds of map:
                    AbstractPlaceType placeType = cellFocused.getPlace().getPlaceType();

                    if (cellFocused.isDiscovered() || Main.game.isMapReveal()) { // If the tile is discovered:
                        if (placeType.equals(PlaceType.GENERIC_IMPASSABLE)) {
                            mapSB.append("<div class='map-tile blank' style='").append(tileWidthStyle).append("'></div>");

                        } else {
                            // This is the "move North" tile:
                            if (y == playerPosition.getY() + 1 && x == playerPosition.getX() && !placeType.equals(PlaceType.GENERIC_IMPASSABLE)) {
                                if (cellFocused.getPlace().isDangerous()) {
                                    mapSB.append("<div class='map-tile movement dangerous' id='upButton' style='").append(cellTravelDisabled ? tileMovementDisabledStyle : "").append(tileWidthStyle).append(getDangerousBackground(placeType)).append("'>");

                                } else {
                                    mapSB.append("<div class='map-tile movement' id='upButton' style='").append(tileWidthStyle).append(" background:").append(placeType.getBackgroundColour().toWebHexString()).append(";").append(cellTravelDisabled
                                            ? tileMovementDisabledStyle
                                            : " border-color:" +
                                            (Main.game.getPlayer().getFemininityValue() <= Femininity.MASCULINE.getMaximumFemininity()
                                                    ? PresetColour.MASCULINE_PLUS
                                                    : (Main.game.getPlayer().getFemininityValue() <= Femininity.ANDROGYNOUS.getMaximumFemininity()
                                                    ? PresetColour.ANDROGYNOUS
                                                    : PresetColour.FEMININE_PLUS)).toWebHexString() + ";").append("'>");
                                }

                                // Put place icon onto tile:
                                if (cellFocused.getPlace().getSVGString() != null) {
                                    mapSB.append("<div class='place-icon'><div class='map-tile-content'>").append(cellFocused.getPlace().getSVGString()).append("</div></div>");
                                }

                                mapSB.append("<b class='hotkey-icon").append(cellFocused.getPlace().isDangerous() ? " dangerous" : "").append("'>").append(Main.getProperties().hotkeyMapPrimary.get(KeyboardAction.MOVE_NORTH) == null ? "" : Main.getProperties().hotkeyMapPrimary.get(KeyboardAction.MOVE_NORTH).getFullName()).append("</b>");

                                appendNPCIcon(Main.game.getActiveWorld(), x, y, unit);
                                appendItemsInAreaIcon(Main.game.getActiveWorld(), x, y);
                                appendNotVisitedLayer(Main.game.getActiveWorld(), x, y);

                                // Close the tile's div:
                                mapSB.append("</div>");

                                // This is the "move South" tile:
                            } else if (y == playerPosition.getY() - 1 && x == playerPosition.getX() && !placeType.equals(PlaceType.GENERIC_IMPASSABLE)) {
                                if (cellFocused.getPlace().isDangerous()) {
                                    mapSB.append("<div class='map-tile movement dangerous' id='downButton' style='").append(cellTravelDisabled ? tileMovementDisabledStyle : "").append(tileWidthStyle).append(getDangerousBackground(placeType)).append("'>");

                                } else {
                                    mapSB.append("<div class='map-tile movement' id='downButton' style='").append(tileWidthStyle).append(" background:").append(placeType.getBackgroundColour().toWebHexString()).append(";").append(cellTravelDisabled
                                            ? tileMovementDisabledStyle
                                            : " border-color:" +
                                            (Main.game.getPlayer().getFemininityValue() <= Femininity.MASCULINE.getMaximumFemininity()
                                                    ? PresetColour.MASCULINE_PLUS
                                                    : (Main.game.getPlayer().getFemininityValue() <= Femininity.ANDROGYNOUS.getMaximumFemininity()
                                                    ? PresetColour.ANDROGYNOUS
                                                    : PresetColour.FEMININE_PLUS)).toWebHexString() + ";").append("'>");
                                }

                                // Put place icon onto tile:
                                if (cellFocused.getPlace().getSVGString() != null) {
                                    mapSB.append("<div class='place-icon'><div class='map-tile-content'>").append(cellFocused.getPlace().getSVGString()).append("</div></div>");
                                }

                                mapSB.append("<b class='hotkey-icon").append(cellFocused.getPlace().isDangerous() ? " dangerous" : "").append("'>").append(Main.getProperties().hotkeyMapPrimary.get(KeyboardAction.MOVE_SOUTH) == null ? "" : Main.getProperties().hotkeyMapPrimary.get(KeyboardAction.MOVE_SOUTH).getFullName()).append("</b>");

                                appendNPCIcon(Main.game.getActiveWorld(), x, y, unit);
                                appendItemsInAreaIcon(Main.game.getActiveWorld(), x, y);
                                appendNotVisitedLayer(Main.game.getActiveWorld(), x, y);

                                // Close the tile's div:
                                mapSB.append("</div>");

                                // This is the "move West" tile:
                            } else if (y == playerPosition.getY() && x == playerPosition.getX() - 1 && !placeType.equals(PlaceType.GENERIC_IMPASSABLE)) {
                                if (cellFocused.getPlace().isDangerous()) {
                                    mapSB.append("<div class='map-tile movement dangerous' id='leftButton' style='").append(cellTravelDisabled ? tileMovementDisabledStyle : "").append(tileWidthStyle).append(getDangerousBackground(placeType)).append("'>");

                                } else {
                                    mapSB.append("<div class='map-tile movement' id='leftButton' style='").append(tileWidthStyle).append(" background:").append(placeType.getBackgroundColour().toWebHexString()).append(";").append(cellTravelDisabled
                                            ? tileMovementDisabledStyle
                                            : " border-color:" +
                                            (Main.game.getPlayer().getFemininityValue() <= Femininity.MASCULINE.getMaximumFemininity()
                                                    ? PresetColour.MASCULINE_PLUS
                                                    : (Main.game.getPlayer().getFemininityValue() <= Femininity.ANDROGYNOUS.getMaximumFemininity()
                                                    ? PresetColour.ANDROGYNOUS
                                                    : PresetColour.FEMININE_PLUS)).toWebHexString() + ";").append("'>");
                                }

                                // Put place icon onto tile:
                                if (cellFocused.getPlace().getSVGString() != null) {
                                    mapSB.append("<div class='place-icon'><div class='map-tile-content'>").append(cellFocused.getPlace().getSVGString()).append("</div></div>");
                                }

                                mapSB.append("<b class='hotkey-icon").append(cellFocused.getPlace().isDangerous() ? " dangerous" : "").append("'>").append(Main.getProperties().hotkeyMapPrimary.get(KeyboardAction.MOVE_WEST) == null ? "" : Main.getProperties().hotkeyMapPrimary.get(KeyboardAction.MOVE_WEST).getFullName()).append("</b>");

                                appendNPCIcon(Main.game.getActiveWorld(), x, y, unit);
                                appendItemsInAreaIcon(Main.game.getActiveWorld(), x, y);
                                appendNotVisitedLayer(Main.game.getActiveWorld(), x, y);

                                // Close the tile's div:
                                mapSB.append("</div>");

                                // This is the "move East" tile:
                            } else if (y == playerPosition.getY() && x == playerPosition.getX() + 1 && !placeType.equals(PlaceType.GENERIC_IMPASSABLE)) {
                                if (cellFocused.getPlace().isDangerous()) {
                                    mapSB.append("<div class='map-tile movement dangerous' id='rightButton' style='").append(cellTravelDisabled ? tileMovementDisabledStyle : "").append(tileWidthStyle).append(getDangerousBackground(placeType)).append("'>");

                                } else {
                                    mapSB.append("<div class='map-tile movement' id='rightButton' style='").append(tileWidthStyle).append(" background:").append(placeType.getBackgroundColour().toWebHexString()).append(";").append(cellTravelDisabled
                                            ? tileMovementDisabledStyle
                                            : " border-color:" +
                                            (Main.game.getPlayer().getFemininityValue() <= Femininity.MASCULINE.getMaximumFemininity()
                                                    ? PresetColour.MASCULINE_PLUS
                                                    : (Main.game.getPlayer().getFemininityValue() <= Femininity.ANDROGYNOUS.getMaximumFemininity()
                                                    ? PresetColour.ANDROGYNOUS
                                                    : PresetColour.FEMININE_PLUS)).toWebHexString() + ";").append("'>");
                                }

                                // Put place icon onto tile:
                                if (cellFocused.getPlace().getSVGString() != null) {
                                    mapSB.append("<div class='place-icon'><div class='map-tile-content'>").append(cellFocused.getPlace().getSVGString()).append("</div></div>");
                                }

                                mapSB.append("<b class='hotkey-icon").append(cellFocused.getPlace().isDangerous() ? " dangerous" : "").append("'>").append(Main.getProperties().hotkeyMapPrimary.get(KeyboardAction.MOVE_EAST) == null ? "" : Main.getProperties().hotkeyMapPrimary.get(KeyboardAction.MOVE_EAST).getFullName()).append("</b>");

                                appendNPCIcon(Main.game.getActiveWorld(), x, y, unit);
                                appendItemsInAreaIcon(Main.game.getActiveWorld(), x, y);
                                appendNotVisitedLayer(Main.game.getActiveWorld(), x, y);

                                // Close the tile's div:
                                mapSB.append("</div>");

                            } else {
                                if (cellFocused.getPlace().isDangerous()) {
                                    mapSB.append("<div class='map-tile").append(y == playerPosition.getY() && x == playerPosition.getX() ? " player dangerous" : " dangerous").append("' style='").append(tileWidthStyle).append(getDangerousBackground(placeType)).append("'>");

                                } else {
                                    mapSB.append("<div class='map-tile").append(y == playerPosition.getY() && x == playerPosition.getX() ? " player" : "").append("' style='").append(tileWidthStyle).append(" background:").append(placeType.getBackgroundColour().toWebHexString()).append(";'>");

                                }

                                // Put place icon onto tile:
                                if (cellFocused.getPlace().getSVGString() != null) {
                                    if (y == playerPosition.getY() && x == playerPosition.getX()) {
                                        mapSB.append("<div class='place-icon' style='margin:calc(18% - 1px); width:64%;'>" + "<div class='map-tile-content' style='background-color:").append(getPlayerIconColour(cellFocused.getPlace().isDangerous()).toWebHexString()).append(";").append("border:1px solid ").append(getPlayerIconColour(cellFocused.getPlace().isDangerous()).getShades()[1]).append("; border-radius:50%;'>").append(cellFocused.getPlace().getSVGString()).append("</div></div>");
                                    } else {
                                        mapSB.append("<div class='place-icon' style='margin:18%;width:64%;'><div class='map-tile-content'>").append(cellFocused.getPlace().getSVGString()).append("</div></div>");
                                    }

                                } else if (y == playerPosition.getY() && x == playerPosition.getX()) {
                                    if (cellFocused.getPlace().isDangerous()) {
                                        mapSB.append("<div class='place-icon' style='margin:18%;width:64%;'><div class='map-tile-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getPlayerMapDangerousIcon()).append("</div></div>");
                                    } else {
                                        if (Main.game.getPlayer().getFemininityValue() <= Femininity.MASCULINE.getMaximumFemininity()) {
                                            mapSB.append("<div class='place-icon' style='margin:18%;width:64%;'><div class='map-tile-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getPlayerMapIconMasculine()).append("</div></div>");

                                        } else if (Main.game.getPlayer().getFemininityValue() <= Femininity.ANDROGYNOUS.getMaximumFemininity()) {
                                            mapSB.append("<div class='place-icon' style='margin:18%;width:64%;'><div class='map-tile-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getPlayerMapIconAndrogynous()).append("</div></div>");

                                        } else {
                                            mapSB.append("<div class='place-icon' style='margin:18%;width:64%;'><div class='map-tile-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getPlayerMapIconFeminine()).append("</div></div>");
                                        }
                                    }
                                }

                                appendNPCIcon(Main.game.getActiveWorld(), x, y, unit);
                                appendItemsInAreaIcon(Main.game.getActiveWorld(), x, y);
                                appendNotVisitedLayer(Main.game.getActiveWorld(), x, y);

                                // Close the tile's div:
                                mapSB.append("</div>");
                            }

                        }

                    } else if (cellFocused.getPlace().getPlaceType() != PlaceType.GENERIC_IMPASSABLE) {
                        mapSB.append("<div class='map-tile' style='background-color:").append(PresetColour.MAP_BACKGROUND_UNEXPLORED.toWebHexString()).append("; ").append(tileWidthStyle).append("'></div>");

                    } else {
                        mapSB.append("<div class='map-tile blank' style='").append(tileWidthStyle).append("'></div>");
                    }

                } else {
                    mapSB.append("<div class='map-tile blank' style='").append(tileWidthStyle).append("'></div>");
                }
            }

        }


        if (!Main.game.isInNewWorld() || Main.game.getCurrentDialogueNode().isTravelDisabled()) {
            mapSB.append("<div style='position:relative; left:0; top:0; margin:0; padding:0; width:100%; height:100vw; background-color:rgba(0,0,0,0.7); border-radius:5px;'></div>");
            renderedDisabledMap = true;

        } else {
            if (!mapCornerGlow.isEmpty()) {
                mapSB.append("<div style='position:relative; left:0; top:0; margin:0; padding:0; width:100%; height:100vw; background-color:transparent; border-radius:5px; pointer-events:none;'>" + "<div style='position:absolute; left:0; top:0; margin:0; padding:0; height:100%; height:100vw;'>").append(mapCornerGlow).append("</div>").append("</div>");
            }
            renderedDisabledMap = false;
        }


        mapSB.append("</div>");


//		long t2 = System.nanoTime();
//		System.out.println("HTML map: "+(t2-t1)/1000000000f);

        return mapSB.toString();
    }

    private void appendNPCIcon(World world, int x, int y, float tileWidth) {

        List<String> mapIcons = new ArrayList<>();
        List<NPC> charactersPresent = Main.game.getCharactersPresent(world.getCell(x, y));
        List<NPC> charactersHome = Main.game.getCharactersTreatingCellAsHome(world.getCell(x, y));

        if (!charactersPresent.isEmpty() || !charactersHome.isEmpty()) {
            for (NPC gc : charactersPresent) {
                mapIcons.add(gc.getMapIcon());
            }

            for (NPC gc : charactersHome) {
                if (!charactersPresent.contains(gc) && (charactersHome.size() == 1 || x != 0 || y != 0)) {
                    mapIcons.add("<span style='opacity:0.5;'>" + gc.getHomeMapIcon() + "</span>");
                }
            }

            if (!mapIcons.isEmpty()) {
                float increment = Math.min(20, 75 / mapIcons.size());
                for (int i = mapIcons.size(); i > 0; i--) {
                    mapSB.append("<div class='npc-icon' style='left:").append(5 + ((i - 1) * increment)).append("%;'>").append(mapIcons.get(i - 1)).append("</div>");
                }
            }
        }
    }

    private void appendItemsInAreaIcon(World world, int x, int y) {
        if (!Main.game.isInNewWorld()) {
            return;
        }
        if (!world.getCell(x, y).getInventory().isEmpty()) {
            mapSB.append("<div class='item-icon'>").append(SVGImages.SVG_IMAGE_PROVIDER.getItemsOnFloorIcon()).append("</div>");
        }
    }

    private void appendNotVisitedLayer(World world, int x, int y) {
        if (!world.getCell(x, y).isTravelledTo()) {
            mapSB.append("<div style='position:absolute;width:100%;height:100%;top:0;left:0;background-color:rgba(0,0,0,0.5);'></div>");
        }
    }

    public void renderButtonsLeft() {
        Main.mainController.setButtonsLeftContent(
                "<div class='quarterContainer'>"
                        + "<div class='button' id='mainMenu'>"
                        + SVGImages.SVG_IMAGE_PROVIDER.getMenuIcon()
                        + "</div>"
                        + "</div>"

                        + "<div class='quarterContainer'>"
                        + "<div class='button" + (
                        (Main.mainController.isPhoneDisabled())
                                ? " disabled"
                                : (Main.game.getPlayer().isMainQuestUpdated()
                                || Main.game.getPlayer().isSideQuestUpdated()
                                || Main.game.getPlayer().isRelationshipQuestUpdated()
                                || Main.getProperties().hasValue(PropertyValue.newWeaponDiscovered)
                                || Main.getProperties().hasValue(PropertyValue.newClothingDiscovered)
                                || Main.getProperties().hasValue(PropertyValue.newItemDiscovered)
                                || Main.getProperties().hasValue(PropertyValue.newRaceDiscovered)
                                || Main.getProperties().hasValue(PropertyValue.levelUpHightlight)
                                ? " highlight"
                                : ""))
                        + "' id='journal'>" + SVGImages.SVG_IMAGE_PROVIDER.getJournalIcon()
                        + (Main.mainController.isPhoneDisabled() ? "<div class='disabledLayer'></div>" : "")
                        + "</div>"
                        + "</div>"

                        + "<div class='quarterContainer'>"
                        + "<div class='button" + (!Main.game.getActiveWorld().getCell(Main.game.getPlayer().getLocation()).getInventory().isEmpty() ? " highlight" : "")
                        + (Main.mainController.isInventoryDisabled() ? " disabled" : "") + "' id='inventory'>"
                        + SVGImages.SVG_IMAGE_PROVIDER.getInventoryIcon()
                        + (Main.mainController.isInventoryDisabled() ? "<div class='disabledLayer'></div>" : "")
                        + "</div>"
                        + "</div>"

                        + "<div class='quarterContainer'>"
                        + "<div class='button" + (Main.game.getCharactersPresent().isEmpty() && Main.game.getCurrentDialogueNode().getDialogueNodeType() != DialogueNodeType.CHARACTERS_PRESENT ? " disabled" : "")
                        + "' id='charactersPresent'>" + SVGImages.SVG_IMAGE_PROVIDER.getPeopleIcon()
                        + (Main.game.getCharactersPresent().isEmpty() && Main.game.getCurrentDialogueNode().getDialogueNodeType() != DialogueNodeType.CHARACTERS_PRESENT ? "<div class='disabledLayer'></div>" : "")
                        + "</div>"
                        + "</div>"

                        + "<div class='quarterContainer'>"
                        + "<div class='button" + (Main.game.getCurrentDialogueNode().isTravelDisabled() ? " disabled" : "") + "' id='mapZoom'>"
                        + (RenderingEngine.isZoomedIn() ? SVGImages.SVG_IMAGE_PROVIDER.getZoomOutIcon() : SVGImages.SVG_IMAGE_PROVIDER.getZoomInIcon())
                        + (Main.game.getCurrentDialogueNode().isTravelDisabled() ? "<div class='disabledLayer'></div>" : "")
                        + "</div>"
                        + "</div>");
    }

    public void renderButtonsRight() {
        boolean exportAvailable = Main.game.isStarted()
                && (Main.game.getCurrentDialogueNode().equals(CharactersPresentDialogue.MENU)
                || Main.game.getCurrentDialogueNode().equals(PhoneDialogue.CHARACTER_APPEARANCE)
                || Main.game.getCurrentDialogueNode().equals(PhoneDialogue.CONTACTS_CHARACTER));

        Main.mainController.setButtonsRightContent(
                "<div class='quarterContainer' style='width:25%; float:right;'>"
                        + "<div class='button" + (Main.isLoadGameAvailable(Main.getQuickSaveName()) ? "" : " disabled") + "' id='quickLoad'>"
                        + (Main.isLoadGameAvailable(Main.getQuickSaveName())
                        ? SVGImages.SVG_IMAGE_PROVIDER.getDiskLoadQuick()
                        : SVGImages.SVG_IMAGE_PROVIDER.getDiskLoadDisabled())
                        + (!Main.isLoadGameAvailable(Main.getQuickSaveName()) ? "<div class='disabledLayer'></div>" : "")
                        + "</div>"
                        + "</div>"

                        + "<div class='quarterContainer' style='width:25%; float:right;'>"
                        + "<div class='button" + (Main.isQuickSaveAvailable() ? "" : " disabled") + "' id='quickSave'>"
                        + (Main.isQuickSaveAvailable()
                        ? SVGImages.SVG_IMAGE_PROVIDER.getDiskSave()
                        : SVGImages.SVG_IMAGE_PROVIDER.getDiskSaveDisabled())
                        + (!Main.isQuickSaveAvailable() ? "<div class='disabledLayer'></div>" : "")
                        + "</div>"
                        + "</div>"

                        + "<div class='quarterContainer' style='width:25%; float:left;'>"
                        + "<div class='button" + (exportAvailable ? "" : " disabled") + "' id='exportCharacter'>"
                        + SVGImages.SVG_IMAGE_PROVIDER.getExportIcon()
                        + (!exportAvailable ? "<div class='disabledLayer'></div>" : "")
                        + "</div>"
                        + "</div>"

                        + "<div class='quarterContainer' style='width:25%; float:left;'>"
                        + "<div class='button' id='copyContent'>"
                        + SVGImages.SVG_IMAGE_PROVIDER.getCopyIcon()
                        + "</div>"
                        + "</div>"

//				+ "<div class='quarterContainer' style='text-align:center; width:60%; font-size:0.8em; float:right; color:"+PresetColour.TEXT_GREY.toWebHexString()+";'>"
//					+ "Difficulty: <span style='color:"+Main.getProperties().difficultyLevel.getColour().getShades()[1]+";'>"+Main.getProperties().difficultyLevel.getName()+"</span>"
//					+ "<br/>"
//					+ "Author: "+Main.game.getCurrentDialogueNode().getAuthor()
//				+ "</div>"
        );
    }

    public boolean isRenderingTattoosLeft() {
        return renderingTattoosLeft;
    }

    public void setRenderingTattoosLeft(boolean renderingTattoosLeft) {
        this.renderingTattoosLeft = renderingTattoosLeft;
    }

    public boolean isRenderingTattoosRight() {
        return renderingTattoosRight;
    }

    public void setRenderingTattoosRight(boolean renderingTattoosRight) {
        this.renderingTattoosRight = renderingTattoosRight;
    }

}