package edu.cg;

public class SeamCoordinates {

    int originalWidthLoc = 0;
    int widthLoc = 0;
    int heightLoc = 0;

    public SeamCoordinates(int x, int originalX, int y) {
        widthLoc = x;
        originalWidthLoc = originalX;
        heightLoc = y;
    }

    public int getWidth() {
        return this.widthLoc;
    }

    public int getHeight() {
        return this.heightLoc;
    }

    public int getOriginalWidthLoc() {
        return originalWidthLoc;
    }
}
