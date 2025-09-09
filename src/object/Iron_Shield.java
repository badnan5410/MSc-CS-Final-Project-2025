package object;

import entity.Entity;
import main.GamePanel;

/**
 * Iron (hero) shield the player can equip.
 * Behavior:
 * Increases the player's defense when equipped and selects the iron guard sprites.
 */
public class Iron_Shield extends Entity {
    public static final String objName = "Hero's Shield";

    /**
     * Creates the iron shield and initializes its sprite and stats.
     *
     * @param gp game context
     */
    public Iron_Shield(GamePanel gp) {
        super(gp);
        name = objName;
        down1 = setup("/objects/shield_iron");
        type = TYPE_SHIELD;
        defenseValue = 3;
        description = "[" + name + "]\nA tough shield, perfect for a\nwarrior.\n+" + defenseValue + " DEF";
        price = 150;
    }
}

