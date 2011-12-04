package de.hattrickorganizer.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import de.hattrickorganizer.net.MyConnector;
import de.hattrickorganizer.tools.developer.SQLDialog;

public class DeveloperMode {

	public static JMenu getDeveloperMenu() {
		JMenu menu = new JMenu("Developer");
		menu.add(getSQLDialogMenuItem());
		menu.add(getSaveXMLMenuItem());
		return menu;
	}

	private static JMenuItem getSQLDialogMenuItem() {
		JMenuItem newItem = new JMenuItem("SQL Editor");
		newItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new SQLDialog().setVisible(true);
			}
		});
		return newItem;
	}

	private static JMenuItem getSaveXMLMenuItem() {
		JMenuItem newItem = new JCheckBoxMenuItem("Save downloaded XML");
		newItem.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				MyConnector.setDebugSave(e.getStateChange() == ItemEvent.SELECTED);
			}
		});
		return newItem;
	}

}
