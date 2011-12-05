// %1572960370:de.hattrickorganizer.gui%
package de.hattrickorganizer.gui;

import java.util.Vector;

import plugins.IRefreshable;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.extension.FileExtensionManager;


/**
 * Managed das Refreshen
 */
public class RefreshManager {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static RefreshManager m_clRefreshManager;

    //~ Instance fields ----------------------------------------------------------------------------

    private Vector<IRefreshable> m_clRefreshable = new Vector<IRefreshable>();

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new RefreshManager object.
     */
    private RefreshManager() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static RefreshManager instance() {
        if (m_clRefreshManager == null) {
            m_clRefreshManager = new RefreshManager();
        }

        return m_clRefreshManager;
    }

    /**
     * Informiert alle registrierten Objekte
     */
    public void doReInit() {
    	FileExtensionManager.modelUpdate();
        for (int i = 0; i < m_clRefreshable.size(); i++) {
            try {
                //no plugin
                if (m_clRefreshable.get(i) instanceof Refreshable) {
                    ((Refreshable) m_clRefreshable.get(i)).reInit();
                }
                //plugin
                else {
                    ((IRefreshable) m_clRefreshable.get(i)).refresh();
                }
            } catch (Exception e) {
                HOLogger.instance().log(getClass(),"Gefangener Fehler beim doReInit:");
                HOLogger.instance().log(getClass(),e);
            }
        }
        System.gc();
        Thread.yield();
		HOMainFrame.setHOStatus(HOMainFrame.READY);        
    }

    /**
     * Informiert alle registrierten Objekte
     */
    public void doRefresh() {
		FileExtensionManager.modelUpdate();
        for (int i = 0; i < m_clRefreshable.size(); i++) {
            try {
                ((IRefreshable) m_clRefreshable.get(i)).refresh();
            } catch (Exception e) {
                HOLogger.instance().log(getClass(),"Gefangener Fehler beim doRefresh:");
                HOLogger.instance().log(getClass(),e);
            }
        }

        System.gc();
        Thread.yield();
		HOMainFrame.setHOStatus(HOMainFrame.READY);        
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param refreshable TODO Missing Method Parameter Documentation
     */
    public void registerRefreshable(IRefreshable refreshable) {
        m_clRefreshable.add(refreshable);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param refreshable TODO Missing Method Parameter Documentation
     */
    public void unregisterRefreshable(IRefreshable refreshable) {
        m_clRefreshable.remove(refreshable);
    }
}
