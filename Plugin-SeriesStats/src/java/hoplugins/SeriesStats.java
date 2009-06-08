// %2810677669:hoplugins%
/*
 * Created on 24.11.2004
 *
 */
package hoplugins;

import hoplugins.commons.ui.DebugWindow;

import hoplugins.seriesstats.ui.GeneralPanelTab;
import hoplugins.seriesstats.ui.RatingPanelTab;
import hoplugins.seriesstats.ui.SeriesPanelTab;

import plugins.IHOMiniModel;
import plugins.IOfficialPlugin;
import plugins.IPlugin;
import plugins.IRefreshable;

import java.awt.BorderLayout;

import java.io.File;

import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;


/**
 * DOCUMENT ME!
 *
 * @author Stefan Cyris TODO:
 */
public class SeriesStats implements IPlugin, IRefreshable, IOfficialPlugin {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static plugins.IDebugWindow IDB;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static plugins.IDebugWindow getIDB() {
        return IDB;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getName() {
        return getPluginName() + " " + getVersion();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getPluginID() {
        return 11;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getPluginName() {
        return "SeriesStats";
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public File[] getUnquenchableFiles() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public double getVersion() {
        return 1.01;
    }

    /**
     * TODO Missing Method Documentation
     */
    public void refresh() {
        Date now = new Date();
        long nowLong = now.getTime();

        IDB.append("" + nowLong + ": refresh");
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param myhOMiniModel TODO Missing Method Parameter Documentation
     */
    public void start(IHOMiniModel myhOMiniModel) {
        final JPanel PanelOuter;
        final JTabbedPane PanelOuterTabbed;

        // Create a DebugWindow and shows some messages
        IDB = myhOMiniModel.getGUI().createDebugWindow(new java.awt.Point(100, 200),
                                                       new java.awt.Dimension(700, 400));

        // IDB.setVisible(true);
        try {
            // plugins.IMatchKurzInfo spilp[] =
            // myhOMiniModel.getMatchesKurzInfo(336386);
            // create Panels
            PanelOuter = myhOMiniModel.getGUI().createImagePanel();
            PanelOuter.setLayout(new BorderLayout());

            PanelOuterTabbed = new JTabbedPane();
            PanelOuterTabbed.addTab(myhOMiniModel.getLanguageString("Allgemein"),
                                    new GeneralPanelTab());
            PanelOuterTabbed.addTab(myhOMiniModel.getLanguageString("Bewertung"),
                                    new RatingPanelTab());
            PanelOuterTabbed.addTab(myhOMiniModel.getLanguageString("Liga"),
                                    new SeriesPanelTab());
            PanelOuter.add(PanelOuterTabbed, BorderLayout.CENTER);

            // add a new Panel to ho
            myhOMiniModel.getGUI().addTab(getPluginName(), PanelOuter);

            // we'd like to get informed by changes from ho
            // hOMiniModel.getGUI ().registerRefreshable ( this );
            // To Stop information use this code
            // m_clModel.getGUI().unregisterRefreshable( this );
            // XtraDataDemo
            // xtraDataDemo();
            // Is User Match check
            // this func checks out if given MatchId is a match of User-Team
            // m_clModel.getGUI().getInfoPanel().setLangInfoText( "Series Spy"
            // );
            refresh();
        } catch (RuntimeException e) {
            DebugWindow.debug(e);
        }
    }
}
