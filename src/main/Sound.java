package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/sound/main_bgm.wav");
        soundURL[1] = getClass().getResource("/sound/coin.wav");
        soundURL[2] = getClass().getResource("/sound/powerup.wav");
        soundURL[3] = getClass().getResource("/sound/door_unlock.wav");
        soundURL[4] = getClass().getResource("/sound/success.wav");
        soundURL[5] = getClass().getResource("/sound/hit_monster.wav");
        soundURL[6] = getClass().getResource("/sound/receive_damage.wav");
        soundURL[7] = getClass().getResource("/sound/swing_weapon.wav");
        soundURL[8] = getClass().getResource("/sound/level_up.wav");
        soundURL[9] = getClass().getResource("/sound/cursor_move.wav");
        soundURL[10] = getClass().getResource("/sound/cursor_select.wav");
    }

    public void fileSetter(int i) {
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void playAudio() {
        clip.start();
    }

    public void loopAudio() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopAudio() {
        clip.stop();
    }

}

