// %2927626437:de.hattrickorganizer.gui.lineup%
package de.hattrickorganizer.gui.lineup2;

import gui.HOColorName;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import plugins.ISpieler;
import plugins.ISpielerPosition;
import de.hattrickorganizer.gui.Updateable;
import de.hattrickorganizer.gui.lineup.LineupAssistantSelectorOverlay;
import de.hattrickorganizer.gui.model.CBItem;
import de.hattrickorganizer.gui.model.SpielerCBItem;
import de.hattrickorganizer.gui.model.SpielerCBItemRenderer;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.gui.templates.SpielerLabelEntry;
import de.hattrickorganizer.gui.theme.ImageUtilities;
import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.Lineup;
import de.hattrickorganizer.model.Spieler;
import de.hattrickorganizer.model.SpielerPosition;
import de.hattrickorganizer.tools.Helper;

/**
 * Panel, in dem die Spielerposition dargestellt wird und geändert werden kann
 */
class PlayerPositionPanel extends ImagePanel implements ItemListener {
	private static final long serialVersionUID = 3121389904282953L;
	private static int PLAYER_POSITION_PANEL_WIDTH = Helper.calcCellWidth(160);
	private static int PLAYER_POSITION_PANEL_HEIGHT_FULL = Helper.calcCellWidth(80);
	// Used for positions with no tactics box
	private static int PLAYER_POSITION_PANEL_HEIGHT_REDUCED = Helper.calcCellWidth(50);
	private static int MINI_PLAYER_POSITION_PANEL_WIDTH = Helper.calcCellWidth(120);
	private static int MINI_PLAYER_POSITION_PANEL_HEIGHT = Helper.calcCellWidth(32);
	private static SpielerCBItem m_clNullSpieler = new SpielerCBItem("", 0f, null, true);
	private final JComboBox playerComboBox = new JComboBox();
	// ComboBox for individual orders (offensive, towards middle...)
	private final JComboBox tacticComboBox = new JComboBox();
	private final JLabel positionLabel = new JLabel();
	// Für Minimized
	private final JLabel m_jlPlayer = new JLabel();
	private final SpielerCBItem m_clSelectedPlayer = new SpielerCBItem("", 0f, null, true);
	private Updateable m_clUpdater;
	// The ID of the player's position
	private int positionID = -1;
	private int playerId = -1;
	private int tacticOrder = -1;
	private final GridBagLayout layout = new GridBagLayout();
	private JLayeredPane layeredPane = new JLayeredPane();
	private Lineup lineup;

	protected PlayerPositionPanel(Updateable updater, Lineup lineup, int positionsID) {
		super(false);

		this.lineup = lineup;
		m_clUpdater = updater;
		this.positionID = positionsID;

		setOpaque(true);

		initTaktik(null);
		initLabel();
		initComponents(true);
	}

	protected int getPositionsID() {
		return this.positionID;
	}

	/**
	 * Gibt den aktuellen Spieler auf dieser Position zurück, oder null, wenn
	 * keiner gewählt wurde
	 * 
	 * @return
	 */
	private plugins.ISpieler getSelectedPlayer() {
		final Object obj = this.playerComboBox.getSelectedItem();

		if ((obj != null) && obj instanceof SpielerCBItem) {
			return ((SpielerCBItem) obj).getSpieler();
		}

		return null;
	}

	/**
	 * Gibt die Taktik an
	 * 
	 * @return
	 */
	private byte getTactic() {
		if (this.tacticComboBox.getSelectedItem() == null) {
			return -1;
		}
		return (byte) ((CBItem) this.tacticComboBox.getSelectedItem()).getId();
	}

	/**
	 * Erzeugt die Komponenten, Die CB für die Spieler und den Listener nicht
	 * vergessen!
	 * 
	 * @param aenderbar
	 */
	private void initComponents(boolean aenderbar) {
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1.0;
		constraints.weighty = 0;
		constraints.insets = new Insets(1, 2, 1, 2);

		this.layeredPane.setLayout(layout);
		// No gaps around the layeredpane.
		FlowLayout fl = new FlowLayout();
		fl.setHgap(0);
		fl.setVgap(0);
		fl.setAlignment(FlowLayout.CENTER);
		setLayout(fl);

		setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		this.layeredPane.add(this.positionLabel, constraints, 1);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		this.playerComboBox.setMaximumRowCount(15);
		this.playerComboBox.setRenderer(new SpielerCBItemRenderer());
		this.layeredPane.add(this.playerComboBox, constraints, 1);

		if (!aenderbar) {
			this.playerComboBox.setEnabled(false);
		}

		this.playerComboBox.setBackground(ThemeManager.getColor(HOColorName.TABLEENTRY_BG));// Color.white

		// Nur anzeigen, wenn mehr als eine Taktik möglich ist
		if (this.tacticComboBox.getItemCount() > 1) {
			constraints.gridx = 0;
			constraints.gridy = 2;
			constraints.gridwidth = 2;
			if (!aenderbar) {
				this.tacticComboBox.setEnabled(false);
			}

			this.tacticComboBox.setBackground(this.playerComboBox.getBackground());
			this.layeredPane.add(this.tacticComboBox, constraints, 1);
			setPreferredSize(new Dimension(PLAYER_POSITION_PANEL_WIDTH, PLAYER_POSITION_PANEL_HEIGHT_FULL));
		} else {
			setPreferredSize(new Dimension(PLAYER_POSITION_PANEL_WIDTH, PLAYER_POSITION_PANEL_HEIGHT_REDUCED));
		}
		this.layeredPane.setPreferredSize(getPreferredSize());
		add(this.layeredPane);
	}

	public void itemStateChanged(java.awt.event.ItemEvent itemEvent) {
		if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
			final plugins.ISpieler spieler = getSelectedPlayer();

			// Spieler setzen
			if (itemEvent.getSource().equals(this.playerComboBox)) {
				// Standart
				if (this.positionID == ISpielerPosition.setPieces) {
					if (spieler != null) {
						this.lineup.setKicker(spieler.getSpielerID());
					} else {
						this.lineup.setKicker(0);
					}
				}
				// Spielführer
				else if (this.positionID == ISpielerPosition.captain) {
					if (spieler != null) {
						this.lineup.setKapitaen(spieler.getSpielerID());
					} else {
						this.lineup.setKapitaen(0);
					}
				}
				// Andere
				else {
					if (spieler != null) {
						this.lineup.setSpielerAtPosition(this.positionID, spieler.getSpielerID());
					} else {
						this.lineup.setSpielerAtPosition(this.positionID, 0);
					}
				}

				// CBFarben anpassen
				if (spieler != null) {
					this.playerComboBox.setForeground(SpielerLabelEntry.getForegroundForSpieler(spieler));
				}

				// Taktikwerte anpassen
				setTaktik(getTactic(), spieler);
			} else if (itemEvent.getSource().equals(this.tacticComboBox)) {
				this.lineup.getPositionById(this.positionID).setTaktik(getTactic());
			}

			// Aktualisierung der Tabellen
			if (spieler != null) {
				// HOMainFrame.instance().setActualSpieler(spieler.getSpielerID());
			}
			// Alle anderen Position aktualisieren
			m_clUpdater.update();
		}
	}

	/**
	 * Erneuert die Daten in den Komponenten
	 * 
	 * @param spieler
	 */
	public void refresh(Vector<ISpieler> spieler) {
		Spieler aktuellerSpieler = null;
		playerId = -1;
		if (this.positionID == ISpielerPosition.setPieces) {
			aktuellerSpieler = HOVerwaltung.instance().getModel().getSpieler(this.lineup.getKicker());
			if (aktuellerSpieler != null) {
				playerId = aktuellerSpieler.getSpielerID();
			}
			tacticOrder = -1;

			// Filter keeper from the spieler vector (can't be sp taker)
			// Make sure the incoming spieler list is not modified, it
			// seems to visit the captain position later.

			ISpieler keeper = this.lineup.getPlayerByPositionID(ISpielerPosition.keeper);
			if (keeper != null) {
				Vector<ISpieler> tmpSpieler = new Vector<ISpieler>(spieler.size() - 1);
				for (int i = 0; i < spieler.size(); i++) {
					if (keeper.getSpielerID() != spieler.get(i).getSpielerID()) {
						tmpSpieler.add(spieler.get(i));
					}
				}
				spieler = tmpSpieler;
			}
		} else if (this.positionID == ISpielerPosition.captain) {
			aktuellerSpieler = HOVerwaltung.instance().getModel().getSpieler(this.lineup.getKapitaen());
			if (aktuellerSpieler != null) {
				playerId = aktuellerSpieler.getSpielerID();
			}
			tacticOrder = -1;
		} else {
			// Aktuell aufgestellten Spieler holen
			final SpielerPosition position = this.lineup.getPositionById(this.positionID);

			if (position != null) {
				aktuellerSpieler = HOVerwaltung.instance().getModel().getSpieler(position.getSpielerId());

				if (aktuellerSpieler != null) {
					this.playerComboBox.setEnabled(true); // To be sure
					playerId = aktuellerSpieler.getSpielerID();
				} else {
					// We want to disable the player selection box if there is
					// already 11 players on the field and this is an on field
					// position.
					if ((this.lineup.hasFreePosition() == false)
							&& (this.positionID >= ISpielerPosition.keeper)
							&& (this.positionID < ISpielerPosition.startReserves)) {
						this.playerComboBox.setEnabled(false);
					} else {
						// And enable empty positions if there is room in the
						// lineup
						this.playerComboBox.setEnabled(true);
					}
				}
				tacticOrder = position.getTaktik();
				setTaktik(position.getTaktik(), aktuellerSpieler);
			}
		}

		setSpielerListe(spieler, aktuellerSpieler);

		initLabel();

		repaint();
	}

	/**
	 * Setzt die Liste der möglichen Spieler für diese Position und den aktuell
	 * gewählten Spieler
	 * 
	 * @param spielerListe
	 * @param aktuellerSpieler
	 */
	private void setSpielerListe(Vector<ISpieler> spielerListe, Spieler aktuellerSpieler) {
		// Listener entfernen
		this.playerComboBox.removeItemListener(this);

		final DefaultComboBoxModel cbmodel = ((DefaultComboBoxModel) this.playerComboBox.getModel());

		// Alle Items entfernen
		cbmodel.removeAllElements();

		// Aktueller Spieler
		if (aktuellerSpieler != null) {
			cbmodel.addElement(createSpielerCBItem(aktuellerSpieler));
		}

		// Kein Spieler
		cbmodel.addElement(m_clNullSpieler);

		// Spielerliste sortieren
		SpielerCBItem[] cbItems = new SpielerCBItem[spielerListe.size()];

		for (int i = 0; i < spielerListe.size(); i++) {
			cbItems[i] = createSpielerCBItem(((Spieler) spielerListe.get(i)));
		}

		java.util.Arrays.sort(cbItems);

		for (int i = 0; i < cbItems.length; i++) {
			// Alle anderen Spieler
			cbmodel.addElement(cbItems[i]);
		}

		// CBFarben anpassen
		if (aktuellerSpieler != null) {
			this.playerComboBox.setForeground(SpielerLabelEntry.getForegroundForSpieler(aktuellerSpieler));
		}

		// Speicher freigeben
		cbItems = null;

		// Listener wieder hinzu
		this.playerComboBox.addItemListener(this);

		// Minimized
		if ((m_clSelectedPlayer != null) && (m_clSelectedPlayer.getSpieler() != null)) {
			m_jlPlayer.setText(m_clSelectedPlayer.getSpieler().getName());
			m_jlPlayer.setIcon(ImageUtilities.getImage4Position(
					this.lineup.getPositionBySpielerId(m_clSelectedPlayer.getSpieler().getSpielerID()),
					m_clSelectedPlayer.getSpieler().getTrikotnummer()));
		} else {
			m_jlPlayer.setText("");
			m_jlPlayer.setIcon(null);
		}

		if (aktuellerSpieler != null) {
			setTaktik(getTactic(), aktuellerSpieler);
		}
	}

	/**
	 * Setzt die aktuelle Taktik
	 * 
	 * @param taktik
	 * @param aktuellerSpieler
	 */
	private void setTaktik(byte taktik, plugins.ISpieler aktuellerSpieler) {
		// Listener entfernen
		this.tacticComboBox.removeItemListener(this);

		// Taktik neu füllen!
		initTaktik(aktuellerSpieler);

		// Suche nach der Taktik
		Helper.markierenComboBox(this.tacticComboBox, taktik);

		// Listener hinzu
		this.tacticComboBox.addItemListener(this);
	}

	/**
	 * Setzt das Label
	 */
	private void initLabel() {

		if (this.positionID == ISpielerPosition.setPieces) {
			this.positionLabel.setText(getLanguageString("Standards"));
		} else if (this.positionID == ISpielerPosition.captain) {
			this.positionLabel.setText(getLanguageString("Spielfuehrer"));
		} else {
			final SpielerPosition position = this.lineup.getPositionById(this.positionID);

			if (position != null) {
				// Reserve
				final String nameForPosition = SpielerPosition.getNameForPosition(position.getPosition());

				if ((this.tacticComboBox.getItemCount() == 1)
						&& (position.getId() != ISpielerPosition.keeper)) {
					// special naming for reserve defender
					if (position.getId() == ISpielerPosition.substDefender) {
						this.positionLabel.setText(getLanguageString("Reserve") + " "
								+ getLanguageString("defender"));
					} else {
						this.positionLabel.setText(getLanguageString("Reserve") + " " + nameForPosition);
					}
				} else {
					this.positionLabel.setText(nameForPosition);
				}
			}
		}

		// Minimized
		if ((m_clSelectedPlayer != null) && (m_clSelectedPlayer.getSpieler() != null)) {
			m_jlPlayer.setText(m_clSelectedPlayer.getSpieler().getName());
			m_jlPlayer.setIcon(ImageUtilities.getImage4Position(
					this.lineup.getPositionBySpielerId(m_clSelectedPlayer.getSpieler().getSpielerID()),
					m_clSelectedPlayer.getSpieler().getTrikotnummer()));
		} else {
			m_jlPlayer.setText("");
			m_jlPlayer.setIcon(null);
		}
	}

	/**
	 * Setzt die Taktik je nach SpielerPosition
	 * 
	 * @param aktuellerSpieler
	 */
	private void initTaktik(plugins.ISpieler aktuellerSpieler) {
		this.tacticComboBox.removeAllItems();

		switch (this.positionID) {
		case ISpielerPosition.keeper: {
			this.tacticComboBox.addItem(new CBItem(getLanguageString("Normal"), ISpielerPosition.NORMAL));
			break;
		}

		case ISpielerPosition.rightBack:
		case ISpielerPosition.leftBack: {
			addTactic(aktuellerSpieler, getLanguageString("Normal"), ISpielerPosition.NORMAL);
			addTactic(aktuellerSpieler, getLanguageString("Offensiv"), ISpielerPosition.OFFENSIVE);
			addTactic(aktuellerSpieler, getLanguageString("Defensiv"), ISpielerPosition.DEFENSIVE);
			addTactic(aktuellerSpieler, getLanguageString("zurMitte"), ISpielerPosition.TOWARDS_MIDDLE);
			break;
		}

		case ISpielerPosition.rightCentralDefender:
		case ISpielerPosition.leftCentralDefender: {
			addTactic(aktuellerSpieler, getLanguageString("Normal"), ISpielerPosition.NORMAL);
			addTactic(aktuellerSpieler, getLanguageString("Offensiv"), ISpielerPosition.OFFENSIVE);
			addTactic(aktuellerSpieler, getLanguageString("nachAussen"), ISpielerPosition.TOWARDS_WING);
			break;
		}

		case ISpielerPosition.middleCentralDefender: {
			addTactic(aktuellerSpieler, getLanguageString("Normal"), ISpielerPosition.NORMAL);
			addTactic(aktuellerSpieler, getLanguageString("Offensiv"), ISpielerPosition.OFFENSIVE);
			break;
		}

		case ISpielerPosition.rightInnerMidfield:
		case ISpielerPosition.leftInnerMidfield: {
			addTactic(aktuellerSpieler, getLanguageString("Normal"), ISpielerPosition.NORMAL);
			addTactic(aktuellerSpieler, getLanguageString("Offensiv"), ISpielerPosition.OFFENSIVE);
			addTactic(aktuellerSpieler, getLanguageString("Defensiv"), ISpielerPosition.DEFENSIVE);
			addTactic(aktuellerSpieler, getLanguageString("nachAussen"), ISpielerPosition.TOWARDS_WING);
			break;
		}

		case ISpielerPosition.centralInnerMidfield: {
			addTactic(aktuellerSpieler, getLanguageString("Normal"), ISpielerPosition.NORMAL);
			addTactic(aktuellerSpieler, getLanguageString("Offensiv"), ISpielerPosition.OFFENSIVE);
			addTactic(aktuellerSpieler, getLanguageString("Defensiv"), ISpielerPosition.DEFENSIVE);
			break;
		}

		case ISpielerPosition.leftWinger:
		case ISpielerPosition.rightWinger: {
			addTactic(aktuellerSpieler, getLanguageString("Normal"), ISpielerPosition.NORMAL);
			addTactic(aktuellerSpieler, getLanguageString("Offensiv"), ISpielerPosition.OFFENSIVE);
			addTactic(aktuellerSpieler, getLanguageString("Defensiv"), ISpielerPosition.DEFENSIVE);
			addTactic(aktuellerSpieler, getLanguageString("zurMitte"), ISpielerPosition.TOWARDS_MIDDLE);
			break;
		}

		case ISpielerPosition.rightForward:
		case ISpielerPosition.leftForward: {
			addTactic(aktuellerSpieler, getLanguageString("Normal"), ISpielerPosition.NORMAL);
			addTactic(aktuellerSpieler, getLanguageString("Defensiv"), ISpielerPosition.DEFENSIVE);
			addTactic(aktuellerSpieler, getLanguageString("nachAussen"), ISpielerPosition.TOWARDS_WING);
			break;
		}

		case ISpielerPosition.centralForward: {
			addTactic(aktuellerSpieler, getLanguageString("Normal"), ISpielerPosition.NORMAL);
			addTactic(aktuellerSpieler, getLanguageString("Defensiv"), ISpielerPosition.DEFENSIVE);
			break;
		}

		case ISpielerPosition.substDefender:
		case ISpielerPosition.substForward:
		case ISpielerPosition.substInnerMidfield:
		case ISpielerPosition.substKeeper:
		case ISpielerPosition.substWinger: {
			this.tacticComboBox.addItem(new CBItem(getLanguageString("Normal"), ISpielerPosition.NORMAL));
			break;
		}

		default:
			this.tacticComboBox.addItem(new CBItem(getLanguageString("Normal"), ISpielerPosition.NORMAL));
		}
	}

	private void addTactic(ISpieler player, String text, byte playerPosition) {
		if (player != null) {
			text += " ("
					+ player.calcPosValue(SpielerPosition.getPosition(this.positionID, playerPosition), true)
					+ ")";
		}
		this.tacticComboBox.addItem(new CBItem(text, playerPosition));
	}

	private SpielerCBItem createSpielerCBItem(Spieler spieler) {
		SpielerCBItem item = new SpielerCBItem("", 0f, null, true);
		// Create a string with just initial as first name
		String spielerName = spieler.getName().substring(0, 1) + "."
				+ spieler.getName().substring(spieler.getName().indexOf(" ") + 1);

		if (this.positionID == ISpielerPosition.setPieces) {
			item.setValues(spielerName,
					spieler.getStandards() + spieler.getSubskill4SkillWithOffset(ISpieler.SKILL_STANDARDS),
					spieler);
		} else if (this.positionID == ISpielerPosition.captain) {
			item.setValues(
					spielerName,
					Helper.round(this.lineup.getAverageExperience(spieler.getSpielerID()),
							gui.UserParameter.instance().anzahlNachkommastellen), spieler);
		} else {
			SpielerPosition position = this.lineup.getPositionById(this.positionID);

			if (position != null) {
				item.setValues(spielerName, spieler.calcPosValue(position.getPosition(), true), spieler);
				return item;
			}
		}
		return item;
	}

	public int getPlayerId() {
		return playerId;
	}

	public int getTacticOrder() {
		return tacticOrder;
	}

	/**
	 * Exposes the player combo box to reset the swap button if needed.
	 * 
	 * @return the player {@link JComboBox}.
	 */
	protected JComboBox getPlayerComboBox() {
		return this.playerComboBox;
	}

	public LayoutManager getSwapLayout() {
		return layout;
	}

	public void addSwapItem(Component c) {
		this.layeredPane.add(c, 1);
	}

	public void addAssistantOverlay(LineupAssistantSelectorOverlay overlay) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.insets = new Insets(2, 2, 2, 2);
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.gridheight = 3;
		this.layeredPane.add(overlay, constraints, 2);
		repaint();
	}

	public void removeAssistantOverlay(LineupAssistantSelectorOverlay overlay) {
		this.layeredPane.remove(overlay);
		repaint();
	}

	private String getLanguageString(String key) {
		return HOVerwaltung.instance().getLanguageString(key);
	}
}