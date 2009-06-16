// %1127326364025:gui.vorlagen%
package de.hattrickorganizer.gui.templates;

import plugins.IHOTableEntry;


/**
 *
 */
public abstract class TableEntry implements plugins.IHOTableEntry {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param isSelected TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public abstract javax.swing.JComponent getComponent(boolean isSelected);

    /**
     * TODO Missing Method Documentation
     */
    public abstract void clear();

    /**
     * TODO Missing Method Documentation
     *
     * @param obj TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public abstract int compareTo(IHOTableEntry obj);

    /**
     * TODO Missing Method Documentation
     */
    public abstract void createComponent();

    /**
     * TODO Missing Method Documentation
     */
    public abstract void updateComponent();
}
