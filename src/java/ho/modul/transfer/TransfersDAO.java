// %3298555606:hoplugins.transfers.dao%
package ho.modul.transfer;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import plugins.ISpieler;
import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.tools.HelperWrapper;


/**
 * DAO to store and retrieve transfers in the HO database.
 *
 * @author <a href=mailto:nethyperon@users.sourceforge.net>Boy van der Werf</a>
 */
public final class TransfersDAO {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** Name of the table in the HO database */
    private static final String TABLE_NAME = "transfers_transfers"; //$NON-NLS-1$

    static {
        checkTable();
    }

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Private default constuctor to prevent class instantiation.
     */
    private TransfersDAO() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Gets a list of transfers.
     *
     * @param playerid Player id for selecting transfers.
     * @param allTransfers If <code>false</code> this method will only return transfers for your
     *        own team, otherwise it will return all transfers for the player.
     *
     * @return List of transfers.
     */
    public static List<PlayerTransfer> getTransfers(int playerid, boolean allTransfers) {
        final StringBuffer sqlStmt = new StringBuffer("SELECT * FROM " + TABLE_NAME); //$NON-NLS-1$
        sqlStmt.append(" WHERE playerid = " + playerid); //$NON-NLS-1$

        if (!allTransfers) {
            final int teamid = HOVerwaltung.instance().getModel().getBasics().getTeamId();
            sqlStmt.append(" AND (buyerid = " + teamid); //$NON-NLS-1$
            sqlStmt.append(" OR sellerid = " + teamid + ")"); //$NON-NLS-1$ //$NON-NLS-2$
        }

        sqlStmt.append(" ORDER BY date DESC"); //$NON-NLS-1$

        return loadTransfers(sqlStmt.toString());
    }

    /**
     * Gets a list of transfers for your own team.
     *
     * @param season Season number for selecting transfers.
     * @param bought <code>true</code> to include BUY transfers.
     * @param sold <code>true</code> to include SELL transfers.
     *
     * @return List of transfers.
     */
    public static List<PlayerTransfer> getTransfers(int season, boolean bought, boolean sold) {
        final int teamid = HOVerwaltung.instance().getModel().getBasics().getTeamId();
        return getTransfers(teamid, season, bought, sold);
    }

    /**
     * Gets a list of transfers.
     *
     * @param teamid Team id to select transfers for.
     * @param season Season number for selecting transfers.
     * @param bought <code>true</code> to include BUY transfers.
     * @param sold <code>true</code> to include SELL transfers.
     *
     * @return List of transfers.
     */
    public static List<PlayerTransfer> getTransfers(int teamid, int season, boolean bought, boolean sold) {
        final StringBuffer sqlStmt = new StringBuffer("SELECT * FROM " + TABLE_NAME); //$NON-NLS-1$
        sqlStmt.append(" WHERE 1=1"); //$NON-NLS-1$

        if (season != 0) {
            sqlStmt.append(" AND season = " + season); //$NON-NLS-1$
        }

        if (bought || sold) {
            sqlStmt.append(" AND ("); //$NON-NLS-1$

            if (bought) {
                sqlStmt.append(" buyerid = " + teamid); //$NON-NLS-1$
            }

            if (bought && sold) {
                sqlStmt.append(" OR"); //$NON-NLS-1$
            }

            if (sold) {
                sqlStmt.append(" sellerid = " + teamid); //$NON-NLS-1$
            }

            sqlStmt.append(")"); //$NON-NLS-1$
        }

        sqlStmt.append(" ORDER BY date DESC"); //$NON-NLS-1$

        return loadTransfers(sqlStmt.toString());
    }

    /**
     * Reload transfer data for a team from the HT xml.
     *
     * @param teamid Team id to reload data for
     *
     * @throws Exception If an error occurs.
     */
    public static void reloadTeamTransfers(int teamid) throws Exception {
        DBZugriff.instance().getAdapter().executeUpdate("DELETE FROM " + TABLE_NAME
                                                      + " WHERE buyerid = " + teamid
                                                      + " OR sellerid = " + teamid);
        updateTeamTransfers(teamid);
    }

    /**
     * Update transfer data for a player.
     *
     * @param playerId Player
     */
    public static void updatePlayerTransfers(int playerId) {
        try {
            final List<PlayerTransfer> transfers = XMLParser.getAllPlayerTransfers(playerId);

            if (transfers.size() > 0) {
                for (Iterator<PlayerTransfer> iter = transfers.iterator(); iter.hasNext();) {
                    final PlayerTransfer transfer = iter.next();
                    addTransfer(transfer);
                }
            } else {
                // Fired player, update team related transfers and remove the rest of the player's history
                final int teamid = HOVerwaltung.instance().getModel().getBasics().getTeamId();

                final StringBuffer sqlStmt = new StringBuffer("UPDATE " + TABLE_NAME); //$NON-NLS-1$
                sqlStmt.append(" SET"); //$NON-NLS-1$
                sqlStmt.append(" playerid = 0, playername = ''"); //$NON-NLS-1$
                sqlStmt.append(" WHERE playerid = " + playerId); //$NON-NLS-1$
                sqlStmt.append(" AND (buyerid = " + teamid + " OR sellerid = " + teamid + ")"); //$NON-NLS-1$
                DBZugriff.instance().getAdapter().executeUpdate(sqlStmt.toString());
                DBZugriff.instance().getAdapter().executeUpdate("DELETE FROM " + TABLE_NAME
                                                              + " WHERE playerid = " + playerId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update transfer data for a team from the HT xml.
     *
     * @param teamid Team id to update data for
     */
    public static void updateTeamTransfers(int teamid) {
        try {
            final List<ISpieler> players = new Vector<ISpieler>();

            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 1);

            final List<PlayerTransfer> transfers = XMLParser.getAllTeamTransfers(teamid, resetDay(cal.getTime()));

            for (Iterator<PlayerTransfer> iter = transfers.iterator(); iter.hasNext();) {
                PlayerTransfer transfer = iter.next();
                addTransfer(transfer);

                final ISpieler player = PlayerRetriever.getPlayer(transfer.getPlayerId());

                if (player != null) {
                    players.add(player);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Date resetDay(Date date) {
        final Calendar cal = new GregorianCalendar();

        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }
    /**
     * Remove a transfer from the HO database
     *
     * @param transferId Transfer ID
     */
    private static void removeTransfer(int transferId) {
    	try {
            DBZugriff.instance().getAdapter()
            	.executeUpdate("DELETE FROM " + TABLE_NAME + " WHERE transferid='"+transferId+"'");
    	} catch (Exception e) {
    		// ignore
    	}
    }
    
    /**
     * Adds a tranfer to the HO database
     *
     * @param transfer Transfer information
     *
     * @return Boolean to indicate if the transfer is sucessfully added.
     */
    private static boolean addTransfer(PlayerTransfer transfer) {
    	removeTransfer(transfer.getTransferID());
        final StringBuffer sqlStmt = new StringBuffer("INSERT INTO " + TABLE_NAME); //$NON-NLS-1$
        sqlStmt.append("(transferid, date, week, season, playerid, playername, buyerid, buyername, sellerid, sellername, price, marketvalue, tsi)"); //$NON-NLS-1$
        sqlStmt.append(" VALUES ("); //$NON-NLS-1$
        sqlStmt.append(transfer.getTransferID() + ","); //$NON-NLS-1$
        sqlStmt.append("'" + transfer.getDate().toString() + "',"); //$NON-NLS-1$ //$NON-NLS-2$
        sqlStmt.append(transfer.getWeek() + ","); //$NON-NLS-1$
        sqlStmt.append(transfer.getSeason() + ","); //$NON-NLS-1$
        sqlStmt.append(transfer.getPlayerId() + ","); //$NON-NLS-1$
        sqlStmt.append("'"
                       +  HelperWrapper.instance().encodeString4Database(transfer.getPlayerName())
                       + "',"); //$NON-NLS-1$ 
        sqlStmt.append(transfer.getBuyerid() + ","); //$NON-NLS-1$
        sqlStmt.append("'"
                       + HelperWrapper.instance().encodeString4Database(transfer.getBuyerName())
                       + "',"); //$NON-NLS-1$ 
        sqlStmt.append(transfer.getSellerid() + ","); //$NON-NLS-1$
        sqlStmt.append("'"
                       +  HelperWrapper.instance().encodeString4Database(transfer.getSellerName())
                       + "',"); //$NON-NLS-1$ 
        sqlStmt.append(transfer.getPrice() + ","); //$NON-NLS-1$
        sqlStmt.append(transfer.getMarketvalue() + ","); //$NON-NLS-1$
        sqlStmt.append(transfer.getTsi());
        sqlStmt.append(" )"); //$NON-NLS-1$

        try {
            DBZugriff.instance().getAdapter().executeUpdate(sqlStmt.toString());
            return true;
        } catch (Exception inore) {
            return false;
        }
    }

    /**
     * Method that check if the table exists, if not creates it and sets the values to default
     */
    private static void checkTable() {
        ResultSet rs = null;

        rs = DBZugriff.instance().getAdapter().executeQuery("SELECT * FROM " + TABLE_NAME); //$NON-NLS-1$

        if (rs == null) {
            final StringBuffer sqlStmt = new StringBuffer("CREATE TABLE " + TABLE_NAME); //$NON-NLS-1$
            sqlStmt.append("("); //$NON-NLS-1$
            sqlStmt.append("transferid INTEGER NOT NULL,"); //$NON-NLS-1$
            sqlStmt.append("date TIMESTAMP,"); //$NON-NLS-1$
            sqlStmt.append("week INTEGER,"); //$NON-NLS-1$
            sqlStmt.append("season INTEGER,"); //$NON-NLS-1$
            sqlStmt.append("playerid INTEGER NOT NULL,"); //$NON-NLS-1$
            sqlStmt.append("playername VARCHAR(127),"); //$NON-NLS-1$
            sqlStmt.append("buyerid INTEGER,"); //$NON-NLS-1$
            sqlStmt.append("buyername VARCHAR(256),"); //$NON-NLS-1$
            sqlStmt.append("sellerid INTEGER,"); //$NON-NLS-1$
            sqlStmt.append("sellername VARCHAR(256),"); //$NON-NLS-1$
            sqlStmt.append("price INTEGER,"); //$NON-NLS-1$
            sqlStmt.append("marketvalue INTEGER,"); //$NON-NLS-1$
            sqlStmt.append("tsi INTEGER,"); //$NON-NLS-1$
            sqlStmt.append("PRIMARY KEY (transferid)"); //$NON-NLS-1$
            sqlStmt.append(")"); //$NON-NLS-1$

            DBZugriff.instance().getAdapter().executeUpdate(sqlStmt.toString());
            DBZugriff.instance().getAdapter().executeUpdate("CREATE INDEX pl_id ON " + TABLE_NAME
                                                          + " (playerid)"); //$NON-NLS-1$ 
            DBZugriff.instance().getAdapter().executeUpdate("CREATE INDEX buy_id ON " + TABLE_NAME
                                                          + " (buyerid)"); //$NON-NLS-1$ 
            DBZugriff.instance().getAdapter().executeUpdate("CREATE INDEX sell_id ON " + TABLE_NAME
                                                          + " (sellerid)"); //$NON-NLS-1$ 

        } else {
            // This part is to solve incorrect calculated values from the 1.01 version of the plugin

           // if (!calendarfix) {
                final Map<Integer,Timestamp> map = new HashMap<Integer,Timestamp>();

                try {
                    while (rs.next()) {
                        map.put(new Integer(rs.getInt("transferid")), rs.getTimestamp("date"));
                    }

                    for (Iterator<Integer> iter = map.keySet().iterator(); iter.hasNext();) {
                        final Integer transferId = iter.next();
                        final Timestamp date = map.get(transferId);

                        final StringBuffer sqlStmt = new StringBuffer("UPDATE " + TABLE_NAME
                                                                      + " SET"); //$NON-NLS-1$
                        sqlStmt.append(" week=" + HelperWrapper.instance().getHTWeek(date)); //$NON-NLS-1$
                        sqlStmt.append(",season=" + HelperWrapper.instance().getHTSeason(date)); //$NON-NLS-1$
                        sqlStmt.append(" WHERE transferid=" + transferId.intValue()); //$NON-NLS-1$
                        DBZugriff.instance().getAdapter().executeUpdate(sqlStmt.toString());
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }

    /**
     * Loads a list of transfers from the HO database.
     *
     * @param sqlStmt SQL statement.
     *
     * @return List of transfers
     */
    private static List<PlayerTransfer> loadTransfers(String sqlStmt) {
        final double curr_rate = HOVerwaltung.instance().getModel().getXtraDaten().getCurrencyRate();

        final List<PlayerTransfer> results = new Vector<PlayerTransfer>();
        final ResultSet rs = DBZugriff.instance().getAdapter().executeQuery(sqlStmt.toString());

        if (rs == null) {
            return new Vector<PlayerTransfer>();
        }

        try {
            while (rs.next()) {
                PlayerTransfer transfer = new PlayerTransfer(rs.getInt("transferid"),
                                                             rs.getInt("playerid")); //$NON-NLS-1$ 
                transfer.setPlayerName( HelperWrapper.instance().decodeStringFromDatabase(rs.getString("playername"))); //$NON-NLS-1$
                transfer.setDate(rs.getTimestamp("date")); //$NON-NLS-1$
                transfer.setWeek(rs.getInt("week")); //$NON-NLS-1$
                transfer.setSeason(rs.getInt("season")); //$NON-NLS-1$

                transfer.setBuyerid(rs.getInt("buyerid")); //$NON-NLS-1$
                transfer.setBuyerName( HelperWrapper.instance().decodeStringFromDatabase(rs.getString("buyername"))); //$NON-NLS-1$
                transfer.setSellerid(rs.getInt("sellerid")); //$NON-NLS-1$
                transfer.setSellerName( HelperWrapper.instance().decodeStringFromDatabase(rs.getString("sellername"))); //$NON-NLS-1$

                transfer.setPrice((int) (rs.getInt("price") / curr_rate)); //$NON-NLS-1$
                transfer.setMarketvalue((int) (rs.getInt("marketvalue") / curr_rate)); //$NON-NLS-1$
                transfer.setTsi(rs.getInt("tsi")); //$NON-NLS-1$

                results.add(transfer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Iterator<PlayerTransfer> iter = results.iterator(); iter.hasNext();) {
            PlayerTransfer transfer = iter.next();
            final ISpieler spieler = DBZugriff.instance().getSpielerAtDate(transfer.getPlayerId(),transfer.getDate());

            if (spieler != null) {
            	int transferSeason = HelperWrapper.instance().getHTSeason(transfer.getDate());
                int transferWeek = HelperWrapper.instance().getHTWeek(transfer.getDate());
                int spielerSeason = HelperWrapper.instance().getHTSeason(spieler.getHrfDate());
                int spielerWeek = HelperWrapper.instance().getHTWeek(spieler.getHrfDate());

                // Not in the same week, possible skillup so skip it
                if (((transferSeason * 16) + transferWeek) == ((spielerSeason * 16) + spielerWeek)) {
                    transfer.setPlayerInfo(spieler);
                }
            }
        }

        return results;
    }
}
