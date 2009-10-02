package hoplugins.tsforecast;

/*
 * ErrorLog.java
 *
 * Created on 24.March 2006, 21:04
 *
 *Version 0.11
 *history :
 *24.03.06  Version 0.1 Creation
 *26.08.06  Version 0.11 rebuilt
 *
 */

/**
 *
 * @author  michael.roux
 */

import java.io.*;
import java.util.Date;
import javax.swing.JOptionPane;

public class ErrorLog {

  public ErrorLog() {}

  public static void write(String s) {
    try {
      FileWriter filewriter = new FileWriter( "tsforecasterror.txt", true);
      filewriter.write( new Date() + " " + s);
      filewriter.flush();
      filewriter.close();
    }
    catch(Exception exception) {
      JOptionPane.showMessageDialog(null, "Can\264t write error file!", "TS Forecast", 0);
    }
  }

  public static void writeln( String s) {
    write( s + "\r\n");
  }

  public static void write( Exception exception) {
    try {
      StringWriter stringwriter = new StringWriter();
      PrintWriter printwriter = new PrintWriter( stringwriter);
      exception.printStackTrace( printwriter);
      write( stringwriter.toString());
    }
    catch( Exception innerException) {
      JOptionPane.showMessageDialog( null, "Can\264t write error file!", "TS Forecast", 0);
    }
  }
}