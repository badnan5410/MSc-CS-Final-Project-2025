package entity;

import main.GamePanel;

/**
 * Cutscene stand-in for the player.
 * Used when the camera pans while the real player sprite is hidden.
 * Shares the player's walking sprites and does not update on its own.
 */
public class DecoyPlayer extends Entity {
    public static final String npcName = "Decoy Player";

    /**
     * Creates a decoy with player-style walking frames.
     * Intended for temporary use in scripted scenes.
     */
    public DecoyPlayer(GamePanel gp) {
        super(gp);
        name = npcName;
        getImage();
    }

    /**
     * Loads the player's walking sprites so the decoy matches the player.
     */
    public void getImage() {
        up1 = setup("/player/walking/up_1");
        up2 = setup("/player/walking/up_2");
        down1 = setup("/player/walking/down_1");
        down2 = setup("/player/walking/down_2");
        right1 = setup("/player/walking/right_1");
        right2 = setup("/player/walking/right_2");
        left1 = setup("/player/walking/left_1");
        left2 = setup("/player/walking/left_2");
    }
}
