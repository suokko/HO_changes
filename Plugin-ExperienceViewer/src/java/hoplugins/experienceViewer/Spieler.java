package hoplugins.experienceViewer;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import plugins.IHOMiniModel;
import plugins.IMatchLineup;
import plugins.ISpieler;

final class Spieler {
	static double fehlerAbsolutZuSigma = 0.66;
	private int id;
	private String name;
	private int alter;
	private int erfahrung;
	private double erfahrungsBonus;
	private double erfahrungMin;
	private double erfahrungWahrscheinlich;
	private double erfahrungMax;
	private double vorletzteErfahrungMin;
	private double vorletzteErfahrungWahrscheinlich;
	private double vorletzteErfahrungMax;
	private int anzahlWochen;
	private int anzahlWochenFehler;
	private double erfahrungWahrscheinlichFehler;
	private double vorletzteErfahrungWahrscheinlichFehler;
	private Timestamp letzteErfahrungsAufwertung;
	private Timestamp vorletzteErfahrungsAufwertung;
	private Timestamp vorvorletzteErfahrungsAufwertung;
	private HashMap einsaetze;
	private HashMap einsaetzeNachAufwertung;
	private HashMap einsaetzeMitAktualisierungNachAufwertung;
	private HashMap einsaetzeNachVorletzterAufwertung;
	private HashMap einsaetzeMitAktualisierungNachVorletzterAufwertung;

	public Spieler(IHOMiniModel m, ISpieler inSpieler) {
		letzteErfahrungsAufwertung = null;
		vorletzteErfahrungsAufwertung = null;
		vorvorletzteErfahrungsAufwertung = null;
		einsaetze = null;
		einsaetzeNachAufwertung = null;
		einsaetzeMitAktualisierungNachAufwertung = null;
		einsaetzeNachVorletzterAufwertung = null;
		einsaetzeMitAktualisierungNachVorletzterAufwertung = null;
		id = inSpieler.getSpielerID();
		ISpieler spieler = m.getSpieler(id);
		name = spieler.getName();
		alter = spieler.getAlter();
		erfahrung = spieler.getErfahrung();
		erfahrungMin = erfahrung;
		erfahrungWahrscheinlich = erfahrung;
		erfahrungMax = erfahrung;
		vorletzteErfahrungMin = erfahrung - 1;
		vorletzteErfahrungWahrscheinlich = erfahrung - 1;
		vorletzteErfahrungMax = erfahrung - 1;
		letzteErfahrungsAufwertung = initLetzteErfahrungsaufwertung(m,
				erfahrung);
		vorletzteErfahrungsAufwertung = initLetzteErfahrungsaufwertung(m,
				erfahrung - 1);
		vorvorletzteErfahrungsAufwertung = initLetzteErfahrungsaufwertung(m,
				erfahrung - 2);
		initEinsaetze(m);
		long wochenSeitLetzterAufwertung = 0L;
		double tempo = 0.0D;
		if (erfahrungWahrscheinlich > erfahrung) {
			Timestamp jetzt = m.getBasics().getDatum();
			long msec = jetzt.getTime() - letzteErfahrungsAufwertung.getTime();
			wochenSeitLetzterAufwertung = msec / 0x240c8400L;
			if (wochenSeitLetzterAufwertung > 0L)
				tempo = (erfahrungWahrscheinlich - erfahrung)
						/ wochenSeitLetzterAufwertung;
		}
		double erf0 = 0.0D;
		if (vorletzteErfahrungsAufwertung == null) {
			erf0 = (erfahrung + 1) - erfahrungMin;
			erfahrungMax = Math.min(erfahrungMax + erf0, erfahrung + 1);
			erf0 /= 2D;
			erfahrungWahrscheinlich = Math.min(erfahrungWahrscheinlich + erf0, erfahrung + 1);
			erfahrungWahrscheinlichFehler += erf0 * erf0 * fehlerAbsolutZuSigma * fehlerAbsolutZuSigma;
		} else if (vorvorletzteErfahrungsAufwertung == null) {
			double d = erfahrung - vorletzteErfahrungMin;
			vorletzteErfahrungMax = Math.min(vorletzteErfahrungMax + d,	erfahrung);
			d /= 2D;
			vorletzteErfahrungWahrscheinlich = Math.min(vorletzteErfahrungWahrscheinlich + d, erfahrung);
			vorletzteErfahrungWahrscheinlichFehler += d * d	* fehlerAbsolutZuSigma * fehlerAbsolutZuSigma;
		}
		erfahrungWahrscheinlichFehler = Math.sqrt(erfahrungWahrscheinlichFehler);
		vorletzteErfahrungWahrscheinlichFehler = Math.sqrt(vorletzteErfahrungWahrscheinlichFehler);
		if (tempo > 0.0D) {
			double reststrecke = (erfahrung + 1) - erfahrungWahrscheinlich;
			double s = 1.0D - reststrecke - erf0;
			double ds = erfahrungWahrscheinlichFehler - erf0 * erf0	* fehlerAbsolutZuSigma * fehlerAbsolutZuSigma;
			double dto = 0.5D * fehlerAbsolutZuSigma;
			double f1 = ds / wochenSeitLetzterAufwertung;
			double f2 = (dto * s) / (wochenSeitLetzterAufwertung * wochenSeitLetzterAufwertung);
			double dv = Math.sqrt(f1 * f1 + f2 * f2);
			anzahlWochen = (int) (reststrecke / tempo) + 1;
			f1 = erfahrungWahrscheinlichFehler / tempo;
			f2 = (dv * reststrecke) / (tempo * tempo);
			anzahlWochenFehler = (int) (Math.sqrt(f1 * f1 + f2 * f2) + 0.5D);
		}
		erfahrungsBonus = spieler
				.getErfahrungsBonus((float) erfahrungWahrscheinlich);
	}

	public int getAnzahlWochen() {
		return anzahlWochen;
	}

	public int getAnzahlWochenFehler() {
		return anzahlWochenFehler;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getErfahrung() {
		return erfahrung;
	}

	public double getErfahrungsBonus() {
		return erfahrungsBonus;
	}

	public double getErfahrungMax() {
		return erfahrungMax;
	}

	public double getErfahrungMin() {
		return erfahrungMin;
	}

	public double getErfahrungWahrscheinlich() {
		return erfahrungWahrscheinlich;
	}

	public double getErfahrungWahrscheinlichFehler() {
		return erfahrungWahrscheinlichFehler;
	}

	public int getAlter() {
		return alter;
	}

	public Timestamp getLetzteErfahrungsAufwertung() {
		return letzteErfahrungsAufwertung;
	}

	public String getBemerkung() {
		String bemerkung = "";
		if (vorletzteErfahrungsAufwertung != null) {
			DecimalFormat df = new DecimalFormat("#0.00");
			DateFormat datef = DateFormat.getDateInstance(2);
			bemerkung = bemerkung + datef.format(vorletzteErfahrungsAufwertung)
					+ "-" + datef.format(letzteErfahrungsAufwertung) + ": "
					+ df.format(vorletzteErfahrungMin) + "; "
					+ df.format(vorletzteErfahrungWahrscheinlich) + " \261 "
					+ df.format(vorletzteErfahrungWahrscheinlichFehler) + "; "
					+ df.format(vorletzteErfahrungMax);
		}
		return bemerkung;
	}

	public String getLetzteErfahrungsAufwertungAlsText() {
		String ret = "";
		if (letzteErfahrungsAufwertung != null)
			ret = ret + letzteErfahrungsAufwertung;
		return ret;
	}

	public String getEinsaetzeAlsText(int typ) {
		String ret = "";
		ret = ret + getEinsaetze(typ);
		ret = ret + "/";
		ret = ret + getEinsaetzeNachAufwertung(typ);
		ret = ret + "/";
		ret = ret + getEinsaetzeMitAktualisierungNachAufwertung(typ);
		return ret;
	}

	public int getEinsaetze(int typ) {
		Integer ret = (Integer) einsaetze.get(new Integer(typ));
		if (ret == null)
			return 0;
		else
			return ret.intValue();
	}

	public int getEinsaetzeNachAufwertung(int typ) {
		Integer ret = (Integer) einsaetzeNachAufwertung.get(new Integer(typ));
		if (ret == null)
			return 0;
		else
			return ret.intValue();
	}

	public int getEinsaetzeMitAktualisierungNachAufwertung(int typ) {
		Integer ret = (Integer) einsaetzeMitAktualisierungNachAufwertung
				.get(new Integer(typ));
		if (ret == null)
			return 0;
		else
			return ret.intValue();
	}

	private void initEinsaetze(IHOMiniModel model) {
		ResultSet rs = null;
		String sql = null;
		HashSet matchIds = new HashSet();
		einsaetze = new HashMap();
		einsaetzeNachAufwertung = new HashMap();
		einsaetzeMitAktualisierungNachAufwertung = new HashMap();
		einsaetzeNachVorletzterAufwertung = new HashMap();
		einsaetzeMitAktualisierungNachVorletzterAufwertung = new HashMap();
		sql = "SELECT MATCHID FROM MATCHLINEUPPLAYER WHERE SpielerID = " + id
				+ " AND ROLEID <> " + 18 + " AND ROLEID <> " + 17;
		rs = model.getAdapter().executeQuery(sql);
		try {
			while (rs.next()) {
				int matchId = rs.getInt("MATCHID");
				matchIds.add(new Integer(matchId));
			}
			rs.close();
			sql = "SELECT MATCHID FROM MATCHHIGHLIGHTS WHERE SPIELERID = " + id
					+ " AND TYP = " + 5;
			for (rs = model.getAdapter().executeQuery(sql); rs.next();) {
				int matchId = rs.getInt("MATCHID");
				matchIds.add(new Integer(matchId));
			}

			rs.close();
			for (Iterator it = matchIds.iterator(); it.hasNext();) {
				Integer mId = (Integer) it.next();
				IMatchLineup lu = model.getMatchLineup(mId.intValue());
				int matchtyp = lu.getMatchTyp();
				Integer iMatchtyp = new Integer(matchtyp);
				Timestamp spieldatum = lu.getSpielDatum();
				Integer e = (Integer) einsaetze.get(iMatchtyp);
				int ei;
				if (e == null)
					ei = 1;
				else
					ei = e.intValue() + 1;
				einsaetze.put(iMatchtyp, new Integer(ei));
				if (spieldatum.after(letzteErfahrungsAufwertung)) {
					double wichtung = getErfahrungsgewicht(matchtyp);
					if (spielerAktualisiert(model, mId.intValue())) {
						erfahrungMin = Math.min(erfahrungMin + wichtung,
								erfahrung + 1.0D);
						erfahrungMax = Math.min(erfahrungMax + wichtung,
								erfahrung + 1.0D);
						e = (Integer) einsaetzeMitAktualisierungNachAufwertung
								.get(iMatchtyp);
						if (e == null)
							ei = 1;
						else
							ei = e.intValue() + 1;
						einsaetzeMitAktualisierungNachAufwertung.put(iMatchtyp,
								new Integer(ei));
					} else {
						erfahrungMax = Math.min(erfahrungMax + 3D * wichtung,
								erfahrung + 1.0D);
						erfahrungWahrscheinlichFehler += 2.25D * wichtung
								* wichtung * fehlerAbsolutZuSigma
								* fehlerAbsolutZuSigma;
					}
					erfahrungWahrscheinlich = Math.min(erfahrungWahrscheinlich
							+ wichtung, erfahrung + 1.0D);
					e = (Integer) einsaetzeNachAufwertung.get(iMatchtyp);
					if (e == null)
						ei = 1;
					else
						ei = e.intValue() + 1;
					einsaetzeNachAufwertung.put(iMatchtyp, new Integer(ei));
				} else if (vorletzteErfahrungsAufwertung != null
						&& spieldatum.after(vorletzteErfahrungsAufwertung)) {
					double wichtung = getErfahrungsgewicht(matchtyp);
					if (spielerAktualisiert(model, mId.intValue())) {
						vorletzteErfahrungMin = Math.min(vorletzteErfahrungMin
								+ wichtung, erfahrung);
						vorletzteErfahrungMax = Math.min(vorletzteErfahrungMax
								+ wichtung, erfahrung);
						e = (Integer) einsaetzeMitAktualisierungNachAufwertung
								.get(iMatchtyp);
						if (e == null)
							ei = 1;
						else
							ei = e.intValue() + 1;
						einsaetzeMitAktualisierungNachVorletzterAufwertung.put(
								iMatchtyp, new Integer(ei));
					} else {
						vorletzteErfahrungMax = Math.min(vorletzteErfahrungMax
								+ 3D * wichtung, erfahrung);
						vorletzteErfahrungWahrscheinlichFehler += 2.25D
								* wichtung * wichtung * fehlerAbsolutZuSigma
								* fehlerAbsolutZuSigma;
					}
					vorletzteErfahrungWahrscheinlich = Math.min(
							vorletzteErfahrungWahrscheinlich + wichtung,
							erfahrung);
					e = (Integer) einsaetzeNachVorletzterAufwertung
							.get(iMatchtyp);
					if (e == null)
						ei = 1;
					else
						ei = e.intValue() + 1;
					einsaetzeNachVorletzterAufwertung.put(iMatchtyp,
							new Integer(ei));
				}
			}

		} catch (Exception e) {
			ErrorLog.write(e);
		}
	}

	public String getErfahrungsschaetzung() {
		DecimalFormat df = new DecimalFormat("#0.00");
		return "[" + df.format(erfahrungMin) + ";"
				+ df.format(erfahrungWahrscheinlich) + ";"
				+ df.format(erfahrungMax) + "]";
	}

	public static double getErfahrungsgewicht(int matchTyp) {
		double punkteBisAufwertung = 28;
		double erfahrungFuerSpiel;
		switch (matchTyp) {
		case 2: // '\002'
		case 3: // '\003'
			erfahrungFuerSpiel = 2;
			break;

		case 1: // '\001'
			erfahrungFuerSpiel = 1;
			break;

		case 6: // '\006'
		case 7: // '\007'
		case 10: // '\n'
		case 11: // '\013'
		case 12: // '\f'
			erfahrungFuerSpiel = 3;
			break;

		case 8: // '\b'
		case 9: // '\t'
			erfahrungFuerSpiel = 0.2;
			break;

		case 4: // '\004'
		case 5: // '\005'
			erfahrungFuerSpiel = 0.1;
			break;

		default:
			erfahrungFuerSpiel = 0.0;
			break;
		}
		return erfahrungFuerSpiel / punkteBisAufwertung;
	}

	private boolean spielerAktualisiert(IHOMiniModel model, int matchId) {
		ResultSet rs = null;
		String sql = null;
		sql = "SELECT TYP,SUBTYP FROM MATCHHIGHLIGHTS WHERE MATCHID = "
				+ matchId + " AND SPIELERID = " + id;
		rs = model.getAdapter().executeQuery(sql);
		try {
			while (rs.next()) {
				int typ = rs.getInt("TYP");
				int subtyp = rs.getInt("SUBTYP");
				switch (typ) {
				case 1: // '\001'
					return true;

				case 5: // '\005'
					return true;

				case 0: // '\0'
				case 3: // '\003'
				case 4: // '\004'
				default:
					switch (subtyp) {
					case 46: // '.'
						return true;

					case 90: // 'Z'
						return true;

					case 91: // '['
						return true;

					case 92: // '\\'
						return true;

					case 93: // ']'
						return true;

					case 94: // '^'
						return true;

					case 95: // '_'
						return true;

					case 96: // '`'
						return true;

					case 10: // '\n'
						return true;

					case 11: // '\013'
						return true;

					case 12: // '\f'
						return true;

					case 13: // '\r'
						return true;

					case 14: // '\016'
						return true;
					}
					break;

				case 2: // '\002'
					break;
				}
			}
			rs.close();
		} catch (Exception e) {
			ErrorLog.write(e);
		}
		return false;
	}

	private Timestamp initLetzteErfahrungsaufwertung(IHOMiniModel model,
			int erfahrung) {
		Timestamp ret = null;
		ResultSet rs = null;
		String sql = null;
		sql = "SELECT min(DATUM) FROM Spieler WHERE SpielerID = " + id
				+ " AND Erfahrung = " + erfahrung;
		rs = model.getAdapter().executeQuery(sql);
		try {
			while (rs.next())
				ret = rs.getTimestamp(1);
			rs.close();
		} catch (Exception e) {
			ErrorLog.write(e);
		}
		return ret;
	}

}
