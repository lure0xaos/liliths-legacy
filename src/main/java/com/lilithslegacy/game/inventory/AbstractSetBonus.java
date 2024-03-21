package com.lilithslegacy.game.inventory;

import com.lilithslegacy.controller.xmlParsing.Element;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.effects.AbstractStatusEffect;
import com.lilithslegacy.game.character.effects.StatusEffect;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.inventory.weapon.AbstractWeapon;
import com.lilithslegacy.main.Main;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Innoxia
 * @version 0.3.8.2
 * @since 0.3.8.2
 */
public abstract class AbstractSetBonus {

    private boolean mod;
    private String name;
    private int numberRequiredForCompleteSet;
    private List<InventorySlot> blockedSlotsCountingTowardsFullSet;
    private String statusEffectId;
    private AbstractStatusEffect associatedStatusEffect;

    public AbstractSetBonus(String name, AbstractStatusEffect associatedStatusEffect, int numberRequiredForCompleteSet, List<InventorySlot> blockedSlotsCountingTowardsFullSet) {
        this.name = name;
        this.numberRequiredForCompleteSet = numberRequiredForCompleteSet;

        if (blockedSlotsCountingTowardsFullSet == null) {
            this.blockedSlotsCountingTowardsFullSet = new ArrayList<>();
        } else {
            this.blockedSlotsCountingTowardsFullSet = blockedSlotsCountingTowardsFullSet;
        }

        this.associatedStatusEffect = associatedStatusEffect;
    }

    public AbstractSetBonus(Path XMLFile, String author, boolean mod) {
        if (Files.exists(XMLFile)) {
            try {
                Document result;
                try {
                    result = Main.getDocBuilder().parse(Files.newInputStream(XMLFile));
                } catch (IOException | SAXException e1) {
                    throw new RuntimeException(e1);
                }
                Document doc = result;

                // Cast magic:
                doc.getDocumentElement().normalize();

                Element coreElement = Element.getDocumentRootElement(XMLFile); // Loads the document and returns the root element - in setBonus files it's <setBonus>

                this.mod = mod;

                this.name = coreElement.getMandatoryFirstOf("name").getTextContent();

                this.numberRequiredForCompleteSet = Integer.valueOf(coreElement.getMandatoryFirstOf("numberRequiredForCompleteSet").getTextContent());

                this.statusEffectId = coreElement.getMandatoryFirstOf("statusEffect").getTextContent();

                this.blockedSlotsCountingTowardsFullSet = new ArrayList<>();
                if (coreElement.getOptionalFirstOf("blockedSlotsCountingTowardsFullSet").isPresent()) {
                    for (Element e : coreElement.getMandatoryFirstOf("blockedSlotsCountingTowardsFullSet").getAllOf("slot")) {
                        InventorySlot slot = InventorySlot.valueOf(e.getTextContent());
                        this.blockedSlotsCountingTowardsFullSet.add(slot);
                    }
                    ;
                }

            } catch (Exception ex) {
                System.getLogger("").log(System.Logger.Level.ERROR, ex);
                System.getLogger("").log(System.Logger.Level.ERROR, "SetBonus was unable to be loaded from file! (" + XMLFile.getFileName().toString() + ")\n" + ex);
            }
        }
    }

    public boolean isMod() {
        return mod;
    }

    public boolean isCharacterWearingCompleteSet(GameCharacter target) {
        int setCount = 0;

        for (InventorySlot slot : this.getBlockedSlotsCountingTowardsFullSet()) {
            if (slot.getBodyPartClothingBlock(target) != null) {
                setCount++;
            }
        }

        boolean atLeastOneClothingFound = false;
        for (AbstractClothing c : target.getClothingCurrentlyEquipped()) {
            if (c.getClothingType().getClothingSet() == this) {
                setCount++;
                atLeastOneClothingFound = true;
            }
        }

        int weaponSetCount = 0;
        for (AbstractWeapon weapon : target.getMainWeaponArray()) {
            if (weapon != null && weapon.getWeaponType().getClothingSet() == this) {
                weaponSetCount++;
                atLeastOneClothingFound = true;
            }
        }
        for (AbstractWeapon weapon : target.getOffhandWeaponArray()) {
            if (weapon != null && weapon.getWeaponType().getClothingSet() == this) {
                weaponSetCount++;
                atLeastOneClothingFound = true;
            }
        }

        setCount += Math.min(2, weaponSetCount);

        return atLeastOneClothingFound && setCount >= this.getNumberRequiredForCompleteSet();
    }

    public String getName() {
        return name;
    }

    public int getNumberRequiredForCompleteSet() {
        return numberRequiredForCompleteSet;
    }

    public AbstractStatusEffect getAssociatedStatusEffect() {
        if (associatedStatusEffect == null) {
            associatedStatusEffect = StatusEffect.getStatusEffectFromId(statusEffectId);
        }
        return associatedStatusEffect;
    }

    public List<InventorySlot> getBlockedSlotsCountingTowardsFullSet() {
        return blockedSlotsCountingTowardsFullSet;
    }
}
