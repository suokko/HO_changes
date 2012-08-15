package ho.module.ifa2;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

public class TablesPanel extends JPanel {

	private static final long serialVersionUID = 3806181337290704445L;
	private ImageDesignPanel imageDesignPanel;
	private StatisticScrollPanel statisticScrollPanelAway;
	private StatisticScrollPanel statisticScrollPanelHome;

	public TablesPanel() {
		initialize();
	}

	private void initialize() {
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;

		this.imageDesignPanel = new ImageDesignPanel(this);
		this.statisticScrollPanelAway = new StatisticScrollPanel(true);
		this.statisticScrollPanelHome = new StatisticScrollPanel(false);
		add(this.imageDesignPanel, constraints, 1, 0, 1, 2);
		constraints.weightx = 3.0;
		add(this.statisticScrollPanelAway, constraints, 0, 0, 1, 1);
		add(this.statisticScrollPanelHome, constraints, 0, 1, 1, 1);
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
