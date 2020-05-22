package edu.cg.scene.objects;

import edu.cg.UnimplementedMethodException;
import edu.cg.algebra.*;

import java.util.LinkedList;

public class Sphere extends Shape {
	private Point center;
	private double radius;
	
	public Sphere(Point center, double radius) {
		this.center = center;
		this.radius = radius;
	}
	
	public Sphere() {
		this(new Point(0, -0.5, -6), 0.5);
	}
	
	@Override
	public String toString() {
		String endl = System.lineSeparator();
		return "Sphere:" + endl + 
				"Center: " + center + endl +
				"Radius: " + radius + endl;
	}
	
	public Sphere initCenter(Point center) {
		this.center = center;
		return this;
	}
	
	public Sphere initRadius(double radius) {
		this.radius = radius;
		return this;
	}
	
	@Override
	public Hit intersect(Ray ray) {
		Hit solution = null;
		double A = ray.direction().lengthSqr();
		double B = 2 * ray.direction().dot(ray.source().sub(center));
		double C = ray.source().sub(center).lengthSqr() - (radius * radius);
		double delta = (B * B) - (4 * A * C);
		if (delta > 0) {
			double t1 = (-B - Math.sqrt(delta)) / (2 * A);
			double t2 = (-B + Math.sqrt(delta)) / (2 * A);
			solution = (isSingleSolution(t1, t2)) ?
					getSingleSolution(ray, t1) : getMinimalSolution(ray, t1, t2);
		}
		return solution;
	}

	private boolean isSingleSolution(double t1, double t2) {
		return (t1 == t2);
	}

	private Hit getSingleSolution(Ray ray, double t) {
		if (t <= Ops.epsilon) {
			// the solution is not positive
			return null;
		}
		else {
			Hit hit = new Hit(t, ray.add(t).sub(center).normalize()).setWithin();
			return hit;
		}
	}

	private Hit getMinimalSolution(Ray ray, double t1, double t2) {
		Hit hit = null;
		if (t2 > Ops.epsilon) {
			if (t1 <= Ops.epsilon) {
				// only t2 is positive
				hit = new Hit(t2, ray.add(t2).sub(center).normalize()).setWithin();
			}
			else {
				// both solutions is positive
				hit = new Hit(t1, ray.add(t1).sub(center).normalize());
			}
		}
		return hit;
	}
}
