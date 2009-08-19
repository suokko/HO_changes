// %2642300606:hoplugins.commons.ui%
package hoplugins.commons.ui;

import hoplugins.Commons;

import hoplugins.commons.utils.Debug;

import plugins.IDebugWindow;

import java.awt.Dimension;
import java.awt.Point;


/**
 * TODO Debug window helper class
 *
 * @author kenmooda
 */
public class DebugWindow {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static IDebugWindow debugWindow;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Private default constructor.
     */
    private DebugWindow() {
        // Empty.
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Sets info text on HO panel.
     *
     * @param text Info text
     */
    public static synchronized void setInfoText(String text) {
        Commons.getModel().getGUI().getInfoPanel().setLangInfoText(text);
    }

    /**
     * Appends the stack trace of the given exception to the debug window. The debug window is set
     * visible, if it is hidden or it has been closed.
     *
     * @param e Exception
     */
    public static synchronized void debug(Exception e) {
        getDebugWindow().append(e);
        getDebugWindow().setVisible(true);
        Debug.logException(e);
    }

    /**
     * Appends the given string to the debug window. The debug window is set visible, if it is
     * hidden or it has been closed.
     *
     * @param s String
     */
    public static synchronized void debug(String s) {
        getDebugWindow().append(s);
        getDebugWindow().setVisible(true);
        Debug.log(s);
    }

    /**
     * Get the debug window instance.
     *
     * @return IDebugWindow
     */
    private static synchronized IDebugWindow getDebugWindow() {
        if (debugWindow == null) {
            debugWindow = Commons.getModel().getGUI().createDebugWindow(new Point(100, 200),
                                                                        new Dimension(700, 400));
        }

        return debugWindow;
    }
}
