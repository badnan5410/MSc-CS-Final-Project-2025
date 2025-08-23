package main;

import entity.Entity;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import object.*;

public class UserInterface {
    GamePanel gp;
    Graphics2D g2;
    Font maruMonica;
    public boolean checkMessage = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean isFinished = false;
    public String currentDialogue = "";
    public int cNum = 0;
    public int cNumMax;
    public int titleScreenState = 0;
    public int settingsScreenState = 0;
    BufferedImage fighterIcon, magicianIcon, thiefIcon;
    BufferedImage heartFull, heartHalf, heartEmpty;
    BufferedImage manaFull, manaEmpty;
    public int slotCol = 0;
    public int slotRow = 0;

    public UserInterface(GamePanel gp) {
        this.gp = gp;

        // Instantiate icons
        Icon_Fighter fighter = new Icon_Fighter(gp);
        fighterIcon = fighter.down1;

        Icon_Magician magician = new Icon_Magician(gp);
        magicianIcon = magician.down1;

        Icon_Thief thief = new Icon_Thief(gp);
        thiefIcon = thief.down1;

        // Instantiate custom font
        try {
            InputStream is = getClass().getResourceAsStream("/font/MaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Entity heart = new Heart(gp);
        heartFull = heart.image1;
        heartHalf = heart.image2;
        heartEmpty = heart.image3;

        Entity mana = new Mana(gp);
        manaFull = mana.image1;
        manaEmpty = mana.image2;

    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.white);

        if (gp.gameState == gp.GS_TITLE_SCREEN) {drawTitleScreen();}

        if (gp.gameState == gp.GS_PLAY) {
            drawPlayerLife();
            drawMessage();
        }

        if (gp.gameState == gp.GS_PAUSE) {
            drawPauseScreen();
            drawPlayerLife();
        }

        if (gp.gameState == gp.GS_DIALOGUE) {drawDialogueScreen();}

        if (gp.gameState == gp.GS_CHARACTER_STATE) {
            drawCharacterScreen();
            drawInventory();
        }

        if (gp.gameState == gp.GS_SETTINGS_STATE) {drawSettingsScreen();}

        if (gp.gameState == gp.GS_END_STATE) {drawEndScreen();}

    }

    public void drawPlayerLife() {
        int x = gp.TILE_SIZE/2;
        int y = gp.TILE_SIZE/2;
        int i = 0;

        // draw max life
        while (i < gp.player.maxLife/2) {
            g2.drawImage(heartEmpty, x, y, null);
            i++;
            x += gp.TILE_SIZE + 4;
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
            x += gp.TILE_SIZE + 4;
        }

        // draw max mana
        x = gp.TILE_SIZE/2;
        y = gp.TILE_SIZE + 20;
        i = 0;
        while (i < gp.player.maxMana) {
            g2.drawImage(manaEmpty, x, y, null);
            i++;
            x += gp.TILE_SIZE + 4;
        }

        // draw mana
        x = gp.TILE_SIZE/2;
        y = gp.TILE_SIZE + 20;
        i = 0;
        while (i < gp.player.mana) {
            g2.drawImage(manaFull, x, y, null);
            i++;
            x += gp.TILE_SIZE + 4;
        }
    }

    public void drawMessage() {
        int messageX = gp.TILE_SIZE;
        int messageY = gp.TILE_SIZE*4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX+2, messageY+2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
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

    public void drawCharacterScreen() {
        // Create a frame
        final int frameX = gp.TILE_SIZE/2;
        final int frameY = gp.TILE_SIZE/2 - 12;
        final int frameWidth = gp.TILE_SIZE*5;
        final int frameHeight = gp.TILE_SIZE*11;
        drawMiniWindow(frameX, frameY, frameWidth, frameHeight);

        // Text
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gp.TILE_SIZE;
        final int lineHeight = 35;

        // Stats
        g2.drawString("LVL", textX, textY); textY += lineHeight;
        g2.drawString("HP", textX, textY); textY += lineHeight;
        g2.drawString("MP", textX, textY); textY += lineHeight;
        g2.drawString("CLASS", textX, textY); textY += lineHeight;
        g2.drawString("STR", textX, textY); textY += lineHeight;
        g2.drawString("DEX", textX, textY); textY += lineHeight;
        g2.drawString("ATK", textX, textY); textY += lineHeight;
        g2.drawString("DEF", textX, textY); textY += lineHeight;
        g2.drawString("XP", textX, textY); textY += lineHeight;
        g2.drawString("MAX XP", textX, textY); textY += lineHeight;
        g2.drawString("COINS", textX, textY); textY += lineHeight + 20;
        g2.drawString("WEAPON", textX, textY); textY += lineHeight + 15;
        g2.drawString("SHIELD", textX, textY); textY += lineHeight;

        // Values
        int tailX = (frameX + frameWidth) - 30;

        // Reset TextY
        textY = frameY + gp.TILE_SIZE;
        String value;

        value = String.valueOf(gp.player.level);
        textX = rightX(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = rightX(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
        textX = rightX(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = gp.player.playerClass;
        textX = rightX(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = rightX(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = rightX(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = rightX(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = rightX(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = rightX(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = rightX(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coins);
        textX = rightX(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.TILE_SIZE + 15, textY - 14, null);
        textY += gp.TILE_SIZE;

        g2.drawImage(gp.player.currentShield.down1, tailX - gp.TILE_SIZE + 15, textY - 12, null);
    }

    public void drawInventory() {
        // Frame
        int frameX = gp.TILE_SIZE*13 + 24;
        int frameY = gp.TILE_SIZE;
        int frameWidth = gp.TILE_SIZE*6;
        int frameHeight = gp.TILE_SIZE*5;
        drawMiniWindow(frameX, frameY, frameWidth, frameHeight);

        // Slot
        final int slotX_start = frameX + 20;
        final int slotY_start = frameY + 20;
        int slotX = slotX_start;
        int slotY = slotY_start;
        int slotSize = gp.TILE_SIZE+3;

        // Inventory Items
        for (int i = 0; i < gp.player.inventory.size(); i++) {

            // Equip Cursor
            if (gp.player.inventory.get(i) == gp.player.currentWeapon || gp.player.inventory.get(i) == gp.player.currentShield) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.TILE_SIZE, gp.TILE_SIZE, 10, 10);
            }

            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14) {
                slotX = slotX_start;
                slotY += slotSize;
            }
        }

        // Cursor
        int cursorX = slotX_start + (slotSize*slotCol);
        int cursorY = slotY_start + (slotSize*slotRow);
        int cursorWidth = gp.TILE_SIZE;
        int cursorHeight = gp.TILE_SIZE;

        // Draw Cursor
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        // Description Frame
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight + 20;
        int dFrameWidth = frameWidth;
        int dFrameHeight = 170;

        // Description Text
        int textX = dFrameX + 20;
        int textY = dFrameY + gp.TILE_SIZE;
        g2.setFont(g2.getFont().deriveFont(24F));

        int itemIndex = getSlotIndex();
        if (itemIndex < gp.player.inventory.size()) {
            drawMiniWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
            for (String line: gp.player.inventory.get(itemIndex).description.split("\n")) {
                g2.drawString(line, textX, textY);
                textY += 32;
            }
        }
    }

    public void drawEndScreen() {
        gp.stopMusic();
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
        cNumMax = 1;

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        // Shadow
        text = "Game Over!";
        g2.setColor(Color.black);
        x = centerX(text);
        y = gp.TILE_SIZE*4;
        g2.drawString(text, x, y);

        // Main Text
        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);

        // Retry
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = centerX(text);
        y += gp.TILE_SIZE*4;
        g2.drawString(text, x, y);
        if (cNum == 0) {
            g2.drawString("->", x-47, y);
            if (gp.kHandler.enterPressed) {
                gp.gameState = gp.GS_PLAY;
                gp.retry();
                gp.playMusic(0);
            }
        }

        // Main Menu
        text = "Quit";
        x = centerX(text);
        y += 60;
        g2.drawString(text, x, y);
        if (cNum == 1) {
            g2.drawString("->", x-47, y);
            if (gp.kHandler.enterPressed) {
                gp.gameState = gp.GS_TITLE_SCREEN;
                gp.restart();
            }
        }

    }

    public void drawSettingsScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int frameX = gp.TILE_SIZE*6;
        int frameY = gp.TILE_SIZE;
        int frameWidth = gp.TILE_SIZE*8;
        int frameHeight = gp.TILE_SIZE*10;
        drawMiniWindow(frameX, frameY, frameWidth, frameHeight);

        switch(settingsScreenState) {
            case 0: settingsMenuScreenState(frameX, frameY); break;
            case 1: settingsFullScreenState(frameX, frameY); break;
            case 2: settingsControlScreenState(frameX, frameY); break;
            case 3: settingsEndGameConfirmScreenState(frameX, frameY); break;
        }
        gp.kHandler.enterPressed = false;
    }

    public void settingsMenuScreenState(int frameX, int frameY) {
        int textX;
        int textY;
        int lineHeight = gp.TILE_SIZE+12;
        cNumMax = 5;

        // Title
        String text = "Settings";
        textX = centerX(text);
        textY = frameY + gp.TILE_SIZE;
        g2.drawString(text, textX, textY);

        // Full Screen Option
        textX = frameX + gp.TILE_SIZE;
        textY += gp.TILE_SIZE + 24;
        g2.drawString("Full Screen", textX, textY);
        if (cNum == 0) {
            g2.drawString("->", textX-32, textY);
            if (gp.kHandler.enterPressed) {
                gp.fullScreenOn = !gp.fullScreenOn;
                settingsScreenState = 1;
            }
        }

        // Music
        textY += lineHeight;
        g2.drawString("Music", textX, textY);
        if (cNum == 1) {
            g2.drawString("->", textX-32, textY);
        }

        // Sound Effects
        textY += lineHeight;
        g2.drawString("SFX", textX, textY);
        if (cNum == 2) {
            g2.drawString("->", textX-32, textY);
        }

        // Controls
        textY += lineHeight;
        g2.drawString("Controls", textX, textY);
        if (cNum == 3) {
            g2.drawString("->", textX-32, textY);
            if (gp.kHandler.enterPressed) {
                settingsScreenState = 2;
                cNum = 0;
            }
        }

        // End Game
        textY += lineHeight;
        g2.drawString("Leave Game", textX, textY);
        if (cNum == 4) {
            g2.drawString("->", textX-32, textY);
            if (gp.kHandler.enterPressed) {
                settingsScreenState = 3;
                cNum = 0;
            }
        }

        // Back
        textY += lineHeight + gp.TILE_SIZE/2;
        g2.drawString("Close", textX, textY);
        if (cNum == 5) {
            g2.drawString("->", textX-32, textY);
            if (gp.kHandler.enterPressed) {
                cNum = 0;
                gp.gameState = gp.GS_PLAY;
            }
        }

        // Full Screen Checkbox
        textX = frameX + (int)(gp.TILE_SIZE*4.5);
        textY = frameY + gp.TILE_SIZE*2;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, gp.TILE_SIZE/2, gp.TILE_SIZE/2);
        if (gp.fullScreenOn) {g2.fillRect(textX, textY, gp.TILE_SIZE/2, gp.TILE_SIZE/2);}

        // Music Volume
        textY += lineHeight;
        g2.drawRect(textX, textY, 120, gp.TILE_SIZE/2);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        // SFX Volume
        textY += lineHeight;
        g2.drawRect(textX, textY, 120, gp.TILE_SIZE/2);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        gp.config.saveConfig();
    }

    public void settingsFullScreenState(int frameX, int frameY) {
        int textX = frameX + gp.TILE_SIZE;
        int textY = frameY + gp.TILE_SIZE*2;
        cNumMax = 0;

        if (gp.fullScreenOn) {currentDialogue = "You will need to restart the\ngame for full screen mode.";}
        else {currentDialogue = "You will need to restart the\ngame for windowed mode.";}

        for (String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // Back
        textY += gp.TILE_SIZE*5 + 16;
        g2.drawString("Cancel", textX, textY);
        if (cNum == 0) {
            g2.drawString("->", textX-32, textY);
            if (gp.kHandler.enterPressed) {
                settingsScreenState = 0;
            }
        }
    }

    public void settingsControlScreenState(int frameX, int frameY) {
        int textX;
        int textY;
        cNumMax = 0;

        String text = "Controls";
        textX = centerX(text);
        textY = frameY + gp.TILE_SIZE;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.TILE_SIZE;
        textY += gp.TILE_SIZE/2;
        g2.drawString("Move", textX, textY+= gp.TILE_SIZE);
        g2.drawString("Select/Attack", textX, textY+= gp.TILE_SIZE);
        g2.drawString("Shoot", textX, textY+= gp.TILE_SIZE);
        g2.drawString("Inventory", textX, textY+= gp.TILE_SIZE);
        g2.drawString("Pause Game", textX, textY+= gp.TILE_SIZE);
        g2.drawString("Settings", textX, textY+= gp.TILE_SIZE);

        textX = frameX + gp.TILE_SIZE*6;
        textY = frameY + gp.TILE_SIZE + 24;
        g2.drawString("WASD", textX, textY+= gp.TILE_SIZE);
        g2.drawString("ENTER", textX, textY+= gp.TILE_SIZE);
        g2.drawString("F", textX, textY+= gp.TILE_SIZE);
        g2.drawString("C", textX, textY+= gp.TILE_SIZE);
        g2.drawString("P", textX, textY+= gp.TILE_SIZE);
        g2.drawString("ESC", textX, textY+= gp.TILE_SIZE);

        // Back
        textX = frameX + gp.TILE_SIZE;
        textY = frameY + gp.TILE_SIZE*8;
        g2.drawString("Go Back", textX, textY+= gp.TILE_SIZE);
        if (cNum == 0) {
            g2.drawString("->", textX-32, textY);
            if (gp.kHandler.enterPressed) {
                settingsScreenState = 0;
                cNum = 3;
            }
        }

    }

    public void settingsEndGameConfirmScreenState(int frameX, int frameY) {
        int textX;
        int textY = frameY + gp.TILE_SIZE*2;
        cNumMax = 1;

        currentDialogue = "Are you sure you want to\nquit the game?";
        for (String line: currentDialogue.split("\n")) {
            textX = centerX(line);
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // Yes
        String text = "Yes";
        textX = centerX(text);
        textY += gp.TILE_SIZE*3;
        g2.drawString(text, textX, textY);
        if (cNum == 0) {
            g2.drawString("->", textX-32, textY);
            if (gp.kHandler.enterPressed) {
                settingsScreenState = 0;
                gp.gameState = gp.GS_TITLE_SCREEN;
                gp.stopMusic();
                gp.restart();
            }
        }

        // No
        text = "No";
        textX = centerX(text);
        textY += gp.TILE_SIZE;
        g2.drawString(text, textX, textY);
        if (cNum == 1) {
            g2.drawString("->", textX-32, textY);
            if (gp.kHandler.enterPressed) {
                settingsScreenState = 0;
                cNum = 4;
            }
        }

    }

    public int getSlotIndex() {
        int itemIndex = slotCol + (slotRow*5);
        return itemIndex;
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

    public int centerX(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.SCREEN_WIDTH/2 - length/2;
        return x;
    }

    public int rightX(String text, int tailX) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length + 15;
        return x;
    }
}
