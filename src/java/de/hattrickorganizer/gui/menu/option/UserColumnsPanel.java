package de.hattrickorganizer.gui.menu.option;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumnModel;

import de.hattrickorganizer.gui.model.HOColumnModel;
import de.hattrickorganizer.gui.model.UserColumn;
import de.hattrickorganizer.gui.model.UserColumnController;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.Helper;
import de.hattrickorganizer.tools.updater.TableEditor;
import de.hattrickorganizer.tools.updater.TableModel;
import de.hattrickorganizer.tools.updater.UpdaterCellRenderer;

/**
 * 
 * @author Thorsten Dietz
 * @since 1.36
 *
 */
public class UserColumnsPanel extends JPanel implements ActionListener{

	private JComboBox m_jcbColumnModel 	= null;
	private JTable table 				= null;
	private final String [] columnNames = new String[]{" ", HOVerwaltung.instance().getResource().getProperty("column")};
	private final ImageIcon lockedImage 	= new ImageIcon(Helper.loadImage("gui/bilder/Locked.gif"));
	private boolean m_bNeedRestart;
	protected UserColumnsPanel(){
		initComponents();
	}
	
	private void initComponents(){
		setLayout(new BorderLayout());
		add(getTopPanel(),BorderLayout.NORTH);
		add(getMiddlePanel(),BorderLayout.CENTER);
	}
	
	/**
	 * 
	 * @return
	 */
	private JPanel getTopPanel(){
		JPanel panel = new ImagePanel();
		m_jcbColumnModel = new JComboBox(UserColumnController.instance().getAllModels());
		m_jcbColumnModel.addActionListener( this );
		
		panel.add(m_jcbColumnModel);
		return panel;
	}
	
	/**
	 * return the panel within JTable
	 * @return JPanel
	 */
	private JPanel getMiddlePanel(){
		JPanel panel = new JPanel();
		 panel.setLayout(new BorderLayout());
	     panel.add(createTable());
	    panel.setPreferredSize(new Dimension(100, 300));
		return panel;
	}
	
	private HOColumnModel getSelectedModel(){
		return (HOColumnModel)m_jcbColumnModel.getSelectedItem();
	}
    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected JScrollPane createTable() {
        table = new JTable(getModel(getSelectedModel().getColumns()));
        table.getTableHeader().setReorderingAllowed(false);
        table.setDefaultRenderer(Object.class, new UpdaterCellRenderer());
        table.getColumn(columnNames[0]).setCellEditor(new TableEditor());

        
        final TableColumnModel tableColumnModel = table.getColumnModel();
        tableColumnModel.getColumn(0).setMaxWidth(50);
        tableColumnModel.getColumn(0).setPreferredWidth(50);
        
        JScrollPane scroll = new JScrollPane(table);
        return scroll;
    }

	
	/**
     * TODO Missing Method Documentation
     *
     * @param selected TODO Missing Method Parameter Documentation
     * @param columnNames2 TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected TableModel getModel(UserColumn[] dbColumns) {
        Object[][] value = new Object[dbColumns.length][2];

        for (int i = 0; i < dbColumns.length; i++) {

            value[i][0] = getCheckbox(dbColumns[i]);
            value[i][1] = dbColumns[i];
        }

        TableModel model = new TableModel(value, columnNames);

        return model;
    }
    /**
     * TODO Missing Method Documentation
     *
     * @param isSelected TODO Missing Method Parameter Documentation
     * @param isEnabled TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected JCheckBox getCheckbox(UserColumn column) {
        JCheckBox tmp = new JCheckBox();
        tmp.setBackground(Color.WHITE);
        tmp.setSelected(column.isDisplay());
        tmp.setHorizontalAlignment(SwingConstants.CENTER);
        if(column.isEditable())
        	tmp.addActionListener( this );
        else{
        	tmp.setIcon(lockedImage);
        }
        return tmp;
    }

    /**
     * action
     */
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == m_jcbColumnModel){
			table.setModel(getModel(getSelectedModel().getColumns()));
			 table.getColumn(columnNames[0]).setCellEditor(new TableEditor());
			final TableColumnModel tableColumnModel = table.getColumnModel();
		        tableColumnModel.getColumn(0).setMaxWidth(50);
		        tableColumnModel.getColumn(0).setPreferredWidth(50);
		}
		
		if(arg0.getSource() instanceof JCheckBox){
			((UserColumn)table.getValueAt(table.getSelectedRow(),1)).setDisplay(((JCheckBox)arg0.getSource()).isSelected());
			m_bNeedRestart = true;
		}
		
	}
	
	/**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean needRestart() {
        return m_bNeedRestart;
    }
}
