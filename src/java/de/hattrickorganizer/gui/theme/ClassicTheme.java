/**
 * 
 */
package de.hattrickorganizer.gui.theme;

import java.awt.Color;
import java.math.BigDecimal;
import java.sql.Timestamp;

import de.hattrickorganizer.gui.HOMainFrame;

public class ClassicTheme extends Theme {

	ClassicTheme(){
		initialize();
	}
	
	private void initialize(){
		setName("Classic");
		setVersion("1.0");
		put("createdDate",Timestamp.valueOf("2011-06-12 12:00:00"));
		put("hoVersion", new BigDecimal(HOMainFrame.VERSION).setScale(4, BigDecimal.ROUND_HALF_UP));
		put("author","HO");
		
		initColors();
	}
	
	/**
	 * key-Syntax => javaComponent.[hoComponent].property || name
	 *  	
	 */
	private void initColors(){
		
		// don´t use UIManager keys !!
		put("black", Color.BLACK);
		put("white", Color.WHITE);
		put("red", Color.RED);
		put("gray", Color.GRAY);
		put("green", Color.GREEN);
		put("yellow", Color.YELLOW);
		put("lightGreen",new Color(220, 255, 220));
		put("lightYellow",new Color(255, 255, 200));
		
		put("ho.checkbox.background","white");
		put("ho.combobox.background","white");
		put("ho.panel.background","white");
		put("ho.button.background","white");
		
		put("ho.label.error.foreground","red");
		put("ho.label.foreground","black");
		put("ho.label.success.foreground","green");
		//player
		put("tableEntry.player.skill.special.background","lightGreen");
		put("tableEntry.player.skill.background","lightYellow");
		put("tableEntry.background","white");
		put("tableEntry.foreground","black");
		put("tableEntry.player.position.background",new Color(220, 220, 255));
		put("tableEntry.player.subposition.background",new Color(235, 235, 255));
		put("tableEntry.player.isOld.foreground","gray");
		put("tableEntry.player.isTransfer.foreground",new Color(0, 180, 0));
		put("tableEntry.player.isInjured.foreground",new Color(200, 0, 0));
		put("tableEntry.player.hasTwoCards.foreground",new Color(100, 100, 0));
		put("tableEntry.player.hasRedCard.foreground",new Color(200, 20, 20));
		put("tableEntry.player.isBruised.foreground",new Color(100, 0, 0));
		
		put("ho.table.selection.background",new java.awt.Color(235, 235, 235));
		
		// league Table
		put("panel.league.usersteam.foreground",new Color(50, 50, 150));
		put("table.league.title.background",new Color(230, 230, 230));
		put("table.league.promoted.background","lightGreen");
		put("table.league.relegation.background","lightYellow");
		put("table.league.demoted.background",new Color(255, 220, 220));
		put("table.league.background","white");
		put("table.league.foreground","black");
		
		// matchday Panel
		put("panel.matchday.background","table.league.background");
		
		// league history panel
		put("panel.leaguehistory.line1.foreground", Color.GREEN);
		put("panel.leaguehistory.line2.foreground",  Color.CYAN);
		put("panel.leaguehistory.line3.foreground",  Color.GRAY);
		put("panel.leaguehistory.line4.foreground",  "black");
		put("panel.leaguehistory.line5.foreground",  Color.ORANGE);
		put("panel.leaguehistory.line6.foreground",  Color.PINK);
		put("panel.leaguehistory.line7.foreground",  "red");
		put("panel.leaguehistory.line8.foreground",  Color.MAGENTA);
		put("panel.leaguehistory.cross.foreground",Color.DARK_GRAY);
		put("panel.leaguehistory.grid.foreground",Color.LIGHT_GRAY);
		
		
		//lineup
		put("ho.label.lineupAssist.background","white");
		put("ho.button.lineupAssist.background","yellow");
		put("selectorOverlay.selected.background",new Color(10, 255, 10, 40));
		put("selectorOverlay.background",new Color(255, 10, 10, 40));
		put("panel.lineup.position.minimized.background","ho.panel.background");
		put("panel.lineup.position.minimized.borderColor",Color.LIGHT_GRAY);
	}
	
	public Color getDefaultColor(String key){
		return key.contains("foreground")?Color.BLACK:Color.white; 
	}
}
