package plugins;

public interface ISubstitution {

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

	// Order type
	public static final byte SUBSTITUTION = 0;
	public static final byte BEHAVIOUR = 1;
	public static final byte POSITION_SWAP = 2;

	public int getPlayerOrderId();

	public void setPlayerOrderId(int id);

	public int getPlayerIn();

	public void setPlayerIn(int playerIn);

	public int getPlayerOut();

	public void setPlayerOut(int playerOut);

	public byte getOrderType();

	public void setOrderType(byte orderType);

	public byte getMatchMinuteCriteria();

	public void setMatchMinuteCriteria(byte matchMinuteCriteria);

	public byte getPos();

	public void setPos(byte pos);

	public byte getBehaviour();

	public void setBehaviour(byte behaviour);

	public byte getCard();

	public void setCard(byte card);

	public byte getStanding();

	public void setStanding(byte standing);

	/**
	 * Merges the data from the given <code>ISubstitution</code> into this
	 * <code>ISubstitution</code>. This method should be used e.g. when a model
	 * has to be updated with data from a different <code>ISubstitution</code>
	 * instance but and object identity has to be preserved.
	 * 
	 * @param other
	 *            the <code>ISubstitution</code> to get the data from.
	 */
	public void merge(ISubstitution other);
}
