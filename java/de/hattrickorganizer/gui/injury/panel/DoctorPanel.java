// %4181403314:de.hattrickorganizer.gui.injury.panel%
package de.hattrickorganizer.gui.injury.panel;

import de.hattrickorganizer.gui.injury.InjuryDialog;
import de.hattrickorganizer.logik.InjuryCalculator;
import de.hattrickorganizer.model.HOVerwaltung;


/**
 * The Panel to calculate the number of needed doctors
 *
 * @author draghetto
 */
public class DoctorPanel extends AbstractInjuryPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    private String msg = HOVerwaltung.instance().getResource().getProperty("DoctorsNeeded");

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new DoctorPanel object.
     *
     * @param dialog the main injury dialog
     */
    public DoctorPanel(InjuryDialog dialog) {
        super(dialog);
        reset();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Action to be executed when the button is pressed Calculates the result using the parameters
     */
    public final void doAction() {
        final int updates = getInput();

        final double doctors = InjuryCalculator.getDoctorNumber(getDetail().getAge(),
                                                                getDetail().getInjury(),
                                                                getDetail().getDesiredLevel(),
                                                                updates);

        if (doctors > -1) {
            setOutputMsg(msg + ": " + formatNumber(doctors));
        }
    }

    /**
     * Reset the panel to default data
     */
    public final void reset() {
        setInputValue("");
        setInputMsg(HOVerwaltung.instance().getResource().getProperty("Updates"));
        setOutputMsg(msg);
        setHeader(HOVerwaltung.instance().getResource().getProperty("Injury1"));
    }
}
