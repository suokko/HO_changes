package ho.module.teamOfTheWeek;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.KeyStroke;


import ho.core.model.HOVerwaltung;
import ho.core.module.DefaultModule;
import ho.module.teamOfTheWeek.gui.TeamOfTheWeekPanel;

public class TeamOfTheWeekModule extends DefaultModule {

	public KeyStroke getKeyStroke() {
		return KeyStroke.getKeyStroke(KeyEvent.VK_3, KeyEvent.CTRL_MASK);
	}

	@Override
	public int getModuleId() {
		return TEAM_OF_THE_WEEK;
	}

	@Override
	public String getDescription() {
		return HOVerwaltung.instance().getLanguageString("Tab_TeamOfTheWeek");
	}

	@Override
	public JPanel createTabPanel() {
		return new TeamOfTheWeekPanel();
	}

}
