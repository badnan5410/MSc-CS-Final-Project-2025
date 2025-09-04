package object;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class Coin_Copper extends Entity {
    GamePanel gp;
    public static final String objName = "Copper Coin";

    public Coin_Copper(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = TYPE_PICKUP;
        name = objName;
        value = getCoinValue();
        down1 = setup("/objects/coin_copper");
    }

    public boolean useItem(Entity entity) {
        gp.soundEffect(1);
        gp.ui.addMessage("Coin +" + value);
        gp.player.coins += value;
        return true;
    }

    public int getCoinValue() {

        // copper coins worth 1-5 coins
        int min = 1;
        int max = 5;
        return new Random().nextInt((max - min) + 1) + min;
    }

}
