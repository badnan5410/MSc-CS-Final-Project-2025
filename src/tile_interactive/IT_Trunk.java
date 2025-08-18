package tile_interactive;

import main.GamePanel;

import java.awt.*;

public class IT_Trunk extends InteractiveTile {
    GamePanel gp;

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