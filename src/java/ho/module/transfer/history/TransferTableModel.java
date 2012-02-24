// %1126721330463:hoplugins.transfers.ui.model%
package ho.module.transfer.history;

import ho.core.model.HOVerwaltung;
import ho.module.transfer.PlayerTransfer;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;



/**
 * TableModel representing the transfers for your own team.
 *
 * @author <a href=mailto:nethyperon@users.sourceforge.net>Boy van der Werf</a>
 */
class TransferTableModel extends AbstractTableModel {
    //~ Static fields/initializers -----------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -7041463041956883945L;

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$

    //~ Instance fields ----------------------------------------------------------------------------

    private List<PlayerTransfer> values;
    private String[] colNames = new String[19];

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a TransferTableModel.
     *
     * @param values List of values to show in table.
     */
    TransferTableModel(List<PlayerTransfer> values) {
        super();

        this.colNames[0] = HOVerwaltung.instance().getLanguageString("Datum"); //$NON-NLS-1$
        this.colNames[1] = HOVerwaltung.instance().getLanguageString("Season"); //$NON-NLS-1$
        this.colNames[2] = HOVerwaltung.instance().getLanguageString("Week"); //$NON-NLS-1$
        this.colNames[3] = HOVerwaltung.instance().getLanguageString("Spieler"); //$NON-NLS-1$
        this.colNames[4] = ""; //$NON-NLS-1$
        this.colNames[5] = HOVerwaltung.instance().getLanguageString("FromTo"); //$NON-NLS-1$
        this.colNames[6] = HOVerwaltung.instance().getLanguageString("Price"); //$NON-NLS-1$
        this.colNames[7] = HOVerwaltung.instance().getLanguageString("TSI"); //$NON-NLS-1$
        this.colNames[8] = HOVerwaltung.instance().getLanguageString("FUE");
        this.colNames[9] = HOVerwaltung.instance().getLanguageString("ER");
        this.colNames[10] = HOVerwaltung.instance().getLanguageString("FO");
        this.colNames[11] = HOVerwaltung.instance().getLanguageString("KO");
        this.colNames[12] = HOVerwaltung.instance().getLanguageString("TW");
        this.colNames[13] = HOVerwaltung.instance().getLanguageString("VE");
        this.colNames[14] = HOVerwaltung.instance().getLanguageString("SA");
        this.colNames[15] = HOVerwaltung.instance().getLanguageString("PS");
        this.colNames[16] = HOVerwaltung.instance().getLanguageString("FL");
        this.colNames[17] = HOVerwaltung.instance().getLanguageString("TS");
        this.colNames[18] = HOVerwaltung.instance().getLanguageString("ST");

        this.values = values;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /** {@inheritDoc} */
    public final int getColumnCount() {
        return colNames.length;
    }

    /** {@inheritDoc} */
    @Override
	public final String getColumnName(int column) {
        return colNames[column];
    }

    /** {@inheritDoc} */
    public final int getRowCount() {
        return values.size();
    }

    /** {@inheritDoc} */
    public final Object getValueAt(int rowIndex, int columnIndex) {
        final PlayerTransfer transfer = values.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return FORMAT.format(transfer.getDate());

            case 1:
                return new Integer(transfer.getSeason());

            case 2:
                return new Integer(transfer.getWeek());

            case 3:

                if ((transfer.getPlayerName() != null) && (transfer.getPlayerName().length() > 0)) {
                    return transfer.getPlayerName();
                } else {
                    return "< " + HOVerwaltung.instance().getLanguageString("FiredPlayer") + " >";
                }

            case 4:
                return new Integer(transfer.getType());

            case 5:

                if (transfer.getType() == PlayerTransfer.BUY) {
                    return transfer.getSellerName();
                } else {
                    return transfer.getBuyerName();
                }

            case 6:
                return new Integer(transfer.getPrice());

            case 7:
                return new Integer(transfer.getTsi());

            case 8:

                if (transfer.getPlayerInfo() != null) {
                    return new Integer(transfer.getPlayerInfo().getFuehrung());
                } else {
                    return new Integer(0);
                }

            case 9:

                if (transfer.getPlayerInfo() != null) {
                    return new Integer(transfer.getPlayerInfo().getErfahrung());
                } else {
                    return new Integer(0);
                }

            case 10:

                if (transfer.getPlayerInfo() != null) {
                    return new Integer(transfer.getPlayerInfo().getForm());
                } else {
                    return new Integer(0);
                }

            case 11:

                if (transfer.getPlayerInfo() != null) {
                    return new Integer(transfer.getPlayerInfo().getKondition());
                } else {
                    return new Integer(0);
                }

            case 12:

                if (transfer.getPlayerInfo() != null) {
                    return new Integer(transfer.getPlayerInfo().getTorwart());
                } else {
                    return new Integer(0);
                }

            case 13:

                if (transfer.getPlayerInfo() != null) {
                    return new Integer(transfer.getPlayerInfo().getVerteidigung());
                } else {
                    return new Integer(0);
                }

            case 14:

                if (transfer.getPlayerInfo() != null) {
                    return new Integer(transfer.getPlayerInfo().getSpielaufbau());
                } else {
                    return new Integer(0);
                }

            case 15:

                if (transfer.getPlayerInfo() != null) {
                    return new Integer(transfer.getPlayerInfo().getPasspiel());
                } else {
                    return new Integer(0);
                }

            case 16:

                if (transfer.getPlayerInfo() != null) {
                    return new Integer(transfer.getPlayerInfo().getFluegelspiel());
                } else {
                    return new Integer(0);
                }

            case 17:

                if (transfer.getPlayerInfo() != null) {
                    return new Integer(transfer.getPlayerInfo().getTorschuss());
                } else {
                    return new Integer(0);
                }

            case 18:

                if (transfer.getPlayerInfo() != null) {
                    return new Integer(transfer.getPlayerInfo().getStandards());
                } else {
                    return new Integer(0);
                }

            default:
                return ""; //$NON-NLS-1$
        }
    }
}
