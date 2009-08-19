// %2304636819:hoplugins.teamplanner.ui.model%
package hoplugins.teamplanner.ui.model;

import hoplugins.teamplanner.ui.model.inner.InnerData;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class OperationData {
    //~ Instance fields ----------------------------------------------------------------------------

    private InnerData inner;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new Empty OperationCellData object.
     *
     * @param inner Missing Constructuor Parameter Documentation
     */
    public OperationData(InnerData inner) {
        this.inner = inner;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getId() {
        return inner.getId();
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public InnerData getInner() {
        return inner;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    @Override
	public String toString() {
        return inner.getDescription();
    }
}
