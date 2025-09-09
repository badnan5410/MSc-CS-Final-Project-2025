package main;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Utility methods for simple image operations.
 * Currently, provides scaling for buffered images.
 */
public class UtilityTool {

    /**
     * Scales the given image to the target width and height.
     * Creates a new BufferedImage, draws the source into it, then returns the result.
     *
     * @param original source image to scale
     * @param width    target width in pixels
     * @param height   target height in pixels
     * @return a new image with the requested size; the source is not modified
     */
    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();
        return scaledImage;
    }
}
