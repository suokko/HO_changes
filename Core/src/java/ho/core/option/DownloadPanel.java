package ho.core.option;



import ho.core.gui.comp.panel.ImagePanel;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;


/**
 * Checkboxes in Download Dialog checked or not 
 */
public final class DownloadPanel extends ImagePanel
    implements javax.swing.event.ChangeListener, java.awt.event.ItemListener
{
    //~ Static fields/initializers -----------------------------------------------------------------

	private static final long serialVersionUID = 1L;
	private JCheckBox m_jchXMLDownload;
	private JCheckBox m_jchCurrentMatchlist;    
    private JCheckBox m_jchFixtures;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new DownloadPanel object.
     */
    public DownloadPanel() {
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------


    /**
     * TODO Missing Method Documentation
     *
     * @param itemEvent TODO Missing Method Parameter Documentation
     */
    public final void itemStateChanged(java.awt.event.ItemEvent itemEvent) {        
        ho.core.model.UserParameter.temp().xmlDownload = m_jchXMLDownload.isSelected();
        ho.core.model.UserParameter.temp().fixtures = m_jchFixtures.isSelected();
        ho.core.model.UserParameter.temp().currentMatchlist = m_jchCurrentMatchlist.isSelected();
    }

	public void stateChanged(ChangeEvent arg0) {
				
	}


    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        setLayout(new GridLayout(10, 1, 4, 4));

		m_jchXMLDownload = new JCheckBox(ho.core.model.HOVerwaltung.instance().getLanguageString("HRFDownload"));
		m_jchXMLDownload.setToolTipText(ho.core.model.HOVerwaltung.instance().getLanguageString("tt_Download_XML"));
		m_jchXMLDownload.setOpaque(false);
		m_jchXMLDownload.setSelected(ho.core.model.UserParameter.temp().xmlDownload);
		m_jchXMLDownload.addItemListener(this);
		add(m_jchXMLDownload);

		m_jchCurrentMatchlist = new JCheckBox(ho.core.model.HOVerwaltung.instance().getLanguageString("AktuellerSpielplanDownload"));
		m_jchCurrentMatchlist.setToolTipText(ho.core.model.HOVerwaltung.instance().getLanguageString("tt_Download_AktuellerSpielplan"));
		m_jchCurrentMatchlist.setOpaque(false);
		m_jchCurrentMatchlist.setSelected(ho.core.model.UserParameter.temp().currentMatchlist);
		m_jchCurrentMatchlist.addItemListener(this);
		add(m_jchCurrentMatchlist);


        m_jchFixtures = new JCheckBox(ho.core.model.HOVerwaltung.instance().getLanguageString("FixturesDownload"));
        m_jchFixtures.setToolTipText(ho.core.model.HOVerwaltung.instance().getLanguageString("tt_Download_Ligatabelle"));
        m_jchFixtures.setOpaque(false);
        m_jchFixtures.setSelected(ho.core.model.UserParameter.temp().fixtures);
        m_jchFixtures.addItemListener(this);
        add(m_jchFixtures);


        for(int i = 0; i < 3; i++) {
        	add(new JLabel(""));
        }
    }

}