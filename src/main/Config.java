package main;

import java.io.*;

/**
 * Persists a few settings to disk so they survive restarts:
 * full screen on/off, music volum, and SFX volume.
 * The format is a tiny text file (one value per line)
 */
public class Config {
    GamePanel gp;

    /**
     * Keep a handle to the game so it can read/write its settings.
     * @param gp
     */
    public Config(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Save current settings to {@code config.txt}.
     * Line 1: "on"/"off" for full screen.
     * Line 2: music volume scale (int).
     * Line 3: SFX volume scale (int).
     */
    public void saveConfig() {

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            // full screen settings
            if (gp.fullScreenOn) {
                bw.write("on");
            }

            else {
                bw.write("off");
            }

            bw.newLine();

            // music volume
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            // sfx volume
            bw.write(String.valueOf(gp.se.volumeScale));
            bw.newLine();
            bw.close();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load settings from {@code config.txt} if present.
     * Falls back to current in-memory defaults on error.
     */
    public void loadConfig() {

        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));
            String s = br.readLine();

            // full screen
            if (s.equals("on")) {
                gp.fullScreenOn = true;
            }

            else if (s.equals("off")) {
                gp.fullScreenOn = false;
            }

            // music volume
            s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s);

            // sfx volume
            s = br.readLine();
            gp.se.volumeScale = Integer.parseInt(s);
            br.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
