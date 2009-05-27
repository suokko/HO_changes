// %2843598420:de.hattrickorganizer.gui.playeroverview%
package de.hattrickorganizer.gui.playeroverview;

import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.templates.DoppelLabelEntry;
import de.hattrickorganizer.tools.Helper;


/**
 * Zeigt die Warnings und Verletzungen an
 */
public class SpielerStatusLabelEntry extends DoppelLabelEntry {
    //~ Instance fields ----------------------------------------------------------------------------

    private ColorLabelEntry verletzung = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                             ColorLabelEntry.BG_STANDARD,
                                                             javax.swing.JLabel.RIGHT);
    private ColorLabelEntry verwarnungen = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                               ColorLabelEntry.BG_STANDARD,
                                                               javax.swing.JLabel.LEFT);
    private de.hattrickorganizer.model.ServerSpieler serverspieler;
    private de.hattrickorganizer.model.Spieler spieler;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SpielerStatusLabelEntry object.
     */
    public SpielerStatusLabelEntry() {
        super();
        this.setLabels(verwarnungen, verletzung);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param spieler TODO Missing Method Parameter Documentation
     */
    public final void setServerSpieler(de.hattrickorganizer.model.ServerSpieler spieler) {
        this.serverspieler = spieler;
        updateComponent();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final de.hattrickorganizer.model.ServerSpieler getServerSpieler() {
        return serverspieler;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param spieler TODO Missing Method Parameter Documentation
     */
    public final void setSpieler(de.hattrickorganizer.model.Spieler spieler) {
        this.spieler = spieler;
        updateComponent();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final de.hattrickorganizer.model.Spieler getSpieler() {
        return spieler;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param obj TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int compareTo(Object obj) {
        if (obj instanceof SpielerStatusLabelEntry) {
            final SpielerStatusLabelEntry entry = (SpielerStatusLabelEntry) obj;

            if ((entry.getSpieler() != null) && (getSpieler() != null)) {
                if (entry.getSpieler().getVerletzt() > getSpieler().getVerletzt()) {
                    return 1;
                } else if (entry.getSpieler().getVerletzt() < getSpieler().getVerletzt()) {
                    return -1;
                } else {
                    if (entry.getSpieler().getGelbeKarten() > getSpieler().getGelbeKarten()) {
                        return 1;
                    } else if (entry.getSpieler().getGelbeKarten() < getSpieler().getGelbeKarten()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            }
            //Wird eigendlich nicht benötigt, darum nur Kartentest
            else if ((entry.getServerSpieler() != null) && (getServerSpieler() != null)) {
                if (entry.getServerSpieler().getGelbeKarten() > getServerSpieler().getGelbeKarten()) {
                    return 1;
                } else if (entry.getServerSpieler().getGelbeKarten() < getServerSpieler()
                                                                           .getGelbeKarten()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }

        return 0;
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void updateComponent() {
        if (spieler != null) {
            if (spieler.isGesperrt()) {
                getLinks().setIcon(Helper.ROTEKARTE);
            } else if (spieler.getGelbeKarten() == 1) {
                getLinks().setIcon(Helper.GELBEKARTE);
            } else if (spieler.getGelbeKarten() >= 2) {
                getLinks().setIcon(Helper.DOPPELGELB);
            } else {
                getLinks().clear();
            }

            if (spieler.getVerletzt() == 0) {
                getRechts().setText("");
                getRechts().setIcon(Helper.ANGESCHLAGEN);
            } else if (spieler.getVerletzt() > 0) {
                getRechts().setText(spieler.getVerletzt() + "");
                getRechts().setIcon(Helper.VERLETZT);
            } else {
                getRechts().clear();
            }
        } else if (serverspieler != null) {
            if (serverspieler.isGesperrt()) {
                getLinks().setIcon(Helper.ROTEKARTE);
            } else if (serverspieler.isGelbVerwarnt()) {
                getLinks().setIcon(Helper.GELBEKARTE);
            } else {
                getLinks().clear();
            }
        } else {
            getLinks().clear();
            getRechts().clear();
        }

        //super.updateComponent();
    }
}
