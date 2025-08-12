package object;

import entity.Entity;
import main.GamePanel;

public class Iron_Sword extends Entity {

    public Iron_Sword(GamePanel gp) {
        super(gp);
        name = "Hero's Sword";
        down1 = setup("/objects/sword_iron");
        type = TYPE_SWORD;
        attackValue = 2;
        attackArea.width = 38;
        attackArea.height = 38;
        description = "[" + name + "]\nA powerful sword that can\nslice enemies in half.\n+" + attackValue + " ATK";
    }
}
