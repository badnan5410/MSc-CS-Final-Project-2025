package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity {
    GamePanel gp;
    public boolean destructible = false;

    public InteractiveTile(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;
    }

    public boolean checkTool(Entity entity) {
        boolean isCorrectTool = false;
        return isCorrectTool;
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
