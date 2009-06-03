// %1117737442015:hoplugins.seriesstats%
/*
 * Created on 24.11.2004
 */
package hoplugins.seriesstats;

/**
 */
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class LegendeCheckBox extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public boolean isSelected = true;
    private JCheckBox jcbBox; // a 
    private JLabel jlLabel; // _fldif

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of LegendeComboBox
     */
    public LegendeCheckBox() {
        this("", null, true);
    }

    /**
     * Creates a new LegendeCheckBox object.
     *
     * @param s TODO Missing Constructuor Parameter Documentation
     */
    public LegendeCheckBox(String s) {
        this(s, null, true);
    }

    /**
     * Creates a new LegendeCheckBox object.
     *
     * @param s TODO Missing Constructuor Parameter Documentation
     * @param imageicon TODO Missing Constructuor Parameter Documentation
     */
    public LegendeCheckBox(String s, ImageIcon imageicon) {
        this(s, imageicon, true);
    }

    /**
     * Creates a new LegendeCheckBox object.
     *
     * @param s TODO Missing Constructuor Parameter Documentation
     * @param color TODO Missing Constructuor Parameter Documentation
     */
    public LegendeCheckBox(String s, Color color) {
        this(s, _col2imic(color), true);
    }

    /**
     * Creates a new LegendeCheckBox object.
     *
     * @param s TODO Missing Constructuor Parameter Documentation
     * @param imageicon TODO Missing Constructuor Parameter Documentation
     * @param flag TODO Missing Constructuor Parameter Documentation
     */
    public LegendeCheckBox(String s, ImageIcon imageicon, boolean flag) {
        this(s, imageicon, flag, 4);
    }

    /**
     * Creates a new LegendeCheckBox object.
     *
     * @param s TODO Missing Constructuor Parameter Documentation
     * @param imageicon TODO Missing Constructuor Parameter Documentation
     * @param flag TODO Missing Constructuor Parameter Documentation
     * @param i TODO Missing Constructuor Parameter Documentation
     */
    public LegendeCheckBox(String s, ImageIcon imageicon, boolean flag, int i) {
        jcbBox = new JCheckBox();
        jlLabel = new JLabel();

        GridBagLayout gridbaglayout = new GridBagLayout();
        GridBagConstraints gridbagconstraints = new GridBagConstraints();
        gridbagconstraints.fill = 2;
        gridbagconstraints.weightx = 0.0D;
        gridbagconstraints.weighty = 0.0D;
        gridbagconstraints.insets = new Insets(0, 0, 0, 0);
        setLayout(gridbaglayout);

        gridbagconstraints.gridx = 0;
        gridbagconstraints.gridy = 0;
        gridbagconstraints.weightx = 0.0D;
        jcbBox.setSelected(flag);
        jcbBox.setOpaque(false);
        gridbaglayout.setConstraints(jcbBox, gridbagconstraints);
        add(jcbBox);

        gridbagconstraints.gridx = 1;
        gridbagconstraints.gridy = 0;
        gridbagconstraints.weightx = 1.0D;
        jlLabel.setHorizontalTextPosition(i);
        jlLabel.setText(s);
        jlLabel.setIcon(imageicon);
        jlLabel.setOpaque(false);
        gridbaglayout.setConstraints(jlLabel, gridbagconstraints);
        add(jlLabel);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public JCheckBox getBox() {
        return jcbBox;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param AL TODO Missing Method Parameter Documentation
     */
    public void aAL(ActionListener AL) {
        jcbBox.addActionListener(AL);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param IL TODO Missing Method Parameter Documentation
     */
    public void aIL(ItemListener IL) {
        jcbBox.addItemListener(IL);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public boolean is() {
        if (isSelected) {
            isSelected = false;
        } else {
            isSelected = true;
        }

        return isSelected;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param AL TODO Missing Method Parameter Documentation
     */
    public void rAL(ActionListener AL) {
        jcbBox.removeActionListener(AL);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param color TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private static ImageIcon _col2imic(Color color) {
        BufferedImage bufferedimage = new BufferedImage(14, 14, 2);
        Graphics2D graphics2d = (Graphics2D) bufferedimage.getGraphics();
        graphics2d.setColor(color);
        graphics2d.fillRect(0, 0, 13, 13);
        return new ImageIcon(bufferedimage);
    }
}
