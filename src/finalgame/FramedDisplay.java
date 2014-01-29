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
public class FramedDisplay {

    private static final int BORDER = 3;    // Border thickness
    private static final int ROUNDNESS = 5; // Corner roundess
    // Colors used for the frame
    private static final Color BORDERCOLOR = Color.gray;
    private static final Color HIGHLIGHTCOLOR = Color.red;
    private FilledRoundedRect body;            // The background
    private FilledRoundedRect border;        // The border

    // Create the display area's background and border
    public FramedDisplay(double x, double y,
            double width, double height,
            DrawingCanvas canvas) {

        border = new FilledRoundedRect(x, y, width, height,
                ROUNDNESS, ROUNDNESS,
                canvas);
        body = new FilledRoundedRect(x + BORDER, y + BORDER,
                width - 2 * BORDER, height - 2 * BORDER,
                ROUNDNESS, ROUNDNESS,
                canvas);
        border.setColor(BORDERCOLOR);
    }

    // Change the border's color to make it stand out
    public void highlight() {
        border.setColor(HIGHLIGHTCOLOR);
    }

    // Restore the standard border color
    public void unHighlight() {
        border.setColor(BORDERCOLOR);
    }

    // Return the x coord of the left edge of display area
    protected double displayLeft() {
        return body.getX();
    }

    // Return the y coord of the top edge of display area
    protected double displayTop() {
        return body.getY();
    }

    // Return the width of the display area
    protected double displayWidth() {
        return body.getWidth();
    }

    // Return the height of the display area
    protected double displayHeight() {
        return body.getHeight();
    }

    public boolean contains(Location pt){

        return border.contains(pt);

    }
}
