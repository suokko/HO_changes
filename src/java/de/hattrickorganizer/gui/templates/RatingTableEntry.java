// %3659932329:de.hattrickorganizer.gui.templates%
package de.hattrickorganizer.gui.templates;

import ho.core.gui.comp.renderer.HODefaultTableCellRenderer;
import ho.core.gui.theme.ImageUtilities;
import ho.core.gui.theme.ThemeManager;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import plugins.IHOTableEntry;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class RatingTableEntry implements TableEntry {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static ImageIcon FULL10STARIMAGEICON;
    private static ImageIcon FULLGREY10STARIMAGEICON;
    private static ImageIcon FULL5STARIMAGEICON;
    private static ImageIcon FULLGREY5STARIMAGEICON;
    private static ImageIcon FULL50STARIMAGEICON;
    private static ImageIcon FULLGREY50STARIMAGEICON;
    private static ImageIcon FULLSTARIMAGEICON;
    private static ImageIcon HALFSTARIMAGEICON;
    private static ImageIcon FULLGREYSTARIMAGEICON;
    private static ImageIcon HALFGREYSTARIMAGEICON;

    //~ Instance fields ----------------------------------------------------------------------------

    private JComponent m_clComponent = new JPanel();
    private String m_sTooltip = "";
    private boolean m_bYellowStar;
    private float m_fRating;
    private boolean isOpaque = true;
    private Color bgColor = ColorLabelEntry.BG_STANDARD;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new RatingTableEntry object.
     */
    public RatingTableEntry() {
        initStarsIcons();

        m_fRating = 0.0F;
        m_bYellowStar = true;
        createComponent();
    }

    /**
     * Creates a new RatingTableEntry object.
     *
     * @param f TODO Missing Constructuor Parameter Documentation
     * @param yellowstar TODO Missing Constructuor Parameter Documentation
     */
    public RatingTableEntry(float f, boolean yellowstar) {
        initStarsIcons();

        m_bYellowStar = yellowstar;
        setRating(f);

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
	public final javax.swing.JComponent getComponent(boolean isSelected) {
        m_clComponent.setBackground((isSelected)?HODefaultTableCellRenderer.SELECTION_BG:bgColor);
        m_clComponent.setOpaque(isOpaque);
        
        return m_clComponent;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param f TODO Missing Method Parameter Documentation
     */
    public final void setRating(float f) {
        setRating(f, false);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param f TODO Missing Method Parameter Documentation
     * @param forceUpdate TODO Missing Method Parameter Documentation
     */
    public final void setRating(float f, boolean forceUpdate) {
        if (f < 0) {
            f = 0;
        }

        if (forceUpdate || (f != m_fRating)) {
            m_fRating = f;
            updateComponent();
        }

        m_clComponent.repaint();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final float getRating() {
        return m_fRating;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param text TODO Missing Method Parameter Documentation
     */
    public final void setToolTipText(String text) {
        m_sTooltip = text;
        updateComponent();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public final void setYellowStar(boolean value) {
        if (m_bYellowStar != value) {
            m_bYellowStar = value;
            updateComponent();
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isYellowStar() {
        return m_bYellowStar;
    }

    /**
     * TODO Missing Method Documentation
     */
	public final void clear() {
        m_clComponent.removeAll();

        //Platzhalter
        JLabel jlabel;
        jlabel = new JLabel(ImageUtilities.NOIMAGEICON);
        jlabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        m_clComponent.add(jlabel);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param obj TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
	public final int compareTo(IHOTableEntry obj) {
        if (obj instanceof RatingTableEntry) {
            final RatingTableEntry entry = (RatingTableEntry) obj;

            if (getRating() < entry.getRating()) {
                return -1;
            } else if (getRating() > entry.getRating()) {
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
        float f = m_fRating / 2;
        JPanel renderer = new JPanel();
        renderer.setLayout(new BoxLayout(renderer, 0));
        renderer.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        //Platzhalter
 
        setStars(renderer,f);

        //renderer.setPreferredSize ( new java.awt.Dimension( 100, 14 ) );
        renderer.setToolTipText(m_sTooltip);

        m_clComponent = renderer;
    }

    /**
     * 
     * @param panel
     * @param yellowImage
     * @param grayImage
     */
    private void addLabel(JComponent panel,ImageIcon yellowImage, ImageIcon grayImage){
    	final JLabel jlabel = new JLabel((m_bYellowStar)?yellowImage:grayImage);
        jlabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        panel.add(jlabel);
    }
    
    /**
     * TODO Missing Method Documentation
     */
	public final void updateComponent() {
        float f = m_fRating / 2;
        
        m_clComponent.removeAll();

       setStars(m_clComponent,f);

        m_clComponent.setToolTipText(m_sTooltip);

        m_clComponent.repaint();
    }
    
    private void setStars(JComponent panel, float f) {
		if (f == 0) {
			JLabel jlabel;
			jlabel = new JLabel(ImageUtilities.NOIMAGEICON);
			jlabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			panel.add(jlabel);
		}

		while (f >= 50) {
			addLabel(panel, FULL50STARIMAGEICON, FULLGREY50STARIMAGEICON);
			f -= 50;
		}

		while (f >= 10) {
			addLabel(panel, FULL10STARIMAGEICON, FULLGREY10STARIMAGEICON);
			f -= 10;
		}

		while (f >= 5) {
			addLabel(panel, FULL5STARIMAGEICON, FULLGREY5STARIMAGEICON);
			f -= 5;
		}

		while (f >= 1) {
			addLabel(panel, FULLSTARIMAGEICON, FULLGREYSTARIMAGEICON);
			f -= 1;
		}

		if (f == 0.5) {
			addLabel(panel, HALFSTARIMAGEICON, HALFGREYSTARIMAGEICON);
		}
	}
    
    private void initStarsIcons(){
    	if ((FULLSTARIMAGEICON == null) || (HALFSTARIMAGEICON == null)) {
            FULL10STARIMAGEICON = new ImageIcon(ImageUtilities.makeColorTransparent(ThemeManager.loadImage("gui/bilder/star_10.png"),
                                                                                  210, 210, 185,
                                                                                  255, 255, 255));
            FULLGREY10STARIMAGEICON = new ImageIcon(ImageUtilities.makeColorTransparent(ThemeManager.loadImage("gui/bilder/star_10_grey.png"),
                                                                                      215, 215,
                                                                                      215, 255,
                                                                                      255, 255));
            FULL5STARIMAGEICON = new ImageIcon(ImageUtilities.makeColorTransparent(ThemeManager.loadImage("gui/bilder/star_5.png"),
                                                                                 210, 210, 185,
                                                                                 255, 255, 255));
            FULLGREY5STARIMAGEICON = new ImageIcon(ImageUtilities.makeColorTransparent(ThemeManager.loadImage("gui/bilder/star_5_grey.png"),
                                                                                     215, 215, 215,
                                                                                     255, 255, 255));
            FULL50STARIMAGEICON = new ImageIcon(ImageUtilities.makeColorTransparent(ThemeManager.loadImage("gui/bilder/star_50.png"),
                                                                                  210, 210, 185,
                                                                                  255, 255, 255));
            FULLGREY50STARIMAGEICON = new ImageIcon(ImageUtilities.makeColorTransparent(ThemeManager.loadImage("gui/bilder/star_50_grey.png"),
                                                                                      215, 215,
                                                                                      215, 255,
                                                                                      255, 255));
            FULLSTARIMAGEICON = new ImageIcon(ImageUtilities.makeColorTransparent(ThemeManager.loadImage("gui/bilder/star.gif"),
                                                                                210, 210, 185, 255,
                                                                                255, 255));
            HALFSTARIMAGEICON = new ImageIcon(ImageUtilities.makeColorTransparent(ThemeManager.loadImage("gui/bilder/star_half.gif"),
                                                                                210, 210, 185, 255,
                                                                                255, 255));
            FULLGREYSTARIMAGEICON = new ImageIcon(ImageUtilities.makeColorTransparent(ThemeManager.loadImage("gui/bilder/star_grey.png"),
                                                                                    215, 215, 215,
                                                                                    255, 255, 255));
            HALFGREYSTARIMAGEICON = new ImageIcon(ImageUtilities.makeColorTransparent(ThemeManager.loadImage("gui/bilder/star_grey_half.png"),
                                                                                    215, 215, 215,
                                                                                    255, 255, 255));
        }	
    }

	public boolean isOpaque() {
		return isOpaque;
	}

	public void setOpaque(boolean isOpaque) {
		this.isOpaque = isOpaque;
		updateComponent();
	}

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
		updateComponent();
	}
    
    
}
