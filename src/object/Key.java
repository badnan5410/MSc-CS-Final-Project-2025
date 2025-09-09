package object;

import entity.Entity;
import main.GamePanel;

/**
 * Consumable key item used to unlock standard wooden doors.
 * This item is stackable and can be used from the inventory. When used, it searches for a nearby {@code Door} in the direction the user is facing.
 * If found, the door is removed and a message is shown; otherwise a failure message is displayed.
 */
public class Key extends Entity {
    GamePanel gp;
    public static final String objName = "Key";

    /**
     * Creates a new Key item and initializes its properties
     * such as sprite, description, price, and stackable flag.
     *
     * @param gp the game panel context
     */
    public Key(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = TYPE_CONSUMABLE;
        name = objName;
        down1 = setup("/objects/key");
        description = "[" + name + "]\nA mysterious key. I wonder\nwhat it unlocks...";
        price = 50;
        stackable = true;
        setDialogue();
    }

    /**
     * Sets the dialogue lines used on success or failure.
     */
    public void setDialogue() {
        dialogues[0][0] = "You unlocked the door using the key!" + "\n\n\n[press enter]";

        dialogues[1][0] = "What are you doing?" + "\n\n\n[press enter]";
    }

    /**
     * Attempts to unlock a nearby {@code Door}.
     * Looks one tile ahead in the user's current facing direction.
     *
     * @param entity the entity attempting to use the key (usually the player)
     * @return {@code true} if a door was found and unlocked; {@code false} otherwise
     */
    public boolean useItem(Entity entity) {
        int objIndex = getDetected(entity, gp.obj, "Door");

        if (objIndex != -1) {
            startDialogue(this, 0);
            gp.soundEffect(3);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        }

        else {
            startDialogue(this, 1);
            return false;
        }
    }
}
