// %1351448296:de.hattrickorganizer.gui.league%
package ho.module.series;

import ho.core.util.HOLogger;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JDialog;



/**
 * Dialog zum Drucken der Ligatabelle
 */
public class SeriesPrintPanelDialog extends JDialog {
	
	private static final long serialVersionUID = 2345698230109077443L;
	
    //~ Instance fields ----------------------------------------------------------------------------

	private SeriesTablePanel m_jpLigaTabelle;
    private MatchDayPanel m_jpSpielPlan1;
    private MatchDayPanel m_jpSpielPlan2;
    private SeriesHistoryPanel m_jpTabellenverlaufStatistik;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new LigaTabellePrintDialog object.
     */
    public SeriesPrintPanelDialog() {
        initComponents();
        initValues();

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

        m_jpLigaTabelle = new SeriesTablePanel();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        layout.setConstraints(m_jpLigaTabelle, constraints);
        getContentPane().add(m_jpLigaTabelle);

        m_jpTabellenverlaufStatistik = new SeriesHistoryPanel();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        layout.setConstraints(m_jpTabellenverlaufStatistik, constraints);
        getContentPane().add(m_jpTabellenverlaufStatistik);

        m_jpSpielPlan1 = new MatchDayPanel(MatchDayPanel.LETZTER_SPIELTAG);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpSpielPlan1, constraints);
        getContentPane().add(m_jpSpielPlan1);

        m_jpSpielPlan2 = new MatchDayPanel(MatchDayPanel.NAECHSTER_SPIELTAG);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jpSpielPlan2, constraints);
        getContentPane().add(m_jpSpielPlan2);
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initValues() {
        m_jpLigaTabelle.changeSaison();
        m_jpTabellenverlaufStatistik.changeSaison();
        m_jpSpielPlan1.changeSaison();
        m_jpSpielPlan2.changeSaison();
    }
}
