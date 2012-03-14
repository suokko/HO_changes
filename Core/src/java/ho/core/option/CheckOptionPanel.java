// %1942107811:de.hattrickorganizer.gui.menu.option%
package ho.core.option;


import ho.core.gui.comp.panel.ImagePanel;

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

	private static final long serialVersionUID = 1L;
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
        ho.core.model.UserParameter.temp().newsCheck = m_jchHONews.isSelected();
        ho.core.model.UserParameter.temp().updateCheck = m_jchUpdateCheck.isSelected();
        ho.core.model.UserParameter.temp().logoutOnExit = m_jchLogout.isSelected();
        ho.core.model.UserParameter.temp().showHRFSaveDialog = m_jchShowSaveDialog.isSelected();
        ho.core.model.UserParameter.temp().userCheck = m_jchHOUsers.isSelected();
    }

	public void stateChanged(ChangeEvent arg0) {
				
	}


    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        setLayout(new GridLayout(10, 1, 4, 4));

		m_jchHONews = new JCheckBox(ho.core.model.HOVerwaltung.instance().getLanguageString("NewsCheck"));
		m_jchHONews.setToolTipText(ho.core.model.HOVerwaltung.instance().getLanguageString("tt_Optionen_NewsCheck"));
		m_jchHONews.setOpaque(false);
		m_jchHONews.setSelected(ho.core.model.UserParameter.temp().newsCheck);
		m_jchHONews.addItemListener(this);
		add(m_jchHONews);

		m_jchHOUsers = new JCheckBox(ho.core.model.HOVerwaltung.instance().getLanguageString("UsersCheck"));
		m_jchHOUsers.setToolTipText(ho.core.model.HOVerwaltung.instance().getLanguageString("tt_Optionen_UsersCheck"));
		m_jchHOUsers.setOpaque(false);
		m_jchHOUsers.setSelected(ho.core.model.UserParameter.temp().userCheck);
		m_jchHOUsers.addItemListener(this);
		add(m_jchHOUsers);


        m_jchUpdateCheck = new JCheckBox(ho.core.model.HOVerwaltung.instance().getLanguageString("UpdateCheck"));
        m_jchUpdateCheck.setToolTipText(ho.core.model.HOVerwaltung.instance().getLanguageString("tt_Optionen_UpdateCheck"));
        m_jchUpdateCheck.setOpaque(false);
        m_jchUpdateCheck.setSelected(ho.core.model.UserParameter.temp().updateCheck);
        m_jchUpdateCheck.addItemListener(this);
        add(m_jchUpdateCheck);

        m_jchLogout = new JCheckBox(ho.core.model.HOVerwaltung.instance().getLanguageString("Logout_beim_Beenden"));
        m_jchLogout.setToolTipText(ho.core.model.HOVerwaltung.instance().getLanguageString("tt_Optionen_Logout_beim_Beenden"));
        m_jchLogout.setOpaque(false);
        m_jchLogout.setSelected(ho.core.model.UserParameter.temp().logoutOnExit);
        m_jchLogout.addItemListener(this);
        add(m_jchLogout);

        m_jchShowSaveDialog = new JCheckBox(ho.core.model.HOVerwaltung.instance().getLanguageString("Show_SaveHRF_Dialog"));
        m_jchShowSaveDialog.setToolTipText(ho.core.model.HOVerwaltung.instance().getLanguageString("tt_Optionen_Show_SaveHRF_Dialog"));
        m_jchShowSaveDialog.setOpaque(false);
        m_jchShowSaveDialog.setSelected(ho.core.model.UserParameter.temp().showHRFSaveDialog);
        m_jchShowSaveDialog.addItemListener(this);
        add(m_jchShowSaveDialog);
        for(int i = 0; i < 5; i++) {
        	add(new JLabel(""));
        }
    }

}
