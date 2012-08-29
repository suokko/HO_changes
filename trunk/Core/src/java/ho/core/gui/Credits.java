package ho.core.gui;

import ho.HO;
import ho.core.gui.comp.HyperLinkLabel;
import ho.core.model.HOVerwaltung;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Credits {

	public static void showCredits(Component parent) {
		StringBuilder hoCredits = new StringBuilder(200);
		hoCredits.append("Hattrick Organizer ").append(HO.VERSION).append("\n\n");
		hoCredits.append(HOVerwaltung.instance().getLanguageString("MenuCredits"));
		hoCredits.append("\n\n");

		String fugueURL = "http://p.yusukekamiyamane.com";
		JPanel fuguePanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		fuguePanel.add(new JLabel("Some icons by Yusuke Kamiyamane, "), gbc);
		gbc.gridx = 1;
		JLabel linkLabel = new HyperLinkLabel(fugueURL + ".", fugueURL);
		fuguePanel.add(linkLabel, gbc);
		gbc.gridx = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		fuguePanel.add(new JLabel(" All rights reserved."), gbc);

		String fatcowURL = "http://www.fatcow.com/free-icons";
		JPanel fatcowPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		fatcowPanel.add(new JLabel("Some icons by Fatcow Hosting, "), gbc);
		gbc.gridx = 1;
		linkLabel = new HyperLinkLabel(fatcowURL + ".", fatcowURL);
		fatcowPanel.add(linkLabel, gbc);
		gbc.gridx = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		fatcowPanel.add(new JLabel(" All rights reserved."), gbc);

		Object[] components = { hoCredits.toString(), fuguePanel, fatcowPanel };

		JOptionPane.showMessageDialog(parent, components, HOVerwaltung.instance()
				.getLanguageString("MenuCreditsChoice"), JOptionPane.INFORMATION_MESSAGE);
	}
}
