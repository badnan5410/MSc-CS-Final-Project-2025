package object;

import entity.Entity;
import main.GamePanel;

/**
 * A heart pickup entity.
 *
 * Behavior:
 * - When collected, fully restores the collector's life to maxLife.
 * - Also provides UI feedback and plays a pickup sound.
 *
 * Sprites:
 * - down1: inventory/world pickup icon.
 * - image1/image2/image3: full/half/empty heart icons used by the UI HUD.
 */
public class Heart extends Entity {
    GamePanel gp;
    public static final String objName = "Heart";

    /**
     * Creates a heart pickup and loads its sprites.
     *
     * @param gp the game panel context
     */
    public Heart(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = TYPE_PICKUP;
        name = objName;
        down1 = setup("/objects/heart_pickup");
        image1 = setup("/objects/heart_full");
        image2 = setup("/objects/heart_half");
        image3 = setup("/objects/heart_empty");
    }

    /**
     * Fully restores the target entity's life.
     * Plays a sound and shows a UI message.
     *
     * @param entity the entity receiving the heal
     * @return true if the item is consumed
     */
    public boolean useItem(Entity entity) {
        gp.soundEffect(2);
        entity.life = entity.maxLife;
        gp.ui.addMessage("You have regained full health!");
        return true;
    }
}
