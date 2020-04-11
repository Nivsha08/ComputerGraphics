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
	ImagePixel[][] energyMap;

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

		energyMap = this.createEnergyMap();

		// TODO: You may initialize your additional fields and apply some preliminary calculations.

		this.logger.log("preliminary calculations were ended.");
	}

	public BufferedImage resize() {
		return resizeOp.resize();
	}

	private ImagePixel[][] createEnergyMap() {
		ImagePixel[][] result = new ImagePixel[inHeight][inWidth];
		setForEachInputParameters();
		System.out.println("inWidth: "+inWidth + ", inHeight: " + inHeight);
		forEach((y, x) -> {
			Color pixelColor = new Color(workingImage.getRGB(x, y));
			int pixelEnergy = calculatePixelEnergy(x, y);
			result[y][x] = new ImagePixel(x, y, pixelColor, pixelEnergy);
		});
		return result;
	}

	private int getPixelGrayscaleColor(int x, int y) {
		Color c = new Color(workingImage.getRGB(x, y));
		int greyColor = this.getGrayscaleColor(c, rgbWeights);
		return greyColor;
	}

	private int calculatePixelEnergy(int x, int y) {
		int current = getPixelGrayscaleColor(x, y);
		int deltaX = (x == inWidth - 1) ?
				(current - getPixelGrayscaleColor(x - 1, y)) : (current - getPixelGrayscaleColor(x + 1, y));
		int deltaY = (y == inHeight - 1) ?
				(current - getPixelGrayscaleColor(x, y - 1)) : (current - getPixelGrayscaleColor(x, y + 1));
		return Math.abs(deltaX) + Math.abs(deltaY);
	}

	private BufferedImage reduceImageWidth() {
		for (int i = 0; i < numOfSeams; i++) {
			Seam s = new Seam(workingImage, energyMap);
		}
//		for each seam in numSeams:
//			s = new Seam(workingImage, energyMap);
//					for the bonus - save each of the seam
//			seamPath = s.findOptimalPath();
//			remove the result seam path from the image
//			update working image
//			update energy map
		return gradientMap();
	}

	private BufferedImage increaseImageWidth() {
		// TODO: Implement this method, remove the exception.
		throw new UnimplementedMethodException("increaseImageWidth");
	}

	public BufferedImage showSeams(int seamColorRGB) {
		// TODO: Implement this method (bonus), remove the exception.
		throw new UnimplementedMethodException("showSeams");
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
