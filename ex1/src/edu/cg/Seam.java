package edu.cg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * An object for representing a Seam. Contains the logic for calculating the optimal seam at the
 * given state of the energy map, by calculating the current cost matrix and performing the dynamic
 * programming procedures.
 */
public class Seam {

    int[][] greyscaleArray;
    ImagePixel[][] energyMap;
    ImagePixel[][] costMatrix;
    ArrayList<SeamCoordinates> optimalPath;

    public Seam(ImagePixel[][] energyMap, int[][] greyscaleArray, int[][] originalPixelsIndices) {
        this.greyscaleArray = greyscaleArray;
        this.energyMap = energyMap;
        optimalPath = findOptimalPath(originalPixelsIndices);
    }

    public ArrayList<SeamCoordinates> getPath() { return this.optimalPath; }

    private ArrayList<SeamCoordinates> findOptimalPath(int[][] originalPixelsIndices) {
        costMatrix = calculateCostsMatrix();
        ImagePixel lastPixelInSeam = getLastPixelInSeam();
        return lastPixelInSeam.traceBackOptimalPath(costMatrix, greyscaleArray, originalPixelsIndices);
    }

    private ImagePixel[][] calculateCostsMatrix() {
        ImagePixel[][] result = new ImagePixel[greyscaleArray.length][greyscaleArray[0].length];

        for (int i = 0; i < result[0].length; i++) {
            result[0][i] = ImagePixel.createCopy(energyMap[0][i]);
        }
        for (int i = 1; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                ImagePixel current = energyMap[i][j];
                ImagePixel updatedPixel = ImagePixel.createCopy(current);
                updatedPixel.setEnergy(current.getEnergy() +
                        current.getMinimalSeamStepCost(result, greyscaleArray).getCost());
                result[i][j] = updatedPixel;
            }
        }
        return result;
    }

    private ImagePixel getLastPixelInSeam() {
        ArrayList<ImagePixel> costMatrixLastRow = new ArrayList<>(Arrays.asList(costMatrix[costMatrix.length - 1]));
        return Collections.min(costMatrixLastRow);
    }

}
