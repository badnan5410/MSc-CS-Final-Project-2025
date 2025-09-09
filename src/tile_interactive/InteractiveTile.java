package tile_interactive;

import entity.Entity;
import main.GamePanel;

/**
 * Base class for interactive map tiles (e.g., breakables, plates).
 * Provides hooks for tool checks, SFX, destroyed-state replacement,
 * and a brief invincibility window after being hit.
 */
public class InteractiveTile extends Entity {
    GamePanel gp;

    /**
     * Creates an interactive tile located at the given grid cell.
     *
     * @param gp   game context
     * @param col  map column (tile units)
     * @param row  map row (tile units)
     */
    public InteractiveTile(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;
    }

    /**
     * Checks whether the given entity's tool can affect this tile.
     *
     * @param entity the actor attempting to use a tool
     * @return true if the tool is valid for this tile; otherwise false
     */
    public boolean checkTool(Entity entity) {
        return false;
    }

    /**
     * Plays the tile's hit sound. Override in subclasses.
     */
    public void soundEffect() {}

    /**
     * Returns the replacement tile to use after destruction.
     * Override to swap to a new tile state or null to remove.
     *
     * @return replacement {@code InteractiveTile} or null
     */
    public InteractiveTile getDestroyedImage() {
        InteractiveTile tile = null;
        return tile;
    }

    /**
     * Updates invincibility frames after the tile is hit.
     * Becomes vulnerable again after a short cooldown.
     */
    public void update() {

        if (invincible) {
            invincibleCounter++;

            if (invincibleCounter > 20) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }
}