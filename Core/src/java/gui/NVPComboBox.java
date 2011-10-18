package gui;
import gui.NVPComboItem;
import javax.swing.JComboBox;
public class NVPComboBox extends JComboBox{

	/**
	 * ComboBox that holds Items of integer - text pairs.
	 * @author Seb04
	 */
	private static final long serialVersionUID = -13046090322067302L;
	
	/**
	 * Add a new item to the combo box
	 * @param value - The value of the item
	 * @param text - The text of the item
	 */
	public void addItem(int value, String text) {
		addItem(new NVPComboItem(value, text));
	}
	/**
	 * Get the value of the selected item
	 * @return The value of the selected item
	 */
	public int getSelectedItemValue() {
		int value = -1;
		NVPComboItem item = (NVPComboItem)getSelectedItem();
		if(item != null)
			value = item.getValue();
		return value;
	}
	
	/**
	 * Set the item to be selected by value
	 * @param value - The value of the item to be selected
	 */
	@Override
	public void setSelectedItem(Object i) {
		// Create a new item without text
		// as comparison only uses value.
		if(i instanceof NVPComboItem)
			super.setSelectedItem(i);
		else if(i instanceof Integer)
			super.setSelectedItem(new NVPComboItem((Integer)i, ""));
		else if(i instanceof String)
			super.setSelectedItem(new NVPComboItem(-1, (String)i));
	}
}
