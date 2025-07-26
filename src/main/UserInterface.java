package main;

import object.Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UserInterface {
    GamePanel gp;
    Font arial_40;
    Font arial_80B;
    BufferedImage keyImage;
    public boolean checkMessage = false;
    public String message = "";
    int messageTimer = 0;
    public boolean isFinished = false;
    double playTime;
    DecimalFormat df = new DecimalFormat("#0.00");

    public UserInterface(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        Key key = new Key();
        keyImage = key.image;
    }

    public void displayMessage(String text) {
        message = text;
        checkMessage = true;
    }

    public void draw(Graphics2D g2) {

        if (isFinished) {
            g2.setFont(arial_40);
            g2.setColor(Color.white);

            String endMessage = "You found the treasure!";
            int endMessageLength = (int)g2.getFontMetrics().getStringBounds(endMessage, g2).getWidth();
            int x = gp.SCREEN_WIDTH/2 - endMessageLength/2;
            int y = gp.SCREEN_HEIGHT/2 - (gp.TILE_SIZE *3);
            g2.drawString(endMessage, x, y);

            endMessage = "Time taken: " +df.format(playTime) + "s!";
            endMessageLength = (int)g2.getFontMetrics().getStringBounds(endMessage, g2).getWidth();
            x = gp.SCREEN_WIDTH/2 - endMessageLength/2;
            y = gp.SCREEN_HEIGHT/2 + (gp.TILE_SIZE *4);
            g2.drawString(endMessage, x, y);

            g2.setFont(arial_80B);
            g2.setColor(Color.yellow);

            endMessage = "Congratulations!";
            endMessageLength = (int)g2.getFontMetrics().getStringBounds(endMessage, g2).getWidth();
            x = gp.SCREEN_WIDTH/2 - endMessageLength/2;
            y = gp.SCREEN_HEIGHT/2 + (gp.TILE_SIZE *2);
            g2.drawString(endMessage, x, y);

            gp.gameLoop = null;

        } else {
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.TILE_SIZE /2, gp.TILE_SIZE /2, gp.TILE_SIZE, gp.TILE_SIZE, null);
            g2.drawString("= " + gp.player.hasKey, 74, 65);

            // Time
            playTime += (double)1/60;
            g2.drawString("Time:" + df.format(playTime), gp.TILE_SIZE *11, 65);

            // Message
            if (checkMessage) {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, gp.TILE_SIZE /2, gp.TILE_SIZE *5);

                messageTimer++;

                if (messageTimer > 120) {
                    messageTimer = 0;
                    checkMessage = false;
                }
            }
        }


    }
}
