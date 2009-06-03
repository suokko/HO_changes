package de.hattrickorganizer.logik.exporter;

import java.util.Hashtable;

import plugins.IExportMatchData;
import plugins.IMatchDetails;
import plugins.IMatchKurzInfo;


public class ExportMatchData implements IExportMatchData {
	
	private Hashtable players;
	private IMatchDetails details;
	private IMatchKurzInfo info;

	public IMatchDetails getDetails() {
		return details;
	}

	public IMatchKurzInfo getInfo() {
		return info;
	}

	public Hashtable getPlayers() {
		return players;
	}

	public void setDetails(IMatchDetails details) {
		this.details = details;
	}

	public void setInfo(IMatchKurzInfo info) {
		this.info = info;
	}

	public void setPlayers(Hashtable hashtable) {
		players = hashtable;
	}

}
