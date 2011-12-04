// %1562433378:de.hattrickorganizer.gui.menu%
package de.hattrickorganizer.gui.menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;

import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.RefreshManager;
import de.hattrickorganizer.gui.login.ProxyDialog;
import de.hattrickorganizer.gui.model.CBItem;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.gui.theme.ho.HOTheme;
import de.hattrickorganizer.gui.utils.OnlineWorker;
import de.hattrickorganizer.model.HOModel;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.extension.StadiumCreator;
import de.hattrickorganizer.tools.extension.StandingCreator;

/**
 * Dialog, der den User den Download von verschiedenen Daten aus Hattrick heraus
 * ermöglicht
 */
public class DownloadDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 7837303870465506844L;

	// ~ Instance fields
	// ----------------------------------------------------------------------------/
	private static HOVerwaltung hov = HOVerwaltung.instance();
	private JButton m_jbAbort = new JButton(hov.getLanguageString("Abbrechen"));
	final private JButton m_jbDownload = new JButton(hov.getLanguageString("Download"));
	private JButton m_jbProxy = new JButton(hov.getLanguageString("ConfigureProxy"));
	private JCheckBox m_jchOldFixtures = new JCheckBox(hov.getLanguageString("FixturesDownload"), false);
	private JCheckBox m_jchOwnFixtures = new JCheckBox(hov.getLanguageString("AktuellerSpielplanDownload"),
			gui.UserParameter.instance().currentMatchlist);
	private JCheckBox m_jchHRF = new JCheckBox(hov.getLanguageString("HRFDownload"),
			gui.UserParameter.instance().xmlDownload);
	private JCheckBox m_jchMatchArchive = new JCheckBox(hov.getLanguageString("Matcharchiv"), false);
	private JCheckBox m_jchFixtures = new JCheckBox(hov.getLanguageString("FixturesDownload"), gui.UserParameter
			.instance().fixtures);
	private JList m_jlOldSeasons = new JList();
	private SpinnerDateModel m_clSpinnerModel = new SpinnerDateModel();
	private JSpinner m_jsSpinner = new JSpinner(m_clSpinnerModel);

	// ~ Constructors
	// -------------------------------------------------------------------------------

	/**
	 * Creates a new DownloadDialog object.
	 */
	public DownloadDialog() {
		super(HOMainFrame.instance(), hov.getLanguageString("Download"), true);
		initComponents();
	}

	// ~ Methods
	// ------------------------------------------------------------------------------------

	// ------------------------------------------------------------------------
	public final void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(m_jchOldFixtures)) {
			m_jlOldSeasons.setEnabled(m_jchOldFixtures.isSelected());
		} else if (e.getSource().equals(m_jbDownload)) {
			startDownload();
			RefreshManager.instance().doReInit();
			setVisible(false);
		} else if (e.getSource().equals(m_jbAbort)) {
			setVisible(false);
		} else if (e.getSource().equals(m_jbProxy)) {
			new ProxyDialog(HOMainFrame.instance());
		}
	}

	/**
	 * Fill season list box.
	 */
	private void fillOldFixturesList() {
		final int aktuelleSaison = hov.getModel().getBasics().getSeason();
		final DefaultListModel listModel = new DefaultListModel();

		for (int i = aktuelleSaison; i > 0; i--) {
			listModel.addElement(new CBItem(hov.getLanguageString("Season") + " " + i, i));
		}
		m_jlOldSeasons.setModel(listModel);
	}

	/**
	 * Initialize the GUI components.
	 */
	private void initComponents() {
		if (HOTheme.getDefaultFont() != null) {
			setFont(HOTheme.getDefaultFont());
		}
		setResizable(false);
		setContentPane(new ImagePanel(null));

		final JPanel normalDownloadPanel = new ImagePanel(new GridLayout(3, 1, 4, 4));
		normalDownloadPanel.setBorder(BorderFactory.createTitledBorder(hov.getLanguageString("Download")));

		m_jchHRF.setToolTipText(hov.getLanguageString("tt_Download_XML"));
		m_jchOwnFixtures.setToolTipText(hov.getLanguageString("tt_Download_AktuellerSpielplan"));
		m_jchFixtures.setToolTipText(hov.getLanguageString("tt_Download_Ligatabelle"));
		m_jchHRF.setOpaque(false);
		m_jchOwnFixtures.setOpaque(false);
		m_jchFixtures.setOpaque(false);
		normalDownloadPanel.add(m_jchHRF);
		normalDownloadPanel.add(m_jchOwnFixtures);
		normalDownloadPanel.add(m_jchFixtures);

		normalDownloadPanel.setSize(200, 200);
		normalDownloadPanel.setLocation(10, 10);
		getContentPane().add(normalDownloadPanel);

		final JPanel specialDownload = new ImagePanel(new GridLayout(1, 1, 4, 4));
		specialDownload.setBorder(BorderFactory.createTitledBorder(hov.getLanguageString("Verschiedenes")));

		// Alte Spielpläne
		final JPanel oldFixturePanel = new ImagePanel(new BorderLayout());

		m_jchOldFixtures.setToolTipText(hov.getLanguageString("tt_Download_AlteLigatabelle"));
		m_jchOldFixtures.addActionListener(this);
		m_jchOldFixtures.setOpaque(false);
		oldFixturePanel.add(m_jchOldFixtures, BorderLayout.NORTH);

		m_jlOldSeasons.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		m_jlOldSeasons.setEnabled(false);
		fillOldFixturesList();
		oldFixturePanel.add(new JScrollPane(m_jlOldSeasons), BorderLayout.CENTER);

		// MatchArchive
		final JPanel matchArchivePanel = new JPanel(new BorderLayout(1, 2));
		matchArchivePanel.setOpaque(false);

		m_jchMatchArchive.setToolTipText(hov.getLanguageString("tt_Download_Matcharchiv"));
		m_jchMatchArchive.addActionListener(this);
		m_jchMatchArchive.setOpaque(false);
		matchArchivePanel.add(m_jchMatchArchive, BorderLayout.WEST);

		m_clSpinnerModel.setCalendarField(java.util.Calendar.MONTH);
		((JSpinner.DateEditor) m_jsSpinner.getEditor()).getFormat().applyPattern("dd.MM.yyyy");
		matchArchivePanel.add(m_jsSpinner, BorderLayout.EAST);

		oldFixturePanel.add(matchArchivePanel, BorderLayout.SOUTH);

		specialDownload.add(oldFixturePanel);

		specialDownload.setSize(300, 200);
		specialDownload.setLocation(220, 10);
		getContentPane().add(specialDownload);

		m_jbDownload.setToolTipText(hov.getLanguageString("tt_Download_Start"));
		m_jbDownload.addActionListener(this);
		m_jbDownload.setFont(m_jbDownload.getFont().deriveFont(Font.BOLD));
		m_jbDownload.setSize(140, 30);
		m_jbDownload.setLocation(10, 220);
		InputMap buttonKeys = m_jbDownload.getInputMap(JButton.WHEN_FOCUSED);
		buttonKeys.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false), "pressed");
		buttonKeys.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,true), "released");

		getContentPane().add(m_jbDownload);
		
		m_jbProxy.setToolTipText(hov.getLanguageString("tt_ConfigureProxy"));
		m_jbProxy.addActionListener(this);
		m_jbProxy.setFont(m_jbProxy.getFont().deriveFont(Font.BOLD));
		m_jbProxy.setSize(140, 30);
		m_jbProxy.setLocation(195, 220);
		
		getContentPane().add(m_jbProxy);

		m_jbAbort.setToolTipText(hov.getLanguageString("tt_Download_Abbrechen"));
		m_jbAbort.addActionListener(this);
		m_jbAbort.setSize(140, 30);
		m_jbAbort.setLocation(380, 220);
		getContentPane().add(m_jbAbort);

		setSize(530, 280);

		final Dimension size = getToolkit().getScreenSize();

		if (size.width > this.getSize().width) {
			// Center
			this.setLocation((size.width / 2) - (this.getSize().width / 2), (size.height / 2) - (this.getSize().height / 2));
		}
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				HOLogger.instance().log(getClass(), "Request focus 1");
				boolean succ = m_jbDownload.requestFocusInWindow();
				HOLogger.instance().log(getClass(), "Request success 1: " + succ);
			}
		});
		
		setVisible(true);
	}

	/**
	 * The download action.
	 */
	private void startDownload() {
		boolean bOK = true;
		OnlineWorker worker = HOMainFrame.instance().getOnlineWorker();
		HOModel model = hov.getModel();
		if (m_jchOwnFixtures.isSelected()) {
			// Only get lineups for own fixtures
			bOK = worker.getMatches(model.getBasics().getTeamId(), false);
			if (bOK) {
				worker.getAllLineups();
				StadiumCreator.extractHistoric();
			}
		}
		if (bOK && m_jchMatchArchive.isSelected()) {
			final java.util.GregorianCalendar tempdate = new java.util.GregorianCalendar();
			tempdate.setTimeInMillis(m_clSpinnerModel.getDate().getTime());
			bOK = worker.getMatchArchive(model.getBasics().getTeamId(), tempdate);
			if (bOK) {
				// Get all lineups for matches, if they don't exist already
				worker.getAllLineups();
			}
		}
		
		if (bOK && m_jchFixtures.isSelected()) {
			// Always get actual season and league
			bOK = worker.getSpielplan(-1, -1);
			if (bOK){
				StandingCreator.extractActual();
			}
		}
		
		if (bOK && m_jchOldFixtures.isSelected()) {
			if (m_jlOldSeasons.getSelectedValues() != null) {
				final Object[] saisons = m_jlOldSeasons.getSelectedValues();
				for (int i = 0; i < saisons.length; i++) {
					if (saisons[i] instanceof CBItem) {
						// Liga
						final int saisonid = ((CBItem)saisons[i]).getId();

						// Abfragen!
						final LigaAuswahlDialog auswahlDialog = new LigaAuswahlDialog(this, saisonid);
						final int ligaid = auswahlDialog.getLigaID();

						if (ligaid > -2) {
							bOK = worker.getSpielplan(saisonid, ligaid);
						}
						if (!bOK) {
							break;
						}
					}
				}
			}
		}
		// Lastly, so that the matches for training are there
		if (bOK && m_jchHRF.isSelected()) {
				worker.getHrf();
		}
	}
}
