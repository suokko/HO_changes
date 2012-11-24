package ho.module.matches;

import ho.core.db.DBManager;
import ho.core.model.match.MatchKurzInfo;
import ho.core.model.match.Matchdetails;

import java.util.ArrayList;
import java.util.List;

public class MatchesModel {

	private MatchKurzInfo match;
	private Matchdetails details;
	private List<MatchModelChangeListener> listeners = new ArrayList<MatchModelChangeListener>();

	public void setMatch(MatchKurzInfo match) {
		if (this.match != match) {
			this.match = match;
			this.details = null;
			fireMatchChanged();
		}
	}

	public MatchKurzInfo getMatch() {
		return this.match;
	}

	public Matchdetails getDetails() {
		if (this.details == null && this.match != null) {
			this.details = DBManager.instance().getMatchDetails(this.match.getMatchID());
		}
		return this.details;
	}

	public void addMatchModelChangeListener(MatchModelChangeListener listener) {
		if (!this.listeners.contains(listener)) {
			this.listeners.add(listener);
		}
	}

	public void removeMatchModelChangeListener(MatchModelChangeListener listener) {
		this.listeners.remove(listener);
	}

	private void fireMatchChanged() {
		for (int i = this.listeners.size() - 1; i >= 0; i--) {
			this.listeners.get(i).matchChanged();
		}
	}
}
