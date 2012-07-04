// %929884203:de.hattrickorganizer.net%
/*
 * ConvertXml2Hrf.java
 *
 * Created on 12. Januar 2004, 09:44
 */
package ho.core.file.xml;


import ho.core.constants.TeamConfidence;
import ho.core.constants.TeamSpirit;
import ho.core.constants.TrainingType;
import ho.core.constants.player.PlayerAggressiveness;
import ho.core.constants.player.PlayerAgreeability;
import ho.core.constants.player.PlayerHonesty;
import ho.core.constants.player.PlayerSpeciality;
import ho.core.model.match.MatchKurzInfo;
import ho.core.model.match.MatchLineup;
import ho.core.model.match.MatchLineupTeam;
import ho.core.model.match.MatchType;
import ho.core.model.match.Matchdetails;
import ho.core.model.player.ISpielerPosition;
import ho.core.net.MyConnector;
import ho.core.net.login.LoginWaitDialog;
import ho.core.util.HOLogger;
import ho.module.lineup.substitution.model.Substitution;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;



/**
 * Convert the necessary xml data into a HRF file.
 *
 * @author thomas.werth
 */
public class ConvertXml2Hrf {

    private Hashtable<?, ?> m_htArena;
    private Hashtable<?, ?> m_htClub;
    private Hashtable<?, ?> m_htEconomy;
    private Hashtable<?, ?> m_htLiga;
    private Map<?, ?> m_htNextLineup;
    private Hashtable<?, ?> m_htTeamdetails;
    private Hashtable<?, ?> m_htTraining;
    private Hashtable<?, ?> m_htWorld;
    private MatchLineup m_clLineUp;
    private MatchLineupTeam m_clTeam;
    private StringBuffer m_sHRFBuffer;

    //enthält eine Liste an Hashtable die je einen Spieler beschreiben
    private Vector<?> m_vSpieler;

    //MatchOrder
    private MatchKurzInfo[] m_aMatches;
    private int m_iLastAttitude;
    private int m_iLastTactic;

    /**
     * Create the HRF data and return it in one string. 
     */
    public final String createHrf(LoginWaitDialog waitDialog) throws Exception {
        //init
        m_sHRFBuffer = new StringBuffer();

        try {
            //Hashtable's füllen
        	final MyConnector mc = MyConnector.instance();
            waitDialog.setValue(5);
            m_htTeamdetails = new xmlTeamDetailsParser().parseTeamdetailsFromString(mc.getTeamdetails(-1));
            waitDialog.setValue(10);
            m_htClub = new XMLClubParser().parseClubFromString(mc.getVerein());
            waitDialog.setValue(15);
            m_htLiga = new xmlLeagueDetailsParser().parseLeagueDetailsFromString(mc.getLeagueDetails(),m_htTeamdetails.get("TeamID").toString());
            waitDialog.setValue(20);
            m_htWorld = new XMLWorldDetailsParser().parseWorldDetailsFromString(mc.getWorldDetails(Integer.parseInt(m_htTeamdetails.get("LeagueID").toString())),m_htTeamdetails.get("LeagueID").toString());
            waitDialog.setValue(25);
            m_clLineUp = new XMLMatchLineupParser().parseMatchLineupFromString(mc.getMatchLineup(-1,-1, MatchType.LEAGUE).toString());
            waitDialog.setValue(30);
            m_vSpieler = new xmlPlayersParser().parsePlayersFromString(mc.getPlayers());
            waitDialog.setValue(35);
            m_htEconomy = new xmlEconomyParser().parseEconomyFromString(mc.getEconomy());
            waitDialog.setValue(40);
            m_htTraining = new XMLTrainingParser().parseTrainingFromString(mc.getTraining());
            waitDialog.setValue(45);
            m_htArena = new XMLArenaParser().parseArenaFromString(mc.getArena());

            //MatchOrder
            waitDialog.setValue(50);
            m_aMatches = new XMLMatchesParser().parseMatchesFromString(mc.getMatches(Integer.parseInt(m_htTeamdetails.get("TeamID").toString()), false, true));
            waitDialog.setValue(52);

            // Automatisch alle MatchLineups runterladen
			for (int i = 0; (m_aMatches != null) && (i < m_aMatches.length); i++) {
				if (m_aMatches[i].getMatchStatus() == MatchKurzInfo.UPCOMING) {
					waitDialog.setValue(54);
					// Match is always from the normal system, and league will do the trick as the type.
					m_htNextLineup = XMLMatchOrderParser.parseMatchOrderFromString(mc.getMatchOrder(m_aMatches[i].getMatchID(), MatchType.LEAGUE));
					break;
				}
			}

            waitDialog.setValue(55);

            // Team ermitteln, für Ratings der Player wichtig
            if (m_clLineUp != null) {
                final Matchdetails md = new xmlMatchdetailsParser().parseMachtdetailsFromString(mc.getMatchdetails(m_clLineUp.getMatchID(), m_clLineUp.getMatchTyp()), null);

                if (m_clLineUp.getHeimId() == Integer.parseInt(m_htTeamdetails.get("TeamID").toString())) {
                    m_clTeam = (ho.core.model.match.MatchLineupTeam) m_clLineUp.getHeim();

                    if (md != null) {
                        m_iLastAttitude = md.getHomeEinstellung();
                        m_iLastTactic = md.getHomeTacticType();
                    }
                } else {
                    m_clTeam = (ho.core.model.match.MatchLineupTeam) m_clLineUp.getGast();

                    if (md != null) {
                        m_iLastAttitude = md.getGuestEinstellung();
                        m_iLastTactic = md.getGuestTacticType();
                    }
                }
                m_clTeam.getTeamID();
            }

            //Abschnitte erstellen   
            waitDialog.setValue(60);

            //basics
            createBasics();
            waitDialog.setValue(65);

            //Liga
            createLeague();
            waitDialog.setValue(70);

            //Club
            createClub();
            waitDialog.setValue(75);

            //team
            createTeam();
            waitDialog.setValue(80);

            //lineup
            m_sHRFBuffer.append(createLineUp(String.valueOf(m_htTeamdetails.get("TrainerID")), m_htNextLineup));
            waitDialog.setValue(85);

            //economy  
            createEconemy();
            waitDialog.setValue(90);

            //Arena
            createArena();
            waitDialog.setValue(93);

            //players
            createPlayers();
            waitDialog.setValue(96);

            //xtra Data
            createWorld();
            waitDialog.setValue(99);

            //lineup from the last match
            createLastLineUp();
            waitDialog.setValue(100);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"convertxml2hrf: Exception: " + e);
            HOLogger.instance().log(getClass(),e);
            throw new Exception(e);
        }

        //dialog zum Saven anzeigen
        //speichern
        //writeHRF( dateiname );
        return m_sHRFBuffer.toString();
    }

    /**
     * Create the arena data.
     */
    protected final void createArena() throws Exception {
        m_sHRFBuffer.append("[arena]").append('\n');
        m_sHRFBuffer.append("arenaname=").append(m_htArena.get("ArenaName")).append('\n');
        m_sHRFBuffer.append("arenaid=").append(m_htArena.get("ArenaID")).append('\n');
        m_sHRFBuffer.append("antalStaplats=").append(m_htArena.get("Terraces")).append('\n');
        m_sHRFBuffer.append("antalSitt=").append(m_htArena.get("Basic")).append('\n');
        m_sHRFBuffer.append("antalTak=").append(m_htArena.get("Roof")).append('\n');
        m_sHRFBuffer.append("antalVIP=").append(m_htArena.get("VIP")).append('\n');
        m_sHRFBuffer.append("seatTotal=").append(m_htArena.get("Total")).append('\n');
        m_sHRFBuffer.append("expandingStaplats=").append(m_htArena.get("ExTerraces")).append('\n');
        m_sHRFBuffer.append("expandingSitt=").append(m_htArena.get("ExBasic")).append('\n');
        m_sHRFBuffer.append("expandingTak=").append(m_htArena.get("ExRoof")).append('\n');
        m_sHRFBuffer.append("expandingVIP=").append(m_htArena.get("ExVIP")).append('\n');
        m_sHRFBuffer.append("expandingSseatTotal=").append(m_htArena.get("ExTotal")).append('\n');
        m_sHRFBuffer.append("isExpanding=").append(m_htArena.get("isExpanding")).append('\n');

        //Achtung bei keiner Erweiterung = 0!
        m_sHRFBuffer.append("ExpansionDate=").append(m_htArena.get("ExpansionDate")).append('\n');
    }

    ////////////////////////////////////////////////////////////////////////////////
    //Helper
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * Create the basic data.
     */
    protected final void createBasics() throws Exception {
        m_sHRFBuffer.append("[basics]\n");
        m_sHRFBuffer.append("application=HO\n");
        m_sHRFBuffer.append("appversion=").append(ho.HO.VERSION).append('\n');
        m_sHRFBuffer.append("date=").append(m_htTeamdetails.get("FetchedDate")).append('\n');
        m_sHRFBuffer.append("season=").append(m_htWorld.get("Season")).append('\n');
        m_sHRFBuffer.append("matchround=").append(m_htWorld.get("MatchRound")).append('\n');
        m_sHRFBuffer.append("teamID=").append(m_htTeamdetails.get("TeamID")).append('\n');
        m_sHRFBuffer.append("teamName=").append(m_htTeamdetails.get("TeamName")).append('\n');
        m_sHRFBuffer.append("owner=").append(m_htTeamdetails.get("Loginname")).append('\n');
        m_sHRFBuffer.append("ownerEmail=").append(m_htTeamdetails.get("Email")).append('\n');
        m_sHRFBuffer.append("ownerICQ=").append(m_htTeamdetails.get("ICQ")).append('\n');
        m_sHRFBuffer.append("ownerHomepage=").append(m_htTeamdetails.get("HomePage")).append('\n');
        m_sHRFBuffer.append("countryID=").append(m_htWorld.get("CountryID")).append('\n');
        m_sHRFBuffer.append("leagueID=").append(m_htTeamdetails.get("LeagueID")).append('\n');
        m_sHRFBuffer.append("regionID=").append(m_htTeamdetails.get("RegionID")).append('\n');
        m_sHRFBuffer.append("hasSupporter=").append(m_htTeamdetails.get("HasSupporter")).append('\n');
    }

    /**
     * Create the club data.
     */
    protected final void createClub() throws Exception {
        m_sHRFBuffer.append("[club]\n");
        m_sHRFBuffer.append("hjTranare=").append(m_htClub.get("AssistantTrainers")).append('\n');
        m_sHRFBuffer.append("psykolog=").append(m_htClub.get("Psychologists")).append('\n');
        m_sHRFBuffer.append("presstalesman=").append(m_htClub.get("PressSpokesmen")).append('\n');
        m_sHRFBuffer.append("massor=").append(m_htClub.get("Physiotherapists")).append('\n');
        m_sHRFBuffer.append("lakare=").append(m_htClub.get("Doctors")).append('\n');
        m_sHRFBuffer.append("juniorverksamhet=").append(m_htClub.get("YouthLevel")).append('\n');
        m_sHRFBuffer.append("undefeated=").append(m_htTeamdetails.get("NumberOfUndefeated")).append('\n');
        m_sHRFBuffer.append("victories=").append(m_htTeamdetails.get("NumberOfVictories")).append('\n');
        m_sHRFBuffer.append("fanclub=").append(m_htEconomy.get("FanClubSize")).append('\n');
    }

    /**
     * Create the economy data.
     */
    protected final void createEconemy() throws Exception {
        //wahrscheinlich in Training.asp fehlt noch
        m_sHRFBuffer.append("[economy]").append('\n');

        if (m_htEconomy.get("SponsorsPopularity") != null) {
            m_sHRFBuffer.append("supporters=").append(m_htEconomy.get("SupportersPopularity")).append('\n');
            m_sHRFBuffer.append("sponsors=").append(m_htEconomy.get("SponsorsPopularity")).append('\n');

            //es wird grad gespielt flag setzen
        } else {
            m_sHRFBuffer.append("playingMatch=true");
        }

        m_sHRFBuffer.append("cash=").append(m_htEconomy.get("Cash")).append('\n');
        m_sHRFBuffer.append("IncomeSponsorer=").append(m_htEconomy.get("IncomeSponsors")).append('\n');
        m_sHRFBuffer.append("incomePublik=").append(m_htEconomy.get("IncomeSpectators")).append('\n');
        m_sHRFBuffer.append("incomeFinansiella=").append(m_htEconomy.get("IncomeFinancial")).append('\n');
        m_sHRFBuffer.append("incomeTillfalliga=").append(m_htEconomy.get("IncomeTemporary")).append('\n');
        m_sHRFBuffer.append("incomeSumma=").append(m_htEconomy.get("IncomeSum")).append('\n');
        m_sHRFBuffer.append("costsSpelare=").append(m_htEconomy.get("CostsPlayers")).append('\n');
        m_sHRFBuffer.append("costsPersonal=").append(m_htEconomy.get("CostsStaff")).append('\n');
        m_sHRFBuffer.append("costsArena=").append(m_htEconomy.get("CostsArena")).append('\n');
        m_sHRFBuffer.append("costsJuniorverksamhet=").append(m_htEconomy.get("CostsYouth")).append('\n');
        m_sHRFBuffer.append("costsRantor=").append(m_htEconomy.get("CostsFinancial")).append('\n');
        m_sHRFBuffer.append("costsTillfalliga=").append(m_htEconomy.get("CostsTemporary")).append('\n');
        m_sHRFBuffer.append("costsSumma=").append(m_htEconomy.get("CostsSum")).append('\n');
        m_sHRFBuffer.append("total=").append(m_htEconomy.get("ExpectedWeeksTotal")).append('\n');
        m_sHRFBuffer.append("lastIncomeSponsorer=").append(m_htEconomy.get("LastIncomeSponsors")).append('\n');
        m_sHRFBuffer.append("lastIncomePublik=").append(m_htEconomy.get("LastIncomeSpectators")).append('\n');
        m_sHRFBuffer.append("lastIncomeFinansiella=").append(m_htEconomy.get("LastIncomeFinancial")).append('\n');
        m_sHRFBuffer.append("lastIncomeTillfalliga=").append(m_htEconomy.get("LastIncomeTemporary")).append('\n');
        m_sHRFBuffer.append("lastIncomeSumma=").append(m_htEconomy.get("LastIncomeSum")).append('\n');
        m_sHRFBuffer.append("lastCostsSpelare=").append(m_htEconomy.get("LastCostsPlayers")).append('\n');
        m_sHRFBuffer.append("lastCostsPersonal=").append(m_htEconomy.get("LastCostsStaff")).append('\n');
        m_sHRFBuffer.append("lastCostsArena=").append(m_htEconomy.get("LastCostsArena")).append('\n');
        m_sHRFBuffer.append("lastCostsJuniorverksamhet=").append(m_htEconomy.get("LastCostsYouth")).append('\n');
        m_sHRFBuffer.append("lastCostsRantor=").append(m_htEconomy.get("LastCostsFinancial")).append('\n');
        m_sHRFBuffer.append("lastCostsTillfalliga=").append(m_htEconomy.get("LastCostsTemporary")).append('\n');
        m_sHRFBuffer.append("lastCostsSumma=").append(m_htEconomy.get("LastCostsSum")).append('\n');
        m_sHRFBuffer.append("lastTotal=").append(m_htEconomy.get("LastWeeksTotal")).append('\n');
    }

    /**
     * Create last lineup section.
     */
    protected final void createLastLineUp() {
        m_sHRFBuffer.append("[lastlineup]").append('\n');
        m_sHRFBuffer.append("trainer=").append(m_htTeamdetails.get("TrainerID")).append('\n');

           
        try {
			m_sHRFBuffer.append("installning=").append(m_iLastAttitude + "\n");
			m_sHRFBuffer.append("tactictype=").append(m_iLastTactic + "\n");
			m_sHRFBuffer.append("keeper=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.keeper).getSpielerId()).append('\n');
			m_sHRFBuffer.append("rightBack=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.rightBack).getSpielerId()).append('\n');
			m_sHRFBuffer.append("insideBack1=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.rightCentralDefender).getSpielerId()).append('\n');
			m_sHRFBuffer.append("insideBack2=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.leftCentralDefender).getSpielerId()).append('\n');
			m_sHRFBuffer.append("insideBack3=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.middleCentralDefender).getSpielerId()).append('\n');
			m_sHRFBuffer.append("leftBack=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.leftBack).getSpielerId()).append('\n');
			m_sHRFBuffer.append("rightWinger=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.rightWinger).getSpielerId()).append('\n');
			m_sHRFBuffer.append("insideMid1=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.rightInnerMidfield).getSpielerId()).append('\n');
			m_sHRFBuffer.append("insideMid2=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.leftInnerMidfield).getSpielerId()).append('\n');
			m_sHRFBuffer.append("insideMid3=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.centralInnerMidfield).getSpielerId()).append('\n');
			m_sHRFBuffer.append("leftWinger=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.leftWinger).getSpielerId()).append('\n');
			m_sHRFBuffer.append("forward1=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.rightForward).getSpielerId()).append('\n');
			m_sHRFBuffer.append("forward2=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.leftForward).getSpielerId()).append('\n');
			m_sHRFBuffer.append("forward3=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.centralForward).getSpielerId()).append('\n');
			m_sHRFBuffer.append("substBack=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.substDefender).getSpielerId()).append('\n');
			m_sHRFBuffer.append("substInsideMid=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.substInnerMidfield).getSpielerId()).append('\n');
			m_sHRFBuffer.append("substWinger=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.substWinger).getSpielerId()).append('\n');
			m_sHRFBuffer.append("substKeeper=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.substKeeper).getSpielerId()).append('\n');
			m_sHRFBuffer.append("substForward=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.substForward).getSpielerId()).append('\n');
			m_sHRFBuffer.append("captain=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.captain).getSpielerId()).append('\n');
			m_sHRFBuffer.append("kicker1=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.setPieces).getSpielerId()).append('\n');

			m_sHRFBuffer.append("behrightBack=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.rightBack).getTaktik()).append('\n');
			m_sHRFBuffer.append("behinsideBack1=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.rightCentralDefender).getTaktik()).append('\n');
			m_sHRFBuffer.append("behinsideBack2=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.leftCentralDefender).getTaktik()).append('\n');
			m_sHRFBuffer.append("behinsideBack3=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.middleCentralDefender).getTaktik()).append('\n');
			m_sHRFBuffer.append("behleftBack=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.leftBack).getTaktik()).append('\n');
			m_sHRFBuffer.append("behrightWinger=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.rightWinger).getTaktik()).append('\n');
			m_sHRFBuffer.append("behinsideMid1=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.rightInnerMidfield).getTaktik()).append('\n');
			m_sHRFBuffer.append("behinsideMid2=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.leftInnerMidfield).getTaktik()).append('\n');
			m_sHRFBuffer.append("behinsideMid3=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.centralInnerMidfield).getTaktik()).append('\n');
			m_sHRFBuffer.append("behleftWinger=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.leftWinger).getTaktik()).append('\n');
			m_sHRFBuffer.append("behforward1=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.rightForward).getTaktik()).append('\n');
			m_sHRFBuffer.append("behforward2=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.leftForward).getTaktik()).append('\n');
			m_sHRFBuffer.append("behforward3=").append(m_clTeam.getPlayerByPosition(ISpielerPosition.centralForward).getTaktik()).append('\n');
			
			int i = 0;
			for (Substitution sub:  m_clTeam.getSubstitutions()) {
				if (sub != null) {
					m_sHRFBuffer.append("subst").append(i).append("playerOrderID=").append(sub.getPlayerOrderId()).append('\n');
					m_sHRFBuffer.append("subst").append(i).append("playerIn=").append(sub.getObjectPlayerID()).append('\n');
					m_sHRFBuffer.append("subst").append(i).append("playerOut=").append(sub.getSubjectPlayerID()).append('\n');
					m_sHRFBuffer.append("subst").append(i).append("orderType=").append(sub.getOrderType().getId()).append('\n');
					m_sHRFBuffer.append("subst").append(i).append("matchMinuteCriteria=").append(sub.getMatchMinuteCriteria()).append('\n');
					m_sHRFBuffer.append("subst").append(i).append("pos=").append(sub.getPos()).append('\n');
					m_sHRFBuffer.append("subst").append(i).append("behaviour=").append(sub.getBehaviour()).append('\n');
					m_sHRFBuffer.append("subst").append(i).append("card=").append(sub.getRedCardCriteria().getId()).append('\n');
					m_sHRFBuffer.append("subst").append(i).append("standing=").append(sub.getStanding().getId()).append('\n');
					i++;
				}
			}
			
			
        } catch (Exception e) {
        	HOLogger.instance().debug(getClass(), "Error(last lineup): " + e);
        }
    }

    /**
     * Create the league data.
     */
    protected final void createLeague() throws Exception {
        m_sHRFBuffer.append("[league]\n");
        m_sHRFBuffer.append("serie=").append(m_htLiga.get("LeagueLevelUnitName")).append('\n');
        m_sHRFBuffer.append("spelade=").append(m_htLiga.get("Matches")).append('\n');
        m_sHRFBuffer.append("gjorda=").append(m_htLiga.get("GoalsFor")).append('\n');
        m_sHRFBuffer.append("inslappta=").append(m_htLiga.get("GoalsAgainst")).append('\n');
        m_sHRFBuffer.append("poang=").append(m_htLiga.get("Points")).append('\n');
        m_sHRFBuffer.append("placering=").append(m_htLiga.get("Position")).append('\n');
    }

    private String getPlayerForNextLineup(String position, Map<?, ?> next) {
    	if (next != null) {
    		final Object ret = next.get(position);
    		if (ret != null) {
    			return ret.toString();
    		}
    	}
    	return "0";
    }
    
    private String getPlayerOrderForNextLineup(String position, Map<?, ?> map) {
    	if (map != null) {
    		String ret = (String)map.get(position);
    		
    		if (ret != null) {
    			ret = ret.trim();
    			if (!"null".equals(ret) && !"".equals(ret)) {
    				return ret.trim();
    			}
    		}
    	}
    	return "0";
    }
    
   /**
    * Creates the lineup data.
    * @param buffer The string buffer that will be modified. Lineup info added.
    * @param trainerId The playerId of the trainer of the club.
    * @param nextLineup The lineup info hashmap from the parser.
    * @return
    * @throws Exception
    */
    public final String createLineUp(String trainerId, Map<?, ?> nextLineup) throws Exception {
    	StringBuilder buffer = new StringBuilder();
    	buffer.append("[lineup]").append('\n');

        try {
        	buffer.append("trainer=").append(trainerId).append('\n');
        	buffer.append("installning=").append(nextLineup.get("Attitude")).append('\n');
        	buffer.append("tactictype=").append((nextLineup.get("TacticType").toString().trim().equals("") ? "0" : nextLineup.get("TacticType").toString().trim())).append('\n');
           
			buffer.append("keeper=").append(getPlayerForNextLineup("KeeperID", nextLineup)).append('\n');
            buffer.append("rightBack=").append(getPlayerForNextLineup("RightBackID", nextLineup)).append('\n');
            buffer.append("insideBack1=").append(getPlayerForNextLineup("RightCentralDefenderID", nextLineup)).append('\n');
            buffer.append("insideBack2=").append(getPlayerForNextLineup("LeftCentralDefenderID", nextLineup)).append('\n');
            buffer.append("insideBack3=").append(getPlayerForNextLineup("MiddleCentralDefenderID", nextLineup)).append('\n');
            buffer.append("leftBack=").append(getPlayerForNextLineup("LeftBackID", nextLineup)).append('\n');
            buffer.append("rightWinger=").append(getPlayerForNextLineup("RightWingerID", nextLineup)).append('\n');
            buffer.append("insideMid1=").append(getPlayerForNextLineup("RightInnerMidfieldID", nextLineup)).append('\n');
            buffer.append("insideMid2=").append(getPlayerForNextLineup("LeftInnerMidfieldID", nextLineup)).append('\n');
            buffer.append("insideMid3=").append(getPlayerForNextLineup("CentralInnerMidfieldID", nextLineup)).append('\n');
            buffer.append("leftWinger=").append(getPlayerForNextLineup("LeftWingerID", nextLineup)).append('\n');
            buffer.append("forward1=").append(getPlayerForNextLineup("RightForwardID", nextLineup)).append('\n');
            buffer.append("forward2=").append(getPlayerForNextLineup("LeftForwardID", nextLineup)).append('\n');
            buffer.append("forward3=").append(getPlayerForNextLineup("CentralForwardID", nextLineup)).append('\n');
            buffer.append("substBack=").append(getPlayerForNextLineup("SubstBackID", nextLineup)).append('\n');
            buffer.append("substInsideMid=").append(getPlayerForNextLineup("SubstInsideMidID", nextLineup)).append('\n');
            buffer.append("substWinger=").append(getPlayerForNextLineup("SubstWingerID", nextLineup)).append('\n');
            buffer.append("substKeeper=").append(getPlayerForNextLineup("SubstKeeperID", nextLineup)).append('\n');
            buffer.append("substForward=").append(getPlayerForNextLineup("SubstForwardID", nextLineup)).append('\n');
            buffer.append("captain=").append(getPlayerForNextLineup("CaptainID", nextLineup)).append('\n');
            buffer.append("kicker1=").append(getPlayerForNextLineup("KickerID", nextLineup)).append('\n');
            
			buffer.append("behrightBack=").append(getPlayerOrderForNextLineup("RightBackOrder", nextLineup)).append('\n');
			buffer.append("behinsideBack1=").append(getPlayerOrderForNextLineup("RightCentralDefenderOrder", nextLineup)).append('\n');
			buffer.append("behinsideBack2=").append(getPlayerOrderForNextLineup("LeftCentralDefenderOrder", nextLineup)).append('\n');
			buffer.append("behinsideBack3=").append(getPlayerOrderForNextLineup("MiddleCentralDefenderOrder", nextLineup)).append('\n');
			buffer.append("behleftBack=").append(getPlayerOrderForNextLineup("LeftBackOrder", nextLineup)).append('\n');
			buffer.append("behrightWinger=").append(getPlayerOrderForNextLineup("RightWingerOrder", nextLineup)).append('\n');
			buffer.append("behinsideMid1=").append(getPlayerOrderForNextLineup("RightInnerMidfieldOrder", nextLineup)).append('\n');
			buffer.append("behinsideMid2=").append(getPlayerOrderForNextLineup("LeftInnerMidfieldOrder", nextLineup)).append('\n');
			buffer.append("behinsideMid3=").append(getPlayerOrderForNextLineup("CentralInnerMidfieldOrder", nextLineup)).append('\n');
			buffer.append("behleftWinger=").append(getPlayerOrderForNextLineup("LeftWingerOrder", nextLineup)).append('\n');
			buffer.append("behforward1=").append(getPlayerOrderForNextLineup("RightForwardOrder", nextLineup)).append('\n');
			buffer.append("behforward2=").append(getPlayerOrderForNextLineup("LeftForwardOrder", nextLineup)).append('\n');
			buffer.append("behforward3=").append(getPlayerOrderForNextLineup("CentralForwardOrder", nextLineup)).append('\n');
			
			for (int i = 0; i < 5; i++) {
				String substNext = "subst" + i;
				if (nextLineup.get(substNext + "playerOrderID") != null) {
					buffer.append(substNext).append("playerOrderID=").append(nextLineup.get(substNext + "playerOrderID")).append('\n');
					buffer.append(substNext).append("playerIn=").append(nextLineup.get(substNext + "playerIn")).append('\n');
					buffer.append(substNext).append("playerOut=").append(nextLineup.get(substNext + "playerOut")).append('\n');
					buffer.append(substNext).append("orderType=").append(nextLineup.get(substNext + "orderType")).append('\n');
					buffer.append(substNext).append("matchMinuteCriteria=").append(nextLineup.get(substNext + "matchMinuteCriteria")).append('\n');
					buffer.append(substNext).append("pos=").append(nextLineup.get(substNext + "pos")).append('\n');
					buffer.append(substNext).append("behaviour=").append(nextLineup.get(substNext + "behaviour")).append('\n');
					buffer.append(substNext).append("card=").append(nextLineup.get(substNext + "card")).append('\n');
					buffer.append(substNext).append("standing=").append(nextLineup.get(substNext + "standing")).append('\n');
				}
			}
			
			for (int i = 0; i < 11; i++) {
				String key = "PenaltyTaker" + i;
				buffer.append("penalty").append(i).append("=").append(getPlayerForNextLineup(key, nextLineup)).append('\n');
			}
			
        } catch (Exception e) {
        	HOLogger.instance().debug(getClass(), "Error(lineup): " + e);
        }
        return buffer.toString();
    }

    /**
     * Create the player data.
     */
    protected final void createPlayers() throws Exception {
        Hashtable<?, ?> ht = null;

        for (int i = 0; (m_vSpieler != null) && (i < m_vSpieler.size()); i++) {
            ht = (Hashtable<?, ?>) m_vSpieler.elementAt(i);

            m_sHRFBuffer.append("[player").append(ht.get("PlayerID").toString()).append(']').append('\n');
           
            if (ht.get("NickName").toString().length()>0) {
            	m_sHRFBuffer.append("name=");
            	m_sHRFBuffer.append(ht.get("FirstName").toString()).append(" '");
            	m_sHRFBuffer.append(ht.get("NickName").toString()).append("' ");
       			m_sHRFBuffer.append(ht.get("LastName").toString()).append('\n');
            } else {
            	m_sHRFBuffer.append("name=");
            	m_sHRFBuffer.append(ht.get("FirstName").toString()).append(' ');
       			m_sHRFBuffer.append(ht.get("LastName").toString()).append('\n');
            }
            m_sHRFBuffer.append("firstname=").append(ht.get("FirstName").toString()).append('\n');
            m_sHRFBuffer.append("nickname=").append(ht.get("NickName").toString()).append('\n');
            m_sHRFBuffer.append("lastname=").append(ht.get("LastName").toString()).append('\n');
            m_sHRFBuffer.append("ald=").append(ht.get("Age").toString()).append('\n');
            m_sHRFBuffer.append("agedays=").append(ht.get("AgeDays").toString()).append('\n');
            m_sHRFBuffer.append("ska=").append(ht.get("InjuryLevel").toString()).append('\n');
            m_sHRFBuffer.append("for=").append(ht.get("PlayerForm").toString()).append('\n');
            m_sHRFBuffer.append("uth=").append(ht.get("StaminaSkill").toString()).append('\n');
            m_sHRFBuffer.append("spe=").append(ht.get("PlaymakerSkill").toString()).append('\n');
            m_sHRFBuffer.append("mal=").append(ht.get("ScorerSkill").toString()).append('\n');
            m_sHRFBuffer.append("fra=").append(ht.get("PassingSkill").toString()).append('\n');
            m_sHRFBuffer.append("ytt=").append(ht.get("WingerSkill").toString()).append('\n');
            m_sHRFBuffer.append("fas=").append(ht.get("SetPiecesSkill").toString()).append('\n');
            m_sHRFBuffer.append("bac=").append(ht.get("DefenderSkill").toString()).append('\n');
            m_sHRFBuffer.append("mlv=").append(ht.get("KeeperSkill").toString()).append('\n');
            m_sHRFBuffer.append("rut=").append(ht.get("Experience").toString()).append('\n');
            m_sHRFBuffer.append("loy=").append(ht.get("Loyalty").toString()).append('\n');
            m_sHRFBuffer.append("homegr=").append(ht.get("MotherClubBonus").toString()).append('\n');
            m_sHRFBuffer.append("led=").append(ht.get("Leadership").toString()).append('\n');
            m_sHRFBuffer.append("sal=").append(ht.get("Salary").toString()).append('\n');
            m_sHRFBuffer.append("mkt=").append(ht.get("MarketValue").toString()).append('\n');
            m_sHRFBuffer.append("gev=").append(ht.get("CareerGoals").toString()).append('\n');
            m_sHRFBuffer.append("gtl=").append(ht.get("LeagueGoals").toString()).append('\n');
            m_sHRFBuffer.append("gtc=").append(ht.get("CupGoals").toString()).append('\n');
            m_sHRFBuffer.append("gtt=").append(ht.get("FriendliesGoals").toString()).append('\n');
            m_sHRFBuffer.append("hat=").append(ht.get("CareerHattricks").toString()).append('\n');
            m_sHRFBuffer.append("CountryID=").append(ht.get("CountryID").toString()).append('\n');
            m_sHRFBuffer.append("warnings=").append(ht.get("Cards").toString()).append('\n');
            m_sHRFBuffer.append("speciality=").append(ht.get("Specialty").toString()).append('\n');
            m_sHRFBuffer.append("specialityLabel=").append(PlayerSpeciality.toString(
            		Integer.parseInt(ht.get("Specialty").toString()))).append('\n');
            m_sHRFBuffer.append("gentleness=").append(ht.get("Agreeability").toString()).append('\n');
            m_sHRFBuffer.append("gentlenessLabel=").append(PlayerAgreeability.toString(
            		Integer.parseInt(ht.get("Agreeability").toString()))).append('\n');
            m_sHRFBuffer.append("honesty=").append(ht.get("Honesty").toString() + "\n");
            m_sHRFBuffer.append("honestyLabel=").append(PlayerHonesty.toString(
            		Integer.parseInt(ht.get("Honesty").toString()))).append('\n');
            m_sHRFBuffer.append("Aggressiveness=").append(ht.get("Aggressiveness").toString()).append('\n');
            m_sHRFBuffer.append("AggressivenessLabel=").append(PlayerAggressiveness.toString(
            		Integer.parseInt(ht.get("Aggressiveness").toString()))).append('\n');

            if (ht.get("TrainerSkill") != null) {
                m_sHRFBuffer.append("TrainerType=").append(ht.get("TrainerType").toString()).append('\n');
                m_sHRFBuffer.append("TrainerSkill=").append(ht.get("TrainerSkill").toString()).append('\n');
            } else {
                m_sHRFBuffer.append("TrainerType=").append('\n');
                m_sHRFBuffer.append("TrainerSkill=").append('\n');
            }

            if ((m_clTeam != null)
                && (m_clTeam.getPlayerByID(Integer.parseInt(ht.get("PlayerID").toString())) != null)
                && (m_clTeam.getPlayerByID(Integer.parseInt(ht.get("PlayerID").toString()))
                            .getRating() >= 0)) {
                m_sHRFBuffer.append("rating=").append((int) (m_clTeam.getPlayerByID(
                		Integer.parseInt(ht.get("PlayerID").toString())).getRating() * 2)).append('\n');
            } else {
                m_sHRFBuffer.append("rating=0").append('\n');
            }

            //Bonus
            if ((ht.get("PlayerNumber") != null) || (!ht.get("PlayerNumber").equals(""))) {
                m_sHRFBuffer.append("PlayerNumber=").append(ht.get("PlayerNumber")).append('\n');
            }

            m_sHRFBuffer.append("TransferListed=").append(ht.get("TransferListed")).append('\n');
            m_sHRFBuffer.append("NationalTeamID=").append(ht.get("NationalTeamID")).append('\n');
            m_sHRFBuffer.append("Caps=").append(ht.get("Caps")).append('\n');
            m_sHRFBuffer.append("CapsU20=").append(ht.get("CapsU20")).append('\n');
        }
    }

    /**
     * Create team related data (training, confidence, formation experience, etc.).
     */
    protected final void createTeam() throws Exception {
        m_sHRFBuffer.append("[team]" + "\n");
        m_sHRFBuffer.append("trLevel=").append(m_htTraining.get("TrainingLevel")).append('\n');
        m_sHRFBuffer.append("staminaTrainingPart=").append(m_htTraining.get("StaminaTrainingPart")).append('\n');
        m_sHRFBuffer.append("trTypeValue=").append(m_htTraining.get("TrainingType")).append('\n');
		m_sHRFBuffer.append("trType=").append(TrainingType.toString(Integer.parseInt(m_htTraining.get("TrainingType").toString()))).append('\n');

		if ((m_htTraining.get("Morale") != null) && (m_htTraining.get("SelfConfidence") != null)) {
			m_sHRFBuffer.append("stamningValue=").append(m_htTraining.get("Morale")).append('\n');
			m_sHRFBuffer.append("stamning=").append(TeamSpirit.toString(Integer.parseInt(m_htTraining.get("Morale").toString()))).append('\n');
			m_sHRFBuffer.append("sjalvfortroendeValue=").append(m_htTraining.get("SelfConfidence")).append('\n');
			m_sHRFBuffer.append("sjalvfortroende=").append(TeamConfidence.toString(Integer.parseInt(m_htTraining.get("SelfConfidence").toString()))).append('\n');
        } else {
            m_sHRFBuffer.append("playingMatch=true");
        }

        m_sHRFBuffer.append("exper433=").append(m_htTraining.get("Experience433")).append('\n');
        m_sHRFBuffer.append("exper451=").append(m_htTraining.get("Experience451")).append('\n');
        m_sHRFBuffer.append("exper352=").append(m_htTraining.get("Experience352")).append('\n');
        m_sHRFBuffer.append("exper532=").append(m_htTraining.get("Experience532")).append('\n');
        m_sHRFBuffer.append("exper343=").append(m_htTraining.get("Experience343")).append('\n');
        m_sHRFBuffer.append("exper541=").append(m_htTraining.get("Experience541")).append('\n');
        if (m_htTraining.get("Experience442") != null) {
        	m_sHRFBuffer.append("exper442=").append(m_htTraining.get("Experience442")).append('\n');
        }
        if (m_htTraining.get("Experience523") != null) {
        	m_sHRFBuffer.append("exper523=").append(m_htTraining.get("Experience523")).append('\n');
        }
        if (m_htTraining.get("Experience550") != null) {
        	m_sHRFBuffer.append("exper550=").append(m_htTraining.get("Experience550")).append('\n');
        }
        if (m_htTraining.get("Experience253") != null) {
        	m_sHRFBuffer.append("exper253=").append(m_htTraining.get("Experience253")).append('\n');
        }
    }

    /**
     * Create the world data.
     */
    protected final void createWorld() throws Exception {
        m_sHRFBuffer.append("[xtra]\n");
        m_sHRFBuffer.append("TrainingDate=").append( m_htWorld.get("TrainingDate")).append('\n');
        m_sHRFBuffer.append("EconomyDate=").append(m_htWorld.get("EconomyDate")).append('\n');
        m_sHRFBuffer.append("SeriesMatchDate=").append(m_htWorld.get("SeriesMatchDate")).append('\n');
        m_sHRFBuffer.append("CurrencyName=").append(m_htWorld.get("CurrencyName")).append('\n');
        m_sHRFBuffer.append("CurrencyRate=").append(m_htWorld.get("CurrencyRate").toString().replace(',', '.')).append('\n');
        m_sHRFBuffer.append("LogoURL=").append(m_htTeamdetails.get("LogoURL")).append('\n');
        m_sHRFBuffer.append("HasPromoted=").append(m_htClub.get("HasPromoted")).append('\n');

        m_sHRFBuffer.append("TrainerID=").append(m_htTraining.get("TrainerID")).append('\n');
        m_sHRFBuffer.append("TrainerName=").append(m_htTraining.get("TrainerName")).append('\n');
        m_sHRFBuffer.append("ArrivalDate=").append(m_htTraining.get("ArrivalDate")).append('\n');
        m_sHRFBuffer.append("LeagueLevelUnitID=").append(m_htTeamdetails.get("LeagueLevelUnitID")).append('\n');
    }

    /**
     * Save the HRF file.
     */
    protected final void writeHRF(String dateiname) {
        BufferedWriter out = null;
        final String text = m_sHRFBuffer.toString();

        //utf-8
        OutputStreamWriter outWrit = null;

        try {
        	File f = new File(dateiname);

            if (f.exists()) {
                f.delete();
            }

            f.createNewFile();

            //write utf 8
            outWrit = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
            out = new BufferedWriter(outWrit);

            //write ansi
            if (text != null) {
                out.write(text);
            }
        } catch (Exception except) {
            HOLogger.instance().log(getClass(),except);
        } finally {
        	if (out != null) {
        		try {
					out.close();
				} catch (Exception e) {
				}        	
        	}
        }
    }
}
