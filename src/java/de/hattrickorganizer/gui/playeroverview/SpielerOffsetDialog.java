// %2759382947:de.hattrickorganizer.gui.playeroverview%
package de.hattrickorganizer.gui.playeroverview;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.gui.menu.option.SliderPanel;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.Spieler;

/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
final class SpielerOffsetDialog extends JDialog implements ActionListener {
	//~ Instance fields ----------------------------------------------------------------------------

	private JButton m_jbAbbrechen;
	private JButton m_jbOK;
	private SliderPanel m_jpFluegelspiel;
	private SliderPanel m_jpPasspiel;
	private SliderPanel m_jpSpielaufbau;
	private SliderPanel m_jpStandard;
	private SliderPanel m_jpTorschuss;
	private SliderPanel m_jpTorwart;
	private SliderPanel m_jpVerteidigung;
	private Spieler m_clSpieler;

	//~ Constructors -------------------------------------------------------------------------------

	/**
	 * Creates a new SpielerOffsetDialog object.
	 *
	 * @param owner TODO Missing Constructuor Parameter Documentation
	 * @param spieler TODO Missing Constructuor Parameter Documentation
	 */
	protected SpielerOffsetDialog(javax.swing.JFrame owner, Spieler spieler) {
		super(owner, true);
		setTitle(
			HOVerwaltung.instance().getResource().getProperty(
				"OffsetTitle")
				+ " "
				+ spieler.getName());

		m_clSpieler = spieler;

		initComponents();

		pack();

		final Dimension size =
			de.hattrickorganizer.gui.HOMainFrame.instance().getToolkit().getScreenSize();

		if (size.width > this.getSize().width) {
			//Mittig positionieren
			this.setLocation(
				(size.width / 2) - (this.getSize().width / 2),
				(size.height / 2) - (this.getSize().height / 2));
		}
	}

	//~ Methods ------------------------------------------------------------------------------------

	/**
	 * TODO Missing Method Documentation
	 *
	 * @param actionEvent TODO Missing Method Parameter Documentation
	 */
	public final void actionPerformed(ActionEvent actionEvent) {
		if (actionEvent.getSource().equals(m_jbOK)) {
			m_clSpieler.setTrainingsOffsetFluegelspiel(m_jpFluegelspiel.getValue() / 100d);
			m_clSpieler.setTrainingsOffsetPasspiel(m_jpPasspiel.getValue() / 100d);
			m_clSpieler.setTrainingsOffsetSpielaufbau(m_jpSpielaufbau.getValue() / 100d);
			m_clSpieler.setTrainingsOffsetStandards(m_jpStandard.getValue() / 100d);
			m_clSpieler.setTrainingsOffsetTorschuss(m_jpTorschuss.getValue() / 100d);
			m_clSpieler.setTrainingsOffsetTorwart(m_jpTorwart.getValue() / 100d);
			m_clSpieler.setTrainingsOffsetVerteidigung(m_jpVerteidigung.getValue() / 100d);

			//Subskills neu berechnen
			// TODO Calc Subskills commented, no need to do that
			//m_clSpieler.calcFullSubskills( database.DBZugriff.instance ().getBasics ( database.DBZugriff.instance ().getHRF_IDByDate ()  ).getDatum (),  model.HOVerwaltung.instance().getModel().getVerein().getCoTrainer(), model.HOVerwaltung.instance().getModel().getVerein().getTorwartTrainer(), model.HOVerwaltung.instance().getModel().getTrainer().getTrainer(), model.HOVerwaltung.instance().getModel().getTeam().getTrainingslevel() );
			//Alle Spieler neu speichern (nur einer ist zu aufwendig ;)
			DBZugriff.instance().saveSpieler(
				HOVerwaltung.instance().getModel().getID(),
				HOVerwaltung.instance().getModel().getAllSpieler(),
				HOVerwaltung.instance().getModel().getBasics().getDatum());

			//GUI aktualisieren
			de.hattrickorganizer.gui.RefreshManager.instance().doReInit();

			setVisible(false);
		} else if (actionEvent.getSource().equals(m_jbAbbrechen)) {
			setVisible(false);
		}
	}

	/**
	 * TODO Missing Method Documentation
	 */
	private void initComponents() {
		setContentPane(new de.hattrickorganizer.gui.templates.ImagePanel());

		final GridBagLayout layout = new GridBagLayout();
		final GridBagConstraints constraints = new GridBagConstraints();

		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.insets = new Insets(4, 4, 4, 4);

		getContentPane().setLayout(layout);

		//----Slider -----------
		final JPanel panel = new ImagePanel();
		panel.setLayout(new GridLayout(7, 1, 4, 4));
		panel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

		Properties properties = HOVerwaltung.instance().getResource();
		m_jpSpielaufbau =
			new SliderPanel(properties.getProperty(	"Spielaufbau"),
				100,
				0,
				1,
				1f,
				80);
		m_jpSpielaufbau.setValue((float) m_clSpieler.getTrainingsOffsetSpielaufbau() * 100f);
		panel.add(m_jpSpielaufbau);

		m_jpFluegelspiel =
			new SliderPanel(properties.getProperty("Fluegelspiel"),
				100,
				0,
				1,
				1f,
				80);
		m_jpFluegelspiel.setValue((float) m_clSpieler.getTrainingsOffsetFluegelspiel() * 100f);
		panel.add(m_jpFluegelspiel);

		m_jpTorschuss =
			new SliderPanel(properties.getProperty("Torschuss"),
				100,
				0,
				1,
				1f,
				80);
		m_jpTorschuss.setValue((float) m_clSpieler.getTrainingsOffsetTorschuss() * 100f);
		panel.add(m_jpTorschuss);

		m_jpTorwart =
			new SliderPanel(properties.getProperty("Torwart"),
				100,
				0,
				1,
				1f,
				80);
		m_jpTorwart.setValue((float) m_clSpieler.getTrainingsOffsetTorwart() * 100f);
		panel.add(m_jpTorwart);

		m_jpPasspiel =
			new SliderPanel(properties.getProperty("Passpiel"),
				100,
				0,
				1,
				1f,
				80);
		m_jpPasspiel.setValue((float) m_clSpieler.getTrainingsOffsetPasspiel() * 100f);
		panel.add(m_jpPasspiel);

		m_jpVerteidigung =
			new SliderPanel(properties.getProperty("Verteidigung"),
				100,
				0,
				1,
				1f,
				80);
		m_jpVerteidigung.setValue((float) m_clSpieler.getTrainingsOffsetVerteidigung() * 100f);
		panel.add(m_jpVerteidigung);

		m_jpStandard =
			new SliderPanel(properties.getProperty("Standards"),
				100,
				0,
				1,
				1f,
				80);
		m_jpStandard.setValue((float) m_clSpieler.getTrainingsOffsetStandards() * 100f);
		panel.add(m_jpStandard);

		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		layout.setConstraints(panel, constraints);
		getContentPane().add(panel);

		m_jbOK =
			new JButton(properties.getProperty("Uebernehmen"));
		m_jbOK.addActionListener(this);

		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.NONE;
		layout.setConstraints(m_jbOK, constraints);
		getContentPane().add(m_jbOK);

		m_jbAbbrechen =
			new JButton(properties.getProperty("Abbrechen"));
		m_jbAbbrechen.addActionListener(this);

		constraints.anchor = GridBagConstraints.EAST;
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.NONE;
		layout.setConstraints(m_jbAbbrechen, constraints);
		getContentPane().add(m_jbAbbrechen);
	}
}
