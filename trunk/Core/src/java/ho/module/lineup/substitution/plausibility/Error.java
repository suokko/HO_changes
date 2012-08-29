package ho.module.lineup.substitution.plausibility;

public enum Error implements Problem {
	NEWBEHAVIOUR_PLAYER_MISSING("subs.plausibility.newbehaviour.playermissing"),
	SUBSTITUTION_PLAYER_MISSING("subs.plausibility.substitution.playermissing"),
	POSITIONSWAP_PLAYER_MISSING("subs.plausibility.positionswap.playermissing"),
	PLAYERIN_NOT_IN_LINEUP("subs.plausibility.playerIn.notInLineup"),
	PLAYEROUT_NOT_IN_LINEUP("subs.plausibility.playerOut.notInLineup"),
	PLAYEROUT_NOT_REAL("subs.plausibility.player.notReal"),
	PLAYERIN_NOT_REAL("subs.plausibility.player.notReal");

	private String languageKey;

	private Error(String languageKey) {
		this.languageKey = languageKey;
	}

	@Override
	public String getLanguageKey() {
		return this.languageKey;
	}
}
