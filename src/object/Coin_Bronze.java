package object;

import entity.Entity;
import main.GamePanel;

public class Coin_Bronze extends Entity {
    GamePanel gp;

    public Coin_Bronze(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = TYPE_PICKUP;
        name = "Bronze Coin";
        value = 1;
        down1 = setup("/objects/coin_bronze");

    }

    public void useItem(Entity entity) {
        gp.soundEffect(1);
        gp.ui.addMessage("Coin +" + value);
        gp.player.coins += value;
    }

}
