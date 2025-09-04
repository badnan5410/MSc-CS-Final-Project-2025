package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;

    public boolean upKey, downKey, leftKey, rightKey, enterPressed, shotKeyPressed, spacePressed;

    // Debug
    public boolean toggleDebug = false;
    public boolean godMode = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (gp.gameState == gp.GS_TITLE_SCREEN) {titleState(keyCode);}

        // Play State
        else if (gp.gameState == gp.GS_PLAY) {playState(keyCode);}

        // Pause State
        else if (gp.gameState == gp.GS_PAUSE) {pauseState(keyCode);}

        // Dialogue or Cutscene States
        else if (gp.gameState == gp.GS_DIALOGUE || gp.gameState == gp.GS_CUTSCENE) {
            dialogueState(keyCode);
        }

        // Character State
        else if (gp.gameState == gp.GS_INVENTORY) {characterState(keyCode);}

        // Settings State
        else if (gp.gameState == gp.GS_SETTINGS) {settingsState(keyCode);}

        // End State
        else if (gp.gameState == gp.GS_GAME_OVER) {endState(keyCode);}

        // Trade State
        else if (gp.gameState == gp.GS_TRADE) {tradeState(keyCode);}

        // Map State
        else if (gp.gameState == gp.GS_MAP) {mapState(keyCode);}
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
            gp.gameState = gp.GS_INVENTORY;
        }

        if (keyCode == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (keyCode == KeyEvent.VK_F) {
            shotKeyPressed = true;
        }

        if (keyCode == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.GS_SETTINGS;
        }

        if (keyCode == KeyEvent.VK_M) {
            gp.gameState = gp.GS_MAP;
        }

        if (keyCode == KeyEvent.VK_X) {
            gp.map.miniMapOn = !gp.map.miniMapOn;
        }

        if (keyCode == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }

        // Debug
        if (keyCode == KeyEvent.VK_T) {
            toggleDebug = !toggleDebug;
        }

        if (keyCode == KeyEvent.VK_R) {
            switch(gp.currentMap) {
                case 0: gp.tManager.mapLoader("/maps/world_02.txt", gp.currentMap); break;
                case 1: gp.tManager.mapLoader("/maps/interior_01.txt", gp.currentMap); break;
                case 2: gp.tManager.mapLoader("/maps/dungeon_01.txt", gp.currentMap); break;
                case 3: gp.tManager.mapLoader("/maps/dungeon_02.txt", gp.currentMap); break;
            }
        }

        if (keyCode == KeyEvent.VK_G) {
            godMode = !godMode;
        }
    }

    public void pauseState(int keyCode) {
        if (keyCode == KeyEvent.VK_P) {
            gp.gameState = gp.GS_PLAY;
        }
    }

    public void dialogueState(int keyCode) {

        if (keyCode == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
    }

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

    public void endState(int keyCode) {

        // Moving Cursor
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

        // Select
        if (keyCode == KeyEvent.VK_ENTER) {
            gp.soundEffect(10);
            enterPressed = true;
        }
    }

    public void tradeState(int keyCode) {
        if (keyCode == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        if (gp.ui.tradeScreenState == 0) {
            if (keyCode == KeyEvent.VK_W) {
                gp.ui.cNum--;
                if (gp.ui.cNum < 0) {gp.ui.cNum = gp.ui.cNumMax;}
            }

            if (keyCode == KeyEvent.VK_S) {
                gp.ui.cNum++;
                if (gp.ui.cNum > gp.ui.cNumMax) {gp.ui.cNum = 0;}
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

    public void mapState(int keyCode) {
        if (keyCode == KeyEvent.VK_M || keyCode == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.GS_PLAY;
        }
    }

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

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W) {upKey = false;}

        if (keyCode == KeyEvent.VK_A) {leftKey = false;}

        if (keyCode == KeyEvent.VK_S) {downKey = false;}

        if (keyCode == KeyEvent.VK_D) {rightKey = false;}

        if (keyCode == KeyEvent.VK_ENTER) {enterPressed = false;}

        if (keyCode == KeyEvent.VK_F) {shotKeyPressed = false;}

        if (keyCode == KeyEvent.VK_SPACE) {spacePressed = false;}
    }
}
