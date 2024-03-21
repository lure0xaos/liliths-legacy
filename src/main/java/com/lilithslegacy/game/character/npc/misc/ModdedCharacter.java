package com.lilithslegacy.game.character.npc.misc;

import com.lilithslegacy.game.character.CharacterImportSetting;
import com.lilithslegacy.game.character.gender.Gender;
import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.character.persona.Name;
import com.lilithslegacy.game.character.race.AbstractSubspecies;
import com.lilithslegacy.game.character.race.RacialBody;
import com.lilithslegacy.game.character.race.Subspecies;
import com.lilithslegacy.game.dialogue.DialogueFlagValue;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.AbstractCoreItem;
import com.lilithslegacy.game.inventory.CharacterInventory;
import com.lilithslegacy.game.inventory.ItemTag;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Vector2i;
import com.lilithslegacy.world.AbstractWorldType;
import com.lilithslegacy.world.Season;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.AbstractPlaceType;
import com.lilithslegacy.world.places.PlaceType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.time.Month;
import java.util.*;
import java.util.function.Predicate;

/**
 * @author AceXp
 * @version 0.4.5.5
 * @since 0.4.5.5
 */
public class ModdedCharacter extends NPC {

    protected boolean isTrader;
    protected boolean isClothingStealable;
    protected boolean isAbleToBeImpregnated;
    protected Set<ItemTag> itemTagsToBuy;

    public ModdedCharacter() {
        this(Gender.getGenderFromUserPreferences(false, false), WorldType.EMPTY, new Vector2i(0, 0), false);
    }

    public ModdedCharacter(boolean isImported) {
        this(Gender.getGenderFromUserPreferences(false, false), WorldType.EMPTY, new Vector2i(0, 0), isImported);
    }

    public ModdedCharacter(Gender gender, AbstractWorldType worldLocation, Vector2i location, boolean isImported) {
        this(gender, worldLocation, location, isImported, null);
    }

    public ModdedCharacter(Gender gender, AbstractWorldType worldLocation, AbstractPlaceType placeType, boolean isImported, Predicate<AbstractSubspecies> subspeciesRemovalFilter) {
        this(gender, worldLocation, Main.game.getWorlds().get(worldLocation).getCell(placeType).getLocation(), isImported, subspeciesRemovalFilter);
    }

    public ModdedCharacter(Gender gender, AbstractWorldType worldLocation, Vector2i location, boolean isImported, Predicate<AbstractSubspecies> subspeciesRemovalFilter) {
        super(isImported, null, null, "",
                Util.random.nextInt(28) + 18, Util.randomItemFrom(Month.values()), 1 + Util.random.nextInt(25),
                3,
                null, null, null,
                new CharacterInventory(10),
                WorldType.EMPTY, PlaceType.GENERIC_HOLDING_CELL,
                false);

        if (!isImported) {
            this.setLocation(worldLocation, location, false);

            setLevel(Util.random.nextInt(5) + 5);

            // RACE & NAME:

            Map<AbstractSubspecies, Integer> availableRaces = new HashMap<>();
            List<AbstractSubspecies> availableSubspecies = new ArrayList<>(Subspecies.getAllSubspecies());

            if (subspeciesRemovalFilter != null) {
                availableSubspecies.removeIf(subspeciesRemovalFilter);
            }

            for (AbstractSubspecies s : availableSubspecies) {
                if (s.getSubspeciesOverridePriority() > 0) { // Do not spawn demonic races, elementals, or youko
                    continue;
                }
                if (s == Subspecies.REINDEER_MORPH
                        && Main.game.getSeason() == Season.WINTER
                        && Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.hasSnowedThisWinter)) {
                    AbstractSubspecies.addToSubspeciesMap(50, gender, s, availableRaces);
                }

                if (Subspecies.getWorldSpecies(WorldType.DOMINION, null, false).containsKey(s)) {
                    AbstractSubspecies.addToSubspeciesMap((int) (1000 * Subspecies.getWorldSpecies(WorldType.DOMINION, null, false).get(s).getChanceMultiplier()), gender, s, availableRaces);
                } else if (Subspecies.getWorldSpecies(WorldType.SUBMISSION, null, false).containsKey(s)) {
                    AbstractSubspecies.addToSubspeciesMap((int) (1000 * Subspecies.getWorldSpecies(WorldType.SUBMISSION, null, false).get(s).getChanceMultiplier()), gender, s, availableRaces);
                }
            }

            this.setBodyFromSubspeciesPreference(gender, availableRaces, true, subspeciesRemovalFilter == null);

            setSexualOrientation(RacialBody.valueOfRace(this.getRace()).getSexualOrientation(gender));

            setName(Name.getRandomTriplet(this.getSubspecies()));
            this.setPlayerKnowsName(false);
            setDescription(UtilText.parse(this, "[npc.Name] is [npc.a_fullRace]."));

            // Set starting attributes based on the character's race
            initPerkTreeAndBackgroundPerks();
            this.setStartingCombatMoves();
            loadImages();

            initHealthAndManaToMax();

            this.isTrader = false;
            this.itemTagsToBuy = new HashSet<>();
            this.isAbleToBeImpregnated = false;
            this.isClothingStealable = false;
        }
    }

    @Override
    public void loadFromXML(Element parentElement, Document doc, CharacterImportSetting... settings) {
        loadNPCVariablesFromXML(this, null, parentElement, doc, settings);

        this.isTrader = false;
        this.itemTagsToBuy = new HashSet<>();
        this.isAbleToBeImpregnated = false;
        this.isClothingStealable = false;
    }

    @Override
    public void setStartingBody(boolean setPersona) {
        // Not needed
    }

    @Override
    public boolean isUnique() {
        return false;
    }

    @Override
    public void changeFurryLevel() {
    }

    @Override
    public DialogueNode getEncounterDialogue() {
        return null;
    }

    public void setAbleToBeImpregnated(boolean ableToBeImpregnated) {
        isAbleToBeImpregnated = ableToBeImpregnated;
    }

    @Override
    public boolean isAbleToBeImpregnated() {
        return this.isAbleToBeImpregnated;
    }

    public void setClothingStealable(boolean clothingStealable) {
        isClothingStealable = clothingStealable;
    }

    @Override
    public boolean isClothingStealable() {
        return this.isClothingStealable;
    }

    public void setTrader(boolean trader) {
        isTrader = trader;
    }

    @Override
    public boolean isTrader() {
        return this.isTrader;
    }

    public void setItemTagsToBuy(ItemTag[] itemTagsToBuy) {
        Collections.addAll(this.itemTagsToBuy, itemTagsToBuy);
    }

    @Override
    public boolean willBuy(AbstractCoreItem item) {
        //disjoint returns true if the two specified collections have no elements in common.
        return !(Collections.disjoint(item.getItemTags(), itemTagsToBuy));
    }

}
