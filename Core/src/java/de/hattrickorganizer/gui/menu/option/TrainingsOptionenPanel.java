// %2563786322:de.hattrickorganizer.gui.menu.option%
package de.hattrickorganizer.gui.menu.option;

import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.model.OptionManager;

import java.awt.GridLayout;

import javax.swing.JLabel;


/**
 * Optionen für das Training
 */
final class TrainingsOptionenPanel extends ImagePanel implements javax.swing.event.ChangeListener {
    //~ Static / Instance fields ----------------------------------------------------------------------------

	private static final long serialVersionUID = 1L;
	private SliderPanel m_jslAlterFaktor;
    private SliderPanel m_jslCoTrainerFaktor;
    private SliderPanel m_jslIntensitaetFaktor;
    private SliderPanel m_jslTrainerFaktor;
    private SliderPanel m_jslTrainingFluegel;
    private SliderPanel m_jslTrainingPasspiel;
    private SliderPanel m_jslTrainingSpielaufbau;
    private SliderPanel m_jslTrainingStandards;
    private SliderPanel m_jslTrainingTorschuss;
    private SliderPanel m_jslTrainingTorwart;
    private SliderPanel m_jslTrainingVerteidigung;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TrainingsOptionenPanel object.
     */
    protected TrainingsOptionenPanel() {
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param changeEvent TODO Missing Method Parameter Documentation
     */
    public final void stateChanged(javax.swing.event.ChangeEvent changeEvent) {
        gui.UserParameter.temp().DAUER_TORWART =  m_jslTrainingTorwart.getValue();
        gui.UserParameter.temp().DAUER_VERTEIDIGUNG =  m_jslTrainingVerteidigung.getValue();
        gui.UserParameter.temp().DAUER_SPIELAUFBAU = m_jslTrainingSpielaufbau.getValue();
        gui.UserParameter.temp().DAUER_PASSPIEL =  m_jslTrainingPasspiel.getValue();
        gui.UserParameter.temp().DAUER_FLUEGELSPIEL =  m_jslTrainingFluegel.getValue();
        gui.UserParameter.temp().DAUER_CHANCENVERWERTUNG =  m_jslTrainingTorschuss
                                                               .getValue();
        gui.UserParameter.temp().DAUER_STANDARDS = m_jslTrainingStandards.getValue();        
        gui.UserParameter.temp().AlterFaktor = m_jslAlterFaktor.getValue();
        gui.UserParameter.temp().TrainerFaktor = m_jslTrainerFaktor.getValue();
        gui.UserParameter.temp().CoTrainerFaktor = m_jslCoTrainerFaktor.getValue();
        gui.UserParameter.temp().IntensitaetFaktor = m_jslIntensitaetFaktor.getValue();
        
        OptionManager.instance().setReInitNeeded();
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        setLayout(new GridLayout(13, 1, 4, 4));

        JLabel label = new JLabel("   "
                                  + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("VoraussichtlicheTrainingwochen"));
        add(label);

        m_jslTrainingTorwart = new SliderPanel(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Torwart"),
                                               100, 1, 10.0f, 0.1f, 120);
        m_jslTrainingTorwart.setValue( gui.UserParameter.temp().DAUER_TORWART);
        m_jslTrainingTorwart.addChangeListener(this);
        add(m_jslTrainingTorwart);

        m_jslTrainingVerteidigung = new SliderPanel(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Verteidigung"),
                                                    100, 1, 10.0f, 0.1f, 120);
        m_jslTrainingVerteidigung.setValue( gui.UserParameter.temp().DAUER_VERTEIDIGUNG);
        m_jslTrainingVerteidigung.addChangeListener(this);
        add(m_jslTrainingVerteidigung);

        m_jslTrainingSpielaufbau = new SliderPanel(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Spielaufbau"),
                                                   100, 1, 10.0f, 0.1f, 120);
        m_jslTrainingSpielaufbau.setValue( gui.UserParameter.temp().DAUER_SPIELAUFBAU);
        m_jslTrainingSpielaufbau.addChangeListener(this);
        add(m_jslTrainingSpielaufbau);

        m_jslTrainingPasspiel = new SliderPanel(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Passpiel"),
                                                100, 1, 10.0f, 0.1f, 120);
        m_jslTrainingPasspiel.setValue( gui.UserParameter.temp().DAUER_PASSPIEL);
        m_jslTrainingPasspiel.addChangeListener(this);
        add(m_jslTrainingPasspiel);

        m_jslTrainingFluegel = new SliderPanel(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Fluegelspiel"),
                                               100, 1, 10.0f, 0.1f, 120);
        m_jslTrainingFluegel.setValue( gui.UserParameter.temp().DAUER_FLUEGELSPIEL);
        m_jslTrainingFluegel.addChangeListener(this);
        add(m_jslTrainingFluegel);

        m_jslTrainingTorschuss = new SliderPanel(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Torschuss"),
                                                 100, 1, 10.0f, 0.1f, 120);
        m_jslTrainingTorschuss.setValue( gui.UserParameter.temp().DAUER_CHANCENVERWERTUNG);
        m_jslTrainingTorschuss.addChangeListener(this);
        add(m_jslTrainingTorschuss);
        
        m_jslTrainingStandards = new SliderPanel(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Standards"),
                                                 100, 1, 10.0f, 0.1f, 120);
        m_jslTrainingStandards.setValue( gui.UserParameter.temp().DAUER_STANDARDS);
                
        m_jslTrainingStandards.addChangeListener(this);
        add(m_jslTrainingStandards);

        label = new JLabel("   "
                           + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("TrainingFaktoren"));
        add(label);

        m_jslAlterFaktor = new SliderPanel(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("FaktorSpieleralter"),
                                           
        //faktor optimal 1.0
        200, 0, 100.0f, 1.0f, 120);
        m_jslAlterFaktor.setValue((float) gui.UserParameter.temp().AlterFaktor);
        m_jslAlterFaktor.addChangeListener(this);
        add(m_jslAlterFaktor);

        m_jslTrainerFaktor = new SliderPanel(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("FaktorTrainerfertigkeit"),
                                             
        //faktor optimal 1.0
        200, 0, 100.0f, 1.0f, 120);
        m_jslTrainerFaktor.setValue((float) gui.UserParameter.temp().TrainerFaktor);
        m_jslTrainerFaktor.addChangeListener(this);
        add(m_jslTrainerFaktor);

        m_jslCoTrainerFaktor = new SliderPanel(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("FaktorCoTraineranzahl"),
                                               
        //faktor optimal 1.0
        200, 0, 100.0f, 1.0f, 120);
        m_jslCoTrainerFaktor.setValue((float) gui.UserParameter.temp().CoTrainerFaktor);
        m_jslCoTrainerFaktor.addChangeListener(this);
        add(m_jslCoTrainerFaktor);

        m_jslIntensitaetFaktor = new SliderPanel(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("FaktorIntensitaet"),
                                                 
        //faktor optimal 1.0
        200, 0, 100.0f, 1.0f, 120);
        m_jslIntensitaetFaktor.setValue((float) gui.UserParameter.temp().IntensitaetFaktor);
        m_jslIntensitaetFaktor.addChangeListener(this);
        add(m_jslIntensitaetFaktor);
    }
}
