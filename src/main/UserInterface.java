package main;

import object.Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UserInterface {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40;
    Font arial_80B;
    public boolean checkMessage = false;
    public String message = "";
    int messageTimer = 0;
    public boolean isFinished = false;
    public String currentDialogue = "";

    public UserInterface(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
    }

    public void displayMessage(String text) {
        message = text;
        checkMessage = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if (gp.gameState == gp.GS_PLAY) {
            // normal user interface stuff
        }
        if (gp.gameState == gp.GS_PAUSE) {
            drawPauseScreen();
        }
        if (gp.gameState == gp.GS_DIALOGUE) {
            drawDialogueScreen();
        }
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
        String message = "GAME PAUSED";
        int x = centerX(message);
        int y = gp.SCREEN_HEIGHT/2;

        g2.drawString(message, x, y);
    }

    public void drawDialogueScreen() {
        // Dialogue Window
        int x = gp.TILE_SIZE * 2;
        int y = gp.TILE_SIZE / 2;
        int width = gp.SCREEN_WIDTH - (gp.TILE_SIZE * 4);
        int height = gp.TILE_SIZE * 4;
        drawMiniWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28));
        x += gp.TILE_SIZE;
        y += gp.TILE_SIZE;

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawMiniWindow(int x, int y, int width, int height) {
        Color c = new Color(0,0,0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x, y, width, height,25,25);
    }

    public int centerX(String message) {
        int length = (int)g2.getFontMetrics().getStringBounds(message, g2).getWidth();
        int x = gp.SCREEN_WIDTH/2 - length/2;
        return x;
    }
}
