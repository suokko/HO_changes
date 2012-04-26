package ho.core.training;

public class TrainingPlayer {
	private String pName = "";
	private int pMinutesPlayedAsGK = 0;
	private int pMinutesPlayedAsWB = 0;
	private int pMinutesPlayedAsCD = 0;
	private int pMinutesPlayedAsW = 0;
	private int pMinutesPlayedAsIM = 0;
	private int pMinutesPlayedAsFW = 0;
	private int pMinutesPlayedAsSP = 0;
	private int pTotalMinutesPlayed = 0;

	public TrainingPlayer()
	{
	}
	public void setMinutesPlayedAsGK(int minutes)
	{
		pMinutesPlayedAsGK = minutes;
		pTotalMinutesPlayed += minutes;
	}
	public int getMinutesPlayedAsGK()
	{
		return pMinutesPlayedAsGK;
	}
	public void setMinutesPlayedAsWB(int minutes)
	{
		pMinutesPlayedAsWB = minutes;
		pTotalMinutesPlayed += minutes;
	}
	public int getMinutesPlayedAsWB()
	{
		return pMinutesPlayedAsWB;
	}
	public void setMinutesPlayedAsCD(int minutes)
	{
		pMinutesPlayedAsCD = minutes;
		pTotalMinutesPlayed += minutes;
	}
	public int getMinutesPlayedAsCD()
	{
		return pMinutesPlayedAsCD;
	}
	public void setMinutesPlayedAsW(int minutes)
	{
		pMinutesPlayedAsW = minutes;
		pTotalMinutesPlayed += minutes;
	}
	public int getMinutesPlayedAsW()
	{
		return pMinutesPlayedAsW;
	}
	public void setMinutesPlayedAsIM(int minutes)
	{
		pMinutesPlayedAsIM = minutes;
		pTotalMinutesPlayed += minutes;
	}
	public int getMinutesPlayedAsIM()
	{
		return pMinutesPlayedAsIM;
	}
	public void setMinutesPlayedAsFW(int minutes)
	{
		pMinutesPlayedAsFW = minutes;
		pTotalMinutesPlayed += minutes;
	}
	public int getMinutesPlayedAsFW()
	{
		return pMinutesPlayedAsFW;
	}
	public void setMinutesPlayedAsSP(int minutes)
	{
		pMinutesPlayedAsSP = minutes;
		pTotalMinutesPlayed += minutes;
	}
	public int getMinutesPlayedAsSP()
	{
		return pMinutesPlayedAsSP;
	}
	public int getMinutesPlayed()
	{
		return pTotalMinutesPlayed;
	}
	public String Name()
	{
		return pName;
	}
	public void Name(String sName)
	{
		pName = sName;
	}
	public boolean PlayerHasPlayed()
	{
		return pTotalMinutesPlayed > 0;
	}
}
