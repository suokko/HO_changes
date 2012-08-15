package ho.module.ifa2;

import ho.core.model.HOVerwaltung;
import ho.core.module.config.ModuleConfig;
import ho.module.ifa2.config.Config;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.math.BigDecimal;

import javax.swing.ButtonGroup;
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

	private static final long serialVersionUID = 4562976951725733955L;
	public static int FLAG_WIDTH = 8;
	public static int MIN_FLAG_WIDTH = 5;
	public static int MAX_FLAG_WIDTH = 12;
	private TablesPanel pluginIfaPanel;
	private EmblemPanel awayEmblemPanel;
	private EmblemPanel homeEmblemPanel;
	private JSpinner sizeSpinner;
	private boolean homeAway = true;
	private JPanel northPanel;
	private JPanel centerPanel;
	private JScrollPane scroll;
	private JTextField textField;
	private JRadioButton home;
	private JRadioButton away;
	private JCheckBox greyColored;
	private JCheckBox roundly;
	private JSlider percentSlider;
	private JCheckBox headerYesNo;
	private JCheckBox footerYesNo;
	private JCheckBox animGif;
	private JSpinner delaySpinner;

	public ImageDesignPanel(TablesPanel pluginIfaPanel) {
		this.pluginIfaPanel = pluginIfaPanel;
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(5, 5, 5, 5);

		this.centerPanel = new JPanel();
		this.centerPanel.setLayout(new GridBagLayout());
		this.northPanel = new JPanel();
		this.northPanel.setLayout(new GridBagLayout());

		FLAG_WIDTH = ModuleConfig.instance().getInteger(Config.VISITED_FLAG_WIDTH.toString(),
				Integer.valueOf(8));

		StateChangeListener changeListener = new StateChangeListener();
		JButton button = new JButton("Update");
		button.setActionCommand("update");
		button.addActionListener(new GlobalActionsListener(this.pluginIfaPanel));
		add(this.northPanel, button, constraints, 0, 0, 2, 1);

		this.home = new JRadioButton("Visited Countries", true);
		this.home.setActionCommand("visited");
		this.home.addActionListener(this);
		this.away = new JRadioButton("Hosted Countries", false);
		this.away.setActionCommand("hosted");
		this.away.addActionListener(this);
		ButtonGroup group = new ButtonGroup();
		group.add(this.home);
		group.add(this.away);
		add(this.northPanel, this.home, constraints, 0, 1, 1, 1);
		add(this.northPanel, this.away, constraints, 1, 1, 1, 1);

		this.headerYesNo = new JCheckBox("Show Header", ModuleConfig.instance().getBoolean(
				Config.SHOW_VISITED_HEADER.toString(), Boolean.TRUE));
		this.headerYesNo.setName("header");
		this.headerYesNo.addChangeListener(changeListener);
		add(this.northPanel, this.headerYesNo, constraints, 0, 2, 1, 1);

		this.footerYesNo = new JCheckBox("Show Footer", ModuleConfig.instance().getBoolean(
				Config.SHOW_VISITED_FOOTER.toString(), Boolean.TRUE));
		this.footerYesNo.setName("footer");
		this.footerYesNo.addChangeListener(changeListener);
		add(this.northPanel, this.footerYesNo, constraints, 1, 2, 1, 1);

		this.roundly = new JCheckBox("Roundly", ModuleConfig.instance().getBoolean(
				Config.VISITED_ROUNDLY.toString(), Boolean.FALSE));
		this.roundly.setName("rounded");
		this.roundly.addChangeListener(changeListener);
		add(this.northPanel, this.roundly, constraints, 0, 3, 1, 1);

		this.greyColored = new JCheckBox("Grey", ModuleConfig.instance().getBoolean(
				Config.VISITED_GREY.toString(), Boolean.TRUE));
		this.greyColored.setName("grey");
		this.greyColored.addChangeListener(changeListener);
		add(this.northPanel, this.greyColored, constraints, 1, 3, 1, 1);

		this.percentSlider = new JSlider(0, 100, ModuleConfig.instance().getInteger(
				Config.VISITED_BRIGHTNESS.toString(), Integer.valueOf(50)));
		this.percentSlider.addChangeListener(changeListener);
		this.percentSlider.setMajorTickSpacing(25);
		this.percentSlider.setMinorTickSpacing(5);
		this.percentSlider.setPaintTicks(true);
		this.percentSlider.setPaintLabels(true);
		add(this.northPanel, this.percentSlider, constraints, 1, 4, 1, 1);

		JPanel sizePanel = new JPanel(new FlowLayout(1, 0, 0));
		this.sizeSpinner = new JSpinner(new SpinnerNumberModel(FLAG_WIDTH, MIN_FLAG_WIDTH,
				MAX_FLAG_WIDTH, 1));
		this.sizeSpinner.setName("size");
		this.sizeSpinner.addChangeListener(changeListener);
		sizePanel.add(new JLabel("Flags/Row: "));
		sizePanel.add(this.sizeSpinner);
		add(this.northPanel, sizePanel, constraints, 0, 5, 1, 1);

		this.textField = new JTextField(ModuleConfig.instance().getString(
				Config.VISITED_HEADER_TEXT.toString(),
				HOVerwaltung.instance().getLanguageString("ifa.visitedHeader.defaultText")));
		this.textField.addKeyListener(new TextKeyListener());
		this.textField.setPreferredSize(new Dimension(150, 25));
		add(this.northPanel, this.textField, constraints, 1, 5, 1, 1);

		this.animGif = new JCheckBox("Animated GIF", ModuleConfig.instance().getBoolean(
				Config.ANIMATED_GIF.toString(), Boolean.FALSE));
		add(this.northPanel, this.animGif, constraints, 0, 6, 1, 1);

		JPanel spinnerPanel = new JPanel(new FlowLayout(1, 0, 0));
		double value = ModuleConfig.instance()
				.getBigDecimal(Config.ANIMATED_GIF_DELAY.toString(), BigDecimal.valueOf(5))
				.doubleValue();
		this.delaySpinner = new JSpinner(new SpinnerNumberModel(value, 0.0, 60.0, 0.1));
		spinnerPanel.add(new JLabel("Delay: "));
		spinnerPanel.add(this.delaySpinner);
		add(this.northPanel, spinnerPanel, constraints, 1, 6, 1, 1);

		setEmblemPanel(true);
		setEmblemPanel(false);

		add(this.centerPanel, this.awayEmblemPanel, constraints, 0, 0, 1, 1);
		JButton saveImage = new JButton("Save Image");
		saveImage.addActionListener(new GlobalActionsListener(this.pluginIfaPanel));
		saveImage.setActionCommand("saveImage");
		add(this.centerPanel, saveImage, constraints, 0, 1, 1, 1);

		add(this.northPanel, "North");
		this.scroll = new JScrollPane(this.centerPanel);
		add(this.scroll, "Center");
	}

	private void add(JPanel panel, Component c, GridBagConstraints constraints, int x, int y,
			int w, int h) {
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = w;
		constraints.gridheight = h;
		panel.add(c, constraints);
	}

	private void setEmblemPanel(boolean homeAway) {

		if (homeAway && this.awayEmblemPanel == null) {
			FLAG_WIDTH = ModuleConfig.instance().getInteger(Config.VISITED_FLAG_WIDTH.toString(),
					Integer.valueOf(8));
			FlagLabel.BRIGHTNESS = ModuleConfig.instance().getInteger(
					Config.VISITED_BRIGHTNESS.toString(), Integer.valueOf(50));
			FlagLabel.GREY = ModuleConfig.instance().getBoolean(Config.VISITED_GREY.toString(),
					Boolean.TRUE);
			String emblemPath = ModuleConfig.instance().getString(
					Config.VISITED_EMBLEM_PATH.toString(), "");

			FlagLabel[] flags = PluginIfaUtils.getAllCountries(homeAway);
			int enabled = 0;
			for (int i = 0; i < flags.length; i++) {
				if (flags[i].isEnabled()) {
					enabled++;
				}
			}
			this.awayEmblemPanel = new EmblemPanel(flags, enabled);
			this.awayEmblemPanel.setFlagWidth(FLAG_WIDTH);
			this.awayEmblemPanel.setBrightness(FlagLabel.BRIGHTNESS);
			this.awayEmblemPanel.setGrey(FlagLabel.GREY);
			this.awayEmblemPanel.setRoundly(FlagLabel.ROUNDFLAG);
			if (!emblemPath.equals("")) {
				File file = new File(emblemPath);
				if (file.exists()) {
					ImageIcon imageIcon = new ImageIcon(emblemPath);
					if (imageIcon != null) {
						this.awayEmblemPanel.setLogo(imageIcon);
						this.awayEmblemPanel.setImagePath(emblemPath);
					}
				}
			}
			this.awayEmblemPanel.setHeaderText(ModuleConfig.instance().getString(
					Config.VISITED_HEADER_TEXT.toString(),
					HOVerwaltung.instance().getLanguageString("ifa.visitedHeader.defaultText")));
			this.awayEmblemPanel.setHeader(ModuleConfig.instance().getBoolean(
					Config.SHOW_VISITED_HEADER.toString(), Boolean.TRUE));
			this.awayEmblemPanel.setFooter(ModuleConfig.instance().getBoolean(
					Config.SHOW_VISITED_FOOTER.toString(), Boolean.TRUE));
		} else if (!homeAway && this.homeEmblemPanel == null) {
			FLAG_WIDTH = ModuleConfig.instance().getInteger(Config.HOSTED_FLAG_WIDTH.toString(),
					Integer.valueOf(8));
			FlagLabel.BRIGHTNESS = ModuleConfig.instance().getInteger(
					Config.HOSTED_BRIGHTNESS.toString(), Integer.valueOf(50));
			FlagLabel.GREY = ModuleConfig.instance().getBoolean(Config.HOSTED_GREY.toString(),
					Boolean.TRUE);
			String emblemPath = ModuleConfig.instance().getString(
					Config.HOSTED_EMBLEM_PATH.toString(), "");

			int enabled = 0;
			FlagLabel[] flags = PluginIfaUtils.getAllCountries(homeAway);
			for (int i = 0; i < flags.length; i++) {
				if (flags[i].isEnabled()) {
					enabled++;
				}
			}
			this.homeEmblemPanel = new EmblemPanel(flags, enabled);
			this.homeEmblemPanel.setFlagWidth(FLAG_WIDTH);
			this.homeEmblemPanel.setBrightness(FlagLabel.BRIGHTNESS);
			this.homeEmblemPanel.setGrey(FlagLabel.GREY);
			this.homeEmblemPanel.setRoundly(FlagLabel.ROUNDFLAG);
			if (!emblemPath.equals("")) {
				File file = new File(emblemPath);
				if (file.exists()) {
					ImageIcon imageIcon = new ImageIcon(emblemPath);
					if (imageIcon != null) {
						this.homeEmblemPanel.setLogo(imageIcon);
						this.homeEmblemPanel.setImagePath(emblemPath);
					}
				}
			}
			this.homeEmblemPanel.setHeaderText(ModuleConfig.instance().getString(
					Config.HOSTED_HEADER_TEXT.toString(),
					HOVerwaltung.instance().getLanguageString("ifa.hostedHeader.defaultText")));
			this.homeEmblemPanel.setHeader(ModuleConfig.instance().getBoolean(
					Config.SHOW_HOSTED_HEADER.toString(), Boolean.TRUE));
			this.homeEmblemPanel.setFooter(ModuleConfig.instance().getBoolean(
					Config.SHOW_HOSTED_FOOTER.toString(), Boolean.TRUE));
		}
	}

	public void refreshFlagPanel() {
		this.centerPanel.remove(this.awayEmblemPanel);
		this.centerPanel.remove(this.homeEmblemPanel);
		setEmblemPanel(this.homeAway);
		this.centerPanel.add(this.awayEmblemPanel, new GridBagConstraints());
		this.centerPanel.validate();
		this.centerPanel.repaint();
		validate();
		repaint();
	}

	public EmblemPanel getEmblemPanel(boolean homeAway) {
		if (homeAway) {
			return this.awayEmblemPanel;
		} else {
			return this.homeEmblemPanel;
		}
	}

	public boolean isHomeAway() {
		return this.homeAway;
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

	@Override
	public void actionPerformed(ActionEvent e) {
		EmblemPanel panel = null;
		if (e.getActionCommand().equals("visited")) {
			this.homeAway = true;
			panel = this.awayEmblemPanel;
		} else if (e.getActionCommand().equals("hosted")) {
			this.homeAway = false;
			panel = this.homeEmblemPanel;
		}

		this.textField.setText(panel.getHeaderText());
		this.headerYesNo.setSelected(panel.isHeader());
		this.footerYesNo.setSelected(panel.isFooter());
		this.greyColored.setSelected(panel.isGrey());
		this.roundly.setSelected(panel.isRoundly());
		this.percentSlider.setValue(panel.getBrightness());
		this.sizeSpinner.setValue(new Integer(panel.getFlagWidth()));
		refreshFlagPanel();
	}

	private class TextKeyListener extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent arg0) {
			if (ImageDesignPanel.this.homeAway) {
				ImageDesignPanel.this.awayEmblemPanel.setHeaderText(((JTextField) arg0.getSource())
						.getText());
			} else {
				ImageDesignPanel.this.homeEmblemPanel.setHeaderText(((JTextField) arg0.getSource())
						.getText());
			}
		}
	}

	private class StateChangeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent arg0) {
			EmblemPanel panel = null;
			if (ImageDesignPanel.this.homeAway) {
				panel = ImageDesignPanel.this.awayEmblemPanel;
			} else {
				panel = ImageDesignPanel.this.homeEmblemPanel;
			}

			if ((arg0.getSource() instanceof JSpinner)) {
				if (((JSpinner) arg0.getSource()).getName().equals("size")) {
					int rowSize = ((Integer) ImageDesignPanel.this.sizeSpinner.getValue())
							.intValue();
					panel.setFlagWidth(rowSize);
					ImageDesignPanel.this.refreshFlagPanel();
				}
			} else if ((arg0.getSource() instanceof JCheckBox)) {
				JCheckBox box = (JCheckBox) arg0.getSource();
				if (box.getName().equals("header")) {
					panel.setHeader(box.isSelected());
					ImageDesignPanel.this.refreshFlagPanel();
				} else if (box.getName().equals("footer")) {
					panel.setFooter(box.isSelected());
					ImageDesignPanel.this.refreshFlagPanel();
				} else if (box.getName().equals("grey")) {
					panel.setGrey(box.isSelected());
					ImageDesignPanel.this.refreshFlagPanel();
				} else if (box.getName().equals("rounded")) {
					panel.setRoundly(box.isSelected());
					ImageDesignPanel.this.refreshFlagPanel();
				}
			} else if ((arg0.getSource() instanceof JSlider)) {
				panel.setBrightness(((JSlider) arg0.getSource()).getValue());
				ImageDesignPanel.this.refreshFlagPanel();
			}
		}
	}
}