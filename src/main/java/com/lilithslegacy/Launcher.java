package com.lilithslegacy;

import com.lilithslegacy.main.Main;

/**
 * If JavaFX isn't part of the java package but rather is added separately, you need to wrap the main method in a launcher method, otherwise it will fail.
 *
 * @author Tazze
 * @version 0.4.6.6
 * @since 0.4.6.6
 */
public class Launcher {
    public static void main(String[] args) {
        Main.main(args);
    }
}
