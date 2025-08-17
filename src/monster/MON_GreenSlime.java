package monster;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.util.Random;

public class MON_GreenSlime extends Entity {
    GamePanel gp;

    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Green Slime";
        type = TYPE_MONSTER;
        speed = 1;
        maxLife = 8;
        life = maxLife;
        attack = 2;
        defense = 0;
        exp = 2;
        coins = 1;
        projectile = new SlimeBall(gp);

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
        int i = new Random().nextInt(100)+1;
        if (i > 99 && !projectile.alive && shotCooldownCounter == 30) {
            projectile.set(worldX, worldY, direction, true, this);
            gp.projectileList.add(projectile);
            shotCooldownCounter = 0;
        }
    }

    public void damageReaction() {
        movementCounter = 0;
        direction = damageMovement(gp.player.direction);
    }

    public void checkDrop() {
        int i = new Random().nextInt(100) + 1; // 1–100

        if (i <= 50) {                           // 1–50 (50%)
            dropItem(new Coin_Bronze(gp));
        } else if (i <= 72) {                    // 51–72 (22%)
            dropItem(new Heart(gp));
        } else if (i <= 94) {                    // 73–94 (22%)
            dropItem(new Mana(gp));
        } else if (i <= 97) {                    // 95–97 (3%)
            dropItem(new Iron_Shield(gp));
        } else if (i <= 99) {                    // 98–99 (2%)
            dropItem(new Iron_Sword(gp));
        } else {                                 // 100 (1%)
            dropItem(new Iron_Axe(gp));
        }
    }
}
