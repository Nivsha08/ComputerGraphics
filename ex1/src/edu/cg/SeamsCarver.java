package edu.cg;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SeamsCarver extends ImageProcessor {

	// MARK: An inner interface for functional programming.
	@FunctionalInterface
	interface ResizeOperation {
		BufferedImage resize();
	}

	private int numOfSeams;
	private ResizeOperation resizeOp;
	boolean[][] imageMask;
	BufferedImage resultImage;
	ImagePixel[][] energyMap;
	ArrayList<Seam> selectedSeams;

	public SeamsCarver(Logger logger, BufferedImage workingImage, int outWidth, RGBWeights rgbWeights,
			boolean[][] imageMask) {
		super((s) -> logger.log("Seam carving: " + s), workingImage, rgbWeights, outWidth, workingImage.getHeight());

		numOfSeams = Math.abs(outWidth - inWidth);
		this.imageMask = imageMask;
		if (inWidth < 2 | inHeight < 2)
			throw new RuntimeException("Can not apply seam carving: workingImage is too small");

		if (numOfSeams > inWidth / 2)
			throw new RuntimeException("Can not apply seam carving: too many seams...");

		// Setting resizeOp by with the appropriate method reference
		if (outWidth > inWidth)
			resizeOp = this::increaseImageWidth;
		else if (outWidth < inWidth)
			resizeOp = this::reduceImageWidth;
		else
			resizeOp = this::duplicateWorkingImage;

		resultImage = duplicateWorkingImage();
		energyMap = this.createEnergyMap();
		selectedSeams = new ArrayList<>();

		this.logger.log("preliminary calculations were ended.");
	}

	public BufferedImage resize() {
		return resizeOp.resize();
	}

	private ImagePixel[][] createEnergyMap() {
		ImagePixel[][] result = new ImagePixel[resultImage.getHeight()][resultImage.getWidth()];
		setForEachInputParameters();
		forEach((y, x) -> {
			Color pixelColor = new Color(resultImage.getRGB(x, y));
			int pixelEnergy = calculatePixelEnergy(x, y);
			result[y][x] = new ImagePixel(x, y, pixelColor, pixelEnergy);
		});
		return result;
	}

	private int calculatePixelEnergy(int x, int y) {
		int current = getPixelGrayscaleValue(x, y);
		int deltaX = isOnXBoundary(x) ?
				(current - getPixelGrayscaleValue(x - 1, y)) : (current - getPixelGrayscaleValue(x + 1, y));
		int deltaY = isOnYBoundary(y) ?
				(current - getPixelGrayscaleValue(x, y - 1)) : (current - getPixelGrayscaleValue(x, y + 1));
		return Math.abs(deltaX) + Math.abs(deltaY);
	}

	private int getPixelGrayscaleValue(int x, int y) {
		Color c = new Color(resultImage.getRGB(x, y));
		int greyColor = this.getGrayscaleColor(c, rgbWeights);
		return greyColor;
	}

	private boolean isOnXBoundary(int x) {
		return (x == resultImage.getWidth() - 1);
	}

	private boolean isOnYBoundary(int y) {
		return (y == resultImage.getHeight() - 1);
	}

	private BufferedImage reduceImageWidth() {
		for (int i = 0; i < numOfSeams; i++) {
			Seam s = new Seam(resultImage, energyMap);
			resultImage = removeSeamFromImage(s);
			energyMap = updateEnergyMapAfterSeamRemoval(s);
			selectedSeams.add(s);
		}
		return resultImage;
	}

	private BufferedImage removeSeamFromImage(Seam s) {
		BufferedImage result = newEmptyImage(resultImage.getWidth() - 1, resultImage.getHeight());
		for (int i = 0; i < resultImage.getHeight(); i++) {
			int resultCol = 0;
			int seamPixelIndex = s.getPath().get(i).getWidth();
			for (int j = 0; j < resultImage.getWidth(); j++) {
				if (j != seamPixelIndex) {
					result.setRGB(resultCol, i, resultImage.getRGB(j, i));
					resultCol++;
				}
			}
		}
		return result;
	}

	private ImagePixel[][] updateEnergyMapAfterSeamRemoval(Seam s) {
		ImagePixel[][] result = new ImagePixel[resultImage.getHeight()][resultImage.getWidth()];
		for (int i = 0; i < energyMap.length; i++) {
			int resultCol = 0;
			int seamPixelIndex = s.getPath().get(i).getWidth();
			for (int j = 0; j < energyMap[0].length; j++) {
				ImagePixel current = energyMap[i][j];
				if (j < seamPixelIndex) {
					result[i][resultCol] = ImagePixel.createCopy(current);
					if (j == seamPixelIndex - 1) {
						result[i][resultCol].setEnergy(calculatePixelEnergy(j, i));
					}
					resultCol++;
				}
				else if (j > seamPixelIndex) {
					result[i][resultCol] = ImagePixel.createCopy(current);
					result[i][resultCol].widthLoc--;
					if (j == seamPixelIndex + 1) {
						result[i][resultCol].setEnergy(calculatePixelEnergy(resultCol, i));
					}
					resultCol++;
				}
			}
		}
		return result;
	}

	private BufferedImage increaseImageWidth() {
		// TODO: Implement this method, remove the exception.
		throw new UnimplementedMethodException("increaseImageWidth");
	}

	public BufferedImage showSeams(int seamColorRGB) {
		reduceImageWidth();
		BufferedImage result = duplicateWorkingImage();
		for (Seam s : selectedSeams) {
			for (SeamCoordinates p : s.optimalPath) {
				result.setRGB(p.getWidth(), p.getHeight(), seamColorRGB);
			}
		}
		return result;
	}

	public boolean[][] getMaskAfterSeamCarving() {
		// TODO: Implement this method, remove the exception.
		// This method should return the mask of the resize image after seam carving.
		// Meaning, after applying Seam Carving on the input image,
		// getMaskAfterSeamCarving() will return a mask, with the same dimensions as the
		// resized image, where the mask values match the original mask values for the
		// corresponding pixels.
		// HINT: Once you remove (replicate) the chosen seams from the input image, you
		// need to also remove (replicate) the matching entries from the mask as well.
		throw new UnimplementedMethodException("getMaskAfterSeamCarving");
	}

	//testing
	public BufferedImage gradientMap() {
		logger.log("Preparing for showing gradient map...");
		BufferedImage ans = newEmptyInputSizedImage();

		forEach((y, x) -> {
			long energyLevel = energyMap[y][x].getEnergy();
			int greyColor = (int)energyLevel;
			Color color = new Color(greyColor, greyColor, greyColor);
			ans.setRGB(x, y, color.getRGB());
		});

		logger.log("Changing to gradient map done!");
		return ans;
	}

}
