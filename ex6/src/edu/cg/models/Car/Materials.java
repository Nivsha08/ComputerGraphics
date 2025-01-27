package edu.cg.models.Car;

		import com.jogamp.opengl.GL2;

public class Materials {

	private static final float dark_gray[] = { 0.2f, 0.2f, 0.2f };
	private static final float dark_red[] = { 0.25f, 0.01f, 0.01f };
	private static final float red[] = { 0.5f, 0f, 0f };
	private static final float black[] = { 0.05f, 0.05f, 0.05f };

	public static void SetMetalMaterial(GL2 gl, float[] color) {
		float specular[] = { 0.8f, 0.8f, 0.8f };
		float diffColor[] = new float[3];
		float minIntensity = 1.0f;
		float maxIntensity = 0.0f;
		for (int i = 0; i < 3; i++) {
			minIntensity = Math.min(color[i], minIntensity);
			maxIntensity = Math.min(color[i], maxIntensity);
		}
		for (int i = 0; i < 3; i++) {
			diffColor[i] =color[i] + 0.5f*(maxIntensity - minIntensity);
		}

		gl.glColor3fv(color, 0);
		gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 10);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, diffColor, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, specular, 0);
	}

	public static void SetBlackMetalMaterial(GL2 gl) {
		float[] mat_ambient = { 0.0f, 0.0f, 0.0f, 1.0f };
		float[] mat_diffuse = { 0.01f, 0.01f, 0.01f, 1.0f };
		float[] mat_specular = { 0.50f, 0.50f, 0.50f, 1.0f };
		float shine = 32.0f;
		gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, shine);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, mat_ambient, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, mat_diffuse, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, mat_specular, 0);
	}

	public static void SetRedMetalMaterial(GL2 gl) {
		// Ruby
		float[] mat_ambient = { 0.1745f, 0.01175f, 0.01175f, 1f };
		float[] mat_diffuse = { 0.61424f, 0.04136f, 0.04136f, 1f };
		float[] mat_specular = { 0.727811f, 0.626959f, 0.626959f, 1f };
		float shine = 76.8f;
		gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, shine);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, mat_ambient, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, mat_diffuse, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, mat_specular, 0);

	}

	public static void SetDarkRedMetalMaterial(GL2 gl) {
		float[] mat_ambient = { 0.0f, 0.0f, 0.0f, 1.0f };
		float[] mat_diffuse = { 0.4f, 0.0f, 0.0f, 1.0f };
		float[] mat_specular = { 0.4f, 0.3f, 0.3f, 1.0f };
		float shine = 32.0f;
		gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, shine);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, mat_ambient, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, mat_diffuse, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, mat_specular, 0);
	}

	public static void SetDarkGreyMetalMaterial(GL2 gl) {
		float[] mat_ambient = { 0.25f, 0.25f, 0.25f, 1.0f };
		float[] mat_diffuse = { 0.4f, 0.4f, 0.4f, 1.0f };
		float[] mat_specular = { 0.774597f, 0.774597f, 0.774597f, 1.0f };
		float shine = 76.8f;
		gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, shine);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, mat_ambient, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, mat_diffuse, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, mat_specular, 0);
	}

	public static void setMaterialTire(GL2 gl) {
		float col[] = { .05f, .05f, .05f };
		gl.glColor3fv(col, 0);
		gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 100);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, col, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, col, 0);
	}

	public static void setMaterialRims(GL2 gl) {
		float col[] = { .8f, .8f, .8f };
		gl.glColor3fv(dark_gray, 0);
		gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 20);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, dark_gray, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, col, 0);
	}

	public static void setGreenMaterial(GL2 gl) {
		float[] mat_ambient = { 0.0215f, 0.1745f, 0.0215f, 1f };
		float[] mat_diffuse = { 0.07568f, 0.61424f, 0.07568f, 1f };
		float[] mat_specular = { 0.633f, 0.727811f, 0.633f, 1f };
		float shine = 128f;
		gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, shine);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, mat_ambient, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, mat_diffuse, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, mat_specular, 0);
	}

	public static void setAsphaltMaterial(GL2 gl) {
		float[] mat_ambient = { 0.15375f, 0.15f, 0.16625f, 1f };
		float[] mat_diffuse = { 0.68275f, 0.67f, 0.72525f, 1f };
		float[] mat_specular = { 0.332741f, 0.328634f, 0.346435f, 1f };
		float shine = 38.4f;
		gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, shine);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, mat_ambient, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, mat_diffuse, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, mat_specular, 0);
	}

	public static void setWoodenBoxMaterial(GL2 gl) {
		float[] mat_ambient = { 0.4f, 0.4f, 0.4f, 1f };
		float[] mat_diffuse = { 0.714f, 0.4284f, 0.18144f, 1f };
		float[] mat_specular = { 0.393548f, 0.271906f, 0.166721f, 1f };
		float shine = 25.6f;
		gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, shine);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, mat_ambient, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, mat_diffuse, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, mat_specular, 0);
	}

}
