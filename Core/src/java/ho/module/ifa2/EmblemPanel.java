package ho.module.ifa2;

import ho.core.model.HOVerwaltung;
import ho.module.ifa.ImageFileFilter;
import ho.module.ifa2.model.IfaModel;
import ho.module.ifa2.model.ModelChangeListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class EmblemPanel extends JPanel {

	private static final long serialVersionUID = 5771493941186321587L;
	private FlagPanel flagPanel;
	private JLabel logoLabel;
	private int flagWidth = 8;
	private String headerText = "";
	private boolean header = true;
	private boolean grey = true;
	private boolean roundly = false;
	private int brightness = 50;
	private String imagePath = "";
	private final IfaModel model;
	private final FlagDisplayModel flagDisplayModel;
	private final boolean away;

	public EmblemPanel(boolean away, IfaModel model, FlagDisplayModel flagDisplayModel) {
		this.away = away;
		this.model = model;
		this.flagDisplayModel = flagDisplayModel;
		this.flagPanel = new FlagPanel(away, model, flagDisplayModel);
		initialize();
		addListeners();
	}

	private void initialize() {
		setLayout(new GridBagLayout());
		setBackground(Color.white);
		this.logoLabel = new JLabel(HOVerwaltung.instance().getLanguageString(
				"ifa.loadEmblem.clickHere"));
		this.logoLabel.setPreferredSize(new Dimension(100, 100));
		this.logoLabel.setVerticalAlignment(0);
		this.logoLabel.setHorizontalAlignment(0);
		this.logoLabel.setBackground(Color.white);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(this.logoLabel, constraints);
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridy = 1;
		add(this.flagPanel, constraints);
	}

	public void setLogo(ImageIcon image) {
		this.logoLabel.setIcon(image);
		if (image != null) {
			this.logoLabel.setText("");
			this.logoLabel.setPreferredSize(new Dimension(image.getIconWidth(), image
					.getIconHeight()));
		} else {
			this.logoLabel.setText(HOVerwaltung.instance().getLanguageString(
					"ifa.loadEmblem.clickHere"));
			this.imagePath = "";
		}
	}

	public FlagDisplayModel getFlagDisplayModel() {
		return this.flagDisplayModel;
	}

	public JComponent getImage() {
		if (this.logoLabel.getIcon() == null) {
			return this.flagPanel;
		}
		return this;
	}

	public void setHeader(boolean header) {
		this.header = header;
		this.flagPanel.setHeaderVisible(header);
	}

	public void setHeaderText(String headerText) {
		this.headerText = headerText;
		this.flagPanel.setHeader(headerText);
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	private void loadEmblem() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new ImageFileFilter(new String[] { "jpg", "gif", "png" }));
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setMultiSelectionEnabled(false);
		Window parent = SwingUtilities.getWindowAncestor(this);
		ImageIcon image = null;
		if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			String path = fileChooser.getSelectedFile().getPath();
			setImagePath(path);
			if (path != null) {
				image = new ImageIcon(path);
			}
			setLogo(image);
		}
		validate();
		repaint();
	}

	private void rebuildFlags() {
		if (this.flagPanel != null) {
			remove(this.flagPanel);
		}
		flagPanel = new FlagPanel(this.away, this.model, this.flagDisplayModel);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridy = 1;
		add(this.flagPanel, constraints);
		validate();
	}

	private void addListeners() {
		this.flagDisplayModel.addModelChangeListener(new FlagModelChangeListener() {

			@Override
			public void flagSizeChanged() {
				rebuildFlags();
			}

			@Override
			public void brightnessChanged() {
				rebuildFlags();
			}
		});

		this.logoLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				loadEmblem();
			}
		});

		this.model.addModelChangeListener(new ModelChangeListener() {

			@Override
			public void modelChanged() {
				rebuildFlags();
			}
		});
	}
}
