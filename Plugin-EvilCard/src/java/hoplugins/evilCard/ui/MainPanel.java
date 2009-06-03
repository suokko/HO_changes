// %482241988:hoplugins.evilCard.ui%
/*
 * Created on 6.11.2005
 */
package hoplugins.evilCard.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;


/**
 * DOCUMENT ME!
 *
 * @author <a href="mailto:kenmooda@users.sourceforge.net">Tommi Rautava</a>
 */
public class MainPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    private DetailsPanel detailsPanel = null;
    private PlayersPanel playersPanel = null;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     *
     */
    public MainPanel() {
        super();

        this.setLayout(new BorderLayout());

        detailsPanel = new DetailsPanel();
        playersPanel = new PlayersPanel(detailsPanel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, playersPanel, detailsPanel);
        splitPane.setResizeWeight(0.5d);

        this.add(splitPane);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param filterMode TODO Missing Method Parameter Documentation
     */
    public void setFilter(int filterMode) {
        playersPanel.setFilter(filterMode);
    }
}
