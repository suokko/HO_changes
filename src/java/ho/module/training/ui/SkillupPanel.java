// %1303949933:hoplugins.trainingExperience.ui%
package ho.module.training.ui;

import ho.core.constants.player.PlayerSkill;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.gui.model.BaseTableModel;
import ho.core.model.HOVerwaltung;
import ho.core.model.ISkillup;
import ho.core.model.Spieler;
import ho.core.util.HelperWrapper;
import ho.module.training.ui.renderer.SkillupTableRenderer;

import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;


/**
 * Panel of past skillups table ("Training History")
 */
public class SkillupPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 57377377617909870L;
	private BaseTableModel tableModel;
    private SkillupTable table;
    private String[] columns = {
                                   HOVerwaltung.instance().getLanguageString("Type"), //$NON-NLS-1$
                                   HOVerwaltung.instance().getLanguageString("Week"), //$NON-NLS-1$
                                   HOVerwaltung.instance().getLanguageString("Season"), //$NON-NLS-1$
    "", "", "" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                               };

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new SkillupPanel object.
     */
    public SkillupPanel() {
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Add a row to the table
     *
     * @param skillup The skillup object to be added
     */
    public void addRow(ISkillup skillup) {
        Vector<Object> v = new Vector<Object>();

        v.add(PlayerSkill.toString(skillup.getType()) + ": " //$NON-NLS-1$
              + HelperWrapper.instance().getNameForSkill(skillup.getValue(), true));
        v.add("" + skillup.getHtWeek()); //$NON-NLS-1$
        v.add("" + skillup.getHtSeason()); //$NON-NLS-1$
        v.add("" + skillup.getTrainType()); //$NON-NLS-1$
        v.add("" + skillup.getDate()); //$NON-NLS-1$
        v.add("" + skillup.getType()); //$NON-NLS-1$
        tableModel.insertRow(0, v);
    }

    /**
     * Populate the table
     *
     * @param player the selected training situation
     */
    public void reload(Spieler player) {
        // empty the table
        tableModel = new BaseTableModel(new Vector<Object>(), new Vector<String>(Arrays.asList(columns)));
        table.setModel(tableModel);

        if (player == null) {
            return;
        }

        // gets calculated past skillups
        for (Iterator<ISkillup> iter = ho.module.training.TrainingPanel.getSkillupManager().getTrainedSkillups().iterator();
             iter.hasNext();) {
            // add it to the table
            addRow(iter.next());
        }

        setColumnWidth(1, 50);
        setColumnWidth(2, 50);
        setColumnWidth(3, 0);
        setColumnWidth(4, 0);
        setColumnWidth(5, 0);
        table.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(0);
        table.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(0);
        table.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(0);
    }

    /**
     * Resize the column
     *
     * @param col column to resize
     * @param width new width
     */
    private void setColumnWidth(int col, int width) {
        table.getTableHeader().getColumnModel().getColumn(col).setWidth(width);
        table.getTableHeader().getColumnModel().getColumn(col).setPreferredWidth(width);
        table.getTableHeader().getColumnModel().getColumn(col).setMaxWidth(200);
        table.getTableHeader().getColumnModel().getColumn(col).setMinWidth(0);
    }

    /**
     * Initialize the object layout
     */
    private void jbInit() {
        Vector<Object> data = new Vector<Object>();

        tableModel = new BaseTableModel(data, new Vector<String>(Arrays.asList(columns)));
        table = new SkillupTable(tableModel);
        table.setDefaultRenderer(Object.class, new SkillupTableRenderer());

        setColumnWidth(1, 30);
        setColumnWidth(2, 30);
        setColumnWidth(3, 0);
        setColumnWidth(4, 0);
        setColumnWidth(5, 0);
        table.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(0);
        table.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(0);
        table.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(0);

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel headerPanel = new ImagePanel();

        headerPanel.setOpaque(false);

        JLabel l = new JLabel(HOVerwaltung.instance().getLanguageString("TrainingHistory"), SwingConstants.CENTER); //$NON-NLS-1$

        l.setOpaque(false);
        headerPanel.add(l, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
