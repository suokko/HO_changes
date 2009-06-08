// %37322930:de.hattrickorganizer.gui.pluginWrapper%
package de.hattrickorganizer.gui.pluginWrapper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JDialog;


/**
 * DebugWindow
 */
public class DebugWindow extends JDialog implements plugins.IDebugWindow {
	
	private static final long serialVersionUID = 4026001586092998743L;
	
    //~ Instance fields ----------------------------------------------------------------------------

	private DebugMessagePanel m_jpText = new DebugMessagePanel();

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new DebugWindow object.
     *
     * @param position TODO Missing Constructuor Parameter Documentation
     * @param size TODO Missing Constructuor Parameter Documentation
     */
    public DebugWindow(Point position, Dimension size) {
        super(de.hattrickorganizer.gui.HOMainFrame.instance(), "Debug Window");

        setLocation(position);
        setSize(size);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param throwable TODO Missing Method Parameter Documentation
     */
    public final void append(Throwable throwable) {
        final StackTraceElement[] elemente = throwable.getStackTrace();

        if ((elemente != null) && (elemente.length > 0)) {
            final String errormessage = throwable.toString() + "&nbsp;&nbsp;"
                                        + elemente[0].getClassName() + "."
                                        + elemente[0].getMethodName() + "&nbsp;&nbsp;File: "
                                        + elemente[0].getFileName() + "&nbsp;&nbsp;Line: "
                                        + elemente[0].getLineNumber();
            m_jpText.append(errormessage);

            for (int i = 0; i < elemente.length; i++) {
                m_jpText.append("&nbsp;&nbsp; " + elemente[i].toString());
            }
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param text TODO Missing Method Parameter Documentation
     */
    public final void append(String text) {
        m_jpText.append(text);
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void clear() {
        m_jpText.clear();
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(m_jpText, BorderLayout.CENTER);
    }
}
