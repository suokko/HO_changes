package ho.module.ifa;

import ho.core.db.DBManager;
import ho.core.file.xml.XMLManager;
import ho.core.file.xml.XMLWorldDetailsParser;
import ho.core.model.HOVerwaltung;
import ho.core.model.WorldDetailLeague;
import ho.core.model.WorldDetailsManager;
import ho.core.net.MyConnector;
import ho.module.ifa.imagebuilder.ImageBuilderDialog;
import ho.module.ifa.imagebuilder.ImageDesignPanel;
import ho.module.ifa.table.FriendlyStatisticsPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class PluginIfaPanel extends JPanel {
	private static final long serialVersionUID = 6250843484613905192L;
	private ImageDesignPanel imageDesignPanel;
	private FriendlyStatisticsPanel statisticScrollPanelAway;
	private FriendlyStatisticsPanel statisticScrollPanelHome;
	private JPanel toolbarPanel;
	private JButton refreshButton = new JButton(HOVerwaltung.instance().getLanguageString("Refresh"));
	private JButton imageBuilderButton = new JButton(HOVerwaltung.instance().getLanguageString("Imagebuilder"));
	private JTabbedPane tabbedPane;

	public PluginIfaPanel() {
		initialize();
	}

	private void initialize() {
		imageDesignPanel = new ImageDesignPanel(this);
		this.statisticScrollPanelAway = new FriendlyStatisticsPanel(false);
		this.statisticScrollPanelHome = new FriendlyStatisticsPanel(true);
		setLayout(new BorderLayout());
		add(getToolbar(),BorderLayout.NORTH);
		add(getTabbedPane(),BorderLayout.CENTER);

	}


	private JTabbedPane getTabbedPane(){
		if(tabbedPane == null){
			tabbedPane = new JTabbedPane();

			tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("AutoFilterPanel.Home_Games"), statisticScrollPanelHome);
			tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("AutoFilterPanel.Away_Games"), statisticScrollPanelAway);
			tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("Imagebuilder"), imageDesignPanel);
		}
		return tabbedPane;
	}


	public FriendlyStatisticsPanel getStatisticScrollPanelAway() {
		return this.statisticScrollPanelAway;
	}

	public FriendlyStatisticsPanel getStatisticScrollPanelHome() {
		return this.statisticScrollPanelHome;
	}

	public JPanel getToolbar(){
		if(toolbarPanel == null){
			toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			refreshButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String worldDetails;
					try {
						worldDetails = MyConnector.instance().getWorldDetails(0);
						List<WorldDetailLeague> leagues = XMLWorldDetailsParser.parseDetails(XMLManager.parseString(worldDetails));
						DBManager.instance().saveWorldDetailLeagues(leagues);
						WorldDetailsManager.instance().refresh();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					PluginIfaUtils.updateMatchesTable();
					imageDesignPanel.refreshFlagPanel();
					getStatisticScrollPanelHome().refresh();
					getStatisticScrollPanelAway().refresh();

				}
			});
			toolbarPanel.add(refreshButton);

			imageBuilderButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					ImageBuilderDialog dialog = new ImageBuilderDialog();
					dialog.setVisible(true);

				}
			});
			//toolbarPanel.add(imageBuilderButton);
		}
		return toolbarPanel;
		}
}
