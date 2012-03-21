package ho.core.file.xml;

import ho.module.matches.model.MatchKurzInfo;

import java.util.Hashtable;

import plugins.IMatchDetails;
import plugins.ISpieler;


public class ExportMatchData {
	
	private Hashtable<Integer,ISpieler> players;
	private IMatchDetails details;
	private MatchKurzInfo info;

	public IMatchDetails getDetails() {
		return details;
	}

	public MatchKurzInfo getInfo() {
		return info;
	}

	public Hashtable<Integer,ISpieler> getPlayers() {
		return players;
	}

	public void setDetails(IMatchDetails details) {
		this.details = details;
	}

	public void setInfo(MatchKurzInfo info) {
		this.info = info;
	}

	public void setPlayers(Hashtable<Integer,ISpieler> hashtable) {
		players = hashtable;
	}

}
