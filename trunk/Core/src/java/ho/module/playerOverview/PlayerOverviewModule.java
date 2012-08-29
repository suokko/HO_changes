package ho.module.playerOverview;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.KeyStroke;


import ho.core.model.HOVerwaltung;
import ho.core.module.DefaultModule;

public final class PlayerOverviewModule extends DefaultModule {

	public PlayerOverviewModule(){
		super(true);
	}
	
	@Override
	public int getModuleId() {
		return PLAYEROVERVIEW;
	}

	@Override
	public String getDescription() {
		return HOVerwaltung.instance().getLanguageString("Spieleruebersicht");
	}

	@Override
	public JPanel createTabPanel() {
		return new SpielerUebersichtsPanel();
	}

	public KeyStroke getKeyStroke() {
		return KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0);
	}

}
