package object;

import entity.Entity;
import main.GamePanel;

public class Wood_Sword extends Entity {

    public Wood_Sword(GamePanel gp) {
        super(gp);
        name = "Wooden Sword";
        down1 = setup("/objects/sword_wooden");
        type = TYPE_SWORD;
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nJust a wooden sword.\n+" + attackValue + " ATK";
    }
}
