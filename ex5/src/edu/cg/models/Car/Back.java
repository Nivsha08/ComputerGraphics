package edu.cg.models.Car;

import java.awt.*;
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
import edu.cg.models.SkewedBox;

public class Back implements IRenderable, IIntersectable {
	private SkewedBox baseBox = new SkewedBox(Specification.B_BASE_LENGTH, Specification.B_BASE_HEIGHT,
			Specification.B_BASE_HEIGHT, Specification.B_BASE_DEPTH, Specification.B_BASE_DEPTH);
	private SkewedBox backBox = new SkewedBox(Specification.B_LENGTH, Specification.B_HEIGHT_1,
			Specification.B_HEIGHT_2, Specification.B_DEPTH_1, Specification.B_DEPTH_2);
	private PairOfWheels wheels = new PairOfWheels();
	private Spolier spoiler = new Spolier();
	private EngineTop engineTop = new EngineTop();

	@Override
	public void render(GL2 gl) {
		gl.glPushMatrix();
		Materials.SetBlackMetalMaterial(gl);
		gl.glTranslated(Specification.B_LENGTH / 2.0 - Specification.B_BASE_LENGTH / 2.0, 0.0, 0.0);
		baseBox.render(gl);
		Materials.SetMetalMaterial(gl, Specification.CAR_MAIN_COLOR);
		gl.glTranslated(-1.0 * (Specification.B_LENGTH / 2.0 - Specification.B_BASE_LENGTH / 2.0),
				Specification.B_BASE_HEIGHT, 0.0);
		backBox.render(gl);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslated(-Specification.B_LENGTH / 2.0 + Specification.TIRE_RADIUS, 0.5 * Specification.TIRE_RADIUS,
				0.0);
		wheels.render(gl);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslated(-Specification.B_LENGTH / 2.0 + 0.5 * Specification.S_LENGTH,
				0.5 * (Specification.B_HEIGHT_1 + Specification.B_HEIGHT_2), 0.0);
		spoiler.render(gl);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslated(0.05, Specification.B_BASE_HEIGHT + Specification.B_HEIGHT_1 +
				Specification.ENGINE_BOX_HEIGHT / 2.0, 0.0);
		gl.glRotated(4, 0, 0, 1);
		engineTop.render(gl);
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

	private BoundingSphere createBoundingSphere() {
		Point center = this.getBoundingSphereCenter();
		double radius = this.getBoundingSphereRadius();
		BoundingSphere backSphere = new BoundingSphere(radius, center);
		backSphere.setSphereColor3d(0.0, 0.0, 1.0);
		return backSphere;
	}

	private Point getBoundingSphereCenter() {
		return new Point(0.0, Specification.B_HEIGHT / 2.0, 0.0);
	}

	private double getBoundingSphereRadius() {
		// top point of the spoiler wing
		Point edge = new Point(-Specification.B_LENGTH / 2.0,
				Specification.B_HEIGHT,
				Specification.S_BASE_DEPTH / 2.0 + Specification.S_WINGS_DEPTH);
		return Ops.dist(edge, this.getBoundingSphereCenter());
	}

}
