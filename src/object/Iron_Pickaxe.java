package object;

import entity.Entity;
import main.GamePanel;

public class Iron_Pickaxe extends Entity {
    public static final String objName = "Heavy Pickaxe";

    public Iron_Pickaxe(GamePanel gp) {
        super(gp);
        name = objName;
        down1 = setup("/objects/pickaxe_iron");
        type = TYPE_PICKAXE;
        attackValue = 2;
        value = 8;
        attackArea.width = 32;
        attackArea.height = 30;
        description = "[" + name + "]\nThe perfect tool to break\ndown the toughest walls.\n+" + attackValue + " ATK";
        price = 50;
        knockBackPower = 2;
        motion1_duration = 10;
        motion2_duration = 20;
    }
}
