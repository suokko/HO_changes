package ho.core.gui;

import ho.core.db.frontend.SQLDialog;
import ho.core.net.MyConnector;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class DebugMode {

	public static JMenu getDeveloperMenu() {
		JMenu menu = new JMenu("Debug");
		menu.add(getSQLDialogMenuItem());
		menu.add(getLookAndFeelDialogMenuItem());
		menu.add(getSaveXMLMenuItem());
		menu.add(getPlayerHistoryMenuItem());
		menu.add(getTransfersTestDialogMenuItem());
		
		return menu;
	}

	private static JMenuItem getTransfersTestDialogMenuItem() {
		JMenuItem newItem = new JMenuItem("Transfers test");
		newItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new ho.module.transfer.test.TestDialog(HOMainFrame.instance()).setVisible(true);
			}
		});
		return newItem;
	}
	
	private static JMenuItem getLookAndFeelDialogMenuItem() {
		JMenuItem newItem = new JMenuItem("Look and Feel");
		newItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new LookAndFeelDialog().setVisible(true);
			}
		});
		return newItem;
	}

	private static JMenuItem getSQLDialogMenuItem() {
		JMenuItem newItem = new JMenuItem("SQL Editor");
		newItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SQLDialog().setVisible(true);
			}
		});
		return newItem;
	}

	private static JMenuItem getSaveXMLMenuItem() {
		JMenuItem newItem = new JCheckBoxMenuItem("Save downloaded XML");
		newItem.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				MyConnector.setDebugSave(e.getStateChange() == ItemEvent.SELECTED);
			}
		});
		return newItem;
	}

	private static JMenuItem getPlayerHistoryMenuItem() {
		JMenuItem newItem = new JMenuItem("Player History");
		newItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ho.core.training.SkillDropTestFrame().setVisible(true);
			}
		});
		return newItem;
		
	}
	
}
