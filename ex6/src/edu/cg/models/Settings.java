package edu.cg.models;

import edu.cg.algebra.Point;
import edu.cg.algebra.Vec;
import edu.cg.models.Car.Specification;

import java.util.Set;

public class Settings {

    // Unit is METER

    // Scene settings
    public static final double CAMERA_VIEWING_ANGEL_DEGREES = 60.0;
    public static final double PROJECTION_PLANE_DISTANCE_FROM_CAM = 2.0;

    public static final Point THIRD_PERSON_CAM_INIT_POS = new Point(0.0, 1.0, 0.0);
    public static final Vec THIRD_PERSON_V_UP = new Vec(0.0, 1.0, 0.0);
    public static final Vec THIRD_PERSON_V_TOWARDS = new Vec(0.0, 0.0, -1.0);

    public static final Point BIRDS_EYE_CAM_INIT_POS = new Point(0.0, 50.0, 0.0);
    public static final Vec BIRDS_EYE_V_UP = new Vec(0.0, 0.0, -1.0);
    public static final Vec BIRDS_EYE_V_TOWARDS = new Vec(0.0, -1.0, 0.0);

    // TrackSegment settings
    public final static double ASPHALT_TEXTURE_WIDTH = 20.0;
    public final static double ASPHALT_TEXTURE_DEPTH = 10.0;
    public final static double GRASS_TEXTURE_WIDTH = 10.0;
    public final static double GRASS_TEXTURE_DEPTH = 10.0;
    public final static double TRACK_LENGTH = 500.0;
    public final static double BOX_LENGTH = 1.5;

    // Car settings
    public static final Point CAR_INIT_POS = new Point(0.0, 0.0,
            (Specification.F_LENGTH + Specification.C_LENGTH + Specification.B_LENGTH) / 2.0 + 4.0);


}
