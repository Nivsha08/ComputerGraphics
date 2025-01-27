package edu.cg;

import java.awt.Component;
import java.util.List;

import com.jogamp.opengl.glu.GLU;
import edu.cg.models.BoundingSphere;
import edu.cg.models.Car.Materials;
import edu.cg.models.Colors;

import javax.swing.JOptionPane;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.FPSAnimator;

import edu.cg.algebra.Vec;
import edu.cg.models.Settings;
import edu.cg.models.Track;
import edu.cg.models.Car.F1Car;

/**
 * An OpenGL 3D Game.
 */
public class NeedForSpeed implements GLEventListener {
    private GameState gameState = null; // Tracks the car movement and orientation
    private F1Car car = null; // The F1 car we want to render
    private Vec carCameraTranslation = null; // The accumulated translation that should be applied on the car, camera
    // and light sources
    private Track gameTrack = null; // The game track we want to render
    private FPSAnimator ani; // This object is responsible to redraw the model with a constant FPS
    private Component glPanel; // The canvas we draw on.
    private boolean isModelInitialized = false; // Whether model.init() was called.
    private boolean isDayMode = true; // Indicates whether the lighting mode is day/night.
    private boolean isBirdseyeView = false; // Indicates whether the camera is looking from above on the scene or
    // looking towards the car direction.

    private int dayLight = GL2.GL_LIGHT0;
    private int carLeftSpotlight = GL2.GL_LIGHT1;
    private int carRightSpotlight = GL2.GL_LIGHT2;
    private int moonLight = GL2.GL_LIGHT3;

    public NeedForSpeed(Component glPanel) {
        this.glPanel = glPanel;
        gameState = new GameState();
        gameTrack = new Track();
        carCameraTranslation = new Vec(0.0);
        car = new F1Car();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        if (!isModelInitialized) {
            initModel(gl);
        }
        if (isDayMode) {
            gl.glClearColor(Colors.DAY_BG[0], Colors.DAY_BG[1], Colors.DAY_BG[2], Colors.DAY_BG[3]);
        } else {
            gl.glClearColor(Colors.NIGHT_BG[0], Colors.NIGHT_BG[1], Colors.NIGHT_BG[2], Colors.NIGHT_BG[3]);
        }
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        // Step (1) Update the accumulated translation that needs to be
        // applied on the car, camera and light sources.
        updateCarCameraTranslation(gl);
        // Step (2) Position the camera and setup its orientation
        reshape(drawable, glPanel.getX(), glPanel.getY(), glPanel.getWidth(), glPanel.getHeight());
        setupCamera(gl);
        // Step (3) setup the lights.
        setupLights(gl);
        // Step (4) render the car.
        renderCar(gl);
        // Step (5) render the track.
        renderTrack(gl);
        // Step (6) check collision. Note this has nothing to do with OpenGL.
        if (checkCollision()) {
            JOptionPane.showMessageDialog(this.glPanel, "Game is Over");
            this.gameState.resetGameState();
            this.carCameraTranslation = new Vec(0.0);
        }

    }

    /**
     * @return Checks if the car intersects the one of the boxes on the track.
     */
    private boolean checkCollision() {
        List<BoundingSphere> carBoundingSpheres = getUpdatedCarBoundingSpheres();
        List<BoundingSphere> trackBoundingSpheres = gameTrack.getBoundingSpheres();
        for (BoundingSphere boxSphere : trackBoundingSpheres) {
            if (carBoundingSpheres.get(0).checkIntersection(boxSphere)) {
                for (int i = 1; i < carBoundingSpheres.size(); i++) {
                    if (carBoundingSpheres.get(i).checkIntersection(boxSphere))
                        return true;
                }
            }
        }
        return false;
    }

    private List<BoundingSphere> getUpdatedCarBoundingSpheres() {
		Vec totalTranslation = Settings.CAR_INIT_POS.add(carCameraTranslation).toVec();
		List<BoundingSphere> carBoundingSpheres = car.getBoundingSpheres();
		for (BoundingSphere sphere : carBoundingSpheres) {
			sphere.translateCenter(totalTranslation.x, totalTranslation.y, totalTranslation.z);
			sphere.setRadius(Settings.CAR_SCALE_FACTOR * sphere.getRadius());
		}
		return carBoundingSpheres;
	}

    private void updateCarCameraTranslation(GL2 gl) {
        // Update the car and camera translation values (not the ModelView-Matrix).
        // - Always keep track of the car offset relative to the starting
        // point.
        // - Change the track segments here.
        Vec ret = gameState.getNextTranslation();
        carCameraTranslation = carCameraTranslation.add(ret);
        double dx = Math.max(carCameraTranslation.x, -Settings.ASPHALT_TEXTURE_DEPTH / 2.0 - 2);
        carCameraTranslation.x = (float) Math.min(dx, Settings.ASPHALT_TEXTURE_DEPTH / 2.0 + 2);
        if (Math.abs(carCameraTranslation.z) >= Settings.TRACK_LENGTH + 10.0) {
            carCameraTranslation.z = -(float) (Math.abs(carCameraTranslation.z) % Settings.TRACK_LENGTH);
            gameTrack.changeTrack(gl);
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU glu = new GLU();
        double aspectRatio = (double) width / height;
        glu.gluPerspective(
                Settings.CAMERA_VIEWING_ANGEL_DEGREES,
                aspectRatio,
                Settings.PROJECTION_PLANE_DISTANCE_FROM_CAM,
                Settings.PROJECTION_PLANE_DISTANCE_FROM_CAM + Settings.TRACK_LENGTH
        );
    }


    private void setupCamera(GL2 gl) {
        GLU glu = new GLU();
        if (isBirdseyeView) {
            double cameraNewXPosition = carCameraTranslation.x + Settings.BIRDS_EYE_CAM_INIT.x;
            double cameraNewYPosition = carCameraTranslation.y + Settings.BIRDS_EYE_CAM_INIT.y;
            double cameraNewZPosition = carCameraTranslation.z + Settings.BIRDS_EYE_CAM_INIT.z;
            glu.gluLookAt(cameraNewXPosition, cameraNewYPosition, cameraNewZPosition,
                    cameraNewXPosition, cameraNewYPosition - 1, cameraNewZPosition,
                    Settings.BIRDS_EYE_V_UP.x, Settings.BIRDS_EYE_V_UP.y, Settings.BIRDS_EYE_V_UP.z
            );
        } else {
            double cameraNewXPosition = carCameraTranslation.x + Settings.THIRD_PERSON_CAM_INIT.x;
            double cameraNewYPosition = carCameraTranslation.y + Settings.THIRD_PERSON_CAM_INIT.y;
            double cameraNewZPosition = carCameraTranslation.z + Settings.THIRD_PERSON_CAM_INIT.z;
            glu.gluLookAt(cameraNewXPosition, cameraNewYPosition, cameraNewZPosition,
                    cameraNewXPosition, cameraNewYPosition, cameraNewZPosition - 1,
                    Settings.THIRD_PERSON_V_UP.x, Settings.THIRD_PERSON_V_UP.y, Settings.THIRD_PERSON_V_UP.z
            );
        }
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    private void setupLights(GL2 gl) {
        if (isDayMode) {
            disableNightLightning(gl);
            gl.glLightfv(dayLight, gl.GL_POSITION, Settings.DIRECTION_TO_SUN, 0);
            gl.glLightfv(dayLight, gl.GL_SPECULAR, Settings.SUN_INTENSITY, 0);
            gl.glLightfv(dayLight, gl.GL_DIFFUSE, Settings.SUN_INTENSITY, 0);
            gl.glEnable(dayLight);
        } else {
            disableDayLightning(gl);
            gl.glLightfv(moonLight, gl.GL_POSITION, Settings.DIRECTION_TO_MOON, 0);
            gl.glLightfv(moonLight, gl.GL_SPECULAR, Settings.MOON_INTENSITY, 0);
            gl.glLightfv(moonLight, gl.GL_DIFFUSE, Settings.MOON_INTENSITY, 0);
            gl.glEnable(moonLight);
            setSpotlight(gl, carRightSpotlight);
            setSpotlight(gl, carLeftSpotlight);
        }

    }

    private void setSpotlight(GL2 gl, int lightNumber) {
        updateSpotlight(gl, lightNumber);
        gl.glLightf(lightNumber, gl.GL_SPOT_CUTOFF, Settings.SPOTLIGHT_CUTOFF_ANGLE);
        gl.glLightf(lightNumber, gl.GL_SPOT_EXPONENT, Settings.SPOTLIGHT_EXPONENT);
        gl.glLightfv(lightNumber, gl.GL_AMBIENT, Settings.SPOTLIGHT_INTENSITY, 0);
        gl.glLightfv(lightNumber, gl.GL_SPECULAR, Settings.SPOTLIGHT_INTENSITY, 0);
        gl.glLightfv(lightNumber, gl.GL_DIFFUSE, Settings.SPOTLIGHT_INTENSITY, 0);
        gl.glLightf(lightNumber, gl.GL_LINEAR_ATTENUATION, 0.1f);
        gl.glLightf(lightNumber, gl.GL_CONSTANT_ATTENUATION, 0.9f);
        gl.glEnable(lightNumber);
    }

    private void updateSpotlight(GL2 gl, int lightNumber) {
        float directionX = (float) (gameState.getCarRotation()) / 100;
        float positionX = (lightNumber == carRightSpotlight) ?
                carCameraTranslation.x + 0.5f : carCameraTranslation.x - 0.5f;
        float positionY = carCameraTranslation.y + 1f;
        float positionZ = (float) (Settings.CAR_INIT_POS.z + carCameraTranslation.z - Settings.CAR_LENGTH / 2.0);
        float[] position = new float[]{positionX, positionY, positionZ, 1.0f};
        gl.glLightfv(lightNumber, gl.GL_POSITION, position, 0);
        gl.glLightfv(lightNumber, gl.GL_SPOT_DIRECTION, new float[]{directionX, 0.0f, -1.0f}, 0);
    }

    private void disableDayLightning(GL2 gl) {
        gl.glDisable(dayLight);
    }

    ;

    private void disableNightLightning(GL2 gl) {
        gl.glDisable(carLeftSpotlight);
        gl.glDisable(carRightSpotlight);
        gl.glDisable(moonLight);
    }

    ;

    private void renderTrack(GL2 gl) {
        // * Note: the track is not translated. It should be fixed.
        gl.glPushMatrix();
        gameTrack.render(gl);
        Materials.SetRedMetalMaterial(gl);
        gameTrack.getBoundingSpheres().get(0).render(gl);
        gl.glPopMatrix();
    }

    private void renderCar(GL2 gl) {
        Vec totalTranslation = Settings.CAR_INIT_POS.add(carCameraTranslation).toVec();
        double rotationAngle = -gameState.getCarRotation();
        gl.glPushMatrix();
        gl.glTranslated(totalTranslation.x, totalTranslation.y, totalTranslation.z);
        gl.glRotated(90, 0.0, 1.0, 0.0);
        gl.glRotated(rotationAngle, 0.0, 1.0, 0.0);
        gl.glScaled(Settings.CAR_SCALE_FACTOR, Settings.CAR_SCALE_FACTOR, Settings.CAR_SCALE_FACTOR);
        car.render(gl);
        gl.glPopMatrix();
        updateSpotlight(gl, carRightSpotlight);
        updateSpotlight(gl, carLeftSpotlight);
    }

    public GameState getGameState() {
        return gameState;
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        // Initialize display callback timer
        ani = new FPSAnimator(30, true);
        ani.add(drawable);
        glPanel.repaint();

        initModel(gl);
        ani.start();
    }

    public void initModel(GL2 gl) {
        gl.glCullFace(GL2.GL_BACK);
        gl.glEnable(GL2.GL_CULL_FACE);

        gl.glEnable(GL2.GL_NORMALIZE);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_SMOOTH);

        car.init(gl);
        gameTrack.init(gl);
        isModelInitialized = true;
    }

    /**
     * Start redrawing the scene with 30 FPS
     */
    public void startAnimation() {
        if (!ani.isAnimating())
            ani.start();
    }

    /**
     * Stop redrawing the scene with 30 FPS
     */
    public void stopAnimation() {
        if (ani.isAnimating())
            ani.stop();
    }

    public void toggleNightMode() {
        isDayMode = !isDayMode;
    }

    public void changeViewMode() {
        isBirdseyeView = !isBirdseyeView;
    }

}
