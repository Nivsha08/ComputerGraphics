package edu.cg.models.Car;


public class Specification {

	// Car colors
	public static final float[] CAR_MAIN_COLOR = new float[] { 0.17f, 0.17f, 0.17f };
	public static final float[] CAR_ACCENT_COLOR = new float[] { 0.047f, 0.47f, 0.52f };
	public static final float[] HEADLIGHTS_COLOR = new float[] {0.98f, 0.92f, 0.6f};
	public static final float[] WHITE_COLOR = new float[] { 0.95f, 0.95f, 0.95f };
	public static final float[] EXHAUST_COLOR = new float[] {0.7f, 0.7f, 0.7f};
	public static final float[] BACK_LIGHT_COLOR = new float[] {0.87f, 0.27f, 0.2f};

	// Engine specification:
	public static final double ENGINE_BOX_LENGTH = 0.2;
	public static final double ENGINE_BOX_HEIGHT = 0.015;
	public static final double ENGINE_BOX_DEPTH_1 = 0.9 * Specification.B_DEPTH_1;
	public static final double ENGINE_BOX_DEPTH_2 = 0.5 * Specification.B_DEPTH_2;
	public static final double ENGINE_ROD_RADIUS = 0.005;
	public static final double ENGINE_ROD_DEPTH = 0.6 * ENGINE_BOX_DEPTH_1;
	public static final double ENGINE_RODS_DISTANCE = 6 * ENGINE_ROD_RADIUS;

	// Exhaust specification:
	public static final double EXHAUST_LENGTH = 0.013;
	public static final double EXHAUST_RADIUS_1 = 0.015;
	public static final double EXHAUST_RADIUS_2 = 0.02;

	// Strip specification:
	public static final double STRIP_HEIGHT = 0.005;
	public static final double NARROW_STRIP_DEPTH = 0.03;
	public static final double WIDE_STRIP_DEPTH = 1.6 * NARROW_STRIP_DEPTH;

	// Back light specification:
	public static final double BACK_LIGHT_PANEL_LENGTH = 0.001;
	public static final double BACK_LIGHT_PANEL_HEIGHT = 0.35 * Specification.B_HEIGHT_1;
	public static final double BACK_LIGHT_PANEL_DEPTH = 1.3 * BACK_LIGHT_PANEL_HEIGHT;
	public static final double BACK_LIGHT_RADIUS = 0.004;

	// Wheels specification:
	public static final double TIRE_DEPTH = 0.1;
	public static final double TIRE_RADIUS = .075;
	public static final double PAIR_OF_WHEELS_ROD_DEPTH = 0.2;
	public static final double PAIR_OF_WHEELS_ROD_RADIUS = 0.01;
	
	// Front Body Specification:
	public static final double F_LENGTH = 0.6;
	// Hood
	public static final double F_HOOD_LENGTH = 0.75*F_LENGTH;
	public static final double F_HOOD_LENGTH_2 = 2.5*TIRE_RADIUS;
	public static final double F_HOOD_LENGTH_1 = F_HOOD_LENGTH-F_HOOD_LENGTH_2;
	public static final double F_HOOD_DEPTH_1 = 2.0 * TIRE_DEPTH + PAIR_OF_WHEELS_ROD_DEPTH;
	public static final double F_HOOD_DEPTH_2 = 0.25*F_HOOD_DEPTH_1;
	public static final double F_HOOD_DEPTH_3 = 0.8*F_HOOD_DEPTH_2;
	public static final double F_HOOD_HEIGHT_1 = 1.75*TIRE_RADIUS;
	public static final double F_HOOD_HEIGHT_2 = 1.5*TIRE_RADIUS;
	public static final double F_DEPTH = F_HOOD_DEPTH_1;
	public static final double F_HEIGHT = F_HOOD_HEIGHT_1;


	// Bumper
	public static final double F_BUMPER_LENGTH = 0.25*F_LENGTH;
	public static final double F_BUMPER_DEPTH = 0.5*F_HOOD_DEPTH_1;
	public static final double F_BUMPER_HEIGHT_2 = 5e-4;
	public static final double F_BUMPER_HEIGHT_1 = 0.25*F_HOOD_HEIGHT_2;
	public static final double F_BUMPER_WINGS_DEPTH = 0.8*0.5*(F_HOOD_DEPTH_1-F_BUMPER_DEPTH);
	public static final double F_BUMPER_WINGS_HEIGHT_1 = 0.75*F_HOOD_HEIGHT_1;
	public static final double F_BUMPER_WINGS_HEIGHT_2 = F_BUMPER_HEIGHT_2;
	public static final double F_BUMPER_HEADLIGHT_RADIUS = 0.4*TIRE_RADIUS;

	// Center Body Specification:
	public static final double C_LENGTH  = 0.25;
	public static final double C_HEIGHT = 1.75*F_HOOD_HEIGHT_1;
	public static final double C_DEPTH = F_HOOD_DEPTH_1;

	// --> Base
	public static final double C_BASE_LENGTH  = C_LENGTH;
	public static final double C_BASE_HEIGHT= 0.1*F_HOOD_HEIGHT_1;
	// --> Back Seat
	public static final double C_BACK_LENGTH = C_BASE_LENGTH *0.5*0.75;
	public static final double C_BACK_HEIGHT_1= F_HOOD_HEIGHT_1;
	public static final double C_BACK_HEIGHT_2= 1.75*F_HOOD_HEIGHT_1;
	public static final double C_BACK_DEPTH = 0.25*F_HOOD_DEPTH_1;
	// --> Front
	public static final double C_FRONT_LENGTH = C_BASE_LENGTH *0.25*0.75;
	public static final double C_FRONT_HEIGHT_2= F_HOOD_HEIGHT_1-C_BASE_HEIGHT;
	public static final double C_FRONT_HEIGHT_1= F_HOOD_HEIGHT_1;
	public static final double C_FRONT_DEPTH_2 = F_HOOD_DEPTH_1;
	public static final double C_FRONT_DEPTH_1 = 0.25*C_FRONT_DEPTH_2;
	// --> Side
	public static final double C_SIDE_LENGTH = (C_DEPTH-C_FRONT_DEPTH_1)/2.0;
	public static final double C_SIDE_HEIGHT_2= F_HOOD_HEIGHT_1;
	public static final double C_SIDE_HEIGHT_1= F_HOOD_HEIGHT_1-C_BASE_HEIGHT;
	public static final double C_SIDE_DEPTH_1 = C_BASE_LENGTH ;
	public static final double C_SIDE_DEPTH_2 = C_BASE_LENGTH -2*C_FRONT_LENGTH;
	// Back Body Specification:
	// --> Base
	public static final double B_BASE_HEIGHT= C_BASE_HEIGHT;
	public static final double B_BASE_LENGTH = 5.0*TIRE_RADIUS;
	public static final double B_BASE_DEPTH = F_HOOD_DEPTH_1;
	// --> Top Box
	public static final double B_LENGTH = 7.0*TIRE_RADIUS;
	public static final double B_HEIGHT_2= F_HOOD_HEIGHT_1-B_BASE_HEIGHT;
	public static final double B_HEIGHT_1= 1.5*TIRE_RADIUS-B_BASE_HEIGHT;
	public static final double B_DEPTH_2 = B_BASE_DEPTH;
	public static final double B_DEPTH_1 = 0.25*B_DEPTH_2;
	
	// Spoiler Specification:    
	// --> Rods
	public static final double S_RODS_SIZE = 0.5*0.5*(B_DEPTH_1 + B_DEPTH_2);
	public static final double S_RODS_DISTANCE = 0.4*S_RODS_SIZE;
	public static final double S_ROD_RADIUS = 0.25*(S_RODS_SIZE-S_RODS_DISTANCE);
	public static final double S_ROD_HEIGHT = 0.05;
	// --> Wings
	public static final double S_DEPTH = 2.*0.5 * (B_DEPTH_1 + B_DEPTH_2);
	public static final double S_BASE_DEPTH = 0.9*S_DEPTH;
	public static final double S_WINGS_DEPTH = 0.5*(S_DEPTH - S_BASE_DEPTH);
	public static final double S_WINGS_HEIGHT = 0.15;
	public static final double S_BASE_HEIGHT = 0.015;
	public static final double S_LENGTH = 0.15;
	public static final double B_DEPTH = S_DEPTH;
	public static final double B_HEIGHT = S_WINGS_HEIGHT + B_HEIGHT_2+ S_ROD_HEIGHT;
	
}
