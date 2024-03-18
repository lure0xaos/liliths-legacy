package com.lilithslegacy.game.occupantManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.lilithslegacy.controller.xmlParsing.XMLUtil;
import com.lilithslegacy.game.character.FluidStored;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.attributes.AffectionLevel;
import com.lilithslegacy.game.character.attributes.Attribute;
import com.lilithslegacy.game.character.body.CoverableArea;
import com.lilithslegacy.game.character.body.abstractTypes.AbstractFluidType;
import com.lilithslegacy.game.character.body.valueEnums.FluidFlavour;
import com.lilithslegacy.game.character.body.valueEnums.FluidModifier;
import com.lilithslegacy.game.character.body.valueEnums.FluidTypeBase;
import com.lilithslegacy.game.character.fetishes.Fetish;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.occupantManagement.slave.SlaveJob;
import com.lilithslegacy.game.occupantManagement.slave.SlaveJobSetting;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.rendering.SVGImages;
import com.lilithslegacy.utils.Units;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Vector2i;
import com.lilithslegacy.utils.XMLSaving;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.world.AbstractWorldType;
import com.lilithslegacy.world.Cell;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.PlaceUpgrade;

/**
 * @author Innoxia
 * @version 0.3.5.1
 * @since 0.2.5
 */
public class MilkingRoom implements XMLSaving {

    public static final int INGESTION_AMOUNT = 100;
    public static final int ARTISAN_MILKING_AMOUNT = 2000;
    public static final int BASE_MILKING_AMOUNT = 2500;
    public static final int INDUSTRIAL_MILKING_AMOUNT = 5000;
    public static final int ARTISAN_CUM_MILKING_AMOUNT = 200;
    public static final int BASE_CUM_MILKING_AMOUNT = 250;
    public static final int INDUSTRIAL_CUM_MILKING_AMOUNT = 500;
    public static final int ARTISAN_GIRLCUM_MILKING_AMOUNT = 40;
    public static final int BASE_GIRLCUM_MILKING_AMOUNT = 50;
    public static final int INDUSTRIAL_GIRLCUM_MILKING_AMOUNT = 100;
    private static GameCharacter targetedCharacter = Main.game.getPlayer();
    private final AbstractWorldType worldType;
    private final Vector2i location;
    private List<FluidStored> fluidsStored;
    private Map<String, List<AbstractClothing>> clothingRemovedForMilking = new HashMap<>();

    public MilkingRoom(AbstractWorldType worldType, Vector2i location) {
        this.worldType = worldType;
        this.location = new Vector2i(location.getX(), location.getY());

        this.fluidsStored = new ArrayList<>();
    }

    public static MilkingRoom loadFromXML(Element parentElement, Document doc) {
        try {
            MilkingRoom room = new MilkingRoom(
                    WorldType.getWorldTypeFromId(parentElement.getAttribute("worldType")),
                    new Vector2i(
                            Integer.valueOf(parentElement.getAttribute("x")),
                            Integer.valueOf(parentElement.getAttribute("y"))));

            try {
                NodeList fluidStoredElements = parentElement.getElementsByTagName("fluidStored");
                for (int i = 0; i < fluidStoredElements.getLength(); i++) {
                    Element fluidElement = (Element) fluidStoredElements.item(i);
                    room.getFluidsStored().add(FluidStored.loadFromXML(null, fluidElement, doc));
                }
            } catch (Exception ex) {
            }

            Map<FluidStored, Float> uniqueFluids = new HashMap<>();

            for (FluidStored fluid : room.getFluidsStored()) {
                if (uniqueFluids.containsKey(fluid)) {
                    uniqueFluids.put(fluid, fluid.getMillilitres() + uniqueFluids.get(fluid));
                } else {
                    uniqueFluids.put(fluid, fluid.getMillilitres());
                }
            }

            room.fluidsStored = new ArrayList<>();
            for (Entry<FluidStored, Float> entry : uniqueFluids.entrySet()) {
                entry.getKey().setMillilitres(entry.getValue());
                room.fluidsStored.add(entry.getKey());
            }


            room.clothingRemovedForMilking = new HashMap<>();

            NodeList clothingStoredElements = parentElement.getElementsByTagName("clothingRemovedForMilking");
            if (clothingStoredElements.item(0) != null) {
                Element clothingStoredElement = (Element) clothingStoredElements.item(0);

                NodeList savedCharacters = clothingStoredElement.getElementsByTagName("clothingCharacter");
                for (int i = 0; i < savedCharacters.getLength(); i++) {
                    Element characterElement = (Element) savedCharacters.item(i);
                    String id = characterElement.getAttribute("id");
                    List<AbstractClothing> clothingLoaded = new ArrayList<>();

                    NodeList clothingElement = characterElement.getElementsByTagName("clothing");
                    for (int j = 0; j < clothingElement.getLength(); j++) {
                        clothingLoaded.add(AbstractClothing.loadFromXML((Element) clothingElement.item(j), doc));
                    }
                    room.clothingRemovedForMilking.put(id, clothingLoaded);
                }
            }

            return room;

        } catch (Exception ex) {
            System.getLogger("").log(System.Logger.Level.ERROR, "Warning: MilkingRoom failed to import!");
            return null;
        }
    }

    public static Cell getMilkingCell(GameCharacter character, boolean needFreeCell) {
        List<MilkingRoom> freeRooms = new ArrayList<>();
        List<MilkingRoom> fullRooms = new ArrayList<>();
        List<Cell> milkingCells = new ArrayList<>();

        for (MilkingRoom room : Main.game.getOccupancyUtil().getMilkingRooms()) {
            Cell c = Main.game.getWorlds().get(room.getWorldType()).getCell(room.getLocation());
            int charactersPresent = 0; //Main.game.getCharactersPresent(c).size();
            for (GameCharacter charPresent : Main.game.getCharactersPresent(c)) {
                if (charPresent.isSlave() && charPresent.getSlaveJob(Main.game.getHourOfDay()) == SlaveJob.MILKING && charPresent != character) {
                    charactersPresent++;
                }
            }
            if (charactersPresent < 8) {
                // Milk tax PR change, but I think the above change already fixed the issue
//			List<NPC> charactersPresent = Main.game.getCharactersPresent(c);
//			if (charactersPresent.contains((NPC) character)) {
//				return c;
//			}
//			if (charactersPresent.size() < 8) {
                freeRooms.add(room);
            } else {
                fullRooms.add(room);
            }
        }
        if (freeRooms.isEmpty() && needFreeCell) {
            return null;
        }

        // check for a room with capacity and the right type first
        List<Cell> suitableCells = new ArrayList<>();
        for (MilkingRoom room : freeRooms) {
            Cell c = Main.game.getWorlds().get(room.getWorldType()).getCell(room.getLocation());

            if (character.hasSlaveJobSetting(SlaveJob.MILKING, SlaveJobSetting.MILKING_INDUSTRIAL)
                    && c.getPlace().getPlaceUpgrades().contains(PlaceUpgrade.LILAYA_MILKING_ROOM_INDUSTRIAL_MILKERS)) {
                suitableCells.add(c);

            } else if (character.hasSlaveJobSetting(SlaveJob.MILKING, SlaveJobSetting.MILKING_ARTISAN)
                    && c.getPlace().getPlaceUpgrades().contains(PlaceUpgrade.LILAYA_MILKING_ROOM_ARTISAN_MILKERS)) {
                suitableCells.add(c);

            } else if (character.hasSlaveJobSetting(SlaveJob.MILKING, SlaveJobSetting.MILKING_REGULAR)
                    && !c.getPlace().getPlaceUpgrades().contains(PlaceUpgrade.LILAYA_MILKING_ROOM_ARTISAN_MILKERS)
                    && !c.getPlace().getPlaceUpgrades().contains(PlaceUpgrade.LILAYA_MILKING_ROOM_INDUSTRIAL_MILKERS)) {
                suitableCells.add(c);

            } else {
                // not the right type, but has capacity
                milkingCells.add(c);
            }
        }
        if (!suitableCells.isEmpty()) {
            if (suitableCells.contains(character.getCell())) { // If the cell the character is in is suitable, just leave them there
                return character.getCell();
            }
            return suitableCells.get(0);
        }

        for (MilkingRoom room : fullRooms) {
            Cell c = Main.game.getWorlds().get(room.getWorldType()).getCell(room.getLocation());

            milkingCells.add(c);
        }

        if (milkingCells.isEmpty()) {
            return null;
        }

        for (Cell c : milkingCells) {
            int charactersPresent = Main.game.getCharactersPresent(c).size();
            // all rooms of the right type are full, so select a room with capacity to avoid crowding
            if (charactersPresent < 8) {
                return c;
                // if all rooms are full, and a free slot is not needed	use a full room of the right type
            } else if (character.hasSlaveJobSetting(SlaveJob.MILKING, SlaveJobSetting.MILKING_INDUSTRIAL)
                    && c.getPlace().getPlaceUpgrades().contains(PlaceUpgrade.LILAYA_MILKING_ROOM_INDUSTRIAL_MILKERS)) {
                if (!needFreeCell && charactersPresent == 8) {
                    return c;
                }

            } else if (character.hasSlaveJobSetting(SlaveJob.MILKING, SlaveJobSetting.MILKING_ARTISAN)
                    && c.getPlace().getPlaceUpgrades().contains(PlaceUpgrade.LILAYA_MILKING_ROOM_ARTISAN_MILKERS)) {
                if (!needFreeCell && charactersPresent == 8) {
                    return c;
                }

            } else if (character.hasSlaveJobSetting(SlaveJob.MILKING, SlaveJobSetting.MILKING_REGULAR)
                    && !c.getPlace().getPlaceUpgrades().contains(PlaceUpgrade.LILAYA_MILKING_ROOM_ARTISAN_MILKERS)
                    && !c.getPlace().getPlaceUpgrades().contains(PlaceUpgrade.LILAYA_MILKING_ROOM_INDUSTRIAL_MILKERS)) {
                if (!needFreeCell && charactersPresent == 8) {
                    return c;
                }
            }
        }
        return milkingCells.get(0);
    }

    public static int getMaximumMilkPerHour(GameCharacter character) {
        Cell c = character.getCell();
        int milked = MilkingRoom.BASE_MILKING_AMOUNT;

        if (c == null) {
            return milked;
        }

        if (c.getPlace().getPlaceUpgrades().contains(PlaceUpgrade.LILAYA_MILKING_ROOM_ARTISAN_MILKERS)) {
            milked = MilkingRoom.ARTISAN_MILKING_AMOUNT;

        } else if (c.getPlace().getPlaceUpgrades().contains(PlaceUpgrade.LILAYA_MILKING_ROOM_INDUSTRIAL_MILKERS)) {
            milked = MilkingRoom.INDUSTRIAL_MILKING_AMOUNT;
        }

        if (c.getPlace().getPlaceUpgrades().contains(PlaceUpgrade.LILAYA_MILKING_ROOM_MILK_EFFICIENCY)) {
            milked *= 2;
        }

        return milked;
    }

    public static int getActualMilkPerHour(GameCharacter character) {
        if (character.getBreastRawMilkStorageValue() == 0) {
            return 0;
        }
        return (int) Math.min(getMaximumMilkPerHour(character), (character.getLactationRegenerationPerSecond(true) * 60 * 60));
    }

    public static int getActualCrotchMilkPerHour(GameCharacter character) {
        if (!character.hasBreastsCrotch() || character.getBreastCrotchRawMilkStorageValue() == 0) {
            return 0;
        }
        return (int) Math.min(getMaximumMilkPerHour(character), (character.getCrotchLactationRegenerationPerSecond(true) * 60 * 60));
    }

    public static int getMaximumCumPerHour(GameCharacter character) {
        Cell c = getMilkingCell(character, false);
        int milked = MilkingRoom.BASE_CUM_MILKING_AMOUNT;

        if (c == null) {
            return milked;
        }

        if (c.getPlace().getPlaceUpgrades().contains(PlaceUpgrade.LILAYA_MILKING_ROOM_ARTISAN_MILKERS)) {
            milked = MilkingRoom.ARTISAN_CUM_MILKING_AMOUNT;

        } else if (c.getPlace().getPlaceUpgrades().contains(PlaceUpgrade.LILAYA_MILKING_ROOM_INDUSTRIAL_MILKERS)) {
            milked = MilkingRoom.INDUSTRIAL_CUM_MILKING_AMOUNT;
        }

        if (c.getPlace().getPlaceUpgrades().contains(PlaceUpgrade.LILAYA_MILKING_ROOM_CUM_EFFICIENCY)) {
            milked *= 2;
        }

        return milked;
    }

    public static int getActualCumPerHour(GameCharacter character) {
        if (!character.hasPenisIgnoreDildo()) {
            return 0;
        }
        return (int) Math.min(getMaximumCumPerHour(character), (character.getCumRegenerationPerSecond() * 60 * 60));
    }

    public static int getMaximumGirlcumPerHour(GameCharacter character) {
        Cell c = getMilkingCell(character, false);
        int milked = MilkingRoom.BASE_GIRLCUM_MILKING_AMOUNT;

        if (c == null) {
            return milked;
        }

        if (c.getPlace().getPlaceUpgrades().contains(PlaceUpgrade.LILAYA_MILKING_ROOM_ARTISAN_MILKERS)) {
            milked = MilkingRoom.ARTISAN_GIRLCUM_MILKING_AMOUNT;

        } else if (c.getPlace().getPlaceUpgrades().contains(PlaceUpgrade.LILAYA_MILKING_ROOM_INDUSTRIAL_MILKERS)) {
            milked = MilkingRoom.INDUSTRIAL_GIRLCUM_MILKING_AMOUNT;
        }

        if (c.getPlace().getPlaceUpgrades().contains(PlaceUpgrade.LILAYA_MILKING_ROOM_GIRLCUM_EFFICIENCY)) {
            milked *= 2;
        }

        return milked;
    }

    public static int getActualGirlcumPerHour(GameCharacter character) {
        if (!character.hasVagina()) {
            return 0;
        }
        int orgasmsPerHour = 10;
        return Math.min(getMaximumGirlcumPerHour(character), orgasmsPerHour * character.getVaginaWetness().getValue() * (character.isVaginaSquirter() ? 5 : 1));
    }

    public static GameCharacter getTargetedCharacter() {
        if (MilkingRoom.targetedCharacter == null || (!MilkingRoom.targetedCharacter.isPlayer() && !Main.game.getCharactersPresent().contains(MilkingRoom.targetedCharacter))) {
            MilkingRoom.targetedCharacter = Main.game.getPlayer();
        }
        return targetedCharacter;
    }

    public static void setTargetedCharacter(GameCharacter targetedCharacter) {
        MilkingRoom.targetedCharacter = targetedCharacter;
    }

    public Element saveAsXML(Element parentElement, Document doc) {
        Element element = doc.createElement("milkingRoom");
        parentElement.appendChild(element);

        XMLUtil.addAttribute(doc, element, "worldType", WorldType.getIdFromWorldType(this.getWorldType()));
        XMLUtil.addAttribute(doc, element, "x", String.valueOf(this.getLocation().getX()));
        XMLUtil.addAttribute(doc, element, "y", String.valueOf(this.getLocation().getY()));

        for (FluidStored fluid : fluidsStored) {
            fluid.saveAsXML(element, doc);
        }

        if (!clothingRemovedForMilking.isEmpty()) {
            Element clothingSaved = doc.createElement("clothingRemovedForMilking");
            element.appendChild(clothingSaved);
            for (Entry<String, List<AbstractClothing>> entry : clothingRemovedForMilking.entrySet()) {
                Element clothingCharacter = doc.createElement("clothingCharacter");
                clothingSaved.appendChild(clothingCharacter);
                XMLUtil.addAttribute(doc, clothingCharacter, "id", entry.getKey());
                for (AbstractClothing clothing : entry.getValue()) {
                    Element e = clothing.saveAsXML(clothingSaved, doc);
                    clothingCharacter.appendChild(e);
                }
            }
        }

        return element;
    }

    public AbstractWorldType getWorldType() {
        return worldType;
    }

    public Vector2i getLocation() {
        return location;
    }

    public List<FluidStored> getFluidsStored() {

        return fluidsStored;
    }

    public List<FluidStored> getMilkFluidsStored() {
        List<FluidStored> milkFluids = new ArrayList<>(fluidsStored);
        milkFluids.removeIf((fluid) -> !fluid.isMilk());
        return milkFluids;
    }

    public List<FluidStored> getCumFluidsStored() {
        List<FluidStored> cumFluids = new ArrayList<>(fluidsStored);
        cumFluids.removeIf((fluid) -> !fluid.isCum());
        return cumFluids;
    }

    public List<FluidStored> getGirlcumFluidsStored() {
        List<FluidStored> girlcumFluids = new ArrayList<>(fluidsStored);
        girlcumFluids.removeIf((fluid) -> !fluid.isGirlCum());
        return girlcumFluids;
    }

    public void incrementFluidStored(FluidStored fluid, float millilitres) {
        boolean fluidIncremented = false;

        for (FluidStored f : getFluidsStored()) {
            if (fluid.equals(f)) {
                f.incrementMillilitres(millilitres);
                fluidIncremented = true;
                break;
            }
        }
        if (!fluidIncremented) {
            getFluidsStored().add(fluid);
        }

        getFluidsStored().removeIf((fs) -> fs.getMillilitres() <= 0);
    }

    public Map<String, List<AbstractClothing>> getClothingRemovedForMilking() {
        return clothingRemovedForMilking;
    }

    public void addClothingRemovedForMilking(GameCharacter character, AbstractClothing clothing) {
        clothingRemovedForMilking.putIfAbsent(character.getId(), new ArrayList<>());
        clothingRemovedForMilking.get(character.getId()).add(clothing);
    }

    public void clearClothingRemovedForMilking(GameCharacter character) {
        clothingRemovedForMilking.remove(character.getId());
    }

    public String getRoomDescription() {

        String milkyMilknessSB = getFluidEntries(this.getMilkFluidsStored(), PresetColour.MILK, "Milk Stored") +
                getFluidEntries(this.getCumFluidsStored(), PresetColour.CUM, "Cum Stored") +
                getFluidEntries(this.getGirlcumFluidsStored(), PresetColour.GIRLCUM, "Girlcum Stored");

        return milkyMilknessSB;
    }

    private String getFluidEntries(List<FluidStored> fluids, Colour colour, String title) {
        StringBuilder milkyMilknessSB = new StringBuilder();


        boolean fluidsFound = false;

        milkyMilknessSB.append("<div class='container-full-width' style='margin-bottom:2px; text-align:center;'><b style='color:").append(colour.toWebHexString()).append(";'>").append(title).append("</b>");

        for (FluidStored fluid : fluids) {
            String idModifier = "";
            AbstractFluidType type = null;

            if (fluid.isMilk()) {
                idModifier = "MILK";
                type = fluid.getFluid().getType();

            } else if (fluid.isCum()) {
                idModifier = "CUM";
                type = fluid.getFluid().getType();

            } else if (fluid.isGirlCum()) {
                idModifier = "GIRLCUM";
                type = fluid.getFluid().getType();
            }

            fluidsFound = true;

            milkyMilknessSB.append("<div class='container-full-width' style='margin-top:2px; background:").append(PresetColour.BACKGROUND_ALT.toWebHexString()).append(";'>");

            milkyMilknessSB.append("<div class='container-half-width' style='margin:0; padding:2px; width:15%; background:transparent;'>" + "[style.colourExcellent(").append(Units.fluid(fluid.getMillilitres())).append(")]");
            if (fluid.isCum()) {
                milkyMilknessSB.append("<br/><span style='color:").append(Attribute.VIRILITY.getColour().toWebHexString()).append(";'>Virility:</span> ").append(Units.adaptiveRound(fluid.getVirility()));
            }
            milkyMilknessSB.append("</div>");

            milkyMilknessSB.append("<div class='container-half-width' style='margin:0; padding:2px; width:25%; background:transparent;'>");
            GameCharacter fluidOwner = null;
            try {
                fluidOwner = fluid.getFluidCharacter();
                milkyMilknessSB.append(UtilText.parse(fluidOwner, "<span style='color:" + fluidOwner.getFemininity().getColour().toWebHexString() + ";'>[npc.NamePos]</span>"));

            } catch (Exception ex) {
                milkyMilknessSB.append("[style.colourDisabled(Unknown's)]");
            }
            milkyMilknessSB.append("<br/>" + "<span style='color:").append(type.getRace().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(type.getRace().getName(fluid.isFeral()))).append(" ").append(type.getBaseType().getNames().get(0)//type.getName(fluidOwner)
            ).append("</span>").append("</div>");

            milkyMilknessSB.append("<div class='container-half-width' style='margin:0; padding:2px; width:35%; background:transparent;'>");
            FluidFlavour flavour = fluid.getFluid().getFlavour();
            milkyMilknessSB.append("<span style='color:").append(flavour.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(flavour.getName())).append("-flavoured</span>.<br/>");
            if (!fluid.getFluid().getFluidModifiers().isEmpty()) {
                int i = 0;
                for (FluidModifier mod : fluid.getFluid().getFluidModifiers()) {
                    if (i > 0) {
                        milkyMilknessSB.append(", ");
                    }
                    milkyMilknessSB.append(Util.capitaliseSentence(mod.getName()));
                    i++;
                }
                milkyMilknessSB.append(".");

            } else {
                milkyMilknessSB.append("[style.colourDisabled(No Modifiers)]");
            }
            milkyMilknessSB.append("</div>");

            milkyMilknessSB.append("<div class='container-half-width' style='margin:0; padding:2px; width:10%; background:transparent;'>").append(UtilText.formatAsMoney((int) (fluid.getMillilitres() * fluid.getFluid().getValuePerMl()), "span")).append("</div>");

            milkyMilknessSB.append("<div style='float:left; width:15%; margin:0 auto; padding:0; display:inline-block; text-align:center; background:transparent;'>" + "<div id='").append(idModifier).append("_").append(CoverableArea.MOUTH).append("_").append(fluid.hashCode()).append("' ").append(isAbleToIngestThroughArea(fluid.getFluid().getType().getBaseType(), getTargetedCharacter(), CoverableArea.MOUTH, fluid.getMillilitres())
                    ? "class='square-button big'"
                    : "class='square-button big disabled'").append(">").append("<div class='square-button-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getCoverableAreaMouth()).append("</div></div>");

            milkyMilknessSB.append("<div id='").append(idModifier).append("_").append(CoverableArea.VAGINA).append("_").append(fluid.hashCode()).append("' ").append(isAbleToIngestThroughArea(fluid.getFluid().getType().getBaseType(), getTargetedCharacter(), CoverableArea.VAGINA, fluid.getMillilitres())
                    ? "class='square-button big'"
                    : "class='square-button big disabled'").append(">").append("<div class='square-button-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getCoverableAreaVagina()).append("</div></div>");

            milkyMilknessSB.append("<div id='").append(idModifier).append("_").append(CoverableArea.ANUS).append("_").append(fluid.hashCode()).append("' ").append(isAbleToIngestThroughArea(fluid.getFluid().getType().getBaseType(), getTargetedCharacter(), CoverableArea.ANUS, fluid.getMillilitres())
                    ? "class='square-button big'"
                    : "class='square-button big disabled'").append(">").append("<div class='square-button-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getCoverableAreaAnus()).append("</div></div>");

            milkyMilknessSB.append("<div id='").append(idModifier).append("_SELL_").append(fluid.hashCode()).append("' class='square-button big'>").append("<div class='square-button-content'>").append(SVGImages.SVG_IMAGE_PROVIDER.getTransactionSell()).append("</div></div>");

            milkyMilknessSB.append("</div>");

            milkyMilknessSB.append("</div>");
        }

        if (!fluidsFound) {
            milkyMilknessSB.append("<div class='container-full-width' style='margin-bottom:2px; text-align:center; background:").append(PresetColour.BACKGROUND_ALT.toWebHexString()).append(";'>[style.colourDisabled(None...)]</div>");
        }

        milkyMilknessSB.append("</div>");

        return milkyMilknessSB.toString();
    }

    public boolean isAbleToIngestThroughArea(FluidTypeBase fluidType, GameCharacter ingestingCharacter, CoverableArea area, float millilitres) {
        return getAreaIngestionBlockedDescription(fluidType, ingestingCharacter, area, millilitres).isEmpty();
    }

    public String getAreaIngestionBlockedDescription(FluidTypeBase fluidType, GameCharacter ingestingCharacter, CoverableArea area, float millilitres) {
        StringBuilder sb = new StringBuilder();

        if (millilitres < 5) {
            sb.append("There needs to be at least ").append(Units.fluid(5)).append(" of fluid for [npc.name] to ingest it!<br/>");
        }

        switch (area) {
            case ANUS:
                if (!ingestingCharacter.isAbleToAccessCoverableArea(CoverableArea.ANUS, true)) {
                    sb.append("[npc.NameIsFull] not able to access [npc.her] asshole!");
                }
                break;
            case MOUTH:
                if (!ingestingCharacter.isAbleToAccessCoverableArea(CoverableArea.MOUTH, true)) {
                    sb.append("[npc.NameIsFull] not able to access [npc.her] mouth!");
                }
                break;
            case VAGINA:
                if (!ingestingCharacter.hasVagina()) {
                    sb.append("[npc.Name] [npc.does] not have a vagina!");

                } else if (!ingestingCharacter.isAbleToAccessCoverableArea(CoverableArea.VAGINA, true)) {
                    sb.append("[npc.NameIsFull] not able to access [npc.her] pussy!");
                }
                break;
            default:
                break;
        }
        if (!sb.isEmpty()) {
            return UtilText.parse(ingestingCharacter, sb.toString());
        }

        if (!ingestingCharacter.isPlayer() && !ingestingCharacter.isSlave()) {
            if (ingestingCharacter.getAffection(Main.game.getPlayer()) < AffectionLevel.POSITIVE_FOUR_LOVE.getMinimumValue()) {
                sb.append(UtilText.parse(ingestingCharacter,
                        "As [npc.sheIs] not your slave, [npc.name] will only let you do this if [npc.she]"
                                + " <span style='color:" + AffectionLevel.POSITIVE_FOUR_LOVE.getColour().toWebHexString() + ";'>" + AffectionLevel.POSITIVE_FOUR_LOVE.getDescriptor() + "</span> you.<br/>"));

            } else if (fluidType == FluidTypeBase.CUM
                    && (area == CoverableArea.VAGINA
                    ? !ingestingCharacter.getFetishDesire(Fetish.FETISH_PREGNANCY).isPositive()
                    : !ingestingCharacter.getFetishDesire(Fetish.FETISH_CUM_ADDICT).isPositive())) {
                sb.append(UtilText.parse(ingestingCharacter,
                        area == CoverableArea.VAGINA
                                ? "[npc.Name] requires a positive desire for the " + Fetish.FETISH_PREGNANCY.getName(ingestingCharacter) + " fetish.<br/>"
                                : "[npc.Name] requires a positive desire for the " + Fetish.FETISH_CUM_ADDICT.getName(ingestingCharacter) + " fetish.<br/>"));

            } else if (fluidType == FluidTypeBase.MILK && !ingestingCharacter.getFetishDesire(Fetish.FETISH_LACTATION_OTHERS).isPositive()) {
                sb.append(UtilText.parse(ingestingCharacter,
                        "[npc.Name] requires a positive desire for the " + Fetish.FETISH_LACTATION_OTHERS.getName(ingestingCharacter) + " fetish.<br/>"));

            } else if (fluidType == FluidTypeBase.GIRLCUM && !ingestingCharacter.getFetishDesire(Fetish.FETISH_VAGINAL_GIVING).isPositive()) {
                sb.append(UtilText.parse(ingestingCharacter,
                        "[npc.Name] requires a positive desire for the " + Fetish.FETISH_VAGINAL_GIVING.getName(ingestingCharacter) + " fetish.<br/>"));
            }
            switch (area) {
                case ANUS:
                    if (!ingestingCharacter.getFetishDesire(Fetish.FETISH_ANAL_RECEIVING).isPositive()) {
                        sb.append(UtilText.parse(ingestingCharacter,
                                "[npc.Name] requires a positive desire for the " + Fetish.FETISH_ANAL_RECEIVING.getName(ingestingCharacter) + " fetish."));
                    }
                    break;
                case VAGINA:
                    if (!ingestingCharacter.getFetishDesire(Fetish.FETISH_VAGINAL_RECEIVING).isPositive()) {
                        sb.append(UtilText.parse(ingestingCharacter,
                                "[npc.Name] requires a non-negative desire for the " + Fetish.FETISH_VAGINAL_RECEIVING.getName(ingestingCharacter) + " fetish."));
                    }
                    break;
                default:
                    break;
            }
        }

        return sb.toString();
    }

}
