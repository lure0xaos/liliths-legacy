package com.lilithslegacy.controller.eventListeners;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;

import com.lilithslegacy.game.dialogue.responses.Response;
import com.lilithslegacy.game.dialogue.utils.EnchantmentDialogue;
import com.lilithslegacy.game.inventory.AbstractCoreItem;
import com.lilithslegacy.game.inventory.enchanting.TFModifier;
import com.lilithslegacy.game.inventory.enchanting.TFPotency;
import com.lilithslegacy.main.Main;

/**
 * @author Innoxia
 * @version 0.3.5.1
 * @since 0.1.7
 */
public class EnchantmentEventListener implements EventListener {
    private AbstractCoreItem itemToEnchant;
    private TFModifier primaryModifier, secondaryModifier;
    private TFPotency potency;
    private boolean effect;
    private int effectIndex;
    private int limit;

    @Override
    public void handleEvent(Event event) {

        if (itemToEnchant != null) {
            if (itemToEnchant.getEnchantmentEffect() != null) {
                EnchantmentDialogue.resetEnchantmentVariables();
                EnchantmentDialogue.setIngredient(itemToEnchant);
            }

        } else if (primaryModifier != null) {
            EnchantmentDialogue.setPrimaryMod(primaryModifier);

        } else if (secondaryModifier != null) {
            EnchantmentDialogue.setSecondaryMod(secondaryModifier);

        } else if (potency != null) {
            EnchantmentDialogue.setPotency(potency);

        } else if (effect) {
            EnchantmentDialogue.removeEffect(effectIndex);

        } else if (limit != EnchantmentDialogue.getLimit()) {
            EnchantmentDialogue.setLimit(limit);
        }

        if (!EnchantmentDialogue.getIngredient().getEnchantmentEffect().getPrimaryModifiers().contains(EnchantmentDialogue.getPrimaryMod())) {
            EnchantmentDialogue.setPrimaryMod(EnchantmentDialogue.getIngredient().getEnchantmentEffect().getPrimaryModifiers().get(0));
        }
        if (!EnchantmentDialogue.getIngredient().getEnchantmentEffect().getSecondaryModifiers(EnchantmentDialogue.getIngredient(), EnchantmentDialogue.getPrimaryMod()).contains(EnchantmentDialogue.getSecondaryMod())) {
            EnchantmentDialogue.setSecondaryMod(EnchantmentDialogue.getIngredient().getEnchantmentEffect().getSecondaryModifiers(EnchantmentDialogue.getIngredient(), EnchantmentDialogue.getPrimaryMod()).get(0));
        }
        if (!EnchantmentDialogue.getIngredient().getEnchantmentEffect().getPotencyModifiers(EnchantmentDialogue.getPrimaryMod(), EnchantmentDialogue.getSecondaryMod()).contains(EnchantmentDialogue.getPotency())) {
            EnchantmentDialogue.setPotency(TFPotency.MINOR_BOOST);
        }
        if (EnchantmentDialogue.getLimit() > EnchantmentDialogue.getIngredient().getEnchantmentEffect().getLimits(EnchantmentDialogue.getPrimaryMod(), EnchantmentDialogue.getSecondaryMod())) {
            EnchantmentDialogue.setLimit(EnchantmentDialogue.getIngredient().getEnchantmentEffect().getLimits(EnchantmentDialogue.getPrimaryMod(), EnchantmentDialogue.getSecondaryMod()));
        }

        Main.game.setContent(new Response("Enchanting", "Start enchanting.", EnchantmentDialogue.ENCHANTMENT_MENU));
    }

    public EnchantmentEventListener setItemToEnchant(AbstractCoreItem itemToEnchant) {
        resetVariables();
        this.itemToEnchant = itemToEnchant;

        return this;
    }

    public EnchantmentEventListener setPrimaryModifier(TFModifier primaryModifier) {
        resetVariables();
        this.primaryModifier = primaryModifier;

        return this;
    }

    public EnchantmentEventListener setSecondaryModifier(TFModifier secondaryModifier) {
        resetVariables();
        this.secondaryModifier = secondaryModifier;

        return this;
    }

    public EnchantmentEventListener setPotency(TFPotency potency) {
        resetVariables();
        this.potency = potency;

        return this;
    }

    public EnchantmentEventListener removeEffect(int effectIndex) {
        resetVariables();
        effect = true;
        this.effectIndex = effectIndex;

        return this;
    }

    public EnchantmentEventListener setLimit(int limit) {
        resetVariables();
        this.limit = limit;

        return this;
    }

    private void resetVariables() {
        effect = false;
        effectIndex = 0;
        itemToEnchant = null;
        primaryModifier = null;
        secondaryModifier = null;
        potency = null;
        limit = 0;
    }
}
