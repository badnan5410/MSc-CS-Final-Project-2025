package tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

/**
 * Destructible dry tree tile.
 * This interactive tile can be chopped down with an axe. When destroyed, it is replaced with an {@code IT_Trunk} at the same grid location and emits wood-colored particles. It also plays a chopping sound when hit.
 */
public class IT_Drytree extends InteractiveTile {
    GamePanel gp;

    /**
     * Creates a dry tree at the given tile grid position.
     *
     * @param gp  game context
     * @param col world column in tile units
     * @param row world row in tile units
     */
    public IT_Drytree(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.TILE_SIZE*col;
        this.worldY = gp.TILE_SIZE*row;

        down1 = setup("/tiles_interactive/dry_tree");
        destructible = true;
        life = 8;
    }

    /**
     * Returns whether the provided entity has the correct tool to interact with this tile. For this tile, an axe is required.
     *
     * @param entity the interacting entity (usually the player)
     * @return {@code true} if the entity's current weapon is an axe
     */
    public boolean checkTool(Entity entity) {
        return entity.currentWeapon.type == TYPE_AXE;
    }

    /**
     * Plays the sound effect associated with chopping the tree.
     */
    public void soundEffect() {
        gp.soundEffect(13);
    }

    /**
     * Returns the replacement tile that should appear once this tile is
     * destroyed. For a dry tree, this is a trunk at the same grid location.
     *
     * @return an {@code IT_Trunk} positioned where this tree stood
     */
    public InteractiveTile getDestroyedImage() {
        InteractiveTile tile = new IT_Trunk(gp, worldX/gp.TILE_SIZE, worldY/gp.TILE_SIZE);
        return tile;
    }

    /**
     * Particle color used when the tree is hit or destroyed.
     *
     * @return a wood-brown color
     */
    public Color getParticleColor() {
        return new Color(90, 58, 31);
    }

    /**
     * Size in pixels of each emitted particle.
     *
     * @return particle size
     */
    public int getParticleSize() {
        return 6;
    }

    /**
     * Base speed applied to the emitted particles.
     *
     * @return particle speed
     */
    public int getParticleSpeed() {
        return 1;
    }

    /**
     * Lifetime in ticks for the emitted particles.
     *
     * @return particle lifetime
     */
    public int getParticleMaxLife() {
        return 20;
    }
}
