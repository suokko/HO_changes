package ho.module.transfer;

import ho.core.db.DBManager;
import ho.core.gui.IRefreshable;
import ho.core.gui.RefreshManager;
import ho.core.model.HOVerwaltung;
import ho.core.model.player.Spieler;
import ho.module.transfer.history.HistoryPane;
import ho.module.transfer.scout.TransferScoutPanel;
import ho.module.transfer.test.OverviewPanel;
import ho.module.transfer.transfertype.TransferTypePane;

import java.awt.BorderLayout;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TransfersPanel extends JPanel implements IRefreshable{

	private static final long serialVersionUID = -5312017309355429020L;
    private HistoryPane historyPane;
    private List<Spieler> oldplayers;
    private List<Spieler> players;
    private TransferTypePane transferTypePane;
    private TransferScoutPanel scoutPanel;
    private OverviewPanel overviewPanel;
    
	public TransfersPanel(){
		initialize();
	}

	private void initialize() {
		this.players = HOVerwaltung.instance().getModel().getAllSpieler();
        this.oldplayers = HOVerwaltung.instance().getModel().getAllOldSpieler();

        // Create the top panel
        final JTabbedPane tabPane = new JTabbedPane();

        historyPane = new HistoryPane();
        tabPane.add(HOVerwaltung.instance().getLanguageString("History"), historyPane); //$NON-NLS-1$

        transferTypePane = new TransferTypePane();
        tabPane.add(HOVerwaltung.instance().getLanguageString("TransferTypes"), transferTypePane);
        
        scoutPanel = new TransferScoutPanel();
        tabPane.add(HOVerwaltung.instance().getLanguageString("TransferScout"), scoutPanel);
        
        this.overviewPanel = new OverviewPanel();
        tabPane.add("Financial", this.overviewPanel);
        
        setLayout(new BorderLayout());
        add(tabPane, BorderLayout.CENTER);

        RefreshManager.instance().registerRefreshable(this);
		
	}

	public void refresh() {
//        final JWindow waitWindow = new LoginWaitDialog(HOMainFrame.instance());
//        waitWindow.setVisible(true);

        final List<Spieler> allOutdated = new Vector<Spieler>();

        // Check for outdated players.
        final List<Spieler> tmp = new Vector<Spieler>();
        tmp.clear();
        tmp.addAll(HOVerwaltung.instance().getModel().getAllSpieler());
        tmp.removeAll(this.players);
        allOutdated.addAll(tmp);

        // Check for outdated old players.
        tmp.clear();
        tmp.addAll(HOVerwaltung.instance().getModel().getAllOldSpieler());
        tmp.removeAll(this.oldplayers);
        allOutdated.addAll(tmp);

        final List<Spieler> outdated = allOutdated;

        for (Iterator<Spieler> iter = allOutdated.iterator(); iter.hasNext();) {
            final Spieler player = iter.next();

            if (player.getSpielerID() < 0) {
                outdated.remove(player);
            }
        }

        if ((outdated.size() > 0) && (DBManager.instance().getTransfers(0, true, true).size() == 0)) {
        	DBManager.instance().updateTeamTransfers(HOVerwaltung.instance().getModel().getBasics().getTeamId());
        }

        for (Iterator<Spieler> iter = outdated.iterator(); iter.hasNext();) {
            final Spieler player = iter.next();
            DBManager.instance().updatePlayerTransfers(player.getSpielerID());
        }

        reloadData();

	}

    /**
     * Refresh the data in the plugin
     *
     * @return List of transfers shown in the plugin
     */
    private List<PlayerTransfer> reloadData() {
    	this.players = HOVerwaltung.instance().getModel().getAllSpieler();
        this.oldplayers = HOVerwaltung.instance().getModel().getAllOldSpieler();

        final List<PlayerTransfer> transfers = DBManager.instance().getTransfers(0, true, true);

        historyPane.refresh();
        transferTypePane.refresh(transfers);
        return transfers;
    }

    

	public TransferScoutPanel getScoutPanel() {
		return scoutPanel;
	}

}
