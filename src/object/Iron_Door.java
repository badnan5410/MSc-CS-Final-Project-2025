package object;

import entity.Entity;
import main.GamePanel;

/**
 * Solid, non-openable iron door obstacle.
 * This object blocks movement, has collision enabled,
 * and simply shows a short dialogue when interacted with.
 * Commonly used for puzzle-gated passages (e.g., pressure plates).
 */
public class Iron_Door extends Entity {
    GamePanel gp;
    public static final String objName = "Iron Door";

    /**
     * Constructs an iron door, configures collision bounds,
     * and initializes its dialogue.
     *
     * @param gp the game panel context
     */
    public Iron_Door(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = TYPE_OBSTACLE;
        name = objName;
        down1 = setup("/objects/door_iron");
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
     * Sets the default interaction text shown to the player.
     */
    public void setDialogue() {
        dialogues[0][0] = "It wont' budge." + "\n\n\n[press enter]";
    }

    /**
     * Displays the iron door's dialogue when the player interacts.
     */
    public void interact() {
        startDialogue(this, 0);
    }
}
