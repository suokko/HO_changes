// %1196097746:hoplugins.teamplanner.ui.controller.input%
package hoplugins.teamplanner.ui.controller.input;

/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public interface CallableListener {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** Missing Parameter Documentation */
    public static final int SELECTION = 0;

    /** Missing Parameter Documentation */
    public static final int CANCEL = 1;

    /** Missing Parameter Documentation */
    public static final int CONFIRMATION = 2;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param cell Missing Constructuor Parameter Documentation
     */
    public abstract void doTriggerEvent(int code);
}
