package object;

import entity.Entity;
import main.GamePanel;

public class Iron_Sword extends Entity {
    public static final String objName = "Hero's Sword";

    public Iron_Sword(GamePanel gp) {
        super(gp);
        name = objName;
        down1 = setup("/objects/sword_iron");
        type = TYPE_SWORD;
        attackValue = 5;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nA powerful sword that can\nslice enemies in half.\n+" + attackValue + " ATK";
        price = 10;
        knockBackPower = 8;
        motion1_duration = 5;
        motion2_duration = 20;
    }
}
