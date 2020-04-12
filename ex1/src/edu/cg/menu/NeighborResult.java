package edu.cg.menu;

import edu.cg.ImagePixel;

public class NeighborResult implements Comparable<NeighborResult> {

    long cost = 0;
    ImagePixel pixel = null;

    public NeighborResult(ImagePixel pixel, long cost) {
        this.pixel = pixel;
        this.cost = cost;
    }

    public long getCost() {
        return cost;
    }

    public ImagePixel getPixel() {
        return pixel;
    }

    @Override
    public int compareTo(NeighborResult o) {
        if (this.cost > o.getCost())
            return 1;
        else if (this.cost < o.getCost())
            return -1;
        else
            return 0;
    }
}
