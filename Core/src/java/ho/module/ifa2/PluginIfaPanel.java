package ho.module.ifa2;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

public class PluginIfaPanel extends JPanel {

	private static final long serialVersionUID = 3806181337290704445L;
	private RightPanel rightPanel;
	private StatisticScrollPanel statisticScrollPanelAway;
	private StatisticScrollPanel statisticScrollPanelHome;

	public PluginIfaPanel() {
		initialize();
	}

	private void initialize() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
		
		this.statisticScrollPanelAway = new StatisticScrollPanel(true);
		add(this.statisticScrollPanelAway, gbc);
		
		this.statisticScrollPanelHome = new StatisticScrollPanel(false);
		gbc.gridy = 1;
		add(this.statisticScrollPanelHome, gbc);

		this.rightPanel = new RightPanel();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.weightx = 0;
		add(this.rightPanel, gbc);
	}
}
