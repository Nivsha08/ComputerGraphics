package edu.cg.scene.objects;

import edu.cg.UnimplementedMethodException;
import edu.cg.algebra.*;

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
		double tSolution = -1;
		double A = ray.direction().lengthSqr();
		double B = 2 * ray.direction().dot(ray.source().sub(center));
		double C = ray.source().sub(center).lengthSqr() - (radius * radius);
		double delta = (B * B) - (4 * A * C);
		if (delta > 0) {
			double t1 = (-B - Math.sqrt(delta)) / (2 * A);
			double t2 = (-B + Math.sqrt(delta)) / (2 * A);
			if (t2 < Ops.epsilon) {
				return null;
			}
			else if (t1 > Ops.epsilon) {
				tSolution = t1;
			}
			else {
				tSolution = t2;
			}
		}
		return (tSolution > Ops.epsilon) ?
			new Hit(tSolution, ray.add(tSolution).sub(center).normalize()) : null;
	}
}
