package object;

import entity.Entity;
import main.GamePanel;

/**
 * Wooden starter shield item.
 * Provides a small defense boost and is intended as an early-game shield.
 */
public class Wood_Shield extends Entity {
    public static final String objName = "Wooden Shield";

    /**
     * Constructs the Wooden Shield and initializes its stats and sprite.
     *
     * @param gp game context
     */
    public Wood_Shield(GamePanel gp) {
        super(gp);
        name = objName;
        down1 = setup("/objects/shield_wooden");
        type = TYPE_SHIELD;
        defenseValue = 1;
        description = "[" + name + "]\nJust a wooden shield.\n+" + defenseValue + " DEF";
        price = 20;
    }
}