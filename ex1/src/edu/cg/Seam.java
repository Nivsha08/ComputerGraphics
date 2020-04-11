package edu.cg;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Seam {

    BufferedImage resultImage;
    ImagePixel[][] energyMap;
    ImagePixel[][] costMatrix;
    ArrayList<SeamCoordinates> optimalPath;

    public Seam(BufferedImage resultImage, ImagePixel[][] energyMap) {
        this.resultImage = resultImage;
        this.energyMap = energyMap;
        optimalPath = findOptimalPath();
    }

    private ArrayList<SeamCoordinates> findOptimalPath() {
        costMatrix = calculateCostsMatrix();
        ImagePixel lastPixelInSeam = getLastPixelInSeam();
        System.out.println("minimal pixel in last row: " + lastPixelInSeam.getEnergy());
        return lastPixelInSeam.traceBackOptimalPath(costMatrix);
    }

    private ImagePixel[][] calculateCostsMatrix() {
        ImagePixel[][] result = new ImagePixel[resultImage.getHeight()][resultImage.getWidth()];

        for (int i = 0; i < result[0].length; i++) {
            result[0][i] = energyMap[0][i];
        }

        for (int i = 1; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                ImagePixel current = energyMap[i][j];
                ArrayList<ImagePixel> pixelNeighbors = current.getTopRowNeighbors(result);
                ImagePixel minimalNeighbor = Collections.min(pixelNeighbors);
                ImagePixel updatedPixel = ImagePixel.createCopy(current);
                updatedPixel.setEnergy(current.getEnergy() + minimalNeighbor.getEnergy());
                result[i][j] = updatedPixel;
            }
        }
        return result;
    }

    private ImagePixel getLastPixelInSeam() {
        ArrayList<ImagePixel> costMatrixLastRow = new ArrayList<>(Arrays.asList(costMatrix[costMatrix.length - 1]));
        return Collections.min(costMatrixLastRow);
    }

    public ArrayList<SeamCoordinates> getPath() { return this.optimalPath; }

    private void printGrid(ImagePixel[][] a)
    {
        for(int i = 0; i < a.length; i++)
        {
            for(int j = 0; j < a[0].length; j++)
            {
                System.out.printf("%5d ", a[i][j].getEnergy());
            }
            System.out.println();
        }
    }

}
