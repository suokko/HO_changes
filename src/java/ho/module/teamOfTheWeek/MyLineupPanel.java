// %71932679:hoplugins.toTW%
/*
 * Created on 17-dic-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ho.module.teamOfTheWeek;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import plugins.LineupPanel;
import de.hattrickorganizer.gui.templates.RasenPanel;


/**
 * DOCUMENT ME!
 *
 * @author Mirtillo To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MyLineupPanel extends JPanel {

	private static final long serialVersionUID = 5900818563415056137L;

    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    LineupPanel p = null;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new MyLineupPanel object.
     *
     * @param arg0 TODO Missing Constructuor Parameter Documentation
     */
    public MyLineupPanel(boolean arg0) {
        super();
        setOpaque(false);
        setLayout(new BorderLayout());

        JPanel pan = new RasenPanel();
        pan.setLayout(new BorderLayout());
        p = new LineupPanel(arg0);
        p.setOpaque(false);
        pan.add(p, BorderLayout.CENTER);
        add(pan, BorderLayout.CENTER);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public LineupPanel getLineup() {
        return p;
    }
}
