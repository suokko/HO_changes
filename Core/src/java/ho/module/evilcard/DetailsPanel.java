package ho.module.evilcard;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;


class DetailsPanel extends JPanel {

	private static final long serialVersionUID = -8385208467445931375L;
	private DetailsTable detailsTable = null;

    DetailsPanel() {
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
