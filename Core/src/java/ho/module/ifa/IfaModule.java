package ho.module.ifa;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.KeyStroke;

import ho.core.model.HOVerwaltung;
import ho.core.module.DefaultModule;

public class IfaModule extends DefaultModule {

	@Override
	public KeyStroke getKeyStroke() {
		return KeyStroke.getKeyStroke(KeyEvent.VK_5, KeyEvent.CTRL_MASK);
	}

	@Override
	public int getModuleId() {
		return IFA;
	}

	@Override
	public String getDescription() {
		return HOVerwaltung.instance().getLanguageString("Tab_IFA");
	}

	@Override
	public JPanel createTabPanel() {
		return new PluginIfaPanel();
	}

}
