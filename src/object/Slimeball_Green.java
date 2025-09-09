package object;

import entity.Projectile;
import main.GamePanel;

import java.awt.*;


/**
 * Projectile used by Green Slimes.
 * Slower than the red variant, with lighter damage. Uses a single sprite for all directions.
 */
public class Slimeball_Green extends Projectile {
    GamePanel gp;
    public static final String objName = "Green Slime Ball";

    /**
     * Creates a green slimeball projectile and initializes its stats and sprites.
     *
     * @param gp the game panel context
     */
    public Slimeball_Green(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = objName;
        speed = 6;
        maxLife = 90;
        life = maxLife;
        defaultAttack = 2;
        attack = defaultAttack;
        knockBackPower = 0;
        alive = false;
        getImage();
    }

    /**
     * Loads the projectile sprite. Same texture is reused for all directions.
     */
    public void getImage() {
        up1 = setup("/projectiles/slimeball_green");
        up2 = setup("/projectiles/slimeball_green");
        down1 = setup("/projectiles/slimeball_green");
        down2 = setup("/projectiles/slimeball_green");
        right1 = setup("/projectiles/slimeball_green");
        right2 = setup("/projectiles/slimeball_green");
        left1 = setup("/projectiles/slimeball_green");
        left2 = setup("/projectiles/slimeball_green");
    }

    /**
     * Particle color emitted on impact.
     *
     * @return particle color
     */
    public Color getParticleColor() {
        return new Color(106, 193, 127);
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
