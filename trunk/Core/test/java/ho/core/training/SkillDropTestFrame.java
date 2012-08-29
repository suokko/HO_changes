package ho.core.training;

import ho.core.constants.player.PlayerSkill;
import ho.core.db.DBManager;
import ho.core.model.player.Spieler;
import ho.core.util.Helper;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class SkillDropTestFrame extends JDialog implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel label = new JLabel("PlayerID");
	JTextField textField = new JTextField();
	JButton button = new JButton("Go");
	JTable table;
	ArrayList<Object[]> dataArray;
	JScrollPane scroll;
	
	String[] columnNames = {"Date", "Season", "Week", "KE", "DF", "PM", "WI", "SC", "PS", "SP"};
	GridBagConstraints constraints;
	
	public SkillDropTestFrame() {
		init();
	}
	
	
	private void init() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Container pane = getContentPane();
		
		constraints = new GridBagConstraints();
		
		pane.setLayout(new GridBagLayout());

		constraints.weightx = 1;
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		
		pane.add(label, constraints);
		constraints.gridx = 2;
		pane.add(textField, constraints);
		constraints.gridx = 1;
		constraints.gridy = 2;
		pane.add(button, constraints);
		button.addActionListener(this);
		
		
		constraints.gridy = 3;
		constraints.gridwidth = 2;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		
		initData(0);
	
		pane.add(scroll, constraints);
		this.setSize(400,600);
		
		
	}
	

	private void initData(int spielerID) {
		dataArray = new ArrayList<Object[]>();
		Spieler player = null;
		
		List<TrainingPerWeek> trainList = TrainingManager.instance().getTrainingWeekList();
		for (TrainingPerWeek train : trainList) {
			player = DBManager.instance().getSpielerFromHrf(train.getHrfId(), spielerID);
			if (player != null) {
				dataArray.add( new Object[]
						{train.getTrainingDate(),
							train.getHattrickSeason(),
							train.getHattrickWeek(),
							getSkill(player, PlayerSkill.KEEPER),
							getSkill(player, PlayerSkill.DEFENDING),
							getSkill(player, PlayerSkill.PLAYMAKING),
							getSkill(player, PlayerSkill.WINGER),
							getSkill(player, PlayerSkill.SCORING),
							getSkill(player, PlayerSkill.PASSING),
							getSkill(player, PlayerSkill.SET_PIECES)
						});
			}
		}
		if (dataArray.size() == 0) {
			Object[] ob = {"0", "0" ,"0" ,"0" ,"0" ,"0" ,"0" ,"0" ,"0" ,"0"};
			dataArray.add(ob);
		}
		table = new JTable(dataArray.toArray(new Object[1][1]), columnNames);
		scroll = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		
		
	}
	
	private String getSkill(Spieler player, int skill) {
		return String.valueOf(Helper.round(player.getValue4Skill4(skill) + player.getSubskill4Pos(skill), 2));
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button) {
			try {
				int spielerId = Integer.parseInt(textField.getText());
				Container pane = getContentPane();
				pane.remove(scroll);
				initData(spielerId);
				pane.add(scroll, constraints);
				pane.validate();
				pane.repaint();
				
				
			} catch (Exception ex) {
				return;
			}
		}
		
	}

}
