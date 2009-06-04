// %1127326826759:plugins%
package plugins;

/**
 * This Window shows Debuginformations for the plugindeveloper, because System.out can not be used.
 * The Window can manage HTML Code, so use it to format your Output and do not use \n or \t.
 */
public interface IDebugWindow {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Set the Location of the Window
     *
     * @param location TODO Missing Constructuor Parameter Documentation
     */
    public void setLocation(java.awt.Point location);

    /**
     * Set the Size of the Window
     *
     * @param size TODO Missing Constructuor Parameter Documentation
     */
    public void setSize(java.awt.Dimension size);

    //--------------------------------------------------------------------------

    /**
     * Set the Window visible or hide it
     *
     * @param show TODO Missing Constructuor Parameter Documentation
     */
    public void setVisible(boolean show);

    /**
     * Add a line to the Output of the Window
     *
     * @param text TODO Missing Constructuor Parameter Documentation
     */
    public void append(String text);

    /**
     * Add an exception to the Output of the Window
     *
     * @param throwable TODO Missing Constructuor Parameter Documentation
     */
    public void append(Throwable throwable);

    /**
     * Clear the Output of the Window
     */
    public void clear();
}
