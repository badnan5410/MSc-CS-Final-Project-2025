package main;

import java.io.*;

public class Config {
    GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    public void saveConfig() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            // Full Screen Setting
            if (gp.fullScreenOn) {bw.write("on");}
            else {bw.write("off");}
            bw.newLine();

            // Music Volume
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            // SFX Volume
            bw.write(String.valueOf(gp.se.volumeScale));
            bw.newLine();

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));
            String s = br.readLine();

            // Full Screen
            if (s.equals("on")) {
                gp.fullScreenOn = true;
            }
            else if (s.equals("off")) {
                gp.fullScreenOn = false;
            }

            // Music Volume
            s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s);

            // SFX Volume
            s = br.readLine();
            gp.se.volumeScale = Integer.parseInt(s);

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
