package object;

import entity.Entity;
import main.GamePanel;

public class Tent extends Entity {
    GamePanel gp;

    public Tent(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = TYPE_CONSUMABLE;
        name = "Tent";
        down1 = setup("/objects/tent");
        description = "[Tent]\nYou can sleep until\nnext morning.";
        price = 25;
    }

    public boolean useItem(Entity entity) {
        gp.gameState = gp.GS_SLEEP;
        gp.soundEffect(18);
        gp.player.life = gp.player.maxLife;
        gp.player.mana = gp.player.maxMana;
        gp.player.getSleepingImage(down1);
        return true;
    }
}
