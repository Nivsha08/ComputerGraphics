package edu.cg;

import com.sun.nio.sctp.SendFailedNotification;

import java.awt.*;
import java.util.ArrayList;

public class ImagePixel {

    int widthLoc = 0;
    int heightLoc = 0;
    Color color = new Color(0, 0,0);
    int energy = 0;
    ArrayList<ImagePixel> optimalCumulativePath = null;
    long cumulativePathTotalCost = 0;

    public ImagePixel(int x, int y) {
        widthLoc = x;
        heightLoc = y;
    }

    public ImagePixel(int x, int y, Color pixelColor) {
        widthLoc = x;
        heightLoc = y;
        color = pixelColor;
    }

    public ImagePixel(int x, int y, Color pixelColor, int energy) {
        widthLoc = x;
        heightLoc = y;
        color = pixelColor;
        this.energy = energy;
    }

    public int getGrayscaleColor() {
        return (color.getRed() + color.getBlue() + color.getGreen()) / 3;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setOptimalPath(ArrayList<ImagePixel> optimalPath) {
        this.optimalCumulativePath = optimalPath;
        this.cumulativePathTotalCost = optimalPath.stream()
                .map(pixel -> pixel.getPathCost())
                .reduce(0L, (a, b) -> a + b);
    }

    public long getPathCost() {
        return this.cumulativePathTotalCost;
    }

}
