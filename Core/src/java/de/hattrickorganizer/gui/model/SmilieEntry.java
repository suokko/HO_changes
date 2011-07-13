// %3364174802:de.hattrickorganizer.gui.model%
package de.hattrickorganizer.gui.model;

import javax.swing.SwingConstants;

import plugins.IHOTableEntry;
import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.templates.DoppelLabelEntry;
import de.hattrickorganizer.gui.theme.ThemeManager;


/**
 * Zeigt die Warnings und Verletzungen an
 */
public class SmilieEntry extends DoppelLabelEntry {
    //~ Instance fields ----------------------------------------------------------------------------

    private ColorLabelEntry manuell = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                          ColorLabelEntry.BG_STANDARD,
                                                          SwingConstants.RIGHT);
    private ColorLabelEntry team = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
                                                       ColorLabelEntry.BG_STANDARD,
                                                       SwingConstants.LEFT);
    private de.hattrickorganizer.model.Spieler spieler;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SmilieEntry object.
     */
    public SmilieEntry() {
        super();
        this.setLabels(team, manuell);
    }

   public final void setSpieler(de.hattrickorganizer.model.Spieler spieler) {
        this.spieler = spieler;
        updateComponent();
    }

    public final de.hattrickorganizer.model.Spieler getSpieler() {
        return spieler;
    }

    @Override
	public final int compareTo(IHOTableEntry obj) {
        if (obj instanceof SmilieEntry) {
            final SmilieEntry entry = (SmilieEntry) obj;

            if ((entry.getSpieler() != null) && (getSpieler() != null)) {
                int ergebnis = 0;

                //Beide null -> Der ManuelleSmilie entscheidet
                if (((entry.getSpieler().getTeamInfoSmilie() == null)
                    || entry.getSpieler().getTeamInfoSmilie().equals(""))
                    && ((getSpieler().getTeamInfoSmilie() == null)
                    || getSpieler().getTeamInfoSmilie().equals(""))) {
                    ergebnis = 0;
                } else if ((entry.getSpieler().getTeamInfoSmilie() == null)
                           || entry.getSpieler().getTeamInfoSmilie().equals("")) {
                    ergebnis = 1;
                } else if ((getSpieler().getTeamInfoSmilie() == null)
                           || getSpieler().getTeamInfoSmilie().equals("")) {
                    ergebnis = -1;
                } else {
                    ergebnis = entry.getSpieler().getTeamInfoSmilie().compareTo(getSpieler()
                                                                                    .getTeamInfoSmilie());
                }

                //Bei "Gleichstand" die Aufstellung beachten
                if (ergebnis == 0) {
                    final de.hattrickorganizer.model.SpielerPosition entrySort = de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                                        .getModel()
                                                                                                                        .getAufstellung()
                                                                                                                        .getPositionBySpielerId(entry.getSpieler()
                                                                                                                                                     .getSpielerID());
                    final de.hattrickorganizer.model.SpielerPosition sort = de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                                   .getModel()
                                                                                                                   .getAufstellung()
                                                                                                                   .getPositionBySpielerId(getSpieler()
                                                                                                                                               .getSpielerID());

                    if ((sort == null) && (entrySort == null)) {
                        ergebnis = 0;
                    } else if (sort == null) {
                        ergebnis = -1;
                    } else if (entrySort == null) {
                        ergebnis = 1;
                    } else if (sort.getSortId() > entrySort.getSortId()) {
                        ergebnis = -1;
                    } else if (sort.getSortId() < entrySort.getSortId()) {
                        ergebnis = 1;
                    } else {
                        ergebnis = 0;
                    }
                }

                return ergebnis;
            }
        }

        return 0;
    }

    @Override
	public final void updateComponent() {
        if (spieler != null) {
            if ((spieler.getTeamInfoSmilie() != null) && !spieler.getTeamInfoSmilie().equals("")) {
                team.setIcon(ThemeManager.getIcon(spieler.getTeamInfoSmilie()));
            } else {
                team.clear();
            }

            if ((spieler.getManuellerSmilie() != null) && !spieler.getManuellerSmilie().equals("")) {
                manuell.setIcon(ThemeManager.getIcon(spieler.getManuellerSmilie()));
            } else {
                manuell.clear();
            }
        } else {
            team.clear();
            manuell.clear();
        }
    }
}
