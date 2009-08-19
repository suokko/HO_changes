// %3381173579:hoplugins.teamplanner.ui.tabs.players%
package hoplugins.teamplanner.ui.tabs.players;

import hoplugins.Commons;

import hoplugins.teamplanner.vo.PlayerData;

import plugins.IEPVData;
import plugins.ISpieler;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemListener;

import java.text.DecimalFormat;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


/**
 * MiniScout dialog
 *
 * @author Marco Senn
 */
public class PlayerDetail extends JPanel implements ItemListener, ActionListener, FocusListener {
    //~ Static fields/initializers -----------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 3072082825289516973L;

	private static DecimalFormat dfPrice = new DecimalFormat("#,###");

    /** Gesamteinstufung */
    public static final CBItem[] EINSTUFUNG = {
                                                  new CBItem(Commons.getModel().getHelper()
                                                                    .getNameForSkill(ISpieler.katastrophal,
                                                                                     false),
                                                             ISpieler.katastrophal),
                                                  new CBItem(Commons.getModel().getHelper()
                                                                    .getNameForSkill(ISpieler.erbaermlich,
                                                                                     false),
                                                             ISpieler.erbaermlich),
                                                  new CBItem(Commons.getModel().getHelper()
                                                                    .getNameForSkill(ISpieler.armselig,
                                                                                     false),
                                                             ISpieler.armselig),
                                                  new CBItem(Commons.getModel().getHelper()
                                                                    .getNameForSkill(ISpieler.schwach,
                                                                                     false),
                                                             ISpieler.schwach),
                                                  new CBItem(Commons.getModel().getHelper()
                                                                    .getNameForSkill(ISpieler.durchschnittlich,
                                                                                     false),
                                                             ISpieler.durchschnittlich),
                                                  new CBItem(Commons.getModel().getHelper()
                                                                    .getNameForSkill(ISpieler.passabel,
                                                                                     false),
                                                             ISpieler.passabel),
                                                  new CBItem(Commons.getModel().getHelper()
                                                                    .getNameForSkill(ISpieler.gut,
                                                                                     false),
                                                             ISpieler.gut),
                                                  new CBItem(Commons.getModel().getHelper()
                                                                    .getNameForSkill(ISpieler.sehr_gut,
                                                                                     false),
                                                             ISpieler.sehr_gut),
                                                  new CBItem(Commons.getModel().getHelper()
                                                                    .getNameForSkill(ISpieler.hervorragend,
                                                                                     false),
                                                             ISpieler.hervorragend),
                                                  new CBItem(Commons.getModel().getHelper()
                                                                    .getNameForSkill(ISpieler.grossartig,
                                                                                     false),
                                                             ISpieler.grossartig),
                                                  new CBItem(Commons.getModel().getHelper()
                                                                    .getNameForSkill(ISpieler.brilliant,
                                                                                     false),
                                                             ISpieler.brilliant),
                                                  new CBItem(Commons.getModel().getHelper()
                                                                    .getNameForSkill(ISpieler.fantastisch,
                                                                                     false),
                                                             ISpieler.fantastisch),
                                                  new CBItem(Commons.getModel().getHelper()
                                                                    .getNameForSkill(ISpieler.Weltklasse,
                                                                                     false),
                                                             ISpieler.Weltklasse),
                                                  new CBItem(Commons.getModel().getHelper()
                                                                    .getNameForSkill(ISpieler.uebernatuerlich,
                                                                                     false),
                                                             ISpieler.uebernatuerlich),
                                                  new CBItem(Commons.getModel().getHelper()
                                                                    .getNameForSkill(ISpieler.gigantisch,
                                                                                     false),
                                                             ISpieler.gigantisch),
                                                  new CBItem(Commons.getModel().getHelper()
                                                                    .getNameForSkill(ISpieler.ausserirdisch,
                                                                                     false),
                                                             ISpieler.ausserirdisch),
                                                  new CBItem(Commons.getModel().getHelper()
                                                                    .getNameForSkill(ISpieler.mythisch,
                                                                                     false),
                                                             ISpieler.mythisch),
                                                  new CBItem(Commons.getModel().getHelper()
                                                                    .getNameForSkill(ISpieler.maerchenhaft,
                                                                                     false),
                                                             ISpieler.maerchenhaft),
                                                  new CBItem(Commons.getModel().getHelper()
                                                                    .getNameForSkill(ISpieler.galaktisch,
                                                                                     false),
                                                             ISpieler.galaktisch),
                                                  new CBItem(Commons.getModel().getHelper()
                                                                    .getNameForSkill(ISpieler.goettlich,
                                                                                     false),
                                                             ISpieler.goettlich)
                                              };

    /** Form */
    public static final CBItem[] EINSTUFUNG_FORM = {
                                                       new CBItem(Commons.getModel().getHelper()
                                                                         .getNameForSkill(ISpieler.katastrophal,
                                                                                          false),
                                                                  ISpieler.katastrophal),
                                                       new CBItem(Commons.getModel().getHelper()
                                                                         .getNameForSkill(ISpieler.erbaermlich,
                                                                                          false),
                                                                  ISpieler.erbaermlich),
                                                       new CBItem(Commons.getModel().getHelper()
                                                                         .getNameForSkill(ISpieler.armselig,
                                                                                          false),
                                                                  ISpieler.armselig),
                                                       new CBItem(Commons.getModel().getHelper()
                                                                         .getNameForSkill(ISpieler.schwach,
                                                                                          false),
                                                                  ISpieler.schwach),
                                                       new CBItem(Commons.getModel().getHelper()
                                                                         .getNameForSkill(ISpieler.durchschnittlich,
                                                                                          false),
                                                                  ISpieler.durchschnittlich),
                                                       new CBItem(Commons.getModel().getHelper()
                                                                         .getNameForSkill(ISpieler.passabel,
                                                                                          false),
                                                                  ISpieler.passabel),
                                                       new CBItem(Commons.getModel().getHelper()
                                                                         .getNameForSkill(ISpieler.gut,
                                                                                          false),
                                                                  ISpieler.gut),
                                                       new CBItem(Commons.getModel().getHelper()
                                                                         .getNameForSkill(ISpieler.sehr_gut,
                                                                                          false),
                                                                  ISpieler.sehr_gut)
                                                   };

    /** Kondition */
    public static final CBItem[] EINSTUFUNG_KONDITION = {
                                                            new CBItem(Commons.getModel().getHelper()
                                                                              .getNameForSkill(ISpieler.katastrophal,
                                                                                               false),
                                                                       ISpieler.katastrophal),
                                                            new CBItem(Commons.getModel().getHelper()
                                                                              .getNameForSkill(ISpieler.erbaermlich,
                                                                                               false),
                                                                       ISpieler.erbaermlich),
                                                            new CBItem(Commons.getModel().getHelper()
                                                                              .getNameForSkill(ISpieler.armselig,
                                                                                               false),
                                                                       ISpieler.armselig),
                                                            new CBItem(Commons.getModel().getHelper()
                                                                              .getNameForSkill(ISpieler.schwach,
                                                                                               false),
                                                                       ISpieler.schwach),
                                                            new CBItem(Commons.getModel().getHelper()
                                                                              .getNameForSkill(ISpieler.durchschnittlich,
                                                                                               false),
                                                                       ISpieler.durchschnittlich),
                                                            new CBItem(Commons.getModel().getHelper()
                                                                              .getNameForSkill(ISpieler.passabel,
                                                                                               false),
                                                                       ISpieler.passabel),
                                                            new CBItem(Commons.getModel().getHelper()
                                                                              .getNameForSkill(ISpieler.gut,
                                                                                               false),
                                                                       ISpieler.gut),
                                                            new CBItem(Commons.getModel().getHelper()
                                                                              .getNameForSkill(ISpieler.sehr_gut,
                                                                                               false),
                                                                       ISpieler.sehr_gut),
                                                            new CBItem(Commons.getModel().getHelper()
                                                                              .getNameForSkill(ISpieler.hervorragend,
                                                                                               false),
                                                                       ISpieler.hervorragend)
                                                        };

    /** Speciality */
    public static final CBItem[] EINSTUFUNG_SPECIALITY = {
                                                             new CBItem("", 0),
                                                             new CBItem(Commons.getModel().getLanguageString("sp_Technical"),
                                                                        1),
                                                             new CBItem(Commons.getModel().getLanguageString("sp_Quick"),
                                                                        2),
                                                             new CBItem(Commons.getModel().getLanguageString("sp_Powerful"),
                                                                        3),
                                                             new CBItem(Commons.getModel().getLanguageString("sp_Unpredictable"),
                                                                        4),
                                                             new CBItem(Commons.getModel().getLanguageString("sp_Head"),
                                                                        5)
                                                         };

    //~ Instance fields ----------------------------------------------------------------------------

    private JComboBox jcbAttacking = new JComboBox(EINSTUFUNG);
    private JComboBox jcbDefense = new JComboBox(EINSTUFUNG);
    private JComboBox jcbExperience = new JComboBox(EINSTUFUNG);
    private JComboBox jcbForm = new JComboBox(EINSTUFUNG_FORM);
    private JComboBox jcbKeeper = new JComboBox(EINSTUFUNG);
    private JComboBox jcbPassing = new JComboBox(EINSTUFUNG);
    private JComboBox jcbPlaymaking = new JComboBox(EINSTUFUNG);
    private JComboBox jcbSpeciality = new JComboBox(EINSTUFUNG_SPECIALITY);
    private JComboBox jcbStamina = new JComboBox(EINSTUFUNG_KONDITION);
    private JComboBox jcbStandards = new JComboBox(EINSTUFUNG);
    private JComboBox jcbWinger = new JComboBox(EINSTUFUNG);
    private JTextField jtfAge = new JTextField("0");
    private JTextField jtfEPV = new JTextField("", SwingConstants.RIGHT);
    private JTextField jtfTSI = new JTextField("1000");
    private PlayerData tempSpieler = null;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new MiniScoutDialog object.
     */
    public PlayerDetail() {
        super();
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Fired when any button is pressed. Corresponding operation is then called
     *
     * @param actionEvent event given when pressed a button
     */
    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(jtfAge)) {
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
     * Missing Method Documentation
     *
     * @param player Missing Method Parameter Documentation
     */
    public void setPlayer(ISpieler player) {
        try {
            if (player != null) {
                jtfAge.setText(player.getAlter() + "");
                jtfTSI.setText(player.getTSI() + "");
                jcbSpeciality.removeItemListener(this);
                markierenComboBox(jcbSpeciality, player.getSpezialitaet());
                jcbSpeciality.addItemListener(this);
                jcbExperience.removeItemListener(this);
                markierenComboBox(jcbExperience, player.getErfahrung());
                jcbExperience.addItemListener(this);
                jcbForm.removeItemListener(this);
                markierenComboBox(jcbForm, player.getForm());
                jcbForm.addItemListener(this);
                jcbStamina.removeItemListener(this);
                markierenComboBox(jcbStamina, player.getKondition());
                jcbStamina.addItemListener(this);
                jcbDefense.removeItemListener(this);
                markierenComboBox(jcbDefense, player.getVerteidigung());
                jcbDefense.addItemListener(this);
                jcbAttacking.removeItemListener(this);
                markierenComboBox(jcbAttacking, player.getTorschuss());
                jcbAttacking.addItemListener(this);
                jcbKeeper.removeItemListener(this);
                markierenComboBox(jcbKeeper, player.getTorwart());
                jcbKeeper.addItemListener(this);
                jcbWinger.removeItemListener(this);
                markierenComboBox(jcbWinger, player.getFluegelspiel());
                jcbWinger.addItemListener(this);
                jcbPassing.removeItemListener(this);
                markierenComboBox(jcbPassing, player.getPasspiel());
                jcbPassing.addItemListener(this);
                jcbStandards.removeItemListener(this);
                markierenComboBox(jcbStandards, player.getStandards());
                jcbStandards.addItemListener(this);
                jcbPlaymaking.removeItemListener(this);
                markierenComboBox(jcbPlaymaking, player.getSpielaufbau());
                jcbPlaymaking.addItemListener(this);

                spielervalueChanged();
            }
        } catch (Exception e) {
        }
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public PlayerData getPlayerData() {
        return tempSpieler;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getPrice() {
        String epv = jtfEPV.getText();

        try {
            return dfPrice.parse(epv).intValue();
        } catch (Exception e) {
        }

        return 0;
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

        JPanel mainPanel = Commons.getModel().getGUI().createImagePanel();
        mainPanel.setLayout(layout);

        // Textfields and Comboboxes
        panel = Commons.getModel().getGUI().createImagePanel();
        panel.setLayout(new GridLayout(9, 4, 4, 4));

        label = new JLabel(Commons.getModel().getLanguageString("Alter"));
        panel.add(label);
        jtfAge.setHorizontalAlignment(SwingConstants.RIGHT);
        jtfAge.addFocusListener(this);
        panel.add(jtfAge);

        label = new JLabel("TSI");
        panel.add(label);
        jtfTSI.setHorizontalAlignment(SwingConstants.RIGHT);
        jtfTSI.addFocusListener(this);
        panel.add(jtfTSI);

        label = new JLabel(Commons.getModel().getLanguageString("Spezialitaet"));
        panel.add(label);
        jcbSpeciality.addItemListener(this);
        panel.add(jcbSpeciality);

        label = new JLabel("EPV");
        panel.add(label);
        panel.add(jtfEPV);

        label = new JLabel(Commons.getModel().getLanguageString("Erfahrung"));
        panel.add(label);
        jcbExperience.addItemListener(this);
        panel.add(jcbExperience);

        label = new JLabel(Commons.getModel().getLanguageString("Form"));
        panel.add(label);
        jcbForm.addItemListener(this);
        panel.add(jcbForm);

        label = new JLabel(Commons.getModel().getLanguageString("Kondition"));
        panel.add(label);
        jcbStamina.addItemListener(this);
        panel.add(jcbStamina);

        label = new JLabel(Commons.getModel().getLanguageString("Torwart"));
        panel.add(label);
        jcbKeeper.addItemListener(this);
        panel.add(jcbKeeper);

        label = new JLabel(Commons.getModel().getLanguageString("Spielaufbau"));
        panel.add(label);
        jcbPlaymaking.addItemListener(this);
        panel.add(jcbPlaymaking);

        label = new JLabel(Commons.getModel().getLanguageString("Passpiel"));
        panel.add(label);
        jcbPassing.addItemListener(this);
        panel.add(jcbPassing);

        label = new JLabel(Commons.getModel().getLanguageString("Fluegelspiel"));
        panel.add(label);
        jcbWinger.addItemListener(this);
        panel.add(jcbWinger);

        label = new JLabel(Commons.getModel().getLanguageString("Verteidigung"));
        panel.add(label);
        jcbDefense.addItemListener(this);
        panel.add(jcbDefense);

        label = new JLabel(Commons.getModel().getLanguageString("Torschuss"));
        panel.add(label);
        jcbAttacking.addItemListener(this);
        panel.add(jcbAttacking);

        label = new JLabel(Commons.getModel().getLanguageString("Standards"));
        panel.add(label);
        jcbStandards.addItemListener(this);
        panel.add(jcbStandards);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTH;
        layout.setConstraints(panel, constraints);
        mainPanel.add(panel);
        add(mainPanel);

        spielervalueChanged();

        setLocation(gui.UserParameter.instance().miniscout_PositionX,
                    gui.UserParameter.instance().miniscout_PositionY);
    }

    /**
     * Missing Method Documentation
     *
     * @param combobox Missing Method Parameter Documentation
     * @param id Missing Method Parameter Documentation
     */
    private void markierenComboBox(javax.swing.JComboBox combobox, int id) {
        final javax.swing.ComboBoxModel model = combobox.getModel();

        for (int i = 0; i < model.getSize(); i++) {
            if (((CBItem) (model.getElementAt(i))).getId() == id) {
                combobox.setSelectedItem(model.getElementAt(i));
                break;
            }
        }
    }

    /**
     * Called whenever value changes. Positions are recalculated
     */
    private void spielervalueChanged() {
        tempSpieler = new PlayerData();
        tempSpieler.setSpeciality(((CBItem) jcbSpeciality.getSelectedItem()).getId());
        tempSpieler.setExperience(((CBItem) jcbExperience.getSelectedItem()).getId());
        tempSpieler.setLeadership(3);
        tempSpieler.setForm(((CBItem) jcbForm.getSelectedItem()).getId());
        tempSpieler.setStamina(((CBItem) jcbStamina.getSelectedItem()).getId());
        tempSpieler.setDefense(((CBItem) jcbDefense.getSelectedItem()).getId());
        tempSpieler.setAttack(((CBItem) jcbAttacking.getSelectedItem()).getId());
        tempSpieler.setGoalKeeping(((CBItem) jcbKeeper.getSelectedItem()).getId());
        tempSpieler.setWing(((CBItem) jcbWinger.getSelectedItem()).getId());
        tempSpieler.setPassing(((CBItem) jcbPassing.getSelectedItem()).getId());
        tempSpieler.setSetPieces(((CBItem) jcbStandards.getSelectedItem()).getId());
        tempSpieler.setPlayMaking(((CBItem) jcbPlaymaking.getSelectedItem()).getId());
        tempSpieler.setAge(Integer.parseInt(jtfAge.getText()));

        IEPVData data = Commons.getModel().getEPV().getEPVData(tempSpieler);
        double price = Commons.getModel().getEPV().getPrice(data);
        double curr_rate = Commons.getModel().getXtraDaten().getCurrencyRate();
        price = (price * 10d) / curr_rate;
        jtfEPV.setText(dfPrice.format(price));
    }
}
