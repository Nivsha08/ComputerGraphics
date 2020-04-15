package edu.cg;

import edu.cg.menu.SeamCarvingResult;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SeamsReducer {

    BufferedImage workingImage;
    boolean[][] imageMask;

    public SeamsReducer(BufferedImage workingImage, boolean[][] imageMask) {
        this.workingImage = workingImage;
        this.imageMask = imageMask;
    }

    private BufferedImage newEmptyImage(int width, int height) {
        return new BufferedImage(width, height, workingImage.getType());
    }

    public SeamCarvingResult reduceImageWidth(ArrayList<Seam> seams) {
        BufferedImage result = workingImage;
        for (Seam s : seams) {
            result = removeSeamFromImage(result, s);
            imageMask = reduceMaskSize(s);
        }
        return new SeamCarvingResult(result, imageMask);
    }

    private BufferedImage removeSeamFromImage(BufferedImage image, Seam s) {
        BufferedImage result = newEmptyImage(image.getWidth() - 1, image.getHeight());
        for (int i = 0; i < image.getHeight(); i++) {
            int resultCol = 0;
            int seamPixelIndex = s.getPath().get(i).getWidth();
            for (int j = 0; j < image.getWidth(); j++) {
                if (j != seamPixelIndex) {
                    result.setRGB(resultCol, i, image.getRGB(j, i));
                    resultCol++;
                }
            }
        }
        return result;
    }

    private boolean[][] reduceMaskSize(Seam s) {
        boolean[][] result = new boolean[imageMask.length][imageMask[0].length - 1];
        for (int i = 0; i < imageMask.length; i++) {
            int resultCol = 0;
            int seamPixelIndex = s.getPath().get(i).getWidth();
            for (int j = 0; j < imageMask[0].length; j++) {
                if (j != seamPixelIndex) {
                    result[i][resultCol] = imageMask[i][j];
                    resultCol++;
                }
            }
        }
        return result;
    }

}
