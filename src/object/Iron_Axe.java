package object;

import entity.Entity;
import main.GamePanel;

/**
 * Iron-tier axe used as both a weapon and a tool.
 */
public class Iron_Axe extends Entity {
    public static final String objName = "Woodcutter's Axe";

    /**
     * Creates the iron axe and initializes its sprite and stats.
     *
     * @param gp game context
     */
    public Iron_Axe(GamePanel gp) {
        super(gp);
        name = objName;
        down1 = setup("/objects/axe_iron");
        type = TYPE_AXE;
        attackValue = 5;
        value = 3;
        attackArea.width = 25;
        attackArea.height = 25;
        description  = "[" + name + "]\nA powerful axe that can cut\ndown the tallest tree.\n+" + attackValue + " ATK";
        price = 120;
        knockBackPower = 3;
        motion1_duration = 15;
        motion2_duration = 30;
    }
}