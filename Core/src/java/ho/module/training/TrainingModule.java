package ho.module.training;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.KeyStroke;


import ho.core.model.HOVerwaltung;
import ho.core.module.DefaultModule;

public final class TrainingModule extends DefaultModule {

	public TrainingModule(){
		super(true);
	}
	
	@Override
	public int getModuleId() {
		return TRAINING;
	}

	@Override
	public String getDescription() {
		return HOVerwaltung.instance().getLanguageString("Training");
	}

	@Override
	public JPanel createTabPanel() {
		return new TrainingPanel();
	}

	@Override
	public KeyStroke getKeyStroke() {
		return KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0);
	}

}
