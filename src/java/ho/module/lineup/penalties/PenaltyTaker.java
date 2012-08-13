package ho.module.lineup.penalties;

import ho.core.constants.player.PlayerSkill;
import ho.core.model.player.Spieler;

public class PenaltyTaker {

	private Spieler player;
	private double ability;

	public PenaltyTaker() {
	}

	public PenaltyTaker(Spieler player) {
		this.player = player;
		this.ability = PenaltyUtils.getAbility(this);
	}

	public Spieler getPlayer() {
		return this.player;
	}

	public double getAbility() {
		return this.ability;
	}

	public double getScoring() {
		return ((double) this.player.getTorschuss())
				+ this.player.getSubskill4Pos(PlayerSkill.SCORING);
	}

	public double getSetPieces() {
		return ((double) this.player.getStandards())
				+ this.player.getSubskill4Pos(PlayerSkill.SET_PIECES);
	}

	public double getExperience() {
		return ((double) this.player.getErfahrung())
				+ this.player.getSubskill4Pos(PlayerSkill.EXPERIENCE);
	}
}
