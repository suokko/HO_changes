// %4077674042:de.hattrickorganizer.credits%
package de.hattrickorganizer.credits;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class StaticEffectLayer {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public int x;

    /** TODO Missing Parameter Documentation */
    public int y;
    private Component component;
    private java.util.Vector elementVector = new java.util.Vector();
    private java.util.Vector statischeObjekte = new java.util.Vector();
    private boolean background;
    private float movex = 2f;
    private float movey = 2f;
    private int height;
    private int width;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new StaticEffectLayer object.
     *
     * @param component TODO Missing Constructuor Parameter Documentation
     * @param background TODO Missing Constructuor Parameter Documentation
     */
    public StaticEffectLayer(Component component, boolean background) {
        this.component = component;
        this.background = background;

        width = 600;
        height = 3000;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param goUp TODO Missing Method Parameter Documentation
     * @param goRight TODO Missing Method Parameter Documentation
     */
    public final void action(int goUp, int goRight) {
        y += (goUp * movey);
        x += (goRight * movex);

        if (x >= width) {
            x = width;
        } else if (x <= 0) {
            x = 0;
        }

        if (y >= height) {
            y = height;
        } else if (y <= 0) {
            y = 0;
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param objekt TODO Missing Method Parameter Documentation
     */
    public final void addStatischenEffekt(StatischesObjekt objekt) {
        elementVector.add(objekt);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param g TODO Missing Method Parameter Documentation
     */
    public final void render(Graphics g) {
        if (background) {
            g.setColor(Color.black);
            g.fillRect(0, 0, width, height);
        }

        java.util.Enumeration enumi = elementVector.elements();

        while (enumi.hasMoreElements()) {
            final StatischesObjekt sO = (StatischesObjekt) (enumi.nextElement());
            sO.render(g, x, y);
        }

        enumi = statischeObjekte.elements();

        while (enumi.hasMoreElements()) {
            final StatischesObjekt sO = (StatischesObjekt) (enumi.nextElement());
            sO.render(g, x, y);
        }
    }

}
