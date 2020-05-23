package edu.cg;

import edu.cg.algebra.Point;
import edu.cg.algebra.Vec;
import edu.cg.scene.Scene;
import edu.cg.scene.lightSources.CutoffSpotlight;
import edu.cg.scene.lightSources.DirectionalLight;
import edu.cg.scene.lightSources.Light;
import edu.cg.scene.objects.Dome;
import edu.cg.scene.objects.Material;
import edu.cg.scene.objects.Plain;
import edu.cg.scene.objects.Shape;
import edu.cg.scene.objects.Sphere;
import edu.cg.scene.objects.Surface;

public class Scenes {
	public static Scene scene1() {
		Shape sphereShape1 = new Sphere(new Point(0.0), 1.0);
		Material sphereMat1 = new Material().initKa(new Vec(0.8, 0.05, 0.05)).initKd(new Vec(0.0)).initKs(new Vec(0.9))
				.initShininess(10).initIsTransparent(false).initRefractionIntensity(0.0);
		Surface boxSurface1 = new Surface(sphereShape1, sphereMat1);

		Light dirLight = new DirectionalLight(new Vec(-1.0, -1.0, -1.0), new Vec(0.9));

		return new Scene().initAmbient(new Vec(1.0))
				.initCamera(new Point(4, 4, 1.5), new Vec(-1.0, -1.0, -0.3), new Vec(0, 0, 1), 3)
				.addLightSource(dirLight).addSurface(boxSurface1).initName("scene1").initAntiAliasingFactor(1)
				.initRenderRefarctions(true).initRenderReflections(true).initMaxRecursionLevel(3);
	}

	public static Scene scene2() {
		// Define basic properties of the scene
		Scene finalScene = new Scene().initAmbient(new Vec(1.0))
				.initCamera(/* Camera Position = */new Point(0.0, 2.0, 6.0), 
						/* Towards Vector = */ new Vec(0.0, -0.1 ,-1.0),
						/* Up vector = */new Vec(0.0, 1.0, 0.0), 
						/*Distance to plain =*/ 2.0)
				.initName("scene2").initAntiAliasingFactor(1)
				.initAmbient(new Vec(0.4))
				.initRenderRefarctions(true).initRenderReflections(true).initMaxRecursionLevel(6);
        // Add Surfaces to the scene.
		// (1) A plain that represents the ground floor.
		Shape plainShape = new Plain(new Vec(0.0,1.0,0.0), new Point(0.0, -1.0, 0.0));
		Material plainMat = Material.getMetalMaterial();
		Surface plainSurface = new Surface(plainShape, plainMat);
		finalScene.addSurface(plainSurface);

		// (2) We will also add spheres to form a triangle shape (similar to a pool game).
		for (int depth = 0; depth < 4; depth++) {
			for(int width=-1*depth; width<=depth; width++) {
				Shape sphereShape = new Sphere(new Point((double)width, 0.0, -1.0*(double)depth), 0.5);
				Material sphereMat = Material.getRandomMaterial();
				Surface sphereSurface = new Surface(sphereShape, sphereMat);
				finalScene.addSurface(sphereSurface);
			}
			
		}
		// Add lighting condition:
		DirectionalLight directionalLight=new DirectionalLight(new Vec(0.5,-0.5,0.0),new Vec(0.7));
		finalScene.addLightSource(directionalLight);

		
		return finalScene;
	}
	
	public static Scene scene3() {
		// Define basic properties of the scene
		Scene finalScene = new Scene().initAmbient(new Vec(1.0))
				.initCamera(/* Camera Position = */new Point(0.0, 2.0, 6.0), 
						/* Towards Vector = */ new Vec(0.0, -0.1 ,-1.0),
						/* Up vector = */new Vec(0.0, 1.0, 0.0), 
						/*Distance to plain =*/ 2.0)
				.initName("scene3").initAntiAliasingFactor(1)
				.initRenderRefarctions(true).initRenderReflections(true).initMaxRecursionLevel(6);
        // Add Surfaces to the scene.
		// (1) A plain that represents the ground floor.
		Shape plainShape = new Plain(new Vec(0.0,1.0,0.0), new Point(0.0, -1.0, 0.0));
		Material plainMat = Material.getMetalMaterial();
		Surface plainSurface = new Surface(plainShape, plainMat);
		finalScene.addSurface(plainSurface);
		
		// (2) We will also add spheres to form a triangle shape (similar to a pool game). 
		for (int depth = 0; depth < 4; depth++) {
			for(int width=-1*depth; width<=depth; width++) {
				Shape sphereShape = new Sphere(new Point((double)width, 0.0, -1.0*(double)depth), 0.5);
				Material sphereMat = Material.getRandomMaterial();
				Surface sphereSurface = new Surface(sphereShape, sphereMat);
				finalScene.addSurface(sphereSurface);
			}
			
		}
		
		// Add light sources:
		CutoffSpotlight cutoffSpotlight = new CutoffSpotlight(new Vec(0.0, -1.0, 0.0), 45.0);
		cutoffSpotlight.initPosition(new Point(4.0, 4.0, -3.0));
		cutoffSpotlight.initIntensity(new Vec(1.0,0.6,0.6));
		finalScene.addLightSource(cutoffSpotlight);
		cutoffSpotlight = new CutoffSpotlight(new Vec(0.0, -1.0, 0.0), 30.0);
		cutoffSpotlight.initPosition(new Point(-4.0, 4.0, -3.0));
		cutoffSpotlight.initIntensity(new Vec(0.6,1.0,0.6));
		finalScene.addLightSource(cutoffSpotlight);
		cutoffSpotlight = new CutoffSpotlight(new Vec(0.0, -1.0, 0.0), 30.0);
		cutoffSpotlight.initPosition(new Point(0.0, 4.0, 0.0));
		cutoffSpotlight.initIntensity(new Vec(0.6,0.6,1.0));
		finalScene.addLightSource(cutoffSpotlight);
		DirectionalLight directionalLight=new DirectionalLight(new Vec(0.5,-0.5,0.0),new Vec(0.2));
		finalScene.addLightSource(directionalLight);
		
		return finalScene;
	}

	public static Scene scene4() {
		// Define basic properties of the scene
		Scene finalScene = new Scene().initAmbient(new Vec(1.0))
				.initCamera(/* Camera Position = */new Point(0.0, 2.0, 6.0), 
						/* Towards Vector = */ new Vec(0.0, -0.1 ,-1.0),
						/* Up vector = */new Vec(0.0, 1.0, 0.0), 
						/*Distance to plain =*/ 2.0)
				.initName("scene4").initAntiAliasingFactor(1)
				.initRenderRefarctions(true).initRenderReflections(true).initMaxRecursionLevel(6);
        // Add Surfaces to the scene.
		
		// (2) Add two domes to make it look like we split a sphere in half. 
		Shape domeShape = new Dome(new Point(2.0, 0.0, -10.0), 5.0, new Vec(1.0, 0.0, 0.0));
		Material domeMat = Material.getRandomMaterial();
		Surface domeSurface = new Surface(domeShape, domeMat);
		finalScene.addSurface(domeSurface);
		
		domeShape = new Dome(new Point(-2.0, 0.0, -10.0), 5.0, new Vec(-1.0, 0.0, 0.0));
		domeSurface = new Surface(domeShape, domeMat);
		finalScene.addSurface(domeSurface);
		
		// Add light sources:
		CutoffSpotlight cutoffSpotlight = new CutoffSpotlight(new Vec(0.0, -1.0, 0.0), 75.0);
		cutoffSpotlight.initPosition(new Point(0.0, 6.0, -10.0));
		cutoffSpotlight.initIntensity(new Vec(.5,0.5,0.5));
		finalScene.addLightSource(cutoffSpotlight);
		
		return finalScene;
	}

	public static Scene scene5() {
		// Define basic properties of the scene
		Scene finalScene = new Scene().initAmbient(new Vec(1.0))
				.initBackgroundColor(new Vec(0.0))
				.initCamera(/* Camera Position = */new Point(0.0, 0.8, 4.0),
						/* Towards Vector = */ new Vec(0.0, -0.1 ,-1.0),
						/* Up vector = */new Vec(0.0, 1.0, 0.0),
						/*Distance to plain =*/ 2.0)
				.initName("scene5").initAntiAliasingFactor(1)
				.initRenderRefarctions(true).initRenderReflections(true).initMaxRecursionLevel(6);
		// Add Surfaces to the scene.
		// (1) A plain that represents the ground floor.
		Shape plainShape = new Plain(new Vec(0.0,1.0,0.0), new Point(0.0, -1.0, 0.0));
		Material plainMat = Material.getMetalMaterial();
		Surface plainSurface = new Surface(plainShape, plainMat);
		finalScene.addSurface(plainSurface);

		Shape sphereShape1 = new Sphere(new Point(0.0), 1);
		Material sphereMat1 = new Material().initKa(new Vec(0.8, 0.05, 0.05)).initKd(new Vec(0.0)).initKs(new Vec(1.0))
				.initShininess(10).initIsTransparent(false).initRefractionIntensity(0.0).initReflectionIntensity(1.0);
		Surface boxSurface1 = new Surface(sphereShape1, sphereMat1);
		finalScene.addSurface(boxSurface1);

		return finalScene;
	}

	public static Scene scene6() {
		// Define basic properties of the scene
		Scene finalScene = new Scene().initAmbient(new Vec(1.0))
				.initBackgroundColor(new Vec(0.0))
				.initCamera(/* Camera Position = */new Point(0.75, 0.8, 4.0),
						/* Towards Vector = */ new Vec(0.0, -0.1 ,-1.0),
						/* Up vector = */new Vec(0.0, 1.0, 0.0),
						/*Distance to plain =*/ 2.0)
				.initName("scene6").initAntiAliasingFactor(1)
				.initRenderRefarctions(true).initRenderReflections(true).initMaxRecursionLevel(6);

		Shape plainShape = new Plain(new Vec(0.0,1.0,0.0), new Point(0.0, -1.0, 0.0));
		Material plainMat = Material.getMetalMaterial();
		Surface plainSurface = new Surface(plainShape, plainMat);
		finalScene.addSurface(plainSurface);

		Shape sphere1 = new Sphere(new Point(0.0), 0.7);
		Material sphere1Mat = Material.getGlassMaterial(true);
		Surface sphere1Surface = new Surface(sphere1, sphere1Mat);
		finalScene.addSurface(sphere1Surface);

		Shape innerSphere = new Sphere(new Point(0.0), 0.3);
		Material innerSphereMat = new Material().initKa(new Vec(0, 0.4, 1)).initKd(new Vec(0.0)).initKs(new Vec(1.0))
				.initShininess(10).initIsTransparent(false).initRefractionIntensity(0.0).initReflectionIntensity(1.0);
		Surface innerSphereSurface = new Surface(innerSphere, innerSphereMat);
		finalScene.addSurface(innerSphereSurface);

		Shape sphere2 = new Sphere(new Point(1.0, 0.0, -2.5), 0.7);
		Material sphere2Mat = new Material().initKa(new Vec(0.8, 0.4, 0)).initKd(new Vec(0.0)).initKs(new Vec(1.0))
				.initShininess(10).initIsTransparent(false).initRefractionIntensity(0.0).initReflectionIntensity(1.0);
		Surface sphere2Surface = new Surface(sphere2, sphere2Mat);
		finalScene.addSurface(sphere2Surface);

		Shape sphere3 = new Sphere(new Point(-1.0, 0.0, -2.5), 0.7);
		Material sphere3Mat = new Material().initKa(new Vec(0.8, 0.4, 0)).initKd(new Vec(0.0)).initKs(new Vec(1.0))
				.initShininess(10).initIsTransparent(false).initRefractionIntensity(0.0).initReflectionIntensity(1.0);
		Surface sphere3Surface = new Surface(sphere3, sphere3Mat);
		finalScene.addSurface(sphere3Surface);

		return finalScene;
	}

	public static Scene scene7() {
		// Define basic properties of the scene
		Scene finalScene = new Scene().initAmbient(new Vec(1.0))
				.initCamera(/* Camera Position = */new Point(0.0, 2.0, 6.0),
						/* Towards Vector = */ new Vec(0.0, -0.1 ,-1.0),
						/* Up vector = */new Vec(0.0, 1.0, 0.0),
						/*Distance to plain =*/ 2.0)
				.initName("scene7").initAntiAliasingFactor(1)
				.initBackgroundColor(new Vec(0.1))
				.initRenderRefarctions(true).initRenderReflections(true).initMaxRecursionLevel(3);
		// Add Surfaces to the scene.

		Shape plainShape = new Plain(new Vec(0.0,-4.3,0.0), new Point(0.0, -4.3, 0.0));
		Material plainMat = Material.getMetalMaterial().initReflectionIntensity(0);
		Surface plainSurface = new Surface(plainShape, plainMat);
		finalScene.addSurface(plainSurface);

		// (2) Add two domes to make it look like we split a sphere in half.
		Shape domeShape = new Dome(new Point(0,0,-10), 5.0, new Vec(0.0, 1.0, 0.0));
		Material domeMat = new Material().initKa(new Vec(0.8, 0.05, 0.05)).initKd(new Vec(0.0)).initKs(new Vec(1.0))
				.initShininess(3).initIsTransparent(false).initRefractionIntensity(0.0).initReflectionIntensity(0.3);
		Surface domeSurface = new Surface(domeShape, domeMat);
		finalScene.addSurface(domeSurface);

		domeShape = new Dome(new Point(0,0,-10), 5.0, new Vec(0.0, -1.0, 0.0));
		domeMat = new Material().initKa(new Vec(0.9)).initKd(new Vec(0.0)).initKs(new Vec(1.0))
				.initShininess(3).initIsTransparent(false).initRefractionIntensity(0.0).initReflectionIntensity(0.3);
		domeSurface = new Surface(domeShape, domeMat);
		finalScene.addSurface(domeSurface);

		Shape outerSphere = new Sphere(new Point(0, 0, -5.6), 0.9);
		Material outerSphereMat = new Material().initKa(new Vec(0, 0, 0)).initKd(new Vec(0.0)).initKs(new Vec(1.0))
				.initShininess(3).initIsTransparent(false).initRefractionIntensity(0.0).initReflectionIntensity(0.3);
		Surface otherSphereSurface = new Surface(outerSphere, outerSphereMat);
		finalScene.addSurface(otherSphereSurface);

		Shape innerSphere = new Sphere(new Point(0, 0, -4.9), 0.5);
		Material innerSphereMat = new Material().initKa(new Vec(0.9)).initKd(new Vec(0.0)).initKs(new Vec(1.0))
				.initShininess(3).initIsTransparent(false).initRefractionIntensity(0.0).initReflectionIntensity(0.3);
		Surface innerSphereSurface = new Surface(innerSphere, innerSphereMat);
		finalScene.addSurface(innerSphereSurface);

		// Add light sources:
		Light dirLight = new DirectionalLight(new Vec(-1.0, -1.0, -0.5), new Vec(0.7));
		finalScene.addLightSource(dirLight);

		return finalScene;
	}

	public static Scene scene8() {
		// Define basic properties of the scene
		Scene finalScene = new Scene().initAmbient(new Vec(1.0))
				.initCamera(/* Camera Position = */new Point(0.0, 2.0, 6.0),
						/* Towards Vector = */ new Vec(0.0, -0.1 ,-1.0),
						/* Up vector = */new Vec(0.0, 1.0, 0.0),
						/*Distance to plain =*/ 2.0)
				.initName("scene8").initAntiAliasingFactor(1)
				.initBackgroundColor(new Vec(0.05, 0.05, 0.7))
				.initRenderRefarctions(true).initRenderReflections(true).initMaxRecursionLevel(3);
		// Add Surfaces to the scene.

		Shape plainShape = new Plain(new Vec(0.0,-4.3,0.0), new Point(0.0, -4.3, 0.0));
		Material plainMat = Material.getMetalMaterial().initKa(new Vec(0.35,0.2,0)).initReflectionIntensity(0.1);
		Surface plainSurface = new Surface(plainShape, plainMat);
		finalScene.addSurface(plainSurface);

		Shape transparentSphere = new Sphere(new Point(0, 0, -5), 4);
		Material transparentSphereMat = Material.getGlassMaterial(true)
				.initRefractionIntensity(0.7).initRefractionIndex(1).initReflectionIntensity(0.1);
		Surface transparentSphereSurface = new Surface(transparentSphere, transparentSphereMat);
		finalScene.addSurface(transparentSphereSurface);

		Shape distantSphere1 = new Sphere(new Point(-5, 0, -25), 4);
		Material distantSphere1Mat = Material.getMetalMaterial().initKa(new Vec(0.05, 0.8, 0.7));
		Surface distantSphere1Surface = new Surface(distantSphere1, distantSphere1Mat);
		finalScene.addSurface(distantSphere1Surface);

		Shape distantSphere2 = new Sphere(new Point(5, 0, -25), 4);
		Material distantSphere2Mat = Material.getMetalMaterial().initKa(new Vec(0.85, 0.2, 0.5));
		Surface distantSphere2Surface = new Surface(distantSphere2, distantSphere2Mat);
		finalScene.addSurface(distantSphere2Surface);

		// Add light sources:
		Light dirLight = new DirectionalLight(new Vec(-1.0, -1.0, -0.5), new Vec(0.7));
		finalScene.addLightSource(dirLight);

		return finalScene;
	}

}
