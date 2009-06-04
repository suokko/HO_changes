// %776873292:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import java.util.Vector;


/**
 * Definiert eine Trainersequenz
 */
public class TrainerSequenz {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public static int DURCHLAEUFE_ENDLOS = -1;

    //~ Instance fields ----------------------------------------------------------------------------

    private Vector m_vImageSequenzItems;
    private int m_iAktuellerDurchlauf = 1;

    //Damit das ++ auf 0 bringt
    private int m_iAktuellesImage = -1;
    private int m_iDurchlaeufe;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TrainerSequenz object.
     *
     * @param imageSequenzItems TODO Missing Constructuor Parameter Documentation
     * @param durchlaeufe TODO Missing Constructuor Parameter Documentation
     */
    public TrainerSequenz(Vector imageSequenzItems, int durchlaeufe) {
        m_vImageSequenzItems = imageSequenzItems;
        m_iDurchlaeufe = durchlaeufe;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Gibt den aktuellen Durchlauf zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getAktuellenDurchlauf() {
        return m_iAktuellerDurchlauf;
    }

    /**
     * Gibt den Index des aktuellen ImageSequenzItems zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getAktuellerImageIndex() {
        return m_iAktuellesImage;
    }

    /**
     * Gibt das nächste ImageSequenzItem in der Reihe zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public final ImageSequenzItem getNextImageSequenzItem() {
        //Noch Bilder in der Reihenfolge vorhanden?
        if (m_vImageSequenzItems.size() > (m_iAktuellesImage + 1)) {
            m_iAktuellesImage++;
        }
        //Sonst wieder von vorne Anfangen und Aktuellen Durchlauf Counter erhöhen
        else {
            m_iAktuellerDurchlauf++;
            m_iAktuellesImage = 0;
        }

        return (ImageSequenzItem) m_vImageSequenzItems.get(m_iAktuellesImage);
    }

    /**
     * Dupliziert die Sequenz, damit nicht mehrere TrainerPanels auf eine Sequenz zurückgreifen
     *
     * @return TODO Missing Return Method Documentation
     */
    public final TrainerSequenz duplicate() {
        return new TrainerSequenz(this.m_vImageSequenzItems, this.m_iDurchlaeufe);
    }

    /**
     * Noch weitere Sequenzitems vorgesehen?
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean hasMoreImageSequenzItems() {
        //Unendlich viele
        if ((m_iDurchlaeufe == -1)
            || (m_iAktuellerDurchlauf < m_iDurchlaeufe)
            || ((m_iAktuellerDurchlauf == m_iDurchlaeufe)
            && (m_iAktuellesImage < (m_vImageSequenzItems.size() - 1)))) {
            return true;
        }

        return false;
    }

    /**
     * Setzt die Counter für das Aktuelle Bild und den Aktuellen Durchlauf zurück
     */
    public final void resetCounters() {
        m_iAktuellesImage = -1;
        m_iAktuellerDurchlauf = 1;
    }
}
