// %3237567999:de.hattrickorganizer.gui.injury.panel%
package de.hattrickorganizer.gui.injury.panel;

import de.hattrickorganizer.gui.injury.InjuryDialog;
import de.hattrickorganizer.logik.InjuryCalculator;
import de.hattrickorganizer.model.HOVerwaltung;


/**
 * The Panel to calculate the exact number of needed updates
 *
 * @author draghetto
 */
public class UpdateTSIPanel extends AbstractInjuryPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    private String msg = HOVerwaltung.instance().getLanguageString("UpdatesNeeded");

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new UpdateTSIPanel object.
     *
     * @param dialog the main injury dialog
     */
    public UpdateTSIPanel(InjuryDialog dialog) {
        super(dialog);
        reset();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Action to be executed when the button is pressed Calculates the result using the parameters
     */
    public final void doAction() {
        final int tsi = getInput();

        final double updates = InjuryCalculator.getUpdateTSINumber(getDetail().getTSIPre(),
                                                                   getDetail().getTSIPost(),
                                                                   getDetail().getDesiredLevel(),
                                                                   tsi);

        if (updates > -1) {
            setOutputMsg(msg + ": " + formatNumber(updates));
        }
    }

    /**
     * Reset the panel to default data
     */
    public final void reset() {
        setInputMsg(HOVerwaltung.instance().getLanguageString("Injury4"));
        setOutputMsg(msg);
        setHeader(HOVerwaltung.instance().getLanguageString("Injury3"));
        setInputValue(HOVerwaltung.instance().getModel().getVerein().getAerzte() + "");
    }
}
