package object;

import entity.Entity;
import main.GamePanel;

public class Icon_Thief extends Entity {
    public static final String objName = "Thief-Icon";

    public Icon_Thief(GamePanel gp) {
        super(gp);
        name = objName;
        down1 = setup("/objects/thief_icon");
    }
}
