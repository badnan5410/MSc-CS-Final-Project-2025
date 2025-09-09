package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import java.awt.*;

/**
 * Player-cast fireball projectile.
 *
 * Behavior:
 * - Moves straight in the current direction at a fixed speed.
 * - Has a finite lifetime in ticks; despawns when life reaches zero.
 * - On hit: deals magic damage, spawns flame-colored particles, and despawns.
 * - Consumes a small amount of the user's mana when fired.
 *
 * Sprites:
 * - Directional animation frames are loaded via {@link #getImage()}.
 *
 * Stats (defaults):
 * - speed: 5
 * - maxLife: 60
 * - attack: 2
 * - mana cost (useCost): 1
 */
public class Fireball extends Projectile {
    GamePanel gp;
    public static final String objName = "Fireball";

    public Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = objName;
        speed = 5;
        maxLife = 60;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    /**
     * Loads directional animation frames for the fireball.
     */
    public void getImage() {
        up1 = setup("/projectiles/fireball_up_1");
        up2 = setup("/projectiles/fireball_up_2");
        down1 = setup("/projectiles/fireball_down_1");
        down2 = setup("/projectiles/fireball_down_2");
        right1 = setup("/projectiles/fireball_right_1");
        right2 = setup("/projectiles/fireball_right_2");
        left1 = setup("/projectiles/fireball_left_1");
        left2 = setup("/projectiles/fireball_left_2");
    }

    /**
     * Checks if the caster has enough mana to fire.
     *
     * @param user the entity attempting to cast
     * @return true if user.mana >= useCost
     */
    public boolean checkResource(Entity user) {
        return user.mana >= useCost;
    }

    /**
     * Deducts the mana cost from the caster.
     *
     * @param user the entity who cast the projectile
     */
    public void subtractResource(Entity user) {
        user.mana -= useCost;
    }

    /**
     * @return Particle tint for the fireball hit effect.
     */
    public Color getParticleColor() {
        return new Color(240, 50, 0);
    }

    /**
     * @return Particle square size in pixels.
     */
    public int getParticleSize() {
        return 10;
    }

    /**
     * @return Particle travel speed scalar.
     */
    public int getParticleSpeed() {
        return 1;
    }

    /**
     * @return Particle lifetime in ticks.
     */
    public int getParticleMaxLife() {
        return 20;
    }
}
