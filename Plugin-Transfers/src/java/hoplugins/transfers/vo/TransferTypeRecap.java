// %1126721331041:hoplugins.transfers.vo%
package hoplugins.transfers.vo;

/**
 * Recap Information about a single Transfer Type
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class TransferTypeRecap {
    //~ Instance fields ----------------------------------------------------------------------------

    /** Net Income of all the transfers of this type */
    private int netIncome;

    /** Number of transfers of this type */
    private int number;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Returns the total Net Income
     *
     * @return total gain or loss by transfers of this type
     */
    public final int getNetIncome() {
        return netIncome;
    }

    /**
     * Returns the number of transfers
     *
     * @return Number of transfer
     */
    public final int getNumber() {
        return number;
    }

    /**
     * Add a Transfer to the collection
     *
     * @param income income of the transfer
     */
    public final void addOperation(int income) {
        number++;
        netIncome = netIncome + income;
    }
}
