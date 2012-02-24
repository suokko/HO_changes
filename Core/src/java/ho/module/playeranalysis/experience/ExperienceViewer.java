package ho.module.playeranalysis.experience;

import ho.core.gui.RefreshManager;
import ho.core.gui.comp.panel.ImagePanel;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import plugins.IRefreshable;

public class ExperienceViewer extends ImagePanel implements IRefreshable {

	private Spielertabelle spielertabelle;
	
	public ExperienceViewer() {
		initialize();
	}


	private void initialize() {
		setLayout(new BorderLayout());
		spielertabelle = new Spielertabelle();
		JPanel tabelle = new JPanel();
		tabelle.setLayout(new BorderLayout());
		JScrollPane scroll = new JScrollPane(spielertabelle);
		scroll.setSize(1200, 600);
		tabelle.add(scroll, "Center");
		tabelle.setBorder(BorderFactory.createEtchedBorder());
		add(tabelle);
		RefreshManager.instance().registerRefreshable(this);
	}

	public void refresh() {
		spielertabelle.aktualisieren();
	}
}
