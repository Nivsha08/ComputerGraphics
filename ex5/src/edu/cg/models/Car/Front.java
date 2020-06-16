package edu.cg.models.Car;

import java.util.LinkedList;
import java.util.List;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import edu.cg.algebra.Ops;
import edu.cg.algebra.Point;
import edu.cg.models.BoundingSphere;
import edu.cg.models.IIntersectable;
import edu.cg.models.IRenderable;

public class Front implements IRenderable, IIntersectable {
	private FrontHood hood = new FrontHood();
	private PairOfWheels wheels = new PairOfWheels();
	private FrontBumper bumper = new FrontBumper();

	@Override
	public void render(GL2 gl) {
		gl.glPushMatrix();
		// Render hood - Use Red Material.
		gl.glTranslated(-Specification.F_LENGTH / 2.0 + Specification.F_HOOD_LENGTH / 2.0, 0.0, 0.0);
		hood.render(gl);
		// Render the wheels.
		gl.glTranslated(Specification.F_HOOD_LENGTH / 2.0 - 1.25 * Specification.TIRE_RADIUS,
				0.5 * Specification.TIRE_RADIUS, 0.0);
		wheels.render(gl);
		// Render the bumper
		gl.glTranslated(Specification.F_BUMPER_LENGTH + 0.25 * Specification.TIRE_RADIUS,
				-0.5 * Specification.F_HEIGHT + Specification.F_BUMPER_HEIGHT_1, 0.0);
		bumper.render(gl);
		gl.glPopMatrix();
	}

	@Override
	public void init(GL2 gl) {
	}

	@Override
	public List<BoundingSphere> getBoundingSpheres() {
		LinkedList<BoundingSphere> res = new LinkedList<>();
		res.add(this.createBoundingSphere());
		return res;
	}

	@Override
	public String toString() {
		return "CarFront";
	}

	private BoundingSphere createBoundingSphere() {
		Point center = this.getBoundingSphereCenter();
		double radius = this.getBoundingSphereRadius();
		BoundingSphere frontSphere = new BoundingSphere(radius, center);
		frontSphere.setSphereColor3d(1.0, 0.0, 0.0);
		return frontSphere;
	}

	private Point getBoundingSphereCenter() {
		return new Point(0.0, Specification.F_HEIGHT / 2.0, 0.0);
	}

	private double getBoundingSphereRadius() {
		// back edge of FrontHood
		Point edge = new Point(-Specification.F_HOOD_LENGTH_1 - 0.5 * Specification.TIRE_RADIUS,
				Specification.F_HOOD_HEIGHT_1,
				Specification.F_HOOD_DEPTH_1 / 2.0);
		return Ops.dist(edge, this.getBoundingSphereCenter());
	}

}
