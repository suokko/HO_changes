package ho.module.specialEvents;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import plugins.IDebugWindow;
import plugins.IHOMiniModel;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.HOLogger;

public class FilterPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 3213362575191990865L;

	

	public static final int SAISONALL = 1;
	public static final int SAISONLAST2 = 2;
	public static final int SAISONACT = 3;

	Object matches[][];
	private static ButtonGroup saisonGroup = new ButtonGroup();
	private static JRadioButton saisonAct;
	private static JRadioButton saisonLastTwo;
	private static JRadioButton saisonAll;
	private static ButtonGroup gameTypGroup = new ButtonGroup();
	private static JRadioButton gameTypAll;
	private static JRadioButton gameTypSE;
	private static JCheckBox friendlies;
	private static JCheckBox specialtySE;
	private static JCheckBox weatherSE;
	private static JCheckBox counter;
	private static JCheckBox freekick;
	private static JCheckBox penalty;
	private static JCheckBox ifk;
	private static JCheckBox longshot;

	private class OptionFieldActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			SpecialEventsPanel.newTableModel();
		}
	}
	
	public FilterPanel() {
		initialize();
	}

	public static int getSaisonTyp() {
		int saison = SAISONALL;

		if (saisonAct.isSelected())
			saison = SAISONACT;
		else if (saisonAll.isSelected())
			saison = SAISONALL;
		else if (saisonLastTwo.isSelected())
			saison = SAISONLAST2;

		return saison;
	}

	public void initialize() {
		// this.props = props;
		try {
			gameTypSE = new JRadioButton(HOVerwaltung.instance().getLanguageString("SpieleMitSEs"));
			gameTypSE.addActionListener(new OptionFieldActionListener());
			gameTypAll = new JRadioButton(HOVerwaltung.instance().getLanguageString("AlleSpiele"));
			gameTypAll.addActionListener(new OptionFieldActionListener());
			gameTypGroup.add(gameTypSE);
			gameTypGroup.add(gameTypAll);
			gameTypSE.setSelected(true);
			saisonAct = new JRadioButton(HOVerwaltung.instance().getLanguageString("AktSaison"));
			saisonAct.addActionListener(new OptionFieldActionListener());
			saisonLastTwo = new JRadioButton(HOVerwaltung.instance().getLanguageString("2Saison"));
			saisonLastTwo.addActionListener(new OptionFieldActionListener());
			saisonAll = new JRadioButton(HOVerwaltung.instance().getLanguageString("AllSaison"));
			saisonAll.addActionListener(new OptionFieldActionListener());
			saisonGroup.add(saisonAct);
			saisonGroup.add(saisonLastTwo);
			saisonGroup.add(saisonAll);
			saisonLastTwo.setSelected(true);
			friendlies = new JCheckBox(HOVerwaltung.instance().getLanguageString("dbcleanup.ownFriendlies"));
			friendlies.setSelected(true);
			friendlies.addActionListener(this);
			specialtySE = new JCheckBox(HOVerwaltung.instance().getLanguageString("SPECIALTYSE"));
			specialtySE.setSelected(true);
			specialtySE.addActionListener(this);
			weatherSE = new JCheckBox(HOVerwaltung.instance().getLanguageString("WEATHERSE"));
			weatherSE.setSelected(true);
			weatherSE.addActionListener(this);
			counter = new JCheckBox(HOVerwaltung.instance().getLanguageString("TT_Counter"));
			counter.setSelected(true);
			counter.addActionListener(this);
			freekick = new JCheckBox(HOVerwaltung.instance().getLanguageString("highlight_freekick"));
			freekick.setSelected(true);
			freekick.addActionListener(this);
			penalty = new JCheckBox(HOVerwaltung.instance().getLanguageString("highlight_penalty"));
			penalty.setSelected(true);
			penalty.addActionListener(this);
			ifk = new JCheckBox(HOVerwaltung.instance().getLanguageString("highlight_freekick") + " " + HOVerwaltung.instance().getLanguageString("indirect"));
			ifk.setSelected(true);
			ifk.addActionListener(this);
			longshot = new JCheckBox(HOVerwaltung.instance().getLanguageString("Tactic.LongShots"));
			longshot.setSelected(true);
			longshot.addActionListener(this);

			setLayout(new BorderLayout());

			JPanel filterTop = new JPanel();

			filterTop.add(gameTypSE);
			filterTop.add(gameTypAll);
			filterTop.add(new JLabel("                           "));
			filterTop.add(saisonAct);
			filterTop.add(saisonLastTwo);
			filterTop.add(saisonAll);
			filterTop.add(new JLabel("                           "));
			filterTop.add(friendlies);

			JPanel filterBottom = new JPanel();

			filterBottom.add(specialtySE);
			filterBottom.add(weatherSE);
			filterBottom.add(counter);
			filterBottom.add(freekick);
			filterBottom.add(penalty);
			filterBottom.add(ifk);
			filterBottom.add(longshot);

			add(BorderLayout.NORTH, filterTop);
			add(BorderLayout.SOUTH, filterBottom);
		} catch (Exception exr) {
			HOLogger.instance().error(this.getClass(), exr);
		}
	}

	public static JRadioButton getGameTypAll() {
		return gameTypAll;
	}

	public static JRadioButton getGameTypSE() {
		return gameTypSE;
	}

	public static JRadioButton getSaisonAct() {
		return saisonAct;
	}

	public static JRadioButton getSaisonLastTwo() {
		return saisonLastTwo;
	}

	public static JRadioButton getSaisonAll() {
		return saisonAll;
	}

	// public static JCheckBox getFriendlies()
	// {
	// return friendlies;
	// }
	//
	// public static JCheckBox getSpecialtySE()
	// {
	// return specialtySE;
	// }
	//
	// public static JCheckBox getWeatherSE()
	// {
	// return weatherSE;
	// }
	//
	// public static JCheckBox getCounter()
	// {
	// return counter;
	// }

	public static boolean showFriendlies() {
		return friendlies.isSelected();
	}

	public static boolean showSpecialtySE() {
		return specialtySE.isSelected();
	}

	public static boolean showWeatherSE() {
		return weatherSE.isSelected();
	}

	public static boolean showCounter() {
		return counter.isSelected();
	}

	public static boolean showFreekick() {
		return freekick.isSelected();
	}

	public static boolean showPenalty() {
		return penalty.isSelected();
	}

	public static boolean showIFK() {
		return ifk.isSelected();
	}

	public static boolean showLongShot() {
		return longshot.isSelected();
	}

	public void actionPerformed(ActionEvent actionevent) {
		SpecialEventsPanel.newTableModel();
	}

}
