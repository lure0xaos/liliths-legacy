package com.lilithslegacy.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Innoxia
 * @version 0.1.87
 * @since 0.1.87
 */
public interface XMLSaving {

    /**
     * @param parentElement The parent element from which to load this object from.
     * @param doc           The document to which to load this object from.
     * @return The base element for this object's XML export.
     */
    static <T> T loadFromXML(Element parentElement, Document doc) {
        return loadFromXML(null, parentElement, doc);
    }

    /**
     * @param log           A StringBuilder to write a log to.
     * @param parentElement The parent element from which to load this object from.
     * @param doc           The document to which to load this object from.
     * @return The base element for this object's XML export.
     */
    static <T> T loadFromXML(StringBuilder log, Element parentElement, Document doc) {
        return null;
    }

    /**
     * @param parentElement The parent element to which to save this object to.
     * @param doc           The document to which to save this object to.
     * @return The base element for this object's XML export.
     */
    Element saveAsXML(Element parentElement, Document doc);
}
