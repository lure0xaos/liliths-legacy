package com.lilithslegacy.game.character.body.abstractTypes;

import com.lilithslegacy.controller.xmlParsing.Element;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.Body;
import com.lilithslegacy.game.character.body.coverings.AbstractBodyCoveringType;
import com.lilithslegacy.game.character.body.coverings.BodyCoveringType;
import com.lilithslegacy.game.character.body.types.BodyPartTypeInterface;
import com.lilithslegacy.game.character.body.types.HornType;
import com.lilithslegacy.game.character.race.AbstractRace;
import com.lilithslegacy.game.character.race.Race;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.enchanting.TFModifier;
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
 * @since 0.3.1
 */
public abstract class AbstractHornType implements BodyPartTypeInterface {

    private boolean mod;
    private boolean fromExternalFile;

    private AbstractBodyCoveringType coveringType;
    private AbstractRace race;

    private String transformationName;

    private boolean generic;

    private int defaultHornsPerRow;

    private String name;
    private String namePlural;

    private List<String> descriptorsMasculine;
    private List<String> descriptorsFeminine;

    private String hornTransformationDescription;
    private String hornBodyDescription;

    /**
     * @param coveringType                  What covers this horn type (i.e skin/fur/feather type).
     * @param race                          What race has this horn type.
     * @param defaultHornsPerRow            The number of horns per row by default for this horn type.
     * @param transformationName            The name that should be displayed when offering this horn type as a transformation. Should be something like "curved" or "straight".
     * @param name                          The singular name of the horn. This will usually just be "horn" or "antler".
     * @param namePlural                    The plural name of the horn. This will usually just be "horns" or "antlers".
     * @param descriptorsMasculine          The descriptors that can be used to describe a masculine form of this horn type.
     * @param descriptorsFeminine           The descriptors that can be used to describe a feminine form of this horn type.
     * @param hornTransformationDescription A paragraph describing a character's horns transforming into this horn type. Parsing assumes that the character already has this horn type and associated skin covering.
     * @param hornBodyDescription           A sentence or two to describe this horn type, as seen in the character view screen. It should follow the same format as all of the other entries in the HornType class.
     */
    public AbstractHornType(
            AbstractBodyCoveringType coveringType,
            AbstractRace race,
            int defaultHornsPerRow,
            String transformationName,
            String name,
            String namePlural,
            List<String> descriptorsMasculine,
            List<String> descriptorsFeminine,
            String hornTransformationDescription,
            String hornBodyDescription) {

        this.coveringType = coveringType;
        this.race = race;

        this.transformationName = transformationName;
        this.name = name;
        this.namePlural = namePlural;

        this.generic = false;

        this.defaultHornsPerRow = defaultHornsPerRow;

        this.descriptorsMasculine = descriptorsMasculine;
        this.descriptorsFeminine = descriptorsFeminine;

        this.hornTransformationDescription = hornTransformationDescription;
        this.hornBodyDescription = hornBodyDescription;
    }

    public AbstractHornType(Path XMLFile, String author, boolean mod) {
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
                this.coveringType = BodyCoveringType.getBodyCoveringTypeFromId(coreElement.getMandatoryFirstOf("coveringType").getTextContent());

                if (coreElement.getOptionalFirstOf("genericType").isPresent()) {
                    this.generic = Boolean.valueOf(coreElement.getMandatoryFirstOf("genericType").getTextContent());
                } else {
                    this.generic = false;
                }

                this.defaultHornsPerRow = Integer.valueOf(coreElement.getMandatoryFirstOf("defaultHornsPerRow").getTextContent());

                this.transformationName = coreElement.getMandatoryFirstOf("transformationName").getTextContent();

                this.name = coreElement.getMandatoryFirstOf("name").getTextContent();
                this.namePlural = coreElement.getMandatoryFirstOf("namePlural").getTextContent();


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

                this.hornTransformationDescription = coreElement.getMandatoryFirstOf("transformationDescription").getTextContent();
                this.hornBodyDescription = coreElement.getMandatoryFirstOf("bodyDescription").getTextContent();

            } catch (Exception ex) {
                System.getLogger("").log(System.Logger.Level.ERROR, ex);
                System.getLogger("").log(System.Logger.Level.ERROR, "AbstractAntennaType was unable to be loaded from file! (" + XMLFile.getFileName().toString() + ")\n" + ex);
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
    public String getDeterminer(GameCharacter gc) {
        if (gc.getHornRows() == 1) {
            if (gc.getHornsPerRow() == 1) {
                return "a solitary";
            } else if (gc.getHornsPerRow() == 2) {
                return "a pair of";
            } else if (gc.getHornsPerRow() == 3) {
                return "a trio of";
            } else {
                return "a quartet of";
            }

        } else {
            if (gc.getHornsPerRow() == 1) {
                return Util.intToString(gc.getHornRows()) + " vertically-aligned";
            } else if (gc.getHornsPerRow() == 2) {
                return Util.intToString(gc.getHornRows()) + " pairs of";
            } else if (gc.getHornsPerRow() == 3) {
                return Util.intToString(gc.getHornRows()) + " trios of";
            } else {
                return Util.intToString(gc.getHornRows()) + " quartets of";
            }
        }
    }

    public int getDefaultHornsPerRow() {
        return defaultHornsPerRow;
    }

    @Override
    public String getTransformationNameOverride() {
        return transformationName;
    }

    @Override
    public boolean isDefaultPlural(GameCharacter gc) {
        return true;
    }

    @Override
    public String getName(GameCharacter gc) {
        if (isDefaultPlural(gc) && (gc.getHornsPerRow() > 1 || gc.getHornRows() > 1)) {
            return getNamePlural(gc);
        } else {
            return getNameSingular(gc);
        }
    }

    @Override
    public String getNameSingular(GameCharacter gc) {
        return name;
    }

    @Override
    public String getNamePlural(GameCharacter gc) {
        return namePlural;
    }

    @Override
    public String getDescriptor(GameCharacter gc) {
        if (gc.isFeminine()) {
            return Util.randomItemFrom(descriptorsFeminine);
        } else {
            return Util.randomItemFrom(descriptorsMasculine);
        }
    }

    @Override
    public AbstractBodyCoveringType getBodyCoveringType(Body body) {
        return coveringType;
    }

    @Override
    public AbstractRace getRace() {
        return race;
    }

    public boolean isGeneric() {
        return generic;
    }

    @Override
    public TFModifier getTFModifier() {
        return this == HornType.NONE ? TFModifier.REMOVAL : getTFTypeModifier(HornType.getHornTypes(race, false));
    }

    //    @Override
    public String getBodyDescription(GameCharacter owner) {
        return UtilText.parse(owner, hornBodyDescription);
    }


    //    @Override
    public String getTransformationDescription(GameCharacter owner) {
        return UtilText.parse(owner, hornTransformationDescription);
    }
}
