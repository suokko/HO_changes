package ho.module.training.ui.comp;
/**
 * This class holds id - value pairs, mainly for use in combo boxes
 *
 * @author thomas.werth
 * @author seb04
 */
public class CBItem {
	/**
	 * Holds the text of the Item
	 */
	private String m_sText;
	/**
	 * Holds the id of the item
	 */
    private int m_iId;

    /**
     * Creates a new CBItem
     *
     * @param text The text component
     * @param id The id component
     */
    public CBItem(String text, int id) {
        m_sText = text;
        m_iId = id;
    }

    /**
     * Set the Id of an item
     *
     * @param id The new Id
     */
    public final void setId(int id) {
        m_iId = id;
    }

    /**
     * Get the Id of an item
     *
     * @return The Id of the item
     */
    public final int getId() {
        return m_iId;
    }

    /**
     * Set the text of an item
     *
     * @param text The text to set
     */
    public final void setText(String text) {
        m_sText = text;
    }

    /**
     * Get the text of an item
     *
     * @return The text of the item
     */
    public final String getText() {
        return m_sText;
    }

    /**
     * Compare two items. Two items are deemed equal if they have the same id
     *
     * @param obj The item to compare to
     *
     * @return true if they are the same, otherwise false
     */
    @Override
	public final boolean equals(Object obj) {
        if (obj instanceof CBItem) {
            final CBItem temp = (CBItem) obj;
            return ((CBItem)temp).getId() == this.getId();
        }
        return false;
    }

    /**
     * Returns a string representation of an item 
     *
     * @return The text of the item
     */
    @Override
	public final String toString() {
        return m_sText;
    }
}
