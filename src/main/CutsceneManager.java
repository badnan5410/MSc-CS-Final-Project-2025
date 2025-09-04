package main;

import entity.DecoyPlayer;
import monster.MON_Boss;
import object.Iron_Door;
import object.Treasure;

import java.awt.*;

public class CutsceneManager {
    GamePanel gp;
    Graphics2D g2;
    public int sceneNum;
    public int scenePhase;
    int counter = 0;
    float alpha = 0f;
    int y;
    String endCredits;

    // scene number
    public final int NA = 0;
    public final int BOSS_MONSTER = 1;
    public final int GAME_ENDING = 2;

    public CutsceneManager(GamePanel gp) {
        this.gp = gp;

        // end credits text
        endCredits = "An Ambitious Project by\n" +
                "Ali Ansari" +
                "\n\n\n\n" +
                "Special Thanks To:\n\n" +
                "Aaisha Ansari (my sister) - Game Tester\n\n" +
                "Lux the Lamp (Pixar) - Lighting Director\n\n" +
                "Dora the Explorer - Pathfinding Consultant\n\n" +
                "Goldilocks - Potion Tester\n\n" +
                "R2-D2 - Voice Acting\n\n" +
                "Bob the Builder - Map Design\n\n" +
                "Daft Punk - Sound Design\n\n" +
                "Samwise Gamgee - Moral Support\n\n" +
                "Jack Sparrow - Key Handler\n\n" +
                "Nickelodeon - Slime Wrangler\n\n" +
                "Steve Buscemi - Skeleton King Body Double\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                "Thank you for playing my game!";
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        switch (sceneNum) {
            case BOSS_MONSTER: finalBossScene(); break;
            case GAME_ENDING: endingScene(); break;
            default: break;
        }
    }

    public void finalBossScene() {

        if (scenePhase == 0) {
            gp.bossBattleOn = true;

            //shut the iron door to trap the player
            for (int i = 0; i < gp.obj[1].length; i++) {

                if (gp.obj[gp.currentMap][i] == null) { // find empty slot for iron door object
                    gp.obj[gp.currentMap][i] = new Iron_Door(gp);
                    gp.obj[gp.currentMap][i].worldX = gp.TILE_SIZE*25;
                    gp.obj[gp.currentMap][i].worldY = gp.TILE_SIZE*28;
                    gp.obj[gp.currentMap][i].temp = true;
                    gp.soundEffect(27);
                    break;
                }
            }

            // draw the decoy player while the real player (camera) pans over the map
            for (int i = 0; i < gp.npc[1].length; i++) {

                if (gp.npc[gp.currentMap][i] == null) { // find empty slot for decoy player
                    gp.npc[gp.currentMap][i] = new DecoyPlayer(gp);
                    gp.npc[gp.currentMap][i].worldX = gp.player.worldX;
                    gp.npc[gp.currentMap][i].worldY = gp.player.worldY;
                    gp.npc[gp.currentMap][i].direction = gp.player.direction;
                    break;
                }
            }


            gp.player.isDrawing = false;
            scenePhase++;
        }

        if (scenePhase == 1) {
            gp.player.worldY -= 2;

            if (gp.player.worldY < gp.TILE_SIZE*16) {
                scenePhase++;
            }
        }

        if (scenePhase == 2) {

            // search monster array and wake up the boss
            for (int i = 0; i < gp.monster[1].length; i++) {

                if (gp.monster[gp.currentMap][i] != null && gp.monster[gp.currentMap][i].name.equals(MON_Boss.monName)) {
                    gp.monster[gp.currentMap][i].isSleeping = false;
                    gp.ui.npc = gp.monster[gp.currentMap][i];
                    scenePhase++;
                    break;
                }
            }
        }

        if (scenePhase == 3) {

            // the bose speaks
            gp.ui.drawDialogueScreen();
        }

        if (scenePhase == 4) { // final phase of cut scene

            // search for the decoy player
            for (int i = 0; i < gp.npc[1].length; i++) {

                if (gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(DecoyPlayer.npcName)) {

                    // return player (camera) back to decoy's position
                    gp.player.worldX = gp.npc[gp.currentMap][i].worldX;
                    gp.player.worldY = gp.npc[gp.currentMap][i].worldY;

                    // delete decoy player
                    gp.npc[gp.currentMap][i] = null;
                }
            }

            // draw the real player
            gp.player.isDrawing = true;

            // reset parameters
            sceneNum = NA;
            scenePhase = 0;
            gp.gameState = gp.GS_PLAY;

            // boss battle music
            gp.stopMusic();
            gp.playMusic(28);
        }
    }

    public void endingScene() {

        if (scenePhase == 0) {
            gp.stopMusic();
            gp.ui.npc = new Treasure(gp);
            scenePhase++;
        }

        if (scenePhase == 1) {

            // display dialogues
            gp.ui.drawDialogueScreen();
        }

        if (scenePhase == 2) {

            // play ending music
            gp.soundEffect(4);
            scenePhase++;
        }

        if (scenePhase == 3) {

            // delay until sound effect ends
            if (counterReached(300)) { // 5 seconds
                scenePhase++;
            }
        }

        if (scenePhase == 4) {

            // screen gets darker
            alpha += 0.005f;

            if (alpha > 1f) {alpha = 1f;}

            drawBlackBackground(alpha);

            if (alpha == 1f) {
                alpha = 0;
                scenePhase++;
            }
        }

        if (scenePhase == 5) {

            // keep black background
            drawBlackBackground(1f);

            // display end message, making it gradually appear
            alpha += 0.005f;

            if (alpha > 1f) {alpha = 1f;}

            String message = "After the fierce battle with the Skeleton King,\n" +
                    "our hero has finally discovered the legendary treasure.\n" +
                    "But, this is not the end of his journey,\n" +
                    "as his adventure has only just begun!";

            drawString(alpha, 38f, 200, message, 70);

            if (counterReached(480)) {
                gp.soundEffect(0);
                scenePhase++;
            }
        }

        if (scenePhase == 6) {

            // draw black screen
            drawBlackBackground(1f);

            // draw title name
            String text = "Pixel Adventure Quest";
            drawString(1f, 100f, gp.SCREEN_HEIGHT/2, text, 40);

            if (counterReached(180)) {
                scenePhase++;
            }
        }

        if (scenePhase == 7) {

            // draw black screen
            drawBlackBackground(1f);

            // draw end credits
            y = gp.SCREEN_WIDTH/2;
            drawString(1f, 38f, y, endCredits, 40);

            if (counterReached(180)) {
                scenePhase++;
            }
        }

        if (scenePhase == 8) {

            // draw black background
            drawBlackBackground(1f);

            //scroll up the screen so the credits move upwards
            y--;
            drawString(1f, 38f, y, endCredits, 40);

            if (counterReached(2220)) {
                System.exit(0);
            }
        }
    }

    public boolean counterReached(int targetNumber) {
        boolean counterReached = false;
        counter++;

        if (counter > targetNumber) {
            counterReached = true;
            counter = 0;
        }

        return counterReached;
    }

    public void drawBlackBackground(float alpha) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
    }

    public void drawString(float alpha, float fontSize, int y, String text, int lineHeight) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(fontSize));

        for (String line: text.split("\n")) {
            int x = gp.ui.centerX(line);
            g2.drawString(line, x, y);
            y += lineHeight;
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
