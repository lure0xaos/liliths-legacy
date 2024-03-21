package com.lilithslegacy.controller.xmlParsing;

import java.nio.file.Path;

/**
 * @author BlazingMagpie@gmail.com (or ping BlazingMagpie in Discord)
 * @version 0.2.12
 *
 * <p>Exception thrown when XML file fails to load, with details for exact cause.</p>
 * @since 0.2.5.8
 */
public class XMLLoadException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * @param cause Actual exception that was thrown, for example SAXException or XMLMissingTagException.
     */
    public XMLLoadException(Throwable cause) {
        super("XML file failed to load. Cause: " + cause.getMessage(), cause);
    }

    public XMLLoadException(Throwable cause, Path causedByFile) {
        super("XML file (" + causedByFile.getFileName().toString() + "/" + causedByFile.getFileName().toString() + ") failed to load. Cause: " + cause.getMessage(), cause);
    }
}
