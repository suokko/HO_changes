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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Arrays;

import javax.swing.BorderFactory;
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

public class ImageDesignPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 6107052908932568929L;
	public static int FLAG_WIDTH = 8;
	public static int MIN_FLAG_WIDTH = 5;
	public static int MAX_FLAG_WIDTH = 12;
	private PluginIfaPanel pluginIfaPanel;
	private EmblemPanel visitedEmblemPanel;
	private EmblemPanel hostedEmblemPanel;
	private JSpinner sizeSpinner;
	private JPanel centerPanel;
	private JPanel hostedOptions;
	private JPanel visitedOptions;
	private JScrollPane scroll;
	private JTextField hostedTextField;
	private JTextField visitedTextField;
	private JRadioButton home;
	private JRadioButton away;
	private JCheckBox greyColoredCheckBox;
	private JCheckBox roundlyCheckBox;
	private JSlider percentSlider;
	private JCheckBox headerYesNoCheckBox;
	// private JCheckBox animGif;
	private JSpinner delaySpinner;
	private JPanel toolbar;
	private JButton saveAnimatedImage = new JButton(HOVerwaltung.instance().getLanguageString(
			"Speichern"));
	private JButton saveHostedImage = new JButton(HOVerwaltung.instance().getLanguageString(
			"Speichern"));
	private JButton saveVisitedImage = new JButton(HOVerwaltung.instance().getLanguageString(
			"Speichern"));

	public ImageDesignPanel(PluginIfaPanel pluginIfaPanel) {
		this.pluginIfaPanel = pluginIfaPanel;

		try {
			initialize();
			addListeners();
		} catch (Exception e) {
			HOLogger.instance().error(ImageDesignPanel.class, e);
		}
	}

	private void initialize() throws Exception {
		setLayout(new BorderLayout());

		FLAG_WIDTH = Integer.parseInt(ConfigManager.getTextFromConfig(ConfigManager.IFA_WIDTH));

		this.centerPanel = new JPanel();
		this.centerPanel.setLayout(new GridLayout(1, 2));

		initVisitedEmblemPanel();
		initHostedEmblemPanel();
		initToolbar();
		initHomeAwayPanel();

		centerPanel.add(visitedOptions);
		centerPanel.add(hostedOptions);

		add(toolbar, BorderLayout.NORTH);
		this.scroll = new JScrollPane(this.centerPanel);
		add(scroll, BorderLayout.CENTER);
	}

	private void initToolbar() {
		toolbar = new JPanel();
		toolbar.setLayout(new FlowLayout(FlowLayout.LEADING));

		JPanel visitedRow = new JPanel();
		toolbar.setLayout(new FlowLayout(FlowLayout.LEADING));

		this.headerYesNoCheckBox = new JCheckBox(HOVerwaltung.instance().getLanguageString(
				"showHeader"), new Boolean(
				ConfigManager.getTextFromConfig(ConfigManager.IFA_HEADERSHOW)).booleanValue());
		this.headerYesNoCheckBox.setName("header");
		toolbar.add(headerYesNoCheckBox);

		this.roundlyCheckBox = new JCheckBox(HOVerwaltung.instance().getLanguageString("Roundly"),
				new Boolean(ConfigManager.getTextFromConfig(ConfigManager.IFA_ROUNDLY))
						.booleanValue());
		this.roundlyCheckBox.setName("rounded");
		toolbar.add(roundlyCheckBox);

		this.greyColoredCheckBox = new JCheckBox(HOVerwaltung.instance().getLanguageString("Grey"),
				new Boolean(ConfigManager.getTextFromConfig(ConfigManager.IFA_GREY)).booleanValue());
		this.greyColoredCheckBox.setName("grey");
		toolbar.add(greyColoredCheckBox);

		this.percentSlider = new JSlider(0, 100, Integer.parseInt(ConfigManager
				.getTextFromConfig(ConfigManager.IFA_BRIGHTNESS)));
		this.percentSlider.setMajorTickSpacing(25);
		this.percentSlider.setMinorTickSpacing(5);
		this.percentSlider.setPaintTicks(true);
		this.percentSlider.setPaintLabels(true);
		toolbar.add(percentSlider);

		JPanel sizePanel = new JPanel(new FlowLayout(1, 0, 0));
		this.sizeSpinner = new JSpinner(new SpinnerNumberModel(FLAG_WIDTH, MIN_FLAG_WIDTH,
				MAX_FLAG_WIDTH, 1));
		this.sizeSpinner.setName("size");
		sizePanel.add(new JLabel(HOVerwaltung.instance().getLanguageString("Flaggen") + "/"
				+ (HOVerwaltung.instance().getLanguageString("Row") + ": ")));
		sizePanel.add(this.sizeSpinner);
		toolbar.add(sizePanel);

		// this.animGif = new
		// JCheckBox(HOVerwaltung.instance().getLanguageString(
		// "animatedGIF"),
		// new Boolean(ConfigManager
		// .getTextFromConfig(ConfigManager.IFA_GIFANIMATED))
		// .booleanValue());
		toolbar.add(new JLabel(HOVerwaltung.instance().getLanguageString("animatedGIF")));

		JPanel spinnerPanel = new JPanel(new FlowLayout(1, 0, 0));
		this.delaySpinner = new JSpinner(new SpinnerNumberModel(Double.parseDouble(ConfigManager
				.getTextFromConfig(ConfigManager.IFA_GIFDELAY)), 0.0D, 60.0D, 0.1D));
		spinnerPanel.add(new JLabel(HOVerwaltung.instance().getLanguageString("Delay") + ": "));
		spinnerPanel.add(this.delaySpinner);
		toolbar.add(spinnerPanel);

		saveAnimatedImage.addActionListener(this);
		toolbar.add(saveAnimatedImage);
	}

	private void initHomeAwayPanel() {
		hostedOptions = new JPanel();
		hostedOptions.setLayout(new GridBagLayout());
		hostedOptions.setBorder(BorderFactory.createTitledBorder(HOVerwaltung.instance()
				.getLanguageString("AutoFilterPanel.Home_Games")));

		visitedOptions = new JPanel();
		visitedOptions.setLayout(new GridBagLayout());
		visitedOptions.setBorder(BorderFactory.createTitledBorder(HOVerwaltung.instance()
				.getLanguageString("AutoFilterPanel.Away_Games")));

		GridBagConstraints constraints = new GridBagConstraints();

		constraints.weighty = 0;
		constraints.weightx = 0;
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		constraints.gridx = 1;
		constraints.gridy = 1;

		// JLabel hostLabel = new JLabel(HOVerwaltung.instance()
		// .getLanguageString("AutoFilterPanel.Home_Games"));
		// hostedOptions.add(hostLabel);
		//
		// JLabel visitLabel = new JLabel(HOVerwaltung.instance()
		// .getLanguageString("AutoFilterPanel.Away_Games"));
		// visitedOptions.add(visitLabel);

		this.hostedTextField = new JTextField(
				ConfigManager.getTextFromConfig(ConfigManager.IFA_HOSTEDHEADER));
		this.hostedTextField.addKeyListener(new TextKeyListener());
		this.hostedTextField.setPreferredSize(new Dimension(150, 25));
		hostedOptions.add(hostedTextField, constraints);

		this.visitedTextField = new JTextField(
				ConfigManager.getTextFromConfig(ConfigManager.IFA_VISITEDHEADER));
		this.visitedTextField.addKeyListener(new TextKeyListener());
		this.visitedTextField.setPreferredSize(new Dimension(150, 25));
		visitedOptions.add(visitedTextField, constraints);

		constraints.gridy = 5;
		hostedOptions.add(saveHostedImage, constraints);
		saveHostedImage.addActionListener(this);

		visitedOptions.add(saveVisitedImage, constraints);
		saveVisitedImage.addActionListener(this);

		constraints.gridy = 10;
		constraints.weightx = 1;
		constraints.weighty = 1;
		hostedOptions.add(hostedEmblemPanel, constraints);
		visitedOptions.add(visitedEmblemPanel, constraints);

	}

	private void initVisitedEmblemPanel() {
		int enabled = 0;
		try {
			if (visitedEmblemPanel == null) {
				FLAG_WIDTH = Integer.parseInt(ConfigManager
						.getTextFromConfig(ConfigManager.IFA_WIDTH));
				FlagLabel.BRIGHTNESS = Integer.parseInt(ConfigManager
						.getTextFromConfig(ConfigManager.IFA_BRIGHTNESS));
				FlagLabel.GREY = new Boolean(
						ConfigManager.getTextFromConfig(ConfigManager.IFA_GREY)).booleanValue();
				FlagLabel[] flags = getAllCountries(true);
				for (int i = 0; i < flags.length; i++) {
					if (flags[i].isEnabled())
						enabled++;
				}
				EmblemPanel emblemPanel = new EmblemPanel(flags, enabled);
				emblemPanel.setFlagWidth(FLAG_WIDTH);
				emblemPanel.setBrightness(FlagLabel.BRIGHTNESS);
				emblemPanel.setGrey(FlagLabel.GREY);
				emblemPanel.setRoundly(FlagLabel.ROUNDFLAG);
				String path = ConfigManager.getTextFromConfig(ConfigManager.IFA_VISITEDPATHEMBLEM);
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
				emblemPanel.setHeaderText(ConfigManager
						.getTextFromConfig(ConfigManager.IFA_VISITEDHEADER));
				emblemPanel.setHeader(new Boolean(ConfigManager
						.getTextFromConfig(ConfigManager.IFA_HEADERSHOW)).booleanValue());
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

	private void initHostedEmblemPanel() {
		int enabled = 0;
		try {
			if (hostedEmblemPanel == null) {
				FLAG_WIDTH = Integer.parseInt(ConfigManager
						.getTextFromConfig(ConfigManager.IFA_WIDTH));
				FlagLabel.BRIGHTNESS = Integer.parseInt(ConfigManager
						.getTextFromConfig(ConfigManager.IFA_BRIGHTNESS));
				FlagLabel.GREY = new Boolean(
						ConfigManager.getTextFromConfig(ConfigManager.IFA_GREY)).booleanValue();
				FlagLabel[] flags = getAllCountries(false);
				for (int i = 0; i < flags.length; i++) {
					if (flags[i].isEnabled())
						enabled++;
				}

				EmblemPanel emblemPanel = new EmblemPanel(flags, enabled);
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
				emblemPanel.setHeaderText(ConfigManager
						.getTextFromConfig(ConfigManager.IFA_HOSTEDHEADER));
				emblemPanel.setHeader(new Boolean(ConfigManager
						.getTextFromConfig(ConfigManager.IFA_HEADERSHOW)).booleanValue());
				hostedEmblemPanel = emblemPanel;
			} else {
				FLAG_WIDTH = hostedEmblemPanel.getFlagWidth();
				FlagLabel.BRIGHTNESS = hostedEmblemPanel.getBrightness();
				FlagLabel.GREY = hostedEmblemPanel.isGrey();
				FlagLabel.ROUNDFLAG = hostedEmblemPanel.isRoundly();
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

		// String hosted = hostedTextField.getText();
		// String visited = visitedTextField.getText();
		//
		// centerPanel.remove(visitedOptions);
		// centerPanel.remove(hostedOptions);

		initVisitedEmblemPanel();
		initHostedEmblemPanel();
		// initHomeAwayPanel();
		//
		// hostedTextField.setText(hosted);
		// visitedTextField.setText(visited);
		//
		// centerPanel.add(visitedOptions);
		// centerPanel.add(hostedOptions);
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

	/*
	 * public boolean isAnimGif() { return this.animGif.isSelected(); }
	 */

	public JSpinner getDelaySpinner() {
		return this.delaySpinner;
	}

	private void addListeners() {
		this.headerYesNoCheckBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				visitedEmblemPanel.setHeader(e.getStateChange() == ItemEvent.SELECTED);
				hostedEmblemPanel.setHeader(e.getStateChange() == ItemEvent.SELECTED);
				ImageDesignPanel.this.refreshFlagPanel();
			}
		});

		this.roundlyCheckBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				visitedEmblemPanel.setRoundly(e.getStateChange() == ItemEvent.SELECTED);
				hostedEmblemPanel.setRoundly(e.getStateChange() == ItemEvent.SELECTED);
				ImageDesignPanel.this.refreshFlagPanel();
			}
		});

		this.greyColoredCheckBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				visitedEmblemPanel.setGrey(e.getStateChange() == ItemEvent.SELECTED);
				hostedEmblemPanel.setGrey(e.getStateChange() == ItemEvent.SELECTED);
				ImageDesignPanel.this.refreshFlagPanel();
			}
		});

		this.percentSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent evt) {
				if (!percentSlider.getValueIsAdjusting()) {
					visitedEmblemPanel.setBrightness(percentSlider.getValue());
					hostedEmblemPanel.setBrightness(percentSlider.getValue());
					ImageDesignPanel.this.refreshFlagPanel();
				}
			}
		});

		this.sizeSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent evt) {
				int rowSize = ((Integer) ImageDesignPanel.this.sizeSpinner.getValue()).intValue();
				visitedEmblemPanel.setFlagWidth(rowSize);
				hostedEmblemPanel.setFlagWidth(rowSize);
				ImageDesignPanel.this.refreshFlagPanel();
			}
		});

	}

	private class TextKeyListener extends KeyAdapter {
		TextKeyListener() {
		}

		@Override
		public void keyReleased(KeyEvent ke) {
			JTextField source = (JTextField) ke.getSource();

			if (source == visitedTextField) {
				visitedEmblemPanel.setHeaderText(source.getText());
			} else {
				hostedEmblemPanel.setHeaderText(source.getText());
			}
		}
	}

	private FlagLabel[] getAllCountries(boolean homeAway) {
		WorldDetailLeague[] leagues = WorldDetailsManager.instance().getLeagues();
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
							+ flagLabel.getCountryId() + " " + flagLabel.getCountryName() + "\n"
							+ e.getMessage());
					flagLabel.setIcon(ImageUtilities.getFlagIcon(-1));
				}
				flagLabel.setToolTipText(flagLabel.getCountryName());
				int flagLeagueID = leagues[i].getLeagueId();
				if (flagLeagueID == HOVerwaltung.instance().getModel().getBasics().getLiga())
					flagLabel.setHomeCountry(true);
				else {
					flagLabel.setEnabled(DBManager.instance().isIFALeagueIDinDB(flagLeagueID,
							homeAway));
				}
				flagLabels[i] = flagLabel;
			}

			Arrays.sort(flagLabels, new UniversalComparator(1));

		} catch (Exception e) {
			return new FlagLabel[0];
		}
		return flagLabels;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == saveAnimatedImage) {
			GlobalActionsListener.saveImage(true, true, this, getParent());
		}

		if (e.getSource() == saveHostedImage) {
			GlobalActionsListener.saveImage(false, true, this, getParent());
		}
		if (e.getSource() == saveVisitedImage) {
			GlobalActionsListener.saveImage(false, false, this, getParent());
		}
	}
}