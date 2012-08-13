// %1126721046041:hoplugins.commons.ui.sorter%
/*
 * Created on 7-apr-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ho.module.transfer.ui.sorter;


/**
 * DOCUMENT ME!
 *
 * @author
 */
class Directive {
    private int column;
    private int direction;

    /**
     * Creates a new Directive object.
     *
     * @param column
     * @param direction
     */
    public Directive(int column, int direction) {
        this.column = column;
        this.direction = direction;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public int getColumn() {
        return column;
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public int getDirection() {
        return direction;
    }
}
