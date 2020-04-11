package edu.cg;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Seam {

    int seamIndex;
    BufferedImage resultImage;
    ImagePixel[][] energyMap;
    ImagePixel[][] costMatrix;
    ArrayList<ImagePixel> optimalPath;

    public Seam(BufferedImage resultImage, ImagePixel[][] energyMap, int seamIndex) {
        this.resultImage = resultImage;
        this.energyMap = energyMap;
        this.seamIndex = seamIndex;
        optimalPath = findOptimalPath();
    }

    private ArrayList<ImagePixel> findOptimalPath() {
        costMatrix = calculateCostsMatrix();
        ImagePixel lastPixelInSeam = getLastPixelInSeam();
        System.out.println("minimal pixel in last row: " + lastPixelInSeam.getEnergy());
        return lastPixelInSeam.getOptimalPath();
    }

    private ImagePixel[][] calculateCostsMatrix() {
        ImagePixel[][] result = new ImagePixel[resultImage.getHeight()][resultImage.getWidth()];

        for (int i = 0; i < result[0].length; i++) {
            result[0][i] = energyMap[0][i];
        }

        for (int i = 1; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                ArrayList<ImagePixel> pixelNeighbors = new ArrayList<>(Arrays.asList(result[i - 1][j]));
                long pixelEnergy = energyMap[i][j].getEnergy();
                if (j == 0) {
                    pixelNeighbors.add(result[i - 1][j + 1]);
                }
                else if (j == result[0].length - 1) {
                    pixelNeighbors.add(result[i - 1][j - 1]);
                }
                else {
                    pixelNeighbors.addAll(Arrays.asList(result[i - 1][j - 1], result[i - 1][j + 1]));
                }
                ImagePixel minimalNeighbor = Collections.min(pixelNeighbors);
                pixelEnergy += minimalNeighbor.getEnergy();
                ImagePixel updatedPixel = ImagePixel.createCopy(energyMap[i][j]);
                updatedPixel.setEnergy(pixelEnergy);
                updatedPixel.updatePath(minimalNeighbor);
                result[i][j] = updatedPixel;
            }
        }
//        printGrid(result);
        return result;
    }

    private ImagePixel getLastPixelInSeam() {
        ArrayList<ImagePixel> costMatrixLastRow = new ArrayList<>(Arrays.asList(costMatrix[costMatrix.length - 1]));
        return Collections.min(costMatrixLastRow);
    }

    public int getIndex() {
        return this.seamIndex;
    }

    public void printGrid(ImagePixel[][] a)
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
