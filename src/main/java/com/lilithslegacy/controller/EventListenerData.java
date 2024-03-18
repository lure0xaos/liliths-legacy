package com.lilithslegacy.controller;

import org.w3c.dom.events.EventListener;

/**
 * @author Innoxia
 * @version 0.1.75
 * @since 0.1.075
 */
public class EventListenerData {
    public final String ID;
    public final String type;
    public final EventListener listener;
    public final boolean useCapture;

    public EventListenerData(String ID, String type, EventListener listener, boolean useCapture) {
        this.ID = ID;
        this.type = type;
        this.listener = listener;
        this.useCapture = useCapture;
    }

}
