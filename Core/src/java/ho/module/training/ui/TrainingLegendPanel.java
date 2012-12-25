/*
 * Created on 14.10.2005
 */
package ho.module.training.ui;

import ho.core.constants.player.PlayerSkill;
import ho.core.gui.theme.ImageUtilities;
import ho.core.model.HOVerwaltung;
import ho.module.training.Skills;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TrainingLegendPanel extends JPanel {
    //~ Constructors -------------------------------------------------------------------------------
	private static final long serialVersionUID = 7019803928403346886L;

	/**
     * Default constructor.
     */
    public TrainingLegendPanel() {
        this.setLayout(new GridLayout(2, 5));

        JLabel title = new JLabel(HOVerwaltung.instance().getLanguageString("Legenda"), SwingConstants.LEFT); //$NON-NLS-1$

        title.setForeground(Color.BLACK);
        this.add(title);

        for (int i = 0; i < 4; i++) {
            int skill = Skills.getSkillAtPosition(i);
            this.add(getSkillupLabel(PlayerSkill.toString(skill), skill));
        }

        this.add(new JLabel(""));

        for (int i = 4; i < 8; i++) {
            int skill = Skills.getSkillAtPosition(i);
            this.add(getSkillupLabel(PlayerSkill.toString(skill), skill));
        }

        this.setBorder(BorderFactory.createEtchedBorder(0));
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Create a position colored label.
     *
     * @param title text of the label
     * @param skill color of the text
     *
     * @return a label
     */
    public static final JLabel getSkillupLabel(String title, int skill) {
        Icon icon = getSkillupTypeIcon(skill, 1);

        return new JLabel(title, icon, SwingConstants.LEFT);
    }

    public static final Icon getSkillupTypeIcon(int skill, int count) {
        Color color = Skills.getSkillColor(skill);

        Color lcolor = new Color(Math.min(color.getRed() + 20, 255),
                                 Math.min(color.getGreen() + 20, 255),
                                 Math.min(color.getBlue() + 20, 255), 224);

        ImageIcon icon =  ImageUtilities.getImageIcon4Veraenderung(count,true);

        int farbwert = Math.min(240, 90 + (50 * count));
        Color iconColor = new Color(0, farbwert, 0);
        Image image = ReplaceImageColor(icon.getImage(), iconColor, color);

        farbwert = Math.min(255, 105 + (50 * count));
        iconColor = new Color(40, farbwert, 40);
        image = ReplaceImageColor(image, iconColor, lcolor);

        if (count > 2) {
            image = ReplaceImageColor(image, Color.white, Color.magenta);
            image = ReplaceImageColor(image, Color.black, Color.white);
            image = ReplaceImageColor(image, Color.magenta, Color.black);
        }

        return new ImageIcon(image);
    }

    public static Image ReplaceImageColor(Image image, Color oldColor, Color newColor) {
        FilteredImageSource filteredimagesource = new FilteredImageSource(image.getSource(),
                                                                          ((new ColorReplaceFilter(oldColor,
                                                                                                                                newColor,
                                                                                                                                null))));

        return Toolkit.getDefaultToolkit().createImage(((filteredimagesource)));
    }

    //~ Inner Classes ------------------------------------------------------------------------------

    /**
     * Color replacement filter class
     */
    public static final class ColorReplaceFilter extends RGBImageFilter {
        //~ Instance fields ------------------------------------------------------------------------
        boolean transparency = false;
        int newColor = 0;
        int oldColor = 0;
        int transparentColor = 0;

        //~ Constructors ---------------------------------------------------------------------------
        /**
         * Creates a new ColorReplaceFilter object.
         */
        ColorReplaceFilter(Color oldColor, Color newColor, Color transparentColor) {
            this.oldColor = oldColor.getRGB();
            this.newColor = newColor.getRGB();

            if (transparentColor != null) {
                transparency = true;
                this.transparentColor = transparentColor.getRGB() | 0xff000000;
            }
        }

        //~ Methods --------------------------------------------------------------------------------
        @Override
		public int filterRGB(int x, int y, int rgb) {
            if (transparency && ((rgb | 0xff000000) == transparentColor)) {
                return 0;
            } else if (rgb == oldColor) {
                return newColor;
            } else {
                return rgb;
            }
        }
    }
}
