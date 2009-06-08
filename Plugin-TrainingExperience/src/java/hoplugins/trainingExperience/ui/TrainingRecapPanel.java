// %776182880:hoplugins.trainingExperience.ui%
package hoplugins.trainingExperience.ui;

import hoplugins.Commons;
import hoplugins.TrainingExperience;

import hoplugins.commons.ui.BaseTableModel;
import hoplugins.commons.utils.ListUtil;
import hoplugins.commons.utils.PluginProperty;

import hoplugins.trainingExperience.ui.renderer.TrainingRecapRenderer;

import plugins.IFutureTrainingManager;
import plugins.ISkillup;
import plugins.ISpieler;

import java.awt.BorderLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;


/**
 * Recap Panel when future preview of skillups is shown ("Prediction" tab, "Training Recap"
 * table").
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class TrainingRecapPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 7240288702397251461L;

	private BaseTableModel tableModel;

    //private HashMap playerRef;
    private TrainingRecapTable recapTable;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TrainingRecapPanel object.
     */
    public TrainingRecapPanel() {
        super();
        reload();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Reload the panel
     */
    public void reload() {
        jbInit();

        // empty the table
        Vector columns = getColumns();

        //playerRef = new HashMap();
        List trainings = TrainingExperience.getTrainPanel().getFutureTrainings();

        Vector v = Commons.getModel().getAllSpieler();

        List players = new ArrayList();

        for (Iterator iter = v.iterator(); iter.hasNext();) {
            ISpieler player = (ISpieler) iter.next();
            IFutureTrainingManager ftm = Commons.getModel().getFutureTrainingManager(player,
                                                                                     trainings,
                                                                                     TrainingExperience.getStaffPanel()
                                                                                                       .getCoTrainerNumber(),
                                                                                     TrainingExperience.getStaffPanel()
                                                                                                       .getKeeperTrainerNumber(),
                                                                                     TrainingExperience.getStaffPanel()
                                                                                                       .getTrainerLevelNumber());
            List su = ftm.getFutureSkillups();

            // Skip player!
            if (su.size() == 0) {
                continue;
            }

            HashMap maps = new HashMap();

            for (Iterator iterator = su.iterator(); iterator.hasNext();) {
                ISkillup skillup = (ISkillup) iterator.next();

                maps.put(skillup.getHtSeason() + " " + skillup.getHtWeek(), skillup); //$NON-NLS-1$
            }

            Vector row = new Vector();

            row.add(player.getName());
            row.add(player.getAlterWithAgeDaysAsString());

            for (int i = 0; i < IFutureTrainingManager.FUTUREWEEKS; i++) {
                ISkillup s = (ISkillup) maps.get(columns.get(i + 2));

                if (s == null) {
                    row.add(""); //$NON-NLS-1$
                } else {
                    row.add(s.getType() + " " + s.getValue()); //$NON-NLS-1$
                }
            }

            row.add(Integer.toString(player.getSpielerID()));

            //playerRef.put(player.getName(), player.getSpielerID() + "");
            players.add(row);

            //count++;
        }

        // Sort the players
        SortedSet sorted = ListUtil.getSortedSet(players, new TrainingComparator());

        // and add them to the model
        for (Iterator iter = sorted.iterator(); iter.hasNext();) {
            Vector row = (Vector) iter.next();

            tableModel.addRow(row);
        }

        updateUI();
    }

    /**
     * Get Columns name
     *
     * @return List of string
     */
    private Vector getColumns() {
        Vector columns = new Vector();

        columns.add(Commons.getModel().getLanguageString("Spieler")); //$NON-NLS-1$
        columns.add(Commons.getModel().getLanguageString("Alter")); //$NON-NLS-1$

        int actualSeason = Commons.getModel().getBasics().getSeason();
        int actualWeek = Commons.getModel().getBasics().getSpieltag();

        // We are in the middle where season has not been updated!
        try {
            if (Commons.getModel().getXtraDaten().getTrainingDate().after(Commons.getModel()
                                                                                 .getXtraDaten()
                                                                                 .getSeriesMatchDate())) {
                actualWeek++;

                if (actualWeek == 17) {
                    actualWeek = 1;
                    actualSeason++;
                }
            }
        } catch (Exception e1) {
            // Null when first time HO is launched		
        }

        for (int i = 0; i < IFutureTrainingManager.FUTUREWEEKS; i++) {
            // calculate the week and season of the future training
            int week = (actualWeek + i) - 1;
            int season = actualSeason + (week / 16);

            week = (week % 16) + 1;

            columns.add(season + " " + week); //$NON-NLS-1$
        }

        columns.add(Commons.getModel().getLanguageString("ID")); //$NON-NLS-1$

        return columns;
    }

    /**
     * Initialize the GUI
     */
    private void jbInit() {
        removeAll();
        setOpaque(false);
        setLayout(new BorderLayout());

        JPanel panel = Commons.getModel().getGUI().createImagePanel();

        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());

        JLabel title = new JLabel(PluginProperty.getString("Recap"), SwingConstants.CENTER); //$NON-NLS-1$

        title.setOpaque(false);
        panel.add(title, BorderLayout.NORTH);

        Vector columns = getColumns();

        tableModel = new BaseTableModel(new Vector(), columns);

        JTable table = new JTable(tableModel);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        recapTable = new TrainingRecapTable(table, 2);

        recapTable.getScrollTable().setDefaultRenderer(Object.class, new TrainingRecapRenderer());

        JTable scrollTable = recapTable.getScrollTable();

        // Hide the last column
        int lastSTCol = scrollTable.getColumnCount() - 1;

        scrollTable.getTableHeader().getColumnModel().getColumn(lastSTCol).setPreferredWidth(0);
        scrollTable.getTableHeader().getColumnModel().getColumn(lastSTCol).setMinWidth(0);
        scrollTable.getTableHeader().getColumnModel().getColumn(lastSTCol).setMaxWidth(0);

        JTable lockedTable = recapTable.getLockedTable();
        lockedTable.getSelectionModel().addListSelectionListener(new PlayerSelectionListener(scrollTable,
                                                                                             lastSTCol));
        panel.add(recapTable, BorderLayout.CENTER);
        recapTable.getScrollTable().getTableHeader().setReorderingAllowed(false);

        // Add legend panel. 
        panel.add(new TrainingLegendPanel(), BorderLayout.SOUTH);
        add(panel, BorderLayout.CENTER);
    }
}
