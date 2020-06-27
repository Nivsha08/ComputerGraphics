package edu.cg.models.Car;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import edu.cg.models.Colors;
import edu.cg.models.IRenderable;
import edu.cg.models.SkewedBox;

public class EngineBox implements IRenderable {

    private SkewedBox engineBox = new SkewedBox(Specification.ENGINE_BOX_LENGTH, Specification.ENGINE_BOX_HEIGHT,
            1.5*Specification.ENGINE_BOX_HEIGHT, Specification.ENGINE_BOX_DEPTH_1, Specification.ENGINE_BOX_DEPTH_2);

    @Override
    public void render(GL2 gl) {
        GLU glu = new GLU();
        GLUquadric quad = glu.gluNewQuadric();
        gl.glPushMatrix();
        Materials.SetBlackMetalMaterial(gl);
        engineBox.render(gl);
        Materials.SetDarkRedMetalMaterial(gl);
        gl.glTranslated(-2 * Specification.ENGINE_RODS_DISTANCE,
                0.5 * Specification.ENGINE_BOX_HEIGHT + Specification.ENGINE_ROD_RADIUS,
                -Specification.ENGINE_ROD_DEPTH / 2.0);
        for (double i = 1; i < 2.8; i += 0.4) {
            double depth = i * Specification.ENGINE_ROD_DEPTH;
            glu.gluCylinder(quad, Specification.ENGINE_ROD_RADIUS, Specification.ENGINE_ROD_RADIUS, depth, 100, 2);
            gl.glRotated(180.0, 0, 1, 0);
            glu.gluDisk(quad, 0, Specification.ENGINE_ROD_RADIUS, 20, 1);
            gl.glRotated(-180.0, 0, 1, 0);
            gl.glTranslated(0.0, 0.0, depth);
            glu.gluDisk(quad, 0, Specification.ENGINE_ROD_RADIUS, 20, 1);
            gl.glTranslated(Specification.ENGINE_RODS_DISTANCE, Specification.ENGINE_ROD_RADIUS / 3*i, -(i + 0.2)*Specification.ENGINE_ROD_DEPTH);
        }
        gl.glPopMatrix();
        glu.gluDeleteQuadric(quad);
    }

    @Override
    public void init(GL2 gl) {
    }

    @Override
    public void destroy(GL2 gl) {

    }
}
