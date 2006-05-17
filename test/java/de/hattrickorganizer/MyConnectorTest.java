// %1308103607:de.hattrickorganizer.net%
/*
 * MyConnector.java
 *
 * Created on 7. April 2003, 09:36
 */
package de.hattrickorganizer;

import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.net.MyConnector;
import de.hattrickorganizer.net.rmiHOFriendly.ServerVerweis;
import de.hattrickorganizer.tools.MyHelper;
import de.hattrickorganizer.tools.xml.XMLManager;

import gui.UserParameter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import java.util.Properties;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class MyConnectorTest {

    public static void main(String[] args) {
        try {
            /*
               //Proxy Settings
               MyConnector.instance().setProxyHost ( "vpc210" );
               MyConnector.instance().setUseProxy ( true );
               MyConnector.instance().setProxyPort ( "8080" );
               MyConnector.instance ().enableProxy ();
             */

            //DB zurücksetzen
            //getWebPage( "http://tooldesign.ch/ho/truncate.php", false );
            //Update test
            System.out.print(MyConnector.instance().getLatestVersion());

            //Matchfinder test
            final int id1 = MyConnector.instance().registerServer("192.168.0.1", 1099, "Test");
            final int id2 = MyConnector.instance().registerServer("192.168.0.2", 1099,
                                                                  "Irgendwie muss die Liste ja voll werden");
            final ServerVerweis[] tmp = MyConnector.instance().getServerList();
            System.out.println(tmp.length);
            MyConnector.instance().unregisterServer(id1);

            MyConnector.instance().unregisterServer(id2);
            System.out.println("klappt");

            //Hrf get Test

            /*
               MyConnector.instance().setUserPwd ( "pwd" );
               MyConnector.instance().setUserName ( "user" );
               MyConnector.instance().login();
               System.out.println();
               String s    =   MyConnector.instance().getHRF();
               System.out.println( s );
               if(s.indexOf("playingMatch=true") > -1)
               {
                   javax.swing.JOptionPane.showMessageDialog(null, "Your club is currently playing a match", "Cannot retrieve HRF Data from Internet", 1);
                   return;
               }
               File file = new File( "C:" + File.separator  + "test.hrf");
               if(file.exists())
                   file.delete();
               try
               {
                   FileOutputStream fileoutputstream = new FileOutputStream(file);
                   fileoutputstream.write(s.getBytes());
                   fileoutputstream.flush();
                   fileoutputstream.close();
               }
               catch(IOException ioexception1)
               {
                   ioexception1.printStackTrace();
               }
               System.out.println( "Details" );
               System.out.println( MyConnector.instance().getMatchDetails ( "5377934" ) );
               System.out.println( "Report" );
               System.out.println( MyConnector.instance().getMatchReport ( "5377934" ) );
               MyConnector.instance().logout();
             */
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
