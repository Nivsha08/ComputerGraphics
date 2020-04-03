package edu.cg;

import java.awt.*;
import java.util.ArrayList;

public class ImagePixel {

    int widthLoc = 0;
    int heightLoc = 0;
    Color color = new Color(0, 0,0);
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
