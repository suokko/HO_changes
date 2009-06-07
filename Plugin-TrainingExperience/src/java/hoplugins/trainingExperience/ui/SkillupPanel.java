// %1303949933:hoplugins.trainingExperience.ui%
package hoplugins.trainingExperience.ui;

import hoplugins.Commons;
import hoplugins.TrainingExperience;

import hoplugins.commons.ui.BaseTableModel;
import hoplugins.commons.utils.PluginProperty;

import hoplugins.trainingExperience.constants.Skills;
import hoplugins.trainingExperience.ui.renderer.SkillupTableRenderer;

import plugins.ISkillup;
import plugins.ISpieler;

import java.awt.BorderLayout;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 * Panel of past skillups table ("Training History")
 */
public class SkillupPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    private BaseTableModel tableModel;
    private SkillupTable table;
    private String[] columns = {
                                   PluginProperty.getString("Type"), //$NON-NLS-1$
    PluginProperty.getString("Week"), //$NON-NLS-1$
    Commons.getModel().getResource().getProperty("Season"), //$NON-NLS-1$
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
        Vector v = new Vector();

        v.add(Skills.getSkillDescription(skillup.getType()) + ": " //$NON-NLS-1$
              + Commons.getModel().getHelper().getNameForSkill(skillup.getValue(), true));
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
    public void reload(ISpieler player) {
        // empty the table
        tableModel = new BaseTableModel(new Vector(), new Vector(Arrays.asList(columns)));
        table.setModel(tableModel);

        if (player == null) {
            return;
        }

        // gets calculated past skillups
        for (Iterator iter = TrainingExperience.getSkillupManager().getTrainedSkillups().iterator();
             iter.hasNext();) {
            // add it to the table
            addRow((ISkillup) iter.next());
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
        Vector data = new Vector();

        tableModel = new BaseTableModel(data, new Vector(Arrays.asList(columns)));
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

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel headerPanel = Commons.getModel().getGUI().createImagePanel();

        headerPanel.setOpaque(false);

        JLabel l = new JLabel(PluginProperty.getString("TrainingHistory"), JLabel.CENTER); //$NON-NLS-1$

        l.setOpaque(false);
        headerPanel.add(l, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
