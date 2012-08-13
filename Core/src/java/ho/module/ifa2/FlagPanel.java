package ho.module.ifa2;

import ho.core.model.WorldDetailsManager;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

public class FlagPanel extends JPanel {
	private GridBagLayout layout = new GridBagLayout();
	private GridBagConstraints constraints = new GridBagConstraints();
	private int countriesPlayedIn;
	private JLabel[] flagLabels;
	private JLabel header;
	private JLabel footer;
	private JProgressBar percentState;

	public FlagPanel(FlagLabel[] flagLabels, int countriesPlayedIn) {
		this.flagLabels = flagLabels;
		this.countriesPlayedIn = countriesPlayedIn;
		initialize();
	}

	private void initialize() {
		setLayout(this.layout);
		setBackground(Color.white);

		this.constraints.fill = 2;
		this.constraints.anchor = 10;
		this.constraints.insets = new Insets(1, 1, 1, 1);
		this.constraints.weightx = 100.0D;
		this.constraints.weighty = 0.0D;

		this.header = new JLabel("");
		this.header.setForeground(new Color(2522928));
		this.header.setHorizontalTextPosition(0);
		add(this.header, this.constraints, 0, 0, ImageDesignPanel.FLAG_WIDTH, 1);
		this.constraints.insets = new Insets(1, 1, 5, 1);
		Color selectionForeground = (Color) UIManager
				.get("ProgressBar.selectionForeground");
		Color selectionBackground = (Color) UIManager
				.get("ProgressBar.selectionBackground");
		UIManager.put("ProgressBar.selectionForeground", Color.black);
		UIManager.put("ProgressBar.selectionBackground", Color.black);
		this.percentState = new JProgressBar();
		this.percentState.setMaximum(WorldDetailsManager.instance().size());
		this.percentState.setValue(this.countriesPlayedIn);
		this.percentState.setPreferredSize(new Dimension(100, 10));
		this.percentState.setFont(new Font("Verdana", 1, 10));
		this.percentState.setForeground(new Color(15979011));
		this.percentState.setBackground(Color.lightGray);
		this.percentState.setString(this.countriesPlayedIn + "/"
				+WorldDetailsManager.instance().size() + " ("
				+ (int) (100.0D * this.percentState.getPercentComplete())
				+ "%)");
		this.percentState.setStringPainted(true);
		this.percentState
				.setBorder(BorderFactory.createLineBorder(Color.black));

		add(this.percentState, this.constraints, 0, 1,
				ImageDesignPanel.FLAG_WIDTH, 1);
		UIManager.put("ProgressBar.selectionForeground", selectionForeground);
		UIManager.put("ProgressBar.selectionBackground", selectionBackground);
		this.constraints.fill = 0;
		this.constraints.anchor = 10;
		this.constraints.insets = new Insets(1, 1, 1, 1);
		this.constraints.weightx = 0.0D;
		this.constraints.weighty = 0.0D;
		if (this.flagLabels != null)
			for (int i = 0; i < this.flagLabels.length; i++) {
				add(this.flagLabels[i], this.constraints, i
						% ImageDesignPanel.FLAG_WIDTH, 2 + i
						/ ImageDesignPanel.FLAG_WIDTH, 1, 1);
			}
		this.constraints.fill = 2;
		this.constraints.anchor = 13;
		this.constraints.insets = new Insets(1, 1, 1, 1);
		this.constraints.weightx = 100.0D;
		this.constraints.weighty = 0.0D;

		this.footer = new JLabel(new ImageIcon(
				FlagPanel.class.getResource("image/copyright.gif")), 4);
		add(this.footer, this.constraints, 0, WorldDetailsManager.instance().size()
				/ ImageDesignPanel.FLAG_WIDTH + 3, ImageDesignPanel.FLAG_WIDTH,
				1);
	}

	private void add(Component c, GridBagConstraints constraints, int x, int y,
			int w, int h) {
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = w;
		constraints.gridheight = h;
		add(c, constraints);
	}

	protected void setHeader(String header) {
		this.header.setText(header);
	}

	protected void setHeaderVisible(boolean enable) {
		this.header.setVisible(enable);
		this.percentState.setVisible(enable);
	}

	protected void setFooterVisible(boolean enable) {
		this.footer.setVisible(enable);
	}
}
