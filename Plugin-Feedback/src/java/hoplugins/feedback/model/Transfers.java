package hoplugins.feedback.model;

import hoplugins.Commons;
import hoplugins.commons.utils.HTCalendar;
import hoplugins.commons.utils.HTCalendarFactory;
import hoplugins.commons.utils.SeriesUtil;
import hoplugins.feedback.constants.FeedbackConstants;
import hoplugins.feedback.dao.TransfersDAO;
import hoplugins.feedback.model.transfer.PlayerTransfer;
import hoplugins.feedback.util.FeedbackHelper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import plugins.IHOMiniModel;
import plugins.ISpieler;

public class Transfers extends FeedbackObject {
	private String baseUrl = "http://hofeedback.flattermann.net/addtransfer.php?";
	// Upload transfers of the last 16 weeks, only 
	private static int MAXAGE = 16;
	/**
	 * Up to which date was the upload completed,
	 * i.e. ignore data sets before this date
	 */
	private static Timestamp completedDate = new Timestamp(0);
	private PlayerTransfer playerTransfer;
	private ISpieler player;
	
	public Transfers (PlayerTransfer playerTransfer) {
		super (FeedbackConstants.TYPE_TRANSFERS, playerTransfer.getTransferID());
		this.playerTransfer = playerTransfer;
		this.player = getMiniModel().getSpielerAtDate(playerTransfer.getPlayerId(), playerTransfer.getDate());
	}
	
	public static List<Transfers> rebuildList(Timestamp completedDate) {
		List<Transfers> list = new Vector<Transfers>();
		try {
			// Set completedDate to one week ago
			// Reason: 
			// When downloading a new HRF, the feedback plugin cannot upload the new transfers,
			// because the Transfer plugin has not collected the data yet. Therefore, some transfers
			// would be ignored.
			// 1 week = 7*24*60*60*1000 millisec
			Transfers.completedDate = new Timestamp(completedDate.getTime() - 7*24*60*60*1000);
			List<PlayerTransfer> transfers = TransfersDAO.getAllTransfers(Transfers.completedDate, MAXAGE);
			ListIterator<PlayerTransfer> iter = transfers.listIterator();
			while (iter.hasNext()) {
				PlayerTransfer curTransfer = iter.next();
				Transfers newTransfer = new Transfers(curTransfer);
				if (newTransfer.isValid())
					list.add(newTransfer);
			}
		} catch (Exception e) {
			System.out.println ("Feedback.Transfers: Exception catched");
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public String createUrl() {
		try {
			IHOMiniModel miniModel = getMiniModel();
			StringBuffer url = new StringBuffer(baseUrl);
			url.append("contributor=" + miniModel.getBasics().getTeamId());
			url.append("&id=" + playerTransfer.getTransferID()); 
			url.append("&plId=" + player.getSpielerID()); 
			url.append("&age=" + FeedbackHelper.round(player.getAlterWithAgeDays(),4)); 
			url.append("&fo=" + player.getForm()); 
			url.append("&inj=" + player.getVerletzt()); 
			url.append("&xp=" + player.getErfahrung()); 
			url.append("&lea=" + player.getFuehrung()); 
			url.append("&tsi=" + playerTransfer.getTsi()); 
			url.append("&st=" + player.getKondition()); 
			url.append("&gk=" + player.getTorwart()); 
			url.append("&pm=" + player.getSpielaufbau()); 
			url.append("&ps=" + player.getPasspiel()); 
			url.append("&wi=" + player.getFluegelspiel()); 
			url.append("&de=" + player.getVerteidigung()); 
			url.append("&sc=" + player.getTorschuss()); 
			url.append("&sp=" + player.getStandards()); 
			url.append("&spec=" + player.getSpezialitaet()); 
			url.append("&agg=" + player.getAgressivitaet()); 
			url.append("&pop=" + player.getCharakter()); 
			url.append("&hon=" + player.getAnsehen()); 

			final double curr_rate = miniModel.getXtraDaten().getCurrencyRate()/10;
			final double price = playerTransfer.getPrice() * curr_rate;
			url.append("&price=" + (int) price); 

			double wage = player.getGehalt();
			if (player.getBonus() > 0) {
				wage = wage / (1d + player.getBonus()/100d);
			}
			wage = wage * curr_rate;
			url.append("&wage=" + (int)wage);

			final HTCalendar globalDate = HTCalendarFactory.createGlobalCalendar(playerTransfer.getDate());
			url.append("&season=" + globalDate.getHTSeason()); 
			url.append("&week=" + globalDate.getHTWeek()); 

			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(playerTransfer.getDate().getTime());
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			url.append("&tstamp="+sdf.format(c.getTime()));

			url.append("&country=" + miniModel.getBasics().getLand());

			String liga = Commons.getModel().getLiga().getLiga();
			int level = SeriesUtil.getSeriesLevel(liga);
			url.append("&level=" + level);

			url.append("&ver=" + getFeedbackVersion()); 

			return url.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public static Timestamp getCompletedDate() {
		return completedDate;
	}

	public static void setCompletedDate(Timestamp completedDate) {
		Transfers.completedDate = completedDate;
	}
	
	private boolean isValid () {
		if (player == null || player.getSpielerID() <= 0)
			return false;
		
        final HTCalendar transferDate = 
        		HTCalendarFactory.createTrainingCalendar(getMiniModel(), playerTransfer.getDate());
        final HTCalendar spielerDate = 
        		HTCalendarFactory.createTrainingCalendar(getMiniModel(), player.getHrfDate());

        // Not in the same week, possible skillup so skip it
        if (((transferDate.getHTSeason() * 16) + transferDate.getHTWeek()) != ((spielerDate
                                                                                .getHTSeason() * 16)
            + spielerDate.getHTWeek())) {
            //System.out.println((transferDate.getHTSeason() * 16) + transferDate.getHTWeek());
            //System.out.println((spielerDate.getHTSeason() * 16) + spielerDate.getHTWeek());
            return false;
        }

        return true;
	}
}
