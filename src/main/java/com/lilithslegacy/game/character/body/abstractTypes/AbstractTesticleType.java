package com.lilithslegacy.game.character.body.abstractTypes;

import com.lilithslegacy.controller.xmlParsing.Element;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.Body;
import com.lilithslegacy.game.character.body.coverings.AbstractBodyCoveringType;
import com.lilithslegacy.game.character.body.coverings.BodyCoveringType;
import com.lilithslegacy.game.character.body.types.BodyPartTypeInterface;
import com.lilithslegacy.game.character.body.types.FluidType;
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
 * @since 0.3.8.8
 */
public abstract class AbstractTesticleType implements BodyPartTypeInterface {

    private boolean mod;
    private boolean fromExternalFile;

    private AbstractBodyCoveringType coveringType;
    private AbstractRace race;
    private AbstractFluidType fluidType;
    private boolean internal;

    private List<String> names;
    private List<String> namesPlural;
    private List<String> descriptors;

    /**
     * @param coveringType What covers this testicle type (i.e skin/fur/feather type).
     * @param race         What race has this testicle type.
     * @param fluidType    The type of cum that these testicles produce.
     * @param internal     If these testicles are internal by default.
     * @param names        A list of singular names for this testicle type. Pass in null to use generic names.
     * @param namesPlural  A list of plural names for this testicle type. Pass in null to use generic names.
     * @param descriptors  The descriptors that can be used for this testicle type.
     */
    public AbstractTesticleType(AbstractBodyCoveringType coveringType,
                                AbstractRace race,
                                AbstractFluidType fluidType,
                                boolean internal,
                                List<String> names,
                                List<String> namesPlural,
                                List<String> descriptors) {

        this.coveringType = coveringType;
        this.race = race;
        this.fluidType = fluidType;
        this.internal = internal;

        this.names = names;
        this.namesPlural = namesPlural;
        this.descriptors = descriptors;
    }

    public AbstractTesticleType(AbstractBodyCoveringType skinType,
                                AbstractRace race,
                                AbstractFluidType fluidType,
                                boolean internal) {
        this(skinType,
                race,
                fluidType,
                internal,
                null, null, null);
    }

    public AbstractTesticleType(Path XMLFile, String author, boolean mod) {
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

                this.fluidType = FluidType.getFluidTypeFromId(coreElement.getMandatoryFirstOf("fluidType").getTextContent());

                this.internal = Boolean.valueOf(coreElement.getMandatoryFirstOf("internal").getTextContent());

                this.names = new ArrayList<>();
                for (Element e : coreElement.getMandatoryFirstOf("names").getAllOf("name")) {
                    names.add(e.getTextContent());
                }

                this.namesPlural = new ArrayList<>();
                for (Element e : coreElement.getMandatoryFirstOf("namesPlural").getAllOf("name")) {
                    namesPlural.add(e.getTextContent());
                }

                this.descriptors = new ArrayList<>();
                if (coreElement.getOptionalFirstOf("descriptors").isPresent()) {
                    for (Element e : coreElement.getMandatoryFirstOf("descriptors").getAllOf("descriptor")) {
                        descriptors.add(e.getTextContent());
                    }
                }

            } catch (Exception ex) {
                System.getLogger("").log(System.Logger.Level.ERROR, ex);
                System.getLogger("").log(System.Logger.Level.ERROR, "AbstractTesticleType was unable to be loaded from file! (" + XMLFile.getFileName().toString() + ")\n" + ex);
            }
        }
    }

    public boolean isMod() {
        return mod;
    }

    public boolean isFromExternalFile() {
        return fromExternalFile;
    }

    public AbstractFluidType getFluidType() {
        return fluidType;
    }

    public boolean isInternal() {
        return internal;
    }

    @Override
    public String getDeterminer(GameCharacter gc) {
        if (gc.getTesticleCount() == 2) {
            return "a pair of";
        } else if (gc.getTesticleCount() == 3) {
            return "a trio of";
        } else {
            return Util.intToString(gc.getTesticleCount());
        }
    }

    @Override
    public boolean isDefaultPlural(GameCharacter gc) {
        return true;
    }

    @Override
    public String getNameSingular(GameCharacter gc) {
        if (names == null || names.isEmpty()) {
            return UtilText.returnStringAtRandom("ball", "testicle");
        }
        return Util.randomItemFrom(names);
    }

    @Override
    public String getNamePlural(GameCharacter gc) {
        if (namesPlural == null || namesPlural.isEmpty()) {
            return UtilText.returnStringAtRandom("balls", "testicles");
        }
        return Util.randomItemFrom(namesPlural);
    }

    @Override
    public String getDescriptor(GameCharacter gc) {
        if (descriptors == null || descriptors.isEmpty()) {
            return "";
        }
        return Util.randomItemFrom(descriptors);
    }

    @Override
    public AbstractBodyCoveringType getBodyCoveringType(Body body) {
//		if(body!=null) {
//			return body.getTorso().getBodyCoveringType(body);
//		}
        return coveringType;
    }

    @Override
    public AbstractRace getRace() {
        return race;
    }

}
