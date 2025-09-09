package object;

import entity.Entity;
import main.GamePanel;

/**
 * Title-screen icon representing the Fighter class choice.
 * This is a non-interactive entity used by the UI when rendering the
 * class selection screen. Only {@code down1} is required.
 */
public class Icon_Fighter extends Entity {
    public static final String objName = "Fighter-Icon";

    /**
     * Creates the fighter icon and loads its sprite.
     *
     * @param gp the game panel context
     */
    public Icon_Fighter(GamePanel gp) {
        super(gp);
        name = objName;
        down1 = setup("/objects/fighter_icon");
    }
}
