// %3860481451:de.hattrickorganizer.gui.lineup%
package de.hattrickorganizer.gui.lineup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import plugins.ILineUp;
import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.model.AufstellungCBItem;
import de.hattrickorganizer.gui.model.CBItem;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.model.HOModel;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.Helper;


/**
 * Die automatische Aufstellung wird hier konfiguriert und gestartet
 */
public class AufstellungsAssistentPanel extends ImagePanel implements ActionListener, ItemListener {
    //~ Instance fields ----------------------------------------------------------------------------

    private JButton m_jbElfmeter = new JButton(new ImageIcon(Helper.changeColor(Helper.loadImage("gui/bilder/credits/Ball.png"),
                                                                                                           Color.red,
                                                                                                           Color.white)
                                                                                              .getScaledInstance(24,
                                                                                                                 24,
                                                                                                                 Image.SCALE_SMOOTH)));
    private final JButton m_jbLoeschen 	= new JButton(new ImageIcon(Helper.loadImage("gui/bilder/Assist_leeren.png")));
    private final JButton m_jbOK 			= new JButton(new ImageIcon(Helper.loadImage("gui/bilder/Assist_start.png")));
    private final JButton m_jbReserveLoeschen = new JButton(new ImageIcon(Helper.loadImage("gui/bilder/Assist_reserveleeren.png")));
    private final JCheckBox m_jchForm 	= new JCheckBox(HOVerwaltung.instance().getLanguageString("Form_beruecksichtigen"),
                                                gui.UserParameter.instance().aufstellungsAssistentPanel_form);
    private final JCheckBox m_jchGesperrte = new JCheckBox(HOVerwaltung.instance().getLanguageString("Gesperrte_aufstellen"),
                                                     gui.UserParameter.instance().aufstellungsAssistentPanel_gesperrt);
    private final JCheckBox m_jchIdealPosition = new JCheckBox(HOVerwaltung.instance().getLanguageString("Idealposition_zuerst"),
                                                         gui.UserParameter.instance().aufstellungsAssistentPanel_idealPosition);
    private final JCheckBox m_jchLast		= new JCheckBox(HOVerwaltung.instance().getLanguageString("NotLast_aufstellen"),
                                                gui.UserParameter.instance().aufstellungsAssistentPanel_notLast);
    private final JCheckBox m_jchListBoxGruppenFilter = new JCheckBox(HOVerwaltung.instance().getLanguageString("ListBoxGruppenFilter"),
                                                                gui.UserParameter.instance().aufstellungsAssistentPanel_cbfilter);
    private final JCheckBox m_jchNot 		= new JCheckBox(HOVerwaltung.instance().getLanguageString("Not"),
                                               gui.UserParameter.instance().aufstellungsAssistentPanel_not);
    private final JCheckBox m_jchVerletzte = new JCheckBox(HOVerwaltung.instance().getLanguageString("Verletze_aufstellen"),
                                                     gui.UserParameter.instance().aufstellungsAssistentPanel_verletzt);
    private final JComboBox m_jcbGruppe 	= new JComboBox(Helper.TEAMSMILIES);
    private final JComboBox m_jcbWetter 	= new JComboBox(Helper.WETTER);
    private final CBItem[] REIHENFOLGE = {
                                       new CBItem(HOVerwaltung.instance().getLanguageString("AW-MF-ST"),
                                    		   ILineUp.AW_MF_ST),
                                                  
                                       new CBItem(HOVerwaltung.instance().getLanguageString("AW-ST-MF"),
                                    		   ILineUp.AW_ST_MF),
                                                  
                                       new CBItem(HOVerwaltung.instance().getLanguageString("MF-AW-ST"),
                                    		   ILineUp.MF_AW_ST),
                                                  
                                       new CBItem(HOVerwaltung.instance().getLanguageString("MF-ST-AW"),
                                    		   ILineUp.MF_ST_AW),
                                                  
                                       new CBItem(HOVerwaltung.instance().getLanguageString("ST-AW-MF"),
                                    		   ILineUp.ST_AW_MF),
                                                  
                                       new CBItem(HOVerwaltung.instance().getLanguageString("ST-MF-AW"),
                                    		   ILineUp.ST_MF_AW)
                                   };
    private JComboBox m_jcbReihenfolge = new JComboBox(REIHENFOLGE);

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new AufstellungsAssistentPanel object.
     */
    public AufstellungsAssistentPanel() {
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isExcludeLastMatch() {
        return m_jchLast.isSelected();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isFormBeruecksichtigen() {
        return m_jchForm.isSelected();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isGesperrtIgnorieren() {
        return m_jchGesperrte.isSelected();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String getGruppe() {
        return m_jcbGruppe.getSelectedItem().toString();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isGruppenFilter() {
        return m_jchListBoxGruppenFilter.isSelected();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isIdealPositionZuerst() {
        return m_jchIdealPosition.isSelected();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isNotGruppe() {
        return m_jchNot.isSelected();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getReihenfolge() {
        return ((CBItem) m_jcbReihenfolge.getSelectedItem()).getId();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isVerletztIgnorieren() {
        return m_jchVerletzte.isSelected();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getWetter() {
        return ((CBItem) m_jcbWetter.getSelectedItem()).getId();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param actionEvent TODO Missing Method Parameter Documentation
     */
    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        final HOModel hoModel 		= HOVerwaltung.instance().getModel();
        final HOMainFrame mainFrame = de.hattrickorganizer.gui.HOMainFrame.instance();
        
        if (actionEvent.getSource().equals(m_jbLoeschen)) {
            //Alle Positionen leeren
            hoModel.getAufstellung().resetAufgestellteSpieler();
            hoModel.getAufstellung().setKicker(0);
            hoModel.getAufstellung().setKapitaen(0);
            de.hattrickorganizer.gui.HOMainFrame.instance().getInfoPanel().setLangInfoText(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Aufstellung_geloescht"));
            mainFrame.getAufstellungsPanel().update();

            //gui.RefreshManager.instance ().doRefresh ();
        } else if (actionEvent.getSource().equals(m_jbReserveLoeschen)) {
            hoModel.getAufstellung().resetReserveBank();
            mainFrame.getAufstellungsPanel().update();

            //gui.RefreshManager.instance ().doRefresh ();
        } else if (actionEvent.getSource().equals(m_jbOK)) {
            final java.util.Vector vSpieler = new java.util.Vector();
            final java.util.Vector alleSpieler = hoModel.getAllSpieler();

            for (int i = 0; i < alleSpieler.size(); i++) {
                final de.hattrickorganizer.model.Spieler spieler = (de.hattrickorganizer.model.Spieler) alleSpieler
                                                                   .get(i);

                //Wenn der Spieler spielberechtigt ist und entweder alle Gruppen aufgestellt werden sollen, oder genau die zu der der Spieler gehört
                if (spieler.isSpielberechtigt()
                    && (((this.getGruppe().trim().equals("")
                    || spieler.getTeamInfoSmilie().equals(this.getGruppe()))
                    && !m_jchNot.isSelected())
                    || (!spieler.getTeamInfoSmilie().equals(this.getGruppe())
                    && m_jchNot.isSelected()))) {
                    boolean include = true;
                    final AufstellungCBItem lastLineup = AufstellungsVergleichHistoryPanel
                                                         .getLastLineup();

                    if (m_jchLast.isSelected()
                        && (lastLineup != null)
                        && lastLineup.getAufstellung().isSpielerInAnfangsElf(spieler.getSpielerID())) {
                        include = false;
                        HOLogger.instance().log(getClass(),"Exclude: " + spieler.getName());
                    }

                    if (include) {
                        vSpieler.add(spieler);
                    }
                }
            }

            hoModel.getAufstellung().doAufstellung(vSpieler,
                                                   (byte) ((CBItem) m_jcbReihenfolge
                                                           .getSelectedItem()).getId(),
                                                   m_jchForm.isSelected(),
                                                   m_jchIdealPosition.isSelected(),
                                                   m_jchVerletzte.isSelected(),
                                                   m_jchGesperrte.isSelected(),
                                                   gui.UserParameter.instance().WetterEffektBonus,
                                                   HOMainFrame.getWetter());
            mainFrame.getInfoPanel().setLangInfoText(HOVerwaltung.instance().getLanguageString("Autoaufstellung_fertig"));
            mainFrame.getAufstellungsPanel().update();

            //gui.RefreshManager.instance ().doRefresh ();
        } else if (actionEvent.getSource().equals(m_jbElfmeter)) {
            new ElfmeterSchuetzenDialog(mainFrame);
        } else if (actionEvent.getSource().equals(m_jchListBoxGruppenFilter)
                   || actionEvent.getSource().equals(m_jchLast)) {
        	mainFrame.getAufstellungsPanel().getAufstellungsPositionsPanel().refresh();
        } else if (actionEvent.getSource().equals(m_jcbGruppe)
                   || actionEvent.getSource().equals(m_jchNot)) {
            //Nur wenn Filter aktiv
            if (m_jchListBoxGruppenFilter.isSelected()) {
            	mainFrame.getAufstellungsPanel().getAufstellungsPositionsPanel().refresh();
            }
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     */
    public final void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            //Wetter -> Refresh
            de.hattrickorganizer.gui.HOMainFrame.instance().getAufstellungsPanel().update();

            //gui.RefreshManager.instance ().doRefresh ();
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel panel = new ImagePanel();
        panel.setLayout(new GridLayout(9, 1));

        panel.setOpaque(false);

        final HOVerwaltung hoVerwaltung = de.hattrickorganizer.model.HOVerwaltung.instance();

        m_jcbWetter.setToolTipText(hoVerwaltung.getLanguageString("tt_AufstellungsAssistent_Wetter"));
        m_jcbWetter.setSelectedIndex(1);
        m_jcbWetter.setPreferredSize(new Dimension(50, 20));
        m_jcbWetter.setBackground(Color.white);
        m_jcbWetter.setRenderer(new de.hattrickorganizer.gui.model.WetterRenderer());
        m_jcbWetter.addItemListener(this);
        panel.add(m_jcbWetter);

        final JPanel panel2 = new JPanel(new BorderLayout());
        panel2.setOpaque(false);
        m_jchNot.setToolTipText(hoVerwaltung.getLanguageString("tt_AufstellungsAssistent_Not"));
        m_jchNot.setOpaque(false);
        m_jchNot.addActionListener(this);
        panel2.add(m_jchNot, BorderLayout.WEST);
        m_jcbGruppe.setToolTipText(hoVerwaltung.getLanguageString("tt_AufstellungsAssistent_Gruppe"));
        m_jcbGruppe.setSelectedItem(gui.UserParameter.instance().aufstellungsAssistentPanel_gruppe);
        m_jcbGruppe.setBackground(Color.white);
        m_jcbGruppe.setRenderer(new de.hattrickorganizer.gui.model.SmilieRenderer());
        m_jcbGruppe.addActionListener(this);
        panel2.add(m_jcbGruppe, BorderLayout.CENTER);
        panel.add(panel2);

        m_jchListBoxGruppenFilter.setToolTipText(hoVerwaltung.getLanguageString("tt_AufstellungsAssistent_GruppeFilter"));
        m_jchListBoxGruppenFilter.setOpaque(false);
        m_jchListBoxGruppenFilter.addActionListener(this);
        panel.add(m_jchListBoxGruppenFilter);

        m_jcbReihenfolge.setToolTipText(hoVerwaltung.getLanguageString("tt_AufstellungsAssistent_Reihenfolge"));
        de.hattrickorganizer.tools.Helper.markierenComboBox(m_jcbReihenfolge,
                                                            gui.UserParameter.instance().aufstellungsAssistentPanel_reihenfolge);
        panel.add(m_jcbReihenfolge);
        m_jchIdealPosition.setToolTipText(hoVerwaltung.getLanguageString("tt_AufstellungsAssistent_Idealposition"));
        m_jchIdealPosition.setOpaque(false);
        panel.add(m_jchIdealPosition);
        m_jchForm.setToolTipText(hoVerwaltung.getLanguageString("tt_AufstellungsAssistent_Form"));
        m_jchForm.setOpaque(false);
        panel.add(m_jchForm);
        m_jchVerletzte.setToolTipText(hoVerwaltung.getLanguageString("tt_AufstellungsAssistent_Verletzte"));
        m_jchVerletzte.setOpaque(false);
        panel.add(m_jchVerletzte);
        m_jchGesperrte.setToolTipText(hoVerwaltung.getLanguageString("tt_AufstellungsAssistent_Gesperrte"));
        m_jchGesperrte.setOpaque(false);
        panel.add(m_jchGesperrte);
        m_jchLast.setToolTipText(hoVerwaltung.getLanguageString("tt_AufstellungsAssistent_NotLast"));
        m_jchLast.setOpaque(false);
        m_jchLast.addActionListener(this);
        panel.add(m_jchLast);

        add(panel, BorderLayout.CENTER);

        panel = new JPanel();
        panel.setOpaque(false);
        m_jbLoeschen.setPreferredSize(new Dimension(28, 28));
        m_jbLoeschen.setToolTipText(hoVerwaltung.getLanguageString("Aufstellung_leeren"));
        m_jbLoeschen.addActionListener(this);
        panel.add(m_jbLoeschen);
        m_jbReserveLoeschen.setPreferredSize(new Dimension(28, 28));
        m_jbReserveLoeschen.setToolTipText(hoVerwaltung.getLanguageString("Reservebank_leeren"));
        m_jbReserveLoeschen.addActionListener(this);
        panel.add(m_jbReserveLoeschen);
        m_jbOK.setPreferredSize(new Dimension(28, 28));
        m_jbOK.setToolTipText(hoVerwaltung.getLanguageString("Assistent_starten"));
        m_jbOK.addActionListener(this);
        panel.add(m_jbOK);
        m_jbElfmeter.setPreferredSize(new Dimension(28, 28));
        m_jbElfmeter.setToolTipText(hoVerwaltung.getLanguageString("Elfmeterschuetzen"));
        m_jbElfmeter.addActionListener(this);
        panel.add(m_jbElfmeter);
        add(panel, BorderLayout.SOUTH);
    }
}
