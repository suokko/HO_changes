package ho.module.nthrf;

/**
 * Simple data class for position related information of a player.
 */
public class NtPlayerPosition {

	private long playerId;
	private String name;

	private int roleId = 0;
	private int positionCode = 1;
	private int behaviour = 0;

	private float ratingStars = 0;

	public long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRoleId() {
		return roleId;
	}
	public int getPositionCode() {
		return positionCode;
	}
	public int getBehaviour() {
		return behaviour;
	}
	public float getRatingStars() {
		return ratingStars;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public void setPositionCode(int positionCode) {
		this.positionCode = positionCode;
	}
	public void setBehaviour(int behaviour) {
		this.behaviour = behaviour;
	}
	public void setRatingStars(float ratingStars) {
		this.ratingStars = ratingStars;
	}
	/**
	 * Overwritten toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer("--- NtPlayerPosition - " + name + " (" + playerId + ") ---");
		sb.append("\n\trole / posi / behaviour: " + roleId + " / " + positionCode + " / " + behaviour);
		sb.append("\n\tRatingstars: " + ratingStars);
		return sb.toString();
	}
}
