package hoplugins.experienceViewer;

import hoplugins.commons.utils.PluginProperty;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import plugins.IHOMiniModel;
import plugins.ISpieler;

public class Spielertabelle extends JTable {
	private static final long serialVersionUID = 3117625304079832033L;

	private class SpielertabellenModell extends AbstractTableModel {
		private static final long serialVersionUID = -3365452097304380041L;

		public int getRowCount() {
			return model.getAllSpieler().size();
		}

		public int getColumnCount() {
			return cm.getColumnCount();
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			DecimalFormat decimalFormat = new DecimalFormat("#0.00");
			DecimalFormat percentFormat = new DecimalFormat("##0%");
			DateFormat datef = DateFormat.getDateInstance(2);
			int pos = spielerSortierung[rowIndex].index;
			Spieler s = (Spieler) spieler.elementAt(pos);
			String ret;
			switch (columnIndex) {
			case 0: // '\0'
				ret = s.getName();
				break;

			case 1: // '\001'
				ret = "" + s.getAlter();
				break;

			case 2: // '\002'
				ret = "" + s.getErfahrung();
				break;

			case 3: // '\003'
				ret = datef.format(s.getLetzteErfahrungsAufwertung());
				break;

			case 4: // '\004'
				ret = percentFormat.format(s.getErfahrungsBonus());
				break;

			case 5: // '\005'
				ret = decimalFormat.format(s.getErfahrungMin());
				break;

			case 6: // '\006'
				ret = decimalFormat.format(s.getErfahrungWahrscheinlich()) + " \261 "
						+ decimalFormat.format(s.getErfahrungWahrscheinlichFehler());
				break;

			case 7: // '\007'
				ret = decimalFormat.format(s.getErfahrungMax());
				break;

			case 8: // '\b'
				ret = "";
				int anzahlWochen = s.getAnzahlWochen();
				if (anzahlWochen > 0)
					ret = ret + anzahlWochen + " \261 "
							+ s.getAnzahlWochenFehler();
				break;

			case 9: // '\t'
				ret = s.getEinsaetzeAlsText(9);
				break;

			case 10: // '\n'
				ret = s.getEinsaetzeAlsText(8);
				break;

			case 11: // '\013'
				ret = s.getEinsaetzeAlsText(7);
				break;

			case 12: // '\f'
				ret = s.getEinsaetzeAlsText(6);
				break;

			case 13: // '\r'
				ret = s.getEinsaetzeAlsText(11);
				break;

			case 14: // '\016'
				ret = s.getEinsaetzeAlsText(10);
				break;

			case 15: // '\017'
				ret = s.getEinsaetzeAlsText(1);
				break;

			case 16: // '\020'
				ret = s.getEinsaetzeAlsText(3);
				break;

			case 17: // '\021'
				ret = s.getEinsaetzeAlsText(2);
				break;

			case 18: // '\022'
				ret = s.getEinsaetzeAlsText(12);
				break;

			case 19: // '\023'
				ret = s.getEinsaetzeAlsText(5);
				break;

			case 20: // '\024'
				ret = s.getEinsaetzeAlsText(4);
				break;

			case 21: // '\025'
				ret = s.getBemerkung();
				break;

			default:
				ret = "-";
				break;
			}
			return ret;
		}

		SpielertabellenModell() {
		}
	}

	private class SpielertabellenSpalte extends DefaultTableColumnModel {
		private static final long serialVersionUID = 2065608315613845209L;
		private ColumnHeaderToolTips tips;

		public ColumnHeaderToolTips getColumnHeaderToolTips() {
			return tips;
		}

		public void SpalteHinzufuegen(int index, String text, int weite) {
			TableColumn col = new TableColumn(index, weite);
//			String t = properties.getProperty(text);
			String t = PluginProperty.getString(text);
			String tipp;
			if (index > 8 && index < 21) {
//				String zusatz = properties
//						.getProperty("EINSATZSPALTENERKLAERUNG");
				String zusatz = PluginProperty.getString("EINSATZSPALTENERKLAERUNG");
				tipp = "<html>" + t + "<br>" + zusatz + "</html>";
			} else {
				tipp = t;
			}
			col.setHeaderValue(t);
			tips.setToolTip(col, tipp);
			addColumn(col);
		}

		public SpielertabellenSpalte() {
			tips = null;
			tips = new ColumnHeaderToolTips();
		}
	}

	private class SpielerSortierung implements Comparable {

		private int index;

		public int compareTo(Object o) {
			int o1 = index;
			int o2 = ((SpielerSortierung) o).index;
			Spieler s1 = (Spieler) spieler.elementAt(o1);
			Spieler s2 = (Spieler) spieler.elementAt(o2);
			int col = GibSortierspalte();
			int r;
			if (istAufwaertsSortiert(col))
				r = 1;
			else
				r = -1;
			switch (col) {
			case 0: // '\0'
			{
				return r * s1.getName().compareTo(s2.getName());
			}

			case 1: // '\001'
			{
				Integer i1 = new Integer(s1.getAlter());
				return r * i1.compareTo(new Integer(s2.getAlter()));
			}

			case 2: // '\002'
			{
				Integer i1 = new Integer(s1.getErfahrung());
				return r * i1.compareTo(new Integer(s2.getErfahrung()));
			}

			case 3: // '\003'
			{
				return r
						* s1.getLetzteErfahrungsAufwertung().compareTo(
								s2.getLetzteErfahrungsAufwertung());
			}

			case 4: // '\004'
			{
				Double d1 = new Double(s1.getErfahrungsBonus());
				return r * d1.compareTo(new Double(s2.getErfahrungsBonus()));
			}

			case 5: // '\005'
			{
				Double d1 = new Double(s1.getErfahrungMin());
				return r * d1.compareTo(new Double(s2.getErfahrungMin()));
			}

			case 6: // '\006'
			{
				Double d1 = new Double(s1.getErfahrungWahrscheinlich());
				return r
						* d1.compareTo(new Double(s2
								.getErfahrungWahrscheinlich()));
			}

			case 7: // '\007'
			{
				Double d1 = new Double(s1.getErfahrungMax());
				return r * d1.compareTo(new Double(s2.getErfahrungMax()));
			}

			case 8: // '\b'
			{
				Integer i1 = new Integer(s1.getAnzahlWochen());
				return r * i1.compareTo(new Integer(s2.getAnzahlWochen()));
			}

			case 9: // '\t'
			{
				return r * vergleicheEinsaetze(s1, s2, 9);
			}

			case 10: // '\n'
			{
				return r * vergleicheEinsaetze(s1, s2, 8);
			}

			case 11: // '\013'
			{
				return r * vergleicheEinsaetze(s1, s2, 7);
			}

			case 12: // '\f'
			{
				return r * vergleicheEinsaetze(s1, s2, 6);
			}

			case 13: // '\r'
			{
				return r * vergleicheEinsaetze(s1, s2, 11);
			}

			case 14: // '\016'
			{
				return r * vergleicheEinsaetze(s1, s2, 10);
			}

			case 15: // '\017'
			{
				return r * vergleicheEinsaetze(s1, s2, 1);
			}

			case 16: // '\020'
			{
				return r * vergleicheEinsaetze(s1, s2, 3);
			}

			case 17: // '\021'
			{
				return r * vergleicheEinsaetze(s1, s2, 2);
			}

			case 18: // '\022'
			{
				return r * vergleicheEinsaetze(s1, s2, 12);
			}

			case 19: // '\023'
			{
				return r * vergleicheEinsaetze(s1, s2, 5);
			}

			case 20: // '\024'
			{
				return r * vergleicheEinsaetze(s1, s2, 4);
			}

			case 21: // '\025'
			{
				return r * s1.getBemerkung().compareTo(s2.getBemerkung());
			}
			}
			return 0;
		}

		public SpielerSortierung(int index) {
			this.index = index;
		}
	}

	private class MouseHandler extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			JTableHeader h = (JTableHeader) e.getSource();
			TableColumnModel columnModel = h.getColumnModel();
			int viewColumn = columnModel.getColumnIndexAtX(e.getX());
			int column = columnModel.getColumn(viewColumn).getModelIndex();
			if (column != -1) {
				boolean aufwaerts = istAufwaertsSortiert(column);
				tabelleSortieren(column, !aufwaerts);
			}
		}

		MouseHandler() {
		}
	}

	public class ColumnHeaderToolTips extends MouseMotionAdapter {

		TableColumn curCol;
		Map tips;

		public void setToolTip(TableColumn col, String tooltip) {
			if (tooltip == null)
				tips.remove(col);
			else
				tips.put(col, tooltip);
		}

		public void mouseMoved(MouseEvent evt) {
			TableColumn col = null;
			JTableHeader header = (JTableHeader) evt.getSource();
			JTable table = header.getTable();
			TableColumnModel colModel = table.getColumnModel();
			int vColIndex = colModel.getColumnIndexAtX(evt.getX());
			if (vColIndex >= 0)
				col = colModel.getColumn(vColIndex);
			if (col != curCol) {
				header.setToolTipText((String) tips.get(col));
				curCol = col;
			}
		}

		public ColumnHeaderToolTips() {
			tips = new HashMap();
		}
	}

	public class ColoredTableCellRenderer implements TableCellRenderer {

		private Color hellblau;
		private Color dunkelblau;
		private Color hellgelb;
		private Color hellgruen;
		private JLabel label;

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (label == null)
				label = new JLabel((String) value);
			else
				label.setText((String) value);
			label.setOpaque(true);
			javax.swing.border.Border b = BorderFactory.createEmptyBorder(1, 1,
					1, 1);
			label.setBorder(b);
			label.setFont(table.getFont());
			label.setForeground(table.getForeground());
			label.setBackground(table.getBackground());
			if (hasFocus)
				label.setBackground(Color.lightGray);
			else if (isSelected) {
				label.setBackground(Color.lightGray);
			} else {
				column = table.convertColumnIndexToModel(column);
				switch (column) {
				case 2: // '\002'
				case 3: // '\003'
				case 4: // '\004'
					label.setBackground(hellgruen);
					break;

				case 5: // '\005'
					label.setBackground(hellgelb);
					break;

				case 6: // '\006'
					label.setBackground(hellgelb);
					break;

				case 7: // '\007'
				case 8: // '\b'
					label.setBackground(hellgelb);
					break;

				case 9: // '\t'
				case 10: // '\n'
					label.setBackground(hellblau);
					break;

				case 11: // '\013'
				case 12: // '\f'
				case 13: // '\r'
				case 14: // '\016'
				case 15: // '\017'
				case 16: // '\020'
				case 17: // '\021'
				case 18: // '\022'
					label.setBackground(dunkelblau);
					break;

				case 19: // '\023'
				case 20: // '\024'
					label.setBackground(hellblau);
					break;
				}
			}
			return label;
		}

		public ColoredTableCellRenderer() {
			hellblau = new Color(220, 220, 255);
			dunkelblau = new Color(200, 200, 255);
			hellgelb = new Color(255, 255, 200);
			hellgruen = new Color(200, 255, 200);
			label = null;
		}
	}

	private class Spaltenkonfiguration {

		public int index;
		public int weite;

		Spaltenkonfiguration() {
		}
	}

	public class WindowClosingAdapter extends WindowAdapter {

		public void windowClosing(WindowEvent event) {
			KonfigurationSpeichern();
		}

		public WindowClosingAdapter() {
		}
	}

	public static final String spaltennamen[] = { "Spieler", "Alter",
			"Erfahrung", "seit", "Bonus", "SchaetzungMin",
			"SchaetzungWahrscheinlich", "SchaetzungMax", "WochenBisAufwertung",
			"INT_TESTCUPSPIEL", "INT_TESTSPIEL", "INTCUPSPIEL", "INTSPIEL",
			"LAENDERCUPSPIEL", "LAENDERSPIEL", "LIGASPIEL", "POKALSPIEL",
			"QUALISPIEL", "TESTLAENDERSPIEL", "TESTPOKALSPIEL", "TESTSPIEL",
			"Bemerkung" };
	public static final int spaltenweite[] = { 120, 60, 60, 60, 40, 40, 80, 40,
			80, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 240 };
	private SpielertabellenSpalte cm;
	private IHOMiniModel model;
	private SpielerSortierung spielerSortierung[];
	private Vector spieler;
	private boolean sortierrichtung[];
	private int sortierspalte;
	private AbstractTableModel tm;
	private MouseListener mouseListener;
//	private Properties properties;
	private WindowClosingAdapter windowClosingAdapter;
	protected static String experienceViewerVerzeichnis;
	protected static String spracheVerzeichnis;

	public Spielertabelle(IHOMiniModel m) {
		cm = null;
		model = null;
		spielerSortierung = null;
		spieler = null;
		sortierrichtung = null;
		sortierspalte = 0;
		tm = null;
		mouseListener = null;
//		properties = null;
		windowClosingAdapter = null;
		model = m;
//		initRessourcen();
		aktualisieren();
		cm = new SpielertabellenSpalte();
		Spaltenkonfiguration spaltenkonfiguration[] = KonfigurationLaden();
		if (spaltenkonfiguration == null) {
			int n = spaltennamen.length;
			for (int i = 0; i < n; i++)
				cm.SpalteHinzufuegen(i, spaltennamen[i], spaltenweite[i]);

		} else {
			int n = spaltenkonfiguration.length;
			for (int i = 0; i < n; i++) {
				int index = spaltenkonfiguration[i].index;
				int weite = spaltenkonfiguration[i].weite;
				cm.SpalteHinzufuegen(index, spaltennamen[index], weite);
			}

		}
		sortierrichtung = new boolean[cm.getColumnCount()];
		tm = new SpielertabellenModell();
		setSize(1200, 500);
		setModel(tm);
		setColumnModel(cm);
		setAutoResizeMode(0);
		setDefaultRenderer(java.lang.Object.class,
				new ColoredTableCellRenderer());
		JTableHeader header = getTableHeader();
		if (header != null) {
			mouseListener = new MouseHandler();
			header.addMouseListener(mouseListener);
			header.addMouseMotionListener(cm.getColumnHeaderToolTips());
		}
	}

	public void aktualisieren() {
		Vector alleSpieler = model.getAllSpieler();
		spielerSortierung = new SpielerSortierung[alleSpieler.size()];
		spieler = new Vector(alleSpieler.size());
		int pos = 0;
		for (Enumeration el = alleSpieler.elements(); el.hasMoreElements();) {
			spieler.add(new Spieler(model, (ISpieler) el.nextElement()));
			spielerSortierung[pos] = new SpielerSortierung(pos);
			pos++;
		}

	}

	private int vergleicheEinsaetze(Spieler s1, Spieler s2, int spieltyp) {
		int ret = 0;
		Integer i1 = new Integer(s1.getEinsaetze(spieltyp));
		ret = i1.compareTo(new Integer(s2.getEinsaetze(spieltyp)));
		if (ret == 0) {
			i1 = new Integer(s1.getEinsaetzeNachAufwertung(spieltyp));
			ret = i1.compareTo(new Integer(s2
					.getEinsaetzeNachAufwertung(spieltyp)));
			if (ret == 0) {
				i1 = new Integer(s1
						.getEinsaetzeMitAktualisierungNachAufwertung(spieltyp));
				ret = i1
						.compareTo(new Integer(
								s2
										.getEinsaetzeMitAktualisierungNachAufwertung(spieltyp)));
			}
		}
		return ret;
	}

	private int GibSortierspalte() {
		return sortierspalte;
	}

	private boolean istAufwaertsSortiert(int column) {
		if (sortierrichtung != null && column > -1
				&& column < cm.getColumnCount())
			return sortierrichtung[column];
		else
			return false;
	}

	private void tabelleSortieren(int spalte, boolean aufwaerts) {
		sortierspalte = spalte;
		sortierrichtung[spalte] = aufwaerts;
		Arrays.sort(spielerSortierung);
		TabelleGeaendert();
	}

	private void TabelleGeaendert() {
		tm.fireTableDataChanged();
	}

//	private void initRessourcen() {
//		properties = new Properties();
//		properties.putAll(model.getResource());
//		File languagefile = new File(spracheVerzeichnis + File.separator
//				+ model.getHelper().getLanguageName() + ".properties");
//		if (!languagefile.exists())
//			languagefile = new File(spracheVerzeichnis + File.separator
//					+ "English.properties");
//		if (languagefile.exists()) {
//			Properties props = new Properties();
//			try {
//				props.load(new FileInputStream(languagefile));
//				properties.putAll(props);
//			} catch (IOException e) {
//				ErrorLog.write(e);
//			}
//		}
//	}

//	public String getProperty(String key) {
//		return properties.getProperty(key);
//	}

	private String gibKonfigurationsdateiname() {
		return experienceViewerVerzeichnis + File.separator
				+ "ExperienceViewer.cfg";
	}

	public Spaltenkonfiguration[] KonfigurationLaden() {
		Spaltenkonfiguration ret[] = null;
		try {
			int index[] = new int[spaltennamen.length];
			int weite[] = new int[spaltennamen.length];
			int n = 0;
			StreamTokenizer st = new StreamTokenizer(new FileReader(
					gibKonfigurationsdateiname()));
			st.slashSlashComments(true);
			st.parseNumbers();
			st.eolIsSignificant(true);
			boolean istIndex = true;
			int tval;
			while ((tval = st.nextToken()) != -1) {
				if (n >= spaltennamen.length)
					break;
				if (tval == -2) {
					if (istIndex) {
						index[n] = (int) st.nval;
						istIndex = false;
					} else {
						istIndex = true;
						int w = (int) st.nval;
						weite[n] = Math.max(0, Math.min(400, w));
					}
					continue;
				}
				if (tval != 10)
					continue;
				if (!istIndex)
					break;
				n++;
			}
			if (n == spaltennamen.length) {
				int i;
				for (i = 0; i < n; i++) {
					int j;
					for (j = 0; j < n; j++)
						if (index[j] == i)
							break;

					if (j == n)
						break;
				}

				if (i == n) {
					ret = new Spaltenkonfiguration[n];
					for (i = 0; i < n; i++) {
						ret[i] = new Spaltenkonfiguration();
						ret[i].index = index[i];
						ret[i].weite = weite[i];
					}

				}
			}
		} catch (Exception e) {
			ErrorLog.write(e);
		}
		return ret;
	}

	public void KonfigurationSpeichern() {
		try {
			TableColumnModel tcm = getColumnModel();
			FileWriter fileWriter = new FileWriter(
					gibKonfigurationsdateiname(), false);
			int n = tcm.getColumnCount();
			for (int i = 0; i < n; i++) {
				TableColumn p = tcm.getColumn(i);
				String out = p.getModelIndex() + " " + p.getWidth() + "\r\n";
				fileWriter.write(out);
			}

			fileWriter.flush();
			fileWriter.close();
		} catch (Exception e) {
			ErrorLog.write(e);
		}
	}

	public WindowListener gibWindowClosingAdapter() {
		if (windowClosingAdapter == null)
			windowClosingAdapter = new WindowClosingAdapter();
		return windowClosingAdapter;
	}

	static {
		experienceViewerVerzeichnis = System.getProperty("user.dir")
				+ File.separator + "hoplugins" + File.separator
				+ "experienceViewer";
		spracheVerzeichnis = experienceViewerVerzeichnis + File.separator
				+ "Sprache";
	}
}
