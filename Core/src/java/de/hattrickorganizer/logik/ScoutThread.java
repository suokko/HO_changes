// %1301392466:de.hattrickorganizer.logik%
/*
 * ScoutThread.java
 *
 * Created on 8. April 2003, 10:49
 */
package de.hattrickorganizer.logik;

import java.util.Vector;

import de.hattrickorganizer.model.ScoutEintrag;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class ScoutThread implements Runnable {
    //~ Instance fields ----------------------------------------------------------------------------

    private Vector<ScoutEintrag> m_vScoutEintraege = new Vector<ScoutEintrag>();
    private int difference = gui.UserParameter.instance().TimeZoneDifference * 3600000;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of ScoutThread
     *
     * @param scouts TODO Missing Constructuor Parameter Documentation
     */
    public ScoutThread(Vector<ScoutEintrag> scouts) {
        m_vScoutEintraege = new Vector<ScoutEintrag>(scouts);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     */
    public final void run() {
        de.hattrickorganizer.model.ScoutEintrag se = null;
        final java.sql.Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());
        final java.sql.Timestamp ts2 = new java.sql.Timestamp(System.currentTimeMillis());

        //5 min vorher meckern : - 300000

        /*m_vScoutEintraege != null && !m_vScoutEintraege.isEmpty ()*/
        while (true) {
            ts.setTime(System.currentTimeMillis());

            //ts2.setTime ( System.currentTimeMillis () + gui.UserParameter.instance ().deadlineFrist );
            for (int i = 0;
                 (m_vScoutEintraege != null) && !m_vScoutEintraege.isEmpty()
                 && (i < m_vScoutEintraege.size()); ++i) {
                se = m_vScoutEintraege.elementAt(i);
                ts2.setTime(se.getDeadline().getTime() - gui.UserParameter.instance().deadlineFrist
                            + difference);

                //übersehene abgelaufen entfernen

                /*
                   if ( se.getDeadline ().before ( ts ) )
                   {
                       m_vScoutEintraege.removeElementAt ( i );
                       --i;
                   }//in 5 min fällige anzeigen
                
                   else */
                if (ts2.before(ts) && !se.getDeadline().before(ts) && !se.isWecker()) {
                    //melden
                    new de.hattrickorganizer.gui.transferscout.Wecker(se.getName() + " ("
                                                                      + se.getPlayerID() + ")"
                                                                      + "\r\n"
                                                                      + se.getDeadline().toString());
                    se.setWecker(true);

                    //m_vScoutEintraege.removeElementAt ( i );
                    //--i;
                }
            }

            try {
                //30 sec heia machen
                Thread.sleep(30000);
            } catch (Exception e) {
            }
        }
    }

    /**
     * startet einen Scout
     *
     * @param scouts TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static ScoutThread start(Vector<ScoutEintrag> scouts) {
        final ScoutThread temp = new ScoutThread(scouts);

        new Thread(temp).start();

        return temp;
    }

    /*
       public void addEintrag( model.ScoutEintrag se )
       {
           if ( !m_vScoutEintraege.contains ( se ) )
           {
               m_vScoutEintraege.addElement ( se );
           }
       }
    
       public void removeEintrag( model.ScoutEintrag se )
       {
           m_vScoutEintraege.removeElement ( se );
       }
     */
    public final void setVector(Vector<ScoutEintrag> vec) {
        m_vScoutEintraege = vec;
    }
}
