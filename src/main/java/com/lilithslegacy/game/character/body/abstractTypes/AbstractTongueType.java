package com.lilithslegacy.game.character.body.abstractTypes;

import com.lilithslegacy.controller.xmlParsing.Element;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.Body;
import com.lilithslegacy.game.character.body.coverings.AbstractBodyCoveringType;
import com.lilithslegacy.game.character.body.coverings.BodyCoveringType;
import com.lilithslegacy.game.character.body.types.BodyPartTypeInterface;
import com.lilithslegacy.game.character.body.valueEnums.TongueModifier;
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
public abstract class AbstractTongueType implements BodyPartTypeInterface {

    private boolean mod;
    private boolean fromExternalFile;

    private AbstractBodyCoveringType coveringType;
    private AbstractRace race;

    private int defaultLength;

    private String name;
    private String namePlural;

    private List<String> descriptorsMasculine;
    private List<String> descriptorsFeminine;

    private String tongueBodyDescription;

    private List<TongueModifier> defaultRacialTongueModifiers;

    /**
     * @param coveringType                 What covers this tongue type.
     * @param race                         What race has this tongue type.
     * @param defaultLength                The default length of this tongue, measured in cm.
     * @param name                         The singular name of the tongue. This will usually just be "tongue".
     * @param namePlural                   The plural name of the tongue. This will usually just be "tongues".
     * @param descriptorsMasculine         The descriptors that can be used to describe a masculine form of this tongue type.
     * @param descriptorsFeminine          The descriptors that can be used to describe a feminine form of this tongue type.
     * @param tongueBodyDescription        A sentence or two to describe this tongue type, as seen in the character view screen. It should follow the same format as all of the other entries in the TongueType class.
     * @param defaultRacialTongueModifiers The default modifiers that this tongue type spawns with.
     */
    public AbstractTongueType(AbstractBodyCoveringType coveringType,
                              AbstractRace race,
                              int defaultLength,
                              String name,
                              String namePlural,
                              List<String> descriptorsMasculine,
                              List<String> descriptorsFeminine,
                              String tongueBodyDescription,
                              List<TongueModifier> defaultRacialTongueModifiers) {

        this.coveringType = coveringType;
        this.race = race;

        this.defaultLength = defaultLength;

        this.name = name;
        this.namePlural = namePlural;

        this.descriptorsMasculine = descriptorsMasculine;
        this.descriptorsFeminine = descriptorsFeminine;

        this.tongueBodyDescription = tongueBodyDescription;

        this.defaultRacialTongueModifiers = defaultRacialTongueModifiers;
    }

    public AbstractTongueType(Path XMLFile, String author, boolean mod) {
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

                this.defaultLength = Integer.valueOf(coreElement.getMandatoryFirstOf("defaultLength").getTextContent());

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

                this.tongueBodyDescription = coreElement.getMandatoryFirstOf("bodyDescription").getTextContent();

                this.defaultRacialTongueModifiers = new ArrayList<>();
                if (coreElement.getOptionalFirstOf("defaultTongueModifiers").isPresent()) {
                    for (Element e : coreElement.getMandatoryFirstOf("defaultTongueModifiers").getAllOf("modifier")) {
                        defaultRacialTongueModifiers.add(TongueModifier.valueOf(e.getTextContent()));
                    }
                }

            } catch (Exception ex) {
                System.getLogger("").log(System.Logger.Level.ERROR, ex);
                System.getLogger("").log(System.Logger.Level.ERROR, "AbstractAnusType was unable to be loaded from file! (" + XMLFile.getFileName().toString() + ")\n" + ex);
            }
        }
    }

    public boolean isMod() {
        return mod;
    }

    public boolean isFromExternalFile() {
        return fromExternalFile;
    }

    public int getDefaultLength() {
        return defaultLength;
    }

    @Override
    public String getDeterminer(GameCharacter gc) {
        return "";
    }

    @Override
    public boolean isDefaultPlural(GameCharacter gc) {
        return false;
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
        return UtilText.parse(owner, tongueBodyDescription);
    }


    public List<TongueModifier> getDefaultRacialTongueModifiers() {
        return defaultRacialTongueModifiers;
    }

}
