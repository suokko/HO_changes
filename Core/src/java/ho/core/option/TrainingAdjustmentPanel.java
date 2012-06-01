// %1839835436:de.hattrickorganizer.gui.menu.option%
package ho.core.option;

import ho.core.gui.comp.panel.ImagePanel;
import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.ThemeManager;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JTextField;



/**
 * Panel for training adjustments
 */
public final class TrainingAdjustmentPanel extends ImagePanel implements ActionListener {
    //~ Static / Instance fields ----------------------------------------------------------------------------

	private static final long serialVersionUID = 1L;
	private JLabel m_jlLabel;
    private JTextField m_jtfTextfield;
    private JLabel m_jlBaseValue;
    private JLabel m_jlTotal;
    private float offset;
    private float base;
    private NumberFormat nf = NumberFormat.getInstance(Locale.US);
    private TrainingsOptionenPanel top;
    
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Constructor
     *
     * @param text The text for the row
     * @param base The base value for the item
     * @param offset The initial offset value for the item
     */
    public TrainingAdjustmentPanel(String text, float base, float offset, TrainingsOptionenPanel top) {
        this.base = base;
        this.offset = offset;
        this.top = top;
        initComponents(text);
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Sets the value in the textfield of the class.
     *
     * @param value The value to dispaly
     */
    public final void setValue(float value) {
        offset = value;
    	m_jtfTextfield.setText(nf.format(value) + "" );
    }

    /**
     * Returns the value of the textfield of the class
     *
     * @return The float value of the text field
     */
    public final float getValue() {
        return offset;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param listener TODO Missing Method Parameter Documentation
     */
    public final void addActionListener(ActionListener listener) {
        m_jtfTextfield.addActionListener(listener);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param listener TODO Missing Method Parameter Documentation
     */
    public final void removeActionListener(ActionListener listener) {
        m_jtfTextfield.removeActionListener(listener);
    }

   
    /**
     * TODO Missing Method Documentation
     *
     * @param text TODO Missing Method Parameter Documentation
     */
    private void initComponents(String text) {
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(4, 4, 4, 4);

        setLayout(layout);
        
        m_jlLabel = new JLabel(text);
        m_jlLabel.setPreferredSize(new Dimension(200, 20));
        constraints.gridwidth = 3;
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        layout.setConstraints(m_jlLabel, constraints);
        add(m_jlLabel);

        m_jlBaseValue = new JLabel(base + ""); 
        m_jlBaseValue.setOpaque(false);
        m_jlBaseValue.setPreferredSize(new Dimension(40, 20));
        constraints.gridwidth = 1;
        constraints.gridx = 4;
        layout.setConstraints(m_jlBaseValue, constraints);
        add(m_jlBaseValue);
        
        JLabel tempLabel = new JLabel("+");
        tempLabel.setPreferredSize(new Dimension(20, 20));
        tempLabel.setOpaque(false);
        constraints.gridx = 5;
        layout.setConstraints(tempLabel, constraints);
        add(tempLabel);
       

        m_jtfTextfield = new JTextField(offset + "", 4);
        m_jtfTextfield.setEditable(true);
        m_jtfTextfield.setHorizontalAlignment(JTextField.CENTER);
        m_jtfTextfield.addActionListener(this);
        m_jtfTextfield.setMaximumSize(new Dimension(20, 20));
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 6;
        layout.setConstraints(m_jtfTextfield, constraints);
        add(m_jtfTextfield);
        
     
        tempLabel = new JLabel("=");
        tempLabel.setOpaque(false);
        tempLabel.setPreferredSize(new Dimension(20, 20));
        constraints.gridwidth = 1;
        constraints.gridx = 7;
        layout.setConstraints(tempLabel, constraints);
        add(tempLabel);
    
        m_jlTotal = new JLabel(nf.format(base + offset + 0.000001f));
        m_jlTotal.setOpaque(false);
        m_jlTotal.setPreferredSize(new Dimension(40, 20));
        constraints.gridx = 8;
        layout.setConstraints(m_jlTotal, constraints);
        add(m_jlTotal);
    
    }

	public void actionPerformed(ActionEvent e) {
		try {
			offset = new Float(m_jtfTextfield.getText());
			m_jlTotal.setText(nf.format(offset + base));
			m_jlTotal.setForeground(ThemeManager.getColor(HOColorName.LABEL_FG));
			top.refresh(); // Store
		} catch (Exception ex) {
			// Give a hint the value was not quite what we wanted
			m_jlTotal.setForeground(ThemeManager.getColor(HOColorName.LABEL_ERROR_FG));
		}
	}
}
