package object;

import entity.Entity;
import main.GamePanel;

public class Icon_Fighter extends Entity {

    public Icon_Fighter(GamePanel gp) {
        super(gp);
        name = "Fighter-Icon";
        down1 = setup("/objects/fighter_icon");
    }
}
