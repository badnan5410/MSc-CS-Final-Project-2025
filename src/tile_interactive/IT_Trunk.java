package tile_interactive;

import main.GamePanel;

import java.awt.*;

/**
 * Non-solid trunk tile that remains after a dry tree is destroyed.
 * The trunk is decorative only: its collision rectangle is cleared so it does not block movement.
 */
public class IT_Trunk extends InteractiveTile {
    GamePanel gp;

    /**
     * Creates a trunk at the given tile grid position.
     *
     * @param gp  game context
     * @param col world column in tile units
     * @param row world row in tile units
     */
    public IT_Trunk(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;
        this.worldX = gp.TILE_SIZE*col;
        this.worldY = gp.TILE_SIZE*row;
        down1 = setup("/tiles_interactive/tree_trunk");

        // make sure its not solid
        rect.x = 0;
        rect.y = 0;
        rect.width = 0;
        rect.height = 0;
        default_rectX = rect.x;
        default_rectY = rect.y;
    }
}