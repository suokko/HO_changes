// %1128099595437:de.hattrickorganizer.gui.transferscout%
package de.hattrickorganizer.gui.transferscout;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;

import plugins.IEPVData;
import de.hattrickorganizer.gui.model.CBItem;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.model.EPVData;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.ScoutEintrag;
import de.hattrickorganizer.model.Spieler;
import de.hattrickorganizer.model.SpielerPosition;


/**
 * MiniScout dialog
 *
 * @author Marco Senn
 */
public class MiniScoutDialog extends JFrame implements ItemListener, ActionListener, FocusListener {
	
	private static final long serialVersionUID = -2092930481559683730L;
	
    //~ Instance fields ----------------------------------------------------------------------------
	private JButton jbApply = new JButton(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Uebernehmen"));
    private JButton jbApplyScout = new JButton(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Uebernehmen"));
    private JButton jbCancel = new JButton(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Abbrechen"));
    private JComboBox jcbAttacking = new JComboBox(de.hattrickorganizer.tools.Helper.EINSTUFUNG);
    private JComboBox jcbDefense = new JComboBox(de.hattrickorganizer.tools.Helper.EINSTUFUNG);
    private JComboBox jcbExperience = new JComboBox(de.hattrickorganizer.tools.Helper.EINSTUFUNG);
    private JComboBox jcbForm = new JComboBox(de.hattrickorganizer.tools.Helper.EINSTUFUNG_FORM);
    private JComboBox jcbKeeper = new JComboBox(de.hattrickorganizer.tools.Helper.EINSTUFUNG);
    private JComboBox jcbPassing = new JComboBox(de.hattrickorganizer.tools.Helper.EINSTUFUNG);
    private JComboBox jcbPlaymaking = new JComboBox(de.hattrickorganizer.tools.Helper.EINSTUFUNG);
    private JComboBox jcbSpeciality = new JComboBox(de.hattrickorganizer.tools.Helper.EINSTUFUNG_SPECIALITY);
    private JComboBox jcbStamina = new JComboBox(de.hattrickorganizer.tools.Helper.EINSTUFUNG_KONDITION);
    private JComboBox jcbStandards = new JComboBox(de.hattrickorganizer.tools.Helper.EINSTUFUNG);
    private JComboBox jcbWinger = new JComboBox(de.hattrickorganizer.tools.Helper.EINSTUFUNG);
    private JLabel jlRating = new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Unbestimmt")
                                         + ": 0.0");
    private JLabel jlStatus = new JLabel("Status: ");
    private JTextArea jtaCopyPaste = new JTextArea(5, 20);
    private JTextArea jtaNotes = new JTextArea(5, 20);
    private JTextField jtfAge = new JTextField("17.0");
    private JTextField jtfName = new JTextField();
    private JTextField jtfPlayerID = new JTextField("0");
    private JTextField jtfPrice = new JTextField("0");
    private JTextField jtfTSI = new JTextField("1000");
    private JLabel jtfEPV = new JLabel("",SwingConstants.RIGHT);
    private SpinnerDateModel clSpinnerModel = new SpinnerDateModel();
    private JSpinner jsSpinner = new JSpinner(clSpinnerModel);
    private TransferEingabePanel clOwner;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new MiniScoutDialog object.
     *
     * @param owner the parent control holding this dialog
     */
    public MiniScoutDialog(TransferEingabePanel owner) {
        super(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("ScoutMini"));
        this.setIconImage(de.hattrickorganizer.tools.Helper.loadImage("gui/bilder/Logo-16px.png"));
        clOwner = owner;
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Save position of MiniScout window
     *
     * @param isVisible visible when isVisible equals true
     */
    @Override
	public final void setVisible(boolean isVisible) {
        super.setVisible(isVisible);

        if (!isVisible) {
            gui.UserParameter.instance().miniscout_PositionX = this.getLocation().x;
            gui.UserParameter.instance().miniscout_PositionY = this.getLocation().y;
        }
    }

    /**
     * Fired when any button is pressed. Corresponding operation is then called
     *
     * @param actionEvent event given when pressed a button
     */
    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(jbApply)) {
            clOwner.setScoutEintrag(createScoutEintrag());
            this.setVisible(false);
            this.dispose();
            de.hattrickorganizer.gui.HOMainFrame.instance().setVisible(true);
        } else if (actionEvent.getSource().equals(jbCancel)) {
            this.setVisible(false);
            this.dispose();
            de.hattrickorganizer.gui.HOMainFrame.instance().setVisible(true);
        } else if (actionEvent.getSource().equals(jbApplyScout)) {
            copyPaste();
		} else if (actionEvent.getSource().equals(jtfAge)) {
			spielervalueChanged();
        }
        
    }

    /**
     * Called when dialog gets focus
     *
     * @param e event when window gets focus
     */
    public void focusGained(FocusEvent e) {
    }

    /**
     * Called when dialog loses focus
     *
     * @param e event when window loses focus
     */
    public final void focusLost(FocusEvent e) {
        if (!de.hattrickorganizer.tools.Helper.parseInt(de.hattrickorganizer.gui.HOMainFrame
                                                           .instance(), jtfTSI, false)
            || !de.hattrickorganizer.tools.Helper.parseInt(de.hattrickorganizer.gui.HOMainFrame
                                                           .instance(), jtfPlayerID, false)
            || !de.hattrickorganizer.tools.Helper.parseInt(de.hattrickorganizer.gui.HOMainFrame
                                                           .instance(), jtfPrice, false)) {
            return;
        }
		if (e.getSource().equals(jtfAge)) {
			spielervalueChanged();
		}
    }

    /**
     * Called when combobox value has changed
     *
     * @param itemEvent event when combobox changes
     */
    public final void itemStateChanged(java.awt.event.ItemEvent itemEvent) {
        if (itemEvent.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            spielervalueChanged();
        }
    }

    /**
     * Calls playerconverter and fills boxes to the corresponding values
     */
    private void copyPaste() {
        final PlayerConverter pc = new PlayerConverter();
        String message = "";

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
                de.hattrickorganizer.tools.Helper.markierenComboBox(jcbSpeciality,
                                                                    player.getSpeciality());
                jcbSpeciality.addItemListener(this);
                jcbExperience.removeItemListener(this);
                de.hattrickorganizer.tools.Helper.markierenComboBox(jcbExperience,
                                                                    player.getExperience());
                jcbExperience.addItemListener(this);
                jcbForm.removeItemListener(this);
                de.hattrickorganizer.tools.Helper.markierenComboBox(jcbForm, player.getForm());
                jcbForm.addItemListener(this);
                jcbStamina.removeItemListener(this);
                de.hattrickorganizer.tools.Helper.markierenComboBox(jcbStamina, player.getStamina());
                jcbStamina.addItemListener(this);
                jcbDefense.removeItemListener(this);
                de.hattrickorganizer.tools.Helper.markierenComboBox(jcbDefense, player.getDefense());
                jcbDefense.addItemListener(this);
                jcbAttacking.removeItemListener(this);
                de.hattrickorganizer.tools.Helper.markierenComboBox(jcbAttacking, player.getAttack());
                jcbAttacking.addItemListener(this);
                jcbKeeper.removeItemListener(this);
                de.hattrickorganizer.tools.Helper.markierenComboBox(jcbKeeper,
                                                                    player.getGoalKeeping());
                jcbKeeper.addItemListener(this);
                jcbWinger.removeItemListener(this);
                de.hattrickorganizer.tools.Helper.markierenComboBox(jcbWinger, player.getWing());
                jcbWinger.addItemListener(this);
                jcbPassing.removeItemListener(this);
                de.hattrickorganizer.tools.Helper.markierenComboBox(jcbPassing, player.getPassing());
                jcbPassing.addItemListener(this);
                jcbStandards.removeItemListener(this);
                de.hattrickorganizer.tools.Helper.markierenComboBox(jcbStandards,
                                                                    player.getSetPieces());
                jcbStandards.addItemListener(this);

                // Listener stays here for recalculation of rating
                de.hattrickorganizer.tools.Helper.markierenComboBox(jcbPlaymaking,
                                                                    player.getPlayMaking());

                // Normally not working. Thus last positioned
                final java.text.SimpleDateFormat simpleFormat = new java.text.SimpleDateFormat("dd.MM.yy HH:mm",
                                                                                               java.util.Locale.GERMANY);
                final java.util.Date date = simpleFormat.parse(player.getExpiryDate() + " "
                                                               + player.getExpiryTime());
                jsSpinner.setValue(date);
                spielervalueChanged();
            }
        } catch (Exception e) {
            message = de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("scout_error");
        }

        jtaCopyPaste.setText("");

        if (message.equals("")) {
            switch (pc.getError()) {
                case 1:
                    message = de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("scout_warning");
                    break;

                case 2:
                    message = de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("scout_error");
                    break;

                default:
                    message = de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("scout_success");
            }
        }

        jlStatus.setText(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("scout_status") + ": "
                         + message);
    }

    /**
     * Create new Scout entry
     *
     * @return Returns new scout entry
     */
    private ScoutEintrag createScoutEintrag() {
        final ScoutEintrag entry = new ScoutEintrag();

        if (!de.hattrickorganizer.tools.Helper.parseInt(de.hattrickorganizer.gui.HOMainFrame
                                                           .instance(), jtfTSI, false)
            || !de.hattrickorganizer.tools.Helper.parseInt(de.hattrickorganizer.gui.HOMainFrame
                                                           .instance(), jtfPlayerID, false)
            || !de.hattrickorganizer.tools.Helper.parseInt(de.hattrickorganizer.gui.HOMainFrame
                                                           .instance(), jtfPrice, false)) {
            return entry;
        }

        entry.setPlayerID(Integer.parseInt(jtfPlayerID.getText()));
        entry.setAlter(Integer.parseInt(jtfAge.getText().replaceFirst("\\..*", "")));
        entry.setAgeDays(Integer.parseInt(jtfAge.getText().replaceFirst(".*\\.", "")));
        entry.setPrice(Integer.parseInt(jtfPrice.getText()));
        entry.setTSI(Integer.parseInt(jtfTSI.getText()));
        entry.setSpeciality(((CBItem) jcbSpeciality.getSelectedItem()).getId());
        entry.setErfahrung(((CBItem) jcbExperience.getSelectedItem()).getId());
        entry.setForm(((CBItem) jcbForm.getSelectedItem()).getId());
        entry.setKondition(((CBItem) jcbStamina.getSelectedItem()).getId());
        entry.setVerteidigung(((CBItem) jcbDefense.getSelectedItem()).getId());
        entry.setTorschuss(((CBItem) jcbAttacking.getSelectedItem()).getId());
        entry.setTorwart(((CBItem) jcbKeeper.getSelectedItem()).getId());
        entry.setFluegelspiel(((CBItem) jcbWinger.getSelectedItem()).getId());
        entry.setPasspiel(((CBItem) jcbPassing.getSelectedItem()).getId());
        entry.setStandards(((CBItem) jcbStandards.getSelectedItem()).getId());
        entry.setSpielaufbau(((CBItem) jcbPlaymaking.getSelectedItem()).getId());
        entry.setName(jtfName.getText());
        entry.setInfo(jtaNotes.getText());
        //entry.setDeadline(new java.sql.Timestamp( zeitlong ) );
        entry.setDeadline(new java.sql.Timestamp(clSpinnerModel.getDate().getTime()));

        return entry;
    }

	/**
     * Creates all the components on the panel
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

        setContentPane(new ImagePanel());
        getContentPane().setLayout(layout);

        // Textfields and Comboboxes
        panel = new ImagePanel();
        panel.setLayout(new GridLayout(9, 4, 4, 4));

        label = new JLabel(HOVerwaltung.instance().getLanguageString("ID"));
        panel.add(label);
        jtfPlayerID.setHorizontalAlignment(SwingConstants.RIGHT);
        jtfPlayerID.addFocusListener(this);
        panel.add(jtfPlayerID);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Name"));
        panel.add(label);
        jtfName.addFocusListener(this);
        panel.add(jtfName);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("Alter"));
        panel.add(label);
        jtfAge.setHorizontalAlignment(SwingConstants.RIGHT);
        jtfAge.addFocusListener(this);
        panel.add(jtfAge);

        label = new JLabel("TSI");
        panel.add(label);
        jtfTSI.setHorizontalAlignment(SwingConstants.RIGHT);
        jtfTSI.addFocusListener(this);
        panel.add(jtfTSI);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("scout_price"));
        panel.add(label);
        jtfPrice.setHorizontalAlignment(SwingConstants.RIGHT);
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
        jcbDefense.addItemListener(this);
        panel.add(jcbDefense);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("skill.scoring"));
        panel.add(label);
        jcbAttacking.addItemListener(this);
        panel.add(jcbAttacking);

        label = new JLabel(HOVerwaltung.instance().getLanguageString("skill.set_pieces"));
        panel.add(label);
        jcbStandards.addItemListener(this);
        panel.add(jcbStandards);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        layout.setConstraints(panel, constraints);
        getContentPane().add(panel);

        panel = new ImagePanel();
        panel.setLayout(new GridLayout(1, 3, 4, 4));
        label = new JLabel(HOVerwaltung.instance().getLanguageString("BestePosition")
                           + ":");
        label.setFont(new Font(label.getFont().getName(), Font.BOLD, label.getFont().getSize() + 2));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);
        jlRating.setFont(new Font(jlRating.getFont().getName(), Font.BOLD,
                                  jlRating.getFont().getSize() + 2));
        jlRating.setHorizontalAlignment(SwingConstants.LEFT);
        panel.add(jlRating);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        layout.setConstraints(panel, constraints);
        getContentPane().add(panel);

        // Notes
        panel = new ImagePanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(javax.swing.BorderFactory.createTitledBorder(HOVerwaltung.instance().getLanguageString("Notizen")));
        panel.add(new JScrollPane(jtaNotes), BorderLayout.CENTER);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.NORTH;
        layout.setConstraints(panel, constraints);
        getContentPane().add(panel);

        // Copy & Paste
        panel = new ImagePanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(javax.swing.BorderFactory.createTitledBorder(HOVerwaltung.instance().getLanguageString("CopyPaste")));
        jtaCopyPaste.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Transferscout_CopyPaste"));
        panel.add(new JScrollPane(jtaCopyPaste), BorderLayout.NORTH);
        layout.setConstraints(jbApplyScout, constraints);
        jbApplyScout.addActionListener(this);
        panel.add(jbApplyScout, BorderLayout.CENTER);
        panel.add(jlStatus, BorderLayout.SOUTH);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.NORTH;
        layout.setConstraints(panel, constraints);
        getContentPane().add(panel);

        panel = new ImagePanel();
        panel.setLayout(new GridLayout(1, 2, 4, 4));

        jbApply.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Transferscout_uebernehmen"));
        jbApply.addActionListener(this);
        panel.add(jbApply);
        jbCancel.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Transferscout_abbrechen"));
        jbCancel.addActionListener(this);
        panel.add(jbCancel);

        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.NORTH;
        layout.setConstraints(panel, constraints);
        getContentPane().add(panel);

        spielervalueChanged();
        pack();

        setLocation(gui.UserParameter.instance().miniscout_PositionX,
                    gui.UserParameter.instance().miniscout_PositionY);
        de.hattrickorganizer.gui.HOMainFrame.instance().setVisible(false);

        setVisible(true);
    }

    /**
     * Called whenever value changes. Positions are recalculated
     */
    private void spielervalueChanged() {
        final Spieler tempSpieler = new Spieler();
        tempSpieler.setSpezialitaet(((CBItem) jcbSpeciality.getSelectedItem()).getId());
        tempSpieler.setErfahrung(((CBItem) jcbExperience.getSelectedItem()).getId());
		tempSpieler.setFuehrung(3);        
        tempSpieler.setForm(((CBItem) jcbForm.getSelectedItem()).getId());
        tempSpieler.setKondition(((CBItem) jcbStamina.getSelectedItem()).getId());
        tempSpieler.setVerteidigung(((CBItem) jcbDefense.getSelectedItem()).getId());
        tempSpieler.setTorschuss(((CBItem) jcbAttacking.getSelectedItem()).getId());
        tempSpieler.setTorwart(((CBItem) jcbKeeper.getSelectedItem()).getId());
        tempSpieler.setFluegelspiel(((CBItem) jcbWinger.getSelectedItem()).getId());
        tempSpieler.setPasspiel(((CBItem) jcbPassing.getSelectedItem()).getId());
        tempSpieler.setStandards(((CBItem) jcbStandards.getSelectedItem()).getId());
        tempSpieler.setSpielaufbau(((CBItem) jcbPlaymaking.getSelectedItem()).getId());
        tempSpieler.setAlter(Integer.parseInt(jtfAge.getText().replaceFirst("\\..*", "")));
        tempSpieler.setAgeDays(Integer.parseInt(jtfAge.getText().replaceFirst(".*\\.", "")));
		IEPVData data = new EPVData(tempSpieler);
		double price = HOVerwaltung.instance().getModel().getEPV().getPrice(data);
		jtfEPV.setText(NumberFormat.getCurrencyInstance().format(price));		
        jlRating.setText(SpielerPosition.getNameForPosition(tempSpieler.getIdealPosition()) + " ("
                         + tempSpieler.calcPosValue(tempSpieler.getIdealPosition(), true) + ")");
    }
}
