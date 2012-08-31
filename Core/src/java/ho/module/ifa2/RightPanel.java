package ho.module.ifa2;

import ho.core.db.DBManager;
import ho.core.file.xml.XMLManager;
import ho.core.file.xml.XMLWorldDetailsParser;
import ho.core.model.HOVerwaltung;
import ho.core.model.WorldDetailLeague;
import ho.core.model.WorldDetailsManager;
import ho.core.net.MyConnector;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class RightPanel extends JPanel {

	private static final long serialVersionUID = -5038012557489983903L;
	private JButton updateButton;
	private JRadioButton homeRadioButton;
	private JRadioButton awayRadioButton;
	private ImageDesignPanel imageDesignPanel;

	public RightPanel() {
		initComponents();
		addListeners();
	}

	public ImageDesignPanel getImageDesignPanel() {
		return this.imageDesignPanel;
	}

	private void initComponents() {
		setLayout(new GridBagLayout());

		this.updateButton = new JButton("Update");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.gridwidth = 2;
//		add(this.updateButton, gbc);

		this.awayRadioButton = new JRadioButton("Visited Countries", true);
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		add(this.awayRadioButton, gbc);

		this.homeRadioButton = new JRadioButton("Hosted Countries", false);
		gbc.gridx = 1;
		add(this.homeRadioButton, gbc);

		ButtonGroup group = new ButtonGroup();
		group.add(this.homeRadioButton);
		group.add(this.awayRadioButton);

		this.imageDesignPanel = new ImageDesignPanel();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(10, 6, 10, 6);
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
				try {
					PluginIfaUtils.updateMatchesTable();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				this.pluginIfaPanel.getImageDesignPanel().refreshFlagPanel();
//				this.pluginIfaPanel.getStatisticScrollPanelHome().refresh();
//				this.pluginIfaPanel.getStatisticScrollPanelAway().refresh();
			}
		});
	}
}
