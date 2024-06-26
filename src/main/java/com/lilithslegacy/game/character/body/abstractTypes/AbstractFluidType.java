package com.lilithslegacy.game.character.body.abstractTypes;

import com.lilithslegacy.controller.xmlParsing.Element;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.Body;
import com.lilithslegacy.game.character.body.coverings.AbstractBodyCoveringType;
import com.lilithslegacy.game.character.body.types.BodyPartTypeInterface;
import com.lilithslegacy.game.character.body.valueEnums.FluidFlavour;
import com.lilithslegacy.game.character.body.valueEnums.FluidModifier;
import com.lilithslegacy.game.character.body.valueEnums.FluidTypeBase;
import com.lilithslegacy.game.character.race.AbstractRace;
import com.lilithslegacy.game.character.race.Race;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Innoxia
 * @version 0.4
 * @since 0.3.8.2
 */
public abstract class AbstractFluidType implements BodyPartTypeInterface {

    private boolean mod;
    private boolean fromExternalFile;

    private String transformationName;

    private FluidTypeBase baseFluidType;
    private FluidFlavour flavour;
    private AbstractRace race;

    private List<String> namesMasculine;
    private List<String> namesFeminine;

    private List<String> descriptorsMasculine;
    private List<String> descriptorsFeminine;

    List<FluidModifier> defaultFluidModifiers;

    /**
     * @param baseFluidType         The base type of this fluid (milk, cum, or girlcum).
     * @param flavour               The default flavour for this fluid.
     * @param race                  What race has this fluid type.
     * @param names                 A list of singular names for this fluid type.
     *                              Pass in null to use generic names.
     *                              Empty values also use generic names.
     *                              Names ending in '-' are handled in a special manner by appending a generic fluid name to the end of it before returning.
     * @param namesPlural           A list of plural names for this fluid type.
     *                              Pass in null to use generic names.
     *                              Empty values also use generic names.
     *                              Names ending in '-' are handled in a special manner by appending a generic fluid name to the end of it before returning.
     * @param descriptorsMasculine  The descriptors that can be used to describe a masculine form of this fluid type.
     * @param descriptorsFeminine   The descriptors that can be used to describe a feminine form of this fluid type.
     * @param defaultFluidModifiers Which modifiers this fluid naturally spawns with.
     */
    public AbstractFluidType(
            FluidTypeBase baseFluidType,
            FluidFlavour flavour,
            AbstractRace race,
            List<String> namesMasculine,
            List<String> namesFeminine,
            List<String> descriptorsMasculine,
            List<String> descriptorsFeminine,
            List<FluidModifier> defaultFluidModifiers) {

        this.baseFluidType = baseFluidType;
        this.flavour = flavour;
        this.race = race;

        this.transformationName = null; // Use default race transformation name

        this.namesMasculine = namesMasculine;
        this.namesFeminine = namesFeminine;

        this.descriptorsMasculine = descriptorsMasculine;
        this.descriptorsFeminine = descriptorsFeminine;

        this.defaultFluidModifiers = defaultFluidModifiers;
    }

    public AbstractFluidType(Path XMLFile, String author, boolean mod) {
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

                Element coreElement = Element.getDocumentRootElement(XMLFile);

                this.mod = mod;
                this.fromExternalFile = true;

                this.race = Race.getRaceFromId(coreElement.getMandatoryFirstOf("race").getTextContent());
                this.baseFluidType = FluidTypeBase.valueOf(coreElement.getMandatoryFirstOf("baseFluidType").getTextContent());
                this.flavour = FluidFlavour.valueOf(coreElement.getMandatoryFirstOf("flavour").getTextContent());

                this.transformationName = coreElement.getMandatoryFirstOf("transformationName").getTextContent();

                this.namesMasculine = new ArrayList<>();
                for (Element e : coreElement.getMandatoryFirstOf("namesMasculine").getAllOf("name")) {
                    namesMasculine.add(e.getTextContent());
                }

                this.namesFeminine = new ArrayList<>();
                for (Element e : coreElement.getMandatoryFirstOf("namesFeminine").getAllOf("name")) {
                    namesFeminine.add(e.getTextContent());
                }

                this.descriptorsMasculine = new ArrayList<>();
                if (coreElement.getOptionalFirstOf("descriptorsMasculine").isPresent()) {
                    for (Element e : coreElement.getMandatoryFirstOf("descriptorsMasculine").getAllOf("descriptor")) {
                        descriptorsMasculine.add(e.getTextContent());
                    }
                }

                this.descriptorsFeminine = new ArrayList<>();
                if (coreElement.getOptionalFirstOf("descriptorsFeminine").isPresent()) {
                    for (Element e : coreElement.getMandatoryFirstOf("descriptorsFeminine").getAllOf("descriptor")) {
                        descriptorsFeminine.add(e.getTextContent());
                    }
                }

                this.defaultFluidModifiers = new ArrayList<>();
                if (coreElement.getOptionalFirstOf("defaultFluidModifiers").isPresent()) {
                    for (Element e : coreElement.getMandatoryFirstOf("defaultFluidModifiers").getAllOf("modifier")) {
                        defaultFluidModifiers.add(FluidModifier.valueOf(e.getTextContent()));
                    }
                }

            } catch (Exception ex) {
                System.getLogger("").log(System.Logger.Level.ERROR, ex);
                System.getLogger("").log(System.Logger.Level.ERROR, "AbstractFluidType was unable to be loaded from file! (" + XMLFile.getFileName().toString() + ")\n" + ex);
            }
        }
    }

    public boolean isMod() {
        return mod;
    }

    public boolean isFromExternalFile() {
        return fromExternalFile;
    }

    @Override
    public String getTransformationNameOverride() {
        return transformationName;
    }

    @Override
    public String toString() {
        System.getLogger("").log(System.Logger.Level.ERROR, "Warning! AbstractFluidType is calling toString()");
        return super.toString();
    }

    @Override
    public String getDeterminer(GameCharacter gc) {
        return "some";
    }

    @Override
    public boolean isDefaultPlural(GameCharacter gc) {
        return false;
    }

    @Override
    public String getNameSingular(GameCharacter gc) {
        String name;

        if (gc == null || gc.isFeminine()) {
            if (namesFeminine == null || namesFeminine.isEmpty()) {
                return Util.randomItemFrom(baseFluidType.getNames());
            }
            name = Util.randomItemFrom(namesFeminine);

        } else {
            if (namesMasculine == null || namesMasculine.isEmpty()) {
                return Util.randomItemFrom(baseFluidType.getNames());
            }
            name = Util.randomItemFrom(namesMasculine);
        }

        if (name == null || name.isEmpty()) {
            return Util.randomItemFrom(baseFluidType.getNames());
        }
        if (name.endsWith("-")) {
            if (Math.random() < 0.25f) { // 25% chance to return this '-' name.
                return name + Util.randomItemFrom(baseFluidType.getNames());
            } else {
                return Util.randomItemFrom(baseFluidType.getNames());
            }
        }
        return name;
    }

    @Override
    public String getNamePlural(GameCharacter gc) {
        return getNameSingular(gc);
    }

    @Override
    public String getDescriptor(GameCharacter gc) {
        if (gc == null || gc.isFeminine()) {
            if (descriptorsFeminine == null || descriptorsFeminine.isEmpty()) {
                return "";
            }
            return Util.randomItemFrom(descriptorsFeminine);

        } else {
            if (descriptorsMasculine == null || descriptorsMasculine.isEmpty()) {
                return "";
            }
            return Util.randomItemFrom(descriptorsMasculine);
        }
    }

    @Override
    public AbstractBodyCoveringType getBodyCoveringType(Body body) {
        return baseFluidType.getCoveringType();
    }

    @Override
    public AbstractRace getRace() {
        return race;
    }

    public FluidTypeBase getBaseType() {
        return baseFluidType;
    }

    public FluidFlavour getFlavour() {
        return flavour;
    }

    public List<FluidModifier> getDefaultFluidModifiers() {
        return defaultFluidModifiers;
    }
}
