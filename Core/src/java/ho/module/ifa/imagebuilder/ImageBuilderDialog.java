package ho.module.ifa.imagebuilder;

import ho.core.gui.HOMainFrame;
import ho.core.model.HOVerwaltung;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class ImageBuilderDialog extends JDialog implements ActionListener{

	private static final long serialVersionUID = -3153426395758006044L;
	private JMenuItem saveItem = new JMenuItem(HOVerwaltung.instance().getLanguageString("Speichern"));
	private JCheckBoxMenuItem showHeaderItem = new JCheckBoxMenuItem(HOVerwaltung.instance().getLanguageString("showHeader"));
	private JCheckBoxMenuItem roundlyItem = new JCheckBoxMenuItem(HOVerwaltung.instance().getLanguageString("Roundly"));
	private JCheckBoxMenuItem greyItem = new JCheckBoxMenuItem(HOVerwaltung.instance().getLanguageString("Grey"));
	
	
	public ImageBuilderDialog(){
		super(HOMainFrame.instance(),HOVerwaltung.instance().getLanguageString("Imagebuilder"),true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(800,600);
		initialize();
	}
	
	private void initialize() {
		setJMenuBar(getMenuBar());
		
	}

	
	private JMenuBar getMenuBar(){
		JMenu fileMenu = new JMenu(HOVerwaltung.instance().getLanguageString("Datei"));
		saveItem.addActionListener(this);
		fileMenu.add(saveItem);
		
		JMenu optionMenu = new JMenu(HOVerwaltung.instance().getLanguageString("Optionen"));
		showHeaderItem.addActionListener(this);
		optionMenu.add(showHeaderItem);
		roundlyItem.addActionListener(this);
		optionMenu.add(roundlyItem);
		greyItem.addActionListener(this);
		optionMenu.add(greyItem);
		JMenu flagsMenu = new JMenu(HOVerwaltung.instance().getLanguageString("Flaggen")+"/"+(HOVerwaltung.instance().getLanguageString("Row")));
		ButtonGroup group = new ButtonGroup();
		for (int i = 6; i < 13; i++) {
			JRadioButtonMenuItem item = new JRadioButtonMenuItem(""+i);
			group.add(item);
			item.addActionListener(this);
			flagsMenu.add(item);
		}
		optionMenu.add(flagsMenu);
		JMenuBar bar = new JMenuBar();
		bar.add(fileMenu);
		bar.add(optionMenu);
		return bar;
	}
	
	@Override
	public void setSize(int width, int height) {  
	   super.setSize(width, height);  
		    
	   Dimension screenSize = getParent().getSize();  
	   int x = (screenSize.width - getWidth()) / 2;  
	   int y = (screenSize.height - getHeight()) / 2;  
	    
	   setLocation(getParent().getX()+x, getParent().getY()+y);     
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
