package ho.module.lineup.penalties;

import ho.core.model.player.Spieler;

public class PenaltyTaker implements Comparable<PenaltyTaker> {

	private Spieler player;
	private float ability;

	public PenaltyTaker() {
	}

	public PenaltyTaker(Spieler player) {
		this.player = player;
		this.ability = PenaltyUtils.getAbility(player);
	}

	public Spieler getPlayer() {
		return this.player;
	}

	public float getAbility() {
		return this.ability;
	}

	@Override
	public int compareTo(PenaltyTaker o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
