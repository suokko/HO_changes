// %2898908854:de.hattrickorganizer.gui.templates%
package de.hattrickorganizer.gui.templates;

//import java.awt.Color;
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
import de.hattrickorganizer.gui.theme.ImageUtilities;
import de.hattrickorganizer.gui.theme.ThemeManager;


public class TorLabelEntry implements TableEntry {

    private static ImageIcon BALLIMAGEICON;

    private JComponent m_clComponent = new JPanel();
    private int m_iTore;


    public TorLabelEntry() {
        this(0);
    }

    public TorLabelEntry(int tore) {
        if (BALLIMAGEICON == null) {
            BALLIMAGEICON = new ImageIcon(ImageUtilities.makeColorTransparent(ThemeManager.loadImage("gui/bilder/Ball.png"),
                                                                                                 Color.RED)
                                                                           .getScaledInstance(14,
                                                                                              14,
                                                                                              Image.SCALE_SMOOTH));
        }

        setTore(tore);
        createComponent();
    }

	public final javax.swing.JComponent getComponent(boolean isSelected) {
    	m_clComponent.setBackground(isSelected?SpielerTableRenderer.SELECTION_BG:ColorLabelEntry.BG_STANDARD);
        return m_clComponent;
    }

    public final void setTore(int tore) {
        if (tore != m_iTore) {
            m_iTore = tore;
            updateComponent();
        }
    }

    public final int getTore() {
        return m_iTore;
    }


	public final void clear() {
        m_clComponent.removeAll();
    }


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

    public final void incTore() {
        setTore(getTore() + 1);
    }

	public final void updateComponent() {
        m_clComponent.removeAll();

        for (float f = m_iTore; f > 0; f--) {
            final JLabel jlabel = new JLabel(BALLIMAGEICON);
            jlabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            m_clComponent.add(jlabel);
        }
    }
}
