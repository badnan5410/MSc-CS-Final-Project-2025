package object;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class Coin_Gold extends Entity {
    GamePanel gp;
    public static final String objName = "Gold Coin";

    public Coin_Gold(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = TYPE_PICKUP;
        name = objName;
        value = getCoinValue();
        down1 = setup("/objects/coin_gold");
    }

    public boolean useItem(Entity entity) {
        gp.soundEffect(1);
        gp.ui.addMessage("Coin +" + value);
        gp.player.coins += value;
        return true;
    }

    public int getCoinValue() {

        // gold coins worth 100-200 coins
        int min = 5;
        int max = 50;
        return new Random().nextInt((max - min) + 1) + min;
    }
}
