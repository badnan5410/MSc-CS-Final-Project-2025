package object;

import entity.Entity;
import main.GamePanel;

public class Icon_Thief extends Entity {

    public Icon_Thief(GamePanel gp) {
        super(gp);
        name = "Thief-Icon";
        down1 = setup("/objects/thief_icon");
    }
}
