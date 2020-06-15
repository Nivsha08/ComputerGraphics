package edu.cg.models.Car;

import java.util.LinkedList;
import java.util.List;

import com.jogamp.opengl.GL2;
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
		// TODO: Return a list of bounding spheres the list structure is as follow:
		// s1
		// where:
		// s1 - sphere bounding the car front
		LinkedList<BoundingSphere> res = new LinkedList<BoundingSphere>();

		return res;
	}

	@Override
	public String toString() {
		return "CarFront";
	}
}
