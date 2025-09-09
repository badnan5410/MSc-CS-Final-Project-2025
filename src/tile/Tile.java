package tile;

import java.awt.image.BufferedImage;

/**
 * Represents a single terrain tile.
 * Holds the rendered sprite and whether the tile blocks movement.
 */
public class Tile {

    // a single tile image
    public BufferedImage image;

    // true if tile is solid
    public boolean collision = false;
}
