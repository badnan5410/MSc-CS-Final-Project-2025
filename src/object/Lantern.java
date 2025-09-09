package object;

import entity.Entity;
import main.GamePanel;

/**
 * A wearable light source that brightens the area around the player.
 * When equipped, it enables a larger light radius for night and dungeon areas.
 */
public class Lantern extends Entity {
    public static final String objName = "Lantern";

    /**
     * Constructs a Lantern item with its sprite, description, price,
     * and light radius configured.
     *
     * @param gp the game panel context
     */
    public Lantern(GamePanel gp) {
        super(gp);
        type = TYPE_LIGHT;
        name = objName;
        down1 = setup("/objects/lantern");
        description = "[Lantern]\nIlluminate your surroundings.";
        price = 100;
        lightRadius = 600;
    }
}
