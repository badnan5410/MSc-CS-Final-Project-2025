package object;

import entity.Entity;
import main.GamePanel;

public class Key extends Entity {

    public Key(GamePanel gp) {
        super(gp);
        name = "Key";
        down1 = setup("/objects/key");
        description = "[" + name + "]\nA mysterious key. I wonder\nwhat it unlocks...";
        price = 50;
    }
}
