package tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class IT_PressurePlate extends InteractiveTile {
    GamePanel gp;
    public static final String itName = "Pressure Plate";

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
