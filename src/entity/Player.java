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
        speed = 4;
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

        if (direction.equals("up")) {
            bufferedImage = up1;
        }
        else if (direction.equals("down")) {
            bufferedImage = down1;
        }
        else if (direction.equals("right")) {
            bufferedImage = right1;
        }
        else if (direction.equals("left")) {
            bufferedImage = left1;
        }
        g2.drawImage(bufferedImage, x, y, gamePanel.TRUE_TILE_SIZE, gamePanel.TRUE_TILE_SIZE, null);
    }
}
