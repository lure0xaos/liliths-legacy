package com.lilithsthrone.utils.xml.transform;


import javax.xml.transform.TransformerConfigurationException;

public final class TransformerFactory {
    private final javax.xml.transform.TransformerFactory transformerFactory;

    private TransformerFactory(javax.xml.transform.TransformerFactory transformerFactory) {
        this.transformerFactory = transformerFactory;
    }

    public static TransformerFactory newInstance() {
        return new TransformerFactory(javax.xml.transform.TransformerFactory.newInstance());
    }

    public Transformer newTransformer() throws TransformerConfigurationException {
        return new Transformer(transformerFactory.newTransformer());
    }
}
