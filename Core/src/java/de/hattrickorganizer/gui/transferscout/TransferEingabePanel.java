// %1128099595984:de.hattrickorganizer.gui.transferscout%
package de.hattrickorganizer.gui.transferscout;

import gui.HOIconName;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.FocusEvent;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;

import plugins.IEPVData;
import plugins.ISpielerPosition;
import de.hattrickorganizer.gui.model.CBItem;
import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.templates.DoppelLabelEntry;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.model.EPVData;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.ScoutEintrag;
import de.hattrickorganizer.model.Spieler;
import de.hattrickorganizer.model.SpielerPosition;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.Helper;
import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.RefreshManager;

/**
 * A player can be created and modified here.
 *
 * @author Marco Senn
 */
public class TransferEingabePanel extends ImagePanel implements ItemListener, ActionListener,
                                                                FocusListener, KeyListener
{
    //~ Static fields/initializers -----------------------------------------------------------------

	private static final long serialVersionUID = -3287232092187457640L;
	private static int iTempSpielerID = -1001;

    //~ Instance fields ----------------------------------------------------------------------------

    private ColorLabelEntry jpBestPosition = new ColorLabelEntry("", ColorLabelEntry.FG_STANDARD,
    		ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
    private DoppelLabelEntry jpRatingWingback = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERPOSITONSWERTE);
    private DoppelLabelEntry jpRatingWingbackDefensive = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private DoppelLabelEntry jpRatingWingbackTowardsMiddle = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private DoppelLabelEntry jpRatingWingbackOffensive = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private DoppelLabelEntry jpRatingWinger = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERPOSITONSWERTE);
    private DoppelLabelEntry jpRatingWingerDefensive = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private DoppelLabelEntry jpRatingWingerTowardsMiddle = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private DoppelLabelEntry jpRatingWingerOffensive = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private DoppelLabelEntry jpRatingDefender = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private DoppelLabelEntry jpRatingDefenderTowardsWing = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private DoppelLabelEntry jpRatingDefenderOffensive = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private DoppelLabelEntry jpRatingMidfielder = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERPOSITONSWERTE);
    private DoppelLabelEntry jpRatingMidfielderTowardsWing = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private DoppelLabelEntry jpRatingMidfielderDefensive = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private DoppelLabelEntry jpRatingMidfielderOffensive = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private DoppelLabelEntry jpRatingForward = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERPOSITONSWERTE);
    private DoppelLabelEntry jpRatingForwardTowardsWing = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private DoppelLabelEntry jpRatingForwardDefensive = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE);
    private DoppelLabelEntry jpRatingKeeper = new DoppelLabelEntry(ColorLabelEntry.BG_SPIELERPOSITONSWERTE);
    private JButton jbAddTempSpieler = new JButton(HOVerwaltung.instance().getLanguageString("AddTempspieler"));
    private JButton jbPrint = new JButton(ThemeManager.getIcon(HOIconName.PRINTER));
    private JButton jbRemove = new JButton(HOVerwaltung.instance().getLanguageString("ScoutEntfernen"));
    private JButton jbAdd = new JButton(HOVerwaltung.instance().getLanguageString("ScoutHinzu"));
    private JButton jbMiniScout = new JButton(HOVerwaltung.instance().getLanguageString("ScoutMini"));
    private JButton jbApply = new JButton(HOVerwaltung.instance().getLanguageString("Uebernehmen"));
    private JButton jbRemoveAll = new JButton(HOVerwaltung.instance().getLanguageString("Scout.RemoveAll"));
    private JComboBox jcbExperience = new JComboBox(Helper.EINSTUFUNG);
    private JComboBox jcbWinger = new JComboBox(Helper.EINSTUFUNG);
    private JComboBox jcbForm = new JComboBox(Helper.EINSTUFUNG_FORM);
    private JComboBox jcbStamina = new JComboBox(Helper.EINSTUFUNG_KONDITION);
    private JComboBox jcbPassing = new JComboBox(Helper.EINSTUFUNG);
    private JComboBox jcbSpeciality = new JComboBox(Helper.EINSTUFUNG_SPECIALITY);
    private JComboBox jcbPlaymaking = new JComboBox(Helper.EINSTUFUNG);
    private JComboBox jcbSetPieces = new JComboBox(Helper.EINSTUFUNG);
    private JComboBox jcbScoring = new JComboBox(Helper.EINSTUFUNG);
    private JComboBox jcbKeeper = new JComboBox(Helper.EINSTUFUNG);
    private JComboBox jcbDefending = new JComboBox(Helper.EINSTUFUNG);
    private JComboBox jcbLoyalty = new JComboBox(Helper.EINSTUFUNG);
    private JCheckBox jchHomegrown = new JCheckBox();
    private JLabel jlStatus = new JLabel(HOVerwaltung.instance().getLanguageString("scout_status") + ": ");
    private JTextArea jtaCopyPaste = new JTextArea(5, 20);
    private JTextArea jtaNotes = new JTextArea();
    private JTextField jtfAge = new JTextField("17.0");
    private JTextField jtfTSI = new JTextField("1000");
    private JTextField jtfPrice = new JTextField("0");
	private JLabel jtfEPV = new JLabel("",SwingConstants.RIGHT);
    private ScoutEintrag clScoutEntry;
    private SpinnerDateModel clSpinnerModel = new SpinnerDateModel();
    private JSpinner jsSpinner = new JSpinner(clSpinnerModel);
    private JTextField jtfName = new JTextField();
    private JTextField jtfPlayerID = new JTextField("0");
    private TransferScoutPanel clOwner;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TransferEingabePanel object.
     *
     * @param owner the parent control holding this panel
     */
    public TransferEingabePanel(TransferScoutPanel owner) {
        clOwner = owner;
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Return next temporary playerID. Starting from -1.
     *
     * @return Returns next temporary playerID.
     */
    public static int getNextTempSpielerID() {
        return iTempSpielerID--;
    }

    /**
     * Set new scout entry or modify old
     *
     * @param scoutEintrag The new scout entry object
     */
    public final void setScoutEintrag(ScoutEintrag scoutEintrag) {
        if (scoutEintrag != null) {
            clScoutEntry = scoutEintrag;
            // If scout entry already exists
            if (clOwner.getTransferTable().getTransferTableModel()
            		.getScoutEintrag(clScoutEntry.getPlayerID()) != null) {
                jbAdd.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Transferscout_ersetzen"));
                jbAdd.setText(HOVerwaltung.instance().getLanguageString("ScoutErsetzen"));
                jbRemove.setEnabled(true);
            } else {
                jbAdd.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Transferscout_hinzufuegen"));
                jbAdd.setText(HOVerwaltung.instance().getLanguageString("ScoutHinzu"));
                jbRemove.setEnabled(false);
            }
            jbAdd.setEnabled(true);
        } else {
            clScoutEntry = new ScoutEintrag();
            jbAdd.setText(HOVerwaltung.instance().getLanguageString("ScoutHinzu"));
            jbRemove.setEnabled(false);
            jbAdd.setEnabled(false);
        }
        setCBs();
        setLabels();
        checkFields();
        invalidate();
        validate();
        repaint();
    }

    /**
     * Fired when any button is pressed
     *
     * @param actionEvent Event fired when button is pressed.
     */
    public final void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(jbApply)) {
            copyPaste();
    	} else if (actionEvent.getSource().equals(jbAddTempSpieler)) {
            final Spieler tempSpieler = new Spieler();
            tempSpieler.setNationalitaet(HOVerwaltung.instance().getModel().getBasics().getLand());
            tempSpieler.setSpielerID(getNextTempSpielerID());
            if (jtfName.getText().trim().equals("")) {
                tempSpieler.setName("Temp " + Math.abs(1000 + tempSpieler.getSpielerID()));
            } else {
                tempSpieler.setName(jtfName.getText());
            }
            tempSpieler.setTSI(Integer.parseInt(jtfTSI.getText()));
            tempSpieler.setSpezialitaet(((CBItem) jcbSpeciality.getSelectedItem()).getId());
            tempSpieler.setAlter(Integer.parseInt(jtfAge.getText().replaceFirst("\\..*", "")));
            tempSpieler.setAgeDays(Integer.parseInt(jtfAge.getText().replaceFirst(".*\\.", "")));
            tempSpieler.setErfahrung(((CBItem) jcbExperience.getSelectedItem()).getId());
            tempSpieler.setForm(((CBItem) jcbForm.getSelectedItem()).getId());
            tempSpieler.setKondition(((CBItem) jcbStamina.getSelectedItem()).getId());
            tempSpieler.setVerteidigung(((CBItem)jcbDefending.getSelectedItem()).getId());
            tempSpieler.setTorschuss(((CBItem) jcbScoring.getSelectedItem()).getId());
            tempSpieler.setTorwart(((CBItem) jcbKeeper.getSelectedItem()).getId());
            tempSpieler.setFluegelspiel(((CBItem) jcbWinger.getSelectedItem()).getId());
            tempSpieler.setPasspiel(((CBItem) jcbPassing.getSelectedItem()).getId());
            tempSpieler.setStandards(((CBItem) jcbSetPieces.getSelectedItem()).getId());
            tempSpieler.setSpielaufbau(((CBItem) jcbPlaymaking.getSelectedItem()).getId());
            tempSpieler.setLoyalty(((CBItem)jcbLoyalty.getSelectedItem()).getId());
            tempSpieler.setHomeGrown(jchHomegrown.isSelected());
            HOVerwaltung.instance().getModel().addSpieler(tempSpieler);
            RefreshManager.instance().doReInit();
            HOMainFrame.instance().showTab(HOMainFrame.SPIELERUEBERSICHT);
        }
		else if (actionEvent.getSource().equals(jbRemoveAll)) {
			clOwner.removeScoutEntries();
		}
    	else {
            clScoutEntry.setPlayerID(Integer.parseInt(jtfPlayerID.getText()));
            clScoutEntry.setPrice(Integer.parseInt(jtfPrice.getText()));
            clScoutEntry.setAlter(Integer.parseInt(jtfAge.getText().replaceFirst("\\..*", "")));
            clScoutEntry.setAgeDays(Integer.parseInt(jtfAge.getText().replaceFirst(".*\\.", "")));
            clScoutEntry.setTSI(Integer.parseInt(jtfTSI.getText()));
            clScoutEntry.setName(jtfName.getText());
            clScoutEntry.setInfo(jtaNotes.getText());
            clScoutEntry.setDeadline(new java.sql.Timestamp(clSpinnerModel.getDate().getTime()));
            if (actionEvent.getSource().equals(jbAdd)) {
                clOwner.addScoutEintrag(clScoutEntry);
            } else if (actionEvent.getSource().equals(jbRemove)) {
                clOwner.removeScoutEintrag(clScoutEntry);
            } else if (actionEvent.getSource().equals(jbMiniScout)) {
                new MiniScoutDialog(this);
            } else if (actionEvent.getSource().equals(jbPrint)) {
                clOwner.drucken();
            }
        }
        checkFields();
    }

    /**
     * Fired when panel receives focus
     *
     * @param focusEvent Event fired when panel receives focus
     */
    public void focusGained(FocusEvent focusEvent) {
    }

    /**
     * Fired when panel losts focus
     *
     * @param focusEvent Event fired when panel losts focus
     */
    public final void focusLost(FocusEvent focusEvent) {
        if (!Helper.parseInt(HOMainFrame.instance(), jtfTSI, false)
            || !Helper.parseInt(HOMainFrame.instance(), jtfPlayerID, false)
            || !Helper.parseInt(HOMainFrame.instance(), jtfPrice, false)) {
            return;
        }
        checkFields();
        if (focusEvent.getSource().equals(jtfAge)) {
			setLabels();
        }
    }

    /**
     * Fired when an item changes
     *
     * @param itemEvent Indicates which item has changed
     */
    public final void itemStateChanged(ItemEvent itemEvent) {
        if (itemEvent.getStateChange() == ItemEvent.SELECTED || itemEvent.getSource() == jchHomegrown) {
            setLabels();
        }
    }

    /**
     * Fired when a key is pressed
     *
     * @param keyEvent Event fired when a key is pressed
     */
    public void keyPressed(KeyEvent keyEvent) {
    }

    /**
     * Fired when a key is released
     *
     * @param keyEvent Event fired when a key is released
     */
    public final void keyReleased(KeyEvent keyEvent) {
        checkFields();
    }

    /**
     * Fired when a key is typed
     *
     * @param keyEvent Fired when a key is typed
     */
    public void keyTyped(KeyEvent keyEvent) {
    }

    /**
     * Set checkboxes to their corresponding value
     */
    private void setCBs() {
        clSpinnerModel.setValue(clScoutEntry.getDeadline());
        jtfPlayerID.setText(clScoutEntry.getPlayerID() + "");
        jtfName.setText(clScoutEntry.getName());
        jtfPrice.setText(clScoutEntry.getPrice() + "");
        jtfAge.setText(clScoutEntry.getAlter() + "." + clScoutEntry.getAgeDays());
        jtfTSI.setText(clScoutEntry.getTSI() + "");
        jtaNotes.setText(clScoutEntry.getInfo());
        jcbSpeciality.removeItemListener(this);
        jcbExperience.removeItemListener(this);
        jcbForm.removeItemListener(this);
        jcbStamina.removeItemListener(this);
        jcbPlaymaking.removeItemListener(this);
        jcbWinger.removeItemListener(this);
        jcbScoring.removeItemListener(this);
        jcbKeeper.removeItemListener(this);
        jcbPassing.removeItemListener(this);
        jcbDefending.removeItemListener(this);
        jcbSetPieces.removeItemListener(this);
        jcbLoyalty.removeActionListener(this);
        jchHomegrown.removeActionListener(this);
        Helper.markierenComboBox(jcbSpeciality, clScoutEntry.getSpeciality());
        Helper.markierenComboBox(jcbExperience, clScoutEntry.getErfahrung());
        Helper.markierenComboBox(jcbForm, clScoutEntry.getForm());
        Helper.markierenComboBox(jcbStamina, clScoutEntry.getKondition());
        Helper.markierenComboBox(jcbPlaymaking, clScoutEntry.getSpielaufbau());
        Helper.markierenComboBox(jcbWinger, clScoutEntry.getFluegelspiel());
        Helper.markierenComboBox(jcbScoring, clScoutEntry.getTorschuss());
        Helper.markierenComboBox(jcbKeeper, clScoutEntry.getTorwart());
        Helper.markierenComboBox(jcbPassing, clScoutEntry.getPasspiel());
        Helper.markierenComboBox(jcbDefending, clScoutEntry.getVerteidigung());
        Helper.markierenComboBox(jcbSetPieces, clScoutEntry.getStandards());
        Helper.markierenComboBox(jcbLoyalty, clScoutEntry.getLoyalty());
        jchHomegrown.setSelected(clScoutEntry.isHomegrown());
        jcbSpeciality.addItemListener(this);
        jcbExperience.addItemListener(this);
        jcbForm.addItemListener(this);
        jcbStamina.addItemListener(this);
        jcbPlaymaking.addItemListener(this);
        jcbWinger.addItemListener(this);
        jcbScoring.addItemListener(this);
        jcbKeeper.addItemListener(this);
        jcbPassing.addItemListener(this);
        jcbDefending.addItemListener(this);
        jcbSetPieces.addItemListener(this);
        jchHomegrown.addItemListener(this);
    }

    /**
     * Set labels to the new values
     */
    private void setLabels() {
        final Spieler tempSpieler = new Spieler();
        tempSpieler.setSpezialitaet(((CBItem)jcbSpeciality.getSelectedItem()).getId());
        tempSpieler.setErfahrung(((CBItem)jcbExperience.getSelectedItem()).getId());
		tempSpieler.setFuehrung(3); // Huh???
        tempSpieler.setForm(((CBItem)jcbForm.getSelectedItem()).getId());
        tempSpieler.setKondition(((CBItem)jcbStamina.getSelectedItem()).getId());
        tempSpieler.setVerteidigung(((CBItem)jcbDefending.getSelectedItem()).getId());
        tempSpieler.setTorschuss(((CBItem)jcbScoring.getSelectedItem()).getId());
        tempSpieler.setTorwart(((CBItem)jcbKeeper.getSelectedItem()).getId());
        tempSpieler.setFluegelspiel(((CBItem)jcbWinger.getSelectedItem()).getId());
        tempSpieler.setPasspiel(((CBItem)jcbPassing.getSelectedItem()).getId());
        tempSpieler.setStandards(((CBItem)jcbSetPieces.getSelectedItem()).getId());
        tempSpieler.setSpielaufbau(((CBItem)jcbPlaymaking.getSelectedItem()).getId());
        tempSpieler.setLoyalty(((CBItem)jcbLoyalty.getSelectedItem()).getId());
        tempSpieler.setHomeGrown(jchHomegrown.isSelected());
        tempSpieler.setAlter(Integer.parseInt(jtfAge.getText().replaceFirst("\\..*", "")));
        tempSpieler.setAgeDays(Integer.parseInt(jtfAge.getText().replaceFirst(".*\\.", "")));
        IEPVData data = new EPVData(tempSpieler);
		double price = HOVerwaltung.instance().getModel().getEPV().getPrice(data);
		jtfEPV.setText(NumberFormat.getCurrencyInstance().format(price));
        jpBestPosition.setText(SpielerPosition.getNameForPosition(tempSpieler.getIdealPosition())
        		+ " (" + 
        		Helper.getNumberFormat(false, gui.UserParameter.instance().anzahlNachkommastellen)
        		.format(tempSpieler.calcPosValue(tempSpieler.getIdealPosition(), true))
        		 + ")");
        jpRatingKeeper.getLinks().setText(Helper.getNumberFormat(false, gui.UserParameter.instance().anzahlNachkommastellen)
        		.format(tempSpieler.calcPosValue(ISpielerPosition.KEEPER, true)) + "");
        jpRatingDefender.getLinks().setText(Helper.getNumberFormat(false, gui.UserParameter.instance().anzahlNachkommastellen)
        		.format(tempSpieler.calcPosValue(ISpielerPosition.CENTRAL_DEFENDER, true)) + "");
        jpRatingDefenderTowardsWing.getLinks().setText(Helper.getNumberFormat(false, gui.UserParameter.instance().anzahlNachkommastellen)
        		.format(tempSpieler.calcPosValue(ISpielerPosition.CENTRAL_DEFENDER_TOWING, true)) + "");
        jpRatingDefenderOffensive.getLinks().setText(Helper.getNumberFormat(false, gui.UserParameter.instance().anzahlNachkommastellen)
        		.format(tempSpieler.calcPosValue(ISpielerPosition.CENTRAL_DEFENDER_OFF, true)) + "");
        jpRatingWingback.getLinks().setText(Helper.getNumberFormat(false, gui.UserParameter.instance().anzahlNachkommastellen)
        		.format(tempSpieler.calcPosValue(ISpielerPosition.BACK, true)) + "");
        jpRatingWingbackTowardsMiddle.getLinks().setText(Helper.getNumberFormat(false, gui.UserParameter.instance().anzahlNachkommastellen)
        		.format(tempSpieler.calcPosValue(ISpielerPosition.BACK_TOMID, true)) + "");
        jpRatingWingbackOffensive.getLinks().setText(Helper.getNumberFormat(false, gui.UserParameter.instance().anzahlNachkommastellen)
        		.format(tempSpieler.calcPosValue(ISpielerPosition.BACK_OFF, true)) + "");
        jpRatingWingbackDefensive.getLinks().setText(Helper.getNumberFormat(false, gui.UserParameter.instance().anzahlNachkommastellen)
        		.format(tempSpieler.calcPosValue(ISpielerPosition.BACK_DEF, true)) + "");
        jpRatingMidfielder.getLinks().setText(Helper.getNumberFormat(false, gui.UserParameter.instance().anzahlNachkommastellen)
        		.format(tempSpieler.calcPosValue(ISpielerPosition.MIDFIELDER, true)) + "");
        jpRatingMidfielderTowardsWing.getLinks().setText(Helper.getNumberFormat(false, gui.UserParameter.instance().anzahlNachkommastellen)
        		.format(tempSpieler.calcPosValue(ISpielerPosition.MIDFIELDER_TOWING, true)) + "");
        jpRatingMidfielderOffensive.getLinks().setText(Helper.getNumberFormat(false, gui.UserParameter.instance().anzahlNachkommastellen)
        		.format(tempSpieler.calcPosValue(ISpielerPosition.MIDFIELDER_OFF, true)) + "");
        jpRatingMidfielderDefensive.getLinks().setText(Helper.getNumberFormat(false, gui.UserParameter.instance().anzahlNachkommastellen)
        		.format(tempSpieler.calcPosValue(ISpielerPosition.MIDFIELDER_DEF, true)) + "");
        jpRatingWinger.getLinks().setText(Helper.getNumberFormat(false, gui.UserParameter.instance().anzahlNachkommastellen)
        		.format(tempSpieler.calcPosValue(ISpielerPosition.WINGER, true)) + "");
        jpRatingWingerTowardsMiddle.getLinks().setText(Helper.getNumberFormat(false, gui.UserParameter.instance().anzahlNachkommastellen)
        		.format(tempSpieler.calcPosValue(ISpielerPosition.WINGER_TOMID, true)) + "");
        jpRatingWingerOffensive.getLinks().setText(Helper.getNumberFormat(false, gui.UserParameter.instance().anzahlNachkommastellen)
        		.format(tempSpieler.calcPosValue(ISpielerPosition.WINGER_OFF, true)) + "");
        jpRatingWingerDefensive.getLinks().setText(Helper.getNumberFormat(false, gui.UserParameter.instance().anzahlNachkommastellen)
        		.format(tempSpieler.calcPosValue(ISpielerPosition.WINGER_DEF, true)) + "");
        jpRatingForward.getLinks().setText(Helper.getNumberFormat(false, gui.UserParameter.instance().anzahlNachkommastellen)
        		.format(tempSpieler.calcPosValue(ISpielerPosition.FORWARD, true)) + "");
        jpRatingForwardTowardsWing.getLinks().setText(Helper.getNumberFormat(false, gui.UserParameter.instance().anzahlNachkommastellen)
        		.format(tempSpieler.calcPosValue(ISpielerPosition.FORWARD_TOWING, true)) + "");
        jpRatingForwardDefensive.getLinks().setText(Helper.getNumberFormat(false, gui.UserParameter.instance().anzahlNachkommastellen)
        		.format(tempSpieler.calcPosValue(ISpielerPosition.FORWARD_DEF, true)) + "");
        clScoutEntry.setSpeciality(((CBItem) jcbSpeciality.getSelectedItem()).getId());
        clScoutEntry.setErfahrung(((CBItem) jcbExperience.getSelectedItem()).getId());
        clScoutEntry.setForm(((CBItem) jcbForm.getSelectedItem()).getId());
        clScoutEntry.setKondition(((CBItem) jcbStamina.getSelectedItem()).getId());
        clScoutEntry.setVerteidigung(((CBItem) jcbDefending.getSelectedItem()).getId());
        clScoutEntry.setTorschuss(((CBItem) jcbScoring.getSelectedItem()).getId());
        clScoutEntry.setTorwart(((CBItem) jcbKeeper.getSelectedItem()).getId());
        clScoutEntry.setFluegelspiel(((CBItem) jcbWinger.getSelectedItem()).getId());
        clScoutEntry.setPasspiel(((CBItem) jcbPassing.getSelectedItem()).getId());
        clScoutEntry.setStandards(((CBItem) jcbSetPieces.getSelectedItem()).getId());
        clScoutEntry.setSpielaufbau(((CBItem) jcbPlaymaking.getSelectedItem()).getId());
        clScoutEntry.setLoyalty(((CBItem) jcbLoyalty.getSelectedItem()).getId());
        clScoutEntry.setHomegrown(jchHomegrown.isSelected());
    }

    /**
     * Check inputfields of valid values
     */
    private void checkFields() {
        // When playerid already exists
        int id = 0;
        boolean valid = true;

        try {
            id = Integer.parseInt(jtfPlayerID.getText());
        } catch (NumberFormatException e) {
            valid = false;
        }

        if (valid && (clOwner.getTransferTable().getTransferTableModel().getScoutEintrag(id) != null)) {
            jbAdd.setEnabled(true);
            jbAdd.setText(HOVerwaltung.instance().getLanguageString("ScoutErsetzen"));
            jbRemove.setEnabled(true);
        } else {
            jbAdd.setEnabled(true);
            jbAdd.setText(HOVerwaltung.instance().getLanguageString("ScoutHinzu"));
            jbRemove.setEnabled(false);
        }
    }

    /**
     * Calls playerconverter and fills boxes to the corresponding values
     */
    private void copyPaste() {
        String message = "";
        final PlayerConverter pc = new PlayerConverter();

        try {
            final Player player = pc.build(jtaCopyPaste.getText());

            if (player != null) {
                jtfPlayerID.setText(player.getPlayerID() + "");
                jtfName.setText(player.getPlayerName());
                jtfAge.setText(player.getAge() + "." + player.getAgeDays());
                jtfPrice.setText(player.getPrice() + "");
                jtfTSI.setText(player.getTSI() + "");
                jtaNotes.setText(player.getInfo());

                jcbSpeciality.removeItemListener(this);
                Helper.markierenComboBox(jcbSpeciality,player.getSpeciality());
                jcbSpeciality.addItemListener(this);
                jcbExperience.removeItemListener(this);
                Helper.markierenComboBox(jcbExperience,player.getExperience());
                jcbExperience.addItemListener(this);
                jcbForm.removeItemListener(this);
                Helper.markierenComboBox(jcbForm, player.getForm());
                jcbForm.addItemListener(this);
                jcbStamina.removeItemListener(this);
                Helper.markierenComboBox(jcbStamina,player.getStamina());
                jcbStamina.addItemListener(this);
                jcbDefending.removeItemListener(this);
                Helper.markierenComboBox(jcbDefending,player.getDefense());
                jcbDefending.addItemListener(this);
                jcbScoring.removeItemListener(this);
                Helper.markierenComboBox(jcbScoring, player.getAttack());
                jcbScoring.addItemListener(this);
                jcbKeeper.removeItemListener(this);
                Helper.markierenComboBox(jcbKeeper,player.getGoalKeeping());
                jcbKeeper.addItemListener(this);
                jcbWinger.removeItemListener(this);
                Helper.markierenComboBox(jcbWinger, player.getWing());
                jcbWinger.addItemListener(this);
                jcbPassing.removeItemListener(this);
                Helper.markierenComboBox(jcbPassing, player.getPassing());
                jcbPassing.addItemListener(this);
                jcbSetPieces.removeItemListener(this);
                Helper.markierenComboBox(jcbSetPieces,player.getSetPieces());
                jcbSetPieces.addItemListener(this);
                jcbLoyalty.removeItemListener(this);
                Helper.markierenComboBox(jcbLoyalty,player.getLoyalty());
                jcbLoyalty.addItemListener(this);
                jchHomegrown.removeItemListener(this);
                jchHomegrown.setSelected(player.isHomwGrown());
                jchHomegrown.addItemListener(this);
                
                // Listener stays here for recalculation of rating
                Helper.markierenComboBox(jcbPlaymaking,player.getPlayMaking());

                // Normally not working. Thus last positioned
                final java.text.SimpleDateFormat simpleFormat = new java.text.SimpleDateFormat("dd.MM.yy HH:mm",
                                                                                               java.util.Locale.GERMANY);
                final java.util.Date date = simpleFormat.parse(player.getExpiryDate() + " "
                                                               + player.getExpiryTime());
                jsSpinner.setValue(date);

                setLabels();
            }
        } catch (Exception e) {
        	HOLogger.instance().debug(getClass(), e);
            message = HOVerwaltung.instance().getLanguageString("scout_error");
        }

        jtaCopyPaste.setText("");

        if (message.equals("")) {
            switch (pc.getError()) {
                case 1:
                    message = HOVerwaltung.instance().getLanguageString("scout_warning");
                    break;
                case 2:
                    message = HOVerwaltung.instance().getLanguageString("scout_error");
                    break;
                default:
                    message = HOVerwaltung.instance().getLanguageString("scout_success");
            }
        }
        jlStatus.setText(HOVerwaltung.instance().getLanguageString("scout_status") + ": " + message);
    }

    /**
     * Create components on the panel with default values
     */
    private void initComponents() {
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(4, 4, 4, 4);

        JPanel panel;
        JLabel label;

        setLayout(layout);

        // Entries
        panel = new ImagePanel();
        panel.setLayout(new GridLayout(10, 4, 4, 4));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("ID"));
        panel.add(label);
        jtfPlayerID.setHorizontalAlignment(JLabel.RIGHT);
        jtfPlayerID.addFocusListener(this);
        jtfPlayerID.addKeyListener(this);
        panel.add(jtfPlayerID);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Name"));
        panel.add(label);
        jtfName.addFocusListener(this);
        panel.add(jtfName);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Alter"));
        panel.add(label);
        jtfAge.setHorizontalAlignment(JLabel.RIGHT);
        jtfAge.addFocusListener(this);
        panel.add(jtfAge);

        label = new JLabel("TSI");
        panel.add(label);
        jtfTSI.setHorizontalAlignment(JLabel.RIGHT);
        jtfTSI.addFocusListener(this);
        panel.add(jtfTSI);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("scout_price"));
        panel.add(label);
        jtfPrice.setHorizontalAlignment(JLabel.RIGHT);
        jtfPrice.addFocusListener(this);
        panel.add(jtfPrice);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Ablaufdatum"));
        panel.add(label);
        jsSpinner.addFocusListener(this);
        panel.add(jsSpinner);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Spezialitaet"));
        panel.add(label);
        jcbSpeciality.addItemListener(this);
        panel.add(jcbSpeciality);

		label = new JLabel("EPV");
		panel.add(label);
		panel.add(jtfEPV);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("skill.experience"));
        panel.add(label);
        jcbExperience.addItemListener(this);
        panel.add(jcbExperience);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Form"));
        panel.add(label);
        jcbForm.addItemListener(this);
        panel.add(jcbForm);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("skill.stamina"));
        panel.add(label);
        jcbStamina.addItemListener(this);
        panel.add(jcbStamina);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("skill.keeper"));
        panel.add(label);
        jcbKeeper.addItemListener(this);
        panel.add(jcbKeeper);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("skill.playmaking"));
        panel.add(label);
        jcbPlaymaking.addItemListener(this);
        panel.add(jcbPlaymaking);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("skill.passing"));
        panel.add(label);
        jcbPassing.addItemListener(this);
        panel.add(jcbPassing);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("skill.winger"));
        panel.add(label);
        jcbWinger.addItemListener(this);
        panel.add(jcbWinger);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("skill.defending"));
        panel.add(label);
        jcbDefending.addItemListener(this);
        panel.add(jcbDefending);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("skill.scoring"));
        panel.add(label);
        jcbScoring.addItemListener(this);
        panel.add(jcbScoring);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("skill.set_pieces"));
        panel.add(label);
        jcbSetPieces.addItemListener(this);
        panel.add(jcbSetPieces);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Loyalty"));
        panel.add(label);
        jcbLoyalty.addItemListener(this);
        panel.add(jcbLoyalty);
        
        label = new JLabel(HOVerwaltung.instance().getLanguageString("Motherclub"));
        panel.add(label);
        jchHomegrown.addItemListener(this);
        panel.add(jchHomegrown);
        
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        layout.setConstraints(panel, constraints);
        add(panel);

        // Notes
        panel = new ImagePanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(javax.swing.BorderFactory.createTitledBorder(HOVerwaltung.instance().getLanguageString("Notizen")));
        jtaNotes.addFocusListener(this);
        panel.add(new JScrollPane(jtaNotes), BorderLayout.CENTER);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.NORTH;
        layout.setConstraints(panel, constraints);
        add(panel);

        // Copy & Paste
        panel = new ImagePanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(javax.swing.BorderFactory.createTitledBorder(HOVerwaltung.instance().getLanguageString("CopyPaste")));
        jtaCopyPaste.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Transferscout_CopyPaste"));
        panel.add(new JScrollPane(jtaCopyPaste), BorderLayout.NORTH);
        jbApply.addActionListener(this);
        layout.setConstraints(jbApply, constraints);
        panel.add(jbApply, BorderLayout.CENTER);
        panel.add(jlStatus, BorderLayout.SOUTH);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.NORTH;
        layout.setConstraints(panel, constraints);

        add(panel);

        // Player values
        panel = new ImagePanel();
        panel.setLayout(new GridLayout(20, 2, 4, 4));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("BestePosition"));
        panel.add(label);
        panel.add(jpBestPosition.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Torwart"));
        panel.add(label);
        panel.add(jpRatingKeeper.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Innenverteidiger"));
        panel.add(label);
        panel.add(jpRatingDefender.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Innenverteidiger_Aus"));
        panel.add(label);
        panel.add(jpRatingDefenderTowardsWing.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Innenverteidiger_Off"));
        panel.add(label);
        panel.add(jpRatingDefenderOffensive.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Aussenverteidiger"));
        panel.add(label);
        panel.add(jpRatingWingback.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Aussenverteidiger_In"));
        panel.add(label);
        panel.add(jpRatingWingbackTowardsMiddle.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Aussenverteidiger_Off"));
        panel.add(label);
        panel.add(jpRatingWingbackOffensive.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Aussenverteidiger_Def"));
        panel.add(label);
        panel.add(jpRatingWingbackDefensive.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Mittelfeld"));
        panel.add(label);
        panel.add(jpRatingMidfielder.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Mittelfeld_Aus"));
        panel.add(label);
        panel.add(jpRatingMidfielderTowardsWing.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Mittelfeld_Off"));
        panel.add(label);
        panel.add(jpRatingMidfielderOffensive.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Mittelfeld_Def"));
        panel.add(label);
        panel.add(jpRatingMidfielderDefensive.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Fluegelspiel"));
        panel.add(label);
        panel.add(jpRatingWinger.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Fluegelspiel_In"));
        panel.add(label);
        panel.add(jpRatingWingerTowardsMiddle.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Fluegelspiel_Off"));
        panel.add(label);
        panel.add(jpRatingWingerOffensive.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Fluegelspiel_Def"));
        panel.add(label);
        panel.add(jpRatingWingerDefensive.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Sturm"));
        panel.add(label);
        panel.add(jpRatingForward.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Sturm_Aus"));
        panel.add(label);
        panel.add(jpRatingForwardTowardsWing.getComponent(false));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Sturm_Def"));
        panel.add(label);
        panel.add(jpRatingForwardDefensive.getComponent(false));

        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        layout.setConstraints(panel, constraints);
        add(panel);

        // Buttons
        panel = new ImagePanel();
        panel.setLayout(new GridLayout(6, 1, 4, 4));

        jbAdd.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Transferscout_hinzufuegen"));
        jbAdd.addActionListener(this);
        panel.add(jbAdd);
        jbRemove.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Transferscout_entfernen"));
        jbRemove.addActionListener(this);
        jbRemove.setEnabled(false);
        panel.add(jbRemove);
		jbRemoveAll.addActionListener(this);
		jbRemoveAll.setToolTipText(HOVerwaltung.instance().getLanguageString("Scout.tt_RemoveAll"));
		panel.add(jbRemoveAll);
        jbMiniScout.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Transferscout_Miniscout"));
        jbMiniScout.addActionListener(this);
        panel.add(jbMiniScout);
        jbPrint.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Transferscout_drucken"));
        jbPrint.addActionListener(this);
        panel.add(jbPrint);
        jbAddTempSpieler.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_add_tempspieler"));
        jbAddTempSpieler.addActionListener(this);
        panel.add(jbAddTempSpieler);

        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.NORTH;
        layout.setConstraints(panel, constraints);
        add(panel);

        setScoutEintrag(null);
    }
}
