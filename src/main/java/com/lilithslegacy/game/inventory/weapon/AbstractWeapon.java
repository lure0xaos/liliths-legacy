package com.lilithslegacy.game.inventory.weapon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.lilithslegacy.controller.xmlParsing.XMLUtil;
import com.lilithslegacy.game.Game;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.attributes.AbstractAttribute;
import com.lilithslegacy.game.character.attributes.Attribute;
import com.lilithslegacy.game.combat.Attack;
import com.lilithslegacy.game.combat.DamageType;
import com.lilithslegacy.game.combat.moves.AbstractCombatMove;
import com.lilithslegacy.game.combat.moves.CombatMove;
import com.lilithslegacy.game.combat.spells.Spell;
import com.lilithslegacy.game.combat.spells.SpellSchool;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.AbstractCoreItem;
import com.lilithslegacy.game.inventory.AbstractCoreType;
import com.lilithslegacy.game.inventory.InventorySlot;
import com.lilithslegacy.game.inventory.ItemTag;
import com.lilithslegacy.game.inventory.Rarity;
import com.lilithslegacy.game.inventory.clothing.BodyPartClothingBlock;
import com.lilithslegacy.game.inventory.enchanting.AbstractItemEffectType;
import com.lilithslegacy.game.inventory.enchanting.ItemEffect;
import com.lilithslegacy.game.inventory.enchanting.ItemEffectType;
import com.lilithslegacy.game.inventory.enchanting.TFModifier;
import com.lilithslegacy.game.inventory.enchanting.TFPotency;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Util.Value;
import com.lilithslegacy.utils.XMLSaving;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.3.7.9
 * @since 0.1.0
 */
public abstract class AbstractWeapon extends AbstractCoreItem implements XMLSaving {

    protected List<ItemEffect> effects;
    private final AbstractWeaponType weaponType;
    private DamageType damageType;

    private AbstractAttribute coreEnchantment;

    private List<Spell> spells;
    private List<AbstractCombatMove> combatMoves;


    public AbstractWeapon(AbstractWeaponType weaponType, DamageType damageType, List<Colour> colours) {
        super(weaponType.getName(), weaponType.getNamePlural(), weaponType.getPathName(), damageType.getMultiplierAttribute().getColour(), weaponType.getRarity(), null);

        this.weaponType = weaponType;
        this.damageType = damageType;

        this.colours = new ArrayList<>(colours);
        if (colours.size() < weaponType.getColourReplacements(false).size()) {
            for (int i = colours.size(); i < weaponType.getColourReplacements(false).size(); i++) {
                this.setColour(i, weaponType.getColourReplacements(false).get(i).getFirstOfDefaultColours());
            }
        }

        coreEnchantment = null;

        spells = new ArrayList<>(weaponType.getSpells(damageType));
        combatMoves = new ArrayList<>(weaponType.getCombatMoves(damageType));

        this.effects = new ArrayList<>();
        if (weaponType.getEffects() != null) {
            for (ItemEffect effect : weaponType.getEffects()) {
                if (effect.getSecondaryModifier() == TFModifier.DAMAGE_WEAPON) {
                    switch (damageType) {
                        case FIRE:
                            this.effects.add(new ItemEffect(ItemEffectType.WEAPON, TFModifier.CLOTHING_ATTRIBUTE, TFModifier.DAMAGE_FIRE, TFPotency.MAJOR_BOOST, 0));
                            break;
                        case ICE:
                            this.effects.add(new ItemEffect(ItemEffectType.WEAPON, TFModifier.CLOTHING_ATTRIBUTE, TFModifier.DAMAGE_ICE, TFPotency.MAJOR_BOOST, 0));
                            break;
                        case LUST:
                            this.effects.add(new ItemEffect(ItemEffectType.WEAPON, TFModifier.CLOTHING_ATTRIBUTE, TFModifier.DAMAGE_LUST, TFPotency.MAJOR_BOOST, 0));
                            break;
                        case MISC:
                        case UNARMED:
                        case HEALTH:
                            break;
                        case PHYSICAL:
                            this.effects.add(new ItemEffect(ItemEffectType.WEAPON, TFModifier.CLOTHING_ATTRIBUTE, TFModifier.DAMAGE_PHYSICAL, TFPotency.MAJOR_BOOST, 0));
                            break;
                        case POISON:
                            this.effects.add(new ItemEffect(ItemEffectType.WEAPON, TFModifier.CLOTHING_ATTRIBUTE, TFModifier.DAMAGE_POISON, TFPotency.MAJOR_BOOST, 0));
                            break;
                    }

                } else if (effect.getSecondaryModifier() == TFModifier.RESISTANCE_WEAPON) {
                    switch (damageType) {
                        case FIRE:
                            this.effects.add(new ItemEffect(ItemEffectType.WEAPON, TFModifier.CLOTHING_ATTRIBUTE, TFModifier.RESISTANCE_FIRE, TFPotency.MAJOR_BOOST, 0));
                            break;
                        case ICE:
                            this.effects.add(new ItemEffect(ItemEffectType.WEAPON, TFModifier.CLOTHING_ATTRIBUTE, TFModifier.RESISTANCE_ICE, TFPotency.MAJOR_BOOST, 0));
                            break;
                        case LUST:
                            this.effects.add(new ItemEffect(ItemEffectType.WEAPON, TFModifier.CLOTHING_ATTRIBUTE, TFModifier.RESISTANCE_LUST, TFPotency.MAJOR_BOOST, 0));
                            break;
                        case MISC:
                        case UNARMED:
                        case HEALTH:
                            break;
                        case PHYSICAL:
                            this.effects.add(new ItemEffect(ItemEffectType.WEAPON, TFModifier.CLOTHING_ATTRIBUTE, TFModifier.RESISTANCE_PHYSICAL, TFPotency.MAJOR_BOOST, 0));
                            break;
                        case POISON:
                            this.effects.add(new ItemEffect(ItemEffectType.WEAPON, TFModifier.CLOTHING_ATTRIBUTE, TFModifier.RESISTANCE_POISON, TFPotency.MAJOR_BOOST, 0));
                            break;
                    }

                } else {
                    this.effects.add(effect);
                }
            }
        }

        int highestEnchantment = 0;
        for (AbstractAttribute a : new HashSet<>(getAttributeModifiers().keySet())) {
            if (getAttributeModifiers().get(a) > highestEnchantment) {
                coreEnchantment = a;
                highestEnchantment = getAttributeModifiers().get(a);
            }
        }
    }

    public AbstractWeapon(AbstractWeapon weapon) {
        this(weapon.getWeaponType(), weapon.getDamageType(), weapon.getColours());

        if (!weapon.getWeaponType().isSpellRegenOnDamageTypeChange()) {
            this.spells = new ArrayList<>(weapon.getSpells());
        }

        if (!weapon.getWeaponType().isCombatMoveRegenOnDamageTypeChange()) {
            this.combatMoves = new ArrayList<>(weapon.getCombatMoves());
        }

        this.setEffects(new ArrayList<>(weapon.getEffects()));

        int highestEnchantment = 0;
        for (AbstractAttribute a : new HashSet<>(getAttributeModifiers().keySet())) {
            if (getAttributeModifiers().get(a) > highestEnchantment) {
                coreEnchantment = a;
                highestEnchantment = getAttributeModifiers().get(a);
            }
        }

        if (!weapon.name.isEmpty()) {
            this.setName(weapon.name);
        }
    }

    public static AbstractWeapon loadFromXML(Element parentElement, Document doc) {
        AbstractWeapon weapon = null;

        try {
            String id = parentElement.getAttribute("id");
            weapon = Main.game.getItemGen().generateWeapon(WeaponType.getWeaponTypeFromId(id), DamageType.valueOf(parentElement.getAttribute("damageType")));
        } catch (Exception ex) {
            System.getLogger("").log(System.Logger.Level.ERROR, ex.getMessage(), ex);
            System.getLogger("").log(System.Logger.Level.ERROR, "Warning: An instance of AbstractWeapon was unable to be imported. (" + parentElement.getAttribute("id") + ")");
            return null;
        }

        if (weapon == null) {
            System.getLogger("").log(System.Logger.Level.ERROR, "Warning: An instance of AbstractWeapon was unable to be imported. (" + parentElement.getAttribute("id") + ")");
            return null;
        }

        if (!parentElement.getAttribute("name").isEmpty()) {
            weapon.setName(parentElement.getAttribute("name"));
        }

        if (!parentElement.getAttribute("coreEnchantment").equals("null")) {
            try {
                weapon.coreEnchantment = Attribute.getAttributeFromId(parentElement.getAttribute("coreEnchantment"));
            } catch (Exception ex) {
            }
        }

        // Try to load colour:
        if (!Main.isVersionOlderThan(Game.loadingVersion, "0.3.7.8")) {
            Element colourElement = (Element) parentElement.getElementsByTagName("colours").item(0);
            if (colourElement != null) {
                NodeList nodes = colourElement.getElementsByTagName("colour");
                for (int i = 0; i < nodes.getLength(); i++) {
                    Element cElement = (Element) nodes.item(i);
                    weapon.setColour(Integer.valueOf(cElement.getAttribute("i")), PresetColour.getColourFromId(cElement.getTextContent()));
                }
            }

        } else {
            try {
                if (!Main.isVersionOlderThan(Game.loadingVersion, "0.3.7.4") || !weapon.getWeaponType().equals(WeaponType.getWeaponTypeFromId("innoxia_bow_shortbow"))) {
                    if (!parentElement.getAttribute("colourPrimary").isEmpty()) {
                        weapon.setColour(0, PresetColour.getColourFromId(parentElement.getAttribute("colourPrimary")));
                    }
                    if (!parentElement.getAttribute("colourSecondary").isEmpty()) {
                        weapon.setColour(1, PresetColour.getColourFromId(parentElement.getAttribute("colourSecondary")));
                    }
                    if (!parentElement.getAttribute("colourTertiary").isEmpty()) {
                        weapon.setColour(2, PresetColour.getColourFromId(parentElement.getAttribute("colourTertiary")));
                    }
                }
            } catch (Exception ex) {
            }
        }

        if (!Main.isVersionOlderThan(Game.loadingVersion, "0.2.10.5")) {
            try {
                weapon.getEffects().clear();

                Element element = (Element) parentElement.getElementsByTagName("effects").item(0);
                NodeList effectElements = element.getElementsByTagName("effect");
                for (int i = 0; i < effectElements.getLength(); i++) {
                    Element e = ((Element) effectElements.item(i));
                    ItemEffect ie = ItemEffect.loadFromXML(e, doc);
                    if (ie != null) {
                        weapon.addEffect(ie);
                    }
                }
            } catch (Exception ex) {
            }
        }

        weapon.spells = new ArrayList<>();
        Element element = (Element) parentElement.getElementsByTagName("spells").item(0);
        NodeList spellElements = element.getElementsByTagName("spell");
        for (int i = 0; i < spellElements.getLength(); i++) {
            Element e = ((Element) spellElements.item(i));
            try {
                String weaponId = e.getAttribute("value");
                if (weaponId.equals("DARK_SIREN_BANEFUL_FISSURE")) {
                    weaponId = "DARK_SIREN_SIRENS_CALL";
                }
                weapon.spells.add(Spell.valueOf(weaponId));
            } catch (Exception ex) {
            }
        }

        weapon.combatMoves = new ArrayList<>();
        element = (Element) parentElement.getElementsByTagName("combatMoves").item(0);
        if (element != null) {
            NodeList combatMoveElements = element.getElementsByTagName("move");
            for (int i = 0; i < combatMoveElements.getLength(); i++) {
                Element e = ((Element) combatMoveElements.item(i));
                try {
                    String identifier = e.getAttribute("value");
                    weapon.combatMoves.add(CombatMove.getCombatMoveFromId(identifier));
                } catch (Exception ex) {
                }
            }
        }

        return weapon;
    }

    public String getId() {
        StringBuilder sb = new StringBuilder();

        sb.append(WeaponType.getIdFromWeaponType(this.getWeaponType()));
        for (Colour colour : this.getColours()) {
            sb.append(colour.getId());
        }

        sb.append(this.getDamageType().toString());
        sb.append(this.getCoreEnchantment() == null ? "ne" : this.getCoreEnchantment().toString());

        for (Spell s : this.getSpells()) {
            sb.append(s.toString());
        }

        for (ItemEffect ie : this.getEffects()) {
            sb.append(ie.getId());
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (super.equals(o)) {
            if (o instanceof AbstractWeapon) {
                return ((AbstractWeapon) o).getWeaponType().equals(getWeaponType())
                        && ((AbstractWeapon) o).getColours().equals(getColours())
                        && ((AbstractWeapon) o).getDamageType() == this.getDamageType()
                        && ((AbstractWeapon) o).getCoreEnchantment() == this.getCoreEnchantment()
                        && ((AbstractWeapon) o).getSpells().equals(this.getSpells())
                        && ((AbstractWeapon) o).getEffects().equals(this.getEffects());
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getWeaponType().hashCode();
        result = 31 * result + getDamageType().hashCode();
        result = 31 * result + getColours().hashCode();
        if (getCoreEnchantment() != null) {
            result = 31 * result + getCoreEnchantment().hashCode();
        }
        result = 31 * result + getSpells().hashCode();
        result = 31 * result + this.getEffects().hashCode();
        return result;
    }

    public Element saveAsXML(Element parentElement, Document doc) {
        Element element = doc.createElement("weapon");
        parentElement.appendChild(element);

        XMLUtil.addAttribute(doc, element, "id", this.getWeaponType().getId());
        XMLUtil.addAttribute(doc, element, "name", name);
        XMLUtil.addAttribute(doc, element, "damageType", this.getDamageType().toString());
        XMLUtil.addAttribute(doc, element, "coreEnchantment", (this.getCoreEnchantment() == null ? "null" : Attribute.getIdFromAttribute(this.getCoreEnchantment())));

        if (!this.getColours().isEmpty()) {
            Element innerElement = doc.createElement("colours");
            element.appendChild(innerElement);

            for (int i = 0; i < this.getColours().size(); i++) {
                Element colourElement = doc.createElement("colour");
                innerElement.appendChild(colourElement);
                colourElement.setAttribute("i", String.valueOf(i));
                if (this.getColour(i) == null) {
                    colourElement.setTextContent(PresetColour.CLOTHING_BLACK.getId());
                } else {
                    colourElement.setTextContent(this.getColour(i).getId());
                }
            }
        }

        Element innerElement = doc.createElement("effects");
        element.appendChild(innerElement);
        for (ItemEffect ie : this.getEffects()) {
            ie.saveAsXML(innerElement, doc);
        }

        innerElement = doc.createElement("spells");
        element.appendChild(innerElement);
        for (Spell s : this.getSpells()) {
            Element spell = doc.createElement("spell");
            innerElement.appendChild(spell);
            XMLUtil.addAttribute(doc, spell, "value", s.toString());
        }

        innerElement = doc.createElement("combatMoves");
        element.appendChild(innerElement);
        for (AbstractCombatMove cm : this.getCombatMoves()) {
            Element move = doc.createElement("move");
            innerElement.appendChild(move);
            XMLUtil.addAttribute(doc, move, "value", cm.getIdentifier());
        }


        return element;
    }

    public abstract String onEquip(GameCharacter character);

    public abstract String onUnequip(GameCharacter character);

    @Override
    public String getDescription() {
        return getDescription(null);
    }

    public String getDescription(GameCharacter character) {
        StringBuilder descriptionSB = new StringBuilder();

        if (character == null || !character.hasWeaponEquipped(this)) {
            character = Main.game.getPlayer();
        }

        int essenceCost = this.getWeaponType().getArcaneCost();
        String damageName = "<b style='color:" + damageType.getMultiplierAttribute().getColour().toWebHexString() + ";'>Damage</b>";

        descriptionSB.append("<p><b>");
        descriptionSB.append("<span style='color:").append(this.getRarity().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(this.getRarity().getName())).append("</span>").append(" | ").append(this.getWeaponType().isUsingUnarmedCalculation()
                ? "[style.colourUnarmed(Unarmed)]"
                : (this.getWeaponType().isMelee()
                ? "[style.colourMelee(Melee)]"
                : "[style.colourRanged(Ranged)]")).append("<br/>");
        descriptionSB.append((this.getWeaponType().isTwoHanded() ? "Two-handed" : "One-handed"));
        if (this.getWeaponType().isOneShot()) {
            descriptionSB.append(" - [style.colourYellow(One-shot)]");
        }
        descriptionSB.append("<br/>");
        if (essenceCost > 0) {
            descriptionSB.append("Costs [style.colourArcane(").append(essenceCost).append(" arcane essence").append(essenceCost == 1 ? "" : "s").append(")] ").append(this.getWeaponType().isMelee() ? "per attack" : "to fire").append("<br/>");
        }
        if (this.getWeaponType().isUsingUnarmedCalculation()) {
            descriptionSB.append("Includes [style.boldUnarmed(").append(character.getUnarmedDamage()).append(" unarmed damage)]<br/>");
        }
        descriptionSB.append(Attack.getMinimumDamage(character, null, Attack.MAIN, this)).append("-").append(Attack.getMaximumDamage(character, null, Attack.MAIN, this)).append(" ").append(damageName).append("<br/>");
        int targetNumber = 2;
        for (Value<Integer, Integer> aoe : this.getWeaponType().getAoeDamage()) {
            int aoeChance = aoe.getKey();
            String position = Util.intToPosition(targetNumber);
            descriptionSB.append("[style.boldAqua(AoE)]: " + "<b style='color:").append((aoeChance <= 25 ? PresetColour.GENERIC_BAD : (aoeChance <= 50 ? PresetColour.GENERIC_MINOR_BAD : (aoeChance <= 75 ? PresetColour.GENERIC_MINOR_GOOD : PresetColour.GENERIC_GOOD))).toWebHexString()).append(";'>").append(aoeChance).append("%</b>").append(" chance to deal ").append("<b>").append(Attack.getMinimumDamage(character, null, Attack.MAIN, this, aoe.getValue())).append(" - ").append(Attack.getMaximumDamage(character, null, Attack.MAIN, this, aoe.getValue())).append("</b> ").append(damageName).append(" to ").append(UtilText.generateSingularDeterminer(position)).append(" ").append(position).append(" enemy!<br/>");
            targetNumber++;
        }
        if (this.getWeaponType().isOneShot()) {
            int chanceToRecoverTurn = (int) this.getWeaponType().getOneShotChanceToRecoverAfterTurn();
            int chanceToRecoverCombat = (int) this.getWeaponType().getOneShotChanceToRecoverAfterCombat();

            descriptionSB.append("<span style='color:").append((chanceToRecoverTurn <= 25 ? PresetColour.GENERIC_BAD : (chanceToRecoverTurn <= 50 ? PresetColour.GENERIC_MINOR_BAD : (chanceToRecoverTurn <= 75 ? PresetColour.GENERIC_MINOR_GOOD : PresetColour.GENERIC_GOOD))).toWebHexString()).append("'>").append(chanceToRecoverTurn).append("%</span> chance to recover [style.colourBlueLight(after use)]<br/>");

            descriptionSB.append("<span style='color:").append((chanceToRecoverCombat <= 25 ? PresetColour.GENERIC_BAD : (chanceToRecoverCombat <= 50 ? PresetColour.GENERIC_MINOR_BAD : (chanceToRecoverCombat <= 75 ? PresetColour.GENERIC_MINOR_GOOD : PresetColour.GENERIC_GOOD))).toWebHexString()).append(";'>").append(chanceToRecoverCombat).append("%</span> chance to recover [style.colourCombat(after combat)]<br/>");
        }
        descriptionSB.append("</b></p>");

        descriptionSB.append("<p>");
        descriptionSB.append(weaponType.getDescription());
        descriptionSB.append("<br/>").append(getWeaponType().isPlural() ? "They have" : "It has").append(" a value of: ").append(UtilText.formatAsMoney(getValue()));
        descriptionSB.append("</p>");


        // Physical resistance
        if (getWeaponType().getPhysicalResistance() > 0) {
            descriptionSB.append("<p>").append(getWeaponType().isPlural()
                    ? "They are armoured, and provide "
                    : "It is armoured, and provides ").append(" <b>").append(getWeaponType().getPhysicalResistance()).append("</b> [style.colourResPhysical(").append(Attribute.RESISTANCE_PHYSICAL.getName()).append(")].").append("</p>");
        }


        if (!this.getEffects().isEmpty() || !this.getWeaponType().getExtraEffects().isEmpty()) {
            descriptionSB.append("<p>Effects:");
            for (ItemEffect e : this.getEffects()) {
                if (e.getPrimaryModifier() != TFModifier.CLOTHING_ATTRIBUTE
                        && e.getPrimaryModifier() != TFModifier.CLOTHING_MAJOR_ATTRIBUTE) {
                    for (String s : e.getEffectsDescription(Main.game.getPlayer(), Main.game.getPlayer())) {
                        descriptionSB.append("<br/>").append(s);
                    }
                }
            }
            for (String s : this.getWeaponType().getExtraEffects()) {
                descriptionSB.append("<br/>").append(s);
            }
            for (Entry<AbstractAttribute, Integer> entry : this.getAttributeModifiers().entrySet()) {
                descriptionSB.append("<br/><b>").append(entry.getKey().getFormattedValue(entry.getValue())).append("</b>");
            }
            descriptionSB.append("</p>");
        }

        if (!spells.isEmpty()) {
            descriptionSB.append("<p>").append(getWeaponType().isPlural() ? "Their" : "Its").append(" arcane power grants you the ability to cast ");
            int i = 0;
            for (Spell s : spells) {
                if (i != 0) {
                    if (i + 1 == spells.size())
                        descriptionSB.append(" and ");
                    else
                        descriptionSB.append(", ");
                }

                descriptionSB.append("<b style='color:").append(s.getSpellSchool().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(s.getName())).append("</b>");
                i++;
            }
            descriptionSB.append(".</p>");
        }


        if (!combatMoves.isEmpty()) {
            descriptionSB.append("<p>When equipped, ").append(getWeaponType().isPlural() ? "they unlock" : "it unlocks").append(" the move").append(combatMoves.size() == 1 ? "" : "s").append(": ");
            List<String> combatMoveNames = new ArrayList<>();
            descriptionSB.append("[style.italicsCombat(");
            for (AbstractCombatMove cm : combatMoves) {
                combatMoveNames.add(cm.getName(0, character));
            }
            descriptionSB.append(Util.stringsToStringList(combatMoveNames, true));
            descriptionSB.append(")]</p>");
        }

        if (getWeaponType().getClothingSet() != null) {
            descriptionSB.append("<p>").append(getWeaponType().isPlural() ? "They are" : "It is").append(" part of the <b style='color:").append(PresetColour.RARITY_EPIC.toWebHexString()).append(";'>").append(getWeaponType().getClothingSet().getName()).append("</b> set.").append("</p>");
        }

        return descriptionSB.toString();
    }

    @Override
    public Rarity getRarity() {
        if (rarity == Rarity.COMMON) {
            if (this.getWeaponType().getClothingSet() != null) {
                return Rarity.EPIC;
            }
            if (this.getEffects().size() > 1) {
                return Rarity.RARE;
            }
            if (!this.getEffects().isEmpty()) {
                return Rarity.UNCOMMON;
            }
        }
        return rarity;
    }

    @Override
    public int getValue() {
        float modifier = 1;

        if (this.getRarity() == Rarity.JINXED) {
            modifier -= 0.25f;
        }

        if (this.getEffects() != null) {
            List<TFModifier> types = effects.stream().map(ItemEffect::getPrimaryModifier).toList();
            float typeModifier = 0.025f;
            boolean clothingBonus = false;
            if (types.contains(TFModifier.CLOTHING_MAJOR_ATTRIBUTE)) {
                typeModifier = 0.5f;
                clothingBonus = true;
            } else if (types.contains(TFModifier.CLOTHING_ATTRIBUTE)
                    || types.contains(TFModifier.DAMAGE_WEAPON)
                    || types.contains(TFModifier.RESISTANCE_WEAPON)) {
                typeModifier = 0.2f;
                clothingBonus = true;
            }

            List<TFPotency> potencies = effects.stream().map(ItemEffect::getPotency).toList();
            if (potencies.contains(TFPotency.MAJOR_BOOST)) {
                modifier += (clothingBonus ? TFPotency.MAJOR_BOOST.getClothingBonusValue() : TFPotency.MAJOR_BOOST.getValue()) * typeModifier;
            } else if (potencies.contains(TFPotency.BOOST)) {
                modifier += (clothingBonus ? TFPotency.BOOST.getClothingBonusValue() : TFPotency.BOOST.getValue()) * typeModifier;
            } else if (potencies.contains(TFPotency.MINOR_BOOST)) {
                modifier += (clothingBonus ? TFPotency.MINOR_BOOST.getClothingBonusValue() : TFPotency.MINOR_BOOST.getValue()) * typeModifier;
            }
        }

        modifier += !this.getSpells().isEmpty() ? 0.2f : 0;

        modifier += effects.size() * 0.01f;

        if (getWeaponType().getClothingSet() != null) {
            modifier += 1;
        }

        modifier = Math.max(0.25f, modifier);

        return Math.max(1, (int) (this.getWeaponType().getBaseValue() * modifier));
    }

    /**
     * @param characterOwner The character who owns this item.
     * @return A List of Strings describing extra features of this WeaponType.
     */
    public List<String> getExtraDescriptions(GameCharacter characterOwner) {
        List<String> descriptionsList = new ArrayList<>();

        for (ItemTag it : this.getWeaponType().getItemTags()) {
            descriptionsList.addAll(it.getClothingTooltipAdditions());
        }

        return descriptionsList;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    public AbstractWeaponType getWeaponType() {
        return weaponType;
    }

    public String getName(boolean withDeterminer, boolean withRarityColour) {
        return (withDeterminer
                ? (!weaponType.getDeterminer().equalsIgnoreCase("a") && !weaponType.getDeterminer().equalsIgnoreCase("an")
                ? weaponType.getDeterminer()
                : UtilText.generateSingularDeterminer(getWeaponType().isAppendDamageName() ? damageType.getWeaponDescriptor() : name))
                + " "
                : "")
                + (getWeaponType().isAppendDamageName()
                ? (withRarityColour
                ? "<span style='color:" + damageType.getMultiplierAttribute().getColour().toWebHexString() + ";'>" + damageType.getWeaponDescriptor() + "</span>"
                : damageType.getWeaponDescriptor())
                : "")
                + (withRarityColour
                ? (" <span style='color: " + rarity.getColour().toWebHexString() + ";'>" + name + "</span>")
                : " " + name);
    }

    @Override
    public String getDisplayName(boolean withRarityColour) {
        return (getWeaponType().isAppendDamageName()
                ? "<span style='color:" + damageType.getMultiplierAttribute().getColour().toWebHexString() + ";'>" + Util.capitaliseSentence(damageType.getWeaponDescriptor()) + "</span> "
                : "")
                + (withRarityColour ? (" <span style='color: " + rarity.getColour().toWebHexString() + ";'>" + name + "</span>") : name);
    }

    @Override
    public String getDisplayNamePlural(boolean withRarityColour) {
        return (getWeaponType().isAppendDamageName()
                ? "<span style='color:" + damageType.getMultiplierAttribute().getColour().toWebHexString() + ";'>" + Util.capitaliseSentence(damageType.getWeaponDescriptor()) + "</span> "
                : "")
                + (withRarityColour ? (" <span style='color: " + rarity.getColour().toWebHexString() + ";'>" + namePlural + "</span>") : namePlural);
    }

    @Override
    public String getSVGString() {
        return weaponType.getSVGImage(damageType, this.getColours());
    }

    public String getSVGEquippedString(GameCharacter owner) {
        return weaponType.getSVGEquippedImage(owner, damageType, this.getColours());
    }

    public List<Spell> getSpells() {
        return spells;
    }

    public List<AbstractCombatMove> getCombatMoves() {
        return combatMoves;
    }

    public AbstractAttribute getCoreEnchantment() {
        return coreEnchantment;
    }

    public SpellSchool getSpellSchool() {
        return this.getDamageType().getSpellSchool();
    }

    public boolean isAbleToBeUsed(GameCharacter user, GameCharacter target) {
        return this.getWeaponType().isAbleToBeUsed(user, target);
    }

    public String getUnableToBeUsedDescription() {
        return this.getWeaponType().getUnableToBeUsedDescription();
    }

    public String applyExtraEffects(GameCharacter user, GameCharacter target, boolean isHit, boolean isCritical) {
        return this.getWeaponType().applyExtraEffects(user, target, isHit, isCritical).trim();
    }


    @Override
    public List<ItemEffect> getEffects() {
        return effects;
    }

    public void setEffects(List<ItemEffect> effects) {
        this.effects = new ArrayList<>(effects);
    }

    public void addEffect(ItemEffect effect) {
        effects.add(effect);
    }

    @Override
    public Map<AbstractAttribute, Integer> getAttributeModifiers() {
        attributeModifiers.clear();

        for (ItemEffect ie : getEffects()) {
            if ((ie.getPrimaryModifier() == TFModifier.CLOTHING_ATTRIBUTE || ie.getPrimaryModifier() == TFModifier.CLOTHING_MAJOR_ATTRIBUTE)
                    && (Main.game.isEnchantmentCapacityEnabled() || ie.getSecondaryModifier() != TFModifier.ENCHANTMENT_LIMIT)) {
                if (attributeModifiers.containsKey(ie.getSecondaryModifier().getAssociatedAttribute())) {
                    attributeModifiers.put(ie.getSecondaryModifier().getAssociatedAttribute(), attributeModifiers.get(ie.getSecondaryModifier().getAssociatedAttribute()) + ie.getPotency().getClothingBonusValue());
                } else {
                    attributeModifiers.put(ie.getSecondaryModifier().getAssociatedAttribute(), ie.getPotency().getClothingBonusValue());
                }
            }
        }

        return attributeModifiers;
    }

    /**
     * @return An integer value of the 'enchantment capacity cost' for this particular weapon. Does not count negative attribute values, and values of Corruption are reversed (so reducing corruption costs enchantment stability).
     */
    public int getEnchantmentCapacityCost() {
        Map<AbstractAttribute, Integer> noCorruption = new HashMap<>();
        getAttributeModifiers().entrySet().stream().filter(ent -> ent.getKey() != Attribute.FERTILITY && ent.getKey() != Attribute.VIRILITY).forEach(ent -> noCorruption.put(ent.getKey(), ent.getValue() * (ent.getKey() == Attribute.MAJOR_CORRUPTION ? -1 : 1)));
        return noCorruption.values().stream().reduce(0, (a, b) -> a + Math.max(0, b));
    }

    @Override
    public int getEnchantmentLimit() {
        return weaponType.getEnchantmentLimit();
    }

    @Override
    public AbstractItemEffectType getEnchantmentEffect() {
        return weaponType.getEnchantmentEffect();
    }

    @Override
    public AbstractCoreType getEnchantmentItemType(List<ItemEffect> effects) {
        return weaponType.getEnchantmentItemType(effects);
    }

    @Override
    public Set<ItemTag> getItemTags() {
        return new HashSet<>(this.getWeaponType().getItemTags());
    }

    public boolean isCanBeEquipped(GameCharacter clothingOwner, InventorySlot slot) {
        return this.isAbleToBeEquipped(clothingOwner, slot).getKey();
    }

    public String getCannotBeEquippedText(GameCharacter clothingOwner, InventorySlot slot) {
        return UtilText.parse(clothingOwner, this.isAbleToBeEquipped(clothingOwner, slot).getValue());
    }

    public Value<Boolean, String> isAbleToBeEquipped(GameCharacter clothingOwner, InventorySlot slot) {
        BodyPartClothingBlock block = slot.getBodyPartClothingBlock(clothingOwner);
        Set<ItemTag> tags = this.getItemTags();

        if (this.getWeaponType().getItemTags().contains(ItemTag.UNIQUE_NO_NPC_EQUIP) && !clothingOwner.isPlayer()) {
            return new Value<>(false, UtilText.parse("[style.colourBad(Only you can equip this weapon!)]"));
        }

        if (block != null && Collections.disjoint(block.getRequiredTags(), tags)) {
            return new Value<>(false, UtilText.parse("[style.colourBad(" + UtilText.parse(clothingOwner, block.getDescription()) + ")]"));
        }
        return new Value<>(true, "");
    }
}