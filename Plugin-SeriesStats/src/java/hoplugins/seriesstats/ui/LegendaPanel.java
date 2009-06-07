// %1117898461671:hoplugins.seriesstats.ui%
/*
 * Created on 2-giu-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package hoplugins.seriesstats.ui;

import hoplugins.Commons;
import hoplugins.SeriesStats;

import hoplugins.seriesstats.Colors;
import hoplugins.seriesstats.LegendeCheckBox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JPanel;


/**
 * DOCUMENT ME!
 *
 * @author Mirtillo To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LegendaPanel extends JPanel implements ActionListener {
    //~ Instance fields ----------------------------------------------------------------------------

    private JComboBox JCBDivision;
    private JComboBox JCBTeam;
    private JComboBox JCBType;
    private Map MapDivisions;
    private Map MapTeams;
    private RefreshablePanel refreshablePanel;
    private LegendeCheckBox[] LCBGeneral;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new LegendaPanel object.
     *
     * @param tab TODO Missing Constructuor Parameter Documentation
     * @param isTeam TODO Missing Constructuor Parameter Documentation
     */
    public LegendaPanel(RefreshablePanel tab, boolean isTeam) {
        this.refreshablePanel = tab;

        LCBGeneral = new LegendeCheckBox[10];
        LCBGeneral[0] = new hoplugins.seriesstats.LegendeCheckBox(Commons.getModel().getResource()
                                                                         .getProperty("Hilflinien"));
        LCBGeneral[0].aAL(this);
        LCBGeneral[1] = new hoplugins.seriesstats.LegendeCheckBox(Commons.getModel().getResource()
                                                                         .getProperty("Beschriftung"));
        LCBGeneral[1].aAL(this);
        LCBGeneral[2] = new hoplugins.seriesstats.LegendeCheckBox(Commons.getModel().getResource()
                                                                         .getProperty("Bewertung"),
                                                                  Colors.Black);
        LCBGeneral[2].aAL(this);
        LCBGeneral[3] = new hoplugins.seriesstats.LegendeCheckBox(Commons.getModel().getResource()
                                                                         .getProperty("MatchMittelfeld"),
                                                                  Colors.Yellow);
        LCBGeneral[3].aAL(this);
        LCBGeneral[4] = new hoplugins.seriesstats.LegendeCheckBox(Commons.getModel().getResource()
                                                                         .getProperty("rechteAbwehrseite"),
                                                                  Colors.Green.darker());
        LCBGeneral[4].aAL(this);
        LCBGeneral[5] = new hoplugins.seriesstats.LegendeCheckBox(Commons.getModel().getResource()
                                                                         .getProperty("Abwehrzentrum"),
                                                                  Colors.Blue);
        LCBGeneral[5].aAL(this);
        LCBGeneral[6] = new hoplugins.seriesstats.LegendeCheckBox(Commons.getModel().getResource()
                                                                         .getProperty("linkeAbwehrseite"),
                                                                  Colors.Green.brighter());
        LCBGeneral[6].aAL(this);
        LCBGeneral[7] = new hoplugins.seriesstats.LegendeCheckBox(Commons.getModel().getResource()
                                                                         .getProperty("rechteAngriffsseite"),
                                                                  Colors.Orange.darker());
        LCBGeneral[7].aAL(this);
        LCBGeneral[8] = new hoplugins.seriesstats.LegendeCheckBox(Commons.getModel().getResource()
                                                                         .getProperty("Angriffszentrum"),
                                                                  Colors.Red);
        LCBGeneral[8].aAL(this);
        LCBGeneral[9] = new hoplugins.seriesstats.LegendeCheckBox(Commons.getModel().getResource()
                                                                         .getProperty("linkeAngriffsseite"),
                                                                  Colors.Orange.brighter());
        LCBGeneral[9].aAL(this);

        JPanel d1 = Commons.getModel().getGUI().createImagePanel();

        // d1.setBackground(Color.green);
        GridBagLayout gridbaglayout1 = new GridBagLayout();
        GridBagConstraints gridbagconstraints1 = new GridBagConstraints();
        gridbagconstraints1.fill = GridBagConstraints.HORIZONTAL;
        gridbagconstraints1.weightx = 0.5;
        gridbagconstraints1.weighty = 0.1;
        gridbagconstraints1.anchor = GridBagConstraints.PAGE_START;
        gridbagconstraints1.insets = new Insets(2, 2, 2, 2);
        gridbagconstraints1.gridx = 0;
        gridbagconstraints1.gridy = 0;
        gridbagconstraints1.gridwidth = 2;
        d1.setLayout(gridbaglayout1);

        gridbagconstraints1.gridx = 0;
        gridbagconstraints1.gridy = 2;
        gridbagconstraints1.gridwidth = 2;
        JCBDivision = new JComboBox();
        JCBDivision.addActionListener(this);
        gridbaglayout1.setConstraints(JCBDivision, gridbagconstraints1);
        d1.add(JCBDivision);

        gridbagconstraints1.gridx = 0;
        gridbagconstraints1.gridy = 3;
        gridbagconstraints1.gridwidth = 2;
        JCBType = new JComboBox();
        JCBType.addActionListener(this);
        JCBTeam = new JComboBox();
        JCBTeam.addActionListener(this);

        if (isTeam) {
            gridbaglayout1.setConstraints(JCBTeam, gridbagconstraints1);
            d1.add(JCBTeam);
        } else {
            gridbaglayout1.setConstraints(JCBType, gridbagconstraints1);
            d1.add(JCBType);
        }

        gridbagconstraints1.gridwidth = 2;
        gridbagconstraints1.gridx = 0;
        gridbagconstraints1.gridy = 5;
        LCBGeneral[0].setOpaque(false);
        LCBGeneral[0].setBackground(Color.white);
        gridbaglayout1.setConstraints(LCBGeneral[0], gridbagconstraints1);
        d1.add(LCBGeneral[0]);

        gridbagconstraints1.gridwidth = 2;
        gridbagconstraints1.gridx = 0;
        gridbagconstraints1.gridy = 6;
        LCBGeneral[1].setOpaque(false);
        LCBGeneral[1].setBackground(Color.white);
        gridbaglayout1.setConstraints(LCBGeneral[1], gridbagconstraints1);
        d1.add(LCBGeneral[1]);

        gridbagconstraints1.gridwidth = 2;
        gridbagconstraints1.gridx = 0;
        gridbagconstraints1.gridy = 7;
        LCBGeneral[2].setOpaque(false);
        gridbaglayout1.setConstraints(LCBGeneral[2], gridbagconstraints1);
        d1.add(LCBGeneral[2]);

        gridbagconstraints1.gridwidth = 2;
        gridbagconstraints1.gridx = 0;
        gridbagconstraints1.gridy = 8;
        LCBGeneral[3].setOpaque(false);
        gridbaglayout1.setConstraints(LCBGeneral[3], gridbagconstraints1);
        d1.add(LCBGeneral[3]);

        gridbagconstraints1.gridwidth = 2;
        gridbagconstraints1.gridx = 0;
        gridbagconstraints1.gridy = 9;
        LCBGeneral[4].setOpaque(false);
        gridbaglayout1.setConstraints(LCBGeneral[4], gridbagconstraints1);
        d1.add(LCBGeneral[4]);

        gridbagconstraints1.gridwidth = 2;
        gridbagconstraints1.gridx = 0;
        gridbagconstraints1.gridy = 10;
        LCBGeneral[5].setOpaque(false);
        gridbaglayout1.setConstraints(LCBGeneral[5], gridbagconstraints1);
        d1.add(LCBGeneral[5]);

        gridbagconstraints1.gridwidth = 2;
        gridbagconstraints1.gridx = 0;
        gridbagconstraints1.gridy = 11;
        LCBGeneral[6].setOpaque(false);
        gridbaglayout1.setConstraints(LCBGeneral[6], gridbagconstraints1);
        d1.add(LCBGeneral[6]);

        gridbagconstraints1.gridwidth = 2;
        gridbagconstraints1.gridx = 0;
        gridbagconstraints1.gridy = 12;
        LCBGeneral[7].setOpaque(false);
        gridbaglayout1.setConstraints(LCBGeneral[7], gridbagconstraints1);
        d1.add(LCBGeneral[7]);

        gridbagconstraints1.gridwidth = 2;
        gridbagconstraints1.gridx = 0;
        gridbagconstraints1.gridy = 13;
        LCBGeneral[8].setOpaque(false);
        gridbaglayout1.setConstraints(LCBGeneral[8], gridbagconstraints1);
        d1.add(LCBGeneral[8]);

        gridbagconstraints1.gridwidth = 2;
        gridbagconstraints1.gridx = 0;
        gridbagconstraints1.gridy = 14;
        LCBGeneral[9].setOpaque(false);
        gridbaglayout1.setConstraints(LCBGeneral[9], gridbagconstraints1);
        d1.add(LCBGeneral[9]);
        setLayout(new BorderLayout());
        add(d1, BorderLayout.CENTER);

        RefreshDivisionBox();
        RefreshTeamBox();
        RefreshTypeBox();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param event TODO Missing Method Parameter Documentation
     */
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(JCBDivision)) {
            RefreshTeamBox();
            RefreshTypeBox();
        }

        refreshablePanel.refresh();
    }

    /**
     * TODO Missing Method Documentation
     */
    private void RefreshDivisionBox() {
        try {
            MapDivisions = new HashMap();
            JCBDivision.removeActionListener(this);

            // Clear ComboBox
            JCBDivision.removeAllItems();

            // Get Spielplaene
            plugins.ISpielplan[] divisions = Commons.getModel().getSpielplaene();

            // add items
            for (int i = 0; i < divisions.length; i++) {
                Vector LigaSeas = new Vector();

                // position 0
                LigaSeas.add(new Integer(divisions[i].getLigaId()));

                // position 1
                LigaSeas.add(new Integer(divisions[i].getSaison()));

                String LigaString = prepareLigaString(divisions[i].getSaison(),
                                                      divisions[i].getLigaName(),
                                                      divisions[i].getLigaId());
                this.JCBDivision.addItem(LigaString);

                this.MapDivisions.put(new String(LigaString), LigaSeas);
            }

            JCBDivision.setSelectedItem(prepareLigaString(Commons.getModel().getBasics().getSeason(),
                                                          Commons.getModel().getLiga().getLiga(),
                                                          Commons.getModel().getBasics().getLiga()));

            JCBDivision.addActionListener(this);
        } catch (Exception e) {
            if (true) {
                SeriesStats.getIDB().append("---ooo---");
                SeriesStats.getIDB().append(e);
            }
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    private void RefreshTeamBox() {
        try {
            MapTeams = new HashMap();
            this.JCBTeam.removeActionListener(this);

            // Clear ComboBox
            this.JCBTeam.removeAllItems();

            // Get season and division
            Vector LigaSeas = (Vector) this.MapDivisions.get((String) this.JCBDivision
                                                             .getSelectedItem());

            // Get teams and team ids
            plugins.ITabellenVerlaufEintrag[] dummy = Commons.getModel()
                                                             .getSpielplan(((Integer) LigaSeas.get(0))
                                                                           .intValue(),
                                                                           ((Integer) LigaSeas.get(1))
                                                                           .intValue()).getVerlauf()
                                                             .getEintraege();

            // sort data
            Vector data = new Vector();

            for (int i = 0; i < dummy.length; i++) {
                this.MapTeams.put(new String(dummy[i].getTeamName()),
                                  new Integer(dummy[i].getTeamId()));
                data.add(new String(dummy[i].getTeamName()));
            }

            Collections.sort(data);

            // add Items
            for (Enumeration e = data.elements(); e.hasMoreElements();) {
                String TeamName = (String) e.nextElement();
                this.JCBTeam.addItem(TeamName);
            }

            this.JCBTeam.addActionListener(this);
        } catch (Exception e) {
            if (true) {
                SeriesStats.getIDB().append("---ooo---");
                SeriesStats.getIDB().append(e);
            }
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    private void RefreshTypeBox() {
        try {
            this.JCBType.removeActionListener(this);

            // Clear ComboBox
            this.JCBType.removeAllItems();
            this.JCBType.addItem("Average");

            this.JCBType.addActionListener(this);
        } catch (Exception e) {
            if (true) {
                SeriesStats.getIDB().append("---ooo---");
                SeriesStats.getIDB().append(e);
            }
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param s TODO Missing Method Parameter Documentation
     * @param l TODO Missing Method Parameter Documentation
     * @param lid TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private String prepareLigaString(int s, String l, int lid) {
        return Commons.getModel().getResource().getProperty("Season") + " " + s + " "
               + Commons.getModel().getResource().getProperty("Liga") + " " + l + " (" + lid + ")";
    }
}
