package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    KeyHandler kHandler;

    public final int screenX;
    public final int screenY;

    public String playerClass;

    public Player(GamePanel gp, KeyHandler kHandler) {
        super(gp);
        this.kHandler = kHandler;

        screenX = (gp.SCREEN_WIDTH/2) - (gp.TILE_SIZE /2);
        screenY = (gp.SCREEN_HEIGHT/2) - (gp.TILE_SIZE /2);

        rect = new Rectangle(16, 19, 22, 25);
        default_rectX = rect.x;
        default_rectY = rect.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void classBonus(String playerClass) {
        switch (playerClass) {
            case "Fighter":
                // TODO
                break;
            case "Magician":
                maxLife = 8;
                life = maxLife;
                break;
            case "Thief":
                speed = 5;
                break;
        }
    }

    public void setDefaultValues() {
        worldX = gp.TILE_SIZE * 23;
        worldY = gp.TILE_SIZE * 21;
        speed = 3;
        direction = "down";

        // Player Status
        maxLife = 6;
        life = maxLife;
    }

    public void getPlayerImage() {
        up1 = setup("/player/player_up_1");
        up2 = setup("/player/player_up_2");
        down1 = setup("/player/player_down_1");
        down2 = setup("/player/player_down_2");
        right1 = setup("/player/player_right_1");
        right2 = setup("/player/player_right_2");
        left1 = setup("/player/player_left_1");
        left2 = setup("/player/player_left_2");
    }

    public void update() {
        if (kHandler.upKey || kHandler.downKey || kHandler.rightKey || kHandler.leftKey) {
            if (kHandler.upKey) {
                direction = "up";
            }
            else if (kHandler.downKey) {
                direction = "down";
            }
            else if (kHandler.rightKey) {
                direction = "right";
            }
            else if (kHandler.leftKey) {
                direction = "left";
            }

            // Check Tile Collision
            checkCollision = false;
            gp.cHandler.checkTile(this);

            // Check Object Collision
            int objectIndex = gp.cHandler.checkObject(this, true);
            objectPickup(objectIndex);

            // Check NPC Collision
            int npcIndex = gp.cHandler.checkEntity(this, gp.npc);
            npcInteraction(npcIndex);

            // Check Monster Collision
            int monsterIndex = gp.cHandler.checkEntity(this, gp.monster);
            monsterInteraction(monsterIndex);

            // Check Events
            gp.eHandler.checkEvent();

            gp.kHandler.enterPressed = false;

            // Player Moves When No Collision
            if (!checkCollision) {
                switch(direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        // Invincible Counter
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void objectPickup(int i) {
        if (i != -1) {}
    }

    public void npcInteraction(int i) {
        if (i != -1) {
            if (gp.kHandler.enterPressed) {
                gp.gameState = gp.GS_DIALOGUE;
                gp.npc[i].speak();
            }
        }
    }

    public void monsterInteraction(int i) {
        if (i != -1) {
            if (!invincible) {
                life--;
                invincible = true;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
        }

        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, screenX, screenY, null);

        // Reset alpha transparency
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // Debug
        /*g2.setFont(new Font("Arial", Font.PLAIN, 26));
        g2.setColor(Color.white);
        g2.drawString("Invincible:"+invincibleCounter, 10, 400);*/
    }
}
