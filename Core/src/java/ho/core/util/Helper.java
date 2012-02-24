// %1586804725:de.hattrickorganizer.tools%
package ho.core.util;


import ho.core.datatype.CBItem;
import ho.core.model.HOVerwaltung;
import ho.core.model.SpielerPosition;

import java.awt.Component;
import java.awt.Window;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import plugins.ISpieler;
import plugins.ISpielerPosition;


/**
 * Klasse mit Hilfsmethoden, die in mehreren Dialogen/Panels benutzt werden
 */
public class Helper {
 
	/** Gesamteinstufung */
	public static final CBItem[] EINSTUFUNG = {
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.nicht_vorhanden), ISpieler.nicht_vorhanden),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.katastrophal), ISpieler.katastrophal),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.erbaermlich), ISpieler.erbaermlich),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.armselig), ISpieler.armselig),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.schwach), ISpieler.schwach),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.durchschnittlich), ISpieler.durchschnittlich),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.passabel), ISpieler.passabel),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.gut), ISpieler.gut),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.sehr_gut), ISpieler.sehr_gut),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.hervorragend), ISpieler.hervorragend),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.grossartig), ISpieler.grossartig),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.brilliant), ISpieler.brilliant),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.fantastisch), ISpieler.fantastisch),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.Weltklasse), ISpieler.Weltklasse),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.uebernatuerlich), ISpieler.uebernatuerlich),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.gigantisch), ISpieler.gigantisch),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.ausserirdisch), ISpieler.ausserirdisch),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.mythisch), ISpieler.mythisch),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.maerchenhaft), ISpieler.maerchenhaft),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.galaktisch), ISpieler.galaktisch),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.goettlich), ISpieler.goettlich) };

    /** Form */
	public static final CBItem[] EINSTUFUNG_FORM = {
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.nicht_vorhanden), ISpieler.nicht_vorhanden),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.katastrophal), ISpieler.katastrophal),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.erbaermlich), ISpieler.erbaermlich),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.armselig), ISpieler.armselig),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.schwach), ISpieler.schwach),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.durchschnittlich), ISpieler.durchschnittlich),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.passabel), ISpieler.passabel),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.gut), ISpieler.gut),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.sehr_gut), ISpieler.sehr_gut) };

    /** Kondition */
	public static final CBItem[] EINSTUFUNG_KONDITION = {
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.nicht_vorhanden), ISpieler.nicht_vorhanden),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.katastrophal), ISpieler.katastrophal),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.erbaermlich), ISpieler.erbaermlich),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.armselig), ISpieler.armselig),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.schwach), ISpieler.schwach),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.durchschnittlich), ISpieler.durchschnittlich),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.passabel), ISpieler.passabel),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.gut), ISpieler.gut),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.sehr_gut), ISpieler.sehr_gut),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.hervorragend), ISpieler.hervorragend) };

    /** Form */
	public static final CBItem[] EINSTUFUNG_TRAINER = {
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.katastrophal), ISpieler.katastrophal),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.erbaermlich), ISpieler.erbaermlich),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.armselig), ISpieler.armselig),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.schwach), ISpieler.schwach),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.durchschnittlich), ISpieler.durchschnittlich),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.passabel), ISpieler.passabel),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.gut), ISpieler.gut),
			new CBItem(PlayerHelper.getNameForSkill(ISpieler.sehr_gut), ISpieler.sehr_gut) };

    /** Speciality */
    public static final CBItem[] EINSTUFUNG_SPECIALITY = {
    	new CBItem("", 0),
    	new CBItem(HOVerwaltung.instance().getLanguageString("sp_Technical"), 1),
    	new CBItem(HOVerwaltung.instance().getLanguageString("sp_Quick"),2),
    	new CBItem(HOVerwaltung.instance().getLanguageString("sp_Powerful"), 3),
    	new CBItem(HOVerwaltung.instance().getLanguageString("sp_Unpredictable"), 4),
    	new CBItem(HOVerwaltung.instance().getLanguageString("sp_Head"), 5),
    	new CBItem(HOVerwaltung.instance().getLanguageString("sp_Regainer"), 6)
    };

    /** Spielerpositionen */
	public static final CBItem[] SPIELERPOSITIONEN = {
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.KEEPER), ISpielerPosition.KEEPER),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.CENTRAL_DEFENDER), ISpielerPosition.CENTRAL_DEFENDER),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.CENTRAL_DEFENDER_OFF), ISpielerPosition.CENTRAL_DEFENDER_OFF),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.CENTRAL_DEFENDER_TOWING), ISpielerPosition.CENTRAL_DEFENDER_TOWING),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.BACK), ISpielerPosition.BACK),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.BACK_OFF), ISpielerPosition.BACK_OFF),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.BACK_DEF), ISpielerPosition.BACK_DEF),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.BACK_TOMID), ISpielerPosition.BACK_TOMID),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.MIDFIELDER), ISpielerPosition.MIDFIELDER),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.MIDFIELDER_OFF), ISpielerPosition.MIDFIELDER_OFF),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.MIDFIELDER_DEF), ISpielerPosition.MIDFIELDER_DEF),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.MIDFIELDER_TOWING), ISpielerPosition.MIDFIELDER_TOWING),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.WINGER), ISpielerPosition.WINGER),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.WINGER_OFF), ISpielerPosition.WINGER_OFF),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.WINGER_DEF), ISpielerPosition.WINGER_DEF),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.WINGER_TOMID), ISpielerPosition.WINGER_TOMID),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.FORWARD), ISpielerPosition.FORWARD),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.FORWARD_DEF), ISpielerPosition.FORWARD_DEF),
			new CBItem(SpielerPosition.getNameForPosition(ISpielerPosition.FORWARD_TOWING), ISpielerPosition.FORWARD_TOWING) };

    /** weather combo boxes */
	public static final CBItem[] WETTER = { new CBItem("", 1), new CBItem("", 2), new CBItem("", 3), new CBItem("", 4) };

	public static NumberFormat CURRENCYFORMAT = java.text.NumberFormat.getCurrencyInstance();

    /** wird für das Parsen in parseFloat benötigt */
    public static DecimalFormat INTEGERFORMAT = new java.text.DecimalFormat("#0");

    /** decimal format - 1 fraction digit */
    public static DecimalFormat DEFAULTDEZIMALFORMAT = new java.text.DecimalFormat("#0.0");

    /** decimal format - 2 fraction digits */
    public static DecimalFormat DEZIMALFORMAT_2STELLEN = new java.text.DecimalFormat("#0.00");

    /** decimal format - 3 fraction digits */
    public static DecimalFormat DEZIMALFORMAT_3STELLEN = new java.text.DecimalFormat("#0.000");

    /** Schon eine Meldung angezeigt? */
    public static boolean paneShown;

    /**
     * Errechnet die Spaltenbreite für den User-Schriftgrösse
     *
     */
    public static int calcCellWidth(int width) {
        return (int) (((float) width) * gui.UserParameter.instance().zellenbreitenFaktor);
    }

    /**
     * Macht aus einem double[] mit Timevalues einen formatierten String[]
     *
     */
    public static String[] convertTimeMillisToFormatString(double[] timewerte) {
        final String[] returnwerte = new String[timewerte.length];

        for (int i = 0; i < returnwerte.length; i++) {
            returnwerte[i] = java.text.DateFormat.getDateInstance().format(new java.util.Date((long) timewerte[i]));
        }

        return returnwerte;
    }

    /**
     * Erzeugt ein ComboBoxModel aus einem Vector
     *
     */
    public static DefaultComboBoxModel createListModel(Vector<Object> vector) {
        final DefaultComboBoxModel model = new DefaultComboBoxModel();

        for (int i = 0; i < vector.size(); i++) {
            model.addElement(vector.get(i));
        }

        return model;
    }

    ////Debug CacheTest------------------------------------------------

    /**
     * Überprüft den Inhalt eines Textfields, ob der Wert aus ints mit , getrennt besteht,
     * ansonsten setzt er den Wert auf 0
     *
     */
	public static int[] generateIntArray(String text) {
		// String message = "";
		final int[] tempzahlen = new int[100];

		try {
			int index = 0;
			StringBuffer buffer = new StringBuffer();

			for (int i = 0; i < text.length(); i++) {
				if (text.charAt(i) != ',') {
					buffer.append("" + text.charAt(i));
				} else { // Komma gefunden
					// buffer ist nicht leer
					if (!buffer.toString().trim().equals("")) {
						tempzahlen[index] = Integer.parseInt(buffer.toString().trim());

						/*
						 * if ( !negativErlaubt && tempzahlen[index] < 0 ) {
						 * //message = "Keinen negativen Werte erlaubt!"; throw
						 * new NumberFormatException(); } //Groesser als
						 * Maximalwert if ( tempzahlen[index] > maxValue ) {
						 * //message = "Ein Wert ist zu hoch!"; throw new
						 * NumberFormatException(); }
						 */
						index++;
					}

					buffer = new StringBuffer();
				}
			}

			if (!buffer.toString().trim().equals("")) {
				// Es folgt am Ende kein , mehr ->
				tempzahlen[index] = Integer.parseInt(buffer.toString().trim());

				/*
				 * if ( !negativErlaubt && tempzahlen[index] < 0 ) { //message =
				 * "Keinen negativen Werte erlaubt!"; throw new
				 * NumberFormatException(); } //Groesser als Maximalwert if (
				 * tempzahlen[index] > maxValue ) { //message =
				 * "Ein Wert ist zu hoch!"; throw new NumberFormatException(); }
				 */
				index++;
			}

			// Zahlen in passenden Array kopieren
			final int[] zahlen = new int[index];

			for (int i = 0; i < index; i++) {
				zahlen[i] = tempzahlen[i];
			}

			return zahlen;
		} catch (NumberFormatException nfe) {
			/*
			 * if (message.equals("") ) { message =
			 * "Eine Eingabe ist keine Zahl!"; } showMessage( parent, message,
			 * "Fehler", javax.swing.JOptionPane.ERROR_MESSAGE);
			 */
			return null;
		}
    }



    /**
     * Markiert das Element mit der angegeben Id
     *
     */
    public static void markierenComboBox(javax.swing.JComboBox combobox, int id) {
        final javax.swing.ComboBoxModel model = combobox.getModel();

        for (int i = 0; i < model.getSize(); i++) {
            if (((CBItem) (model.getElementAt(i))).getId() == id) {
                combobox.setSelectedItem(model.getElementAt(i));
                break;
            }
        }
    }


    /**
     * Überprüft den Inhalt eines Textfields, ob der Wert ein int ist, ansonsten setzt er den Wert
     * auf 0
     *
     */
    public static boolean parseInt(Window parent, JTextField field, boolean negativErlaubt) {
        String message = "";

        try {
            final int temp = Integer.parseInt(field.getText());

            if (!negativErlaubt && (temp < 0)) {
                message = HOVerwaltung.instance().getLanguageString("negativVerboten");
                throw new NumberFormatException();
            }

            field.setText(String.valueOf(temp));
            return true;
        } catch (NumberFormatException nfe) {
            if (message.equals("")) {
                message = HOVerwaltung.instance().getLanguageString("keineZahl");
            }

            showMessage(parent, message,
                        HOVerwaltung.instance().getLanguageString("Fehler"), JOptionPane.ERROR_MESSAGE);

            field.setText(String.valueOf(0));
            return false;
        }
    }


    /**
     * Round to one fraction digit
     *
     */
    public static float round(float wert) {
        return Helper.round(wert, 1);
    }

    /**
     * Round a double value
     *
     */
    public static double round(double wert, int nachkommastellen) {
        //Wert mit 10^nachkommastellen multiplizieren
        final double dwert = wert * Math.pow(10.0, nachkommastellen);

        //Nachkommastellen abschneiden
//        final long lwert = Math.round(dwert);
        final double lwert = (int) dwert;

        //Wert wieder durch 10^nachkommastellen teilen und zurückgeben
        return (lwert / Math.pow(10.0, nachkommastellen));
    }

    /**
     * Round a float value
     *
     */
    public static float round(float wert, int nachkommastellen) {
        //Wert mit 10^nachkommastellen multiplizieren
        final float dwert = wert * (int)Math.pow(10.0, nachkommastellen);

        //Nachkommastellen abschneiden
//        final long lwert = Math.round(dwert);
        final float lwert = (int) dwert;

        //Wert wieder durch 10^nachkommastellen teilen und zurückgeben
        return (lwert / (int)Math.pow(10.0, nachkommastellen));
    }

    /**
     * Zeigt eine Meldung per JOptionPane an, aber immer nur eine!
     *
     */
    public static void showMessage(Component parent, String message, String titel, int typ) {
        //new gui.ShowMessageThread( parent, message, titel, typ );
        //Ignorieren, wenn schon ein Fehler angezeigt wird.
        if (!paneShown) {
            paneShown = true;
            javax.swing.JOptionPane.showMessageDialog(parent, message, titel, typ);
            paneShown = false;
        }
    }

    /*
     *sortieren eines doppelintArrays
     */
    public static int[][] sortintArray(int[][] toSort, int spaltenindex) {
        //Sicherheit!
        try {
            //Sicherheit!
            if ((toSort == null) || (toSort.length == 0) || (toSort[0].length == 0)) {
                return null;
            }

            final int[][] ergebnis = new int[toSort.length][toSort[0].length];
            final int[] sortSpalte = new int[toSort.length];

            //Spalte zum Sortieren holen
            for (int i = 0; i < toSort.length; i++) {
                sortSpalte[i] = toSort[i][spaltenindex];
            }

            //Spalte sortieren
            java.util.Arrays.sort(sortSpalte);

            //Alle Einträge durchlaufen und nach Wert im toSort suchen und den Wert dann in das Ergebnis kopieren
            for (int i = 0; i < toSort.length; i++) {
                for (int j = 0; j < toSort.length; j++) {
                    if (sortSpalte[i] == toSort[j][spaltenindex]) {
                        for (int k = 0; k < toSort[j].length; k++) {
                            ergebnis[i][k] = toSort[j][k];
                        }

                        break;
                    }
                }
            }

            //Referenz umbiegen
            // = ergebnis;
            return ergebnis;
        } catch (Exception e) {
            HOLogger.instance().log(Helper.class,"Helper.sortintArray:  " + e);
            return null;
        }
    }

    /**
	 * Returns a NumberFormat based on the parameters
	 */
	public static NumberFormat getNumberFormat(boolean currencyformat, int nachkommastellen) {
		NumberFormat numFormat;
		if (currencyformat) {
			numFormat = Helper.CURRENCYFORMAT;
		} else {
			numFormat = NumberFormat.getNumberInstance();
		}
		numFormat.setMinimumFractionDigits(nachkommastellen);
		numFormat.setMaximumFractionDigits(nachkommastellen);
		return numFormat;
	}

	/**
	 * ersetzt die Substrings in einem String
	 *
	 */
	public static String replaceSubString(String[] varFind, String[] varReplace, String text) {
	    final StringBuffer buf = new StringBuffer(text);
	    int index = 0;
	
	    for (int i = 0;
	         (varFind != null) && (varReplace != null) && (varReplace.length == varFind.length)
	         && (i < varFind.length); i++) {
	        index = buf.indexOf(varFind[i]);
	
	        //Substring ersetzen
	        while (index > -1) {
	            buf.replace(index, index + varFind[i].length(), varReplace[i]);
	            index = buf.indexOf(varFind[i]);
	        }
	    }
	
	    return buf.toString();
	}

	/**
	 * replace char
	 *
	 */
	public static String replaceChar(String text, char gesucht, char ersatz) {
	    final char[] charText = text.toCharArray();
	    final StringBuffer buffer = new StringBuffer();
	
	    for (int i = 0; (charText != null) && (i < charText.length); i++) {
	        if (charText[i] == gesucht) {
	            buffer.append("" + ersatz);
	        } else {
	            buffer.append("" + charText[i]);
	        }
	    }
	
	    return buffer.toString();
	}

	/**
	 * liefer das Datum aus einem String im Format yyyy-MM-dd HH:mm:ss
	 *
	 */
	public static Timestamp parseDate(String date) {
	    try {
	        //Hattrick
	        final SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.GERMANY);
	
	        return new Timestamp(simpleFormat.parse(date).getTime());
	    } catch (Exception e) {
	        try {
	            //Hattrick
	            final SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
	
	            return new Timestamp(simpleFormat.parse(date).getTime());
	        } catch (Exception ex) {
	            HOLogger.instance().log(Helper.class,ex);
	        }
	    }
	
	    return null;
	}

	/**
	 * Erzeugt aus einem String, der durch Komma getrennte Teilstrings enthält einen Array mit den
	 * Teilstrings.
	 *
	 */
	public static String[] generateStringArray(String werte, char trenner) {
	    if ((werte == null) || (werte.trim().equals(""))) {
	        return new String[0];
	    }
	
	    final StringBuffer buffer = new StringBuffer();
	    final char[] ccoords = werte.toCharArray();
	    int j = 0;
	
	    for (int i = 0; i < ccoords.length; i++) {
	        if (ccoords[i] == (trenner)) {
	            j++;
	        }
	    }
	
	    //+1, da es values-1 Kommas in der Zeile sind
	    final String[] sValues = new String[j + 1];
	    j = 0;
	
	    for (int i = 0; i < ccoords.length; i++) {
	        if (ccoords[i] != (trenner)) {
	            buffer.append("" + ccoords[i]);
	        } else {
	            sValues[j++] = buffer.toString().trim();
	
	            //buffer reseten, eine new StringBuffer sparen
	            buffer.delete(0, buffer.capacity());
	        }
	    }
	
	    //letzte Zahl(Koordinate) hinzufügen, da am Ende kein Komma mehr folgt
	    sValues[j] = buffer.toString().trim();
	
	    return sValues;
	}

	/**
	 * entschlüsselt einen String der zuvor mit der Crypt methode verschlüsselt wurde
	 *
	 */
	public static String decryptString(String text) {
	    byte[] encoded;
	
	    if (text == null) {
	        return "";
	    }
	
	    encoded = text.getBytes();
	
	    for (int i = 0; (encoded != null) && (i < encoded.length); ++i) {
	        //check ob Zeichen gleich ~ = 126 ?
	        if (encoded[i] == 126) {
	            //Dann mit tilde ersetzen slash = 92
	            encoded[i] = 92;
	        }
	
	        encoded[i] += 7;
	
	        if ((encoded[i] % 2) == 0) {
	            ++encoded[i];
	        } else {
	            --encoded[i];
	        }
	    }
	
	    return new String(encoded);
	}

	/**
	 * verschlüsselt einen String der nur aus Zahlen und Buchstaben besteht
	 *
	 */
	public static String cryptString(String text) {
	    byte[] encoded;
	
	    if (text == null) {
	        return "";
	    }
	
	    //prüfen ob nur zahlen eingegeben sind !!!
	    for (int j = 0; j < text.length(); j++) {
	        if (!Character.isLetterOrDigit(text.charAt(j))) {
	            return null;
	        }
	    }
	
	    encoded = text.getBytes();
	
	    for (int i = 0; (encoded != null) && (i < encoded.length); ++i) {
	        if ((encoded[i] % 2) == 0) {
	            ++encoded[i];
	        } else {
	            --encoded[i];
	        }
	
	        encoded[i] -= 7;
	
	        //check ob Zeichen gleich slash = 92 ?
	        if (encoded[i] == 92) {
	            //Dann mit tilde ersetzen ~ = 126
	            encoded[i] = 126;
	        }
	    }
	
	    return new String(encoded);
	}

	/**
	 * Kopiert einen Vector in einen Array
	 *
	 * @param src der Vektor der kopiert werden soll
	 * @param dest Array der Vector aufnehmen soll, muss bereits erstellt sein und die größe =
	 *        Vector.size() haben
	 */
	public static <T> void copyVector2Array(Vector<T> src, T[] dest) {
	    for (int i = 0;
	         (src != null) && (dest != null) && (dest.length >= src.size()) && (i < src.size());
	         i++) {
	        dest[i] = src.elementAt(i);
	    }
	}

	/**
	 * Kopiert einen Vector in einen Array
	 *
	 * @param src der Array der kopiert werden soll
	 * @param dest Vektor der den Array aufnehmen soll
	 */
	public static <T> void copyArray2Vector(T[] src, Vector<T> dest) {
	    for (int i = 0; (src != null) && (dest != null) && (i < src.length); i++) {
	        dest.addElement(src[i]);
	    }
	}
}
