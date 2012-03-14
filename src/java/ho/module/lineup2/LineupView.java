package ho.module.lineup2;

import ho.core.db.DBManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.module.lineup.Lineup;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;


public class LineupView extends JPanel {

	private static final long serialVersionUID = 1882335792142040473L;

	public LineupView(Lineup lineup) {
		initComponents(lineup);
	}

	private void initComponents(Lineup lineup) {
		setLayout(new GridBagLayout());

		JPanel detailsPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		AufstellungsRatingPanel ratingPanel = new AufstellungsRatingPanel(lineup);
		detailsPanel.add(ratingPanel, gbc);

		gbc.gridy = 1;
		AufstellungsDetailPanel aufstellungsDetailPanel = new AufstellungsDetailPanel();
		detailsPanel.add(aufstellungsDetailPanel, gbc);

		LineupPositionsPanel lineupPositionsPanel = new LineupPositionsPanel(lineup, new LineupSettings());
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 5);
		add(lineupPositionsPanel, gbc);
		new Insets(10, 5, 10, 10);
		gbc.gridx = 1;
		add(detailsPanel, gbc);

		aufstellungsDetailPanel.reInit();
		lineupPositionsPanel.refresh();
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
				dlg.getContentPane().add(new LineupView(lineup));
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
