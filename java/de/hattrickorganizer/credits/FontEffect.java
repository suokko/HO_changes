// %4199094859:de.hattrickorganizer.credits%
package de.hattrickorganizer.credits;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;


/**
 * Statischer Text
 */
public class FontEffect implements DynamischesObjekt {
    //~ Instance fields ----------------------------------------------------------------------------

    private AlphaComposite alpha;
    private Color color;
    private Font font;
    private String text;
    private int fadetime;
    private int x;
    private int y;
    private long endtime;
    private long starttime;

    //Zeitsteuerung
    private long time;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param text Der Text
     * @param font Font für den Text
     * @param color Farbe für den Text
     * @param x X-Position
     * @param y Y-Position
     * @param starttime Offsetzeit, wenn der Effekt gezeigt werden soll.
     * @param endtime Zeit, wenn der Effekt zu ende ist
     * @param fadetime Aus und Einfaden des Textes, &lt;0 = auto 10% der Anzeigelänge,  0 = aus ,
     *        &gt;0 = die Zeit in ms
     */
    public FontEffect(String text, Font font, Color color, int x, int y, long starttime,
                      long endtime, int fadetime) {
        this.text = text;
        this.font = font;
        this.color = color;
        this.x = x;
        this.y = y;

        //Zeitsteuerung
        this.time = starttime;
        this.starttime = starttime;
        this.endtime = endtime;

        if (fadetime < 0) {
            this.fadetime = (int) ((endtime - starttime) / 10);
        } else {
            this.fadetime = fadetime;
        }
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
        if ((endtime > 0) && (gesamtZeit > endtime)) {
            return false;
        }

        //Funktioniert nur wenn das Objekt direkt an Anfang erstellt wurde und der offset "starttime" auch die echte startzeit ist.
        if (gesamtZeit < (starttime + fadetime)) {
            float alphawert = (float) (gesamtZeit - starttime) / fadetime;

            if (alphawert > 1.0f) {
                alphawert = 1.0f;
            } else if (alphawert < 0.0f) {
                alphawert = 0.0f;
            }

            alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphawert);
        } else if (gesamtZeit > (endtime - fadetime)) {
            float alphawert = Math.abs(((float) (gesamtZeit - endtime + fadetime) / fadetime)
                                       - 1.0f);

            if (alphawert > 1.0f) {
                alphawert = 1.0f;
            } else if (alphawert < 0.0f) {
                alphawert = 0.0f;
            }

            alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphawert);
        } else {
            alpha = GraphicLibrary.DEFAULTALPHA;
        }

        return true;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param g TODO Missing Method Parameter Documentation
     * @param xx TODO Missing Method Parameter Documentation
     * @param yy TODO Missing Method Parameter Documentation
     */
    public final void render(java.awt.Graphics g, int xx, int yy) {
        final Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(alpha);
        g2d.setColor(color);
        g2d.setFont(font);
        g2d.drawString(text, x + xx, y + yy);
        g2d.setComposite(GraphicLibrary.DEFAULTALPHA);
    }
}
