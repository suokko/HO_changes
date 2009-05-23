// %3482096464:de.hattrickorganizer.gui.playeroverview%
package de.hattrickorganizer.gui.playeroverview;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;

import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.Spieler;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.Helper;


/**
 * Alle Spieler der Gruppe werden einer Gruppe zugeordnet
 */
public class RemoveGruppenPanel extends de.hattrickorganizer.gui.templates.ImagePanel
    implements ActionListener
{

	private static final long serialVersionUID = 3606384591123088694L;
	
	//~ Instance fields ----------------------------------------------------------------------------

    private final JButton doButton = new JButton(new ImageIcon(Helper.loadImage("gui/bilder/Taktik_Offensiv.png")));
    private final JButton m_jbDrucken = new JButton(new ImageIcon(Helper.loadImage("gui/bilder/Drucken.png")));
    private final JToggleButton aGruppe = new JToggleButton(new ImageIcon(Helper.makeGray(Helper
                                                                              .loadImage("gui/bilder/smilies/A-Team.png"),
                                                                              0.5f)));
    private final JToggleButton aGruppe2 = new JToggleButton(new ImageIcon(Helper.makeGray(Helper
                                                                               .loadImage("gui/bilder/smilies/A-Team.png"),
                                                                               0.5f)));
    private final JToggleButton bGruppe = new JToggleButton(new ImageIcon(Helper.makeGray(Helper
                                                                              .loadImage("gui/bilder/smilies/B-Team.png"),
                                                                              0.5f)));
    private final JToggleButton bGruppe2 = new JToggleButton(new ImageIcon(Helper.makeGray(Helper
                                                                               .loadImage("gui/bilder/smilies/B-Team.png"),
                                                                               0.5f)));
    private final JToggleButton cGruppe = new JToggleButton(new ImageIcon(Helper.makeGray(Helper
                                                                              .loadImage("gui/bilder/smilies/C-Team.png"),
                                                                              0.5f)));
    private final JToggleButton cGruppe2 = new JToggleButton(new ImageIcon(Helper.makeGray(Helper
                                                                               .loadImage("gui/bilder/smilies/C-Team.png"),
                                                                               0.5f)));
    private final JToggleButton dGruppe = new JToggleButton(new ImageIcon(Helper.makeGray(Helper
                                                                              .loadImage("gui/bilder/smilies/D-Team.png"),
                                                                              0.5f)));
    private final JToggleButton dGruppe2 = new JToggleButton(new ImageIcon(Helper.makeGray(Helper
                                                                               .loadImage("gui/bilder/smilies/D-Team.png"),
                                                                               0.5f)));
    private final JToggleButton eGruppe = new JToggleButton(new ImageIcon(Helper.makeGray(Helper
                                                                              .loadImage("gui/bilder/smilies/E-Team.png"),
                                                                              0.5f)));
    private final JToggleButton eGruppe2 = new JToggleButton(new ImageIcon(Helper.makeGray(Helper
                                                                               .loadImage("gui/bilder/smilies/E-Team.png"),
                                                                               0.5f)));
    private final JToggleButton noGruppe = new JToggleButton(new ImageIcon(Helper.makeGray(Helper
                                                                               .loadImage("gui/bilder/smilies/No-Team.png"),
                                                                               0.5f)));
    private final JToggleButton noGruppe2 = new JToggleButton(new ImageIcon(Helper.makeGray(Helper
                                                                                .loadImage("gui/bilder/smilies/No-Team.png"),
                                                                                0.5f)));
    private PlayerOverviewTable m_clTable;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new RemoveGruppenPanel object.
     *
     * @param spielerTable TODO Missing Constructuor Parameter Documentation
     */
    public RemoveGruppenPanel(PlayerOverviewTable spielerTable) {
        m_clTable = spielerTable;
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     */
    public final void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(doButton)) {
            gruppenMarkierung();
        } else if (e.getSource().equals(m_jbDrucken)) {
            drucken();
        }

        //Von beiden Gruppen ein Button selektiert // Nach gruppenMarkierung werden die Button wieder unselected
        if ((getSelectedButton(true) != null) && (getSelectedButton(false) != null)) {
            doButton.setEnabled(true);
        } else {
            doButton.setEnabled(false);
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param button TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private String getName4Button(JToggleButton button) {
        if (button.equals(noGruppe) || button.equals(noGruppe2)) {
            return "";
        } else if (button.equals(aGruppe) || button.equals(aGruppe2)) {
            return "A-Team.png";
        } else if (button.equals(bGruppe) || button.equals(bGruppe2)) {
            return "B-Team.png";
        } else if (button.equals(cGruppe) || button.equals(cGruppe2)) {
            return "C-Team.png";
        } else if (button.equals(dGruppe) || button.equals(dGruppe2)) {
            return "D-Team.png";
        } else if (button.equals(eGruppe) || button.equals(eGruppe2)) {
            return "E-Team.png";
        } else {
            return "";
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param obereReihe TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private JToggleButton getSelectedButton(boolean obereReihe) {
        if (obereReihe) {
            if (noGruppe.isSelected()) {
                return noGruppe;
            } else if (aGruppe.isSelected()) {
                return aGruppe;
            } else if (bGruppe.isSelected()) {
                return bGruppe;
            } else if (cGruppe.isSelected()) {
                return cGruppe;
            } else if (dGruppe.isSelected()) {
                return dGruppe;
            } else if (eGruppe.isSelected()) {
                return eGruppe;
            }
        } else {
            if (noGruppe2.isSelected()) {
                return noGruppe2;
            } else if (aGruppe2.isSelected()) {
                return aGruppe2;
            } else if (bGruppe2.isSelected()) {
                return bGruppe2;
            } else if (cGruppe2.isSelected()) {
                return cGruppe2;
            } else if (dGruppe2.isSelected()) {
                return dGruppe2;
            } else if (eGruppe2.isSelected()) {
                return eGruppe2;
            }
        }

        return null;
    }

    /**
     * Drucken der Spielerübersicht
     */
    private void drucken() {
        try {
            //Damit nur bestimmte Spalten gedruckt werden ist eine spezielle Tabelle notwendig.
            //Das Scrollpane benötigt man, damit die Spaltenbeschriftung auch angezeigt wird.
            final SpielerUebersichtPrintTable table = new SpielerUebersichtPrintTable(m_clTable);
            final JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(table.getPreferredSize().width + 10,
                                                      table.getPreferredSize().height + 70));
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
            scrollPane.getViewport().setBackground(Color.white);

            final de.hattrickorganizer.gui.print.PrintController printController = de.hattrickorganizer.gui.print.PrintController
                                                                                   .getInstance();

            final java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            final String titel = de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Spieleruebersicht")
                                 + " - "
                                 + de.hattrickorganizer.model.HOVerwaltung.instance().getModel()
                                                                          .getBasics().getTeamName()
                                 + " - "
                                 + java.text.DateFormat.getDateTimeInstance().format(calendar
                                                                                     .getTime());
            printController.add(new de.hattrickorganizer.gui.print.ComponentPrintObject(printController
                                                                                        .getPf(),
                                                                                        titel,
                                                                                        scrollPane,
                                                                                        de.hattrickorganizer.gui.print.ComponentPrintObject.NICHTSICHTBAR));

            printController.print();
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),e);
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    private void gruppenMarkierung() {
        //Von beiden Gruppen ein Button selektiert
        if ((getSelectedButton(true) != null) && (getSelectedButton(false) != null)) {
            final Vector<Spieler> alleSpieler = HOVerwaltung.instance().getModel().getAllSpieler();
            final String suchName = getName4Button(getSelectedButton(true));
            final String ersatzName = getName4Button(getSelectedButton(false));

            for (int i = 0; i < alleSpieler.size(); i++) {
                final Spieler spieler = alleSpieler.get(i);

                //Spieler in der Gruppe
                if (spieler.getTeamInfoSmilie().equals(suchName)) {
                    spieler.setTeamInfoSmilie(ersatzName);
                }
            }

            de.hattrickorganizer.gui.HOMainFrame.instance().getAufstellungsPanel().update();

            //gui.RefreshManager.instance ().doRefresh ();
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(4, 4, 4, 4);

        setLayout(layout);

        final ButtonGroup bg = new ButtonGroup();
        final String tooltipFrom = HOVerwaltung.instance().getLanguageString("tt_Gruppe_von");
        final String tooltipTo = HOVerwaltung.instance().getLanguageString("tt_Gruppe_nach");
        
        initButton(noGruppe,tooltipFrom,"gui/bilder/smilies/No-Team.png");
        constraints.gridx = 0;
        constraints.gridy = 0;
        layout.setConstraints(noGruppe, constraints);
        bg.add(noGruppe);
        add(noGruppe);
        initButton(aGruppe,tooltipFrom,"gui/bilder/smilies/A-Team.png");
        constraints.gridx = 1;
        constraints.gridy = 0;
        layout.setConstraints(aGruppe, constraints);
        bg.add(aGruppe);
        add(aGruppe);
        initButton(bGruppe,tooltipFrom,"gui/bilder/smilies/B-Team.png");
        constraints.gridx = 2;
        constraints.gridy = 0;
        layout.setConstraints(bGruppe, constraints);
        bg.add(bGruppe);
        add(bGruppe);
        initButton(cGruppe,tooltipFrom,"gui/bilder/smilies/C-Team.png");
        constraints.gridx = 3;
        constraints.gridy = 0;
        layout.setConstraints(cGruppe, constraints);
        bg.add(cGruppe);
        add(cGruppe);
        initButton(dGruppe,tooltipFrom,"gui/bilder/smilies/D-Team.png");       
        constraints.gridx = 4;
        constraints.gridy = 0;
        layout.setConstraints(dGruppe, constraints);
        bg.add(dGruppe);
        add(dGruppe);
        initButton(eGruppe,tooltipFrom,"gui/bilder/smilies/E-Team.png");
        constraints.gridx = 5;
        constraints.gridy = 0;
        layout.setConstraints(eGruppe, constraints);
        bg.add(eGruppe);
        add(eGruppe);

        final ButtonGroup bg2 = new ButtonGroup();

        initButton(noGruppe2,tooltipTo,"gui/bilder/smilies/No-Team.png");
        constraints.gridx = 0;
        constraints.gridy = 1;
        layout.setConstraints(noGruppe2, constraints);
        bg2.add(noGruppe2);
        add(noGruppe2);
        initButton(aGruppe2,tooltipTo,"gui/bilder/smilies/A-Team.png");
        constraints.gridx = 1;
        constraints.gridy = 1;
        layout.setConstraints(aGruppe2, constraints);
        bg2.add(aGruppe2);
        add(aGruppe2);
        initButton(bGruppe2,tooltipTo,"gui/bilder/smilies/B-Team.png");       
        constraints.gridx = 2;
        constraints.gridy = 1;
        layout.setConstraints(bGruppe2, constraints);
        bg2.add(bGruppe2);
        add(bGruppe2);
        initButton(cGruppe2,tooltipTo,"gui/bilder/smilies/C-Team.png");
        constraints.gridx = 3;
        constraints.gridy = 1;
        layout.setConstraints(cGruppe2, constraints);
        bg2.add(cGruppe2);
        add(cGruppe2);
        initButton(dGruppe2,tooltipTo,"gui/bilder/smilies/D-Team.png");
        constraints.gridx = 4;
        constraints.gridy = 1;
        layout.setConstraints(dGruppe2, constraints);
        bg2.add(dGruppe2);
        add(dGruppe2);
        initButton(eGruppe2,tooltipTo,"gui/bilder/smilies/E-Team.png");
        constraints.gridx = 5;
        constraints.gridy = 1;
        layout.setConstraints(eGruppe2, constraints);
        bg2.add(eGruppe2);
        add(eGruppe2);

        doButton.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Gruppe_wechseln"));
        doButton.setPreferredSize(new Dimension(28, 28));
        doButton.setEnabled(false);
        doButton.addActionListener(this);
        constraints.gridx = 6;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        layout.setConstraints(doButton, constraints);
        add(doButton);

        m_jbDrucken.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Spieler_drucken"));
        m_jbDrucken.setPreferredSize(new Dimension(28, 28));
        m_jbDrucken.addActionListener(this);
        constraints.gridx = 7;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        layout.setConstraints(m_jbDrucken, constraints);
        add(m_jbDrucken);
    }
    
    private void initButton(JToggleButton button,String tooltip,String iconPath){
    	button.setToolTipText(tooltip);
    	button.setPreferredSize(new Dimension(16, 16));
    	button.setSelectedIcon(new ImageIcon(Helper.loadImage(iconPath)));
    	button.addActionListener(this);
    }
}
