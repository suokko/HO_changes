package de.hattrickorganizer.model.lineup;

import plugins.ISubstitution;

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
	private byte orderType = -1;
	private byte matchMinuteCriteria = -1;
	private byte pos = -1;
	private byte behaviour = -1;
	private byte card = -1;
	private byte standing = -1;

	public Substitution(int playerOrderID, int playerIn, int playerOut, byte orderType,
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

	public byte getOrderType() {
		return orderType;
	}

	public void setOrderType(byte orderType) {
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

	public void setEmpty() {
		playerIn = -1;
		playerOut = -1;
		orderType = -1;
		matchMinuteCriteria = -1;
		pos = -1;
		behaviour = -1;
		card = -1;
		standing = -1;
	}

	public boolean isEmpty() {
		if (playerIn > 0) {
			return true;
		}
		return false;
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
