// %3954663446:hoplugins.teamplanner.ui.model.inner%
package hoplugins.teamplanner.ui.model.inner;

/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class NumericInner implements InnerData {
    //~ Instance fields ----------------------------------------------------------------------------

    private double value = 0;
    private int money = 0;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new BaseInner object.
     */
    public NumericInner() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param val Missing Method Parameter Documentation
     * @param money Missing Method Parameter Documentation
     */
    public void setData(double val, int money) {
        this.money = money;
        this.value = val;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public String getDescription() {
        return (int) value + "";
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getId() {
        return 1;
    }

    /**
     * Missing Method Documentation
     *
     * @param i Missing Method Parameter Documentation
     */
    public void setMoney(int i) {
        this.money = i;
        this.value = i;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getMoney() {
        return money;
    }

    /* (non-Javadoc)
     * @see hoplugins.teamplanner.ui.model.inner.InnerData#isValid()
     */
    public boolean isValid() {
        return true;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public double getValue() {
        return value;
    }
}
