package object;

import entity.Entity;
import main.GamePanel;

public class Iron_Axe extends Entity {

    public Iron_Axe(GamePanel gp) {
        super(gp);
        name = "Woodcutter's Axe";
        down1 = setup("/objects/axe_iron");
        type = TYPE_AXE;
        attackValue = 6;
        value = 4;
        attackArea.width = 32;
        attackArea.height = 32;
        description  = "[" + name + "]\nA powerful axe that can cut\ndown the tallest tree.\n+" + attackValue + " ATK";
        price = 12;
        knockBackPower = 4;
        motion1_duration = 10;
        motion2_duration = 30;
    }
}