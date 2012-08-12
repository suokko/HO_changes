package ho.module.tsforecast;

import ho.core.gui.IRefreshable;
import ho.core.gui.RefreshManager;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;
import ho.core.model.match.IMatchDetails;
import ho.core.model.match.MatchType;
import ho.core.module.config.ModuleConfig;
import ho.core.util.HOLogger;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.text.DateFormat;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class TSForecast extends ImagePanel implements IRefreshable,
		ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;
	final static String TS_SHOWCUPMATCHES = "TS_ShowCupMatches";
	final static String TS_SHOWQUALIFICATIONMATCH = "TS_ShowQualificationMatch";
	final static String TS_HISTORY = "TS_History";
	final static String TS_LOEPIFORECAST = "TS_LoepiForecast";
	final static String TS_LOEPIHISTORY = "TS_LoepiHistory";
	final static String TS_CONFIDENCE = "TS_Confidence";
	final static String TS_GENERALSPIRIT = "TS_GeneralSpirit";

	private JPanel m_jpSettingsPanel = null;
	private JPanel m_jpGamesPanel = null;

	private JCheckBox m_jtCupMatches = null;
	private JCheckBox m_jtRelegationMatch = null;

	private CheckBox m_jtHistory = null;
	private CheckBox m_jtLoepiHist = null;
	private CheckBox m_jtLoepiFore = null;
	private CheckBox m_jtConfidence = null;

	private TSPanel m_jpGraphics = null;

	private HistoryCurve m_History = null;
	private LoepiCurve m_LoepiForecast = null;
	private LoepiCurve m_LoepiHist = null;
	private TrainerCurve m_Trainer = null;
	private ConfidenceCurve m_Confidence = null;

	public TSForecast() {
		initializeConfig();
		initialize();
	}

	/**
	 * Is called by HO! to start the plugin
	 */

	private void initialize() {
		try {

			GridBagLayout gridbaglayout = new GridBagLayout();
			setLayout(gridbaglayout);

			GridBagConstraints gridbagconstraints = new GridBagConstraints();
			gridbagconstraints.fill = GridBagConstraints.NONE;
			gridbagconstraints.insets = new Insets(5, 5, 5, 5);

			m_jpSettingsPanel = new JPanel();
			m_jpSettingsPanel.setOpaque(false);
			m_jpSettingsPanel.setLayout(new BoxLayout(m_jpSettingsPanel,
					BoxLayout.Y_AXIS));

			createSettingsPanel(m_jpSettingsPanel);
			createCurvesPanel(m_jpSettingsPanel);
			createGamesPanel(m_jpSettingsPanel);

			gridbagconstraints.gridx = 0;
			gridbagconstraints.gridheight = 2;
			gridbagconstraints.anchor = 18;
			add(m_jpSettingsPanel, gridbagconstraints);
			m_jpGraphics = new TSPanel();
			gridbagconstraints.gridx = 1;
			gridbagconstraints.gridy = 0;
			gridbagconstraints.fill = 1;
			gridbagconstraints.anchor = 11;
			gridbagconstraints.weightx = 1.0D;
			gridbagconstraints.weighty = 1.0D;
			gridbagconstraints.gridheight = -1;
			add(m_jpGraphics, gridbagconstraints);

			initCurves();
			// createTeamData(1);
			double d = ModuleConfig.instance().getBigDecimal(TS_GENERALSPIRIT)
					.doubleValue();
			try {
				m_LoepiForecast.setGeneralSpirit(d);
				m_LoepiHist.setGeneralSpirit(d);
			} catch (Exception ex) {
				HOLogger.instance().error(this.getClass(), ex);
			}
			RefreshManager.instance().registerRefreshable(this);
		} catch (Exception exception) {
			HOLogger.instance().error(this.getClass(), exception);
		}
	}

	private void initializeConfig() {
		ModuleConfig config = ModuleConfig.instance();
		if (!config.containsKey(TS_SHOWCUPMATCHES)) {
			config.setBoolean(TS_SHOWCUPMATCHES, false);
			config.setBoolean(TS_SHOWQUALIFICATIONMATCH, false);
			config.setBoolean(TS_HISTORY, false);
			config.setBoolean(TS_LOEPIFORECAST, false);
			config.setBoolean(TS_LOEPIHISTORY, false);
			config.setBoolean(TS_CONFIDENCE, false);
			config.setBigDecimal(TS_GENERALSPIRIT, new BigDecimal("4.50"));
			ModuleConfig.instance().save();
		}
	}

	@Override
	public void refresh() {
		// ErrorLog.writeln("refresh");
		try {
			createCurves();
		} catch (Exception ex) {
			HOLogger.instance().error(this.getClass(), ex);
		}
		createGamesPanel(m_jpSettingsPanel);
		m_jpGraphics.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent actionevent) {
		Cursor cursor = getCursor();
		setCursor(Cursor.getPredefinedCursor(3));
		try {
			if (actionevent.getSource() instanceof JRadioButton) {
				int iButton = Integer.parseInt(actionevent.getActionCommand()
						.substring(1));

				switch (actionevent.getActionCommand().charAt(0)) {
				case 80: // 'P'
					m_LoepiForecast.setAttitude(iButton,
							IMatchDetails.EINSTELLUNG_PIC);
					break;
				case 77: // 'M'
					m_LoepiForecast.setAttitude(iButton,
							IMatchDetails.EINSTELLUNG_MOTS);
					break;
				case 78: // 'N'
				case 79: // 'O'
				default:
					m_LoepiForecast.setAttitude(iButton,
							IMatchDetails.EINSTELLUNG_NORMAL);
					break;
				}
				m_jpGraphics.repaint();
			}
		} catch (Exception ex) {
			HOLogger.instance().error(this.getClass(), ex);
		}
		setCursor(cursor);
	}

	@Override
	public void itemStateChanged(ItemEvent itemevent) {
		ModuleConfig config = ModuleConfig.instance();
		try {
			boolean selected = itemevent.getStateChange() == ItemEvent.SELECTED;
			if (itemevent.getSource() == m_jtCupMatches) {
				config.setBoolean(TS_SHOWCUPMATCHES, selected);
				createGamesPanel(m_jpSettingsPanel);
			} else if (itemevent.getSource() == m_jtRelegationMatch) {
				config.setBoolean(TS_SHOWQUALIFICATIONMATCH, selected);
				createGamesPanel(m_jpSettingsPanel);
			} else if (itemevent.getSource() == m_jtHistory.getCheckBox()) {
				config.setBoolean(TS_HISTORY, selected);
				if (selected) {
					m_jpGraphics.addCurve(m_History, true);
					m_jpGraphics.addCurve(m_Trainer);
				} else {
					m_jpGraphics.removeCurve(m_History);
					m_jpGraphics.removeCurve(m_Trainer);
				}
			} else if (itemevent.getSource() == m_jtLoepiFore.getCheckBox()) {
				config.setBoolean(TS_LOEPIFORECAST, selected);
				if (selected) {
					m_jpGraphics.addCurve(m_LoepiForecast);
				} else {
					m_jpGraphics.removeCurve(m_LoepiForecast);
				}
			} else if (itemevent.getSource() == m_jtLoepiHist.getCheckBox()) {
				config.setBoolean(TS_LOEPIHISTORY, selected);
				if (selected) {
					m_jpGraphics.addCurve(m_LoepiHist);
				} else {
					m_jpGraphics.removeCurve(m_LoepiHist);
				}
			} else if (itemevent.getSource() == m_jtConfidence.getCheckBox()) {
				config.setBoolean(TS_CONFIDENCE, selected);
				if (selected) {
					m_jpGraphics.addCurve(m_Confidence);
				} else {
					m_jpGraphics.removeCurve(m_Confidence);
				}
			}
			ModuleConfig.instance().save();
		} catch (Exception exception) {
			HOLogger.instance().error(this.getClass(), exception);
		}

		m_jpGraphics.showConfidenceScale(config.getBoolean(TS_CONFIDENCE));

		// check whether it is necessary to draw teamspirit scale

		if (!config.getBoolean(TS_LOEPIHISTORY)
				&& !config.getBoolean(TS_LOEPIFORECAST)
				&& !config.getBoolean(TS_HISTORY)) {
			m_jpGraphics.showTeamspiritScale(false);
		} else {
			m_jpGraphics.showTeamspiritScale(true);
		}

		m_jpGraphics.repaint();
	}

	// public void hyperlinkUpdate(javax.swing.event.HyperlinkEvent e) {
	// if (e.getEventType() ==
	// javax.swing.event.HyperlinkEvent.EventType.ACTIVATED) {
	// try {
	// String os = System.getProperty("os.name");
	// if ( os != null && os.startsWith("Windows"))
	// Runtime.getRuntime().exec(
	// "rundll32 url.dll,FileProtocolHandler "+e.getURL());
	// else //UNIX Mac
	// Runtime.getRuntime().exec( "netscape "+e.getURL());
	// } catch (Exception exc) {
	// HOLogger.instance().error(this.getClass(), exc);
	// }
	// }
	// }

	// - private
	// -------------------------------------------------------------------------

	// private int createTeamData(int gridy) throws SQLException {
	// IJDBCAdapter ijdbcadapter = DBManager.instance().getAdapter();
	//
	// GridBagConstraints gridbagconstraints = new GridBagConstraints();
	// gridbagconstraints.anchor = GridBagConstraints.WEST;
	// gridbagconstraints.insets = new Insets(0, 5, 5, 5);
	// gridbagconstraints.gridy = gridy;
	// gridbagconstraints.gridx = 1;
	//
	// String strLabel = new String();
	//
	// ResultSet resultset =
	// ijdbcadapter.executeQuery("select FUEHRUNG from SPIELER where TRAINER > 0 order by DATUM desc");
	// if( resultset != null && resultset.first()) {
	// strLabel += HOVerwaltung.instance().getLanguageString( "FQTrainer")
	// + PlayerHelper.getNameForSkill( resultset.getInt("FUEHRUNG"), true)
	// + ". ";
	// }
	//
	// resultset = ijdbcadapter.executeQuery(
	// "select max(FUEHRUNG) as MAXF, max(DATUM) as MAXD from SPIELER where TRAINER=0");
	// resultset.first();
	// resultset = ijdbcadapter.executeQuery(
	// "select count(SPIELERID) as COUNTF from SPIELER where FUEHRUNG = "
	// + resultset.getInt("MAXF") + " and DATUM = '"
	// + resultset.getTimestamp("MAXD") + "' and TRAINER=0");
	// resultset.first();
	// if( resultset.getInt("COUNTF") > 1) {
	// strLabel += HOVerwaltung.instance().getLanguageString("no_teamleader");
	// } else {
	// strLabel += HOVerwaltung.instance().getLanguageString("teamleader") +
	// " ";
	// resultset =
	// ijdbcadapter.executeQuery("select ICHARAKTER from SPIELER order by FUEHRUNG desc, DATUM desc");
	//
	// if(resultset != null && resultset.first()) {
	// switch(resultset.getInt("ICHARAKTER")) {
	// case 5:
	// strLabel
	// +=HOVerwaltung.instance().getLanguageString("ls.player.agreeability.belovedteammember") +
	// ". ";
	// break;
	// case 4:
	// strLabel += HOVerwaltung.instance().getLanguageString("ls.player.agreeability.popularguypopular") + ". ";
	// break;
	// case 3:
	// strLabel += HOVerwaltung.instance().getLanguageString("ls.player.agreeability.sympatheticguy") +
	// ". ";
	// break;
	// case 2:
	// strLabel +=HOVerwaltung.instance().getLanguageString("ls.player.agreeability.pleasantguy") + ". ";
	// break;
	// case 1:
	// strLabel +=HOVerwaltung.instance().getLanguageString("ls.player.agreeability.controversialperson") +
	// ". ";
	// break;
	// case 0:
	// strLabel += HOVerwaltung.instance().getLanguageString("ls.player.agreeability.nastyfellow") + ". ";
	// break;
	// default:
	// strLabel += "free of character. ";
	// break;
	// }
	// }
	// }
	//
	// resultset =
	// ijdbcadapter.executeQuery("select PSCHYOLOGEN from VEREIN order by HRF_ID desc");
	// if(resultset != null && resultset.first()) {
	// strLabel += HOVerwaltung.instance().getLanguageString("Staff")
	// + resultset.getInt("PSCHYOLOGEN") + " "
	// + HOVerwaltung.instance().getLanguageString("ls.club.staff.sportpsychologists")
	// + ". ";
	// }
	// resultset =
	// ijdbcadapter.executeQuery("select TRAININGSINTENSITAET from TEAM order by HRF_ID desc");
	// if(resultset != null && resultset.first()) {
	// strLabel +=
	// HOVerwaltung.instance().getLanguageString("Trainingsintensity")
	// + resultset.getInt("TRAININGSINTENSITAET")
	// + "% . ";
	// }
	// JLabel jlabel = new JLabel(strLabel, 2);
	// jlabel.setOpaque(true);
	// add(jlabel, gridbagconstraints);
	// return gridbagconstraints.gridy;
	// }

	private void createSettingsPanel(JPanel jpanel) {
		ModuleConfig config = ModuleConfig.instance();
		m_jtCupMatches = new JCheckBox(HOVerwaltung.instance()
				.getLanguageString("CupMatches"),
				config.getBoolean(TS_SHOWCUPMATCHES));
		m_jtCupMatches.setToolTipText(HOVerwaltung.instance()
				.getLanguageString("ShowCupMatches"));
		m_jtCupMatches.setAlignmentX(0.0F);
		m_jtCupMatches.addItemListener(this);
		jpanel.add(m_jtCupMatches);

		m_jtRelegationMatch = new JCheckBox(HOVerwaltung.instance()
				.getLanguageString("ls.match.matchtype.qualification"),
				config.getBoolean(TS_SHOWQUALIFICATIONMATCH));
		m_jtRelegationMatch.setToolTipText(HOVerwaltung.instance()
				.getLanguageString("ShowQMatch"));
		m_jtRelegationMatch.setAlignmentX(0.0F);
		m_jtRelegationMatch.addItemListener(this);
		jpanel.add(m_jtRelegationMatch);
	}

	private void createCurvesPanel(JPanel jpanel) throws Exception {
		ModuleConfig config = ModuleConfig.instance();
		createCurves();

		m_jtHistory = new CheckBox(HOVerwaltung.instance().getLanguageString(
				"HistoryCurve"), m_History.getColor(),
				config.getBoolean(TS_HISTORY));
		m_jtHistory.setAlignmentX(0.0F);
		m_jtHistory.addItemListener(this);
		jpanel.add(m_jtHistory);

		m_jtConfidence = new CheckBox(HOVerwaltung.instance()
				.getLanguageString("Selbstvertrauen"), m_Confidence.getColor(),
				config.getBoolean(TS_CONFIDENCE));
		m_jtConfidence.setAlignmentX(0.0F);
		m_jtConfidence.addItemListener(this);
		jpanel.add(m_jtConfidence);

		m_jtLoepiHist = new CheckBox(HOVerwaltung.instance().getLanguageString(
				"LoepiCurve"), m_LoepiHist.getColor(),
				config.getBoolean(TS_LOEPIHISTORY));
		m_jtLoepiHist.setAlignmentX(0.0F);
		m_jtLoepiHist.addItemListener(this);
		jpanel.add(m_jtLoepiHist);

		m_jtLoepiFore = new CheckBox(HOVerwaltung.instance().getLanguageString(
				"TSForecast"), m_LoepiForecast.getColor(),
				config.getBoolean(TS_LOEPIFORECAST));
		m_jtLoepiFore.setAlignmentX(0.0F);
		m_jtLoepiFore.addItemListener(this);
		jpanel.add(m_jtLoepiFore);
	}

	private void createGamesPanel(JPanel jpanel) {
		if (m_jpGamesPanel == null) {
			m_jpGamesPanel = new JPanel();
			m_jpGamesPanel.setLayout(new GridBagLayout());
		}
		jpanel.remove(m_jpGamesPanel);
		m_jpGamesPanel.removeAll();

		GridBagConstraints gridbagconstraints = new GridBagConstraints();
		gridbagconstraints.gridwidth = 1;
		gridbagconstraints.anchor = GridBagConstraints.NORTHWEST;

		int iCmdID = 0;
		JLabel jlabel = new JLabel(" PIC  N  MOTS");
		jlabel.setOpaque(true);
		gridbagconstraints.insets = new Insets(10, 0, 2, 0);
		gridbagconstraints.gridy = 0;
		m_jpGamesPanel.add(jlabel, gridbagconstraints);
		gridbagconstraints.insets = new Insets(0, 0, 0, 0);

		boolean bshowCupMatches = ModuleConfig.instance().getBoolean(
				TS_SHOWCUPMATCHES);
		boolean bshowQualMatches = ModuleConfig.instance().getBoolean(
				TS_SHOWQUALIFICATIONMATCH);

		for (boolean flag = m_LoepiForecast.first() && m_LoepiForecast.next(); flag;) {
			if (m_LoepiForecast.getAttitude() != IMatchDetails.EINSTELLUNG_UNBEKANNT) {
				if (m_LoepiForecast.getMatchType() == MatchType.LEAGUE
						|| (m_LoepiForecast.getMatchType() == MatchType.CUP && bshowCupMatches)
						|| (m_LoepiForecast.getMatchType() == MatchType.QUALIFICATION && bshowQualMatches)) {

					FutureMatchBox futurematchbox = new FutureMatchBox(
							DateFormat.getDateInstance(3).format(
									m_LoepiForecast.getDate()),
							m_LoepiForecast.getTooltip(), iCmdID,
							m_LoepiForecast.getAttitude(),
							m_LoepiForecast.getMatchType());
					futurematchbox.addActionListener(this);
					gridbagconstraints.gridy++;
					m_jpGamesPanel.add(futurematchbox, gridbagconstraints);
				}
				if (m_LoepiForecast.getMatchType() == MatchType.QUALIFICATION) { // indicate
																					// end
																					// of
																					// season
					gridbagconstraints.gridy++;
					m_jpGamesPanel.add(
							new JLabel("  "
									+ HOVerwaltung.instance()
											.getLanguageString("EndOFSeason")),
							gridbagconstraints);
					// JToolBar.Separator s = new JToolBar.Separator( new
					// Dimension(40,40));
					// m_jpGamesPanel.add( s, gridbagconstraints);
				}
			}
			flag = m_LoepiForecast.next();
			iCmdID++;
		}

		m_jpGamesPanel.setAlignmentX(0.0F);
		jpanel.add(m_jpGamesPanel);
		jpanel.revalidate();
	}

	private void createCurves() throws Exception {
		if (m_Trainer != null && m_jpGraphics.removeCurve(m_Trainer)) {
			m_Trainer = new TrainerCurve();
			m_jpGraphics.addCurve(m_Trainer);
		} else {
			m_Trainer = new TrainerCurve();
		}

		if (m_History != null && m_jpGraphics.removeCurve(m_History)) {
			m_History = new HistoryCurve();
			m_jpGraphics.addCurve(m_History, true);
		} else {
			m_History = new HistoryCurve();
		}
		m_History.setColor(Color.black);
		m_History.first();
		m_History.next();

		if (m_Confidence != null && m_jpGraphics.removeCurve(m_Confidence)) {
			m_Confidence = new ConfidenceCurve();
			m_jpGraphics.addCurve(m_Confidence);
		} else {
			m_Confidence = new ConfidenceCurve();
		}
		m_Confidence.setColor(Color.blue);

		if (m_LoepiHist != null && m_jpGraphics.removeCurve(m_LoepiHist)) {
			m_LoepiHist = new LoepiCurve(m_Trainer, false);
			m_jpGraphics.addCurve(m_LoepiHist);
		} else {
			m_LoepiHist = new LoepiCurve(m_Trainer, false);
		}
		m_LoepiHist.setSpirit(0, m_History.getSpirit());
		m_LoepiHist.setColor(Color.orange);

		if (m_LoepiForecast != null
				&& m_jpGraphics.removeCurve(m_LoepiForecast)) {
			m_LoepiForecast = new LoepiCurve(m_Trainer, true);
			m_jpGraphics.addCurve(m_LoepiForecast);
		} else {
			m_LoepiForecast = new LoepiCurve(m_Trainer, true);
		}
		m_LoepiForecast.setStartPoint(m_History.getLastPoint());
		// m_LoepiForecast.setAttitudes( m_Configuration);
		m_LoepiForecast.forecast(0);
		m_LoepiForecast.setColor(Color.red);
	}


	private void initCurves() {

		ModuleConfig config = ModuleConfig.instance();

		if (config.getBoolean(TS_HISTORY)) {
			m_jpGraphics.addCurve(m_History, true);
			m_jpGraphics.addCurve(m_Trainer);
		}

		if (config.getBoolean(TS_LOEPIFORECAST)) {
			m_jpGraphics.addCurve(m_LoepiForecast);
		}

		if (config.getBoolean(TS_LOEPIHISTORY)) {
			m_jpGraphics.addCurve(m_LoepiHist);
		}

		if (config.getBoolean(TS_CONFIDENCE)) {
			m_jpGraphics.addCurve(m_Confidence);

		}
	}

}