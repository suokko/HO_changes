// %3109779468:de.hattrickorganizer.gui.lineup%
package ho.module.lineup;

import ho.core.gui.model.SpielerCBItem;
import ho.core.model.HOVerwaltung;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

/**
 * Shows the best penalty shooters from the current lineup.
 */
public class PenaltyShootersDialog extends JDialog {

	private static final long serialVersionUID = -7330144318224339032L;

	// ~ Constructors
	// -------------------------------------------------------------------------------

	/**
	 * Creates a new ElfmeterSchuetzenDialog object.
	 * 
	 * @param owner
	 *            TODO Missing Constructuor Parameter Documentation
	 */
	public PenaltyShootersDialog(JFrame owner) {
		super(owner, HOVerwaltung.instance().getLanguageString("Elfmeterschuetzen"));
		initComponents();

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setLocation((owner.getLocation().x + (owner.getWidth() / 2)) - (this.getWidth() / 2),
				(owner.getLocation().y + (owner.getHeight() / 2)) - (this.getHeight() / 2));
		setVisible(true);
	}

	// ~ Methods
	// ------------------------------------------------------------------------------------

	/**
	 * TODO Missing Method Documentation
	 */
	public final void initComponents() {
		setContentPane(new ho.core.gui.comp.panel.ImagePanel());
		getContentPane().setLayout(new BorderLayout());

		final JList liste = new JList();
		liste.setCellRenderer(new ho.core.gui.model.SpielerCBItemRenderer());
		liste.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		final DefaultListModel listmodel = new DefaultListModel();
		List<Integer> shooters = HOVerwaltung.instance().getModel().getAufstellung()
				.getBestElferKicker();

		for (Integer id : shooters) {
			listmodel.addElement(createSpielerLabel(id.intValue()));
		}

		liste.setModel(listmodel);
		getContentPane().add(liste, BorderLayout.CENTER);

		pack();

		// setSize( 150, 35 + ( elfmeterIDs.length * 15 ) );
	}

	/**
	 * Erzeugt ein Label für den Spieler
	 * 
	 * @param spielerID
	 *            TODO Missing Constructuor Parameter Documentation
	 * 
	 * @return TODO Missing Return Method Documentation
	 */
	private SpielerCBItem createSpielerLabel(int spielerID) {
		final ho.core.model.player.Spieler spieler = HOVerwaltung.instance().getModel()
				.getSpieler(spielerID);

		if (spieler != null) {
			return new SpielerCBItem(spieler.getName(), 0f, spieler);
		} else {
			return new SpielerCBItem("", 0f, null);
		}
	}
}
