package de.hattrickorganizer.gui.model;

import java.awt.Color;

import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;

import plugins.ISpieler;
import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.templates.DoppelLabelEntry;
import de.hattrickorganizer.gui.templates.SkillEntry;
import de.hattrickorganizer.gui.templates.TableEntry;
import de.hattrickorganizer.model.Spieler;
import de.hattrickorganizer.tools.Helper;


/**
 * Column shows a player skill of a player
 * @author Thorsten Dietz
 * @since 1.36
 */
class PlayerSkillColumn extends PlayerColumn {
	
	/** id for the skill **/
	private int skill;
	
	/** different color for some skills **/
	private Color background;
	
	/**
	 * constructor
	 * @param id
	 * @param name
	 * @param tooltip
	 * @param skill
	 */
	protected PlayerSkillColumn(int id,String name, String tooltip,int skill){
		super(id,name,tooltip);
		this.skill = skill;
		background = (skill == ISpieler.SKILL_EXPIERIENCE
				||  skill == ISpieler.SKILL_FORM
				|| skill == ISpieler.SKILL_LEADERSHIP
				)?ColorLabelEntry.BG_SPIELERSONDERWERTE:ColorLabelEntry.BG_SPIELEREINZELWERTE;
	}
	
	/**
	 * overwritten by created columns
	 */
	@Override
	public TableEntry getTableEntry(Spieler player,Spieler comparePlayer){
		return new DoppelLabelEntry(getSkillValue(player),getCompareValue(player,comparePlayer));
	}
	
	public  TableEntry getSkillValue(Spieler player){
		if(skill == ISpieler.SKILL_EXPIERIENCE
				||  skill == ISpieler.SKILL_FORM
				|| skill == ISpieler.SKILL_KONDITION
				|| skill == ISpieler.SKILL_LEADERSHIP
				|| skill == ISpieler.SKILL_LOYALTY){
		return new ColorLabelEntry(getSkill(player),
                background,
                false, 0);
		}
		return new SkillEntry(getSkill(player)
                + player.getSubskill4SkillWithOffset(skill),
              ColorLabelEntry.FG_STANDARD,
              background);
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
	                   background,
	                   SwingConstants.RIGHT);
		}
		
		return new ColorLabelEntry(getSkill(player)
                - getSkill(comparePlayer),
                  player.getSubskill4SkillWithOffset(skill)
                - comparePlayer
                  .getSubskill4SkillWithOffset(skill),
                !comparePlayer.isOld(),
                background,
                true);
		
	}
	
	/**
	 * returns right value for the skill
	 * @param player
	 * @return
	 */
	private int getSkill(Spieler player){
		switch(skill){
		case ISpieler.SKILL_TORWART: 		return player.getTorwart();
		case ISpieler.SKILL_VERTEIDIGUNG: 	return player.getVerteidigung();
		case ISpieler.SKILL_PASSSPIEL: 		return player.getPasspiel();
		case ISpieler.SKILL_FLUEGEL: 		return player.getFluegelspiel();
		case ISpieler.SKILL_SPIELAUFBAU: 	return player.getSpielaufbau();
		case ISpieler.SKILL_STANDARDS: 		return player.getStandards();
		case ISpieler.SKILL_TORSCHUSS: 		return player.getTorschuss();
		case ISpieler.SKILL_EXPIERIENCE: 	return player.getErfahrung();
		case ISpieler.SKILL_FORM: 			return player.getForm();
		case ISpieler.SKILL_KONDITION: 		return player.getKondition();
		case ISpieler.SKILL_LEADERSHIP:		return player.getFuehrung();
		case ISpieler.SKILL_LOYALTY: 		return player.getLoyalty();
		
		}
		return 0;
	}
	
	/**
	 * overwrite the method from UserColumn
	 */
	@Override
	public void setSize(TableColumn column){
		final int breite = (int) (55d * (1d + ((gui.UserParameter.instance().anzahlNachkommastellen - 1) / 4.5d)));
		column.setMinWidth(20);
		column.setPreferredWidth((preferredWidth == 0)?Helper.calcCellWidth(breite):preferredWidth);
	}
}
