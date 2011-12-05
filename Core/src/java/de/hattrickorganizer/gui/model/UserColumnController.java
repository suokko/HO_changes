package de.hattrickorganizer.gui.model;

import java.util.Vector;

import de.hattrickorganizer.database.DBZugriff;
/**
 * Controller for the UserColumns.
 * Create columns and managed the models
 * @author Thorsten Dietz
 * @since 1.36
 *
 */
public final class UserColumnController {

	/** singleton **/
	private static UserColumnController columnController = new UserColumnController();
	
	/** model for matches table **/
	private  MatchesColumnModel matchesColumnModel			= null;
	
	/** model for player overview **/
	private  PlayerOverviewModel playerOverviewColumnModel	= null;
	
	/** model for lineup table **/
	private  LineupColumnModel lineupColumnModel			= null;
	
	/** model for player analysis **/
	private PlayerAnalysisModel playerAnalysis1Model 		= null;
	
	/** model for player analysis **/
	private PlayerAnalysisModel playerAnalysis2Model 		= null;
	
	/**
	 * constructor
	 *
	 */
	private UserColumnController(){
		
	}
	/**
	 * singelton
	 * @return UserColumnController
	 */
	public static UserColumnController instance(){
		return columnController;
	}
	
	/**
	 * load all models from db
	 *
	 */
	public final void load(){
		final DBZugriff dbZugriff = DBZugriff.instance();
		
		dbZugriff.loadHOColumModel( getMatchesModel() );
		dbZugriff.loadHOColumModel( getPlayerOverviewModel() );
		dbZugriff.loadHOColumModel( getLineupModel() );
		dbZugriff.loadHOColumModel( getAnalysis1Model() );
		dbZugriff.loadHOColumModel( getAnalysis2Model() );
	}
	
	/**
	 * 
	 * @return PlayerAnalysisModel
	 */
	public final PlayerAnalysisModel getAnalysis1Model(){
		if(playerAnalysis1Model == null)
			playerAnalysis1Model = new PlayerAnalysisModel(4,1);
		
		return playerAnalysis1Model;
	}
	
	/**
	 * 
	 * @return PlayerAnalysisModel
	 */
	public final PlayerAnalysisModel getAnalysis2Model(){
		if(playerAnalysis2Model == null)
			playerAnalysis2Model = new PlayerAnalysisModel(5,2);
		
		return playerAnalysis2Model;
	}
	/**
	 * 
	 * @return MatchesColumnModel
	 */
	public final MatchesColumnModel getMatchesModel(){
		if(matchesColumnModel == null)
			matchesColumnModel = new MatchesColumnModel(1);
		
		return matchesColumnModel;
	}
	
	/**
	 * 
	 * @return PlayerOverviewModel
	 */
	public final PlayerOverviewModel getPlayerOverviewModel(){
		if(playerOverviewColumnModel == null){
			playerOverviewColumnModel = new PlayerOverviewModel(2);
		}
		return playerOverviewColumnModel;
	}
	
	/**
	 * 
	 * @return LineupColumnModel
	 */
	public final LineupColumnModel getLineupModel(){
		if(lineupColumnModel == null){
			lineupColumnModel = new LineupColumnModel(3);
			
		}
		return lineupColumnModel;
	}
	

	/**
	 * return all model as Vector
	 * @return
	 */
	public Vector<HOColumnModel> getAllModels(){
		Vector<HOColumnModel> v = new Vector<HOColumnModel>();
		
		v.add( getPlayerOverviewModel() );
		v.add( getLineupModel() );
		v.add( getAnalysis1Model() );
		v.add( getAnalysis2Model() );
		return v;
	}
	

	

	

	

}
