package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Central keyboard input handler.
 * Routes key events to the appropriate handler based on the current game state.
 * Maintains simple boolean flags for continuous input (e.g., WASD, fire, space).
 * Also exposes a few debug toggles (frame info, god mode).
 */
public class KeyHandler implements KeyListener {
    GamePanel gp;

    // movement and action flags used by update loops
    public boolean upKey, downKey, leftKey, rightKey, enterPressed, shotKeyPressed, spacePressed;

    // debug toggles (FPS/text overlays) and invincibility mode
    public boolean toggleDebug = false;
    public boolean godMode = false;

    /**
     * Creates a key handler linked to a {@link GamePanel}.
     *
     * @param gp owning game panel
     */
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Unused by this game. Kept to satisfy {@link KeyListener}.
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Dispatches a key press to the current state handler.
     * Uses the game state to decide which UI or gameplay layer should respond.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // title state
        if (gp.gameState == gp.GS_TITLE_SCREEN) {
            titleState(keyCode);
        }

        // player state
        else if (gp.gameState == gp.GS_PLAY) {
            playState(keyCode);
        }

        // pause state
        else if (gp.gameState == gp.GS_PAUSE) {
            pauseState(keyCode);
        }

        // dialogue or cutscene state
        else if (gp.gameState == gp.GS_DIALOGUE || gp.gameState == gp.GS_CUTSCENE) {
            dialogueState(keyCode);
        }

        // inventory state
        else if (gp.gameState == gp.GS_INVENTORY) {
            characterState(keyCode);
        }

        // settings state
        else if (gp.gameState == gp.GS_SETTINGS) {
            settingsState(keyCode);
        }

        // end state
        else if (gp.gameState == gp.GS_GAME_OVER) {
            endState(keyCode);
        }

        // trade state
        else if (gp.gameState == gp.GS_TRADE) {
            tradeState(keyCode);
        }

        // map state
        else if (gp.gameState == gp.GS_MAP) {
            mapState(keyCode);
        }
    }

    /**
     * Title screen navigation and class selection.
     * W/S move the cursor. Enter selects. Handles New, Load, Quit and class selection.
     */
    public void titleState(int keyCode) {

        // main menu
        if (gp.ui.titleScreenState == 0) {

            if (keyCode == KeyEvent.VK_W) {
                gp.soundEffect(9);
                gp.ui.cNum--;

                if (gp.ui.cNum < 0) {
                    gp.ui.cNum = 2;
                }
            }

            if (keyCode == KeyEvent.VK_S) {
                gp.soundEffect(9);
                gp.ui.cNum++;

                if (gp.ui.cNum > 2) {
                    gp.ui.cNum = 0;
                }
            }

            if (keyCode == KeyEvent.VK_ENTER) {
                gp.soundEffect(10);

                if (gp.ui.cNum == 0) {
                    gp.ui.titleScreenState = 1;
                }

                if (gp.ui.cNum == 1) {
                    gp.saveLoad.load();
                    gp.gameState = gp.GS_PLAY;
                    gp.playMusic(0);

                }

                if (gp.ui.cNum == 2) {
                    System.exit(0);
                }
            }
        }

        else if (gp.ui.titleScreenState == 1) {

            if (keyCode == KeyEvent.VK_W) {
                gp.soundEffect(9);
                gp.ui.cNum--;

                if (gp.ui.cNum < 0) {
                    gp.ui.cNum = 3;
                }
            }

            if (keyCode == KeyEvent.VK_S) {
                gp.soundEffect(9);
                gp.ui.cNum++;

                if (gp.ui.cNum > 3) {
                    gp.ui.cNum = 0;
                }
            }

            if (keyCode == KeyEvent.VK_ENTER) {
                gp.soundEffect(10);
                String[] classes = {"Fighter", "Magician", "Thief"};

                if (gp.ui.cNum >= 0 && gp.ui.cNum <= 2) {
                    gp.player.playerClass = classes[gp.ui.cNum];
                    gp.player.playerClassBonus();
                    gp.gameState = gp.GS_PLAY;
                    gp.playMusic(0);
                }

                gp.ui.titleScreenState = 0;
                gp.ui.cNum = 0;
            }
        }
    }

    /**
     * Active gameplay controls.
     * WASD movement, Enter interact/attack, F shoot, C inventory, P pause, Esc settings, M map, X toggle mini-map, T debug overlay, R reload map, G god mode.
     */
    public void playState(int keyCode) {

        if (keyCode == KeyEvent.VK_W) {
            upKey = true;
        }

        if (keyCode == KeyEvent.VK_A) {
            leftKey = true;
        }

        if (keyCode == KeyEvent.VK_S) {
            downKey = true;
        }

        if (keyCode == KeyEvent.VK_D) {
            rightKey = true;
        }

        // pause game
        if (keyCode == KeyEvent.VK_P) {
            gp.gameState = gp.GS_PAUSE;
        }

        // open inventory
        if (keyCode == KeyEvent.VK_C) {
            gp.gameState = gp.GS_INVENTORY;
        }

        // attack/select
        if (keyCode == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        // cast fireball
        if (keyCode == KeyEvent.VK_F) {
            shotKeyPressed = true;
        }

        if (keyCode == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.GS_SETTINGS;
        }

        // open map view
        if (keyCode == KeyEvent.VK_M) {
            gp.gameState = gp.GS_MAP;
        }

        // toggle minimap
        if (keyCode == KeyEvent.VK_X) {
            gp.map.miniMapOn = !gp.map.miniMapOn;
        }

        // block
        if (keyCode == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }

        // debug
        if (keyCode == KeyEvent.VK_T) {
            toggleDebug = !toggleDebug;
        }

        if (keyCode == KeyEvent.VK_R) {

            switch(gp.currentMap) {
                case 0: gp.tManager.mapLoader("/maps/world_03.txt", gp.currentMap); break;
                case 1: gp.tManager.mapLoader("/maps/interior_02.txt", gp.currentMap); break;
                case 2: gp.tManager.mapLoader("/maps/dungeon_01.txt", gp.currentMap); break;
                case 3: gp.tManager.mapLoader("/maps/dungeon_02.txt", gp.currentMap); break;
            }
        }

        // god mode
        if (keyCode == KeyEvent.VK_G) {
            godMode = !godMode;
        }
    }

    /**
     * Pause game. P resumes play.
     */
    public void pauseState(int keyCode) {

        if (keyCode == KeyEvent.VK_P) {
            gp.gameState = gp.GS_PLAY;
        }
    }

    /**
     * Dialogue and cutscenes. Enter advances text or scene.
     */
    public void dialogueState(int keyCode) {

        if (keyCode == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
    }

    /**
     * Character/inventory screen.
     * C or Esc closes. Enter equips/uses selected item. Uses grid navigation helpers.
     */
    public void characterState(int keyCode) {

        if (keyCode == KeyEvent.VK_C || keyCode == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.GS_PLAY;
        }

        if (keyCode == KeyEvent.VK_ENTER) {
            gp.soundEffect(10);
            gp.player.selectItemInInventory();
        }

        playerInventory(keyCode);
    }

    /**
     * Settings menu.
     * Esc closes. Enter confirms. W/S move cursor. A/D adjust sliders (music and SFX).
     */
    public void settingsState(int keyCode) {

        if (keyCode == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.GS_PLAY;
            gp.ui.cNum = 0;
            gp.ui.settingsScreenState = 0;
        }

        if (keyCode == KeyEvent.VK_ENTER) {
            gp.soundEffect(10);
            enterPressed = true;
        }

        if (keyCode == KeyEvent.VK_W) {
            gp.soundEffect(9);
            gp.ui.cNum--;

            if (gp.ui.cNum < 0) {
                gp.ui.cNum = gp.ui.cNumMax;
            }
        }

        if (keyCode == KeyEvent.VK_S) {
            gp.soundEffect(9);
            gp.ui.cNum++;

            if (gp.ui.cNum > gp.ui.cNumMax) {
                gp.ui.cNum = 0;
            }
        }

        if (keyCode == KeyEvent.VK_A) {

            if (gp.ui.cNum == 1 && gp.music.volumeScale > 0) {
                gp.soundEffect(14);
                gp.music.volumeScale--;
                gp.music.checkVolume();
            }

            if (gp.ui.cNum == 2 && gp.se.volumeScale > 0) {
                gp.soundEffect(14);
                gp.se.volumeScale--;
            }
        }

        if (keyCode == KeyEvent.VK_D) {

            if (gp.ui.cNum == 1 && gp.music.volumeScale < 5) {
                gp.soundEffect(14);
                gp.music.volumeScale++;
                gp.music.checkVolume();
            }

            if (gp.ui.cNum == 2 && gp.se.volumeScale < 5) {
                gp.soundEffect(14);
                gp.se.volumeScale++;
            }
        }
    }

    /**
     * Game over menu.
     * W/S move cursor. Enter confirms (Retry or Quit).
     */
    public void endState(int keyCode) {

        if (keyCode == KeyEvent.VK_W) {
            gp.soundEffect(9);
            gp.ui.cNum--;

            if (gp.ui.cNum < 0) {
                gp.ui.cNum = gp.ui.cNumMax;
            }
        }

        if (keyCode == KeyEvent.VK_S) {
            gp.soundEffect(9);
            gp.ui.cNum++;

            if (gp.ui.cNum > gp.ui.cNumMax) {
                gp.ui.cNum = 0;
            }
        }

        if (keyCode == KeyEvent.VK_ENTER) {
            gp.soundEffect(10);
            enterPressed = true;
        }
    }

    /**
     * Merchant trade flow.
     * Enter confirms. Uses inventory navigation for buy/sell and Esc to back out of submenus.
     */
    public void tradeState(int keyCode) {

        if (keyCode == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        if (gp.ui.tradeScreenState == 0) {

            if (keyCode == KeyEvent.VK_W) {
                gp.ui.cNum--;

                if (gp.ui.cNum < 0) {
                    gp.ui.cNum = gp.ui.cNumMax;
                }
            }

            if (keyCode == KeyEvent.VK_S) {
                gp.ui.cNum++;

                if (gp.ui.cNum > gp.ui.cNumMax) {
                    gp.ui.cNum = 0;
                }
            }
        }

        if (gp.ui.tradeScreenState == 1) {

            if (keyCode == KeyEvent.VK_ESCAPE) {
                gp.ui.tradeScreenState = 0;
            }

            npcInventory(keyCode);
        }

        if (gp.ui.tradeScreenState == 2) {

            if (keyCode == KeyEvent.VK_ESCAPE) {
                gp.ui.tradeScreenState = 0;
            }

            playerInventory(keyCode);
        }
    }

    /**
     * Full map screen. M or Esc closes.
     */
    public void mapState(int keyCode) {

        if (keyCode == KeyEvent.VK_M || keyCode == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.GS_PLAY;
        }
    }

    /**
     * Player inventory grid navigation (5 columns x 4 rows).
     * W/S change row, A/D change column. Plays a cursor move sound.
     */
    public void playerInventory(int keyCode) {

        if (keyCode == KeyEvent.VK_W) {
            gp.soundEffect(9);

            if (gp.ui.playerSlotRow != 0) {
                gp.ui.playerSlotRow--;
            }
        }

        if (keyCode == KeyEvent.VK_S) {
            gp.soundEffect(9);

            if (gp.ui.playerSlotRow != 3) {
                gp.ui.playerSlotRow++;
            }
        }

        if (keyCode == KeyEvent.VK_D) {
            gp.soundEffect(9);

            if (gp.ui.playerSlotCol != 4) {
                gp.ui.playerSlotCol++;
            }
        }

        if (keyCode == KeyEvent.VK_A) {
            gp.soundEffect(9);

            if (gp.ui.playerSlotCol != 0) {
                gp.ui.playerSlotCol--;
            }
        }
    }

    /**
     * NPC inventory grid navigation (5 columns x 4 rows) in the trade UI.
     * W/S change row, A/D change column. Plays a cursor move sound.
     */
    public void npcInventory(int keyCode) {

        if (keyCode == KeyEvent.VK_W) {
            gp.soundEffect(9);

            if (gp.ui.npcSlotRow != 0) {
                gp.ui.npcSlotRow--;
            }
        }

        if (keyCode == KeyEvent.VK_S) {
            gp.soundEffect(9);

            if (gp.ui.npcSlotRow != 3) {
                gp.ui.npcSlotRow++;
            }
        }

        if (keyCode == KeyEvent.VK_D) {
            gp.soundEffect(9);

            if (gp.ui.npcSlotCol != 4) {
                gp.ui.npcSlotCol++;
            }
        }

        if (keyCode == KeyEvent.VK_A) {
            gp.soundEffect(9);

            if (gp.ui.npcSlotCol != 0) {
                gp.ui.npcSlotCol--;
            }
        }
    }

    /**
     * Clears continuous-input flags when keys are released.
     * Prevents unwanted “stuck” movement or actions.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W) {
            upKey = false;
        }

        if (keyCode == KeyEvent.VK_A) {
            leftKey = false;
        }

        if (keyCode == KeyEvent.VK_S) {
            downKey = false;
        }

        if (keyCode == KeyEvent.VK_D) {
            rightKey = false;
        }

        if (keyCode == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }

        if (keyCode == KeyEvent.VK_F) {
            shotKeyPressed = false;
        }

        if (keyCode == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }
}
