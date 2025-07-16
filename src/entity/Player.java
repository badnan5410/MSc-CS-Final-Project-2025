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


    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
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

        if (keyHandler.upKey) {
            direction = "up";
            y -= speed;
        }
        else if (keyHandler.downKey) {
            direction = "down";
            y += speed;
        }
        else if (keyHandler.rightKey) {
            direction = "right";
            x += speed;
        }
        else if (keyHandler.leftKey) {
            direction = "left";
            x -= speed;
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

        g2.drawImage(bufferedImage, x, y, gamePanel.TRUE_TILE_SIZE, gamePanel.TRUE_TILE_SIZE, null);
    }
}
