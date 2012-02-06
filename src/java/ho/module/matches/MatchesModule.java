package ho.module.matches;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.KeyStroke;

import de.hattrickorganizer.model.HOVerwaltung;

import ho.core.module.DefaultModule;

public final class MatchesModule extends DefaultModule {

	public MatchesModule(){
		super(true);
	}
	
	@Override
	public int getModuleId() {
		return MATCHES;
	}

	@Override
	public String getDescription() {
		return HOVerwaltung.instance().getLanguageString("Spiele");
	}

	@Override
	public JPanel createTabPanel() {
		return new SpielePanel();
	}

	public KeyStroke getKeyStroke() {
		return KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0);
	}

}
