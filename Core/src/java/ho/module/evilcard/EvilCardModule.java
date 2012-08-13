package ho.module.evilcard;

import ho.core.model.HOVerwaltung;
import ho.core.module.DefaultModule;
import ho.module.evilcard.gui.EvilCardPanel;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class EvilCardModule extends DefaultModule {

	public KeyStroke getKeyStroke() {
		return KeyStroke.getKeyStroke(KeyEvent.VK_4, KeyEvent.CTRL_MASK);
	}

	@Override
	public int getModuleId() {
		return EVIL_CARD;
	}

	@Override
	public String getDescription() {
		return HOVerwaltung.instance().getLanguageString("Tab_Evilcard");
	}

	@Override
	public JPanel createTabPanel() {
		return new EvilCardPanel();
	}

}
