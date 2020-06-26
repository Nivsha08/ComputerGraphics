package edu.cg.models.Car;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import edu.cg.models.IRenderable;
import edu.cg.models.SkewedBox;

public class BackLight implements IRenderable {

    private SkewedBox backLightPanel = new SkewedBox(Specification.BACK_LIGHT_PANEL_LENGTH,
            Specification.BACK_LIGHT_PANEL_HEIGHT, Specification.BACK_LIGHT_PANEL_HEIGHT,
            Specification.BACK_LIGHT_PANEL_DEPTH, Specification.BACK_LIGHT_PANEL_DEPTH);

    @Override
    public void render(GL2 gl) {
        GLU glu = new GLU();
        GLUquadric quad = glu.gluNewQuadric();
        gl.glPushMatrix();
        Materials.SetBlackMetalMaterial(gl);
        backLightPanel.render(gl);
        Materials.SetMetalMaterial(gl, Specification.BACK_LIGHT_COLOR);
        gl.glTranslated(-Specification.BACK_LIGHT_PANEL_LENGTH / 2.0 - 0.001,
                Specification.BACK_LIGHT_PANEL_HEIGHT - 2*Specification.BACK_LIGHT_RADIUS,
                -Specification.BACK_LIGHT_PANEL_DEPTH / 2.0 + 2*Specification.BACK_LIGHT_RADIUS);
        gl.glRotated(-90, 0.0, 1.0, 0.0);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                glu.gluDisk(quad, 0.0, Specification.BACK_LIGHT_RADIUS, 20, 1);
                gl.glTranslated(2 * Specification.BACK_LIGHT_RADIUS + 0.0015, 0.0, 0.0);
            }
            gl.glTranslated(-4*(2*Specification.BACK_LIGHT_RADIUS+0.0015),
                    -2*Specification.BACK_LIGHT_RADIUS - 0.0015, 0.0);
        }
        gl.glPopMatrix();
    }

    @Override
    public void init(GL2 gl) {
    }

    @Override
    public void destroy(GL2 gl) {

    }
}
