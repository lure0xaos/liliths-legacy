package com.lilithslegacy.controller.eventListeners.tooltips;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;

import com.lilithslegacy.controller.TooltipUpdateThread;
import com.lilithslegacy.game.PropertyValue;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.attributes.AbstractAttribute;
import com.lilithslegacy.game.character.attributes.ArousalLevel;
import com.lilithslegacy.game.character.attributes.Attribute;
import com.lilithslegacy.game.character.attributes.CorruptionLevel;
import com.lilithslegacy.game.character.attributes.IntelligenceLevel;
import com.lilithslegacy.game.character.attributes.LustLevel;
import com.lilithslegacy.game.character.attributes.PhysiqueLevel;
import com.lilithslegacy.game.character.body.Body;
import com.lilithslegacy.game.character.body.BodyPartInterface;
import com.lilithslegacy.game.character.body.CoverableArea;
import com.lilithslegacy.game.character.body.coverings.AbstractBodyCoveringType;
import com.lilithslegacy.game.character.body.coverings.BodyCoveringType;
import com.lilithslegacy.game.character.body.coverings.Covering;
import com.lilithslegacy.game.character.body.types.AntennaType;
import com.lilithslegacy.game.character.body.types.HornType;
import com.lilithslegacy.game.character.body.types.TailType;
import com.lilithslegacy.game.character.body.types.VaginaType;
import com.lilithslegacy.game.character.body.types.WingType;
import com.lilithslegacy.game.character.body.valueEnums.BreastShape;
import com.lilithslegacy.game.character.body.valueEnums.CoveringPattern;
import com.lilithslegacy.game.character.body.valueEnums.Femininity;
import com.lilithslegacy.game.character.body.valueEnums.LegConfiguration;
import com.lilithslegacy.game.character.effects.AbstractPerk;
import com.lilithslegacy.game.character.effects.AbstractStatusEffect;
import com.lilithslegacy.game.character.effects.Perk;
import com.lilithslegacy.game.character.effects.PerkCategory;
import com.lilithslegacy.game.character.effects.PerkManager;
import com.lilithslegacy.game.character.effects.StatusEffect;
import com.lilithslegacy.game.character.fetishes.AbstractFetish;
import com.lilithslegacy.game.character.fetishes.FetishDesire;
import com.lilithslegacy.game.character.fetishes.FetishLevel;
import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.character.npc.misc.Elemental;
import com.lilithslegacy.game.character.race.AbstractRace;
import com.lilithslegacy.game.character.race.Race;
import com.lilithslegacy.game.combat.Attack;
import com.lilithslegacy.game.combat.moves.AbstractCombatMove;
import com.lilithslegacy.game.combat.spells.Spell;
import com.lilithslegacy.game.combat.spells.SpellUpgrade;
import com.lilithslegacy.game.dialogue.places.dominion.lilayashome.Library;
import com.lilithslegacy.game.dialogue.utils.InventoryDialogue;
import com.lilithslegacy.game.dialogue.utils.InventoryInteraction;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.InventorySlot;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.inventory.enchanting.ItemEffect;
import com.lilithslegacy.game.inventory.enchanting.LoadedEnchantment;
import com.lilithslegacy.game.occupantManagement.slave.SlaveJob;
import com.lilithslegacy.game.occupantManagement.slave.SlaveJobFlag;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.rendering.CachedImage;
import com.lilithslegacy.rendering.ImageCache;
import com.lilithslegacy.rendering.RenderingEngine;
import com.lilithslegacy.utils.Units;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Util.Value;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.world.Cell;
import com.lilithslegacy.world.WorldType;

/**
 * @author Innoxia
 * @version 0.3.8.6
 * @since 0.1.0
 */
public class TooltipInformationEventListener implements EventListener {
    private static final int LINE_HEIGHT = 16;
    private static boolean attributeTableLeft = true;
    private static final StringBuilder tooltipSB = new StringBuilder();
    private String title;
    private String description;
    private boolean extraAttributes = false;
    private boolean weather = false;
    private boolean protection = false;
    private boolean copyInformation = false;
    private boolean availableForSelection = false;
    private GameCharacter owner;
    private AbstractStatusEffect statusEffect;
    private AbstractPerk perk;
    private AbstractPerk levelUpPerk;
    private int perkRow;
    private AbstractFetish fetish;
    private boolean fetishExperience = false;
    private FetishDesire desire;
    private Spell spell;
    private SpellUpgrade spellUpgrade;
    private AbstractAttribute attribute;
    private InventorySlot concealedSlot;
    private LoadedEnchantment loadedEnchantment;
    private AbstractCombatMove move;
    private Cell cell;
    private GameCharacter moneyTransferTarget;
    private int moneyTransferPercentage;
    private SlaveJob slaveJob;
    private Body loadedBody;
    private int descriptionHeightOverride;

    @Override
    public void handleEvent(Event event) {
        Main.mainController.setTooltipSize(360, 180);
        Main.mainController.setTooltipContent("");

        if (statusEffect != null) {

            // I hate this. If only JavaFX's height detection and resizing methods actually worked...
            int size = statusEffect.getModifiersAsStringList(owner).size() + statusEffect.getCombatMoves().size() + statusEffect.getSpells().size();
            int yIncrease = (size > 4 ? size - 4 : 0) + (owner.hasStatusEffect(statusEffect) ? (owner.getStatusEffectDuration(statusEffect) == -1 && !statusEffect.isCombatEffect() ? 0 : 2) : 0);
//								+ (owner.hasStatusEffect(statusEffect)?(owner.getStatusEffectDuration(statusEffect) == -1 ? 0 : 2):0);
            int spacingHeight = 0;

            List<Value<Integer, String>> additionalDescriptions = statusEffect.getAdditionalDescriptions(owner);
            if (additionalDescriptions != null && !additionalDescriptions.isEmpty()) {
                for (Value<Integer, String> value : additionalDescriptions) {
                    yIncrease += 1 + value.getKey();
                }
                spacingHeight += 12 * additionalDescriptions.size();
            }

            Main.mainController.setTooltipSize(360, 278 + spacingHeight + (yIncrease * LINE_HEIGHT));


            // Title:
            tooltipSB.setLength(0);
            tooltipSB.append("<body>" + "<div class='title'>").append(Util.capitaliseSentence(statusEffect.getName(owner))).append("</div>");

            // Attribute modifiers:
            tooltipSB.append("<div class='subTitle-picture'>");// style='white-space: nowrap'>");
            boolean effectsFound = false;
            if (statusEffect != StatusEffect.SUBSPECIES_BONUS || (Main.getProperties().isAdvancedRaceKnowledgeDiscovered(owner.getTrueSubspecies()) && !owner.isRaceConcealed()) || owner.isPlayer()) {
                if (!statusEffect.getModifiersAsStringList(owner).isEmpty()) {
                    for (String s : statusEffect.getModifiersAsStringList(owner)) {
                        tooltipSB.append(effectsFound ? "<br/>" : "").append(UtilText.parse(owner, s));
                        effectsFound = true;
                    }
                }
            } else {
                tooltipSB.append("<p style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>");
                if (owner.isRaceConcealed()) {
                    tooltipSB.append(UtilText.parse(owner, "You don't know what [npc.namePos] race is, so can't know [npc.her] strengths and weaknesses...</p>"));
                } else {
                    tooltipSB.append(UtilText.parse(owner, "You don't know enough about [npc.racePlural] to know [npc.her] strengths and weaknesses...</p>"));
                }
                effectsFound = true;
            }
            for (AbstractCombatMove cm : statusEffect.getCombatMoves()) {
                tooltipSB.append(effectsFound ? "<br/>" : "").append("[style.boldExcellent(Grants)] [style.boldCombat(Move)]: ").append(Util.capitaliseSentence(cm.getName(0, owner)));
                effectsFound = true;
            }
            for (Spell spell : statusEffect.getSpells()) {
                tooltipSB.append(effectsFound ? "<br/>" : "").append("[style.boldExcellent(Grants)] [style.boldSpell(Spell)]<b>:</b> <b style='color:").append(spell.getSpellSchool().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(spell.getName())).append("</b>");
                effectsFound = true;
            }

            if (!effectsFound) {
                tooltipSB.append("<span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>No bonuses</span>");
            }
            tooltipSB.append("</div>");

            // Picture:
            tooltipSB.append("<div class='picture'>").append(statusEffect.getSVGString(owner)).append("</div>").append("<div class='description'>").append(statusEffect.getDescription(owner)).append("</div>");

            if (additionalDescriptions != null && !additionalDescriptions.isEmpty()) {
                for (Value<Integer, String> desc : additionalDescriptions) {
                    int heightString = 16 + (desc.getKey() * LINE_HEIGHT);
                    tooltipSB.append("<div class='description' style='text-align:center; line-height:" + LINE_HEIGHT + "px; min-height:").append(heightString).append("px;height:").append(heightString).append("px;'>").append(desc.getValue()).append("</div>");
                }
            }

            if (owner.hasStatusEffect(statusEffect)) {
                if (owner.getStatusEffectDuration(statusEffect) != -1 || statusEffect.isCombatEffect()) {
                    if (statusEffect.isCombatEffect()) {
                        tooltipSB.append("<div class='subTitle'><b>Turns remaining: ");
                        if (owner.getStatusEffectDuration(statusEffect) != -1) {
                            tooltipSB.append(owner.getStatusEffectDuration(statusEffect));
                        } else {
                            tooltipSB.append(UtilText.getBasicInfinitySymbol());
                        }
                        tooltipSB.append("</b></div>");

                    } else {
                        int timerHeight = (int) ((owner.getStatusEffectDuration(statusEffect) / (60 * 60 * 6f)) * 100);

                        Colour timerColour = PresetColour.STATUS_EFFECT_TIME_HIGH;

                        if (timerHeight > 100) {
                            timerHeight = 100;
                            timerColour = PresetColour.STATUS_EFFECT_TIME_OVERFLOW;
                        } else if (timerHeight < 15) {
                            timerColour = PresetColour.STATUS_EFFECT_TIME_LOW;
                        } else if (timerHeight < 50) {
                            timerColour = PresetColour.STATUS_EFFECT_TIME_MEDIUM;
                        }
                        int minutes = Math.max(1, owner.getStatusEffectDuration(statusEffect) / 60);
                        int hours = minutes / 60;
                        int days = hours / 24;

                        tooltipSB.append("<div class='subTitle'><b>Time remaining: " + "<b style='color:").append(timerColour.toWebHexString()).append(";'>").append(days > 0
                                ? days + " day" + (days > 1 ? "s" : "")
                                + (hours % 24 > 0
                                ? " " + (hours % 24) + " hour" + ((hours % 24) > 1 ? "s" : "")
                                + (minutes % 60 > 0
                                ? " " + (minutes % 60) + " minute" + ((minutes % 60) > 1 ? "s" : "")
                                : "")
                                : (minutes % 60 > 0
                                ? " " + (minutes % 60) + " minute" + ((minutes % 60) > 1 ? "s" : "")
                                : ""))
                                : (hours > 0
                                ? " " + (hours) + " hour" + ((hours) > 1 ? "s" : "")
                                + (minutes % 60 > 0
                                ? " " + (minutes % 60) + " minute" + ((minutes % 60) > 1 ? "s" : "")
                                : "")
                                : (minutes) + " minute" + ((minutes) > 1 ? "s" : ""))).append("</b>").append("</div>");
                        //STATUS_EFFECT_TIME_OVERFLOW
                    }
                }
            }

            tooltipSB.append("</body>");

            Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));

            // Wasted more time trying to get JavaFX to return sensible height values.
//			int height = Integer.valueOf(((String) Main.mainController.getWebEngineTooltip().executeScript("window.getComputedStyle(document.body, null).getPropertyValue('height')")).replace("px", ""));
////					"Math.max( document.body.scrollHeight, document.body.offsetHeight );");
//
//			System.out.println(height);
//
//			Main.mainController.setTooltipSize(360, height+8);

        } else if (perk != null) { // Perks:

            int yIncrease = (perk.getModifiersAsStringList(owner).size() > 4 ? perk.getModifiersAsStringList(owner).size() - 4 : 0);

            Main.mainController.setTooltipSize(360, 324 + (yIncrease * LINE_HEIGHT));

            // Title:
            tooltipSB.setLength(0);
            tooltipSB.append("<div class='title'>").append(Util.capitaliseSentence(perk.getName(owner))).append("</div>");

            if (perk.isEquippableTrait()) {
                tooltipSB.append("<div class='subTitle' style='color:").append(PresetColour.TRAIT.toWebHexString()).append(";'>Trait</div>");
            } else {
                tooltipSB.append("<div class='subTitle' style='color:").append(PresetColour.PERK.toWebHexString()).append(";'>Perk</div>");
            }

            // Attribute modifiers:
            tooltipSB.append("<div class='subTitle-picture'>");
            if (!perk.getModifiersAsStringList(owner).isEmpty()) {
                int i = 0;
                for (String s : perk.getModifiersAsStringList(owner)) {
                    tooltipSB.append(i != 0 ? "<br/>" : "").append(s);
                    i++;
                }
            } else {
                tooltipSB.append("<b style='color:").append(PresetColour.PERK.toWebHexString()).append(";'>Perk</b>").append("<br/><span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>None</span>");
            }
            tooltipSB.append("</div>");

            // Picture:
            tooltipSB.append("<div class='picture'>").append(perk.getSVGString(owner)).append("</div>");

            // Description:
            tooltipSB.append("<div class='description'>").append(UtilText.parse(owner, perk.getDescription(owner))).append("</div>");

            Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));


        } else if (levelUpPerk != null) { // Level Up Perk (same as Perk, but with requirements at top):

            int yIncrease = (levelUpPerk.getModifiersAsStringList(owner).size() > 4 ? levelUpPerk.getModifiersAsStringList(owner).size() - 4 : 0);

            Main.mainController.setTooltipSize(360, 320 + (availableForSelection ? 32 : 0) + (yIncrease * LINE_HEIGHT));

            // Title:
            tooltipSB.setLength(0);
            tooltipSB.append("<div class='title'>").append(Util.capitaliseSentence(levelUpPerk.getName(owner))).append("</div>");

            if (levelUpPerk.isEquippableTrait()) {
                if (levelUpPerk.getPerkCategory() == PerkCategory.JOB) {
                    tooltipSB.append("<div class='subTitle' style='color:").append(PresetColour.GENERIC_EXCELLENT.toWebHexString()).append(";'>'").append(Util.capitaliseSentence(owner.getHistory().getName(owner))).append("' Occupation Trait</div>");
                } else if (levelUpPerk.isHiddenPerk()) {
                    tooltipSB.append("<div class='subTitle' style='color:").append(PresetColour.GENERIC_EXCELLENT.toWebHexString()).append(";'>Unique Trait</div>");
                } else {
                    tooltipSB.append("<div class='subTitle' style='color:").append(PresetColour.TRAIT.toWebHexString()).append(";'>Trait</div>");
                }
            } else {
                if (levelUpPerk.isHiddenPerk()) {
                    tooltipSB.append("<div class='subTitle' style='color:").append(PresetColour.GENERIC_EXCELLENT.toWebHexString()).append(";'>Unique Perk</div>");
                } else {
                    tooltipSB.append("<div class='subTitle' style='color:").append(PresetColour.PERK.toWebHexString()).append(";'>Perk</div>");
                }
            }

            // Attribute modifiers:
            tooltipSB.append("<div class='subTitle-picture'>");
            if (!levelUpPerk.getModifiersAsStringList(owner).isEmpty()) {
                int i = 0;
                for (String s : levelUpPerk.getModifiersAsStringList(owner)) {
                    tooltipSB.append(i != 0 ? "<br/>" : "").append(s);
                    i++;
                }
            } else {
                tooltipSB.append("<b style='color:").append(PresetColour.PERK.toWebHexString()).append(";'>Perk</b>").append("<br/><span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>None</span>");
            }
            tooltipSB.append("</div>");

            // Picture:
            tooltipSB.append("<div class='picture'>").append(levelUpPerk.getSVGString(owner)).append("</div>");

            // Description:
//			boolean booly1 = PerkManager.MANAGER.isPerkEndOfTreeBranch(owner, perkRow, levelUpPerk, true);
//			boolean booly2 = PerkManager.MANAGER.isPerkEndOfTreeBranch(owner, perkRow, levelUpPerk, false);
            tooltipSB.append("<div class='description'>"
//					+ booly1+", "+booly2+"<br/>"
            ).append(UtilText.parse(owner, levelUpPerk.getDescription(owner))).append("</div>");

            if (availableForSelection) {
                if (levelUpPerk.isEquippableTrait()) {
                    if (levelUpPerk.getPerkCategory() == PerkCategory.JOB) {
                        tooltipSB.append("<div class='subTitle' style='color:").append(PresetColour.GENERIC_EXCELLENT.toWebHexString()).append(";'>Occupation traits cannot be removed.</div>");

                    } else {
                        if (!owner.hasPerkInTree(perkRow, levelUpPerk)) {
                            if (!PerkManager.MANAGER.isPerkAvailable(owner, perkRow, levelUpPerk)) {
                                tooltipSB.append("<div class='subTitle' style='color:").append(PresetColour.GENERIC_BAD.toWebHexString()).append(";'>Purchasing requires a connecting perk or trait.</div>");
                            } else {
                                tooltipSB.append("<div class='subTitle' style='color:").append(PresetColour.GENERIC_MINOR_GOOD.toWebHexString()).append(";'>Click to purchase trait.</div>");
                            }
                        } else {
                            if (owner.getTraits().contains(levelUpPerk)) {
                                tooltipSB.append("<div class='subTitle' style='color:").append(PresetColour.GENERIC_MINOR_BAD.toWebHexString()).append(";'>Click to unequip trait.</div>");
                            } else {
                                if (owner.getTraits().size() == GameCharacter.MAX_TRAITS) {
                                    tooltipSB.append("<div class='subTitle' style='color:").append(PresetColour.GENERIC_BAD.toWebHexString()).append(";'>Maximum traits activated.</div>");
                                } else {
                                    tooltipSB.append("<div class='subTitle' style='color:").append(PresetColour.TRAIT.toWebHexString()).append(";'>Click to equip trait.</div>");
                                }
                            }
                        }
                    }

                } else {
                    if (!owner.hasPerkInTree(perkRow, levelUpPerk) && !levelUpPerk.isHiddenPerk()) {
                        if (!PerkManager.MANAGER.isPerkAvailable(owner, perkRow, levelUpPerk)) {
                            tooltipSB.append("<div class='subTitle' style='color:").append(PresetColour.GENERIC_BAD.toWebHexString()).append(";'>Purchasing requires a connecting perk or trait.</div>");
                        } else {
                            tooltipSB.append("<div class='subTitle' style='color:").append(PresetColour.GENERIC_MINOR_GOOD.toWebHexString()).append(";'>Click to purchase perk.</div>");
                        }

                    } else {
                        tooltipSB.append("<div class='subTitle' style='color:").append(PresetColour.PERK.toWebHexString()).append(";'>").append(UtilText.parse(owner, "[npc.Name] already [npc.verb(own)] this perk!")).append("</div>");
                    }
                }
            }

            Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));

        } else if (move != null) {
            List<String> critReqs = move.getCritRequirements(owner, null, null, null);

            int currentCooldown = owner.getMoveCooldown(move.getIdentifier());

            Main.mainController.setTooltipSize(360,
                    (Main.game.isInCombat() ? 320 : 352)
                            + (!critReqs.isEmpty() ? (32 + critReqs.size() * 16) : 0)
                            + (currentCooldown > 0 ? 32 : 0));

            // Title:
            tooltipSB.setLength(0);
            tooltipSB.append("<div class='title'>").append(Util.capitaliseSentence(move.getName(0, owner))).append("</div>");

            boolean coreMove = owner.getEquippedMoves().contains(move);

            tooltipSB.append("<div class='subTitle' style='width:46%; margin:2% 2% 0% 2%;'>").append(coreMove ? "[style.colourMinorGood(Core)]" : "[style.colourMinorBad(Non-core)]").append("</div>");
            tooltipSB.append("<div class='subTitle' style='color:").append(move.getColourByDamageType(0, owner).toWebHexString()).append("; width:46%; margin:2% 2% 0% 2%;'>").append(move.getType().getName()).append("</div>");

            if (currentCooldown > 0) {
                tooltipSB.append("<div class='subTitle'><span style='color:").append(PresetColour.GENERIC_MINOR_BAD.toWebHexString()).append(";'>On cooldown</span>: ").append(currentCooldown).append(currentCooldown == 1 ? " turn" : " turns").append("</div>");
            }

            // Picture:

            // Description:
            tooltipSB.append("<div class='subTitle-picture'>");

            int apCost = move.getAPcost(owner);
            int cooldown = move.getCooldown(owner);

            tooltipSB.append("AP cost: " + "<span style='color:").append((PresetColour.ACTION_POINT_COLOURS[apCost]).toWebHexString()).append(";'>").append(coreMove ? apCost : (apCost - 1) + "[style.colourBad(+1)]").append("</span>").append("<br/>Cooldown: ").append("<span style='color:").append((cooldown - (coreMove ? 0 : 1) <= 0 ? PresetColour.GENERIC_MINOR_GOOD : PresetColour.GENERIC_MINOR_BAD).toWebHexString()).append(";'>").append(coreMove ? cooldown : (cooldown - 1) + "[style.colourBad(+1)]").append("</span> turn").append(cooldown == 1 ? "" : "s");

//			tooltipSB.append("AP cost: "+"<span style='color:"+(apColours[apCost]).toWebHexString()+";'>"+apCost+"</span>");
//			tooltipSB.append("<br/>Cooldown: "+"<span style='color:"+(cooldown==0?PresetColour.GENERIC_MINOR_GOOD:PresetColour.GENERIC_MINOR_BAD).toWebHexString()+";'>"+cooldown+(cooldown==1?" turn":" turns")+"</span>");

            if (move.getStatusEffects(owner, owner, false) != null) {
                for (Entry<AbstractStatusEffect, Integer> entry : move.getStatusEffects(owner, owner, false).entrySet()) {
                    tooltipSB.append("<br/>Applies: <span style='color:").append(entry.getKey().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(entry.getKey().getName(null))).append("</span> for ").append(entry.getValue()).append(entry.getValue() == 1 ? " turn" : " turns");
                }
            }
            tooltipSB.append("</div>");

            tooltipSB.append("<div class='picture'>").append(move.getSVGString()).append("</div>");

            // Description:
            Value<Boolean, String> availableValue = owner.isMoveAvailable(move.getIdentifier());

            tooltipSB.append("<div class='description'>" + "<span style='color:").append((availableValue.getKey() ? PresetColour.GENERIC_MINOR_GOOD : PresetColour.GENERIC_MINOR_BAD).toWebHexString()).append(";'>").append(availableValue.getValue()).append("</span> ").append(move.getDescription(!Main.game.isInCombat() ? 0 : owner.getSelectedMoves().size(), owner)).append("</div>");


            tooltipSB.append("<div class='subTitle'><span style='color:").append(PresetColour.CRIT.toWebHexString()).append(";'>Critically hits when:</span>");
            for (String s : critReqs) {
                tooltipSB.append("<br/>").append(s);
            }
            tooltipSB.append("</div>");

            if (!Main.game.isInCombat()) {
                if (owner.getEquippedMoves().contains(move)) {
                    tooltipSB.append("<div class='subTitle' style='color:").append(PresetColour.GENERIC_MINOR_BAD.toWebHexString()).append(";'>Click to unequip move.</div>");
                } else {
                    if (owner.getEquippedMoves().size() >= GameCharacter.MAX_COMBAT_MOVES) {
                        tooltipSB.append("<div class='subTitle' style='color:").append(PresetColour.GENERIC_BAD.toWebHexString()).append(";'>Maximum core moves selected.</div>");
                    } else {
                        tooltipSB.append("<div class='subTitle' style='color:").append(PresetColour.TRAIT.toWebHexString()).append(";'>Click to equip move.</div>");
                    }
                }
            }

            Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));

        } else if (desire != null) { // Desire:

            Main.mainController.setTooltipSize(360, 264);

            // Title:
            tooltipSB.setLength(0);
            tooltipSB.append("<div class='title'>Set Desire: <b style='color:").append(desire.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(desire.getName())).append("</b></div>");

            // Attribute modifiers:
            tooltipSB.append("<div class='subTitle-picture'>");
            int i = 0;
            for (String s : desire.getModifiersAsStringList()) {
                tooltipSB.append(i != 0 ? "<br/>" : "").append(s);
                i++;
            }
            tooltipSB.append("</div>");

            // Picture:
            tooltipSB.append("<div class='picture'>").append(desire.getSVGImage()).append("</div>");

            // Description:
            if (owner.hasFetish(fetish) && desire != FetishDesire.FOUR_LOVE) {
                tooltipSB.append("<div class='description' style='height:53px'>Your desire is [style.boldBad(locked)] to <b style='color:").append(FetishDesire.FOUR_LOVE.getColour().toWebHexString()).append(";'>").append(FetishDesire.FOUR_LOVE.getName()).append("</b>,").append(" due to owning the related fetish (").append(fetish.getName(owner)).append(").</div>");
                tooltipSB.append("<div class='subTitle' style='text-align:center;'>Cost: [style.boldDisabled(N/A)]</div>");
            } else {
                tooltipSB.append("<div class='description' style='height:53px'>").append(fetish.getFetishDesireDescription(owner, desire)).append("</div>");
                if (owner.getBaseFetishDesire(fetish) == desire) {
                    tooltipSB.append("<div class='subTitle' style='text-align:center;'>Cost: [style.boldDisabled(N/A)]</div>");
                } else {
                    tooltipSB.append("<div class='subTitle' style='text-align:center;'>Cost: [style.boldArcane(").append(FetishDesire.getCostToChange() == 0
                            ? "Free"
                            : FetishDesire.getCostToChange() + " Arcane Essence" + (FetishDesire.getCostToChange() > 1 ? "s" : "")).append(")]</div>");
                }
            }


            Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));

        } else if (fetish != null) { // Fetishes:

            if (fetishExperience) {

                Main.mainController.setTooltipSize(420, 156);

                tooltipSB.setLength(0);
                tooltipSB.append("<div class='title'>").append(Util.capitaliseSentence(fetish.getName(owner))).append(" fetish</div>");
                FetishLevel level = FetishLevel.getFetishLevelFromValue(owner.getFetishExperience(fetish));
                tooltipSB.append("<div class='subTitle'>Level ").append(level.getNumeral()).append(": <span style='color:").append(level.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(level.getName())).append("</span>").append(" <span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>|</span> ").append(owner.getFetishExperience(fetish)).append(" / ").append(level.getMaximumExperience()).append(" xp").append("</div>");
                tooltipSB.append("<div class='description' style='height:53px'>You earn fetish experience by performing related actions in sex. Each level increases the fetish's bonuses (max level is 5).</div>");

                Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));

            } else {
                int yIncrease = (fetish.getModifiersAsStringList(owner).size() > 4
                        ? fetish.getModifiersAsStringList(owner).size() - 4
                        : 0);

                yIncrease += fetish.getFetishesForAutomaticUnlock().size();
                if (!owner.hasFetish(fetish)) {
                    yIncrease += fetish.getPerkRequirements(owner).size();
                }
                int specialIncrease = 0;
                if (!owner.hasFetish(fetish) && !fetish.getPerkRequirements(owner).isEmpty()) {
                    specialIncrease += LINE_HEIGHT * 2 + 8;

                } else if (!fetish.getFetishesForAutomaticUnlock().isEmpty()) {
                    specialIncrease += 8;
                }
                specialIncrease += LINE_HEIGHT; // For fetish level effects

                Main.mainController.setTooltipSize(360, 342 + specialIncrease + (yIncrease * LINE_HEIGHT));

                // Title:
                tooltipSB.setLength(0);
                tooltipSB.append("<div class='title'>").append(Util.capitaliseSentence(fetish.getName(owner))).append(" fetish</div>");
                FetishLevel level = FetishLevel.getFetishLevelFromValue(owner.getFetishExperience(fetish));
                tooltipSB.append("<div class='subTitle'>");
                tooltipSB.append("Level ").append(level.getNumeral()).append(": <span style='color:").append(level.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(level.getName())).append("</span>").append(" <span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>|</span> ").append(owner.getFetishExperience(fetish)).append(" / ").append(level.getMaximumExperience()).append(" xp");

                String appliedFetishLevelDescription = fetish.getAppliedFetishLevelEffectDescription(owner);
                tooltipSB.append("<br/>[style.boldFetish(Level Effects:)] ");
                if (appliedFetishLevelDescription != null && !appliedFetishLevelDescription.isEmpty()) {
                    tooltipSB.append(appliedFetishLevelDescription);
                } else {
                    tooltipSB.append("[style.colourDisabled(None...)]");
                }
                tooltipSB.append("</div>");

                // Requirements:
                if (!fetish.getFetishesForAutomaticUnlock().isEmpty() || (!owner.hasFetish(fetish) && !fetish.getPerkRequirements(owner).isEmpty())) {
                    tooltipSB.append("<div class='subTitle' style='font-weight:normal;'><b>Requirements</b>");
                    for (AbstractFetish f : fetish.getFetishesForAutomaticUnlock()) {
                        if (owner.hasFetish(f)) {
                            tooltipSB.append("<br/>[style.italicsGood(").append(Util.capitaliseSentence(f.getName(owner))).append(")]");
                        } else {
                            tooltipSB.append("<br/>[style.italicsBad(").append(Util.capitaliseSentence(f.getName(owner))).append(")]");
                        }
                    }
                    if (!owner.hasFetish(fetish)) {
                        for (String s : fetish.getPerkRequirements(owner)) {
                            tooltipSB.append("<br/>").append(s);
                        }
                    }
                    tooltipSB.append("</div>");
                }

                // Attribute modifiers:
                tooltipSB.append("<div class='subTitle-picture'>");
                if (!fetish.getModifiersAsStringList(owner).isEmpty()) {
                    int i = 0;
                    for (String s : fetish.getModifiersAsStringList(owner)) {
                        tooltipSB.append(i != 0 ? "<br/>" : "").append(s);
                        i++;
                    }
                } else {
                    tooltipSB.append("<b style='color:").append(PresetColour.FETISH.toWebHexString()).append(";'>Fetish</b>").append("<br/><span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>None</span>");
                }
                tooltipSB.append("</div>");

                // Picture:
                tooltipSB.append("<div class='picture'>").append(fetish.getSVGString(owner)).append("</div>");

                // Description:
                tooltipSB.append("<div class='description'>").append(fetish.getDescription(owner)).append("</div>");

                if (fetish.getFetishesForAutomaticUnlock().isEmpty()) {
                    if (owner.hasBaseFetish(fetish)) {
                        tooltipSB.append("<div class='subTitle' style='text-align:center;'>Cost: [style.boldDisabled(N/A)]</div>");
                    } else {
                        tooltipSB.append("<div class='subTitle' style='text-align:center;'>Cost: [style.boldArcane(").append(fetish.getCost()).append(" Arcane Essences)]</div>");
                    }
                }

                Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));
            }

        } else if (spell != null) { // Spells:

            int yIncrease = (spell.getModifiersAsStringList().size() > 5 ? spell.getModifiersAsStringList().size() - 5 : 0);

            Main.mainController.setTooltipSize(360, 312 + (yIncrease * LINE_HEIGHT));

            // Title:
            tooltipSB.setLength(0);
            tooltipSB.append("<div class='title'>").append(Util.capitaliseSentence(spell.getName())).append("</div>");

            // Attribute modifiers:
            tooltipSB.append("<div class='subTitle-picture'>");

            if (spell.getDamage(Main.game.getPlayer()) > 0) {
                tooltipSB.append("<b>Base ").append(spell.getDamage(owner)).append("</b> <b style='color:").append(spell.getDamageType().getMultiplierAttribute().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(spell.getDamageType().getName())).append(" Damage</b><br/>").append("<b>").append(Attack.getMinimumSpellDamage(owner, null, spell.getDamageType(), spell.getDamage(owner), spell.getDamageVariance())).append("-").append(Attack.getMaximumSpellDamage(owner, null, spell.getDamageType(), spell.getDamage(owner), spell.getDamageVariance())).append("</b>").append(" <b style='color:").append(spell.getDamageType().getMultiplierAttribute().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(spell.getDamageType().getName())).append(" Damage</b><br/>");
            }

            if (!spell.getModifiersAsStringList().isEmpty()) {
                for (int i = 0; i < spell.getModifiersAsStringList().size(); i++) {
                    tooltipSB.append(spell.getModifiersAsStringList().get(i)).append(i < spell.getModifiersAsStringList().size() - 1 ? "<br/>" : "");
                }
            } else {
                tooltipSB.append("<span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>No effects</span><br/>");
            }
            tooltipSB.append("</div>");

            // Picture:
            tooltipSB.append("<div class='picture'>").append(spell.getSVGString()).append("</div>");

            // Description & turns remaining:
            tooltipSB.append("<div class='description'>").append(spell.isForbiddenSpell() && !owner.hasSpell(spell) ? "[style.italicsArcane(This is a forbidden spell, and can only be discovered through a special quest!)]<br/>" : "").append(spell.getDescription(owner)).append("<br/>[style.colourExcellent(Crit requirements)]: ");
            for (String s : spell.getCritRequirements(owner, null, null, null)) {
                tooltipSB.append(s);
            }
            tooltipSB.append("</div>" + "<div class='subTitle'>" + "<b style='color:").append(PresetColour.GENERIC_BAD.toWebHexString()).append(";'>Costs</b> <b>").append(spell.getModifiedCost(owner)).append("</b> <b style='color:").append(PresetColour.ATTRIBUTE_MANA.toWebHexString()).append(";'>aura</b>").append("</div>");

            Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));

        } else if (spellUpgrade != null) { // Spell upgrades:

            int yIncrease = (spellUpgrade.getModifiersAsStringList().size() > 5 ? spellUpgrade.getModifiersAsStringList().size() - 5 : 0);

            Main.mainController.setTooltipSize(360, 316 + (yIncrease * LINE_HEIGHT));

            // Title:
            tooltipSB.setLength(0);
            tooltipSB.append("<div class='title'>").append(Util.capitaliseSentence(spellUpgrade.getName())).append("</div>");

            // Attribute modifiers:
            tooltipSB.append("<div class='subTitle-picture'>");

            if (!spellUpgrade.getModifiersAsStringList().isEmpty()) {
                for (int i = 0; i < spellUpgrade.getModifiersAsStringList().size(); i++) {
                    tooltipSB.append(spellUpgrade.getModifiersAsStringList().get(i)).append(i < spellUpgrade.getModifiersAsStringList().size() - 1 ? "<br/>" : "");
                }
            } else {
                tooltipSB.append("<span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>No effects</span><br/>");
            }

            tooltipSB.append("</div>");

            // Picture:
            tooltipSB.append("<div class='picture'>").append(spellUpgrade.getSVGString()).append("</div>");

            // Description:
            tooltipSB.append("<div class='description'>").append(spellUpgrade.getDescription()).append(" ").append(spellUpgrade.getUnavailableReason(owner)).append("</div>").append("<div class='subTitle'>").append(owner.hasSpellUpgrade(spellUpgrade)
                    ? "[style.boldExcellent(Owned)] (Cost <b style='color:" + spellUpgrade.getSpellSchool().getColour().toWebHexString() + ";'>" + spellUpgrade.getPointCost() + "</b> Point" + (spellUpgrade.getPointCost() == 1 ? "" : "s") + ")"
                    : (owner.getSpellUpgradePoints(spellUpgrade.getSpellSchool()) >= spellUpgrade.getPointCost()
                    ? "Costs <b style='color:" + spellUpgrade.getSpellSchool().getColour().toWebHexString() + ";'>" + spellUpgrade.getPointCost() + "</b> Point" + (spellUpgrade.getPointCost() == 1 ? "" : "s") + " - [style.colourGood(Can afford!)]"
                    : "Costs <b style='color:" + spellUpgrade.getSpellSchool().getColour().toWebHexString() + ";'>" + spellUpgrade.getPointCost() + "</b> Point" + (spellUpgrade.getPointCost() == 1 ? "" : "s") + " - [style.colourBad(Cannot afford!)]")).append("</div>");

            Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));

        } else if (attribute != null) {
            if (attribute == Attribute.MAJOR_PHYSIQUE
                    || attribute == Attribute.MAJOR_ARCANE
                    || attribute == Attribute.MAJOR_CORRUPTION
                    || attribute == Attribute.AROUSAL
                    || attribute == Attribute.LUST) {
                AbstractStatusEffect currentAttributeStatusEffect = null;
                int minimumLevelValue = 0;
                int maximumLevelValue = 0;

                if (attribute == Attribute.MAJOR_PHYSIQUE) {
                    currentAttributeStatusEffect = PhysiqueLevel.getPhysiqueLevelFromValue(owner.getAttributeValue(Attribute.MAJOR_PHYSIQUE)).getRelatedStatusEffect();
                    minimumLevelValue = PhysiqueLevel.getPhysiqueLevelFromValue(owner.getAttributeValue(Attribute.MAJOR_PHYSIQUE)).getMinimumValue();
                    maximumLevelValue = PhysiqueLevel.getPhysiqueLevelFromValue(owner.getAttributeValue(Attribute.MAJOR_PHYSIQUE)).getMaximumValue();

                } else if (attribute == Attribute.MAJOR_ARCANE) {
                    currentAttributeStatusEffect = IntelligenceLevel.getIntelligenceLevelFromValue(owner.getAttributeValue(Attribute.MAJOR_ARCANE)).getRelatedStatusEffect();
                    minimumLevelValue = IntelligenceLevel.getIntelligenceLevelFromValue(owner.getAttributeValue(Attribute.MAJOR_ARCANE)).getMinimumValue();
                    maximumLevelValue = IntelligenceLevel.getIntelligenceLevelFromValue(owner.getAttributeValue(Attribute.MAJOR_ARCANE)).getMaximumValue();

                } else if (attribute == Attribute.MAJOR_CORRUPTION) {
                    currentAttributeStatusEffect = CorruptionLevel.getCorruptionLevelFromValue(owner.getAttributeValue(Attribute.MAJOR_CORRUPTION)).getRelatedStatusEffect();
                    minimumLevelValue = CorruptionLevel.getCorruptionLevelFromValue(owner.getAttributeValue(Attribute.MAJOR_CORRUPTION)).getMinimumValue();
                    maximumLevelValue = CorruptionLevel.getCorruptionLevelFromValue(owner.getAttributeValue(Attribute.MAJOR_CORRUPTION)).getMaximumValue();

                } else if (attribute == Attribute.AROUSAL) {
                    currentAttributeStatusEffect = ArousalLevel.getArousalLevelFromValue(owner.getAttributeValue(Attribute.AROUSAL)).getRelatedStatusEffect();
                    minimumLevelValue = ArousalLevel.getArousalLevelFromValue(owner.getAttributeValue(Attribute.AROUSAL)).getMinimumValue();
                    maximumLevelValue = ArousalLevel.getArousalLevelFromValue(owner.getAttributeValue(Attribute.AROUSAL)).getMaximumValue();

                } else if (attribute == Attribute.LUST) {
                    currentAttributeStatusEffect = LustLevel.getLustLevelFromValue(owner.getAttributeValue(Attribute.LUST)).getRelatedStatusEffect();
                    minimumLevelValue = LustLevel.getLustLevelFromValue(owner.getAttributeValue(Attribute.LUST)).getMinimumValue();
                    maximumLevelValue = LustLevel.getLustLevelFromValue(owner.getAttributeValue(Attribute.LUST)).getMaximumValue();
                }

                int yIncrease = (currentAttributeStatusEffect.getModifiersAsStringList(owner).size() > 4 ? currentAttributeStatusEffect.getModifiersAsStringList(owner).size() - 4 : 0)
                        + (owner.hasStatusEffect(currentAttributeStatusEffect) ? (owner.getStatusEffectDuration(currentAttributeStatusEffect) == -1 ? 0 : 2) : 0);

                Main.mainController.setTooltipSize(360, 460 + (yIncrease * LINE_HEIGHT));

                tooltipSB.setLength(0);
                tooltipSB.append("<div class='title' style='color:").append(attribute.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(attribute.getName())).append("</div>").append("<div class='subTitle-third'>").append("<b style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>Core</b><br/>").append(owner.getBaseAttributeValue(attribute) > 0 ? "<span style='color: " + PresetColour.GENERIC_EXCELLENT.getShades()[1] + ";'>" : "<span>").append(Units.number(owner.getBaseAttributeValue(attribute), 1, 1)).append("</span>").append("</div>").append("<div class='subTitle-third'>").append("<b style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>Bonus</b><br/>").append((owner.getBonusAttributeValue(attribute)) > 0 ? "<span style='color: " + PresetColour.GENERIC_GOOD.getShades()[1] + ";'>"
                        : ((owner.getBonusAttributeValue(attribute)) == 0 ? "<span style='color:" + PresetColour.TEXT_GREY.toWebHexString() + ";'>" : "<span style='color: " + PresetColour.GENERIC_BAD.getShades()[1] + ";'>")).append(Units.number(owner.getBonusAttributeValue(attribute), 1, 1)).append("</span>").append("</div>").append("<div class='subTitle-third'>").append("<b style='color:").append(attribute.getColour().toWebHexString()).append(";'>Total</b><br/>").append(Units.number(owner.getAttributeValue(attribute), 1, 1)).append("</span>").append("</div>");

                tooltipSB.append("<div class='description-half'>").append(attribute.getDescription(owner)).append("</div>");

                // Related status effect:
                tooltipSB.append("<div class='title'>" + "<span style='color:").append(currentAttributeStatusEffect.getColour().toWebHexString()).append(";'>").append(currentAttributeStatusEffect.getName(owner)).append("</span> (").append(minimumLevelValue).append("-").append(maximumLevelValue).append(")").append("</div>");

                // Attribute modifiers:
                tooltipSB.append("<div class='subTitle-picture'>");
                if (!currentAttributeStatusEffect.getModifiersAsStringList(owner).isEmpty()) {
                    int i = 0;
                    for (String s : currentAttributeStatusEffect.getModifiersAsStringList(owner)) {
                        if (i != 0) {
                            tooltipSB.append("<br/>");
                        }
                        tooltipSB.append(s);
                        i++;
                    }
                } else {
                    tooltipSB.append("<span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>No bonuses</span>");
                }
                tooltipSB.append("</div>");

                // Picture:
                tooltipSB.append("<div class='picture'>").append(currentAttributeStatusEffect.getSVGString(owner)).append("</div>");

                // Description & turns remaining:
                tooltipSB.append("<div class='description'>").append(currentAttributeStatusEffect.getDescription(owner)).append("</div>");

                Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));

            } else if (attribute == Attribute.EXPERIENCE) {
                // Special tooltip for experience/transformation combo:

                if (owner.isRaceConcealed()) {
                    tooltipSB.setLength(0);
                    tooltipSB.append("<div class='title' style='color:").append(PresetColour.RACE_UNKNOWN.toWebHexString()).append(";'>").append("Unknown Race!").append("</div>");

                    int knownAreas = 0;
                    if (Main.game.getPlayer().isKnowsCharacterArea(CoverableArea.ANUS, owner)) {
                        knownAreas++;
                        tooltipSB.append(getBodyPartDiv(owner, "Anus", owner.getAssRace(), owner.getAssType().getAnusType().getBodyCoveringType(owner), owner.isAnusFeral()));
                    }
                    if (Main.game.getPlayer().isKnowsCharacterArea(CoverableArea.BREASTS, owner)) {
                        knownAreas++;
                        if (owner.isFeral() && !owner.getFeralAttributes().isBreastsPresent()) {
                            tooltipSB.append(getEmptyBodyPartDiv("Nipples (Breasts)", "None"));
                        } else {
                            tooltipSB.append(getBodyPartDiv(owner, "Nipples",
                                    owner.getBreastRace(),
                                    owner.getBreastType().getNippleType().getBodyCoveringType(owner),
                                    owner.isNippleFeral(),
                                    Util.capitaliseSentence(Util.intToString(owner.getBreastRows() * 2)) + " " + (owner.getBreastRawSizeValue() > 0 ? (owner.getBreastSize().getCupSizeName() + "-cup breasts") : (owner.isFeminine() ? "flat breasts" : "pecs"))));
                        }
                    }
                    if (owner.hasBreastsCrotch() && Main.game.getPlayer().isKnowsCharacterArea(CoverableArea.BREASTS_CROTCH, owner)) {
                        knownAreas++;
                        tooltipSB.append(getBodyPartDiv(owner, "Nipples",
                                owner.getBreastCrotchRace(),
                                owner.getNippleCrotchCovering(),
                                owner.isNippleCrotchFeral(),
                                Util.capitaliseSentence(Util.intToString(Math.max(1, owner.getBreastCrotchRows() * 2))) + " "
                                        + (owner.getBreastRawSizeValue() > 0 ? (owner.getBreastCrotchSize().getCupSizeName() + "-cup ") : "flat ")
                                        + (owner.getBreastCrotchShape() == BreastShape.UDDERS
                                        ? (owner.getBreastCrotchRows() == 0
                                        ? "udder"
                                        : "udders")
                                        : "crotch-boobs")));
                    }
                    if (Main.game.getPlayer().isKnowsCharacterArea(CoverableArea.PENIS, owner)) {
                        knownAreas++;
                        if (owner.hasPenisIgnoreDildo()) {
                            tooltipSB.append(getBodyPartDiv(owner, "Penis", owner.getPenisRace(), owner.getPenisCovering(), owner.isPenisFeral(), "[unit.sizeShort(" + owner.getPenisRawSizeValue() + ")]"));
                        }
//						else if (owner.hasPenis()) {
//							tooltipSB.append(getBodyPartDiv(owner, "Penis", owner.getPenisRace(), owner.getPenisCovering(), owner.isPenisFeral(), "[unit.sizeShort(" + owner.getPenisRawSizeValue()+ ")]"));
//						}
                        else {
                            tooltipSB.append(getEmptyBodyPartDiv("Penis", "None"));
                        }
                    }
                    if (Main.game.getPlayer().isKnowsCharacterArea(CoverableArea.VAGINA, owner)) {
                        knownAreas++;
                        if (owner.getVaginaType() != VaginaType.NONE) {
                            tooltipSB.append(getBodyPartDiv(owner, "Vagina", owner.getVaginaRace(), owner.getVaginaCovering(), owner.isVaginaFeral(), owner.isClitorisPseudoPenis() ? "[unit.sizeShort(" + owner.getVaginaRawClitorisSizeValue() + ")] clit" : null));
                        } else {
                            tooltipSB.append(getEmptyBodyPartDiv("Vagina", "None"));
                        }
                    }

                    Main.mainController.setTooltipSize(420, 64 + (knownAreas * 28));


                } else {
                    CachedImage image = null;
                    boolean displayImage = Main.getProperties().hasValue(PropertyValue.thumbnail)
                            && Main.getProperties().hasValue(PropertyValue.artwork)
                            && (!owner.isElemental() || ((Elemental) owner).isActive());
                    if (displayImage) {
                        if (owner.hasArtwork()) {
                            image = ImageCache.INSTANCE.requestImage(owner.getCurrentArtwork().getCurrentImage());
                        }
                        displayImage = image != null;
                    }

                    boolean crotchBreasts = owner.hasBreastsCrotch()
                            && (owner.isBreastsCrotchVisibleThroughClothing() || owner.isAreaKnownByCharacter(CoverableArea.NIPPLES_CROTCH, Main.game.getPlayer()));
                    boolean spinneret = owner.hasSpinneret();
                    boolean elemental = owner.isElemental() && !((Elemental) owner).getSummoner().isElementalActive();

                    int crotchBreastAddition = crotchBreasts ? 24 : 0;
                    int spinneretAddition = spinneret ? 24 : 0;


                    int[] dimensions = new int[]{419, elemental ? 108 + (((Elemental) owner).getSummoner().isPlayer() ? 28 : 0) : (508 + crotchBreastAddition + spinneretAddition)};
                    int imagePadding = 0;
                    int imageWidth = 0;
                    if (displayImage) {
                        // Add the scaled width to the tooltip dimensions
                        int[] scaledSize = image.getAdjustedSize(300, 445);
                        imageWidth = scaledSize[0];
                        dimensions[0] += scaledSize[0];
                        // ... and place it in the bottom right corner of the tooltip
                        imagePadding = Math.max(0, 455 - scaledSize[1]);
                    }

                    Main.mainController.setTooltipSize(dimensions[0], dimensions[1]);

                    boolean showWinged = (owner.hasWings() || owner.isArmWings()) && !owner.getFleshSubspecies().isWinged();
                    tooltipSB.setLength(0);
                    tooltipSB.append("<div class='title' style='color:").append(owner.getRace().getColour().toWebHexString()).append(";'>").append(owner.getRaceStage().getName() != ""
                            ? "<b style='color:" + owner.getRaceStage().getColour().toWebHexString() + ";'>" + Util.capitaliseSentence(owner.getRaceStage().getName()) + "</b> "
                            : "").append("<b style='color:").append(owner.getSubspecies().getColour(owner).toWebHexString()).append(";'>").append(owner.isFeminine()
                            ? Util.capitaliseSentence((showWinged ? "winged " : "") + owner.getSubspecies().getSingularFemaleName(owner.getBody()))
                            : Util.capitaliseSentence((showWinged ? "winged " : "") + owner.getSubspecies().getSingularMaleName(owner.getBody()))).append("</b>").append("</div>");

                    if (displayImage) {
                        tooltipSB.append("<div style='width: 410px; float: left'>");
                    }

                    if (!elemental) {
                        boolean feral = owner.isFeral();

                        // GREATER:
                        if (owner.getCovering(owner.getFaceCovering()).getPattern() == CoveringPattern.FRECKLED_FACE) {
                            Covering c = owner.getCovering(owner.getFaceCovering());
                            tooltipSB.append(getBodyPartDiv(owner, "Face", owner.getFaceRace(),
                                    new Covering(owner.getFaceCovering(),
                                            CoveringPattern.FRECKLED,
                                            c.getModifier(),
                                            c.getPrimaryColour(),
                                            c.isPrimaryGlowing(),
                                            c.getSecondaryColour(),
                                            c.isSecondaryGlowing()),
                                    owner.isFaceFeral(),
                                    null));

                        } else {
                            tooltipSB.append(getBodyPartDiv(owner, "Face", owner.getFaceRace(), owner.getFaceCovering(), owner.isFaceFeral()));
                        }
                        tooltipSB.append(getBodyPartDiv(owner, "Torso", owner.getSkinRace(), owner.getTorsoCovering(), owner.isTorsoFeral(),
                                (owner.isSizeDifferenceShorterThan(Main.game.getPlayer())
                                        ? "<span style='color:" + PresetColour.BODY_SIZE_ONE.toWebHexString() + ";'>"
                                        : (owner.isSizeDifferenceTallerThan(Main.game.getPlayer())
                                        ? "<span style='color:" + PresetColour.BODY_SIZE_FOUR.toWebHexString() + ";'>"
                                        : "<span>"))
                                        + (feral && !owner.getFeralAttributes().isSizeHeight()
//										?"Length: [unit.sizeShort(" + (owner.getHeightValue() + owner.getLegTailLength(false))+ ")]</span>"
                                        ? "Length: [unit.sizeShort(" + (owner.getHeightValue()) + ")]</span>"
                                        : "Height: [unit.sizeShort(" + owner.getHeightValue() + ")]</span>")));


                        // LESSER:
                        if (owner.isFeral() && !owner.getFeralAttributes().isArmsOrWingsPresent() && owner.getLegConfiguration() != LegConfiguration.AVIAN) {
                            tooltipSB.append(getEmptyBodyPartDiv("Arms", "None"));
                        } else {
                            tooltipSB.append(getBodyPartDiv(owner, Util.capitaliseSentence(Util.intToString(owner.getArmRows() * 2)) + " arms", owner.getArmRace(), owner.getArmCovering(), owner.isArmFeral()));
                        }
                        switch (owner.getLegConfiguration()) {
                            case ARACHNID:
                                tooltipSB.append(getBodyPartDiv(owner, Util.capitaliseSentence(Util.intToString(owner.getLegCount())) + " arachnid legs", owner.getLegRace(), owner.getLegCovering(), owner.isLegFeral()));
                                break;
                            case BIPEDAL:
                            case QUADRUPEDAL:
                            case WINGED_BIPED:
                                tooltipSB.append(getBodyPartDiv(owner, Util.capitaliseSentence(Util.intToString(owner.getLegCount())) + " " + owner.getFootStructure().getName() + " legs", owner.getLegRace(), owner.getLegCovering(), owner.isLegFeral()));
                                break;
                            case CEPHALOPOD:
                                tooltipSB.append(getBodyPartDiv(owner, Util.capitaliseSentence(Util.intToString(owner.getLegCount())) + " tentacle-legs", owner.getLegRace(), owner.getLegCovering(), owner.isLegFeral()));
                                break;
                            case TAIL:
                                if (owner.hasStatusEffect(StatusEffect.AQUATIC_NEGATIVE)) {
                                    tooltipSB.append(getBodyPartDiv(owner, Util.capitaliseSentence(Util.intToString(owner.getLegCount())) + " " + owner.getFootStructure().getName() + " legs", owner.getLegRace(), owner.getLegCovering(), owner.isLegFeral()));
                                } else {
                                    tooltipSB.append(getBodyPartDiv(owner, "Mer-tail", owner.getLegRace(), owner.getLegCovering(), owner.isLegFeral()));
                                }
                                break;
                            case TAIL_LONG:
//								tooltipSB.append(getBodyPartDiv(owner, "Serpent-tail"+ (feral&&!owner.getFeralAttributes().isSizeHeight()?"":" (Length: "+(Units.size(owner.getLegTailLength(false)))+")"),
//										owner.getLegRace(), owner.getLegCovering(), owner.isLegFeral()));
                                tooltipSB.append(getBodyPartDiv(owner, "Serpent-tail (Length: " + (Units.size(owner.getLegTailLength(false))) + ")", owner.getLegRace(), owner.getLegCovering(), owner.isLegFeral()));
                                break;
                            case AVIAN:
                                tooltipSB.append(getBodyPartDiv(owner, Util.capitaliseSentence(Util.intToString(owner.getLegCount())) + " bird legs", owner.getLegRace(), owner.getLegCovering(), owner.isLegFeral()));
                                break;
                        }

                        // PARTIAL:
                        if (owner.getHairRawLengthValue() == 0 && owner.isFaceBaldnessNatural()) {
                            tooltipSB.append(getEmptyBodyPartDiv("Hair", "None"));
                        } else {
                            tooltipSB.append(getBodyPartDiv(owner,
                                    Util.capitaliseSentence(owner.getHairLength().getDescriptor()) + " " + owner.getHairStyle().getName(owner) + " " + owner.getHairName(), owner.getHairRace(), owner.getHairCovering(), owner.isHairFeral()));
                        }
                        if (!owner.isPlayer() && !owner.isAreaKnownByCharacter(CoverableArea.EYES, Main.game.getPlayer())) {
                            tooltipSB.append(getEmptyBodyPartDiv("Eyes", "Unknown!"));
                        } else {
                            tooltipSB.append(getBodyPartDiv(owner, Util.capitaliseSentence(Util.intToString(owner.getEyePairs() * 2)) + " eyes", owner.getEyeRace(), owner.getEyeCovering(), owner.isEyeFeral()));
                        }
                        tooltipSB.append(getBodyPartDiv(owner, "Ears", owner.getEarRace(), owner.getEarCovering(), owner.isEarFeral()));
                        tooltipSB.append(getBodyPartDiv(owner, "Tongue", owner.getTongueRace(), owner.getTongueCovering(), owner.isTongueFeral()));
                        if (owner.getHornType() != HornType.NONE) {
                            tooltipSB.append(getBodyPartDiv(owner, Util.capitaliseSentence(Util.intToString(owner.getTotalHorns())) + " " + (owner.getTotalHorns() == 1 ? owner.getHornNameSingular() : owner.getHornName()),
                                    owner.getHornRace(), owner.getHornCovering(), owner.isHornFeral()));
                        } else {
                            tooltipSB.append(getEmptyBodyPartDiv("Horns", "None"));
                        }
                        if (owner.getAntennaType() != AntennaType.NONE) {
                            //TODO might need changing if made like horn count:
                            tooltipSB.append(getBodyPartDiv(owner, Util.capitaliseSentence(Util.intToString(owner.getAntennaRows() * owner.getAntennaePerRow())) + " antennae", owner.getAntennaRace(), owner.getAntennaCovering(), owner.isAntennaFeral()));
                        } else {
                            tooltipSB.append(getEmptyBodyPartDiv("Antennae", "None"));
                        }
                        if (owner.getWingType() != WingType.NONE) {
                            tooltipSB.append(getBodyPartDiv(owner, Util.capitaliseSentence(owner.getWingSize().getName()) + " wings", owner.getWingRace(), owner.getWingCovering(), owner.isWingFeral()));
                        } else {
                            tooltipSB.append(getEmptyBodyPartDiv("Wings", "None"));
                        }
                        if (owner.getTailType() != TailType.NONE) {
                            tooltipSB.append(
                                    getBodyPartDiv(owner,
                                            Util.capitaliseSentence(Util.intToString(owner.getTailCount())) + " " + (owner.getTailGirthDescriptor()) + " tail" + (owner.getTailCount() != 1 ? "s" : ""), owner.getTailRace(), owner.getTailCovering(), owner.isTailFeral()));
                        } else {
                            tooltipSB.append(getEmptyBodyPartDiv("Tail", "None"));
                        }

                        // SEXUAL:
                        if (!owner.isPlayer() && !owner.isAreaKnownByCharacter(CoverableArea.VAGINA, Main.game.getPlayer())) {
                            if (owner.getVaginaType() == VaginaType.NONE && Main.game.getPlayer().hasTrait(Perk.OBSERVANT, true)) {
                                tooltipSB.append(getEmptyBodyPartDiv("Vagina", "None"));
                            } else {
                                tooltipSB.append(getEmptyBodyPartDiv("Vagina", "Unknown!"));
                            }
                        } else {
                            if (owner.getVaginaType() != VaginaType.NONE) {
                                tooltipSB.append(
                                        getBodyPartDiv(owner, "Vagina", owner.getVaginaRace(), owner.getVaginaCovering(), owner.isVaginaFeral(), owner.isClitorisPseudoPenis() ? "[unit.sizeShort(" + owner.getVaginaRawClitorisSizeValue() + ")] clit" : null));
                            } else {
                                tooltipSB.append(getEmptyBodyPartDiv("Vagina", "None"));
                            }
                        }

                        if (!owner.isPlayer() && !owner.isAreaKnownByCharacter(CoverableArea.PENIS, Main.game.getPlayer())) {
                            if (!owner.hasPenis() && Main.game.getPlayer().hasTrait(Perk.OBSERVANT, true)) {
                                tooltipSB.append(getEmptyBodyPartDiv("Penis", "None"));
                            } else {
                                tooltipSB.append(getEmptyBodyPartDiv("Penis", "Unknown!"));
                            }
                        } else {
                            if (owner.hasPenisIgnoreDildo()) {
                                tooltipSB.append(getBodyPartDiv(owner, "Penis", owner.getPenisRace(), owner.getPenisCovering(), owner.isPenisFeral(),
                                        "[unit.sizeShort(" + owner.getPenisRawSizeValue() + ")] long, [unit.sizeShort(" + owner.getPenisDiameter() + ")] diameter"));
                            } else if (owner.hasPenis()) {
                                tooltipSB.append(getBodyPartDiv(owner, "Penis", owner.getPenisRace(), owner.getPenisCovering(), owner.isPenisFeral(),
                                        "[unit.sizeShort(" + owner.getPenisRawSizeValue() + ")] long, [unit.sizeShort(" + owner.getPenisDiameter() + ")] diameter"));
                            } else {
                                tooltipSB.append(getEmptyBodyPartDiv("Penis", "None"));
                            }
                        }

                        if (!owner.isPlayer() && !owner.isAreaKnownByCharacter(CoverableArea.ANUS, Main.game.getPlayer())) {
                            tooltipSB.append(getEmptyBodyPartDiv("Anus", "Unknown!"));
                        } else {
                            tooltipSB.append(getBodyPartDiv(owner, "Anus", owner.getAssRace(), owner.getAssType().getAnusType().getBodyCoveringType(owner), owner.isAnusFeral()));
                        }

                        if (!owner.isPlayer() && !owner.isAreaKnownByCharacter(CoverableArea.NIPPLES, Main.game.getPlayer())) {
                            if (owner.isFeral() && !owner.getFeralAttributes().isBreastsPresent()) {
                                tooltipSB.append(getEmptyBodyPartDiv("Nipples (Breasts)", "None"));
                            } else {
                                tooltipSB.append(getEmptyBodyPartDiv("Nipples",
                                        "Unknown!",
                                        Util.capitaliseSentence(Util.intToString(owner.getBreastRows() * 2)) + " " + (owner.getBreastRawSizeValue() > 0 ? (owner.getBreastSize().getCupSizeName() + "-cup breasts") : (owner.isFeminine() ? "flat breasts" : "pecs"))));
                            }
                        } else {
                            if (owner.isFeral() && !owner.getFeralAttributes().isBreastsPresent()) {
                                tooltipSB.append(getEmptyBodyPartDiv("Nipples (Breasts)", "None"));
                            } else {
                                tooltipSB.append(getBodyPartDiv(owner, "Nipples",
                                        owner.getBreastRace(),
                                        owner.getBreastType().getNippleType().getBodyCoveringType(owner),
                                        owner.isNippleFeral(),
                                        Util.capitaliseSentence(Util.intToString(owner.getBreastRows() * 2)) + " " + (owner.getBreastRawSizeValue() > 0 ? (owner.getBreastSize().getCupSizeName() + "-cup breasts") : (owner.isFeminine() ? "flat breasts" : "pecs"))));
                            }
                        }

                        if (spinneret) {
                            if (owner.hasTailSpinneret()) {
                                tooltipSB.append(getBodyPartDiv(owner, "Spinneret",
                                        owner.getTailRace(),
                                        owner.getSpinneretCovering(),
                                        owner.isTailFeral(),
                                        ""));
                            } else {
                                tooltipSB.append(getBodyPartDiv(owner, "Spinneret",
                                        owner.getLegRace(),
                                        owner.getSpinneretCovering(),
                                        owner.isLegFeral(),
                                        ""));
                            }
                        }

                        if (crotchBreasts) {
                            if (!owner.isAreaKnownByCharacter(CoverableArea.NIPPLES_CROTCH, Main.game.getPlayer())) {
                                tooltipSB.append(getEmptyBodyPartDiv("Nipples",
                                        "Unknown!",
                                        Util.capitaliseSentence(Util.intToString(Math.max(1, owner.getBreastCrotchRows() * 2))) + " "
                                                + (owner.getBreastCrotchRawSizeValue() > 0 ? (owner.getBreastCrotchSize().getCupSizeName() + "-cup ") : "flat ")
                                                + (owner.getBreastCrotchShape() == BreastShape.UDDERS
                                                ? (owner.getBreastCrotchRows() == 0
                                                ? "udder"
                                                : "udders")
                                                : "crotch-boobs")));
                            } else {
                                tooltipSB.append(getBodyPartDiv(owner, "Nipples",
                                        owner.getBreastCrotchRace(),
                                        owner.getNippleCrotchCovering(),
                                        owner.isNippleCrotchFeral(),
                                        Util.capitaliseSentence(Util.intToString(Math.max(1, owner.getBreastCrotchRows() * 2))) + " "
                                                + (owner.getBreastCrotchRawSizeValue() > 0 ? (owner.getBreastCrotchSize().getCupSizeName() + "-cup ") : "flat ")
                                                + (owner.getBreastCrotchShape() == BreastShape.UDDERS
                                                ? (owner.getBreastCrotchRows() == 0
                                                ? "udder"
                                                : "udders")
                                                : "crotch-boobs")));
                            }
                        }

                    } else {
                        tooltipSB.append(getBodyPartDiv(owner, "Passive form", owner.getSkinRace(), owner.getTorsoCovering(), owner.isTorsoFeral()));

                        if (((Elemental) owner).getSummoner().isPlayer()) {
                            tooltipSB.append("<div class='subTitle' style='font-weight:normal; margin-top:2px; white-space:nowrap;'>");
                            if (!Main.game.isInNeutralDialogue()) {
                                tooltipSB.append("[style.italicsBad(You cannot talk to your Elemental in this scene!)]");
                            } else {
                                tooltipSB.append("[style.italicsMinorGood(Click to start talking to your Elemental!)]");
                            }
                            tooltipSB.append("</div>");
                        }
                    }

                    if (displayImage) {
                        boolean revealed = owner.isImageRevealed();
                        tooltipSB.append("</div>" + "<div style='float: left;'>" + "<img id='CHARACTER_IMAGE' style='").append(revealed ? "" : "-webkit-filter: brightness(0%);").append(" width: auto; height: auto; max-width: 300; max-height: 445; padding-top: ").append(imagePadding).append("px;' src='").append(image.getThumbnailString()).append("'/>").append(revealed ? "" : "<p style='position:absolute; top:33%; right:0; width:" + imageWidth + "; font-weight:bold; text-align:center; color:" + PresetColour.BASE_GREY.toWebHexString() + ";'>Unlocked through sex!</p>").append("</div>");
                    }
                }

                Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));

            } else {
                Main.mainController.setTooltipSize(360, 234);

                Main.mainController.setTooltipContent(UtilText.parse(
                        "<div class='title' style='color:" + attribute.getColour().toWebHexString() + ";'>" + Util.capitaliseSentence(attribute.getName()) + "</div>"

                                + "<div class='subTitle-third'>"
                                + "<b style='color:" + PresetColour.TEXT_GREY.toWebHexString() + ";'>Core</b><br/>"
                                + (owner.getBaseAttributeValue(attribute) > 0 ? "<span style='color: " + PresetColour.GENERIC_EXCELLENT.getShades()[1] + ";'>" : "<span>")
                                + Units.number(owner.getBaseAttributeValue(attribute), 1, 1)
                                + "</span>"
                                + "</div>"
                                + "<div class='subTitle-third'>"
                                + "<b style='color:"
                                + PresetColour.TEXT_GREY.toWebHexString()
                                + ";'>Bonus</b><br/>"
                                + ((owner.getBonusAttributeValue(attribute)) > 0 ? "<span style='color: "
                                + PresetColour.GENERIC_GOOD.getShades()[1]
                                + ";'>"
                                : ((owner.getBonusAttributeValue(attribute)) == 0 ? "<span style='color:"
                                + PresetColour.TEXT_GREY.toWebHexString()
                                + ";'>"
                                : "<span style='color: "
                                + PresetColour.GENERIC_BAD.getShades()[1]
                                + ";'>"))
                                + Units.number(owner.getBonusAttributeValue(attribute), 1, 1)
                                + "</span>"
                                + "</div>"
                                + "<div class='subTitle-third'>"
                                + "<b style='color:"
                                + attribute.getColour().toWebHexString() + ";'>Total</b><br/>" + Units.number(owner.getAttributeValue(attribute), 1, 1) + "</span>"
                                + "</div>"

                                + "<div class='description'>" + attribute.getDescription(owner) + "</div>"));

            }

        } else if (extraAttributes) {

            boolean elemental = owner.isElemental() && ((Elemental) owner).getSummoner().isPlayer();
            Main.mainController.setTooltipSize(400, 528 + (Main.game.isEnchantmentCapacityEnabled() ? 46 : 32) + (elemental ? 28 : 0));

            int enchantmentPointsUsed = owner.getEnchantmentPointsUsedTotal();
            tooltipSB.setLength(0);
            tooltipSB.append("<div class='subTitle'>" + "<span style='color:").append(Femininity.valueOf(owner.getFemininityValue()).getColour().toWebHexString()).append("; font-size:110%;'>").append(owner.getName(true).isEmpty()
                    ? "[npc.Race]"
                    : (owner.isPlayer() || owner.isPlayerKnowsName()
                    ? "[npc.NameFull]"
                    : "[npc.Name]")).append("</span>").append("<br/>Level ").append(owner.getLevel()).append(owner.getLevel() != owner.getTrueLevel() ? " [style.colourDisabled((" + owner.getTrueLevel() + "))]" : "").append(" <span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>| ").append(owner.isElemental()
                    ? "Elementals share their summoner's level</span>"
                    : "</span>" + owner.getExperience() + " / " + (10 * owner.getLevel()) + " xp").append("</div>");

            tooltipSB.append(Main.game.isEnchantmentCapacityEnabled()
                    ? "<div class='subTitle-half' style='padding:2px; margin:2px 1% 2px 2%; width:47%;'>"
                    + "[style.colourEnchantment(" + Util.capitaliseSentence(Attribute.ENCHANTMENT_LIMIT.getName()) + ")]<br/>"
                    + (enchantmentPointsUsed > owner.getAttributeValue(Attribute.ENCHANTMENT_LIMIT)
                    ? "[style.colourBad("
                    : (enchantmentPointsUsed == owner.getAttributeValue(Attribute.ENCHANTMENT_LIMIT)
                    ? "[style.colourGood("
                    : "[style.colourMinorGood("))
                    + enchantmentPointsUsed + ")]" + "/" + Math.round(owner.getAttributeValue(Attribute.ENCHANTMENT_LIMIT))
                    + "</div>"

                    + "<div class='subTitle-half' style='padding:2px; margin:2px 2% 2px 1%; width:47%;'>"
                    : "<div class='subTitle' style='margin:2px 1%; width:98%'>").append("[style.colourArcane(Essences)]").append(Main.game.isEnchantmentCapacityEnabled() ? "<br/>" : ": ").append(owner.getEssenceCount()).append("</div>");

            attributeTableLeft = true;

            tooltipSB.append(getAttributeDiv(owner, Attribute.HEALTH_MAXIMUM)).append(getAttributeDiv(owner, Attribute.MANA_MAXIMUM)).append(getAttributeDiv(owner, Attribute.MAJOR_PHYSIQUE)).append(getAttributeDiv(owner, Attribute.MAJOR_ARCANE)).append(getAttributeDiv(owner, Attribute.MAJOR_CORRUPTION)).append(getAttributeDiv(owner, Attribute.CRITICAL_DAMAGE)).append(getAttributeDiv(owner, Attribute.SPELL_COST_MODIFIER)).append(getAttributeDiv(owner, Attribute.DAMAGE_SPELLS)).append(getAttributeDiv(owner, Attribute.DAMAGE_UNARMED)).append(getAttributeDiv(owner, Attribute.DAMAGE_MELEE_WEAPON)).append(getAttributeDiv(owner, Attribute.DAMAGE_RANGED_WEAPON)).append(getAttributeDiv(owner, Attribute.ENERGY_SHIELDING)

                    // Header:
            ).append("<div class='subTitle-third combatValue' style='padding:2px; margin:2px 0 2px 2%; width:31.5%;'>").append("Type").append("</div>").append("<div class='subTitle-third combatValue' style='padding:2px; margin:2px 0.75%; width:31.5%;'>").append("Damage").append("</div>").append("<div class='subTitle-third combatValue' style='padding:2px; margin:2px 2% 2px 0; width:31.5%;'>").append("Shielding").append("</div>"

                    // Values:
            ).append(getAttributeTableRowDiv(owner, "Physical", Attribute.DAMAGE_PHYSICAL, Attribute.RESISTANCE_PHYSICAL)).append(getAttributeTableRowDiv(owner, "Fire", Attribute.DAMAGE_FIRE, Attribute.RESISTANCE_FIRE)).append(getAttributeTableRowDiv(owner, "Cold", Attribute.DAMAGE_ICE, Attribute.RESISTANCE_ICE)).append(getAttributeTableRowDiv(owner, "Poison", Attribute.DAMAGE_POISON, Attribute.RESISTANCE_POISON)).append(getAttributeTableRowDiv(owner, "Seduction", Attribute.DAMAGE_LUST, Attribute.RESISTANCE_LUST)).append(getAttributeDiv(owner, Attribute.FERTILITY)).append(getAttributeDiv(owner, Attribute.VIRILITY));

            if (elemental) {
                tooltipSB.append("<div class='subTitle' style='font-weight:normal; margin-top:2px; white-space:nowrap;'>");
                if (!Main.game.isInNeutralDialogue()) {
                    tooltipSB.append("[style.italicsBad(You cannot talk to your Elemental in this scene!)]");
                } else {
                    tooltipSB.append("[style.italicsMinorGood(Click to start talking to your Elemental!)]");
                }
                tooltipSB.append("</div>");
            }

            Main.mainController.setTooltipContent(UtilText.parse(owner, tooltipSB.toString()));

        } else if (weather) {

            Main.mainController.setTooltipSize(360, 100);

            tooltipSB.setLength(0);
            int minutes = Math.max(1, Main.game.getWeatherTimeRemainingInSeconds() / 60);
            int hours = minutes / 60;
            tooltipSB.append("<div class='title'>" + "<b style='color:").append(Main.game.getCurrentWeather().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(Main.game.getCurrentWeather().getName())).append("</b>").append("</div>").append("<div class='title'><b>").append(hours > 0
                    ? hours + " hour" + (hours > 1 ? "s " : " ")
                    : "").append(minutes % 60 > 0
                    ? minutes + " minute" + (minutes > 1 ? "s " : " ")
                    : "").append("remaining").append("</b></div>");

            Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));

        } else if (protection) {

            Main.mainController.setTooltipSize(360, 100);

            tooltipSB.setLength(0);
            tooltipSB.append("<div class='title'>Protection</div>" + "<div class='subTitle'>").append(owner.isWearingCondom() ? "<span style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Wearing Condom</span>" : "<span style='color:" + PresetColour.GENERIC_BAD.toWebHexString() + ";'>No Condom</span>").append("</div>");

            Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));

        } else if (copyInformation) {

            Main.mainController.setTooltipSize(360, 170);

            tooltipSB.setLength(0);
            tooltipSB.append("<div class='subTitle'>"
//					+(Main.game.getCurrentDialogueNode().getLabel() == "" || Main.game.getCurrentDialogueNode().getLabel() == null ? "-" : Main.game.getCurrentDialogueNode().getLabel())
                    + "Copy Scene" + "</div>" + "<div class='description'>" + "Click to copy the currently displayed dialogue to your clipboard.<br/><br/>" + "This scene was written by <b style='color:").append(PresetColour.ANDROGYNOUS.toWebHexString()).append(";'>").append(Main.game.getCurrentDialogueNode().getAuthor()).append("</b></div>");

            Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));

        } else if (concealedSlot != null) {
            Map<InventorySlot, List<AbstractClothing>> concealedSlots = RenderingEngine.getCharacterToRender().getInventorySlotsConcealed(Main.game.getPlayer());

            List<AbstractClothing> clothingVisible = concealedSlots.get(concealedSlot).stream().filter(clothing -> !concealedSlots.containsKey(clothing.getSlotEquippedTo())).collect(Collectors.toList());

            Main.mainController.setTooltipSize(360, 175);

            Main.mainController.setTooltipContent(UtilText.parse(
                    "<div class='title'>" + Util.capitaliseSentence(concealedSlot.getName()) + " - [style.boldBad(Concealed!)]</div>"
                            + "<div class='description'>"
                            + UtilText.parse(RenderingEngine.getCharacterToRender(),
                            (concealedSlots.get(concealedSlot).isEmpty()
                                    ? "Due to [npc.namePos] position, this slot is currently hidden from view!"
                                    : (clothingVisible.isEmpty()
                                    ? "This slot is currently hidden from view by items of [npc.namePos] clothing that you cannot see!"
                                    : "This slot is currently hidden from view by [npc.namePos] <b>" + Util.clothesToStringList(clothingVisible, false) + "</b>.")))
                            + "</div>"));

        } else if (slaveJob != null) {//TODO
            int yIncrease = 0;

            // Title:
            tooltipSB.setLength(0);
            tooltipSB.append("<div class='title'>").append(Util.capitaliseSentence(slaveJob.getName(owner))).append("</div>");

            tooltipSB.append("<div class='description' style='height:28px; text-align:center;'>" + "[style.boldStamina(Hourly Stamina Cost:)]").append(slaveJob.getHourlyStaminaDrain() > 0
                    ? " [style.boldBad("
                    : " [style.boldGood(").append(slaveJob.getHourlyStaminaDrain()).append(")]").append("</div>");

            tooltipSB.append("<div class='description' style='height:64px'>");
            tooltipSB.append(slaveJob.getDescription());
            if (slaveJob == SlaveJob.IDLE) {
                tooltipSB.append("<br/>");
                tooltipSB.append("The idle hours in which this slave will choose to sleep will be marked with [style.colourSleep(zzZ)].");
            }
            tooltipSB.append("</div>");

            for (SlaveJobFlag flag : slaveJob.getFlags()) {
                tooltipSB.append("<div class='description' style='height:48px'>" + "<b style='color:").append(flag.getColour().toWebHexString()).append(";'>").append(flag.getName()).append(":</b> ").append(flag.getDescription()).append("</div>");
                yIncrease++;
            }

            Main.mainController.setTooltipSize(360, 172 + (yIncrease * (48 + 8)));

            Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));

        } else if (loadedEnchantment != null) {
            int yIncrease = 0;

            // Title:
            tooltipSB.setLength(0);
            tooltipSB.append("<div class='title'>").append(Util.capitaliseSentence(loadedEnchantment.getName())).append("</div>");

            if (loadedEnchantment.isSuitableItemAvailable()) {
                tooltipSB.append("<div class='subTitle' style='color:").append(PresetColour.GENERIC_GOOD.toWebHexString()).append(";'>Suitable item in inventory</div>");
            } else {
                tooltipSB.append("<div class='subTitle' style='color:").append(PresetColour.GENERIC_BAD.toWebHexString()).append(";'>No suitable item in inventory</div>");
            }

            // Attribute modifiers:
            tooltipSB.append("<div class='subTitle-picture'>");
            int i = 0;
            for (ItemEffect ie : loadedEnchantment.getEffects()) {
                for (String s : ie.getEffectsDescription(Main.game.getPlayer(), Main.game.getPlayer())) {
                    tooltipSB.append(i != 0 ? "<br/>" : "").append(s);
                    yIncrease++;
                    if (UtilText.parse(s).replaceAll("<.*?>", "").length() > 32) { // Yes, this is terrible...
                        yIncrease++;
                    }
                }
                i++;
            }
            if (yIncrease >= 5) {
                yIncrease -= 5;
            } else {
                yIncrease = 0;
            }
            tooltipSB.append("</div>");

            // Picture:
            tooltipSB.append("<div class='picture'>").append(loadedEnchantment.getSVGString()).append("</div>");

            Main.mainController.setTooltipSize(360, 208 + (yIncrease > 0 ? 4 : 0) + (yIncrease * LINE_HEIGHT));

            Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));


        } else if (cell != null) {

            Set<NPC> charactersPresent = new HashSet<>(Main.game.getCharactersPresent(cell));
            if (!cell.equals(Main.game.getWorlds().get(WorldType.DOMINION).getCell(0, 0))) { // Override as NPCs had their home placed here... Add a version catch?
                charactersPresent.addAll(Main.game.getCharactersTreatingCellAsHome(cell));
            }

            boolean libraryMap = Main.game.getCurrentDialogueNode() == Library.DOMINION_MAP;

            boolean teleport = !libraryMap && Main.game.getPlayer().hasSpell(Spell.TELEPORT);

            int yIncrease = 0;
            StringBuilder charactersPresentDescription = new StringBuilder();
            StringBuilder teleportingDescription = new StringBuilder();
            if (!libraryMap) {
                if (!charactersPresent.isEmpty()) {
                    for (NPC character : charactersPresent) {
                        yIncrease++;
                        charactersPresentDescription.append(Main.game.getCharactersPresent(cell).contains(character)
                                ? character.getName("The")
                                : "[style.colourDisabled(" + character.getName("The") + ")]").append(": ").append(character.isRaceConcealed() ? "[style.colourDisabled(Unknown race!)]" : UtilText.parse(character, "[npc.FullRace(true)]")).append("<br/>");
                    }
                }
                if (teleport) {
                    if (cell.getType().getTeleportPermissions().isIncoming() && cell.getPlace().getPlaceType().getTeleportPermissions().isIncoming()) {
                        teleportingDescription.append("It [style.colourGood(is possible)] to [style.colourArcane(teleport)] into this tile!");
                    } else {
                        teleportingDescription.append("It [style.colourBad(is not possible)] to [style.colourArcane(teleport)] into this tile!");
                    }
                    if (cell.getType().getTeleportPermissions().isOutgoing() && cell.getPlace().getPlaceType().getTeleportPermissions().isOutgoing()) {
                        teleportingDescription.append("<br/>It [style.colourGood(is possible)] to [style.colourArcane(teleport)] out of this tile!");
                    } else {
                        teleportingDescription.append("<br/>It [style.colourBad(is not possible)] to [style.colourArcane(teleport)] out of this tile!");
                    }
                }
            }


            Main.mainController.setTooltipSize(360, 175 + (yIncrease > 0 ? 32 : 0) + (teleport ? 8 + 48 : 0) + (yIncrease * LINE_HEIGHT));

            String tooltipDesc = cell.getPlace().getPlaceType().getTooltipDescription();

            Main.mainController.setTooltipContent(UtilText.parse(
                    "<div class='title'>" + Util.capitaliseSentence(cell.getPlaceName()) + "</div>"
                            + "<div class='description'>"
                            + (tooltipDesc == null || tooltipDesc.isEmpty()
                            ? ""
                            : tooltipDesc + "<br/>")
                            + (cell.getPlace().getPlaceType().isDangerous()
                            ? "This is a [style.italicsBad(dangerous)] area!"
                            : "This is a [style.italicsGood(safe)] area.")
                            + "</div>"
                            + (yIncrease > 0
                            ? "<div class='description' style='height:" + (24 + yIncrease * LINE_HEIGHT) + "px;'>" + charactersPresentDescription + "</div>"
                            : "")
                            + (teleport
                            ? "<div class='description' style='height:48px; text-align:center;'>" + teleportingDescription + "</div>"
                            : "")));

        } else if (moneyTransferPercentage > 0) {

            if (InventoryDialogue.getNPCInventoryInteraction() == InventoryInteraction.FULL_MANAGEMENT
                    && owner != null ? owner.getMoney() > 0 : Main.game.getPlayerCell().getInventory().getMoney() > 0) {
                Main.mainController.setTooltipSize(360, 112);
            } else {
                Main.mainController.setTooltipSize(360, 96);
            }
            tooltipSB.setLength(0);

            String percentageTransfer;
            int transferAmount;

            if (this.moneyTransferPercentage == 1) {
                tooltipSB.append("<div class='title'>[style.colourMinorGood(Small Flames Transfer)]</div>");
                percentageTransfer = "[style.colourMinorGood(" + moneyTransferPercentage + "%)]";
            } else if (this.moneyTransferPercentage == 10) {
                tooltipSB.append("<div class='title'>[style.colourGood(Flames Transfer)]</div>");
                percentageTransfer = "[style.colourGood(" + moneyTransferPercentage + "%)]";
            } else {
                tooltipSB.append("<div class='title'>[style.colourExcellent(Total Flames Transfer)]</div>");
                percentageTransfer = "[style.colourExcellent(" + moneyTransferPercentage + "%)]";
            }

            if (InventoryDialogue.getNPCInventoryInteraction() != InventoryInteraction.FULL_MANAGEMENT) {
                tooltipSB.append("<div class='subtitle'>"
                        + "[style.italicsBad(Flame transfer not available in this interaction!)]"
                        + "</div>");

            } else if (owner == null) {
                transferAmount = (int) Math.max(1, Main.game.getPlayerCell().getInventory().getMoney() * (moneyTransferPercentage / 100f));
                tooltipSB.append("<div class='subtitle'>").append(Main.game.getPlayerCell().getInventory().getMoney() == 0
                        ? "[style.italicsBad(There are no flames in this area...)]"
                        : UtilText.parse(moneyTransferTarget,
                        "Pick up " + percentageTransfer + " of the flames in this area:<br/> ")
                        + UtilText.formatAsMoney(transferAmount, "i")).append("</div>");

            } else if (owner.isPlayer()) {
                transferAmount = (int) Math.max(1, owner.getMoney() * (moneyTransferPercentage / 100f));
                tooltipSB.append("<div class='subtitle'>").append(owner.getMoney() == 0
                        ? "[style.italicsBad(You do not have any flames, so cannot transfer any money...)]"
                        : ((moneyTransferTarget == null
                        ? (Main.game.getPlayerCell().getPlace().isItemsDisappear()
                        ? "[style.colourBad(Abandon)] " + percentageTransfer + " of your flames in this area:<br/> "
                        : "[style.colourGood(Safely store)] " + percentageTransfer + " of your flames in this area:<br/> ")
                        : UtilText.parse(moneyTransferTarget,
                        "Transfer " + percentageTransfer + " of your flames to [npc.name]:<br/> "))
                        + UtilText.formatAsMoney(transferAmount, "i"))).append("</div>");

            } else {
                transferAmount = (int) Math.max(1, owner.getMoney() * (moneyTransferPercentage / 100f));
                tooltipSB.append("<div class='subtitle'>").append(UtilText.parse(owner,
                        (owner.getMoney() == 0
                                ? "[style.italicsBad([npc.Name] does not have any flames...)]"
                                : "Take " + percentageTransfer + " of [npc.namePos] flames:<br/> "
                                + UtilText.formatAsMoney(transferAmount, "i")))).append("</div>");
            }

            Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));

        } else if (loadedBody != null) {
            boolean feral = loadedBody.isFeral();
            boolean crotchBreasts = loadedBody.hasBreastsCrotch();
            boolean spinneret = loadedBody.hasSpinneret();

            int crotchBreastAddition = crotchBreasts ? 24 : 0;
            int spinneretAddition = spinneret ? 24 : 0;

            int[] dimensions = new int[]{419, (508 + crotchBreastAddition + spinneretAddition)};

            Main.mainController.setTooltipSize(dimensions[0], dimensions[1]);

            tooltipSB.setLength(0);
            tooltipSB.append("<div class='title' style='color:").append(loadedBody.getRace().getColour().toWebHexString()).append(";'>").append(loadedBody.getRaceStage().getName() != ""
                    ? "<b style='color:" + loadedBody.getRaceStage().getColour().toWebHexString() + ";'>" + Util.capitaliseSentence(loadedBody.getRaceStage().getName()) + "</b> "
                    : "").append("<b style='color:").append(loadedBody.getSubspecies().getColour(null).toWebHexString()).append(";'>").append(loadedBody.isFeminine()
                    ? Util.capitaliseSentence(loadedBody.getSubspecies().getSingularFemaleName(loadedBody))
                    : Util.capitaliseSentence(loadedBody.getSubspecies().getSingularMaleName(loadedBody))).append("</b>").append("</div>");


            // GREATER:
            AbstractBodyCoveringType covType = loadedBody.getFace().getBodyCoveringType(loadedBody);
            if (loadedBody.getCovering(covType, true).getPattern() == CoveringPattern.FRECKLED_FACE) {
                Covering c = loadedBody.getCovering(covType, true);
                tooltipSB.append(getBodyPartDiv(loadedBody, "Face", loadedBody.getFace(), null,
                        new Covering(covType,
                                CoveringPattern.FRECKLED,
                                c.getModifier(),
                                c.getPrimaryColour(),
                                c.isPrimaryGlowing(),
                                c.getSecondaryColour(),
                                c.isSecondaryGlowing())));

            } else {
                tooltipSB.append(getBodyPartDiv(loadedBody, "Face", loadedBody.getFace()));
            }

            tooltipSB.append(getBodyPartDiv(loadedBody, "Torso", loadedBody.getTorso(),
                    "<span>"
                            + (feral && !loadedBody.getSubspecies().getFeralAttributes(loadedBody).isSizeHeight()
                            ? "Length: [unit.sizeShort(" + (loadedBody.getHeightValue()) + ")]</span>"
                            : "Height: [unit.sizeShort(" + loadedBody.getHeightValue() + ")]</span>")));


            // LESSER:
            if (feral && !loadedBody.getSubspecies().getFeralAttributes(loadedBody).isArmsOrWingsPresent() && loadedBody.getLegConfiguration() != LegConfiguration.AVIAN) {
                tooltipSB.append(getEmptyBodyPartDiv("Arms", "None"));
            } else {
                tooltipSB.append(getBodyPartDiv(loadedBody, Util.capitaliseSentence(Util.intToString(loadedBody.getArm().getArmRows() * 2)) + " arms", loadedBody.getArm()));
            }
            switch (loadedBody.getLegConfiguration()) {
                case ARACHNID:
                    tooltipSB.append(getBodyPartDiv(loadedBody, Util.capitaliseSentence(Util.intToString(loadedBody.getLeg().getLegConfiguration().getNumberOfLegs())) + " arachnid legs", loadedBody.getLeg()));
                    break;
                case BIPEDAL:
                case QUADRUPEDAL:
                case WINGED_BIPED:
                    tooltipSB.append(getBodyPartDiv(loadedBody, Util.capitaliseSentence(
                            Util.intToString(loadedBody.getLeg().getLegConfiguration().getNumberOfLegs())) + " " + loadedBody.getLeg().getFootStructure().getName() + " legs", loadedBody.getLeg()));
                    break;
                case CEPHALOPOD:
                    tooltipSB.append(getBodyPartDiv(loadedBody, Util.capitaliseSentence(Util.intToString(loadedBody.getLeg().getLegConfiguration().getNumberOfLegs())) + " tentacle-legs", loadedBody.getLeg()));
                    break;
                case TAIL:
                    tooltipSB.append(getBodyPartDiv(loadedBody, "Mer-tail", loadedBody.getLeg()));
                    break;
                case TAIL_LONG:
                    tooltipSB.append(getBodyPartDiv(loadedBody, "Serpent-tail (Length: " + (Units.size(loadedBody.getLeg().getLength(loadedBody))) + ")", loadedBody.getLeg()));
                    break;
                case AVIAN:
                    tooltipSB.append(getBodyPartDiv(loadedBody, Util.capitaliseSentence(Util.intToString(loadedBody.getLeg().getLegConfiguration().getNumberOfLegs())) + " bird legs", loadedBody.getLeg()));
                    break;
            }

            // PARTIAL:
            if (loadedBody.getHair().getRawLengthValue() == 0 && loadedBody.getFace().isBaldnessNatural()) {
                tooltipSB.append(getEmptyBodyPartDiv("Hair", "None"));
            } else {
                tooltipSB.append(getBodyPartDiv(loadedBody, Util.capitaliseSentence(
                        loadedBody.getHair().getLength().getDescriptor()) + " " + loadedBody.getHair().getStyle().getName(loadedBody) + " " + loadedBody.getHair().getName(owner), loadedBody.getHair()));
            }
            tooltipSB.append(getBodyPartDiv(loadedBody, Util.capitaliseSentence(Util.intToString(loadedBody.getEye().getEyePairs() * 2)) + " eyes", loadedBody.getEye()));
            tooltipSB.append(getBodyPartDiv(loadedBody, "Ears", loadedBody.getEar()));
            tooltipSB.append(getBodyPartDiv(loadedBody, "Tongue", loadedBody.getFace().getTongue()));
            if (loadedBody.getHornType() != HornType.NONE) {
                tooltipSB.append(getBodyPartDiv(loadedBody, Util.capitaliseSentence(Util.intToString(loadedBody.getHorn().getTotalHorns())) + " " + loadedBody.getHorn().getName(owner),
                        loadedBody.getHorn()));
            } else {
                tooltipSB.append(getEmptyBodyPartDiv("Horns", "None"));
            }
            if (loadedBody.getAntenna().getType() != AntennaType.NONE) {
                tooltipSB.append(getBodyPartDiv(loadedBody, Util.capitaliseSentence(Util.intToString(loadedBody.getAntenna().getTotalAntennae())) + " antennae", loadedBody.getAntenna()));
            } else {
                tooltipSB.append(getEmptyBodyPartDiv("Antennae", "None"));
            }
            if (loadedBody.getWingType() != WingType.NONE) {
                tooltipSB.append(getBodyPartDiv(loadedBody, Util.capitaliseSentence(loadedBody.getWing().getSize().getName()) + " wings", loadedBody.getWing()));
            } else {
                tooltipSB.append(getEmptyBodyPartDiv("Wings", "None"));
            }
            if (loadedBody.getTailType() != TailType.NONE) {
                tooltipSB.append(
                        getBodyPartDiv(loadedBody,
                                Util.capitaliseSentence(
                                        Util.intToString(loadedBody.getTail().getTailCount()))
                                        + " " + (loadedBody.getTail().getType().getGirthDescriptor(loadedBody)) + " tail" + (loadedBody.getTail().getTailCount() != 1 ? "s" : ""), loadedBody.getTail()));
            } else {
                tooltipSB.append(getEmptyBodyPartDiv("Tail", "None"));
            }

            // SEXUAL:
            if (loadedBody.getVaginaType() != VaginaType.NONE) {
                tooltipSB.append(getBodyPartDiv(loadedBody, "Vagina", loadedBody.getVagina(),
                        loadedBody.getVagina().getClitoris().getClitorisSize().isPseudoPenisSize() ? "[unit.sizeShort(" + loadedBody.getVagina().getClitoris().getRawClitorisSizeValue() + ")] clit" : null));
            } else {
                tooltipSB.append(getEmptyBodyPartDiv("Vagina", "None"));
            }

            if (loadedBody.hasPenisIgnoreDildo()) {
                tooltipSB.append(getBodyPartDiv(loadedBody, "Penis", loadedBody.getPenis(),
                        "[unit.sizeShort(" + loadedBody.getPenis().getRawLengthValue() + ")] long, [unit.sizeShort(" + loadedBody.getPenis().getDiameter() + ")] diameter"));
            } else if (loadedBody.hasPenis()) {
                tooltipSB.append(getBodyPartDiv(loadedBody, "Penis", loadedBody.getPenis(),
                        "[unit.sizeShort(" + loadedBody.getPenis().getRawLengthValue() + ")] long, [unit.sizeShort(" + loadedBody.getPenis().getDiameter() + ")] diameter"));
            } else {
                tooltipSB.append(getEmptyBodyPartDiv("Penis", "None"));
            }

            tooltipSB.append(getBodyPartDiv(loadedBody, "Anus", loadedBody.getAss().getAnus()));

            if (feral && !loadedBody.getSubspecies().getFeralAttributes(loadedBody).isBreastsPresent()) {
                tooltipSB.append(getEmptyBodyPartDiv("Nipples (Breasts)", "None"));
            } else {
                tooltipSB.append(getBodyPartDiv(loadedBody, "Nipples",
                        loadedBody.getBreast().getNipples(),
                        Util.capitaliseSentence(Util.intToString(loadedBody.getBreast().getRows() * 2)) + " "
                                + (loadedBody.getBreast().getRawSizeValue() > 0
                                ? (loadedBody.getBreast().getSize().getCupSizeName() + "-cup breasts")
                                : (loadedBody.isFeminine() ? "flat breasts" : "pecs"))));
            }

            if (spinneret) {
                if (loadedBody.hasTailSpinneret()) {
                    tooltipSB.append(getBodyPartDiv(loadedBody, "Spinneret",
                            loadedBody.getTail(),
                            "",
                            loadedBody.getCovering(BodyCoveringType.SPINNERET, true)));
                } else {
                    tooltipSB.append(getBodyPartDiv(loadedBody, "Spinneret",
                            loadedBody.getLeg(),
                            "",
                            loadedBody.getCovering(BodyCoveringType.SPINNERET, true)));
                }
            }

            if (crotchBreasts) {
                tooltipSB.append(getBodyPartDiv(loadedBody, "Nipples",
                        loadedBody.getBreastCrotch().getNipples(),
                        Util.capitaliseSentence(Util.intToString(Math.max(1, loadedBody.getBreastCrotch().getRows() * 2))) + " "
                                + (loadedBody.getBreastCrotch().getRawSizeValue() > 0 ? (loadedBody.getBreastCrotch().getSize().getCupSizeName() + "-cup ") : "flat ")
                                + (loadedBody.getBreastCrotch().getShape() == BreastShape.UDDERS
                                ? (loadedBody.getBreastCrotch().getRows() == 0
                                ? "udder"
                                : "udders")
                                : "crotch-boobs")));
            }

            Main.mainController.setTooltipContent(UtilText.parse(tooltipSB.toString()));


        } else { // Standard information:
            if (description == null || description.isEmpty()) {
                Main.mainController.setTooltipSize(360, 64);

                Main.mainController.setTooltipContent(UtilText.parse(
                        "<div class='title'>" + title + "</div>"));

            } else if (title == null || title.isEmpty()) {
                Main.mainController.setTooltipSize(360, descriptionHeightOverride > 0 ? descriptionHeightOverride + 64 + 32 : 200);

                Main.mainController.setTooltipContent(UtilText.parse(
                        "<div class='description' style='height:" + (descriptionHeightOverride > 0 ? (descriptionHeightOverride + 26) : "176") + "px;'>" + description + "</div>"));

            } else {
                Main.mainController.setTooltipSize(360, descriptionHeightOverride > 0 ? descriptionHeightOverride + 64 + 32 : 175);

                Main.mainController.setTooltipContent(UtilText.parse(
                        "<div class='title'>" + title + "</div>"
                                + "<div class='description' " + (descriptionHeightOverride > 0 ? "style='height:" + (descriptionHeightOverride + 26) + "px;'" : "") + ">" + description + "</div>"));
            }
        }

        TooltipUpdateThread.updateToolTip(-1, -1);
    }

    private String getBodyPartDiv(GameCharacter character, String name, AbstractRace race, AbstractBodyCoveringType covering, boolean feral) {
        return getBodyPartDiv(character, name, race, covering, feral, null);
    }

    private String getBodyPartDiv(GameCharacter character, String name, AbstractRace race, AbstractBodyCoveringType covering, boolean feral, String size) {
        return getBodyPartDiv(character, name, race, owner.getCovering(covering), feral, size);
    }

    private String getBodyPartDiv(GameCharacter character, String name, AbstractRace race, Covering covering, boolean feral, String size) {
        String raceName;
        raceName = race.getName(character.getBody(), feral);

        Colour primaryColour = covering.getPrimaryColour();
        Colour secondaryColour = covering.getSecondaryColour();
        boolean displaySecondary = covering.getPattern().isNaturalSecondColour(character);
        String coveringName = covering.getName(character);

        boolean elementalFeral = false;
        boolean passiveElemental = false;
        if (character.isElemental()) {
            elementalFeral = !((Elemental) character).getSummoner().isElementalActive();
            if (elementalFeral) {
                passiveElemental = true;
                if (((Elemental) character).getPassiveForm() == null) {
                    coveringName = "ethereal energy";
                    raceName = ((Elemental) character).getCurrentSchool().getName() + "-wisp";
                    elementalFeral = false;
                } else {
                    raceName = ((Elemental) character).getPassiveForm().getFeralName(character.getBody());
                }
            }
        }

        //  background-image:linear-gradient(to right bottom, " + primaryColour.toWebHexString() + " 50%, " + secondaryColour.toWebHexString() + " 50%);
        return "<div class='subTitle' style='font-weight:normal; text-align:" + (passiveElemental ? "center" : "left") + "; margin-top:2px; white-space: nowrap;'>"
                + "<div style='width:10px; height:16px; padding:0; margin:0;'>"
                + "<div class='colour-box' style='width:8px; height:" + (displaySecondary ? "8px; margin:0;" : "8px; margin:4px 0 0 0;") + " border-radius:2px; padding:0;"
                + (primaryColour.isMetallic()
                ? "background: repeating-linear-gradient(135deg, " + primaryColour.toWebHexString() + ", " + primaryColour.getShades()[4] + " 1px);"
                : (primaryColour.getRainbowColours() != null
                ? "background: " + primaryColour.getRainbowDiv(1) + ";"
                : "background:" + (primaryColour.getCoveringIconColour()) + ";"))
                + "'></div>"
                + (displaySecondary
                ? "<div class='colour-box' style='width:8px; height:8px; margin:0; padding:0; border-radius:2px;"
                + (secondaryColour.isMetallic()
                ? "background: repeating-linear-gradient(135deg, " + secondaryColour.toWebHexString() + ", " + secondaryColour.getShades()[4] + " 1px);"
                : (secondaryColour.getRainbowColours() != null
                ? "background: " + secondaryColour.getRainbowDiv(1) + ";"
                : "background:" + (secondaryColour.getCoveringIconColour()) + ";"))
                + "'></div>"
                : "")
                + "</div>"
                + name + (size != null && !size.isEmpty() ? " (" + size + "): " : ": ")
                + (elementalFeral || (feral && race != Race.NONE) ? "[style.colourFeral(Feral )]" : "")
                + (covering.getType() != BodyCoveringType.DILDO
                ? "<span style='color:" + race.getColour().toWebHexString() + ";'>"
                + Util.capitaliseSentence(raceName)
                : "<span style='color:" + PresetColour.BASE_PINK_DEEP.toWebHexString() + ";'>"
                + "Dildo")
                + "</span>"
                + (passiveElemental ? "<br/>" : " - ")
                + covering.getColourDescriptor(character, true, true) + " " + coveringName
                + "</div>";
    }

    private String getBodyPartDiv(Body body, String name, BodyPartInterface bodyPart) {
        return getBodyPartDiv(body, name, bodyPart, null, null);
    }

    private String getBodyPartDiv(Body body, String name, BodyPartInterface bodyPart, String size) {
        return getBodyPartDiv(body, name, bodyPart, size, null);
    }

    private String getBodyPartDiv(Body body, String name, BodyPartInterface bodyPart, String size, Covering coveringOverride) {
        String raceName;
        boolean feral = bodyPart.isFeral(owner);
        AbstractRace race = bodyPart.getType().getRace();
        raceName = race.getName(body, feral);

        Covering covering;
        if (coveringOverride != null) {
            covering = coveringOverride;
        } else {
            covering = body.getCovering(bodyPart.getBodyCoveringType(body), true);
        }

        Colour primaryColour = covering.getPrimaryColour();
        Colour secondaryColour = covering.getSecondaryColour();
        boolean displaySecondary = covering.getPattern().isNaturalSecondColour(owner);
        String coveringName = covering.getName(owner);

        //  background-image:linear-gradient(to right bottom, " + primaryColour.toWebHexString() + " 50%, " + secondaryColour.toWebHexString() + " 50%);
        return "<div class='subTitle' style='font-weight:normal; text-align:left; margin-top:2px; white-space: nowrap;'>"
                + "<div style='width:10px; height:16px; padding:0; margin:0;'>"
                + "<div class='colour-box' style='width:8px; height:" + (displaySecondary ? "8px; margin:0;" : "8px; margin:4px 0 0 0;") + " border-radius:2px; padding:0;"
                + (primaryColour.isMetallic()
                ? "background: repeating-linear-gradient(135deg, " + primaryColour.toWebHexString() + ", " + primaryColour.getShades()[4] + " 1px);"
                : (primaryColour.getRainbowColours() != null
                ? "background: " + primaryColour.getRainbowDiv(1) + ";"
                : "background:" + (primaryColour.getCoveringIconColour()) + ";"))
                + "'></div>"
                + (displaySecondary
                ? "<div class='colour-box' style='width:8px; height:8px; margin:0; padding:0; border-radius:2px;"
                + (secondaryColour.isMetallic()
                ? "background: repeating-linear-gradient(135deg, " + secondaryColour.toWebHexString() + ", " + secondaryColour.getShades()[4] + " 1px);"
                : (secondaryColour.getRainbowColours() != null
                ? "background: " + secondaryColour.getRainbowDiv(1) + ";"
                : "background:" + (secondaryColour.getCoveringIconColour()) + ";"))
                + "'></div>"
                : "")
                + "</div>"
                + name + (size != null && !size.isEmpty() ? " (" + size + "): " : ": ")
                + ((feral && race != Race.NONE) ? "[style.colourFeral(Feral )]" : "")
                + (covering.getType() != BodyCoveringType.DILDO
                ? "<span style='color:" + race.getColour().toWebHexString() + ";'>"
                + Util.capitaliseSentence(raceName)
                : "<span style='color:" + PresetColour.BASE_PINK_DEEP.toWebHexString() + ";'>"
                + "Dildo")
                + "</span>"
                + " - "
                + covering.getColourDescriptor(owner, true, true) + " " + coveringName
                + "</div>";
    }

    private String getEmptyBodyPartDiv(String name, String description) {
        return getEmptyBodyPartDiv(name, description, null);
    }

    private String getEmptyBodyPartDiv(String name, String description, String size) {
        return "<div class='subTitle' style='font-weight:normal; text-align:left; margin-top:2px; white-space: nowrap;'>"
                + name + (size != null ? " (" + size + "): " : ": ") + "<span style='color:" + PresetColour.TEXT_GREY.toWebHexString() + ";'>" + description + "</span>"
                + "</div>";
    }

    private String getAttributeDiv(GameCharacter owner, AbstractAttribute attribute) {
        float value = owner.getAttributeValue(attribute);

        String valueForDisplay;
        if (((int) value) == value) {
            valueForDisplay = String.valueOf(((int) value));
        } else {
            valueForDisplay = String.valueOf(value);
        }
        if (attribute.isInfiniteAtUpperLimit() && value >= attribute.getUpperLimit()) {
            valueForDisplay = UtilText.getInfinitySymbol(true);
        }
        if (attribute.isPercentage()) {
            valueForDisplay = (value >= 0 ? "+" : "") + valueForDisplay + "%";
        }

        attributeTableLeft = !attributeTableLeft;

        return "<div class='subTitle-half' style='padding:2px; margin:2px " + (!attributeTableLeft ? "1" : "2") + "% 2px " + (!attributeTableLeft ? "2" : "1") + "%; width:47%;'>"
                + "<span style='color:" + attribute.getColour().toWebHexString() + ";'>"
                + Util.capitaliseSentence(attribute.getName())
                + "</span>"
                + "<br/>"
                + (value > attribute.getBaseValue()
                ? "<span style='color:" + (value == attribute.getUpperLimit() ? PresetColour.GENERIC_GOOD : PresetColour.GENERIC_MINOR_GOOD).toWebHexString() + ";'>"
                : (value < attribute.getBaseValue()
                ? "<span style='color:" + (value == attribute.getLowerLimit() ? PresetColour.GENERIC_BAD : PresetColour.GENERIC_MINOR_BAD).toWebHexString() + ";'>"
                : "<span style='color:" + PresetColour.TEXT_GREY.toWebHexString() + ";'>"))
                + valueForDisplay
                + "</span>"
                + "</div>";
    }

    private String getAttributeTableRowDiv(GameCharacter owner, String type, AbstractAttribute damage, AbstractAttribute resist) {
        float damageValue = owner.getAttributeValue(damage);
        float resistValue = owner.getAttributeValue(resist);

        String damageValueForDisplay;
        if (((int) damageValue) == damageValue) {
            damageValueForDisplay = String.valueOf(((int) damageValue));
        } else {
            damageValueForDisplay = String.valueOf(damageValue);
        }
        if (damage.isInfiniteAtUpperLimit() && damageValue >= damage.getUpperLimit()) {
            damageValueForDisplay = UtilText.getInfinitySymbol(true);
        }
        if (damage.isPercentage()) {
            damageValueForDisplay = (damageValue >= 0 ? "+" : "") + damageValueForDisplay + "%";
        }

        String resistValueForDisplay;
        if (((int) resistValue) == resistValue) {
            resistValueForDisplay = String.valueOf(((int) resistValue));
        } else {
            resistValueForDisplay = String.valueOf(resistValue);
        }
        if (resist.isInfiniteAtUpperLimit() && resistValue >= resist.getUpperLimit()) {
            resistValueForDisplay = UtilText.getInfinitySymbol(true);
        }
        if (resist.isPercentage()) {
            resistValueForDisplay = (resistValue >= 0 ? "+" : "") + resistValueForDisplay + "%";
        }

        return "<div class='subTitle-third combatValue' style='padding:2px; margin:2px 0 2px 2%; width:31.5%;'>"
                + "<span style='color:" + damage.getColour().toWebHexString() + ";'>" + type + "</span>"
                + "</div>"
                + "<div class='subTitle-third combatValue' style='padding:2px; margin:2px 0.75%; width:31.5%;'>"
                + (damageValue > damage.getBaseValue()
                ? "<span style='color:" + (damageValue == damage.getUpperLimit() ? PresetColour.GENERIC_GOOD : PresetColour.GENERIC_MINOR_GOOD).toWebHexString() + ";'>"
                : (damageValue < damage.getBaseValue()
                ? "<span style='color:" + (damageValue == damage.getLowerLimit() ? PresetColour.GENERIC_BAD : PresetColour.GENERIC_MINOR_BAD).toWebHexString() + ";'>"
                : "<span style='color:" + PresetColour.TEXT_GREY.toWebHexString() + ";'>"))
                + damageValueForDisplay
                + "</span>"
                + "</div>"
                + "<div class='subTitle-third combatValue' style='padding:2px; margin:2px 2% 2px 0; width:31.5%;'>"
                + (resist == null
                ? "0.0"
                : (resistValue > 0
                ? "<span style='color:" + (resistValue == resist.getUpperLimit() ? PresetColour.GENERIC_GOOD : PresetColour.GENERIC_MINOR_GOOD).toWebHexString() + ";'>"
                : (resistValue < 0
                ? "<span style='color:" + (resistValue == resist.getLowerLimit() ? PresetColour.GENERIC_BAD : PresetColour.GENERIC_MINOR_BAD).toWebHexString() + ";'>"
                : "<span style='color:" + PresetColour.TEXT_GREY.toWebHexString() + ";'>"))
                + resistValueForDisplay
                + "</span>")
                + "</div>";
    }

    public TooltipInformationEventListener setInformation(String title, String description) {
        resetFields();
        this.title = title;
        this.description = description;
        return this;
    }

    public TooltipInformationEventListener setInformation(String title, String description, int descriptionHeightOverride) {
        setInformation(title, description);
        this.descriptionHeightOverride = descriptionHeightOverride;
        return this;
    }

    public TooltipInformationEventListener setWeather() {
        resetFields();
        weather = true;
        return this;
    }

    public TooltipInformationEventListener setExtraAttributes(GameCharacter owner) {
        resetFields();
        extraAttributes = true;
        this.owner = owner;
        return this;
    }

    public TooltipInformationEventListener setStatusEffect(AbstractStatusEffect statusEffect, GameCharacter owner) {
        resetFields();
        this.statusEffect = statusEffect;
        this.owner = owner;
        return this;
    }

    public TooltipInformationEventListener setPerk(AbstractPerk perk, GameCharacter owner) {
        resetFields();
        this.perk = perk;
        this.owner = owner;
        return this;
    }

    public TooltipInformationEventListener setFetish(AbstractFetish fetish, GameCharacter owner) {
        resetFields();
        this.fetish = fetish;
        this.owner = owner;
        return this;
    }

    public TooltipInformationEventListener setFetishExperience(AbstractFetish fetish, GameCharacter owner) {
        resetFields();
        fetishExperience = true;
        this.fetish = fetish;
        this.owner = owner;
        return this;
    }

    public TooltipInformationEventListener setFetishDesire(AbstractFetish fetish, FetishDesire desire, GameCharacter owner) {
        resetFields();
        this.desire = desire;
        this.fetish = fetish;
        this.owner = owner;
        return this;
    }

    public TooltipInformationEventListener setLevelUpPerk(int perkRow, AbstractPerk levelUpPerk, GameCharacter owner, boolean availableForSelection) {
        resetFields();
        this.levelUpPerk = levelUpPerk;
        this.perkRow = perkRow;
        this.owner = owner;
        this.availableForSelection = availableForSelection;
        return this;
    }

    public TooltipInformationEventListener setSpell(Spell spell, GameCharacter owner) {
        resetFields();
        this.spell = spell;
        this.owner = owner;
        return this;
    }

    public TooltipInformationEventListener setSpellUpgrade(SpellUpgrade spellUpgrade, GameCharacter owner) {
        resetFields();
        this.spellUpgrade = spellUpgrade;
        this.owner = owner;
        return this;
    }

    public TooltipInformationEventListener setAttribute(AbstractAttribute attribute, GameCharacter owner) {
        resetFields();
        this.attribute = attribute;
        this.owner = owner;
        return this;
    }

    public TooltipInformationEventListener setProtection(GameCharacter owner) {
        resetFields();
        this.owner = owner;
        protection = true;
        return this;
    }

    public TooltipInformationEventListener setCopyInformation() {
        resetFields();
        copyInformation = true;
        return this;
    }

    public TooltipInformationEventListener setConcealedSlot(InventorySlot concealedSlot) {
        resetFields();
        this.concealedSlot = concealedSlot;
        return this;
    }

    public TooltipInformationEventListener setLoadedEnchantment(LoadedEnchantment loadedEnchantment) {
        resetFields();
        this.loadedEnchantment = loadedEnchantment;
        return this;
    }

    public TooltipInformationEventListener setCombatMove(AbstractCombatMove move, GameCharacter owner) {
        resetFields();
        this.owner = owner;
        this.move = move;
        return this;
    }

    public TooltipInformationEventListener setCell(Cell cell) {
        resetFields();
        this.cell = cell;
        return this;
    }

    public TooltipInformationEventListener setMoneyTransferTarget(GameCharacter from, GameCharacter to, int moneyTransferPercentage) {
        resetFields();
        this.owner = from;
        this.moneyTransferTarget = to;
        this.moneyTransferPercentage = moneyTransferPercentage;
        return this;
    }

    public TooltipInformationEventListener setSlaveJob(SlaveJob slaveJob, GameCharacter owner) {
        resetFields();
        this.owner = owner;
        this.slaveJob = slaveJob;
        return this;
    }

    public TooltipInformationEventListener setLoadedBody(Body loadedBody, GameCharacter owner) {
        resetFields();
        this.owner = owner;
        this.loadedBody = loadedBody;
        return this;
    }

    private void resetFields() {
        extraAttributes = false;
        weather = false;
        owner = null;
        statusEffect = null;
        perk = null;
        fetish = null;
        fetishExperience = false;
        desire = null;
        levelUpPerk = null;
        availableForSelection = false;
        perkRow = 0;
        spell = null;
        spellUpgrade = null;
        attribute = null;
        protection = false;
        copyInformation = false;
        concealedSlot = null;
        loadedEnchantment = null;
        move = null;
        descriptionHeightOverride = 0;
        cell = null;
        moneyTransferTarget = null;
        moneyTransferPercentage = 0;
        slaveJob = null;
        loadedBody = null;
    }
}