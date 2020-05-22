package edu.cg.scene;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.cg.Logger;
import edu.cg.algebra.*;
import edu.cg.scene.camera.PinholeCamera;
import edu.cg.scene.lightSources.Light;
import edu.cg.scene.objects.Surface;

public class Scene {
	private String name = "scene";
	private int maxRecursionLevel = 1;
	private int antiAliasingFactor = 1; // gets the values of 1, 2 and 3
	private boolean renderRefractions = false;
	private boolean renderReflections = false;

	private PinholeCamera camera;
	private Vec ambient = new Vec(1, 1, 1); // white
	private Vec backgroundColor = new Vec(0, 0.5, 1); // blue sky
	private List<Light> lightSources = new LinkedList<>();
	private List<Surface> surfaces = new LinkedList<>();

	// MARK: initializers
	public Scene initCamera(Point eyePosition, Vec towardsVec, Vec upVec, double distanceToPlain) {
		this.camera = new PinholeCamera(eyePosition, towardsVec, upVec, distanceToPlain);
		return this;
	}

	public Scene initAmbient(Vec ambient) {
		this.ambient = ambient;
		return this;
	}

	public Scene initBackgroundColor(Vec backgroundColor) {
		this.backgroundColor = backgroundColor;
		return this;
	}

	public Scene addLightSource(Light lightSource) {
		lightSources.add(lightSource);
		return this;
	}

	public Scene addSurface(Surface surface) {
		surfaces.add(surface);
		return this;
	}

	public Scene initMaxRecursionLevel(int maxRecursionLevel) {
		this.maxRecursionLevel = maxRecursionLevel;
		return this;
	}

	public Scene initAntiAliasingFactor(int antiAliasingFactor) {
		this.antiAliasingFactor = antiAliasingFactor;
		return this;
	}

	public Scene initName(String name) {
		this.name = name;
		return this;
	}

	public Scene initRenderRefarctions(boolean renderRefarctions) {
		this.renderRefractions = renderRefarctions;
		return this;
	}

	public Scene initRenderReflections(boolean renderReflections) {
		this.renderReflections = renderReflections;
		return this;
	}

	// MARK: getters
	public String getName() {
		return name;
	}

	public int getFactor() {
		return antiAliasingFactor;
	}

	public int getMaxRecursionLevel() {
		return maxRecursionLevel;
	}

	public boolean getRenderRefractions() {
		return renderRefractions;
	}

	public boolean getRenderReflections() {
		return renderReflections;
	}

	@Override
	public String toString() {
		String endl = System.lineSeparator();
		return "Camera: " + camera + endl + "Ambient: " + ambient + endl + "Background Color: " + backgroundColor + endl
				+ "Max recursion level: " + maxRecursionLevel + endl + "Anti aliasing factor: " + antiAliasingFactor
				+ endl + "Light sources:" + endl + lightSources + endl + "Surfaces:" + endl + surfaces;
	}

	private transient ExecutorService executor = null;
	private transient Logger logger = null;

	private void initSomeFields(int imgWidth, int imgHeight, Logger logger) {
		this.logger = logger;
		// TODO: initialize your additional field here.
	}

	public BufferedImage render(int imgWidth, int imgHeight, double viewAngle, Logger logger)
			throws InterruptedException, ExecutionException, IllegalArgumentException {

		initSomeFields(imgWidth, imgHeight, logger);

		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
		camera.initResolution(imgHeight, imgWidth, viewAngle);
		int nThreads = Runtime.getRuntime().availableProcessors();
		nThreads = nThreads < 2 ? 2 : nThreads;
		this.logger.log("Intitialize executor. Using " + nThreads + " threads to render " + name);
		executor = Executors.newFixedThreadPool(nThreads);

		@SuppressWarnings("unchecked")
		Future<Color>[][] futures = (Future<Color>[][]) (new Future[imgHeight][imgWidth]);

		this.logger.log("Starting to shoot " + (imgHeight * imgWidth * antiAliasingFactor * antiAliasingFactor)
				+ " rays over " + name);

		for (int y = 0; y < imgHeight; ++y)
			for (int x = 0; x < imgWidth; ++x)
				futures[y][x] = calcColor(x, y);

		this.logger.log("Done shooting rays.");
		this.logger.log("Wating for results...");

		for (int y = 0; y < imgHeight; ++y)
			for (int x = 0; x < imgWidth; ++x) {
				Color color = futures[y][x].get();
				img.setRGB(x, y, color.getRGB());
			}

		executor.shutdown();

		this.logger.log("Ray tracing of " + name + " has been completed.");

		executor = null;
		this.logger = null;

		return img;
	}

	private Future<Color> calcColor(int x, int y) {
		return executor.submit(() -> {
			// TODO: You need to re-implement this method if you want to handle
			// super-sampling. You're also free to change the given implementation if you
			// want.
			Point centerPoint = camera.transform(x, y);
			Ray ray = new Ray(camera.getCameraPosition(), centerPoint);
			Vec color = calcColor(ray, 0);
			return color.toColor();
		});
	}

	private Vec calcColor(Ray ray, int recursionLevel) {
		Hit minimalIntersection = findMinimalIntersection(ray);
		if (minimalIntersection == null)
			return this.backgroundColor;
		Surface hitSurface = minimalIntersection.getSurface();
		Vec color = calcColorAtHittingPoint(ray, minimalIntersection);
		if (recursionLevel == maxRecursionLevel - 1)
			return color;
		if (renderReflections) {
			double reflectionCoeff = hitSurface.reflectionIntensity();
			Ray reflectedRay = getReflectedRay(ray, minimalIntersection);
			Vec reflectionColor = calcColor(reflectedRay, recursionLevel + 1);
			color = color.add(reflectionColor.mult(reflectionCoeff));
		}
		if (renderRefractions) {
			double refractionCoeff = hitSurface.refractionIntensity();
			Ray refractedRay = getRefractedRay(ray, minimalIntersection);
			if (refractedRay != null) {
				Vec refractionColor = calcColor(refractedRay, recursionLevel + 1);
				color = color.add(refractionColor.mult(refractionCoeff));
			}
		}
		return color;
	}

	private Hit findMinimalIntersection(Ray ray) {
		LinkedList<Hit> intersections = new LinkedList<>();
		for (Surface s : this.surfaces) {
			Hit surfaceIntersection = s.intersect(ray);
			if (surfaceIntersection != null) intersections.add(surfaceIntersection);
		}
		return (intersections.size() == 0) ? null : Collections.min(intersections);
	}

	private Vec calcColorAtHittingPoint(Ray ray, Hit minimalIntersection) {
		Surface intersectionSurface = minimalIntersection.getSurface();
		Point hittingPoint = ray.getHittingPoint(minimalIntersection);
		Vec color = new Vec(0);
		color = color.add(this.calcAmbientTerm(intersectionSurface));
		for (Light lightSource : this.lightSources) {
			Ray rayToLight = lightSource.rayToLight(hittingPoint);
			Vec lightIntensity = lightSource.intensity(hittingPoint, rayToLight);
			if (!this.isOccludedFromLight(lightSource, rayToLight)) {
				Vec diffuseTerm = this.calcDiffuseTerm(minimalIntersection, rayToLight);
				Vec specularTerm = this.calcSpecularTerm(minimalIntersection, ray, rayToLight);
				color = color.add(diffuseTerm.add(specularTerm).mult(lightIntensity));
			}
		}
		return color;
	}

	private Vec calcAmbientTerm(Surface surface) {
		Vec Ka = surface.Ka();
		return Ka.mult(this.ambient);
	}

	private boolean isOccludedFromLight(Light lightSource, Ray rayToLight) {
		for (Surface s : this.surfaces) {
			if (lightSource.isOccludedBy(s, rayToLight)) {
				return true;
			}
		}
		return false;
	}

	private Vec calcDiffuseTerm(Hit minimalIntersection, Ray rayToLight) {
		Vec Kd = minimalIntersection.getSurface().Kd();
		Vec N = minimalIntersection.getNormalToSurface().normalize();
		Vec L = rayToLight.direction().normalize();
		return Kd.mult(N.dot(L));
	}

	private Vec calcSpecularTerm(Hit minimalIntersection, Ray rayFromViewer, Ray rayToLight) {
		double n = minimalIntersection.getSurface().shininess();
		Vec Ks = minimalIntersection.getSurface().Ks();
		Vec V = rayFromViewer.direction().neg().normalize();
		Vec N = minimalIntersection.getNormalToSurface().normalize();
		Vec L_hat = Ops.reflect(rayToLight.direction().neg().normalize(), N);
		double cosineAlpha = V.dot(L_hat);
		return (cosineAlpha < Ops.epsilon) ? new Vec(0) : Ks.mult(Math.pow(cosineAlpha, n));
	}

	private Ray getReflectedRay(Ray ray, Hit minimalIntersection) {
		Point hittingPoint = ray.getHittingPoint(minimalIntersection);
		Vec normalToSurface = minimalIntersection.getNormalToSurface().normalize();
		Vec reflectDirection = Ops.reflect(ray.direction().normalize(), normalToSurface).normalize();
		return new Ray(hittingPoint, reflectDirection);
	}

	private Ray getRefractedRay(Ray ray, Hit minimalIntersection) {
		if (!minimalIntersection.getSurface().isTransparent())
			return null;
		Point hittingPoint = ray.getHittingPoint(minimalIntersection);
		Vec normalToSurface = minimalIntersection.getNormalToSurface().normalize();
		double n1 = minimalIntersection.getSurface().n1(minimalIntersection);
		double n2 = minimalIntersection.getSurface().n2(minimalIntersection);
		Vec refractDirection = Ops.refract(ray.direction().normalize(), normalToSurface, n1, n2).normalize();
		return (refractDirection != null) ? new Ray(hittingPoint, refractDirection) : null;
	}

}
