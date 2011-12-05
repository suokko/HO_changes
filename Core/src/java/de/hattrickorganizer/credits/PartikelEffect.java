// %3859595549:de.hattrickorganizer.credits%
package de.hattrickorganizer.credits;

import java.awt.Graphics;
import java.awt.Graphics2D;


/**
 * Erzeugt einen PartikelEffekt für Magische Ereignisse
 *
 * @author Volker Fischer
 * @version 0.2a 09.10.01
 */
public class PartikelEffect implements DynamischesObjekt {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    protected java.awt.Color farbe = new java.awt.Color(255, 255, 255);

    /** TODO Missing Parameter Documentation */
    protected int ausdehnungX = 50;

    /** TODO Missing Parameter Documentation */
    protected int ausdehnungY = 50;

    /** TODO Missing Parameter Documentation */
    protected int bogenStaerke = 4;

    /** TODO Missing Parameter Documentation */
    protected int dauer = 500;

    /** TODO Missing Parameter Documentation */
    protected int farbAbweichung = 20;

    /** TODO Missing Parameter Documentation */
    protected int partikelAnzahl = 20;

    /** TODO Missing Parameter Documentation */
    protected int zentrumX;

    /** TODO Missing Parameter Documentation */
    protected int zentrumY;

    /** TODO Missing Parameter Documentation */

    //Zeitsteuerung
    protected long time;
    private Partikel[] partikel;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new PartikelEffect object.
     *
     * @param partikelAnzahl TODO Missing Constructuor Parameter Documentation
     * @param zentrumX TODO Missing Constructuor Parameter Documentation
     * @param zentrumY TODO Missing Constructuor Parameter Documentation
     * @param ausdehnungX TODO Missing Constructuor Parameter Documentation
     * @param ausdehnungY TODO Missing Constructuor Parameter Documentation
     * @param dauer TODO Missing Constructuor Parameter Documentation
     * @param bogenStaerke TODO Missing Constructuor Parameter Documentation
     * @param farbAbweichung TODO Missing Constructuor Parameter Documentation
     * @param farbe TODO Missing Constructuor Parameter Documentation
     * @param time TODO Missing Constructuor Parameter Documentation
     */
    public PartikelEffect(int partikelAnzahl, int zentrumX, int zentrumY, int ausdehnungX,
                          int ausdehnungY, int dauer, int bogenStaerke, int farbAbweichung,
                          java.awt.Color farbe, long time) {
        this.partikelAnzahl = partikelAnzahl;
        this.zentrumX = zentrumX;
        this.zentrumY = zentrumY;
        this.ausdehnungX = ausdehnungX;
        this.ausdehnungY = ausdehnungY;
        this.dauer = dauer;
        this.bogenStaerke = bogenStaerke;
        this.farbAbweichung = farbAbweichung;
        this.farbe = farbe;
        this.time = time;
        init();
    }

    /**
     * Creates a new PartikelEffect object.
     */
    public PartikelEffect() {
        init();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param time TODO Missing Method Parameter Documentation
     */
    public final void setTime(long time) {
        this.time = time;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final long getTime() {
        return time;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param time TODO Missing Method Parameter Documentation
     * @param gesamtZeit TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean action(int time, long gesamtZeit) {
        for (int i = 0; i < partikel.length; i++) {
            if (partikel[i].lebensdauer <= dauer) {
                partikel[i].move(time);
            }
        }

        dauer -= time;

        if (dauer < 1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void init() {
        partikel = new Partikel[partikelAnzahl];

        for (int i = 0; i < partikelAnzahl; i++) {
            partikel[i] = new Partikel(this);
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param g TODO Missing Method Parameter Documentation
     * @param xx TODO Missing Method Parameter Documentation
     * @param yy TODO Missing Method Parameter Documentation
     */
    public final void render(Graphics g, int xx, int yy) {
        final Graphics2D g2D = (Graphics2D) (g);
        final int faktor = 2;

        for (int i = 0; i < partikel.length; i++) {
            if (partikel[i].lebensdauer <= dauer) {
                g2D.setComposite(GraphicLibrary.DEFAULTALPHA);
                g2D.setColor(partikel[i].farbe);
                g2D.drawRect(partikel[i].positionX + xx, partikel[i].positionY + yy, 1, faktor - 1);
            }
        }
    }
}


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
class Partikel {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public java.awt.Color farbe;

    /** TODO Missing Parameter Documentation */
    public int lebensdauer;

    /** TODO Missing Parameter Documentation */
    public int positionX;

    /** TODO Missing Parameter Documentation */
    public int positionY;

    /** TODO Missing Parameter Documentation */
    public int zielX;

    /** TODO Missing Parameter Documentation */
    public int zielY;
    private float _positionX;
    private float _positionY;
    private float bogenStaerke;
    private int aktuell;
    private int dauer;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new Partikel object.
     *
     * @param partikelEffekt TODO Missing Constructuor Parameter Documentation
     */
    public Partikel(PartikelEffect partikelEffekt) {
        this.bogenStaerke = partikelEffekt.bogenStaerke / 10;

        double x;
        double y;

        do {
            x = ((2 * Math.random()) - 1);
            y = ((2 * Math.random()) - 1);
        } //Damit die Partikel eine Mindeststecke, aber nicht ganz in die Ecken fliegen
         while ((((x * x) + (y * y)) < 0.5) || (((x * x) + (y * y)) > 1.5));

        zielX = (int) (x * partikelEffekt.ausdehnungX) + partikelEffekt.zentrumX;
        zielY = (int) (y * partikelEffekt.ausdehnungY) + partikelEffekt.zentrumY;

        int red = partikelEffekt.farbe.getRed()
                  + (int) (((2 * Math.random()) - 1) * partikelEffekt.farbAbweichung);

        if (red < 0) {
            red = 0;
        }

        if (red > 255) {
            red = 255;
        }

        int green = partikelEffekt.farbe.getGreen()
                    + (int) (((2 * Math.random()) - 1) * partikelEffekt.farbAbweichung);

        if (green < 0) {
            green = 0;
        }

        if (green > 255) {
            green = 255;
        }

        int blue = partikelEffekt.farbe.getBlue()
                   + (int) (((2 * Math.random()) - 1) * partikelEffekt.farbAbweichung);

        if (blue < 0) {
            blue = 0;
        }

        if (blue > 255) {
            blue = 255;
        }

        farbe = new java.awt.Color(red, green, blue);

        //partikelEffekt.dauer*0.7 +  
        lebensdauer = (int) (partikelEffekt.dauer * (0.3 * ((2 * Math.random()) - 1)));
        dauer = partikelEffekt.dauer - lebensdauer;

        _positionX = positionX = partikelEffekt.zentrumX;
        _positionY = positionY = partikelEffekt.zentrumY;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param time TODO Missing Method Parameter Documentation
     */
    public void move(int time) {
        aktuell += time;

        int faktor = dauer - aktuell;

        if (faktor < 1) {
            faktor = 1;
        }

        positionX = (int) (zielX
                    - ((zielX - _positionX) * ((float) faktor / (float) dauer)));
        positionY = (int) (zielY
                    - ((zielY - _positionY) * ((float) faktor / (float) dauer)));

        //positionX += ( ((float)zielX - _positionX) / (dauer) ) ;
        //positionY += ( ((float)zielY - _positionY) / (dauer) ) ;
        if ((dauer / 4) > aktuell) {
            positionY -= (bogenStaerke * 2);
        } else if ((dauer / 2) > aktuell) {
            positionY -= bogenStaerke;
        } else if (((dauer * 3) / 4) > aktuell) {
            positionY += bogenStaerke;
        } else {
            positionY += (bogenStaerke * 2);
        }
    }
}
