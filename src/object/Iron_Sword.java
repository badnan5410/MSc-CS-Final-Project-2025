package object;

import entity.Entity;
import main.GamePanel;

/**
 * High-tier sword the player can equip.
 */
public class Iron_Sword extends Entity {
    public static final String objName = "Hero's Sword";

    /**
     * Constructs the Hero's Sword and initializes its sprite and stats.
     *
     * @param gp game context
     */
    public Iron_Sword(GamePanel gp) {
        super(gp);
        name = objName;
        down1 = setup("/objects/sword_iron");
        type = TYPE_SWORD;
        attackValue = 4;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\nA powerful sword that can\nslice enemies in half.\n+" + attackValue + " ATK";
        price = 200;
        knockBackPower = 2;
        motion1_duration = 5;
        motion2_duration = 25;
    }
}
