package edu.cg.models.Car;

import com.jogamp.opengl.GL2;

import edu.cg.models.Colors;
import edu.cg.models.IRenderable;
import edu.cg.models.SkewedBox;

public class FrontHood implements IRenderable {
	private SkewedBox hoodBox1 = new SkewedBox(Specification.F_HOOD_LENGTH_1, Specification.F_HOOD_HEIGHT_1,
			Specification.F_HOOD_HEIGHT_2, Specification.F_HOOD_DEPTH_1, Specification.F_HOOD_DEPTH_2);
	private SkewedBox narrowStrip1 = new SkewedBox(1.02*Specification.F_HOOD_LENGTH_1, Specification.STRIP_HEIGHT,
			Specification.STRIP_HEIGHT, Specification.NARROW_STRIP_DEPTH, Specification.NARROW_STRIP_DEPTH);
	private SkewedBox wideStrip1 = new SkewedBox(1.02*Specification.F_HOOD_LENGTH_1, Specification.STRIP_HEIGHT,
			Specification.STRIP_HEIGHT, Specification.WIDE_STRIP_DEPTH, Specification.WIDE_STRIP_DEPTH);
	private SkewedBox hoodBox2 = new SkewedBox(Specification.F_HOOD_LENGTH_2, Specification.F_HOOD_HEIGHT_2,
			Specification.F_BUMPER_HEIGHT_1, Specification.F_HOOD_DEPTH_2, Specification.F_HOOD_DEPTH_3);
	private SkewedBox narrowStrip2 = new SkewedBox(1.09*Specification.F_HOOD_LENGTH_2, Specification.STRIP_HEIGHT,
			Specification.STRIP_HEIGHT, Specification.NARROW_STRIP_DEPTH, Specification.NARROW_STRIP_DEPTH);
	private SkewedBox wideStrip2 = new SkewedBox(1.09*Specification.F_HOOD_LENGTH_2, Specification.STRIP_HEIGHT,
			Specification.STRIP_HEIGHT, Specification.WIDE_STRIP_DEPTH, Specification.WIDE_STRIP_DEPTH);

	@Override
	public void render(GL2 gl) {
		gl.glPushMatrix();
		double hoodLength = Specification.F_HOOD_LENGTH_1 + Specification.F_HOOD_LENGTH_2;
		// Render hood - Use Red Material.
		Materials.SetRedMetalMaterial(gl);
		gl.glTranslated(-hoodLength / 2.0 + Specification.F_HOOD_LENGTH_1 / 2.0, 0.0, 0.0);
		hoodBox1.render(gl);
		gl.glTranslated(0.0, 0.9*Specification.F_HOOD_HEIGHT_1, 0.0);
		gl.glRotated(-4.3, 0.0,0.0, 1.0);
		Materials.SetDarkRedMetalMaterial(gl);
		wideStrip1.render(gl);
		gl.glTranslated(0.0, Specification.STRIP_HEIGHT / 2.0, 0.0);
		Materials.SetDarkRedMetalMaterial(gl);
		narrowStrip1.render(gl);
		gl.glTranslated(0.0, -Specification.STRIP_HEIGHT / 2.0, 0.0);
		gl.glRotated(4.3, 0.0,0.0, 1.0);
		gl.glTranslated(0.0, -0.9*Specification.F_HOOD_HEIGHT_1, 0.0);
		Materials.SetRedMetalMaterial(gl);
		gl.glTranslated(Specification.F_HOOD_LENGTH_1 / 2.0 + Specification.F_HOOD_LENGTH_2 / 2.0, 0.0, 0.0);
		hoodBox2.render(gl);
		gl.glTranslated(0.0 , Specification.F_HOOD_HEIGHT_2 / 2.0 + 2*Specification.STRIP_HEIGHT, 0.0);
		gl.glRotated(-24.0 , 0.0, 0.0,1.0);
		Materials.SetDarkRedMetalMaterial(gl);
		wideStrip2.render(gl);
		gl.glTranslated(0.0 , Specification.STRIP_HEIGHT / 2.0, 0.0);
		Materials.SetDarkRedMetalMaterial(gl);
		narrowStrip2.render(gl);
		gl.glPopMatrix();
	}

	@Override
	public void init(GL2 gl) {
	}

	@Override
	public void destroy(GL2 gl) {

	}

	@Override
	public String toString() {
		return "FrontHood";
	}

}
