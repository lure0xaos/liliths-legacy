package com.lilithslegacy.game.dialogue;

import java.nio.file.Path;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;

import com.lilithslegacy.controller.xmlParsing.Element;
import com.lilithslegacy.main.Main;

/**
 * @author Innoxia
 * @version 0.4
 * @since 0.4
 */
public class AbstractDialogueFlagValue {

    private String id;
    private int resetHour = -1;

    public AbstractDialogueFlagValue() {
        this(false);
    }

    public AbstractDialogueFlagValue(boolean dailyReset) {
        if (dailyReset) {
            resetHour = 0;
        }
    }

    public static List<AbstractDialogueFlagValue> loadFlagsFromFile(Path XMLFile, String author, boolean mod) {
        if (Files.exists(XMLFile)) {
            try {
                DocumentBuilder fsDocumentBuilder = Main.getDocBuilder();
                Document doc = fsDocumentBuilder.parse(Files.newInputStream(XMLFile));

                // Cast magic:
                doc.getDocumentElement().normalize();

                Element coreElement = Element.getDocumentRootElement(XMLFile); // Loads the document and returns the root element - in AbstractDialogueFlagValue files it's <dialogueFlags>

                List<AbstractDialogueFlagValue> flags = new ArrayList<>();
                for (Element e : coreElement.getAllOf("flag")) {
                    AbstractDialogueFlagValue newFlag = new AbstractDialogueFlagValue(Boolean.valueOf(e.getAttribute("dailyReset".trim())));
                    if (!e.getAttribute("resetHour").isEmpty()) {
                        newFlag.resetHour = Integer.valueOf(e.getAttribute("resetHour"));
                    }
                    newFlag.id = author + "_" + e.getTextContent();
//					System.out.println(newFlag.id);
                    flags.add(newFlag);
                }

                return flags;

            } catch (Exception ex) {
                System.getLogger("").log(System.Logger.Level.ERROR, ex.getMessage(), ex);
                System.getLogger("").log(System.Logger.Level.ERROR, "AbstractDialogueFlagValue was unable to be loaded from file! (" + XMLFile.getFileName().toString() + ")\n" + ex);
            }
        }
        return null;
    }

    public int getResetHour() {
        return resetHour;
    }

    /**
     * @return The id for this flag.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
