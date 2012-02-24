package ho.module.lineup2;

import gui.UserParameter;
import ho.core.db.DBManager;
import ho.core.model.HOVerwaltung;
import ho.module.lineup.Lineup;
import ho.module.lineup.substitution.SubstitutionOverview;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;


public class MatchLineupView extends JPanel {

	private static final long serialVersionUID = -1646037839028508537L;

	public MatchLineupView(Lineup lineup) {
		initComponents(lineup);
	}

	private void initComponents(Lineup lineup) {	
		setLayout(new BorderLayout());
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("lineup", new LineupView(lineup));
		tabbedPane.addTab("subs", new SubstitutionOverview(lineup));
		add(tabbedPane, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				DBManager.instance().loadUserParameter();
				HOVerwaltung.instance().setResource(UserParameter.instance().sprachDatei);
				HOVerwaltung.instance().loadLatestHoModel();

				Lineup lineup = null;
				try {
					lineup = Helper.getLineup(new File(
							"/home/chr/tmp/matchorders_version_1_8_matchID_362419131_isYouth_false.xml"));
					// lineup = Helper.getLineup(new
					// File("/home/chr/tmp/matchorders_version_1_8_matchID_362217696_isYouth_false.xml"));
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception e) {
					// use standard LaF
				}
				JDialog dlg = new JDialog();
				dlg.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				dlg.getContentPane().add(new MatchLineupView(lineup));
				dlg.pack();
				// dlg.setSize(new Dimension(1300, 800));
				dlg.setVisible(true);

				dlg.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						System.exit(0);
					}
				});
			}
		});

		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				DBManager.instance().disconnect();
			}
		});
	}
}
