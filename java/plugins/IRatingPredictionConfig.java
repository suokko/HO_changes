package plugins;

public interface IRatingPredictionConfig {

    public static final int THISSIDE = IRatingPredictionParameter.THISSIDE;
    public static final int OTHERSIDE = IRatingPredictionParameter.OTHERSIDE;
    public static final int ALLSIDES = IRatingPredictionParameter.ALLSIDES;
    public static final int MIDDLE = IRatingPredictionParameter.MIDDLE;

    public String getPredictionName ();
    public int getPredictionType ();

    public IRatingPredictionParameter getCentralAttackParameters() ;

	public IRatingPredictionParameter getSideAttackParameters() ;

	public IRatingPredictionParameter getCentralDefenseParameters() ;

	public IRatingPredictionParameter getSideDefenseParameters() ;

	public IRatingPredictionParameter getMidfieldParameters() ;

	public IRatingPredictionParameter getPlayerStrengthParameters();
	
    public IRatingPredictionParameter getTacticsParameters();

}
