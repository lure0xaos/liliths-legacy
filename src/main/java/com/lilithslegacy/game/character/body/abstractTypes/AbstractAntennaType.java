package com.lilithslegacy.game.character.body.abstractTypes;

import com.lilithslegacy.controller.xmlParsing.Element;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.Body;
import com.lilithslegacy.game.character.body.coverings.AbstractBodyCoveringType;
import com.lilithslegacy.game.character.body.coverings.BodyCoveringType;
import com.lilithslegacy.game.character.body.types.BodyPartTypeInterface;
import com.lilithslegacy.game.character.race.AbstractRace;
import com.lilithslegacy.game.character.race.Race;
import com.lilithslegacy.game.dialogue.utils.UtilText;
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
 * @since 0.3.7
 */
public abstract class AbstractAntennaType implements BodyPartTypeInterface {

    private boolean mod;
    private boolean fromExternalFile;

    private int defaultAntennaePerRow;

    private String name;
    private String namePlural;
    private String transformationName;

    private AbstractBodyCoveringType coveringType;
    private AbstractRace race;


    private List<String> descriptorsMasculine;
    private List<String> descriptorsFeminine;

    private String antennaTransformationDescription;
    private String antennaBodyDescription;

    /**
     * @param coveringType                     What covers this antenna type (i.e skin/fur/feather type).
     * @param race                             What race has this antenna type.
     * @param transformationName               The name that should be displayed when offering this antenna type as a transformation. Should be something like "curved" or "straight".
     * @param name                             The singular name of the antenna. This will usually just be "antenna" or "antler".
     * @param namePlural                       The plural name of the antenna. This will usually just be "antennae" or "antlers".
     * @param descriptorsMasculine             The descriptors that can be used to describe a masculine form of this antenna type.
     * @param descriptorsFeminine              The descriptors that can be used to describe a feminine form of this antenna type.
     * @param antennaTransformationDescription A paragraph describing a character's antennae transforming into this antenna type. Parsing assumes that the character already has this antenna type and associated skin covering.
     * @param antennaBodyDescription           A sentence or two to describe this antenna type, as seen in the character view screen. It should follow the same format as all of the other entries in the AntennaType class.
     */
    public AbstractAntennaType(
            AbstractBodyCoveringType coveringType,
            AbstractRace race,
            String transformationName,
            String name,
            String namePlural,
            List<String> descriptorsMasculine,
            List<String> descriptorsFeminine,
            String antennaTransformationDescription,
            String antennaBodyDescription) {
        this.mod = false;
        this.fromExternalFile = false;

        this.coveringType = coveringType;
        this.race = race;

        this.transformationName = transformationName;

        this.name = name;
        this.namePlural = namePlural;

        this.defaultAntennaePerRow = 2;

        this.descriptorsMasculine = descriptorsMasculine;
        this.descriptorsFeminine = descriptorsFeminine;

        this.antennaTransformationDescription = antennaTransformationDescription;
        this.antennaBodyDescription = antennaBodyDescription;
    }

    public AbstractAntennaType(Path XMLFile, String author, boolean mod) {
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

                this.defaultAntennaePerRow = Integer.valueOf(coreElement.getMandatoryFirstOf("defaultAntennaePerRow").getTextContent());

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

                this.antennaTransformationDescription = coreElement.getMandatoryFirstOf("transformationDescription").getTextContent();
                this.antennaBodyDescription = coreElement.getMandatoryFirstOf("bodyDescription").getTextContent();

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
    public String getTransformationNameOverride() {
        return transformationName;
    }

    public int getDefaultAntennaePerRow() {
        return defaultAntennaePerRow;
    }

    @Override
    public String getDeterminer(GameCharacter gc) {
        if (gc.getAntennaRows() == 1) {
            if (gc.getAntennaePerRow() == 1) {
                return "a solitary";
            } else if (gc.getAntennaePerRow() == 2) {
                return "a pair of";
            } else if (gc.getAntennaePerRow() == 3) {
                return "a trio of";
            } else {
                return "a quartet of";
            }

        } else {
            if (gc.getAntennaePerRow() == 1) {
                return Util.intToString(gc.getAntennaRows()) + " vertically-aligned";
            } else if (gc.getAntennaePerRow() == 2) {
                return Util.intToString(gc.getAntennaRows()) + " pairs of";
            } else if (gc.getAntennaePerRow() == 3) {
                return Util.intToString(gc.getAntennaRows()) + " trios of";
            } else {
                return Util.intToString(gc.getAntennaRows()) + " quartets of";
            }
        }
    }

    @Override
    public boolean isDefaultPlural(GameCharacter gc) {
        return true;
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

    //	@Override
    public String getBodyDescription(GameCharacter owner) {
        return UtilText.parse(owner, antennaBodyDescription);
    }

    //	@Override
    public String getTransformationDescription(GameCharacter owner) {
        return UtilText.parse(owner, antennaTransformationDescription);
    }
}
