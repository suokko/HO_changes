package ho.module.evilcard;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.model.HOVerwaltung;

public class FilterPanel extends ImagePanel implements ActionListener {

	private static final long serialVersionUID = 5993279445476499431L;
	private JComboBox choosePlayersComboBox = null;
	private MainPanel mainPanel = null;

	FilterPanel(MainPanel mainPanel) {
		super();
		this.setOpaque(false);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.mainPanel = mainPanel;

//		JLabel chooseMatchesLabel = new JLabel(HOVerwaltung.instance()
//				.getLanguageString("label.ChooseMatches"));
//		chooseMatchesLabel.setForeground(java.awt.Color.WHITE);
//		chooseMatchesLabel.setFont(new Font(chooseMatchesLabel.getFont()
//				.getName(), Font.BOLD, 14));
//		chooseMatchesLabel.setSize(200, 25);
//		chooseMatchesLabel.setLocation(25, 100);
//		//this.add(chooseMatchesLabel);
//
//		JLabel fromSeasonLabel = new JLabel(HOVerwaltung.instance()
//				.getLanguageString("label.FromSeason"));
//		fromSeasonLabel.setForeground(java.awt.Color.WHITE);
//		fromSeasonLabel.setFont(new Font(fromSeasonLabel.getFont().getName(),
//				Font.BOLD, 10));
//		fromSeasonLabel.setSize(120, 25);
//		fromSeasonLabel.setLocation(25, 140);
//		//this.add(fromSeasonLabel);
//
//		fromSeasonComboBox = new JComboBox();
//		fromSeasonComboBox.setSize(120, 25);
//		fromSeasonComboBox.setLocation(25, 170);
//		fromSeasonComboBox.setEnabled(false);
//		//this.add(fromSeasonComboBox);
//
//		JLabel toSeasonLabel = new JLabel(HOVerwaltung.instance()
//				.getLanguageString("label.ToSeason"));
//		toSeasonLabel.setForeground(java.awt.Color.WHITE);
//		toSeasonLabel.setFont(new Font(toSeasonLabel.getFont().getName(),
//				Font.BOLD, 10));
//		toSeasonLabel.setSize(120, 25);
//		toSeasonLabel.setLocation(25, 200);
//		this.add(toSeasonLabel); // combobox
//
//		toSeasonComboBox = new JComboBox();
//		toSeasonComboBox.setSize(120, 25);
//		toSeasonComboBox.setLocation(25, 230);
//		toSeasonComboBox.setEnabled(false);
//		//this.add(toSeasonComboBox);

//		// Choose match type label
//		JLabel chooseMatchTypeLabel = new JLabel(HOVerwaltung.instance()
//				.getLanguageString("label.ChooseMatchType"));
//		chooseMatchTypeLabel.setForeground(java.awt.Color.WHITE);
//		chooseMatchTypeLabel.setFont(new Font(chooseMatchTypeLabel.getFont()
//				.getName(), Font.BOLD, 10));
//		chooseMatchTypeLabel.setSize(120, 25);
//		chooseMatchTypeLabel.setLocation(25, 270);
		//this.add(chooseMatchTypeLabel);

//		// Choose match type combo box
//		chooseMatchTypeComboBox = new JComboBox();
//		chooseMatchTypeComboBox.addItem(HOVerwaltung.instance()
//				.getLanguageString("label.AllMatches"));
//		chooseMatchTypeComboBox.addItem(HOVerwaltung.instance()
//				.getLanguageString("label.OfficialMatchesOnly"));
//		chooseMatchTypeComboBox.addItem(HOVerwaltung.instance()
//				.getLanguageString("label.FriendlyMatchesOnly"));
//		chooseMatchTypeComboBox.setSize(120, 25);
//		chooseMatchTypeComboBox.setLocation(25, 300);
//		chooseMatchTypeComboBox.setEnabled(false);
//		//this.add(chooseMatchTypeComboBox);

		JLabel choosePlayersLabel = new javax.swing.JLabel(HOVerwaltung
				.instance().getLanguageString("label.ChoosePlayers"));
		//choosePlayersLabel.setForeground(java.awt.Color.WHITE);
		//choosePlayersLabel.setFont(new Font(choosePlayersLabel.getFont()
		//		.getName(), Font.BOLD, 10));
		//choosePlayersLabel.setSize(120, 25);
		//choosePlayersLabel.setLocation(25, 340);
		this.add(choosePlayersLabel);

		choosePlayersComboBox = new JComboBox();
		choosePlayersComboBox.addItem(HOVerwaltung.instance()
				.getLanguageString("label.CurrentPlayersOnly"));
		choosePlayersComboBox.addItem(HOVerwaltung.instance()
				.getLanguageString("label.AllPlayers"));
//		choosePlayersComboBox.setSize(120, 25);
		//choosePlayersComboBox.setLocation(25, 370);
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
