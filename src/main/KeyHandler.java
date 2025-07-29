package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;

    public boolean upKey, downKey, leftKey, rightKey;

    // Debug
    boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

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

        // Pause/Play Game
        if (keyCode == KeyEvent.VK_P) {
            if (gp.gameState == gp.GS_PLAY) {
                gp.gameState = gp.GS_PAUSE;
            }
            else if (gp.gameState == gp.GS_PAUSE) {
                gp.gameState = gp.GS_PLAY;
            }
        }

        // Debug
        if (keyCode == KeyEvent.VK_T) {
            checkDrawTime = !checkDrawTime;
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
