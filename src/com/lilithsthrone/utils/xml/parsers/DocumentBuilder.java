package com.lilithsthrone.utils.xml.parsers;

import com.lilithsthrone.utils.io.File;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;

public class DocumentBuilder {
    private final javax.xml.parsers.DocumentBuilder documentBuilder;

    public DocumentBuilder(javax.xml.parsers.DocumentBuilder documentBuilder) {
        this.documentBuilder=documentBuilder;
    }

    public Document parse(File xmlFile) throws IOException, SAXException {
        return documentBuilder.parse(xmlFile.toUri().toString());
    }

    public Document newDocument() {
        return documentBuilder.newDocument();
    }
}
