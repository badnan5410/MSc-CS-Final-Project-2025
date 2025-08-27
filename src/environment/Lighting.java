package environment;

import main.GamePanel;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Lighting {
    GamePanel gp;
    BufferedImage darknessFilter;

    public Lighting(GamePanel gp, int circleSize) {

        // instantiate buffered image
        darknessFilter = new BufferedImage(gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();

        // get the coordinates of the light circle
        int circleCenterX = gp.player.screenX + (gp.TILE_SIZE)/2;
        int circleCenterY = gp.player.screenY + (gp.TILE_SIZE)/2;

        // create a gradient effect in the light circle
        int gradientLevel = 12;
        Color gradientColour[] = new Color[gradientLevel];
        float gradientDistance[] = new float[gradientLevel];

        // set the different gradient colours
        gradientColour[0] = new Color(0, 0, 0, 0.1f);
        gradientColour[1] = new Color(0, 0, 0, 0.42f);
        gradientColour[2] = new Color(0, 0, 0, 0.52f);
        gradientColour[3] = new Color(0, 0, 0, 0.61f);
        gradientColour[4] = new Color(0, 0, 0, 0.69f);
        gradientColour[5] = new Color(0, 0, 0, 0.76f);
        gradientColour[6] = new Color(0, 0, 0, 0.82f);
        gradientColour[7] = new Color(0, 0, 0, 0.87f);
        gradientColour[8] = new Color(0, 0, 0, 0.91f);
        gradientColour[9] = new Color(0, 0, 0, 0.94f);
        gradientColour[10] = new Color(0, 0, 0, 0.96f);
        gradientColour[11] = new Color(0, 0, 0, 0.98f);

        // set the distance between each colour
        gradientDistance[0] = 0f;
        gradientDistance[1] = 0.4f;
        gradientDistance[2] = 0.5f;
        gradientDistance[3] = 0.6f;
        gradientDistance[4] = 0.65f;
        gradientDistance[5] = 0.7f;
        gradientDistance[6] = 0.75f;
        gradientDistance[7] = 0.8f;
        gradientDistance[8] = 0.85f;
        gradientDistance[9] = 0.9f;
        gradientDistance[10] = 0.95f;
        gradientDistance[11] = 1f;

        // create gradation paint settings for the light circle
        RadialGradientPaint gPaint = new RadialGradientPaint(circleCenterX, circleCenterY, (circleSize/2), gradientDistance, gradientColour);

        // set the gradient data on g2
        g2.setPaint(gPaint);

        // draw the darkness filter over the game screen
        g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
        g2.dispose();
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(darknessFilter, 0, 0, null);
    }
}
