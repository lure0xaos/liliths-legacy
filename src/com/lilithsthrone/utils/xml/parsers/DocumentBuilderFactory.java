package com.lilithsthrone.utils.xml.parsers;

public final class DocumentBuilderFactory {
    private final javax.xml.parsers.DocumentBuilderFactory documentBuilderFactory;

    private DocumentBuilderFactory(javax.xml.parsers.DocumentBuilderFactory documentBuilderFactory) {
        this.documentBuilderFactory=documentBuilderFactory;
    }

    public static DocumentBuilderFactory newInstance() {
        return new DocumentBuilderFactory(javax.xml.parsers.DocumentBuilderFactory.newInstance());
    }

    /**
     * @return
     * @throws ParserConfigurationException
     */
    public DocumentBuilder newDocumentBuilder() {
        try {
            return new DocumentBuilder(documentBuilderFactory.newDocumentBuilder());
        } catch (javax.xml.parsers.ParserConfigurationException e) {
            throw new ParserConfigurationException(e);
        }
    }
}
