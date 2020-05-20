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
		this.initResolution(200, 200, 90);
		this.initImageSpace(cameraPosition, towardsVec, upVec, distanceToPlain);
		this.initImagePlain(distanceToPlain);
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
	}
	private void initImageSpace(Point cameraPosition, Vec towardsVec, Vec upVec, double distanceToPlain) {
		this.cameraPosition = cameraPosition;
		this.Vtowards = towardsVec;
		this.Vright = Ops.normalize(Ops.cross(upVec, towardsVec));
		this.Vup = Ops.normalize(Ops.cross(this.Vright, this.Vtowards));
	}

	private void initImagePlain(double distanceToPlain) {
		this.plainCenter = Ops.add(cameraPosition, Ops.mult(distanceToPlain, this.Vtowards));
		this.imageWidth = distanceToPlain * Math.tan(this.viewAngle / 2) * 2;
		this.imageHeight = this.pixelWidth * this.Ry;
		this.pixelWidth = this.imageWidth / this.Rx;
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
		Vec deltaX = Ops.mult(x - Math.floor(this.Rx / 2), this.Vright);
		Vec deltaY = Ops.mult(y - Math.floor(this.Ry / 2), Ops.neg(this.Vup));
		return Ops.add(Ops.add(this.plainCenter, deltaX), deltaY);
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
