package ho.module.evilcard.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class PlayersPanel extends JPanel implements ListSelectionListener {

	private static final long serialVersionUID = 5173473921072367115L;
	private DetailsTable detailsTable = null;
	private PlayersTable playersTable = null;

	PlayersPanel(DetailsTable detailsTable) {
		super();
		this.detailsTable = detailsTable;
		this.setLayout(new BorderLayout());
		playersTable = new PlayersTable();
		this.add(new JScrollPane(playersTable));

		playersTable.getSelectionModel().addListSelectionListener(this);
		playersTable.getColumnModel().getColumn(PlayersTableModel.COL_ID).setMinWidth(0);
		playersTable.getColumnModel().getColumn(PlayersTableModel.COL_ID).setMaxWidth(0);
		playersTable.getColumnModel().getColumn(PlayersTableModel.COL_ID).setWidth(0);
	}

	void setFilter(int filterMode) {
		playersTable.setFilter(filterMode);
	}

	protected int getSelectedPlayer() {
		int row = this.playersTable.getSelectedRow();

		if (row >= 0) {
			Integer playerId = (Integer) playersTable.getValueAt(row, PlayersTableModel.COL_ID);

			return playerId.intValue();
		}

		return 0;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			detailsTable.refresh(getSelectedPlayer());
		}
	}
}
