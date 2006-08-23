// %3416077218:de.hattrickorganizer.credits%
package de.hattrickorganizer.credits;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;


/**
 * GraphicLibrary mit dynamischen GraphicSet jetzt komplett static!
 *
 * @author Volker Fischer
 * @version 0.4b 27.10.02
 */
public class GraphicLibrary {
    //~ Static fields/initializers -----------------------------------------------------------------

    private static Component m_clComponent;
    private static MediaTracker m_clTracker;

    /** TODO Missing Parameter Documentation */
    public static final AlphaComposite DEFAULTALPHA = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                                                 1.0f);

    //-----------Grafiken-Anfang----------------------------------------------------	
    //--Anfang Misc--

    /** TODO Missing Parameter Documentation */
    public static transient Image ball;

    /** TODO Missing Parameter Documentation */
    public static transient Image effekt;

    /** TODO Missing Parameter Documentation */
    public static transient Image nix;

    /** TODO Missing Parameter Documentation */
    public static transient Image background;

    /** TODO Missing Parameter Documentation */
    public static transient Image splashscreen;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * lädt alle Grafiken, die für das Spiel benötigt werden:<br>
     * - Misc (alles, was keiner anderen Gruppe angehört)<br>
     *
     * @param component TODO Missing Constructuor Parameter Documentation
     */
    public static void load(Component component) {
        m_clComponent = component;
        m_clTracker = new MediaTracker(m_clComponent);
        loadMisc();
    }

    /**
     * lädt alle weiteren Grafiken
     */
    private static void loadMisc() {
        ball = de.hattrickorganizer.tools.Helper.makeColorTransparent(de.hattrickorganizer.tools.Helper
                                                                      .loadImage("gui/bilder/credits/Ball.png"),
                                                                      new Color(255, 0, 0));
        effekt = de.hattrickorganizer.tools.Helper.loadImage("gui/bilder/credits/Effekt.gif");
        background = de.hattrickorganizer.tools.Helper.loadImage("gui/bilder/credits/Background.jpg");
        splashscreen = de.hattrickorganizer.tools.Helper.makeColorTransparent(de.hattrickorganizer.tools.Helper
                                                                              .loadImage("gui/bilder/intro.jpg"),
                                                                              new Color(255, 0, 0));
        nix = de.hattrickorganizer.tools.Helper.loadImage("gui/bilder/credits/nix.GIF");
    }
}
