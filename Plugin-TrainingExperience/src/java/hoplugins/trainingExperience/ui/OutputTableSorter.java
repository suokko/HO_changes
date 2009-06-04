// %1126721451729:hoplugins.trainingExperience.ui%
package hoplugins.trainingExperience.ui;

import hoplugins.commons.ui.DefaultTableSorter;
import hoplugins.trainingExperience.ui.bar.VerticalIndicator;
import hoplugins.trainingExperience.ui.model.OutputTableModel;

import javax.swing.table.TableModel;

import java.util.Comparator;

/**
 * Output Table custom sorter
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class OutputTableSorter extends DefaultTableSorter
{
    /**
* Creates a new OutputTableSorter object.
*
* @param tableModel
*/
    public OutputTableSorter(TableModel tableModel)
    {
        super(tableModel);
    }

    /**
* Custom Comparator
*
* @param column column to compare
*
* @return comparator
*/
    public Comparator getCustomComparator(int column)
    {
        if ((column > 2) && (column < 11))
        {
            return new Comparator()
                {
                    public boolean equals(Object arg0)
                    {
                        return false;
                    }

                    public int compare(Object arg0, Object arg1)
                    {
                        VerticalIndicator v1 = (VerticalIndicator) arg0;
                        VerticalIndicator v2 = (VerticalIndicator) arg1;

                        if (v1.getPercentage() > v2.getPercentage())
                        {
                            return 1;
                        }

                        if (v1.getPercentage() < v2.getPercentage())
                        {
                            return -1;
                        }

                        if (v1.getTotal() > v2.getTotal())
                        {
                            return -1;
                        }

                        if (v1.getTotal() < v2.getTotal())
                        {
                            return 1;
                        }

                        return 0;
                    }
                };
        }

        return null;
    }

    /**
* DOCUMENT ME!
*
* @param rowIndex
* @param columnIndex
*
* @return tooltip
*/
    public Object getToolTipAt(int rowIndex, int columnIndex)
    {
        OutputTableModel ttm = (OutputTableModel) tableModel;

        return ttm.getToolTipAt(rowIndex, columnIndex);
    }

    /**
* Fill Table with Data
*/
    public void fillWithData()
    {
        OutputTableModel ttm = (OutputTableModel) tableModel;

        ttm.fillWithData();
    }
}
