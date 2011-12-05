// %1127326955603:plugins%
package plugins;

/**
 * TODO Missing Interface Documentation
 *
 * @author TODO Author Name
 */
public interface IInfoPanel {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Set the short infotext
     *
     * @param text TODO Missing Constructuor Parameter Documentation
     */
    public void setKurzInfoText(String text);

    /**
     * Set the long infotext
     *
     * @param text TODO Missing Constructuor Parameter Documentation
     */
    public void setLangInfoText(String text);

    /**
     * Set the long infotext in that color
     *
     * @param text TODO Missing Constructuor Parameter Documentation
     * @param zeichenfarbe TODO Missing Constructuor Parameter Documentation
     */
    public void setLangInfoText(String text, java.awt.Color zeichenfarbe);

    /**
     * Change the value of the progressbar
     *
     * @param value min=0, max=100
     */
    public void changeProgressbarValue(int value);

    /**
     * clear all informations
     */
    public void clearAll();

    /**
     * clear the short infotext
     */
    public void clearKurzInfo();

    /**
     * clear the long infotext
     */
    public void clearLangInfo();

    /**
     * reset the progressbar
     */
    public void clearProgressbar();
}
