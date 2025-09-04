package main;

import javax.swing.*;

public class Main {
    public static JFrame window;

    public static void main(String[] args) {

        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Pixel Adventure Quest");
        new Main().setGameWindowIcon();

        GamePanel gp = new GamePanel();
        window.add(gp);

        gp.config.loadConfig();
        if (gp.fullScreenOn) {window.setUndecorated(true);}

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gp.gameSetup();
        gp.startGameLoop();
    }

    public void setGameWindowIcon() {
        // Option A: Class.getResource — leading slash for absolute path
        java.net.URL url = Main.class.getResource("/player/walking/down_1.png");

        // Option B: ClassLoader.getResource — NO leading slash
        // java.net.URL url = Main.class.getClassLoader().getResource("player/walking/down_1.png");

        if (url == null) {
            System.err.println("Icon not found on classpath: /player/walking/down_1.png");
            return;
        }
        window.setIconImage(new javax.swing.ImageIcon(url).getImage());
    }

}