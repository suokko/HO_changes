// %996996046:de.hattrickorganizer.gui.statistic%
package de.hattrickorganizer.gui.statistic;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Checkbox mit einem Bild
 */
public class ImageCheckbox extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    private JCheckBox m_jchCheckbox = new JCheckBox();
    private JLabel m_jlLabel = new JLabel();

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new ImageCheckbox object.
     */
    public ImageCheckbox() {
        this("", null, false);
    }

    /**
     * Creates a new ImageCheckbox object.
     *
     * @param text TODO Missing Constructuor Parameter Documentation
     * @param icon TODO Missing Constructuor Parameter Documentation
     * @param selected TODO Missing Constructuor Parameter Documentation
     */
    public ImageCheckbox(String text, ImageIcon icon, boolean selected) {
        this(text, icon, selected, JLabel.RIGHT);
    }

    /**
     * Creates a new ImageCheckbox object.
     *
     * @param text TODO Missing Constructuor Parameter Documentation
     * @param icon TODO Missing Constructuor Parameter Documentation
     * @param selected TODO Missing Constructuor Parameter Documentation
     * @param alignment TODO Missing Constructuor Parameter Documentation
     */
    public ImageCheckbox(String text, ImageIcon icon, boolean selected, int alignment) {
        final GridBagLayout layout2 = new GridBagLayout();
        final GridBagConstraints constraints2 = new GridBagConstraints();
        constraints2.fill = GridBagConstraints.HORIZONTAL;
        constraints2.weightx = 0.0;
        constraints2.weighty = 0.0;
        constraints2.insets = new Insets(0, 0, 0, 0);

        setLayout(layout2);

        constraints2.gridx = 0;
        constraints2.gridy = 0;
        constraints2.weightx = 0.0;
        m_jchCheckbox.setSelected(selected);
        m_jchCheckbox.setOpaque(false);
        layout2.setConstraints(m_jchCheckbox, constraints2);
        add(m_jchCheckbox);

        constraints2.gridx = 1;
        constraints2.gridy = 0;
        constraints2.weightx = 1.0;
        m_jlLabel.setHorizontalTextPosition(alignment);
        m_jlLabel.setText(text);
        m_jlLabel.setIcon(icon);
        m_jlLabel.setOpaque(false);
        layout2.setConstraints(m_jlLabel, constraints2);
        add(m_jlLabel);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JCheckBox getCheckbox() {
        return m_jchCheckbox;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param icon TODO Missing Method Parameter Documentation
     */
    public final void setIcon(ImageIcon icon) {
        m_jlLabel.setIcon(icon);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JLabel getLabel() {
        return m_jlLabel;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param selected TODO Missing Method Parameter Documentation
     */
    public final void setSelected(boolean selected) {
        m_jchCheckbox.setSelected(selected);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isSelected() {
        return m_jchCheckbox.isSelected();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param text TODO Missing Method Parameter Documentation
     */
    public final void setText(String text) {
        m_jlLabel.setText(text);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param listener TODO Missing Method Parameter Documentation
     */
    public final void addActionListener(ActionListener listener) {
        m_jchCheckbox.addActionListener(listener);
    }
}
