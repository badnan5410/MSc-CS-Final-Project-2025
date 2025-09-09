package object;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

/**
 * Pickup coin that awards a small, randomized amount of currency to the player.
 * When spawned, its {@code value} is initialized to a random number between 1 and 5.
 * On use, it plays a sound, shows a UI message, and adds that amount to the player's coins.
 */
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
     * Returns a randomized copper coin value.
     * Copper coins are worth 1–5 coins inclusive.
     */
    public int getCoinValue() {

        // copper coins worth 1-5 coins
        int min = 1;
        int max = 5;
        return new Random().nextInt((max - min) + 1) + min;
    }
}
