// %1126721451963:hoplugins.trainingExperience.ui%
package ho.module.training.ui;

import ho.module.training.DividerDAO;
import ho.module.training.ui.comp.DividerListener;
import ho.module.training.ui.comp.FutureSettingPanel;
import ho.module.training.ui.model.FutureTrainingsTableModel;
import ho.module.training.ui.model.PastTrainingsTableModel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;

import plugins.IFutureTrainingWeek;
import plugins.ITrainingWeek;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.model.HOVerwaltung;


/**
 * Panel where past and future training are shown
 */
public class TrainingPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 5456485854278251422L;
	/** Future trainings table model */
    private FutureTrainingsTableModel futureModel;
    private JTable futureTable;

    /** Past trainings table model */
    private PastTrainingsTableModel oldTableModel;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TrainingPanel object.
     */
    public TrainingPanel() {
        super();
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Returns the list of future trainings
     *
     * @return future trainings list
     */
    public List<IFutureTrainingWeek> getFutureTrainings() {
        return futureModel.getTrainingsData();
    }

    /**
     * Returns the list of past trainings
     *
     * @return past trainings list
     */
    public Vector<ITrainingWeek> getOldTrainings() {
        return oldTableModel.getTrainingsData();
    }

    /**
     * Populate the table is called everytime a refresh command is issued
     */
    public void reload() {
        oldTableModel.populate();
        futureModel.populate();
        updateUI();
    }

    /**
     * Initialize the object layout
     */
    private void jbInit() {

        // create and populate the old training table
        oldTableModel = new PastTrainingsTableModel();

        JTable oldTable = new TrainingTable(oldTableModel);

        oldTableModel.populate();

        JScrollPane scrollPane = new JScrollPane(oldTable);

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //create and populate the future training table
        futureModel = new FutureTrainingsTableModel();
        futureTable = new TrainingTable(futureModel);
        futureModel.populate();

        JScrollPane scrollPane2 = new JScrollPane(futureTable);

        scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Organize the panel layout
        JPanel panel2 = new ImagePanel();

        panel2.setLayout(new BorderLayout());
        panel2.setOpaque(false);

        JLabel l = new JLabel(HOVerwaltung.instance().getLanguageString("FutureTrainings"), SwingConstants.CENTER); //$NON-NLS-1$

        l.setOpaque(false);

        JButton button = new JButton(HOVerwaltung.instance().getLanguageString("SetAll")); //$NON-NLS-1$

        button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    TableCellEditor editor = futureTable.getCellEditor();

                    if (editor != null) {
                        editor.stopCellEditing();
                    }

                    JOptionPane.showMessageDialog((JFrame)getTopLevelAncestor(),
                                                  new FutureSettingPanel(futureModel),
                                                  HOVerwaltung.instance().getLanguageString("SetAll"), //$NON-NLS-1$
                                                  JOptionPane.PLAIN_MESSAGE);
                }
            });
        panel2.add(button, BorderLayout.EAST);
        panel2.add(l, BorderLayout.CENTER);

        JSplitPane pane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel2, scrollPane2);

        pane2.setDividerLocation(25);
        pane2.setDividerSize(0);

        JSplitPane pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, pane2);

        pane.setDividerLocation(DividerDAO.getDividerPosition("TrainingDivider")); //$NON-NLS-1$
        pane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
                                       new DividerListener("TrainingDivider")); //$NON-NLS-1$
        pane.setDividerSize(1);
        setLayout(new BorderLayout());
        setOpaque(false);
        add(pane, BorderLayout.CENTER);
    }
}
