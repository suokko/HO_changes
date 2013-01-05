package ho.core.model.match;

import ho.core.db.DBManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.match.MatchLineupTeam;
import ho.core.model.match.MatchStatistics;
import ho.core.model.player.ISpielerPosition;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class StatisticsTestFrame extends JDialog implements ActionListener{


	JCheckBox[] positions = new JCheckBox[14];
	JPanel posPanel = new JPanel(new GridBagLayout());
	JTextField matchField = new JTextField();
	JTextField playerField = new JTextField();
	JTextField minuteField = new JTextField();
	JLabel resultLabel = new JLabel();
	JComboBox homeawayBox = new JComboBox();

	JButton posButton = new JButton("Get position for player, match, minute");
	JButton minButton = new JButton("Get minutes for player, match, positions");

	private static final long serialVersionUID = 1L;

	public StatisticsTestFrame() {
		JPanel panel = new JPanel(new GridBagLayout());
		setPositionPanel();

		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 1;
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;


		constraints.gridx=1;
		constraints.gridy=5;
		constraints.gridwidth = 2;

		panel.add(posPanel, constraints);

		constraints.gridx=1;
		constraints.gridy=8;
		panel.add(new JLabel("Home or Away"), constraints);

		constraints.gridx=2;
		homeawayBox.addItem("Home");
		homeawayBox.addItem("Away");
		homeawayBox.setSelectedIndex(0);
		panel.add(homeawayBox, constraints);

		constraints.gridwidth = 1;
		constraints.gridx = 1;
		constraints.gridy = 10;
		panel.add(new JLabel("MatchID"), constraints);

		constraints.gridx=2;
		panel.add(matchField, constraints);


		constraints.gridx=1;
		constraints.gridy=15;
		panel.add(new JLabel("PlayerId"),constraints);

		constraints.gridx=2;
		panel.add(playerField, constraints);

		constraints.gridx=1;
		constraints.gridy=20;
		panel.add(new JLabel("Minute"), constraints);

		constraints.gridx = 2;
		panel.add(minuteField, constraints);

		constraints.gridx = 1;
		constraints.gridy = 25;
		constraints.gridwidth = 2;
		panel.add(minButton, constraints);
		minButton.addActionListener(this);

		constraints.gridy = 30;
		panel.add(posButton, constraints);
		posButton.addActionListener(this);

		constraints.gridy=35;
		panel.add(new JLabel("Latest Result"), constraints);

		constraints.gridx = 2;

		panel.add(resultLabel, constraints);

		this.getContentPane().add(panel);

		this.setSize(200, 300);
		pack();

	}





	private void setPositionPanel() {

		JCheckBox cb;
		GridBagConstraints constraints = new GridBagConstraints();

		cb = new JCheckBox();
		cb.setText("gk");
		positions[0] = cb;

		cb = new JCheckBox();
		cb.setText("rb");
		positions[1] = cb;

		cb = new JCheckBox();
		cb.setText("rcd");
		positions[2] = cb;

		cb = new JCheckBox();
		cb.setText("mcd");
		positions[3] = cb;

		cb = new JCheckBox();
		cb.setText("lcd");
		positions[4] = cb;

		cb = new JCheckBox();
		cb.setText("lb");
		positions[5] = cb;

		cb = new JCheckBox();
		cb.setText("rw");
		positions[6] = cb;

		cb = new JCheckBox();
		cb.setText("rim");
		positions[7] = cb;

		cb = new JCheckBox();
		cb.setText("cim");
		positions[8] = cb;

		cb = new JCheckBox();
		cb.setText("lim");
		positions[9] = cb;

		cb = new JCheckBox();
		cb.setText("lw");
		positions[10] = cb;

		cb = new JCheckBox();
		cb.setText("rfw");
		positions[11] = cb;

		cb = new JCheckBox();
		cb.setText("cfw");
		positions[12] = cb;

		cb = new JCheckBox();
		cb.setText("lfw");
		positions[13] = cb;

		constraints.weightx = 0;
		constraints.weighty = 0;

		// keeper
		constraints.gridx = 3;
		constraints.gridy = 1;
		posPanel.add(positions[0], constraints);

		// defense
		constraints.gridx = 1;
		constraints.gridy = 2;
		posPanel.add(positions[1], constraints);

		constraints.gridx = 2;
		constraints.gridy = 2;
		posPanel.add(positions[2], constraints);

		constraints.gridx = 3;
		constraints.gridy = 2;
		posPanel.add(positions[3], constraints);

		constraints.gridx = 4;
		constraints.gridy = 2;
		posPanel.add(positions[4], constraints);

		constraints.gridx = 5;
		constraints.gridy = 2;
		posPanel.add(positions[5], constraints);

		// midfield

		constraints.gridx = 1;
		constraints.gridy = 3;
		posPanel.add(positions[6], constraints);

		constraints.gridx = 2;
		constraints.gridy = 3;
		posPanel.add(positions[7], constraints);

		constraints.gridx = 3;
		constraints.gridy = 3;
		posPanel.add(positions[8], constraints);

		constraints.gridx = 4;
		constraints.gridy = 3;
		posPanel.add(positions[9], constraints);

		constraints.gridx = 5;
		constraints.gridy = 3;
		posPanel.add(positions[10], constraints);

		// attack

		constraints.gridx = 2;
		constraints.gridy = 4;
		posPanel.add(positions[11], constraints);

		constraints.gridx = 3;
		constraints.gridy = 4;
		posPanel.add(positions[12], constraints);

		constraints.gridx = 4;
		constraints.gridy = 4;
		posPanel.add(positions[13], constraints);

	}





	@Override
	public void actionPerformed(ActionEvent e) {
		boolean error;
		String errorMsg = "";
		MatchLineupTeam team = null;
		MatchStatistics stats = null;

		if (e.getSource() == posButton) {
			boolean isHome = (homeawayBox.getSelectedIndex() == 0);
			int player = -1;
			int match = -1;
			int minute = -1;

			try {
				player = Integer.parseInt(playerField.getText().trim());
				match = Integer.parseInt(matchField.getText().trim());
				minute = Integer.parseInt(minuteField.getText().trim());

				// Hack
				if ((match <= 0) || (player <= 0) || (minute < 0)) {
					throw new NumberFormatException();
				}

				if (isHome) {
					team = (MatchLineupTeam) DBManager.instance().getMatchLineup(match).getHeim();
				} else {
					team = (MatchLineupTeam) DBManager.instance().getMatchLineup(match).getGast();
				}

				if (team == null) {
					displayError("Team for given match not found. Check matchID.");
					return;
				}

				stats = new MatchStatistics(match, team);
				resultLabel.setText(getPosName(stats.getPlayerFieldPositionAtMinute(player, minute)));

			} catch (NumberFormatException ex) {
				displayError("Match, minute and player must all be numbers. Minutes can be 0, the others not. None of them can be negative");
			} catch (Exception ex) {
				displayError(ex.getMessage());
			}
		} else if (e.getSource() == minButton) {
			boolean isHome = (homeawayBox.getSelectedIndex() == 0);
			int player = -1;
			int match = -1;
			int[] positions;
			try {

				player = Integer.parseInt(playerField.getText().trim());
				match = Integer.parseInt(matchField.getText().trim());
				// Hack
				if ((match <= 0) || (player <= 0)) {
					throw new NumberFormatException();
				}
				positions = getPosArray();
				if (positions == null) {
					displayError("You must select at least one position");
					return;
				}

				if (isHome) {
					team = (MatchLineupTeam) DBManager.instance().getMatchLineup(match).getHeim();
				} else {
					team = (MatchLineupTeam) DBManager.instance().getMatchLineup(match).getGast();
				}

				if (team == null) {
					displayError("Team for given match not found. Check matchID.");
					return;
				}
				
				
				stats = new MatchStatistics(match, team);
				resultLabel.setText(stats.getMinutesPlayedInPositions(player, positions) + "");


			} catch (NumberFormatException ex) {
				displayError("Match player must both be numbers greater than 0.");
			} catch (Exception ex) {
				displayError(ex.getMessage());
			}

		}
	}


	private int[] getPosArray() {
		Vector<Integer> vec = new Vector<Integer>();

		for (int i = 0; i < positions.length ; i++) {
			if (positions[i].isSelected()) {
				vec.add(new Integer(i + ISpielerPosition.keeper));
			}
		}

		if (vec.size() == 0) {
			return null;
		} else {
			int[] arr = new int[vec.size()];
			for (int i = 0; i < vec.size(); i++) {
				arr[i] = vec.get(i).intValue();
			}
			return arr;
		}
	}

	private void displayError(String msg){
		JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);

	}
	private String getPosName(int pos) {

		switch (pos) {
		case ISpielerPosition.keeper :
			return HOVerwaltung.instance().getLanguageString("subs.gk");
		case ISpielerPosition.rightBack :
			return HOVerwaltung.instance().getLanguageString("subs.rb");
		case ISpielerPosition.rightCentralDefender :
			return HOVerwaltung.instance().getLanguageString("subs.rcd");
		case ISpielerPosition.middleCentralDefender :
			return HOVerwaltung.instance().getLanguageString("subs.mcd");
		case ISpielerPosition.leftCentralDefender :
			return HOVerwaltung.instance().getLanguageString("subs.lcd");
		case ISpielerPosition.leftBack :
			return HOVerwaltung.instance().getLanguageString("subs.lb");
		case ISpielerPosition.rightWinger :
			return HOVerwaltung.instance().getLanguageString("subs.rw");
		case ISpielerPosition.rightInnerMidfield :
			return HOVerwaltung.instance().getLanguageString("subs.rim");
		case ISpielerPosition.centralInnerMidfield :
			return HOVerwaltung.instance().getLanguageString("subs.cim");
		case ISpielerPosition.leftInnerMidfield :
			return HOVerwaltung.instance().getLanguageString("subs.lim");
		case ISpielerPosition.leftWinger :
			return HOVerwaltung.instance().getLanguageString("subs.lw");
		case ISpielerPosition.rightForward :
			return HOVerwaltung.instance().getLanguageString("subs.rfw");
		case ISpielerPosition.centralForward :
			return HOVerwaltung.instance().getLanguageString("subs.cfw");
		case ISpielerPosition.leftForward :
			return HOVerwaltung.instance().getLanguageString("subs.lfw");
		case ISpielerPosition.substKeeper :
			return HOVerwaltung.instance().getLanguageString("subs.subgk");
		case ISpielerPosition.substDefender :
			return HOVerwaltung.instance().getLanguageString("subs.subdef");
		case ISpielerPosition.substInnerMidfield :
			return HOVerwaltung.instance().getLanguageString("subs.submid");
		case ISpielerPosition.substWinger :
			return HOVerwaltung.instance().getLanguageString("subs.subwing");
		case ISpielerPosition.substForward :
			return HOVerwaltung.instance().getLanguageString("subs.subfw");
		default :
			return "not on the field";
		}
	}

}
