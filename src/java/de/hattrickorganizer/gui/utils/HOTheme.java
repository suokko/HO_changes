package de.hattrickorganizer.gui.utils;

import gui.UserParameter;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;

import de.hattrickorganizer.HO;
import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.tools.HOLogger;

/**
 * Theme configuring HO colors, fonts and sizes.
 */
public class HOTheme extends DefaultMetalTheme {
    private static final ColorUIResource primary1 = new ColorUIResource(106, 104, 100);
    private static final ColorUIResource primary2 = new ColorUIResource(159, 156, 150);
    private static final ColorUIResource primary3 = new ColorUIResource(212, 208, 200);
    private static final ColorUIResource secondary1 = new ColorUIResource(106, 104, 100);
    private static final ColorUIResource secondary2 = new ColorUIResource(159, 156, 150);
    private static final ColorUIResource secondary3 = new ColorUIResource(212, 208, 200);
    private static FontUIResource TEXTFONT;

    //~ Constructors -------------------------------------------------------------------------------
    /**
     * Creates a new HOTheme object.
     *
     * @param schriftgroesse font size (e.g. from user parameters)
     */
    public HOTheme(int schriftgroesse) {
        super();
        try {
			if ("Georgian".equals(UserParameter.instance().sprachDatei)) {
				Font[] allfonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
				String geFont = checkInstalledFont("BPG Nino Elite Round Cond", allfonts); // prefer BPG_Nino_Elite_Round_Cond
				if (geFont == null) {
					geFont = checkInstalledFont("Arial Unicode MS", allfonts); // Arial Unicode is the 2nd option
				}
				if (geFont == null) {
					geFont = checkInstalledFont("Sylfaen", allfonts); // try Sylfan as 3rd
				}
				if (geFont == null) {
					String georgiansample = "\u10f0";
					for (int j = 0; j < allfonts.length; j++) {
						if (allfonts[j].canDisplayUpTo(georgiansample) == -1) {
							HOLogger.instance().log(HOMainFrame.class, "Font can handle Georgian: " + allfonts[j].getFontName());
							geFont = allfonts[j].getFontName();
							break;
						}
					}
				}
				TEXTFONT = new FontUIResource((geFont != null ? geFont : "SansSerif"), Font.PLAIN, schriftgroesse);
			} else if ("Chinese".equals(UserParameter.instance().sprachDatei)) {
				Font[] allfonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
				String chFont = checkInstalledFont("Arial Unicode MS", allfonts); // 1. try to use Arial Unicode MS
				if (chFont == null) {
					chFont = checkInstalledFont("SimSun", allfonts); // 2. 2nd best option is SimSun
				}
				if (chFont == null) { // 3. still no font found yet, check other fonts
					String chinesesample = "\u4e00";
					for (int j = 0; j < allfonts.length; j++) {
						if (allfonts[j].canDisplayUpTo(chinesesample) == -1) {
							HOLogger.instance().log(HOMainFrame.class, "Font can handle Chinese: " + allfonts[j].getFontName());
							chFont = allfonts[j].getFontName();
							break;
						}
					}
				}
				TEXTFONT = new FontUIResource((chFont != null ? chFont : "SansSerif"), Font.PLAIN, schriftgroesse);
			} else {
				TEXTFONT = new FontUIResource("SansSerif", Font.PLAIN, schriftgroesse);
			}
			setUIFont(TEXTFONT);
		} catch (Exception e) {
			HOLogger.instance().log(HOMainFrame.class, "Error switching fonts: " + e);
		}

        //Ausnahme TitledBorderfont nicht Bold
        javax.swing.UIManager.put("TitledBorder.font", TEXTFONT);
    }

    //~ Methods ------------------------------------------------------------------------------------

    @Override
	public final FontUIResource getControlTextFont() {
        return TEXTFONT;
    }

    @Override
	public final FontUIResource getMenuTextFont() {
        return TEXTFONT;
    }

    @Override
	public final FontUIResource getSubTextFont() {
        return TEXTFONT;
    }

    @Override
	public final FontUIResource getSystemTextFont() {
        return TEXTFONT;
    }

    @Override
	public final FontUIResource getUserTextFont() {
        return TEXTFONT;
    }

    @Override
	public final FontUIResource getWindowTitleFont() {
        return TEXTFONT;
    }

    // these are blue in Metal Default Theme
    @Override
	protected final ColorUIResource getPrimary1() {
        return primary1;
    }

    @Override
	protected final ColorUIResource getPrimary2() {
        return primary2;
    }

    @Override
	protected final ColorUIResource getPrimary3() {
        return primary3;
    }

    // these are gray in Metal Default Theme
    @Override
	protected final ColorUIResource getSecondary1() {
        return secondary1;
    }

    @Override
	protected final ColorUIResource getSecondary2() {
        return secondary2;
    }

    @Override
	protected final ColorUIResource getSecondary3() {
        return secondary3;
    }

    public static FontUIResource getDefaultFont() {
    	return TEXTFONT;
    }

    private static String checkInstalledFont(String targetFont, Font[] allfonts) {
    	if (targetFont != null) {
    		for (int j = 0; j < allfonts.length; j++) {
    			if (targetFont.equalsIgnoreCase(allfonts[j].getFontName())) {
    				return allfonts[j].getFontName();
    			}
    		}
    	}
    	return null;
    }

    /**
     * Globally configure the font (size).
     */
    public static void setUIFont(FontUIResource f) {
    	try {
    		TEXTFONT = f;
			Enumeration<Object> keys = UIManager.getDefaults().keys();
			while (keys.hasMoreElements()) {
				Object key = keys.nextElement();
				Object value = UIManager.get(key);
				if (value instanceof FontUIResource) {
					UIManager.put(key, f);
				}
			}
			UIManager.put("Frame.font", f);
			UIManager.put("InternalFrame.titleFont", f);
			UIManager.put("TitledBorder.font", f);
		} catch (Exception e) {
			HOLogger.instance().log(HO.class, "Error(setUIFont): " + e);
		}
    }
}
