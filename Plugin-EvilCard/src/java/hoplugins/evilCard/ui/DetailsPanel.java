// %1525957469:hoplugins.evilCard.ui%
/*
 * Created on 4.11.2005
 */
package hoplugins.evilCard.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 * DOCUMENT ME!
 *
 * @author <a href="mailto:kenmooda@users.sourceforge.net">Tommi Rautava</a>
 */
public class DetailsPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -8385208467445931375L;
	private DetailsTable detailsTable = null;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Constructor
     */
    public DetailsPanel() {
        super();

        this.setLayout(new BorderLayout());

        // dimensioni colonne
        detailsTable = new DetailsTable();

//        detailsTable.getColumnModel().getColumn(DetailsTableModel.COL_MATCH_ID).setMinWidth(0);
//        detailsTable.getColumnModel().getColumn(DetailsTableModel.COL_MATCH_ID).setMaxWidth(0);
//        detailsTable.getColumnModel().getColumn(DetailsTableModel.COL_MATCH_ID).setWidth(0);

        detailsTable.refresh(0);

        this.add(new JScrollPane(detailsTable));
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param playerId TODO Missing Method Parameter Documentation
     */
    public void refresh(int playerId) {
        detailsTable.refresh(playerId);
    }
}
