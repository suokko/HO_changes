// %2843598420:de.hattrickorganizer.gui.playeroverview%
package ho.module.playerOverview;

import gui.HOIconName;

import javax.swing.SwingConstants;

import plugins.IHOTableEntry;
import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.templates.DoppelLabelEntry;
import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.model.ServerSpieler;
import de.hattrickorganizer.model.Spieler;


/**
 * Zeigt die Warnings und Verletzungen an
 */
public class SpielerStatusLabelEntry extends DoppelLabelEntry {
    //~ Instance fields ----------------------------------------------------------------------------

    private ColorLabelEntry verletzung = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                             ColorLabelEntry.BG_STANDARD,
                                                             SwingConstants.RIGHT);
    private ColorLabelEntry verwarnungen = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                               ColorLabelEntry.BG_STANDARD,
                                                               SwingConstants.LEFT);
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

    public final void setServerSpieler(ServerSpieler spieler) {
        this.serverspieler = spieler;
        updateComponent();
    }

    public final ServerSpieler getServerSpieler() {
        return serverspieler;
    }

    public final void setSpieler(Spieler spieler) {
        this.spieler = spieler;
        updateComponent();
    }

    public final Spieler getSpieler() {
        return spieler;
    }

    @Override
	public final int compareTo(IHOTableEntry obj) {
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

    @Override
	public final void updateComponent() {
        if (spieler != null) {
            if (spieler.isGesperrt()) {
                getLinks().setIcon(ThemeManager.getIcon(HOIconName.REDCARD));
            } else if (spieler.getGelbeKarten() == 1) {
                getLinks().setIcon(ThemeManager.getIcon(HOIconName.YELLOWCARD));
            } else if (spieler.getGelbeKarten() >= 2) {
                getLinks().setIcon(ThemeManager.getIcon(HOIconName.TWOCARDS));
            } else {
                getLinks().clear();
            }

            if (spieler.getVerletzt() == 0) {
                getRechts().setText("");
                getRechts().setIcon(ThemeManager.getIcon(HOIconName.PATCH));
            } else if (spieler.getVerletzt() > 0) {
                getRechts().setText(spieler.getVerletzt() + "");
                getRechts().setIcon(ThemeManager.getIcon(HOIconName.INJURED));
            } else {
                getRechts().clear();
            }
        } else if (serverspieler != null) {
            if (serverspieler.isGesperrt()) {
                getLinks().setIcon(ThemeManager.getIcon(HOIconName.REDCARD));
            } else if (serverspieler.isGelbVerwarnt()) {
                getLinks().setIcon(ThemeManager.getIcon(HOIconName.YELLOWCARD));
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
