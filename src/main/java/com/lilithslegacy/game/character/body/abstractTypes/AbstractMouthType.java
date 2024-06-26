package com.lilithslegacy.game.character.body.abstractTypes;

import com.lilithslegacy.controller.xmlParsing.Element;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.Body;
import com.lilithslegacy.game.character.body.coverings.AbstractBodyCoveringType;
import com.lilithslegacy.game.character.body.coverings.BodyCoveringType;
import com.lilithslegacy.game.character.body.types.BodyPartTypeInterface;
import com.lilithslegacy.game.character.body.types.TongueType;
import com.lilithslegacy.game.character.body.valueEnums.OrificeModifier;
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
public abstract class AbstractMouthType implements BodyPartTypeInterface {

    private boolean mod;
    private boolean fromExternalFile;

    private AbstractBodyCoveringType coveringType;
    private AbstractRace race;
    private AbstractTongueType tongueType;

    private List<String> names;
    private List<String> namesPlural;

    private List<String> descriptorsMasculine;
    private List<String> descriptorsFeminine;

    private List<String> lipNames;
    private List<String> lipNamesPlural;

    private boolean lipDescriptorsMasculineSizeAllowed;
    private boolean lipDescriptorsFeminineSizeAllowed;
    private List<String> lipDescriptorsMasculine;
    private List<String> lipDescriptorsFeminine;

    private String mouthBodyDescription;

    List<OrificeModifier> defaultRacialOrificeModifiers;

    public AbstractMouthType(AbstractRace race, AbstractTongueType tongueType) {
        this(BodyCoveringType.MOUTH,
                race,
                tongueType,
                null,
                null,
                Util.newArrayListOfValues(""),
                Util.newArrayListOfValues(""),
                Util.newArrayListOfValues("lip"),
                Util.newArrayListOfValues("lips"),
                Util.newArrayListOfValues(""),
                Util.newArrayListOfValues("soft", "delicate"),
                null,
                Util.newArrayListOfValues());
    }

    /**
     * @param coveringType         What covers this mouth type (i.e skin/fur/feather type). This is never used, as skin type covering mouth is determined by torso covering.
     * @param race                 What race has this mouth type.
     * @param tongueType           The type of tongue this mouth contains.
     * @param names                A list of singular names for this mouth type. Pass in null to use generic names.
     * @param namesPlural          A list of plural names for this mouth type. Pass in null to use generic names.
     * @param descriptorsMasculine The descriptors that can be used to describe a masculine form of this mouth type.
     * @param mouthBodyDescription A sentence or two to describe this mouth type, as seen in the character view screen. It should follow the same format as all of the other entries in the MouthType class. Pass in null to use a generic description.
     * @param descriptorsFeminine  The descriptors that can be used to describe a feminine form of this mouth type.
     */
    public AbstractMouthType(AbstractBodyCoveringType coveringType,
                             AbstractRace race,
                             AbstractTongueType tongueType,
                             List<String> names,
                             List<String> namesPlural,
                             List<String> descriptorsMasculine,
                             List<String> descriptorsFeminine,
                             List<String> lipNames,
                             List<String> lipNamesPlural,
                             List<String> lipDescriptorsMasculine,
                             List<String> lipDescriptorsFeminine,
                             String mouthBodyDescription,
                             List<OrificeModifier> defaultRacialOrificeModifiers) {

        this.coveringType = coveringType;
        this.race = race;
        this.tongueType = tongueType;

        this.names = names;
        this.namesPlural = namesPlural;

        this.descriptorsMasculine = descriptorsMasculine;
        this.descriptorsFeminine = descriptorsFeminine;

        this.lipNames = lipNames;
        this.lipNamesPlural = lipNamesPlural;

        this.lipDescriptorsMasculineSizeAllowed = true;
        this.lipDescriptorsFeminineSizeAllowed = true;

        this.lipDescriptorsMasculine = lipDescriptorsMasculine;
        this.lipDescriptorsFeminine = lipDescriptorsFeminine;

        if (mouthBodyDescription == null || mouthBodyDescription.isEmpty()) {
            this.mouthBodyDescription = "[npc.SheHasFull] [npc.lipSize], [npc.mouthColourPrimary(true)] [npc.lips]"
                    + "#IF(npc.isWearingLipstick())"
                    + "#IF(npc.isPiercedLip()), which have been pierced, and#ELSE, which#ENDIF"
                    + " are currently [npc.materialCompositionDescriptor]"
                    + "#IF(npc.isHeavyMakeup(BODY_COVERING_TYPE_MAKEUP_LIPSTICK) && game.isLipstickMarkingEnabled())"
                    + " a [style.colourPinkDeep(heavy layer)] of"
                    + "#ENDIF"
                    + " [#npc.getLipstick().getFullDescription(npc, true)]."
                    + "#ELSE"
                    + "#IF(npc.isPiercedLip()), which have been pierced#ENDIF."
                    + "#ENDIF"
                    + " [npc.Her] throat is [npc.mouthColourSecondary(true)] in colour.";
        } else {
            this.mouthBodyDescription = mouthBodyDescription;
        }

        if (defaultRacialOrificeModifiers == null) {
            this.defaultRacialOrificeModifiers = new ArrayList<>();
        } else {
            this.defaultRacialOrificeModifiers = defaultRacialOrificeModifiers;
        }
    }

    public AbstractMouthType(Path XMLFile, String author, boolean mod) {
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

                this.tongueType = TongueType.getTongueTypeFromId(coreElement.getMandatoryFirstOf("tongueType").getTextContent());

                this.names = new ArrayList<>();
                for (Element e : coreElement.getMandatoryFirstOf("names").getAllOf("name")) {
                    names.add(e.getTextContent());
                }

                this.namesPlural = new ArrayList<>();
                for (Element e : coreElement.getMandatoryFirstOf("namesPlural").getAllOf("name")) {
                    namesPlural.add(e.getTextContent());
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

                // Lips:

                this.lipNames = new ArrayList<>();
                if (coreElement.getOptionalFirstOf("lipNames").isPresent()) {
                    for (Element e : coreElement.getMandatoryFirstOf("lipNames").getAllOf("name")) {
                        lipNames.add(e.getTextContent());
                    }
                } else {
                    this.lipNames.add("lip");
                }

                this.lipNamesPlural = new ArrayList<>();
                if (coreElement.getOptionalFirstOf("lipNamesPlural").isPresent()) {
                    for (Element e : coreElement.getMandatoryFirstOf("lipNamesPlural").getAllOf("name")) {
                        lipNamesPlural.add(e.getTextContent());
                    }
                } else {
                    this.lipNamesPlural.add("lips");
                }


                this.lipDescriptorsMasculine = new ArrayList<>();
                this.lipDescriptorsMasculineSizeAllowed = true;
                if (coreElement.getOptionalFirstOf("lipDescriptorsMasculine").isPresent()) {
                    this.lipDescriptorsMasculineSizeAllowed = Boolean.valueOf(coreElement.getMandatoryFirstOf("lipDescriptorsMasculine").getAttribute("allowSizeDescriptors"));
                    for (Element e : coreElement.getMandatoryFirstOf("lipDescriptorsMasculine").getAllOf("descriptor")) {
                        lipDescriptorsMasculine.add(e.getTextContent());
                    }
                }

                this.lipDescriptorsFeminine = new ArrayList<>();
                this.lipDescriptorsFeminineSizeAllowed = true;
                if (coreElement.getOptionalFirstOf("lipDescriptorsFeminine").isPresent()) {
                    this.lipDescriptorsFeminineSizeAllowed = Boolean.valueOf(coreElement.getMandatoryFirstOf("lipDescriptorsFeminine").getAttribute("allowSizeDescriptors"));
                    for (Element e : coreElement.getMandatoryFirstOf("lipDescriptorsFeminine").getAllOf("descriptor")) {
                        lipDescriptorsFeminine.add(e.getTextContent());
                    }
                } else {
                    this.lipDescriptorsFeminine = Util.newArrayListOfValues("soft", "delicate");
                }

                // Other:

                this.mouthBodyDescription = coreElement.getMandatoryFirstOf("bodyDescription").getTextContent();

                this.defaultRacialOrificeModifiers = new ArrayList<>();
                if (coreElement.getOptionalFirstOf("defaultOrificeModifiers").isPresent()) {
                    for (Element e : coreElement.getMandatoryFirstOf("defaultOrificeModifiers").getAllOf("modifier")) {
                        defaultRacialOrificeModifiers.add(OrificeModifier.valueOf(e.getTextContent()));
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

    public AbstractTongueType getTongueType() {
        return tongueType;
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
        if (names == null || names.isEmpty()) {
            return UtilText.returnStringAtRandom("mouth");
        }
        return Util.randomItemFrom(names);
    }

    @Override
    public String getNamePlural(GameCharacter gc) {
        if (namesPlural == null || names.isEmpty()) {
            return UtilText.returnStringAtRandom("mouths");
        }
        return Util.randomItemFrom(namesPlural);
    }

    @Override
    public String getDescriptor(GameCharacter gc) {
        if (gc.isFeminine()) {
            return Util.randomItemFrom(descriptorsFeminine);
        } else {
            return Util.randomItemFrom(descriptorsMasculine);
        }
    }

    public String getLipsNameSingular(GameCharacter gc) {
        return Util.randomItemFrom(lipNames);
    }

    public String getLipsNamePlural(GameCharacter gc) {
        return Util.randomItemFrom(lipNamesPlural);
    }

    public boolean isLipsDescriptorSizeAllowed(GameCharacter gc) {
        if (gc.isFeminine()) {
            return lipDescriptorsFeminineSizeAllowed;
        } else {
            return lipDescriptorsMasculineSizeAllowed;
        }
    }

    public List<String> getLipsDescriptors(GameCharacter gc) {
        if (gc.isFeminine()) {
            return (lipDescriptorsFeminine);
        } else {
            return (lipDescriptorsMasculine);
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
        return UtilText.parse(owner, mouthBodyDescription);
    }

    public List<OrificeModifier> getDefaultRacialOrificeModifiers() {
        return defaultRacialOrificeModifiers;
    }
}
