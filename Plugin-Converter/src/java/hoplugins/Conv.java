/*
 * Created on 20.05.2004
 *
 */
package hoplugins;

import java.awt.event.ActionListener;
import java.io.File;


import hoplugins.conv.CListener;
import hoplugins.conv.RSC;


import javax.swing.JMenu;
import javax.swing.JMenuItem;


import plugins.IHOMiniModel;
import plugins.IOfficialPlugin;
import plugins.IPlugin;

/**
 * @author Thorsten Dietz
 * 25.05.2005 Version 0.9
 * 18.02.2005 Version 1.1
 */
public final class Conv implements IPlugin,IOfficialPlugin {

	public String getName() {
		return RSC.NAME;
	}

	public void start(IHOMiniModel arg0) {
	    RSC.initializeStrings(arg0);
	    arg0.getGUI().addMenu(getMenu());
	}

	private JMenu getMenu() {
	    CListener menuAction = new CListener();
		JMenu menu = new JMenu(RSC.getProperty("conversion"));
		menu.add(getItem(RSC.BUDDY_TO_HRF,RSC.BUDDY_TO_HRF,menuAction));
		menu.addSeparator();
		menu.add(getItem("HRF => "+RSC.PROP_FILE,RSC.DB_TO_HRF,menuAction));
		menu.addSeparator();
		menu.add(getItem(RSC.PROP_PLAYERS+" => "+RSC.PROP_FILE,RSC.PROP_PLAYERS,menuAction));
		return menu;
	}

	private JMenuItem getItem(String label, String action, ActionListener listener)
    {
        JMenuItem newItem = new JMenuItem(label);
        newItem.setActionCommand(action);
        newItem.addActionListener(listener);
        return newItem;
    }

	public double getVersion(){
		return 1.2d;
	}

	public int getPluginID(){
		return 17;
	}

	public String getPluginName(){
		return getName();
	}

	public File[] getUnquenchableFiles(){
		return new File[0];
	}

}
