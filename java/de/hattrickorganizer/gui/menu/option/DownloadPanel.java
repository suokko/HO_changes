package de.hattrickorganizer.gui.menu.option;


import de.hattrickorganizer.gui.templates.ImagePanel;

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
        gui.UserParameter.temp().xmlDownload = m_jchXMLDownload.isSelected();
        gui.UserParameter.temp().fixtures = m_jchFixtures.isSelected();
        gui.UserParameter.temp().currentMatchlist = m_jchCurrentMatchlist.isSelected();
    }

	public void stateChanged(ChangeEvent arg0) {
				
	}


    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        setLayout(new GridLayout(10, 1, 4, 4));

		m_jchXMLDownload = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
																		   .getProperty("HRFDownload"));
		m_jchXMLDownload.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
																		  .getProperty("tt_Download_XML"));
		m_jchXMLDownload.setOpaque(false);
		m_jchXMLDownload.setSelected(gui.UserParameter.temp().xmlDownload);
		m_jchXMLDownload.addItemListener(this);
		add(m_jchXMLDownload);

		m_jchCurrentMatchlist = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
																		   .getProperty("AktuellerSpielplanDownload"));
		m_jchCurrentMatchlist.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
																		  .getProperty("tt_Download_AktuellerSpielplan"));
		m_jchCurrentMatchlist.setOpaque(false);
		m_jchCurrentMatchlist.setSelected(gui.UserParameter.temp().currentMatchlist);
		m_jchCurrentMatchlist.addItemListener(this);
		add(m_jchCurrentMatchlist);


        m_jchFixtures = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                           .getProperty("FixturesDownload"));
        m_jchFixtures.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                          .getProperty("tt_Download_Ligatabelle"));
        m_jchFixtures.setOpaque(false);
        m_jchFixtures.setSelected(gui.UserParameter.temp().fixtures);
        m_jchFixtures.addItemListener(this);
        add(m_jchFixtures);


        for(int i = 0; i < 3; i++) {
        	add(new JLabel(""));
        }
    }

}