// %3416077218:de.hattrickorganizer.credits%
package de.hattrickorganizer.credits;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;

import de.hattrickorganizer.gui.theme.ImageUtilities;
import de.hattrickorganizer.gui.theme.ThemeManager;


/**
 * GraphicLibrary mit dynamischen GraphicSet jetzt komplett static!
 *
 * @author Volker Fischer
 * @version 0.4b 27.10.02
 */
class GraphicLibrary {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static Component m_clComponent;
    private static MediaTracker m_clTracker;

    static final AlphaComposite DEFAULTALPHA = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f);

    //-----------Grafiken-Anfang----------------------------------------------------	
    //--Anfang Misc--

    static transient Image ball;
    static transient Image effekt;
    static transient Image nix;
    static transient Image background;
    static transient Image splashscreen;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * lädt alle Grafiken, die für das Spiel benötigt werden:<br>
     * - Misc (alles, was keiner anderen Gruppe angehört)<br>
     *
     */
    static void load(Component component) {
        m_clComponent = component;
        m_clTracker = new MediaTracker(m_clComponent);
        loadMisc();
    }

    /**
     * lädt alle weiteren Grafiken
     */
    private static void loadMisc() {
        ball 		= ImageUtilities.makeColorTransparent(ThemeManager.loadImage("gui/bilder/credits/Ball.png"),new Color(255, 0, 0));
        effekt 		= ThemeManager.loadImage("gui/bilder/credits/Effekt.gif");
        background 	= ThemeManager.loadImage("gui/bilder/credits/Background.jpg");
        splashscreen = ImageUtilities.makeColorTransparent(ThemeManager.loadImage("gui/bilder/intro.jpg"), new Color(255, 0, 0));
        nix 		= ThemeManager.loadImage("gui/bilder/credits/nix.GIF");
    }
}
