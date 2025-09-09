package entity;

import main.GamePanel;

import java.awt.*;

/**
 * Lightweight square particle used for hit and break effects.
 *
 * The particle starts centered on a generator entity and moves each tick using simple velocity components. It fades by lifetime and is removed when life reaches zero.
 */
public class Particle extends Entity {
    Entity generator;
    Color color;
    int size;
    int displacementX;
    int displacementY;

    /**
     * Creates a new particle centered on the generator.
     *
     * @param gp            game context
     * @param generator     source entity; used to derive starting position
     * @param color         render color
     * @param size          square size in pixels
     * @param speed         movement speed multiplier
     * @param maxLife       total lifetime in ticks
     * @param displacementX base horizontal velocity unit per tick
     * @param displacementY base vertical velocity unit per tick
     */
    public Particle(GamePanel gp, Entity generator, Color color, int size, int speed, int maxLife, int displacementX, int displacementY) {
        super(gp);
        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;
        this.displacementX = displacementX;
        this.displacementY = displacementY;
        life = maxLife;
        int offset = (gp.TILE_SIZE/2) - (size/2);
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
    }

    /**
     * Updates lifetime and position.
     *
     * Behavior:
     * - Decrements {@code life} each tick.
     * - Adds simple "gravity" by increasing {@code displacementY} during the final third of life.
     * - Moves by {@code displacementX * speed} and {@code displacementY * speed}.
     * - Sets {@code alive} to {@code false} when {@code life == 0}.
     */
    public void update() {
        life--;
        if (life < maxLife/3) {
            displacementY++;
        }

        worldX += displacementX *speed;
        worldY += displacementY *speed;

        if (life == 0) {
            alive = false;
        }
    }

    /**
     * Renders the particle as a filled square in screen space relative to the player's camera.
     *
     * @param g2 active graphics context
     */
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        g2.setColor(color);
        g2.fillRect(screenX, screenY, size, size);
    }
}
