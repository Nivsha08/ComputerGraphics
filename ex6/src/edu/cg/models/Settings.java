package edu.cg.models;

import edu.cg.algebra.Point;
import edu.cg.algebra.Vec;
import edu.cg.models.Car.Specification;

import java.util.Set;

public class Settings {

    // Unit is METER

    // TrackSegment settings
    public final static double ASPHALT_TEXTURE_WIDTH = 20.0;
    public final static double ASPHALT_TEXTURE_DEPTH = 10.0;
    public final static double GRASS_TEXTURE_WIDTH = 10.0;
    public final static double GRASS_TEXTURE_DEPTH = 10.0;
    public final static double TRACK_LENGTH = 500.0;
    public final static double BOX_LENGTH = 1.5;

    // Car settings
    public static final double CAR_SCALE_FACTOR = 4.1;
    public static final double CAR_LENGTH =
            CAR_SCALE_FACTOR * (Specification.F_LENGTH + Specification.C_LENGTH + Specification.B_LENGTH);
    public static final Point CAR_INIT_POS = new Point(0.0, 0.3, -CAR_LENGTH / 2.0 - 1.5);

    // Scene settings
    public static final double CAMERA_VIEWING_ANGEL_DEGREES = 60.0;
    public static final double PROJECTION_PLANE_DISTANCE_FROM_CAM = 2.0;

    public static final Point THIRD_PERSON_CAM_INIT =
            new Point(0.0, 2.0, Settings.CAR_INIT_POS.z + (Settings.CAR_LENGTH / 2.0) + 4.0);
    public static final Vec THIRD_PERSON_V_UP = new Vec(0.0, 1.0, 0.0);
    public static final Vec THIRD_PERSON_V_TOWARDS = new Vec(0.0, 0.0, -1.0);

    public static final Point BIRDS_EYE_CAM_INIT = new Point(0.0, 50.0, -CAR_LENGTH - 1.5 - 22.0);
    public static final Vec BIRDS_EYE_V_UP = new Vec(0.0, 0.0, -1.0);
    public static final Vec BIRDS_EYE_V_TOWARDS = new Vec(0.0, -1.0, 0.0);

    // Lights settings
    public static final float[] SUN_INTENSITY = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
    public static final float[] DIRECTION_TO_SUN = new float[] { 0.0f, 1.0f, 1.0f, 0.0f };
    public static final float[] MOON_INTENSITY = new float[] { 0.3f, 0.3f, 0.3f, 1.0f };
    public static final float[] DIRECTION_TO_MOON = new float[] { 0.0f, 1.0f, 1.0f, 0.0f };

}
