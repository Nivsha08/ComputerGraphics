package edu.cg.models.Car;

import java.util.LinkedList;
import java.util.List;

import com.jogamp.opengl.GL2;
import edu.cg.algebra.Ops;
import edu.cg.algebra.Point;
import edu.cg.models.*;

public class Back implements IRenderable, IIntersectable {
	private SkewedBox baseBox = new SkewedBox(Specification.B_BASE_LENGTH, Specification.B_BASE_HEIGHT,
			Specification.B_BASE_HEIGHT, Specification.B_BASE_DEPTH, Specification.B_BASE_DEPTH);
	private SkewedBox backBox = new SkewedBox(Specification.B_LENGTH, Specification.B_HEIGHT_1,
			Specification.B_HEIGHT_2, Specification.B_DEPTH_1, Specification.B_DEPTH_2);
	private PairOfWheels wheels = new PairOfWheels();
	private Spolier spoiler = new Spolier();
	private EngineBox engineBox = new EngineBox();
	private Exhaust exhaust = new Exhaust();
	private BackLight backlight = new BackLight();
	private SkewedBox narrowStrip = new SkewedBox(Specification.B_LENGTH, Specification.STRIP_HEIGHT,
			Specification.STRIP_HEIGHT, Specification.NARROW_STRIP_DEPTH, Specification.NARROW_STRIP_DEPTH);
	private SkewedBox wideStrip = new SkewedBox(Specification.B_LENGTH, Specification.STRIP_HEIGHT,
			Specification.STRIP_HEIGHT, Specification.WIDE_STRIP_DEPTH, Specification.WIDE_STRIP_DEPTH);

	@Override
	public void render(GL2 gl) {
		gl.glPushMatrix();
		Materials.SetBlackMetalMaterial(gl);
		gl.glTranslated(Specification.B_LENGTH / 2.0 - Specification.B_BASE_LENGTH / 2.0, 0.0, 0.0);
		baseBox.render(gl);
		Materials.SetMetalMaterial(gl, Colors.CAR_MAIN_COLOR);
		gl.glTranslated(-1.0 * (Specification.B_LENGTH / 2.0 - Specification.B_BASE_LENGTH / 2.0),
				Specification.B_BASE_HEIGHT, 0.0);
		backBox.render(gl);
		Materials.SetMetalMaterial(gl, Colors.WHITE_COLOR);
		gl.glTranslated(0.0, Specification.B_HEIGHT_1 + Specification.STRIP_HEIGHT, 0.0);
		gl.glRotated(2.0, 0.0, 0.0, 1.0);
		wideStrip.render(gl);
		gl.glTranslated(0.0, Specification.STRIP_HEIGHT / 2.0, 0.0);
		Materials.SetMetalMaterial(gl, Colors.CAR_ACCENT_COLOR);
		narrowStrip.render(gl);
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
				Specification.ENGINE_BOX_HEIGHT / 2.0 + 0.01, 0.0);
		gl.glRotated(4, 0, 0, 1);
		engineBox.render(gl);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslated(-Specification.B_LENGTH / 2.0,
				Specification.B_BASE_HEIGHT + Specification.B_HEIGHT_1 / 2.0 - 0.02,
				-1.5*Specification.EXHAUST_RADIUS_1);
		exhaust.render(gl);
		gl.glTranslated(0.0, 0.0, 3*Specification.EXHAUST_RADIUS_1);
		exhaust.render(gl);
		gl.glTranslated(0.0, 1.5*Specification.EXHAUST_RADIUS_1, -1.5*Specification.EXHAUST_RADIUS_1);
		backlight.render(gl);
		gl.glPopMatrix();
	}

	@Override
	public void init(GL2 gl) {

	}

	@Override
	public void destroy(GL2 gl) {

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
