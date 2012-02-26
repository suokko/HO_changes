package ho.module.lineup.substitution;

import plugins.ISpieler;

public class PlayerPositionItem {
	private Integer position;
	private ISpieler player;

	PlayerPositionItem(Integer pos, ISpieler player) {
		this.player = player;
		this.position = pos;
	}

	public Integer getPosition() {
		return position;
	}

	public ISpieler getSpieler() {
		return this.player;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (this.position != null) {
			builder.append(Lookup.getPosition(this.position.byteValue()));
			builder.append(" - ");
		}
		if (this.player != null) {
			builder.append(this.player.getName());
		}

		return builder.toString();
	}
}