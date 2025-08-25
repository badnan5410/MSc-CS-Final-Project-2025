package object;

import entity.Entity;
import main.GamePanel;

public class Wood_Axe extends Entity {

    public Wood_Axe(GamePanel gp) {
        super(gp);
        name = "Basic Axe";
        down1 = setup("/objects/axe_wooden");
        type = TYPE_AXE;
        attackValue = 2;
        value = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\nJust a wooden axe.\n+" + attackValue + " ATK";
        price = 8;
    }
}
