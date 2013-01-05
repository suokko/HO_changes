package ho.module.teamAnalyzer.manager;

import ho.core.db.DBManager;
import ho.module.teamAnalyzer.vo.PlayerInfo;
import java.util.Iterator;
import java.util.List;

public class PlayerDataManager {

	public static final int AVAILABLE = 0;
	public static final int INJURED = 1;
	public static final int SUSPENDED = 2;
	public static final int SOLD = 3;

	public static PlayerInfo getLatestPlayerInfo(int playerId) {
		PlayerInfo info = DBManager.instance().getTAPlayerInfo(playerId);

		if (info.getPlayerId() == 0) {
			info = DBManager.instance().getTAPreviousPlayerInfo(playerId);
		}

		return info;
	}

	public static PlayerInfo getPlayerInfo(int id, int week, int season) {
		return DBManager.instance().getTAPlayerInfo(id, week, season);
	}

	public static PlayerInfo getPlayerInfo(int id) {
		return DBManager.instance().getTAPlayerInfo(id);
	}

	public static void update(List<PlayerInfo> players) {
		for (Iterator<PlayerInfo> iter = players.iterator(); iter.hasNext();) {
			PlayerInfo parsedPlayer = iter.next();
			setPlayer(parsedPlayer);
		}
	}

	private static void setPlayer(PlayerInfo info) {
		if (info.getPlayerId() == 0) {
			return;
		}

		PlayerInfo actual = DBManager.instance().getTAPlayerInfo(info.getPlayerId());
		if (info.getPlayerId() == 217381328) {
			System.out.println();
		}

		if (actual.getPlayerId() == 0) {
			DBManager.instance().addTAPlayerInfo(info);
		} else {
			DBManager.instance().updateTAPlayerInfo(info);
		}
	}
}
