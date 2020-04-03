package edu.cg;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Seam {

    BufferedImage workingImage;
    ImagePixel[][] costMatrix;

    public Seam(BufferedImage workingImage) {
        this.workingImage = workingImage;
    }

    private ArrayList<ImagePixel> findOptimalPath() {
        // costMatrix = calculateCostsMatrix()
        // find minimal cost in the bottom row of the cost matrix
        // return the pixel who's path is the optimal
        return null;
    }

    private ImagePixel[][] calculateCostsMatrix() {
        this.costMatrix = new ImagePixel[workingImage.getWidth()][workingImage.getHeight()];
        return null;
    }

}
