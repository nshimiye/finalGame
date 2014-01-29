/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalgame;

/**
 *
 * @author Marcellin Nshimiyimana
 */
import java.awt.Color;
import objectdraw.*;

public class BrickPack {

  Brick[] blocks = new Brick[4];
  private RandomIntGenerator selector = new RandomIntGenerator(0, 4);
  private RandomIntGenerator side = new RandomIntGenerator(0, 1);
  private int rL;
  private int randI = selector.nextValue();

  //constructor
  public BrickPack(Location p, DrawingCanvas c) {
    rL = side.nextValue();
    for (int i = 0; i < blocks.length; i++) {

      blocks[i] = new Brick(p, 25, 25, c);

      switch (randI) {
        case 0:
          //all blocks lined up
          p.translate(0, blocks[i].getHeight());
          break;
        case 1:
          //blocks arranged in box of fours
          p.translate(0, blocks[i].getHeight());
          if (i == 1) {
            p.translate(blocks[i].getWidth(), -(2 * blocks[i].getHeight()));
          }

          break;

        case 2:
          //3 blocks lined up and 1 on side of 3rd
          p.translate(0, blocks[i].getHeight());
          //   left side
          if (i == 2) {
            if (rL == 0) {
              p.translate(-blocks[i].getWidth(), -blocks[i].getHeight());
            } else {
              p.translate(blocks[i].getWidth(), -blocks[i].getHeight());
            }
          }

          break;

        case 3:
          //2 by 2 blocks lined up
          p.translate(0, blocks[i].getHeight());

          if (i == 1) {
            if (rL == 0) {
              p.translate(blocks[i].getWidth(), -blocks[i].getHeight());
            } else {
              p.translate(-blocks[i].getWidth(), -blocks[i].getHeight());

            }
          }
          break;
        case 4:
          //2 by 2 blocks lined up
          p.translate(0, blocks[i].getHeight());

          //alternative
          if (i == 1) {
            if (rL == 0) {
              p.translate(-blocks[i].getWidth(), -(blocks[i].getHeight()));
            } else {
              p.translate(blocks[i].getWidth(), -(blocks[i].getHeight()));
            }
          } else if (i == 2) {
            if (rL == 0) {
              p.translate(blocks[i].getWidth(), 0);
            } else {
              p.translate(-blocks[i].getWidth(), 0);
            }
          }

      }

    }

  }

  // Move blocks by (dx,dy)
  public void move(double dx, double dy) {
    for (int i = 0; i < blocks.length; i++) {
      blocks[i].move(dx, dy);
    }
  }

  public void moveTo(double x, double y) {

    double xval = this.getX(),
            yval = this.getY();

    //move the cube
    this.move(x - xval, y - yval);

  }
  private int countRot = 0;
  //location storage
  private double prevX;

  public void comPosition() {

    if (countRot % 2 == 0) { //even number of rotation

      //store previous location of cubes
      prevX = this.getX();

      this.rotate();

      /* compute better location for cubes
       *
       * if x mod 25 > 12.5 move to right
       * if not move to left
       */
      double xoff =
              (25 * ((int) ((this.getX() % 25) / 12.5))) - (this.getX() % 25),
              yoff =
              (25 * ((int) ((this.getY() % 25) / 12.5))) - (this.getY() % 25);
      this.move(xoff, yoff);

      countRot++;
    } else {

      this.rotate();
      double yoff = this.getY()
              + (25 * ((int) ((this.getY() % 25) / 12.5))) - (this.getY() % 25);
      this.moveTo(prevX, yoff);
      countRot++;
    }
  }
  //get the width

  /**
   *
   * @return
   */
  public double getX() {
    double x = blocks[0].getX();
    for (int i = 1; i < blocks.length; i++) {
      if (x >= blocks[i].getX()) {
        x = blocks[i].getX();
      }
    }
    return x;
  }

  //get the height
  /**
   *
   * @return
   */
  public double getY() {
    double y = blocks[0].getY();
    for (int i = 1; i < blocks.length; i++) {
      if (y >= blocks[i].getY()) {
        y = blocks[i].getY();
      }
    }
    return y;
  }

  //rotate cubes
  public void rotate() {
    double x, y;
    Location cent = center();
    for (int i = 0; i < blocks.length; i++) {

      double offx = cent.getX() - blocks[i].getX(),
              offy = cent.getY() - blocks[i].getY();
      /* rotate a graphics
       * x1 = x cos(-90) - y sin(-90)
       * y1 = x sin(-90) + y cos(-90)
       */
      y = -(cent.getX() - blocks[i].getX());
      x = (cent.getY() - (blocks[i].getY() + blocks[i].getHeight()));

      blocks[i].move(offx + x, offy + y);

    }
  }

  //find center
  public Location center() {
    double x, y, w,
            h = (getSetHeight()) / 2;
    w = (getSetWidth()) / 2;

    x = getX() + w;
    y = getY() + h;
    return new Location(x, y);
  }

  //get one of cubes
  /**
   *
   * @param index
   * @return
   */
  public Brick getCube(int index) {
    return blocks[index];
  }

  ////access all 4 cubes
  /**
   *
   * @return
   */
  public Brick[] getCubes() {
    return blocks;
  }

//  access cube with greater y location
  public Brick getLower() {
    double yLoc = blocks[0].getY();
    Brick lowCu = blocks[0];
    for (int i = 1; i < blocks.length; i++) {

      if (blocks[i].getY() > yLoc) {
        lowCu = blocks[i];
      }
    }
    return lowCu;

  }

//    identify center of a cebe
  public Location getCent(Brick cube) {
    return new Location(cube.getX() + (cube.getWidth() / 2), cube.getY() + (cube.getHeight() / 2));

  }

  //    identify center of a cebe
  public Location getDownCent(Brick cube) {
    return new Location(cube.getX() + (cube.getWidth() / 2), cube.getY() + (cube.getHeight()));

  }

  //    identify upper left of a cebe
  public Location getUpLeft(Brick cube) {
    return new Location(cube.getX(), cube.getY());

  }

  //    identify upper right of a cebe
  public Location getUpRight(Brick cube) {
    return new Location(cube.getX() + (cube.getWidth()), cube.getY());

  }

  //    identify lower left of a cube
  public Location getDownLeft(Brick cube) {
    return new Location(cube.getX(), cube.getY() + (cube.getHeight()));

  }
  //    identify lower right of a cebe

  public Location getDownRight(Brick cube) {
    return new Location(cube.getX() + (cube.getWidth()), cube.getY() + (cube.getHeight()));

  }

//access location of a cube
  /**
   *
   * @param index
   * @return
   * access a location of particular cube
   */
  public Location getLoc(int index) {
    return new Location(blocks[index].getX(), blocks[index].getY());
  }

  //access location of a lowest cube
  /**
   *
   * @return
   */
  public Location getLowLoc() {
    Location lower = new Location(0, 0);
    for (int i = 0; i < blocks.length; i++) {

      //assign lower to cube's location
      if (lower.getY() < blocks[i].getY()) {
        lower = this.getLoc(i);
      }
    }
    return lower;
  }

  //move left or right
  /**
   *
   * @param right
   * @param offset
   */
  public void moveSide(boolean right, int offset) {
    if (right) {
      move(offset, 0);
      if (countRot % 2 != 0) {
        prevX += offset;
      }
    } else {
      move(-offset, 0);
      if (countRot % 2 != 0) {
        prevX -= offset;
      }
    }
  }
  //compute the width of the set of cubes
  /**
   *
   * @return
   */
  public double getSetWidth() {
    double width1 = 25, width2 = 25;
    if (randI == 4) {

      //check if has been rotated
      if (blocks[2].getX() < blocks[0].getX()) {
        width1 = blocks[0].getX() + blocks[0].getWidth() - blocks[2].getX();
      } else {
        width1 = blocks[2].getX() + blocks[2].getWidth() - blocks[0].getX();
      }

      //check if has been rotated
      if (blocks[3].getX() < blocks[0].getX()) {
        width2 = blocks[0].getX() + blocks[0].getWidth() - blocks[3].getX();
      } else {
        width2 = blocks[3].getX() + blocks[3].getWidth() - blocks[0].getX();
      }

      if (Math.abs(width2) > Math.abs(width1)) {
        width1 = width2;
      }

    } else {
      //check if has been rotated
      if (blocks[3].getX() < blocks[0].getX()) {
        width1 = blocks[0].getX() + blocks[0].getWidth() - blocks[3].getX();
      } else {
        width1 = blocks[3].getX() + blocks[3].getWidth() - blocks[0].getX();
      }
    }
    return Math.abs(width1);
  }

  //compute the height of the set of cubes
  /**
   *
   * @return
   */
  public double getSetHeight() {
    double height1 = 25, height2 = 25;
    if (randI == 4) {

      //check if has been rotated
      if (blocks[2].getY() < blocks[0].getY()) {
        height1 = blocks[0].getY() + blocks[0].getHeight() - blocks[2].getY();
      } else {
        height1 = blocks[2].getY() + blocks[2].getHeight() - blocks[0].getY();
      }

      //check if has been rotated
      if (blocks[3].getY() < blocks[0].getY()) {
        height2 = blocks[0].getY() + blocks[0].getHeight() - blocks[3].getY();
      } else {
        height2 = blocks[3].getY() + blocks[3].getHeight() - blocks[0].getY();
      }


      if (Math.abs(height2) > Math.abs(height1)) {
        height1 = height2;
      }

    } else {
      //check if has been rotated
      if (blocks[3].getY() < blocks[0].getY()) {
        height1 = blocks[0].getY() + blocks[0].getHeight() - blocks[3].getY();
      } else {
        height1 = blocks[3].getY() + blocks[3].getHeight() - blocks[0].getY();
      }
    }
    return Math.abs(height1);
  }


  /*
   * hide cubes

   */
  public void hide() {

    for (int i = 0; i < blocks.length; i++) {
      blocks[i].hide();
    }

  }

  /*
   * show cubes

   */
  public void show() {

    for (int i = 0; i < blocks.length; i++) {
      blocks[i].show();
    }

  }

  //delete the object
  public void removefromCanvas() {
    for (int i = 0; i < blocks.length; i++) {
      blocks[i].removeFromCanvas();
    }
  }
}
