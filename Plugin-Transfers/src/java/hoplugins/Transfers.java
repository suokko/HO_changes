// %2362941848:hoplugins%
package hoplugins;

import hoplugins.commons.utils.PluginProperty;

import hoplugins.transfers.dao.TransferSettingDAO;
import hoplugins.transfers.dao.TransferStatusDAO;
import hoplugins.transfers.dao.TransfersDAO;
import hoplugins.transfers.ui.HistoryPane;
import hoplugins.transfers.ui.TransferTypePane;
import hoplugins.transfers.vo.PlayerTransfer;

import plugins.IHOMiniModel;
import plugins.IOfficialPlugin;
import plugins.IPlugin;
import plugins.IRefreshable;
import plugins.ISpieler;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JWindow;


/**
 * Plugin regarding transfer information.
 *
 * @author <a href=mailto:nethyperon@users.sourceforge.net>Boy van der Werf</a>
 */
public class Transfers implements IPlugin, IRefreshable, ActionListener, IOfficialPlugin {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** PLugin String name */
    private static final String PLUGIN_NAME = "Transfers";

    //~ Instance fields ----------------------------------------------------------------------------

    /** Plugin version constant */
    private final double PLUGIN_VERSION = 0.973;

    /** Plugin Id */
    private final int PLUGIN_ID = 25;
    private HistoryPane historyPane;
    private List<ISpieler> oldplayers;
    private List<ISpieler> players;
    private TransferTypePane transferTypePane;

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    public final String getName() {
        return getPluginName() + " " + getVersion(); //$NON-NLS-1$
    }

    /**
     * {@inheritDoc}
     */
    public final int getPluginID() {
        return PLUGIN_ID;
    }

    /**
     * {@inheritDoc}
     */
    public final String getPluginName() {
        return PLUGIN_NAME; 
    }

    /**
     * {@inheritDoc}
     */
    public final File[] getUnquenchableFiles() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public final double getVersion() {
        return PLUGIN_VERSION;
    }

    /**
     * {@inheritDoc}
     */
    public final void actionPerformed(ActionEvent evt) {
        final IHOMiniModel model = Commons.getModel();

        if (model.getBasics().getTeamId() != 0) {
            final StringBuffer sBuffer = new StringBuffer();
            sBuffer.append(PluginProperty.getString("UpdConfirmMsg.0"));
            sBuffer.append("\n" + PluginProperty.getString("UpdConfirmMsg.1"));
            sBuffer.append("\n" + PluginProperty.getString("UpdConfirmMsg.2"));
            sBuffer.append("\n\n" + PluginProperty.getString("UpdConfirmMsg.3"));

            final int choice = JOptionPane.showConfirmDialog(model.getGUI().getOwner4Dialog(),
                                                             sBuffer.toString(),
                                                             PluginProperty.getString("Warning"),
                                                             JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                try {
                    final JWindow waitWindow = model.getGUI().createWaitDialog(model.getGUI()
                                                                                    .getOwner4Dialog());
                    waitWindow.setVisible(true);
                    TransfersDAO.reloadTeamTransfers(model.getBasics().getTeamId());
                    waitWindow.setVisible(false);
                    waitWindow.dispose();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                refresh();
            }
        } else {
            model.getHelper().showMessage(model.getGUI().getOwner4Dialog(),
                                          PluginProperty.getString("UpdMsg"), "", 1);
        }
    }

    /**
     * {@inheritDoc}
     */
    public final void refresh() {
        final IHOMiniModel model = Commons.getModel();

        final JWindow waitWindow = model.getGUI().createWaitDialog(model.getGUI().getOwner4Dialog());
        waitWindow.setVisible(true);

        final List<ISpieler> allOutdated = new Vector<ISpieler>();

        // Check for outdated players.
        final List<ISpieler> tmp = new Vector<ISpieler>();
        tmp.clear();
        tmp.addAll(model.getAllSpieler());
        tmp.removeAll(this.players);
        allOutdated.addAll(tmp);

        // Check for outdated old players.
        tmp.clear();
        tmp.addAll(model.getAllOldSpieler());
        tmp.removeAll(this.oldplayers);
        allOutdated.addAll(tmp);

        final List<ISpieler> outdated = new ArrayList<ISpieler>(allOutdated);

        for (Iterator<ISpieler> iter = allOutdated.iterator(); iter.hasNext();) {
            final ISpieler player = iter.next();

            if (player.getSpielerID() < 0) {
                outdated.remove(player);
            }
        }

        if ((outdated.size() > 0) && (TransfersDAO.getTransfers(0, true, true).size() == 0)) {
            TransfersDAO.updateTeamTransfers(model.getBasics().getTeamId());
        }

        for (Iterator<ISpieler> iter = outdated.iterator(); iter.hasNext();) {
            final ISpieler player = iter.next();
            TransfersDAO.updatePlayerTransfers(player.getSpielerID());
        }

        final List<PlayerTransfer> transfers = reloadData();

        // aik: workaround: instead of uploading player data we always set the state
        //		of all transfers to 'uploaded'
        //      the EPV server (at ethz.ch) doesn't work anyway
        setTransfersUploaded(transfers);
//        if (TransferSettingDAO.isAutomatic()) {
//            uploadTransfers(transfers);
//        }

        waitWindow.setVisible(false);
        waitWindow.dispose();
    }

    /**
     * {@inheritDoc}
     */
    public final void start(IHOMiniModel hoMiniModel) {
        this.players = new Vector<ISpieler>(hoMiniModel.getAllSpieler());
        this.oldplayers = new Vector<ISpieler>(hoMiniModel.getAllOldSpieler());

        // Create the top panel
        final JTabbedPane tabPane = new JTabbedPane();

        historyPane = new HistoryPane();
        tabPane.add(PluginProperty.getString("History"), historyPane); //$NON-NLS-1$

        transferTypePane = new TransferTypePane();
        tabPane.add(PluginProperty.getString("TransferTypes"), transferTypePane);

        //tabPane.add("Bookmarked Teams", new TeamBookmarkPane());
        //JPanel playerPanel = hoMiniModel.getGUI().createImagePanel();
        //tabPane.add("Bookmarked Players", playerPanel);
        final JPanel pluginPanel = hoMiniModel.getGUI().createImagePanel();
        pluginPanel.setLayout(new BorderLayout());
        pluginPanel.add(tabPane, BorderLayout.CENTER);

        hoMiniModel.getGUI().addTab(getPluginName(), pluginPanel);
        hoMiniModel.getGUI().registerRefreshable(this);

        final JMenu menu = new JMenu(getPluginName()); 
        final JMenuItem item = new JMenuItem(PluginProperty.getString("Menu.refreshData")); //$NON-NLS-1$
        item.addActionListener(this);
        menu.add(item);
        hoMiniModel.getGUI().addMenu(menu);
//        hoMiniModel.getGUI().addOptionPanel(getPluginName(), new OptionPanel());
        reloadData();

//        Disable EPV info for fresh HO! installations
//        if (!TransferSettingDAO.isStarted()) {
//            JOptionPane.showMessageDialog(hoMiniModel.getGUI().getOwner4Dialog(),
//                                          new StartingPanel(), "Info", //$NON-NLS-1$
//                                          JOptionPane.PLAIN_MESSAGE);
//            TransferSettingDAO.setStarted();
//        }

        if (!TransferSettingDAO.isFixed()) {
            TransferStatusDAO.resetTransfers();
            TransferSettingDAO.setFixed();
        }
    }

    /**
     * Refresh the data in the plugin
     *
     * @return List of transfers shown in the plugin
     */
    private List<PlayerTransfer> reloadData() {
        final IHOMiniModel model = Commons.getModel();
        this.players = new Vector<ISpieler>(model.getAllSpieler());
        this.oldplayers = new Vector<ISpieler>(model.getAllOldSpieler());

        final List<PlayerTransfer> transfers = TransfersDAO.getTransfers(0, true, true);

        historyPane.refresh();
        transferTypePane.refresh(transfers);
        return transfers;
    }

    /**
     * Uploads transfer and player information for TPE
     * NOT USED ANY LONGER
     *
     * @param pt Transfer information
     *
     * @return Boolean to indicate succesfull upload
     */
    /*private boolean upload(PlayerTransfer pt) {
        final IHOMiniModel model = Commons.getModel();
        final ISpieler spieler = model.getSpielerAtDate(pt.getPlayerId(), pt.getDate());

        //System.out.println(spieler + "-" + pt.getPlayerName() + "|" + pt.getPlayerId());
        if (spieler == null) {
            return false;
        }

        //System.out.println(new Date(pt.getDate().getTime()));
        //System.out.println(new Date(spieler.getHrfDate().getTime()));
        int transferSeason = Commons.getModel().getHelper().getHTSeason(pt.getDate());
        int transferWeek = Commons.getModel().getHelper().getHTWeek(pt.getDate());
        int spielerSeason = Commons.getModel().getHelper().getHTSeason(spieler.getHrfDate());
        int spielerWeek = Commons.getModel().getHelper().getHTWeek(spieler.getHrfDate());

        // Not in the same week, possible skillup so skip it
        if (((transferSeason * 16) + transferWeek) != ((spielerSeason * 16) + spielerWeek)) {
            //System.out.println((transferDate.getHTSeason() * 16) + transferDate.getHTWeek());
            //System.out.println((spielerDate.getHTSeason() * 16) + spielerDate.getHTWeek());
            return false;
        }

        final StringBuffer url = new StringBuffer();
        url.append("http://URL_TO_EPV_SERVER/ho.php?cmd=addTransfer");
        url.append("&id=" + pt.getTransferID()); //$NON-NLS-1$ 
        url.append("&age=" + spieler.getAlter()); //$NON-NLS-1$ 
        url.append("&for=" + spieler.getForm()); //$NON-NLS-1$ 
        url.append("&inj=" + spieler.getVerletzt()); //$NON-NLS-1$ 
        url.append("&exp=" + spieler.getErfahrung()); //$NON-NLS-1$ 
        url.append("&lea=" + spieler.getFuehrung()); //$NON-NLS-1$ 
        url.append("&tsi=" + pt.getTsi()); //$NON-NLS-1$ 
        url.append("&sta=" + spieler.getKondition()); //$NON-NLS-1$ 
        url.append("&kee=" + spieler.getTorwart()); //$NON-NLS-1$ 
        url.append("&pla=" + spieler.getSpielaufbau()); //$NON-NLS-1$ 
        url.append("&pas=" + spieler.getPasspiel()); //$NON-NLS-1$ 
        url.append("&win=" + spieler.getFluegelspiel()); //$NON-NLS-1$ 
        url.append("&def=" + spieler.getVerteidigung()); //$NON-NLS-1$ 
        url.append("&att=" + spieler.getTorschuss()); //$NON-NLS-1$ 
        url.append("&set=" + spieler.getStandards()); //$NON-NLS-1$ 
        url.append("&spe=" + spieler.getSpezialitaet()); //$NON-NLS-1$ 
        url.append("&agg=" + spieler.getAgressivitaet()); //$NON-NLS-1$ 
        url.append("&pop=" + spieler.getCharakter()); //$NON-NLS-1$ 
        url.append("&hon=" + spieler.getAnsehen()); //$NON-NLS-1$ 

        final double curr_rate = Commons.getModel().getXtraDaten().getCurrencyRate();
        final double price = (pt.getPrice() * curr_rate) / 10;
        url.append("&pri=" + (int) price); //$NON-NLS-1$ 

        url.append("&wee=" + transferWeek); //$NON-NLS-1$ 
        url.append("&sea=" + transferSeason); //$NON-NLS-1$ 

        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(pt.getDate().getTime());
        url.append("&hou=" + c.get(Calendar.HOUR_OF_DAY)); //$NON-NLS-1$

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        url.append("&date=" + sdf.format(c.getTime())); //$NON-NLS-1$

        url.append("&ver=" + getVersion()); //$NON-NLS-1$

        url.append("&teamId=" + Commons.getModel().getBasics().getTeamId());
        url.append("&countryId=" + Commons.getModel().getBasics().getLand());

        String liga = Commons.getModel().getLiga().getLiga();
        int level = SeriesUtil.getSeriesLevel(liga);
        url.append("&levelId=" + level);

        try {
            final String result = Commons.getModel().getDownloadHelper().getUsalWebPage(url
                                                                                        .toString(),
                                                                                        false);

            //System.out.println(result);
            if ((result.equalsIgnoreCase("True")) || (result.equalsIgnoreCase("Skipped"))) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }*/

    /**
     * Upload to EPV Server the needed Transfer operation
     * NOT USED ANY LONGER
     *
     * @param transfers list of transfers to check for upload
     */
    /*private void uploadTransfers(List<PlayerTransfer> transfers) {
        for (Iterator<PlayerTransfer> iter = transfers.iterator(); iter.hasNext();) {
            final PlayerTransfer pt = iter.next();

            if (!TransferStatusDAO.isUploaded(pt.getTransferID())) {
                if (upload(pt)) {
                    TransferStatusDAO.setUploaded(pt.getTransferID());
                }
            }
        }
    }*/

    /**
     * Set the 'uploaded' state for all transfers.
     *
     * @param transfers list of transfers to check for upload
     */
    private void setTransfersUploaded(List<PlayerTransfer> transfers) {
    	if (transfers == null) return;
        for (Iterator<PlayerTransfer> iter = transfers.iterator(); iter.hasNext();) {
            PlayerTransfer pt = iter.next();
            if (pt != null) {
            	TransferStatusDAO.setUploaded(pt.getTransferID());
            }
        }
    }
}
