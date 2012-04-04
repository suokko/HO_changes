package ho.core.file.xml;

import ho.core.model.Spieler;
import ho.module.matches.model.MatchKurzInfo;
import ho.module.matches.model.Matchdetails;

import java.util.Hashtable;


public class ExportMatchData {
	
	private Hashtable<Integer,Spieler> players;
	private Matchdetails details;
	private MatchKurzInfo info;

	public Matchdetails getDetails() {
		return details;
	}

	public MatchKurzInfo getInfo() {
		return info;
	}

	public Hashtable<Integer,Spieler> getPlayers() {
		return players;
	}

	public void setDetails(Matchdetails details) {
		this.details = details;
	}

	public void setInfo(MatchKurzInfo info) {
		this.info = info;
	}

	public void setPlayers(Hashtable<Integer,Spieler> hashtable) {
		players = hashtable;
	}

}
