// %752552287:de.hattrickorganizer.tools%
package ho.core.util;

import ho.core.model.HOVerwaltung;
import plugins.ISpieler;


/**
 * PlayerHelper contains some static help methods for the playerObjects(Spieler)
 *
 * @author Thorsten Dietz
 */
public class PlayerHelper {
    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Parameter Documentation
     *
     * @param sub TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */

    //public static int currentTacticType = IMatchDetails.TAKTIK_NORMAL;

    /**
     * TODO Missing Method Documentation
     *
     * @param sub TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getName4Sublevel(int sub) {
        if (sub == 1) {
            return " ("
                   + HOVerwaltung.instance().getLanguageString("verylow")
                   + ")";
        } else if (sub == 2) {
            return " ("
                   + HOVerwaltung.instance().getLanguageString("low")
                   + ")";
        } else if (sub == 3) {
            return " ("
                   + HOVerwaltung.instance().getLanguageString("high")
                   + ")";
        } else if (sub == 0) {
            return " ("
                   + HOVerwaltung.instance().getLanguageString("veryhigh")
                   + ")";
        } else {
            return "";
        }
    }

    /**
     * Gibt den Namen zu einer Bewertungzurück
     *
     * @param level TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getNameForAggressivness(int level) {
        switch (level) {
            case ISpieler.PS_aufbrausend:
                return HOVerwaltung.instance().getLanguageString("fiery");

            case ISpieler.PS_temparamentvoll:
                return HOVerwaltung.instance().getLanguageString("temperamental");

            case ISpieler.PS_ausgeglichen:
                return HOVerwaltung.instance().getLanguageString("balanced");

            case ISpieler.PS_ruhig:
                return HOVerwaltung.instance().getLanguageString("calm");

            case ISpieler.PS_introvertiert:
                return HOVerwaltung.instance().getLanguageString("tranquil");

            default:
                return HOVerwaltung.instance().getLanguageString("Unbestimmt");
        }
    }

    /**
     * Gibt den Namen zu einer Bewertungzurück
     *
     * @param level TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getNameForCharacter(int level) {
        switch (level) {
            case ISpieler.CK_herzensgut:
                return HOVerwaltung.instance().getLanguageString("saint_like");

            case ISpieler.CK_rechtschaffen:
                return HOVerwaltung.instance().getLanguageString("righteous");

            case ISpieler.CK_aufrichtig:
                return HOVerwaltung.instance().getLanguageString("upright");

            case ISpieler.CK_ehrlich:
                return HOVerwaltung.instance().getLanguageString("honest");

            case ISpieler.CK_unehrlich:
                return HOVerwaltung.instance().getLanguageString("dishonest");

            case ISpieler.CK_niedertraechtig:
                return HOVerwaltung.instance().getLanguageString("infamous");

            default:
                return HOVerwaltung.instance().getLanguageString("Unbestimmt");
        }
    }

    /**
     * Gibt den Namen zu einer Bewertungzurück
     *
     * @param level TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getNameForGentleness(int level) {
        switch (level) {
            case ISpieler.BL_Integrationsfigur:
                return HOVerwaltung.instance().getLanguageString("beloved_team_member");

            case ISpieler.BL_beliebt:
                return HOVerwaltung.instance().getLanguageString("popular");

            case ISpieler.BL_sympathisch:
                return HOVerwaltung.instance().getLanguageString("sympathetic");

            case ISpieler.BL_angenehm:
                return HOVerwaltung.instance().getLanguageString("pleasant");

            case ISpieler.BL_umstritten:
                return HOVerwaltung.instance().getLanguageString("controversial");

            case ISpieler.BL_Ekel:
                return HOVerwaltung.instance().getLanguageString("nasty");

            default:
                return HOVerwaltung.instance().getLanguageString("Unbestimmt");
        }
    }

    /**
     * Gibt den Namen zu einer Bewertungzurück
     *
     * @param bewertung TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getNameForSkill(float bewertung) {
        return getNameForSkill(bewertung, gui.UserParameter.instance().zahlenFuerSkill);
    }

    /**
     * Gibt den Namen zu einer Bewertungzurück
     *
     * @param bewertungwert TODO Missing Constructuor Parameter Documentation
     * @param zahlen TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getNameForSkill(float bewertungwert, boolean zahlen) {
        return getNameForSkill(bewertungwert, zahlen, false);
    }

    /**
     * Gibt den Namen zu einer Bewertungzurück
     *
     * @param isMatch TODO Missing Constructuor Parameter Documentation
     * @param bewertungwert TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getNameForSkill(boolean isMatch, float bewertungwert) {
        return getNameForSkill(bewertungwert, gui.UserParameter.instance().zahlenFuerSkill, isMatch);
    }

    /**
     * Gibt den Namen zu einer Bewertungzurück
     *
     * @param bewertungwertfloat TODO Missing Constructuor Parameter Documentation
     * @param zahlen TODO Missing Constructuor Parameter Documentation
     * @param isMatch TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getNameForSkill(float bewertungwertfloat, boolean zahlen, boolean isMatch) {
        String bewertung = null;
        int bewertungwert = (int) bewertungwertfloat;

        //Für match
        int sublevel = 0;

        //Umrechnung für ein Spiel
        if (isMatch) {
            sublevel = (bewertungwert) % 4;

            //(int)Math.floor ( ( (float)bewertungwert)/4f ) +1; 
            bewertungwert = ((bewertungwert - 1) / 4) + 1;
        }

        switch (bewertungwert) {
        
        	case ISpieler.nicht_vorhanden: {
        		bewertung = HOVerwaltung.instance().getLanguageString("nonexisting");
        		break;
        	}
            
            case ISpieler.katastrophal: {
                bewertung = HOVerwaltung.instance().getLanguageString("katastrophal");
                break;
            }

            case ISpieler.erbaermlich: {
                bewertung = HOVerwaltung.instance().getLanguageString("erbaermlich");
                break;
            }

            case ISpieler.armselig: {
                bewertung = HOVerwaltung.instance().getLanguageString("armselig");
                break;
            }

            case ISpieler.schwach: {
                bewertung = HOVerwaltung.instance().getLanguageString("schwach");
                break;
            }

            case ISpieler.durchschnittlich: {
                bewertung = HOVerwaltung.instance().getLanguageString("durchschnittlich");
                break;
            }

            case ISpieler.passabel: {
                bewertung = HOVerwaltung.instance().getLanguageString("passabel");
                break;
            }

            case ISpieler.gut: {
                bewertung = HOVerwaltung.instance().getLanguageString("gut");
                break;
            }

            case ISpieler.sehr_gut: {
                bewertung = HOVerwaltung.instance().getLanguageString("sehr_gut");
                break;
            }

            case ISpieler.hervorragend: {
                bewertung = HOVerwaltung.instance().getLanguageString("hervorragend");
                break;
            }

            case ISpieler.grossartig: {
                bewertung = HOVerwaltung.instance().getLanguageString("grossartig");
                break;
            }

            case ISpieler.brilliant: {
                bewertung = HOVerwaltung.instance().getLanguageString("brilliant");
                break;
            }

            case ISpieler.fantastisch: {
                bewertung = HOVerwaltung.instance().getLanguageString("fantastisch");
                break;
            }

            case ISpieler.Weltklasse: {
                bewertung = HOVerwaltung.instance().getLanguageString("Weltklasse");
                break;
            }

            case ISpieler.uebernatuerlich: {
                bewertung = HOVerwaltung.instance().getLanguageString("uebernatuerlich");
                break;
            }

            case ISpieler.gigantisch: {
                bewertung = HOVerwaltung.instance().getLanguageString("gigantisch");
                break;
            }

            case ISpieler.ausserirdisch: {
                bewertung = HOVerwaltung.instance().getLanguageString("ausserirdisch");
                break;
            }

            case ISpieler.mythisch: {
                bewertung = HOVerwaltung.instance().getLanguageString("mythisch");
                break;
            }

            case ISpieler.maerchenhaft: {
                bewertung = HOVerwaltung.instance().getLanguageString("maerchenhaft");
                break;
            }

            case ISpieler.galaktisch: {
                bewertung = HOVerwaltung.instance().getLanguageString("galaktisch");
                break;
            }

            case ISpieler.goettlich: {
                bewertung = HOVerwaltung.instance().getLanguageString("goettlich");
                break;
            }

            default: {
                if (bewertungwert > ISpieler.goettlich) {
                    bewertung = HOVerwaltung.instance().getLanguageString("goettlich");
                } else {
                    bewertung = HOVerwaltung.instance().getLanguageString("Unbestimmt");
                }

                break;
            }
        }

        if (isMatch) {
            bewertung += getName4Sublevel(sublevel);
        }

        if (zahlen) {
            if (isMatch) {
                bewertung += (" ("
                +  Helper.getNumberFormat(false, gui.UserParameter.instance().anzahlNachkommastellen)
        		.format(Helper.round(bewertungwert + getValue4Sublevel(sublevel), 2))
                 + ")");
            } else {
                bewertung += (" ("
                		+ Helper.getNumberFormat(false, gui.UserParameter.instance().anzahlNachkommastellen)
                 		.format(Helper.round(bewertungwertfloat, gui.UserParameter.instance().anzahlNachkommastellen)) + ")");
            }
        }

        return bewertung;
    }

    /**
     * Gibt den Namen zu einer Bewertungzurück
     *
     * @param level TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static String getNameForSpeciality(int level) {
        switch (level) {
            case ISpieler.BALLZAUBERER:
                return HOVerwaltung.instance().getLanguageString("sp_Technical");

            case ISpieler.SCHNELL:
                return HOVerwaltung.instance().getLanguageString("sp_Quick");

            case ISpieler.DURCHSETZUGNSSTARK:
                return HOVerwaltung.instance().getLanguageString("sp_Powerful");

            case ISpieler.UNBERECHENBAR:
                return HOVerwaltung.instance().getLanguageString("sp_Unpredictable");

            case ISpieler.KOPFBALLSTARK:
                return HOVerwaltung.instance().getLanguageString("sp_Head");

            case ISpieler.REGAINER:
                return HOVerwaltung.instance().getLanguageString("sp_Regainer");
                
            default:
                return "";
        }
    }

    //    /**
    //     * Returns  1 for good SE 0 for no SE -1 for bad SE  Works only by TacticType = playing
    //     * creatively
    //     *
    //     * @param player TODO Missing Constructuor Parameter Documentation
    //     * @param currentPosition TODO Missing Constructuor Parameter Documentation
    //     *
    //     * @return TODO Missing Return Method Documentation
    //     */
    //    public static int getSpecialEventEffect(plugins.ISpieler player, int currentPosition, byte tactic) {
    //        int seEffect = 0;
    //    	if ((player != null)
    //            && (currentPosition != 0))
    //        {
    //        	int weather = HOMainFrame.getWetter();
    //            if ((player.getSpezialitaet() == ISpieler.SCHNELL)
    //                && !((currentPosition == ISpielerPosition.insideMid1)
    //                || (currentPosition == ISpielerPosition.insideMid2)
    //                || (currentPosition == ISpielerPosition.keeper)
    //                || (tactic == ISpielerPosition.ZUS_MITTELFELD))) {
    //                return 1;
    //            } // SCHNELL
    //
    //            if (player.getSpezialitaet() == ISpieler.BALLZAUBERER) {
    //                if ((currentPosition == ISpielerPosition.insideMid1)
    //                    || (currentPosition == ISpielerPosition.insideMid2)
    //                    || (currentPosition == ISpielerPosition.forward1)
    //                    || (currentPosition == ISpielerPosition.forward2)
    //                    || (currentPosition == ISpielerPosition.leftWinger)
    //                    || (currentPosition == ISpielerPosition.rightWinger)
    //                    || (tactic == ISpielerPosition.ZUS_MITTELFELD)
    //                    || (tactic == ISpielerPosition.ZUS_STUERMER)) {
    //                    seEffect = 1;
    //                	switch(weather){
    //                    case ISpieler.SONNIG:
    //                    	seEffect++; 
    //                    	break;
    //                    case ISpieler.REGEN:
    //                    	seEffect--;
    //                    	break;
    //                    }
    //                	return seEffect;
    //                }
    //            }// BALLZAUBERER
    //            
    //            if (player.getSpezialitaet() == ISpieler.KOPFBALLSTARK) {
    //            	if ((currentPosition == ISpielerPosition.insideMid1)
    //            	    || (currentPosition == ISpielerPosition.insideMid2)
    //            	    || (currentPosition == ISpielerPosition.leftBack)
    //            	    || (currentPosition == ISpielerPosition.insideBack1)
    //            	    || (currentPosition == ISpielerPosition.insideBack2)
    //            	    || (currentPosition == ISpielerPosition.rightBack)
    //            	    || (tactic == ISpielerPosition.ZUS_INNENV)
    //            	    || (tactic == ISpielerPosition.ZUS_MITTELFELD)){
    //            		return -1;
    //            	}
    //            } // KOPFBALLSTARK
    //            if (player.getSpezialitaet() == ISpieler.UNBERECHENBAR) {
    //            	if ((currentPosition == ISpielerPosition.forward1)
    //            	    || (currentPosition == ISpielerPosition.forward2)
    //            	    || (tactic == ISpielerPosition.ZUS_STUERMER)){
    //            		return 1;
    //            	}
    //            } // KOPFBALLSTARK
    //        }
    //        return seEffect;
    //    }

    /**
     * TODO Missing Method Documentation
     *
     * @param sub TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static double getValue4Sublevel(int sub) {
        if (sub == 1) {
            return 0.125;
        } else if (sub == 2) {
            return 0.375;
        } else if (sub == 3) {
            return 0.625;
        } else if (sub == 0) {
            return 0.875;
        } else {
            return 0;
        }
    }

    /*
       Wetterabhängige Sonderereignisse
       Bestimmte Spezialfähigkeiten können in Zusammenhang mit einem bestimmten Wetter
        zu Sonderereignissen führen. Die Auswirkung dieses Sonderereignisses tritt
        von dem Zeitpunkt in Kraft, an dem es im Spielbericht erwähnt wird,
        und hat bis zum Spielende Einfluß auf die Leistung des Spielers.
        Diese Auswirkung wird nach dem Spiel an der Spielerbewertung (Anzahl Sterne) sichtbar.
       Die Torschuß- und die Spielaufbau-Fähigkeit von Ballzauberern kann sich bei Regen verschlechtern,
        während sich die gleichen Fähigkeiten bei Sonnenschein verbessern können.
       Bei Regen gibt es die Möglichkeit, daß sich die Torschuß-, Verteidigungs- und Spielaufbau-Fähigkeit
        von durchsetzungsstarken Spielern verbessert.
        Auf der anderen Seite kann sich die Torschußfähigkeit bei Sonnenschein verschlechtern.
       Schnelle Spieler laufen bei Regen Gefahr, daß sich ihre Torschuß- und
        Verteidigungsfähigkeiten verschlechtern. Bei Sonnenschein besteht das Risiko
        , daß ihre Torschußfähigkeit unter dem Wetter leidet.
     */
    /*
       Liefert die mögliche Auswirkung des Wetters auf den Spieler
       return 0 bei keine auswirkung
       1 bei positiv
       -1 bei negativ
     */
    public static int getWeatherEffect(int wetter, int m_iSpezialitaet) {
        int ret = 0;

        switch (wetter) {
            case ISpieler.SONNIG:

                //zauberer
                if (m_iSpezialitaet == ISpieler.BALLZAUBERER) {
                    ret = 1;
                }
                //durchsetz
                else if (m_iSpezialitaet == ISpieler.DURCHSETZUGNSSTARK) {
                    ret = -1;
                }
                //schnell
                else if (m_iSpezialitaet == ISpieler.SCHNELL) {
                    ret = -1;
                }

                break;

            case ISpieler.LEICHTBEWOELKT:
                break;

            case ISpieler.BEWOELKT:
                break;

            case ISpieler.REGEN:

                //zauberer
                if (m_iSpezialitaet == ISpieler.BALLZAUBERER) {
                    ret = -1;
                }
                //durchsetz
                else if (m_iSpezialitaet == ISpieler.DURCHSETZUGNSSTARK) {
                    ret = 1;
                }
                //schnell
                else if (m_iSpezialitaet == ISpieler.SCHNELL) {
                    ret = -1;
                }

                break;
        }

        return ret;
    }

    ////////////////////////////////////////////////////////////////////////////////
    //  Calculation
    ////////////////////////////////////////////////////////////////////////////////         

    /**
     * check Skillup
     *
     * @param skill TODO Missing Constructuor Parameter Documentation
     * @param oldPlayer TODO Missing Constructuor Parameter Documentation
     * @param currentPlayer TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public static boolean check4SkillUp(int skill, ISpieler oldPlayer, ISpieler currentPlayer) {
        if ((oldPlayer != null) && (oldPlayer.getSpielerID() > 0)) {
        	if (oldPlayer.getValue4Skill4(skill) < currentPlayer.getValue4Skill4(skill)) {
//        		HOLogger.instance().debug(PlayerHelper.class, "Skillup for "+currentPlayer.getName()+" ("+currentPlayer.getSpielerID()+"), skill="+skill+", oldVal="+oldPlayer.getValue4Skill4(skill)+" < cur="+currentPlayer.getValue4Skill4(skill));
        		return true;
        	}
        }
        return false;
    }
}
