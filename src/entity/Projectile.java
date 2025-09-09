package entity;

import main.GamePanel;

/**
 * Base projectile entity fired by the player or by monsters.
 *
 * Subclasses (e.g., fireballs, slimeballs) must configure visuals, hitbox, speed, damage, knockback, and lifetime. This class handles travel in a straight line, basic collision with targets, sprite animation, and expiry.
 *
 * Required overrides:
 * - {@link #projectileBoost()}        // optional per-difficulty/day buffs
 * - {@link #checkResource(Entity)}    // return true if shooter can fire
 * - {@link #subtractResource(Entity)} // consume resource (e.g., mana)
 */
public class Projectile extends Entity {
    Entity user;

    public Projectile(GamePanel gp) {
        super(gp);
    }

    /**
     * Initializes a projectile for firing.
     *
     * @param worldX    start X in world pixels (usually shooter’s center/weapon tip)
     * @param worldY    start Y in world pixels
     * @param direction "up", "down", "left", or "right"
     * @param alive     initial alive flag (typically true)
     * @param user      the firing entity; used for ownership and FX
     */
    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
    }

    /**
     * Advances the projectile:
     * - If owned by player, checks hit on monsters; if owned by a monster, checks hit on player.
     * - Moves one step along {@code direction}.
     * - Decrements lifetime and toggles a two-frame sprite.
     * - Marks itself dead when it hits something or its life expires.
     *
     * Note: By default this does not stop on tiles; subclasses can add a tile collision check if projectiles should be blocked by walls.
     */
    public void update() {

        if (user == gp.player) {
            int i = gp.cHandler.checkEntity(this, gp.monster);

            if (i != -1) {
                gp.player.damageMonster(i, this, attack*(gp.player.level/2), knockBackPower, true);
                generateParticle(user.projectile, gp.monster[gp.currentMap][i]);
                alive = false;
            }
        }

        else {
            boolean touchPlayer = gp.cHandler.checkPlayer(this);

            if (!gp.player.invincible && touchPlayer) {
                damagePlayer(attack);
                generateParticle(user.projectile, user.gp.player);
                alive = false;
            }
        }

        switch (direction) {
            case "up": worldY -= speed; break;
            case "down": worldY += speed; break;
            case "right": worldX += speed; break;
            case "left": worldX -= speed; break;
        }

        life--;

        if (life < 0) {
            alive = false;
        }

        spriteCounter++;

        if (spriteCounter > 12) {

            if (spriteNum == 1) {
                spriteNum = 2;
            }

            else if (spriteNum == 2) {
                spriteNum = 1;
            }

            spriteCounter = 0;
        }
    }

    /**
     * Optional per-tick buff hook (e.g., scale stats with time of day/area).
     * Default no-op; override in subclasses.
     */
    public void projectileBoost() {}

    /**
     * Returns whether the shooter has enough resource to fire (e.g., mana).
     * Default false; must be overridden by resource-using projectiles.
     */
    public boolean checkResource(Entity user) {
        return false;
    }

    /**
     * Subtracts the firing resource from the shooter (e.g., reduce mana).
     * Default no-op; override alongside {@link #checkResource(Entity)}.
     */
    public void subtractResource(Entity user) {}
}
