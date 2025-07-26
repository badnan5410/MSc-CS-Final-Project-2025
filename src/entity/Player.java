package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler kHandler;

    public final int screenX;
    public final int screenY;

    public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler kHandler) {
        this.gp = gp;
        this.kHandler = kHandler;

        screenX = (gp.SCREEN_WIDTH/2) - (gp.TILE_SIZE /2);
        screenY = (gp.SCREEN_HEIGHT/2) - (gp.TILE_SIZE /2);

        rect = new Rectangle(16, 19, 22, 25);
        default_rectX = rect.x;
        default_rectY = rect.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.TILE_SIZE * 23;
        worldY = gp.TILE_SIZE * 21;
        speed = 3;
        direction = "down";
    }

    public void getPlayerImage() {
        up1 = setup("player_up_1");
        up2 = setup("player_up_2");
        down1 = setup("player_down_1");
        down2 = setup("player_down_2");
        right1 = setup("player_right_1");
        right2 = setup("player_right_2");
        left1 = setup("player_left_1");
        left2 = setup("player_left_2");
    }

    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.TILE_SIZE, gp.TILE_SIZE);

        }catch(IOException e) {
            e.printStackTrace();
        }
        return image;
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
    }

    public void objectPickup(int i) {
        if (i != -1) {
            String objectName = gp.obj[i].name;

            switch(objectName) {
                case "Key":
                    gp.soundEffect(1);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.displayMessage("You've got a key!");
                    break;
                case "Door":
                    if (hasKey > 0) {
                        gp.soundEffect(3);
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.displayMessage("You unlocked the door!");
                    } else {
                        gp.ui.displayMessage("Door is locked. Find a key.");
                    }
                    break;
                case "Boots":
                    gp.soundEffect(2);
                    speed += 2;
                    gp.obj[i] = null;
                    gp.ui.displayMessage("You got a speed boost!");
                    break;
                case "Chest":
                    gp.ui.isFinished = true;
                    gp.stopMusic();
                    gp.soundEffect(4);
                    break;
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

        g2.drawImage(image, screenX, screenY, null);
    }
}
