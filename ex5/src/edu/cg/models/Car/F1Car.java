package edu.cg.models.Car;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.jogamp.opengl.*;

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
		Point backRelativeCenter = new Point(-Specification.C_LENGTH / 2.0 -Specification.B_LENGTH / 2.0, Specification.B_HEIGHT / 2.0, 0.0);
		Point centerRelativeCenter = new Point(0.0, Specification.C_HEIGHT / 2.0, 0.0);
		Point frontRelativeCenter = new Point(Specification.C_LENGTH / 2.0 + Specification.F_LENGTH / 2.0, Specification.F_HEIGHT / 2.0, 0.0);
		LinkedList<BoundingSphere> res = new LinkedList<BoundingSphere>();
		BoundingSphere centerSphere = carCenter.getBoundingSpheres().get(0);
		centerSphere.setCenter(centerRelativeCenter);
		BoundingSphere backSphere = carBack.getBoundingSpheres().get(0);
		backSphere.setCenter(backRelativeCenter);
		BoundingSphere frontSphere = carFront.getBoundingSpheres().get(0);
		frontSphere.setCenter(frontRelativeCenter);
		Point carSphereCenter = this.getBoundingSphereCenter();
		double carSphereRadius = this.getBoundingSphereRadius();
		BoundingSphere carSphere = new BoundingSphere(carSphereRadius, carSphereCenter);
		res.addAll(Arrays.asList(carSphere, frontSphere, centerSphere, backSphere));
		return res;
	}

	private Point getBoundingSphereCenter() {
		return new Point(0.0, (Specification.B_HEIGHT + Specification.C_HEIGHT + Specification.F_HEIGHT) / 6.0, 0.0);
	}

	private double getBoundingSphereRadius() {
		return 0.8;
	}
}
