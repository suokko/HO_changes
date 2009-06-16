// %1126721330323:hoplugins.transfers.ui%
package hoplugins.transfers.ui;

import hoplugins.Commons;

import hoplugins.commons.ui.info.clearthought.layout.TableLayout;
import hoplugins.commons.ui.info.clearthought.layout.TableLayoutConstants;
import hoplugins.commons.utils.PluginProperty;

import hoplugins.transfers.dao.DividerDAO;
import hoplugins.transfers.dao.TransfersDAO;
import hoplugins.transfers.ui.controller.DividerListener;
import hoplugins.transfers.vo.PlayerTransfer;
import hoplugins.transfers.vo.TransferTotals;

import java.awt.BorderLayout;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * Pane to show transfer histry information for your own team.
 *
 * @author <a href=mailto:nethyperon@users.sourceforge.net>Boy van der Werf</a>
 */
public class HistoryPane extends JSplitPane {
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = 5465572622813044852L;
	private ButtonModel spinSeason;
    private JLabel amountTransfers = new JLabel("", SwingConstants.RIGHT);
    private JLabel amountTransfersIn = new JLabel("", SwingConstants.RIGHT);
    private JLabel amountTransfersOut = new JLabel("", SwingConstants.RIGHT);
    private JSpinner spinner = new JSpinner();
    private List<PlayerTransfer> transfers;
    private PlayerDetailPanel playerDetailPanel = new PlayerDetailPanel();
    private TeamTransfersPane transferPane;
    private TotalsPanel pricePanel;
    private TotalsPanel tsiPanel;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates an instance of the HistoryPane
     */
    public HistoryPane() {
        super(JSplitPane.VERTICAL_SPLIT);

        // Create side panel
        final double[][] sizes = {
                               {TableLayoutConstants.PREFERRED, TableLayoutConstants.FILL},
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

        final JRadioButton rb2 = new JRadioButton(Commons.getModel().getLanguageString("Season")); //$NON-NLS-1$
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
                                                  {.25, 75, 25, .5, 75, 25, .25},
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

        sidePanel.add(filterPanel, "0, 0");
        sidePanel.add(new JSeparator(), "0, 2");
        sidePanel.add(amountPanel, "0, 4");
        sidePanel.add(pricePanel, "0, 5");
        sidePanel.add(tsiPanel, "0, 6");

        final JScrollPane sidePane = new JScrollPane(sidePanel);
        sidePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sidePane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        sidePane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Create the top panel and add it to the split pane
        final JPanel topPanel = Commons.getModel().getGUI().createImagePanel();
        topPanel.setLayout(new BorderLayout());

        transferPane = new TeamTransfersPane(playerDetailPanel);

        topPanel.add(transferPane, BorderLayout.CENTER);
        topPanel.add(sidePane, BorderLayout.WEST);

        setDividerLocation(DividerDAO.getDividerPosition("HistoryTabDivider")); //$NON-NLS-1$
        addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY,
                                  new DividerListener("HistoryTabDivider")); //$NON-NLS-1$

        setLeftComponent(topPanel);
        setRightComponent(playerDetailPanel);

        refresh();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Refresh the displayed information.
     */
    public final void refresh() {
        if (spinSeason.isSelected()) {
            final SpinnerNumberModel model = (SpinnerNumberModel) this.spinner.getModel();
            this.transfers = TransfersDAO.getTransfers(model.getNumber().intValue(), true, true);
        } else {
            this.transfers = TransfersDAO.getTransfers(0, true, true);
        }

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
}
