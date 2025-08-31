package object;

import entity.Entity;
import main.GamePanel;

public class Coin extends Entity {
    GamePanel gp;

    public Coin(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = TYPE_PICKUP;
        name = "Gold Coin";
        value = 5;
        down1 = setup("/objects/coin_gold");

    }

    public boolean useItem(Entity entity) {
        gp.soundEffect(1);
        gp.ui.addMessage("Coin +" + value);
        gp.player.coins += value;
        return true;
    }

}
