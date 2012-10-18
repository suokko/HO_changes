package ho.module.specialEvents;

import ho.core.model.match.MatchHighlight;

public class MatchLine {

	private Match match;
	private MatchHighlight matchHighlight;
	private boolean isMatchHeaderLine;

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public boolean isMatchHeaderLine() {
		return isMatchHeaderLine;
	}

	public void setMatchHeaderLine(boolean isMatchHeaderLine) {
		this.isMatchHeaderLine = isMatchHeaderLine;
	}

	public MatchHighlight getMatchHighlight() {
		return matchHighlight;
	}

	public void setMatchHighlight(MatchHighlight matchHighlight) {
		this.matchHighlight = matchHighlight;
	}

}
