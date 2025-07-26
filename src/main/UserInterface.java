package main;

import object.Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UserInterface {
    GamePanel gamePanel;
    Font arial_40;
    Font arial_80B;
    BufferedImage keyImage;
    public boolean checkMessage = false;
    public String message = "";
    int messageTimer = 0;
    public boolean isFinished = false;
    double playTime;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public UserInterface(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        Key key = new Key();
        keyImage = key.bufferedImage;
    }

    public void displayMessage(String text) {
        message = text;
        checkMessage = true;
    }

    public void draw(Graphics2D g2) {

        if (isFinished) {
            g2.setFont(arial_40);
            g2.setColor(Color.white);

            String endMessage;
            int endMessageLength;
            int x;
            int y;

            endMessage = "You found the treasure!";
            endMessageLength = (int)g2.getFontMetrics().getStringBounds(endMessage, g2).getWidth();
            x = gamePanel.SCREEN_WIDTH/2 - endMessageLength/2;
            y = gamePanel.SCREEN_HEIGHT/2 - (gamePanel.TRUE_TILE_SIZE*3);
            g2.drawString(endMessage, x, y);

            endMessage = "Time taken: " +decimalFormat.format(playTime) + "s!";
            endMessageLength = (int)g2.getFontMetrics().getStringBounds(endMessage, g2).getWidth();
            x = gamePanel.SCREEN_WIDTH/2 - endMessageLength/2;
            y = gamePanel.SCREEN_HEIGHT/2 + (gamePanel.TRUE_TILE_SIZE*4);
            g2.drawString(endMessage, x, y);

            g2.setFont(arial_80B);
            g2.setColor(Color.yellow);

            endMessage = "Congratulations!";
            endMessageLength = (int)g2.getFontMetrics().getStringBounds(endMessage, g2).getWidth();
            x = gamePanel.SCREEN_WIDTH/2 - endMessageLength/2;
            y = gamePanel.SCREEN_HEIGHT/2 + (gamePanel.TRUE_TILE_SIZE*2);
            g2.drawString(endMessage, x, y);

            gamePanel.gameLoop = null;

        } else {
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gamePanel.TRUE_TILE_SIZE/2, gamePanel.TRUE_TILE_SIZE/2, gamePanel.TRUE_TILE_SIZE, gamePanel.TRUE_TILE_SIZE, null);
            g2.drawString("= " + gamePanel.player.hasKey, 74, 65);

            // Time
            playTime += (double)1/60;
            g2.drawString("Time:" + decimalFormat.format(playTime), gamePanel.TRUE_TILE_SIZE*12, 65);

            // Message
            if (checkMessage) {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, gamePanel.TRUE_TILE_SIZE/2, gamePanel.TRUE_TILE_SIZE*5);

                messageTimer++;

                if (messageTimer > 120) {
                    messageTimer = 0;
                    checkMessage = false;
                }
            }
        }


    }
}
