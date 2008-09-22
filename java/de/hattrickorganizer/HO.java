// %3209489045:de.hattrickorganizer%
package de.hattrickorganizer;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;


// %1127326364087:%

/*
 * ho.java
 *
 * Created on 17. März 2003, 15:30
 */

/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class HO {
    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of ho
     */
    public HO() {
    }

    /**
     * Globally configure the font (size).
     */
    public static void setUIFont (javax.swing.plaf.FontUIResource f){
    	Enumeration keys = UIManager.getDefaults().keys();
    	while (keys.hasMoreElements()) {
    		Object key = keys.nextElement();
    		Object value = UIManager.get (key);
    		if (value instanceof javax.swing.plaf.FontUIResource)
    			UIManager.put (key, f);
    	}
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	try {
    		if (args !=null && args.length>0) {
    			try {
    				int size = Integer.parseInt(args[0]);
    				FontUIResource fr = new FontUIResource("SansSerif", Font.PLAIN, size);
    				setUIFont(fr);
    			} catch (NumberFormatException nx) { /* nothing */ }
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
        de.hattrickorganizer.gui.HOMainFrame.main(args);
		//MemoryCleaner mc = new MemoryCleaner();
    }
}
