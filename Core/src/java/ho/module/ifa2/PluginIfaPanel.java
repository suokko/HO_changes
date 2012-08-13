package ho.module.ifa2;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

public class PluginIfaPanel extends JPanel {
	private GridBagLayout layout = new GridBagLayout();
	private GridBagConstraints constraints = new GridBagConstraints();
	private ImageDesignPanel imageDesignPanel;
	private StatisticScrollPanel statisticScrollPanelAway;
	private StatisticScrollPanel statisticScrollPanelHome;

	public PluginIfaPanel() {
		initialize();
	}

	private void initialize() {
		setLayout(this.layout);
		this.constraints.fill = 1;
		this.constraints.anchor = 10;
		this.constraints.insets = new Insets(10, 10, 10, 10);
		this.constraints.weightx = 1.0D;
		this.constraints.weighty = 1.0D;

		this.imageDesignPanel = new ImageDesignPanel(this);
		this.statisticScrollPanelAway = new StatisticScrollPanel(true);
		this.statisticScrollPanelHome = new StatisticScrollPanel(false);
		add(this.imageDesignPanel, this.constraints, 1, 0, 1, 2);
		this.constraints.weightx = 3.0D;
		add(this.statisticScrollPanelAway, this.constraints, 0, 0, 1, 1);
		add(this.statisticScrollPanelHome, this.constraints, 0, 1, 1, 1);
	}

	private void add(Component c, GridBagConstraints constraints, int x, int y,
			int w, int h) {
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = w;
		constraints.gridheight = h;
		add(c, constraints);
	}

	public ImageDesignPanel getImageDesignPanel() {
		return this.imageDesignPanel;
	}

	public StatisticScrollPanel getStatisticScrollPanelAway() {
		return this.statisticScrollPanelAway;
	}

	public StatisticScrollPanel getStatisticScrollPanelHome() {
		return this.statisticScrollPanelHome;
	}
}
