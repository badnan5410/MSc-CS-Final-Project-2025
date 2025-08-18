package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity {
    GamePanel gp;

    public InteractiveTile(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;
    }
    public boolean checkTool(Entity entity) {
        return false;
    }

    public void soundEffect() {}

    public InteractiveTile getDestroyedImage() {
        InteractiveTile tile = null;
        return tile;
    }

    public void update() {
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 20) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

}
