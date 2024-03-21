package com.lilithslegacy.world;

import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Vector2i;
import com.lilithslegacy.world.places.GenericPlace;
import javafx.concurrent.Task;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Innoxia
 * @version 0.3.9.3
 * @since 0.1.0
 */
public class Generation extends Task<Boolean> {

    private boolean debug = false;

    public Generation() {
    }

    @Override
    public Boolean call() {
        int maxSize = WorldType.getAllWorldTypes().size();
        AtomicInteger count = new AtomicInteger();

        WorldType.getAllWorldTypes().parallelStream().forEach(wt -> {
            if (debug) {
                System.out.println(wt);
            }
            try {
                Main.game.getWorlds().put(wt, worldGeneration(wt));
            } catch (Exception e) {
                String message = "Exception while generating world type! " + wt.getId();
                System.getLogger("").log(System.Logger.Level.ERROR, message);
                e.printStackTrace(System.err);
            }
            updateProgress(count.incrementAndGet(), maxSize);
        });
        return true;
    }

    public World worldGeneration(AbstractWorldType worldType) {
//		System.out.println(worldType);
        if (worldType.isUsesFile()) {
            try {

                BufferedImage img;
                if (worldType.isFromExternalFile()) {
                    img = ImageIO.read(Files.newInputStream(worldType.getFileLocation()));
                } else {
                    img = ImageIO.read((Files.newInputStream(worldType.getFileLocation())));
                }

                World world = new World(img.getWidth(), img.getHeight(), null, worldType);
//				Main.game.getWorlds().put(worldType, world);

                if (debug)
                    System.out.println(worldType.getName() + " Start-FSPath 1");

                Cell[][] grid = new Cell[img.getWidth()][img.getHeight()];

                for (int i = 0; i < img.getWidth(); i++) {
                    for (int j = 0; j < img.getHeight(); j++) {
                        grid[i][j] = new Cell(worldType, new Vector2i(i, j));
                        if (worldType.isRevealedOnStart()) {
                            grid[i][j].setDiscovered(true);
                            grid[i][j].setTravelledTo(true);

                        } else if (worldType.isDiscoveredOnStart()) {
                            grid[i][j].setDiscovered(true);
                        }
                    }
                }

                if (debug)
                    System.out.println(worldType.getName() + " Start-FSPath 2");

                for (int w = 0; w < img.getWidth(); w++) {
                    for (int h = 0; h < img.getHeight(); h++) {
                        grid[w][img.getHeight() - 1 - h].setPlace(new GenericPlace(worldType.getPlacesMap().get(new Color(img.getRGB(w, h)))), true);
                    }
                }

                if (debug)
                    System.out.println(worldType.getName() + " Start-FSPath 3");

                world.setGrid(grid);

                return world;

            } catch (IOException e) {
                System.getLogger("").log(System.Logger.Level.ERROR, e);
            }

        } else {
            //TODO?
        }
        return null;
    }


}
