// %198737965:de.hattrickorganizer.gui.menu.option%
package ho.core.option;

import ho.core.gui.HOMainFrame;
import ho.core.gui.RefreshManager;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;
import ho.core.module.ModuleConfigPanel;
import ho.core.module.ModuleManager;
import ho.core.module.config.ModuleConfig;
import ho.core.net.login.LoginWaitDialog;
import ho.core.util.Helper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;



/**
 * Ein Dialog mit allen Optionen für HO
 */
public class OptionenDialog extends JDialog implements WindowListener, ActionListener {
    //~ Instance fields ----------------------------------------------------------------------------
	private static final long serialVersionUID = 1L;
	private FarbPanel m_jpFarben;
    private FormelPanel m_jpFormeln;
    private RatingOffsetPanel m_jpRatingOffset;
    private SonstigeOptionenPanel m_jpSonstigeOptionen;
    private CheckOptionPanel hoConnectionOptions;
//    private TabOptionenPanel m_jpTabOptionen;
    private TrainingsOptionenPanel m_jpTrainingsOptionen;
    private UserPanel m_jpUserOptionen;
    private UserColumnsPanel m_jpUserColumns;
    private DownloadPanel m_jpDownloadPanel;
    private JButton m_jbSave = new JButton(HOVerwaltung.instance().getLanguageString("ls.button.save"));
    private JButton m_jbCancel = new JButton(HOVerwaltung.instance().getLanguageString("ls.button.cancel"));
    private ImagePanel m_jpButtonPanel = new ImagePanel();

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new OptionenDialog object.
     *
     * @param owner TODO Missing Constructuor Parameter Documentation
     */
    public OptionenDialog(JFrame owner) {
        super(owner,
              HOVerwaltung.instance().getLanguageString("Optionen"),
              true);

        this.addWindowListener(this);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    //---------------unused-------------------------------------
    public void windowActivated(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public final void windowClosing(java.awt.event.WindowEvent windowEvent) {

    	ho.core.model.UserParameter.saveTempParameter();
    	ModuleConfig.instance().save();
		if (OptionManager.instance().isRestartNeeded()) {
	            Helper.showMessage(this, HOVerwaltung.instance().getLanguageString("NeustartErforderlich"),
	            		"", JOptionPane.INFORMATION_MESSAGE);
	    }

		if (OptionManager.instance().isReInitNeeded()) {
			final LoginWaitDialog waitdialog = new LoginWaitDialog(HOMainFrame.instance());
	        waitdialog.setVisible(true);
	        RefreshManager.instance().doReInit();
	        waitdialog.setVisible(false);
		}


		OptionManager.deleteInstance();
		setVisible(false);
		removeWindowListener(this);
		dispose();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowDeactivated(WindowEvent windowEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowDeiconified(WindowEvent windowEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowIconified(WindowEvent windowEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowOpened(WindowEvent windowEvent) {
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        setContentPane(new ImagePanel());
        getContentPane().setLayout(new BorderLayout());

        final JTabbedPane tabbedPane = new JTabbedPane();

        //Verschiedenes
        m_jpSonstigeOptionen = new SonstigeOptionenPanel();
        tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("Verschiedenes"),
                          new JScrollPane(m_jpSonstigeOptionen));

		tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("Module"),new JScrollPane(new ModuleConfigPanel()));

        //Farben
        m_jpFarben = new FarbPanel();
        tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("Farben"),
                          new JScrollPane(m_jpFarben));

        //Formeln
        m_jpFormeln = new FormelPanel();
        tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("Formeln"),
                          new JScrollPane(m_jpFormeln));

		//Rating Offset
		m_jpRatingOffset = new RatingOffsetPanel();
		tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("PredictionOffset"),
						  new JScrollPane(m_jpRatingOffset));

        //Training
        m_jpTrainingsOptionen = new TrainingsOptionenPanel();
        tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("Training"),
                          new JScrollPane(m_jpTrainingsOptionen));

        m_jpUserOptionen = new UserPanel();
        tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("Info.users"), new JScrollPane(m_jpUserOptionen));

		// HO Check
		hoConnectionOptions = new CheckOptionPanel();
		tabbedPane.addTab("HO Check",new JScrollPane(hoConnectionOptions));

		//Download
        m_jpDownloadPanel = new DownloadPanel();
        tabbedPane.addTab(ho.core.model.HOVerwaltung.instance().getLanguageString("Download"),
                          new JScrollPane(m_jpDownloadPanel));

//		 HO Check
		m_jpUserColumns = new UserColumnsPanel();
		tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("columns"),new JScrollPane(m_jpUserColumns));


        //Tabs der plugins
        for (int i = 0;
             (i < HOMainFrame.instance().getOptionPanelNames().size())
             && (i < HOMainFrame.instance().getOptionPanels().size());
             ++i) {
            tabbedPane.addTab(HOMainFrame.instance().getOptionPanelNames().get(i).toString(),
                              HOMainFrame.instance().getOptionPanels().get(i));
        }

        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        //Add Buttons
        m_jpButtonPanel.add(m_jbSave);
        m_jbSave.setFont(m_jbSave.getFont().deriveFont(Font.BOLD));
        m_jpButtonPanel.add(m_jbCancel);

        m_jbSave.addActionListener(this);
        m_jbCancel.addActionListener(this);

        getContentPane().add(m_jpButtonPanel,BorderLayout.SOUTH);

        if (HOMainFrame.instance().getToolkit().getScreenSize().height >= 700) {
            setSize(new Dimension(450, 700));
        } else {
            setSize(new Dimension(450, HOMainFrame.instance().getToolkit().getScreenSize().height - 50));
        }

        final Dimension size = HOMainFrame.instance().getToolkit().getScreenSize();

        if (size.width > this.getSize().width) {
            //Mittig positionieren
            this.setLocation((size.width / 2) - (this.getSize().width / 2),
                             (size.height / 2) - (this.getSize().height / 2));
        }

        this.setResizable(false);
    }

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(m_jbSave)) {
			ho.core.model.UserParameter.saveTempParameter();
			ModuleManager.instance().saveTemp();
			if (OptionManager.instance().isRestartNeeded()) {
		            Helper.showMessage(this, HOVerwaltung.instance().getLanguageString("NeustartErforderlich"),
		            		"", JOptionPane.INFORMATION_MESSAGE);
		    }
			if (OptionManager.instance().isReInitNeeded()) {
				final LoginWaitDialog waitdialog = new LoginWaitDialog(HOMainFrame.instance());
		        waitdialog.setVisible(true);
		        RefreshManager.instance().doReInit();
		        waitdialog.setVisible(false);
			}
			//if (HOMainFrame.isDevelopment() && OptionManager.instance().isSkinChanged()) {
			//	HOMainFrame.instance().setDefaultFont(gui.UserParameter.temp().schriftGroesse);
			//}
		}
		else if (e.getSource().equals(m_jbCancel)) {
			ho.core.model.UserParameter.deleteTempParameter();
			ModuleManager.instance().clearTemp();
		}
		OptionManager.deleteInstance();
		setVisible(false);
		removeWindowListener(this);
		dispose();
	}
}
