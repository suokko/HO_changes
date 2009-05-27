// %3109779468:de.hattrickorganizer.gui.lineup%
package de.hattrickorganizer.gui.lineup;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import de.hattrickorganizer.gui.model.SpielerCBItem;


/**
 * Zeigt die besten Elfmeterschützen für die aktuelle Aufstellung an
 */
public class ElfmeterSchuetzenDialog extends JDialog {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new ElfmeterSchuetzenDialog object.
     *
     * @param owner TODO Missing Constructuor Parameter Documentation
     */
    public ElfmeterSchuetzenDialog(JFrame owner) {
        super(owner,
              de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Elfmeterschuetzen"));
        initComponents();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLocation((owner.getLocation().x + (owner.getWidth() / 2)) - (this.getWidth() / 2),
                    (owner.getLocation().y + (owner.getHeight() / 2)) - (this.getHeight() / 2));
        setVisible(true);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     */
    public final void initComponents() {
        setContentPane(new de.hattrickorganizer.gui.templates.ImagePanel());
        getContentPane().setLayout(new BorderLayout());

        final int[] elfmeterIDs = de.hattrickorganizer.model.HOVerwaltung.instance().getModel()
                                                                         .getAufstellung()
                                                                         .getBestElferKicker();

        final JList liste = new JList();
        liste.setCellRenderer(new de.hattrickorganizer.gui.model.SpielerCBItemRenderer());
        liste.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        final DefaultListModel listmodel = new DefaultListModel();

        for (int i = 0; i < elfmeterIDs.length; i++) {
            listmodel.addElement(createSpielerLabel(elfmeterIDs[i]));
        }

        liste.setModel(listmodel);

        getContentPane().add(liste, BorderLayout.CENTER);

        pack();

        //setSize( 150, 35 + ( elfmeterIDs.length * 15 ) );
    }

    /**
     * Erzeugt ein Label für den Spieler
     *
     * @param spielerID TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private SpielerCBItem createSpielerLabel(int spielerID) {
        final de.hattrickorganizer.model.Spieler spieler = de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                  .getModel()
                                                                                                  .getSpieler(spielerID);

        if (spieler != null) {
            return new SpielerCBItem(spieler.getName(), 0f, spieler);
        } else {
            return new SpielerCBItem("", 0f, null);
        }
    }
}
