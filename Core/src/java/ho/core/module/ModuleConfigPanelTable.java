package ho.core.module;

import ho.core.gui.comp.entry.ColorLabelEntry;
import ho.core.gui.comp.renderer.HODefaultTableCellRenderer;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.module.config.ModuleConfigDialog;
import ho.tool.updater.TableEditor;
import ho.tool.updater.TableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.TableColumn;


class ModuleConfigPanelTable extends JTable implements ActionListener{
	private static final long serialVersionUID = 1L;
	private static String[] stateDescriptions = {HOVerwaltung.instance().getLanguageString("Deactivated"),HOVerwaltung.instance().getLanguageString("Activated"),HOVerwaltung.instance().getLanguageString("Autostart")};
	protected String[] columnNames = {HOVerwaltung.instance().getLanguageString("Status"),HOVerwaltung.instance().getLanguageString("Name"),HOVerwaltung.instance().getLanguageString("Optionen")};
	private TableEditor editor = new TableEditor();

	
	ModuleConfigPanelTable(){
		initialize();
	}

	private void initialize() {
		refresh();
		setRowHeight(25);
		setDefaultRenderer(Object.class, new HODefaultTableCellRenderer());
		getTableHeader().setReorderingAllowed(false);
		

		
	}
	
	protected TableModel getTableModel() {
		IModule[] modules = ModuleManager.instance().getTempModules(); 
		Object[][] value = new Object[modules.length][columnNames.length];

		for (int i = 0; i < modules.length; i++) {
			value[i][0] = getComboBox(modules[i]);
			value[i][1] = new ColorLabelEntry(modules[i].getDescription());
			if(modules[i].getStatus() == IModule.STATUS_DEACTIVATED)
				((ColorLabelEntry)value[i][1]).setFGColor(ThemeManager.getColor("dark_gray"));
			value[i][2] = modules[i].hasConfigPanel()?getButton(modules[i]):"";
		}

		TableModel model = new TableModel(value, columnNames);
		return model;

	}

//	private JLabel getLabel( String txt) {
//		JLabel tmp = new JLabel(txt);
//		return tmp;
//	}

	private JComboBox getComboBox(IModule module){
		JComboBox cBox = new JComboBox(stateDescriptions);
		cBox.putClientProperty("MODULE", module);
		cBox.setSelectedIndex(module.getStatus());
		cBox.addActionListener(this);
		return cBox;
	}
	
//	private JCheckBox getCheckbox(IModule module, boolean value, String name) {
//	    JCheckBox tmp = new JCheckBox();
//	    tmp.setName(name);
//	    tmp.putClientProperty("MODULE", module);
//	    tmp.setOpaque(false);
//	    tmp.setSelected(value);
//	    tmp.addActionListener(this);
//	    tmp.setHorizontalAlignment(SwingConstants.CENTER);
//    return tmp;
//	}
	
	private JButton getButton(IModule module) {
		JButton tmp = new JButton(ThemeManager.getIcon(HOIconName.INFO));
		tmp.putClientProperty("MODULE", module);
		//tmp.setOpaque(false);
		tmp.setEnabled(module.getStatus()>IModule.STATUS_DEACTIVATED);
		tmp.addActionListener(this);
		return tmp;
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()instanceof JComboBox){
			JComboBox box = (JComboBox)e.getSource();
			IModule module = (IModule)box.getClientProperty("MODULE");
			int index = box.getSelectedIndex();
			module.setStatus(index);
			refresh();
		} else if(e.getSource() instanceof JButton){
			JButton button = (JButton)e.getSource();
			IModule module = (IModule)button.getClientProperty("MODULE");
			ModuleConfigDialog dialog = new ModuleConfigDialog((JDialog)getTopLevelAncestor(), module);
			dialog.setVisible(true);
		}
		
	}
	
	void refresh(){
		setModel(getTableModel());
		TableColumn c = getColumn(columnNames[0]);
		c.setCellEditor(editor);
		c.setMinWidth(120);
		c.setMaxWidth(150);
		c = getColumn(columnNames[2]);
		c.setCellEditor(editor);
		c.setMinWidth(50);
		c.setMaxWidth(60);
	}

	
}
