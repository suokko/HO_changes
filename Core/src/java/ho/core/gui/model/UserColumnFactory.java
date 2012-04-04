package ho.core.gui.model;

import ho.core.constants.player.PlayerSkill;
import ho.core.epv.EPVData;
import ho.core.gui.comp.entry.ColorLabelEntry;
import ho.core.gui.comp.entry.DoppelLabelEntry;
import ho.core.gui.comp.entry.HomegrownEntry;
import ho.core.gui.comp.entry.IHOTableEntry;
import ho.core.gui.comp.entry.RatingTableEntry;
import ho.core.gui.comp.entry.SmilieEntry;
import ho.core.gui.comp.entry.SpielerLabelEntry;
import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ImageUtilities;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.HOModel;
import ho.core.model.HOVerwaltung;
import ho.core.model.ISpielerPosition;
import ho.core.model.Spieler;
import ho.core.model.SpielerPosition;
import ho.core.util.Helper;
import ho.core.util.HelperWrapper;
import ho.core.util.PlayerHelper;
import ho.core.util.StringUtilities;
import ho.module.matches.model.MatchKurzInfo;
import ho.module.matches.model.Matchdetails;
import ho.module.playerOverview.SpielerStatusLabelEntry;

import java.awt.Color;

import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;


/**
 * Create the userColumns
 * @author Thorsten Dietz
 *
 */
final public class UserColumnFactory {

    //~ Static fields/initializers -----------------------------------------------------------------
	/** id from the column NAME **/
	public static final int NAME = 1;
	
	/** id from the column BEST_POSITION **/
	public static final int BEST_POSITION = 40;

	/** id from the column LINUP **/
	public static final int LINUP = 50;
	
	/** id from the column GROUP **/
	public static final int GROUP = 60;
	
	/** id from the column ID **/
	public static final int ID = 440;
	
	/** id from the column DATUM **/
	public static final int DATUM = 450;
	
	/** id from the column RATING **/
	public static final int RATING = 435;
	
	/** id from the column DATUM **/
	public static final int AUTO_LINEUP = 510;
	
	/**
	 * 
	 * @return PlayerCBItem[]
	 */
	public static PlayerCBItem[] createPlayerCBItemArray(){
		final PlayerCBItem[] playerCBItemArray = new PlayerCBItem[4];
		playerCBItemArray[0] = new PlayerCBItem(590,"Stimmung"){
			@Override
			public IHOTableEntry getTableEntry(SpielerMatchCBItem spielerCBItem){
				return new ColorLabelEntry(spielerCBItem.getStimmung(),
                        ColorLabelEntry.FG_STANDARD,
                        ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
			}
		};
		
		playerCBItemArray[1] = new PlayerCBItem(600,"Selbstvertrauen"){
			@Override
			public IHOTableEntry getTableEntry(SpielerMatchCBItem spielerCBItem){
				return new ColorLabelEntry(spielerCBItem.getSelbstvertrauen(),
                        ColorLabelEntry.FG_STANDARD,
                        ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
			}
		};
		
		playerCBItemArray[2] = new PlayerCBItem(601,"Position"){
			@Override
			public IHOTableEntry getTableEntry(SpielerMatchCBItem spielerCBItem){
				ColorLabelEntry colorLabelEntry = new ColorLabelEntry(ImageUtilities
                        .getImage4Position(SpielerPosition
                                           .getHTPosidForHOPosition4Image((byte) spielerCBItem
                                                                          .getPosition()),
                                           (byte) 0, 0),
                        -SpielerPosition.getSortId((byte) spielerCBItem
                                                   .getPosition(), false),
                        ColorLabelEntry.FG_STANDARD,
                        ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
				colorLabelEntry.setText(SpielerPosition.getNameForPosition((byte) spielerCBItem
                        .getPosition())
                        + " ("
                        + spielerCBItem.getSpieler().calcPosValue((byte) spielerCBItem
                                                        .getPosition(),
                                                        true) + ")");
				return colorLabelEntry;
			}
		};
		
		playerCBItemArray[3] = new PlayerCBItem(RATING,"Bewertung"){
			@Override
			public IHOTableEntry getTableEntry(SpielerMatchCBItem spielerCBItem){
				return new RatingTableEntry(spielerCBItem.getRating(), false);		            
			}				
		};
				
		return playerCBItemArray;
	}
	
	/**
	 * 
	 * @return MatchDetailsColumn[]
	 */
	public static MatchDetailsColumn[] createMatchDetailsColumnsArray(){
		final MatchDetailsColumn[] matchDetailsColumnsArray = new MatchDetailsColumn[4];
		matchDetailsColumnsArray[0] = new MatchDetailsColumn(550,"Wetter",30){
			@Override
			public IHOTableEntry getTableEntry(Matchdetails matchdetails){
				return new ColorLabelEntry(ThemeManager.getIcon(HOIconName.WEATHER[matchdetails.getWetterId()]),
                        matchdetails.getWetterId(),
                        ColorLabelEntry.FG_STANDARD,
                        ColorLabelEntry.BG_STANDARD, SwingConstants.RIGHT);
			}
		};// Wetter
		matchDetailsColumnsArray[1] = new MatchDetailsColumn(560,"Einstellung"){
			@Override
			public IHOTableEntry getTableEntry(Matchdetails matchdetails){
				final int teamid = HOVerwaltung.instance().getModel()
                .getBasics().getTeamId();
				int einstellung = (matchdetails.getHeimId() == teamid)?matchdetails.getHomeEinstellung():matchdetails.getGuestEinstellung(); 
				return new ColorLabelEntry(Matchdetails.getNameForEinstellung(einstellung), ColorLabelEntry.FG_STANDARD,
                        ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
			}
		};
		matchDetailsColumnsArray[2] = new MatchDetailsColumn(570,"Taktik"){
			@Override
			public IHOTableEntry getTableEntry(Matchdetails matchdetails){
				final int teamid = HOVerwaltung.instance().getModel()
                .getBasics().getTeamId();
				int tactic = (matchdetails.getHeimId() == teamid)?matchdetails.getHomeTacticType():matchdetails.getGuestTacticType(); 
				return new ColorLabelEntry(Matchdetails.getNameForTaktik(tactic), ColorLabelEntry.FG_STANDARD,
                        ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
			}
		};
		matchDetailsColumnsArray[3] = new MatchDetailsColumn(580,"Taktikstaerke"){
			@Override
			public IHOTableEntry getTableEntry(Matchdetails matchdetails){
				final int teamid = HOVerwaltung.instance().getModel()
                .getBasics().getTeamId();
				int tacticSkill = (matchdetails.getHeimId() == teamid)?matchdetails.getHomeTacticSkill():matchdetails.getGuestTacticSkill(); 
				return new ColorLabelEntry(PlayerHelper.getNameForSkill(tacticSkill), ColorLabelEntry.FG_STANDARD,
                        ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
			}
		};
		
		return matchDetailsColumnsArray;
	}
	
	/**
	 * 
	 * @return PlayerColumn[]
	 */
	public static PlayerColumn[] createGoalsColumnsArray(){
		final PlayerColumn[] playerGoalsArray = new PlayerColumn[4];
		playerGoalsArray[0] = new PlayerColumn(380,"TG","ToreGesamt",20){
			@Override
			public int getValue(Spieler player){
				return player.getToreGesamt();
			}
		}; 
		
		playerGoalsArray[1] = new PlayerColumn(390,"TF","ToreFreund",20){
			@Override
			public int getValue(Spieler player){
				return player.getToreFreund();
			}
		}; 
		
		playerGoalsArray[2] = new PlayerColumn(400,"TL","ToreLiga",20){
			@Override
			public int getValue(Spieler player){
				return player.getToreLiga();
			}
		}; 
		
		playerGoalsArray[3] = new PlayerColumn(410,"TP","TorePokal",20){
			@Override
			public int getValue(Spieler player){
				return player.getTorePokal();
			}
		}; 
		return playerGoalsArray;
	}
	/**
	 * 
	 * @return PlayerSkillColumn []
	 */
	public static PlayerSkillColumn[] createPlayerSkillArray(){
		final PlayerSkillColumn[] playerSkillArray = new PlayerSkillColumn[12];
		playerSkillArray[0]  = new PlayerSkillColumn( 	80,  "FUE",		"Fuehrung",				PlayerSkill.LEADERSHIP); 
		playerSkillArray[1]  = new PlayerSkillColumn( 	90,  "ER",		"Erfahrung",			PlayerSkill.EXPERIENCE); 
		playerSkillArray[2]  = new PlayerSkillColumn( 	100, "FO",		"Form",					PlayerSkill.FORM); 
		playerSkillArray[3]  = new PlayerSkillColumn( 	110, "KO",		"Kondition",			PlayerSkill.STAMINA); 
		playerSkillArray[4]  = new PlayerSkillColumn( 	115, "LOY",		"Loyalty",				PlayerSkill.LOYALTY); 
		playerSkillArray[5]  = new PlayerSkillColumn( 	120, "TW",		"Torwart",				PlayerSkill.KEEPER); 
		playerSkillArray[6]  = new PlayerSkillColumn( 	130, "VE",		"Verteidigung",			PlayerSkill.DEFENDING); 
		playerSkillArray[7]  = new PlayerSkillColumn( 	140, "SA",		"Spielaufbau",			PlayerSkill.PLAYMAKING); 
		playerSkillArray[8]  = new PlayerSkillColumn( 	150, "PS",		"Passpiel",				PlayerSkill.PASSING); 
		playerSkillArray[9]  = new PlayerSkillColumn( 	160, "FL",		"Fluegelspiel",			PlayerSkill.WINGER); 
		playerSkillArray[10]  = new PlayerSkillColumn( 	170, "TS",		"Torschuss",			PlayerSkill.SCORING); 
		playerSkillArray[11] = new PlayerSkillColumn( 	180, "ST",		"Standards",			PlayerSkill.SET_PIECES); 

		return playerSkillArray;
	}
	
	/**
	 * 
	 * @return PlayerColumn []
	 */
	public static PlayerColumn[] createPlayerBasicArray(){
		final PlayerColumn[] playerBasicArray = new PlayerColumn[2];
		playerBasicArray[0] = new PlayerColumn(NAME,"Name",0){
			@Override
			public IHOTableEntry getTableEntry(Spieler player,Spieler playerCompare){
				return new SpielerLabelEntry(player,
                        HOVerwaltung.instance().getModel()
                        .getAufstellung()
                        .getPositionBySpielerId(player.getSpielerID()),
            0f, false, false);
			}
			@Override
			public boolean isEditable(){
				return true; //false
			}
		};
		playerBasicArray[0].setDisplay(false);
		
		playerBasicArray[1] = new PlayerColumn(ID,"ID",0){
			@Override
			public IHOTableEntry getTableEntry(Spieler player,Spieler playerCompare){
				return new ColorLabelEntry(player.getSpielerID(),
						player.getSpielerID() + "",
                        ColorLabelEntry.FG_STANDARD,
                        ColorLabelEntry.BG_STANDARD, SwingConstants.RIGHT);
			}
			@Override
			public boolean isEditable(){
				return true; //false
			}
			
			@Override
			public void setSize(TableColumn column){
				// Column ID is not shown!
				column.setMinWidth(0);
				column.setPreferredWidth(0);
			} 
		};
		playerBasicArray[1].setDisplay(false);
		return playerBasicArray;
	}
	
	/**
	 * 
	 * @return PlayerPositionColumn[]
	 */
	public static PlayerPositionColumn[] createPlayerPositionArray(){
		final PlayerPositionColumn[] playerPositionArray = new PlayerPositionColumn[19];
		playerPositionArray[0] = new PlayerPositionColumn( 190, "TORW",	"Torwart",				ISpielerPosition.KEEPER ); 
		playerPositionArray[1] = new PlayerPositionColumn( 200, "IV",		"Innenverteidiger",		ISpielerPosition.CENTRAL_DEFENDER ); 
		playerPositionArray[2] = new PlayerPositionColumn( 210, "IVA",		"Innenverteidiger_Aus",	ISpielerPosition.CENTRAL_DEFENDER_TOWING ); 
		playerPositionArray[3] = new PlayerPositionColumn( 220, "IVO",		"Innenverteidiger_Off",	ISpielerPosition.CENTRAL_DEFENDER_OFF ); 
		playerPositionArray[4] = new PlayerPositionColumn( 230, "AV",		"Aussenverteidiger",	ISpielerPosition.BACK ); 
		playerPositionArray[5] = new PlayerPositionColumn( 240, "AVI",		"Aussenverteidiger_In",	ISpielerPosition.BACK_TOMID ); 
		playerPositionArray[6] = new PlayerPositionColumn( 250, "AVO",		"Aussenverteidiger_Off",ISpielerPosition.BACK_OFF ); 
		playerPositionArray[7] = new PlayerPositionColumn( 260, "AVD",		"Aussenverteidiger_Def",ISpielerPosition.BACK_DEF ); 
		playerPositionArray[8] = new PlayerPositionColumn( 270, "MIT",		"Mittelfeld",			ISpielerPosition.MIDFIELDER ); 
		playerPositionArray[9] = new PlayerPositionColumn( 280, "MITA",		"Mittelfeld_Aus",		ISpielerPosition.MIDFIELDER_TOWING ); 
		playerPositionArray[10] = new PlayerPositionColumn( 290, "MITO",	"Mittelfeld_Off",		ISpielerPosition.MIDFIELDER_OFF ); 
		playerPositionArray[11] = new PlayerPositionColumn( 300, "MITD",	"Mittelfeld_Def",		ISpielerPosition.MIDFIELDER_DEF ); 
		playerPositionArray[12] = new PlayerPositionColumn( 310, "FLG",		"Fluegelspiel",			ISpielerPosition.WINGER ); 
		playerPositionArray[13] = new PlayerPositionColumn( 320, "FLGI",	"Fluegelspiel_In",		ISpielerPosition.WINGER_TOMID ); 
		playerPositionArray[14] = new PlayerPositionColumn( 330, "FLGO",	"Fluegelspiel_Off",		ISpielerPosition.WINGER_OFF ); 
		playerPositionArray[15] = new PlayerPositionColumn( 340, "FLGD",	"Fluegelspiel_Def",		ISpielerPosition.WINGER_DEF ); 
		playerPositionArray[16] = new PlayerPositionColumn( 350, "STU",		"Sturm",				ISpielerPosition.FORWARD ); 
		playerPositionArray[17] = new PlayerPositionColumn( 360, "STUA",	"Sturm_Aus",			ISpielerPosition.FORWARD_TOWING ); 
		playerPositionArray[18] = new PlayerPositionColumn( 370, "STUD",	"Sturm_Def",			ISpielerPosition.FORWARD_DEF ); 
		return playerPositionArray;
	}
	
	
	
	/**
	 * 
	 * @return MatchKurzInfoColumn[]
	 */
	public static MatchKurzInfoColumn[] createMatchesArray(){
		final MatchKurzInfoColumn[] matchesArray = new MatchKurzInfoColumn[6];
		matchesArray[0] = new MatchKurzInfoColumn(450,"Datum",70){
			@Override
			public IHOTableEntry getTableEntry(MatchKurzInfo match){
				final Color background = MatchesColumnModel.getColor4Matchtyp(match.getMatchTyp());
				return new ColorLabelEntry(match.getMatchDateAsTimestamp().getTime(),
                        java.text.DateFormat.getDateTimeInstance().format(match
                                .getMatchDateAsTimestamp()),
                                ColorLabelEntry.FG_STANDARD, background,
                                SwingConstants.LEFT);
			}
			
			@Override
			public IHOTableEntry getTableEntry(SpielerMatchCBItem spielerCBItem){
				return new ColorLabelEntry(spielerCBItem.getMatchdate(),
                    ColorLabelEntry.FG_STANDARD,
                    ColorLabelEntry.BG_STANDARD, SwingConstants.CENTER);
			}
		};
		
		matchesArray[1] = new MatchKurzInfoColumn(460," ","Spielart",20){
			@Override
			public IHOTableEntry getTableEntry(MatchKurzInfo match){
				final Color background = MatchesColumnModel.getColor4Matchtyp(match.getMatchTyp());
				return new ColorLabelEntry(ThemeManager.getIcon(HOIconName.MATCHTYPES[match.getMatchTyp()]),
                        match.getMatchTyp(), ColorLabelEntry.FG_STANDARD,
                        background, SwingConstants.CENTER);
			}
			
			@Override
			public IHOTableEntry getTableEntry(SpielerMatchCBItem spielerCBItem){
				final Color background = MatchesColumnModel.getColor4Matchtyp(spielerCBItem.getMatchTyp());
				return new ColorLabelEntry(ThemeManager.getIcon(HOIconName.MATCHTYPES[spielerCBItem.getMatchTyp()]),
                        spielerCBItem.getMatchTyp(),
                        ColorLabelEntry.FG_STANDARD, background,
                        SwingConstants.CENTER);
			}
		};
		
		matchesArray[2] = new MatchKurzInfoColumn(470,"Heim",60){
			@Override
			public IHOTableEntry getTableEntry(MatchKurzInfo match){
				final Color background = MatchesColumnModel.getColor4Matchtyp(match.getMatchTyp());
				ColorLabelEntry entry = new ColorLabelEntry(match.getHeimName(), ColorLabelEntry.FG_STANDARD,
                        background, SwingConstants.LEFT);
				entry.setFGColor((match.getHeimID() == HOVerwaltung.instance().getModel().getBasics()
                        .getTeamId())?ThemeManager.getColor(HOColorName.TEAM_FG):ThemeManager.getColor(HOColorName.LABEL_FG));
				
				if (match.getMatchStatus() != MatchKurzInfo.FINISHED) 
					entry.setIcon(ImageUtilities.NOIMAGEICON);
				else if (match.getHeimTore() > match.getGastTore())
					entry.setIcon(ThemeManager.getTransparentIcon(HOIconName.STAR, Color.WHITE));
				else if (match.getHeimTore() < match.getGastTore())
					entry.setIcon(ImageUtilities.NOIMAGEICON);
				else
					entry.setIcon(ThemeManager.getTransparentIcon("star_gray", Color.WHITE));
				return entry;
			}
			
			@Override
			public IHOTableEntry getTableEntry(SpielerMatchCBItem spielerCBItem){
				final Color background = MatchesColumnModel.getColor4Matchtyp(spielerCBItem.getMatchTyp());
				ColorLabelEntry entry = new ColorLabelEntry(spielerCBItem.getHeimteam() + "",
                        ColorLabelEntry.FG_STANDARD, background,
                        SwingConstants.LEFT);
				entry.setFGColor((spielerCBItem.getHeimID() == HOVerwaltung.instance().getModel().getBasics()
                        .getTeamId())?ThemeManager.getColor(HOColorName.TEAM_FG):ThemeManager.getColor(HOColorName.LABEL_FG));
				return entry;
			}
			
			@Override
			public void setSize(TableColumn column){
				column.setMinWidth(60);
				column.setPreferredWidth((preferredWidth==0)?160:preferredWidth);
			}
		};
		
		matchesArray[3] = new MatchKurzInfoColumn(480,"Gast",60){
			@Override
			public IHOTableEntry getTableEntry(MatchKurzInfo match){
				final Color background = MatchesColumnModel.getColor4Matchtyp(match.getMatchTyp());
				ColorLabelEntry entry = new ColorLabelEntry(match.getGastName(), ColorLabelEntry.FG_STANDARD,
                        background, SwingConstants.LEFT);
				entry.setFGColor((match.getGastID() == HOVerwaltung.instance().getModel().getBasics()
                        .getTeamId())?ThemeManager.getColor(HOColorName.TEAM_FG):ThemeManager.getColor(HOColorName.LABEL_FG));
				
				if (match.getMatchStatus() != MatchKurzInfo.FINISHED) 
					entry.setIcon(ImageUtilities.NOIMAGEICON);
				else if (match.getHeimTore() > match.getGastTore())
					entry.setIcon(ImageUtilities.NOIMAGEICON);
				else if (match.getHeimTore() < match.getGastTore())
					entry.setIcon(ThemeManager.getTransparentIcon(HOIconName.STAR, Color.WHITE));
				else
					entry.setIcon(ThemeManager.getTransparentIcon(HOIconName.STAR_GRAY, Color.WHITE));
				
				return entry;
			}
			
			@Override
			public IHOTableEntry getTableEntry(SpielerMatchCBItem spielerCBItem){
				final Color background = MatchesColumnModel.getColor4Matchtyp(spielerCBItem.getMatchTyp());
				ColorLabelEntry entry = new ColorLabelEntry(spielerCBItem.getGastteam() + "",
                        ColorLabelEntry.FG_STANDARD, background,
                        SwingConstants.LEFT);
				entry.setFGColor((spielerCBItem.getGastID() == HOVerwaltung.instance().getModel().getBasics()
                        .getTeamId())?ThemeManager.getColor(HOColorName.TEAM_FG):ThemeManager.getColor(HOColorName.LABEL_FG));
				return entry;
			}
			
			@Override
			public void setSize(TableColumn column){
				column.setMinWidth(60);
				column.setPreferredWidth((preferredWidth==0)?160:preferredWidth);
			}
		};
		
		matchesArray[4] = new MatchKurzInfoColumn(490,"Ergebnis",45){
			@Override
			public IHOTableEntry getTableEntry(MatchKurzInfo match){
				final Color background = MatchesColumnModel.getColor4Matchtyp(match.getMatchTyp());
				return new ColorLabelEntry(StringUtilities.getResultString(match.getHeimTore(),
                        match.getGastTore()),
                        	ColorLabelEntry.FG_STANDARD, background,
                        	SwingConstants.CENTER);
			}
			
			@Override
			public IHOTableEntry getTableEntry(SpielerMatchCBItem spielerCBItem){
				final Color background = MatchesColumnModel.getColor4Matchtyp(spielerCBItem.getMatchTyp());
				return new ColorLabelEntry(StringUtilities.getResultString(spielerCBItem.getMatchdetails().getHomeGoals(),
						spielerCBItem.getMatchdetails().getGuestGoals()),
                        	ColorLabelEntry.FG_STANDARD, background,
                        	SwingConstants.CENTER);
			}

		};
		
		matchesArray[5] = new MatchKurzInfoColumn(500,"ID",55){
			
			@Override
			public IHOTableEntry getTableEntry(MatchKurzInfo match){
				final Color background = MatchesColumnModel.getColor4Matchtyp(match.getMatchTyp());
				return new ColorLabelEntry(match.getMatchID(), match.getMatchID() + "",
                        ColorLabelEntry.FG_STANDARD, background,
                        SwingConstants.RIGHT);
			}
		};
		
		return matchesArray;
	}
	
	/**
	 * creates an array of various player columns
	 * @return PlayerColumn[]
	 */
	public static PlayerColumn[] createPlayerAdditionalArray(){
			final PlayerColumn [] playerAdditionalArray = new PlayerColumn[12];
			
			playerAdditionalArray[0] =new PlayerColumn(10," "," ",0){
				@Override
				public IHOTableEntry getTableEntry(Spieler player,Spieler playerCompare){
					int sort = player.getTrikotnummer();
					if (sort <= 0) {
		                //Damit die Spieler ohne Trickot nach den andern kommen
		                sort = 10000;
		            }
					//FIXME getImageIcon4Trickotnummer
					
					return new ColorLabelEntry(sort,sort+"", ColorLabelEntry.FG_STANDARD, ColorLabelEntry.BG_STANDARD,
                                    		SwingConstants.LEFT);
							}
				
				@Override
				public boolean isEditable(){
					return false;
				}
			}; 
			
			playerAdditionalArray[1] =new PlayerColumn(20," ","Nationalitaet",25){
				@Override
				public IHOTableEntry getTableEntry(Spieler player,Spieler playerCompare){
					return new ColorLabelEntry(ImageUtilities.getFlagIcon(player.getNationalitaet()),
                            player.getNationalitaet(),
                            ColorLabelEntry.FG_STANDARD,
                            ColorLabelEntry.BG_STANDARD, SwingConstants.CENTER);
				}
			}; 
			
			playerAdditionalArray[2] = new PlayerColumn(30, "Alter", 40){
				@Override
				public IHOTableEntry getTableEntry(Spieler player,Spieler playerCompare){
					String ageString = player.getAlterWithAgeDaysAsString();
					int birthdays = 0;
					boolean playerExists;

					if(playerCompare == null){
						// Birthdays since last HRF
						birthdays = (int) (Math.floor(player.getAlterWithAgeDays()) - player.getAlter());
						playerExists = false;
					} else {
						// Birthdays since compare
						birthdays = (int) (Math.floor(player.getAlterWithAgeDays()) - Math.floor(playerCompare.getAlterWithAgeDays()));
						if (playerCompare.isOld())
							// Player was not in our team at compare date
							playerExists = false;
						else
							// Player was in our team at compare date
							playerExists = true;
					}
					return new ColorLabelEntry(
							birthdays,
							ageString,
							player.getAlterWithAgeDays(),
							playerExists,
							ColorLabelEntry.BG_STANDARD,
							true);
				}
			};

			playerAdditionalArray[3] =new PlayerColumn(40,"BestePosition",100){
				@Override
				public IHOTableEntry getTableEntry(Spieler player,Spieler playerCompare){
					
					ColorLabelEntry tmp = new ColorLabelEntry(
							-SpielerPosition.getSortId(player.getIdealPosition(), false)
								- (player.getIdealPosStaerke(true) / 100.0f),
							SpielerPosition.getNameForPosition(player.getIdealPosition()) 
								+ " (" 
								+ player.getIdealPosStaerke(true) 
								+ ")",
							ColorLabelEntry.FG_STANDARD,
							ColorLabelEntry.BG_STANDARD, SwingConstants.LEFT);
					tmp.setIcon(ThemeManager.getIcon((player.getUserPosFlag() < 0)?HOIconName.TOOTHEDWHEEL:HOIconName.HAND));
					return tmp;
				}
				@Override
				public boolean isEditable(){
					return false;
				}
				
			}; 
			
			// Position
			playerAdditionalArray[4] =new PlayerColumn(LINUP," ","Aufgestellt",28){
				@Override
				public IHOTableEntry getTableEntry(Spieler player,Spieler playerCompare){
					final HOModel model = HOVerwaltung.instance().getModel();
					if (model.getAufstellung().isSpielerAufgestellt(player.getSpielerID())
                            	&& (model.getAufstellung().getPositionBySpielerId(player
                                  .getSpielerID()) != null)) {
						return new ColorLabelEntry(ImageUtilities.getImage4Position(model.getAufstellung()
                       .getPositionBySpielerId(player.getSpielerID()),
                                               player.getTrikotnummer()),
                                               -model.getAufstellung()
                                               .getPositionBySpielerId(player
                                            		   .getSpielerID())
                                            		   .getSortId(),
                                            		   ColorLabelEntry.FG_STANDARD,
                                            		   ColorLabelEntry.BG_STANDARD, SwingConstants.CENTER);
					} 

					return new ColorLabelEntry(ImageUtilities.getImage4Position(null,
														player.getTrikotnummer()),
												-player.getTrikotnummer() - 1000,
												ColorLabelEntry.FG_STANDARD,
												ColorLabelEntry.BG_STANDARD,
												SwingConstants.CENTER);
												}
			}; 
				
			playerAdditionalArray[5] = new PlayerColumn(GROUP,"Gruppe",50){
				@Override
				public IHOTableEntry getTableEntry(Spieler player,Spieler playerCompare){
					SmilieEntry smilieEntry = new SmilieEntry();
					smilieEntry.setSpieler(player);
					return  smilieEntry;
				}
			}; 
				
			playerAdditionalArray[6] = new PlayerColumn(70,"Status",50){
				@Override
				public IHOTableEntry getTableEntry(Spieler player,Spieler playerCompare){
					SpielerStatusLabelEntry entry = new SpielerStatusLabelEntry();
					entry.setSpieler(player);
					return  entry;
				}
			}; 
			

			
			playerAdditionalArray[7] = new PlayerColumn(420,"Gehalt",100){
			@Override
			public IHOTableEntry getTableEntry(Spieler player,Spieler playerCompare){
				final String bonus = "";
				final int gehalt = (int) (player.getGehalt() / ho.core.model.UserParameter.instance().faktorGeld);
				final String gehalttext = Helper.getNumberFormat(true, 0).format(gehalt);
				if(playerCompare == null){
					return new DoppelLabelEntry(new ColorLabelEntry(gehalt,
                        gehalttext + bonus,
                        ColorLabelEntry.FG_STANDARD,
                        ColorLabelEntry.BG_STANDARD,
                        SwingConstants.RIGHT),
                        new ColorLabelEntry("",
                        ColorLabelEntry.FG_STANDARD,
                        ColorLabelEntry.BG_STANDARD,
                        SwingConstants.RIGHT));
				}

                final int gehalt2 = (int) (playerCompare.getGehalt() / ho.core.model.UserParameter
                                                                           .instance().faktorGeld);
				return new DoppelLabelEntry(new ColorLabelEntry(gehalt,
									                        gehalttext + bonus,
									                        ColorLabelEntry.FG_STANDARD,
									                        ColorLabelEntry.BG_STANDARD,
									                        SwingConstants.RIGHT),
									                        new ColorLabelEntry(gehalt - gehalt2,
									                        ColorLabelEntry.BG_STANDARD,
									                        true, false, 0));
			}
			}; 
			playerAdditionalArray[8] = new PlayerColumn(430,"TSI",0){
				@Override
				public IHOTableEntry getTableEntry(Spieler player,Spieler playerCompare){
					final String text = Helper.getNumberFormat(false, 0).format(player.getTSI());
					if(playerCompare == null){
						return new DoppelLabelEntry(new ColorLabelEntry(player
                            .getTSI(),
                            text,
                            ColorLabelEntry.FG_STANDARD,
                            ColorLabelEntry.BG_STANDARD,
                            SwingConstants.RIGHT),
                            new ColorLabelEntry("",
                            ColorLabelEntry.FG_STANDARD,
                            ColorLabelEntry.BG_STANDARD,
                            SwingConstants.RIGHT));
					}
					
					
					return new DoppelLabelEntry(new ColorLabelEntry(player
                            .getTSI(),
                            text,
                            ColorLabelEntry.FG_STANDARD,
                            ColorLabelEntry.BG_STANDARD,
                            SwingConstants.RIGHT),
                            new ColorLabelEntry(player.getTSI()
                                   - playerCompare.getTSI(), ColorLabelEntry.BG_STANDARD,
                                   false, false, 0));
				}
				@Override
				public void setSize(TableColumn column){
					column.setMinWidth(Helper.calcCellWidth(90));
					
				}
			};
				
			playerAdditionalArray[9] = new PlayerColumn(RATING,"Bewertung",50){
				@Override
				public IHOTableEntry getTableEntry(Spieler player,Spieler playerCompare){
					if (player.getBewertung() > 0) {
		                //Hat im letzen Spiel gespielt
		                return new RatingTableEntry(player.getBewertung(), true);
		            } 
		            
					return new RatingTableEntry(player.getLetzteBewertung(), false);
		            
				}
				
			};
			
			playerAdditionalArray[10] = new PlayerColumn(436,"Marktwert",140){
				@Override
				public IHOTableEntry getTableEntry(Spieler player,Spieler playerCompare){
					EPVData data = HOVerwaltung.instance().getModel().getEPV().getEPVData(player);
					double price = HOVerwaltung.instance().getModel().getEPV().getPrice(data);
					final String text = Helper.getNumberFormat(true, 0).format(price);

					if(playerCompare == null){
					
					return new DoppelLabelEntry(new ColorLabelEntry(price,
                            text,
                            ColorLabelEntry.FG_STANDARD,
                            ColorLabelEntry.BG_STANDARD,
                            SwingConstants.RIGHT),
                            new ColorLabelEntry("",
                            ColorLabelEntry.FG_STANDARD,
                            ColorLabelEntry.BG_STANDARD,
                            SwingConstants.RIGHT));
					
					}
					EPVData comparedata = HOVerwaltung.instance().getModel().getEPV().getEPVData(playerCompare);
					int htweek = HelperWrapper.instance().getHTWeek(playerCompare.getHrfDate());
					double compareepv = HOVerwaltung.instance().getModel().getEPV().getPrice(comparedata, htweek);
					return new DoppelLabelEntry(new ColorLabelEntry(price,
                            text,
                            ColorLabelEntry.FG_STANDARD,
                            ColorLabelEntry.BG_STANDARD,
                            SwingConstants.RIGHT),
                            new ColorLabelEntry((float)(price-compareepv),
                            		ColorLabelEntry.BG_STANDARD, 
                            		true, false, 0)
                            );
				}
			};
			
			playerAdditionalArray[11] = new PlayerColumn(437, "MC", "Motherclub",  25) {
				@Override
				public IHOTableEntry getTableEntry(Spieler player,Spieler playerCompare){
					HomegrownEntry home = new HomegrownEntry();
					home.setSpieler(player);
					setPreferredWidth(35);
					return  home;
				}
			};
				
			return playerAdditionalArray;
	}
	
	
	
}
