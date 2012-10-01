package ho.module.ifa2;

import ho.core.db.DBManager;
import ho.core.file.xml.XMLManager;
import ho.core.file.xml.XMLWorldDetailsParser;
import ho.core.model.HOVerwaltung;
import ho.core.model.WorldDetailLeague;
import ho.core.model.WorldDetailsManager;
import ho.core.net.MyConnector;
import ho.core.util.GUIUtils;
import ho.core.util.IOUtils;
import ho.module.ifa2.gif.Gif89Encoder;
import ho.module.ifa2.model.IfaModel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

public class RightPanel extends JPanel {

	private static final long serialVersionUID = -5038012557489983903L;
	private JButton updateButton;
	private JButton saveImageButton;
	private JRadioButton homeRadioButton;
	private JRadioButton awayRadioButton;
	private ImageDesignPanel imageDesignPanel;
	private final IfaModel model;

	public RightPanel(IfaModel model) {
		this.model = model;
		initComponents();
		addListeners();
	}

	public ImageDesignPanel getImageDesignPanel() {
		return this.imageDesignPanel;
	}

	private void initComponents() {
		setLayout(new GridBagLayout());

		JPanel buttonPanel = new JPanel();
		GridBagConstraints gbc = new GridBagConstraints();
		this.updateButton = new JButton(getLangString("ifa.button.update"));
		gbc.anchor = GridBagConstraints.EAST;
		buttonPanel.add(this.updateButton, gbc);
		this.saveImageButton = new JButton(getLangString("ifa.button.save"));
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		buttonPanel.add(this.saveImageButton, gbc);
		GUIUtils.equalizeComponentSizes(this.updateButton, this.saveImageButton);

		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.gridwidth = 2;
		add(buttonPanel, gbc);

		this.awayRadioButton = new JRadioButton(getLangString("ifa.visited"), true);
		gbc.insets = new Insets(5, 6, 5, 6);
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		add(this.awayRadioButton, gbc);

		this.homeRadioButton = new JRadioButton(getLangString("ifa.hosted"), false);
		gbc.gridx = 1;
		add(this.homeRadioButton, gbc);

		ButtonGroup group = new ButtonGroup();
		group.add(this.homeRadioButton);
		group.add(this.awayRadioButton);

		this.imageDesignPanel = new ImageDesignPanel(this.model);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(5, 6, 10, 6);
		add(this.imageDesignPanel, gbc);
	}

	private void addListeners() {
		this.awayRadioButton.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent evt) {
				if (evt.getStateChange() == ItemEvent.SELECTED) {
					imageDesignPanel.setAway(true);
				}
			}
		});

		this.homeRadioButton.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent evt) {
				if (evt.getStateChange() == ItemEvent.SELECTED) {
					imageDesignPanel.setAway(false);
				}
			}
		});

		this.updateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String worldDetails;
				try {
					worldDetails = MyConnector.instance().getWorldDetails(0);
					List<WorldDetailLeague> leagues = XMLWorldDetailsParser.parseDetails(XMLManager
							.parseString(worldDetails));
					DBManager.instance().saveWorldDetailLeagues(leagues);
					WorldDetailsManager.instance().refresh();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if (HOVerwaltung.instance().getModel().getBasics().getTeamId() == 0) {
					PluginIfaUtils.updateTeamTable();
				}
				PluginIfaUtils.updateMatchesTable();
				RightPanel.this.model.reload();
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

	/**
	 * Convenience method
	 * 
	 * @param key
	 * @return
	 */
	private static String getLangString(String key) {
		return HOVerwaltung.instance().getLanguageString(key);
	}

	private void saveImage() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new ImageFileFilter(new String[] { "gif" }));
		fileChooser.setAcceptAllFileFilterUsed(false);
		if (this.imageDesignPanel.isAnimGif()) {
			fileChooser.setSelectedFile(new File("animated.gif"));
		} else {
			fileChooser.setSelectedFile(new File("hosted.gif"));
		}
		if (fileChooser.showSaveDialog(SwingUtilities.getWindowAncestor(this)) != 0) {
			return;
		}
		OutputStream out = new FileOutputStream(fileChooser.getSelectedFile().getPath());

		if (this.imageDesignPanel.isAnimGif()) {
			JDialog dialog = new JDialog();
			dialog.getContentPane().setBackground(Color.white);
			dialog.setUndecorated(true);
			dialog.getContentPane().setLayout(null);

			JComponent panel1 = this.imageDesignPanel.getEmblemPanel().getImage();
			JComponent panel2 = this.imageDesignPanel.getEmblemPanel().getImage();

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
			encoder.setUniformDelay((int) (100.0D * new Double(this.imageDesignPanel
					.getDelaySpinner().getValue().toString()).doubleValue()));
			encoder.encode(out);
			dialog.dispose();
		} else {
			JComponent panel = this.imageDesignPanel.getEmblemPanel().getImage();
			BufferedImage bufferedImage = new BufferedImage(panel.getWidth(), panel.getHeight(), 1);
			panel.paintAll(bufferedImage.createGraphics());
			Gif89Encoder encoder = new Gif89Encoder();
			encoder.addFrame(PluginIfaUtils.quantizeBufferedImage(bufferedImage));
			encoder.encode(out);
		}

		IOUtils.closeQuietly(out);
	}
}
