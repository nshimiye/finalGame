/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalgame;

/**
 *
 * @author Marcellin Nshimiyimana
 *
 * create a filled rect with random color
 * enclose it with a framed rect
 */
import objectdraw.*;
import java.awt.*;

public class Brick {

    private FilledRoundedRect box;
    private FramedRoundedRect out;
    private RandomIntGenerator g = new RandomIntGenerator(0, 255);

    /**
     *
     * @param upL
     * @param width
     * @param height
     * @param c
     */
    public Brick(Location upL, double width, double height, DrawingCanvas c) {
        box = new FilledRoundedRect(upL, width, height, width / 5, height / 5, c);
        box.setColor(new Color(g.nextValue(), 255, g.nextValue()));
        out = new FramedRoundedRect(upL, width, height, width / 5, height / 5, c);
    }

    public void setColor(Color c) {
        box.setColor(c);
        out.setColor(Color.BLACK);
    }

    /**
     * remove the object from canvas
     */
    public void removeFromCanvas() {
        if (box != null && out != null) {
            box.removeFromCanvas();
            out.removeFromCanvas();
        }
    }
    // Determine whether pt is inside box

    /**
     *
     * @param pt
     * @return
     */
    public boolean contains(Location pt) {
        return out.contains(pt);
    }
    // Move box by (dx,dy)

    /**
     *
     * @param dx
     * @param dy
     */
    public void move(double dx, double dy) {
        box.move(dx, dy);
        out.move(dx, dy);

    }

//  move the specified location
    /**
     *
     * @param x
     * @param y
     */
    public void moveTo(double x, double y) {
        box.moveTo(x, y);
        out.moveTo(x, y);
    }
    //access the y component

    /**
     *
     * @return
     */
    public double getY() {
        return box.getY();
    }

    //access the x component
    /**
     *
     * @return
     * access the x coordinate of the object
     */
    public double getX() {
        return box.getX();
    }

    /**
     *
     * @return
     * access the height of the object
     */
    public double getHeight() {
        return out.getHeight();
    }

    /**
     *
     * @return
     * access the width of the object
     */
    public double getWidth() {
        return out.getWidth();
    }

    //check for hidden
    /**
     *
     * @return
     */
    public boolean isHidden() {
        return box.isHidden() && out.isHidden();
    }

    //hide the object
    /**
     *
     */
    public void hide() {
        box.hide();
        out.hide();
    }

    //hide the object
    /**
     *
     */
    public void show() {
        box.show();
        out.show();
    }
}
