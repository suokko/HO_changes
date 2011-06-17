// %2898908854:de.hattrickorganizer.gui.templates%
package de.hattrickorganizer.gui.templates;

import java.awt.Color;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import plugins.IHOTableEntry;
import de.hattrickorganizer.gui.model.SpielerTableRenderer;
import de.hattrickorganizer.tools.Helper;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class TorLabelEntry extends TableEntry {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static ImageIcon BALLIMAGEICON;

    //~ Instance fields ----------------------------------------------------------------------------

    private JComponent m_clComponent = new JPanel();
    private int m_iTore;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TorLabelEntry object.
     */
    public TorLabelEntry() {
        if (BALLIMAGEICON == null) {
            BALLIMAGEICON = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/credits/Ball.png"),
                                                                                                 new Color(255,
                                                                                                           0,
                                                                                                           0))
                                                                           .getScaledInstance(14,
                                                                                              14,
                                                                                              Image.SCALE_SMOOTH));
        }

        m_iTore = 0;
        createComponent();
    }

    /**
     * Creates a new TorLabelEntry object.
     *
     * @param tore TODO Missing Constructuor Parameter Documentation
     */
    public TorLabelEntry(int tore) {
        if (BALLIMAGEICON == null) {
            BALLIMAGEICON = new ImageIcon(Helper.makeColorTransparent(Helper.loadImage("gui/bilder/credits/Ball.png"),
                                                                                                 new Color(255,
                                                                                                           0,
                                                                                                           0))
                                                                           .getScaledInstance(14,
                                                                                              14,
                                                                                              Image.SCALE_SMOOTH));
        }

        setTore(tore);
        createComponent();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param isSelected TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public final javax.swing.JComponent getComponent(boolean isSelected) {
        if (isSelected) {
            m_clComponent.setOpaque(true);
            m_clComponent.setBackground(SpielerTableRenderer.SELECTION_BG);
        } else {
            m_clComponent.setOpaque(true);
            m_clComponent.setBackground(ColorLabelEntry.BG_STANDARD);
        }

        return m_clComponent;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param tore TODO Missing Method Parameter Documentation
     */
    public final void setTore(int tore) {
        if (tore != m_iTore) {
            m_iTore = tore;
            updateComponent();
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getTore() {
        return m_iTore;
    }

    /**
     * TODO Missing Method Documentation
     */
    @Override
	public final void clear() {
        m_clComponent.removeAll();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param obj TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public final int compareTo(IHOTableEntry obj) {
        if (obj instanceof TorLabelEntry) {
            final TorLabelEntry entry = (TorLabelEntry) obj;

            if (getTore() < entry.getTore()) {
                return -1;
            } else if (getTore() > entry.getTore()) {
                return 1;
            } else {
                return 0;
            }
        }

        return 0;
    }

    /**
     * TODO Missing Method Documentation
     */
    @Override
	public final void createComponent() {
        JPanel renderer = new JPanel();
        renderer.setLayout(new BoxLayout(renderer, 0));
        renderer.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        for (float f = m_iTore; f > 0; f--) {
            final JLabel jlabel = new JLabel(BALLIMAGEICON);
            jlabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            renderer.add(jlabel);
        }

        m_clComponent = renderer;
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void incTore() {
        setTore(getTore() + 1);
    }

    /**
     * TODO Missing Method Documentation
     */
    @Override
	public final void updateComponent() {
        m_clComponent.removeAll();

        for (float f = m_iTore; f > 0; f--) {
            final JLabel jlabel = new JLabel(BALLIMAGEICON);
            jlabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            m_clComponent.add(jlabel);
        }
    }
}
