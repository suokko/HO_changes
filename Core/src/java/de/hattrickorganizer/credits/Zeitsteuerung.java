// %1413161871:de.hattrickorganizer.credits%
package de.hattrickorganizer.credits;

import java.util.Enumeration;
import java.util.Vector;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
class Zeitsteuerung {
    //~ Instance fields ----------------------------------------------------------------------------

    private DynamicEffectLayer effectLayer;
    private Vector<DynamischesObjekt> dynamischeObjekte = new Vector<DynamischesObjekt>(100);

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new Zeitsteuerung object.
     *
     * @param effectLayer TODO Missing Constructuor Parameter Documentation
     */
    public Zeitsteuerung(DynamicEffectLayer effectLayer) {
        this.effectLayer = effectLayer;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param time TODO Missing Method Parameter Documentation
     */
    public void action(long time) {
        final Enumeration<DynamischesObjekt> enumi = dynamischeObjekte.elements();

        while (enumi.hasMoreElements()) {
            final DynamischesObjekt dO = (DynamischesObjekt) (enumi.nextElement());
            dO.setTime(dO.getTime() - time);

            if (dO.getTime() <= 0) {
                effectLayer.addDynamischenEffekt(dO);
                dynamischeObjekte.remove(dO);
            }
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param dO TODO Missing Method Parameter Documentation
     */
    public void addDynamischesObjekt(DynamischesObjekt dO) {
        dynamischeObjekte.add(dO);
    }
}
