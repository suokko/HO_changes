// %4038744218:hoplugins.teamAnalyzer.ui%
package ho.module.teamAnalyzer.ui;

//import ho.module.teamAnalyzer.SystemManager;
import ho.module.ModuleConfig;
import ho.module.teamAnalyzer.manager.MatchPopulator;
import ho.module.teamAnalyzer.ui.controller.RecapListSelectionListener;
import ho.module.teamAnalyzer.ui.model.UiRecapTableModel;
import ho.module.teamAnalyzer.vo.Match;
import ho.module.teamAnalyzer.vo.MatchDetail;
import ho.module.teamAnalyzer.vo.MatchRating;
import ho.module.teamAnalyzer.vo.TeamLineup;

import java.awt.BorderLayout;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.HelperWrapper;


public class RecapPanel extends JPanel {
    //~ Static fields/initializers -----------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 486150690031160261L;

	/** TODO Missing Parameter Documentation */
    public static final String VALUE_NA = "---"; //$NON-NLS-1$

    /** TODO Missing Parameter Documentation */
    private static final String GOALS_SPACE = " - "; //$NON-NLS-1$

    //~ Instance fields ----------------------------------------------------------------------------
    private JTable table;
    private RecapTableSorter sorter;
    private UiRecapTableModel tableModel;
    private RecapListSelectionListener recapListener = null;
    private String[] columns = {
    		HOVerwaltung.instance().getLanguageString("RecapPanel.Game"), //$NON-NLS-1$
    		HOVerwaltung.instance().getLanguageString("Type"), //$NON-NLS-1$
    		HOVerwaltung.instance().getLanguageString("Ergebnis"), //$NON-NLS-1$
    		HOVerwaltung.instance().getLanguageString("Week"), //$NON-NLS-1$
    		HOVerwaltung.instance().getLanguageString("Season"), //$NON-NLS-1$
    		HOVerwaltung.instance().getLanguageString("MatchMittelfeld"), //$NON-NLS-1$
    		HOVerwaltung.instance().getLanguageString("rechteAbwehrseite"), //$NON-NLS-1$
    		HOVerwaltung.instance().getLanguageString("Abwehrzentrum"), //$NON-NLS-1$
    		HOVerwaltung.instance().getLanguageString("linkeAbwehrseite"), //$NON-NLS-1$
    		HOVerwaltung.instance().getLanguageString("rechteAngriffsseite"), //$NON-NLS-1$
    		HOVerwaltung.instance().getLanguageString("Angriffszentrum"), //$NON-NLS-1$
    		HOVerwaltung.instance().getLanguageString("linkeAngriffsseite"), //$NON-NLS-1$
    		HOVerwaltung.instance().getLanguageString("RecapPanel.Stars"), //$NON-NLS-1$
    		HOVerwaltung.instance().getLanguageString("Gesamtstaerke"), //$NON-NLS-1$
    		HOVerwaltung.instance().getLanguageString("Squad"), //$NON-NLS-1$
    		HOVerwaltung.instance().getLanguageString("SmartSquad"), //$NON-NLS-1$
    "LoddarStats", //$NON-NLS-1$
    HOVerwaltung.instance().getLanguageString("Taktik"), //$NON-NLS-1$
    HOVerwaltung.instance().getLanguageString("Taktikstaerke"), //$NON-NLS-1$
    HOVerwaltung.instance().getLanguageString("RecapPanel.Formation"), //$NON-NLS-1$
    "", //$NON-NLS-1$
    "" //$NON-NLS-1$
    };

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new RecapPanel object.
     */
    public RecapPanel() {
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param lineup TODO Missing Method Parameter Documentation
     */
    public void reload(TeamLineup lineup) {
        // Empty model
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }

        // table.updateUI();
        MatchRating averageRating = null;
        double stars = 0;

        if (lineup != null) {
            averageRating = lineup.getRating();
            stars = lineup.getStars();
        }

        List<MatchDetail> list = MatchPopulator.getAnalyzedMatch();
        Vector<Object> rowData;

        if (list.size() > 1) {
            rowData = new Vector<Object>();
            rowData.add(HOVerwaltung.instance().getLanguageString("Durchschnitt")); //$NON-NLS-1$
            rowData.add(VALUE_NA);
            rowData.add(VALUE_NA);
            rowData.add(VALUE_NA);
            rowData.add(VALUE_NA);
            setRating(rowData, averageRating);

            DecimalFormat df = new DecimalFormat("###.#"); //$NON-NLS-1$

            rowData.add(df.format(stars));
            rowData.add(df.format(averageRating.getHatStats()));
            rowData.add(df.format(averageRating.getSquad()));
            rowData.add(df.format(averageRating.getSquad() / stars));
            rowData.add(VALUE_NA);
            rowData.add(VALUE_NA);
            rowData.add(VALUE_NA);
            rowData.add(VALUE_NA);
            rowData.add(""); //$NON-NLS-1$
            rowData.add(""); //$NON-NLS-1$
            tableModel.addRow(rowData);
            table.getSelectionModel().setSelectionInterval(0, 0);
        }

        for (Iterator<MatchDetail> iter = list.iterator(); iter.hasNext();) {
            MatchDetail matchDetail = (MatchDetail) iter.next();

            rowData = new Vector<Object>();

            Match match = matchDetail.getMatchDetail();

            int matchType = match.getMatchType();
            boolean isHomeMatch = match.isHome();

            // Columns 0-2
            if (isHomeMatch) {
                rowData.add(match.getAwayTeam());
                rowData.add(HelperWrapper.instance().getImageIcon4Spieltyp(matchType));
                rowData.add(match.getHomeGoals() + GOALS_SPACE + match.getAwayGoals());
            } else {
                rowData.add("* " + match.getHomeTeam()); //$NON-NLS-1$
                rowData.add(HelperWrapper.instance().getImageIcon4Spieltyp(matchType));
                rowData.add(match.getAwayGoals() + GOALS_SPACE + match.getHomeGoals());
            }

            // Columns 3 & 4
            rowData.add(match.getWeek() + ""); //$NON-NLS-1$
            rowData.add(match.getSeason() + ""); //$NON-NLS-1$

            // Columns 5-11
            setRating(rowData, matchDetail.getRating());

            DecimalFormat df = new DecimalFormat("###.#"); //$NON-NLS-1$

            // Columns 12-15
            rowData.add(df.format(matchDetail.getStars()));
            rowData.add(df.format(matchDetail.getRating().getHatStats()));
            rowData.add(df.format(matchDetail.getRating().getSquad()));
            rowData.add(df.format(matchDetail.getRating().getSquad() / matchDetail.getStars()));

            DecimalFormat df2 = new DecimalFormat("###.##"); //$NON-NLS-1$

            // Columns 16-17
            rowData.add(df2.format(matchDetail.getRating().getLoddarStats(matchDetail.getTacticCode(),
                                                                          matchDetail
                                                                          .getTacticLevel())));
            rowData.add(HelperWrapper.instance().getNameForTaktik(matchDetail.getTacticCode()));

            // Column 18
            if (matchDetail.getTacticCode() == 0) {
                rowData.add(VALUE_NA);
            } else {
                rowData.add(HelperWrapper.instance().getNameForSkill(matchDetail
                                                                           .getTacticLevel(), false));
            }

            // Columns 19-21
            rowData.add(matchDetail.getFormation());
            rowData.add(new Integer(matchType));
            rowData.add(new Boolean(isHomeMatch));

            tableModel.addRow(rowData);
        }

        if (list.size() == 0) {
            rowData = new Vector<Object>();
            rowData.add(HOVerwaltung.instance().getLanguageString("RecapPanel.NoMatch")); //$NON-NLS-1$
            tableModel.addRow(rowData);
        }

        setColumnWidth(0, 100);
        setColumnWidth(1, 20);
        setColumnWidth(2, 40);
        setColumnWidth(3, 50);
        setColumnWidth(4, 50);

        if (ModuleConfig.instance().getBoolean(ModuleConfig.isStars)) {
            setColumnWidth(12, 50);
        } else {
            setColumnInvisible(12);
        }

        if (ModuleConfig.instance().getBoolean(ModuleConfig.isTotalStrength)) {
            setColumnWidth(13, 50);
        } else {
            setColumnInvisible(13);
        }

        if (ModuleConfig.instance().getBoolean(ModuleConfig.isSquad)) {
            setColumnWidth(14, 50);
        } else {
            setColumnInvisible(14);
        }

        if (ModuleConfig.instance().getBoolean(ModuleConfig.isSmartSquad)) {
            setColumnWidth(15, 50);
        } else {
            setColumnInvisible(15);
        }

        if (ModuleConfig.instance().getBoolean(ModuleConfig.isLoddarStats)) {
            setColumnWidth(16, 50);
        } else {
            setColumnInvisible(16);
        }

        // Hide 'match type' and 'is home match?' columns.
        setColumnInvisible(20);
        setColumnInvisible(21);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param col TODO Missing Method Parameter Documentation
     */
    private void setColumnInvisible(int col) {
        table.getTableHeader().getColumnModel().getColumn(col).setWidth(0);
        table.getTableHeader().getColumnModel().getColumn(col).setPreferredWidth(0);
        table.getTableHeader().getColumnModel().getColumn(col).setMaxWidth(0);
        table.getTableHeader().getColumnModel().getColumn(col).setMinWidth(0);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param col TODO Missing Method Parameter Documentation
     * @param width TODO Missing Method Parameter Documentation
     */
    private void setColumnWidth(int col, int width) {
        table.getTableHeader().getColumnModel().getColumn(col).setWidth(width);
        table.getTableHeader().getColumnModel().getColumn(col).setPreferredWidth(width);
        table.getTableHeader().getColumnModel().getColumn(col).setMaxWidth(200);
        table.getTableHeader().getColumnModel().getColumn(col).setMinWidth(0);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param row TODO Missing Method Parameter Documentation
     * @param rating TODO Missing Method Parameter Documentation
     */
    private void setRating(Vector<Object> row, MatchRating rating) {
        if (rating == null) {
            for (int i = 0; i < 7; i++) {
                row.add(""); //$NON-NLS-1$
            }

            return;
        }

        row.add(getRating((int) rating.getMidfield()));
        row.add(getRating((int) rating.getRightDefense()));
        row.add(getRating((int) rating.getCentralDefense()));
        row.add(getRating((int) rating.getLeftDefense()));
        row.add(getRating((int) rating.getRightAttack()));
        row.add(getRating((int) rating.getCentralAttack()));
        row.add(getRating((int) rating.getLeftAttack()));
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param rating TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    private String getRating(int rating) {
        return RatingUtil.getRating(rating, 
        		ModuleConfig.instance().getBoolean(ModuleConfig.isNumericRating),
        		ModuleConfig.instance().getBoolean(ModuleConfig.isDescriptionRating));
    }

    /**
     * TODO Missing Method Documentation
     */
    private void jbInit() {
        Vector<Object> data = new Vector<Object>();

        tableModel = new UiRecapTableModel(data, new Vector<String>(Arrays.asList(columns)));

        sorter = new RecapTableSorter(tableModel);
        table = new JTable(sorter);
        sorter.setTableHeader(table.getTableHeader());

        // Set up tool tips for column headers.
        table.getTableHeader().setToolTipText(HOVerwaltung.instance().getLanguageString("RecapPanel.Tooltip")); //$NON-NLS-1$

        table.setDefaultRenderer(Object.class, new RecapTableRenderer());
        table.setDefaultRenderer(ImageIcon.class, new RecapTableRenderer());
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ListSelectionModel rowSM = table.getSelectionModel();
        recapListener = new RecapListSelectionListener(sorter, tableModel);
        rowSM.addListSelectionListener(recapListener);
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scrollPane);
    }

    public String getSelectedTacticType() {
    	return recapListener.getSelectedTacticType();
    }

    public String getSelectedTacticSkill() {
    	return recapListener.getSelectedTacticSkill();
    }
}
