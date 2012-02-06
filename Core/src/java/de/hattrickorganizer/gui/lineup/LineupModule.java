package de.hattrickorganizer.gui.lineup;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.KeyStroke;

import de.hattrickorganizer.model.HOVerwaltung;

import ho.core.module.DefaultModule;

public final class LineupModule extends DefaultModule {

	public LineupModule(){
		super(true);
	}
	
	@Override
	public int getModuleId() {
		return LINEUP;
	}

	@Override
	public String getDescription() {
		return HOVerwaltung.instance().getLanguageString("Aufstellung");
	}

	@Override
	public JPanel createTabPanel() {
		return new LineupPanel();
	}

	public KeyStroke getKeyStroke() {
		return KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
	}

}
