package main;

import entity.Entity;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import object.*;

/**
 * Renders all on-screen UI for the game.
 * Decides what to draw based on the current game state.
 * Also manages transient UI state like toast messages and dialogue text.
 */
public class UserInterface {
    GamePanel gp;
    Graphics2D g2;

    // primary UI font
    public Font maruMonica;

    // flag for messsage flow control (used elsewhere)
    public boolean checkMessage = false;

    // queued HUD messages (toast-style)
    ArrayList<String> message = new ArrayList<>();

    // per-message life counters in frames
    ArrayList<Integer> messageCounter = new ArrayList<>();

    // dialogue rendering state
    public boolean isFinished = false;
    public String currentDialogue = "";

    // generic menu cursor index and bounds
    public int cNum = 0;
    public int cNumMax;

    // sub-state selectors for specific screens
    public int titleScreenState = 0;
    public int settingsScreenState = 0;
    public int tradeScreenState = 0;

    // cached UI icons and HUD sprites
    BufferedImage fighterIcon, magicianIcon, thiefIcon;
    BufferedImage heartFull, heartHalf, heartEmpty;
    BufferedImage manaFull, manaEmpty;
    BufferedImage coin;

    // inventory grid cursors
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;

    // frame counter for timed UI effects
    int counter = 0;

    // current dialogue speaker entity
    public Entity npc;

    // typewriter effect state for dialogue
    int charIndex = 0;
    String combinedText = "";

    /**
     * Creates the UI system and loads HUD assets (icons, hearts, mana, coin).
     * Also loads the custom font from the classpath.
     *
     * @param gp game context
     */
    public UserInterface(GamePanel gp) {
        this.gp = gp;

        // instantiate icons
        Icon_Fighter fighter = new Icon_Fighter(gp);
        fighterIcon = fighter.down1;

        Icon_Magician magician = new Icon_Magician(gp);
        magicianIcon = magician.down1;

        Icon_Thief thief = new Icon_Thief(gp);
        thiefIcon = thief.down1;

        // instantiate custom font
        try {
            InputStream is = getClass().getResourceAsStream("/font/MaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        }

        catch (FontFormatException e) {
            e.printStackTrace();
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        Entity heart = new Heart(gp);
        heartFull = heart.image1;
        heartHalf = heart.image2;
        heartEmpty = heart.image3;

        Entity mana = new Mana(gp);
        manaFull = mana.image1;
        manaEmpty = mana.image2;

        Entity copperCoin = new Coin_Copper(gp);
        coin = copperCoin.down1;
    }

    /**
     * Adds a transient HUD message (toast).
     * Message will fade out after a timed duration.
     *
     * @param text message string
     */
    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    /**
     * Entry point for drawing the UI each frame.
     * Dispatches to per-state draw methods.
     *
     * @param g2 active graphics context
     */
    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.white);

        // title state
        if (gp.gameState == gp.GS_TITLE_SCREEN) {drawTitleScreen();}

        // player state
        if (gp.gameState == gp.GS_PLAY) {
            drawPlayerLifeAndMana();
            drawMonsterLife();
            drawMessage();
        }

        // pause state
        if (gp.gameState == gp.GS_PAUSE) {
            drawPlayerLifeAndMana();
        }

        // dialogue state
        if (gp.gameState == gp.GS_DIALOGUE) {drawDialogueScreen();}

        // character state
        if (gp.gameState == gp.GS_INVENTORY) {
            drawCharacterScreen();
            drawInventory(gp.player, true);
        }

        // settings state
        if (gp.gameState == gp.GS_SETTINGS) {drawSettingsScreen();}

        // end state
        if (gp.gameState == gp.GS_GAME_OVER) {drawEndScreen();}

        // transition state
        if (gp.gameState == gp.GS_TRANSITION) {drawTransition();}

        // trade state
        if (gp.gameState == gp.GS_TRADE) {drawTradeScreen();}

        // sleep state
        if (gp.gameState == gp.GS_SLEEP) {drawSleepScreen();}
    }

    /**
     * Draws heart and mana icons in rows.
     * Hearts: 1 heart equals 2 HP; supports half hearts.
     * Mana: draws empties then filled up to current mana.
     */
    public void drawPlayerLifeAndMana() {
        final int iconSize = 32;                 // 32x32 icons
        final int startX   = gp.TILE_SIZE / 2;
        final int startY   = gp.TILE_SIZE / 2;
        final int perRow   = 8;

        // ----- HEARTS -----
        int hearts  = gp.player.maxLife / 2;                     // 1 heart = 2 HP
        int curLife = Math.min(gp.player.life, gp.player.maxLife);

        // empties
        for (int slot = 0; slot < hearts; slot++) {
            int x = startX + (slot % perRow) * iconSize;
            int y = startY + (slot / perRow) * iconSize;
            g2.drawImage(heartEmpty, x, y, iconSize, iconSize, null);
        }

        // overlay full/half
        for (int slot = 0; slot < hearts; slot++) {
            int x = startX + (slot % perRow) * iconSize;
            int y = startY + (slot / perRow) * iconSize;
            int heartStart = slot * 2 + 1;   // first HP in this heart
            int heartEnd   = slot * 2 + 2;   // second HP in this heart

            if (curLife >= heartEnd) {
                g2.drawImage(heartFull, x, y, iconSize, iconSize, null);
            }

            else if (curLife == heartStart) {
                g2.drawImage(heartHalf, x, y, iconSize, iconSize, null);
            }
        }

        // ----- MANA (below heart rows, wrap at 8) -----
        int heartRows  = (hearts + perRow - 1) / perRow;         // ceil
        int manaStartX = startX;
        int manaStartY = startY + heartRows * iconSize;

        // empties
        for (int i = 0; i < gp.player.maxMana; i++) {
            int x = manaStartX + (i % perRow) * iconSize;
            int y = manaStartY + (i / perRow) * iconSize;
            g2.drawImage(manaEmpty, x, y, iconSize, iconSize, null);
        }

        // filled
        for (int i = 0; i < gp.player.mana; i++) {
            int x = manaStartX + (i % perRow) * iconSize;
            int y = manaStartY + (i / perRow) * iconSize;
            g2.drawImage(manaFull, x, y, iconSize, iconSize, null);
        }
    }

    /**
     * Draws monster HP bars.
     * Small bars for normal monsters near their sprite, and a large centered bar for boss monsters.
     * Bars auto-hide after a short duration when not damaged.
     */
    public void drawMonsterLife() {

        for (int i = 0; i < gp.monster[1].length; i++) {
            Entity monster = gp.monster[gp.currentMap][i];

            if (monster != null && monster.entityIsInCamera()) {

                if (monster.hpBarOn && !monster.isBossMonster) {
                    double oneScale = (double)gp.TILE_SIZE/monster.maxLife;
                    double hpBarValue = oneScale*monster.life;
                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(monster.getScreenX()-1, monster.getScreenY()-16, gp.TILE_SIZE+2, 12);
                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(monster.getScreenX(), monster.getScreenY()-15, (int)hpBarValue, 10);
                    monster.hpBarCounter++;

                    if (monster.hpBarCounter > 600) {
                        monster.hpBarCounter = 0;
                        monster.hpBarOn = false;
                    }
                }

                else if (monster.isBossMonster) {
                    double oneScale = (double)gp.TILE_SIZE*8/monster.maxLife;
                    double hpBarValue = oneScale*monster.life;
                    int x = gp.SCREEN_WIDTH/2 - gp.TILE_SIZE*4;
                    int y = gp.TILE_SIZE*10;
                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(x-1, y-1, (gp.TILE_SIZE*8)+2, 22);
                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(x, y, (int)hpBarValue, 20);
                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
                    g2.setColor(Color.white);
                    g2.drawString(monster.name, x+4, y-10);
                }
            }
        }
    }

    /**
     * Draws queued HUD messages (toast-style) with a drop shadow.
     * Each message remains for ~3 seconds at 60 FPS, then is removed.
     * Stacks messages vertically.
     */
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

    /**
     * Draws the title screen and class-select screen.
     * Uses {@code titleScreenState} to switch layouts:
     * 0 = main menu, 1 = class selection.
     * Relies on {@code cNum} for the cursor index.
     */
    public void drawTitleScreen() {

        // check titleScreenState
        if (titleScreenState == 0) {

            // background colour
            g2.setColor(Color.black);
            g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);

            // set title
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 88F));
            String text = "Pixel Adventure Quest";
            int x = centerX(text);
            int y = gp.TILE_SIZE*3;

            // text shadow
            g2.setColor(Color.gray);
            g2.drawString(text, x+5, y+5);

            // text colour
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            // credits
            g2.setFont(g2.getFont().deriveFont(24f));
            text = "By Ali Ansari";

            // text shadow
            g2.setColor(Color.gray);
            g2.drawString(text, x+2, y+38);

            // text colour
            g2.setColor(Color.white);
            g2.drawString(text, x, y+35);

            // player image
            x = gp.SCREEN_WIDTH/2 - (gp.TILE_SIZE*2)/2;
            y += gp.TILE_SIZE*2;
            g2.drawImage(gp.player.down1, x, y, gp.TILE_SIZE*2, gp.TILE_SIZE*2, null);

            // menu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "New Game";
            x = centerX(text);
            y += gp.TILE_SIZE*3.5;
            g2.drawString(text, x, y);

            if (cNum == 0) {
                g2.drawString("->", x-gp.TILE_SIZE, y);
            }

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "Load Game";
            x = centerX(text);
            y += gp.TILE_SIZE;
            g2.drawString(text, x, y);

            if (cNum == 1) {
                g2.drawString("->", x-gp.TILE_SIZE, y);
            }

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "Quit";
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

    /**
     * Renders the dialogue window and typewriter effect.
     * Uses {@code npc.dialogues[dialogueSet][dialogueIndex]} as source text.
     * Advances with Enter; hands control back to Play/Cutscene when finished.
     */
    public void drawDialogueScreen() {

        // Dialogue Window
        int x = gp.TILE_SIZE * 3;
        int y = gp.TILE_SIZE / 2;
        int width = gp.SCREEN_WIDTH - (gp.TILE_SIZE * 6);
        int height = gp.TILE_SIZE * 4;
        drawMiniWindow(x, y, width, height);
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        x += gp.TILE_SIZE;
        y += gp.TILE_SIZE;

        // check if any text in dialogue array
        if (npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null) {
            char characters[] = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();

            if (charIndex < characters.length) {
                gp.soundEffect(21);
                String s = String.valueOf(characters[charIndex]);
                combinedText += s;
                currentDialogue = combinedText;
                charIndex++;
            }

            if (gp.kHandler.enterPressed) {
                charIndex = 0;
                combinedText = "";

                if (gp.gameState == gp.GS_DIALOGUE || gp.gameState == gp.GS_CUTSCENE) {
                    npc.dialogueIndex++;
                    gp.kHandler.enterPressed = false;
                }
            }
        }

        else { // if no text in the array
            npc.dialogueIndex = 0;

            if (gp.gameState == gp.GS_DIALOGUE) {
                gp.gameState = gp.GS_PLAY;
            }

            if (gp.gameState == gp.GS_CUTSCENE) {
                gp.cManager.scenePhase++;
            }
        }

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    /**
     * Draws the character/status panel for the player.
     * Renders a framed card with labeled stats on the left and right-aligned values.
     * Also previews the currently equipped weapon and shield icons.
     *
     * Layout notes:
     * - Frame: fixed size at top-left.
     * - Labels: LVL, HP, MP, CLASS, STR, DEX, ATK, DEF, XP, MAX XP, COINS, WEAPON, SHIELD.
     * - Values: right-aligned to a tail X using {@link #rightX(String, int)}.
     */
    public void drawCharacterScreen() {

        // create a frame
        final int frameX = gp.TILE_SIZE/2;
        final int frameY = gp.TILE_SIZE/2 - 12;
        final int frameWidth = gp.TILE_SIZE*5;
        final int frameHeight = gp.TILE_SIZE*11;
        drawMiniWindow(frameX, frameY, frameWidth, frameHeight);

        // text
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        int textX = frameX + 20;
        int textY = frameY + gp.TILE_SIZE;
        final int lineHeight = 35;

        // stats
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

        // values
        int tailX = (frameX + frameWidth) - 30;

        // reset textY
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

    /**
     * Draws an inventory grid for a given entity (player or NPC).
     * Highlights equipped items, shows stack counts, and (optionally) a cursor box with a description pane.
     *
     * Grid details:
     * - 3 rows × 5 columns (wraps after item indices 4, 9, 14).
     * - Player/NPC use different frame positions; cursor uses playerSlotCol/Row or npcSlotCol/Row.
     *
     * @param entity the owner of the inventory (e.g., {@code gp.player} or an NPC)
     * @param cursor when true, draws a selection cursor and item description panel
     */
    public void drawInventory(Entity entity, boolean cursor) {
        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if (entity == gp.player) {
            frameX = gp.TILE_SIZE*13 + 24;
            frameY = 12;
            frameWidth = gp.TILE_SIZE*6;
            frameHeight = gp.TILE_SIZE*5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        }

        else {
            frameX = 24;
            frameY = 12;
            frameWidth = gp.TILE_SIZE*6;
            frameHeight = gp.TILE_SIZE*5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        // frame
        drawMiniWindow(frameX, frameY, frameWidth, frameHeight);

        // slot
        final int slotX_start = frameX + 20;
        final int slotY_start = frameY + 20;
        int slotX = slotX_start;
        int slotY = slotY_start;
        int slotSize = gp.TILE_SIZE+3;

        // inventory items
        for (int i = 0; i < entity.inventory.size(); i++) {

            // equip cursor
            if (entity.inventory.get(i) == entity.currentWeapon || entity.inventory.get(i) == entity.currentShield || entity.inventory.get(i) == entity.currentLight) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.TILE_SIZE, gp.TILE_SIZE, 10, 10);
            }

            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

            // display amount
            if (entity == gp.player && entity.inventory.get(i).amount > 1) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32f));
                String s = "" + entity.inventory.get(i).amount;
                int amountX = rightX(s, slotX+44);
                int amountY = slotY + gp.TILE_SIZE;

                // shadow
                g2.setColor(new Color(60, 60, 60));
                g2.drawString(s, amountX, amountY);

                // main colour
                g2.setColor(Color.white);
                g2.drawString(s, amountX-3, amountY-3);

            }

            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14) {
                slotX = slotX_start;
                slotY += slotSize;
            }
        }

        // cursor
        if (cursor) {
            int cursorX = slotX_start + (slotSize* slotCol);
            int cursorY = slotY_start + (slotSize*slotRow);
            int cursorWidth = gp.TILE_SIZE;
            int cursorHeight = gp.TILE_SIZE;

            // draw cursor
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            // description frame
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight + 15;
            int dFrameWidth = frameWidth;
            int dFrameHeight = 170;

            // description text
            int textX = dFrameX + 20;
            int textY = dFrameY + gp.TILE_SIZE;
            g2.setFont(g2.getFont().deriveFont(24f));
            int itemIndex = getSlotIndex(slotCol, slotRow);

            if (itemIndex < entity.inventory.size()) {
                drawMiniWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

                for (String line: entity.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }
        }
    }

    /**
     * Draws the "Game Over" overlay with menu options.
     * Darkens the screen, shows title text, and a two-option menu:
     * - Retry: resumes play, resets entities, and restarts music.
     * - Quit: returns to title and resets the game.
     * Uses {@code cNum} as the cursor index.
     */
    public void drawEndScreen() {
        gp.stopMusic();
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
        cNumMax = 1;
        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        // shadow
        text = "Game Over!";
        g2.setColor(Color.black);
        x = centerX(text);
        y = gp.TILE_SIZE*4;
        g2.drawString(text, x, y);

        // main text
        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);

        // retry
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = centerX(text);
        y += gp.TILE_SIZE*4;
        g2.drawString(text, x, y);

        if (cNum == 0) {
            g2.drawString("->", x-47, y);

            if (gp.kHandler.enterPressed) {
                gp.gameState = gp.GS_PLAY;
                gp.resetGame(false);
                gp.playMusic(0);
            }
        }

        // main menu
        text = "Quit";
        x = centerX(text);
        y += 60;
        g2.drawString(text, x, y);

        if (cNum == 1) {
            g2.drawString("->", x-47, y);

            if (gp.kHandler.enterPressed) {
                gp.gameState = gp.GS_TITLE_SCREEN;
                cNum = 0;
                gp.resetGame(true);
            }
        }
    }

    /**
     * Renders the Settings UI container and routes to a specific sub-screen
     * (menu, fullscreen notice, controls, or end-game confirm) based on
     * {@code settingsScreenState}. Resets {@code enterPressed} each frame.
     */
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

    /**
     * Main Settings menu.
     * Draws toggleable items (Full Screen, Music, SFX, Controls, Leave Game, Close), a checkbox for fullscreen, and simple volume bars for Music/SFX.
     * Uses {@code cNum} as the cursor, updates {@code cNumMax}, and persists config via {@code gp.config.saveConfig()}.
     *
     * @param frameX left of the settings frame
     * @param frameY top of the settings frame
     */
    public void settingsMenuScreenState(int frameX, int frameY) {
        int textX;
        int textY;
        int lineHeight = gp.TILE_SIZE+12;
        cNumMax = 5;

        // title
        String text = "Settings";
        textX = centerX(text);
        textY = frameY + gp.TILE_SIZE;
        g2.drawString(text, textX, textY);

        // full screen option
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

        // music
        textY += lineHeight;
        g2.drawString("Music", textX, textY);

        if (cNum == 1) {
            g2.drawString("->", textX-32, textY);
        }

        // sound effects
        textY += lineHeight;
        g2.drawString("SFX", textX, textY);

        if (cNum == 2) {
            g2.drawString("->", textX-32, textY);
        }

        // controls
        textY += lineHeight;
        g2.drawString("Controls", textX, textY);

        if (cNum == 3) {
            g2.drawString("->", textX-32, textY);

            if (gp.kHandler.enterPressed) {
                settingsScreenState = 2;
                cNum = 0;
            }
        }

        // end game
        textY += lineHeight;
        g2.drawString("Leave Game", textX, textY);

        if (cNum == 4) {
            g2.drawString("->", textX-32, textY);

            if (gp.kHandler.enterPressed) {
                settingsScreenState = 3;
                cNum = 0;
            }
        }

        // back
        textY += lineHeight + gp.TILE_SIZE/2;
        g2.drawString("Close", textX, textY);

        if (cNum == 5) {
            g2.drawString("->", textX-32, textY);

            if (gp.kHandler.enterPressed) {
                cNum = 0;
                gp.gameState = gp.GS_PLAY;
            }
        }

        // full screen checkbox
        textX = frameX + (int)(gp.TILE_SIZE*4.5);
        textY = frameY + gp.TILE_SIZE*2;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, gp.TILE_SIZE/2, gp.TILE_SIZE/2);

        if (gp.fullScreenOn) {
            g2.fillRect(textX, textY, gp.TILE_SIZE/2, gp.TILE_SIZE/2);
        }

        // music volume
        textY += lineHeight;
        g2.drawRect(textX, textY, 120, gp.TILE_SIZE/2);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        // sfx volume
        textY += lineHeight;
        g2.drawRect(textX, textY, 120, gp.TILE_SIZE/2);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);
        gp.config.saveConfig();
    }

    /**
     * Fullscreen mode notice.
     * Informs the player a restart is required after toggling fullscreen/windowed.
     * Single “Cancel” option returns to the main settings menu.
     *
     * @param frameX left of the settings frame
     * @param frameY top of the settings frame
     */
    public void settingsFullScreenState(int frameX, int frameY) {
        int textX = frameX + gp.TILE_SIZE;
        int textY = frameY + gp.TILE_SIZE*2;
        cNumMax = 0;

        if (gp.fullScreenOn) {
            currentDialogue = "You will need to restart the\ngame for full screen mode.";
        }

        else {
            currentDialogue = "You will need to restart the\ngame for windowed mode.";
        }

        for (String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // back
        textY += gp.TILE_SIZE*5 + 16;
        g2.drawString("Cancel", textX, textY);

        if (cNum == 0) {
            g2.drawString("->", textX-32, textY);

            if (gp.kHandler.enterPressed) {
                settingsScreenState = 0;
            }
        }
    }

    /**
     * Controls help screen.
     *
     * @param frameX left of the settings frame
     * @param frameY top of the settings frame
     */
    public void settingsControlScreenState(int frameX, int frameY) {
        int textX;
        int textY;
        cNumMax = 0;

        String text = "Controls";
        textX = centerX(text);
        textY = frameY + gp.TILE_SIZE;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.TILE_SIZE;
        textY += 5;
        g2.drawString("Move", textX, textY+= gp.TILE_SIZE);
        g2.drawString("Select/Attack", textX, textY+= gp.TILE_SIZE);
        g2.drawString("Block", textX, textY+= gp.TILE_SIZE);
        g2.drawString("Shoot", textX, textY+= gp.TILE_SIZE);
        g2.drawString("Inventory", textX, textY+= gp.TILE_SIZE);
        g2.drawString("Pause Game", textX, textY+= gp.TILE_SIZE);
        g2.drawString("Map/Minimap", textX, textY+= gp.TILE_SIZE);

        textX = frameX + gp.TILE_SIZE*6;
        textY = frameY + gp.TILE_SIZE + 5;
        g2.drawString("WASD", textX, textY+= gp.TILE_SIZE);
        g2.drawString("ENTER", textX, textY+= gp.TILE_SIZE);
        g2.drawString("SPACE", textX, textY+= gp.TILE_SIZE);
        g2.drawString("F", textX, textY+= gp.TILE_SIZE);
        g2.drawString("C", textX, textY+= gp.TILE_SIZE);
        g2.drawString("P", textX, textY+= gp.TILE_SIZE);
        g2.drawString("M/X", textX, textY+= gp.TILE_SIZE);

        // back
        textX = frameX + gp.TILE_SIZE;
        textY = frameY + gp.TILE_SIZE*8 + 20;
        g2.drawString("Go Back", textX, textY+= gp.TILE_SIZE);

        if (cNum == 0) {
            g2.drawString("->", textX-32, textY);

            if (gp.kHandler.enterPressed) {
                settingsScreenState = 0;
                cNum = 3;
            }
        }
    }

    /**
     * End-game confirmation dialog.
     * Asks the player to confirm quitting to the title screen.
     * Options: Yes (resets and returns to title) / No (back to Settings).
     *
     * @param frameX left of the settings frame
     * @param frameY top of the settings frame
     */
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

        // yes
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
                gp.resetGame(true);
            }
        }

        // no
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

    /**
     * Fades the screen to black and performs an area/map transition.
     * Uses a simple alpha ramp via {@code counter}; at 50 ticks it teleports
     * the player to {@code eHandler.tempMap/Col/Row}, updates previous event coords, then resumes play and calls {@code gp.changeArea()}.
     */
    public void drawTransition() {
        counter++;
        g2.setColor(new Color(0, 0, 0, counter*5));
        g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);

        if (counter == 50) {
            counter = 0;
            gp.gameState = gp.GS_PLAY;
            gp.currentMap = gp.eHandler.tempMap;
            gp.player.worldX = gp.TILE_SIZE * gp.eHandler.tempCol;
            gp.player.worldY = gp.TILE_SIZE * gp.eHandler.tempRow;
            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;
            gp.changeArea();
        }
    }

    /**
     * Trade UI router. Draws the correct sub-screen based on
     * {@code tradeScreenState} (0=select, 1=buy, 2=sell) and then clears
     * {@code enterPressed} to avoid repeated actions on a held key.
     */
    public void drawTradeScreen() {

        switch(tradeScreenState) {
            case 0: trade_select(); break;
            case 1: trade_buy(); break;
            case 2: trade_sell(); break;
        }

        gp.kHandler.enterPressed = false;
    }

    /**
     * Trade: selection menu. Shows Buy / Sell / Leave options, advances
     * {@code tradeScreenState} and resets {@code cNum} on selection.
     * Also draws the standard mini-window frame.
     */
    public void trade_select() {
        npc.dialogueSet = 0;
        drawDialogueScreen();
        cNumMax = 2;

        // draw window
        int x = gp.TILE_SIZE*15;
        int y = gp.TILE_SIZE*4;
        int width = gp.TILE_SIZE*3;
        int height = (int)(gp.TILE_SIZE*3.5);
        drawMiniWindow(x, y, width, height);

        // draw text
        x += gp.TILE_SIZE;
        y += gp.TILE_SIZE;
        g2.drawString("Buy", x, y);

        if (cNum == 0) {
            g2.drawString("->", x-32, y);

            if (gp.kHandler.enterPressed) {
                tradeScreenState = 1;
                cNum = 0;
            }
        }

        y += gp.TILE_SIZE;
        g2.drawString("Sell", x, y);

        if (cNum == 1) {
            g2.drawString("->", x-32, y);

            if (gp.kHandler.enterPressed) {
                tradeScreenState = 2;
                cNum = 0;
            }
        }

        y += gp.TILE_SIZE;
        g2.drawString("Leave", x, y);

        if (cNum == 2) {
            g2.drawString("->", x-32, y);

            if (gp.kHandler.enterPressed) {
                npc.startDialogue(npc, 1);
                cNum = 0;
            }
        }

        y += gp.TILE_SIZE;
    }

    /**
     * Trade: buy flow. Renders player and merchant inventories, shows price
     * for the merchant's selected item, and processes purchase on ENTER:
     * - If not enough coins: switch back to select and trigger dialogue set 2.
     * - If item fits inventory: deduct coins and add item.
     * - Else: switch back to select and trigger dialogue set 3.
     * Also displays the player's current coin total.
     */
    public void trade_buy() {

        // draw player inventory
        drawInventory(gp.player, false);

        // draw merchant inventory
        drawInventory(npc, true);

        // draw price window
        int x = gp.TILE_SIZE/2;
        int y = gp.TILE_SIZE*9 + 24;
        int width = gp.TILE_SIZE*3 + 24;
        int height = gp.TILE_SIZE+24;
        int itemIndex = getSlotIndex(npcSlotCol, npcSlotRow);

        if (itemIndex < npc.inventory.size()) {
            drawMiniWindow(x, y, width, height);
            g2.drawString("Price: ", x+20, y+45);
            g2.drawImage(coin, x+65, y+12, gp.TILE_SIZE, gp.TILE_SIZE, null);
            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;
            g2.drawString(text, x+(gp.TILE_SIZE*2)+15, y+45);

            // buy an item
            if (gp.kHandler.enterPressed) {

                if (price > gp.player.coins) {
                    tradeScreenState = 0;
                    npc.startDialogue(npc, 2);
                }

                else {

                    if (gp.player.isItemObtainable(npc.inventory.get(itemIndex))) {
                        gp.player.coins -= price;
                    }

                    else {
                        tradeScreenState = 0;
                        npc.startDialogue(npc, 3);
                    }
                }
            }
        }

        // draw player coin
        x = gp.TILE_SIZE*13 + 24;
        y = gp.TILE_SIZE*9 + 24;
        width = gp.TILE_SIZE*6;
        height = gp.TILE_SIZE+24;
        drawMiniWindow(x, y, width, height);
        g2.drawString("Your coins: " + gp.player.coins, x+24, y+45);
    }

    /**
     * Trade: sell flow. Renders player inventory, shows selling price for the selected item (Thief gets 75%, others 50%), blocks selling equipped weapon/shield, and on ENTER removes/decrements the item and adds coins.
     * Also displays the player's current coin total.
     */
    public void trade_sell() {

        // draw player inventory
        drawInventory(gp.player, true);

        int x;
        int y;
        int width;
        int height;

        // draw price window
        x = gp.TILE_SIZE*8 + 24;
        y = gp.TILE_SIZE*9 + 24;
        width = gp.TILE_SIZE*4 + 24;
        height = gp.TILE_SIZE+24;
        int itemIndex = getSlotIndex(playerSlotCol, playerSlotRow);

        if (itemIndex < gp.player.inventory.size()) {
            drawMiniWindow(x, y, width, height);
            g2.drawString("Selling Price: ", x+20, y+45);
            g2.drawImage(coin, x+125, y+12, gp.TILE_SIZE, gp.TILE_SIZE, null);
            int sellingPrice = 0;

            if (gp.player.playerClass == "Thief") {
                sellingPrice = (int)(gp.player.inventory.get(itemIndex).price * 0.75);
            }

            else {
                sellingPrice = (int)(gp.player.inventory.get(itemIndex).price * 0.5);
            }

            String text = "" + sellingPrice;
            g2.drawString(text, x+(gp.TILE_SIZE*3)+36, y+45);

            // sell an item
            if (gp.kHandler.enterPressed) {

                if (gp.player.inventory.get(itemIndex) == gp.player.currentWeapon || gp.player.inventory.get(itemIndex) == gp.player.currentShield) {
                    tradeScreenState = 0;
                    npc.startDialogue(npc, 4);
                }

                else {

                    if (gp.player.inventory.get(itemIndex).amount > 1) {
                        gp.player.inventory.get(itemIndex).amount--;
                    }

                    else {
                        gp.player.inventory.remove(itemIndex);
                    }

                    gp.player.coins += sellingPrice;
                }
            }
        }

        // draw player coin
        x = gp.TILE_SIZE*13 + 24;
        y = gp.TILE_SIZE*9 + 24;
        width = gp.TILE_SIZE*6;
        height = gp.TILE_SIZE+24;
        drawMiniWindow(x, y, width, height);
        g2.drawString("Your coins: " + gp.player.coins, x+24, y+45);
    }

    /**
     * Plays the sleep transition: fades the lighting to black, then back to day.
     * When the fade-in completes, it resets day cycle, returns to PLAY state, and refreshes the player's sprites via getImage().
     */
    public void drawSleepScreen() {
        counter++;

        if (counter < 120) {
            gp.eManager.lighting.filterAlpha += 0.01f;

            if (gp.eManager.lighting.filterAlpha > 1f) {
                gp.eManager.lighting.filterAlpha = 1f;
            }
        }

        if (counter >= 120) {
            gp.eManager.lighting.filterAlpha -= 0.01f;

            if (gp.eManager.lighting.filterAlpha <= 0f) {
                gp.eManager.lighting.filterAlpha = 0;
                counter = 0;
                gp.eManager.lighting.dayState = gp.eManager.lighting.NOON;
                gp.eManager.lighting.dayCounter = 0;
                gp.gameState = gp.GS_PLAY;
                gp.player.getImage();
            }
        }
    }

    /**
     * Returns the linear index for a 5-column inventory grid from (col,row).
     * Used to map cursor coordinates to the inventory list index.
     */
    public int getSlotIndex(int slotCol, int slotRow) {
        return slotCol + (slotRow*5);
    }

    /**
     * Draws a rounded, semi-transparent panel with a white outline.
     * Shared frame used across UI sub-screens.
     */
    public void drawMiniWindow(int x, int y, int width, int height) {
        Color c = new Color(0,0,0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x, y, width, height,25,25);
    }

    /**
     * Calculates the x coordinate to horizontally center the given text
     * using the current Graphics2D font metrics and screen width.
     */
    public int centerX(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.SCREEN_WIDTH/2 - length/2;
    }

    /**
     * Calculates the x coordinate to right-align the given text so its tail sits at tailX (with a small padding).
     */
    public int rightX(String text, int tailX) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length + 15;
    }
}
