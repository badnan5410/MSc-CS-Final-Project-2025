package object;

import entity.Projectile;
import main.GamePanel;

import java.awt.*;

/**
 * Projectile used by Red Slimes.
 * Fast, straight-moving slime ball with moderate damage.
 * All directional frames use the same sprite.
 */
public class Slimeball_Red extends Projectile {
    GamePanel gp;
    public static final String objName = "Red Slime Ball";

    /**
     * Creates a red slimeball projectile and initializes its stats and sprites.
     *
     * @param gp the game panel context
     */
    public Slimeball_Red(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = objName;
        speed = 10;
        maxLife = 90;
        life = maxLife;
        defaultAttack = 4;
        attack = defaultAttack;
        knockBackPower = 0;
        alive = false;
        getImage();
    }

    /**
     * Loads the projectile sprites. Uses the same texture for all directions.
     */
    public void getImage() {
        up1 = setup("/projectiles/slimeball_red");
        up2 = setup("/projectiles/slimeball_red");
        down1 = setup("/projectiles/slimeball_red");
        down2 = setup("/projectiles/slimeball_red");
        right1 = setup("/projectiles/slimeball_red");
        right2 = setup("/projectiles/slimeball_red");
        left1 = setup("/projectiles/slimeball_red");
        left2 = setup("/projectiles/slimeball_red");
    }

    /**
     * Particle color emitted on impact.
     *
     * @return particle color
     */
    public Color getParticleColor() {
        return new Color(193, 106, 127);
    }

    /**
     * Particle square size in pixels.
     *
     * @return particle size
     */
    public int getParticleSize() {
        return 10;
    }

    /**
     * Particle movement speed multiplier.
     *
     * @return particle speed
     */
    public int getParticleSpeed() {
        return 1;
    }

    /**
     * Particle lifetime in ticks.
     *
     * @return particle max life
     */
    public int getParticleMaxLife() {
        return 20;
    }
}
