// %3210796985:de.hattrickorganizer.logik%
/*
 * GebChecker.java
 *
 * Created on 4. April 2003, 10:27
 */
package de.hattrickorganizer.logik;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class GebChecker implements Runnable {
    //~ Instance fields ----------------------------------------------------------------------------

    private javax.swing.JDialog m_clDialog;
    private int m_iSekunden;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of GebChecker
     */
    public GebChecker() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param frame TODO Missing Method Parameter Documentation
     */
    public final void setDialog(javax.swing.JDialog frame) {
        m_clDialog = frame;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static boolean checkTWGeb() {
        final java.util.Calendar calendar = java.util.Calendar.getInstance();

        if ((calendar.get(java.util.Calendar.MONTH) == java.util.Calendar.APRIL)
            && (calendar.get(java.util.Calendar.DAY_OF_MONTH) == 12)) {
            return true;
        }

        return false;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static boolean checkVFGeb() {
        final java.util.Calendar calendar = java.util.Calendar.getInstance();

        if ((calendar.get(java.util.Calendar.MONTH) == java.util.Calendar.MAY)
            && (calendar.get(java.util.Calendar.DAY_OF_MONTH) == 18)) {
            return true;
        }

        return false;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getSekunden() {
        //+ 1 weil bei 0 angefangen wird
        return m_iSekunden + 1;
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void run() {
        for (m_iSekunden = 0; m_iSekunden < 60; m_iSekunden++) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }

        if (m_clDialog != null) {
            m_clDialog.setVisible(false);
            m_clDialog.dispose();
        }
    }
}
