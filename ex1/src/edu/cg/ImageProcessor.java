package edu.cg;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageProcessor extends FunctioalForEachLoops {
	// MARK: fields
	public final Logger logger;
	public final BufferedImage workingImage;
	public final RGBWeights rgbWeights;
	public final int inWidth;
	public final int inHeight;
	public final int workingImageType;
	public final int outWidth;
	public final int outHeight;

	// MARK: constructors
	public ImageProcessor(Logger logger, BufferedImage workingImage, RGBWeights rgbWeights, int outWidth,
			int outHeight) {
		super(); // initializing for each loops...

		this.logger = logger;
		this.workingImage = workingImage;
		this.rgbWeights = rgbWeights;
		inWidth = workingImage.getWidth();
		inHeight = workingImage.getHeight();
		workingImageType = workingImage.getType();
		this.outWidth = outWidth;
		this.outHeight = outHeight;
		setForEachInputParameters();
	}

	public ImageProcessor(Logger logger, BufferedImage workingImage, RGBWeights rgbWeights) {
		this(logger, workingImage, rgbWeights, workingImage.getWidth(), workingImage.getHeight());
	}

	// Changes the picture's hue - example
	public BufferedImage changeHue() {
		logger.log("Prepareing for hue changing...");

		int r = rgbWeights.redWeight;
		int g = rgbWeights.greenWeight;
		int b = rgbWeights.blueWeight;
		int max = rgbWeights.maxWeight;
		BufferedImage ans = newEmptyInputSizedImage();

		forEach((y, x) -> {
			Color c = new Color(workingImage.getRGB(x, y));
			int red = r * c.getRed() / max;
			int green = g * c.getGreen() / max;
			int blue = b * c.getBlue() / max;
			Color color = new Color(red, green, blue);
			ans.setRGB(x, y, color.getRGB());
		});

		logger.log("Changing hue done!");

		return ans;
	}

	// Sets the ForEach parameters with the input dimensions
	public final void setForEachInputParameters() {
		setForEachParameters(inWidth, inHeight);
	}

	// Sets the ForEach parameters with the output dimensions
	public final void setForEachOutputParameters() {
		setForEachParameters(outWidth, outHeight);
	}

	// A helper method that creates an empty image with the specified input dimensions.
	public final BufferedImage newEmptyInputSizedImage() {
		return newEmptyImage(inWidth, inHeight);
	}

	// A helper method that creates an empty image with the specified output dimensions.
	public final BufferedImage newEmptyOutputSizedImage() {
		return newEmptyImage(outWidth, outHeight);
	}

	// A helper method that creates an empty image with the specified dimensions.
	public final BufferedImage newEmptyImage(int width, int height) {
		return new BufferedImage(width, height, workingImageType);
	}

	// A helper method that deep copies the current working image.
	public final BufferedImage duplicateWorkingImage() {
		BufferedImage output = newEmptyInputSizedImage();

		forEach((y, x) -> output.setRGB(x, y, workingImage.getRGB(x, y)));

		return output;
	}
	
	public BufferedImage greyscale() {
		logger.log("Preparing for grayscale...");

		int r = rgbWeights.redWeight;
		int g = rgbWeights.greenWeight;
		int b = rgbWeights.blueWeight;
		int weightsAmount = rgbWeights.weightsAmount;
		BufferedImage ans = newEmptyInputSizedImage();

		forEach((y, x) -> {
			Color c = new Color(workingImage.getRGB(x, y));
			int red = r * c.getRed();
			int green = g * c.getGreen();
			int blue = b * c.getBlue();
			int greyColor = (red + green + blue) / weightsAmount;
			Color color = new Color(greyColor, greyColor, greyColor);
			ans.setRGB(x, y, color.getRGB());
		});

		logger.log("Changing to grayscale done!");

		return ans;
	}

	public BufferedImage nearestNeighbor() {
		logger.log("Preparing to resize image using Nearest Neighbor method...");

		double widthScale = (double) inWidth / outWidth;
		double heightScale = (double) inHeight / outHeight;
		BufferedImage result = newEmptyOutputSizedImage();

		setForEachOutputParameters();
		forEach((y, x) -> {
			int widthLocation = (int)(x * widthScale);
			int heightLocation = (int)(y * heightScale);
			result.setRGB(x, y, workingImage.getRGB(widthLocation, heightLocation));
		});

		logger.log("Resizing done!");
		return result;
	}

}
