package tile_interactive;

import entity.Entity;
import main.GamePanel;
import object.Coin;
import object.Heart;
import object.Mana;

import java.awt.*;
import java.util.Random;

public class IT_DestructibleWall extends InteractiveTile {
    GamePanel gp;

    public IT_DestructibleWall(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.TILE_SIZE*col;
        this.worldY = gp.TILE_SIZE*row;

        down1 = setup("/tiles_interactive/wall_destructible");
        destructible = true;
        life = 35;
    }

    public boolean checkTool(Entity entity) {
        return entity.currentWeapon.type == TYPE_PICKAXE;
    }

    public void soundEffect() {
        gp.soundEffect(24);
    }

    public InteractiveTile getDestroyedImage() {
        InteractiveTile tile = null;
        return tile;
    }

    public Color getParticleColor() {return new Color(65, 65, 65);}

    public int getParticleSize() {return 6;}

    public int getParticleSpeed() {return 1;}

    public int getParticleMaxLife() {return 20;}

    public void checkDrop() {
        int i = new Random().nextInt(200) + 1; // 1–100

        if (i == 1) {dropItem(new Coin(gp));}
    }
}
