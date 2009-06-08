// %1180046389:hoplugins.teamAnalyzer.ui%
package hoplugins.teamAnalyzer.ui;

import hoplugins.Commons;

import hoplugins.commons.ui.sorter.AbstractTableSorter;
import hoplugins.commons.utils.RatingUtil;

import hoplugins.teamAnalyzer.SystemManager;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.TableModel;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class RecapTableSorter extends AbstractTableSorter {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -3606200720032237171L;
	private List skills;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new RecapTableSorter object.
     *
     * @param tableModel TODO Missing Constructuor Parameter Documentation
     */
    public RecapTableSorter(TableModel tableModel) {
        super(tableModel);
        skills = new ArrayList();

        for (int i = 1; i < 21; i++) {
            skills.add(Commons.getModel().getHelper().getNameForBewertung(i, false, false));
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param column TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public Comparator getCustomComparator(int column) {
        if ((column > 4) && (column < 12)) {
            return new Comparator() {
                    @Override
					public boolean equals(Object arg0) {
                        return false;
                    }

                    public int compare(Object arg0, Object arg1) {
                        try {
                            double d1 = RatingUtil.getRating(arg0 + "",
                                                             SystemManager.getConfig()
                                                                          .isNumericRating(),
                                                             SystemManager.getConfig()
                                                                          .isDescriptionRating(),
                                                             skills);
                            double d2 = RatingUtil.getRating(arg1 + "",
                                                             SystemManager.getConfig()
                                                                          .isNumericRating(),
                                                             SystemManager.getConfig()
                                                                          .isDescriptionRating(),
                                                             skills);

                            if (d1 > d2) {
                                return 1;
                            }

                            if (d1 < d2) {
                                return -1;
                            }
                        } catch (Exception e) {
                        }

                        return 0;
                    }
                };
        }

        if ((column > 11) && (column < 16)) {
            return new Comparator() {
                    private DecimalFormat df = new DecimalFormat("###.#");

                    @Override
					public boolean equals(Object arg0) {
                        return false;
                    }

                    public int compare(Object arg0, Object arg1) {
                        try {
                            double d1 = df.parse(arg0 + "").doubleValue();
                            double d2 = df.parse(arg1 + "").doubleValue();

                            if (d1 > d2) {
                                return 1;
                            }

                            if (d1 < d2) {
                                return -1;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return 0;
                    }
                };
        }

        if (column == 16) {
            return new Comparator() {
                    private DecimalFormat df = new DecimalFormat("###.##");

                    @Override
					public boolean equals(Object arg0) {
                        return false;
                    }

                    public int compare(Object arg0, Object arg1) {
                        try {
                            double d1 = df.parse(arg0 + "").doubleValue();
                            double d2 = df.parse(arg1 + "").doubleValue();

                            if (d1 > d2) {
                                return 1;
                            }

                            if (d1 < d2) {
                                return -1;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return 0;
                    }
                };
        }

        if (column == 18) {
            return new Comparator() {
                    @Override
					public boolean equals(Object arg0) {
                        return false;
                    }

                    public int compare(Object arg0, Object arg1) {
                        try {
                            double d1 = RatingUtil.getRating(arg0 + "", false, true, skills);
                            double d2 = RatingUtil.getRating(arg1 + "", false, true, skills);

                            if (d1 > d2) {
                                return 1;
                            }

                            if (d1 < d2) {
                                return -1;
                            }
                        } catch (Exception e) {
                        }

                        return 0;
                    }
                };
        }

        return null;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public boolean hasHeaderLine() {
        return true;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    @Override
	public int minSortableColumn() {
        return 3;
    }
}
