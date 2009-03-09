// %198737965:de.hattrickorganizer.gui.menu.option%
package de.hattrickorganizer.gui.menu.option;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JButton;

import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.gui.templates.ImagePanel;


/**
 * Ein Dialog mit allen Optionen f�r HO
 */
public class OptionenDialog extends JDialog implements WindowListener/*, ActionListener */{
    //~ Instance fields ----------------------------------------------------------------------------

    private FarbPanel m_jpFarben;
    private FormelPanel m_jpFormeln;
    private RatingOffsetPanel m_jpRatingOffset;
    private SonstigeOptionenPanel m_jpSonstigeOptionen;
    private CheckOptionPanel hoConnectionOptions;
    private TabOptionenPanel m_jpTabOptionen;
    private TrainingsOptionenPanel m_jpTrainingsOptionen;
    private UserPanel m_jpUserOptionen;
    private UserColumnsPanel m_jpUserColumns;
    private DownloadPanel m_jpDownloadPanel;
    //private JButton m_jbSave = new JButton(de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Speichern"));
    //private JButton m_jbCancel = new JButton(de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Abbrechen"));
    //private ImagePanel m_jpButtonPanel = new ImagePanel();

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new OptionenDialog object.
     *
     * @param owner TODO Missing Constructuor Parameter Documentation
     */
    public OptionenDialog(JFrame owner) {
        super(owner,
              de.hattrickorganizer.model.HOVerwaltung.instance().getResource().getProperty("Optionen"),
              true);

        this.addWindowListener(this);

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
        if (m_jpSonstigeOptionen.restartErforderlich()
            || m_jpFarben.needRestart()
            || m_jpUserColumns.needRestart()
            || m_jpTabOptionen.somethingChanged()) {
            de.hattrickorganizer.tools.Helper.showMessage(this,
					                                          de.hattrickorganizer.model.HOVerwaltung.instance()
					                                                                                 .getResource()
					                                                                                 .getProperty("NeustartErforderlich"),
					                                          "", JOptionPane.INFORMATION_MESSAGE);
        }

        final de.hattrickorganizer.gui.login.LoginWaitDialog waitdialog = new de.hattrickorganizer.gui.login.LoginWaitDialog(de.hattrickorganizer.gui.HOMainFrame
                                                                                                                             .instance());
        waitdialog.setVisible(true);

        de.hattrickorganizer.gui.RefreshManager.instance().doReInit();

        waitdialog.setVisible(false);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowDeactivated(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowDeiconified(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowIconified(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param windowEvent TODO Missing Method Parameter Documentation
     */
    public void windowOpened(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        setContentPane(new de.hattrickorganizer.gui.templates.ImagePanel());
        getContentPane().setLayout(new BorderLayout());

        final JTabbedPane tabbedPane = new JTabbedPane();
		
        //Verschiedenes
        m_jpSonstigeOptionen = new SonstigeOptionenPanel();
        tabbedPane.addTab(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                 .getProperty("Verschiedenes"),
                          new JScrollPane(m_jpSonstigeOptionen));

        //Tab
        m_jpTabOptionen = new TabOptionenPanel();
        tabbedPane.addTab(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                 .getProperty("TabManagement"),
                          new JScrollPane(m_jpTabOptionen));

        //Farben
        m_jpFarben = new FarbPanel();
        tabbedPane.addTab(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                 .getProperty("Farben"),
                          new JScrollPane(m_jpFarben));

        //Formeln
        m_jpFormeln = new FormelPanel();
        tabbedPane.addTab(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                 .getProperty("Formeln"),
                          new JScrollPane(m_jpFormeln));

		//Rating Offset
		m_jpRatingOffset = new RatingOffsetPanel();
		tabbedPane.addTab(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
																 .getProperty("PredictionOffset"),
						  new JScrollPane(m_jpRatingOffset));

        //Training
        m_jpTrainingsOptionen = new TrainingsOptionenPanel();
        tabbedPane.addTab(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                 .getProperty("Training"),
                          new JScrollPane(m_jpTrainingsOptionen));

        m_jpUserOptionen = new UserPanel();
        tabbedPane.addTab("User", new JScrollPane(m_jpUserOptionen));

		// HO Check		
		hoConnectionOptions = new CheckOptionPanel();
		tabbedPane.addTab("HO Check",new JScrollPane(hoConnectionOptions));
		
		//Download
        m_jpDownloadPanel = new DownloadPanel();
        tabbedPane.addTab(de.hattrickorganizer.model.HOVerwaltung.instance().getResource()
                                                                 .getProperty("Download"),
                          new JScrollPane(m_jpDownloadPanel));
		
//		 HO Check		
		m_jpUserColumns = new UserColumnsPanel();
		tabbedPane.addTab(HOVerwaltung.instance().getResource().getProperty("columns"),new JScrollPane(m_jpUserColumns));


        //Tabs der plugins
        for (int i = 0;
             (i < de.hattrickorganizer.gui.HOMainFrame.instance().getOptionPanelNames().size())
             && (i < de.hattrickorganizer.gui.HOMainFrame.instance().getOptionPanels().size());
             ++i) {
            tabbedPane.addTab(de.hattrickorganizer.gui.HOMainFrame.instance().getOptionPanelNames()
                                                                  .get(i).toString(),
                              (javax.swing.JPanel) de.hattrickorganizer.gui.HOMainFrame.instance()
                                                                                       .getOptionPanels()
                                                                                       .get(i));
        }

        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        
        //Add Buttons
        //m_jpButtonPanel.add(m_jbSave);
        //m_jbSave.setFont(m_jbSave.getFont().deriveFont(Font.BOLD));
        //m_jpButtonPanel.add(m_jbCancel);
        
        //m_jbSave.addActionListener(this);
        //m_jbCancel.addActionListener(this);
        
        //getContentPane().add(m_jpButtonPanel,BorderLayout.SOUTH);

        if (de.hattrickorganizer.gui.HOMainFrame.instance().getToolkit().getScreenSize().height >= 700) {
            setSize(new java.awt.Dimension(450, 700));
        } else {
            setSize(new java.awt.Dimension(450,
                                           de.hattrickorganizer.gui.HOMainFrame.instance()
                                                                               .getToolkit()
                                                                               .getScreenSize().height
                                           - 50));
        }

        final Dimension size = de.hattrickorganizer.gui.HOMainFrame.instance().getToolkit()
                                                                   .getScreenSize();

        if (size.width > this.getSize().width) {
            //Mittig positionieren
            this.setLocation((size.width / 2) - (this.getSize().width / 2),
                             (size.height / 2) - (this.getSize().height / 2));
        }

        this.setResizable(false);
    }

/*	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(m_jbSave)) {
			if (m_jpSonstigeOptionen.restartErforderlich()
		            || m_jpFarben.needRestart()
		            || m_jpUserColumns.needRestart()
		            || m_jpTabOptionen.somethingChanged()) {
		            de.hattrickorganizer.tools.Helper.showMessage(this,
							                                          de.hattrickorganizer.model.HOVerwaltung.instance()
							                                                                                 .getResource()
							                                                                                 .getProperty("NeustartErforderlich"),
							                                          "", JOptionPane.INFORMATION_MESSAGE);
		        }

		        final de.hattrickorganizer.gui.login.LoginWaitDialog waitdialog = new de.hattrickorganizer.gui.login.LoginWaitDialog(de.hattrickorganizer.gui.HOMainFrame
		                                                                                                                             .instance());
		        waitdialog.setVisible(true);

		        de.hattrickorganizer.gui.RefreshManager.instance().doReInit();

		        waitdialog.setVisible(false);
		        
		        
		}
		setVisible(false);
				
		
	}*/
}
