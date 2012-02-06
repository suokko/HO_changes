package ho.module.playeranalysis;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.KeyStroke;

import de.hattrickorganizer.model.HOVerwaltung;

import ho.core.module.DefaultModule;

public final class PlayerAnalysisModule extends DefaultModule {

	public PlayerAnalysisModule(){
		super(true);
	}
	
	@Override
	public int getModuleId() {
		return PLAYERANALYSIS;
	}

	@Override
	public String getDescription() {
		return HOVerwaltung.instance().getLanguageString("SpielerAnalyse");
	}

	@Override
	public JPanel createTabPanel() {
		return new PlayerAnalysisPanel();
	}

	public KeyStroke getKeyStroke() {
		return KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0);
	}

}
