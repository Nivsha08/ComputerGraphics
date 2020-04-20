package edu.cg;

import java.awt.image.BufferedImage;

/**
 * An object containing the details of a seam removal or insertion operation -
 * the updated image and the updated mask.
 */
public class SeamCarvingResult {

    BufferedImage updatedImage;
    boolean[][] updatedImageMask;

    public SeamCarvingResult(BufferedImage updatedImage, boolean[][] updatedImageMask) {
        this.updatedImage = updatedImage;
        this.updatedImageMask = updatedImageMask;
    }

    public BufferedImage getUpdatedImage() {
        return updatedImage;
    }

    public boolean[][] getUpdatedImageMask() {
        return updatedImageMask;
    }
}
