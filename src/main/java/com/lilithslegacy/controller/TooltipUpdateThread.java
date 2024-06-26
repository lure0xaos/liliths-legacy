package com.lilithslegacy.controller;

import com.lilithslegacy.main.Main;
import javafx.application.Platform;

/**
 * @author Innoxia
 * @version 0.1.7
 * @since 0.1.3
 */
public class TooltipUpdateThread extends Thread {

    public static boolean cancelThreads = false;

    public static void updateToolTip(double xPosition, double yPosition) {
        cancelThreads = false;

        try {
            sleep(50);
        } catch (InterruptedException e) {
            System.getLogger("").log(System.Logger.Level.ERROR, e);
        }

        // While this probably isn't needed, since we run the game on the JavaFX thread most of the time
        // I'm keeping it in just in case since JavaFX isn't threadsafe.
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                if (!cancelThreads) {
                    Main.mainController.getTooltip().show(Main.primaryStage);

                    if (xPosition != -1) {
                        Main.mainController.getTooltip().setAnchorY(yPosition);
                    }
                }
            }
        });
    }
}
