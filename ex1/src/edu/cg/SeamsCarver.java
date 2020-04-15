package edu.cg;

import edu.cg.menu.SeamCarvingResult;

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
	boolean[][] originalImageMask;
	int[][] originalPixelsIndices;
	int[][] greyscaleArray;
	ImagePixel[][] energyMap;
	ArrayList<Seam> selectedSeams;

	public SeamsCarver(Logger logger, BufferedImage workingImage, int outWidth, RGBWeights rgbWeights,
			boolean[][] imageMask) {
		super((s) -> logger.log("Seam carving: " + s), workingImage, rgbWeights, outWidth, workingImage.getHeight());

		numOfSeams = Math.abs(outWidth - inWidth);
		this.imageMask = imageMask;
		this.originalImageMask = imageMask;
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

		originalPixelsIndices = initOriginalIndicesHelperArray();
		greyscaleArray = initGreyscaleArray();
		selectedSeams = new ArrayList<>();

		this.logger.log("preliminary calculations were ended.");
	}

	private int[][] initOriginalIndicesHelperArray() {
		int[][] result = new int[workingImage.getHeight()][workingImage.getWidth()];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				result[i][j] = j;
			}
		}
		return result;
	}

	public BufferedImage resize() {
		return resizeOp.resize();
	}

	private int[][] initGreyscaleArray() {
		int[][] result = new int[workingImage.getHeight()][workingImage.getWidth()];
		BufferedImage greyscaleImage = greyscale();
		setForEachInputParameters();
		forEach((y, x) -> {
			result[y][x] = getGrayscaleColor(new Color(greyscaleImage.getRGB(x, y)), rgbWeights);
		});
		return result;
	}

	private ArrayList<Seam> findSeams() {
		ArrayList<Seam> seamsList = new ArrayList<>();
		energyMap = this.createEnergyMap();
		for (int i = 0; i < numOfSeams; i++) {
			Seam s = new Seam(energyMap, greyscaleArray, originalPixelsIndices);
			seamsList.add(s);
			greyscaleArray = updateGreyscaleArray(s);
			imageMask = updateImageMask(s);
			energyMap = updateEnergyMap(s);
			originalPixelsIndices = updateOriginalPixelIndices(s);
		}
		return seamsList;
	}

	private ImagePixel[][] createEnergyMap() {
		ImagePixel[][] result = new ImagePixel[workingImage.getHeight()][workingImage.getWidth()];
		setForEachInputParameters();
		forEach((y, x) -> {
			Color pixelColor = new Color(workingImage.getRGB(x, y));
			if (imageMask[y][x]) {
				result[y][x] = new ImagePixel(x, y, pixelColor, Long.MIN_VALUE);
			}
			else {
				long pixelEnergy = calculatePixelEnergy(x, y);
				result[y][x] = new ImagePixel(x, y, pixelColor, pixelEnergy);
			}
		});
		return result;
	}

	private long calculatePixelEnergy(int x, int y) {
		if (imageMask[y][x])
			return Long.MIN_VALUE;
		int current = greyscaleArray[y][x];
		int deltaX = isOnXBoundary(x) ? (greyscaleArray[y][x - 1] - current) : (greyscaleArray[y][x + 1] - current);
		int deltaY = isOnYBoundary(y) ? (greyscaleArray[y - 1][x] - current) : (greyscaleArray[y + 1][x] - current);
		return (long)this.calculateEuclideanNorm(deltaX, deltaY);
	}

	private double calculateEuclideanNorm(int deltaX, int deltaY) {
		return Math.abs(Math.sqrt((deltaX * deltaX) + (deltaY * deltaY)));
	}

	private boolean isOnXBoundary(int x) {
		return (x == greyscaleArray[0].length - 1);
	}

	private boolean isOnYBoundary(int y) {
		return (y == greyscaleArray.length - 1);
	}

	private BufferedImage reduceImageWidth() {
		selectedSeams = findSeams();
		SeamsReducer reducer = new SeamsReducer(workingImage, originalImageMask);
		SeamCarvingResult result = reducer.reduceImageWidth(selectedSeams);
		imageMask = result.getUpdatedImageMask();
		return result.getUpdatedImage();
	}

	private int[][] updateGreyscaleArray(Seam s) {
		int[][] result = new int[greyscaleArray.length][greyscaleArray[0].length - 1];
		for (int i = 0; i < greyscaleArray.length; i++) {
			int resultCol = 0;
			int seamPixelIndex = s.getPath().get(i).getWidth();
			for (int j = 0; j < greyscaleArray[0].length; j++) {
				if (j != seamPixelIndex) {
					result[i][resultCol] = greyscaleArray[i][j];
					resultCol++;
				}
			}
		}
		return result;
	}

	private boolean[][] updateImageMask(Seam s) {
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

	private ImagePixel[][] updateEnergyMap(Seam s) {
		ImagePixel[][] result = new ImagePixel[energyMap.length][energyMap[0].length - 1];
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

	private int[][] updateOriginalPixelIndices(Seam s) {
		int[][] result = new int[originalPixelsIndices.length][originalPixelsIndices[0].length - 1];
		for (int i = 0; i < originalPixelsIndices.length; i++) {
			int resultCol = 0;
			int seamPixelIndex = s.getPath().get(i).getWidth();
			for (int j = 0; j < originalPixelsIndices[0].length; j++) {
				if (j != seamPixelIndex) {
					result[i][resultCol] = originalPixelsIndices[i][j];
					resultCol++;
				}
			}
		}
		return result;
	}

	private BufferedImage increaseImageWidth() {
		selectedSeams = findSeams();
		SeamsIncreaser increaser = new SeamsIncreaser(workingImage, originalImageMask);
		SeamCarvingResult result = increaser.increaseImageWidth(selectedSeams);
		imageMask = result.getUpdatedImageMask();
		return result.getUpdatedImage();
	}

	public BufferedImage showSeams(int seamColorRGB) {
		selectedSeams = findSeams();
		BufferedImage result = duplicateWorkingImage();
		for (Seam s : selectedSeams) {
			for (SeamCoordinates p : s.getPath()) {
				result.setRGB(p.getOriginalWidthLoc(), p.getHeight(), seamColorRGB);
			}
		}
		return result;
	}

	public boolean[][] getMaskAfterSeamCarving() {
		return imageMask;
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
