package object;

import entity.Entity;
import main.GamePanel;

public class Sword_Wood extends Entity {

    public Sword_Wood(GamePanel gp) {
        super(gp);
        name = "Wooden Sword";
        down1 = setup("/objects/sword_wooden");
        attackValue = 1;
        description = "[" + name + "]\nJust a wooden sword.";
    }
}
