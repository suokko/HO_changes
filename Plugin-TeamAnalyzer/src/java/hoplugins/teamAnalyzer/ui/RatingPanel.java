// %898728795:hoplugins.teamAnalyzer.ui%
package hoplugins.teamAnalyzer.ui;

import hoplugins.Commons;
import hoplugins.commons.utils.PluginProperty;
import hoplugins.commons.utils.RatingUtil;
import hoplugins.teamAnalyzer.SystemManager;
import hoplugins.teamAnalyzer.ui.model.UiRatingTableModel;
import hoplugins.teamAnalyzer.ui.renderer.RatingTableCellRenderer;
import hoplugins.teamAnalyzer.vo.TeamLineup;

import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class RatingPanel extends JPanel {
	private static final long serialVersionUID = -1086256822169689318L;

	//~ Instance fields ----------------------------------------------------------------------------

	private JTable table;
    private UiRatingTableModel tableModel;
    private String[] columns = {
                                   PluginProperty.getString("RatingPanel.Area"),
                                   Commons.getModel().getLanguageString("Bewertung"),
                                   Commons.getModel().getLanguageString("Differenz_kurz"),
                                   PluginProperty.getString("TeamAnalyzer.RatingPanel.Relative")
                               }; //$NON-NLS-1$

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new RatingPanel object.
     */
    public RatingPanel() {
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param lineup TODO Missing Method Parameter Documentation
     */
    public void reload(TeamLineup lineup) {
        tableModel = new UiRatingTableModel(new Vector<Object>(), new Vector<String>(Arrays.asList(columns)));
        table.setModel(tableModel);

        if ((lineup == null) || (!SystemManager.getConfig().isLineup())) {
            return;
        }

        TeamLineupData myTeam = SystemManager.getPlugin().getMainPanel().getMyTeamLineupPanel();
        TeamLineupData opponentTeam = SystemManager.getPlugin().getMainPanel()
                                                   .getOpponentTeamLineupPanel();

        tableModel.addRow(getRow(Commons.getModel().getLanguageString("MatchMittelfeld"),
                                 myTeam.getMidfield(), opponentTeam.getMidfield()));
        tableModel.addRow(getRow(Commons.getModel().getLanguageString("rechteAbwehrseite"),
                                 myTeam.getRightDefence(), opponentTeam.getLeftAttack()));
        tableModel.addRow(getRow(Commons.getModel().getLanguageString("Abwehrzentrum"),
                                 myTeam.getMiddleDefence(), opponentTeam.getMiddleAttack()));
        tableModel.addRow(getRow(Commons.getModel().getLanguageString("linkeAbwehrseite"),
                                 myTeam.getLeftDefence(), opponentTeam.getRightAttack()));
        tableModel.addRow(getRow(Commons.getModel().getLanguageString("rechteAngriffsseite"),
                                 myTeam.getRightAttack(), opponentTeam.getLeftDefence()));
        tableModel.addRow(getRow(Commons.getModel().getLanguageString("Angriffszentrum"),
                                 myTeam.getMiddleAttack(), opponentTeam.getMiddleDefence()));
        tableModel.addRow(getRow(Commons.getModel().getLanguageString("linkeAngriffsseite"),
                                 myTeam.getLeftAttack(), opponentTeam.getRightDefence()));

        table.getTableHeader().getColumnModel().getColumn(0).setWidth(130);
        table.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(130);
        table.getTableHeader().getColumnModel().getColumn(1).setWidth(100);
        table.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(100);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param rating TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private String getRating(int rating) {
        return RatingUtil.getRating(rating, SystemManager.getConfig().isNumericRating(),
                                    SystemManager.getConfig().isDescriptionRating(),
                                    Commons.getModel());
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param label TODO Missing Method Parameter Documentation
     * @param myRating TODO Missing Method Parameter Documentation
     * @param opponentRating TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private Vector<Object> getRow(String label, double myRating, double opponentRating) {
        Vector<Object> rowData = new Vector<Object>();

        rowData.add(label); //$NON-NLS-1$
        rowData.add("" + getRating((int) myRating));

        int diff = (int) myRating - (int) opponentRating;

        double relativeVal;
        if (myRating != 0 || opponentRating != 0)
        	relativeVal = Commons.getModel().getHelper().round(myRating/(myRating+opponentRating),2);
        else
        	relativeVal = 0;
        
//        System.out.println ("mR="+myRating+", oR="+opponentRating+", rV="+relativeVal);
        String relValString = (int)(relativeVal * 100) + "%";
        
        // Add a character indicating more or less than 50%
        // will be used in RatingTableCellRenderer to set the foreground color
        if (relativeVal > 0.5)
        	relValString = "+" + relValString;
        else if (relativeVal < 0.5)
        	relValString = "-" + relValString;

        // Add difference as icon
        rowData.add(Commons.getModel().getHelper().getImageIcon4Veraenderung(diff));
        // Add relative difference [%]
        rowData.add(relValString);

        return rowData;
    }

    /**
     * TODO Missing Method Documentation
     */
    private void jbInit() {
        Vector<Object> data = new Vector<Object>();

        //JPanel main = Commons.getModel().getGUI().createImagePanel();
        tableModel = new UiRatingTableModel(data, new Vector<String>(Arrays.asList(columns)));
        table = new JTable(tableModel);

        table.setDefaultRenderer(Object.class, new RatingTableCellRenderer());

        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //main.add(scrollPane);
        add(scrollPane);
    }
}
