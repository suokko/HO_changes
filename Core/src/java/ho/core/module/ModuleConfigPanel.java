package ho.core.module;

import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;
import ho.core.option.OptionManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class ModuleConfigPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private ModuleConfigPanelTable table;
    private JPanel toolbar;
    private JButton addButton = new JButton(HOVerwaltung.instance().getLanguageString("Hinzufuegen"));
    private JButton removeButton = new JButton(HOVerwaltung.instance().getLanguageString("loeschen"));
    
    
	public ModuleConfigPanel(){
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(100, 300));
		add(getToolbar(),BorderLayout.NORTH);
		add(new JScrollPane(getTable()),BorderLayout.CENTER);
		
	}
	
	private ModuleConfigPanelTable getTable(){
		if(table == null)
			table = new ModuleConfigPanelTable();
		return table;
	}
	private JPanel getToolbar(){
		if(toolbar == null){
			toolbar = new ImagePanel();
			toolbar.setLayout(new FlowLayout(FlowLayout.LEADING));
			addButton.addActionListener(this);
			removeButton.addActionListener(this);
			toolbar.add(addButton);
			toolbar.add(removeButton);
		}
		return toolbar;
	}

	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == addButton || e.getSource() == removeButton){
			boolean active = e.getSource() == addButton;
			JList list = new JList(ModuleManager.instance().getTempModules( !active ));
			int option = JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), new JScrollPane(list),active?addButton.getText():removeButton.getText(),JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
			if(option == JOptionPane.OK_OPTION){
				Object[] tmp = list.getSelectedValues();
				for (int i = 0; i < tmp.length; i++) {
					((IModule)tmp[i]).setActive( active );
					((IModule)tmp[i]).setStartup( active );
				}
				
				getTable().refresh();
				OptionManager.instance().setRestartNeeded();
			}
		} 
	}
	
}
