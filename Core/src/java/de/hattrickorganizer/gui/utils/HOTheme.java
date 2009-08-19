// %2098794029:de.hattrickorganizer.gui.utils%
package de.hattrickorganizer.gui.utils;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class HOTheme extends DefaultMetalTheme {
    //~ Static fields/initializers -----------------------------------------------------------------

    //Default

    /*
       private static final ColorUIResource primary1 = new ColorUIResource( 102, 102, 153);
       private static final ColorUIResource primary2 = new ColorUIResource( 153, 153, 204);
       private static final ColorUIResource primary3 = new ColorUIResource( 204, 204, 255);
       private static final ColorUIResource secondary1 = new ColorUIResource( 102, 102, 102);
       private static final ColorUIResource secondary2 = new ColorUIResource( 153, 153, 153);
       private static final ColorUIResource secondary3 = new ColorUIResource( 204, 204, 204);
     */

    /** TODO Missing Parameter Documentation */
    private static final ColorUIResource primary1 = new ColorUIResource(106, 104, 100);

    /** TODO Missing Parameter Documentation */
    private static final ColorUIResource primary2 = new ColorUIResource(159, 156, 150);

    /** TODO Missing Parameter Documentation */
    private static final ColorUIResource primary3 = new ColorUIResource(212, 208, 200);

    /** TODO Missing Parameter Documentation */
    private static final ColorUIResource secondary1 = new ColorUIResource(106, 104, 100);

    /** TODO Missing Parameter Documentation */
    private static final ColorUIResource secondary2 = new ColorUIResource(159, 156, 150);

    /** TODO Missing Parameter Documentation */
    private static final ColorUIResource secondary3 = new ColorUIResource(212, 208, 200);
    private static FontUIResource TEXTFONT;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new HOTheme object.
     *
     * @param schriftgroesse TODO Missing Constructuor Parameter Documentation
     */
    public HOTheme(int schriftgroesse) {
        super();

        TEXTFONT = new FontUIResource("SansSerif", java.awt.Font.PLAIN, schriftgroesse);

        //Ausnahme TitledBorderfont nicht Bold
        javax.swing.UIManager.put("TitledBorder.font", TEXTFONT);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public final FontUIResource getControlTextFont() {
        return TEXTFONT;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public final FontUIResource getMenuTextFont() {
        return TEXTFONT;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public final FontUIResource getSubTextFont() {
        return TEXTFONT;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public final FontUIResource getSystemTextFont() {
        return TEXTFONT;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public final FontUIResource getUserTextFont() {
        return TEXTFONT;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public final FontUIResource getWindowTitleFont() {
        return TEXTFONT;
    }

    // these are blue in Metal Default Theme
    @Override
	protected final ColorUIResource getPrimary1() {
        return primary1;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	protected final ColorUIResource getPrimary2() {
        return primary2;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	protected final ColorUIResource getPrimary3() {
        return primary3;
    }

    // these are gray in Metal Default Theme
    @Override
	protected final ColorUIResource getSecondary1() {
        return secondary1;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	protected final ColorUIResource getSecondary2() {
        return secondary2;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	protected final ColorUIResource getSecondary3() {
        return secondary3;
    }
}
