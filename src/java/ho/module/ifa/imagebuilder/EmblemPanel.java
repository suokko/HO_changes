package ho.module.ifa.imagebuilder;

import ho.core.file.ExampleFileFilter;
import ho.core.model.HOVerwaltung;
import ho.core.util.HOLogger;
import ho.module.ifa.FlagLabel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EmblemPanel extends JPanel {

	private static final long serialVersionUID = -4241847282455700992L;
	private GridBagConstraints constraints = new GridBagConstraints();
	private FlagPanel flagPanel;
	private JLabel logo;
	private int flagWidth = 8;
	private String headerText = "";
	private boolean header = true;
	private boolean grey = true;
	private boolean roundly = false;
	private int brightness = 50;
	private String imagePath = "";

	public EmblemPanel(FlagLabel[] flagLabels, int countriesPlayedIn) {
		setOpaque(false);
		this.flagPanel = new FlagPanel(flagLabels, countriesPlayedIn);
		initialize();
	}

	private void initialize() {
		setLayout(new GridBagLayout());
		setBackground(Color.white);
		if (this.logo == null) {
			this.logo = new JLabel(HOVerwaltung.instance().getLanguageString("loadEmblem"));
			this.logo.setPreferredSize(new Dimension(100, 100));
			this.logo.setVerticalAlignment(0);
			this.logo.setHorizontalAlignment(0);
			this.logo.setBackground(Color.white);
			this.logo.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					try {
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setFileFilter(new ExampleFileFilter(new String[] { "jpg", "gif" }));
						fileChooser.setAcceptAllFileFilterUsed(false);
						fileChooser.setMultiSelectionEnabled(false);
						if (fileChooser.showOpenDialog(EmblemPanel.this) == JFileChooser.OPEN_DIALOG) {
							String path = fileChooser.getSelectedFile().getPath();
							setImagePath(path);
							ImageIcon image = createImageIcon(path);
							setLogo(image);
							getParent().validate();
							getParent().repaint();
						} else {
							setLogo(null);
							getParent().validate();
							getParent().repaint();
						}
					} catch (Exception ex) {
						HOLogger.instance().error(EmblemPanel.class, ex);
					}
				}
			});
		}

		this.constraints.fill = 1;
		this.constraints.anchor = 10;
		this.constraints.insets = new Insets(0, 0, 0, 0);
		this.constraints.weightx = 100.0D;
		this.constraints.weighty = 100.0D;
		add(this.logo, this.constraints, 0, 0, 1, 1);
		this.constraints.fill = 0;
		this.constraints.anchor = 10;
		this.constraints.weightx = 0.0D;
		this.constraints.weighty = 0.0D;
		add(this.flagPanel, this.constraints, 0, 1, 1, 1);
	}

	private void add(Component c, GridBagConstraints constraints, int x, int y, int w, int h) {
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = w;
		constraints.gridheight = h;
		add(c, constraints);
	}

	public void setLogo(ImageIcon image) {
		this.logo.setIcon(image);
		if (image != null) {
			this.logo.setText("");
			this.logo.setPreferredSize(new Dimension(image.getIconWidth(), image.getIconHeight()));
		} else {
			this.logo.setText(HOVerwaltung.instance().getLanguageString("loadEmblem"));
			this.imagePath = "";
		}
	}

	public FlagPanel getFlagPanel() {
		return this.flagPanel;
	}

	public void setFlagPanel(FlagPanel flagPanel) {
		remove(this.flagPanel);
		this.flagPanel = flagPanel;
		this.flagPanel.setHeaderVisible(this.header);
		this.flagPanel.setHeader(this.headerText);
		add(this.flagPanel, this.constraints, 0, 1, 1, 1);
	}

	public JLabel getLogo() {
		return this.logo;
	}

	public JComponent getImage() {
		if (this.logo.getIcon() == null)
			return this.flagPanel;
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

	public boolean isHeader() {
		return this.header;
	}

	public String getHeaderText() {
		return this.headerText;
	}

	public int getFlagWidth() {
		return this.flagWidth;
	}

	public void setFlagWidth(int flagWidth) {
		this.flagWidth = flagWidth;
	}

	public String getImagePath() {
		if (this.imagePath.equals(""))
			return "\"\"";
		return this.imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getBrightness() {
		return this.brightness;
	}

	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}

	public boolean isGrey() {
		return this.grey;
	}

	public void setGrey(boolean grey) {
		this.grey = grey;
	}

	public boolean isRoundly() {
		return this.roundly;
	}

	public void setRoundly(boolean roundly) {
		this.roundly = roundly;
	}

	private ImageIcon createImageIcon(String path) {
		if (path != null) {
			return new ImageIcon(path);
		}
		HOLogger.instance().debug(this.getClass(), "Couldn't find file: " + path);
		return null;
	}

	public void setHeaderImage(ImageDesignPanel imagePanel) {
		try {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new ExampleFileFilter(new String[] { "jpg", "gif" }));
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.setMultiSelectionEnabled(false);
			if (fileChooser.showOpenDialog(this) == JFileChooser.OPEN_DIALOG) {
				String path = fileChooser.getSelectedFile().getPath();
				setImagePath(path);
				ImageIcon image = createImageIcon(path);
				setLogo(image);
				this.getParent().validate();
				this.getParent().repaint();
			} else {
				setLogo(null);
				this.getParent().validate();
				this.getParent().repaint();
				ConfigManager.saveConfig(imagePanel);
			}
		} catch (Exception ex) {
			HOLogger.instance().error(getClass(), ex);
		}
	}
}
