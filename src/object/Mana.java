package object;

import entity.Entity;
import main.GamePanel;

/**
 * Mana pickup item.
 * When collected or used, restores the target entity's mana to its maximum.
 * Also provides UI icons for full/empty mana pips.
 */
public class Mana extends Entity {
    GamePanel gp;
    public static final String objName = "Mana";

    /**
     * Creates a mana pickup with its sprites, value, and UI images configured.
     *
     * @param gp the game context
     */
    public Mana(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = TYPE_PICKUP;
        name = objName;
        value = 4;
        down1 = setup("/objects/mana_pickup");
        image1 = setup("/objects/mana_full");
        image2 = setup("/objects/mana_empty");
    }

    /**
     * Restores the target's mana to maximum, plays a sound,
     * and adds a UI message.
     *
     * @param entity the entity receiving the effect
     * @return true if the item was consumed
     */
    public boolean useItem(Entity entity) {
        gp.soundEffect(2);
        entity.mana = entity.maxMana;
        gp.ui.addMessage("You have regained full mana!");
        return true;
    }
}
