package ho.core.file.xml;

import java.util.Hashtable;

import plugins.IExportMatchData;
import plugins.IMatchDetails;
import plugins.IMatchKurzInfo;
import plugins.ISpieler;


public class ExportMatchData implements IExportMatchData {
	
	private Hashtable<Integer,ISpieler> players;
	private IMatchDetails details;
	private IMatchKurzInfo info;

	public IMatchDetails getDetails() {
		return details;
	}

	public IMatchKurzInfo getInfo() {
		return info;
	}

	public Hashtable<Integer,ISpieler> getPlayers() {
		return players;
	}

	public void setDetails(IMatchDetails details) {
		this.details = details;
	}

	public void setInfo(IMatchKurzInfo info) {
		this.info = info;
	}

	public void setPlayers(Hashtable<Integer,ISpieler> hashtable) {
		players = hashtable;
	}

}
