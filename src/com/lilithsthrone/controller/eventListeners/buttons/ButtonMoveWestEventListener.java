package com.lilithsthrone.controller.eventListeners.buttons;

import com.lilithsthrone.main.Main;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;

/**
 * @since 0.1.69.9
 * @version 0.1.69.9
 * @author Innoxia
 */
public class ButtonMoveWestEventListener implements EventListener {

	@Override
	public void handleEvent(Event event) {
		Main.mainController.moveWest();
	}
}