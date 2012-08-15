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
	private PluginIfaPanel pluginIfaPanel;
	private EmblemPanel[] emblemPanels = new EmblemPanel[2];
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

	public ImageDesignPanel(PluginIfaPanel pluginIfaPanel) {
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

		add(this.centerPanel, this.emblemPanels[1], constraints, 0, 0, 1, 1);
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
		int enabled = 0;
		if (this.emblemPanels[1] == null) {
			String emblemPath = "";
			if (homeAway) {
				FLAG_WIDTH = ModuleConfig.instance().getInteger(
						Config.VISITED_FLAG_WIDTH.toString(), Integer.valueOf(8));
				FlagLabel.BRIGHTNESS = ModuleConfig.instance().getInteger(
						Config.VISITED_BRIGHTNESS.toString(), Integer.valueOf(50));
				FlagLabel.GREY = ModuleConfig.instance().getBoolean(Config.VISITED_GREY.toString(),
						Boolean.TRUE);
				emblemPath = ModuleConfig.instance().getString(
						Config.VISITED_EMBLEM_PATH.toString(), "");
			} else {
				FLAG_WIDTH = ModuleConfig.instance().getInteger(
						Config.HOSTED_FLAG_WIDTH.toString(), Integer.valueOf(8));
				FlagLabel.BRIGHTNESS = ModuleConfig.instance().getInteger(
						Config.HOSTED_BRIGHTNESS.toString(), Integer.valueOf(50));
				FlagLabel.GREY = ModuleConfig.instance().getBoolean(Config.HOSTED_GREY.toString(),
						Boolean.TRUE);
				emblemPath = ModuleConfig.instance().getString(
						Config.HOSTED_EMBLEM_PATH.toString(), "");
			}

			FlagLabel[] flags = PluginIfaUtils.getAllCountries(homeAway);
			for (int i = 0; i < flags.length; i++) {
				if (flags[i].isEnabled())
					enabled++;
			}
			EmblemPanel emblemPanel = new EmblemPanel(flags, enabled);
			emblemPanel.setFlagWidth(FLAG_WIDTH);
			emblemPanel.setBrightness(FlagLabel.BRIGHTNESS);
			emblemPanel.setGrey(FlagLabel.GREY);
			emblemPanel.setRoundly(FlagLabel.ROUNDFLAG);
			if (!emblemPath.equals("")) {
				File file = new File(emblemPath);
				if (file.exists()) {
					ImageIcon imageIcon = new ImageIcon(emblemPath);
					if (imageIcon != null) {
						emblemPanel.setLogo(imageIcon);
						emblemPanel.setImagePath(emblemPath);
					}
				}
			}
			if (homeAway) {
				emblemPanel
						.setHeaderText(ModuleConfig.instance().getString(
								Config.VISITED_HEADER_TEXT.toString(),
								HOVerwaltung.instance().getLanguageString(
										"ifa.visitedHeader.defaultText")));
				emblemPanel.setHeader(ModuleConfig.instance().getBoolean(
						Config.SHOW_VISITED_HEADER.toString(), Boolean.TRUE));
				emblemPanel.setFooter(ModuleConfig.instance().getBoolean(
						Config.SHOW_VISITED_FOOTER.toString(), Boolean.TRUE));
			} else {
				emblemPanel.setHeaderText(ModuleConfig.instance().getString(
						Config.HOSTED_HEADER_TEXT.toString(),
						HOVerwaltung.instance().getLanguageString("ifa.hostedHeader.defaultText")));
				emblemPanel.setHeader(ModuleConfig.instance().getBoolean(
						Config.SHOW_HOSTED_HEADER.toString(), Boolean.TRUE));
				emblemPanel.setFooter(ModuleConfig.instance().getBoolean(
						Config.SHOW_HOSTED_FOOTER.toString(), Boolean.TRUE));
			}
			this.emblemPanels[(homeAway ? 0 : 1)] = emblemPanel;
		} else {
			FLAG_WIDTH = this.emblemPanels[1].getFlagWidth();
			FlagLabel.BRIGHTNESS = this.emblemPanels[1].getBrightness();
			FlagLabel.GREY = this.emblemPanels[1].isGrey();
			FlagLabel.ROUNDFLAG = this.emblemPanels[1].isRoundly();
			FlagLabel[] flags = PluginIfaUtils.getAllCountries(homeAway);
			for (int i = 0; i < flags.length; i++) {
				if (flags[i].isEnabled())
					enabled++;
			}
			this.emblemPanels[1].setFlagPanel(new FlagPanel(flags, enabled));
		}
	}

	public void refreshFlagPanel() {
		this.centerPanel.remove(this.emblemPanels[0]);
		this.centerPanel.remove(this.emblemPanels[1]);
		setEmblemPanel(this.homeAway);
		this.centerPanel.add(this.emblemPanels[1], new GridBagConstraints());
		this.centerPanel.validate();
		this.centerPanel.repaint();
		validate();
		repaint();
	}

	public EmblemPanel getEmblemPanel(boolean homeAway) {
		return this.emblemPanels[1];
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
		if (this.emblemPanels[1].getFlagPanel() == null)
			return;
		if (e.getActionCommand().equals("visited")) {
			this.homeAway = true;
			this.textField.setText(this.emblemPanels[1].getHeaderText());
			this.headerYesNo.setSelected(this.emblemPanels[1].isHeader());
			this.footerYesNo.setSelected(this.emblemPanels[1].isFooter());
			this.greyColored.setSelected(this.emblemPanels[1].isGrey());
			this.roundly.setSelected(this.emblemPanels[1].isRoundly());
			this.percentSlider.setValue(this.emblemPanels[1].getBrightness());
			this.sizeSpinner.setValue(new Integer(this.emblemPanels[1].getFlagWidth()));

			refreshFlagPanel();
		}
		if (e.getActionCommand().equals("hosted")) {
			this.homeAway = false;
			this.textField.setText(this.emblemPanels[1].getHeaderText());
			this.headerYesNo.setSelected(this.emblemPanels[1].isHeader());
			this.footerYesNo.setSelected(this.emblemPanels[1].isFooter());
			this.greyColored.setSelected(this.emblemPanels[1].isGrey());
			this.roundly.setSelected(this.emblemPanels[1].isRoundly());
			this.percentSlider.setValue(this.emblemPanels[1].getBrightness());
			this.sizeSpinner.setValue(new Integer(this.emblemPanels[1].getFlagWidth()));
			refreshFlagPanel();
		}
	}

	private class TextKeyListener extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent arg0) {
			ImageDesignPanel.this.emblemPanels[1].setHeaderText(((JTextField) arg0.getSource())
					.getText());
		}
	}

	private class StateChangeListener implements ChangeListener {
		
		@Override
		public void stateChanged(ChangeEvent arg0) {
			if ((arg0.getSource() instanceof JSpinner)) {
				if (((JSpinner) arg0.getSource()).getName().equals("size")) {
					int rowSize = ((Integer) ImageDesignPanel.this.sizeSpinner.getValue())
							.intValue();
					ImageDesignPanel.this.emblemPanels[1].setFlagWidth(rowSize);
					ImageDesignPanel.this.refreshFlagPanel();
				}
			} else if ((arg0.getSource() instanceof JCheckBox)) {
				JCheckBox box = (JCheckBox) arg0.getSource();
				if (box.getName().equals("header")) {
					ImageDesignPanel.this.emblemPanels[1].setHeader(box.isSelected());
					ImageDesignPanel.this.refreshFlagPanel();
				} else if (box.getName().equals("footer")) {
					ImageDesignPanel.this.emblemPanels[1].setFooter(box.isSelected());
					ImageDesignPanel.this.refreshFlagPanel();
				} else if (box.getName().equals("grey")) {
					ImageDesignPanel.this.emblemPanels[1].setGrey(box.isSelected());
					ImageDesignPanel.this.refreshFlagPanel();
				} else if (box.getName().equals("rounded")) {
					ImageDesignPanel.this.emblemPanels[1].setRoundly(box.isSelected());
					ImageDesignPanel.this.refreshFlagPanel();
				}
			} else if ((arg0.getSource() instanceof JSlider)) {
				ImageDesignPanel.this.emblemPanels[1].setBrightness(((JSlider) arg0.getSource())
						.getValue());
				ImageDesignPanel.this.refreshFlagPanel();
			}
		}
	}
}