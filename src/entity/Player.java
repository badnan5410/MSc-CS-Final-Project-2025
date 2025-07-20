package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = (gamePanel.SCREEN_WIDTH/2) - (gamePanel.TRUE_TILE_SIZE/2);
        screenY = (gamePanel.SCREEN_HEIGHT/2) - (gamePanel.TRUE_TILE_SIZE/2);

        rectangle = new Rectangle(15, 18, 24, 27);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gamePanel.TRUE_TILE_SIZE * 23;
        worldY = gamePanel.TRUE_TILE_SIZE * 21;
        speed = 3;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_2.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        if (keyHandler.upKey || keyHandler.downKey || keyHandler.rightKey || keyHandler.leftKey) {
            if (keyHandler.upKey) {
                direction = "up";
            }
            else if (keyHandler.downKey) {
                direction = "down";
            }
            else if (keyHandler.rightKey) {
                direction = "right";
            }
            else if (keyHandler.leftKey) {
                direction = "left";
            }

            // Check Tile Collision
            checkCollision = false;
            gamePanel.collisionHandler.checkTile(this);

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

    public void draw(Graphics2D g2) {
        BufferedImage bufferedImage = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    bufferedImage = up1;
                }
                if (spriteNum == 2) {
                    bufferedImage = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    bufferedImage = down1;
                }
                if (spriteNum == 2) {
                    bufferedImage = down2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    bufferedImage = right1;
                }
                if (spriteNum == 2) {
                    bufferedImage = right2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    bufferedImage = left1;
                }
                if (spriteNum == 2) {
                    bufferedImage = left2;
                }
                break;
        }

        g2.drawImage(bufferedImage, screenX, screenY, gamePanel.TRUE_TILE_SIZE, gamePanel.TRUE_TILE_SIZE, null);
    }
}
