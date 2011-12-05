// %2129436708:de.hattrickorganizer.gui.menu.option%
package de.hattrickorganizer.gui.menu.option;

import de.hattrickorganizer.gui.templates.ImagePanel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


/**
 * Panel mit Slider und Textfield
 */
class ComboBoxPanel extends ImagePanel {
    //~ Instance fields ----------------------------------------------------------------------------

	private static final long serialVersionUID = 1L;
	private JComboBox m_jcbComboBox;
    private JLabel m_jlLabel;
    private int m_iTextbreite = 80;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param text Text des Labels
     * @param items Einträge in der ComboBox
     * @param textbreite Breite, die für das Label vorgesehen ist.
     */
    protected ComboBoxPanel(String text, Object[] items, int textbreite) {
        m_iTextbreite = textbreite;
        initComponents(text, items);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param id TODO Missing Method Parameter Documentation
     */
    public final void setSelectedId(int id) {
        de.hattrickorganizer.tools.Helper.markierenComboBox(m_jcbComboBox, id);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param obj TODO Missing Method Parameter Documentation
     */
    public final void setSelectedItem(Object obj) {
        m_jcbComboBox.setSelectedItem(obj);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final Object getSelectedItem() {
        return m_jcbComboBox.getSelectedItem();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param listener TODO Missing Method Parameter Documentation
     */
    public final void addItemListener(ItemListener listener) {
        m_jcbComboBox.addItemListener(listener);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param listener TODO Missing Method Parameter Documentation
     */
    public final void removeChangeListener(ItemListener listener) {
        m_jcbComboBox.removeItemListener(listener);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param text TODO Missing Method Parameter Documentation
     * @param items TODO Missing Method Parameter Documentation
     */
    private void initComponents(String text, Object[] items) {
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(4, 4, 4, 4);

        setLayout(layout);

        m_jlLabel = new JLabel(text, SwingConstants.LEFT);
        m_jlLabel.setPreferredSize(new Dimension(m_iTextbreite, 35));
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jlLabel, constraints);
        add(m_jlLabel);

        m_jcbComboBox = new JComboBox(items);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.weightx = 1.0;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jcbComboBox, constraints);
        add(m_jcbComboBox);
    }
}
