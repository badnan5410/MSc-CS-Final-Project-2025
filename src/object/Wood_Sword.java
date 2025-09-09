package object;

import entity.Entity;
import main.GamePanel;

/**
 * Wooden starter sword item.
 * Provides light damage and a small knockback. Intended as the player's early weapon.
 */
public class Wood_Sword extends Entity {
    public static final String objName = "Wooden Sword";

    /**
     * Constructs the Wooden Sword and initializes all item stats and visuals.
     *
     * @param gp game context
     */
    public Wood_Sword(GamePanel gp) {
        super(gp);
        name = objName;
        down1 = setup("/objects/sword_wooden");
        type = TYPE_SWORD;
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\nJust a wooden sword.\n+" + attackValue + " ATK";
        price = 10;
        knockBackPower = 1;
        motion1_duration = 5;
        motion2_duration = 25;
    }
}
