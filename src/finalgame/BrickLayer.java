/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalgame;

import objectdraw.*;

/**
 *
 * @author Marcellin
 */
public class BrickLayer {

    private double dy;
    private Brick[] iceB;
    private final Location bLoc;
    /**
     *
     */
    /**
     *
     */
    public int size, index;

    /**
     *
     * @param layeX
     * @param layerY
     * @param blockHt
     * @param blockWidth
     * @param numBlocks
     * @param c
     */
    public BrickLayer(double layeX, double layerY, int numBlocks, DrawingCanvas c) {
        iceB = new Brick[numBlocks];
        bLoc = new Location(layeX, layerY);
        index = -1;
    }

    //get the size of the layer
    /**
     *
     * @return
     */
    public int size() {
        return iceB.length;
    }

    // get number of elements
    public int elts() {
        return index + 1;
    }
    //add a block to the array

    /**
     *
     * @param cube
     * @return
     */
    public boolean addCube(Brick cube) {
        index++;
        iceB[index] = cube;
        System.out.printf("yes= ===");
        return true;
    }

    //clear the layer from objects
    public boolean removeAll() {
        boolean ok = false;
        for (int i = 0; i < this.elts(); i++) {

            if (iceB[i] != null) { //remove from leyaer if it exist
                iceB[i] = null;
            }
        }
        if (this.isEmpty()) {
            System.out.printf("yep works!!!!!!");
            ok = true;
            index = -1;
        } else {
            System.out.printf("nop not working!!!!!!");
        }
        return ok;
    }

    //access all cubes
    /**
     *
     * @return
     */
    public Brick[] getCubes() {

        return iceB;
    }

    //access the position of the layer
    /**
     *
     * @return
     */
    public Location getLayLoc() {
        return bLoc;
    }

    /**
     *
     * move cubes down
     */
    public void fall() {
        for (int col = 0; col < iceB.length; col++) {
            if (iceB[col] != null) {
                dy = iceB[col].getHeight();
                iceB[col].move(0, dy);
            }
        }
    }

    /**
     *
     * @param pt
     * @return
     */
    public boolean isHit(Location pt) {
        boolean contBox = false;
        for (int col = 0; col < iceB.length; col++) {

            if (iceB[col] != null) {
                if (iceB[col].contains(pt)) {
                    contBox = true;

                    iceB[col].removeFromCanvas();
                    iceB[col] = null;

                    //modified????????????????????
                    index--;
//                    System.out.printf("<<<%d>>>", index);

                }
            }
        }
        return contBox;
    }

//    check if a particular space(x loc) is occupied within a layer
    public boolean isOccupied(double xLoc, double yLoc) {
        boolean occupied = false;


        for (int i = 0; i < this.elts(); i++) {

            if (iceB[i].getX() < xLoc && xLoc < (iceB[i].getX() + iceB[i].getWidth())
                    && (iceB[i].getY() <= yLoc && yLoc <= (iceB[i].getY() + iceB[i].getHeight()))) {
                occupied = true;
                break;
            }
        }

        return occupied;
    }

    /**
     *
     * @return
     * the status of layer
     */
    public boolean isEmpty() {
        boolean empt = true;
        for (int col = 0; col < iceB.length; col++) {
            if (iceB[col] != null) {
                empt = false;
            }
        }
        return empt;
    }
}
