package object;

import entity.Entity;
import main.GamePanel;

/**
 * Mana-restoring consumable.
 *
 * When used, this potion restores half of the target entity's maximum MP.
 * It is stackable, costs 15 coins, and shows a short dialogue message on use.
 *
 * Details:
 * - Name: Mana Potion
 * - Type: CONSUMABLE
 * - Effect: Refresh MP
 */
public class Potion_Blue extends Entity {
    GamePanel gp;
    public static final String objName = "Mana Potion";

    /**
     * Creates a mana potion and initializes its metadata and sprite.
     *
     * @param gp the game context
     */
    public Potion_Blue(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = objName;
        down1 = setup("/objects/potion_blue");
        type = TYPE_CONSUMABLE;
        description = "[" + name + "]\nDrink this potion to recover\nyour mana.";
        price = 15;
        stackable = true;
        setDialogue();
    }

    /**
     * Prepares the dialogue text shown when the potion is consumed.
     * The numeric value is set at use time based on the target's stats.
     */
    public void setDialogue() {
        dialogues[0][0] = "You drink the " + name + "!\n You have recovered your MP!" + "\n\n[press enter]";
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
        entity.mana += entity.maxMana;
        startDialogue(this, 0);
        return true;
    }
}
