package ho.module.lineup.substitution;

import plugins.ISubstitution;
import plugins.MatchOrderType;

/**
 * A class holding information about substitutions and order changes
 * 
 * @author blaghaid
 * 
 */
public class Substitution implements plugins.ISubstitution {

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
	 * {@inheritDoc}
	 */
	public void merge(ISubstitution other) {
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
