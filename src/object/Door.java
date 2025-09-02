package object;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class Door extends Entity {
    GamePanel gp;

    public Door(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = TYPE_OBSTACLE;
        name = "Door";
        down1 = setup("/objects/door");
        collision = true;
        rect.x = 0;
        rect.y = 16;
        rect.width = 48;
        rect.height = 32;
        default_rectX = rect.x;
        default_rectY = rect.y;
        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "You need a key to open this door." + "\n\n\n[press enter]";
    }

    public void interact() {
        startDialogue(this, 0);
    }
}
