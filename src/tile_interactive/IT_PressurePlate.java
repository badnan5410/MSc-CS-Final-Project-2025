package tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

/**
 * Interactive tile representing a pressure plate used for simple
 * map puzzles. The plate itself is non-solid and not destructible.
 * It serves as a marker the game can detect when an entity
 * such as a rock is positioned on top of it.
 *
 * Detection and puzzle logic are handled elsewhere
 * (for example, {@link entity.NPC_Rock#detectPressurePlate()}).
 */
public class IT_PressurePlate extends InteractiveTile {
    GamePanel gp;
    public static final String itName = "Pressure Plate";

    /**
     * Creates a pressure plate at the given tile grid position.
     * The collision rectangle is cleared so the tile does not block movement.
     *
     * @param gp  game context
     * @param col world column in tile units
     * @param row world row in tile units
     */
    public IT_PressurePlate(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;
        this.worldX = gp.TILE_SIZE*col;
        this.worldY = gp.TILE_SIZE*row;
        name = itName;
        down1 = setup("/tiles_interactive/pressure_plate");
        destructible = false;

        // make sure its not solid
        rect.x = 0;
        rect.y = 0;
        rect.width = 0;
        rect.height = 0;
        default_rectX = rect.x;
        default_rectY = rect.y;
    }
}
