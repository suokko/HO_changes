package ho.module.ifa2;

import ho.core.model.HOVerwaltung;
import ho.core.module.config.ModuleConfig;
import ho.module.ifa2.config.Config;
import ho.module.ifa2.gif.Gif89Encoder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ImageDesignPanel extends JPanel {

	private static final long serialVersionUID = 4562976951725733955L;
	public static int FLAG_WIDTH = 8;
	private static final int MIN_FLAG_WIDTH = 5;
	private static final int MAX_FLAG_WIDTH = 12;
	private EmblemPanel emblemPanel;
	private JSpinner sizeSpinner;
	private boolean away;
	private JPanel centerPanel;
	private JTextField headerTextField;
	private JCheckBox greyColoredCheckBox;
	private JCheckBox roundlyCheckBox;
	private JSlider brightnessSlider;
	private JCheckBox headerYesNoCheckBox;
	private JCheckBox animGifCheckBox;
	private JSpinner delaySpinner;
	private JLabel delayLabel;
	private JButton saveImageButton;

	public ImageDesignPanel() {
		initialize();
		addListeners();
		setAway(true);
	}

	public void setAway(boolean away) {
		this.away = away;

		FlagDisplayModel flagDisplayModel = new FlagDisplayModel();
		int flagWidth;
		String emblemPath;
		String headerText;
		boolean roundly;
		boolean grey;
		boolean showHeader;

		if (this.away) {
			flagWidth = ModuleConfig.instance().getInteger(Config.VISITED_FLAG_WIDTH.toString(),
					Integer.valueOf(8));
			emblemPath = ModuleConfig.instance().getString(Config.VISITED_EMBLEM_PATH.toString(),
					"");
			headerText = ModuleConfig.instance().getString(Config.VISITED_HEADER_TEXT.toString(),
					HOVerwaltung.instance().getLanguageString("ifa.visitedHeader.defaultText"));
			flagDisplayModel.setBrightness(ModuleConfig.instance().getInteger(
					Config.VISITED_BRIGHTNESS.toString(), Integer.valueOf(50)));
			grey = ModuleConfig.instance().getBoolean(Config.VISITED_GREY.toString(), Boolean.TRUE)
					.booleanValue();
			roundly = ModuleConfig.instance()
					.getBoolean(Config.VISITED_ROUNDLY.toString(), Boolean.TRUE).booleanValue();
			showHeader = ModuleConfig.instance()
					.getBoolean(Config.SHOW_VISITED_HEADER.toString(), Boolean.TRUE).booleanValue();
		} else {
			flagWidth = ModuleConfig.instance().getInteger(Config.HOSTED_FLAG_WIDTH.toString(),
					Integer.valueOf(8));
			emblemPath = ModuleConfig.instance()
					.getString(Config.HOSTED_EMBLEM_PATH.toString(), "");
			headerText = ModuleConfig.instance().getString(Config.VISITED_HEADER_TEXT.toString(),
					HOVerwaltung.instance().getLanguageString("ifa.hostedHeader.defaultText"));
			flagDisplayModel.setBrightness(ModuleConfig.instance().getInteger(
					Config.HOSTED_BRIGHTNESS.toString(), Integer.valueOf(50)));
			grey = ModuleConfig.instance().getBoolean(Config.HOSTED_GREY.toString(), Boolean.TRUE)
					.booleanValue();
			roundly = ModuleConfig.instance()
					.getBoolean(Config.HOSTED_ROUNDLY.toString(), Boolean.TRUE).booleanValue();
			showHeader = ModuleConfig.instance()
					.getBoolean(Config.SHOW_HOSTED_HEADER.toString(), Boolean.TRUE).booleanValue();
		}
		flagDisplayModel.setRoundFlag(roundly);
		flagDisplayModel.setGrey(grey);		
		flagDisplayModel.setFlagWidth(flagWidth);
		if (this.emblemPanel != null) {
			this.centerPanel.remove(this.emblemPanel);
		}
		this.emblemPanel = new EmblemPanel(away, flagDisplayModel);
		if (!emblemPath.equals("")) {
			File file = new File(emblemPath);
			if (file.exists()) {
				ImageIcon imageIcon = new ImageIcon(emblemPath);
				if (imageIcon != null) {
					this.emblemPanel.setLogo(imageIcon);
					this.emblemPanel.setImagePath(emblemPath);
				}
			}
		}
		this.roundlyCheckBox.setSelected(roundly);
		this.greyColoredCheckBox.setSelected(grey);
		this.sizeSpinner.setValue(Integer.valueOf(flagWidth));
		this.headerYesNoCheckBox.setSelected(showHeader);		
		this.emblemPanel.setHeader(showHeader);
		this.emblemPanel.setHeaderText(headerText);
		this.centerPanel.add(this.emblemPanel, new GridBagConstraints());
		this.centerPanel.validate();
		this.centerPanel.repaint();
	}

	private void initialize() {
		FLAG_WIDTH = ModuleConfig.instance().getInteger(Config.VISITED_FLAG_WIDTH.toString(),
				Integer.valueOf(8));

		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridBagLayout());

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.anchor = GridBagConstraints.WEST;
		gbc1.insets = new Insets(5, 5, 5, 5);
		this.roundlyCheckBox = new JCheckBox("Roundly");
		this.roundlyCheckBox.setSelected(false);
		panel.add(this.roundlyCheckBox, gbc1);
		this.greyColoredCheckBox = new JCheckBox("Grey", true);
		this.greyColoredCheckBox.setEnabled(true);
		gbc1.gridx = 1;
		panel.add(this.greyColoredCheckBox, gbc1);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);
		northPanel.add(panel, gbc);
		
		JLabel brightnessLabel = new JLabel("Brightness:");
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		northPanel.add(brightnessLabel, gbc);

		this.brightnessSlider = new JSlider(0, 100, 50);
		this.brightnessSlider.setMinimumSize(new Dimension(200,
				brightnessSlider.getPreferredSize().height));
		this.brightnessSlider.setMajorTickSpacing(25);
		this.brightnessSlider.setMinorTickSpacing(5);
		this.brightnessSlider.setPaintTicks(true);
		this.brightnessSlider.setPaintLabels(true);
		gbc.gridx = 1;
		gbc.gridwidth = 2;
		northPanel.add(this.brightnessSlider, gbc);

		JLabel sizeLabel = new JLabel("Flags/Row: ");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		northPanel.add(sizeLabel, gbc);
		
		this.sizeSpinner = new JSpinner(new SpinnerNumberModel(FLAG_WIDTH, MIN_FLAG_WIDTH,
				MAX_FLAG_WIDTH, 1));
		this.sizeSpinner.setName("size");
		gbc.gridx = 1;
		gbc.gridwidth = 2;		
		northPanel.add(this.sizeSpinner, gbc);

		this.headerYesNoCheckBox = new JCheckBox("Show Header", true);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		northPanel.add(this.headerYesNoCheckBox, gbc);

		this.headerTextField = new JTextField(ModuleConfig.instance().getString(
				Config.VISITED_HEADER_TEXT.toString(),
				HOVerwaltung.instance().getLanguageString("ifa.visitedHeader.defaultText")));
		this.headerTextField.setPreferredSize(new Dimension(150, 25));
		this.headerTextField.setMinimumSize(new Dimension(150, 25));
		gbc.gridx = 1;
		gbc.gridwidth = 2;
		northPanel.add(this.headerTextField, gbc);

		this.animGifCheckBox = new JCheckBox("Animated GIF", ModuleConfig.instance().getBoolean(
				Config.ANIMATED_GIF.toString(), Boolean.FALSE));
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		northPanel.add(this.animGifCheckBox, gbc);

		this.delayLabel = new JLabel("Delay:");
		this.delayLabel.setEnabled(false);
		gbc.gridx = 1;
		gbc.insets = new Insets(5, 5, 5, 2);
		northPanel.add(this.delayLabel, gbc);
		double value = ModuleConfig.instance()
				.getBigDecimal(Config.ANIMATED_GIF_DELAY.toString(), BigDecimal.valueOf(5))
				.doubleValue();
		this.delaySpinner = new JSpinner(new SpinnerNumberModel(value, 0.0, 60.0, 0.1));
		this.delaySpinner.setEnabled(false);
		gbc.gridx = 2;
		gbc.insets = new Insets(5, 2, 5, 5);
		northPanel.add(this.delaySpinner, gbc);

		this.saveImageButton = new JButton("Save Image");
		this.saveImageButton.setActionCommand("saveImage");

		this.centerPanel = new JPanel();
		this.centerPanel.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridy = 1;
		this.centerPanel.add(this.saveImageButton, gbc);

		setLayout(new BorderLayout());
		add(northPanel, BorderLayout.NORTH);
		add(new JScrollPane(this.centerPanel), BorderLayout.CENTER);
	}

	public void refreshFlagPanel() {
		this.centerPanel.validate();
		this.centerPanel.repaint();
		validate();
		repaint();
	}

	public EmblemPanel getEmblemPanel() {
		return this.emblemPanel;
	}

	public boolean isAnimGif() {
		return this.animGifCheckBox.isSelected();
	}

	public JSpinner getDelaySpinner() {
		return this.delaySpinner;
	}

	private void addListeners() {
		this.headerYesNoCheckBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				boolean selected = e.getStateChange() == ItemEvent.SELECTED;
				headerTextField.setEnabled(selected);
				if (away) {
					ModuleConfig.instance().setBoolean(Config.SHOW_VISITED_HEADER.toString(),
							selected);
				} else {
					ModuleConfig.instance().setBoolean(Config.SHOW_HOSTED_HEADER.toString(),
							selected);
				}
				emblemPanel.setHeader(selected);
				ImageDesignPanel.this.refreshFlagPanel();
			}
		});

		this.roundlyCheckBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				boolean selected = e.getStateChange() == ItemEvent.SELECTED;
				if (away) {
					ModuleConfig.instance().setBoolean(Config.VISITED_ROUNDLY.toString(), selected);
				} else {
					ModuleConfig.instance().setBoolean(Config.HOSTED_ROUNDLY.toString(), selected);
				}
				emblemPanel.getFlagDisplayModel().setRoundFlag(selected);
				ImageDesignPanel.this.refreshFlagPanel();
			}
		});

		this.greyColoredCheckBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				boolean selected = e.getStateChange() == ItemEvent.SELECTED;
				if (away) {
					ModuleConfig.instance().setBoolean(Config.VISITED_GREY.toString(), selected);
				} else {
					ModuleConfig.instance().setBoolean(Config.HOSTED_GREY.toString(), selected);
				}
				emblemPanel.getFlagDisplayModel().setGrey(selected);
				ImageDesignPanel.this.refreshFlagPanel();
			}
		});

		this.animGifCheckBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				boolean selected = e.getStateChange() == ItemEvent.SELECTED;
				delaySpinner.setEnabled(selected);
				delayLabel.setEnabled(selected);
			}
		});

		this.brightnessSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (!brightnessSlider.getValueIsAdjusting()) {
					int value = brightnessSlider.getValue();
					if (away) {
						ModuleConfig.instance().setInteger(Config.VISITED_BRIGHTNESS.toString(),
								value);
					} else {
						ModuleConfig.instance().setInteger(Config.HOSTED_BRIGHTNESS.toString(),
								value);
					}
					emblemPanel.getFlagDisplayModel().setBrightness(value);
					ImageDesignPanel.this.refreshFlagPanel();
				}
			}
		});

		this.sizeSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int rowSize = ((Integer) ImageDesignPanel.this.sizeSpinner.getValue()).intValue();
				if (away) {
					ModuleConfig.instance().setInteger(Config.VISITED_FLAG_WIDTH.toString(),
							rowSize);
				} else {
					ModuleConfig.instance()
							.setInteger(Config.HOSTED_FLAG_WIDTH.toString(), rowSize);
				}
				emblemPanel.getFlagDisplayModel().setFlagWidth(rowSize);
				ImageDesignPanel.this.refreshFlagPanel();
			}
		});

		this.headerTextField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent arg0) {
				ImageDesignPanel.this.emblemPanel.setHeaderText(((JTextField) arg0.getSource())
						.getText());
			}
		});

		this.saveImageButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					saveImage();
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}
		});
	}

	private void saveImage() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new ImageFileFilter(new String[] { "gif" }));
		fileChooser.setAcceptAllFileFilterUsed(false);
		if (isAnimGif()) {
			fileChooser.setSelectedFile(new File("animated.gif"));
		} else {
			fileChooser.setSelectedFile(new File("hosted.gif"));
		}
		if (fileChooser.showSaveDialog(SwingUtilities.getWindowAncestor(this)) != 0) {
			return;
		}
		OutputStream out = new FileOutputStream(fileChooser.getSelectedFile().getPath());

		if (isAnimGif()) {
			JDialog dialog = new JDialog();
			dialog.getContentPane().setBackground(Color.white);
			dialog.setUndecorated(true);
			dialog.getContentPane().setLayout(null);

			JComponent panel1 = getEmblemPanel().getImage();
			JComponent panel2 = getEmblemPanel().getImage();

			Dimension size1 = panel1.getSize();
			Dimension size2 = panel2.getSize();
			int maxW = size1.width > size2.width ? size1.width : size2.width;
			int maxH = size1.height > size2.height ? size1.height : size2.height;
			panel1.setBounds(0, 0, size1.width, size1.height);
			panel2.setBounds(maxW, 0, size2.width, size2.height);

			dialog.getContentPane().add(panel1);
			dialog.getContentPane().add(panel2);
			dialog.setBounds(WIDTH + 1, HEIGHT + 1, 2 * maxW, maxH);
			dialog.setVisible(true);

			BufferedImage bufferedImage = new BufferedImage(dialog.getWidth(), dialog.getHeight(),
					1);
			dialog.getContentPane().paintAll(bufferedImage.createGraphics());

			Gif89Encoder encoder = new Gif89Encoder();
			BufferedImage bufIma = PluginIfaUtils.quantizeBufferedImage(bufferedImage);
			encoder.addFrame(bufIma.getSubimage(0, 0, maxW, maxH));
			encoder.addFrame(bufIma.getSubimage(maxW, 0, maxW, maxH));
			encoder.setLoopCount(0);
			encoder.setUniformDelay((int) (100.0D * new Double(getDelaySpinner().getValue()
					.toString()).doubleValue()));
			encoder.encode(out);
			out.close();
			dialog.dispose();
			return;
		}

		JComponent panel = getEmblemPanel().getImage();
		BufferedImage bufferedImage = new BufferedImage(panel.getWidth(), panel.getHeight(), 1);
		panel.paintAll(bufferedImage.createGraphics());
		Gif89Encoder encoder = new Gif89Encoder();
		encoder.addFrame(PluginIfaUtils.quantizeBufferedImage(bufferedImage));
		encoder.encode(out);
		out.close();

	}
}
