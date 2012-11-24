package ho.module.lineup;

import ho.core.gui.comp.table.BooleanColumn;
import ho.core.gui.comp.table.HOTableModel;
import ho.core.gui.comp.table.UserColumn;
import ho.core.gui.model.PlayerColumn;
import ho.core.gui.model.UserColumnFactory;
import ho.core.model.player.Spieler;
import ho.core.util.HOLogger;

import java.util.Vector;

public class LineupTableModel extends HOTableModel {

	private static final long serialVersionUID = 6706783648812506363L;

	private Vector<Spieler> m_vPlayers;

	public LineupTableModel(int id) {
		super(id, "Aufstellung");
		initialize();
	}

	private void initialize() {
		UserColumn[] basic = UserColumnFactory.createPlayerBasicArray();
		columns = new UserColumn[49];
		columns[0] = basic[0];
		columns[48] = basic[1];

		UserColumn[] skills = UserColumnFactory.createPlayerSkillArray();
		for (int i = 10; i < skills.length + 10; i++) {
			columns[i] = skills[i - 10];
		}

		UserColumn[] positions = UserColumnFactory.createPlayerPositionArray();
		for (int i = 22; i < positions.length + 22; i++) {
			columns[i] = positions[i - 22];
		}

		UserColumn[] goals = UserColumnFactory.createGoalsColumnsArray();
		for (int i = 42; i < goals.length + 42; i++) {
			columns[i] = goals[i - 42];
		}

		UserColumn[] add = UserColumnFactory.createPlayerAdditionalArray();
		columns[1] = add[0];
		columns[2] = add[1];
		columns[3] = add[2];

		columns[4] = new BooleanColumn(UserColumnFactory.AUTO_LINEUP, " ", "AutoAufstellung", 28) {
			@Override
			public boolean isEditable() {
				return false;
			}
		};
		columns[5] = add[3];
		columns[6] = add[4];
		columns[7] = add[5];
		columns[8] = add[6];
		columns[9] = add[11]; // Homegrown
		columns[46] = add[7];
		columns[47] = add[8];
		columns[41] = add[9];
	}

	/**
	 * TODO Missing Method Documentation
	 * 
	 * @param row
	 *            TODO Missing Method Parameter Documentation
	 * @param col
	 *            TODO Missing Method Parameter Documentation
	 * 
	 * @return TODO Missing Return Method Documentation
	 */
	@Override
	public final boolean isCellEditable(int row, int col) {
		if (getValueAt(row, col) instanceof Boolean) {
			return true;
		}

		return false;

	}

	/**
	 * Listener für die Checkboxen zum Autoaufstellen
	 * 
	 * @param zeile
	 *            TODO Missing Constructuor Parameter Documentation
	 */
	public final void setSpielberechtigung() {
		try {
			for (int i = 0; i < this.getRowCount(); i++) {
				final int id = Integer
						.parseInt(((ho.core.gui.comp.entry.ColorLabelEntry) getValueAt(i,
								getColumnIndexOfDisplayedColumn(UserColumnFactory.ID))).getText());
				final Spieler spieler = getSpieler(id);

				if ((spieler != null)
						&& (spieler.isSpielberechtigt() != ((Boolean) getValueAt(i,
								getColumnIndexOfDisplayedColumn(UserColumnFactory.AUTO_LINEUP)))
								.booleanValue())) {
					spieler.setSpielberechtigt(((Boolean) getValueAt(i,
							getColumnIndexOfDisplayedColumn(UserColumnFactory.AUTO_LINEUP)))
							.booleanValue());
				}

			}
		} catch (Exception e) {
			HOLogger.instance().log(getClass(), e);
		}
	}

	/**
	 * TODO Missing Method Documentation
	 * 
	 * @param id
	 *            TODO Missing Method Parameter Documentation
	 * 
	 * @return TODO Missing Return Method Documentation
	 */
	public final Spieler getSpieler(int id) {
		if (id > 0) {
			for (int i = 0; i < m_vPlayers.size(); i++) {
				if (((Spieler) m_vPlayers.get(i)).getSpielerID() == id) {
					return (Spieler) m_vPlayers.get(i);
				}
			}
		}

		return null;
	}

	/**
	 * Spieler neu setzen
	 * 
	 * @param spieler
	 *            TODO Missing Constructuor Parameter Documentation
	 */
	public final void setValues(Vector<Spieler> player) {
		m_vPlayers = player;
		initData();
	}

	/**
	 * Fügt der Tabelle einen Spieler hinzu
	 * 
	 * @param spieler
	 *            TODO Missing Constructuor Parameter Documentation
	 * @param index
	 *            TODO Missing Constructuor Parameter Documentation
	 */
	public final void addSpieler(Spieler player, int index) {
		m_vPlayers.add(index, player);
		initData();
	}

	/**
	 * create a data[][] from player-Vector
	 */
	@Override
	protected void initData() {
		UserColumn[] tmpDisplayedColumns = getDisplayedColumns();
		m_clData = new Object[m_vPlayers.size()][tmpDisplayedColumns.length];

		for (int i = 0; i < m_vPlayers.size(); i++) {
			final Spieler aktuellerSpieler = (Spieler) m_vPlayers.get(i);

			for (int j = 0; j < tmpDisplayedColumns.length; j++) {
				if (tmpDisplayedColumns[j] instanceof PlayerColumn)
					m_clData[i][j] = ((PlayerColumn) tmpDisplayedColumns[j]).getTableEntry(
							aktuellerSpieler, null);
				if (tmpDisplayedColumns[j] instanceof BooleanColumn)
					m_clData[i][j] = ((BooleanColumn) tmpDisplayedColumns[j])
							.getValue(aktuellerSpieler);
			}
		}
	}

	/**
	 * Passt nur die Aufstellung an
	 */
	public final void reInitData() {
		UserColumn[] tmpDisplayedColumns = getDisplayedColumns();
		for (int i = 0; i < m_vPlayers.size(); i++) {
			final Spieler aktuellerSpieler = (Spieler) m_vPlayers.get(i);

			for (int j = 0; j < tmpDisplayedColumns.length; j++) {
				if (tmpDisplayedColumns[j].getId() == UserColumnFactory.NAME
						|| tmpDisplayedColumns[j].getId() == UserColumnFactory.LINUP
						|| tmpDisplayedColumns[j].getId() == UserColumnFactory.BEST_POSITION
						|| tmpDisplayedColumns[j].getId() == UserColumnFactory.GROUP)
					m_clData[i][j] = ((PlayerColumn) tmpDisplayedColumns[j]).getTableEntry(
							aktuellerSpieler, null);
			}
		}
	}

	/**
	 * Entfernt den Spieler aus der Tabelle
	 * 
	 * @param spieler
	 *            TODO Missing Constructuor Parameter Documentation
	 */
	public final void removeSpieler(Spieler player) {
		m_vPlayers.remove(player);
		initData();
	}
}
