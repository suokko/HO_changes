package ho.module.ifa;

import ho.core.db.DBManager;
import ho.core.file.xml.XMLManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.WorldDetailLeague;
import ho.core.model.WorldDetailsManager;
import ho.core.net.MyConnector;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class PluginIfaPanel extends JPanel {
	private static final long serialVersionUID = 6250843484613905192L;
	private GridBagLayout layout = new GridBagLayout();
	private GridBagConstraints constraints = new GridBagConstraints();
	private ImageDesignPanel imageDesignPanel;
	private StatisticScrollPanel statisticScrollPanelAway;
	private StatisticScrollPanel statisticScrollPanelHome;
	private JPanel toolbarPanel;
	private JButton refreshButton = new JButton(HOVerwaltung.instance().getLanguageString("Refresh"));
	private JTabbedPane tabbedPane;
	
	public PluginIfaPanel() {
		initialize();
	}

	private void initialize() {
//		setLayout(this.layout);
//		this.constraints.fill = 1;
//		this.constraints.anchor = 10;
//		this.constraints.insets = new Insets(10, 10, 10, 10);
//		this.constraints.weightx = 1.0D;
//		this.constraints.weighty = 1.0D;
//
		this.imageDesignPanel = new ImageDesignPanel(this);
		this.statisticScrollPanelAway = new StatisticScrollPanel(false);
		this.statisticScrollPanelHome = new StatisticScrollPanel(true);
//		add(this.imageDesignPanel, this.constraints, 1, 0, 1, 2);
//		this.constraints.weightx = 3.0D;
//		add(this.statisticScrollPanelAway, this.constraints, 0, 0, 1, 1);
//		add(this.statisticScrollPanelHome, this.constraints, 0, 1, 1, 1);
		setLayout(new BorderLayout());
		add(getToolbar(),BorderLayout.NORTH);
		add(getTabbedPane(),BorderLayout.CENTER);
		
	}

	
	private JTabbedPane getTabbedPane(){
		if(tabbedPane == null){
			tabbedPane = new JTabbedPane();
			tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("AutoFilterPanel.Home_Games"), statisticScrollPanelHome);
			tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("AutoFilterPanel.Away_Games"), statisticScrollPanelAway);
			tabbedPane.addTab(HOVerwaltung.instance().getLanguageString("ImageBuilder"), imageDesignPanel);
		}
		return tabbedPane;
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
	
	public JPanel getToolbar(){
		if(toolbarPanel == null){
			toolbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			refreshButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					String worldDetails;
					try {
						worldDetails = MyConnector.instance().getWorldDetails(0);
						WorldDetailLeague[] leagues =XMLManager.instance().parseWorldDetails(worldDetails);
						DBManager.instance().saveWorldDetailLeagues(leagues);
						WorldDetailsManager.instance().refresh();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					PluginIfaUtils.updateMatchesTable();
					getImageDesignPanel().refreshFlagPanel();
					getStatisticScrollPanelHome().refresh();
					getStatisticScrollPanelAway().refresh();
					
				}
			});
			toolbarPanel.add(refreshButton);
		}
		return toolbarPanel;
		}
}
