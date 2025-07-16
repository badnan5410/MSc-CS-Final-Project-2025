package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;


    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        setDefaultValues();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
    }

    public void update() {
        if (keyHandler.upKey) {
            y -= speed;
        }
        if (keyHandler.downKey) {
            y += speed;
        }
        if (keyHandler.rightKey) {
            x += speed;
        }
        if (keyHandler.leftKey) {
            x -= speed;
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.fillRect(x, y, gamePanel.TRUE_TILE_SIZE, gamePanel.TRUE_TILE_SIZE);
    }
}
