// %1126721330666:hoplugins.transfers.ui%
package hoplugins.transfers.ui;

import hoplugins.Commons;

import hoplugins.commons.ui.DefaultTableSorter;
import hoplugins.commons.ui.info.clearthought.layout.TableLayout;
import hoplugins.commons.ui.info.clearthought.layout.TableLayoutConstants;
import hoplugins.commons.utils.PluginProperty;

import hoplugins.transfers.dao.BookmarkDAO;
import hoplugins.transfers.dao.TransfersDAO;
import hoplugins.transfers.ui.model.TeamBookmarksTableModel;
import hoplugins.transfers.vo.Bookmark;
import hoplugins.transfers.vo.TransferTotals;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;


/**
 * Pane to show bookmarks.
 *
 * @author <a href=mailto:nethyperon@users.sourceforge.net>Boy van der Werf</a>
 */
public class TeamBookmarkPane extends JPanel implements ListSelectionListener {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 8570913357830649878L;
	private ButtonModel spinSeason;
    private JLabel amountTransfers = new JLabel("", SwingConstants.RIGHT);
    private JLabel amountTransfersIn = new JLabel("", SwingConstants.RIGHT);
    private JLabel amountTransfersOut = new JLabel("", SwingConstants.RIGHT);
    private JSpinner spinner = new JSpinner();
    private JTable bookmarkTable;
    private List bookmarks;
    private List transfers;
    private TeamTransfersPane transferPane;
    private TotalsPanel pricePanel;
    private TotalsPanel tsiPanel;
    private int teamid = 240416;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates an instance of the TeamBookmarkPane
     */
    public TeamBookmarkPane() {
        super(new BorderLayout());

        // Create side panel
        final double[][] sizes = {
                               {300},
                               {
                                   TableLayoutConstants.PREFERRED, 10, TableLayoutConstants.PREFERRED,
                                   TableLayoutConstants.FILL, TableLayoutConstants.PREFERRED, TableLayoutConstants.PREFERRED,
                                   TableLayoutConstants.PREFERRED
                               }
                           };
        final JPanel sidePanel = Commons.getModel().getGUI().createImagePanel();
        sidePanel.setLayout(new TableLayout(sizes));
        sidePanel.setOpaque(false);

        final JPanel filterPanel = Commons.getModel().getGUI().createImagePanel();
        filterPanel.setLayout(new TableLayout(new double[][]{
                                                  {
                                                      10, TableLayoutConstants.PREFERRED, 50,
                                                      TableLayoutConstants.FILL, 10
                                                  },
                                                  {10, TableLayoutConstants.PREFERRED, TableLayoutConstants.PREFERRED}
                                              }));

        final JRadioButton rb1 = new JRadioButton(PluginProperty.getString("AllSeason")); //$NON-NLS-1$
        rb1.setFocusable(false);
        rb1.setOpaque(false);

        final JRadioButton rb2 = new JRadioButton(PluginProperty.getString("Season")); //$NON-NLS-1$
        spinSeason = rb2.getModel();
        rb2.setFocusable(false);
        rb2.setOpaque(false);

        if (Commons.getModel().getBasics().getSeason() > 0) {
            spinner.setModel(new SpinnerNumberModel(Commons.getModel().getBasics().getSeason(), 1,
                                                    Commons.getModel().getBasics().getSeason(), 1));
        } else {
            rb2.setEnabled(false);
            spinner.setModel(new SpinnerNumberModel());
        }

        spinner.setFocusable(false);
        spinner.setEnabled(false);
        spinner.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    refresh();
                }
            });

        rb1.setSelected(true);
        rb1.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    spinner.setEnabled(false);
                    refresh();
                }
            });
        rb2.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    spinner.setEnabled(true);
                    refresh();
                }
            });

        filterPanel.add(rb1, "1, 1, 2, 1"); //$NON-NLS-1$
        filterPanel.add(rb2, "1, 2"); //$NON-NLS-1$
        filterPanel.add(spinner, "2, 2"); //$NON-NLS-1$

        final ButtonGroup bg = new ButtonGroup();
        bg.add(rb1);
        bg.add(rb2);

        final JPanel amountPanel = Commons.getModel().getGUI().createImagePanel();
        amountPanel.setLayout(new TableLayout(new double[][]{
                                                  {.25, 75, 25, .5, 50, 25, .25},
                                                  {10, 20, 20}
                                              }));

        final JLabel amTrans = new JLabel(PluginProperty.getString("Transfers") + ":", SwingConstants.LEFT);
        final JLabel amTransIn = new JLabel(PluginProperty.getString("In") + ":", SwingConstants.LEFT);
        amTransIn.setIcon(Icon.IN);

        final JLabel amTransOut = new JLabel(PluginProperty.getString("Out") + ":", SwingConstants.LEFT);
        amTransOut.setIcon(Icon.OUT);
        amountPanel.add(amTrans, "1, 1");
        amountPanel.add(amountTransfers, "2, 1");
        amountPanel.add(amTransIn, "4, 1");
        amountPanel.add(amountTransfersIn, "5, 1");
        amountPanel.add(amTransOut, "4, 2");
        amountPanel.add(amountTransfersOut, "5, 2");

        pricePanel = new TotalsPanel(PluginProperty.getString("Price"),
                                     Commons.getModel().getXtraDaten().getCurrencyName()); 
        tsiPanel = new TotalsPanel(PluginProperty.getString("TSI")); //$NON-NLS-1$

        final TableModel model = new TeamBookmarksTableModel(new Vector());
        final DefaultTableSorter sorter = new DefaultTableSorter(model);
        bookmarkTable = new JTable(sorter);
        sorter.setTableHeader(bookmarkTable.getTableHeader());

        final JScrollPane bookmarkPanel = new JScrollPane(bookmarkTable);
        bookmarkPanel.setOpaque(false);

        bookmarkTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookmarkTable.getSelectionModel().addListSelectionListener(this);
        bookmarkTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        bookmarkTable.getColumnModel().getColumn(1).setPreferredWidth(150);

        sidePanel.add(filterPanel, "0, 0");
        sidePanel.add(new JSeparator(), "0, 2");
        sidePanel.add(bookmarkPanel, "0, 3");
        sidePanel.add(amountPanel, "0, 4");
        sidePanel.add(pricePanel, "0, 5");
        sidePanel.add(tsiPanel, "0, 6");
        sidePanel.setMinimumSize(new Dimension(285, 360));

        // Create the top panel and add it to the split pane
        final JPanel mainPanel = Commons.getModel().getGUI().createImagePanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        transferPane = new TeamTransfersPane();
        mainPanel.add(transferPane, BorderLayout.CENTER);
        mainPanel.add(sidePanel, BorderLayout.WEST);

        refresh();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Refreshes the information on the panel.
     */
    public final void refresh() {
        if (this.teamid > 0) {
            if (spinSeason.isSelected()) {
                final SpinnerNumberModel model = (SpinnerNumberModel) this.spinner.getModel();
                this.transfers = TransfersDAO.getTransfers(teamid, model.getNumber().intValue(),
                                                           true, true);
            } else {
                this.transfers = TransfersDAO.getTransfers(teamid, 0, true, true);
            }
        } else {
            this.transfers = new Vector();
        }

        this.bookmarks = BookmarkDAO.getBookmarks(Bookmark.TEAM);

        final DefaultTableSorter sorter = (DefaultTableSorter) bookmarkTable.getModel();
        sorter.setTableModel(new TeamBookmarksTableModel(transfers));

        bookmarkTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        bookmarkTable.getColumnModel().getColumn(1).setPreferredWidth(150);

        final TransferTotals totals = TransferTotals.calculateTotals(transfers);
        pricePanel.setValues(totals.getBuyPriceTotal(), totals.getBuyPriceAvg(),
                             totals.getSellPriceTotal(), totals.getSellPriceAvg());
        amountTransfers.setText(Integer.toString(totals.getAmountSell() + totals.getAmountBuy()));
        amountTransfersIn.setText(Integer.toString(totals.getAmountBuy()));
        amountTransfersOut.setText(Integer.toString(totals.getAmountSell()));
        tsiPanel.setValues(totals.getBuyTsiTotal(), totals.getBuyTsiAvg(),
                           totals.getSellTsiTotal(), totals.getSellTsiAvg());
        pricePanel.revalidate();

        transferPane.refresh(this.transfers);
    }

    /** {@inheritDoc} */
    public final void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            final DefaultTableSorter sorter = (DefaultTableSorter) bookmarkTable.getModel();
            final int index = sorter.modelIndex(bookmarkTable.getSelectedRow());

            if (index >= 0) {
                final Bookmark bookmark = (Bookmark) this.bookmarks.get(index);
                this.teamid = bookmark.getId();
            } else {
                this.teamid = 0;
            }
        }
    }
}
