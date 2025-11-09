package com.lilithsthrone.utils.xml.transform;

import com.lilithsthrone.utils.io.File;

import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.net.URI;
import java.net.URISyntaxException;

public class Transformer {
    private final javax.xml.transform.Transformer transformer;

    Transformer(javax.xml.transform.Transformer transformer) {
        this.transformer = transformer;
    }

    public void transform(DOMSource domSource, StreamResult streamResult) throws TransformerException {
        String systemId = streamResult.getSystemId();
        if (streamResult.getOutputStream() != null || streamResult.getWriter() != null) {
            transformer.transform(domSource, streamResult);
        } else {
            boolean isUri;
            URI uri = null;
            try {
                uri = new URI(systemId);
                isUri = uri.isAbsolute();
            } catch (URISyntaxException e) {
                isUri = false;
            }
            if (!isUri) {
                File file = new File(systemId);
                uri = file.toUri();
            }
            transformer.transform(domSource, new StreamResult(uri.toASCIIString()));
        }
    }

    public void setOutputProperty(String omitXmlDeclaration, String yes) {
        transformer.setOutputProperty(omitXmlDeclaration, yes);
    }
}
