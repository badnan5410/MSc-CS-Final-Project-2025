package object;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class Door extends Entity {

    public Door(GamePanel gp) {
        super(gp);
        name = "Door";
        down1 = setup("/objects/door");
        collision = true;

        rect.x = 0;
        rect.y = 16;
        rect.width = 48;
        rect.height = 32;
        default_rectX = rect.x;
        default_rectY = rect.y;
    }
}
