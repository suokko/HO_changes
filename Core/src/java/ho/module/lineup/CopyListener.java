package ho.module.lineup;

import ho.core.model.HOVerwaltung;
import ho.core.util.HOLogger;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;


/**
 * Listener for the "copy ratings to clipboard" feature at the lineup screen.
 *
 * @author aik
 */
public class CopyListener implements ActionListener {

	private AufstellungsRatingPanel lineup;
	private static String LF = System.getProperty("line.separator", "\n");
	private JMenuItem miPlaintext = new JMenuItem(HOVerwaltung.instance().getLanguageString("Lineup.CopyRatings.PlainText"));
	private JMenuItem miHattickMLDef = new JMenuItem(HOVerwaltung.instance().getLanguageString("Lineup.CopyRatings.HattrickML"));
	final JPopupMenu menu = new JPopupMenu();

	/**
	 * Create the CopyListener and initialize the gui components.
	 */
	public CopyListener(AufstellungsRatingPanel lineup) {
		this.lineup = lineup;
		miPlaintext.addActionListener(this);
		miHattickMLDef.addActionListener(this);
		menu.add(miPlaintext);
		menu.add(miHattickMLDef);
	}

	/**
	 * Handle action events (shop popup menu or copy ratings).
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e != null && e.getSource().equals(miPlaintext)) {
			menu.setVisible(false);
			copyToClipboard(getRatingsAsText());
		} else if (e != null && e.getSource().equals(miHattickMLDef)) {
			copyToClipboard(getRatingsAsHattrickML_DefTop());
			menu.setVisible(false);
		} else if (e != null && e.getSource() != null && e.getSource() instanceof Component) {
			menu.show((Component)e.getSource(), 1, 1);
		}
	}

	/**
	 * Copy the given text into the system clip board.
	 */
	public static void copyToClipboard(final String txt) {
		try {
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(txt), null);
		} catch (Exception e) {
			HOLogger.instance().error(CopyListener.class, e);
		}
	}

	/**
	 * Get ratings as normal text, ordered like in HT.
	 */
	private String getRatingsAsText() {
		StringBuilder sb = new StringBuilder("");
		if (lineup != null) {
			sb.append(HOVerwaltung.instance().getLanguageString("ls.match.ratingsector.midfield") + ": " + lineup.getMidfieldRating() + LF);
			sb.append(HOVerwaltung.instance().getLanguageString("ls.match.ratingsector.rightdefence") + ": " + lineup.getRightDefenseRating() + LF);
			sb.append(HOVerwaltung.instance().getLanguageString("ls.match.ratingsector.centraldefence") + ": " + lineup.getCentralDefenseRating() + LF);
			sb.append(HOVerwaltung.instance().getLanguageString("ls.match.ratingsector.leftdefence") + ": " + lineup.getLeftDefenseRating() + LF);
			sb.append(HOVerwaltung.instance().getLanguageString("ls.match.ratingsector.rightattack") + ": " + lineup.getRightAttackRating() + LF);
			sb.append(HOVerwaltung.instance().getLanguageString("ls.match.ratingsector.centralattack") + ": " + lineup.getCentralAttackRating() + LF);
			sb.append(HOVerwaltung.instance().getLanguageString("ls.match.ratingsector.leftattack") + ": " + lineup.getLeftAttackRating() + LF);
		}
		return sb.toString();
	}

	/**
	 * Get ratings in a HT-ML style table.
	 */
	private String getRatingsAsHattrickML_DefTop() {
		StringBuilder sb = new StringBuilder("");
		if (lineup != null) {
			sb.append("[table]");
			sb.append("[tr][th][/th][th]"+HOVerwaltung.instance().getLanguageString("Rechts"));
			sb.append("[/th][th]"+HOVerwaltung.instance().getLanguageString("Mitte"));
			sb.append("[/th][th]"+HOVerwaltung.instance().getLanguageString("Links")+"[/th][/tr]" + LF);
			sb.append("[tr][th]"+HOVerwaltung.instance().getLanguageString("match.sector.defence"));
			sb.append("[/th][td align=center]"+lineup.getRightDefenseRating());
			sb.append("[/td][td align=center]"+lineup.getCentralDefenseRating());
			sb.append("[/td][td align=center]"+lineup.getLeftDefenseRating());
			sb.append("[/td][/tr]" + LF);
			sb.append("[tr][th]"+HOVerwaltung.instance().getLanguageString("ls.match.ratingsector.midfield"));
			sb.append("[/th][td colspan=3 align=center]");
			sb.append(lineup.getMidfieldRating()+"[/td][/tr]" + LF);
			sb.append("[tr][th]"+HOVerwaltung.instance().getLanguageString("Attack"));
			sb.append("[/th][td align=center]"+lineup.getRightAttackRating());
			sb.append("[/td][td align=center]"+lineup.getCentralAttackRating());
			sb.append("[/td][td align=center]"+lineup.getLeftAttackRating());
			sb.append("[/td][/tr]" + LF);
			sb.append("[/table]");
			sb.append(LF);
		}
		return sb.toString();
	}
}
