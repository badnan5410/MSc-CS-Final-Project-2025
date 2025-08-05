package object;

import entity.Entity;
import main.GamePanel;

public class Icon_Magician extends Entity {

    public Icon_Magician(GamePanel gp) {
        super(gp);
        name = "Magician-Icon";
        down1 = setup("/objects/magician_icon");
    }
}
