package edu.cg;

import edu.cg.menu.NeighborResult;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class ImagePixel implements Comparable<ImagePixel> {

    public static enum NeighborPositions {
        TOP_LEFT,
        ABOVE,
        TOP_RIGHT
    }

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

    public static int toGreyscale(int rgb) {
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = rgb & 0xff;
        return (r + g + b) / 3;
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

    public int getGreyscale() {
        return (color.getRed() + color.getGreen() + color.getBlue()) / 3;
    };

    public long getEnergy() {
        return this.energy;
    }

    public void setEnergy(long energy) {
        this.energy = energy;
    }

    public ArrayList<SeamCoordinates> traceBackOptimalPath(ImagePixel[][] costMatrix, int[][] greyscaleArray) {
        ArrayList<SeamCoordinates> path = new ArrayList<>();
        ImagePixel current = this;
        while (current.heightLoc > 0) {
            path.add(0, toSeamCoordinates(current));
            NeighborResult nextSeamStep = current.getMinimalSeamStepCost(costMatrix, greyscaleArray);
            current = nextSeamStep.getPixel();
        }
        path.add(0, toSeamCoordinates(current));
        return path;
    }

    public NeighborResult getMinimalSeamStepCost(ImagePixel[][] costMatrix, int[][] greyscaleArray) {
        Map<NeighborPositions, ImagePixel> neighbors = this.getTopRowNeighbors(costMatrix);
        long aboveCost = neighbors.get(NeighborPositions.ABOVE).getEnergy() + calcAboveSeamCost(greyscaleArray);
        long leftCost = (neighbors.containsKey(NeighborPositions.TOP_LEFT)) ?
             neighbors.get(NeighborPositions.TOP_LEFT).getEnergy() + calcLeftSeamCost(greyscaleArray) : Long.MAX_VALUE;
        long rightCost = (neighbors.containsKey(NeighborPositions.TOP_RIGHT)) ?
                neighbors.get(NeighborPositions.TOP_RIGHT).getEnergy() + calcRightSeamCost(greyscaleArray) : Long.MAX_VALUE;
        NeighborResult topLeft = new NeighborResult(neighbors.get(NeighborPositions.TOP_LEFT), leftCost);
        NeighborResult above = new NeighborResult(neighbors.get(NeighborPositions.ABOVE), aboveCost);
        NeighborResult topRight = new NeighborResult(neighbors.get(NeighborPositions.TOP_RIGHT), rightCost);
        return Collections.min(Arrays.asList(topLeft, above, topRight));
    }

    public Map<NeighborPositions, ImagePixel> getTopRowNeighbors(ImagePixel[][] table) {
        Map<NeighborPositions, ImagePixel> neighbors = new HashMap<>();
        neighbors.put(NeighborPositions.ABOVE, table[heightLoc - 1][widthLoc]);
        if (widthLoc == 0) {
            neighbors.put(NeighborPositions.TOP_RIGHT, table[heightLoc - 1][widthLoc + 1]);
        }
        else if (widthLoc == table[0].length - 1) {
            neighbors.put(NeighborPositions.TOP_LEFT, table[heightLoc - 1][widthLoc - 1]);
        }
        else {
            neighbors.put(NeighborPositions.TOP_RIGHT, table[heightLoc - 1][widthLoc + 1]);
            neighbors.put(NeighborPositions.TOP_LEFT, table[heightLoc - 1][widthLoc - 1]);
        }
        return neighbors;
    }

    private int calcLeftSeamCost(int[][] greyscaleArray) {
        if (widthLoc == 0)
            return Integer.MAX_VALUE;
        else if (widthLoc == greyscaleArray[0].length - 1) {
            return getDiff(widthLoc, heightLoc, widthLoc - 1, heightLoc, greyscaleArray) +
                    getDiff(widthLoc, heightLoc - 1, widthLoc, heightLoc - 1, greyscaleArray);
        }
        else {
            return getDiff(widthLoc + 1, heightLoc, widthLoc - 1, heightLoc, greyscaleArray) +
                    getDiff(widthLoc, heightLoc - 1, widthLoc + 1, heightLoc - 1, greyscaleArray);
        }
    }

    private int calcRightSeamCost(int[][] greyscaleArray) {
        if (widthLoc == 0)
            return getDiff(widthLoc, heightLoc, widthLoc + 1, heightLoc, greyscaleArray) +
                    getDiff(widthLoc, heightLoc - 1, widthLoc + 1, heightLoc, greyscaleArray);
        else if (widthLoc == greyscaleArray[0].length - 1)
            return Integer.MAX_VALUE;
        else {
            return getDiff(widthLoc + 1, heightLoc, widthLoc - 1, heightLoc, greyscaleArray) +
                    getDiff(widthLoc, heightLoc - 1, widthLoc + 1, heightLoc, greyscaleArray);
        }
    }

    private int calcAboveSeamCost(int[][] greyscaleArray) {
        if (widthLoc == 0)
            return 0;
        else if (widthLoc == greyscaleArray[0].length - 1)
            return 0;
        else
            return getDiff(widthLoc - 1, heightLoc, widthLoc + 1, heightLoc, greyscaleArray);
    }

    private int getDiff(int x1, int y1, int x2, int y2, int[][] greyscaleArray) {
        return Math.abs(greyscaleArray[y1][x1] - greyscaleArray[y2][x2]);
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
