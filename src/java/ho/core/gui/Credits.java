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

	private static final Color LINK_COLOR = new Color(6, 69, 173);
	
	public static void showCredits(Component parent) {
		StringBuilder hoCredits = new StringBuilder(200);
		hoCredits.append("Hattrick Organizer ").append(HO.VERSION).append("\n\n");
		hoCredits.append(HOVerwaltung.instance().getLanguageString("MenuCredits"));
		hoCredits.append("\n\n");

		String fugueURL = "http://p.yusukekamiyamane.com/";
		JPanel fuguePanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		fuguePanel.add(new JLabel("Some icons by Yusuke Kamiyamane, "), gbc);
		gbc.gridx = 1;
		JLabel linkLabel = new JLabel(fugueURL + ".");
		linkLabel.setForeground(LINK_COLOR);
		linkLabel.addMouseListener(new LinkListener(fugueURL));
		fuguePanel.add(linkLabel, gbc);
		gbc.gridx = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		fuguePanel.add(new JLabel(" All rights reserved."), gbc);
		
		String fatcowURL = "http://www.fatcow.com/free-icons";
		JPanel fatcowPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		fatcowPanel.add(new JLabel("Thx to Fatcow Hosting for this great Icon set! Check it out: "), gbc);
		gbc.gridx = 1;
		linkLabel = new JLabel(fatcowURL);
		linkLabel.setForeground(LINK_COLOR);
		linkLabel.addMouseListener(new LinkListener(fatcowURL));
		gbc.gridx = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		fatcowPanel.add(linkLabel, gbc);
		
		Object[] components = { hoCredits.toString(), fuguePanel, fatcowPanel };

		JOptionPane.showMessageDialog(parent, components, HOVerwaltung.instance()
				.getLanguageString("MenuCreditsChoice"), JOptionPane.INFORMATION_MESSAGE);
	}

	private static class LinkListener extends MouseAdapter {

		private final String url;

		public LinkListener(String url) {
			this.url = url;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			((Component) e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			((Component) e.getSource()).setCursor(Cursor.getDefaultCursor());
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				BrowserLauncher.openURL(url);
			} catch (IOException ex) {
				HOLogger.instance().log(Credits.class, ex);
			} catch (URISyntaxException ex) {
				HOLogger.instance().log(Credits.class, ex);
			}
		}
	}

}
