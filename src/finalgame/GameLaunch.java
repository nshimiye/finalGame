/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalgame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.*;
import objectdraw.*;


/**
 *
 * @author Marcellin
 */
public class GameLaunch extends JApplet implements ActionListener, ItemListener, KeyListener, MouseListener {

    //gui component
    private Button nPlayer, cPlayer, startG, stopB, submit, submitR;
    private JTextField nickn1, nickn2, fn, ln;
    private Panel bPanel = new Panel(), welc = new Panel(), soundS,
            subscP, newP = new Panel(), currentP = new Panel();
    private BlockBuilder bloc;
    private PostDataTest dataSender = new PostDataTest();
    private JRadioButton sp1_but, sp2_but, sp3_but, soundOff, soundOn;
    private ButtonGroup genders, speeds, soundGr;
    private static String speed1 = "Slow",
            speed2 = "Middle",
            speed3 = "Fast",
            gMale = "Mr.",
            gFemale = "Mrs.",
            subInfo = " ",
            player = "",
            status = "";
//    private Image imFile;
//    private ImageDisplay imDspl;
    private Text in;
    //checkboxes
    private JCheckBox status1;
    private JCheckBox status2;
    private JCheckBox status3;
    //deal with sound
    private boolean soundValue = true;
    private JFrame frame = new JFrame("Senyura Game");
    private Container contentPane = getContentPane();
    private JDrawingCanvas canvas = new JDrawingCanvas();

    public static void main(String[] args) {

        new GameLaunch().init();
    }

    /**
     * Initialization method that will be called after the applet is loaded
     * into the browser.
     */
    @Override
    public void init() {
        frame.setSize(810, 580);
//       this.setBackground(new Color(0, 150, 255));
        //backGround color
        new FilledRect(0, 0, getWidth(), getHeight(), canvas).setColor(new Color(0, 150, 255));

//       =================================gui============================

        nickn1 = new JTextField("user");
        nickn1.setForeground(new Color(88, 71, 245));
        nickn1.setFont(new Font("Arial", Font.BOLD, 20));

        nickn2 = new JTextField("ex:FisrtL");
        nickn2.setForeground(new Color(88, 71, 245));
        nickn2.setFont(new Font("Arial", Font.BOLD, 15));


        fn = new JTextField("First name");
        fn.setForeground(new Color(88, 71, 245));
        fn.setFont(new Font("Arial", Font.BOLD, 15));


        ln = new JTextField("Last name");
        ln.setForeground(new Color(88, 71, 245));
        ln.setFont(new Font("Arial", Font.BOLD, 15));

        //construct buttons
        nPlayer = new Button("New player!!");
        nPlayer.setBackground(new Color(24, 200, 241));
        nPlayer.setFont(new Font("Arial", Font.BOLD, 15));

        cPlayer = new Button("Start Playing!!");
        cPlayer.setBackground(new Color(24, 200, 241));
        cPlayer.setFont(new Font("Arial", Font.BOLD, 15));

        startG = new Button("Pause!!");
        startG.setBackground(new Color(24, 200, 241));
        startG.setFont(new Font("Arial", Font.BOLD, 15));

        stopB = new Button("Stop playing!!");
        stopB.setBackground(new Color(24, 200, 241));
        stopB.setFont(new Font("", Font.BOLD, 15));

        submitR = new Button("Submit your results!!");
        submitR.setBackground(new Color(24, 200, 241));
        submitR.setFont(new Font("", Font.BOLD, 15));

        submit = new Button("submit your information!!");
        submit.setBackground(new Color(24, 200, 241));
        submit.setFont(new Font("", Font.BOLD, 15));

        nPlayer.addActionListener(this);
        stopB.addActionListener(this);
        startG.addActionListener(this);
        submit.addActionListener(this);
        cPlayer.addActionListener(this);
        submitR.addActionListener(this);


        //Create the radio buttons for sound
        soundOff = new JRadioButton("sound off");
        soundOff.setActionCommand("false");


        soundOn = new JRadioButton("sound on");
        soundOn.setActionCommand("true");
        soundOn.setSelected(true);

        //Group the radio buttons for gender.
        soundGr = new ButtonGroup();
        soundGr.add(soundOn);
        soundGr.add(soundOff);

        //Put the radio buttons in a column in a panel.
        soundS = new Panel(new GridLayout(1, 0));
        soundS.add(new JLabel());
        soundS.add(new JLabel("turn sound on/off :"));
        soundS.add(soundOn);
        soundS.add(soundOff);
        soundS.add(new JLabel());
        soundS.add(new JLabel());


        soundOn.addActionListener(this);
        soundOff.addActionListener(this);

        //group buttons
        /*
         * welcome scren gui
         */
        welc.setLayout(new GridLayout(4, 1));
        welc.add(new JLabel());

        newP.setLayout(new GridLayout(1, 3));
        newP.add(new JLabel("Type your username (Case sensitive):  "));

        newP.add(nickn1);

        newP.add(cPlayer);

        welc.add(newP);

        welc.add(soundS);

        currentP.setLayout(new GridLayout(1, 4));
        currentP.add(new JLabel());
        currentP.add(new JLabel("Or click here if first time playing:  "));
        currentP.add(nPlayer);
        currentP.add(new JLabel());

        welc.add(currentP);

        /*
         * subscription form
         */

        //Create the radio buttons for gender
        JRadioButton genderM = new JRadioButton(gMale);
        genderM.setActionCommand(gMale);
        genderM.setSelected(true);

        JRadioButton genderF = new JRadioButton(gFemale);
        genderF.setActionCommand(gFemale);

        //Group the radio buttons for gender.
        genders = new ButtonGroup();
        genders.add(genderM);
        genders.add(genderF);

        //Put the radio buttons in a column in a panel.
        Panel gPanel = new Panel(new GridLayout(1, 0));
        gPanel.add(genderM);
        gPanel.add(genderF);


        //checkboxs for status
        //Create the check boxes.
        status1 = new JCheckBox("Student");
//        chinButton.setMnemonic(KeyEvent.VK_C);
        status1.setSelected(true);
        status2 = new JCheckBox("Sewanee");
        status3 = new JCheckBox("US Resident");


        //Register a listener for the check boxes.
        status1.addItemListener(this);
        status2.addItemListener(this);
        status3.addItemListener(this);

        //Put the check boxes in a column in a panel
        JPanel checkPanel = new JPanel(new GridLayout(0, 2));

        checkPanel.add(status1);
        checkPanel.add(status2);



        subscP = new Panel();
        subscP.setLayout(new GridLayout(7, 4));

        subscP.add(new JLabel("Specify your Gender:"));
        subscP.add(gPanel);
        subscP.add(new JLabel());

        subscP.add(new JLabel("Type your first name:  "));
        subscP.add(fn);
        subscP.add(new JLabel("Required!!"));

        subscP.add(new JLabel("Type your last name:  "));
        subscP.add(ln);
        subscP.add(new JLabel());

        subscP.add(new JLabel("Choose a username here:  "));
        subscP.add(nickn2);
        subscP.add(new JLabel("Must Start with your Initials"));

        subscP.add(new JLabel("check all that Applies: "));
        subscP.add(checkPanel);
        subscP.add(status3);

        subscP.add(new JLabel());
        subscP.add(new JLabel());
        subscP.add(new JLabel());

        subscP.add(new JLabel("Click here to Start Playing: "));
        subscP.add(submit);
        subscP.add(new JLabel());


        sp1_but = new JRadioButton(speed1);
        sp1_but.setActionCommand(speed1);
        sp1_but.setSelected(true);

        sp2_but = new JRadioButton(speed2);
        sp2_but.setActionCommand(speed2);
        sp3_but = new JRadioButton(speed3);
        sp3_but.setActionCommand(speed3);


        //Group the radio buttons for speed.
        speeds = new ButtonGroup();
        speeds.add(sp1_but);
        speeds.add(sp2_but);
        speeds.add(sp3_but);

        //Put the radio buttons in a column in a panel.
        Panel spPanel = new Panel(new GridLayout(0, 3));
        spPanel.add(sp1_but);
        spPanel.add(sp2_but);
        spPanel.add(sp3_but);


        //Register a listener for the radio buttons.
        genderM.addActionListener(this);
        genderF.addActionListener(this);
        sp1_but.addActionListener(this);
        sp2_but.addActionListener(this);
        sp3_but.addActionListener(this);

        bPanel.setLayout(new GridLayout(2, 3));
        bPanel.add(startG);
        bPanel.add(stopB);
        bPanel.add(submitR);


        bPanel.add(spPanel);
        bPanel.add(new JLabel(""));
        stopB.setEnabled(false);


        contentPane.add(welc, BorderLayout.NORTH);
        contentPane.add(canvas, BorderLayout.CENTER);

        contentPane.validate();

        canvas.addMouseListener(this);
        contentPane.addMouseListener(this);
        contentPane.setFocusable(true);

        frame.setVisible(true);
        frame.setContentPane(contentPane);

        frame.setLocation(50, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.validate();
//       ===============================================================
        canvas.setBackground(new Color(0, 150, 255));
        in = new Text("Welcome to Senyura Game!!! ", 255, 130, canvas);


//        ImageDisplay imDspl = new ImageDisplay(image, 0, 0, 700, 450, canvas);

        in.setColor(new Color(150, 200, 50));
        in.setFont(new Font("", Font.ITALIC, 30));



    }

    public void actionPerformed(ActionEvent evt) {


        if (evt.getSource() == nPlayer) {

            contentPane.remove(welc);
            contentPane.validate();

            contentPane.add("North", subscP);
            contentPane.validate();



        } else if (evt.getSource() == submit) {

            this.checkB();

            subInfo = dataSender.submPInfo(genders.getSelection().getActionCommand(), nickn2.getText(), fn.getText(), ln.getText(), status);


            System.out.printf("<<<%s>>>", subInfo.trim());


            if (subInfo.trim().equals("submitted")) {

                if (in != null) {
                    in.removeFromCanvas();
                }

                contentPane.remove(subscP);
                contentPane.validate();

                soundS.remove(0);
                soundS.remove(0);
                soundS.remove(2);
                soundS.validate();

                bPanel.add(soundS);
                bPanel.validate();
                contentPane.add("South", bPanel);
                contentPane.validate();


                if (bloc != null) {// kill current object before creating onather
                    bloc = null;
                } else {

                    this.checkB();
//                     if (imDspl != null) {
//                        imDspl.removeFromCanvas();
//                    }
                    player = nickn2.getText();
                    bloc = new BlockBuilder(player, new Location(0, 0), 17, soundValue, getWidth(), getHeight(), canvas);
                    bloc.addList(contentPane, canvas);

                    startG.setEnabled(true);
                    stopB.setEnabled(true);
                }

            } else {
                in.setText(subInfo.trim());
                in.setColor(Color.red);
            }

        } else if (evt.getSource() == cPlayer) {

            //retrieve info
            subInfo = dataSender.getPlayerInfo(nickn1.getText(), fn.getText());


            System.out.printf("<<<%s>>>", subInfo.trim());

            if (subInfo.trim().equals("Unable to connect to MySQL:")) {
                in.setText("Unable to connect , check your connection!!");
            } else if ((subInfo.trim().equals("Oops!! Player not found!!") || subInfo.trim().equals("Unable to query info:"))) {
                in.setText(subInfo.trim());
                in.setColor(Color.red);

            } else {
                if (cPlayer.getLabel().equals("Start Playing!!")) {
                    this.remove(welc);
                    this.validate();

                    soundS.remove(0);
                    soundS.remove(0);
                    soundS.remove(2);
                    soundS.validate();

                    bPanel.add(soundS);
                    bPanel.validate();

                    contentPane.add("South", bPanel);
                    contentPane.validate();


                    if (bloc != null) {// kill current object before creating onather
                        bloc = null;
                    } else {
                        if (in != null) {
                            in.removeFromCanvas();
                        }
//                         if (imDspl != null) {
//                        imDspl.removeFromCanvas();
//                    }

                        this.checkB();
                        player = nickn1.getText();
                        bloc = new BlockBuilder(player, new Location(0, 0), 17, soundValue, getWidth(), getHeight(), canvas);
                        bloc.addList(contentPane, canvas);

                        startG.setEnabled(true);
                        stopB.setEnabled(true);
                    }

                }
            }
        } else if (evt.getSource() == startG) {
            if (startG.getLabel().equals("Pause!!")) {
                if (bloc != null) {
                    bloc.pause();
                    startG.setLabel("Continue!!");
                }
            } else if (startG.getLabel().equals("Continue!!")) {
                if (bloc != null) {
                    bloc.contin();
                    startG.setLabel("Pause!!");
                }
            }

            //focus the canvas
            contentPane.remove(bPanel);
            contentPane.add("South", bPanel);
            contentPane.validate();
        } else if (evt.getSource() == stopB) {

            if (stopB.getLabel().equals("Stop playing!!")) {

                if (bloc != null) {

                    bloc.stop();
                    bloc = null;

                }

                canvas.clear();

                //front Page
//                imFile = getImage("media/endingImageCube.jpg");
//                imDspl = new ImageDisplay(imFile, 0, 0, 700, 450, canvas);
                startG.setEnabled(false);
                submitR.setEnabled(false);

                sp1_but.setSelected(true);
                stopB.setLabel("New game!!");

                contentPane.remove(bPanel);
                contentPane.add("South", bPanel);
                contentPane.validate();

            } else if (stopB.getLabel().equals("New game!!")) {

                contentPane.remove(bPanel);
                contentPane.add("South", bPanel);
                contentPane.validate();

                if (bloc == null) {
                    //game
//                    if (imDspl != null) {
//                        imDspl.removeFromCanvas();
//                    }
                    this.checkB();
                    bloc = new BlockBuilder(player, new Location(0, 0), 17, soundValue, getWidth(), getHeight(), canvas);
                    bloc.addList(contentPane, canvas);

                    startG.setEnabled(true);
                    submitR.setEnabled(true);
                    startG.setLabel("Pause!!");

                    stopB.setLabel("Stop playing!!");
                    sp1_but.setSelected(true);

                }
//                canvas.setEnabled(true);
//                contentPane.validate();
//                frame.validate();
            }
        } else if (evt.getSource() == submitR) {

            if (bloc != null) {
                dataSender.submResult(player, bloc.getResult()[0], bloc.getResult()[1], (int) bloc.getResult()[2]);
            }
            contentPane.remove(bPanel);
            contentPane.add("South", bPanel);
            contentPane.validate();

        } else {

            if (bloc != null) {
                if (speeds.getSelection().getActionCommand().equals(speed1)) {
                    bloc.setdelay(75);
                    bloc.setLevelC();
                    //focus the canvas
                    contentPane.remove(bPanel);
                    contentPane.add("South", bPanel);
                    contentPane.validate();


                } else if (speeds.getSelection().getActionCommand().equals(speed2)) {

                    bloc.setdelay(55);
                    bloc.setLevelC();
                    //focus the canvas
                    contentPane.remove(bPanel);
                    contentPane.add("South", bPanel);
                    contentPane.validate();

                } else if (speeds.getSelection().getActionCommand().equals(speed3)) {
                    bloc.setdelay(35);
                    bloc.setLevelC();
                    //focus the canvas
                    contentPane.remove(bPanel);
                    contentPane.add("South", bPanel);
                    contentPane.validate();
                }

                //turn sound on or off
                if (soundGr.getSelection().getActionCommand().equals("true")) {
                    soundValue = true;
                    bloc.setSound(soundValue);
                    //focus the canvas
                    contentPane.remove(bPanel);
                    contentPane.add("South", bPanel);
                    contentPane.validate();
                } else if (soundGr.getSelection().getActionCommand().equals("false")) {
                    soundValue = false;
                    bloc.setSound(soundValue);
                    //focus the canvas
                    contentPane.remove(bPanel);
                    contentPane.add("South", bPanel);
                    contentPane.validate();
                }
            }
        }
    }

    /** Listens to the check boxes. */
    public void itemStateChanged(ItemEvent e) {


        this.checkB();

    }

    //check box
    public void checkB() {
        status = " ";
        if (status1.isSelected()) {
            status += " " + status1.getActionCommand();
        } else {
            status += " Non-Student ";
        }

        if (status2.isSelected()) {
            status += " " + status2.getActionCommand();
        } else {
            status += " Non-Sewanee ";
        }

        if (status3.isSelected()) {
            status += " USA";
        } else {
            status += " Other-Country ";
        }


        //turn sound on or off
        if (soundGr.getSelection().getActionCommand().equals("true")) {
            soundValue = true;
//            //focus the canvas
//            contentPane.remove(bPanel);
//            contentPane.add("South", bPanel);
//            contentPane.validate();
        } else if (soundGr.getSelection().getActionCommand().equals("false")) {
            soundValue = false;

//            //focus the canvas
//            contentPane.remove(bPanel);
//            contentPane.add("South", bPanel);
//            contentPane.validate();
        }

    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        Location pt = new Location(e.getX(), e.getY());
        if (bloc != null) {
            bloc.mouseResp(pt);
        }
        canvas.setFocusable(true);
        canvas.setCursor(contentPane.getCursor());
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
