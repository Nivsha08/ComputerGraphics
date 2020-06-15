package edu.cg.models;

import com.jogamp.opengl.GL2;

import edu.cg.algebra.Ops;
import edu.cg.algebra.Point;
import edu.cg.algebra.Vec;

public class BoundingSphere implements IRenderable {
	private double radius = 0.0;
	private Point center;
	private double color[];

	public BoundingSphere(double radius, Point center) {
		color = new double[3];
		this.setRadius(radius);
		this.setCenter(new Point(center.x, center.y, center.z));
	}

	public void setSphereColor3d(double r, double g, double b) {
		this.color[0] = r;
		this.color[1] = g;
		this.color[2] = b;
	}

	/**
	 * Given a sphere s - check if this sphere and the given sphere intersect.
	 * 
	 * @return true if the spheres intersects, and false otherwise
	 */
	public boolean checkIntersection(BoundingSphere s) {
		double distance = Ops.dist(this.center, s.getCenter());
		return (distance >= this.radius + s.getRadius());
	}

	public void translateCenter(double dx, double dy, double dz) {
		this.center = this.center.add(new Vec(dx, dy, dz));
	}

	@Override
	public void render(GL2 gl) {
		// TODO: Render a sphere with the given radius and center.
		// NOTE : Use the specified color when rendering.
	}

	@Override
	public void init(GL2 gl) {
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

}
