package de.hattrickorganizer.gui.model;


import java.awt.Color;

import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;

import plugins.ISpielerPosition;
import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.templates.DoppelLabelEntry;
import de.hattrickorganizer.gui.templates.TableEntry;
import de.hattrickorganizer.model.Spieler;
import de.hattrickorganizer.tools.Helper;

/**
 * Column shows a position skill of a player
 * @author Thorsten Dietz
 * @since 1.36
 */
class PlayerPositionColumn extends PlayerColumn {

	/** position id **/
	private byte position;

     /**
     * constructor
     * @param id
     * @param name
     * @param tooltip
     * @param position
     */
	protected PlayerPositionColumn(int id,String name, String tooltip,byte position){
		super(id,name,tooltip);
		this.position = position;
	}
	
	/**
	 * returns TableEntry
	 * will not be overwrite
	 */
	@Override
	public TableEntry getTableEntry(Spieler player,Spieler comparePlayer){
		return new DoppelLabelEntry(getEntryValue(player),getCompareValue(player,comparePlayer));
	}
	
	/**
	 * returns player value
	 * overwritten by created columns
	 * @param player
	 * @return
	 */
	public ColorLabelEntry getEntryValue(Spieler player){
		return new ColorLabelEntry(player.calcPosValue(position, true),
				getBackgroundColor(), false,
				gui.UserParameter.instance().anzahlNachkommastellen);
	}
	
	/**
	 * return a value if comparePlayer is not null
	 * @param player
	 * @param comparePlayer
	 * @return ColorLabelEntry
	 */
	public ColorLabelEntry getCompareValue(Spieler player,Spieler comparePlayer){
		if(comparePlayer == null){
			return new ColorLabelEntry("",
		            ColorLabelEntry.FG_STANDARD,
		            getBackgroundColor(),
		            SwingConstants.RIGHT);
		}
		
		return new ColorLabelEntry(player.calcPosValue(position, true)
				-comparePlayer.calcPosValue(position, true),
				getBackgroundColor(),false,false,
				gui.UserParameter.instance().anzahlNachkommastellen);
		
	}
	
	/**
	 * overwrite the method from UserColumn
	 */
	@Override
	public void setSize(TableColumn column){
		final int breite = (int) (55d * (1d + ((gui.UserParameter.instance().anzahlNachkommastellen - 1) / 4.5d)));
		column.setMinWidth(25);
		column.setPreferredWidth((preferredWidth == 0)?Helper.calcCellWidth(breite):preferredWidth);
	}
	
	private Color getBackgroundColor(){
		switch(position){
		case ISpielerPosition.TORWART:
		case ISpielerPosition.INNENVERTEIDIGER:
		case ISpielerPosition.AUSSENVERTEIDIGER:
		case ISpielerPosition.MITTELFELD:
		case ISpielerPosition.FLUEGELSPIEL:
		case ISpielerPosition.STURM:	
			return ColorLabelEntry.BG_SPIELERPOSITONSWERTE;
		default:
			return ColorLabelEntry.BG_SPIELERSUBPOSITONSWERTE;
		}
	}
}
