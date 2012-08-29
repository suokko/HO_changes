package ho.module.specialEvents;

import ho.core.model.HOVerwaltung;
import ho.core.module.DefaultModule;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.KeyStroke;


public class SpecialEventsModule extends DefaultModule {

	public KeyStroke getKeyStroke() {
		return KeyStroke.getKeyStroke(KeyEvent.VK_2, KeyEvent.CTRL_MASK);
	}

	@Override
	public int getModuleId() {
		return SPECIALEVENTS;
	}

	@Override
	public String getDescription() {
		return  HOVerwaltung.instance().getLanguageString("Tab_SpecialEvents");
	}

	@Override
	public JPanel createTabPanel() {
		return new SpecialEventsPanel();
	}

}
