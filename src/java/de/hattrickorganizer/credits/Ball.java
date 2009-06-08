// %3065356162:de.hattrickorganizer.credits%
package de.hattrickorganizer.credits;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class Ball {
    //~ Instance fields ----------------------------------------------------------------------------

    //private AffineTransformation at = new AffineTransformation();
    private Color farbe;
    private boolean goDown = true;
    private boolean goRight = true;
    private float x = 10.0f;
    private float y = 10.0f;
    private int screenHeight = VAPCredits.screenHeight;
    private int screenWidth = VAPCredits.screenWidth;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new Ball object.
     *
     * @param width TODO Missing Constructuor Parameter Documentation
     * @param height TODO Missing Constructuor Parameter Documentation
     */
    public Ball(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;

        x = (float) ((Math.random() * (screenWidth - 20)) + 10);
        y = (float) ((Math.random() * (screenHeight - 20)) + 10);

        if (Math.random() > 0.5) {
            goRight = false;
        }

        if (Math.random() > 0.5) {
            goDown = false;
        }

        farbe = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param vergangeneZeit TODO Missing Method Parameter Documentation
     */
    public final void action(long vergangeneZeit) {
        if (x <= 0) {
            goRight = true;
        }

        if (x > (screenWidth - 20)) {
            goRight = false;
        }

        if (y <= 0) {
            goDown = true;
        }

        if (y > (screenHeight - 20)) {
            goDown = false;
        }

        if (goRight) {
            x += (vergangeneZeit / 5.0f);
        } else {
            x -= (vergangeneZeit / 5.0f);
        }

        if (goDown) {
            y += (vergangeneZeit / 5.0f);
        } else {
            y -= (vergangeneZeit / 5.0f);
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param g TODO Missing Method Parameter Documentation
     */
    public final void render(Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;

        g2d.setComposite(GraphicLibrary.DEFAULTALPHA);

        //g2d.setColor(farbe);
        //g2d.fillOval(x, y, 20, 20);
        //g2d.drawRect(x,y,20,20);
        g2d.drawImage(GraphicLibrary.ball, (int) x, (int) y, null);
    }
}
