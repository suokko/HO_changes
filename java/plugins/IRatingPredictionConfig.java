package plugins;

public interface IRatingPredictionConfig {

	public IRatingPredictionParameter getCentralAttackParameters() ;

	public IRatingPredictionParameter getSideAttackParameters() ;

	public IRatingPredictionParameter getCentralDefenseParameters() ;

	public IRatingPredictionParameter getSideDefenseParameters() ;

	public IRatingPredictionParameter getMidfieldParameters() ;
	
}
