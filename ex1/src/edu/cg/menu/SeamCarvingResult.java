package edu.cg.menu;

import java.awt.image.BufferedImage;

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
