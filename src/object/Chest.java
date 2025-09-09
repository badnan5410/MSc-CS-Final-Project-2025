package object;

import entity.Entity;
import main.GamePanel;

/**
 * A chest placed on the map. It starts closed and collidable.
 * When interacted with, it attempts to give its configured loot to the player.
 * If the player has no space, it shows a message. Once opened, it swaps to the opened sprite and becomes empty on further interactions.
 */
public class Chest extends Entity {
    GamePanel gp;
    public static final String objName = "Chest";

    public Chest(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = TYPE_OBSTACLE;
        name = objName;
        image1 = setup("/objects/chest_closed");
        image2 = setup("/objects/chest_opened");
        down1 = image1;
        collision = true;
        rect.x = 4;
        rect.y = 16;
        rect.width = 40;
        rect.height = 32;
        default_rectX = rect.x;
        default_rectY = rect.y;
    }

    /**
     * Assigns the item that will be awarded when the chest is opened.
     * Also refreshes the dialogue lines to reflect the chosen loot.
     */
    public void setLoot(Entity loot) {
        this.loot = loot;
        setDialogue();
    }

    /**
     * Populates the dialogue variants for full inventory, success, and empty chest.
     */
    public void setDialogue() {
        dialogues[0][0] = "You open the chest and find a " + loot.name + "!\n... but you cannot carry any more!" + "\n\n[press enter]";

        dialogues[1][0] = "You open the chest and find a " + loot.name + "!\nYou obtain the " + loot.name + "!" + "\n\n[press enter]";

        dialogues[2][0] = "It's empty." + "\n\n\n[press enter]";
    }

    /**
     * Handles player interaction:
     * - If unopened, plays a sound and tries to give the loot.
     * - If inventory is full, shows the appropriate message.
     * - If successful, switches to the opened sprite and marks as opened.
     * - If already opened, shows the empty message.
     */
    public void interact() {

        if (!opened) {
            gp.soundEffect(3);

            if (!gp.player.isItemObtainable(loot)) {
                startDialogue(this, 0);
            }

            else {
                startDialogue(this, 1);
                down1 = image2;
                opened = true;
            }
        }

        else {
            startDialogue(this, 2);
        }
    }
}
