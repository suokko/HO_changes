package ho.modul.transfer;

import ho.modul.transfer.history.HistoryPane;
import ho.modul.transfer.scout.TransferScoutPanel;
import ho.modul.transfer.transfertype.TransferTypePane;

import java.awt.BorderLayout;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import plugins.IRefreshable;
import plugins.ISpieler;
import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.gui.RefreshManager;
import de.hattrickorganizer.model.HOVerwaltung;

public class TransfersPanel extends JPanel implements IRefreshable{

	private static final long serialVersionUID = -5312017309355429020L;
    private HistoryPane historyPane;
    private List<ISpieler> oldplayers;
    private List<ISpieler> players;
    private TransferTypePane transferTypePane;
    private TransferScoutPanel scoutPanel;
    
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
        
        setLayout(new BorderLayout());
        add(tabPane, BorderLayout.CENTER);

        RefreshManager.instance().registerRefreshable(this);
		
	}

	public void refresh() {
//        final JWindow waitWindow = new LoginWaitDialog(HOMainFrame.instance());
//        waitWindow.setVisible(true);

        final List<ISpieler> allOutdated = new Vector<ISpieler>();

        // Check for outdated players.
        final List<ISpieler> tmp = new Vector<ISpieler>();
        tmp.clear();
        tmp.addAll(HOVerwaltung.instance().getModel().getAllSpieler());
        tmp.removeAll(this.players);
        allOutdated.addAll(tmp);

        // Check for outdated old players.
        tmp.clear();
        tmp.addAll(HOVerwaltung.instance().getModel().getAllOldSpieler());
        tmp.removeAll(this.oldplayers);
        allOutdated.addAll(tmp);

        final List<ISpieler> outdated = allOutdated;

        for (Iterator<ISpieler> iter = allOutdated.iterator(); iter.hasNext();) {
            final ISpieler player = iter.next();

            if (player.getSpielerID() < 0) {
                outdated.remove(player);
            }
        }

        if ((outdated.size() > 0) && (DBZugriff.instance().getTransfers(0, true, true).size() == 0)) {
        	DBZugriff.instance().updateTeamTransfers(HOVerwaltung.instance().getModel().getBasics().getTeamId());
        }

        for (Iterator<ISpieler> iter = outdated.iterator(); iter.hasNext();) {
            final ISpieler player = iter.next();
            DBZugriff.instance().updatePlayerTransfers(player.getSpielerID());
        }

        final List<PlayerTransfer> transfers = reloadData();

	}

    /**
     * Refresh the data in the plugin
     *
     * @return List of transfers shown in the plugin
     */
    private List<PlayerTransfer> reloadData() {
    	this.players = HOVerwaltung.instance().getModel().getAllSpieler();
        this.oldplayers = HOVerwaltung.instance().getModel().getAllOldSpieler();

        final List<PlayerTransfer> transfers = DBZugriff.instance().getTransfers(0, true, true);

        historyPane.refresh();
        transferTypePane.refresh(transfers);
        return transfers;
    }

    

	public TransferScoutPanel getScoutPanel() {
		return scoutPanel;
	}

}
