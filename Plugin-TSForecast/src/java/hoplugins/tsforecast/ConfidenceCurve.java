package hoplugins.tsforecast;

/*
 * ConfidenceCurve.java
 *
 * Created on 26.August 2006, 11:04
 *
 *Version 0.1
 *history :
 *18.03.06  Version 0.1 rebuilt
 */

/**
 *
 * @author  michael.roux
 */

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import plugins.IHOMiniModel;

// Referenced classes of package hoplugins.tsforecast:
//            Curve

public class ConfidenceCurve extends Curve {

  public ConfidenceCurve(IHOMiniModel ihominimodel) throws SQLException {
    super(ihominimodel);
    readConfidenceHistory();
  }

  private void readConfidenceHistory() throws SQLException {
    GregorianCalendar gregoriancalendar = new GregorianCalendar();
    gregoriancalendar.setTime( m_clModel.getBasics().getDatum());
    gregoriancalendar.add( Calendar.WEEK_OF_YEAR, -WEEKS_BACK);
    Date start = gregoriancalendar.getTime();
    
    ResultSet resultset = m_clJDBC.executeQuery( "select DATUM, ISELBSTVERTRAUEN from HRF, TEAM "
                                               + "where HRF.HRF_ID = TEAM.HRF_ID order by DATUM");
    for(boolean flag = resultset.first(); flag; flag = resultset.next()) {
      if(   start.before( resultset.getTimestamp( "DATUM")) 
         && !m_clModel.getBasics().getDatum().before( resultset.getTimestamp( "DATUM"))) {
        m_clPoints.add( new Point( resultset.getTimestamp( "DATUM"), resultset.getInt( "ISELBSTVERTRAUEN")));
      }
    }
  }
}