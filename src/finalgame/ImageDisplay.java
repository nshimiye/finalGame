/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package finalgame;

/**
 *
 * @author Marcellin
 *
 * code from java eventful
 * http://eventfuljava.cs.williams.edu/sampleProgs/ch17/textbook/FrameMaker/FrameMaker-protected-methods/FrameMaker.html
 */
import objectdraw.*;
import java.awt.*;


// A FramedDisplay is a composite graphic intended to serve
// as a frame in which a program can display various forms
// of information.
public class ImageDisplay {
    private VisibleImage vIm;
//    private FilledRoundedRect border;        // The border

    // Create the display area's background and border
    public ImageDisplay( Image imgF, double x, double y,
            double width, double height,
            DrawingCanvas canvas) {


        vIm = new VisibleImage(imgF, x, y, width, height, canvas);

    }

        // update image
    public void setImage(Image img) {
        vIm.setImage(img);
    }

         // remove image image
    public void removeFromCanvas() {
        vIm.removeFromCanvas();
    }

    // Return the x coord of the left edge of display area
    protected double displayLeft() {
        return vIm.getX();
    }

    // Return the y coord of the top edge of display area
    protected double displayTop() {
        return vIm.getY();
    }

    // Return the width of the display area
    protected double displayWidth() {
        return vIm.getWidth();
    }

    // Return the height of the display area
    protected double displayHeight() {
        return vIm.getHeight();
    }

    public boolean contains(Location pt){

        return vIm.contains(pt);

    }
}
