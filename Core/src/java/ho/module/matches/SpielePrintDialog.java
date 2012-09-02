// %1645704922:de.hattrickorganizer.gui.matches%
package ho.module.matches;

import ho.core.db.DBManager;
import ho.core.model.match.MatchKurzInfo;
import ho.core.model.match.Matchdetails;
import ho.core.util.HOLogger;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JPanel;



/**
 * Dialog um ein Spiel mit allen Infos zu Drucken
 */
public class SpielePrintDialog extends JDialog {
	
	private static final long serialVersionUID = 1259449929345060213L;
	
    //~ Instance fields ----------------------------------------------------------------------------

    private AufstellungsSternePanel m_jpAufstellungGastPanel;
    private AufstellungsSternePanel m_jpAufstellungHeimPanel;
    private ManschaftsBewertungsPanel m_jpManschaftsBewertungsPanel;
    private SpielHighlightPanel m_jpSpielHighlightPanel;
    private StaerkenvergleichPanel m_jpStaerkenvergleichsPanel;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SpielePrintDialog object.
     *
     * @param info TODO Missing Constructuor Parameter Documentation
     */
    public SpielePrintDialog(ho.core.model.match.MatchKurzInfo info) {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        initComponents();
        initValues(info);

        try {
            final Toolkit kit = Toolkit.getDefaultToolkit();
            setLocation(kit.getScreenSize().width, kit.getScreenSize().height);
        } catch (Exception e) {
        }

        pack();

        setVisible(true);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Drucken des Spiels
     *
     * @param titel TODO Missing Constructuor Parameter Documentation
     */
    public final void doPrint(String titel) {
        try {
            final ho.core.gui.print.PrintController printController = ho.core.gui.print.PrintController
                                                                                   .getInstance();

            printController.add(new ho.core.gui.print.ComponentPrintObject(printController
                                                                                        .getPf(),
                                                                                        titel,
                                                                                        getContentPane(),
                                                                                        ho.core.gui.print.ComponentPrintObject.SICHTBAR));

            printController.print();
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Component initAufstellungGast() {
        m_jpAufstellungGastPanel = new AufstellungsSternePanel(false, true);
        return m_jpAufstellungGastPanel;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Component initAufstellungHeim() {
        m_jpAufstellungHeimPanel = new AufstellungsSternePanel(true, true);
        return m_jpAufstellungHeimPanel;
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.insets = new Insets(0, 0, 0, 0);
        getContentPane().setLayout(layout);
        //background important for printing, so static white
        getContentPane().setBackground(Color.WHITE);

        //Allgemein
        m_jpStaerkenvergleichsPanel = new StaerkenvergleichPanel(true);
        constraints.gridx = 0;
        constraints.gridy = 0;
        layout.setConstraints(m_jpStaerkenvergleichsPanel, constraints);
        getContentPane().add(m_jpStaerkenvergleichsPanel);

        //Bewertung
        m_jpManschaftsBewertungsPanel = new ManschaftsBewertungsPanel(true);
        constraints.gridx = 0;
        constraints.gridy = 1;
        layout.setConstraints(m_jpManschaftsBewertungsPanel, constraints);
        getContentPane().add(m_jpManschaftsBewertungsPanel);

        //Highlights
        m_jpSpielHighlightPanel = new SpielHighlightPanel(true);
        constraints.gridx = 0;
        constraints.gridy = 2;
        layout.setConstraints(m_jpSpielHighlightPanel, constraints);
        getContentPane().add(m_jpSpielHighlightPanel);

        //Aufstellung
        final JPanel aufstellungsPanel = new JPanel(new GridLayout(2, 1));
        aufstellungsPanel.add(initAufstellungHeim());
        aufstellungsPanel.add(initAufstellungGast());
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 3;
        layout.setConstraints(aufstellungsPanel, constraints);
        getContentPane().add(aufstellungsPanel);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param info TODO Missing Method Parameter Documentation
     */
    private void initValues(MatchKurzInfo info) {
        if (info != null) {
            //Selektiertes Spiel des Models holen und alle 3 Panel informieren 
            try {
            	final Matchdetails details = DBManager.instance().getMatchDetails(info.getMatchID());
                m_jpStaerkenvergleichsPanel.refresh(info,details);
                m_jpManschaftsBewertungsPanel.refresh(info,details);
                m_jpSpielHighlightPanel.refresh(info,details);

                if (info.getMatchStatus() == MatchKurzInfo.FINISHED) {
                    m_jpAufstellungHeimPanel.refresh(info.getMatchID(), info.getHeimID());
                    m_jpAufstellungGastPanel.refresh(info.getMatchID(), info.getGastID());
                } else {
                    m_jpAufstellungHeimPanel.clearAll();
                    m_jpAufstellungGastPanel.clearAll();
                }
            } catch (Exception e) {
                m_jpStaerkenvergleichsPanel.clear();
                m_jpManschaftsBewertungsPanel.clear();
                m_jpSpielHighlightPanel.clear();
                m_jpAufstellungHeimPanel.clearAll();
                m_jpAufstellungGastPanel.clearAll();
                HOLogger.instance().log(getClass(),"SpielePanel.newSelectionInform: Keine Match zum Eintrag in der Tabelle gefunden! "
                                   + e);
            }
        } else {
            //Alle Panels zurücksetzen
            m_jpStaerkenvergleichsPanel.clear();
            m_jpManschaftsBewertungsPanel.clear();
            m_jpSpielHighlightPanel.clear();
            m_jpAufstellungHeimPanel.clearAll();
            m_jpAufstellungGastPanel.clearAll();
        }
    }
}
