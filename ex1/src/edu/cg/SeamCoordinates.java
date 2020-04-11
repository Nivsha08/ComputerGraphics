package edu.cg;

public class SeamCoordinates {

    int widthLoc = 0;
    int heightLoc = 0;

    public SeamCoordinates(int x, int y) {
        widthLoc = x;
        heightLoc = y;
    }

    public int getWidth() { return this.widthLoc; }
    public int getHeight() { return this.heightLoc; }
}
