// %752552287:de.hattrickorganizer.tools%
package de.hattrickorganizer.tools;

import plugins.ISpieler;
import de.hattrickorganizer.model.HOVerwaltung;


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
                   + HOVerwaltung.instance().getResource().getProperty("verylow")
                   + ")";
        } else if (sub == 2) {
            return " ("
                   + HOVerwaltung.instance().getResource().getProperty("low")
                   + ")";
        } else if (sub == 3) {
            return " ("
                   + HOVerwaltung.instance().getResource().getProperty("high")
                   + ")";
        } else if (sub == 0) {
            return " ("
                   + HOVerwaltung.instance().getResource().getProperty("veryhigh")
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
                return HOVerwaltung.instance().getResource().getProperty("fiery");

            case ISpieler.PS_temparamentvoll:
                return HOVerwaltung.instance().getResource().getProperty("temperamental");

            case ISpieler.PS_ausgeglichen:
                return HOVerwaltung.instance().getResource().getProperty("balanced");

            case ISpieler.PS_ruhig:
                return HOVerwaltung.instance().getResource().getProperty("calm");

            case ISpieler.PS_introvertiert:
                return HOVerwaltung.instance().getResource().getProperty("tranquil");

            default:
                return HOVerwaltung.instance().getResource().getProperty("Unbestimmt");
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
                return HOVerwaltung.instance().getResource().getProperty("saint_like");

            case ISpieler.CK_rechtschaffen:
                return HOVerwaltung.instance().getResource().getProperty("righteous");

            case ISpieler.CK_aufrichtig:
                return HOVerwaltung.instance().getResource().getProperty("upright");

            case ISpieler.CK_ehrlich:
                return HOVerwaltung.instance().getResource().getProperty("honest");

            case ISpieler.CK_unehrlich:
                return HOVerwaltung.instance().getResource().getProperty("dishonest");

            case ISpieler.CK_niedertraechtig:
                return HOVerwaltung.instance().getResource().getProperty("infamous");

            default:
                return HOVerwaltung.instance().getResource().getProperty("Unbestimmt");
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
                return HOVerwaltung.instance().getResource().getProperty("beloved_team_member");

            case ISpieler.BL_beliebt:
                return HOVerwaltung.instance().getResource().getProperty("popular");

            case ISpieler.BL_sympathisch:
                return HOVerwaltung.instance().getResource().getProperty("sympathetic");

            case ISpieler.BL_angenehm:
                return HOVerwaltung.instance().getResource().getProperty("pleasant");

            case ISpieler.BL_umstritten:
                return HOVerwaltung.instance().getResource().getProperty("controversial");

            case ISpieler.BL_Ekel:
                return HOVerwaltung.instance().getResource().getProperty("nasty");

            default:
                return HOVerwaltung.instance().getResource().getProperty("Unbestimmt");
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
            case ISpieler.katastrophal: {
                bewertung = HOVerwaltung.instance().getResource().getProperty("katastrophal");
                break;
            }

            case ISpieler.erbaermlich: {
                bewertung = HOVerwaltung.instance().getResource().getProperty("erbaermlich");
                break;
            }

            case ISpieler.armselig: {
                bewertung = HOVerwaltung.instance().getResource().getProperty("armselig");
                break;
            }

            case ISpieler.schwach: {
                bewertung = HOVerwaltung.instance().getResource().getProperty("schwach");
                break;
            }

            case ISpieler.durchschnittlich: {
                bewertung = HOVerwaltung.instance().getResource().getProperty("durchschnittlich");
                break;
            }

            case ISpieler.passabel: {
                bewertung = HOVerwaltung.instance().getResource().getProperty("passabel");
                break;
            }

            case ISpieler.gut: {
                bewertung = HOVerwaltung.instance().getResource().getProperty("gut");
                break;
            }

            case ISpieler.sehr_gut: {
                bewertung = HOVerwaltung.instance().getResource().getProperty("sehr_gut");
                break;
            }

            case ISpieler.hervorragend: {
                bewertung = HOVerwaltung.instance().getResource().getProperty("hervorragend");
                break;
            }

            case ISpieler.grossartig: {
                bewertung = HOVerwaltung.instance().getResource().getProperty("grossartig");
                break;
            }

            case ISpieler.brilliant: {
                bewertung = HOVerwaltung.instance().getResource().getProperty("brilliant");
                break;
            }

            case ISpieler.fantastisch: {
                bewertung = HOVerwaltung.instance().getResource().getProperty("fantastisch");
                break;
            }

            case ISpieler.Weltklasse: {
                bewertung = HOVerwaltung.instance().getResource().getProperty("Weltklasse");
                break;
            }

            case ISpieler.uebernatuerlich: {
                bewertung = HOVerwaltung.instance().getResource().getProperty("uebernatuerlich");
                break;
            }

            case ISpieler.gigantisch: {
                bewertung = HOVerwaltung.instance().getResource().getProperty("gigantisch");
                break;
            }

            case ISpieler.ausserirdisch: {
                bewertung = HOVerwaltung.instance().getResource().getProperty("ausserirdisch");
                break;
            }

            case ISpieler.mythisch: {
                bewertung = HOVerwaltung.instance().getResource().getProperty("mythisch");
                break;
            }

            case ISpieler.maerchenhaft: {
                bewertung = HOVerwaltung.instance().getResource().getProperty("maerchenhaft");
                break;
            }

            case ISpieler.galaktisch: {
                bewertung = HOVerwaltung.instance().getResource().getProperty("galaktisch");
                break;
            }

            case ISpieler.goettlich: {
                bewertung = HOVerwaltung.instance().getResource().getProperty("goettlich");
                break;
            }

            default: {
                if (bewertungwert > ISpieler.goettlich) {
                    bewertung = HOVerwaltung.instance().getResource().getProperty("goettlich");
                } else {
                    bewertung = HOVerwaltung.instance().getResource().getProperty("Unbestimmt");
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
                + de.hattrickorganizer.tools.Helper.round(bewertungwert
                                                          + getValue4Sublevel(sublevel), 2) + ")");
            } else {
                bewertung += (" ("
                + de.hattrickorganizer.tools.Helper.round(bewertungwertfloat,
                                                          gui.UserParameter.instance().anzahlNachkommastellen)
                + ")");
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
                return HOVerwaltung.instance().getResource().getProperty("sp_Technical");

            case ISpieler.SCHNELL:
                return HOVerwaltung.instance().getResource().getProperty("sp_Quick");

            case ISpieler.DURCHSETZUGNSSTARK:
                return HOVerwaltung.instance().getResource().getProperty("sp_Powerful");

            case ISpieler.UNBERECHENBAR:
                return HOVerwaltung.instance().getResource().getProperty("sp_Unpredictable");

            case ISpieler.KOPFBALLSTARK:
                return HOVerwaltung.instance().getResource().getProperty("sp_Head");

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
            switch (skill) {
                case ISpieler.SKILL_STANDARDS:
                    return (oldPlayer.getStandards() < currentPlayer.getStandards()) ? true : false;

                case ISpieler.SKILL_PASSSPIEL:
                    return (oldPlayer.getPasspiel() < currentPlayer.getPasspiel()) ? true : false;

                case ISpieler.SKILL_TORSCHUSS:
                    return (oldPlayer.getTorschuss() < currentPlayer.getTorschuss()) ? true : false;

                case ISpieler.SKILL_SPIELAUFBAU:
                    return (oldPlayer.getSpielaufbau() < currentPlayer.getSpielaufbau()) ? true
                                                                                         : false;

                case ISpieler.SKILL_FLUEGEL:
                    return (oldPlayer.getFluegelspiel() < currentPlayer.getFluegelspiel()) ? true
                                                                                           : false;

                case ISpieler.SKILL_TORWART:
                    return (oldPlayer.getTorwart() < currentPlayer.getTorwart()) ? true : false;

                case ISpieler.SKILL_VERTEIDIGUNG:
                    return (oldPlayer.getVerteidigung() < currentPlayer.getVerteidigung()) ? true
                                                                                           : false;

                case ISpieler.SKILL_KONDITION:
                    return (oldPlayer.getKondition() < currentPlayer.getKondition()) ? true : false;

                case ISpieler.SKILL_EXPIERIENCE:
                    return (oldPlayer.getErfahrung() < currentPlayer.getErfahrung()) ? true : false;
            }
        }

        //No Skill ???
        return false;

        /*old
           java.sql.Timestamp  compareDate =   null;
           boolean             hasLevelUp  =   false;
           Object[]            obj         =   null;
        
           obj =   getLastLevelUp( skill );
           try
           {
               //hat levelup ?
               if ( ((Boolean)obj[1]).booleanValue() )
               {
                   compareDate = (java.sql.Timestamp) obj[0];
                   if ( (compareDate != null )
                   //und im Zeitrahmen von +- 10 Sec
           //                && ( compareDate.getTime() > hrftimestamp.getTime()-10000 )
           //                && ( compareDate.getTime() < hrftimestamp.getTime()+10000 ) )
                           && database.DBZugriff.instance().gleicheTrainingsWoche( hrftimestamp, compareDate ) )
                           {
                               hasLevelUp  =   true;
                           }
                           else
                           {
                               hasLevelUp  =   false;
                           }
                       }
                   }
                   catch (Exception e )
                   {
                       hasLevelUp = false;
                   }
        
                   return hasLevelUp;
         **/
    }
}
