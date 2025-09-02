package object;

import entity.Entity;
import main.GamePanel;

public class Icon_Fighter extends Entity {
    public static final String objName = "Fighter-Icon";

    public Icon_Fighter(GamePanel gp) {
        super(gp);
        name = objName;
        down1 = setup("/objects/fighter_icon");
    }
}
