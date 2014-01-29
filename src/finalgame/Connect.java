/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalgame;

/**
 *
 * @author Administrator
 */
import java.sql.Connection;
import java.sql.DriverManager;

   public class Connect
   {
        
       public static void main (String[] args)
       {
           PostDataTest dataSender = new PostDataTest();
           Connection conn = null;

           try
           {
               String userName = "root";
               String password = "From2to1";
               String url = "jdbc:mysql://152.97.100.111/senyuragame";
               Class.forName ("com.mysql.jdbc.Driver").newInstance ();
               conn = DriverManager.getConnection (url, userName, password);
               System.out.println ("Database connection established");
           }
           catch (Exception e)
           {
               System.err.println ("Cannot connect to database server"+e.toString());
           }
           finally
           {
               if (conn != null)
               {
                   try
                   {
                       conn.close ();
                       System.out.println ("Database connection terminated");
                   }
                   catch (Exception e) { /* ignore close errors */ }
               }
           }
           
           
          String s =  dataSender.submPInfo("Mr.", "user", "Marc", "nma", "bite");
          String s0 =  dataSender.getPlayerInfo("user", "Marcellin");
          System.out.println(s0);
       }
   }