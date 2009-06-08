// %1116523449437:hoplugins.commons.ui%
package hoplugins.commons.ui;

import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

/**
 * Class of Utility for positioning of Frames and Dialog
 */
public class PositioningUtilities {
    /**
     * Method that place Dialog in the center of his parent
     *
     * @param dialog
     */
    public static void centerDialogInParent(Dialog dialog) {
        positionDialogRelativeToParent(dialog, 0.5D, 0.5D);
    }

    /**
     * Method the center the frame in the center of the screen
     *
     * @param window
     */
    public static void centerFrameOnScreen(Window window) {
        positionFrameOnScreen(window, 0.5D, 0.5D);
    }

    /**
     * Method that place the dialog relative to the parent
     *
     * @param dialog
     * @param x horizontal placement (0-1)
     * @param y vertical placement (0-1)
     */
    public static void positionDialogRelativeToParent(Dialog dialog, double x,
        double y) {
        Dimension dimension = dialog.getSize();
        Container container = dialog.getParent();
        Dimension dimension1 = container.getSize();
        int i = container.getX() - dimension.width;
        int j = container.getY() - dimension.height;
        int k = dimension.width + dimension1.width;
        int l = dimension.height + dimension1.height;
        int i1 = i + (int) (x * k);
        int j1 = j + (int) (y * l);
        Dimension dimension2 = Toolkit.getDefaultToolkit().getScreenSize();

        i1 = Math.min(i1, dimension2.width - dimension.width);
        i1 = Math.max(i1, 0);
        j1 = Math.min(j1, dimension2.height - dimension.height);
        j1 = Math.max(j1, 0);
        dialog.setBounds(i1, j1, dimension.width, dimension.height);
    }

    /**
     * Method that place the window relative to the screen
     *
     * @param window
     * @param x horizontal placement (0-1)
     * @param y vertical placement (0-1)
     */
    public static void positionFrameOnScreen(Window window, double x, double y) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dimension1 = window.getSize();
        int i = Math.max(dimension.width - dimension1.width, 0);
        int j = Math.max(dimension.height - dimension1.height, 0);
        int k = (int) (x * i);
        int l = (int) (y * j);

        window.setBounds(k, l, dimension1.width, dimension1.height);
    }

    /**
     * Method that place the window randomly in the screen
     *
     * @param window
     */
    public static void positionFrameRandomly(Window window) {
        positionFrameOnScreen(window, Math.random(), Math.random());
    }
}
