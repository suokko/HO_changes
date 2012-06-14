package ho.core.gui.theme;





/**
 * Constants for Colors used in HO.
 * Plugins can use them too.
 * @see IGui.getColor(String key)  
 */
public interface HOColorName {

	public static final String PANEL_BG 				= "panel.bg";
	public static final String PANEL_BORDER				= "panel.border";
	public static final String LABEL_FG 				= "label.fg";
	public static final String LABEL_ERROR_FG 			= "label.error.fg";
	public static final String LABEL_SUCCESS_FG 		= "label.success.fg";
	public static final String LABEL_ONGREEN_FG 		= "label.onGreen.fg";
	public static final String LIST_FG 					= "list.fg";
	public static final String LIST_CURRENT_FG 			= "list.current.fg";
	public static final String LIST_SELECTION_BG 		= "list.selection.bg";
	public static final String BUTTON_BG 				= "button.bg";
	public static final String BUTTON_ASSIST_BG			= "button.assist.bg";
	public static final String TABLE_SELECTION_BG		= "table.selection.bg";
	public static final String TABLE_SELECTION_FG		= "table.selection.fg";
	
	public static final String TABLEENTRY_BG 			= "tableEntry.bg";
	public static final String TABLEENTRY_FG 			= "tableEntry.fg";
	public static final String TABLEENTRY_IMPROVEMENT_FG = "tableEntry.improvement.fg";
	public static final String TABLEENTRY_DECLINE_FG 	= "tableEntry.decline.fg";
	public static final String SKILLENTRY2_BG 			= "skillEntry2.bg";

	public static final String PLAYER_POS_BG 			= "player.pos.bg";
	public static final String PLAYER_SUBPOS_BG 		= "player.subpos.bg";
	public static final String PLAYER_OLD_FG 			= "player.old.fg";
	public static final String PLAYER_SKILL_BG 			= "player.skill.bg";
	public static final String PLAYER_SKILL_SPECIAL_BG 	= "player.skill.special.bg";
	public static final String TEAM_FG 					= "team.fg";
	
	public static final String MATCHTYPE_BG 				= "matchtype.bg";
	public static final String MATCHTYPE_LEAGUE_BG 			= "matchtype.league.bg";
	public static final String MATCHTYPE_QUALIFIKATION_BG 	= "matchtype.qualification.bg";
	public static final String MATCHTYPE_CUP_BG				= "matchtype.cup.bg";
	public static final String MATCHTYPE_FRIENDLY_BG 		= "matchtype.friendly.bg";
	public static final String MATCHTYPE_INT_BG 			= "matchtype.int.normal.bg";
	public static final String MATCHTYPE_MASTERS_BG 		= "matchtype.masters.bg";
	public static final String MATCHTYPE_INTFRIENDLY_BG 	= "matchtype.intFriendly.bg";
	public static final String MATCHTYPE_NATIONAL_BG 		= "matchtype.national.bg";
	public static final String MATCHTYPE_TOURNAMENT_GROUP_BG		= "matchtype.tourneyGroup.bg";
	public static final String MATCHTYPE_TOURNAMENT_FINALS_BG 		= "matchtype.tourneyFinals.bg";
	
	
	public static final String LEAGUEHISTORY_LINE1_FG 	= "leaguehistory.line1.fg";
	public static final String LEAGUEHISTORY_LINE2_FG 	= "leaguehistory.line2.fg";
	public static final String LEAGUEHISTORY_LINE3_FG 	= "leaguehistory.line3.fg";
	public static final String LEAGUEHISTORY_LINE4_FG 	= "leaguehistory.line4.fg";
	public static final String LEAGUEHISTORY_LINE5_FG 	= "leaguehistory.line5.fg";
	public static final String LEAGUEHISTORY_LINE6_FG 	= "leaguehistory.line6.fg";
	public static final String LEAGUEHISTORY_LINE7_FG 	= "leaguehistory.line7.fg";
	public static final String LEAGUEHISTORY_LINE8_FG 	= "leaguehistory.line8.fg";
	public static final String LEAGUEHISTORY_CROSS_FG 	= "leaguehistory.cross.fg";
	public static final String LEAGUEHISTORY_GRID_FG  	= "leaguehistory.grid.fg";
	
	public static final String LEAGUE_TITLE_BG 			= "league.title.bg";
	public static final String LEAGUE_PROMOTED_BG 		= "league.promoted.bg";
	public static final String LEAGUE_RELEGATION_BG 	= "league.relegation.bg";
	public static final String LEAGUE_DEMOTED_BG 		= "league.demoted.bg";
	public static final String LEAGUE_BG 				= "league.bg";
	public static final String LEAGUE_FG 				= "league.fg";
	
	public static final String SHIRT_KEEPER 			= "shirt.kee";
	public static final String SHIRT_CENTRALDEFENCE 	= "shirt.cd";
	public static final String SHIRT_WINGBACK 			= "shirt.wb";
	public static final String SHIRT_MIDFIELD 			= "shirt.mid";
	public static final String SHIRT_WING 				= "shirt.win";
	public static final String SHIRT_FORWARD 			= "shirt.for";
	public static final String SHIRT_SUBKEEPER			= "shirt.subKee";
	public static final String SHIRT_SUBDEFENCE 		= "shirt.subDef";
	public static final String SHIRT_SUBMIDFIELD 		= "shirt.subMid";
	public static final String SHIRT_SUBWING 			= "shirt.subWin";
	public static final String SHIRT_SUBFORWARD 		= "shirt.subFor";
	public static final String SHIRT 					= "shirt";
	
	public static final String STAT_LEADERSHIP 			= "stat.leadership";
	public static final String STAT_EXPERIENCE 			= "stat.experience";;
	public static final String STAT_FORM 				= "stat.form";;
	public static final String STAT_LOYALTY				= "stat.loyalty";;
	public static final String STAT_STAMINA 			= "stat.stamina";
	public static final String STAT_KEEPER 				= "stat.keeper";
	public static final String STAT_DEFENDING 			= "stat.defending";
	public static final String STAT_PLAYMAKING 			= "stat.playmaking";
	public static final String STAT_PASSING 			= "stat.passing";
	public static final String STAT_WINGER 				= "stat.winger";
	public static final String STAT_SCORING 			= "stat.scoring";
	public static final String STAT_SET_PIECES 			= "stat.setPieces";
	
	public static final String STAT_CASH 				= "stat.cash";
	public static final String STAT_WINLOST 			= "stat.winLost";
	public static final String STAT_INCOMESUM 			= "stat.incomeSum";
	public static final String STAT_COSTSUM 			= "stat.costSum";
	public static final String STAT_INCOMESPECTATORS 	= "stat.incomeSpectators";
	public static final String STAT_INCOMESPONSORS		= "stat.incomeSponsors";
	public static final String STAT_INCOMEFINANCIAL		= "stat.incomeFinancial";
	public static final String STAT_INCOMETEMPORARY		= "stat.incomeTemporary";
	public static final String STAT_COSTARENA			= "stat.costArena";
	public static final String STAT_COSTSPLAYERS		= "stat.costsPlayers";
	public static final String STAT_COSTFINANCIAL		= "stat.costFinancial";
	public static final String STAT_COSTTEMPORARY		= "stat.costTemporary";
	public static final String STAT_COSTSTAFF			= "stat.costStaff";
	public static final String STAT_COSTSYOUTH			= "stat.costsYouth";
	public static final String STAT_FANS				= "stat.fans";
	public static final String STAT_MARKETVALUE			= "stat.marketValue";
	public static final String STAT_RATING				= "stat.rating";
	public static final String STAT_WAGE				= "stat.wage";
	public static final String STAT_RATING2				= "stat.rating2";
	public static final String STAT_TOTAL				= "stat.total";
	public static final String STAT_MOOD				= "stat.mood";
	public static final String STAT_CONFIDENCE			= "stat.confidence";
	public static final String STAT_HATSTATS			= "stat.hatstats";
	public static final String STAT_LODDAR				= "stat.loddar";
	
	public static final String MATCHHIGHLIGHT_FAILED_FG	= "matchHighlight.failed.fg";
	
	//lineup
	
	public static final String SEL_OVERLAY_SELECTION_BG	= "selectorOverlay.selected.bg";
	public static final String SEL_OVERLAY_BG 			= "selectorOverlay.bg";
	public static final String LINEUP_POS_MIN_BG 		= "lineup.pos.min.bg";
	public static final String LINEUP_POS_MIN_BORDER	= "lineup.pos.min.border";
	public static final String SUBST_CHANGED_VALUE_BG   = "substitution.changed.value.bg";
	
	//1.431
	public static final String MATCHDETAILS_PROGRESSBAR_GREEN = "matchdetails.progressbar.green";
	public static final String MATCHDETAILS_PROGRESSBAR_RED = "matchdetails.progressbar.red";
	
	//1.432
	// Team Analyzer
	public static final String TA_TEAM_LEAGUE_NEXT = "teamanalyzer.teamlist.league";
	public static final String TA_TEAM_CUP_NEXT = "teamanalyzer.teamlist.cup";
	public static final String TA_TEAM_TOURNAMENT_NEXT = "teamanalyzer.teamlist.nexttournament";
	public static final String TA_TEAM_TOURNAMENT = "teamanalyzer.teamlist.tournament";
	
	
}
