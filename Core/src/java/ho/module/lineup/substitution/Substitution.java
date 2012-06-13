package ho.module.lineup.substitution;

import ho.module.lineup.substitution.model.GoalDiffCriteria;
import ho.module.lineup.substitution.model.MatchOrderType;
import ho.module.lineup.substitution.model.RedCardCriteria;


/**
 * A class holding information about substitutions and order changes
 * 
 * @author blaghaid
 * 
 */
public class Substitution {
	
	private int playerOrderID = -1;
	private int playerIn = -1;
	private int playerOut = -1;
	private MatchOrderType orderType = MatchOrderType.SUBSTITUTION;
	private byte matchMinuteCriteria = -1;
	private byte pos = -1;
	private byte behaviour = -1;
	private RedCardCriteria card = RedCardCriteria.IGNORE;
	private GoalDiffCriteria standing = GoalDiffCriteria.ANY_STANDING;

	public Substitution(int playerOrderID, int playerIn, int playerOut, MatchOrderType orderType,
			byte matchMinuteCriteria, byte pos, byte behaviour, RedCardCriteria card, GoalDiffCriteria standing) {
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
		setPlayerIn(other.getPlayerIn());
		setPlayerOrderId(other.getPlayerOrderId());
		setPlayerOut(other.getPlayerOut());
		setPos(other.getPos());
		setStanding(other.getStanding());
	}

}
