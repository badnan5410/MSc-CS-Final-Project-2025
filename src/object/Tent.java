package object;

import entity.Entity;
import main.GamePanel;

/**
 * Consumable item that lets the player sleep until morning.
 * When used, this item:
 * - Switches the game to the sleep state
 * - Plays a sleep sound effect
 * - Fully restores the player's HP and MP
 * - Swaps the player's sprite to a sleeping image for the duration
 */
public class Tent extends Entity {
    GamePanel gp;
    public static final String objName = "Tent";

    /**
     * Creates a Tent item and initializes its metadata and sprite.
     *
     * @param gp the game context
     */
    public Tent(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = TYPE_CONSUMABLE;
        name = objName;
        down1 = setup("/objects/tent");
        description = "[Tent]\nYou can sleep until\nnext morning.";
        price = 75;
    }

    /**
     * Uses the tent: enters sleep state and restores the player's stats.
     *
     * @param entity the entity attempting to use this item
     * @return true if the item was successfully used
     */
    public boolean useItem(Entity entity) {
        gp.gameState = gp.GS_SLEEP;
        gp.soundEffect(18);
        gp.player.life = gp.player.maxLife;
        gp.player.mana = gp.player.maxMana;
        gp.player.getSleepingImage(down1);
        return true;
    }
}
