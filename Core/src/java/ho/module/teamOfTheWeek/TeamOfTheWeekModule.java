package ho.module.teamOfTheWeek;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.KeyStroke;

import ho.core.module.DefaultModule;

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
		// TODO Auto-generated method stub
		return "TOTW";
	}

	@Override
	public JPanel createTabPanel() {
		return new PotwUI();
	}

}
