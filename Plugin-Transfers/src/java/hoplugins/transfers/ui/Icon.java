// %1126721330369:hoplugins.transfers.ui%
package hoplugins.transfers.ui;

import javax.swing.ImageIcon;


/**
 * Interface for easy access to icons.
 *
 * @author <a href=mailto:nethyperon@users.sourceforge.net>Boy van der Werf</a>
 */
public interface Icon {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** Icon for transfers in. */
    javax.swing.Icon IN = new ImageIcon(Icon.class.getResource("images/in.gif"));

    /** Icon for transfers out. */
    javax.swing.Icon OUT = new ImageIcon(Icon.class.getResource("images/out.gif"));
}
