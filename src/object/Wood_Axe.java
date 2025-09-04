package object;

import entity.Entity;
import main.GamePanel;

public class Wood_Axe extends Entity {
    public static final String objName = "Wooden Axe";

    public Wood_Axe(GamePanel gp) {
        super(gp);
        name = objName;
        down1 = setup("/objects/axe_wooden");
        type = TYPE_AXE;
        attackValue = 1;
        value = 2;
        attackArea.width = 25;
        attackArea.height = 25;
        description = "[" + name + "]\nJust a wooden axe.\n+" + attackValue + " ATK";
        price = 25;
        knockBackPower = 2;
        motion1_duration = 15;
        motion2_duration = 30;
    }
}
