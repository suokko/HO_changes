// %3118319609:de.hattrickorganizer.tools%
/*
 * MyHelper.java
 *
 * Created on 9. Oktober 2003, 09:00
 */
package de.hattrickorganizer.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class MyHelper {
	private final static SimpleDateFormat HT_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of MyHelper
     */
    public MyHelper() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param tempdate TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String Calendar2HTString(Calendar tempdate) {
//        final StringBuffer dateString = new StringBuffer();
//        dateString.append(tempdate.get(Calendar.YEAR));
//        dateString.append("-");
//
//        if ((tempdate.get(Calendar.MONTH) + 1) < 10) {
//            dateString.append("0");
//        }
//
//        dateString.append(tempdate.get(Calendar.MONTH) + 1);
//        dateString.append("-");
//
//        if (tempdate.get(Calendar.DAY_OF_MONTH) < 10) {
//            dateString.append("0");
//        }
//
//        dateString.append(tempdate.get(Calendar.DAY_OF_MONTH));
//
//        return dateString.toString();
    	return HT_FORMAT.format(tempdate.getTime());
    }

    /**
     * fügt den Array src an den Vector dest an
     *
     * @param src TODO Missing Constructuor Parameter Documentation
     * @param dest TODO Missing Constructuor Parameter Documentation
     */
    public static <T> void addArray2Vector(T[] src, Vector<T> dest) {
        for (int i = 0; (src != null) && (dest != null) && (i < src.length); i++) {
            dest.addElement(src[i]);
        }
    }

    /**
     * fügt den Vector src an den Vector dest an
     *
     * @param src TODO Missing Constructuor Parameter Documentation
     * @param dest TODO Missing Constructuor Parameter Documentation
     */
    public static <T> void addVector2Vector(Vector<T> src, Vector<T> dest) {
        for (int i = 0; (src != null) && (dest != null) && (i < src.size()); i++) {
            dest.addElement(src.elementAt(i));
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

    ////////////////////////////////////Copy Funcs

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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////77
    //Cypt Funtkionen
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////77

    /**
     * verschlüsselt einen String der nur aus Zahlen und Buchstaben besteht
     *
     * @param text TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
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
     * entschlüsselt einen String der zuvor mit der Crypt methode verschlüsselt wurde
     *
     * @param text TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
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

    /////////////////////////////String Funcs//////////////////

    /**
     * Erzeugt aus einem String, der durch Komma getrennte Teilstrings enthält einen Array mit den
     * Teilstrings.
     *
     * @param werte TODO Missing Constructuor Parameter Documentation
     * @param trenner das Trennzeichen , z.B. : ','
     *
     * @return TODO Missing Return Method Documentation
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

    //////////////////////////Date-Parser//////////////////////77

    /**
     * liefer das Datum aus einem String im Format yyyy-MM-dd HH:mm:ss
     *
     * @param date TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static java.sql.Timestamp parseDate(String date) {
        try {
            //Hattrick
            final java.text.SimpleDateFormat simpleFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                                                                           Locale.GERMANY);

            return new java.sql.Timestamp(simpleFormat.parse(date).getTime());
        } catch (Exception e) {
            try {
                //Hattrick
                final java.text.SimpleDateFormat simpleFormat = new java.text.SimpleDateFormat("yyyy-MM-dd",
                                                                                               Locale.GERMANY);

                return new java.sql.Timestamp(simpleFormat.parse(date).getTime());
            } catch (Exception ex) {
                HOLogger.instance().log(MyHelper.class,ex);
            }
        }

        return null;
    }

    /**
     * replace char
     *
     * @param text TODO Missing Constructuor Parameter Documentation
     * @param gesucht TODO Missing Constructuor Parameter Documentation
     * @param ersatz TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
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
     * ersetzt die Substrings in einem String
     *
     * @param varFind Array der zu ersetzenden Substrings
     * @param varReplace Inhalt mit dem ersetzt werden soll
     * @param text TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
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
}
