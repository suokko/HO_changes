package ho.module.tsforecast;

/*
 * Curve.java
 *
 * Created on 18.March 2006, 11:04
 *
 *Version 0.5
 *history :
 *18.03.06  Version 0.1
 *19.03.06  Version 0.2  subclassing
 *02.04.06  Version 0.3  added Matchday in Point
 *24.08.06  Version 0.31 rebuilt
 *19.02.07  Version 0.4  added Trainer Start Point 
 *21.02.07  Version 0.5  added tooltip to point
 */

/**
 *
 * @author  michael.roux
 */


import ho.core.db.DBManager;
import ho.core.db.JDBCAdapter;
import ho.core.model.match.IMatchDetails;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;



class Curve {

  static final int WEEKS_BACK = 26;
  
  static final int RESET                               = -1;
  static final int UNKNOWN_MATCH                       = 0;

  
  static final double TEAM_SPIRIT_UNKNOWN = -1D;
  static final double TEAM_SPIRIT_RESET   = 4.5D;
  
  static final int STANDARD_PT      = 0;
  static final int RESET_PT         = 1;
  static final int NEW_TRAINER_PT   = 10;
  static final int TRAINER_DOWN_PT  = 11;
  static final int START_TRAINER_PT = 12;
  
  protected JDBCAdapter m_clJDBC  = null;
  
  protected ArrayList<Point> m_clPoints   = new ArrayList<Point>();
  
  private Iterator<Point> m_clIterator = null;
  private Point m_currentPoint  = null;
  private Color m_Color         = null;


  class Point implements Comparable<Point> {
  
    double m_dSpirit    = TEAM_SPIRIT_UNKNOWN;
    int m_iAttitude     = ho.core.model.match.IMatchDetails.EINSTELLUNG_UNBEKANNT;
    Date m_dDate        = null; 
    int m_iMatchDay     = 0;
    int m_iMatchType    = UNKNOWN_MATCH;
    int m_iPointType    = STANDARD_PT;
    String m_strTooltip = null;

    Point( Point point) {
        m_dDate      = new Date( point.m_dDate.getTime());
        m_dSpirit    = point.m_dSpirit;
        m_iAttitude  = point.m_iAttitude;
        m_iMatchDay  = point.m_iMatchDay;
        m_iMatchType = point.m_iMatchType;
        m_iPointType = point.m_iPointType;
    }

    Point( Date date, double dSpirit, int iAttitude, int iMatchDay, int iMatchType, int iPointType) {
      m_dDate      = new Date(date.getTime());
      m_dSpirit    = dSpirit;
      m_iAttitude  = iAttitude;
      m_iMatchDay  = iMatchDay;
      m_iMatchType = iMatchType;
      m_iPointType = iPointType;
    }

    Point( Date date, int iAttitude, int iMatchDay, int iMatchType) {
      this( date, TEAM_SPIRIT_UNKNOWN, iAttitude, iMatchDay, iMatchType, STANDARD_PT);
    }

    Point( Date date, double dSpirit){
      this( date, dSpirit, IMatchDetails.EINSTELLUNG_UNBEKANNT, 0, UNKNOWN_MATCH, STANDARD_PT);
    }

    Point(Date date, double dSpirit, int iPointType) { //Spirit or Coach leadership
      this( date, dSpirit, IMatchDetails.EINSTELLUNG_UNBEKANNT, 0, UNKNOWN_MATCH, iPointType);
    }

    public int compareTo(Point obj) {
      return m_dDate.compareTo( obj.m_dDate);
    }
  }


  Curve( ) {
    m_clJDBC = DBManager.instance().getAdapter();
  }

  boolean first() {
    m_clIterator = m_clPoints.iterator();
    return !m_clPoints.isEmpty();
  }

  boolean last() {
    for(m_clIterator = m_clPoints.iterator(); m_clIterator.hasNext();)
      m_currentPoint = m_clIterator.next();
    return !m_clPoints.isEmpty();
  }

  boolean next(){
    if(m_clIterator.hasNext()) {
      m_currentPoint = m_clIterator.next();
      return true;
    }
    return false;
    
  }

  Date getDate()                    { return m_currentPoint.m_dDate; }
  double getSpirit()                { return m_currentPoint.m_dSpirit; }
  int getAttitude()                 { return m_currentPoint.m_iAttitude; }
  int getMatchType()                { return m_currentPoint.m_iMatchType; }
  int getMatchDay()                 { return m_currentPoint.m_iMatchDay; }
  String getTooltip()			 { return m_currentPoint.m_strTooltip; }
  Color getColor()                  { return m_Color; }
  int getPointType()                { return m_currentPoint.m_iPointType; }
  void setColor(Color color)        { m_Color = color; }
  Point getFirstPoint()             { return m_clPoints.get(0); }
  Point getLastPoint()              { return m_clPoints.get(m_clPoints.size() - 1); }
  void addPoint(int i, Point point) { m_clPoints.add(i, new Point(point)); }

  //-- protected ---------------------------------------------------------------------

  protected static int getDiffDays(Point point1, Point point2) {
    GregorianCalendar gregoriancalendar1 = new GregorianCalendar();
    gregoriancalendar1.setTime( point1.m_dDate);
    GregorianCalendar gregoriancalendar2 = new GregorianCalendar();
    gregoriancalendar2.setTime( point2.m_dDate);
    int iRet = 0;
    if( gregoriancalendar1.get( Calendar.YEAR) != gregoriancalendar2.get( Calendar.YEAR)) {
      iRet += 365;
      if( gregoriancalendar1.isLeapYear( gregoriancalendar1.get( Calendar.YEAR)))
        iRet++;
    }
    iRet += gregoriancalendar2.get( Calendar.DAY_OF_YEAR) - gregoriancalendar1.get( Calendar.DAY_OF_YEAR);
    return iRet;
  }

}