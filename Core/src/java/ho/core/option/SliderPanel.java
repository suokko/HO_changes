// %1839835436:de.hattrickorganizer.gui.menu.option%
package ho.core.option;


import ho.core.gui.comp.panel.ImagePanel;
import ho.core.util.HOLogger;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;


/**
 * Panel mit Slider und Textfield
 */
public final class SliderPanel extends ImagePanel implements ChangeListener {
    //~ Static / Instance fields ----------------------------------------------------------------------------

	private static final long serialVersionUID = 1L;
	private JLabel m_jlLabel;
    private JSlider m_jslSlider;
    private JTextField m_jtfTextfield;
    private float m_fFaktor = 1;
    private float m_fTextfeldFaktor = 1;
    private int m_iTextbreite = 80;
    private int decimals = 1;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param text Text des Labels
     * @param max Maximaler Wert
     * @param min Minimaler Wert
     * @param faktor Faktor, mit dem Werte eingangs multipliziert und durch die sie ausgangs wieder
     *        dividiert werden
     * @param textfeldfaktor Faktor für die Anzeige des Sliderwerts in Textfeld
     * @param textbreite Breite, die für das Label vorgesehen ist.
     */
    public SliderPanel(String text, int max, int min, float faktor, float textfeldfaktor,
                       int textbreite) {
        m_fFaktor = faktor;
        m_fTextfeldFaktor = textfeldfaktor;
        m_iTextbreite = textbreite;
        initComponents(text, max, min);
    }

	/**
	 * DOCUMENT ME!
	 *
	 * @param text Text des Labels
	 * @param max Maximaler Wert
	 * @param min Minimaler Wert
	 * @param faktor Faktor, mit dem Werte eingangs multipliziert und durch die sie ausgangs wieder
	 *        dividiert werden
	 * @param textfeldfaktor Faktor für die Anzeige des Sliderwerts in Textfeld
	 * @param textbreite Breite, die für das Label vorgesehen ist.
	 */
	public SliderPanel(String text, int max, int min, float faktor, float textfeldfaktor,
					   int textbreite,int decimal) {
		this(text,max,min,faktor,textfeldfaktor,textbreite);
		this.decimals = decimal;					   	
	}

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * werte direkt übernehmen public void setValue( int value ) { m_jslSlider.setValue ( value );
     * }
     *
     * @param value TODO Missing Constructuor Parameter Documentation
     */
    /**
     * Wert mit faktor multiplizieren und auf int casten
     *
     * @param value TODO Missing Constructuor Parameter Documentation
     */
    public final void setValue(float value) {
        m_jslSlider.setValue((int) (value * m_fFaktor));
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final float getValue() {
        HOLogger.instance().log(getClass(),(float) m_jslSlider.getValue() + " : "
                           + (m_jslSlider.getValue() / m_fFaktor));
        return m_jslSlider.getValue() / m_fFaktor;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param listener TODO Missing Method Parameter Documentation
     */
    public final void addChangeListener(ChangeListener listener) {
        m_jslSlider.addChangeListener(listener);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param listener TODO Missing Method Parameter Documentation
     */
    public final void removeChangeListener(ChangeListener listener) {
        m_jslSlider.removeChangeListener(listener);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param changeEvent TODO Missing Method Parameter Documentation
     */
    public final void stateChanged(javax.swing.event.ChangeEvent changeEvent) {
        m_jtfTextfield.setText(ho.core.util.Helper.round(m_jslSlider.getValue() * m_fTextfeldFaktor,decimals)
                               + "");
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param text TODO Missing Method Parameter Documentation
     * @param max TODO Missing Method Parameter Documentation
     * @param min TODO Missing Method Parameter Documentation
     */
    private void initComponents(String text, int max, int min) {
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(4, 4, 4, 4);

        setLayout(layout);

        m_jlLabel = new JLabel(text, SwingConstants.LEFT);
        m_jlLabel.setMaximumSize(new Dimension(m_iTextbreite, 35));
        m_jlLabel.setPreferredSize(m_jlLabel.getMaximumSize());
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jlLabel, constraints);
        add(m_jlLabel);

        m_jslSlider = new JSlider(min, max);
        m_jslSlider.setMaximumSize(new Dimension(150, 35));
        m_jslSlider.setPreferredSize(m_jslSlider.getMaximumSize());
        m_jslSlider.setOpaque(false);
        m_jslSlider.setValue(0);
        m_jslSlider.addChangeListener(this);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.weightx = 1.0;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jslSlider, constraints);
        add(m_jslSlider);

        m_jtfTextfield = new JTextField(ho.core.util.Helper.round(m_jslSlider
                                                                                .getValue() * m_fTextfeldFaktor,decimals)
                                        + "", 4);
        m_jtfTextfield.setEditable(false);
        m_jtfTextfield.setHorizontalAlignment(SwingConstants.RIGHT);
        constraints.anchor = GridBagConstraints.EAST;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0.0;
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        layout.setConstraints(m_jtfTextfield, constraints);
        add(m_jtfTextfield);
    }
}
