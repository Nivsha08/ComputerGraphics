package edu.cg.models.Car;

import com.jogamp.opengl.GL2;

import edu.cg.models.IRenderable;
import edu.cg.models.SkewedBox;

public class FrontBumper implements IRenderable {
	private SkewedBox base = new SkewedBox(Specification.F_BUMPER_LENGTH, Specification.F_BUMPER_HEIGHT_1,
			Specification.F_BUMPER_HEIGHT_2, Specification.F_BUMPER_DEPTH, Specification.F_BUMPER_DEPTH);
	private SkewedBox wing = new SkewedBox(Specification.F_BUMPER_LENGTH, Specification.F_BUMPER_WINGS_HEIGHT_1,
			Specification.F_BUMPER_WINGS_HEIGHT_2, Specification.F_BUMPER_WINGS_DEPTH, Specification.F_BUMPER_WINGS_DEPTH);

	@Override
	public void render(GL2 gl) {
		gl.glPushMatrix();
		Materials.SetBlackMetalMaterial(gl);
		base.render(gl);
		gl.glTranslated(0.0, 0.0, 0.5 * Specification.F_BUMPER_DEPTH + 0.5 * Specification.F_BUMPER_WINGS_DEPTH);
		wing.render(gl);
		gl.glTranslated(0.0, 0.0, -Specification.F_BUMPER_DEPTH - Specification.F_BUMPER_WINGS_DEPTH);
		wing.render(gl);
		gl.glPopMatrix();
	}

	@Override
	public void init(GL2 gl) {
	}

	@Override
	public String toString() {
		return "FrontBumper";
	}

}
