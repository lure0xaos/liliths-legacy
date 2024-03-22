package com.lilithslegacy.controller;

import org.w3c.dom.events.EventTarget;

import com.lilithslegacy.controller.eventListeners.EnchantmentEventListener;
import com.lilithslegacy.controller.eventListeners.tooltips.TooltipInformationEventListener;
import com.lilithslegacy.controller.eventListeners.tooltips.TooltipInventoryEventListener;
import com.lilithslegacy.game.character.markings.Tattoo;
import com.lilithslegacy.game.dialogue.responses.Response;
import com.lilithslegacy.game.dialogue.utils.EnchantmentDialogue;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.inventory.enchanting.AbstractItemEffectType;
import com.lilithslegacy.game.inventory.enchanting.EnchantingUtils;
import com.lilithslegacy.game.inventory.enchanting.ItemEffect;
import com.lilithslegacy.game.inventory.enchanting.TFModifier;
import com.lilithslegacy.game.inventory.enchanting.TFPotency;
import com.lilithslegacy.game.inventory.item.AbstractItem;
import com.lilithslegacy.game.inventory.weapon.AbstractWeapon;
import com.lilithslegacy.main.Main;

/**
 * @author Maxis010, Innoxia
 * @version 0.4.6.4
 * @since 0.4.6.4
 */
public class EnchantmentController {
    static void initEnchantmentMenuListeners() {
        // Tooltips:
        String id = "MOD_PRIMARY_ENCHANTING";
        if (MainController.document.getElementById(id) != null) {
            MainController.addTooltipListeners(id,
                    new TooltipInventoryEventListener().setTFModifier(EnchantmentDialogue.getPrimaryMod()),
                    new EnchantmentEventListener().setPrimaryModifier(TFModifier.NONE), false);
        }
        id = "MOD_SECONDARY_ENCHANTING";
        if (MainController.document.getElementById(id) != null) {
            MainController.addTooltipListeners(id,
                    new TooltipInventoryEventListener().setTFModifier(EnchantmentDialogue.getSecondaryMod()),
                    new EnchantmentEventListener().setSecondaryModifier(TFModifier.NONE), false);
        }

        for (TFPotency potency : TFPotency.values()) {
            id = "POTENCY_" + potency;
            if (MainController.document.getElementById(id) != null) {
                MainController.addEventListener(MainController.document, id, "click", new EnchantmentEventListener().setPotency(potency), false);
            }
        }
        for (int effectCount = 0; effectCount < EnchantmentDialogue.getEffects().size(); effectCount++) {
            id = "DELETE_EFFECT_" + effectCount;
            if (MainController.document.getElementById(id) != null) {
                MainController.addTooltipListeners(id,
                        new TooltipInformationEventListener().setInformation("Delete Effect", ""),
                        new EnchantmentEventListener().removeEffect(effectCount), false);
            }
        }

        AbstractItemEffectType effect = EnchantmentDialogue.getIngredient().getEnchantmentEffect();
        int maxLimit = effect.getMaximumLimit();
        int currentLimit = EnchantmentDialogue.getLimit();

        if (currentLimit > 0) {
            id = "LIMIT_MINIMUM";
            if (MainController.document.getElementById(id) != null) {
                MainController.addEventListener(MainController.document, id, "click", new EnchantmentEventListener().setLimit(0), false);
            }

            id = "LIMIT_DECREASE_LARGE";
            if (MainController.document.getElementById(id) != null) {
                MainController.addEventListener(MainController.document, id, "click", new EnchantmentEventListener().setLimit(Math.max(0, EnchantmentDialogue.getLimit() - effect.getLargeLimitChange())), false);
            }

            id = "LIMIT_DECREASE";
            if (MainController.document.getElementById(id) != null) {
                MainController.addEventListener(MainController.document, id, "click", new EnchantmentEventListener().setLimit(EnchantmentDialogue.getLimit() - effect.getSmallLimitChange()), false);
            }
        }

        if (currentLimit < maxLimit) {
            id = "LIMIT_INCREASE";
            if (MainController.document.getElementById(id) != null) {
                MainController.addEventListener(MainController.document, id, "click", new EnchantmentEventListener().setLimit(EnchantmentDialogue.getLimit() + effect.getSmallLimitChange()), false);
            }

            id = "LIMIT_INCREASE_LARGE";
            if (MainController.document.getElementById(id) != null) {
                MainController.addEventListener(MainController.document, id, "click", new EnchantmentEventListener().setLimit(Math.min(maxLimit, EnchantmentDialogue.getLimit() + effect.getLargeLimitChange())), false);
            }

            id = "LIMIT_MAXIMUM";
            if (MainController.document.getElementById(id) != null) {
                MainController.addEventListener(MainController.document, id, "click", new EnchantmentEventListener().setLimit(maxLimit), false);
            }
        }

        // Ingredient icon:
        id = "INGREDIENT_ENCHANTING";
        if (MainController.document.getElementById(id) != null) {
            TooltipInventoryEventListener el = null;
            if (EnchantmentDialogue.getIngredient() instanceof AbstractItem) {
                el = new TooltipInventoryEventListener().setItem((AbstractItem) EnchantmentDialogue.getIngredient(), Main.game.getPlayer(), null);
            } else if (EnchantmentDialogue.getIngredient() instanceof AbstractClothing) {
                el = new TooltipInventoryEventListener().setClothing((AbstractClothing) EnchantmentDialogue.getIngredient(), Main.game.getPlayer(), null);
            } else if (EnchantmentDialogue.getIngredient() instanceof AbstractWeapon) {
                el = new TooltipInventoryEventListener().setWeapon((AbstractWeapon) EnchantmentDialogue.getIngredient(), Main.game.getPlayer(), false);
            } else if (EnchantmentDialogue.getIngredient() instanceof Tattoo) {
                el = new TooltipInventoryEventListener().setTattoo(EnchantmentDialogue.getTattooSlot(), (Tattoo) EnchantmentDialogue.getIngredient(), EnchantmentDialogue.getTattooBearer(), EnchantmentDialogue.getTattooBearer());
            }
            MainController.addTooltipListeners(id, el);
        }

        // Output icon:
        id = "OUTPUT_ENCHANTING";
        if (MainController.document.getElementById(id) != null) {
            TooltipInventoryEventListener el = null;
            if (EnchantmentDialogue.getIngredient() instanceof AbstractItem) {
                el = new TooltipInventoryEventListener().setItem(EnchantingUtils.craftItem(EnchantmentDialogue.getIngredient(), EnchantmentDialogue.getEffects()), Main.game.getPlayer(), null);
            } else if (EnchantmentDialogue.getIngredient() instanceof AbstractClothing) {
                el = new TooltipInventoryEventListener().setClothing(EnchantingUtils.craftClothing(EnchantmentDialogue.getIngredient(), EnchantmentDialogue.getEffects()), Main.game.getPlayer(), null);
            } else if (EnchantmentDialogue.getIngredient() instanceof AbstractWeapon) {
                el = new TooltipInventoryEventListener().setWeapon(EnchantingUtils.craftWeapon(EnchantmentDialogue.getIngredient(), EnchantmentDialogue.getEffects()), Main.game.getPlayer(), false);
            } else if (EnchantmentDialogue.getIngredient() instanceof Tattoo) {
                el = new TooltipInventoryEventListener().setTattoo(EnchantmentDialogue.getTattooSlot(), EnchantingUtils.craftTattoo(EnchantmentDialogue.getIngredient(), EnchantmentDialogue.getEffects()), EnchantmentDialogue.getTattooBearer(), EnchantmentDialogue.getTattooBearer());
            }
            MainController.addTooltipListeners(id, el);
        }

        // Adding an effect:
        id = "ENCHANT_ADD_BUTTON";
        if (MainController.document.getElementById(id) != null) {
            ((EventTarget) MainController.document.getElementById(id)).addEventListener("click", e -> {
                if (EnchantmentDialogue.getIngredient().getEnchantmentEffect().getEffectsDescription(
                        EnchantmentDialogue.getPrimaryMod(), EnchantmentDialogue.getSecondaryMod(), EnchantmentDialogue.getPotency(), EnchantmentDialogue.getLimit(), Main.game.getPlayer(), Main.game.getPlayer()) != null) {
                    Main.game.setContent(new Response("Add", "Add the effect.", EnchantmentDialogue.ENCHANTMENT_MENU) {
                        @Override
                        public void effects() {
                            EnchantmentDialogue.addEffect(new ItemEffect(
                                    EnchantmentDialogue.getIngredient().getEnchantmentEffect(), EnchantmentDialogue.getPrimaryMod(), EnchantmentDialogue.getSecondaryMod(), EnchantmentDialogue.getPotency(), EnchantmentDialogue.getLimit()));
                        }
                    });
                }
            }, false);
            MainController.addTooltipListeners(id, new TooltipInformationEventListener().setInformation("Add Effect", ""));
        }

        id = "ENCHANT_ADD_BUTTON_DISABLED";
        if (MainController.document.getElementById(id) != null) {
            String blockedString = EnchantmentDialogue.getEnchantmentEffectBlockedReason(EnchantmentDialogue.getCurrentEffect());
            MainController.addTooltipListeners(id, new TooltipInformationEventListener().setInformation("Add Effect",
                    EnchantmentDialogue.getEffects().size() >= EnchantmentDialogue.getIngredient().getEnchantmentLimit()
                            ? "You have already added the maximum number of effects for this item!"
                            : (blockedString != null
                            ? blockedString
                            : "")));
        }

        // Choosing a primary modifier:
        for (TFModifier tfMod : EnchantmentDialogue.getIngredient().getEnchantmentEffect().getPrimaryModifiers()) {
            id = "MOD_PRIMARY_" + tfMod.hashCode();
            if (MainController.document.getElementById(id) != null) {
                MainController.addTooltipListeners(id,
                        new TooltipInventoryEventListener().setTFModifier(tfMod),
                        new EnchantmentEventListener().setPrimaryModifier(tfMod), false);
            }
        }
        // Choosing a secondary modifier:
        for (TFModifier tfMod : EnchantmentDialogue.getIngredient().getEnchantmentEffect().getSecondaryModifiers(EnchantmentDialogue.getIngredient(), EnchantmentDialogue.getPrimaryMod())) {
            id = "MOD_SECONDARY_" + tfMod.hashCode();
            if (MainController.document.getElementById(id) != null) {
                MainController.addTooltipListeners(id,
                        new TooltipInventoryEventListener().setTFModifier(tfMod),
                        new EnchantmentEventListener().setSecondaryModifier(tfMod), false);
            }
        }
    }
}
