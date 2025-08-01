package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UserInterface {
    GamePanel gp;
    Graphics2D g2;
    Font maruMonica;
    public boolean checkMessage = false;
    public String message = "";
    int messageTimer = 0;
    public boolean isFinished = false;
    public String currentDialogue = "";
    public int cNum = 0;
    public int titleScreenState = 0;

    public UserInterface(GamePanel gp) {
        this.gp = gp;
        try {
            InputStream is = getClass().getResourceAsStream("/font/MaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayMessage(String text) {
        message = text;
        checkMessage = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.white);

        // Title Screen
        if (gp.gameState == gp.GS_TITLE_SCREEN) {
            drawTitleScreen();
        }
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

    public void drawTitleScreen() {
        // Check titleScreenState
        if (titleScreenState == 0) {
            // Background Colour
            g2.setColor(Color.black);
            g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);

            // Title Name
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 88F));
            String text = "Pixel Adventure Quest";
            int x = centerX(text);
            int y = gp.TILE_SIZE*3;

            // Shadow Colour
            g2.setColor(Color.black);
            g2.drawString(text, x+5, y+5);

            // Main Colour
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            // Player Image
            x = gp.SCREEN_WIDTH/2 - (gp.TILE_SIZE*2)/2;
            y += gp.TILE_SIZE*2;
            g2.drawImage(gp.player.down1, x, y, gp.TILE_SIZE*2, gp.TILE_SIZE*2, null);

            // Menu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "NEW GAME";
            x = centerX(text);
            y += gp.TILE_SIZE*3.5;
            g2.drawString(text, x, y);
            if (cNum == 0) {
                g2.drawString("->", x-gp.TILE_SIZE, y);
            }

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "LOAD GAME";
            x = centerX(text);
            y += gp.TILE_SIZE;
            g2.drawString(text, x, y);
            if (cNum == 1) {
                g2.drawString("->", x-gp.TILE_SIZE, y);
            }

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "QUIT";
            x = centerX(text);
            y += gp.TILE_SIZE;
            g2.drawString(text, x, y);
            if (cNum == 2) {
                g2.drawString("->", x-gp.TILE_SIZE, y);
            }
        }
        else if (titleScreenState == 1) {
            g2.setColor(Color.black);
            g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);

            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42F));

            String text = "Select your class!";
            int x = centerX(text);
            int y = gp.TILE_SIZE*3;
            g2.drawString(text, x, y);


            text = "Fighter";
            x = centerX(text);
            y += gp.TILE_SIZE*3;
            g2.drawString(text, x, y);
            if (cNum == 0) {
                g2.drawString("->", x-gp.TILE_SIZE, y);
            }

            text = "Thief";
            x = centerX(text);
            y += gp.TILE_SIZE;
            g2.drawString(text, x, y);
            if (cNum == 1) {
                g2.drawString("->", x-gp.TILE_SIZE, y);
            }

            text = "Magician";
            x = centerX(text);
            y += gp.TILE_SIZE;
            g2.drawString(text, x, y);
            if (cNum == 2) {
                g2.drawString("->", x-gp.TILE_SIZE, y);
            }

            text = "Go Back";
            x = centerX(text);
            y += gp.TILE_SIZE*3;
            g2.drawString(text, x, y);
            if (cNum == 3) {
                g2.drawString("->", x-gp.TILE_SIZE, y);
            }

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

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
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
