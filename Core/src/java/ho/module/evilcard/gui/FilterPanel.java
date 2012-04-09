package ho.module.evilcard.gui;

import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;


public class FilterPanel extends ImagePanel implements ActionListener {

	private static final long serialVersionUID = 5993279445476499431L;
	private JComboBox choosePlayersComboBox = null;
	private MainPanel mainPanel = null;

	FilterPanel(MainPanel mainPanel) {
		super();
		this.setOpaque(false);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.mainPanel = mainPanel;





		JLabel choosePlayersLabel = new javax.swing.JLabel(HOVerwaltung
				.instance().getLanguageString("Spieler"));

		this.add(choosePlayersLabel);

		choosePlayersComboBox = new JComboBox();
		choosePlayersComboBox.addItem(HOVerwaltung.instance()
				.getLanguageString("label.CurrentPlayersOnly"));
		choosePlayersComboBox.addItem(HOVerwaltung.instance()
				.getLanguageString("alle"));
		choosePlayersComboBox.addActionListener(this);
		choosePlayersComboBox.setSelectedIndex(0);
		this.add(choosePlayersComboBox);
	}

	public void actionPerformed(java.awt.event.ActionEvent e) {
		// combobox for players
		if (e.getSource().equals(choosePlayersComboBox)) {
			switch (choosePlayersComboBox.getSelectedIndex()) {
			case 0:
				mainPanel.setFilter(PlayersTableModel.TYPE_CURRENT_PLAYERS);
				break;

			case 1:
				mainPanel.setFilter(PlayersTableModel.TYPE_ALL_PLAYERS);
				break;

			default:
				// no actions.
			}
		}
	}
}
