// %535933150:hoplugins.trainingExperience.ui%
/*
 * Created on 20-mar-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package hoplugins.trainingExperience.ui;

import plugins.IFutureTrainingManager;

import java.util.Comparator;
import java.util.Vector;


/**
 * DOCUMENT ME!
 *
 * @author Mirtillo To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TrainingComparator implements Comparator {
    //~ Methods ------------------------------------------------------------------------------------

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Object arg0, Object arg1) {
        Vector v1 = (Vector) arg0;
        Vector v2 = (Vector) arg1;

        for (int i = 1; i < (IFutureTrainingManager.FUTUREWEEKS + 1); i++) {
            String s1 = "" + v1.get(i); //$NON-NLS-1$
            String s2 = "" + v2.get(i); //$NON-NLS-1$

            if (!s1.equalsIgnoreCase("") && s2.equalsIgnoreCase("")) { //$NON-NLS-1$ //$NON-NLS-2$
                return -1;
            } else if (s1.equalsIgnoreCase("") && !s2.equalsIgnoreCase("")) { //$NON-NLS-1$ //$NON-NLS-2$
                return 1;
            }
        }

        return 1;
    }
}
