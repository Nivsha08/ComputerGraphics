package edu.cg;

import java.awt.*;
import java.util.ArrayList;

public class ImagePixel implements Comparable<ImagePixel> {

    int widthLoc = 0;
    int heightLoc = 0;
    Color color = new Color(0, 0,0);
    long energy = 0;
    ArrayList<ImagePixel> optimalCumulativePath = null;

    public ImagePixel(int x, int y) {
        widthLoc = x;
        heightLoc = y;
        optimalCumulativePath = new ArrayList<>();
        optimalCumulativePath.add(this);
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

    public void updatePath(ImagePixel minimalNeighbor) {
        this.optimalCumulativePath.addAll(0, minimalNeighbor.getOptimalPath());
    }

    public ArrayList<ImagePixel> getOptimalPath() {
        return this.optimalCumulativePath;
    };

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
