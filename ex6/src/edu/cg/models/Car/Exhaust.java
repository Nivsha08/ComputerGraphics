package edu.cg.models.Car;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import edu.cg.models.IRenderable;

public class Exhaust implements IRenderable {

    @Override
    public void render(GL2 gl) {
        GLU glu = new GLU();
        GLUquadric quad = glu.gluNewQuadric();
        Materials.SetMetalMaterial(gl, Specification.EXHAUST_COLOR);
        gl.glPushMatrix();
        gl.glRotated(-90, 0.0, 1.0, 0.0);
        glu.gluCylinder(quad, Specification.EXHAUST_RADIUS_1, Specification.EXHAUST_RADIUS_2,
                Specification.EXHAUST_LENGTH, 20, 2);
        gl.glRotated(180.0, 0, 1, 0);
        glu.gluDisk(quad, 0, Specification.EXHAUST_RADIUS_1, 20, 1);
        gl.glRotated(-180.0, 0, 1, 0);
        gl.glTranslated(0.0, 0.0, Specification.EXHAUST_LENGTH);
        glu.gluDisk(quad, 0, Specification.EXHAUST_RADIUS_2, 20, 1);
        Materials.SetBlackMetalMaterial(gl);
        gl.glTranslated(0.0, 0.0, 0.001);
        glu.gluDisk(quad, 0, 0.8 * Specification.EXHAUST_RADIUS_2, 20, 1);
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
