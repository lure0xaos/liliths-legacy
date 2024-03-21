package com.lilithslegacy.game.inventory.item;

import java.util.List;

import com.lilithslegacy.game.character.body.Body;
import com.lilithslegacy.game.inventory.enchanting.PossibleItemEffect;

/**
 * Container class for TF potions
 *
 * @author Stadler76
 * @version 0.3.6.4
 * @since 0.3.6.4
 */
public class TransformativePotion extends AbstractPotion {
    private final Body body;

    public TransformativePotion(AbstractItemType itemType, List<PossibleItemEffect> effects, Body body) {
        super(itemType, effects);
        this.body = body;
    }

    public TransformativePotion(AbstractItemType itemType, List<PossibleItemEffect> effects) {
        this(itemType, effects, null);
    }

    public Body getBody() {
        return body;
    }
}
