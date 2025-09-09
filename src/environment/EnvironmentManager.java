package environment;

import main.GamePanel;

import java.awt.*;

/**
 * Coordinates environment-wide visual systems (e.g., lighting).
 * Acts as a thin façade around {@link Lighting} for init/update/draw.
 */
public class EnvironmentManager {
    GamePanel gp;

    // active lighting controller
    public Lighting lighting;

    /**
     * Creates the manager.
     *
     * @param gp game context
     */
    public EnvironmentManager(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Initializes environment systems (currently just lighting).
     */
    public void setup() {
        lighting = new Lighting(gp);
    }

    /**
     * Steps time-based effects (delegates to {@link Lighting#update()}).
     */
    public void update() {
        lighting.update();
    }

    /**
     * Renders environment overlays on top of the world.
     *
     * @param g2 target graphics
     */
    public void draw(Graphics2D g2) {
        lighting.draw(g2);
    }
}
