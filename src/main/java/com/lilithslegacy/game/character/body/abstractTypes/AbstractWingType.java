package com.lilithslegacy.game.character.body.abstractTypes;

import com.lilithslegacy.controller.xmlParsing.Element;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.Body;
import com.lilithslegacy.game.character.body.coverings.AbstractBodyCoveringType;
import com.lilithslegacy.game.character.body.coverings.BodyCoveringType;
import com.lilithslegacy.game.character.body.types.BodyPartTypeInterface;
import com.lilithslegacy.game.character.body.types.WingType;
import com.lilithslegacy.game.character.body.valueEnums.WingSize;
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
 * @since 0.3.8.2
 */
public abstract class AbstractWingType implements BodyPartTypeInterface {

    private boolean mod;
    private boolean fromExternalFile;

    private AbstractBodyCoveringType coveringType;
    private AbstractRace race;

    private String transformationName;

    private boolean allowsFlight;
    private boolean generic;

    private WingSize minimumSize;
    private WingSize maximumSize;

    private String name;
    private String namePlural;

    private List<String> descriptorsMasculine;
    private List<String> descriptorsFeminine;

    private String wingTransformationDescription;
    private String wingBodyDescription;

    /**
     * @param coveringType                  What covers this wing type (i.e skin/fur/feather type).
     * @param race                          What race has this wing type.
     * @param allowsFlight                  Whether this wing type is capable of granting the character flight.
     * @param minimumSize                   The minimum size that this wing type is capable of being transformed into.
     * @param maximumSize                   The maximum size that this wing type is capable of being transformed into.
     * @param transformationName            The name that should be displayed when offering this wing type as a transformation. Should be something like "curved" or "straight".
     * @param name                          The singular name of the wing. This will usually just be "wing" or "antler".
     * @param namePlural                    The plural name of the wing. This will usually just be "wings" or "antlers".
     * @param descriptorsMasculine          The descriptors that can be used to describe a masculine form of this wing type.
     * @param descriptorsFeminine           The descriptors that can be used to describe a feminine form of this wing type.
     * @param wingTransformationDescription A paragraph describing a character's wings transforming into this wing type. Parsing assumes that the character already has this wing type and associated skin covering.
     * @param wingBodyDescription           A sentence or two to describe this wing type, as seen in the character view screen. It should follow the same format as all of the other entries in the WingType class.
     */
    public AbstractWingType(
            AbstractBodyCoveringType coveringType,
            AbstractRace race,
            boolean allowsFlight,
            String transformationName,
            String name,
            String namePlural,
            List<String> descriptorsMasculine,
            List<String> descriptorsFeminine,
            String wingTransformationDescription,
            String wingBodyDescription) {

        this.coveringType = coveringType;
        this.race = race;

        this.generic = false;
        this.allowsFlight = allowsFlight;

        this.minimumSize = WingSize.ZERO_TINY;
        this.maximumSize = WingSize.FOUR_HUGE;

        this.transformationName = transformationName;
        this.name = name;
        this.namePlural = namePlural;

        this.descriptorsMasculine = descriptorsMasculine;
        this.descriptorsFeminine = descriptorsFeminine;

        this.wingTransformationDescription = wingTransformationDescription;
        this.wingBodyDescription = wingBodyDescription;
    }

    public AbstractWingType(Path XMLFile, String author, boolean mod) {
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

                this.transformationName = coreElement.getMandatoryFirstOf("transformationName").getTextContent();

                this.allowsFlight = Boolean.valueOf(coreElement.getMandatoryFirstOf("allowsFlight").getTextContent());

                if (coreElement.getOptionalFirstOf("genericType").isPresent()) {
                    this.generic = Boolean.valueOf(coreElement.getMandatoryFirstOf("genericType").getTextContent());
                } else {
                    this.generic = false;
                }

                this.minimumSize = WingSize.ZERO_TINY;
                if (coreElement.getOptionalFirstOf("minimumSize").isPresent()) {
                    this.minimumSize = WingSize.valueOf(coreElement.getMandatoryFirstOf("minimumSize").getTextContent());
                }
                this.maximumSize = WingSize.FOUR_HUGE;
                if (coreElement.getOptionalFirstOf("maximumSize").isPresent()) {
                    this.maximumSize = WingSize.valueOf(coreElement.getMandatoryFirstOf("maximumSize").getTextContent());
                }

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

                this.wingTransformationDescription = coreElement.getMandatoryFirstOf("transformationDescription").getTextContent();
                this.wingBodyDescription = coreElement.getMandatoryFirstOf("bodyDescription").getTextContent();

            } catch (Exception ex) {
                System.getLogger("").log(System.Logger.Level.ERROR, ex);
                System.getLogger("").log(System.Logger.Level.ERROR, "AbstractArmType was unable to be loaded from file! (" + XMLFile.getFileName().toString() + ")\n" + ex);
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
        return "a pair of";
    }

    public boolean allowsFlight() {
        return allowsFlight;
    }

    public boolean isGeneric() {
        return generic;
    }

    public WingSize getMinimumSize() {
        return minimumSize;
    }

    public WingSize getMaximumSize() {
        return maximumSize;
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

    @Override
    public TFModifier getTFModifier() {
        return this == WingType.NONE ? TFModifier.REMOVAL : getTFTypeModifier(WingType.getWingTypes(race));
    }

    //	@Override
    public String getBodyDescription(GameCharacter owner) {
        return UtilText.parse(owner, wingBodyDescription);
    }


    //	@Override
    public String getTransformationDescription(GameCharacter owner) {
        return UtilText.parse(owner, wingTransformationDescription);
    }
}
