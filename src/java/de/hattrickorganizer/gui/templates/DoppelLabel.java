// %1614414392:de.hattrickorganizer.gui.templates%
package de.hattrickorganizer.gui.templates;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class DoppelLabel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    private JLabel m_jlLinks;
    private JLabel m_jlRechts;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new DoppelLabel object.
     *
     * @param color TODO Missing Constructuor Parameter Documentation
     */
    public DoppelLabel(java.awt.Color color) {
        this();
        m_jlLinks = new JLabel();
        m_jlLinks.setBackground(color);
        m_jlRechts = new JLabel();
        m_jlRechts.setBackground(color);
    }

    /**
     * Creates a new DoppelLabel object.
     *
     * @param links TODO Missing Constructuor Parameter Documentation
     * @param rechts TODO Missing Constructuor Parameter Documentation
     */
    public DoppelLabel(JLabel links, JLabel rechts) {
        this();
        m_jlLinks = links;
        m_jlRechts = rechts;
    }

    /**
     * Creates a new DoppelLabel object.
     */
    public DoppelLabel() {
        setLayout(new GridLayout(1, 2));
        setOpaque(true);
        setBackground(de.hattrickorganizer.gui.model.SpielerTableRenderer.SELECTION_BG);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param links TODO Missing Method Parameter Documentation
     * @param rechts TODO Missing Method Parameter Documentation
     */
    public final void setLabels(JLabel links, JLabel rechts) {
        this.removeAll();

        m_jlLinks = links;
        m_jlRechts = rechts;
        add(links);
        add(rechts);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JLabel getLinks() {
        return m_jlLinks;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JLabel getRechts() {
        return m_jlRechts;
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void clear() {
        m_jlLinks.setText("");
        m_jlLinks.setIcon(null);
        m_jlRechts.setText("");
        m_jlRechts.setIcon(null);
    }
}
