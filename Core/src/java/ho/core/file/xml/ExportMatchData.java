package ho.core.file.xml;

import ho.module.matches.model.MatchKurzInfo;
import ho.module.matches.model.Matchdetails;

import java.util.Hashtable;

import plugins.ISpieler;


public class ExportMatchData {
	
	private Hashtable<Integer,ISpieler> players;
	private Matchdetails details;
	private MatchKurzInfo info;

	public Matchdetails getDetails() {
		return details;
	}

	public MatchKurzInfo getInfo() {
		return info;
	}

	public Hashtable<Integer,ISpieler> getPlayers() {
		return players;
	}

	public void setDetails(Matchdetails details) {
		this.details = details;
	}

	public void setInfo(MatchKurzInfo info) {
		this.info = info;
	}

	public void setPlayers(Hashtable<Integer,ISpieler> hashtable) {
		players = hashtable;
	}

}
