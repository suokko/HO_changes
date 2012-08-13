// %1127326195572:gui%
package ho.core.gui;

/**
 * Die Implementation kann sich beim MainFrame anmelden und wird dann bei Datenänderungen
 * aufgefordert sich neu zu zeichnen
 */
public interface Refreshable extends IRefreshable {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     */
    public void reInit();


}
