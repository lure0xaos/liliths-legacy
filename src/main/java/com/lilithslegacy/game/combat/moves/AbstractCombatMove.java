package com.lilithslegacy.game.combat.moves;

import com.lilithslegacy.controller.xmlParsing.Element;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.attributes.Attribute;
import com.lilithslegacy.game.character.attributes.CorruptionLevel;
import com.lilithslegacy.game.character.attributes.LustLevel;
import com.lilithslegacy.game.character.effects.AbstractStatusEffect;
import com.lilithslegacy.game.character.effects.StatusEffect;
import com.lilithslegacy.game.combat.Attack;
import com.lilithslegacy.game.combat.CombatBehaviour;
import com.lilithslegacy.game.combat.DamageType;
import com.lilithslegacy.game.combat.DamageVariance;
import com.lilithslegacy.game.combat.spells.Spell;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.weapon.AbstractWeapon;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.SvgUtil;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Util.Value;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.utils.fs.FS;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A class containing logic for Combat Moves.
 *
 * @author Irbynx, Innoxia
 * @version 0.4
 * @since 0.4
 */
public abstract class AbstractCombatMove {

    private boolean mod;
    private boolean fromExternalFile;

    private CombatMoveCategory category;
    private CombatMoveType type;

    private String name;
    private String description;

    private String damageTypeString = "";
    private String baseDamageString = "";
    private String cooldownString = "";
    private String APCostString = "";
    private String blockString = "";

    private String availabilityCondition = "";
    private String availabilityDescription = "";

    private String criticalCondition = "";
    private String criticalDescription = "";

    private String movePredictionDescriptionWithTarget = "";
    private String movePredictionDescriptionNoTarget = "";

    private String performingText = "";
    private String performingCritDescText = "";
    private String performingCritText = "";

    private String weightingText = "";

    private DamageType damageType;
    private int baseDamage;
    private DamageVariance damageVariance;

    private int cooldown;
    private int APcost;
    private int equipWeighting;

    private boolean canTargetEnemies;
    private boolean canTargetAllies;
    private boolean canTargetSelf;

    private String SVGString;

    private Map<AbstractStatusEffect, Integer> statusEffects;
    private Map<AbstractStatusEffect, Integer> statusEffectsCritical;

    private Spell associatedSpell;

    public AbstractCombatMove(CombatMoveCategory category,
                              String name,
                              int cooldown,
                              int APcost,
                              CombatMoveType type,
                              DamageType damageType,
                              String pathName,
                              boolean canTargetAllies,
                              boolean canTargetEnemies,
                              boolean canTargetSelf,
                              Map<AbstractStatusEffect, Integer> statusEffects) {
        this(category, name, cooldown, APcost, 1, type, damageType, DamageVariance.NONE, pathName, null, canTargetAllies, canTargetEnemies, canTargetSelf, statusEffects, statusEffects);
    }

    public AbstractCombatMove(CombatMoveCategory category,
                              String name,
                              int cooldown,
                              int APcost,
                              CombatMoveType type,
                              DamageType damageType,
                              DamageVariance damageVariance,
                              String pathName,
                              boolean canTargetAllies,
                              boolean canTargetEnemies,
                              boolean canTargetSelf,
                              Map<AbstractStatusEffect, Integer> statusEffects) {
        this(category, name, cooldown, APcost, 1, type, damageType, damageVariance, pathName, null, canTargetAllies, canTargetEnemies, canTargetSelf, statusEffects, statusEffects);
    }

    public AbstractCombatMove(CombatMoveCategory category,
                              String name,
                              int cooldown,
                              int APcost,
                              CombatMoveType type,
                              DamageType damageType,
                              String pathName,
                              List<Colour> iconColours,
                              boolean canTargetAllies,
                              boolean canTargetEnemies,
                              boolean canTargetSelf,
                              Map<AbstractStatusEffect, Integer> statusEffects) {
        this(category, name, cooldown, APcost, 1, type, damageType, DamageVariance.NONE, pathName, iconColours, canTargetAllies, canTargetEnemies, canTargetSelf, statusEffects, statusEffects);
    }

    /**
     * Default constructor
     *
     * @param identifier    = Internal name that the game will refer to when looking up the item. Must be unique for proper searching.
     * @param name          = Human friendly name of the action
     * @param cooldown      = Amount of turns this action takes to become available. 0 means it's available always, 1 means it is available only once per turn.
     * @param APcost        = Cost in action points to perform this action.
     * @param type          = Combat action type. Used for various interactions.
     * @param statusEffects = Status effects that are applied when this move is used.
     */
    public AbstractCombatMove(CombatMoveCategory category,
                              String name,
                              int cooldown,
                              int APcost,
                              int equipWeighting,
                              CombatMoveType type,
                              DamageType damageType,
                              DamageVariance damageVariance,
                              String pathName,
                              List<Colour> iconColours,
                              boolean canTargetAllies,
                              boolean canTargetEnemies,
                              boolean canTargetSelf,
                              Map<AbstractStatusEffect, Integer> statusEffects,
                              Map<AbstractStatusEffect, Integer> statusEffectsCritical) {
        this.fromExternalFile = false;
        this.mod = false;

        this.category = category;
        this.name = name;
        this.description = "This action does nothing.";
        this.performingText = "The move is performed.";

        this.cooldown = cooldown;
        this.APcost = APcost;
        this.equipWeighting = equipWeighting;
        this.type = type;
        this.baseDamage = 0;
        this.damageType = damageType;
        this.damageVariance = damageVariance;
        this.canTargetEnemies = canTargetEnemies;
        this.canTargetAllies = canTargetAllies;
        this.canTargetSelf = canTargetSelf;

        this.statusEffects = statusEffects;
        this.statusEffectsCritical = statusEffectsCritical;

        this.associatedSpell = null;

        try {
            InputStream is = FS.newResourceAsStream("/com/lilithslegacy/res/" + pathName + ".svg");
            if (is == null) {
                System.getLogger("").log(System.Logger.Level.ERROR, "Error! CombatMove icon file does not exist (Trying to read from '" + pathName + "')!");
            }
            SVGString = Util.inputStreamToString(is);

            if (iconColours != null) {
                SVGString = SvgUtil.colourReplacement("CM", iconColours.get(0), iconColours.size() > 1 ? iconColours.get(1) : null, iconColours.size() > 2 ? iconColours.get(2) : null, SVGString);
            } else {
                SVGString = SvgUtil.colourReplacement("CM", type.getColour(), SVGString);
            }

            is.close();

        } catch (IOException e) {
            System.getLogger("").log(System.Logger.Level.ERROR, e.getMessage(), e);
        }
    }

    public AbstractCombatMove(Path XMLFile, String author, boolean mod) {
        if (Files.exists(XMLFile)) {
            try {
                DocumentBuilder fsDocumentBuilder = Main.getDocBuilder();
                Document doc = fsDocumentBuilder.parse(Files.newInputStream(XMLFile));

                // Cast magic:
                doc.getDocumentElement().normalize();

                Element coreElement = Element.getDocumentRootElement(XMLFile); // Loads the document and returns the root element - in combatMove files it's <combatMove>

                this.mod = mod;
                this.fromExternalFile = true;

                this.category = CombatMoveCategory.SPECIAL;
                if (coreElement.getOptionalFirstOf("category").isPresent()) {
                    try {
                        this.category = CombatMoveCategory.valueOf(coreElement.getMandatoryFirstOf("category").getTextContent());
                    } catch (Exception ex) {
                        System.getLogger("").log(System.Logger.Level.ERROR, "CombatMove loading error in '" + XMLFile.getFileName().toString() + "': category not recognised! (Set to SPECIAL)");
                    }
                }

                this.type = CombatMoveType.ATTACK;
                if (coreElement.getOptionalFirstOf("type").isPresent()) {
                    try {
                        this.type = CombatMoveType.valueOf(coreElement.getMandatoryFirstOf("type").getTextContent());
                    } catch (Exception ex) {
                        System.getLogger("").log(System.Logger.Level.ERROR, "CombatMove loading error in '" + XMLFile.getFileName().toString() + "': type not recognised! (Set to ATTACK)");
                    }
                }

                this.damageVariance = DamageVariance.NONE;
                if (coreElement.getOptionalFirstOf("damageVariance").isPresent()) {
                    try {
                        String variance = coreElement.getMandatoryFirstOf("damageVariance").getTextContent();
                        if (!variance.isEmpty()) {
                            this.damageVariance = DamageVariance.valueOf(variance);
                        }
                    } catch (Exception ex) {
                        System.getLogger("").log(System.Logger.Level.ERROR, "CombatMove loading error in '" + XMLFile.getFileName().toString() + "': variance not recognised! (Set to NONE)");
                    }
                }

                this.equipWeighting = Integer.valueOf(coreElement.getMandatoryFirstOf("equipWeighting").getTextContent().trim());

                this.name = coreElement.getMandatoryFirstOf("name").getTextContent();
                this.description = coreElement.getMandatoryFirstOf("description").getTextContent();
                this.damageTypeString = coreElement.getMandatoryFirstOf("damageType").getTextContent();
                this.baseDamageString = coreElement.getMandatoryFirstOf("baseDamage").getTextContent();
                this.blockString = coreElement.getMandatoryFirstOf("blockAmount").getTextContent();
                this.cooldown = 1; // Backup in case cooldownString fails to load
                this.cooldownString = coreElement.getMandatoryFirstOf("cooldown").getTextContent();
                this.APcost = 1; // Backup in case APCostString fails to load
                this.APCostString = coreElement.getMandatoryFirstOf("APcost").getTextContent();

                this.canTargetAllies = Boolean.valueOf(coreElement.getMandatoryFirstOf("canTargetAllies").getTextContent().trim());
                this.canTargetEnemies = Boolean.valueOf(coreElement.getMandatoryFirstOf("canTargetEnemies").getTextContent().trim());
                this.canTargetSelf = Boolean.valueOf(coreElement.getMandatoryFirstOf("canTargetSelf").getTextContent().trim());


                Path path = XMLFile.getParent();
                String pathName = path.resolve(coreElement.getMandatoryFirstOf("imageName").getTextContent().trim()).toString();
                SVGString = null;

                Colour colourShade = PresetColour.getColourFromId(coreElement.getMandatoryFirstOf("colourPrimary").getTextContent());
                Colour colourShadeSecondary = null;
                if (coreElement.getOptionalFirstOf("colourSecondary").isPresent() && !coreElement.getMandatoryFirstOf("colourSecondary").getTextContent().isEmpty()) {
                    colourShadeSecondary = PresetColour.getColourFromId(coreElement.getMandatoryFirstOf("colourSecondary").getTextContent());
                }
                Colour colourShadeTertiary = null;
                if (coreElement.getOptionalFirstOf("colourTertiary").isPresent() && !coreElement.getMandatoryFirstOf("colourTertiary").getTextContent().isEmpty()) {
                    colourShadeTertiary = PresetColour.getColourFromId(coreElement.getMandatoryFirstOf("colourTertiary").getTextContent());
                }
                List<Colour> colourShades = Util.newArrayListOfValues(colourShade, colourShadeSecondary, colourShadeTertiary);


                try {
                    List<String> lines = Files.readAllLines(FS.newPath(pathName));
                    StringBuilder sb = new StringBuilder();
                    for (String line : lines) {
                        sb.append(line);
                    }
                    SVGString = sb.toString();
                    SVGString = SvgUtil.colourReplacement(this.getIdentifier(), colourShades, null, SVGString);

                } catch (IOException e) {
                    System.getLogger("").log(System.Logger.Level.ERROR, e.getMessage(), e);
                }

                this.statusEffects = new HashMap<>();
                this.statusEffectsCritical = new HashMap<>();
                if (coreElement.getOptionalFirstOf("statusEffects").isPresent()) {
                    for (Element e : coreElement.getMandatoryFirstOf("statusEffects").getAllOf("effect")) {
                        try {
                            int length = Integer.valueOf(e.getAttribute("turnLength"));
                            AbstractStatusEffect se = StatusEffect.getStatusEffectFromId(e.getTextContent());
                            if (Boolean.valueOf(e.getAttribute("onCrit"))) {
                                statusEffectsCritical.put(se, length);
                            } else {
                                statusEffects.put(se, length);
                            }
                        } catch (Exception ex) {
                            System.getLogger("").log(System.Logger.Level.ERROR, ex.getMessage(), ex);
                        }
                    }
                }

                this.availabilityCondition = coreElement.getMandatoryFirstOf("availabilityCondition").getTextContent();
                this.availabilityDescription = coreElement.getMandatoryFirstOf("availabilityDescription").getTextContent();

                if (coreElement.getOptionalFirstOf("weighting").isPresent()) {
                    this.weightingText = coreElement.getMandatoryFirstOf("weighting").getTextContent();
                } else {
                    this.weightingText = "";
                }
                this.criticalCondition = coreElement.getMandatoryFirstOf("criticalCondition").getTextContent();
                this.criticalDescription = coreElement.getMandatoryFirstOf("criticalDescription").getTextContent();
                this.movePredictionDescriptionWithTarget = coreElement.getMandatoryFirstOf("movePredictionDescriptionWithTarget").getTextContent();
                this.movePredictionDescriptionNoTarget = coreElement.getMandatoryFirstOf("movePredictionDescriptionNoTarget").getTextContent();

                this.performingText = coreElement.getMandatoryFirstOf("performMove").getMandatoryFirstOf("execute").getTextContent();
                this.performingCritDescText = coreElement.getMandatoryFirstOf("performMove").getMandatoryFirstOf("critDescription").getTextContent();
                this.performingCritText = coreElement.getMandatoryFirstOf("performMove").getMandatoryFirstOf("critEffectDescription").getTextContent();

            } catch (Exception ex) {
                System.getLogger("").log(System.Logger.Level.ERROR, ex.getMessage(), ex);
                System.getLogger("").log(System.Logger.Level.ERROR, "SetBonus was unable to be loaded from file! (" + XMLFile.getFileName().toString() + ")\n" + ex);
            }
        }
    }

    /**
     * @return A standard formatting of the damage.
     */
    public static String getFormattedDamage(DamageType damageType, int damage, GameCharacter target, boolean damageHasBeenApplied, boolean targetWasAtMaximumLust) {
        if (target != null && damageType == DamageType.LUST && targetWasAtMaximumLust) {
            damageType = DamageType.HEALTH;
            if (damageHasBeenApplied) {
                damage /= 2;
            }
            return "<span style='color:" + damageType.getMultiplierAttribute().getColour().toWebHexString() + ";'>" + damage * 2 + " " + damageType.getName() + "</span>"
                    + " and <span style='color:" + PresetColour.DAMAGE_TYPE_MANA.toWebHexString() + ";'>" + damage + " " + Attribute.MANA_MAXIMUM.getName() + "</span>";
        }
        return "<span style='color:" + damageType.getMultiplierAttribute().getColour().toWebHexString() + ";'>" + damage + " " + damageType.getName() + "</span>";
    }

    protected static String getFormattedDamageRange(GameCharacter attacker, GameCharacter defender, DamageType damageType, Attack attackType, AbstractWeapon weapon, boolean isCritical) {
        return getFormattedDamageRange(attacker, defender, damageType, attackType, weapon, isCritical, 1);
    }

    /**
     * @return A standard formatting of the damage range.
     */
    protected static String getFormattedDamageRange(GameCharacter attacker, GameCharacter defender, DamageType damageType, Attack attackType, AbstractWeapon weapon, boolean isCritical, float multiplier) {
        return "<span style='color:" + damageType.getMultiplierAttribute().getColour().toWebHexString() + ";'>"
                + Attack.applyFinalDamageModifiers(attacker, defender, Attack.getMinimumDamage(attacker, defender, attackType, weapon) * multiplier, isCritical)
                + "-"
                + Attack.applyFinalDamageModifiers(attacker, defender, Attack.getMaximumDamage(attacker, defender, attackType, weapon) * multiplier, isCritical)
                + " "
                + damageType.getName()
                + "</span>";
    }

    /**
     * @return A standard formatting of an attack's outcome.
     */
    protected static String formatAttackOutcome(GameCharacter source, GameCharacter target, String attackText, String attackEffectText, String criticalText, String criticalEffectText) {
        StringBuilder sb = new StringBuilder();

        sb.append("<p><i>").append(attackText).append("</i></p>");
        sb.append(attackEffectText);

        if (criticalText != null) {
            sb.append("<p>" + "<span style='color:").append(PresetColour.CRIT.toWebHexString()).append(";'>Critical</span>: <i>").append(criticalEffectText).append("</i>").append("</p>");
        }

        return UtilText.parse(source, target, sb.toString());
    }

    /**
     * Returns true based on user settings on how often should the AI make "mistakes" and select actions irrationally.
     *
     * @return
     */
    public static boolean shouldBlunder() {
        return Math.random() <= Main.getProperties().AIblunderRate;
    }

    protected boolean isTargetAtMaximumLust(GameCharacter target) {
        return target != null && target.hasStatusEffect(StatusEffect.DESPERATE_FOR_SEX);
    }

    /**
     * Returns weight of the action.
     * Used in calculations for AI to pick certain actions.
     * For every unspent AP, AI will try to select an action out of the available ones.
     * The AI will then pick the action with highest weight.
     * Randomness of action selection should be in the weight values itself!
     *
     * @param source  Character that uses the weight function.
     * @param enemies Enemies of the character
     * @param allies  Allies of the character
     * @return Weight of the action. 1.0 is the expected normal weight; weigh the actions accordingly.
     */
    public float getWeight(GameCharacter source, List<GameCharacter> enemies, List<GameCharacter> allies) {
        if (isCanTargetAllies() && !isCanTargetSelf() && !isCanTargetEnemies() && allies.isEmpty()) { //TODO test?
            return 0.0f;
        }
        if (shouldBlunder()) {
            return (float) (Math.random()) - 0.2f * source.getSelectedMovesByType(type);
        }
        if (weightingText != null && !weightingText.isEmpty()) {
            float maxWeight = 0.0f;
            for (GameCharacter character : Main.combat.getAllCombatants(true)) {
                if ((isCanTargetSelf() && character.equals(source))
                        || (isCanTargetAllies() && character.isCombatAlly(source))
                        || (isCanTargetEnemies() && character.isCombatEnemy(source))) {
                    float weight = Float.valueOf(UtilText.parse(source, character, weightingText).trim());
                    if (weight > maxWeight) {
                        maxWeight = weight;
                    }
                }
            }
            return maxWeight;
        }
        int behaviourMultiplier = 1;
        // Trying to figure out best use cases
        switch (type) {
            default:
                return (float) (Math.random()) - 0.2f * source.getSelectedMovesByType(type); // Other types are too nuanced in themselves to have a broad weight generation apply to them
            case ATTACK:
                if (source.getCombatBehaviour() == CombatBehaviour.ATTACK) {
                    behaviourMultiplier = 2;
                }
                for (GameCharacter character : enemies) {
                    if (character.getHealthPercentage() < 0.2) {
                        return (1.1f * behaviourMultiplier) + 0.2f * (float) (Math.random()) - 0.2f * source.getSelectedMovesByType(type); // If the enemy is low on health, chances to attack increase
                    }
                }
                return (0.8f * behaviourMultiplier) + 0.2f * (float) (Math.random()) - 0.2f * source.getSelectedMovesByType(type); // Attacks aren't sophisticated

            case DEFEND:
                if (source.getCombatBehaviour() == CombatBehaviour.DEFEND) {
                    behaviourMultiplier = 2;
                }
                if (source.getHealthPercentage() < 0.2) {
                    return (1.0f * behaviourMultiplier) + 0.5f * (float) (Math.random()) - 0.2f * source.getSelectedMovesByType(type);
                }
                return (0.25f * behaviourMultiplier) + 0.75f * (float) (Math.random()) - 0.2f * source.getSelectedMovesByType(type);

            case TEASE:
                if (source.getCombatBehaviour() == CombatBehaviour.SEDUCE) {
                    behaviourMultiplier = 2;
                }
                float weight = (0.8f * behaviourMultiplier) + 0.2f * (float) (Math.random()) - 0.2f * source.getSelectedMovesByType(type);
                for (GameCharacter character : enemies) {
                    if (character.getLustLevel() == LustLevel.FOUR_IMPASSIONED || character.getLustLevel() == LustLevel.FIVE_BURNING) {
                        weight += 0.2f;
                        break;
                    }
                }
                if (source.getCorruptionLevel() == CorruptionLevel.FOUR_LUSTFUL || source.getCorruptionLevel() == CorruptionLevel.FIVE_CORRUPT) {
                    weight += 0.4f;
                }
                if (!source.isAttractedTo(this.getPreferredTarget(source, enemies, allies))) {
                    weight *= 0.5f; // 50% lower chance to use tease attacks if not attracted to the enemy.
                }
                return weight; // Attacks aren't sophisticated
        }
    }

    /**
     * Returns the preferred target for the action. Prefers to aim at targets with lowest HP values if not forced to select at random. Override for custom behaviour.
     *
     * @param source  Character that uses the target function.
     * @param enemies Enemies of the character
     * @param allies  Allies of the character
     * @return Character to target with this action.
     */
    public GameCharacter getPreferredTarget(GameCharacter source, List<GameCharacter> enemies, List<GameCharacter> allies) {
        if (Main.game.isInCombat()) {
            GameCharacter preferredTarget = Main.combat.getPreferredTarget(source);
            if (preferredTarget != null && !Main.combat.isCombatantDefeated(preferredTarget)) {
                return preferredTarget;
            }
        }

        if (weightingText != null && !weightingText.isEmpty()) {
            float maxWeight = 0.0f;
            GameCharacter target = null;
            for (GameCharacter character : Main.combat.getAllCombatants(true)) {
                if ((isCanTargetSelf() && character.equals(source))
                        || (isCanTargetAllies() && character.isCombatAlly(source))
                        || (isCanTargetEnemies() && character.isCombatEnemy(source))) {
                    float weight = Float.valueOf(UtilText.parse(source, character, weightingText).trim()) * (Main.combat.isCombatantDefeated(character) ? 0 : 1);
                    if (weight > maxWeight) {
                        target = character;
                        maxWeight = weight;
                    }
                }
            }
            if (target != null) {
                return target;
            }
        }

        if (isCanTargetEnemies()) {
            if (shouldBlunder() && enemies.stream().anyMatch(enemy -> !Main.combat.isCombatantDefeated(enemy))) {
                List<GameCharacter> nonDefeatedEnemies = new ArrayList<>(enemies);
                nonDefeatedEnemies.removeIf(enemy -> Main.combat.isCombatantDefeated(enemy));
                return nonDefeatedEnemies.get(Util.random.nextInt(nonDefeatedEnemies.size()));
            } else {
                float lowestHP = -1;
                GameCharacter potentialCharacter = null;
                for (GameCharacter character : enemies) {
                    if (lowestHP == -1 || character.getHealth() < lowestHP) {
                        potentialCharacter = character;
                        lowestHP = character.getHealth();
                    }
                }
                return potentialCharacter;
            }
        }
        if (isCanTargetAllies() && !allies.isEmpty()) {
            if (shouldBlunder() && allies.stream().anyMatch(ally -> !Main.combat.isCombatantDefeated(ally))) {
                List<GameCharacter> nonDefeatedAllies = new ArrayList<>(allies);
                nonDefeatedAllies.removeIf(ally -> Main.combat.isCombatantDefeated(ally));
                return nonDefeatedAllies.get(Util.random.nextInt(nonDefeatedAllies.size()));
            } else {
                float lowestHP = -1;
                GameCharacter potentialCharacter = null;
                for (GameCharacter character : allies) {
                    if (lowestHP == -1 || character.getHealth() < lowestHP) {
                        potentialCharacter = character;
                        lowestHP = character.getHealth();
                    }
                }
                return potentialCharacter;
            }
        }
        return source;
    }

    /**
     * Gets a prediction that specifies what kind of action will be performed for the player (i.e "The catgirl will attack you for 10 damage" or "The horseboy is planning to buff his ally")
     *
     * @param turnIndex
     * @param source    Character that uses the action.
     * @param source    Target for the action.
     * @param enemies   Enemies of the character
     * @param allies    Allies of the character
     * @return The string that describes the intent of the NPC that uses this action.
     */
    public String getPrediction(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
        String parseText;
        if (target == null) {
            parseText = movePredictionDescriptionNoTarget;
        } else {
            parseText = movePredictionDescriptionWithTarget;
        }

        if (fromExternalFile) {
            boolean crit = canCrit(turnIndex, source, target, enemies, allies);
            parseText = parseText.replaceAll("isCritical", String.valueOf(crit));
            parseText = parseText.replaceAll("damageInflicted", String.valueOf(getDamage(turnIndex, source, target, crit)));
//	    	parseText = parseText.replaceAll("damageInflictedNoCrit", String.valueOf(getDamage(source, target, false)));
            parseText = parseText.replaceAll("formattedDamageInflicted", getFormattedDamage(getDamageType(turnIndex, source), getDamage(turnIndex, source, target, crit), target, false, isTargetAtMaximumLust(target)));
//	    	parseText = parseText.replaceAll("formattedDamageInflictedNoCrit", getFormattedDamage(damageType, getDamage(source, target, false), target, false, isTargetAtMaximumLust(target)));
        }

        return UtilText.parse(source, target, parseText);
    }

    /**
     * Performs the action itself. Override to get actual abilities there.
     *
     * @param source  Character that uses the action.
     * @param source  Target for the action.
     * @param enemies Enemies of the character
     * @param allies  Allies of the character
     * @return The string that describes the action that has been performed.
     */
    public String perform(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
        String parseText = performingText;

        if (fromExternalFile) {
            boolean crit = canCrit(turnIndex, source, target, enemies, allies);
            parseText = parseText.replaceAll("isCritical", String.valueOf(crit));

            DamageType damageType = getDamageType(turnIndex, source);
            boolean maxLust = isTargetAtMaximumLust(target);
            Value<String, Integer> damageValue = damageType.damageTarget(source, target, getDamage(turnIndex, source, target, crit));
            parseText = parseText.replaceAll("damageInflicted", String.valueOf(damageValue.getValue()));
            parseText = parseText.replaceAll("formattedDamageInflicted", getFormattedDamage(damageType, damageValue.getValue(), target, true, maxLust));
            parseText = parseText.replaceAll("formattedHealthDamage", damageValue.getKey());

            return formatAttackOutcome(source, target,
                    parseText,
                    damageValue.getValue() > 0
                            ? "[npc2.Name] took " + getFormattedDamage(damageType, damageValue.getValue(), target, true, maxLust) + " damage!"
                            : "",
                    crit
                            ? performingCritDescText
                            : null,
                    performingCritText);
        }

        return UtilText.parse(source, target, parseText);
    }

    /**
     * Performs the action itself. Override to get actual abilities there. This is performed during selection of the action and not during the turn. Use for blocks, AP damage/gains or disrupts.
     *
     * @param source  Character that uses the action.
     * @param source  Target for the action.
     * @param enemies Enemies of the character
     * @param allies  Allies of the character
     * @return The string that describes the action that has been performed.
     */
    public void performOnSelection(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
        // Nothing. Override it.
    }

    //TODO is this needed when the performOnDeselection() method above exists?

    /**
     * Applies the reverse of the performOnSelection() method. Override whenever performOnSelection() is overridden. This is performed during deselection of the action and not during the turn.
     *
     * @param source  Character that uses the action.
     * @param source  Target for the action.
     * @param enemies Enemies of the character
     * @param allies  Allies of the character
     * @return The string that describes the action that has been performed.
     */
    public void performOnDeselection(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
        // Nothing. Override it.
    }

    /**
     * Cancel out the action's effects if it's disrupted or cancelled via AP loss. Is called for every action in the queue for every disruption caused; non disrupted actions get reapplied.
     *
     * @param source  Character that uses the action.
     * @param source  Target for the action.
     * @param enemies Enemies of the character
     * @param allies  Allies of the character
     * @return The string that describes the action that has been performed.
     */
    public void applyDisruption(GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
        source.setRemainingAP(source.getRemainingAP() + this.getAPcost(source) * -1, enemies, allies); // Normally this is the only thing that gets adjusted on selection.
    }

    /**
     * Checks the source character to see if they will have to use the action already with a disruption.
     *
     * @param source
     */
    public boolean isAlreadyDisrupted(GameCharacter source) {
        return source.disruptionByTypeCheck(this.getType());
    }

    /**
     * Returns a string if the character has the move available to select even if they don't "own" it; for example, purity based moves are available to Pure Virgin fetishists without even unlocking them.
     * <p>
     * String contains the reason for why the move is available to them. Otherwise returns null.
     */
    public Value<Boolean, String> isAvailableFromSpecialCase(GameCharacter source) {
        if (fromExternalFile) {
            boolean condition = Boolean.valueOf(UtilText.parse(source, availabilityCondition).trim());
            return new Value<>(condition, availabilityDescription);
        }
        return null;
    }

    /**
     * Returns a string if action can't be used either due to special constraints or because of AP/cooldowns on a specified target; string specifies rejection reason. Returns null if action can be used without an issue.
     *
     * @param turnIndex The turn index in which this move is to be performed.
     * @param source    Character that uses the action.
     * @param source    Target for the action. Can be null.
     * @param enemies   Enemies of the character
     * @param allies    Allies of the character
     */
    public String isUsable(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
        if (target != null) {
            if (!canTargetSelf && source == target) {
                return "This action can't be used on yourself!";
            }

            if (!canTargetAllies && allies.contains(target) && source != target) {
                return "This action can't be used on your allies!";
            }

            if (!canTargetEnemies && enemies.contains(target)) {
                return "This action can't be used on your enemies!";
            }
        }

        if (source.getMoveCooldown(this.getIdentifier()) > 0) {
            return "This action can't be used since it is still on cooldown! " + source.getMoveCooldown(this.getIdentifier()) + " turns remaining.";
        }

        if (source.getRemainingAP() < this.getAPcost(source)) {
            return "This action can't be used since you don't have enough AP!";
        }

        return null;
    }

    public CombatMoveCategory getCategory() {
        return category;
    }

    public String getIdentifier() {
        return CombatMove.getIdFromCombatMove(this);
    }

    public int getCooldown(GameCharacter source) {
        int derivedCooldown = cooldown;

        if (fromExternalFile) {
            try {
                derivedCooldown = Integer.valueOf(UtilText.parse(source, cooldownString).trim());
            } catch (Exception ex) {
                System.getLogger("").log(System.Logger.Level.ERROR, "CombatMove loading error: cooldownString parsing not recognised! (Left as 'cooldown')");
            }
        }

        if (!source.getEquippedMoves().contains(this)) {
            return derivedCooldown + 1;
        }
        return derivedCooldown;
    }

    public int getBlock(GameCharacter source, boolean isCrit) {
        int block = 0;
        if (fromExternalFile) {
            try {
                String parseText = blockString;
                parseText = parseText.replaceAll("isCritical", String.valueOf(isCrit));
                block = Integer.valueOf(UtilText.parse(source, parseText).trim());
            } catch (Exception ex) {
                System.getLogger("").log(System.Logger.Level.ERROR, "CombatMove loading error: blockString parsing not recognised! (Set to 0)");
            }
        }
        return block;
    }

    public CombatMoveType getType() {
        return type;
    }

    public DamageType getDamageType(int turnIndex, GameCharacter source) {
        if (fromExternalFile) {
            DamageType dt = DamageType.PHYSICAL;
            try {
                dt = DamageType.valueOf(UtilText.parse(source, damageTypeString).trim());
            } catch (Exception ex) {
                System.getLogger("").log(System.Logger.Level.ERROR, "CombatMove loading error: DamageType parsing not recognised! (Set to PHYSICAL)");
            }
            return dt;
        }
        return damageType;
    }

    protected int getBaseDamage(GameCharacter source) {
        if (fromExternalFile) {
            float damage = 1;
            try {
                String s = UtilText.parse(source, baseDamageString).trim();
//    			System.out.println(s);
                damage = Float.valueOf(s);
            } catch (Exception ex) {
                System.getLogger("").log(System.Logger.Level.ERROR, "CombatMove loading error: baseDamage parsing not recognised! (Set to 1)");
            }
            return (int) damage;
        }
        return baseDamage;
    }

    protected int getDamage(int turnIndex, GameCharacter source, GameCharacter target, boolean isCrit) {
        if (getBaseDamage(source) == 0) {
            return 0;
        }
        DamageType damageType = getDamageType(turnIndex, source);
        return Attack.calculateSpecialAttackDamage(source, target, getType(), damageType, getBaseDamage(source), getDamageVariance(), isCrit);
    }

    public DamageVariance getDamageVariance() {
        return damageVariance;
    }

    public Spell getAssociatedSpell() {
        return associatedSpell;
    }

    public void setAssociatedSpell(Spell associatedSpell) {
        this.associatedSpell = associatedSpell;
    }

    public boolean isCanTargetEnemies() {
        return canTargetEnemies;
    }

    public boolean isCanTargetAllies() {
        return canTargetAllies;
    }

    public boolean isCanTargetSelf() {
        return canTargetSelf;
    }

    public int getAPcost(GameCharacter source) {
        int derivedAPCost = APcost;

        if (fromExternalFile) {
            try {
                derivedAPCost = Integer.valueOf(UtilText.parse(source, APCostString).trim());
            } catch (Exception ex) {
                System.getLogger("").log(System.Logger.Level.ERROR, "CombatMove loading error: APCostString parsing not recognised! (Left as 'APcost')");
            }
        }

        return derivedAPCost + (!source.getEquippedMoves().contains(this) ? 1 : 0);
    }

    public String getName(int turnIndex, GameCharacter source) {
        return UtilText.parse(source, name);
    }

    public String getDescription(int turnIndex, GameCharacter source) {
        String parseText = description;

        parseText = parseText.replaceAll("damageInflicted", String.valueOf(getDamage(turnIndex, source, null, false)));
        parseText = parseText.replaceAll("formattedDamageInflicted", getFormattedDamage(getDamageType(turnIndex, source), getBaseDamage(source), null, false, false));

        return UtilText.parse(source, parseText);
    }

    public String getSVGString() {
        return SVGString;
    }

    public Map<AbstractStatusEffect, Integer> getStatusEffects(GameCharacter caster, GameCharacter target, boolean isCritical) {
        if (isCritical) {
            return statusEffectsCritical;
        }
        return statusEffects;
    }

    public List<String> getCritRequirements(GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
        if (fromExternalFile) {
            return Util.newArrayListOfValues(criticalDescription);
        }
        return Util.newArrayListOfValues("It's the third time being used this turn.");
    }

    public boolean canCrit(int turnIndex, GameCharacter source, GameCharacter target, List<GameCharacter> enemies, List<GameCharacter> allies) {
        if (fromExternalFile) {
            String parseText = criticalCondition;

            parseText = parseText.replaceAll("turnIndex", String.valueOf(turnIndex));
            parseText = parseText.replaceAll("damageInflicted", String.valueOf(getDamage(turnIndex, source, target, false)));
            parseText = parseText.replaceAll("damageType", "DAMAGE_TYPE_" + this.getDamageType(turnIndex, source));

            return Boolean.valueOf(UtilText.parse(source, target, parseText).trim());

        } else {
            // Normally moves crit on every third use per turn.
            int thisMoveSelected = 0;
            for (int i = 0; i < source.getSelectedMoves().size(); i++) {
                Value<GameCharacter, AbstractCombatMove> move = source.getSelectedMoves().get(i);
                if (Objects.equals(move.getValue().getIdentifier(), this.getIdentifier())) {
                    thisMoveSelected++;
                }
                if (i == turnIndex) {
                    return thisMoveSelected % 3 == 0;
                }
            }
            return false;
        }
    }

    public float getCritStatusEffectDurationMultiplier() {
        return 1;
    }

    public Colour getColour() {
        if (this.getAssociatedSpell() != null) {
            return this.getAssociatedSpell().getSpellSchool().getColour();
        }
        return this.getType().getColour();
    }

    public Colour getColourByDamageType(int turnIndex, GameCharacter source) {
        if (Util.newArrayListOfValues(CombatMoveType.SPELL, CombatMoveType.POWER).contains(type)) {
            return getDamageType(turnIndex, source).getColour();
        }

        return type.getColour();
    }

    public boolean isMod() {
        return mod;
    }

    public int getEquipWeighting() {
        return equipWeighting;
    }
}