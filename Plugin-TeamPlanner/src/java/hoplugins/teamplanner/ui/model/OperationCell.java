// %3096710261:hoplugins.teamplanner.ui.model%
package hoplugins.teamplanner.ui.model;

import hoplugins.teamplanner.ui.model.inner.InnerData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Missing Class Documentation
 *
 * @author Draghetto
 */
public class OperationCell {
    //~ Instance fields ----------------------------------------------------------------------------

    private List datas = new ArrayList();
    private boolean multi = false;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new OperationCellData object.
     *
     * @param inner Missing Constructuor Parameter Documentation
     * @param multiLine Missing Constructuor Parameter Documentation
     */
    public OperationCell(InnerData inner, boolean multiLine) {
        super();
        this.multi = multiLine;

        if (!isMulti()) {
            datas.add(new OperationData(inner));
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public int getBalance() {
        if (isMulti()) {
            int balance = 0;

            for (Iterator iter = getOperationList().iterator(); iter.hasNext();) {
                OperationData element = (OperationData) iter.next();
                balance += element.getInner().getMoney();
            }

            return balance;
        } else {
            return getOperation().getInner().getMoney();
        }
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    public boolean isMulti() {
        return multi;
    }

    /**
     * Missing Method Documentation
     *
     * @param data Missing Method Parameter Documentation
     */
    public void setOperation(OperationData data) {
        int index = 0;

        for (Iterator iter = datas.iterator(); iter.hasNext();) {
            OperationData element = (OperationData) iter.next();

            if (element.getId() == data.getId()) {
                datas.remove(index);
                datas.add(index, data);
                return;
            }

            index++;
        }
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     *
     * @throws RuntimeException Missing Constructuor Exception Documentation
     */
    public OperationData getOperation() {
        if (isMulti()) {
            throw new RuntimeException("Not Supported");
        } else {
            return (OperationData) datas.get(0);
        }
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     *
     * @throws RuntimeException Missing Constructuor Exception Documentation
     */
    public List getOperationList() {
        if (isMulti()) {
            return datas;
        } else {
            throw new RuntimeException("Not Supported");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public boolean isValid() {
        boolean isValid = true;

        for (Iterator iter = datas.iterator(); iter.hasNext();) {
            OperationData element = (OperationData) iter.next();

            if (!element.getInner().isValid()) {
                isValid = false;
            }
        }

        return isValid;
    }

    /**
     * Missing Method Documentation
     *
     * @param inner Missing Method Parameter Documentation
     *
     * @throws RuntimeException Missing Constructuor Exception Documentation
     */
    public void addOperation(InnerData inner) {
        OperationData data = new OperationData(inner);

        if (isMulti()) {
            datas.add(data);
        } else {
            throw new RuntimeException("Not Supported");
        }
    }

    /**
     *
     */
    public void clean() {
        if (isMulti()) {
            datas = new ArrayList();
        } else {
            getOperation().getInner().setMoney(0);
        }
    }

    /**
     * Missing Method Documentation
     *
     * @param id Missing Method Parameter Documentation
     *
     * @throws RuntimeException Missing Constructuor Exception Documentation
     */
    public void removeOperation(int id) {
        if (!isMulti()) {
            throw new RuntimeException("Not Supported");
        }

        int index = 0;

        for (Iterator iter = datas.iterator(); iter.hasNext();) {
            OperationData element = (OperationData) iter.next();

            if (element.getId() == id) {
                datas.remove(index);
                return;
            }

            index++;
        }
    }

    /**
     * Missing Method Documentation
     *
     * @return Missing Return Method Documentation
     */
    @Override
	public String toString() {
        StringBuffer s = new StringBuffer();

        for (Iterator iter = datas.iterator(); iter.hasNext();) {
            OperationData element = (OperationData) iter.next();
            s.append(element.toString());

            if (iter.hasNext()) {
                s.append("\n");
            }
        }

        return s.toString();
    }
}
