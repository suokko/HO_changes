// %1127326958697:plugins%
/*
 * ISpielerComboboxItem.java
 *
 * Created on 16. Oktober 2004, 11:06
 */
package plugins;

/**
 * Items for a JCombobox, if you use IGUI.addPlayerComboboxRenderer to add the Renderer to the
 * Combobox
 *
 * @author Pirania
 */
public interface ISpielerComboboxItem extends Comparable<ISpielerComboboxItem> {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public float getPositionsBewertung();

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public plugins.ISpieler getSpieler();

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getText();

    /**
     * DOCUMENT ME!
     *
     * @param text Shown text, usually spieler.getName()
     * @param poswert Rating for the actual position
     * @param spieler The player
     */
    public void setValues(String text, float poswert, plugins.ISpieler spieler);

    /**
     * TODO Missing Method Documentation
     *
     * @param obj TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int compareTo(ISpielerComboboxItem obj);
}
