package ho.module.playeranalysis;

import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;
import ho.core.module.config.ModuleConfig;
import ho.module.playeranalysis.experience.ExperienceViewer;
import ho.module.playeranalysis.skillCompare.PlayerComparePanel;

import java.awt.BorderLayout;

import javax.swing.JTabbedPane;


public class PlayerAnalysisPanel extends ImagePanel {

	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	private SpielerAnalyseMainPanel spielerAnalyseMainPanel;
	private PlayerComparePanel playerComparePanel;
	private ExperienceViewer experienceViewer;

	
	public PlayerAnalysisPanel(){
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout());
		add(getTabbedPane(),BorderLayout.CENTER);
		
		
	}
	
	private JTabbedPane getTabbedPane(){
		if(tabbedPane == null){
			tabbedPane = new JTabbedPane();
			tabbedPane.add(getSpielerAnalyseMainPanel(),HOVerwaltung.instance().getLanguageString("Spiele"));
			if(ModuleConfig.instance().getBoolean(PlayerAnalysisModule.SHOW_PLAYERCOMPARE))
				tabbedPane.add(getPlayerComparePanel(),HOVerwaltung.instance().getLanguageString("PlayerCompare"));
			if(ModuleConfig.instance().getBoolean(PlayerAnalysisModule.SHOW_EXPERIENCE))
				tabbedPane.add(getExperienceViewer(),HOVerwaltung.instance().getLanguageString("ExperienceViewer"));
		}
		return tabbedPane;
	}
	
	
	
    SpielerAnalyseMainPanel getSpielerAnalyseMainPanel() {
    	if(spielerAnalyseMainPanel == null)
    		spielerAnalyseMainPanel = new SpielerAnalyseMainPanel();
		return spielerAnalyseMainPanel;
	}

	PlayerComparePanel getPlayerComparePanel() {
		if(playerComparePanel == null)
			playerComparePanel = new PlayerComparePanel();
		return playerComparePanel;
	}

	ExperienceViewer getExperienceViewer(){
		if(experienceViewer == null)
			experienceViewer = new ExperienceViewer();
		return experienceViewer;
	}
	/**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getDividerLocation() {
        return getSpielerAnalyseMainPanel().getDividerLocation();
    }

    public void saveColumnOrder(){
    	getSpielerAnalyseMainPanel().saveColumnOrder();
    }
    
    /**
     * TODO Missing Method Documentation
     *
     * @param spielerid TODO Missing Method Parameter Documentation
     */
    public final void setSpieler4Bottom(int spielerid) {
    	getSpielerAnalyseMainPanel().setSpieler4Bottom(spielerid);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param spielerid TODO Missing Method Parameter Documentation
     */
    public final void setSpieler4Top(int spielerid) {
    	getSpielerAnalyseMainPanel().setSpieler4Top(spielerid);
    }
}
