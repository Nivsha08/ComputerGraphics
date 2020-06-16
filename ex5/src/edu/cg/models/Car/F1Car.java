package edu.cg.models.Car;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.jogamp.opengl.*;

import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import edu.cg.algebra.Ops;
import edu.cg.algebra.Point;
import edu.cg.models.BoundingSphere;
import edu.cg.models.IIntersectable;
import edu.cg.models.IRenderable;

/**
 * A F1 Racing Car.
 *
 */
public class F1Car implements IRenderable, IIntersectable {
	// TODO : Add new design features to the car.
	// Remember to include a ReadMe file specifying what you implemented.
	Center carCenter = new Center();
	Back carBack = new Back();
	Front carFront = new Front();

	@Override
	public void render(GL2 gl) {
		carCenter.render(gl);
		gl.glPushMatrix();
		gl.glTranslated(-Specification.B_LENGTH / 2.0 - Specification.C_BASE_LENGTH / 2.0, 0.0, 0.0);
		carBack.render(gl);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslated(Specification.F_LENGTH / 2.0 + Specification.C_BASE_LENGTH / 2.0, 0.0, 0.0);
		carFront.render(gl);
		gl.glTranslated(-Specification.F_LENGTH / 2.0 -Specification.C_BASE_LENGTH / 2.0, 0.0, 0.0);
		gl.glPopMatrix();
	}

	@Override
	public String toString() {
		return "F1Car";
	}

	@Override
	public void init(GL2 gl) {

	}

	@Override
	public List<BoundingSphere> getBoundingSpheres() {
		LinkedList<BoundingSphere> res = new LinkedList<>();
		Point backRelativeCenter = new Point(-Specification.C_LENGTH / 2.0 -Specification.B_LENGTH / 2.0, Specification.B_HEIGHT / 2.0, 0.0);
		Point centerRelativeCenter = new Point(0.0, Specification.C_HEIGHT / 2.0, 0.0);
		Point frontRelativeCenter = new Point(Specification.C_LENGTH / 2.0 + Specification.F_LENGTH / 2.0, Specification.F_HEIGHT / 2.0, 0.0);
		BoundingSphere centerSphere = carCenter.getBoundingSpheres().get(0);
		centerSphere.setCenter(centerRelativeCenter);
		BoundingSphere backSphere = carBack.getBoundingSpheres().get(0);
		backSphere.setCenter(backRelativeCenter);
		BoundingSphere frontSphere = carFront.getBoundingSpheres().get(0);
		frontSphere.setCenter(frontRelativeCenter);
		res.addAll(Arrays.asList(this.createBoundingSphere(), frontSphere, centerSphere, backSphere));
		return res;
	}

	private BoundingSphere createBoundingSphere() {
		Point center = this.getBoundingSphereCenter();
		double radius = this.getBoundingSphereRadius();
		BoundingSphere carSphere = new BoundingSphere(radius, center);
		return carSphere;
	}

	private Point getBoundingSphereCenter() {
		return new Point(0.0, (Specification.B_HEIGHT + Specification.C_HEIGHT + Specification.F_HEIGHT) / 6.0, 0.0);
	}

	private double getBoundingSphereRadius() {
		Point edge = new Point(Specification.C_LENGTH / 2.0 + Specification.F_LENGTH,
				0,
				Specification.F_BUMPER_DEPTH / 2.0 + Specification.F_BUMPER_WINGS_DEPTH);
		return Ops.dist(edge, this.getBoundingSphereCenter());
	}
}
