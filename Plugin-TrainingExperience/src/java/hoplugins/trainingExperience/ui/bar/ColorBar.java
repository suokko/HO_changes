// %1542412551:hoplugins.trainingExperience.ui.bar%
package hoplugins.trainingExperience.ui.bar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JComponent;
import javax.swing.JFrame;


/**
 * ColorBar: A color bar
 */
public final class ColorBar extends JComponent {
    //~ Static fields/initializers -----------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -4167591212962253628L;

	/** TODO Missing Parameter Documentation */
    static final int RED = 0x1000000;

    /** TODO Missing Parameter Documentation */
    static final int GREEN = 0x0010000;

    /** TODO Missing Parameter Documentation */
    static final int BLUE = 0x0000100;

    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    final float xfrac;

    /** TODO Missing Parameter Documentation */
    final int length; /* length in x direction */

    /** TODO Missing Parameter Documentation */

    //int backgroundColor = 0x004cff;
    int backgroundColor = 0xff0000;

    /* thickness of the bar */

    /** TODO Missing Parameter Documentation */
    final int thickness;
    private float end;

    /** TODO Missing Parameter Documentation */
    private final int colormask;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * ColorBar: initialize a color bar
     *
     * @param value TODO Missing Constructuor Parameter Documentation
     * @param len TODO Missing Constructuor Parameter Documentation
     * @param len TODO Missing Constructuor Parameter Documentation
     */
    public ColorBar(float value, int len, int thick) {
        this.length = len;

        int col = GREEN;
        this.end = (int) (len * value);
        this.thickness = thick;
        this.colormask = col - (col / 256);
        this.backgroundColor = ((backgroundColor & (0xffffff ^ colormask))
                               ^ ((int) (value * colormask) & colormask));
        this.xfrac = (col / (float) Math.abs(length));
        paintImmediately(getBounds());
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    public void setLevel(float value) {
        this.end = (int) (value * length);
        paintImmediately(getBounds());
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param args TODO Missing Method Parameter Documentation
     */
    public static void main(String[] args) {
        ColorBar bar = new ColorBar(0.9f, 100, 10);
        bar.setOpaque(false);

        JFrame f = new JFrame();
		f.setBackground(Color.BLUE);
        f.getContentPane().add(bar);
        f.setVisible(true);
        f.pack();
        f.addWindowListener(new WindowListener() {
                public void windowOpened(WindowEvent arg0) {
                    // TODO Auto-generated method stub
                }

                public void windowClosing(WindowEvent arg0) {
                    System.exit(-1);
                }

                public void windowClosed(WindowEvent arg0) {
                    System.exit(-1);
                }

                public void windowIconified(WindowEvent arg0) {
                    // TODO Auto-generated method stub
                }

                public void windowDeiconified(WindowEvent arg0) {
                    // TODO Auto-generated method stub
                }

                public void windowActivated(WindowEvent arg0) {
                    // TODO Auto-generated method stub
                }

                public void windowDeactivated(WindowEvent arg0) {
                    // TODO Auto-generated method stub
                }
            });
    }

    /* draw a color bar */
    @Override
	public void paint(Graphics g) {    	
        g.clearRect(0, 0, length, thickness);

        int x = 0;
        int y = 0;
        int basecolor = (backgroundColor & (0xffffff ^ colormask));
        float amount = length - xfrac;

        for (; x < end; x += 1, amount -= xfrac) {
            int q = basecolor ^ (((int) amount) & colormask);
            g.setColor(new Color(q));
            g.drawLine(x, y, x, y + thickness);
        }
    }
}
