package object;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

/**
 * Solid door object that blocks passage until opened by external logic.
 * Characteristics:
 * - Type: {@code TYPE_OBSTACLE} (collidable).
 * - Hitbox: occupies the lower portion of the tile (x=0, y=16, w=48, h=32).
 * - Visual: {@code /objects/door_normal}.
 * - Interaction: shows a hint that a key is required; this class does not perform unlocking itself—actual unlocking (e.g., consuming a key and replacing/removing the door) is handled elsewhere.
 */
public class Door extends Entity {
    GamePanel gp;
    public static final String objName = "Door";

    public Door(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = TYPE_OBSTACLE;
        name = objName;
        down1 = setup("/objects/door_normal");
        collision = true;
        rect.x = 0;
        rect.y = 16;
        rect.width = 48;
        rect.height = 32;
        default_rectX = rect.x;
        default_rectY = rect.y;
        setDialogue();
    }

    /**
     * Initializes the door's interaction dialogue.
     * Shown when the player attempts to interact with a locked door.
     */
    public void setDialogue() {
        dialogues[0][0] = "You need a key to open this door." + "\n\n\n[press enter]";
    }

    /**
     * Handles interaction by presenting the hint dialogue.
     * This method does not modify the door state.
     */
    public void interact() {
        startDialogue(this, 0);
    }
}
