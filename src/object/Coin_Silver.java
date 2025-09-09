package object;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

/**
 * Pickup coin that awards a medium, randomized amount of currency.
 * Silver coins are worth between 10 and 50 coins inclusive. When picked up, a sound plays, a UI message appears, and the value is added to the player's coins.
 */
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

    /**
     * Grants the coin's value to the player, plays a sound, and shows a message.
     *
     * @param entity the entity using the item (ignored for coins)
     * @return true to indicate the item was consumed
     */
    public boolean useItem(Entity entity) {
        gp.soundEffect(1);
        gp.ui.addMessage("Coin +" + value);
        gp.player.coins += value;
        return true;
    }

    /**
     * Returns a randomized silver coin value between 10 and 50 inclusive.
     */
    public int getCoinValue() {

        // silver coins worth 10-50 coins
        int min = 10;
        int max = 50;
        return new Random().nextInt((max - min) + 1) + min;
    }
}
