package main;

import javax.swing.*;

/**
 * App entry point. Creates the main JFrame, attaches the GamePanel, load config (incl. fullscreen), and starts the game loop.
 */
public class Main {

    // shared window so other systems (e.g., fullscreen) can access it
    public static JFrame window;

    /**
     * Boot the game: build window, attach panel, apply config, show the UI, then run setup and start the loop.
     *
     * @param args
     */
    public static void main(String[] args) {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Pixel Adventure Quest");
        new Main().setGameWindowIcon();
        GamePanel gp = new GamePanel();
        window.add(gp);
        gp.config.loadConfig();

        if (gp.fullScreenOn) {
            window.setUndecorated(true);
        }

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gp.requestFocusInWindow();
        gp.gameSetup();
        gp.startGameLoop();
    }

    /**
     * Set the window icon from a classpath sprite.
     * Loads a single PNG; if it's missing, it logs a warning and continues.
     */
    public void setGameWindowIcon() {
        java.net.URL url = Main.class.getResource("/player/walking/down_1.png");

        if (url == null) {
            System.err.println("Icon not found on classpath: /player/walking/down_1.png");
            return;
        }

        window.setIconImage(new javax.swing.ImageIcon(url).getImage());
    }

}