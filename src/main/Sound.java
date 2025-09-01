package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];
    FloatControl fc;
    int volumeScale = 3;
    float volume;

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
        soundURL[11] = getClass().getResource("/sound/drinking.wav");
        soundURL[12] = getClass().getResource("/sound/fireball.wav");
        soundURL[13] = getClass().getResource("/sound/chopping_tree.wav");
        soundURL[14] = getClass().getResource("/sound/slider.wav");
        soundURL[15] = getClass().getResource("/sound/game_over.wav");
        soundURL[16] = getClass().getResource("/sound/door.wav");
        soundURL[17] = getClass().getResource("/sound/item_buying.wav");
        soundURL[18] = getClass().getResource("/sound/sleep.wav");
        soundURL[19] = getClass().getResource("/sound/attack_block.wav");
        soundURL[20] = getClass().getResource("/sound/attack_parry.wav");
    }

    public void fileSetter(int i) {
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();

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

    public void checkVolume() {
        switch(volumeScale) {
            case 0: volume = -80f; break;
            case 1: volume = -20f; break;
            case 2: volume = -12f; break;
            case 3: volume = -5f; break;
            case 4: volume = 1f; break;
            case 5: volume = 6f; break;
        }
        fc.setValue(volume);
    }

}

