package ho.module.lineup;

import ho.core.datatype.CBItem;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import plugins.ISpieler;
import plugins.ISpielerPosition;
import plugins.ISubstitution;


public class SubstitutionPanel extends JDialog implements ItemListener{
	//	protected static int PLAYER_POSITION_PANEL_WIDTH = Helper.calcCellWidth(160);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int SUBSTITUTION = 1;
	public static final int PLAYER_SWAP = 3;
	public static final int ORDER_CHANGE = -1;

	private JLabel idLabel = new JLabel("0");
	private JComboBox orderCB;
	private JTextField matchMinuteTF = new JTextField();
	private JComboBox positionCB;
	private JComboBox behaviourCB;
	private JComboBox redCardCB;
	private JComboBox standingCB;
	private JComboBox playerInCB;
	private JComboBox playerOutCB;

	private HashMap<Integer, ISpieler> positionMap; 
	private Lineup lineup;

	// Used for order changes
	SpielerItem inheritPos = new SpielerItem(-1, null);


	private CBItem[] standingValues = {
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalAny"), -1),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalTied"), 0),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalLead"), 1),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalDown"), 2),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalLeadMT1"), 3),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalDownMT1"), 4),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalNotDown"), 5),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalNotLead"), 6),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalLeadMT2"), 7),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.GoalDownMT2"), 8)
	};

	private CBItem[] redcardValues = {
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedIgnore"), -1),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedMy"), 1),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedOpp"), 2),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedMyCD"), 11),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedMyMF"), 12),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedMyFW"), 13),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedMyWB"), 14),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedMyWI"), 15),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedOppCD"), 21),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedOppMF"), 22),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedOppFW"), 23),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedOppWB"), 24),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.RedOppWi"), 25),
	};


	private CBItem[] orderValues = {
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.TypeSub"), 1),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.TypeOrder"), -1),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.TypeSwap"), 3), 
	};

	private CBItem[] behaviourValues = {
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.BehNoChange"), -1),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.BehNormal"), 0),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.BehOffensive"), 1),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.BehDefensive"), 2),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.BehToMid"), 3),
			new CBItem(HOVerwaltung.instance().getLanguageString("subs.BehToWi"), 4)
	};


	public SubstitutionPanel(String panelId) {
		this(null, panelId);
	}

	public SubstitutionPanel(ISubstitution sub, String panelId) {
		inheritPos.setText(HOVerwaltung.instance().getLanguageString("subs.BehNoChange"));
		initComponents();
		idLabel.setText(panelId);
		updatePositions();
		updatePlayerLists();
		if (sub != null) {
			setValues(sub);
		}


	}

	private void setValues(ISubstitution sub) {

		orderCB.setSelectedItem(getMatchingItem(orderValues, sub.getOrderType().getId()));
		redCardCB.setSelectedItem(getMatchingItem(redcardValues, sub.getCard()));
		matchMinuteTF.setText(""+sub.getMatchMinuteCriteria());



		//
		//		// positionCB
		//		
		//		private JComboBox positionCB;
		//		private JComboBox behaviourCB;
		//		private JComboBox redCardCB;
		//		private JComboBox standingCB;
		//		private JComboBox typeCB;
		//		private JComboBox playerInCB;
		//		private JComboBox playerOutCB;



	}

	private CBItem getMatchingItem(CBItem[] arr, int id) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].getId() == id) {
				return arr[i];
			}
		}
		return null;
	}

	private void initComponents() {

		JPanel mainPanel = new JPanel(new GridBagLayout());


		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1.0;
		constraints.weighty = 0;
		constraints.insets = new Insets(1, 2, 1, 2);


		constraints.gridx = 0;
		constraints.gridy = 0;

		idLabel = new JLabel("-1");
		mainPanel.add(idLabel, constraints);


		constraints.gridy = 5;
		mainPanel.add(new JLabel(HOVerwaltung.instance().getLanguageString("subs.order")), constraints);
		constraints.gridx = 5;
		orderCB = new JComboBox(orderValues);
		orderCB.addItemListener(this);
		mainPanel.add(orderCB, constraints);


		constraints.gridy = 10;
		constraints.gridx = 0;
		mainPanel.add(new JLabel(HOVerwaltung.instance().getLanguageString("subs.in")), constraints);
		constraints.gridx = 5;
		playerInCB = new JComboBox();
		playerInCB.addItemListener(this);
		mainPanel.add(playerInCB, constraints);

		constraints.gridy = 15;
		constraints.gridx = 0;
		mainPanel.add(new JLabel(HOVerwaltung.instance().getLanguageString("subs.out")), constraints);
		constraints.gridx = 5;
		playerOutCB = new JComboBox();
		playerOutCB.addItemListener(this);
		mainPanel.add(playerOutCB, constraints);

		constraints.gridy = 20;
		constraints.gridx = 0;
		mainPanel.add(new JLabel(HOVerwaltung.instance().getLanguageString("subs.minute")), constraints);
		constraints.gridx = 5;
		matchMinuteTF = new JTextField();
//		matchMinuteTF.addActionListener(this);
		mainPanel.add(matchMinuteTF, constraints);

		constraints.gridy = 25;
		constraints.gridx = 0;
		mainPanel.add(new JLabel(HOVerwaltung.instance().getLanguageString("subs.position")), constraints);
		constraints.gridx = 5;
		positionCB = new JComboBox();
		positionCB.addItemListener(this);
		mainPanel.add(positionCB, constraints);

		constraints.gridy = 30;
		constraints.gridx = 0;
		mainPanel.add(new JLabel(HOVerwaltung.instance().getLanguageString("subs.behaviour")), constraints);
		constraints.gridx = 5;
		behaviourCB = new JComboBox(behaviourValues);
		behaviourCB.addItemListener(this);
		mainPanel.add(behaviourCB, constraints);

		constraints.gridy = 35;
		constraints.gridx = 0;
		mainPanel.add(new JLabel(HOVerwaltung.instance().getLanguageString("subs.redcards")), constraints);
		constraints.gridx = 5;
		redCardCB = new JComboBox(redcardValues);
		redCardCB.addItemListener(this);
		mainPanel.add(redCardCB, constraints);

		constraints.gridy = 40;
		constraints.gridx = 0;
		mainPanel.add(new JLabel(HOVerwaltung.instance().getLanguageString("subs.standing")), constraints);
		constraints.gridx = 5;
		standingCB = new JComboBox(standingValues);
		standingCB.addItemListener(this);
		mainPanel.add(standingCB, constraints);

		this.add(mainPanel);
	}


	private void updatePositions() {

		lineup = HOVerwaltung.instance().getModel().getAufstellung();

		positionMap = new HashMap<Integer, ISpieler>(22);
		for (int i = ISpielerPosition.startLineup ; i <= ISpielerPosition.substForward; i++) {
			positionMap.put(new Integer(i), lineup.getPlayerByPositionID(i));
		}
	}


	private void updatePlayerLists() {
		DefaultComboBoxModel inModel = (DefaultComboBoxModel)playerInCB.getModel();
		DefaultComboBoxModel outModel = (DefaultComboBoxModel)playerOutCB.getModel();
		DefaultComboBoxModel posModel = (DefaultComboBoxModel)positionCB.getModel();

		inModel.removeAllElements();
		outModel.removeAllElements();
		posModel.removeAllElements();

		positionCB.setEnabled(true);
		playerInCB.setEnabled(true);
		SpielerItem[] ins = null;
		SpielerItem[] outs = null;
		SpielerItem[] positions = null;


		switch (getSelectedValue(orderCB)) {
		case SUBSTITUTION : 
			ins = getFieldPositions(ISpielerPosition.keeper, ISpielerPosition.startReserves);
			outs = getFieldPositions(ISpielerPosition.startReserves, ISpielerPosition.substForward + 1);
			positions = ins;
			break;
		case ORDER_CHANGE : 
			playerInCB.setEnabled(false);
			ins = getFieldPositions(ISpielerPosition.keeper, ISpielerPosition.startReserves);
			// Positions should only be the empty ones, and the same.
			positions = getFreePosItems();
			break;
		case PLAYER_SWAP :
			ins = getFieldPositions(ISpielerPosition.keeper, ISpielerPosition.startReserves);
			outs = getFieldPositions(ISpielerPosition.keeper, ISpielerPosition.startReserves);
			positionCB.setEnabled(false);
			break;
		}

		// Positions should have identical list to the ins, always
		if (ins != null) {
			for (int i = 0; i < ins.length; i++ ) {
				if (ins[i] != null) {
					inModel.addElement(ins[i]);
					posModel.addElement(ins[i]);
				}
			}
		}
		if (outs != null) {
			for (int i = 0; i < outs.length; i++ ) {
				if (outs[i] != null) {
					outModel.addElement(outs[i]);
				}
			}
		}



	}

	private SpielerItem[] getFreePosItems() {
		List<SpielerItem> list = new ArrayList<SpielerItem>();
		for (int i = ISpielerPosition.keeper; i < ISpielerPosition.startReserves; i++) {
			if (positionMap.get(i) == null) {
				list.add(new SpielerItem(i, positionMap.get(i)));
			}
		}
		SpielerItem[] arr = new SpielerItem[list.size() + 1];
		arr[0] = inheritPos;
		
		//Remember to shift one step right in array as we already added one item
		for (int i = 0; i < list.size() ; i++) {
			arr[i+1] = list.get(i);
		}
		
		return arr;
	}

	private SpielerItem[] getFieldPositions(int start, int end) {

		SpielerItem[] arr = new SpielerItem[14];
		int a = 0;
		for (int i = start; i < end; i++) {
			arr[a] = new SpielerItem(i, positionMap.get(i));
			a++;
		}

		return arr;
	}


	private int getSelectedValue(JComboBox box) {
		return ((CBItem)box.getSelectedItem()).getId();
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
			return HOVerwaltung.instance().getLanguageString("subs.rb");
		case ISpielerPosition.rightWinger :
			return HOVerwaltung.instance().getLanguageString("subs.rw");
		case ISpielerPosition.rightInnerMidfield :
			return HOVerwaltung.instance().getLanguageString("subs.rim");
		case ISpielerPosition.centralInnerMidfield :
			return HOVerwaltung.instance().getLanguageString("subs.cim");
		case ISpielerPosition.leftInnerMidfield :
			return HOVerwaltung.instance().getLanguageString("subs.lim");
		case ISpielerPosition.leftWinger :
			return HOVerwaltung.instance().getLanguageString("subs.rw");
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
			return "";
		}
	}

	private class SpielerItem {
		int position;
		String name = "";
		ISpieler spieler;
		String text = "";

		SpielerItem(int pos, ISpieler spieler) {
			this.spieler = spieler;
			position = pos;
			if (this.spieler != null) {
				name = this.spieler.getName();
			}
		}

		public void setText(String text) {
			this.text = text; 
		}

		public int getPosition() {
			return position;
		}

		public ISpieler getSpieler() {
			return spieler;
		}

		@Override
		public String toString() {
			if (text.length() > 0) {
				return text;
			} else {
				return getPosName(position) + " - " + name;
			}
		}
	}

	

	public void itemStateChanged(ItemEvent arg0) {
		
		
		
	}

}

