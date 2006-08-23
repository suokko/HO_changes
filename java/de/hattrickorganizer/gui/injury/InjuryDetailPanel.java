// %4234203651:de.hattrickorganizer.gui.injury%
package de.hattrickorganizer.gui.injury;

import de.hattrickorganizer.gui.keepertool.PlayerItem;
import de.hattrickorganizer.gui.pluginWrapper.GUIPluginWrapper;
import de.hattrickorganizer.model.HOMiniModel;
import de.hattrickorganizer.model.HOVerwaltung;

import plugins.ISpieler;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.sql.ResultSet;

import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * Panel that contains Player and Injury Details
 *
 * @author draghetto
 */
public class InjuryDetailPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    private JComboBox injuryType = new JComboBox();
    private JComboBox players = new JComboBox();
    private JTextField age = new JTextField(8);
    private JTextField injury = new JTextField(8);
    private JTextField tsiPost = new JTextField(8);
    private JTextField tsiPre = new JTextField(8);

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new InjuryDetailPanel object.
     */
    public InjuryDetailPanel() {
        init();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Returns the Player's age
     *
     * @return age
     */
    public final int getAge() {
        try {
            return Integer.parseInt(age.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Returns the desired recovery rate. - 0.0 Healthy - 0.5 Bruised - 1.0 Injured but Training
     *
     * @return the rate
     */
    public final double getDesiredLevel() {
        return injuryType.getSelectedIndex() / 2.0d;
    }

    /**
     * Returns the actual injury length
     *
     * @return injury
     */
    public final int getInjury() {
        try {
            return Integer.parseInt(injury.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Returns the TSI before the injury
     *
     * @return TSI
     */
    public final int getTSIPost() {
        try {
            return Integer.parseInt(tsiPost.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Returns the TSI after the injury, must be before a training
     *
     * @return TSI
     */
    public final int getTSIPre() {
        try {
            return Integer.parseInt(tsiPre.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Method called to reset panel and reload needed data
     */
    public final void reload() {
        players.removeAllItems();
        players.addItem(new PlayerItem());

        for (Iterator iter = HOVerwaltung.instance().getModel().getAllSpieler().iterator();
             iter.hasNext();) {
            final ISpieler element = (ISpieler) iter.next();

            if (element.getVerletzt() > 0) {
                players.addItem(new PlayerItem(element));
            }
        }

        if (players.getItemCount() == 1) {
            players.setEnabled(false);
        }

        players.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    final PlayerItem selected = (PlayerItem) players.getSelectedItem();

                    if (selected == null) {
                        return;
                    }

                    final ISpieler spieler = HOVerwaltung.instance().getModel().getSpieler(selected
                                                                                           .getId());

                    if (spieler == null) {
                        return;
                    }

                    age.setText("" + spieler.getAlter());
                    injury.setText("" + spieler.getVerletzt());

                    String tsi = "";

                    try {
                        ResultSet rs = HOMiniModel.instance().getAdapter().executeQuery("select marktwert, hrf_id from SPIELER where spielerid="
                                                                                        + spieler
                                                                                          .getSpielerID()
                                                                                        + " and verletzt=-1 order by hrf_id desc");

                        if (rs.next()) {
                            tsi = rs.getString("marktwert");
                        }
                    } catch (Exception e1) {
                    }

                    tsiPre.setText(tsi);

                    try {
                        ResultSet rs = HOMiniModel.instance().getAdapter().executeQuery("select marktwert from SPIELER where spielerid="
                                                                                        + spieler
                                                                                          .getSpielerID()
                                                                                        + " and verletzt>-1 order by hrf_id desc");

                        if (rs.next()) {
                            tsi = rs.getString("marktwert");
                        }
                    } catch (Exception e1) {
                    }

                    tsiPost.setText(tsi);
                }
            });

        injuryType.setSelectedIndex(0);
        players.setSelectedIndex(0);
        age.setText("");
        tsiPost.setText("");
        tsiPre.setText("");
        injury.setText("");
    }

    /**
     * Create a JLabel Component for rendering
     *
     * @param text The message to show
     *
     * @return the GUI Component
     */
    private Component createLabel(String text) {
        return new JLabel(text, JLabel.CENTER);
    }

    /**
     * Create a JPanel Component for rendering
     *
     * @param field The component to show
     *
     * @return the GUI Component
     */
    private Component createPanel(Component field) {
        final JPanel p = GUIPluginWrapper.instance().createImagePanel();
        p.add(field);
        return p;
    }

    /**
     * Initialize the GUI components
     */
    private void init() {
        injuryType.addItem(HOVerwaltung.instance().getResource().getProperty("Healthy"));
        injuryType.addItem(HOVerwaltung.instance().getResource().getProperty("Bruised"));
        injuryType.addItem(HOVerwaltung.instance().getResource().getProperty("InjuredTraining"));

        setOpaque(false);
        setLayout(new BorderLayout());

        final JPanel config = GUIPluginWrapper.instance().createImagePanel();
        config.setOpaque(false);
        config.setLayout(new GridLayout(4, 3));
        config.add(createLabel(HOVerwaltung.instance().getResource().getProperty("Spieler")));
        config.add(createLabel(HOVerwaltung.instance().getResource().getProperty("Alter")));
        config.add(createLabel(HOVerwaltung.instance().getResource().getProperty("Verletzt")));
        config.add(createPanel(players));
        config.add(createPanel(age));
        config.add(createPanel(injury));
        config.add(createLabel(HOVerwaltung.instance().getResource().getProperty("Status")));
        config.add(createLabel(HOVerwaltung.instance().getResource().getProperty("TSIPre")));
        config.add(createLabel(HOVerwaltung.instance().getResource().getProperty("TSIPost")));
        config.add(createPanel(injuryType));
        config.add(createPanel(tsiPre));
        config.add(createPanel(tsiPost));
        add(config, BorderLayout.CENTER);
        setBorder(BorderFactory.createEtchedBorder(1));
    }
}
