// %3644680912:de.hattrickorganizer.gui.arenasizer%
package ho.modul.arenasizer;


import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JScrollPane;
import javax.swing.JViewport;

import de.hattrickorganizer.gui.templates.ImagePanel;


/**
 * Zeigt die Arena
 */
public final class ArenaSizerPanel extends ImagePanel {
	
	private static final long serialVersionUID = -675326023014634404L;
	
    //~ Constructors -------------------------------------------------------------------------------
	

	/**
     * Creates a new ArenaSizerPanel object.
     */
    public ArenaSizerPanel() {
        initComponents();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param typ TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Component initArena(byte typ) {
        final JScrollPane scrollpane = new JScrollPane(new ArenaPanel(typ));
        scrollpane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
        return scrollpane;
    }

    //----------init-----------------------------------------------
    private void initComponents() {
        setLayout(new GridLayout(2, 1, 4, 4));

        add(initArena(ArenaPanel.HRFARENA));
        add(initArena(ArenaPanel.TESTARENA));
    }
}
