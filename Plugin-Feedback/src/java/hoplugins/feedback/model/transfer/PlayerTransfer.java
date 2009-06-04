package hoplugins.feedback.model.transfer;

import hoplugins.Commons;

import java.sql.Timestamp;

import plugins.ISpieler;


/**
 * Value Object representing a player transfer.
 *
 * @author <a href=mailto:nethyperon@users.sourceforge.net>Boy van der Werf</a>
 * @author flattermann <flattermannHO@gmail.com>
 */
public class PlayerTransfer {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** Type to indicate a BUY transfer */
    public static final int BUY = 1;

    /** Type to indicate a SELL transfer */
    public static final int SELL = 2;

    /** Type to indicate a SELL transfer */
    public static final int REBOUGHT = 0;

    //~ Instance fields ----------------------------------------------------------------------------

    /** Player info on tranfer date */
    private ISpieler playerInfo;

    /** Name of the buyer team */
    private String buyerName = ""; //$NON-NLS-1$

    /** Name of the transfered player */
    private String playerName = ""; //$NON-NLS-1$

    /** Name of the seller team */
    private String sellerName = ""; //$NON-NLS-1$

    /** Tranfer date */
    private Timestamp date;

    /** Id of the buyer team */
    private int buyerid = 0;

    /** Market value of the player at transfer date */
    private int marketvalue = 0;

    /** Id of the transfered player */
    private int playerId = 0;

    /** Transfer price */
    private int price = 0;

    /** Season */
    private int season;

    /** Id of the seller team */
    private int sellerid = 0;

    /** Id of the transfer */
    private int transferId = 0;

    /** TSI value of the player at transfer date */
    private int tsi = 0;

    /** Transfer type */
    private int type;

    /** Week */
    private int week;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates an instance of PlayerTransfer representing a player transfer.
     *
     * @param transferid Id of the transfer
     * @param playerid Id of the transferred player
     */
    public PlayerTransfer(int transferid, int playerid) {
        this.transferId = transferid;
        this.playerId = playerid;
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Sets the name of the buyer team.
     *
     * @param name name of the buyer team
     */
    public final void setBuyerName(String name) {
        this.buyerName = name;
    }

    /**
     * Gets the name of the buyer team.
     *
     * @return Name of the buyer team
     */
    public final String getBuyerName() {
        return buyerName;
    }

    /**
     * Sets the id of the buyer team.
     *
     * @param id Id of the buyer team
     */
    public final void setBuyerid(int id) {
        this.buyerid = id;

        if (buyerid == Commons.getModel().getBasics().getTeamId()) {
            if (sellerid != Commons.getModel().getBasics().getTeamId()) {
                this.type = BUY;
            } else {
                this.type = REBOUGHT;
            }
        }
    }

    /**
     * Gets the id of the buyer team.
     *
     * @return Id of the buyer team
     */
    public final int getBuyerid() {
        return buyerid;
    }

    /**
     * Sets the transfer date
     *
     * @param date Transfer date
     */
    public final void setDate(Timestamp date) {
        this.date = date;
    }

    /**
     * Gets the transfer date
     *
     * @return Transfer date
     */
    public final Timestamp getDate() {
        return date;
    }

    /**
     * Sets the market value of the player on transfer date.
     *
     * @param value Market value of the player on transfer date.
     */
    public final void setMarketvalue(int value) {
        this.marketvalue = value;
    }

    /**
     * Gets the market value of the player on transfer date.
     *
     * @return Market value of the player on transfer date.
     */
    public final int getMarketvalue() {
        return marketvalue;
    }

    /**
     * Gets the id of the transfered player.
     *
     * @return Id of the transfered player.
     */
    public final int getPlayerId() {
        return playerId;
    }

    /**
     * Sets the information about the player on transfer date.
     *
     * @param info Player information on transfer date.
     */
    public final void setPlayerInfo(ISpieler info) {
        this.playerInfo = info;
    }

    /**
     * Gets the information about the player on transfer date.
     *
     * @return Playerinformation if available. else <code>null</code>
     */
    public final ISpieler getPlayerInfo() {
        return playerInfo;
    }

    /**
     * Sets the name of the transfered player.
     *
     * @param name Name of the transfered player.
     */
    public final void setPlayerName(String name) {
        this.playerName = name;
    }

    /**
     * Gets the name of the transfered player.
     *
     * @return Name of the transfered player.
     */
    public final String getPlayerName() {
        return playerName;
    }

    /**
     * Sets the transfer price.
     *
     * @param price Transfer price.
     */
    public final void setPrice(int price) {
        this.price = price;
    }

    /**
     * Gets the tranfer price.
     *
     * @return Transfer price
     */
    public final int getPrice() {
        return price;
    }

    /**
     * Gets the season number.
     *
     * @param season number on transfer date
     */
    public final void setSeason(int season) {
        this.season = season;
    }

    /**
     * Gets the season number.
     *
     * @return Season number on transfer date
     */
    public final int getSeason() {
        return season;
    }

    /**
     * Sets the name of the seller team.
     *
     * @param name Name of the seller team.
     */
    public final void setSellerName(String name) {
        this.sellerName = name;
    }

    /**
     * Gets the name of the seller team.
     *
     * @return Name of the seller team.
     */
    public final String getSellerName() {
        return sellerName;
    }

    /**
     * Sets the id of the seller team.
     *
     * @param id Id of the seller team.
     */
    public final void setSellerid(int id) {
        this.sellerid = id;

        if (sellerid == Commons.getModel().getBasics().getTeamId()) {
            if (buyerid != Commons.getModel().getBasics().getTeamId()) {
                this.type = SELL;
            } else {
                this.type = REBOUGHT;
            }
        }
    }

    /**
     * Gets the id of the seller team.
     *
     * @return Id of the seller team.
     */
    public final int getSellerid() {
        return sellerid;
    }

    /**
     * Gets the id of the transfer.
     *
     * @return Id of the transfer
     */
    public final int getTransferID() {
        return transferId;
    }

    /**
     * Sets the TSI value when player was tranfered.
     *
     * @param tsi TSI value on transfer date.
     */
    public final void setTsi(int tsi) {
        this.tsi = tsi;
    }

    /**
     * Gets the TSI value when player was tranfered.
     *
     * @return TSI value on transfer date
     */
    public final int getTsi() {
        return tsi;
    }

    /**
     * Gets the transfer type.
     *
     * @return Type
     */
    public final int getType() {
        return type;
    }

    /**
     * Gets the week number.
     *
     * @param week number on transfer date
     */
    public final void setWeek(int week) {
        this.week = week;
    }

    /**
     * Gets the week number.
     *
     * @return Week number on transfer date
     */
    public final int getWeek() {
        return week;
    }
}
