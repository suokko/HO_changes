package ho.module.transfer.test;

import ho.core.db.DBManager;
import ho.core.gui.HOMainFrame;
import ho.core.model.HOVerwaltung;
import ho.core.model.player.Spieler;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextArea;

public class TestDialog extends JDialog {

	private static final long serialVersionUID = -2583288103393550257L;
	private JTextArea textArea;
	private JComboBox cbox;

	public TestDialog() {
		super(HOMainFrame.instance(), ModalityType.APPLICATION_MODAL);
		initComponents();
		pack();
	}

	private void initComponents() {
		getContentPane().setLayout(new BorderLayout());
		List<Spieler> spieler = HOVerwaltung.instance().getModel().getAllSpieler();
		Collections.sort(spieler, new Comparator<Spieler>() {

			@Override
			public int compare(Spieler o1, Spieler o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		CBItm[] array = new CBItm[spieler.size()];
		for (int i = 0; i < spieler.size(); i++) {
			array[i] = new CBItm(spieler.get(i));
		}

		this.cbox = new JComboBox(array);
		this.cbox.setSelectedItem(null);
		getContentPane().add(this.cbox, BorderLayout.NORTH);

		this.textArea = new JTextArea("", 30, 60);
		getContentPane().add(this.textArea, BorderLayout.CENTER);

		this.cbox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				spielerChanged();
			}
		});
	}

	private void spielerChanged() {
		if (this.cbox.getSelectedItem() != null) {
			Spieler spieler = ((CBItm) this.cbox.getSelectedItem()).getSpieler();
			List<Wage> wages = Wage.getWages(spieler.getSpielerID());

			StringBuilder sb = new StringBuilder();
			sb.append("Age   -   Wage" + "\n");
			for (Wage wage : wages) {
				sb.append(wage.getAge() + " - " + wage.getWage() + "\n");
			}
			sb.append("\n\n");
			sb.append("Bought at: " + getByingDate(spieler.getSpielerID()).get(0));
			this.textArea.setText(sb.toString());
		} else {
			this.textArea.setText("");
		}
	}

	private class CBItm {
		Spieler spieler;

		CBItm(Spieler spieler) {
			this.spieler = spieler;
		}

		@Override
		public String toString() {
			return spieler.getName();
		}

		public Spieler getSpieler() {
			return this.spieler;
		}
	}
	
	private List<Date> getByingDate(int playerId) {
		List<Date> list = new ArrayList<Date>();
		
		String query = "SELECT * FROM transfer WHERE playerid=" + playerId + " AND buyerid=" +
				HOVerwaltung.instance().getModel().getBasics().getTeamId();
		ResultSet rs = DBManager.instance().getAdapter().executeQuery(query);
		try {
			while (rs.next()) {
				list.add(new Date(rs.getTimestamp("date").getTime()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
