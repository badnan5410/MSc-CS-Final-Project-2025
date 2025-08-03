package main;

import object.Icon_Fighter;
import object.Icon_Magician;
import object.Icon_Thief;
import object.Heart;
import object.ParentObject;

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
    BufferedImage fighterIcon, magicianIcon, thiefIcon;
    BufferedImage heartFull, heartHalf, heartEmpty;

    public UserInterface(GamePanel gp) {
        this.gp = gp;

        // Instantiate icons
        Icon_Fighter fighter = new Icon_Fighter(gp);
        fighterIcon = fighter.image1;

        Icon_Magician magician = new Icon_Magician(gp);
        magicianIcon = magician.image1;

        Icon_Thief thief = new Icon_Thief(gp);
        thiefIcon = thief.image1;

        // Instantiate custom font
        try {
            InputStream is = getClass().getResourceAsStream("/font/MaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ParentObject heart = new Heart(gp);
        heartFull = heart.image1;
        heartHalf = heart.image2;
        heartEmpty = heart.image3;
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
            drawPlayerLife();
        }
        if (gp.gameState == gp.GS_PAUSE) {
            drawPauseScreen();
            drawPlayerLife();
        }
        if (gp.gameState == gp.GS_DIALOGUE) {
            drawDialogueScreen();
        }
    }

    public void drawPlayerLife() {
        int x = gp.TILE_SIZE/2;
        int y = gp.TILE_SIZE/2;
        int i = 0;

        // draw max life
        while (i < gp.player.maxLife/2) {
            g2.drawImage(heartEmpty, x, y, null);
            i++;
            x += gp.TILE_SIZE;
        }

        // reset values
        x = gp.TILE_SIZE/2;
        y = gp.TILE_SIZE/2;
        i = 0;

        // draw current life
        while (i < gp.player.life) {
            g2.drawImage(heartHalf, x, y, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heartFull, x, y, null);
            }
            i++;
            x += gp.TILE_SIZE;
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
            g2.setColor(Color.gray);
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
                g2.drawImage(fighterIcon, x+(gp.TILE_SIZE*3), y-gp.TILE_SIZE+5, gp.TILE_SIZE, gp.TILE_SIZE, null);
            }

            text = "Magician";
            x = centerX(text);
            y += gp.TILE_SIZE;
            g2.drawString(text, x, y);
            if (cNum == 1) {
                g2.drawString("->", x-gp.TILE_SIZE, y);
                g2.drawImage(magicianIcon, x+(gp.TILE_SIZE*4), y-gp.TILE_SIZE+5, gp.TILE_SIZE, gp.TILE_SIZE, null);
            }

            text = "Thief";
            x = centerX(text);
            y += gp.TILE_SIZE;
            g2.drawString(text, x, y);
            if (cNum == 2) {
                g2.drawString("->", x-gp.TILE_SIZE, y);
                g2.drawImage(thiefIcon, x+(gp.TILE_SIZE*3), y-gp.TILE_SIZE+5, gp.TILE_SIZE, gp.TILE_SIZE, null);
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
