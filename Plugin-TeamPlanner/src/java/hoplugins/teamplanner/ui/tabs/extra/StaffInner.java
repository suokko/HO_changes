// %2582897248:hoplugins.teamplanner.ui.tabs.extra%
package hoplugins.teamplanner.ui.tabs.extra;

import hoplugins.teamplanner.ui.model.inner.InnerData;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class StaffInner implements InnerData {
    //~ Instance fields ----------------------------------------------------------------------------

    private StaffInner total = null;
    private boolean expenses = true;
    private int assistantCoaches;
    private int assistantKeeper;
    private int doctor;
    private int phisio;
    private int psico;
    private int spokesman;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @param i Missing Method Parameter Documentation
     */
    public void setAssistantCoaches(int i) {
        assistantCoaches = i;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getAssistantCoaches() {
        return assistantCoaches;
    }

    /**
     * Missing Method Documentation
     *
     * @param i Missing Method Parameter Documentation
     */
    public void setAssistantKeeper(int i) {
        assistantKeeper = i;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getAssistantKeeper() {
        return assistantKeeper;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public String getDescription() {
        if (getChanges() > 0) {
            return "Hired " + getChanges();
        } else if (getChanges() < 0) {
            return "Fired " + (-getChanges());
        }

        return "";
    }

    /**
     * Missing Method Documentation
     *
     * @param i Missing Method Parameter Documentation
     */
    public void setDoctor(int i) {
        doctor = i;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getDoctor() {
        return doctor;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getFired() {
        int fired = Math.min(0, assistantCoaches);
        fired += Math.min(0, assistantKeeper);
        fired += Math.min(0, phisio);
        fired += Math.min(0, psico);
        fired += Math.min(0, doctor);
        fired += Math.min(0, spokesman);
        return fired;
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
     *
     * @throws RuntimeException Missing Constructuor Exception Documentation
     */
    public void setMoney(int i) {
        throw new RuntimeException("Not Supported");
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getMoney() {
        if (expenses) {
            return getFired() * 1500;
        }

        return getChanges() * -1500;
    }

    /**
     * Missing Method Documentation
     *
     * @param i Missing Method Parameter Documentation
     */
    public void setPhisio(int i) {
        phisio = i;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getPhisio() {
        return phisio;
    }

    /**
     * Missing Method Documentation
     *
     * @param i Missing Method Parameter Documentation
     */
    public void setPsico(int i) {
        psico = i;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getPsico() {
        return psico;
    }

    /**
     * Missing Method Documentation
     *
     * @param i Missing Method Parameter Documentation
     */
    public void setSpokesman(int i) {
        spokesman = i;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getSpokesman() {
        return spokesman;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public StaffInner getTotal() {
        if (total == null) {
            total = new StaffInner();
            total.expenses = false;
        }

        return total;
    }

    /* (non-Javadoc)
     * @see hoplugins.teamplanner.ui.model.inner.InnerData#isValid()
     */
    public boolean isValid() {
        if (getTotal().assistantCoaches < 0) {
            return false;
        }

        if (getTotal().assistantKeeper < 0) {
            return false;
        }

        if (getTotal().doctor < 0) {
            return false;
        }

        if (getTotal().phisio < 0) {
            return false;
        }

        if (getTotal().psico < 0) {
            return false;
        }

        if (getTotal().spokesman < 0) {
            return false;
        }

        if (getTotal().assistantCoaches > 10) {
            return false;
        }

        if (getTotal().assistantKeeper > 10) {
            return false;
        }

        if (getTotal().doctor > 10) {
            return false;
        }

        if (getTotal().phisio > 10) {
            return false;
        }

        if (getTotal().psico > 10) {
            return false;
        }

        if (getTotal().spokesman > 10) {
            return false;
        }

        return true;
    }

    /**
     * Missing Method Documentation
     *
     * @param i Missing Method Parameter Documentation
     */
    public void addAssistantCoaches(int i) {
        assistantCoaches += i;
    }

    /**
     * Missing Method Documentation
     *
     * @param i Missing Method Parameter Documentation
     */
    public void addAssistantKeeper(int i) {
        assistantKeeper += i;
    }

    /**
     * Missing Method Documentation
     *
     * @param i Missing Method Parameter Documentation
     */
    public void addDoctor(int i) {
        doctor += i;
    }

    /**
     * Missing Method Documentation
     *
     * @param i Missing Method Parameter Documentation
     */
    public void addPhisio(int i) {
        phisio += i;
    }

    /**
     * Missing Method Documentation
     *
     * @param i Missing Method Parameter Documentation
     */
    public void addPsico(int i) {
        psico += i;
    }

    /**
     * Missing Method Documentation
     *
     * @param i Missing Method Parameter Documentation
     */
    public void addSpokesman(int i) {
        spokesman += i;
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    private int getChanges() {
        return assistantCoaches + assistantKeeper + psico + phisio + doctor + spokesman;
    }
}
