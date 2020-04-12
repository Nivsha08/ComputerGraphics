package edu.cg;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ImagePixel implements Comparable<ImagePixel> {

    int widthLoc = 0;
    int heightLoc = 0;
    Color color = new Color(0, 0,0);
    long energy = 0;

    public ImagePixel(int x, int y) {
        widthLoc = x;
        heightLoc = y;
    }

    public ImagePixel(int x, int y, Color pixelColor) {
        this(x, y);
        color = pixelColor;
    }

    public ImagePixel(int x, int y, Color pixelColor, long energy) {
        this(x, y);
        color = pixelColor;
        this.energy = energy;
    }

    public SeamCoordinates toSeamCoordinates(ImagePixel p) {
        return new SeamCoordinates(p.widthLoc, p.heightLoc);
    }

    public static ImagePixel createCopy(ImagePixel src) {
        return new ImagePixel(src.widthLoc, src.heightLoc, src.getColor(), src.getEnergy());
    }

    public Color getColor() {
        return color;
    }

    public int getGrayscaleColor() {
        return (color.getRed() + color.getBlue() + color.getGreen()) / 3;
    }

    public long getEnergy() {
        return this.energy;
    }

    public void setEnergy(long energy) {
        this.energy = energy;
    }

    public ArrayList<SeamCoordinates> traceBackOptimalPath(ImagePixel[][] costMatrix) {
        ArrayList<SeamCoordinates> path = new ArrayList<>();
        ImagePixel current = this;
        while (current.heightLoc > 0) {
            path.add(0, toSeamCoordinates(current));
            ArrayList<ImagePixel> neighbors = current.getTopRowNeighbors(costMatrix);
            current = Collections.min(neighbors);
        }
        path.add(0, toSeamCoordinates(current));
        return path;
    }

    public ArrayList<ImagePixel> getTopRowNeighbors(ImagePixel[][] pixels) {
        ArrayList<ImagePixel> neighbors = new ArrayList<>();
        neighbors.add(pixels[heightLoc - 1][widthLoc]);
        if (widthLoc == 0) {
            neighbors.add(pixels[heightLoc - 1][widthLoc + 1]);
        }
        else if (widthLoc == pixels[0].length - 1) {
            neighbors.add(pixels[heightLoc - 1][widthLoc - 1]);
        }
        else {
            neighbors.addAll(Arrays.asList(pixels[heightLoc - 1][widthLoc - 1], pixels[heightLoc - 1][widthLoc + 1]));
        }
        return neighbors;
    }

    @Override
    public int compareTo(ImagePixel o) {
        if (energy > o.getEnergy())
            return 1;
        else if (energy < o.getEnergy())
            return -1;
        else
            return 0;
    }
}
