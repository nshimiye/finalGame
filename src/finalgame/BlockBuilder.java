
package finalgame;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.net.URL;
import objectdraw.*;

/**
 *
 * @author Marcellin
 */
public class BlockBuilder implements KeyListener, Runnable {

    //declare a thread
    Thread runner = null;
    private BrickPack pack, nextB;
    private BrickLayer[] blayers;
    private final FilledRect topF;
    private DrawingCanvas can;
    //display results info
    private FramedText points_display, perf_disp, level_display;
    private String perform, sig, player1, resuInfo, infoP, infoSplit[];
    private Text dateP, hiPerf, markDis, levelDis;
    private FramedRect border;
    //sound on or off
    private boolean sound_on = true;
     AudioClip ac;
    //datasender
    private PostDataTest dataSender = new PostDataTest();
    // randomize start loc
     private RandomDoubleGenerator locS = new RandomDoubleGenerator(0, LAY_WIDTH - 50);

    /*
    the delay between successive moves of cube's set
     * number of level at which player is playing
     * delay to remove cubes from full layers
     *
     */
    private int DELAY_TIME = 25, delay_time = 75,
            CONST_TIME = 30, level = 1;

    /* a single move
     * width of a layer
     * location of other important info
     *
     */
    private static final double Y_SPEED = 5, LAY_WIDTH = 400,
            INFO_X = 425, INFO_Y = 85;
    //declare variable to compute results
    private double percent, hiPercent = 0, marks = 0, wid = 140, hei = 120;

    //constructor
    /**
     * 
     * @param nick
     * @param upperLeft
     * @param layers
     * @param sound
     * @param iceWidth
     * @param iceHeight
     * @param c 
     */
    public BlockBuilder(String nick, Location upperLeft, int layers, boolean sound, double iceWidth, double iceHeight, DrawingCanvas c) {
         sound_on = sound;

        //play welcoming sound
        if (sound_on) {

            if (ac != null) {
                ac.stop();
            }
            URL url = this.getClass().getResource("media/beep-04.wav");

             ac = Applet.newAudioClip(url);

            ac.play();
        }
        //get player info
        infoP = "nam!0!0!0!0!0";//dataSender.getPlayerInfo(nick, "fname");
        infoP = infoP.trim();
        infoSplit = infoP.split("!");

        can = c;
        blayers = new BrickLayer[layers];
        topF = new FilledRect(0, 0, LAY_WIDTH, 25, c);
        topF.setColor(Color.white);
        perform = "Perfomance:";
        sig = "%";
        player1 = nick;



        // important info
        Text in = new Text("Welcome " + infoSplit[0], INFO_X, 15, c);
        in.setColor(Color.white);
        in.setFont(new Font("", Font.ROMAN_BASELINE, 15));

        dateP = new Text("Here is your Records!! ", INFO_X + 30, 40, c);
        dateP.setColor(new Color(34, 56, 56));
        dateP.setFont(new Font("", Font.ITALIC, 15));

        dateP = new Text("points: " + infoSplit[1], INFO_X, 60, c);
        dateP.setColor(Color.white);
        dateP.setFont(new Font("", Font.PLAIN, 15));

        hiPerf = new Text("Highest Performance: " + infoSplit[2] + " %", INFO_X, 80, c);
        hiPerf.setColor(Color.white);
        hiPerf.setFont(new Font("", Font.PLAIN, 15));

        markDis = new Text("Highest Level reached: " + infoSplit[3], INFO_X, 100, c);
        markDis.setColor(Color.white);
        markDis.setFont(new Font("", Font.PLAIN, 15));

        levelDis = new Text("Last time played: " + infoSplit[4], INFO_X, 120, c);
        levelDis.setColor(Color.white);
        levelDis.setFont(new Font("", Font.PLAIN, 15));


        in = new Text("Click Rotate the object!! ", INFO_X + 20 + wid, INFO_Y + 60, c);
//        in.setColor(Color.white);
        in.setFont(new Font("", Font.BOLD, 15));

        in = new Text("Use up/Down-left/right Keys ", INFO_X + 20 + wid, INFO_Y + 90, c);
//        in.setColor(Color.white);
        in.setFont(new Font("", Font.BOLD, 15));

        in = new Text("to rotate(Up) move sides", INFO_X + 20 + wid, INFO_Y + 120, c);
        in.setFont(new Font("", Font.BOLD, 15));

        in = new Text("And Space key to position ", INFO_X + 20 + wid, INFO_Y + 150, c);
        in.setFont(new Font("", Font.BOLD, 15));

        in = new Text("the object right away!! ", INFO_X + 20 + wid, INFO_Y + 180, c);
        in.setFont(new Font("", Font.BOLD, 15));

        in = new Text("Enjoy the Game!!! ", INFO_X + 20 + wid, INFO_Y + 230, c);
        in.setColor(new Color(150, 200, 50));
        in.setFont(new Font("", Font.ITALIC, 20));

        for (int row = 0; row < blayers.length; row++) {
            blayers[row] = new BrickLayer(0, (((row + 1) * 25)), 16, c);
//            FramedRect framedRect = new FramedRect(0, (((row + 1) * 25)), LAY_WIDTH, 25, c);
        }

        // border
        border = new FramedRect(0, 0, LAY_WIDTH, blayers[blayers.length - 1].getLayLoc().getY() + 25, c);
        border.setColor(Color.red);
        FramedText framedText = new FramedText(" Next Object!!", INFO_X, INFO_Y + 60, wid, hei, c);

        hei = 35;
        perf_disp = new FramedText(perform + "not yet!!!", INFO_X, INFO_Y + 40 + 120 + 40, wid, hei, c);
//            new FramedRect(INFO_X, INFO_Y + 40 + 120 + 40, wid, hei, c);
//    info1 = new Text(perform + "not yet!!!", new Location(INFO_X + 5, INFO_Y + 40 + 120 + 40 + 5), c);

        points_display = new FramedText("points: " + String.valueOf(marks), INFO_X, (INFO_Y + 40 + 120 + 40) + hei + 30, wid, hei, c);

        level_display = new FramedText("level: 1", INFO_X, (INFO_Y + 40 + 120 + 40) + 2 * (hei + 30), wid, hei, c);

        pack = new BrickPack(new Location(200, 0), c);


        this.start();


    }

    //set up keystroke listener
    /**
     *
     *
     */
    public void addList(Component win, DrawingCanvas c) {
        c.addKeyListener(this);
        win.addKeyListener(this);
        win.setFocusable(true);

    }

    public final void start() {

        // user visits the page, create a new thread
        if (runner == null) {

            runner = new Thread(this);
            runner.start();


        }
    }

    public void stop() {

        // user leaves the page, stop the thread
        if (runner != null && runner.isAlive()) {
            //runner.interrupt();
            runner.stop();
        }

        runner = null;
    }
    private boolean noPause = true;

    public final void pause() {

        // user visits the page, create a new thread
        if (runner != null && runner.isAlive()) {

            noPause = false;

        }
    }

    public final void contin() {

        // user visits the page, create a new thread
        if (runner != null && runner.isAlive()) {

            noPause = true;
        }
    }

    public void setSound(Boolean sons){
      sound_on = sons;
    }

    public void run() {

        boolean stop = false;
        while (runner != null) {

            if (blayers[0].elts() <= 0 && !stop) {
                topF.sendToFront();

                //next object
                nextB = new BrickPack(new Location(INFO_X + 50, INFO_Y + 70), can);

                while (this.canMove()) {

                    if (noPause) {
                        pack.move(0, 5);
                    }
                    try {

                        Thread.sleep(delay_time);

                    } catch (InterruptedException e) {
                        // do nothing
                    }

                    level = (int) (85 - delay_time) / 10;
                    level_display.setText("level: " + String.valueOf(level));

                    //congratulate the player
                    if (level >= 6 && sound_on) {
                        //play welcoming sound
                        if (ac != null) {
                            ac.stop();
                        }
                        URL url = this.getClass().getResource("media/congl1.wav");

                        ac = Applet.newAudioClip(url);

                        ac.play();
                        try {

                        Thread.sleep(delay_time);

                    } catch (InterruptedException e) {
                        // do nothing
                    }
                        ac.stop();

                    }
                }

                if (pack.getY() < 0) {
                    stop = true;
                } else {
                    //play welcoming sound
                    if (sound_on) {
                        if (ac != null) {
                            ac.stop();
                        }
                        URL url = this.getClass().getResource("media/beep-10.wav");

                         ac = Applet.newAudioClip(url);

                        ac.play();
                    }

                }


                // index for layer arround cubes
                int stInd = ((int) pack.getY() / 25) - 1,
                        endInd = ((int) pack.getLowLoc().getY() / 25) - 1;


                for (int i = endInd; i >= stInd; i--) {  // specified layer arround cobes

                    if (i >= 0) { //exit layer that accepts current cube

                        if (i < blayers.length) {

                            for (int j = 0; j < pack.getCubes().length; j++) {  //get cubes each

                                if (pack.getCube(j).getY() == blayers[i].getLayLoc().getY()) {  // insert cube in the right layer

                                    // put the cube to the location of the layer
                                    blayers[i].addCube(pack.getCube(j));

                                }
                            }
                        }


                    } else {
                        stop = true;
                        break;
                    }
                }

                // compute the performance
                double curCubes = 0, total = 0;
                for (int j = 0; j < blayers.length; j++) {
                    if (!blayers[j].isEmpty()) {
                        curCubes += blayers[j].elts();
                        total += blayers[j].size();
                    }
                }
                percent = 100 * (curCubes / total);



                perf_disp.setText(perform + String.valueOf(String.format("%.2f", percent)) + sig);

                if (hiPercent < percent) {
                    hiPercent = percent;

                }

//            ===========================================================
                topF.sendToFront();

                //remove the cubes from full layers
                boolean remCube = true;
                for (int n = blayers.length - 1; n >= 0; n--) {

                    remCube = true;
                    do {

                        if (!blayers[n].isEmpty()) {// layer has some elts in it

                            if (blayers[n].elts() == blayers[n].size()) { //full layer

                                for (int j = 0; j < blayers[n].size(); j++) {//remove cubes
                                    Location mid = new Location(blayers[n].getCubes()[j].getX() + 5, blayers[n].getCubes()[j].getY() + 5);
                                    this.hit(mid);
                                    //play welcoming sound
                                    if (sound_on) {
                                        if (ac != null) {
                                            ac.stop();
                                        }
                                        URL url = this.getClass().getResource("media/hittedM.wav");

                                         ac = Applet.newAudioClip(url);

                                        ac.play();
                                    }
                                    try {

                                        Thread.sleep(CONST_TIME);

                                    } catch (InterruptedException e) {
                                        // do nothing
                                    }
                                    marks += j * 5;
                                }
                                points_display.setText("points: " + String.valueOf(marks));

                                levelCount++;

                                //change time delay depending on how many hitted layers
                                if (((int) (level * 3 / 2)) < levelCount) {
                                    delay_time -= 10;
                                    levelCount = 0;
                                }

                                //restore cubes depending on position
                                int k = n;//clear layers
                                do {
                                    if (k > 0 && blayers[k].isEmpty()) {


                                        if (!blayers[k - 1].isEmpty()) {
                                            for (int m = 0; m < blayers[k - 1].elts(); m++) {//move cubes to actual layer
                                                blayers[k].addCube(blayers[k - 1].getCubes()[m]);

                                            }
                                            blayers[k - 1].removeAll();
            
                                        } else {

//                                            remCube = false;
                                            break;
                                        }
                                    } else {
                                        System.out.printf("problem occured!!!!!!");
                                        //remCube = false;
                                        break;

                                    }
                                    k--;
                                } while (k >= 0);

                            } else {

                                remCube = false;
                            }

                        } else {

                            remCube = false;
                        }
                    } while (remCube);
                }

//            ============================================================



                if (blayers[0].elts() <= 0 && !stop) {
                    pack = nextB;
//                    pack.moveTo(225, -75);
                    pack.moveTo(25 * ((int) (locS.nextValue() / 25)), blayers[0].getLayLoc().getY() - pack.getSetHeight());
                }


            } else {
                System.out.printf("no layer to store\n");
                int startx = 0;

                int count = 0;
                do {
                   
//submit records
                    resuInfo = "scored"; //dataSender.submResult(player1, marks, Double.valueOf(String.format("%.2f",hiPercent)), level);

                    System.out.printf("\n==%s}}}}", resuInfo);
                    // fill in the upper part
                    for (int k = 0; k < blayers[0].size(); k++) {  // specified layer arround cobes

                         if (sound_on ) {
                        //play closing sound
                          if(ac != null){
                       ac.stop();}
                        URL url = this.getClass().getResource("media/tos-computer-05.wav");

                        ac = Applet.newAudioClip(url);
                        ac.play();
                    }
                         
                        //check if half of the main area is filled with cubes
                        if (count == 0) {  // first half not yet filled
                            startx = 0;
                            for (int j = k; j >= 0; j--) {  // specified layer arround cobes
                                new Brick(new Location((j * 25), (startx * 25) + 25), 25, 25, can);

                                startx++;
                                try {

                                    Thread.sleep(DELAY_TIME);

                                } catch (InterruptedException e) {
                                    // do nothing
                                }
                            }
                        } else {  // first half filled, work on second half
                            startx = k;
                            for (int j = blayers.length - 1; j >= k; j--) {  // specified layer arround cobes
//
                                if ((j * 25) < topF.getWidth()) {
                                    new Brick(new Location((j * 25), (startx * 25) + 25), 25, 25, can);

                                }
                                startx++;
                                try {

                                    Thread.sleep(DELAY_TIME);

                                } catch (InterruptedException e) {
                                    // do nothing
                                }
                            }
                        }


                    }
                    count++;
                } while (count < 2);

                Text in = new Text("Game Over!!! ", 20, can.getHeight() - 250, can);
                in.setColor(new Color(150, 50, 50));
                in.setFont(new Font("", Font.ITALIC, 20));

                in = new Text("Click \"stop Playing!!\" Button to restart playing again ", 20, can.getHeight() - 40, can);
                in.setColor(new Color(150, 50, 50));
                in.setFont(new Font("", Font.ITALIC, 20));

                this.stop();
//                    postin.submResult(player1, getResult()[0], getResult()[1], getResult()[2]);


                break;

            }

        }

    }

    //rotate on mouse click
    public void mouseResp(Location pt) {
        if (noPause) {
            //rotate the current set of cubes
            if (this.canRotate() && border.contains(pt)) {
                pack.comPosition();

                //play welcoming sound
                if (sound_on) {
                    if (ac != null) {
                        ac.stop();
                    }
                    URL url = this.getClass().getResource("media/rotate2.wav");

                    ac = Applet.newAudioClip(url);

                    ac.play();
                }

            }
        }
    }
    //keep track of hitted layers
    private int levelCount = 0;

    public void setLevelC() {
        levelCount = 0;
    }

    public void setdelay(int i) {
        delay_time = i;
    }

    //results of the game
    public double [] getResult() {
        double[] res = new double[3];
        res[0] = marks;
        res[1] = Double.valueOf(String.format("%.2f", hiPercent));
        res[2] = level;
        return res;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent ke) {

        if (noPause) {
            if (blayers[0].elts() == 0) {
                if (ke.getKeyCode() == KeyEvent.VK_LEFT) {

                    //move left the current set of cubes
                    if (canMoveS(false) && 0 < pack.getX()) {
                        pack.moveSide(false, 25);
                    }

                } else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {

                    //move right the current set
                    if (canMoveS(true) && (pack.getX() + pack.getSetWidth()) < (topF.getX() + topF.getWidth())) {
                        pack.moveSide(true, 25);
                    }

                } else if (ke.getKeyCode() == KeyEvent.VK_UP) {

                    //rotate the current set of cubes
                    if (this.canRotate()) {
                        pack.comPosition();

                        //play welcoming sound
                        if (sound_on) {
                            if (ac != null) {
                                ac.stop();
                            }

                            URL url = this.getClass().getResource("media/rotate2.wav");
//
                            ac = Applet.newAudioClip(url);
                            ac.play();
                        }
                    }

                } else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                    //
                    if (this.canMove()) {

                        pack.move(0, 5);
                    }
                } else if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                    //
                    while (this.canMove()) {
                        pack.move(0, 5);

                    }

                } else if (ke.getKeyCode() == KeyEvent.VK_CONTROL) {
                    //
                    if (ke.getKeyCode() == KeyEvent.VK_A) {
                        // pack.comPosition();
                        //
                    } else if (ke.getKeyCode() == KeyEvent.VK_D) {
                        //
                    }
//
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    /*determine if it can rotate
     *
     */
    public boolean canRotate() {
        boolean rotat = true;

        double bigger = pack.getSetHeight(),
                regUpx = 0, regUpy = 0,
                regDx = 0, regDy = 0;
        if (pack.getSetHeight() < pack.getSetWidth()) {// get the bigger length between width and height
            bigger = pack.getSetWidth();
        }

        //location of rect that contain current object
        regUpx = pack.center().getX() - (bigger / 2);

        regUpy = pack.center().getY() - (bigger / 2);

        //end location of that rect
        regDx = pack.center().getX() + (bigger / 2);

        regDy = pack.center().getY() + (bigger / 2);


        //get index of layer that has same y as our region
        int layInY = (int) ((regUpy / 25) - 1),
                endL = (int) ((regDy) / 25);
        double layLoc = ((layInY + 1) * 25) + (15);
        if (0 <= layInY && endL < blayers.length && 0 <= regUpx && regDx <= (topF.getX() + topF.getWidth())) {   //do not know why it sometimes stops when "0 <= layInY" is put inside first for loop

            for (int i = layInY; i <= endL; i++) { //layers in a region to check

                for (double j = regUpx + 18; j < regDx; j += 25) { //width of region to check

                    if (blayers[i].isOccupied(j, layLoc)) {  // layer stores cube with same
                        rotat = false;
                        break;
                    }
                }

                if (!rotat) { // occupation found
                    break;
                }
                layLoc += 25;
            }
        } else {
            rotat = false;

        }
        return rotat;

    }

    /*
     *
     * permission to move down
     */
    public boolean canMove() {

        //check if there is cube with same Y and y+25 as saved cube
        boolean allow = true;

        //boundary for layers to check
        int layIn = (int) ((pack.getY() / 25) - 1),
                endIn = (int) ((pack.getY() + pack.getSetHeight()) / 25);

        if (pack.getUpLeft(pack.getLower()).getY() < blayers[blayers.length - 1].getLayLoc().getY()) {

            for (int k = layIn; k < blayers.length && k <= endIn; k++) { // for layers in region that contains current object

                if (0 <= k) { // increment k untill its greater than -1

                    for (int i = 0; i < pack.getCubes().length; i++) {  // check all 4 cubes ???? not efficient

                        if (blayers[k].isOccupied(pack.getDownCent(pack.getCube(i)).getX(), pack.getDownCent(pack.getCube(i)).getY())) {
                            allow = false;
                            break;
                        }
                    }
                }

                if (!allow) {
                    // matching (x and y+25) boxes are found
                    break;
                }
            }
        } else {
            allow = false;

        }

        return allow;

    }
    //fix moveside with respcet to layer
    //to be changed??????????????????????????
    //empty or not empty

    public boolean canMoveS(boolean right) {

        boolean moveSides = true;
        int lay = 0;

        for (int k = 0; k < pack.getCubes().length; k++) { // for current cubes
            //make (0-25)  (26-50) (51-75) ... corresppond to  0 1 2 ...

            if (pack.getCube(k).getY() % 25 == 0) {  // current cube is exactly in one layer

                lay = (int) ((pack.getCube(k).getY() - 1) / 25); // layer that contain cube k

                if (lay >= 0 && lay < blayers.length) {
                    for (int i = 0; i < blayers[lay].elts(); i++) {  // stored cubes
                        // need same x+25 and x in order to stop moving to right
                        if (right) {
                            if (pack.getCube(k).getX()
                                    + pack.getCube(k).getWidth()
                                    == blayers[lay].getCubes()[i].getX()) {  //check if the right side is empty
                                moveSides = false;
                                break;
                            }
                        } else {
                            // need same x(current cube) and x-25(stored cube) in order to stop moving to left
                            if (pack.getCube(k).getX()
                                    == blayers[lay].getCubes()[i].getX()
                                    + pack.getCube(k).getWidth()) {  //check if the left side is empty
                                moveSides = false;
                                break;
                            }
                        }
                    }
                }
                if (!moveSides) {  // stop for (int n = lay - 1; loop
                    // matching (x and y+25) boxes are found
                    break;
                }
            } else {  //current cube is between two layer's regions

                // get highest level in which the current cubes touches
                lay = (int) (pack.getCube(k).getY() / 25);

                for (int n = lay - 1; n <= lay; n++) {  // layer arround cube k

                    if (n >= 0) {
                        for (int i = 0; i < blayers[n].elts(); i++) {  // stored cubes
                            // need same x+25 and x in order to stop moving to right
                            if (right) {
                                if (pack.getCube(k).getX()
                                        + pack.getCube(k).getWidth()
                                        == blayers[n].getCubes()[i].getX()) {  //check if the right side is empty
                                    moveSides = false;
                                    break;
                                }
                            } else {
                                // need same x(current cube) and x-25(stored cube) in order to stop moving to left
                                if (pack.getCube(k).getX()
                                        == blayers[n].getCubes()[i].getX()
                                        + pack.getCube(k).getWidth()) {  //check if the left side is empty
                                    moveSides = false;
                                    break;
                                }
                            }
                        }
                    }
                    if (!moveSides) {  // stop for (int n = lay - 1; loop
                        // matching (x and y+25) boxes are found
                        break;
                    }
                }
            }
            if (!moveSides) { //stop for (int k = 0; k < boxs.getCubes()
                // matching (x and y+25) boxes are found
                break;
            }
        }

        return moveSides;
    }

    /**
     *
     * remove an object (cube) that contains pt
     */
    public void hit(Location pt) {
        for (int row = blayers.length - 1; row
                >= 0; row--) {
            if (blayers[row].isHit(pt)) {

                pt = new Location(0, 0); // reset Location

                if (blayers[row].isEmpty()) {
                    for (int row2 = row - 1; row2
                            >= 0; row2--) {
                        if (blayers[row2] != null) {
                            blayers[row2].fall();

                        }

                    }

                }
            }

        }

    }
}
