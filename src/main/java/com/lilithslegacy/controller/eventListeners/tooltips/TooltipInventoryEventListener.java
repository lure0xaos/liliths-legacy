package com.lilithslegacy.controller.eventListeners.tooltips;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;

import com.lilithslegacy.controller.TooltipUpdateThread;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.attributes.AbstractAttribute;
import com.lilithslegacy.game.character.attributes.Attribute;
import com.lilithslegacy.game.character.body.CoverableArea;
import com.lilithslegacy.game.character.body.coverings.Covering;
import com.lilithslegacy.game.character.body.types.HornType;
import com.lilithslegacy.game.character.body.types.PenisType;
import com.lilithslegacy.game.character.body.types.TailType;
import com.lilithslegacy.game.character.body.types.VaginaType;
import com.lilithslegacy.game.character.body.types.WingType;
import com.lilithslegacy.game.character.body.valueEnums.Femininity;
import com.lilithslegacy.game.character.markings.AbstractTattooType;
import com.lilithslegacy.game.character.markings.Scar;
import com.lilithslegacy.game.character.markings.Tattoo;
import com.lilithslegacy.game.character.markings.TattooCountType;
import com.lilithslegacy.game.character.markings.TattooCounter;
import com.lilithslegacy.game.character.markings.TattooCounterType;
import com.lilithslegacy.game.character.markings.TattooWriting;
import com.lilithslegacy.game.character.markings.TattooWritingStyle;
import com.lilithslegacy.game.combat.Attack;
import com.lilithslegacy.game.combat.DamageType;
import com.lilithslegacy.game.combat.moves.AbstractCombatMove;
import com.lilithslegacy.game.combat.spells.Spell;
import com.lilithslegacy.game.dialogue.utils.EnchantmentDialogue;
import com.lilithslegacy.game.dialogue.utils.InventoryDialogue;
import com.lilithslegacy.game.dialogue.utils.InventoryInteraction;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.AbstractCoreItem;
import com.lilithslegacy.game.inventory.InventorySlot;
import com.lilithslegacy.game.inventory.ShopTransaction;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.inventory.clothing.AbstractClothingType;
import com.lilithslegacy.game.inventory.clothing.BodyPartClothingBlock;
import com.lilithslegacy.game.inventory.enchanting.EnchantingUtils;
import com.lilithslegacy.game.inventory.enchanting.ItemEffect;
import com.lilithslegacy.game.inventory.enchanting.TFModifier;
import com.lilithslegacy.game.inventory.enchanting.TFPotency;
import com.lilithslegacy.game.inventory.item.AbstractItem;
import com.lilithslegacy.game.inventory.item.AbstractItemType;
import com.lilithslegacy.game.inventory.weapon.AbstractWeapon;
import com.lilithslegacy.game.inventory.weapon.AbstractWeaponType;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.rendering.Pattern;
import com.lilithslegacy.rendering.RenderingEngine;
import com.lilithslegacy.utils.SizedStack;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Util.Value;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * Shows the tooltip at the given element's position.
 *
 * @author Innoxia
 * @version 0.3.9
 * @since 0.1.0
 */
public class TooltipInventoryEventListener implements EventListener {
    private static final int LINE_HEIGHT = 17;
    private static final int TOOLTIP_WIDTH = 400;
    private static final StringBuilder tooltipSB = new StringBuilder();
    private GameCharacter owner;
    private GameCharacter equippedToCharacter;
    private AbstractCoreItem coreItem;
    private InventorySlot invSlot;
    private AbstractItem item;
    private AbstractItemType genericItem;
    private AbstractWeapon weapon;
    private AbstractWeaponType genericWeapon;
    private DamageType dt;
    private AbstractWeapon dyeWeapon;
    private DamageType damageType;
    private AbstractClothing clothing;
    private AbstractClothingType genericClothing;
    private AbstractClothing dyeClothing;
    private Tattoo tattoo;
    private AbstractTattooType genericTattoo;
    private int colourIndex;
    private Colour patternColour;
    private Colour colour;
    private TFModifier enchantmentModifier;
    private TFPotency potency;

    @Override
    public void handleEvent(Event event) {

        if (item != null || (coreItem instanceof AbstractItem)) {
            if (coreItem != null) {
                item = (AbstractItem) coreItem;
            }
            itemTooltip(item);

        } else if (weapon != null || (coreItem instanceof AbstractWeapon)) {
            if (coreItem != null) {
                weapon = (AbstractWeapon) coreItem;
            }
            weaponTooltip(weapon);

        } else if (clothing != null || (coreItem instanceof AbstractClothing)) {
            if (coreItem != null) {
                clothing = (AbstractClothing) coreItem;
            }
            clothingTooltip(clothing);

        } else if (tattoo != null) {
            tattooTooltip(tattoo);

        } else if (dyeClothing != null) {
            Main.mainController.setTooltipSize(TOOLTIP_WIDTH, 480);

            Colour subtitleColour = dyeClothing.isEnchantmentKnown() ? dyeClothing.getRarity().getColour() : PresetColour.RARITY_UNKNOWN;

            InventorySlot slotEquippedTo = dyeClothing.getSlotEquippedTo();
            if (slotEquippedTo == null) {
                slotEquippedTo = dyeClothing.getClothingType().getEquipSlots().get(0);
            }

            tooltipSB.setLength(0);
            if (colour != null) {
                List<Colour> dyeColours = new ArrayList<>(InventoryDialogue.dyePreviews);
                dyeColours.remove(colourIndex);
                dyeColours.add(colourIndex, colour);
                tooltipSB.append("<div class='title' style='color:").append(subtitleColour.toWebHexString()).append(";'>").append(Util.capitaliseSentence(dyeClothing.getName())).append("</div>").append("<div class='subTitle'>").append(Util.capitaliseSentence(colour.getName())).append(" (").append(dyeClothing.getClothingType().getColourReplacement(colourIndex).getDefaultColours().contains(colour)
                        ? "Standard Colour"
                        : "Non-standard Colour").append(")").append("</div>").append("<div class='picture full' style='position:relative; margin:8px; padding:0; width:calc(").append(TOOLTIP_WIDTH).append("px - 24px); height:calc(").append(TOOLTIP_WIDTH).append("px - 24px);'>").append(dyeClothing.getClothingType().getSVGImage(
                        slotEquippedTo,
                        dyeColours,
                        InventoryDialogue.dyePreviewPattern,
                        InventoryDialogue.dyePreviewPatterns,
                        InventoryDialogue.getDyePreviewStickersAsStrings())).append("</div>");

            } else if (patternColour != null) {
                List<Colour> dyeColours = new ArrayList<>(InventoryDialogue.dyePreviewPatterns);
                dyeColours.remove(colourIndex);
                dyeColours.add(colourIndex, patternColour);
                tooltipSB.append("<div class='title' style='color:").append(subtitleColour.toWebHexString()).append(";'>").append(Util.capitaliseSentence(dyeClothing.getName())).append("</div>").append("<div class='subTitle'>").append(Util.capitaliseSentence(Pattern.getPattern(InventoryDialogue.dyePreviewPattern).getNiceName())).append("</div>").append("<div class='picture full' style='position:relative; margin:8px; padding:0; width:calc(").append(TOOLTIP_WIDTH).append("px - 24px); height:calc(").append(TOOLTIP_WIDTH).append("px - 24px);'>").append(dyeClothing.getClothingType().getSVGImage(
                        slotEquippedTo,
                        InventoryDialogue.dyePreviews,
                        InventoryDialogue.dyePreviewPattern,
                        dyeColours,
                        InventoryDialogue.getDyePreviewStickersAsStrings())).append("</div>");

            }

            Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));

        } else if (dyeWeapon != null) {
            Main.mainController.setTooltipSize(TOOLTIP_WIDTH, 480);

            tooltipSB.setLength(0);
            tooltipSB.append("<div class='title' style='color:").append(dyeWeapon.getRarity().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(dyeWeapon.getName())).append("</div>");

            if (colour != null) {
                List<Colour> dyeColours = new ArrayList<>(InventoryDialogue.dyePreviews);
                dyeColours.remove(colourIndex);
                dyeColours.add(colourIndex, colour);
                tooltipSB.append("<div class='subTitle'>").append(Util.capitaliseSentence(colour.getName())).append(" (").append(dyeWeapon.getWeaponType().getColourReplacement(true, colourIndex).getDefaultColours().contains(colour)
                        ? "Standard Colour"
                        : "Non-standard Colour").append(")").append("</div>").append("<div class='picture full' style='position:relative; margin:8px; padding:0; width:calc(").append(TOOLTIP_WIDTH).append("px - 24px); height:calc(").append(TOOLTIP_WIDTH).append("px - 24px);'>").append(dyeWeapon.getWeaponType().getSVGImage(dyeWeapon.getDamageType(), dyeColours)).append("</div>");

            } else if (damageType != null) {
                tooltipSB.append("<div class='subTitle'>").append(Util.capitaliseSentence(damageType.getName())).append("</div>").append("<div class='picture full' style='position:relative; margin:8px; padding:0; width:calc(").append(TOOLTIP_WIDTH).append("px - 24px); height:calc(").append(TOOLTIP_WIDTH).append("px - 24px);'>").append(dyeWeapon.getWeaponType().getSVGImage(damageType, InventoryDialogue.dyePreviews)).append("</div>");
            }

            Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));

        } else if (genericItem != null) {
            itemTooltip(Main.game.getItemGen().generateItem(genericItem));

        } else if (genericClothing != null) {
            if (colour != null) {
                clothingTooltip(Main.game.getItemGen().generateClothing(genericClothing, colour, false));
            } else {
                clothingTooltip(Main.game.getItemGen().generateClothing(genericClothing, false));
            }

        } else if (genericTattoo != null) {
            tattooTooltip(new Tattoo(
                    genericTattoo,
                    false,
                    new TattooWriting(
                            "The quick brown fox jumps over the lazy dog.",
                            genericTattoo.getAvailablePrimaryColours().get(0),
                            false),
                    new TattooCounter(
                            TattooCounterType.UNIQUE_SEX_PARTNERS,
                            TattooCountType.NUMBERS,
                            genericTattoo.getAvailablePrimaryColours().get(0),
                            false)));

        } else if (genericWeapon != null) {
            weaponTooltip(Main.game.getItemGen().generateWeapon(genericWeapon, dt));

        } else if (invSlot != null) {
            if (invSlot == InventorySlot.WEAPON_MAIN_1) {
                if (equippedToCharacter != null) {

                    if (equippedToCharacter.getMainWeapon(0) == null) {
                        setUnarmedWeaponSlotTooltip(InventorySlot.WEAPON_MAIN_1, "Primary Weapon");

                    } else {
                        weaponTooltip(equippedToCharacter.getMainWeapon(0));
                    }
                } else {
                    Main.mainController.setTooltipSize(TOOLTIP_WIDTH, 60);
                    Main.mainController.setTooltipContent("<div class='title'>Primary Weapon</div>");
                }

            } else if (invSlot == InventorySlot.WEAPON_MAIN_2) {
                if (equippedToCharacter != null) {
                    if (equippedToCharacter.getMainWeapon(1) == null) {
                        if (equippedToCharacter.getArmRows() < 2) {
                            setBlockedTooltipContent(getTooltipText(equippedToCharacter,
                                    "You do not have a second pair of arms with which to hold another primary weapon!",
                                    "[npc.Name] [npc.does] not have a second pair of arms with which to hold another primary weapon!"));
                        } else {
                            setUnarmedWeaponSlotTooltip(InventorySlot.WEAPON_MAIN_2, "Primary Weapon (2nd)");
                        }

                    } else {
                        weaponTooltip(equippedToCharacter.getMainWeapon(1));
                    }
                } else {
                    Main.mainController.setTooltipSize(TOOLTIP_WIDTH, 60);
                    Main.mainController.setTooltipContent("<div class='title'>Primary Weapon (2nd)</div>");
                }

            } else if (invSlot == InventorySlot.WEAPON_MAIN_3) {
                if (equippedToCharacter != null) {
                    if (equippedToCharacter.getMainWeapon(2) == null) {
                        if (equippedToCharacter.getArmRows() < 3) {
                            setBlockedTooltipContent(getTooltipText(equippedToCharacter,
                                    "You do not have a third pair of arms with which to hold another primary weapon!",
                                    "[npc.Name] [npc.does] not have a third pair of arms with which to hold another primary weapon!"));
                        } else {
                            setUnarmedWeaponSlotTooltip(InventorySlot.WEAPON_MAIN_3, "Primary Weapon (3rd)");
                        }

                    } else {
                        weaponTooltip(equippedToCharacter.getMainWeapon(2));
                    }
                } else {
                    Main.mainController.setTooltipSize(TOOLTIP_WIDTH, 60);
                    Main.mainController.setTooltipContent("<div class='title'>Primary Weapon (3rd)</div>");
                }

            } else if (invSlot == InventorySlot.WEAPON_OFFHAND_1) {
                if (equippedToCharacter != null) {
                    if (equippedToCharacter.getOffhandWeapon(0) == null) {
                        AbstractWeapon primary = equippedToCharacter.getMainWeapon(0);
                        if (primary != null && primary.getWeaponType().isTwoHanded()) {
                            setBlockedTooltipContent(getTooltipText(equippedToCharacter,
                                    primary.getWeaponType().isPlural()
                                            ? "As your " + primary.getName() + " require two hands to wield correctly, you are unable to equip a weapon in your off-hand."
                                            : "As your " + primary.getName() + " requires two hands to wield correctly, you are unable to equip a weapon in your off-hand",
                                    primary.getWeaponType().isPlural()
                                            ? "As [npc.namePos] " + primary.getName() + " require two hands to wield correctly, [npc.sheIsFull] unable to equip a weapon in [npc.her] off-hand."
                                            : "As [npc.namePos] " + primary.getName() + " requires two hands to wield correctly, [npc.sheIsFull] unable to equip a weapon in [npc.her] off-hand"));

                        } else {
                            setUnarmedWeaponSlotTooltip(InventorySlot.WEAPON_OFFHAND_1, "Secondary Weapon");
                        }

                    } else {
                        weaponTooltip(equippedToCharacter.getOffhandWeapon(0));
                    }

                } else {
                    Main.mainController.setTooltipSize(TOOLTIP_WIDTH, 60);
                    Main.mainController.setTooltipContent("<div class='title'>Secondary Weapon</div>");
                }

            } else if (invSlot == InventorySlot.WEAPON_OFFHAND_2) {
                if (equippedToCharacter != null) {
                    if (equippedToCharacter.getOffhandWeapon(1) == null) {
                        AbstractWeapon primary = equippedToCharacter.getMainWeapon(1);
                        if (primary != null && primary.getWeaponType().isTwoHanded()) {
                            setBlockedTooltipContent(getTooltipText(equippedToCharacter,
                                    primary.getWeaponType().isPlural()
                                            ? "As your " + primary.getName() + " require two hands to wield correctly, you are unable to equip a weapon in your off-hand."
                                            : "As your " + primary.getName() + " requires two hands to wield correctly, you are unable to equip a weapon in your off-hand",
                                    primary.getWeaponType().isPlural()
                                            ? "As [npc.namePos] " + primary.getName() + " require two hands to wield correctly, [npc.sheIsFull] unable to equip a weapon in [npc.her] off-hand."
                                            : "As [npc.namePos] " + primary.getName() + " requires two hands to wield correctly, [npc.sheIsFull] unable to equip a weapon in [npc.her] off-hand"));

                        } else if (equippedToCharacter.getArmRows() < 2) {
                            setBlockedTooltipContent(getTooltipText(equippedToCharacter,
                                    "You do not have a second pair of arms with which to hold another secondary weapon!",
                                    "[npc.Name] [npc.does] not have a second pair of arms with which to hold another secondary weapon!"));
                        } else {
                            setUnarmedWeaponSlotTooltip(InventorySlot.WEAPON_OFFHAND_2, "Secondary Weapon (2nd)");
                        }

                    } else {
                        weaponTooltip(equippedToCharacter.getOffhandWeapon(1));
                    }
                } else {
                    Main.mainController.setTooltipSize(TOOLTIP_WIDTH, 60);
                    Main.mainController.setTooltipContent("<div class='title'>Secondary Weapon (2nd)</div>");
                }

            } else if (invSlot == InventorySlot.WEAPON_OFFHAND_3) {
                if (equippedToCharacter != null) {
                    if (equippedToCharacter.getOffhandWeapon(2) == null) {
                        AbstractWeapon primary = equippedToCharacter.getMainWeapon(2);
                        if (primary != null && primary.getWeaponType().isTwoHanded()) {
                            setBlockedTooltipContent(getTooltipText(equippedToCharacter,
                                    primary.getWeaponType().isPlural()
                                            ? "As your " + primary.getName() + " require two hands to wield correctly, you are unable to equip a weapon in your off-hand."
                                            : "As your " + primary.getName() + " requires two hands to wield correctly, you are unable to equip a weapon in your off-hand",
                                    primary.getWeaponType().isPlural()
                                            ? "As [npc.namePos] " + primary.getName() + " require two hands to wield correctly, [npc.sheIsFull] unable to equip a weapon in [npc.her] off-hand."
                                            : "As [npc.namePos] " + primary.getName() + " requires two hands to wield correctly, [npc.sheIsFull] unable to equip a weapon in [npc.her] off-hand"));

                        } else if (equippedToCharacter.getArmRows() < 3) {
                            setBlockedTooltipContent(getTooltipText(equippedToCharacter,
                                    "You do not have a third pair of arms with which to hold another secondary weapon!",
                                    "[npc.Name] [npc.does] not have a third pair of arms with which to hold another secondary weapon!"));
                        } else {
                            setUnarmedWeaponSlotTooltip(InventorySlot.WEAPON_OFFHAND_3, "Secondary Weapon (3rd)");
                        }
                    } else {
                        weaponTooltip(equippedToCharacter.getOffhandWeapon(2));
                    }
                } else {
                    Main.mainController.setTooltipSize(TOOLTIP_WIDTH, 60);
                    Main.mainController.setTooltipContent("<div class='title'>Secondary Weapon (3rd)</div>");
                }

            } else {
                if (equippedToCharacter != null) {
                    boolean renderingTattoos = (equippedToCharacter.isPlayer() && RenderingEngine.ENGINE.isRenderingTattoosLeft()) || (!equippedToCharacter.isPlayer() && RenderingEngine.ENGINE.isRenderingTattoosRight())
                            && !invSlot.isJewellery();

                    if ((!renderingTattoos && equippedToCharacter.getClothingInSlot(invSlot) == null)
                            || (renderingTattoos && equippedToCharacter.getTattooInSlot(invSlot) == null)) {

                        List<String> clothingBlockingThisSlot = new ArrayList<>();
                        for (AbstractClothing c : equippedToCharacter.getClothingCurrentlyEquipped()) {
                            if (c.getIncompatibleSlots(equippedToCharacter, c.getSlotEquippedTo()).contains(invSlot)) {
                                clothingBlockingThisSlot.add(c.getName());
                            }
                        }

                        BodyPartClothingBlock block = invSlot.getBodyPartClothingBlock(equippedToCharacter);

                        if (!renderingTattoos && !clothingBlockingThisSlot.isEmpty()) {
                            setBlockedTooltipContent(UtilText.parse(equippedToCharacter, "This slot is currently <b style='color:" + PresetColour.SEALED.toWebHexString() + ";'>blocked</b> by [npc.namePos] ")
                                    + Util.stringsToStringList(clothingBlockingThisSlot, false) + ".");

                        } else if (!renderingTattoos && block != null) {
                            setBlockedTooltipContent("<span style='color:" + PresetColour.GENERIC_MINOR_BAD.toWebHexString() + ";'>Restricted!</span>", UtilText.parse(equippedToCharacter, block.getDescription()));

                        } else {
                            boolean piercingBlocked = false;
                            boolean bypassesPiercing = !equippedToCharacter.getBody().getBodyMaterial().isRequiresPiercing();

                            switch (invSlot) {
                                case PIERCING_VAGINA:
                                    if (equippedToCharacter.getVaginaType() == VaginaType.NONE) {
                                        setBlockedTooltipContent(getTooltipText(equippedToCharacter,
                                                "You don't have a vagina.",
                                                (equippedToCharacter.isAreaKnownByCharacter(CoverableArea.VAGINA, Main.game.getPlayer())
                                                        ? "[npc.Name] doesn't have a vagina."
                                                        : "You don't know if [npc.name] has a vagina.")));
                                        piercingBlocked = true;

                                    } else if (!bypassesPiercing && !equippedToCharacter.isPiercedVagina()) {
                                        setBlockedTooltipContent(getTooltipText(equippedToCharacter,
                                                "Your vagina has not been pierced.",
                                                (equippedToCharacter.isAreaKnownByCharacter(CoverableArea.VAGINA, Main.game.getPlayer())
                                                        ? "[npc.NamePos] vagina has not been pierced."
                                                        : "You don't know if [npc.name] has a vagina.")));
                                        piercingBlocked = true;
                                    }
                                    break;

                                case PIERCING_EAR:
                                    if (!bypassesPiercing) {
                                        if (!equippedToCharacter.isPiercedEar()) {
                                            setBlockedTooltipContent(getTooltipText(equippedToCharacter,
                                                    "Your ears have not been pierced.",
                                                    "[npc.NamePos] ears have not been pierced."));
                                            piercingBlocked = true;
                                        }
                                    }
                                    break;

                                case PIERCING_LIP:
                                    if (!bypassesPiercing) {
                                        if (!equippedToCharacter.isPiercedLip()) {
                                            setBlockedTooltipContent(getTooltipText(equippedToCharacter,
                                                    "Your lips have not been pierced.",
                                                    "[npc.NamePos] lips have not been pierced."));
                                            piercingBlocked = true;
                                        }
                                    }
                                    break;

                                case PIERCING_NIPPLE:
                                    if (!bypassesPiercing) {
                                        if (!equippedToCharacter.isPiercedNipple()) {
                                            setBlockedTooltipContent(getTooltipText(equippedToCharacter,
                                                    "Your nipples have not been pierced.",
                                                    (equippedToCharacter.isAreaKnownByCharacter(CoverableArea.NIPPLES, Main.game.getPlayer())
                                                            ? "[npc.NamePos] nipples have not been pierced."
                                                            : "You don't know if [npc.namePos] nipples have been pierced.")));
                                            piercingBlocked = true;
                                        }
                                    }
                                    break;

                                case PIERCING_NOSE:
                                    if (!bypassesPiercing) {
                                        if (!equippedToCharacter.isPiercedNose()) {
                                            setBlockedTooltipContent(getTooltipText(equippedToCharacter,
                                                    "Your nose has not been pierced.",
                                                    "[npc.NamePos] nose has not been pierced."));
                                            piercingBlocked = true;
                                        }
                                    }
                                    break;

                                case PIERCING_PENIS:
                                    if (equippedToCharacter.getPenisType() == PenisType.NONE) {
                                        setBlockedTooltipContent(getTooltipText(equippedToCharacter,
                                                "You don't have a penis.",
                                                (equippedToCharacter.isAreaKnownByCharacter(CoverableArea.PENIS, Main.game.getPlayer())
                                                        ? "[npc.Name] doesn't have a penis."
                                                        : "You don't know if [npc.name] has a penis.")));
                                        piercingBlocked = true;

                                    } else if (!bypassesPiercing && !equippedToCharacter.isPiercedPenis()) {
                                        setBlockedTooltipContent(getTooltipText(equippedToCharacter,
                                                "Your penis has not been pierced.",
                                                (equippedToCharacter.isAreaKnownByCharacter(CoverableArea.PENIS, Main.game.getPlayer())
                                                        ? "[npc.NamePos] penis has not been pierced."
                                                        : "You don't know if [npc.name] has a penis.")));
                                        piercingBlocked = true;
                                    }
                                    break;

                                case PIERCING_STOMACH:
                                    if (!bypassesPiercing) {
                                        if (!equippedToCharacter.isPiercedNavel()) {
                                            setBlockedTooltipContent(getTooltipText(equippedToCharacter,
                                                    "Your navel has not been pierced.",
                                                    "[npc.NamePos] navel has not been pierced."));
                                            piercingBlocked = true;
                                        }
                                    }
                                    break;

                                case PIERCING_TONGUE:
                                    if (!bypassesPiercing) {
                                        if (!equippedToCharacter.isPiercedTongue()) {
                                            setBlockedTooltipContent(getTooltipText(equippedToCharacter,
                                                    "Your tongue has not been pierced.",
                                                    "[npc.NamePos] tongue has not been pierced."));
                                            piercingBlocked = true;
                                        }
                                    }
                                    break;

                                case HORNS:
                                    if (equippedToCharacter.getHornType().equals(HornType.NONE)) {
                                        setBlockedTooltipContent(getTooltipText(equippedToCharacter,
                                                "You don't have any horns.",
                                                "[npc.Name] doesn't have any horns."));
                                        piercingBlocked = true;
                                    }
                                    break;

                                case PENIS:
                                    if (!equippedToCharacter.hasPenisIgnoreDildo()) {
                                        setBlockedTooltipContent(getTooltipText(equippedToCharacter,
                                                "You don't have a penis.",
                                                "[npc.Name] doesn't have a penis."));
                                        piercingBlocked = true;
                                    }
                                    break;

                                case TAIL:
                                    if (equippedToCharacter.getTailType() == TailType.NONE) {
                                        setBlockedTooltipContent(getTooltipText(equippedToCharacter,
                                                "You don't have a tail.",
                                                "[npc.Name] doesn't have a tail."));
                                        piercingBlocked = true;
                                    }
                                    break;

                                case VAGINA:
                                    if (!equippedToCharacter.hasVagina()) {
                                        setBlockedTooltipContent(getTooltipText(equippedToCharacter,
                                                "You don't have a vagina.",
                                                "[npc.Name] doesn't have a vagina."));
                                        piercingBlocked = true;
                                    }
                                    break;

                                case WINGS:
                                    if (equippedToCharacter.getWingType() == WingType.NONE) {
                                        setBlockedTooltipContent(getTooltipText(equippedToCharacter,
                                                "You don't have any wings.",
                                                "[npc.Name] doesn't have any wings."));
                                        piercingBlocked = true;
                                    }
                                    break;

                                default:
                                    break;
                            }

                            if (!piercingBlocked) {
                                if (renderingTattoos) {
                                    scarTooltip(equippedToCharacter.getScarInSlot(invSlot));

                                } else {
                                    setEmptyInventorySlotTooltipContent();
                                }
                            }
                        }

                    } else {
                        if (renderingTattoos && !invSlot.isJewellery()) {
                            tattooTooltip(equippedToCharacter.getTattooInSlot(invSlot));
                        } else {
                            clothingTooltip(equippedToCharacter.getClothingInSlot(invSlot));
                        }
                    }

                } else {
                    setEmptyInventorySlotTooltipContent();
                }
            }

        } else if (enchantmentModifier != null) {
            Main.mainController.setTooltipSize(TOOLTIP_WIDTH, 152);
            Main.mainController.setTooltipContent(UtilText.parse(
                    "<div class='title' style='color:" + enchantmentModifier.getRarity().getColour().toWebHexString() + ";'>"
                            + Util.capitaliseSentence(enchantmentModifier.getName())
                            + "</div>"
                            + "<div class='description' style='height:48px'>"
                            + UtilText.parse(enchantmentModifier.getDescription())
                            + "</div>"
                            + "<div class='subTitle'>"
                            + (EnchantmentDialogue.getIngredient() instanceof Tattoo
                            ? UtilText.formatAsMoney(enchantmentModifier.getValue() * EnchantingUtils.FLAME_COST_MODIFER, "b") + " cost"
                            : UtilText.formatAsEssences(enchantmentModifier.getValue(), "b", false) + " essence cost")
                            + "</div>"));

        } else if (potency != null) {
            Main.mainController.setTooltipSize(TOOLTIP_WIDTH, 60);
            Main.mainController.setTooltipContent(UtilText.parse("<div class='title'>Set potency to <b style='color:" + potency.getColour().toWebHexString() + ";'>" + Util.capitaliseSentence(potency.getName()) + "</b></div>"));

        } else {
            return;
        }

        TooltipUpdateThread.updateToolTip(-1, -1);
        // Main.mainController.getTooltip().show(Main.primaryStage);
    }


    private void setBlockedTooltipContent(String description) {
        setBlockedTooltipContent("<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Blocked!</span>", description);
    }

    private void setBlockedTooltipContent(String title, String description) {
        boolean dirty = equippedToCharacter.isDirtySlot(invSlot);
        Main.mainController.setTooltipSize(TOOLTIP_WIDTH, 144);
        Main.mainController.setTooltipContent(UtilText.parse(equippedToCharacter,
                "<div class='title'>"
                        + Util.capitaliseSentence(invSlot.getName()) + ": " + title
                        + "</div>"
                        + "<div class='description' style='height:72px; text-align:center;'>"
                        + (dirty
                        ? "[npc.NamePos] " + invSlot.getName() + " " + (invSlot.isPlural() ? "are" : "is")
                        + " <span style='color:" + PresetColour.CUM.toWebHexString() + ";'>dirty</span>!<br/>"
                        : "")
                        + UtilText.parse(description)
                        + "</div>"));
    }


    private void setEmptyInventorySlotTooltipContent() {
        if (equippedToCharacter == null) {
            Main.mainController.setTooltipSize(TOOLTIP_WIDTH, 60);
            Main.mainController.setTooltipContent("<div class='title'>"
                    + Util.capitaliseSentence(invSlot.getName())
                    + "</div>");
            return;
        }
        boolean dirty = equippedToCharacter.isDirtySlot(invSlot);
        Main.mainController.setTooltipSize(TOOLTIP_WIDTH, 60 + (dirty ? 56 : 0));
        Main.mainController.setTooltipContent(UtilText.parse(equippedToCharacter,
                "<div class='title'>"
                        + Util.capitaliseSentence(invSlot.getName())
                        + "</div>"
                        + (dirty
                        ? "<div class='description' style='height:48px; text-align:center;'>"
                        + "[npc.NamePos] " + invSlot.getName() + " " + (invSlot.isPlural() ? "have" : "has")
                        + " been <span style='color:" + PresetColour.CUM.toWebHexString() + ";'>dirtied</span> by sexual fluids!"
                        + "</div>"
                        : "")));
    }


    public TooltipInventoryEventListener setCoreItem(AbstractCoreItem coreItem, GameCharacter owner, GameCharacter equippedToCharacter) {
        resetVariables();
        this.coreItem = coreItem;
        this.equippedToCharacter = equippedToCharacter;
        this.owner = owner;
        return this;
    }

    public TooltipInventoryEventListener setItem(AbstractItem item, GameCharacter owner, GameCharacter equippedToCharacter) {
        resetVariables();
        this.item = item;
        this.equippedToCharacter = equippedToCharacter;
        this.owner = owner;
        return this;
    }

    public TooltipInventoryEventListener setTattoo(InventorySlot invSlot, Tattoo tattoo, GameCharacter owner, GameCharacter equippedToCharacter) {
        resetVariables();
        this.invSlot = invSlot;
        this.tattoo = tattoo;
        this.equippedToCharacter = equippedToCharacter;
        this.owner = owner;
        return this;
    }

    public TooltipInventoryEventListener setGenericItem(AbstractItemType genericItem) {
        resetVariables();
        this.genericItem = genericItem;
        return this;
    }

    public TooltipInventoryEventListener setClothing(AbstractClothing clothing, GameCharacter owner, GameCharacter equippedToCharacter) {
        resetVariables();
        this.clothing = clothing;
        this.equippedToCharacter = equippedToCharacter;
        this.owner = owner;
        return this;
    }

    public TooltipInventoryEventListener setDyeClothing(AbstractClothing dyeClothing, int colourIndex, Colour colour) {
        resetVariables();
        this.dyeClothing = dyeClothing;
        this.colourIndex = colourIndex;
        this.colour = colour;
        return this;
    }

    public TooltipInventoryEventListener setDyeClothingPattern(AbstractClothing dyeClothing, int colourIndex, Colour patternColour) {
        resetVariables();
        this.dyeClothing = dyeClothing;
        this.colourIndex = colourIndex;
        this.patternColour = patternColour;
        return this;
    }

    public TooltipInventoryEventListener setDyeWeapon(AbstractWeapon dyeWeapon, int colourIndex, Colour colour) {
        resetVariables();
        this.dyeWeapon = dyeWeapon;
        this.colourIndex = colourIndex;
        this.colour = colour;
        return this;
    }

    public TooltipInventoryEventListener setDamageTypeWeapon(AbstractWeapon dyeWeapon, DamageType damageType) {
        resetVariables();
        this.dyeWeapon = dyeWeapon;
        this.damageType = damageType;
        return this;
    }

    public TooltipInventoryEventListener setGenericClothing(AbstractClothingType genericClothing) {
        resetVariables();
        this.genericClothing = genericClothing;
        return this;
    }

    public TooltipInventoryEventListener setGenericClothing(AbstractClothingType genericClothing, Colour colour) {
        resetVariables();
        this.genericClothing = genericClothing;
        this.colour = colour;
        return this;
    }

    public TooltipInventoryEventListener setGenericTattoo(AbstractTattooType genericTattoo) {
        resetVariables();
        this.genericTattoo = genericTattoo;
        invSlot = genericTattoo.getSlotAvailability().contains(InventorySlot.TORSO_UNDER) ? InventorySlot.TORSO_UNDER : genericTattoo.getSlotAvailability().get(0);
        return this;
    }

    public TooltipInventoryEventListener setGenericWeapon(AbstractWeaponType genericWeapon, DamageType dt) {
        resetVariables();
        this.genericWeapon = genericWeapon;
        this.dt = dt;
        return this;
    }

    public TooltipInventoryEventListener setWeapon(AbstractWeapon weapon, GameCharacter owner, boolean isEquipped) {
        resetVariables();
        this.weapon = weapon;
        if (isEquipped) {
            this.equippedToCharacter = owner;
        }
        this.owner = owner;
        return this;
    }

    public TooltipInventoryEventListener setInventorySlot(InventorySlot invSlot, GameCharacter equippedToCharacter) {
        resetVariables();
        this.invSlot = invSlot;
        this.equippedToCharacter = equippedToCharacter;
        this.owner = equippedToCharacter;
        return this;
    }

    public TooltipInventoryEventListener setTFModifier(TFModifier enchantmentModifier) {
        resetVariables();
        this.enchantmentModifier = enchantmentModifier;
        return this;
    }

    public TooltipInventoryEventListener setTFPotency(TFPotency potency) {
        resetVariables();
        this.potency = potency;
        return this;
    }

    private void resetVariables() {
        owner = null;
        equippedToCharacter = null;
        coreItem = null;
        item = null;
        tattoo = null;
        genericItem = null;
        weapon = null;
        genericWeapon = null;
        dt = null;
        clothing = null;
        patternColour = null;
        colour = null;
        colourIndex = 0;
        dyeClothing = null;
        dyeWeapon = null;
        damageType = null;
        genericClothing = null;
        genericTattoo = null;
        invSlot = null;
        enchantmentModifier = null;
        potency = null;
    }

    private void itemTooltip(AbstractItem absItem) {

        int yIncrease = 0;
        int listIncrease = 0;

        String author = absItem.getItemType().getAuthorDescription();
        if (!author.isEmpty()) {
            yIncrease += 4;
        }

        if (!absItem.getEffects().isEmpty()) {
            listIncrease += 1;
            for (ItemEffect ie : absItem.getEffects()) {
                listIncrease += ie.getEffectsDescription(Main.game.getPlayer(), Main.game.getPlayer()).size();
            }
            listIncrease += absItem.getEffectTooltipLines().size();
        }

        if (!absItem.getExtraDescriptions(equippedToCharacter).isEmpty()) { //TODO
            yIncrease += 2 + absItem.getExtraDescriptions(equippedToCharacter).size();
        }


        // Title:
        tooltipSB.setLength(0);
        tooltipSB.append("<body>" + "<div class='container-full-width center'><h5>").append(Util.capitaliseSentence(absItem.getDisplayName(true))).append("</h5></div>");

        // Core info:
        tooltipSB.append("<div class='container-full-width titular'>").append(absItem.isConsumedOnUse()
                ? "<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>Consumed on use</span>"
                : "<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Infinite uses</span>").append("</div>");

        tooltipSB.append("<div class='container-full-width'>" + "<div class='container-half-width titular' style='width:calc(66.6% - 16px);'>" + "<span style='color:").append(absItem.getRarity().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(absItem.getRarity().getName())).append("</span>");


        if (absItem.isAppendItemEffectLinesToTooltip()) {
            String effectEntry = "";
            int effectMulti = 0;
            for (int it = 0; it < absItem.getEffects().size(); it++) {
                ItemEffect ie = absItem.getEffects().get(it);
                StringBuilder effectSB = new StringBuilder();
                for (int i = 0; i < ie.getEffectsDescription(Main.game.getPlayer(), Main.game.getPlayer()).size(); i++) {
                    if (i != 0) {
                        effectSB.append("</br>");
                    }
                    effectSB.append(ie.getEffectsDescription(Main.game.getPlayer(), Main.game.getPlayer()).get(i));
                }

                effectEntry = effectSB.toString();
                if (it == absItem.getEffects().size() - 1 || !absItem.getEffects().get(it + 1).equals(ie)) {
                    tooltipSB.append("</br>");
                    if (effectMulti > 0) {
                        tooltipSB.append("[style.colourArcane(x").append(effectMulti + 1).append(")] ");
                        listIncrease -= effectMulti;
                    }
                    tooltipSB.append(effectEntry);
                    effectMulti = 0;

                } else {
                    effectMulti++;
                }
            }
        }
        yIncrease += Math.max(0, listIncrease - 4);

        for (String s : absItem.getEffectTooltipLines()) {
            tooltipSB.append("</br>").append(s);
        }

        tooltipSB.append("</div>");

        // Picture:
        tooltipSB.append("<div class='item-image'>" + "<div class='item-image-content'>").append(absItem.getSVGString()).append("</div>").append("</div>");

        tooltipSB.append("</div>");

        tooltipSB.append("<div class='container-full-width' style='padding:8px; min-height:106px;'>").append(absItem.getDescription()).append("</div>");


        // Extra descriptions:
        List<String> extraDescriptions = absItem.getExtraDescriptions(equippedToCharacter);

        if (!extraDescriptions.isEmpty()) {
            tooltipSB.append("<div class='container-full-width titular' style='font-weight: normal;'>");
            tooltipSB.append("<b>Status</b>");
            for (int i = 0; i < extraDescriptions.size(); i++) {
                tooltipSB.append("<br/>");
                tooltipSB.append(extraDescriptions.get(i));
            }
            tooltipSB.append("</div>");
        }


        // Value:

        if (owner != null && InventoryDialogue.getInventoryNPC() != null && InventoryDialogue.getNPCInventoryInteraction() == InventoryInteraction.TRADING) {
            if (owner.isPlayer()) {
                if (InventoryDialogue.getInventoryNPC().willBuy(absItem)) {
                    tooltipSB.append("<div class='container-full-width titular'>" + "Value: ").append(UtilText.formatAsMoney(absItem.getValue())).append(" | ").append(InventoryDialogue.getInventoryNPC().getName("The")).append(" offers: ").append(UtilText.formatAsMoney(absItem.getPrice(InventoryDialogue.getInventoryNPC().getBuyModifier()))).append("</div>");
                } else {
                    tooltipSB.append("<div class='container-full-width titular'>" + "Value: ").append(UtilText.formatAsMoney(absItem.getValue())).append(" | ").append("<span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>").append(InventoryDialogue.getInventoryNPC().getName("The")).append(" will not buy this</span>").append("</div>");
                }
            } else {
                if (InventoryDialogue.isBuyback()) {
                    tooltipSB.append("<div class='container-full-width titular'>" + "Value: ").append(UtilText.formatAsMoney(absItem.getValue())).append(" | ").append(InventoryDialogue.getInventoryNPC().getName("The")).append(" wants ").append(UtilText.formatAsMoney(+getBuybackPriceFor(absItem))).append("</div>");
                } else {
                    tooltipSB.append("<div class='container-full-width titular'>" + "Value: ").append(UtilText.formatAsMoney(absItem.getValue())).append(" | ").append(InventoryDialogue.getInventoryNPC().getName("The")).append(" wants ").append(UtilText.formatAsMoney(absItem.getPrice(InventoryDialogue.getInventoryNPC().getSellModifier(absItem)))).append("</div>");
                }
            }
        } else {
            tooltipSB.append("<div class='container-full-width titular'>" + "Value: ").append(UtilText.formatAsMoney(absItem.getValue())).append("</div>");
        }

        if (!author.isEmpty()) {
            tooltipSB.append("<div class='description' style='height:52px;'>").append(author).append("</div>");
        }

        tooltipSB.append("</body>");

        Main.mainController.setTooltipSize(TOOLTIP_WIDTH, 364 + (yIncrease * LINE_HEIGHT));
        Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));
    }

    private void weaponTooltip(AbstractWeapon absWep) {

        int yIncrease = 0;
        int listIncrease = 2 + absWep.getAttributeModifiers().size();
        listIncrease += absWep.getSpells().size();
        listIncrease += absWep.getWeaponType().getExtraEffects().size();

        String author = absWep.getWeaponType().getAuthorDescription();
        if (!author.isEmpty()) {
            yIncrease += 4;
        }
        if (!absWep.getExtraDescriptions(equippedToCharacter).isEmpty()) { //TODO
            yIncrease += 2 + absWep.getExtraDescriptions(equippedToCharacter).size();
        }

        // Title:
        tooltipSB.setLength(0);
        tooltipSB.append("<body>" + "<div class='container-full-width center'><h5>").append(Util.capitaliseSentence(absWep.getDisplayName(true))).append("</h5></div>");

        // Core info:
        tooltipSB.append("<div class='container-half-width titular' style='color:").append(absWep.getDamageType().getMultiplierAttribute().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(absWep.getDamageType().getName())).append(" damage</div>");
        tooltipSB.append("<div class='container-half-width titular'>").append(absWep.getWeaponType().getClothingSet() == null
                ? "<span style='color:" + PresetColour.TEXT_GREY.toWebHexString() + ";'>Not part of a set</span>"
                : "<span style='color:" + PresetColour.RARITY_EPIC.toWebHexString() + ";'>" + absWep.getWeaponType().getClothingSet().getName() + " set</span>").append("</div>");


        // Attribute modifiers:
        tooltipSB.append("<div class='container-full-width'>");
        tooltipSB.append("<div class='container-half-width titular' style='width:calc(66.6% - 16px);'>");
        tooltipSB.append("<span style='color:").append(absWep.getRarity().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(absWep.getRarity().getName())).append("</span>").append(" | ").append(absWep.getWeaponType().isUsingUnarmedCalculation()
                ? "[style.colourUnarmed(Unarmed)]"
                : (absWep.getWeaponType().isMelee()
                ? "[style.colourMelee(Melee)]"
                : "[style.colourRanged(Ranged)]")).append("</br>").append(absWep.getWeaponType().isTwoHanded() ? "Two-handed" : "One-handed").append(absWep.getWeaponType().isOneShot() ? " - [style.colourYellow(One-shot)]" : "").append("</br>");

        float res = absWep.getWeaponType().getPhysicalResistance();
        if (res > 0) {
            listIncrease++;
            tooltipSB.append("[style.boldGood(+").append(res).append(")] Natural [style.boldResPhysical(").append(Util.capitaliseSentence(Attribute.RESISTANCE_PHYSICAL.getName())).append(")]</br>");
        }

        int cost = absWep.getWeaponType().getArcaneCost();
        if (cost > 0) {
            listIncrease++;
            tooltipSB.append("Costs [style.boldArcane(").append(cost).append(" Arcane essence").append(cost > 1 ? "s" : "").append(")] ").append(absWep.getWeaponType().isMelee() ? "per attack" : "to fire").append("<br/>");
            if (absWep.getWeaponType().isMelee()) {
                listIncrease++; // To account for the fact that the arcane cost description for melee weapons takes two lines
            }
        }

        if (equippedToCharacter != null) {
            if (absWep.getWeaponType().isUsingUnarmedCalculation()) {
                listIncrease++;
                tooltipSB.append("Includes [style.boldUnarmed(").append(equippedToCharacter.getUnarmedDamage()).append(" unarmed damage)]<br/>");
            }
            tooltipSB.append("<b>").append(Attack.getMinimumDamage(equippedToCharacter, null, Attack.MAIN, absWep)).append(" - ").append(Attack.getMaximumDamage(equippedToCharacter, null, Attack.MAIN, absWep)).append("</b>").append(" <b style='color:").append(absWep.getDamageType().getMultiplierAttribute().getColour().toWebHexString()).append(";'>Damage</b>");

            for (Value<Integer, Integer> aoe : absWep.getWeaponType().getAoeDamage()) {
                listIncrease++;
                int aoeChance = aoe.getKey();
                tooltipSB.append("<br/>[style.boldAqua(AoE)]: " + "(<b style='color:").append((aoeChance <= 25 ? PresetColour.GENERIC_BAD : (aoeChance <= 50 ? PresetColour.GENERIC_MINOR_BAD : (aoeChance <= 75 ? PresetColour.GENERIC_MINOR_GOOD : PresetColour.GENERIC_GOOD))).toWebHexString()).append(";'>").append(aoeChance).append("%</b>): ").append("<b>").append(Attack.getMinimumDamage(equippedToCharacter, null, Attack.MAIN, absWep, aoe.getValue())).append(" - ").append(Attack.getMaximumDamage(equippedToCharacter, null, Attack.MAIN, absWep, aoe.getValue())).append("</b>").append(" <b style='color:").append(absWep.getDamageType().getMultiplierAttribute().getColour().toWebHexString()).append(";'>Damage</b>");
            }

        } else {
            if (owner != null && !owner.isPlayer()) {
                listIncrease++;
                tooltipSB.append(UtilText.parse(owner, "[npc.Name]: ")).append("<b>").append(Attack.getMinimumDamage(owner, null, Attack.MAIN, absWep)).append(" - ").append(Attack.getMaximumDamage(owner, null, Attack.MAIN, absWep)).append("</b>").append(" <b style='color:").append(absWep.getDamageType().getMultiplierAttribute().getColour().toWebHexString()).append(";'>Damage</b>");
                for (Value<Integer, Integer> aoe : absWep.getWeaponType().getAoeDamage()) {
                    listIncrease++;
                    int aoeChance = aoe.getKey();
                    tooltipSB.append("<br/>[style.boldAqua(AoE)]: " + "(<b style='color:").append((aoeChance <= 25 ? PresetColour.GENERIC_BAD : (aoeChance <= 50 ? PresetColour.GENERIC_MINOR_BAD : (aoeChance <= 75 ? PresetColour.GENERIC_MINOR_GOOD : PresetColour.GENERIC_GOOD))).toWebHexString()).append(";'>").append(aoeChance).append("%</b>): ").append("<b>").append(Attack.getMinimumDamage(owner, null, Attack.MAIN, absWep, aoe.getValue())).append(" - ").append(Attack.getMaximumDamage(owner, null, Attack.MAIN, absWep, aoe.getValue())).append("</b>").append(" <b style='color:").append(absWep.getDamageType().getMultiplierAttribute().getColour().toWebHexString()).append(";'>Damage</b>");
                }
                tooltipSB.append("<br/>You: ");
            }
            tooltipSB.append("<b>").append(Attack.getMinimumDamage(Main.game.getPlayer(), null, Attack.MAIN, absWep)).append(" - ").append(Attack.getMaximumDamage(Main.game.getPlayer(), null, Attack.MAIN, absWep)).append("</b>").append(" <b style='color:").append(absWep.getDamageType().getMultiplierAttribute().getColour().toWebHexString()).append(";'>Damage</b>");

            for (Value<Integer, Integer> aoe : absWep.getWeaponType().getAoeDamage()) {
                listIncrease++;
                int aoeChance = aoe.getKey();
                tooltipSB.append("<br/>[style.boldAqua(AoE)] " + "(<b style='color:").append((aoeChance <= 25 ? PresetColour.GENERIC_BAD : (aoeChance <= 50 ? PresetColour.GENERIC_MINOR_BAD : (aoeChance <= 75 ? PresetColour.GENERIC_MINOR_GOOD : PresetColour.GENERIC_GOOD))).toWebHexString()).append(";'>").append(aoeChance).append("%</b>): ").append("<b>").append(Attack.getMinimumDamage(Main.game.getPlayer(), null, Attack.MAIN, absWep, aoe.getValue())).append(" - ").append(Attack.getMaximumDamage(Main.game.getPlayer(), null, Attack.MAIN, absWep, aoe.getValue())).append("</b>").append(" <b style='color:").append(absWep.getDamageType().getMultiplierAttribute().getColour().toWebHexString()).append(";'>Damage</b>");
            }
        }

        if (absWep.getWeaponType().isOneShot()) {
            listIncrease++;
            listIncrease++;
            int chanceToRecoverTurn = (int) absWep.getWeaponType().getOneShotChanceToRecoverAfterTurn();
            int chanceToRecoverCombat = (int) absWep.getWeaponType().getOneShotChanceToRecoverAfterCombat();

            tooltipSB.append("<br/><span style='color:").append((chanceToRecoverTurn <= 25 ? PresetColour.GENERIC_BAD : (chanceToRecoverTurn <= 50 ? PresetColour.GENERIC_MINOR_BAD : (chanceToRecoverTurn <= 75 ? PresetColour.GENERIC_MINOR_GOOD : PresetColour.GENERIC_GOOD))).toWebHexString()).append("'>").append(chanceToRecoverTurn).append("%</span> recovery [style.colourBlueLight(after use)]<br/>");

            tooltipSB.append("<span style='color:").append((chanceToRecoverCombat <= 25 ? PresetColour.GENERIC_BAD : (chanceToRecoverCombat <= 50 ? PresetColour.GENERIC_MINOR_BAD : (chanceToRecoverCombat <= 75 ? PresetColour.GENERIC_MINOR_GOOD : PresetColour.GENERIC_GOOD))).toWebHexString()).append(";'>").append(chanceToRecoverCombat).append("%</span> recovery [style.colourCombat(after combat)]");
        }

        for (String s : absWep.getWeaponType().getExtraEffects()) {
            tooltipSB.append("<br/><b>").append(s).append("</b>");
        }

        for (Entry<AbstractAttribute, Integer> entry : absWep.getAttributeModifiers().entrySet()) {
            tooltipSB.append("<br/><b>").append(entry.getKey().getFormattedValue(entry.getValue())).append("</b>");
        }

        for (Spell s : absWep.getSpells()) {
            tooltipSB.append("<br/>[style.boldSpell(Spell)]<b>:</b> <b style='color:").append(s.getSpellSchool().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(s.getName())).append("</b>");
        }

        for (AbstractCombatMove cm : absWep.getCombatMoves()) {
            tooltipSB.append("<br/>[style.boldCombat(Move)]<b>:</b> ").append(Util.capitaliseSentence(cm.getName(0, Main.game.getPlayer())));
        }

        tooltipSB.append("</div>");

        // Picture:
        tooltipSB.append("<div class='item-image'>" + "<div class='item-image-content'>").append((owner != null && owner.hasWeaponEquipped(absWep))
                ? absWep.getSVGEquippedString(owner)
                : absWep.getSVGString()).append("</div>").append("</div>");

        tooltipSB.append("</div>");

        tooltipSB.append("<div class='container-full-width' style='padding:8px; min-height:106px;'>").append(UtilText.parse(absWep.getWeaponType().getDescription())).append("</div>");

        if (owner != null && owner.getEssenceCount() < absWep.getWeaponType().getArcaneCost()) {
            yIncrease += 2;
            tooltipSB.append("<div class='container-full-width titular'>"
                    + "[style.colourBad(Not enough essences to fire!)]"
                    + "</div>");
        }

        // Extra descriptions:
        List<String> extraDescriptions = absWep.getExtraDescriptions(equippedToCharacter);

        if (!extraDescriptions.isEmpty()) {
            tooltipSB.append("<div class='container-full-width titular' style='font-weight: normal;'>");
            tooltipSB.append("<b>Status</b>");
            for (int i = 0; i < extraDescriptions.size(); i++) {
                tooltipSB.append("<br/>");
                tooltipSB.append(extraDescriptions.get(i));
            }
            tooltipSB.append("</div>");
        }

        // Value:

        if (InventoryDialogue.getInventoryNPC() != null && InventoryDialogue.getNPCInventoryInteraction() == InventoryInteraction.TRADING) {
            if (owner.isPlayer()) {
                if (InventoryDialogue.getInventoryNPC().willBuy(absWep)) {
                    tooltipSB.append("<div class='container-full-width titular'>" + "Value: ").append(UtilText.formatAsMoney(absWep.getValue())).append(" | ").append(InventoryDialogue.getInventoryNPC().getName("The")).append(" offers: ").append(UtilText.formatAsMoney(absWep.getPrice(InventoryDialogue.getInventoryNPC().getBuyModifier()))).append("</div>");
                } else {
                    tooltipSB.append("<div class='container-full-width titular'>" + "Value: ").append(UtilText.formatAsMoney(absWep.getValue())).append(" | ").append("<span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>").append(InventoryDialogue.getInventoryNPC().getName("The")).append(" will not buy this</span>").append("</div>");
                }
            } else {
                if (InventoryDialogue.isBuyback()) {
                    tooltipSB.append("<div class='container-full-width titular'>" + "Value: ").append(UtilText.formatAsMoney(absWep.getValue())).append(" | ").append(InventoryDialogue.getInventoryNPC().getName("The")).append(" wants ").append(UtilText.formatAsMoney(+getBuybackPriceFor(absWep))).append("</div>");
                } else {
                    tooltipSB.append("<div class='container-full-width titular'>" + "Value: ").append(UtilText.formatAsMoney(absWep.getValue())).append(" | ").append(InventoryDialogue.getInventoryNPC().getName("The")).append(" wants ").append(UtilText.formatAsMoney(absWep.getPrice(InventoryDialogue.getInventoryNPC().getSellModifier(absWep)))).append("</div>");
                }
            }
        } else {
            tooltipSB.append("<div class='container-full-width titular'>" + "Value: ").append(UtilText.formatAsMoney(absWep.getValue())).append("</div>");
        }
        if (Main.game.isEnchantmentCapacityEnabled()) {
            int enchCapacityCost = absWep.getEnchantmentCapacityCost();
            tooltipSB.append("<div class='container-full-width titular'>").append(enchCapacityCost == 0
                    ? Util.capitaliseSentence(Attribute.ENCHANTMENT_LIMIT.getName()) + " cost: [style.boldDisabled(" + enchCapacityCost + ")]"
                    : "[style.colourEnchantment(" + Util.capitaliseSentence(Attribute.ENCHANTMENT_LIMIT.getName()) + " cost)]: [style.boldBad(" + enchCapacityCost + ")]").append("</div>");
        }
        if (!author.isEmpty()) {
            tooltipSB.append("<div class='description' style='height:52px;'>").append(author).append("</div>");
        }

        tooltipSB.append("</body>");

        yIncrease += Math.max(0, listIncrease - 4);
        Main.mainController.setTooltipSize(TOOLTIP_WIDTH, 364 + (Main.game.isEnchantmentCapacityEnabled() ? 32 : 0) + (yIncrease * LINE_HEIGHT));
        Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));

    }

    private void clothingTooltip(AbstractClothing absClothing) {
        int yIncrease = 0;

        int listIncrease = absClothing.getAttributeModifiers().size();

        float resistance = absClothing.getClothingType().getPhysicalResistance();
        if (resistance > 0) {
            listIncrease++;
        }

        InventorySlot slotEquippedTo = absClothing.getSlotEquippedTo();
        yIncrease += absClothing.getExtraDescriptions(equippedToCharacter, null, false).size();
        if (slotEquippedTo == null) {
            slotEquippedTo = absClothing.getClothingType().getEquipSlots().get(0);
            for (InventorySlot is : absClothing.getClothingType().getEquipSlots()) {
                yIncrease += absClothing.getExtraDescriptions(equippedToCharacter, is, false).size();
            }

        } else {
            yIncrease += absClothing.getExtraDescriptions(equippedToCharacter, slotEquippedTo, false).size();
        }

        for (ItemEffect ie : absClothing.getEffects()) {
            if (ie.getSecondaryModifier() == TFModifier.CLOTHING_ENSLAVEMENT
                    || ie.getSecondaryModifier() == TFModifier.CLOTHING_SEALING) {
                listIncrease += 1;

            } else if (ie.getPrimaryModifier() != TFModifier.CLOTHING_ATTRIBUTE && ie.getPrimaryModifier() != TFModifier.CLOTHING_MAJOR_ATTRIBUTE) {
                listIncrease += 2;
            }
        }
        yIncrease += Math.max(0, listIncrease - 4);

        String author = absClothing.getClothingType().getAuthorDescription();
        if (!author.isEmpty()) {
            yIncrease += 4;
        }

        // Title:
        tooltipSB.setLength(0);
        tooltipSB.append("<body>" + "<div class='container-full-width center'><h5>").append(Util.capitaliseSentence(absClothing.getDisplayName(true))).append("</h5></div>");

        // Core info:
        tooltipSB.append("<div class='container-half-width titular'>");
        boolean nonPiercingSlots = absClothing.getClothingType().getEquipSlots().stream().anyMatch(is -> !is.isJewellery());
        List<InventorySlot> possibleSlots = new ArrayList<>(absClothing.getClothingType().getEquipSlots());
        if (nonPiercingSlots) {
            possibleSlots.sort((is1, is2) -> absClothing.getSlotEquippedTo() == is1 ? 1 : (absClothing.getSlotEquippedTo() == is2 ? -1 : 0));
        }
        for (int i = 0; i < possibleSlots.size(); i++) {
            InventorySlot slot = possibleSlots.get(i);
            boolean equipped = absClothing.getSlotEquippedTo() == slot;
            if (nonPiercingSlots) { // Slots contain non-piercing slots
                tooltipSB.append(equipped || absClothing.getSlotEquippedTo() == null
                        ? Util.capitaliseSentence(slot.getName())
                        : "[style.colourDisabled(" + Util.capitaliseSentence(slot.getName()) + ")]").append(i == possibleSlots.size() - 1
                        ? ""
                        : (absClothing.getSlotEquippedTo() != null
                        ? "[style.colourDisabled(/)]"
                        : "/"));
            } else { // Slots are all piercings, so to abbreviate the slot names, the ' piercing' parts can all be removed, then a final ' piercing' can be appended at the end
                String slotName = slot.getName().replace(" piercing", "");
                tooltipSB.append(equipped || absClothing.getSlotEquippedTo() == null
                        ? Util.capitaliseSentence(slotName)
                        : "[style.colourDisabled(" + Util.capitaliseSentence(slotName) + ")]").append(i == possibleSlots.size() - 1
                        ? ""
                        : (absClothing.getSlotEquippedTo() != null
                        ? "[style.colourDisabled(/)]"
                        : "/"));
                if (i == possibleSlots.size() - 1) {
                    tooltipSB.append(" piercing");
                }
            }
        }
        tooltipSB.append("</div>");
        tooltipSB.append("<div class='container-half-width titular'>").append(absClothing.getClothingType().getClothingSet() == null
                ? "<span style='color:" + PresetColour.TEXT_GREY.toWebHexString() + ";'>Not part of a set</span>"
                : "<span style='color:" + PresetColour.RARITY_EPIC.toWebHexString() + ";'>" + absClothing.getClothingType().getClothingSet().getName() + " set</span>").append("</div>");

        // Attribute modifiers:
        tooltipSB.append("<div class='container-full-width'>"
                + "<div class='container-half-width titular' style='width:calc(66.6% - 16px);'>");

        Femininity femininityRestriction = absClothing.getClothingType().getFemininityRestriction();
        tooltipSB.append("<span style='color:").append((absClothing.isEnchantmentKnown() ? absClothing.getRarity().getColour() : PresetColour.TEXT_GREY).toWebHexString()).append(";'>").append(Util.capitaliseSentence(absClothing.isEnchantmentKnown() ? absClothing.getRarity().getName() : "Unknown")).append("</span>").append(" | ").append(femininityRestriction == null || femininityRestriction == Femininity.ANDROGYNOUS
                ? "[style.boldAndrogynous(Unisex)]"
                : (femininityRestriction.isFeminine()
                ? "[style.boldFeminine(Feminine)]"
                : "[style.boldMasculine(Masculine)]"));

        if (resistance > 0) {
            tooltipSB.append("</br>[style.boldGood(+").append(resistance).append(")] Natural [style.boldResPhysical(").append(Util.capitaliseSentence(Attribute.RESISTANCE_PHYSICAL.getName())).append(")]");
        }

        if (!absClothing.getEffects().isEmpty()) {
            if (!absClothing.isEnchantmentKnown()) {
                tooltipSB.append("<br/>[style.colourDisabled(Unidentified effects!)]");
            } else {
                for (ItemEffect e : absClothing.getEffects()) {
                    if (e.getPrimaryModifier() != TFModifier.CLOTHING_ATTRIBUTE && e.getPrimaryModifier() != TFModifier.CLOTHING_MAJOR_ATTRIBUTE) {
                        for (String s : e.getEffectsDescription(owner, owner)) {
                            tooltipSB.append("<br/>").append(s);
                        }
                    }
                }
                for (Entry<AbstractAttribute, Integer> entry : absClothing.getAttributeModifiers().entrySet()) {
                    tooltipSB.append("<br/><b>").append(entry.getKey().getFormattedValue(entry.getValue())).append("</b>");
                }
            }

        } else {
            tooltipSB.append("<br/>[style.colourDisabled(No bonuses)]");
        }

        tooltipSB.append("</div>");


        // Picture:
        tooltipSB.append("<div class='item-image'>" + "<div class='item-image-content'>").append(owner != null && owner.getClothingCurrentlyEquipped().contains(absClothing) ? absClothing.getSVGEquippedString(owner) : absClothing.getSVGString()).append("</div>").append("</div>");

        tooltipSB.append("</div>");

        tooltipSB.append("<div class='container-full-width' style='padding:8px; min-height:106px;'>").append(absClothing.getTypeDescription()).append("</div>");


        // Extra descriptions:

        tooltipSB.append("<div class='container-full-width titular' style='font-weight: normal;'>");

        List<String> extraDescriptions = new ArrayList<>(absClothing.getExtraDescriptions(equippedToCharacter, null, false));

        if (absClothing.getSlotEquippedTo() == null && absClothing.getClothingType().getEquipSlots().size() > 1) {
            for (int i = 0; i < absClothing.getClothingType().getEquipSlots().size(); i++) {
                InventorySlot slot = absClothing.getClothingType().getEquipSlots().get(i);

                if (!absClothing.getExtraDescriptions(equippedToCharacter, slot, false).isEmpty()) {
                    extraDescriptions.add("<i>When equipped into '" + slot.getName() + "' slot:</i>");
                    yIncrease++;
                    extraDescriptions.addAll(absClothing.getExtraDescriptions(equippedToCharacter, slot, false));
                }
            }

        } else {
            if (!absClothing.getExtraDescriptions(equippedToCharacter, slotEquippedTo, false).isEmpty()) {
                extraDescriptions.addAll(absClothing.getExtraDescriptions(equippedToCharacter, slotEquippedTo, false));
            }
        }
        if (extraDescriptions.isEmpty()) {
            tooltipSB.append("<span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>No Status</span>");

        } else {
            tooltipSB.append("<b>Status</b>");
            for (int i = 0; i < extraDescriptions.size(); i++) {
                tooltipSB.append("<br/>");
                tooltipSB.append(extraDescriptions.get(i));
            }
        }
        tooltipSB.append("</div>");


        // Value:
        if (InventoryDialogue.getInventoryNPC() != null && InventoryDialogue.getNPCInventoryInteraction() == InventoryInteraction.TRADING) {
            if (owner.isPlayer()) {
                if (InventoryDialogue.getInventoryNPC().willBuy(absClothing)) {
                    tooltipSB.append("<div class='container-full-width titular'>" + "Value: ").append(absClothing.isEnchantmentKnown() ? UtilText.formatAsMoney(absClothing.getValue()) : UtilText.formatAsMoney("?", "b")).append(" | ").append(InventoryDialogue.getInventoryNPC().getName("The")).append(" offers ").append(UtilText.formatAsMoney(absClothing.getPrice(InventoryDialogue.getInventoryNPC().getBuyModifier()))).append("</div>");
                } else {
                    tooltipSB.append("<div class='container-full-width titular'>" + "Value: ").append(absClothing.isEnchantmentKnown() ? UtilText.formatAsMoney(absClothing.getValue()) : UtilText.formatAsMoney("?", "b")).append(" | ").append("<span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>").append(InventoryDialogue.getInventoryNPC().getName("The")).append(" will not buy this</span>").append("</div>");
                }
            } else {
                if (InventoryDialogue.isBuyback()) {
                    tooltipSB.append("<div class='container-full-width titular'>" + "Value: ").append(absClothing.isEnchantmentKnown() ? UtilText.formatAsMoney(absClothing.getValue()) : UtilText.formatAsMoney("?", "b")).append(" | ").append(InventoryDialogue.getInventoryNPC().getName("The")).append(" wants ").append(UtilText.formatAsMoney(+getBuybackPriceFor(absClothing))).append("</div>");
                } else {
                    tooltipSB.append("<div class='container-full-width titular'>" + "Value: ").append(absClothing.isEnchantmentKnown() ? UtilText.formatAsMoney(absClothing.getValue()) : UtilText.formatAsMoney("?", "b")).append(" | ").append(InventoryDialogue.getInventoryNPC().getName("The")).append(" wants ").append(UtilText.formatAsMoney(absClothing.getPrice(InventoryDialogue.getInventoryNPC().getSellModifier(absClothing)))).append("</div>");
                }
            }
        } else {
            tooltipSB.append("<div class='container-full-width titular'>Value: ").append(absClothing.isEnchantmentKnown() ? UtilText.formatAsMoney(absClothing.getValue()) : UtilText.formatAsMoney("?", "b")).append("</div>");
        }

        if (Main.game.isEnchantmentCapacityEnabled()) {
            int enchCapacityCost = absClothing.getEnchantmentCapacityCost();
            tooltipSB.append("<div class='container-full-width titular'>").append(absClothing.isEnchantmentKnown() ?
                    (enchCapacityCost == 0
                            ? Util.capitaliseSentence(Attribute.ENCHANTMENT_LIMIT.getName()) + " cost: [style.boldDisabled(" + enchCapacityCost + ")]"
                            : "[style.colourEnchantment(" + Util.capitaliseSentence(Attribute.ENCHANTMENT_LIMIT.getName()) + " cost)]: [style.boldBad(" + enchCapacityCost + ")]")
                    : Util.capitaliseSentence(Attribute.ENCHANTMENT_LIMIT.getName()) + " cost: ?").append("</div>");
        }

        if (!author.isEmpty()) {
            tooltipSB.append("<div class='description' style='height:52px;'>").append(author).append("</div>");
        }

        tooltipSB.append("</body>");

        int specialIncrease = 0;
        if (absClothing.getDisplayName(false).length() > 40) {
            specialIncrease = 26;
        }
        Main.mainController.setTooltipSize(TOOLTIP_WIDTH, 400 + (Main.game.isEnchantmentCapacityEnabled() ? 32 : 0) + (yIncrease * LINE_HEIGHT) + specialIncrease);
        Main.mainController.setTooltipContent(UtilText.parse(equippedToCharacter == null ? Main.game.getPlayer() : equippedToCharacter, tooltipSB.toString()));

    }


    private void scarTooltip(Scar scar) {
        int yIncrease = 0;
        // Title:
        tooltipSB.setLength(0);
        tooltipSB.append("<body>"
                + "<div class='container-full-width center'><h5>No tattoo</h5></div>");

        // Core info:
        tooltipSB.append("<div class='container-half-width titular'>").append(Util.capitaliseSentence(invSlot.getTattooSlotName())).append("</div>");
        tooltipSB.append("<div class='container-half-width titular'>").append(scar == null
                ? "<span style='color:" + PresetColour.TEXT_GREY.toWebHexString() + ";'>No scars</span>"
                : "<span style='color:" + PresetColour.SCAR.toWebHexString() + ";'>" + Util.capitaliseSentence(owner.getScarInSlot(invSlot).getName()) + "</span>").append("</div>");

        SizedStack<Covering> lipsticks = owner.getLipstickMarkingsInSlot(invSlot);
        if (lipsticks != null) {
            yIncrease = 24 + (1 + lipsticks.size()) * LINE_HEIGHT;
            tooltipSB.append("<div class='container-full-width' style='text-align:center; padding:8px; height:").append(16 + (1 + lipsticks.size()) * LINE_HEIGHT).append("px;'>");
            tooltipSB.append(UtilText.parse(owner, "[npc.NamePos] ")).append(invSlot.getNameOfAssociatedPart(owner)).append(" ").append(invSlot.isPlural() ? "have" : "has").append(" been marked by:");
            for (int i = lipsticks.size() - 1; i >= 0; i--) {
                tooltipSB.append("<br/>").append(Util.capitaliseSentence(lipsticks.get(i).getFullDescription(owner, true)));
            }
            tooltipSB.append("</div>");
        }

        Main.mainController.setTooltipSize(TOOLTIP_WIDTH, yIncrease + 88);
        Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));
    }

    private void tattooTooltip(Tattoo tattoo) {
        int yIncrease = 0;
        int specialIncrease = 0;
        int lipstickYIncrease = 0;

        if (tattoo.getWriting() != null && !tattoo.getWriting().getText().isEmpty()) {
            yIncrease++;
        }
        if (tattoo.getCounter() != null && tattoo.getCounter().getType() != TattooCounterType.NONE) {
            yIncrease++;
        }
        int lSize = 0;
        for (ItemEffect e : tattoo.getEffects()) {
            if (e.getPrimaryModifier() == TFModifier.CLOTHING_ATTRIBUTE
                    || e.getPrimaryModifier() == TFModifier.CLOTHING_MAJOR_ATTRIBUTE
                    || e.getPrimaryModifier() == TFModifier.TF_MOD_FETISH_BEHAVIOUR
                    || e.getPrimaryModifier() == TFModifier.TF_MOD_FETISH_BODY_PART) {
                lSize++;
            } else {
                lSize += 2;
            }
        }
        lSize -= 4;
        if (lSize < 0) {
            lSize = 0;
        }

        // Title:
        tooltipSB.setLength(0);
        tooltipSB.append("<body>" + "<div class='container-full-width center'><h5>").append(Util.capitaliseSentence(tattoo.getDisplayName(true))).append("</h5></div>");

        // Core info:
        tooltipSB.append("<div class='container-half-width titular'>").append(invSlot.getTattooSlotName() == null ? "[style.colourDisabled(Cannot be tattooed)]" : Util.capitaliseSentence(invSlot.getTattooSlotName())).append("</div>");
        if (owner != null) {
            tooltipSB.append("<div class='container-half-width titular'>").append(owner.getScarInSlot(invSlot) == null
                    ? "<span style='color:" + PresetColour.TEXT_GREY.toWebHexString() + ";'>No scars</span>"
                    : "<span style='color:" + PresetColour.SCAR.toWebHexString() + ";'>" + Util.capitaliseSentence(owner.getScarInSlot(invSlot).getName()) + "</span>").append("</div>");
        }
        // Attribute modifiers:
        tooltipSB.append("<div class='container-full-width'>"
                + "<div class='container-half-width titular' style='width:calc(66.6% - 16px);'>");

        if (!tattoo.getEffects().isEmpty()) {
            int i = 0;
            for (ItemEffect e : tattoo.getEffects()) {
                if (e.getPrimaryModifier() != TFModifier.CLOTHING_ATTRIBUTE && e.getPrimaryModifier() != TFModifier.CLOTHING_MAJOR_ATTRIBUTE) {
                    for (String s : e.getEffectsDescription(owner, owner)) {
                        tooltipSB.append((i > 0 ? "<br/>" + s : s));
                    }
                    i++;
                }
            }
            for (Entry<AbstractAttribute, Integer> entry : tattoo.getAttributeModifiers().entrySet()) {
                tooltipSB.append(i > 0 ? "<br/>" : "").append("<b>").append(entry.getKey().getFormattedValue(entry.getValue())).append("</b>");
                i++;
            }

        } else {
            tooltipSB.append("[style.colourDisabled(No bonuses)]");
        }

        tooltipSB.append("</div>");

        // Picture:
        tooltipSB.append("<div class='item-image'>");
        tooltipSB.append("<div class='item-image-content'>");
        tooltipSB.append(tattoo.getSVGImage(
                equippedToCharacter == null
                        ? Main.game.getPlayer()
                        : equippedToCharacter));
        tooltipSB.append("</div>");
        tooltipSB.append("</div>");

        tooltipSB.append("</div>");

        tooltipSB.append("<div class='container-full-width' style='padding:8px; height:106px;'>");
        tooltipSB.append(tattoo.getType().getDescription());

        if (tattoo.getWriting() != null && !tattoo.getWriting().getText().isEmpty()) {
            tooltipSB.append("<br/>");
            if (tattoo.getWriting().getStyles().isEmpty()) {
                tooltipSB.append("Normal,");
            } else {
                int i = 0;
                for (TattooWritingStyle style : tattoo.getWriting().getStyles()) {
                    tooltipSB.append(i == 0 ? Util.capitaliseSentence(style.getName()) : ", " + style.getName());
                    i++;
                }
            }
            tooltipSB.append(" ").append(tattoo.getWriting().getColour().getName()).append(" writing forms part of the tattoo.");
        }

        if (tattoo.getCounter() != null && tattoo.getCounter().getType() != TattooCounterType.NONE) {
            tooltipSB.append("<br/>" + "An enchanted, ").append(tattoo.getCounter().getColour().getName()).append(" ").append(tattoo.getCounter().getType().getName()).append(" counter has been applied to the tattoo.");
        }

        tooltipSB.append("</div>");

        if (tattoo.getWriting() != null && !tattoo.getWriting().getText().isEmpty()) {
            tooltipSB.append("<div class='container-full-width' style='padding:4px; height:42px; text-align:center;'>");
            tooltipSB.append("The writing reads:<br/>");
            tooltipSB.append(tattoo.getFormattedWritingOutput()).append("</div>");
        } else {
            tooltipSB.append(
                    "<div class='container-full-width' style='padding:4px; height:28px; text-align:center;'>"
                            + "[style.colourDisabled(This tattoo doesn't have any writing.)]"
                            + "</div>");
        }

        if (tattoo.getCounter() != null && tattoo.getCounter().getType() != TattooCounterType.NONE) {
            tooltipSB.append("<div class='container-full-width' style='padding:4px; height:42px; text-align:center;'>" + "The '").append(tattoo.getCounter().getType().getName()).append("' counter displays:<br/>").append("<span style='color:").append(tattoo.getCounter().getColour().toWebHexString()).append(";'>").append(tattoo.getFormattedCounterOutput(
                    equippedToCharacter == null
                            ? Main.game.getPlayer()
                            : equippedToCharacter)).append("</span>").append("</div>");
        } else {
            tooltipSB.append(
                    "<div class='container-full-width' style='padding:8px; height:28px; text-align:center;'>"
                            + "[style.colourDisabled(This tattoo doesn't have a counter.)]"
                            + "</div>");
        }

        if (Main.game.isEnchantmentCapacityEnabled()) {
            int enchCapacityCost = tattoo.getEnchantmentCapacityCost();
            tooltipSB.append("<div class='container-full-width titular'>").append(enchCapacityCost == 0
                    ? Util.capitaliseSentence(Attribute.ENCHANTMENT_LIMIT.getName()) + " cost: [style.boldDisabled(" + enchCapacityCost + ")]"
                    : "[style.colourEnchantment(" + Util.capitaliseSentence(Attribute.ENCHANTMENT_LIMIT.getName()) + " cost)]: [style.boldBad(" + enchCapacityCost + ")]").append("</div>");
        }

        tooltipSB.append("</div>");

        if (owner != null) {
            SizedStack<Covering> lipsticks = owner.getLipstickMarkingsInSlot(invSlot);
            if (lipsticks != null) {
                lipstickYIncrease = 24 + (1 + lipsticks.size()) * LINE_HEIGHT;
                tooltipSB.append("<div class='container-full-width' style='text-align:center; padding:8px; height:").append(16 + (1 + lipsticks.size()) * LINE_HEIGHT).append("px;'>");
                tooltipSB.append(UtilText.parse(owner, "[npc.NamePos] ")).append(invSlot.getNameOfAssociatedPart(owner)).append(" ").append(invSlot.isPlural() ? "have" : "has").append(" been marked by:");
                for (int i = lipsticks.size() - 1; i >= 0; i--) {
                    tooltipSB.append("<br/>").append(Util.capitaliseSentence(lipsticks.get(i).getFullDescription(owner, true)));
                }
                tooltipSB.append("</div>");
            }
        }

        tooltipSB.append("</body>");

        if (tattoo.getDisplayName(false).length() > 40) {
            specialIncrease = 26;
        }
        Main.mainController.setTooltipSize(TOOLTIP_WIDTH, 404 + (Main.game.isEnchantmentCapacityEnabled() ? 32 : 0) + (yIncrease * LINE_HEIGHT) + lipstickYIncrease + specialIncrease);
        Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));
    }

    private int getBuybackPriceFor(AbstractCoreItem item) {
        for (ShopTransaction s : Main.game.getPlayer().getBuybackStack()) {
            if (s.getAbstractItemSold() == item) {
                return s.getPrice();
            }
        }
        throw new IllegalArgumentException("That's not a buyback item");
    }

    private String getTooltipText(GameCharacter character, String playerText, String NPCText) {
        if (character.isPlayer()) {
            return playerText;
        } else {
            return UtilText.parse(character, NPCText);
        }
    }

    private void setUnarmedWeaponSlotTooltip(InventorySlot slot, String title) {
        BodyPartClothingBlock block = slot.getBodyPartClothingBlock(equippedToCharacter);

        Main.mainController.setTooltipSize(TOOLTIP_WIDTH, 132 + (block != null ? 128 : 0));
        int baseDamage = equippedToCharacter.getBaseUnarmedDamage();
        int modifiedDamage = equippedToCharacter.getUnarmedDamage();

        StringBuilder sb = new StringBuilder();

        sb.append("<div class='title'>").append(title).append(" (Unarmed)").append("</div>").append("<div class='description' style='height:64px; text-align:center;'>").append(UtilText.parse(equippedToCharacter,
                "[npc.Name] [npc.has] a base unarmed damage value of " + baseDamage + ", which is modified from attributes to deal:"
                        + "<br/>[style.boldUnarmed(" + modifiedDamage + " Unarmed damage)]")).append("</div>");

        if (block != null) {
            sb.append(UtilText.parse(equippedToCharacter,
                    "<div class='title'>"
                            + "<span style='color:" + PresetColour.GENERIC_MINOR_BAD.toWebHexString() + ";'>Restricted!</span>"
                            + "</div>"
                            + "<div class='description' style='height:72px; text-align:center;'>"
                            + UtilText.parse(equippedToCharacter, block.getDescription())
                            + "</div>"));
        }

        Main.mainController.setTooltipContent(sb.toString());
    }
}
