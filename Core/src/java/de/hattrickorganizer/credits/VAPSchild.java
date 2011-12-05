// %2403823547:de.hattrickorganizer.credits%
package de.hattrickorganizer.credits;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class VAPSchild implements DynamischesObjekt {
    //~ Instance fields ----------------------------------------------------------------------------

    private AlphaComposite alpha;
    private Image image;
    private int x = 160;
    private int y = -200;
    private long time;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new VAPSchild object.
     *
     * @param image TODO Missing Constructuor Parameter Documentation
     * @param time TODO Missing Constructuor Parameter Documentation
     */
    public VAPSchild(Image image, long time) {
        this.image = image;
        this.time = time;
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
        if (y < 200) {
            y += (int) (time * 0.3);
        }

        //Transparenz fadeout
        if (gesamtZeit > 4500) {
            float alphawert = Math.abs(((gesamtZeit - 4500.0f) / 500.0f) - 1.0f);

            if (alphawert > 1.0f) {
                alphawert = 1.0f;
            } else if (alphawert < 0.0f) {
                alphawert = 0.0f;
            }

            alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphawert);
        } else {
            alpha = GraphicLibrary.DEFAULTALPHA;
        }

        if (gesamtZeit > 5000) {
            return false;
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
        g2d.drawImage(image, x + xx, y + yy, null);

        //        fontEffect.action ( 0, 0 );//Muß aufgerufen werden
        //        fontEffect.setAlpha ( AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaWert ) );
        //        fontEffect.render ( g2d, x + xx - 100, y + yy + 100 );
        g2d.setComposite(GraphicLibrary.DEFAULTALPHA);
    }
}
