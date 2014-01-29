/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalgame;

/**
 *
 * @author Marcellin
 * from citation needed??????????????????
 */
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostDataTest {

    URL url;
    URLConnection urlConn;
    DataOutputStream output;
    BufferedReader input;
    String error = "", ln, fn, twn, content;
    String result = "";

    public PostDataTest() {
    }

    //subimt player record
    public String submPInfo(String title, String nickn, String fname, String lname, String status) {

        String resul;
        try {
            url = new URL("http://nshimiye.co.cc/HTML5T/game/placeInfo.php");
            content = "gender= " + title + "& nick= " + nickn + "& fname=" + fname + "& lname=" + lname + "& status=" + status;
            resul = this.init(content);

        } catch (MalformedURLException ex) {
            //Logger.getLogger(PostDataTest.class.getName()).log(Level.SEVERE, null, ex);
            resul = "Unable to connect to MySQL:";

        }

        return resul;

    }

    //
    public String submResult(String nickn, double point, double perform, int level) {
        String resul = "";
        try {
            url = new URL("http://nshimiye.co.cc/HTML5T/game/RecordSend.php");
            content = "nick=" + nickn + "& points=" + point + "& hiPerform=" + perform + "& gameLevel=" + level;
            resul = this.init(content);

        } catch (MalformedURLException ex) {
            Logger.getLogger(PostDataTest.class.getName()).log(Level.SEVERE, null, ex);
            resul = "Unable to connect to MySQL:";
        }
        return resul;

    }

    /**
     * 
     * @param nickn
     * @param fname
     * @return 
     */
    public String getPlayerInfo(String nickn, String fname) {
        String resul = "";
        try {
            url = new URL("http://nshimiye.co.cc/HTML5T/game/playerSend.php");
            content = "nick=" + nickn + "& fname=" + fname;
            resul = this.init(content);


        } catch (MalformedURLException ex) {
//      Logger.getLogger(PostDataTest.class.getName()).log(Level.SEVERE, null, ex);
            resul = "Unable to connect to MySQL:";
        }
        return resul;
    }

//    public String submInfo2(String postInfo) {
//        result = "";
//
//        try {
//            url = new URL("http://nshimiye.co.cc/HTML5T/game/engine.php");
//
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(PostDataTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        content = "actions= " + postInfo;
//        return this.init(content);
//
//
//
//    }

    public String init(String content) {

        result = "";

        try {

            urlConn = url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

//      //Create Post String

            output = new DataOutputStream(urlConn.getOutputStream());
            output.writeBytes(content);
            output.flush();
            output.close();

            DataInputStream in = new DataInputStream(urlConn.getInputStream());
            input = new BufferedReader(new InputStreamReader(in));
            String str;
            while ((str = input.readLine()) != null) {
                result = result + str + "\n";
            }
            input.close();
        } catch (MalformedURLException e) {
            error += e + "\n";
        } catch (IOException e) {
            error += e + "\n";
        }
        return result;
    }
}
