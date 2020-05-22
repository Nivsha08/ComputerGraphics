package edu.cg.scene.objects;

import edu.cg.UnimplementedMethodException;
import edu.cg.algebra.*;

public class Dome extends Shape {
	private Plain plain;
	private Sphere sphere;
	private Point sphereCenter;
	private double sphereRadius;

	public Dome() {
		sphereCenter = new Point(0, -0.5, -6);
		sphere = new Sphere().initCenter(sphereCenter);
		plain = new Plain(new Vec(-1, 0, -1), sphereCenter);
	}

	public Dome(Point center, double radius, Vec plainDirection) {
		sphereCenter = center;
		sphereRadius = radius;
		sphere = new Sphere(center, radius);
		plain = new Plain(plainDirection, center);
	}

	@Override
	public String toString() {
		String endl = System.lineSeparator();
		return "Dome:" + endl + sphere + plain + endl;
	}

	@Override
	public Hit intersect(Ray ray) {
		Hit solution = null;
		Hit sphereHit = sphere.intersect(ray);
		if (sphereHit != null) {
			Hit plainHit = plain.intersect(ray);
			solution = (singleSolution(ray, plainHit, sphereHit)) ?
					getSingleSolution(ray, sphereHit) : getMinimalSolution(ray, plainHit, sphereHit);
		}
		return solution;
	}

	private boolean singleSolution(Ray ray, Hit planeHit, Hit sphereHit) {
		return (planeHit == null) ||
			(!isPlainIntersectionInsideDome(ray.getHittingPoint(planeHit))) ||
			(planeHit.t() == sphereHit.t());
	}

	private Hit getSingleSolution(Ray ray, Hit sphereHit) {
		Point intersectionPoint = ray.getHittingPoint(sphereHit);
		return (intersectionAbovePlain(intersectionPoint)) ?
			new Hit(sphereHit.t(), ray.add(sphereHit.t()).sub(sphereCenter).normalize()) : null;
	}

	private Hit getMinimalSolution(Ray ray, Hit planeHit, Hit sphereHit) {
		return (sphereHit.t() < planeHit.t()) ?
			new Hit(sphereHit.t(), ray.add(sphereHit.t()).sub(sphereCenter).normalize())
			: new Hit(planeHit.t(), plain.normal().neg());
	}

	private boolean intersectionAbovePlain(Point intersectionPoint) {
		return (plain.substitute(intersectionPoint) >= 0);
	}

	private boolean isPlainIntersectionInsideDome(Point plainIntersection) {
		double distanceFromSphereCenter = Ops.dist(plainIntersection, sphereCenter);
		return (distanceFromSphereCenter <= sphereRadius);
	}
}
