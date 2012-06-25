package ho.module.lineup;

import ho.core.gui.Updateable;
import ho.core.model.HOVerwaltung;
import ho.module.lineup.substitution.SubstitutionOverview;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Top-Level Container for the Lineups (contains a tab for the lineup, a tab for
 * the match orders...)
 * 
 * @author kruescho
 * 
 */
public class LineupMasterView extends JPanel {

	private static final long serialVersionUID = 6557097920433876610L;
	private LineupPanel lineupPanel;
	private SubstitutionOverview substitutionOverview;

	public LineupMasterView() {
		initComponents();
		addListeners();
	}

	public LineupPanel getLineupPanel() {
		return this.lineupPanel;
	}

	private void initComponents() {
		JTabbedPane tabbedPane = new JTabbedPane();
		HOVerwaltung hov = HOVerwaltung.instance();

		this.lineupPanel = new LineupPanel();
		tabbedPane.addTab(hov.getLanguageString("Aufstellung"), this.lineupPanel);

		this.substitutionOverview = new SubstitutionOverview(hov.getModel().getAufstellung());
		tabbedPane.addTab(hov.getLanguageString("subs.Title"), this.substitutionOverview);

		tabbedPane.addTab("Upload / Download", new UploadDownloadPanel());

		setLayout(new BorderLayout());
		add(tabbedPane, BorderLayout.CENTER);
	}

	private void addListeners() {
		this.lineupPanel.addUpdateable(new Updateable() {

			@Override
			public void update() {
				System.out.println("####- update");
				substitutionOverview.setLineup(HOVerwaltung.instance().getModel().getAufstellung());
			}
		});
	}

}
