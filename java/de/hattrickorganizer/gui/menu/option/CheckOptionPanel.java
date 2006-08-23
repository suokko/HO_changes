// %1942107811:de.hattrickorganizer.gui.menu.option%
package de.hattrickorganizer.gui.menu.option;

import de.hattrickorganizer.gui.templates.ImagePanel;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;


/**
 * Alle weiteren Optionen, die Keine Formeln sind
 */
public final class CheckOptionPanel extends ImagePanel
    implements javax.swing.event.ChangeListener, java.awt.event.ItemListener
{
    //~ Static fields/initializers -----------------------------------------------------------------

	private JCheckBox m_jchHONews;
	private JCheckBox m_jchHOUsers;    
    private JCheckBox m_jchLogout;
    private JCheckBox m_jchShowSaveDialog;
    private JCheckBox m_jchUpdateCheck;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SonstigeOptionenPanel object.
     */
    public CheckOptionPanel() {
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------


    /**
     * TODO Missing Method Documentation
     *
     * @param itemEvent TODO Missing Method Parameter Documentation
     */
    public final void itemStateChanged(java.awt.event.ItemEvent itemEvent) {        
        gui.UserParameter.instance().newsCheck = m_jchHONews.isSelected();
        gui.UserParameter.instance().updateCheck = m_jchUpdateCheck.isSelected();
        gui.UserParameter.instance().logoutOnExit = m_jchLogout.isSelected();
        gui.UserParameter.instance().showHRFSaveDialog = m_jchShowSaveDialog.isSelected();
        gui.UserParameter.instance().userCheck = m_jchHOUsers.isSelected();
    }

	public void stateChanged(ChangeEvent arg0) {
				
	}


    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        setLayout(new GridLayout(10, 1, 4, 4));

		m_jchHONews = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
																		   .getProperty("NewsCheck"));
		m_jchHONews.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
																		  .getProperty("tt_Optionen_NewsCheck"));
		m_jchHONews.setOpaque(false);
		m_jchHONews.setSelected(gui.UserParameter.instance().newsCheck);
		m_jchHONews.addItemListener(this);
		add(m_jchHONews);

		m_jchHOUsers = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
																		   .getProperty("UsersCheck"));
		m_jchHOUsers.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
																		  .getProperty("tt_Optionen_UsersCheck"));
		m_jchHOUsers.setOpaque(false);
		m_jchHOUsers.setSelected(gui.UserParameter.instance().userCheck);
		m_jchHOUsers.addItemListener(this);
		add(m_jchHOUsers);


        m_jchUpdateCheck = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                .getResource()
                                                                                .getProperty("UpdateCheck"));
        m_jchUpdateCheck.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                               .getResource()
                                                                               .getProperty("tt_Optionen_UpdateCheck"));
        m_jchUpdateCheck.setOpaque(false);
        m_jchUpdateCheck.setSelected(gui.UserParameter.instance().updateCheck);
        m_jchUpdateCheck.addItemListener(this);
        add(m_jchUpdateCheck);

        m_jchLogout = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                           .getProperty("Logout_beim_Beenden"));
        m_jchLogout.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                          .getProperty("tt_Optionen_Logout_beim_Beenden"));
        m_jchLogout.setOpaque(false);
        m_jchLogout.setSelected(gui.UserParameter.instance().logoutOnExit);
        m_jchLogout.addItemListener(this);
        add(m_jchLogout);

        m_jchShowSaveDialog = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                   .getResource()
                                                                                   .getProperty("Show_SaveHRF_Dialog"));
        m_jchShowSaveDialog.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                  .getResource()
                                                                                  .getProperty("tt_Optionen_Show_SaveHRF_Dialog"));
        m_jchShowSaveDialog.setOpaque(false);
        m_jchShowSaveDialog.setSelected(gui.UserParameter.instance().showHRFSaveDialog);
        m_jchShowSaveDialog.addItemListener(this);
        add(m_jchShowSaveDialog);
        for(int i = 0; i < 5; i++) {
        	add(new JLabel(""));
        }
    }

}
