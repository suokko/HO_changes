package ho.core.gui;

import ho.HO;
import ho.core.model.HOVerwaltung;
import ho.core.util.BrowserLauncher;
import ho.core.util.HOLogger;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Credits {

	public static void showCredits(Component parent) {
		StringBuilder hoCredits = new StringBuilder(200);
		hoCredits.append("Hattrick Organizer ").append(HO.VERSION).append("\n\n");
		hoCredits.append(HOVerwaltung.instance().getLanguageString("MenuCredits"));
		hoCredits.append("\n\n");

		JPanel fuguePanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		fuguePanel.add(new JLabel("Some icons by "), gbc);
		gbc.gridx = 1;
		final JLabel linkLabel = new JLabel("Yusuke Kamiyamane.");
		linkLabel.setForeground(new Color(6, 69, 173));
		fuguePanel.add(linkLabel, gbc);
		gbc.gridx = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		fuguePanel.add(new JLabel(" All rights reserved."), gbc);

		linkLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				linkLabel.setCursor(Cursor.getDefaultCursor());
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					BrowserLauncher.openURL("http://p.yusukekamiyamane.com/");
				} catch (IOException ex) {
					HOLogger.instance().log(Credits.class, ex);
				} catch (URISyntaxException ex) {
					HOLogger.instance().log(Credits.class, ex);
				}
			}
		});

		Object[] components = { hoCredits.toString(), fuguePanel };

		JOptionPane.showMessageDialog(parent, components, HOVerwaltung.instance()
				.getLanguageString("MenuCreditsChoice"), JOptionPane.INFORMATION_MESSAGE);
	}

}
