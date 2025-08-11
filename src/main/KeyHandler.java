package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;

    public boolean upKey, downKey, leftKey, rightKey, enterPressed;

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
    }

    public void titleState(int keyCode) {
        // Title State 1
        if (gp.ui.titleScreenState == 0) {
            if (keyCode == KeyEvent.VK_W) {
                gp.ui.cNum--;
                if (gp.ui.cNum < 0) {
                    gp.ui.cNum = 2;
                }
            }
            if (keyCode == KeyEvent.VK_S) {
                gp.ui.cNum++;
                if (gp.ui.cNum > 2) {
                    gp.ui.cNum = 0;
                }
            }
            if (keyCode == KeyEvent.VK_ENTER) {
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
                gp.ui.cNum--;
                if (gp.ui.cNum < 0) {
                    gp.ui.cNum = 3;
                }
            }
            if (keyCode == KeyEvent.VK_S) {
                gp.ui.cNum++;
                if (gp.ui.cNum > 3) {
                    gp.ui.cNum = 0;
                }
            }
            if (keyCode == KeyEvent.VK_ENTER) {
                String[] classes = {"FIGHTER", "MAGICIAN", "THIEF"};

                if (gp.ui.cNum >= 0 && gp.ui.cNum <= 2) {
                    gp.player.playerClass = classes[gp.ui.cNum];
                    gp.gameState = gp.GS_PLAY;
                }
                else if (gp.ui.cNum == 3) {
                    gp.ui.titleScreenState = 0;
                    gp.ui.cNum = 0;
                }
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

        // Debug
        if (keyCode == KeyEvent.VK_T) {
            toggleDebug = !toggleDebug;
        }

        if (keyCode == KeyEvent.VK_R) {
            gp.tm.mapLoader("/maps/world_02.txt");
            System.out.println("refresh map");
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
    }
}
