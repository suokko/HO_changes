package ho.module.lineup.substitution.model;

/**
 * A class holding information about substitutions and order changes
 * 
 * @author blaghaid
 * 
 */
public class Substitution {

	private int playerOrderID = -1;
	private int objectPlayerID = -1;
	private int subjectPlayerID = -1;
	private MatchOrderType orderType = MatchOrderType.SUBSTITUTION;
	private byte matchMinuteCriteria = -1;
	private byte pos = -1;
	private byte behaviour = -1;
	private RedCardCriteria card = RedCardCriteria.IGNORE;
	private GoalDiffCriteria standing = GoalDiffCriteria.ANY_STANDING;

	public Substitution(int playerOrderID, int playerIn, int subjectPlayerID,
			MatchOrderType orderType, byte matchMinuteCriteria, byte pos, byte behaviour,
			RedCardCriteria card, GoalDiffCriteria standing) {
		this.playerOrderID = playerOrderID;
		this.objectPlayerID = playerIn;
		this.subjectPlayerID = subjectPlayerID;
		this.orderType = orderType;
		this.matchMinuteCriteria = matchMinuteCriteria;
		this.pos = pos;
		this.behaviour = behaviour;
		this.card = card;
		this.standing = standing;
	}

	public Substitution() {
	}

	public int getPlayerOrderId() {
		return playerOrderID;
	}

	public void setPlayerOrderId(int id) {
		this.playerOrderID = id;
	}

	/**
	 * Gets the ID of the the player entering, or if position swap, the player
	 * to swap with.
	 * 
	 * @return the id of the player entering
	 */
	public int getObjectPlayerID() {
		return objectPlayerID;
	}

	public void setObjectPlayerID(int objectPlayerID) {
		this.objectPlayerID = objectPlayerID;
	}

	/**
	 * Gets the ID of the affected player. If substitution: the player leaving.
	 * If behaviour change: the player changing his behaviour. If position swap:
	 * the first player that will change his position.
	 * 
	 * @return the id of the affected player.
	 */
	public int getSubjectPlayerID() {
		return subjectPlayerID;
	}

	public void setSubjectPlayerID(int subjectPlayerID) {
		this.subjectPlayerID = subjectPlayerID;
		// to get conform with CHPP API (playerout==playerin if its a 
		// behaviour change)
		if (this.orderType == MatchOrderType.NEW_BEHAVIOUR) {
			this.objectPlayerID = subjectPlayerID;
		}
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

	public RedCardCriteria getRedCardCriteria() {
		return card;
	}

	public void setRedCardCriteria(RedCardCriteria card) {
		this.card = card;
	}

	public GoalDiffCriteria getStanding() {
		return standing;
	}

	public void setStanding(GoalDiffCriteria standing) {
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
		setRedCardCriteria(other.getRedCardCriteria());
		setMatchMinuteCriteria(other.getMatchMinuteCriteria());
		setOrderType(other.getOrderType());
		setObjectPlayerID(other.getObjectPlayerID());
		setPlayerOrderId(other.getPlayerOrderId());
		setSubjectPlayerID(other.getSubjectPlayerID());
		setPos(other.getPos());
		setStanding(other.getStanding());
	}

}
