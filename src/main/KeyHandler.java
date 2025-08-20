package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;

    public boolean upKey, downKey, leftKey, rightKey, enterPressed, shotKeyPressed;

    // Debug
    boolean toggleDebug = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (gp.gameState == gp.GS_TITLE_SCREEN) {
            titleState(keyCode);
        }

        // Play State
        else if (gp.gameState == gp.GS_PLAY) {
            playState(keyCode);
        }

        // Pause State
        else if (gp.gameState == gp.GS_PAUSE) {
            pauseState(keyCode);
        }

        // Dialogue State
        else if (gp.gameState == gp.GS_DIALOGUE) {
            dialogueState(keyCode);
        }

        // Character State
        else if (gp.gameState == gp.GS_CHARACTER_STATE) {
            characterState(keyCode);
        }

        // Options State
        else if (gp.gameState == gp.GS_OPTIONS_STATE) {
            optionsState(keyCode);
        }
    }

    public void titleState(int keyCode) {
        // Title State 1
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
                    // load game
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
                String[] classes = {"FIGHTER", "MAGICIAN", "THIEF"};

                if (gp.ui.cNum >= 0 && gp.ui.cNum <= 2) {
                    gp.player.playerClass = classes[gp.ui.cNum];
                    gp.gameState = gp.GS_PLAY;
                    gp.playMusic(0);
                }
                gp.ui.titleScreenState = 0;
                gp.ui.cNum = 0;
            }
        }
    }

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

        // Pause Game
        if (keyCode == KeyEvent.VK_P) {
            gp.gameState = gp.GS_PAUSE;
        }

        if (keyCode == KeyEvent.VK_C) {
            gp.gameState = gp.GS_CHARACTER_STATE;
        }

        if (keyCode == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (keyCode == KeyEvent.VK_F) {
            shotKeyPressed = true;
        }

        if (keyCode == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.GS_OPTIONS_STATE;
        }

        // Debug
        if (keyCode == KeyEvent.VK_T) {
            toggleDebug = !toggleDebug;
        }

        if (keyCode == KeyEvent.VK_R) {
            gp.tm.mapLoader("/maps/world_02.txt");
        }
    }

    public void pauseState(int keyCode) {
        if (keyCode == KeyEvent.VK_P) {
            gp.gameState = gp.GS_PLAY;
        }
    }

    public void dialogueState(int keyCode) {
        if (keyCode == KeyEvent.VK_ENTER) {
            gp.gameState = gp.GS_PLAY;
        }
    }

    public void characterState(int keyCode) {
        if (keyCode == KeyEvent.VK_C) {
            gp.gameState = gp.GS_PLAY;
        }
        if (keyCode == KeyEvent.VK_W) {
            gp.soundEffect(9);
            if (gp.ui.slotRow != 0) {
                gp.ui.slotRow--;
            }
        }
        if (keyCode == KeyEvent.VK_S) {
            gp.soundEffect(9);
            if (gp.ui.slotRow != 3) {
                gp.ui.slotRow++;
            }
        }
        if (keyCode == KeyEvent.VK_D) {
            gp.soundEffect(9);
            if (gp.ui.slotCol != 4) {
                gp.ui.slotCol++;
            }
        }
        if (keyCode == KeyEvent.VK_A) {
            gp.soundEffect(9);
            if (gp.ui.slotCol != 0) {
                gp.ui.slotCol--;
            }
        }
        if (keyCode == KeyEvent.VK_ENTER) {
            gp.soundEffect(10);
            gp.player.selectItem();
        }
    }

    public void optionsState(int keyCode) {
        if (keyCode == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.GS_PLAY;
            gp.ui.cNum = 0;
            gp.ui.optionsScreenState = 0;
        }
        if (keyCode == KeyEvent.VK_ENTER) {
            gp.soundEffect(10);
            enterPressed = true;
        }

        // Moving cursor
        if (keyCode == KeyEvent.VK_W) {
            gp.soundEffect(9);
            gp.ui.cNum--;
            if (gp.ui.cNum < 0) {gp.ui.cNum = gp.ui.cNumMax;}

        }
        if (keyCode == KeyEvent.VK_S) {
            gp.soundEffect(9);
            gp.ui.cNum++;
            if (gp.ui.cNum > gp.ui.cNumMax) {gp.ui.cNum = 0;}
        }

        // Moving slider
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
        if (keyCode == KeyEvent.VK_F) {
            shotKeyPressed = false;
        }
    }
}
