package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class MON_GreenSlime extends Entity {
    GamePanel gp;

    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Green Slime";
        type = 2;
        speed = 1;
        maxLife = 4;
        life = maxLife;

        rect.x = 3;
        rect.y = 18;
        rect.width = 42;
        rect.height = 30;
        default_rectX = rect.x;
        default_rectY = rect.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("/monster/green_slime_down_1");
        up2 = setup("/monster/green_slime_down_2");
        down1 = setup("/monster/green_slime_down_1");
        down2 = setup("/monster/green_slime_down_2");
        right1 = setup("/monster/green_slime_down_1");
        right2 = setup("/monster/green_slime_down_2");
        left1 = setup("/monster/green_slime_down_1");
        left2 = setup("/monster/green_slime_down_2");
    }

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
                direction = "left";
            }

            movementCounter = 0;
        }
    }
}
