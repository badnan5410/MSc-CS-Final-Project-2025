package object;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

/**
 * Pickup coin that awards a larger, randomized amount of currency to the player.
 * Gold coins are worth 5–50 coins inclusive. When picked up, it plays a sound, shows a UI message, and adds the value to the player's total coins.
 */
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

    /**
     * Grants the coin's value to the player and shows a message.
     *
     * @param entity the entity using the item (ignored; coin always gives to player)
     * @return true to indicate the item was consumed
     */
    public boolean useItem(Entity entity) {
        gp.soundEffect(1);
        gp.ui.addMessage("Coin +" + value);
        gp.player.coins += value;
        return true;
    }

    /**
     * Returns a randomized gold coin value (5–50 inclusive).
     */
    public int getCoinValue() {

        // gold coins worth 100-200 coins
        int min = 5;
        int max = 50;
        return new Random().nextInt((max - min) + 1) + min;
    }
}
