package ho.module.lineup.substitution.model;

/**
 * GoalDiffCriteria for substitutions.
 * 
 * @author kruescho
 *
 */
public enum Standing {

//	public static final byte ANY_STANDING = -1;
//	public static final byte MATCH_IS_TIED = 0;
//	public static final byte IN_THE_LEAD = 1;
//	public static final byte DOWN = 2;
//	public static final byte IN_THE_LEAD_BY_MORE_THAN_ONE = 3;
//	public static final byte DOWN_BY_MORE_THAN_ONE = 4;
//	public static final byte NOT_DOWN = 5;
//	public static final byte NOT_IN_THE_LEAD = 6;
//	public static final byte IN_THE_LEAD_BY_MORE_THAN_TWO = 7;
//	public static final byte DOWN_BY_MORE_THAN_TWO = 8;
	
	ANY_STANDING((byte) -1),
	MATCH_IS_TIED((byte) 0),
	IN_THE_LEAD((byte) 1),
	DOWN((byte) 2),
	IN_THE_LEAD_BY_MORE_THAN_ONE((byte) 3),
	DOWN_BY_MORE_THAN_ONE((byte) 4),
	NOT_DOWN((byte) 5),
	NOT_IN_THE_LEAD((byte) 6),
	IN_THE_LEAD_BY_MORE_THAN_TWO((byte) 7),
	DOWN_BY_MORE_THAN_TWO((byte) 8);

	private final byte id;

	private Standing(byte id) {
		this.id = id;
	}

	public byte getId() {
		return this.id;
	}

	public static Standing getById(byte id) {
		for (Standing standing : Standing.values()) {
			if (standing.getId() == id) {
				return standing;
			}
		}
		return null;
	}
}
