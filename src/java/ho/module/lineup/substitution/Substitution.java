package ho.module.lineup.substitution;


/**
 * A class holding information about substitutions and order changes
 * 
 * @author blaghaid
 * 
 */
public class Substitution {

	// See the match order API for meanings

	// Red card criteria
	public static final byte IGNORE_RED_CARD_STATUSIgnore = -1;
	public static final byte MY_PLAYER_RED_CARDED = 1;
	public static final byte OPPONENT_PLAYER_RED_CARDED = 2;
	public static final byte MY_CENTRAL_DEFENDER_RED_CARDED = 11;
	public static final byte MY_MIDFIELDER_RED_CARDED = 12;
	public static final byte MY_FORWARD_RED_CARDED = 13;
	public static final byte MY_WING_BACK_RED_CARDED = 14;
	public static final byte MY_WINGER_RED_CARDED = 15;
	public static final byte OPPONENT_CENTRAL_DEFENDER_RED_CARDED = 21;
	public static final byte OPPONENT_MIDFIELDER_RED_CARDED = 22;
	public static final byte OPPONENT_FORAWARD_RED_CARDED = 23;
	public static final byte OPPONENT_WING_BACK_RED_CARDED = 24;
	public static final byte OPPONENT_WINGER_RED_CARDED = 25;

	// GoalDiffCriteria
	public static final byte ANY_STANDING = -1;
	public static final byte MATCH_IS_TIED = 0;
	public static final byte IN_THE_LEAD = 1;
	public static final byte DOWN = 2;
	public static final byte IN_THE_LEAD_BY_MORE_THAN_ONE = 3;
	public static final byte DOWN_BY_MORE_THAN_ONE = 4;
	public static final byte NOT_DOWN = 5;
	public static final byte NOT_IN_THE_LEAD = 6;
	public static final byte IN_THE_LEAD_BY_MORE_THAN_TWO = 7;
	public static final byte DOWN_BY_MORE_THAN_TWO = 8;
	
	private int playerOrderID = -1;
	private int playerIn = -1;
	private int playerOut = -1;
	private MatchOrderType orderType = MatchOrderType.SUBSTITUTION;
	private byte matchMinuteCriteria = -1;
	private byte pos = -1;
	private byte behaviour = -1;
	private byte card = -1;
	private byte standing = -1;

	public Substitution(int playerOrderID, int playerIn, int playerOut, MatchOrderType orderType,
			byte matchMinuteCriteria, byte pos, byte behaviour, byte card, byte standing) {
		super();

		this.playerOrderID = playerOrderID;
		this.playerIn = playerIn;
		this.playerOut = playerOut;
		this.orderType = orderType;
		this.matchMinuteCriteria = matchMinuteCriteria;
		this.pos = pos;
		this.behaviour = behaviour;
		this.card = card;
		this.standing = standing;
	}

	public Substitution(int id) {
		this.playerOrderID = id;

	}

	public Substitution() {
	}

	public int getPlayerOrderId() {
		return playerOrderID;
	}

	public void setPlayerOrderId(int id) {
		this.playerOrderID = id;
	}

	public int getPlayerIn() {
		return playerIn;
	}

	public void setPlayerIn(int playerIn) {
		this.playerIn = playerIn;
	}

	public int getPlayerOut() {
		return playerOut;
	}

	public void setPlayerOut(int playerOut) {
		this.playerOut = playerOut;
	}

	public MatchOrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(MatchOrderType orderType) {
		this.orderType = orderType;
	}

	public byte getMatchMinuteCriteria() {
		return matchMinuteCriteria;
	}

	public void setMatchMinuteCriteria(byte matchMinuteCriteria) {
		this.matchMinuteCriteria = matchMinuteCriteria;
	}

	public byte getPos() {
		return pos;
	}

	public void setPos(byte pos) {
		this.pos = pos;
	}

	public byte getBehaviour() {
		return behaviour;
	}

	public void setBehaviour(byte behaviour) {
		this.behaviour = behaviour;
	}

	public byte getCard() {
		return card;
	}

	public void setCard(byte card) {
		this.card = card;
	}

	public byte getStanding() {
		return standing;
	}

	public void setStanding(byte standing) {
		this.standing = standing;
	}

	/**
	 * Merges the data from the given <code>Substitution</code> into this
	 * <code>Substitution</code>. This method should be used e.g. when a model
	 * has to be updated with data from a different <code>Substitution</code>
	 * instance but and object identity has to be preserved.
	 * 
	 * @param other
	 *            the <code>Substitution</code> to get the data from.
	 */
	public void merge(Substitution other) {
		setBehaviour(other.getBehaviour());
		setCard(other.getCard());
		setMatchMinuteCriteria(other.getMatchMinuteCriteria());
		setOrderType(other.getOrderType());
		setPlayerIn(other.getPlayerIn());
		setPlayerOrderId(other.getPlayerOrderId());
		setPlayerOut(other.getPlayerOut());
		setPos(other.getPos());
		setStanding(other.getStanding());
	}

}
