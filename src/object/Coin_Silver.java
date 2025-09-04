package object;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class Coin_Silver extends Entity {
    GamePanel gp;
    public static final String objName = "Silver Coin";

    public Coin_Silver(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = TYPE_PICKUP;
        name = objName;
        value = getCoinValue();
        down1 = setup("/objects/coin_silver");
    }

    public boolean useItem(Entity entity) {
        gp.soundEffect(1);
        gp.ui.addMessage("Coin +" + value);
        gp.player.coins += value;
        return true;
    }

    public int getCoinValue() {

        // silver coins worth 10-50 coins
        int min = 10;
        int max = 50;
        return new Random().nextInt((max - min) + 1) + min;
    }
}
