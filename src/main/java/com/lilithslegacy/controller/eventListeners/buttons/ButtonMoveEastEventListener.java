package com.lilithslegacy.controller.eventListeners.buttons;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;

import com.lilithslegacy.main.Main;

/**
 * @author Innoxia
 * @version 0.1.69.9
 * @since 0.1.69.9
 */
public class ButtonMoveEastEventListener implements EventListener {

    @Override
    public void handleEvent(Event event) {
        Main.mainController.moveEast();
    }
}
