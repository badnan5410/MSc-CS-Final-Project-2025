package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_Wizard extends Entity {

    public NPC_Wizard(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 1;

        getImage();
    }

    public void getImage() {
        up1 = setup("/npc/wizard/wizard_up_1");
        up2 = setup("/npc/wizard/wizard_up_2");
        down1 = setup("/npc/wizard/wizard_down_1");
        down2 = setup("/npc/wizard/wizard_down_2");
        right1 = setup("/npc/wizard/wizard_right_1");
        right2 = setup("/npc/wizard/wizard_right_2");
        left1 = setup("/npc/wizard/wizard_left_1");
        left2 = setup("/npc/wizard/wizard_left_2");
    }

    @Override
    public void setAction() {
        movementCounter++;

        if (movementCounter == 120) {
            Random rand = new Random();
            int i = rand.nextInt(100)+1;

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "right";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }

            movementCounter = 0;
        }
    }
}
