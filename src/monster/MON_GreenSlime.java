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
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 8;
        life = maxLife;
        defaultAttack = 2;
        defaultDefense = 1;
        attack = defaultAttack;
        defense = defaultDefense;
        exp = 2;
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

    public void update() {
        super.update();
        int dx, dy, tileDistance;

        dx = Math.abs(worldX - gp.player.worldX);
        dy = Math.abs(worldY - gp.player.worldY);
        tileDistance = (dx + dy)/gp.TILE_SIZE;

        if (!onPath && tileDistance < 3) {
            int i = new Random().nextInt(100)+1;
            if (i > 50) {
                onPath = true;
            }
        }
        if (onPath && tileDistance > 10) {
            onPath = false;
        }
    }

    public void setAction() {
        if (onPath) {

            // player's position
            int endCol = (gp.player.worldX + gp.player.rect.x)/gp.TILE_SIZE;
            int endRow = (gp.player.worldY + gp.player.rect.y)/gp.TILE_SIZE;
            searchPath(endCol, endRow);

            int i = new Random().nextInt(200)+1;
            if (i > 198 && !projectile.alive && shotCooldownCounter == 30) {
                projectile.set(worldX, worldY, direction, true, this);

                for (int j = 0; j < gp.projectile[1].length; j++) {
                    if (gp.projectile[gp.currentMap][j] == null) {
                        gp.projectile[gp.currentMap][j] = projectile;
                        break;
                    }
                }

                shotCooldownCounter = 0;
            }
        }
        else {
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

    public void damageReaction() {
        movementCounter = 0;
        onPath = true;
    }

    public void checkDrop() {
        int i = new Random().nextInt(100) + 1; // 1–100

        if (i <= 80) {dropItem(new Coin(gp));}
        else if (i <= 90) {dropItem(new Mana(gp));}
        else if (i <= 100) {dropItem(new Heart(gp));}
    }
}
