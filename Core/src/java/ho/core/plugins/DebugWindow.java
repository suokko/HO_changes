// %37322930:de.hattrickorganizer.gui.pluginWrapper%
package ho.core.plugins;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JDialog;

import de.hattrickorganizer.gui.HOMainFrame;


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
        super(HOMainFrame.instance(), "Debug Window");

        setLocation(position);
        setSize(size);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
    }

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

    public final void append(String text) {
        m_jpText.append(text);
    }

    public final void clear() {
        m_jpText.clear();
    }

    private void initComponents() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(m_jpText, BorderLayout.CENTER);
    }
}