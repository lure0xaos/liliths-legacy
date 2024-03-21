package com.lilithslegacy.controller.eventListeners.tooltips;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;

import com.lilithslegacy.controller.TooltipUpdateThread;
import com.lilithslegacy.main.Main;

/**
 * Hides the tooltip.
 *
 * @author Innoxia
 * @version 0.1.3
 * @since 0.1.0
 */
public class TooltipHideEventListener implements EventListener {
    @Override
    public void handleEvent(Event event) {
        TooltipUpdateThread.cancelThreads = true;
        Main.mainController.getTooltip().hide();
    }
}
