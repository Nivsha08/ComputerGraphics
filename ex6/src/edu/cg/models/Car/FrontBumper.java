package edu.cg.models.Car;

import com.jogamp.opengl.GL2;

import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import edu.cg.models.Colors;
import edu.cg.models.IRenderable;
import edu.cg.models.SkewedBox;

public class FrontBumper implements IRenderable {
	private final int SPHERE_SLICES = 20;
	private SkewedBox base = new SkewedBox(Specification.F_BUMPER_LENGTH, Specification.F_BUMPER_HEIGHT_1,
			Specification.F_BUMPER_HEIGHT_2, Specification.F_BUMPER_DEPTH, Specification.F_BUMPER_DEPTH);
	private SkewedBox wing = new SkewedBox(Specification.F_BUMPER_LENGTH, Specification.F_BUMPER_WINGS_HEIGHT_1,
			Specification.F_BUMPER_WINGS_HEIGHT_2, Specification.F_BUMPER_WINGS_DEPTH, Specification.F_BUMPER_WINGS_DEPTH);

	@Override
	public void render(GL2 gl) {
		GLU glu = new GLU();
		GLUquadric q = glu.gluNewQuadric();
		gl.glPushMatrix();
		Materials.SetBlackMetalMaterial(gl);
		base.render(gl);
		gl.glTranslated(0.0, 0.0, 0.5 * Specification.F_BUMPER_DEPTH + 0.5 * Specification.F_BUMPER_WINGS_DEPTH);
		Materials.SetDarkGreyMetalMaterial(gl);
		wing.render(gl);
		gl.glTranslated(0.0, 0.0, -Specification.F_BUMPER_DEPTH - Specification.F_BUMPER_WINGS_DEPTH);
		wing.render(gl);
		gl.glPopMatrix();
		Materials.SetRedMetalMaterial(gl);
		gl.glTranslated(0.0, 0.4*Specification.F_BUMPER_WINGS_HEIGHT_1,
				0.5 * Specification.F_BUMPER_DEPTH + 0.5 * Specification.F_BUMPER_WINGS_DEPTH);
		glu.gluSphere(q, Specification.F_BUMPER_HEADLIGHT_RADIUS, SPHERE_SLICES, SPHERE_SLICES);
		gl.glTranslated(0.0, 0.0, -Specification.F_BUMPER_DEPTH + -Specification.F_BUMPER_WINGS_DEPTH);
		glu.gluSphere(q, Specification.F_BUMPER_HEADLIGHT_RADIUS, SPHERE_SLICES, SPHERE_SLICES);

	}

	@Override
	public void init(GL2 gl) {
	}

	@Override
	public void destroy(GL2 gl) {

	}

	@Override
	public String toString() {
		return "FrontBumper";
	}

}
