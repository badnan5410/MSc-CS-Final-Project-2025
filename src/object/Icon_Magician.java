package object;

import entity.Entity;
import main.GamePanel;

/**
 * Title-screen icon representing the Magician class choice.
 * This entity is non-interactive and used by the UI on the class
 * selection screen. Only the down1 sprite is needed.
 */
public class Icon_Magician extends Entity {
    public static final String objName = "Magician-Icon";

    /**
     * Creates the magician icon and loads its sprite.
     *
     * @param gp the game panel context
     */
    public Icon_Magician(GamePanel gp) {
        super(gp);
        name = objName;
        down1 = setup("/objects/magician_icon");
    }
}
