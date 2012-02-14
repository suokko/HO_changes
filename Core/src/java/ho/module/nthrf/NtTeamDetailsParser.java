package ho.module.nthrf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import plugins.IXMLParser;

public class NtTeamDetailsParser {

	private int teamId;
	private String teamName;
	private String teamNameShort;
	private long coachId;
	private String coachName;
	private int leagueId;
	private String leagueName;
	private long trainerId;
	private String trainerName;
	private String homePageUrl;
	private int xp433;
	private int xp451;
	private int xp352;
	private int xp532;
	private int xp343;
	private int xp541;
	private int morale;
	private int selfConfidence;
	private int supportersPopularity;
	private int ratingScore;
	private int fanclubSize;
	private int rank;
	private String fetchedDate;
	private boolean parsingSuccess;

	public NtTeamDetailsParser(IXMLParser xm, String xmlData) {
		Document doc = xm.parseString(xmlData);
	    parseDetails(xm, doc);
	}

	private void parseDetails(IXMLParser xm, Document doc) {
        if (doc == null) {
            return;
        }
        try {

            Element root = doc.getDocumentElement();
            Element teamRoot;
            Element ele = (Element)root.getElementsByTagName("FetchedDate").item(0);
            fetchedDate = xm.getFirstChildNodeValue(ele);

            // root Team
            teamRoot = (Element) root.getElementsByTagName("Team").item(0);
            root = teamRoot;
            ele = (Element) root.getElementsByTagName("TeamID").item(0);
            teamId = Integer.parseInt(xm.getFirstChildNodeValue(ele));
            ele = (Element) root.getElementsByTagName("TeamName").item(0);
            teamName = xm.getFirstChildNodeValue(ele);
            ele = (Element) root.getElementsByTagName("ShortTeamName").item(0);
            teamNameShort = xm.getFirstChildNodeValue(ele);

            // root national coach
            root = (Element) teamRoot.getElementsByTagName("NationalCoach").item(0);
            ele = (Element) root.getElementsByTagName("NationalCoachUserID").item(0);
            coachId = Integer.parseInt(xm.getFirstChildNodeValue(ele));
            ele = (Element) root.getElementsByTagName("NationalCoachLoginname").item(0);
            coachName = xm.getFirstChildNodeValue(ele);

            // root League
            root = (Element) teamRoot.getElementsByTagName("League").item(0);
            ele = (Element) root.getElementsByTagName("LeagueID").item(0);
            leagueId = Integer.parseInt(xm.getFirstChildNodeValue(ele));
            ele = (Element) root.getElementsByTagName("LeagueName").item(0);
            leagueName = xm.getFirstChildNodeValue(ele);

            // root Trainer
            root = (Element) teamRoot.getElementsByTagName("Trainer").item(0);
            ele = (Element) root.getElementsByTagName("PlayerID").item(0);
            trainerId = Integer.parseInt(xm.getFirstChildNodeValue(ele));
            ele = (Element) root.getElementsByTagName("PlayerName").item(0);
            trainerName = xm.getFirstChildNodeValue(ele);

            // root HomePage
            root = (Element) teamRoot.getElementsByTagName("HomePage").item(0);
            homePageUrl = xm.getFirstChildNodeValue(root);

            // formation XP
            root = (Element) teamRoot.getElementsByTagName("Experience433").item(0);
            xp433 = Integer.parseInt(xm.getFirstChildNodeValue(root));
            root = (Element) teamRoot.getElementsByTagName("Experience451").item(0);
            xp451 = Integer.parseInt(xm.getFirstChildNodeValue(root));
            root = (Element) teamRoot.getElementsByTagName("Experience352").item(0);
            xp352 = Integer.parseInt(xm.getFirstChildNodeValue(root));
            root = (Element) teamRoot.getElementsByTagName("Experience532").item(0);
            xp532 = Integer.parseInt(xm.getFirstChildNodeValue(root));
            root = (Element) teamRoot.getElementsByTagName("Experience343").item(0);
            xp343 = Integer.parseInt(xm.getFirstChildNodeValue(root));
            root = (Element) teamRoot.getElementsByTagName("Experience541").item(0);
            xp541 = Integer.parseInt(xm.getFirstChildNodeValue(root));

            // morale etc.
            root = (Element) teamRoot.getElementsByTagName("Morale").item(0);
            morale = Integer.parseInt(xm.getFirstChildNodeValue(root));
            root = (Element) teamRoot.getElementsByTagName("SelfConfidence").item(0);
            selfConfidence = Integer.parseInt(xm.getFirstChildNodeValue(root));
            root = (Element) teamRoot.getElementsByTagName("SupportersPopularity").item(0);
            supportersPopularity = Integer.parseInt(xm.getFirstChildNodeValue(root));
            root = (Element) teamRoot.getElementsByTagName("RatingScore").item(0);
            ratingScore = Integer.parseInt(xm.getFirstChildNodeValue(root));
            root = (Element) teamRoot.getElementsByTagName("FanClubSize").item(0);
            fanclubSize = Integer.parseInt(xm.getFirstChildNodeValue(root));
            root = (Element) teamRoot.getElementsByTagName("Rank").item(0);
            rank = Integer.parseInt(xm.getFirstChildNodeValue(root));
            parsingSuccess = true;
        } catch (Exception e) {
        	parsingSuccess = false;
        	e.printStackTrace();
        }
	}

	public int getTeamId() {
		return teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public long getCoachId() {
		return coachId;
	}

	public String getCoachName() {
		return coachName;
	}

	public int getLeagueId() {
		return leagueId;
	}

	public String getLeagueName() {
		return leagueName;
	}

	public long getTrainerId() {
		return trainerId;
	}

	public String getTrainerName() {
		return trainerName;
	}

	public String getHomePageUrl() {
		return homePageUrl;
	}

	public int getXp433() {
		return xp433;
	}

	public int getXp451() {
		return xp451;
	}

	public int getXp352() {
		return xp352;
	}

	public int getXp532() {
		return xp532;
	}

	public int getXp343() {
		return xp343;
	}

	public int getXp541() {
		return xp541;
	}

	public int getMorale() {
		return morale;
	}

	public int getSelfConfidence() {
		return selfConfidence;
	}

	public int getSupportersPopularity() {
		return supportersPopularity;
	}

	public int getRatingScore() {
		return ratingScore;
	}

	public int getFanclubSize() {
		return fanclubSize;
	}

	public int getRank() {
		return rank;
	}

	public String getTeamNameShort() {
		return teamNameShort;
	}

	public String getFetchedDate() {
		return fetchedDate;
	}

	public boolean isParsingSuccess() {
		return parsingSuccess;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("NtTeamDetails (from "+fetchedDate+"), parsingSuccess: " + parsingSuccess);
		sb.append("\n\tteam:    " + teamName + " (" + teamId + ") - short: " + teamNameShort);
		sb.append("\n\tcoach:   " + coachName + " (" + coachId + ")");
		sb.append("\n\tleague : " + leagueName + " (" + leagueId + ")");
		sb.append("\n\ttrainer: " + trainerName + " (" + trainerId + ")");
		sb.append("\n\thomePageUrl: " + homePageUrl);
		sb.append("\n\txp433: " + xp433);
		sb.append("\txp451: " + xp451);
		sb.append("\txp352: " + xp352);
		sb.append("\txp532: " + xp532);
		sb.append("\txp343: " + xp343);
		sb.append("\txp541: " + xp541);
		sb.append("\n\tmorale:               " + morale);
		sb.append("\n\tselfConfidence:       " + selfConfidence);
		sb.append("\n\tsupportersPopularity: " + supportersPopularity);
		sb.append("\n\tratingScore:          " + ratingScore);
		sb.append("\n\tfanclubSize:          " + fanclubSize);
		sb.append("\n\trank:                 " + rank);
		return sb.toString();
	}
}
