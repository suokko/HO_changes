// %2389920460:hoplugins.evilCard.ui%
/*
 * Created on 4.11.2005
 */
package hoplugins.evilCard.ui;

import hoplugins.evilCard.ui.model.PlayersTableModel;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 * DOCUMENT ME!
 *
 * @author <a href="mailto:kenmooda@users.sourceforge.net">Tommi Rautava </a>
 */
public class PlayersPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 5173473921072367115L;
	private DetailsPanel detailsPanel = null;
    private PlayersTable playersTable = null;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new PlayersPanel object.
     *
     * @param detailsPanel TODO Missing Constructuor Parameter Documentation
     */
    public PlayersPanel(DetailsPanel detailsPanel) {
        super();

        this.detailsPanel = detailsPanel;

        this.setLayout(new BorderLayout());

        playersTable = new PlayersTable();

        this.add(new JScrollPane(playersTable));

        playersTable.getSelectionModel().addListSelectionListener(new PlayerSelectionListener());

        // sorter.setSortingStatus(4, TableSorter.ASCENDING);
        playersTable.getColumnModel().getColumn(PlayersTableModel.COL_ID).setMinWidth(0);
        playersTable.getColumnModel().getColumn(PlayersTableModel.COL_ID).setMaxWidth(0);
        playersTable.getColumnModel().getColumn(PlayersTableModel.COL_ID).setWidth(0);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param filterMode TODO Missing Method Parameter Documentation
     */
    public void setFilter(int filterMode) {
        playersTable.setFilter(filterMode);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected int getSelectedPlayer() {
        int row = this.playersTable.getSelectedRow();

        if (row >= 0) {
            Integer playerId = (Integer) playersTable.getValueAt(row, PlayersTableModel.COL_ID);

            return playerId.intValue();
        }

        return 0;
    }

    //~ Inner Classes ------------------------------------------------------------------------------

    /**
     * TODO Missing Class Documentation
     *
     * @author TODO Author Name
     */
    class PlayerSelectionListener implements ListSelectionListener {
        //~ Methods --------------------------------------------------------------------------------

        /**
         * TODO Missing Method Documentation
         *
         * @param e TODO Missing Method Parameter Documentation
         */
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                detailsPanel.refresh(getSelectedPlayer());
            }
        }
    }
}
