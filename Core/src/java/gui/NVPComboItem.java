package gui;

public class NVPComboItem {
	private int value;
	private String text;
	/**
	 * Create a new value - text Item
	 * @param value - the value of the item
	 * @param text - the text of the item
	 */
	public NVPComboItem(int value, String text) {
		this.value = value;
		this.text = text;
	}
	/**
	 * Returns the text of the Item
	 * Used to display the text in the combo box
	 */
	@Override
	public String toString() {
		return text;
	}
	/**
	 * Returns the value of the item
	 * @return
	 */
	public int getValue() {
		return value;
	}
	@Override
	/**
	 * Compares two items by value only.
	 */
	public boolean equals(Object i) {
		boolean bCompare = false;
		if(i instanceof NVPComboItem)
		{
			NVPComboItem localItem = (NVPComboItem)i;
			if (localItem.value > -1)
				bCompare = localItem.value == getValue();
			else if (localItem.text.length() > 0)
				bCompare = localItem.text == toString();
		}
		return bCompare;
	}
}