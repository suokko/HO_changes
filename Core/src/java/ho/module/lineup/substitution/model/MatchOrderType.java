package ho.module.lineup.substitution.model;

/**
 * Enum for the three order types used in substitutions/orders for the lineup.
 * 
 */
public enum MatchOrderType {

	// In the HT-API, position swap has id 3, normal substitution and behaviour
	// change both have id 1.
	SUBSTITUTION((byte) 1),
	NEW_BEHAVIOUR((byte) 1),
	POSITION_SWAP((byte) 3);

	private final byte id;

	private MatchOrderType(byte id) {
		this.id = id;
	}

	/**
	 * Gets the HT matchOrderTypeId.
	 * 
	 * @return the matchOrderTypeId like defined by the CHPP API.
	 */
	public byte getId() {
		return this.id;
	}
}
