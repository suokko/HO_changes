// %1117737442437:hoplugins.seriesstats.ui%
/*
 * Created on 2-giu-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package hoplugins.seriesstats.ui;

import hoplugins.Commons;

import hoplugins.seriesstats.MiniPanel;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;


/**
 * DOCUMENT ME!
 *
 * @author Mirtillo To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BottomPanel extends JTabbedPane {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 3872059255348104027L;
	private MiniPanel GeneralAverage;
    private MiniPanel GeneralMax;
    private MiniPanel GeneralMin;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new BottomPanel object.
     */
    public BottomPanel() {
        this.GeneralAverage = new MiniPanel(Commons.getModel());
        this.GeneralMax = new MiniPanel(Commons.getModel());
        this.GeneralMin = new MiniPanel(Commons.getModel());

        addTab(Commons.getModel().getLanguageString("Durchschnitt"),
               new JScrollPane(this.GeneralAverage.getPanel()));
        addTab(Commons.getModel().getLanguageString("Maximal"),
               new JScrollPane(this.GeneralMax.getPanel()));
        addTab(Commons.getModel().getLanguageString("Minimal"),
               new JScrollPane(this.GeneralMin.getPanel()));
    }
}
