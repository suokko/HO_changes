// %1166674044:de.hattrickorganizer.gui.injury.panel%
package de.hattrickorganizer.gui.injury.panel;

import de.hattrickorganizer.gui.injury.InjuryDialog;
import de.hattrickorganizer.logik.InjuryCalculator;
import de.hattrickorganizer.model.HOVerwaltung;


/**
 * The Panel to calculate the number of needed updates
 *
 * @author draghetto
 */
public class UpdatePanel extends AbstractInjuryPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    private String msg = HOVerwaltung.instance().getResource().getProperty("UpdatesNeeded");

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new UpdatePanel object.
     *
     * @param dialog the main injury dialog
     */
    public UpdatePanel(InjuryDialog dialog) {
        super(dialog);
        reset();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Action to be executed when the button is pressed Calculates the result using the parameters
     */
    public final void doAction() {
        final int doctors = getInput();

        final double updates = InjuryCalculator.getUpdateNumber(getDetail().getAge(),
                                                                getDetail().getInjury(),
                                                                getDetail().getDesiredLevel(),
                                                                doctors);

        if (updates > -1) {
            setOutputMsg(msg + ": " + formatNumber(updates));
        }
    }

    /**
     * Reset the panel to default data
     */
    public final void reset() {
        setInputMsg(HOVerwaltung.instance().getResource().getProperty("Doctors"));
        setOutputMsg(msg);
        setHeader(HOVerwaltung.instance().getResource().getProperty("Injury2"));
        setInputValue(HOVerwaltung.instance().getModel().getVerein().getAerzte() + "");
    }
}
