package ho.module.playeranalysis.experience;

import ho.core.gui.CursorToolkit;
import ho.core.gui.IRefreshable;
import ho.core.gui.RefreshManager;
import ho.core.gui.comp.panel.ImagePanel;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ExperienceViewer extends ImagePanel implements IRefreshable {

	private static final long serialVersionUID = 3294326447950073349L;
	private Spielertabelle spielertabelle;
	private boolean initialized = false;
	private boolean needsRefresh = false;

	public ExperienceViewer() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				if (isShowing()) {
					if (!initialized) {
						CursorToolkit.startWaitCursor(ExperienceViewer.this);
						try {
							initialize();
						} finally {
							CursorToolkit.stopWaitCursor(ExperienceViewer.this);
						}
					}
					if (needsRefresh) {
						update();
					}
				}
			}
		});
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
		initialized = true;
	}

	@Override
	public void refresh() {
		if (isShowing()) {
			update();
		} else {
			this.needsRefresh = true;
		}
	}

	private void update() {
		CursorToolkit.startWaitCursor(this);
		try {
			spielertabelle.aktualisieren();
		} finally {
			CursorToolkit.stopWaitCursor(this);
		}
		this.needsRefresh = false;
	}
}
