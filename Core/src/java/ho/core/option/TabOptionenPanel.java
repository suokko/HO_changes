// %3158012967:de.hattrickorganizer.gui.menu.option%
package ho.core.option;


import ho.core.gui.comp.panel.ImagePanel;

import java.awt.GridLayout;
import java.awt.event.ItemEvent;

import javax.swing.JCheckBox;


/**
 * Alle weiteren Optionen, die Keine Formeln sind
 */
final class TabOptionenPanel extends ImagePanel implements java.awt.event.ItemListener {
    //~ Instance fields ----------------------------------------------------------------------------

	private static final long serialVersionUID = 1L;
//	private JCheckBox m_jchArenasizer;
    private JCheckBox m_jchAufstellung;
    private JCheckBox m_jchInformation;
    private JCheckBox m_jchLigatabelle;
    private JCheckBox m_jchSpiele;
    private JCheckBox m_jchSpielerAnalyse;

    //private ComboBoxPanel       m_jcbHTIP           = null;
    private JCheckBox m_jchSpieleruebersicht;
    private JCheckBox m_jchStatistik;


    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TabOptionenPanel object.
     */
    protected TabOptionenPanel() {
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------
    public final void itemStateChanged(ItemEvent itemEvent) {

    	// New Tab can not be shown immediately
    	if (itemEvent.getStateChange()== ItemEvent.SELECTED)
    		OptionManager.instance().setRestartNeeded();
    	// ReInit deletes the deselected Tab
    	if (itemEvent.getStateChange()== ItemEvent.DESELECTED)
    		OptionManager.instance().setReInitNeeded();

        ho.core.model.UserParameter.temp().tempTabSpieleruebersicht = !m_jchSpieleruebersicht.isSelected();
        ho.core.model.UserParameter.temp().tempTabAufstellung = !m_jchAufstellung.isSelected();
        ho.core.model.UserParameter.temp().tempTabLigatabelle = !m_jchLigatabelle.isSelected();
        ho.core.model.UserParameter.temp().tempTabSpiele = !m_jchSpiele.isSelected();
        ho.core.model.UserParameter.temp().tempTabSpieleranalyse = !m_jchSpielerAnalyse.isSelected();
        ho.core.model.UserParameter.temp().tempTabStatistik = !m_jchStatistik.isSelected();
        ho.core.model.UserParameter.temp().tempTabInformation = !m_jchInformation.isSelected();
    }

    private void initComponents() {
        setLayout(new GridLayout(9, 1, 4, 4));

        //        m_jcbHTIP= new ComboBoxPanel( model.HOVerwaltung.instance().getLanguageString( "Hattrick" ), HT_IP_ADRESSEN, 120 );
        //        m_jcbHTIP.setSelectedItem ( gui.UserParameter.temp ().htip );
        //        m_jcbHTIP.addItemListener ( this );
        //        add( m_jcbHTIP );
        m_jchSpieleruebersicht = new JCheckBox(ho.core.model.HOVerwaltung.instance().getLanguageString("Spieleruebersicht"));
        m_jchSpieleruebersicht.setToolTipText(ho.core.model.HOVerwaltung.instance().getLanguageString("tt_Optionen_TabManagement"));
        m_jchSpieleruebersicht.setOpaque(false);
        m_jchSpieleruebersicht.setSelected(!ho.core.model.UserParameter.temp().tempTabSpieleruebersicht);
        m_jchSpieleruebersicht.addItemListener(this);
        add(m_jchSpieleruebersicht);

        m_jchAufstellung = new JCheckBox(ho.core.model.HOVerwaltung.instance().getLanguageString("Aufstellung"));
        m_jchAufstellung.setToolTipText(ho.core.model.HOVerwaltung.instance().getLanguageString("tt_Optionen_TabManagement"));
        m_jchAufstellung.setOpaque(false);
        m_jchAufstellung.setSelected(!ho.core.model.UserParameter.temp().tempTabAufstellung);
        m_jchAufstellung.addItemListener(this);
        add(m_jchAufstellung);

        m_jchLigatabelle = new JCheckBox(ho.core.model.HOVerwaltung.instance().getLanguageString("Ligatabelle"));
        m_jchLigatabelle.setToolTipText(ho.core.model.HOVerwaltung.instance().getLanguageString("tt_Optionen_TabManagement"));
        m_jchLigatabelle.setOpaque(false);
        m_jchLigatabelle.setSelected(!ho.core.model.UserParameter.temp().tempTabLigatabelle);
        m_jchLigatabelle.addItemListener(this);
        add(m_jchLigatabelle);

        m_jchSpiele = new JCheckBox(ho.core.model.HOVerwaltung.instance().getLanguageString("Tab_Title_Matches"));
        m_jchSpiele.setToolTipText(ho.core.model.HOVerwaltung.instance().getLanguageString("tt_Optionen_TabManagement"));
        m_jchSpiele.setOpaque(false);
        m_jchSpiele.setSelected(!ho.core.model.UserParameter.temp().tempTabSpiele);
        m_jchSpiele.addItemListener(this);
        add(m_jchSpiele);

        m_jchSpielerAnalyse = new JCheckBox(ho.core.model.HOVerwaltung.instance().getLanguageString("SpielerAnalyse"));
        m_jchSpielerAnalyse.setToolTipText(ho.core.model.HOVerwaltung.instance().getLanguageString("tt_Optionen_TabManagement"));
        m_jchSpielerAnalyse.setOpaque(false);
        m_jchSpielerAnalyse.setSelected(!ho.core.model.UserParameter.temp().tempTabSpieleranalyse);
        m_jchSpielerAnalyse.addItemListener(this);
        add(m_jchSpielerAnalyse);

        m_jchStatistik = new JCheckBox(ho.core.model.HOVerwaltung.instance().getLanguageString("Statistik"));
        m_jchStatistik.setToolTipText(ho.core.model.HOVerwaltung.instance().getLanguageString("tt_Optionen_TabManagement"));
        m_jchStatistik.setOpaque(false);
        m_jchStatistik.setSelected(!ho.core.model.UserParameter.temp().tempTabStatistik);
        m_jchStatistik.addItemListener(this);
        add(m_jchStatistik);

        m_jchInformation = new JCheckBox(ho.core.model.HOVerwaltung.instance().getLanguageString("Verschiedenes"));
        m_jchInformation.setToolTipText(ho.core.model.HOVerwaltung.instance().getLanguageString("tt_Optionen_TabManagement"));
        m_jchInformation.setOpaque(false);
        m_jchInformation.setSelected(!ho.core.model.UserParameter.temp().tempTabInformation);
        m_jchInformation.addItemListener(this);
        add(m_jchInformation);

    }
}
