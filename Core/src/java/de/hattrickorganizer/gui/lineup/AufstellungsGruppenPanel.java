// %3622084902:de.hattrickorganizer.gui.lineup%
package de.hattrickorganizer.gui.lineup;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import plugins.ISpieler;

import de.hattrickorganizer.model.Aufstellung;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.Helper;


/**
 * Hier lassen sich mit einem Klick alle aufgestellten Spieler einer Gruppe zuordnen
 */
final class AufstellungsGruppenPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 955755336335567688L;
	
    //~ Instance fields ----------------------------------------------------------------------------

	private JButton aGruppe = new JButton(new ImageIcon(Helper.loadImage("gui/bilder/smilies/A-Team.png")));
    private JButton bGruppe = new JButton(new ImageIcon(Helper.loadImage("gui/bilder/smilies/B-Team.png")));
    private JButton cGruppe = new JButton(new ImageIcon(Helper.loadImage("gui/bilder/smilies/C-Team.png")));
    private JButton dGruppe = new JButton(new ImageIcon(Helper.loadImage("gui/bilder/smilies/D-Team.png")));
    private JButton eGruppe = new JButton(new ImageIcon(Helper.loadImage("gui/bilder/smilies/E-Team.png")));

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new AufstellungsGruppenPanel object.
     */
    public AufstellungsGruppenPanel() {
        this.setOpaque(false);

        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(aGruppe)) {
            gruppenMarkierung("A-Team.png");
        } else if (e.getSource().equals(bGruppe)) {
            gruppenMarkierung("B-Team.png");
        } else if (e.getSource().equals(cGruppe)) {
            gruppenMarkierung("C-Team.png");
        } else if (e.getSource().equals(dGruppe)) {
            gruppenMarkierung("D-Team.png");
        } else if (e.getSource().equals(eGruppe)) {
            gruppenMarkierung("E-Team.png");
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param gruppenName TODO Missing Method Parameter Documentation
     */
    private void gruppenMarkierung(String gruppenName) {
        final Vector<ISpieler> alleSpieler = HOVerwaltung.instance().getModel().getAllSpieler();
        final Aufstellung aufstellung = HOVerwaltung.instance()
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

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        setPreferredSize(new Dimension(20, 200));

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
    }
}
