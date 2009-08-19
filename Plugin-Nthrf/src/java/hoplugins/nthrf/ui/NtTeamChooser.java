package hoplugins.nthrf.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NtTeamChooser extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private long selectedTeamId = -1;

	public NtTeamChooser(List<String[]> teams) {
		setLayout(new BorderLayout());
		add(new JLabel("Choose your team:"), BorderLayout.NORTH);
		JPanel teamPanel = new JPanel();
		for (Iterator<String[]> i=teams.iterator(); i.hasNext(); ) {
			String[] t = i.next();
			JButton btn = new JButton(t[1] + " (" + t[0] + ")");
			btn.addActionListener(this);
			teamPanel.add(btn);
		}
		add(teamPanel, BorderLayout.CENTER);
		setMinimumSize(new Dimension(220, 180));
	}

	public void actionPerformed(ActionEvent ev) {
		if (ev != null && ev.getSource() instanceof JButton) {
			String btnTxt = ((JButton)ev.getSource()).getText();
			System.out.println("btnTxt: " + btnTxt);
			btnTxt = btnTxt.substring(btnTxt.lastIndexOf("(")+1, btnTxt.lastIndexOf(")"));
			selectedTeamId = Long.parseLong(btnTxt);
			System.out.println("selectedTeamId: " + selectedTeamId);
			this.dispose();
		}
	}

	public long getSelectedTeamId() {
		return selectedTeamId;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String[]> teams = new ArrayList<String[]>();
		teams.add(new String[]{"526156", "Club Team"});
		teams.add(new String[]{"3216", "National Team"});
		NtTeamChooser chooser = new NtTeamChooser(teams);
		chooser.setModal(true);
		chooser.setVisible(true);
		//JOptionPane.showMessageDialog(new JLabel("test"), chooser, "Choose team", JOptionPane.QUESTION_MESSAGE);
		System.out.println("Result is: " + chooser.getSelectedTeamId());
		chooser.dispose();
	}
}
