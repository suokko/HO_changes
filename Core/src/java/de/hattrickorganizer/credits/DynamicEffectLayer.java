// %4210040167:de.hattrickorganizer.credits%
package de.hattrickorganizer.credits;

import java.awt.Component;
import java.awt.Graphics;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class DynamicEffectLayer {
    //~ Instance fields ----------------------------------------------------------------------------

    private Component component;
    private StaticEffectLayer background;
    private java.util.Vector dynamischeObjekte = new java.util.Vector();
    private Zeitsteuerung zeitsteuerung;
    private int time;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new DynamicEffectLayer object.
     *
     * @param component TODO Missing Constructuor Parameter Documentation
     * @param background TODO Missing Constructuor Parameter Documentation
     */
    public DynamicEffectLayer(Component component, StaticEffectLayer background) {
        this.component = component;
        this.background = background;
        zeitsteuerung = new Zeitsteuerung(this);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param time TODO Missing Method Parameter Documentation
     * @param gesamtZeit TODO Missing Method Parameter Documentation
     */
    public final void action(int time, long gesamtZeit) {
        this.zeitsteuerung.action(time);

        this.time = time;

        final java.util.Enumeration enumi = dynamischeObjekte.elements();

        while (enumi.hasMoreElements()) {
            final DynamischesObjekt sO = (DynamischesObjekt) (enumi.nextElement());

            if (!sO.action(time, gesamtZeit)) {
                dynamischeObjekte.remove(sO);
            }
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseX TODO Missing Method Parameter Documentation
     * @param mouseY TODO Missing Method Parameter Documentation
     */
    public final void addDynamischenEffekt(int mouseX, int mouseY) {
        //DynamischesObjekt dO	= new ExplosionsEffekt( mouseX+background.x, mouseY+background.y, 1000, explosion, -75, -75);
        final DynamischesObjekt dO = new PartikelEffect(80, mouseX + background.x,
                                                        mouseY + background.y, 100, 100, 500, 2,
                                                        120, new java.awt.Color(255, 255, 255), 0);
        dynamischeObjekte.add(dO);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param dO TODO Missing Method Parameter Documentation
     */
    public final void addDynamischenEffekt(DynamischesObjekt dO) {
        dynamischeObjekte.add(dO);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param mouseX TODO Missing Method Parameter Documentation
     * @param mouseY TODO Missing Method Parameter Documentation
     */
    public final void addFirestorm(int mouseX, int mouseY) {
        addDynamischenEffekt(mouseX + (int) ((Math.random() - 0.5) * 150),
                             mouseY + (int) ((Math.random() - 0.5) * 150));
        addDynamischenEffekt(mouseX + (int) ((Math.random() - 0.5) * 150),
                             mouseY + (int) ((Math.random() - 0.5) * 150));
        addDynamischenEffekt(mouseX + (int) ((Math.random() - 0.5) * 150),
                             mouseY + (int) ((Math.random() - 0.5) * 150));
        addDynamischenEffekt(mouseX + (int) ((Math.random() - 0.5) * 150),
                             mouseY + (int) ((Math.random() - 0.5) * 150));

        mouseX += background.x;
        mouseY += background.y;

        zeitsteuerung.addDynamischesObjekt(new PartikelEffect(80,
                                                              mouseX
                                                              + (int) ((Math.random() - 0.5) * 200),
                                                              mouseY
                                                              + (int) ((Math.random() - 0.5) * 200),
                                                              200, 200, 700, 3, 120,
                                                              new java.awt.Color(255, 255, 255), 100));
        zeitsteuerung.addDynamischesObjekt(new PartikelEffect(80,
                                                              mouseX
                                                              + (int) ((Math.random() - 0.5) * 200),
                                                              mouseY
                                                              + (int) ((Math.random() - 0.5) * 200),
                                                              200, 200, 700, 3, 120,
                                                              new java.awt.Color(255, 255, 255), 100));
        zeitsteuerung.addDynamischesObjekt(new PartikelEffect(80,
                                                              mouseX
                                                              + (int) ((Math.random() - 0.5) * 200),
                                                              mouseY
                                                              + (int) ((Math.random() - 0.5) * 200),
                                                              200, 200, 700, 3, 120,
                                                              new java.awt.Color(255, 255, 255), 150));
        zeitsteuerung.addDynamischesObjekt(new PartikelEffect(80,
                                                              mouseX
                                                              + (int) ((Math.random() - 0.5) * 200),
                                                              mouseY
                                                              + (int) ((Math.random() - 0.5) * 200),
                                                              200, 200, 700, 3, 120,
                                                              new java.awt.Color(255, 255, 255), 150));
        zeitsteuerung.addDynamischesObjekt(new PartikelEffect(80,
                                                              mouseX
                                                              + (int) ((Math.random() - 0.5) * 250),
                                                              mouseY
                                                              + (int) ((Math.random() - 0.5) * 250),
                                                              200, 200, 700, 3, 120,
                                                              new java.awt.Color(255, 255, 255), 200));
        zeitsteuerung.addDynamischesObjekt(new PartikelEffect(80,
                                                              mouseX
                                                              + (int) ((Math.random() - 0.5) * 250),
                                                              mouseY
                                                              + (int) ((Math.random() - 0.5) * 250),
                                                              200, 200, 700, 3, 120,
                                                              new java.awt.Color(255, 255, 255), 200));
        zeitsteuerung.addDynamischesObjekt(new PartikelEffect(80,
                                                              mouseX
                                                              + (int) ((Math.random() - 0.5) * 250),
                                                              mouseY
                                                              + (int) ((Math.random() - 0.5) * 250),
                                                              200, 200, 700, 3, 120,
                                                              new java.awt.Color(255, 255, 255), 250));
        zeitsteuerung.addDynamischesObjekt(new PartikelEffect(80,
                                                              mouseX
                                                              + (int) ((Math.random() - 0.5) * 250),
                                                              mouseY
                                                              + (int) ((Math.random() - 0.5) * 250),
                                                              200, 200, 700, 3, 120,
                                                              new java.awt.Color(255, 255, 255), 250));

        zeitsteuerung.addDynamischesObjekt(new PartikelEffect(50,
                                                              mouseX
                                                              + (int) ((Math.random() - 0.5) * 300),
                                                              mouseY
                                                              + (int) ((Math.random() - 0.5) * 300),
                                                              150, 150, 500, 2, 120,
                                                              new java.awt.Color(255, 255, 255), 300));
        zeitsteuerung.addDynamischesObjekt(new PartikelEffect(50,
                                                              mouseX
                                                              + (int) ((Math.random() - 0.5) * 300),
                                                              mouseY
                                                              + (int) ((Math.random() - 0.5) * 300),
                                                              150, 150, 500, 2, 120,
                                                              new java.awt.Color(255, 255, 255), 300));
        zeitsteuerung.addDynamischesObjekt(new PartikelEffect(50,
                                                              mouseX
                                                              + (int) ((Math.random() - 0.5) * 300),
                                                              mouseY
                                                              + (int) ((Math.random() - 0.5) * 300),
                                                              150, 150, 500, 2, 120,
                                                              new java.awt.Color(255, 255, 255), 350));
        zeitsteuerung.addDynamischesObjekt(new PartikelEffect(50,
                                                              mouseX
                                                              + (int) ((Math.random() - 0.5) * 300),
                                                              mouseY
                                                              + (int) ((Math.random() - 0.5) * 300),
                                                              150, 150, 500, 2, 120,
                                                              new java.awt.Color(255, 255, 255), 350));
        zeitsteuerung.addDynamischesObjekt(new PartikelEffect(50,
                                                              mouseX
                                                              + (int) ((Math.random() - 0.5) * 400),
                                                              mouseY
                                                              + (int) ((Math.random() - 0.5) * 400),
                                                              100, 100, 500, 2, 120,
                                                              new java.awt.Color(255, 255, 255), 400));
        zeitsteuerung.addDynamischesObjekt(new PartikelEffect(50,
                                                              mouseX
                                                              + (int) ((Math.random() - 0.5) * 400),
                                                              mouseY
                                                              + (int) ((Math.random() - 0.5) * 400),
                                                              100, 100, 500, 2, 120,
                                                              new java.awt.Color(255, 255, 255), 400));
        zeitsteuerung.addDynamischesObjekt(new PartikelEffect(50,
                                                              mouseX
                                                              + (int) ((Math.random() - 0.5) * 400),
                                                              mouseY
                                                              + (int) ((Math.random() - 0.5) * 400),
                                                              100, 100, 500, 2, 120,
                                                              new java.awt.Color(255, 255, 255), 450));
        zeitsteuerung.addDynamischesObjekt(new PartikelEffect(50,
                                                              mouseX
                                                              + (int) ((Math.random() - 0.5) * 400),
                                                              mouseY
                                                              + (int) ((Math.random() - 0.5) * 400),
                                                              100, 100, 500, 2, 120,
                                                              new java.awt.Color(255, 255, 255), 450));
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param dO TODO Missing Method Parameter Documentation
     */
    public final void addZeitverzoegertDynamischenEffekt(DynamischesObjekt dO) {
        zeitsteuerung.addDynamischesObjekt(dO);
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void removeAll() {
        dynamischeObjekte.removeAllElements();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param g TODO Missing Method Parameter Documentation
     */
    public final void render(Graphics g) {
        final java.util.Enumeration enumi = dynamischeObjekte.elements();

        while (enumi.hasMoreElements()) {
            final DynamischesObjekt sO = (DynamischesObjekt) (enumi.nextElement());
            sO.render(g, background.x, background.y);
        }
    }

}
