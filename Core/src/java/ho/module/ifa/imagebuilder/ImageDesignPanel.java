package ho.module.ifa.imagebuilder;

import ho.core.db.DBManager;
import ho.core.gui.theme.ImageUtilities;
import ho.core.model.HOVerwaltung;
import ho.core.model.WorldDetailLeague;
import ho.core.model.WorldDetailsManager;
import ho.core.util.HOLogger;
import ho.module.ifa.FlagLabel;
import ho.module.ifa.GlobalActionsListener;
import ho.module.ifa.PluginIfaPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ImageDesignPanel extends JPanel {
	private static final long serialVersionUID = 6107052908932568929L;
	public static int FLAG_WIDTH = 8;
	public static int MIN_FLAG_WIDTH = 5;
	public static int MAX_FLAG_WIDTH = 12;
	private PluginIfaPanel pluginIfaPanel;
	private EmblemPanel visitedEmblemPanel;
	private EmblemPanel hostedEmblemPanel;
	private JSpinner sizeSpinner;
	private JPanel centerPanel;
	private JScrollPane scroll;
	private JTextField textField;
	private JRadioButton home;
	private JRadioButton away;
	private JCheckBox greyColored;
	private JCheckBox roundly;
	private JSlider percentSlider;
	private JCheckBox headerYesNo;
	private JCheckBox animGif;
	private JSpinner delaySpinner;
	private JPanel toolbar;

	public ImageDesignPanel(PluginIfaPanel pluginIfaPanel) {
		this.pluginIfaPanel = pluginIfaPanel;
		
		try {
			initialize();
		} catch (Exception e) {
			HOLogger.instance().error(ImageDesignPanel.class, e);
		}
	}

	private void initialize() throws Exception {
		setLayout(new BorderLayout());

		this.centerPanel = new JPanel();
		this.centerPanel.setLayout(new GridLayout(1, 2));

		FLAG_WIDTH = Integer.parseInt(ConfigManager.getTextFromConfig(ConfigManager.IFA_WIDTH));

		StateChangeListener changeListener = new StateChangeListener();

		this.headerYesNo = new JCheckBox(HOVerwaltung.instance().getLanguageString("showHeader"),
				new Boolean(ConfigManager
						.getTextFromConfig(ConfigManager.IFA_HEADERSHOW))
						.booleanValue());
		this.headerYesNo.setName("header");
		this.headerYesNo.addChangeListener(changeListener);
		getToolbar().add(headerYesNo);

		this.roundly = new JCheckBox(HOVerwaltung.instance().getLanguageString("Roundly"),
				new Boolean(ConfigManager
						.getTextFromConfig(ConfigManager.IFA_ROUNDLY))
						.booleanValue());
		this.roundly.setName("rounded");
		this.roundly.addChangeListener(changeListener);
		getToolbar().add(roundly);

		this.greyColored = new JCheckBox(HOVerwaltung.instance().getLanguageString("Grey"),
				new Boolean(ConfigManager
						.getTextFromConfig(ConfigManager.IFA_GREY))
						.booleanValue());
		this.greyColored.setName("grey");
		this.greyColored.addChangeListener(changeListener);
		getToolbar().add(greyColored);

		this.percentSlider = new JSlider(0, 100, Integer.parseInt(ConfigManager
				.getTextFromConfig(ConfigManager.IFA_BRIGHTNESS)));
		this.percentSlider.addChangeListener(changeListener);
		this.percentSlider.setMajorTickSpacing(25);
		this.percentSlider.setMinorTickSpacing(5);
		this.percentSlider.setPaintTicks(true);
		this.percentSlider.setPaintLabels(true);
		getToolbar().add(percentSlider);

		JPanel sizePanel = new JPanel(new FlowLayout(1, 0, 0));
		this.sizeSpinner = new JSpinner(new SpinnerNumberModel(FLAG_WIDTH,
				MIN_FLAG_WIDTH, MAX_FLAG_WIDTH, 1));
		this.sizeSpinner.setName("size");
		this.sizeSpinner.addChangeListener(changeListener);
		sizePanel.add(new JLabel(HOVerwaltung.instance().getLanguageString("Flaggen")+"/"+(HOVerwaltung.instance().getLanguageString("Row")+": ")));
		sizePanel.add(this.sizeSpinner);
		getToolbar().add(sizePanel);
		this.textField = new JTextField(
				ConfigManager.getTextFromConfig(ConfigManager.IFA_VISITEDHEADER));
		this.textField.addKeyListener(new TextKeyListener());
		this.textField.setPreferredSize(new Dimension(150, 25));
		getToolbar().add(textField);

		this.animGif = new JCheckBox(HOVerwaltung.instance().getLanguageString("animatedGIF"),
				new Boolean(ConfigManager.getTextFromConfig(ConfigManager.IFA_GIFANIMATED)).booleanValue());
		getToolbar().add(animGif);

		JPanel spinnerPanel = new JPanel(new FlowLayout(1, 0, 0));
		this.delaySpinner = new JSpinner(new SpinnerNumberModel(
				Double.parseDouble(ConfigManager
						.getTextFromConfig(ConfigManager.IFA_GIFDELAY)), 0.0D,
				60.0D, 0.1D));
		spinnerPanel.add(new JLabel(HOVerwaltung.instance().getLanguageString("Delay")+": "));
		spinnerPanel.add(this.delaySpinner);
		getToolbar().add(spinnerPanel);
		setVisitedEmblemPanel();
		setHostedEmblemPanel();
		
		centerPanel.add(visitedEmblemPanel);
		centerPanel.add(hostedEmblemPanel);
		
		JButton saveImage = new JButton(HOVerwaltung.instance().getLanguageString("Speichern"));
		saveImage.addActionListener(new GlobalActionsListener(
				this.pluginIfaPanel));
		saveImage.setActionCommand("saveImage");
		getToolbar().add(saveImage);

		add(getToolbar(), BorderLayout.NORTH);
		this.scroll = new JScrollPane(this.centerPanel);
		add(scroll, BorderLayout.CENTER);
	}

	private void setVisitedEmblemPanel() {
		int enabled = 0;
		try {
			if (visitedEmblemPanel == null) {
				FLAG_WIDTH = Integer.parseInt(ConfigManager.getTextFromConfig(ConfigManager.IFA_WIDTH));
				FlagLabel.BRIGHTNESS = Integer
						.parseInt(ConfigManager.getTextFromConfig(ConfigManager.IFA_BRIGHTNESS));
				FlagLabel.GREY = new Boolean(ConfigManager.getTextFromConfig(ConfigManager.IFA_GREY)).booleanValue();
				FlagLabel[] flags = getAllCountries(true);
				for (int i = 0; i < flags.length; i++) {
					if (flags[i].isEnabled())
						enabled++;
				}
				EmblemPanel emblemPanel = new EmblemPanel(flags, enabled,"AutoFilterPanel.Away_Games");
				emblemPanel.setFlagWidth(FLAG_WIDTH);
				emblemPanel.setBrightness(FlagLabel.BRIGHTNESS);
				emblemPanel.setGrey(FlagLabel.GREY);
				emblemPanel.setRoundly(FlagLabel.ROUNDFLAG);
				String path =ConfigManager.getTextFromConfig(ConfigManager.IFA_VISITEDPATHEMBLEM);
				if (!path.equals("")) {
					File file = new File(path);
					if (file.exists()) {
						ImageIcon imageIcon = new ImageIcon(path);
						if (imageIcon != null) {
							emblemPanel.setLogo(imageIcon);
							emblemPanel.setImagePath(path);
						}
					}
				}
				emblemPanel.setHeaderText(ConfigManager.getTextFromConfig(ConfigManager.IFA_VISITEDHEADER));
				emblemPanel
						.setHeader(new Boolean(ConfigManager.getTextFromConfig(ConfigManager.IFA_HEADERSHOW)).booleanValue());
				visitedEmblemPanel = emblemPanel;
			} else {
				FLAG_WIDTH = visitedEmblemPanel.getFlagWidth();
				FlagLabel.BRIGHTNESS = visitedEmblemPanel.getBrightness();
				FlagLabel.GREY = visitedEmblemPanel.isGrey();
				FlagLabel.ROUNDFLAG = visitedEmblemPanel.isRoundly();
				FlagLabel[] flags = getAllCountries(true);
				for (int i = 0; i < flags.length; i++) {
					if (flags[i].isEnabled())
						enabled++;
				}
				visitedEmblemPanel.setFlagPanel(new FlagPanel(flags, enabled));
			}
		} catch (Exception e) {
			HOLogger.instance().error(ImageDesignPanel.class, e);
		}
	}

	
	
	
	
	private void setHostedEmblemPanel() {
		int enabled = 0;
		try {
			if (hostedEmblemPanel == null) {
				FLAG_WIDTH = Integer.parseInt(ConfigManager.getTextFromConfig(ConfigManager.IFA_WIDTH));
				FlagLabel.BRIGHTNESS = Integer
						.parseInt(ConfigManager.getTextFromConfig(ConfigManager.IFA_BRIGHTNESS));
				FlagLabel.GREY = new Boolean(ConfigManager.getTextFromConfig(ConfigManager.IFA_GREY)).booleanValue();
				FlagLabel[] flags = getAllCountries(false);
				for (int i = 0; i < flags.length; i++) {
					if (flags[i].isEnabled())
						enabled++;
				}
				EmblemPanel emblemPanel = new EmblemPanel(flags, enabled,"AutoFilterPanel.Home_Games");
				emblemPanel.setFlagWidth(FLAG_WIDTH);
				emblemPanel.setBrightness(FlagLabel.BRIGHTNESS);
				emblemPanel.setGrey(FlagLabel.GREY);
				emblemPanel.setRoundly(FlagLabel.ROUNDFLAG);
				String path = ConfigManager.getTextFromConfig(ConfigManager.IFA_HOSTEDPATHEMBLEM);
				if (!path.equals("")) {
					File file = new File(path);
					if (file.exists()) {
						ImageIcon imageIcon = new ImageIcon(path);
						if (imageIcon != null) {
							emblemPanel.setLogo(imageIcon);
							emblemPanel.setImagePath(path);
						}
					}
				}
				emblemPanel.setHeaderText( ConfigManager.getTextFromConfig(ConfigManager.IFA_HOSTEDHEADER));
				emblemPanel.setHeader(new Boolean(ConfigManager.getTextFromConfig(ConfigManager.IFA_HEADERSHOW)).booleanValue());
				hostedEmblemPanel = emblemPanel;
			} else {
				FLAG_WIDTH = hostedEmblemPanel.getFlagWidth();
				FlagLabel.BRIGHTNESS =hostedEmblemPanel.getBrightness();
				FlagLabel.GREY =hostedEmblemPanel.isGrey();
				FlagLabel.ROUNDFLAG =hostedEmblemPanel.isRoundly();
				FlagLabel[] flags = getAllCountries(false);
				for (int i = 0; i < flags.length; i++) {
					if (flags[i].isEnabled())
						enabled++;
				}
				hostedEmblemPanel.setFlagPanel(new FlagPanel(flags, enabled));
			}
		} catch (Exception e) {
			HOLogger.instance().error(ImageDesignPanel.class, e);
		}
	}

	public void refreshFlagPanel() {
		centerPanel.remove(visitedEmblemPanel);
		centerPanel.remove(hostedEmblemPanel);
		setVisitedEmblemPanel();
		setHostedEmblemPanel();
		centerPanel.add(visitedEmblemPanel);
		centerPanel.add(hostedEmblemPanel);
		centerPanel.validate();
		centerPanel.repaint();
		validate();
		repaint();
	}



	public final EmblemPanel getVisitedEmblemPanel() {
		return visitedEmblemPanel;
	}

	public final EmblemPanel getHostedEmblemPanel() {
		return hostedEmblemPanel;
	}

	public JRadioButton getAway() {
		return this.away;
	}

	public JRadioButton getHome() {
		return this.home;
	}

	public boolean isAnimGif() {
		return this.animGif.isSelected();
	}

	public JSpinner getDelaySpinner() {
		return this.delaySpinner;
	}

//	public void actionPerformed(ActionEvent e) {
//			this.homeAway = true;
//			this.textField.setText(this.emblemPanels.getHeaderText());
//			this.headerYesNo.setSelected(this.emblemPanels.isHeader());
//			this.greyColored.setSelected(this.emblemPanels.isGrey());
//			this.roundly.setSelected(this.emblemPanels.isRoundly());
//			this.percentSlider.setValue(this.emblemPanels.getBrightness());
//			this.sizeSpinner.setValue(new Integer(this.emblemPanels.getFlagWidth()));
//
//			refreshFlagPanel();
//	}

	private class TextKeyListener extends KeyAdapter {
		TextKeyListener() {
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			visitedEmblemPanel.setHeaderText(((JTextField) arg0.getSource()).getText());
		}
	}

	private class StateChangeListener implements ChangeListener {
		StateChangeListener() {
		}

		public void stateChanged(ChangeEvent arg0) {
			if ((arg0.getSource() instanceof JSpinner)) {
				if (((JSpinner) arg0.getSource()).getName().equals("size")) {
					int rowSize = ((Integer) ImageDesignPanel.this.sizeSpinner
							.getValue()).intValue();
					visitedEmblemPanel.setFlagWidth(rowSize);
					hostedEmblemPanel.setFlagWidth(rowSize);
				}
			} else if ((arg0.getSource() instanceof JCheckBox)) {
				JCheckBox box = (JCheckBox) arg0.getSource();
				if (box.getName().equals("header")) {
					visitedEmblemPanel.setHeader(box.isSelected());
					hostedEmblemPanel.setHeader(box.isSelected());
				} else if (box.getName().equals("grey")) {
					visitedEmblemPanel.setGrey(box.isSelected());
					hostedEmblemPanel.setGrey(box.isSelected());
				} else if (box.getName().equals("rounded")) {
					visitedEmblemPanel.setRoundly(box.isSelected());
					hostedEmblemPanel.setRoundly(box.isSelected());
					
				}

			} else if ((arg0.getSource() instanceof JSlider)) {
				visitedEmblemPanel.setBrightness(((JSlider) arg0.getSource()).getValue());
				hostedEmblemPanel.setBrightness(((JSlider) arg0.getSource()).getValue());
			}
			ImageDesignPanel.this.refreshFlagPanel();
		}
	}
	
	private JPanel getToolbar(){
		if(toolbar == null){
			toolbar = new JPanel();
			toolbar.setLayout(new FlowLayout(FlowLayout.LEADING));
		}
		return toolbar;
	}
	
	private FlagLabel[] getAllCountries(boolean homeAway) {
		WorldDetailLeague[] leagues =WorldDetailsManager.instance().getLeagues();
		FlagLabel[] flagLabels = null;
		// ArrayList ret = new ArrayList();
		flagLabels = new FlagLabel[leagues.length];
		try {
			for (int i = 0; i < leagues.length; i++) {
				FlagLabel flagLabel = new FlagLabel();
				flagLabel.setCountryId(leagues[i].getCountryId());
				flagLabel.setCountryName(leagues[i].getCountryName());
				try {
					flagLabel.setIcon(ImageUtilities.getFlagIcon(flagLabel.getCountryId()));
				} catch (Exception e) {
					System.out.println("Error getting image icon for country "
							+ flagLabel.getCountryId() + " "
							+ flagLabel.getCountryName() + "\n"
							+ e.getMessage());
					flagLabel.setIcon(ImageUtilities.getFlagIcon(-1));
				}
				flagLabel.setToolTipText(flagLabel.getCountryName());
				int flagLeagueID = leagues[i].getLeagueId();
				if (flagLeagueID == HOVerwaltung.instance().getModel().getBasics().getLiga())
					flagLabel.setHomeCountry(true);
				else {
					flagLabel.setEnabled(DBManager.instance().isIFALeagueIDinDB(flagLeagueID, homeAway));
				}
				flagLabels[i] = flagLabel;
			}

			Arrays.sort(flagLabels, new UniversalComparator(1));

		} catch (Exception e) {
			return new FlagLabel[0];
		} 
		return flagLabels;
	}
}