package object;

import entity.Entity;
import main.GamePanel;

public class Icon_Magician extends Entity {
    public static final String objName = "Magician-Icon";

    public Icon_Magician(GamePanel gp) {
        super(gp);
        name = objName;
        down1 = setup("/objects/magician_icon");
    }
}
