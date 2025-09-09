package object;

import entity.Entity;
import main.GamePanel;

/**
 * Heavy pickaxe the player can equip.
 * Behavior:
 * Works as a melee weapon and as the required tool for certain
 * destructible walls.
 */
public class Iron_Pickaxe extends Entity {
    public static final String objName = "Heavy Pickaxe";

    /**
     * Creates the heavy pickaxe and initializes its sprite and stats.
     *
     * @param gp game context
     */
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
