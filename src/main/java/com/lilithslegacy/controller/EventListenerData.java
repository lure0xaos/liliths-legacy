package com.lilithslegacy.controller;

import org.w3c.dom.events.EventListener;

/**
 * @author Innoxia
 * @version 0.1.75
 * @since 0.1.075
 */
public class EventListenerData {
    public String ID, type;
    public EventListener listener;
    public boolean useCapture;
    public boolean documentHolder;

    public EventListenerData(String ID, String type, EventListener listener, boolean useCapture) {
        this.ID = ID;
        this.type = type;
        this.listener = listener;
        this.useCapture = useCapture;
    }

    public EventListenerData(String id, String jsType, EventListener listener, boolean useCapture, boolean b) {
        this(id, jsType, listener, useCapture);
        documentHolder = b;
    }
}
