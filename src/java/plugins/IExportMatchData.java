package plugins;

import java.util.Hashtable;

public interface IExportMatchData {
	public IMatchDetails getDetails();
	public IMatchKurzInfo getInfo();
	public Hashtable getPlayers();
	public void setDetails(IMatchDetails details);
	public void setInfo(IMatchKurzInfo info);
	public void setPlayers(Hashtable hashtable);

}
