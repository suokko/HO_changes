package ho.module.lineup;

import ho.core.gui.Updateable;
import ho.core.model.HOVerwaltung;
import ho.module.lineup.penalties.PenaltyTakersView;
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
	private PenaltyTakersView penaltyTakersView;

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

		this.penaltyTakersView = new PenaltyTakersView();
		this.penaltyTakersView.setPlayers(hov.getModel().getAllSpieler());
		this.penaltyTakersView.setLineup(hov.getModel().getAufstellung());
		tabbedPane.addTab(hov.getLanguageString("lineup.penaltytakers.tab.title"),
				this.penaltyTakersView);
		tabbedPane.addTab(hov.getLanguageString("lineup.upload.tab.title"),
				new UploadDownloadPanel());

		setLayout(new BorderLayout());
		add(tabbedPane, BorderLayout.CENTER);
	}

	private void addListeners() {
		this.lineupPanel.addUpdateable(new Updateable() {

			@Override
			public void update() {
				System.out.println("####- update");
				substitutionOverview.setLineup(HOVerwaltung.instance().getModel().getAufstellung());
				penaltyTakersView.setLineup(HOVerwaltung.instance().getModel().getAufstellung());
			}
		});
	}

}
