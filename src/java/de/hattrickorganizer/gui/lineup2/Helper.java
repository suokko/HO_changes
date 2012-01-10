package de.hattrickorganizer.gui.lineup2;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import de.hattrickorganizer.logik.xml.XMLMatchOrderParser;
import de.hattrickorganizer.model.Lineup;

public class Helper {

	public static Lineup getLineup(File file) throws Exception {
		Map<String, String> ht = XMLMatchOrderParser.parseMatchOrder(file);
		String str = getLineUpString(ht);
		Properties props = new Properties();
		try {
			props.load(new ByteArrayInputStream(str.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new Lineup(props);
	}

	private static String getLineUpString(Map<String, String> ht) {
		StringBuilder hrfBuffer = new StringBuilder();

		hrfBuffer.append("[lineup]" + "\n");

		// m_sHRFBuffer.append("trainer=" + m_htTeamdetails.get("TrainerID")
		// + "\n");
		hrfBuffer.append("installning=" + ht.get("Attitude") + "\n");
		hrfBuffer.append("tactictype="
				+ (ht.get("TacticType").toString().trim().equals("") ? "0" : ht.get("TacticType").toString()
						.trim()) + "\n");

		hrfBuffer.append("keeper=" + getPlayer(ht, "KeeperID") + "\n");
		hrfBuffer.append("rightback=" + getPlayer(ht, "RightBackID") + "\n");
		hrfBuffer.append("insideback1=" + getPlayer(ht, "RightCentralDefenderID") + "\n");
		hrfBuffer.append("insideback2=" + getPlayer(ht, "LeftCentralDefenderID") + "\n");
		hrfBuffer.append("insideback3=" + getPlayer(ht, "MiddleCentralDefenderID") + "\n");
		hrfBuffer.append("leftback=" + getPlayer(ht, "LeftBackID") + "\n");
		hrfBuffer.append("rightwinger=" + getPlayer(ht, "RightWingerID") + "\n");
		hrfBuffer.append("insidemid1=" + getPlayer(ht, "RightInnerMidfieldID") + "\n");
		hrfBuffer.append("insidemid2=" + getPlayer(ht, "LeftInnerMidfieldID") + "\n");
		hrfBuffer.append("insidemid3=" + getPlayer(ht, "CentralInnerMidfieldID") + "\n");
		hrfBuffer.append("leftwinger=" + getPlayer(ht, "LeftWingerID") + "\n");
		hrfBuffer.append("forward1=" + getPlayer(ht, "RightForwardID") + "\n");
		hrfBuffer.append("forward2=" + getPlayer(ht, "LeftForwardID") + "\n");
		hrfBuffer.append("forward3=" + getPlayer(ht, "CentralForwardID") + "\n");
		hrfBuffer.append("substback=" + getPlayer(ht, "SubstBackID") + "\n");
		hrfBuffer.append("substinsidemid=" + getPlayer(ht, "SubstInsideMidID") + "\n");
		hrfBuffer.append("substwinger=" + getPlayer(ht, "SubstWingerID") + "\n");
		hrfBuffer.append("substkeeper=" + getPlayer(ht, "SubstKeeperID") + "\n");
		hrfBuffer.append("substforward=" + getPlayer(ht, "SubstForwardID") + "\n");
		hrfBuffer.append("captain=" + getPlayer(ht, "CaptainID") + "\n");
		hrfBuffer.append("kicker1=" + getPlayer(ht, "KickerID") + "\n");

		hrfBuffer.append("behrightback=" + getPlayerOrder(ht, "RightBackOrder") + "\n");
		hrfBuffer.append("behinsideback1=" + getPlayerOrder(ht, "RightCentralDefenderOrder") + "\n");
		hrfBuffer.append("behinsideback2=" + getPlayerOrder(ht, "LeftCentralDefenderOrder") + "\n");
		hrfBuffer.append("behinsideback3=" + getPlayerOrder(ht, "MiddleCentralDefenderOrder") + "\n");
		hrfBuffer.append("behleftback=" + getPlayerOrder(ht, "LeftBackOrder") + "\n");
		hrfBuffer.append("behrightwinger=" + getPlayerOrder(ht, "RightWingerOrder") + "\n");
		hrfBuffer.append("behinsidemid1=" + getPlayerOrder(ht, "RightInnerMidfieldOrder") + "\n");
		hrfBuffer.append("behinsidemid2=" + getPlayerOrder(ht, "LeftInnerMidfieldOrder") + "\n");
		hrfBuffer.append("behinsidemid3=" + getPlayerOrder(ht, "CentralInnerMidfieldOrder") + "\n");
		hrfBuffer.append("behleftwinger=" + getPlayerOrder(ht, "LeftWingerOrder") + "\n");
		hrfBuffer.append("behforward1=" + getPlayerOrder(ht, "RightForwardOrder") + "\n");
		hrfBuffer.append("behforward2=" + getPlayerOrder(ht, "LeftForwardOrder") + "\n");
		hrfBuffer.append("behforward3=" + getPlayerOrder(ht, "CentralForwardOrder") + "\n");

		for (int i = 0; i < 5; i++) {
			if (ht.get("subst" + i + "playerOrderID") != null) {
				hrfBuffer.append("subst" + i + "playerOrderID=" + ht.get("subst" + i + "playerOrderID")
						+ "\n");
				hrfBuffer.append("subst" + i + "playerIn=" + ht.get("subst" + i + "playerIn") + "\n");
				hrfBuffer.append("subst" + i + "playerOut=" + ht.get("subst" + i + "playerOut") + "\n");
				hrfBuffer.append("subst" + i + "orderType=" + ht.get("subst" + i + "orderType") + "\n");
				hrfBuffer.append("subst" + i + "matchMinuteCriteria="
						+ ht.get("subst" + i + "matchMinuteCriteria") + "\n");
				hrfBuffer.append("subst" + i + "pos=" + ht.get("subst" + i + "pos") + "\n");
				hrfBuffer.append("subst" + i + "behaviour=" + ht.get("subst" + i + "behaviour") + "\n");
				hrfBuffer.append("subst" + i + "card=" + ht.get("subst" + i + "card") + "\n");
				hrfBuffer.append("subst" + i + "standing=" + ht.get("subst" + i + "standing") + "\n");
			}
		}
		return hrfBuffer.toString();
	}

	private static String getPlayer(Map<String, String> map, String position) {
		if (map != null) {
			final Object ret = map.get(position);
			if (ret != null) {
				return ret.toString();
			}
		}
		return "0";
	}

	private static String getPlayerOrder(Map<String, String> map, String position) {
		if (map != null) {
			String ret = (String) map.get(position);

			if (ret != null) {
				ret = ret.trim();
				if (!"null".equals(ret) && !"".equals(ret)) {
					return ret.trim();
				}
			}
		}
		return "0";
	}
}
