package ho.module.playerOverview;

import ho.core.model.player.Spieler;

public interface PlayerTable {

	Spieler getSpieler(int row);
	void setSpieler(int spielerid);
	
}
