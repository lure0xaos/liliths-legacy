package com.lilithslegacy.game.inventory.clothing;

import java.util.ArrayList;
import java.util.List;

import com.lilithslegacy.game.character.race.AbstractRace;
import com.lilithslegacy.game.inventory.InventorySlot;
import com.lilithslegacy.game.inventory.ItemTag;

/**
 * @author Innoxia
 * @version 0.3.9.1
 * @since 0.3.1
 */
public class BodyPartClothingBlock {

    private List<InventorySlot> blockedSlots;
    private AbstractRace race;
    private String description;
    private List<ItemTag> requiredTags;

    public BodyPartClothingBlock(List<InventorySlot> blockedSlots, AbstractRace race, String description, List<ItemTag> requiredTags) {
        this.blockedSlots = blockedSlots;
        this.race = race;
        this.description = description;

        if (requiredTags == null) {
            this.requiredTags = new ArrayList<>();
        } else {
            this.requiredTags = requiredTags;
        }
    }

    public List<InventorySlot> getBlockedSlots() {
        return blockedSlots;
    }

    public AbstractRace getRace() {
        return race;
    }

    public String getDescription() {
        return description;
    }

    public List<ItemTag> getRequiredTags() {
        return requiredTags;
    }


}
