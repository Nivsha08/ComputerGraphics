package edu.cg;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A class responsible for removing the pixels contained in the given image mask.
 * Uses SeamCarver to manipulate the image dimensions.
 */
public class ObjectRemover {

    final BufferedImage workingImage;
    final int seamAmountLowerBound;

    BufferedImage currentImage;
    boolean[][] currentImageMask;
    Logger logger;
    RGBWeights rgbWeights;

    public ObjectRemover(BufferedImage workingImage, boolean[][] imageMask, Logger logger, RGBWeights rgbWeights) {
        this.workingImage = workingImage;
        this.currentImage = duplicateWorkingImage();
        this.currentImageMask = imageMask;
        this.seamAmountLowerBound = (workingImage.getWidth() / 3) - 1;
        this.logger = logger;
        this.rgbWeights = rgbWeights;
    }

    public final BufferedImage duplicateWorkingImage() {
        BufferedImage output = new BufferedImage(workingImage.getWidth(), workingImage.getHeight(), workingImage.getType());
        for (int i = 0; i < workingImage.getHeight(); i++) {
            for (int j = 0; j < workingImage.getWidth(); j++) {
                output.setRGB(j, i, workingImage.getRGB(j, i));
            }
        }
        return output;
    }

    public SeamCarvingResult removeObject() {
        while (maskContainsTrueEntries()) {
            int numOfSeamsToRemove = getNumberOfSeamsToRemove();
            SeamCarvingResult reduceResult = modifyImage(currentImage.getWidth() - numOfSeamsToRemove);
            updateCurrentImageState(reduceResult);
            SeamCarvingResult increaseResult = modifyImage(workingImage.getWidth());
            updateCurrentImageState(increaseResult);
        }
        return new SeamCarvingResult(currentImage, currentImageMask);
    }

    private void updateCurrentImageState(SeamCarvingResult newState) {
        currentImage = newState.getUpdatedImage();
        currentImageMask = newState.getUpdatedImageMask();
    }

    private SeamCarvingResult modifyImage(int outWidth) {
        SeamsCarver carver = new SeamsCarver(logger, currentImage, outWidth, rgbWeights, currentImageMask);
        BufferedImage resultImage = carver.resize();
        boolean[][] newImageMask = carver.getMaskAfterSeamCarving();
        return new SeamCarvingResult(resultImage, newImageMask);
    }

    private boolean maskContainsTrueEntries() {
        boolean result = false;
        for (int i = 0; i < currentImageMask.length; i++) {
            for (int j = 0; j < currentImageMask[0].length; j++) {
                if (currentImageMask[i][j]) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    private int getNumberOfSeamsToRemove() {
        return Math.min(seamAmountLowerBound, getMaximumTrueValuesInMask());
    }

    private int getMaximumTrueValuesInMask() {
        ArrayList<Integer> rowsTrueValuesAmount = new ArrayList<>();
        for (int i = 0; i < currentImageMask.length; i++) {
            int sum = 0;
            for (int j = 0; j < currentImageMask[0].length; j++) {
                if (currentImageMask[i][j]) sum++;
            }
            rowsTrueValuesAmount.add(sum);
        }
        return Collections.max(rowsTrueValuesAmount);
    }

}
