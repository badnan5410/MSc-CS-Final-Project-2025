package object;

import entity.Entity;
import main.GamePanel;

/**
 * Title-screen icon representing the Thief class choice.
 * This entity is non-interactive and used by the UI on the class
 * selection screen. Only the down1 sprite is needed.
 */
public class Icon_Thief extends Entity {
    public static final String objName = "Thief-Icon";

    /**
     * Creates the thief icon and loads its sprite.
     *
     * @param gp the game panel context
     */
    public Icon_Thief(GamePanel gp) {
        super(gp);
        name = objName;
        down1 = setup("/objects/thief_icon");
    }
}
