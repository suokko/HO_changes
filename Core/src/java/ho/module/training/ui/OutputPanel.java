// %3839090226:hoplugins.trainingExperience.ui%
package ho.module.training.ui;



import ho.module.training.ui.model.OutputTableModel;
import ho.module.training.ui.renderer.OutputTableRenderer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import de.hattrickorganizer.gui.RefreshManager;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.logik.TrainingsManager;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.HelperWrapper;


/**
 * The Panel where the main training table is shown ("Training").
 *
 * <p>
 * TODO Costomize to show only players that received training?
 * </p>
 *
 * <p>
 * TODO Maybe i want to test for players that haven't received trainings to preview effect of
 * change of training.
 * </p>
 *
 * @author <a href=mailto:draghetto@users.sourceforge.net>Massimiliano Amato</a>
 */
public class OutputPanel extends JPanel {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 7955126207696897546L;
	private JPanel m_jpPanel;
    private JTable m_jtOutputTable;
    private OutputTableSorter sorter;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new OutputPanel object.
     */
    public OutputPanel() {
        super();
        jbInit();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * update the panel with the new value
     */
    public void reload() {
        OutputTableSorter otm = (OutputTableSorter) m_jtOutputTable.getModel();

        otm.fillWithData();
    }

    /**
     * Import a match from Hattrick
     */
    private void import_matches() {
        String input = JOptionPane.showInputDialog(HOVerwaltung.instance().getLanguageString("GameID")); //$NON-NLS-1$

        try {
        	if (input != null) input = input.trim();
            Integer matchID = new Integer(input);

            if (HelperWrapper.instance().isUserMatch(input)) {
                if (HelperWrapper.instance().downloadMatchData(matchID.intValue())) {
                	HelperWrapper.instance().showMessage(null, HOVerwaltung.instance().getLanguageString("MatchImported"), //$NON-NLS-1$
                    		HOVerwaltung.instance().getLanguageString("ImportOK"), //$NON-NLS-1$
                                                  1); 
                                                  RefreshManager.instance().doRefresh();
                }
            } else {
                HelperWrapper.instance().showMessage(null, HOVerwaltung.instance().getLanguageString("NotUserMatch"), //$NON-NLS-1$
                		HOVerwaltung.instance().getLanguageString("ImportError"), 1); //$NON-NLS-1$ 
            }
        } catch (Exception e) {
        	HelperWrapper.instance().showMessage(null, HOVerwaltung.instance().getLanguageString("MatchNotImported"), //$NON-NLS-1$
            		HOVerwaltung.instance().getLanguageString("ImportError"), 1); //$NON-NLS-1$ 
        }
    }

    /**
     * Initialize the object layout
     */
    private void jbInit() {

        m_jpPanel = new ImagePanel();
        m_jpPanel.setLayout(new BorderLayout());

        OutputTableModel outputTableModel = new OutputTableModel();

        sorter = new OutputTableSorter(outputTableModel);
        m_jtOutputTable = new OutputTable(sorter);
        m_jtOutputTable.getTableHeader().setReorderingAllowed(false);
        sorter.setTableHeader(m_jtOutputTable.getTableHeader());
        m_jtOutputTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m_jtOutputTable.setDefaultRenderer(Object.class, new OutputTableRenderer());

        ListSelectionModel rowSM = m_jtOutputTable.getSelectionModel();

        rowSM.addListSelectionListener(new PlayerSelectionListener(m_jtOutputTable, 11));

        for (int i = 0; i < m_jtOutputTable.getColumnCount(); i++) {
            TableColumn column = m_jtOutputTable.getColumnModel().getColumn(i);

            switch (i) {
                case 0:
                    column.setPreferredWidth(150);
                    break;

                case 1:
                    column.setPreferredWidth(43);
                    break;

                case 2:
                    column.setPreferredWidth(100);
                    break;

                default:
                    column.setPreferredWidth(70);
            }
        }

        // Hide column 11 (playerId)
        m_jtOutputTable.getTableHeader().getColumnModel().getColumn(11).setPreferredWidth(0);
        m_jtOutputTable.getTableHeader().getColumnModel().getColumn(11).setMinWidth(0);
        m_jtOutputTable.getTableHeader().getColumnModel().getColumn(11).setMaxWidth(0);
        m_jtOutputTable.setAutoResizeMode(0);
        m_jtOutputTable.setPreferredScrollableViewportSize(new Dimension(500, 70));

        JScrollPane scrollPane = new JScrollPane(m_jtOutputTable);

        m_jpPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel m_jpPanel2 = new JPanel();

        m_jpPanel2.setLayout(new BorderLayout());

        JButton p_JB_berechne = new JButton(HOVerwaltung.instance().getLanguageString("Calculate")); //$NON-NLS-1$

        p_JB_berechne.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                	TrainingsManager.instance().recalcSubskills(true);
                    reload();
                    ho.module.training.TrainingPanel.getTabbedPanel().getRecap().reload();
                }
            });
        m_jpPanel2.add(p_JB_berechne, BorderLayout.CENTER);

        JButton p_JB_import = new JButton(HOVerwaltung.instance().getLanguageString("ImportMatch")); //$NON-NLS-1$

        p_JB_import.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    import_matches();
                }
            });
        m_jpPanel2.add(p_JB_import, BorderLayout.WEST);

        m_jpPanel.add(m_jpPanel2, BorderLayout.NORTH);
        setLayout(new BorderLayout());
        add(m_jpPanel, BorderLayout.CENTER);
    }
}
