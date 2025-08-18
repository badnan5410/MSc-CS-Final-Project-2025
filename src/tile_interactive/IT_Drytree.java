package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class IT_Drytree extends InteractiveTile {
    GamePanel gp;

    public IT_Drytree(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.TILE_SIZE*col;
        this.worldY = gp.TILE_SIZE*row;

        down1 = setup("/tiles_interactive/dry_tree");
        destructible = true;
        life = 6;
    }

    public boolean checkTool(Entity entity) {
        boolean isCorrectTool = entity.currentWeapon.type == TYPE_AXE;
        return isCorrectTool;
    }

    public void soundEffect() {
        gp.soundEffect(13);
    }

    public InteractiveTile getDestroyedImage() {
        InteractiveTile tile = new IT_Trunk(gp, worldX/gp.TILE_SIZE, worldY/gp.TILE_SIZE);
        return tile;
    }
}
