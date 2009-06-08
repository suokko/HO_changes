// %1586804725:de.hattrickorganizer.tools%
package hoplugins.commons.tools;

/**
 * Klasse mit Hilfsmethoden, die in mehreren Dialogen/Panels benutzt werden
 */
public class Helper {

    /**
     * Runden auf eine Nachkommastelle
     */
    public static float round(double wert) {
        return Helper.round(wert, 1);
    }

    /**
     * Rundet den übergeben wert auf eine bestimmte nachkommastellen-Anzahl
     */
    public static float round(double wert, int nachkommastellen) {
        //Wert mit 10^nachkommastellen multiplizieren
        final double dwert = wert * Math.pow(10.0, nachkommastellen);

        //Nachkommastellen abschneiden
//        final long lwert = Math.round(dwert);
        final double lwert = Math.floor(dwert);

        //Wert wieder durch 10^nachkommastellen teilen und zurückgeben
        return (float) (lwert / Math.pow(10.0, nachkommastellen));
    }

}
