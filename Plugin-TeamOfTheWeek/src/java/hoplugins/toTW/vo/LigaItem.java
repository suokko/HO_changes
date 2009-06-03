// %3956941637:hoplugins.potw%
package hoplugins.toTW.vo;

/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class LigaItem {
    //~ Instance fields ----------------------------------------------------------------------------

    private String name;
    private int ligaId;
    private int season;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public void setLigaId(int i) {
        ligaId = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getLigaId() {
        return ligaId;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param string TODO Missing Method Parameter Documentation
     */
    public void setName(String string) {
        name = string;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getName() {
        return name;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param i TODO Missing Method Parameter Documentation
     */
    public void setSeason(int i) {
        season = i;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public int getSeason() {
        return season;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public String toString() {
        return name + " " + season;
    }
}
