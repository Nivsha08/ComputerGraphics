package edu.cg.models;

import com.jogamp.opengl.GL2;

import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import edu.cg.algebra.Ops;
import edu.cg.algebra.Point;
import edu.cg.algebra.Vec;
import edu.cg.models.Car.Materials;

public class BoundingSphere implements IRenderable {
	private final int SPHERE_SLICES = 50;
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
		GLU glu = new GLU();
		GLUquadric q = glu.gluNewQuadric();
		gl.glPushMatrix();
		gl.glColor3d(this.color[0], this.color[1], this.color[2]);
		gl.glTranslated(this.center.x, this.center.y, this.center.z);
		glu.gluSphere(q, this.radius, SPHERE_SLICES, SPHERE_SLICES);
		gl.glPopMatrix();
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
