package ho.module.lineup.substitution.model;

/**
 * GoalDiffCriteria for substitutions.
 * 
 * @author kruescho
 *
 */
public enum Standing {

	GOAL_ANY((byte) -1),
	GOAL_TIED((byte) 0),
	GOAL_LEAD((byte) 1),
	GOAL_DOWN((byte) 2),
	GOAL_LEAD_MT1((byte) 3),
	GOAL_DOWN_MT1((byte) 4),
	GOAL_NOT_DOWN((byte) 5),
	GOAL_NOT_LEAD((byte) 6),
	GOAL_LEAD_MT2((byte) 7),
	GOAL_DOWN_MT2((byte) 8);

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
