// %3615011109:de.hattrickorganizer.gui.playeroverview%
package ho.module.playerOverview;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import de.hattrickorganizer.model.Spieler;


/**
 * Transferabel für DND
 */
public class SpielerTransferable implements Transferable {
    //~ Instance fields ----------------------------------------------------------------------------

    private DataFlavor m_clSpielerFlavor;
    private Spieler m_clSpieler;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SpielerTransferable object.
     *
     * @param spieler TODO Missing Constructuor Parameter Documentation
     */
    public SpielerTransferable(Spieler spieler) {
        m_clSpielerFlavor = new DataFlavor(spieler.getClass(), "Spieler");
        m_clSpieler = spieler;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param dataflavor TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isDataFlavorSupported(DataFlavor dataflavor) {
        return dataflavor.equals(m_clSpielerFlavor);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param dataflavor TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     *
     * @throws UnsupportedFlavorException TODO Missing Method Exception Documentation
     * @throws java.io.IOException TODO Missing Method Exception Documentation
     */
    public final Object getTransferData(DataFlavor dataflavor)
      throws UnsupportedFlavorException, java.io.IOException
    {
        if (isDataFlavorSupported(dataflavor)) {
            return m_clSpieler;
        } else {
            throw new UnsupportedFlavorException(dataflavor);
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final DataFlavor[] getTransferDataFlavors() {
        final DataFlavor[] flavor = {m_clSpielerFlavor};
        return flavor;
    }
}
