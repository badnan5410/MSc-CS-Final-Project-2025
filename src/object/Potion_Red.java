package object;

import entity.Entity;
import main.GamePanel;

/**
 * Health-restoring consumable.
 *
 * When used, this potion restores the target entity's maximum HP.
 * It is stackable, costs 15 coins, and shows a short dialogue message on use.
 *
 * Details:
 * - Name: Health Potion
 * - Type: CONSUMABLE
 * - Effect: Refresh HP
 */
public class Potion_Red extends Entity {
    GamePanel gp;
    public static final String objName = "Health Potion";

    /**
     * Creates a health potion and initializes its metadata and sprite.
     *
     * @param gp the game context
     */
    public Potion_Red(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = objName;
        down1 = setup("/objects/potion_red");
        type = TYPE_CONSUMABLE;
        description = "[" + name + "]\nDrink this potion to recover\nyour health.";
        price = 15;
        stackable = true;
        setDialogue();
    }

    public void setDialogue() {
        dialogues[0][0] = "You drink the " + name + "!\n You have recovered your HP!" + "\n\n[press enter]";
    }

    /**
     * Uses the potion on the specified entity.
     * Sets value to the target's max MP, plays a sound,
     * restores mana, and displays a dialogue message.
     *
     * @param entity the entity receiving the potion effect
     * @return true if the item was consumed
     */
    public boolean useItem(Entity entity) {
        gp.soundEffect(11);
        entity.life += entity.maxLife;
        startDialogue(this, 0);
        return true;
    }
}
