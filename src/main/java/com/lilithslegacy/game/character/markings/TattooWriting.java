package com.lilithslegacy.game.character.markings;

import com.lilithslegacy.controller.xmlParsing.XMLUtil;
import com.lilithslegacy.utils.XMLSaving;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.ColourListPresets;
import com.lilithslegacy.utils.colours.PresetColour;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Innoxia
 * @version 0.2.6
 * @since 0.2.6
 */
public class TattooWriting implements XMLSaving {

    private String text;
    private Colour colour;
    private boolean glow;
    private List<TattooWritingStyle> styles;

    public TattooWriting(String text, Colour colour, boolean glow, TattooWritingStyle... styles) {
        this.text = text;
        this.colour = colour;
        this.glow = glow;
        this.styles = new ArrayList<>();
        Collections.addAll(this.styles, styles);
        this.styles.removeIf(e -> e == null);
    }

    public static List<Colour> getAvailableColours() {
        return ColourListPresets.ALL;
    }

    @Override
    public boolean equals(Object o) {
        if (super.equals(o)) {
            return (o instanceof TattooWriting)
                    && ((TattooWriting) o).getText().equals(this.getText())
                    && ((TattooWriting) o).getColour().equals(this.getColour())
                    && ((TattooWriting) o).isGlow() == glow
                    && ((TattooWriting) o).getStyles().equals(this.getStyles());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + getText().hashCode();
        result = 31 * result + getColour().hashCode();
        result = 31 * result + (isGlow() ? 1 : 0);
        result = 31 * result + getStyles().hashCode();
        return result;
    }

    public Element saveAsXML(Element parentElement, Document doc) {
        Element element = doc.createElement("tattooWriting");
        parentElement.appendChild(element);

        XMLUtil.addAttribute(doc, element, "colour", this.getColour().getId());
        XMLUtil.addAttribute(doc, element, "glow", String.valueOf(this.isGlow()));

        element.appendChild(doc.createCDATASection(this.getText().trim()));

        Element innerElement = doc.createElement("styles");
        element.appendChild(innerElement);
        for (TattooWritingStyle style : this.getStyles()) {
            Element styleElement = doc.createElement("style");
            innerElement.appendChild(styleElement);
            XMLUtil.addAttribute(doc, styleElement, "value", style.toString());
        }

        return element;
    }

    public static TattooWriting loadFromXML(Element parentElement, Document doc) {
        try {
            List<TattooWritingStyle> importedStyles = new ArrayList<>();
            try {
                NodeList stylesList = ((Element) parentElement.getElementsByTagName("styles").item(0)).getElementsByTagName("style");
                for (int i = 0; i < stylesList.getLength(); i++) {
                    Element e = ((Element) stylesList.item(i));

                    TattooWritingStyle style = TattooWritingStyle.valueOf(e.getAttribute("value"));
                    importedStyles.add(style);
                }
            } catch (Exception ex) {
            }

            String text = parentElement.getTextContent();

            TattooWriting tw = new TattooWriting(text.trim(),
                    PresetColour.getColourFromId(parentElement.getAttribute("colour")),
                    Boolean.valueOf(parentElement.getAttribute("glow")));

            tw.styles = importedStyles;

            return tw;

        } catch (Exception ex) {
            System.getLogger("").log(System.Logger.Level.ERROR, "Warning: An instance of TattooWriting was unable to be imported!");
            return null;
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Colour getColour() {
        return colour;
    }

    public boolean isGlow() {
        return glow;
    }

    public List<TattooWritingStyle> getStyles() {
        return styles;
    }

    public void addStyle(TattooWritingStyle style) {
        this.styles.add(style);
    }

    public void removeStyle(TattooWritingStyle style) {
        this.styles.remove(style);
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public void setGlow(boolean glow) {
        this.glow = glow;
    }

}
