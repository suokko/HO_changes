package hoplugins.tsforecast;

/*
 * TrainerCurve.java
 *
 * Created on 29.May 2006, 11:04
 *
 *Version 0.2
 *history :
 *29.05.06  Version 0.1 Creation
 *26.08.06  Version 0.11 rebuilt
 *19.02.07  Version 0.2 Trainerstart point
 */

/**
 *
 * @author  michael.roux
 */

import java.util.Date;
import java.util.Iterator;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import plugins.IHOMiniModel;

// Referenced classes of package hoplugins.tsforecast:
//            Curve

public class TrainerCurve extends Curve {

  public TrainerCurve(IHOMiniModel ihominimodel) throws SQLException {
    super(ihominimodel);
    readTrainer();
  }
  
  public double getLeadership( Date d) throws Exception {
    double dRet = -1;
    if ( d != null) {
      for( Iterator<Point> i = m_clPoints.iterator(); i.hasNext();) {
        Point p = i.next();
        if( p.m_dDate.before( d))
          dRet = p.m_dSpirit;
      }
    } else {
      throw new NullPointerException("Given date is null!");
    }
    if (dRet < 0)
      throw new Exception("Trainer has no leadership!");
    return dRet;
  }

  public double getCurrentLeadership() {
    last();
//ErrorLog.writeln( "currentLeadership = " + getSpirit());
    return getSpirit();
  }

  private void readTrainer() throws SQLException {
    GregorianCalendar gregoriancalendar = new GregorianCalendar();
    gregoriancalendar.setTime( m_clModel.getBasics().getDatum());
    gregoriancalendar.add( Calendar.WEEK_OF_YEAR, -WEEKS_BACK);
    Timestamp start = new Timestamp( gregoriancalendar.getTimeInMillis());

    int iLeadership = -1;
    int iLastLeadership = -1;
    int iID = -1;
    int iLastID = -1;
    
    // get last skill just before start date
    ResultSet resultset = m_clJDBC.executeQuery( "select SPIELERID, FUEHRUNG, DATUM from SPIELER "
                                               + "where TRAINERTYP <> -1 "
                                               +   "and DATUM <= '" + start + "' "
                                               + "order by DATUM desc");
    resultset.first();
    iLeadership = resultset.getInt("FUEHRUNG");
    iID = iLastID = resultset.getInt("SPIELERID");
    m_clPoints.add( new Point( resultset.getTimestamp("DATUM"), iLeadership, START_TRAINER_PT));

    resultset = m_clJDBC.executeQuery( "select SPIELERID, FUEHRUNG, DATUM from SPIELER "
                                     + "where TRAINERTYP <> -1 "
                                     +   "and DATUM > '" + start + "' and DATUM < '" + m_clModel.getBasics().getDatum() + "' "
                                     + "order by DATUM");
    for( boolean flag = resultset.first(); flag; flag = resultset.next()) {
      iLeadership = resultset.getInt("FUEHRUNG");
      iID = resultset.getInt("SPIELERID");
      if( iID != iLastID) { //New Trainer
        m_clPoints.add( new Point( resultset.getTimestamp("DATUM"), iLeadership, NEW_TRAINER_PT));
      } else if( iLastLeadership != -1 && iLeadership != iLastLeadership) { //Trainer Skilldown
        m_clPoints.add( new Point( resultset.getTimestamp("DATUM"), iLeadership, TRAINER_DOWN_PT));
      }
      iLastLeadership = iLeadership;
      iLastID = iID;
    }
  }

}