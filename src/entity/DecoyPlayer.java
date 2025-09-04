package entity;

import main.GamePanel;

public class DecoyPlayer extends Entity {
    public static final String npcName = "Decoy Player";

    public DecoyPlayer(GamePanel gp) {
        super(gp);
        name = npcName;
        getImage();
    }

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
