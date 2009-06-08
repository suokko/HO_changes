// %1126721330729:hoplugins.transfers.ui%
package hoplugins.transfers.ui;

import hoplugins.Commons;

import hoplugins.commons.ui.DefaultTableSorter;

import hoplugins.transfers.ui.model.TransferTableModel;
import hoplugins.transfers.vo.PlayerTransfer;

import java.awt.BorderLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;


/**
 * Pane to show transfers for your own team.
 *
 * @author <a href=mailto:nethyperon@users.sourceforge.net>Boy van der Werf</a>
 */
public class TeamTransfersPane extends JPanel implements ListSelectionListener {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 5180919662981139849L;
	private JTable transferTable;
    private List transfers = new ArrayList();
    private PlayerDetailPanel playerDetailPanel;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new TeamTransfersPane object.
     */
    public TeamTransfersPane() {
        super(new BorderLayout());

        final JPanel mainPanel = Commons.getModel().getGUI().createImagePanel();
        mainPanel.setLayout(new BorderLayout());
        setOpaque(false);
        add(mainPanel, BorderLayout.CENTER);

        final TableModel model = new TransferTableModel(new ArrayList());
        final TeamTransferSorter sorter = new TeamTransferSorter(model);
        transferTable = new JTable(sorter);

        sorter.setTableHeader(transferTable.getTableHeader());

        final JScrollPane pane = new JScrollPane(transferTable);
        pane.setOpaque(false);
        mainPanel.add(pane, BorderLayout.CENTER);

        refresh(new Vector());
    }

    /**
     * Creates a new TeamTransfersPane object.
     *
     * @param playerDetailPanel TODO Missing Constructuor Parameter Documentation
     */
    public TeamTransfersPane(PlayerDetailPanel playerDetailPanel) {
        this();
        this.playerDetailPanel = playerDetailPanel;

        transferTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        transferTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        transferTable.getSelectionModel().addListSelectionListener(this);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Refresh the information on the panel.
     *
     * @param transfers List of transfers to show.
     */
    public final void refresh(List transfers) {
        this.transfers = transfers;

        final DefaultTableSorter sorter = (DefaultTableSorter) transferTable.getModel();
        sorter.setTableModel(new TransferTableModel(transfers));

        transferTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        transferTable.getColumnModel().getColumn(4).setCellRenderer(new IconCellRenderer());
        transferTable.getColumnModel().getColumn(4).setMaxWidth(20);
        transferTable.getColumnModel().getColumn(5).setPreferredWidth(150);

        transferTable.getColumnModel().getColumn(8).setCellRenderer(new ColorCellRenderer(ColorCellRenderer.GREEN));
        transferTable.getColumnModel().getColumn(8).setPreferredWidth(30);
        transferTable.getColumnModel().getColumn(9).setCellRenderer(new ColorCellRenderer(ColorCellRenderer.GREEN));
        transferTable.getColumnModel().getColumn(9).setPreferredWidth(30);
        transferTable.getColumnModel().getColumn(10).setCellRenderer(new ColorCellRenderer(ColorCellRenderer.GREEN));
        transferTable.getColumnModel().getColumn(10).setPreferredWidth(30);

        transferTable.getColumnModel().getColumn(11).setCellRenderer(new ColorCellRenderer(ColorCellRenderer.YELLOW));
        transferTable.getColumnModel().getColumn(11).setPreferredWidth(30);
        transferTable.getColumnModel().getColumn(12).setCellRenderer(new ColorCellRenderer(ColorCellRenderer.YELLOW));
        transferTable.getColumnModel().getColumn(12).setPreferredWidth(30);
        transferTable.getColumnModel().getColumn(13).setCellRenderer(new ColorCellRenderer(ColorCellRenderer.YELLOW));
        transferTable.getColumnModel().getColumn(13).setPreferredWidth(30);
        transferTable.getColumnModel().getColumn(14).setCellRenderer(new ColorCellRenderer(ColorCellRenderer.YELLOW));
        transferTable.getColumnModel().getColumn(14).setPreferredWidth(30);
        transferTable.getColumnModel().getColumn(15).setCellRenderer(new ColorCellRenderer(ColorCellRenderer.YELLOW));
        transferTable.getColumnModel().getColumn(15).setPreferredWidth(30);
        transferTable.getColumnModel().getColumn(16).setCellRenderer(new ColorCellRenderer(ColorCellRenderer.YELLOW));
        transferTable.getColumnModel().getColumn(16).setPreferredWidth(30);
        transferTable.getColumnModel().getColumn(17).setCellRenderer(new ColorCellRenderer(ColorCellRenderer.YELLOW));
        transferTable.getColumnModel().getColumn(17).setPreferredWidth(30);
        transferTable.getColumnModel().getColumn(18).setCellRenderer(new ColorCellRenderer(ColorCellRenderer.YELLOW));
        transferTable.getColumnModel().getColumn(18).setPreferredWidth(30);
    }

    /** {@inheritDoc} */
    public final void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            final DefaultTableSorter sorter = (DefaultTableSorter) transferTable.getModel();

            if (transferTable.getSelectedRow() >= 0) {
                final int index = sorter.modelIndex(transferTable.getSelectedRow());
                final PlayerTransfer transfer = (PlayerTransfer) this.transfers.get(index);
                this.playerDetailPanel.setPlayer(transfer.getPlayerId(), transfer.getPlayerName());
            } else {
                this.playerDetailPanel.clearPanel();
            }
        }
    }
}
