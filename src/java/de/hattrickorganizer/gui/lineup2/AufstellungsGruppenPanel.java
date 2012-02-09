// %3622084902:de.hattrickorganizer.gui.lineup%
package de.hattrickorganizer.gui.lineup2;

import gui.HOIconName;
import ho.core.gui.theme.ThemeManager;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import plugins.ISpieler;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.Lineup;


/**
 * Hier lassen sich mit einem Klick alle aufgestellten Spieler einer Gruppe zuordnen
 */
final class AufstellungsGruppenPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 955755336335567688L;

    //~ Instance fields ----------------------------------------------------------------------------

	private JButton aGruppe = new JButton(ThemeManager.getIcon(HOIconName.TEAMSMILIES[1]));
    private JButton bGruppe = new JButton(ThemeManager.getIcon(HOIconName.TEAMSMILIES[2]));
    private JButton cGruppe = new JButton(ThemeManager.getIcon(HOIconName.TEAMSMILIES[3]));
    private JButton dGruppe = new JButton(ThemeManager.getIcon(HOIconName.TEAMSMILIES[4]));
    private JButton eGruppe = new JButton(ThemeManager.getIcon(HOIconName.TEAMSMILIES[5]));
    private JButton fGruppe = new JButton(ThemeManager.getIcon(HOIconName.TEAMSMILIES[6]));

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new AufstellungsGruppenPanel object.
     */
    public AufstellungsGruppenPanel() {
        this.setOpaque(false);

        initComponents();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(aGruppe)) {
            gruppenMarkierung(HOIconName.TEAMSMILIES[1]);
        } else if (e.getSource().equals(bGruppe)) {
            gruppenMarkierung(HOIconName.TEAMSMILIES[2]);
        } else if (e.getSource().equals(cGruppe)) {
            gruppenMarkierung(HOIconName.TEAMSMILIES[3]);
        } else if (e.getSource().equals(dGruppe)) {
            gruppenMarkierung(HOIconName.TEAMSMILIES[4]);
        } else if (e.getSource().equals(eGruppe)) {
            gruppenMarkierung(HOIconName.TEAMSMILIES[5]);
        } else if (e.getSource().equals(fGruppe)) {
            gruppenMarkierung(HOIconName.TEAMSMILIES[6]);
        }
    }

    private void gruppenMarkierung(String gruppenName) {
        final Vector<ISpieler> alleSpieler = HOVerwaltung.instance().getModel().getAllSpieler();
        final Lineup aufstellung = HOVerwaltung.instance()
                                                                                                          .getModel()
                                                                                                          .getAufstellung();

        //Alle Spieler auf der Gruppe entfernen und die neuen reinsetzen
        for (int i = 0; i < alleSpieler.size(); i++) {
            final ISpieler spieler = alleSpieler.get(i);

            //ein erste 11
            if (aufstellung.isSpielerInAnfangsElf(spieler.getSpielerID())) {
                spieler.setTeamInfoSmilie(gruppenName);
            }
            //nicht erste 11 und trotzdem in der gleichen Gruppe
            else if (spieler.getTeamInfoSmilie().equals(gruppenName)) {
                //Gruppe entfernen
                spieler.setTeamInfoSmilie("");
            }
        }

        de.hattrickorganizer.gui.HOMainFrame.instance().getAufstellungsPanel().update();

        //gui.RefreshManager.instance ().doRefresh ();
    }

    private void initComponents() {
    	
    	// Horizontal by Blaghaid - away with size specification
    	
        //setPreferredSize(new Dimension(20, 200));

        //Platzhalter
        add(new JLabel("   "));
        aGruppe.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_AufstellungsGruppe_Zuordnung"));
        aGruppe.setPreferredSize(new Dimension(18, 18));
        aGruppe.addActionListener(this);
        add(aGruppe);
        bGruppe.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_AufstellungsGruppe_Zuordnung"));
        bGruppe.setPreferredSize(new Dimension(18, 18));
        bGruppe.addActionListener(this);
        add(bGruppe);
        cGruppe.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_AufstellungsGruppe_Zuordnung"));
        cGruppe.setPreferredSize(new Dimension(18, 18));
        cGruppe.addActionListener(this);
        add(cGruppe);
        dGruppe.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_AufstellungsGruppe_Zuordnung"));
        dGruppe.setPreferredSize(new Dimension(18, 18));
        dGruppe.addActionListener(this);
        add(dGruppe);
        eGruppe.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_AufstellungsGruppe_Zuordnung"));
        eGruppe.setPreferredSize(new Dimension(18, 18));
        eGruppe.addActionListener(this);
        add(eGruppe);
        fGruppe.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_AufstellungsGruppe_Zuordnung"));
        fGruppe.setPreferredSize(new Dimension(18, 18));
        fGruppe.addActionListener(this);
        add(fGruppe);
    }
}
