// %3525181034:hoplugins.trainingExperience.ui%
package hoplugins.trainingExperience.ui;

import hoplugins.Commons;

import hoplugins.commons.utils.PluginProperty;

import hoplugins.trainingExperience.dao.EffectDAO;
import hoplugins.trainingExperience.ui.model.EffectTableModel;
import hoplugins.trainingExperience.ui.renderer.SkillupsTableCellRenderer;
import hoplugins.trainingExperience.vo.TrainWeekEffect;

import java.awt.BorderLayout;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;


/**
 * Shows a table with training effet.
 *
 * @author NetHyperon
 */
public class EffectPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 6139712209582341384L;
	private JTable effectTable = new JTable();

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new AnalyzerPanel object.
     */
    public EffectPanel() {
        super();
        jbInit();
        reload();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Reload data and redraw panel.
     */
    public void reload() {
        EffectDAO.reload();
        setEffectModel(EffectDAO.getTrainEffect());

        updateUI();
    }

    /**
     * Sets the model for effect table.
     *
     * @param values List of values
     */
    private void setEffectModel(List<TrainWeekEffect> values) {
        effectTable.setModel(new EffectTableModel(values));
        effectTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        effectTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        effectTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        effectTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        effectTable.getColumnModel().getColumn(7).setPreferredWidth(25);
        effectTable.getColumnModel().getColumn(7).setCellRenderer(new SkillupsTableCellRenderer());

        // Hide column 8
        effectTable.getTableHeader().getColumnModel().getColumn(8).setPreferredWidth(0);
        effectTable.getTableHeader().getColumnModel().getColumn(8).setMinWidth(0);
        effectTable.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(0);
    }

    /**
     * Initialize panel.
     */
    private void jbInit() {
        setOpaque(false);
        setLayout(new BorderLayout());

        JPanel p = Commons.getModel().getGUI().createImagePanel();

        p.setOpaque(false);
        p.setLayout(new BorderLayout());

        JLabel label = new JLabel(PluginProperty.getString("TAB_EFFECT"), SwingConstants.CENTER); //$NON-NLS-1$

        label.setOpaque(false);
        p.add(label, BorderLayout.NORTH);

        JPanel mainpanel = Commons.getModel().getGUI().createImagePanel();

        mainpanel.setLayout(new BorderLayout());

        JScrollPane effectPane = new JScrollPane(effectTable);

        effectPane.setOpaque(false);
        mainpanel.add(effectPane, BorderLayout.CENTER);

        p.add(mainpanel, BorderLayout.CENTER);
        add(p, BorderLayout.CENTER);

        // Add legend panel. 
        p.add(new TrainingLegendPanel(), BorderLayout.SOUTH);
        add(p, BorderLayout.CENTER);
    }
}
