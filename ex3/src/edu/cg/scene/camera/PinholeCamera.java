package edu.cg.scene.camera;

import edu.cg.algebra.Ops;
import edu.cg.algebra.Point;
import edu.cg.algebra.Vec;

public class PinholeCamera {

	private Point cameraPosition;
	private Point plainCenter;
	private Vec Vup;
	private Vec Vright;
	private Vec Vtowards;
	private int Rx;
	private int Ry;
	private double distanceToPlain;
	private double imageWidth;
	private double imageHeight;
	private double pixelWidth;
	private double viewAngle;

	/**
	 * Initializes a pinhole camera model with default resolution 200X200 (RxXRy)
	 * and View Angle 90.
	 * 
	 * @param cameraPosition  - The position of the camera.
	 * @param towardsVec      - The towards vector of the camera (not necessarily
	 *                        normalized).
	 * @param upVec           - The up vector of the camera.
	 * @param distanceToPlain - The distance of the camera (position) to the center
	 *                        point of the image-plain.
	 * 
	 */
	public PinholeCamera(Point cameraPosition, Vec towardsVec, Vec upVec, double distanceToPlain) {
		this.distanceToPlain = distanceToPlain;
		this.initImageSpace(cameraPosition, towardsVec, upVec);
		this.initResolution(200, 200, 90);
	}

	/**
	 * Initializes the resolution and width of the image.
	 * 
	 * @param height    - the number of pixels in the y direction.
	 * @param width     - the number of pixels in the x direction.
	 * @param viewAngle - the view Angle.
	 */
	public void initResolution(int height, int width, double viewAngle) {
		this.Rx = width;
		this.Ry = height;
		this.viewAngle = viewAngle;
		this.initImagePlain();
	}

	private void initImageSpace(Point cameraPosition, Vec towardsVec, Vec upVec) {
		this.cameraPosition = cameraPosition;
		this.Vtowards = towardsVec.normalize();
		this.Vright = Vtowards.cross(upVec).normalize();
		this.Vup =  Vright.cross(Vtowards).normalize();
	}

	private void initImagePlain() {
		this.plainCenter = cameraPosition.add(Vtowards.mult(distanceToPlain));
		this.imageWidth = distanceToPlain * Math.tan(Math.toRadians(viewAngle / 2)) * 2;
		this.pixelWidth = imageWidth / Rx;
		this.imageHeight = pixelWidth * Ry;
	}

	/**
	 * Transforms from pixel coordinates to the center point of the corresponding
	 * pixel in model coordinates.
	 * 
	 * @param x - the pixel index in the x direction.
	 * @param y - the pixel index in the y direction.
	 * @return the middle point of the pixel (x,y) in the model coordinates.
	 */
	public Point transform(int x, int y) {
		Vec deltaX = Vright.mult(x - Math.floor(Rx / 2)).mult(pixelWidth);
		Vec deltaY = Vup.neg().mult(y - Math.floor(Ry / 2)).mult(pixelWidth);
		return plainCenter.add(deltaX).add(deltaY);
	}

	public Point transform(int x, int y, double rightOffset, double upOffset) {
		Vec deltaX = Vright.mult(x - Math.floor(Rx / 2) + rightOffset).mult(pixelWidth);
		Vec deltaY = Vup.neg().mult(y - Math.floor(Ry / 2) + upOffset).mult(pixelWidth);
		return plainCenter.add(deltaX).add(deltaY);
	}

	/**
	 * Returns the camera position
	 * 
	 * @return a new point representing the camera position.
	 */
	public Point getCameraPosition() {
		return this.cameraPosition;
	}
}
