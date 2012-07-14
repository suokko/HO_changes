package ho.module.lineup.exchange;

import ho.core.gui.HOMainFrame;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.player.ISpielerPosition;
import ho.core.model.player.SpielerPosition;
import ho.module.lineup.Lineup;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class LineupCheck {

	public static boolean doUpload(Lineup lineup) {
		List<JLabel> problems = new ArrayList<JLabel>();
		if (hasFreePosition(lineup)) {
			problems.add(getErrorLabel("lineup.upload.check.lineupIncomplete"));
		}
		if (hasFreeReserves(lineup)) {
			problems.add(getWarningLabel("lineup.upload.check.reservesIncomplete"));
		}
		if (lineup.getKapitaen() <= 0) {
			problems.add(getWarningLabel("lineup.upload.check.captainNotSet"));
		}
		if (lineup.getKicker() <= 0) {
			problems.add(getWarningLabel("lineup.upload.check.setPiecesNotSet"));
		}
		if (problems.size() > 0) {
			JLabel label = new JLabel(HOVerwaltung.instance().getLanguageString("lineup.upload.check.uploadAnywayQ"));
			label.setBorder(BorderFactory.createEmptyBorder(10, 20, 2, 10));
			problems.add(label);
		} else {
			return true;
		}

		String title = HOVerwaltung.instance().getLanguageString("lineup.upload.check.title");
		int result = JOptionPane.showConfirmDialog(HOMainFrame.instance(),
				problems.toArray(), title, JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		return result == JOptionPane.YES_OPTION;
	}

	public static boolean hasFreePosition(Lineup lineup) {
		return lineup.hasFreePosition();
	}

	public static boolean hasFreeReserves(Lineup lineup) {
		return isFree(lineup, ISpielerPosition.substKeeper)
				|| isFree(lineup, ISpielerPosition.substDefender)
				|| isFree(lineup, ISpielerPosition.substWinger)
				|| isFree(lineup, ISpielerPosition.substInnerMidfield)
				|| isFree(lineup, ISpielerPosition.substForward);
	}

	private static boolean isFree(Lineup lineup, int positionId) {
		SpielerPosition pos = lineup.getPositionById(positionId);
		return pos == null || pos.getSpielerId() == 0;
	}

	private static JLabel getWarningLabel(String key) {
		JLabel label = new JLabel(HOVerwaltung.instance().getLanguageString(key));
		label.setIcon(ThemeManager.getIcon(HOIconName.EXCLAMATION));
		return label;
	}

	private static JLabel getErrorLabel(String key) {
		JLabel label = new JLabel(HOVerwaltung.instance().getLanguageString(key));
		label.setIcon(ThemeManager.getIcon(HOIconName.EXCLAMATION_RED));
		return label;
	}
}
