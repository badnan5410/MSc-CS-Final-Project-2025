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
        attackValue = 3;
        value = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\nJust a wooden axe.\n+" + attackValue + " ATK";
        price = 6;
        knockBackPower = 4;
        motion1_duration = 5;
        motion2_duration = 25;
    }
}
