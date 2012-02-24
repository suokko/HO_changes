package ho.core.module;

import gui.HOIconName;
import ho.core.gui.comp.entry.ColorLabelEntry;
import ho.core.gui.comp.renderer.HODefaultTableCellRenderer;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOVerwaltung;
import ho.core.module.config.ModuleConfigDialog;
import ho.tool.updater.TableEditor;
import ho.tool.updater.TableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;


class ModuleConfigPanelTable extends JTable implements ActionListener{
	private static final long serialVersionUID = 1L;
	protected String[] columnNames = {HOVerwaltung.instance().getLanguageString("TabManagement"),HOVerwaltung.instance().getLanguageString("Name"),HOVerwaltung.instance().getLanguageString("Optionen")};
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
		IModule[] modules = ModuleManager.instance().getTempModules(true); 
		Object[][] value = new Object[modules.length][columnNames.length];

		for (int i = 0; i < modules.length; i++) {
			value[i][0] = getCheckbox(modules[i]);
			value[i][1] = new ColorLabelEntry(modules[i].getDescription() );
			value[i][2] = modules[i].hasConfigPanel()?getButton(modules[i]):"";
		}

		TableModel model = new TableModel(value, columnNames);
		return model;

	}

//	private JLabel getLabel( String txt) {
//		JLabel tmp = new JLabel(txt);
//		return tmp;
//	}

	private JCheckBox getCheckbox(IModule module) {
	    JCheckBox tmp = new JCheckBox();
	    tmp.putClientProperty("MODULE", module);
	    tmp.setOpaque(false);
	    tmp.setSelected(module.isStartup());
	    tmp.addActionListener(this);
	    tmp.setHorizontalAlignment(SwingConstants.CENTER);
    return tmp;
	}
	
	private JButton getButton(IModule module) {
		JButton tmp = new JButton(ThemeManager.getIcon(HOIconName.INFO));
		tmp.putClientProperty("MODULE", module);
		//tmp.setOpaque(false);
		tmp.addActionListener(this);
		return tmp;
	}

	@SuppressWarnings("cast")
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JCheckBox){
			JCheckBox box = (JCheckBox)e.getSource();
			IModule module = (IModule)box.getClientProperty("MODULE");
			module.setStartup(box.isSelected());
			return;
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
		c.setMaxWidth(25);
		c =	getColumn(columnNames[2]);
		c.setCellEditor(editor);
		c.setMaxWidth(50);
	}

	
}
