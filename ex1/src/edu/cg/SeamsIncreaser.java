package edu.cg;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SeamsIncreaser {

    BufferedImage workingImage;
    boolean[][] imageMask;

    public SeamsIncreaser(BufferedImage workingImage, boolean[][] imageMask) {
        this.workingImage = workingImage;
        this.imageMask = imageMask;
    }

    private BufferedImage newEmptyImage(int width, int height) {
        return new BufferedImage(width, height, workingImage.getType());
    }

    public SeamCarvingResult increaseImageWidth(ArrayList<Seam> seams) {
        BufferedImage result = workingImage;
        int indent = 0;
        for (Seam s : seams) {
            result = duplicateSeamInImage(result, s, indent);
            imageMask = increaseMaskSize(s, indent);
            indent++;
        }
        return new SeamCarvingResult(result, imageMask);
    }

    private BufferedImage duplicateSeamInImage(BufferedImage image, Seam s, int indent) {
        BufferedImage result = newEmptyImage(image.getWidth() + 1, image.getHeight());
        for (int i = 0; i < image.getHeight(); i++) {
            int resultCol = 0;
            int seamPixelIndex = s.getPath().get(i).getWidth() + indent;
            for (int j = 0; j < image.getWidth(); j++) {
                if (j == seamPixelIndex) {
                    result.setRGB(resultCol, i, image.getRGB(j, i));
                    resultCol++;
                    result.setRGB(resultCol, i, image.getRGB(j, i));
                }
                else {
                    result.setRGB(resultCol, i, image.getRGB(j, i));
                }
                resultCol++;
            }
        }
        return result;
    }

    private boolean[][] increaseMaskSize(Seam s, int indent) {
        boolean[][] result = new boolean[imageMask.length][imageMask[0].length + 1];
        for (int i = 0; i < imageMask.length; i++) {
            int resultCol = 0;
            int seamPixelIndex = s.getPath().get(i).getWidth() + indent;
            for (int j = 0; j < imageMask[0].length; j++) {
                if (j == seamPixelIndex) {
                    imageMask[i][j] = false;
                    result[i][resultCol] = imageMask[i][j];
                    resultCol++;
                    result[i][resultCol] = imageMask[i][j];
                }
                else {
                    result[i][resultCol] = imageMask[i][j];
                }
                resultCol++;
            }
        }
        return result;
    }

}
