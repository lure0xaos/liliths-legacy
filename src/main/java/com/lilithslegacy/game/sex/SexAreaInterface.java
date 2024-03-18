package com.lilithslegacy.game.sex;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.CoverableArea;
import com.lilithslegacy.game.inventory.InventorySlot;

/**
 * @author Innoxia
 * @version 0.4
 * @since 0.2.7
 */
public interface SexAreaInterface {

    boolean isOrifice();

    CoverableArea getRelatedCoverableArea(GameCharacter owner);

    InventorySlot getRelatedInventorySlot(GameCharacter owner);

    default String getName(GameCharacter owner) {
        return getName(owner, false);
    }

    /**
     * @param owner        The owner of this sex area.
     * @param standardName true if you want a non-random standard name of this area.
     * @return The name of this sex area.
     */
    String getName(GameCharacter owner, boolean standardName);

    boolean isFree(GameCharacter owner);

    default boolean isPenetration() {
        return !isOrifice();
    }

    default boolean isPlural() {
        return false;
    }

    String getSexDescription(boolean pastTense, GameCharacter performer, SexPace performerPace, GameCharacter target, SexPace targetPace, SexAreaInterface targetArea);

}
