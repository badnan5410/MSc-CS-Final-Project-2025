package main;

import javax.swing.JFrame;

public class Main {
    public static JFrame window;

    public static void main(String[] args) {

        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Pixel Adventure Quest");

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
}