// %1127327738165:hoplugins%
/*
 * ratingPrediction.java
 *
 * Created on 20. November 2004, 11:31
 */
package hoplugins;

import plugins.IPlugin;
import plugins.ISpieler;
import plugins.ISpielerPosition;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Timestamp;


/**
 * DOCUMENT ME!
 *
 * @author TheTom
 */
public class RatingPrediction implements plugins.IPlugin, ActionListener {
    //~ Instance fields ----------------------------------------------------------------------------

    private plugins.IHOMiniModel m_clModel = null;
    private float m_fErfahrung = 0.428f;
    private float m_fForm = 0.502f;

    /* old version 1 values from HO, new ones are analysed!
       private float                           m_fKF           =   0.8f;
       private float                           m_fErfahrung    =   0.35f;
       private float                           m_fForm         =   0.6f;*/
    private float m_fKF = 0.461f;
    private short m_sHome = 0;
    private short m_sPM = 0;
    private short m_sSelbstvertrauen = 0;
    private short m_sStimmung = 0;
    private short m_sTacticTyp = 0;
    private short m_sTrainerTyp = 0;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of ratingPrediction
     */
    public RatingPrediction() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    ///////////////////////////////////////////////////////////////////////////////
    //Taktiken
    /////////////////////////////////////////////////////////////////////////////
    public float getAow_AimRatings() {
        float psOut = 0.0f;
        float zPsOut = 0.0f;
        float rating = 0.0f;

        /*
         *TArating=p(1)*(PSout+p(2)*zPSout)+P(3)
             EXT PARAMETER                  PARABOLIC         MINOS ERRORS
              NO.   NAME        VALUE          ERROR      NEGATIVE      POSITIVE
               1      P1       0.20331       0.50758E-02  -0.97254E-02   0.11949E-01
               2      P2       0.85284       0.80906E-01  -0.78982E-01   0.80823E-01
               3      P3        1.7430       0.14388      -0.39528       at limit
             CHISQUARE = 0.8110E+00  NPFIT =    96
         */
        float P1 = 0.20331f;
        float P2 = 0.85284f;
        float P3 = 1.7430f;

        for (int i = ISpielerPosition.rightBack; i < ISpielerPosition.beginnReservere; i++) {
            ISpieler player = m_clModel.getLineUP().getPlayerByPositionID(i);
            byte tactic = m_clModel.getLineUP().getTactic4PositionID(i);

            if (player != null) {
                //Zus Player
                if ((tactic == ISpielerPosition.ZUS_INNENV)
                    || (tactic == ISpielerPosition.ZUS_MITTELFELD)
                    || (tactic == ISpielerPosition.ZUS_STUERMER)) {
                    zPsOut += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                } else //norm Player
                 {
                    psOut += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                }
            }
        }

        //TW+IV
        rating = (P1 * (psOut + (P2 * zPsOut))) + P3;

        return rating;
    }

    /*
       private float getEffectivePA( int erf, float skill, int form )
        {
            float   skillEff   =   0.0f;
            float   ff      =   0.0f;
            float   eb      =   0.0f;
    
            eb      =   (float) (2.0f*m_fErfahrung* (Math.log( erf )/Math.log(10)));
            ff      =   (float) Math.pow( ( form -1 ) / 7.0f, m_fForm*7.0f/ 8.0f );
            skillEff   =   ff  * skill + eb;
    
            return skillEff;
        }
     */
    public float getCentralAttackRatings() {
        float st = 0.0f;
        float stDef = 0.0f;
        float zSt = 0.0f;
        float stPa = 0.0f;
        float stDefPa = 0.0f;
        float zStPa = 0.0f;
        float im = 0.0f;
        float imOff = 0.0f;
        float imDef = 0.0f;
        float zIm = 0.0f;
        float midAtt = 0.0f;

        /*
           FCN=   374.6639     FROM MINOS     STATUS=SUCCESSFUL  1829 CALLS     2390 TOTAL
                                EDM=  0.24E-03    STRATEGY= 1      ERROR MATRIX ACCURATE
             EXT PARAMETER                  PARABOLIC         MINOS ERRORS
             NO.   NAME        VALUE          ERROR      NEGATIVE      POSITIVE
              1      P1       0.22678       0.41357E-02  -0.47691E-02   0.48601E-02
              2      P2       0.93063       0.22975E-01  -0.24020E-01   0.24162E-01
              3      P3       0.58560       0.32296E-01  -0.32789E-01   0.32494E-01
              4      P4       0.22493       0.27263E-01  -0.26951E-01   0.27713E-01
              5      P5       0.27078       0.21201E-01  -0.17586E-01   0.17758E-01
              6      P6       0.59990       0.82754E-01  -0.73078E-01   0.77794E-01
              7      P7       0.85600E-01   0.44100E-02  -0.43702E-02   0.43795E-02
              8      P8       0.84391E-02   0.26618E-02  -0.26750E-02   0.26962E-02
              9      P9       0.62981       0.74397E-01  -0.52887E-01   0.52110E-01
            CHISQUARE = 0.9808E+00  NPFIT =   391
         **/
        float P1 = 0.22678f;
        float P2 = 0.93063f;
        float P3 = 0.58560f;
        ;

        float P4 = 0.22493f;
        float P5 = 0.27078f;
        float P6 = 0.59990f;
        float P7 = 0.0856f;
        float P8 = 0.00844f;
        float P9 = 0.62981f;
        int trainer = 0;

        //Spieler durchlaufen und MF-Leute Filtern
        for (int i = ISpielerPosition.keeper; i < ISpielerPosition.beginnReservere; i++) {
            ISpieler player = m_clModel.getLineUP().getPlayerByPositionID(i);
            byte tactic = m_clModel.getLineUP().getTactic4PositionID(i);

            if (player != null) {
                //st
                if (((i == ISpielerPosition.forward1) || (i == ISpielerPosition.forward2))
                    && (tactic == ISpielerPosition.NORMAL)) {
                    st += getEffectiveSkillvalue(player, ISpieler.SKILL_TORSCHUSS);
                    stPa += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                }

                //ST_DEV
                if (((i == ISpielerPosition.forward1) || (i == ISpielerPosition.forward2))
                    && (tactic == ISpielerPosition.DEFENSIV)) {
                    stDef += getEffectiveSkillvalue(player, ISpieler.SKILL_TORSCHUSS);
                    stDefPa += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                }

                //zus ST
                if (tactic == ISpielerPosition.ZUS_STUERMER) {
                    zSt += getEffectiveSkillvalue(player, ISpieler.SKILL_TORSCHUSS);
                    zStPa += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                }

                //Inner MF + IMnA
                if (((i == ISpielerPosition.insideMid1) || (i == ISpielerPosition.insideMid2))
                    && ((tactic == ISpielerPosition.NORMAL)
                    || (tactic == ISpielerPosition.NACH_AUSSEN))) {
                    im += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                }

                //Zus MF
                if (tactic == ISpielerPosition.ZUS_MITTELFELD) {
                    zIm += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                }

                //Im OFF
                if (((i == ISpielerPosition.insideMid1) || (i == ISpielerPosition.insideMid2))
                    && (tactic == ISpielerPosition.OFFENSIV)) {
                    imOff += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                }

                //IM-Def
                if (((i == ISpielerPosition.insideMid1) || (i == ISpielerPosition.insideMid2))
                    && (tactic == ISpielerPosition.DEFENSIV)) {
                    imDef += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                }
            }
        }

        //Torschuss ST
        midAtt = (st + (P2 * zSt) + (P3 * stDef));

        //Passing ST (STdef has same influence of passing than normal ST!!!)
        midAtt += (P4 * (stPa + (P2 * zStPa) + (P3 * stDefPa)));

        //MF Passing
        midAtt += (P5 * (im + ((1 + P6) * imOff) + ((1 - P6) * imDef) + (P2 * zIm)));

        //Trainer + Selbstvertrauen
        if (m_sTrainerTyp == 1) {
            midAtt *= (1 + P7);
        } else if (m_sTrainerTyp == 0) {
            midAtt *= (1 - P7);
        }

        midAtt = (P1 * midAtt * (1 + (P8 * (m_sSelbstvertrauen - 5)))) + P9;

        return midAtt;
    }

    /////////////////////////////////////////////////////////////////
    //central Defense
    /////////////////////////////////////////////////////////////////

    /*
       private float getEffectiveVE( int erf, float skill, int form )
       {
           float   skillEff   =   0.0f;
           float   ff      =   0.0f;
           float   eb      =   0.0f;
    
           eb      =   (float) (2.0f*m_fErfahrung* (Math.log( erf )/Math.log(10)));
           ff      =   (float) Math.pow( ( form -1 ) / 7.0f, m_fForm*7.0f/ 8.0f );
           skillEff   =   ff  * skill + eb;
    
           return skillEff;
       }
    
       private float getEffectiveTW( int erf, float skill, int form )
       {
           float   skillEff   =   0.0f;
           float   ff      =   0.0f;
           float   eb      =   0.0f;
    
           eb      =   (float) (2.0f*m_fErfahrung* (Math.log( erf )/Math.log(10)));
           ff      =   (float) Math.pow( ( form -1 ) / 7.0f, m_fForm*7.0f/ 8.0f );
           skillEff   =   ff  * skill + eb;
    
           return skillEff;
       }
     */
    public float getCentralDefenseRatings() {
        float iv = 0.0f;
        float ivOff = 0.0f;
        float ivDef = 0.0f;
        float ivNa = 0.0f;
        float tw = 0.0f;
        float av = 0.0f;
        float avDef = 0.0f;
        float avOff = 0.0f;
        float avZm = 0.0f;
        float im = 0.0f;
        float zIm = 0.0f;
        float imOff = 0.0f;
        float imDef = 0.0f;
        float flZm = 0.0f;
        float fl = 0.0f;
        float flOff = 0.0f;
        float flDef = 0.0f;
        float zIv = 0.0f;
        float rating = 0.0f;

        /*  FCN=   220.8350     FROM MINOS     STATUS=SUCCESSFUL 12425 CALLS    13558 TOTAL
           EDM=  0.47E-03    STRATEGY= 1      ERR MATRIX NOT POS-DEF
           EXT PARAMETER                  PARABOLIC         MINOS ERRORS
           NO.   NAME        VALUE          ERROR      NEGATIVE      POSITIVE
            1     P1        0.22735       0.56946E-02  -0.13023E-01   0.13792E-01
            2     P2        0.92502       0.33689E-01  -0.62702E-01   0.66530E-01
            3     P3        0.68150       0.41008E-01  -0.40933E-01   0.41889E-01
            4     P4        0.46024       0.27082E-01  -0.30118E-01   0.29102E-01
            5     P5        0.49326       0.48394E-01  -0.50434E-01   0.51414E-01
            6     P6        0.42613       0.20296E-01  -0.39804E-01   0.42073E-01
            7     P7        0.42579       0.41206E-01  -0.59023E-01   0.64827E-01
            8     P8         1.1550       0.25265      -0.25816       0.26473
            9     P9        0.29191       0.21539E-01  -0.31608E-01   0.33180E-01
           10     P10       0.70349       0.90048E-01  -0.91501E-01   0.10305
           11     P11       0.14579       0.40989E-01  -0.38136E-01   0.38655E-01
           12     P12       0.61893       0.16088      -0.16406       0.16377
           13     P13        1.3494       0.35773      -0.29988       0.44417
           14     P14       0.11123       0.38793E-02  -0.41616E-02   0.40970E-02
           15     P15       0.13117       0.13115E-01  -0.13228E-01   0.13191E-01
           16     P16       0.59061       0.52399E-01  -0.74001E-01   0.71113E-01
           CHISQUARE = 0.5827E+00  NPFIT =   395
         */
        float P1 = 0.22735f;
        float P2 = 0.92502f;
        float P3 = 0.68150f;
        ;

        float P4 = 0.46024f;
        float P5 = 0.49326f;
        float P6 = 0.42613f;
        float P7 = 0.42579f;
        float P8 = 1.1550f;
        float P9 = 0.29191f;
        float P10 = 0.70349f;
        float P11 = 0.14579f;
        float P12 = 0.61893f;
        float P13 = 1.3494f;
        float P14 = 0.11123f;
        float P15 = 0.13117f;
        float P16 = 0.59061f;

        //Spieler durchlaufen und MF-Leute Filtern
        for (int i = ISpielerPosition.keeper; i < ISpielerPosition.beginnReservere; i++) {
            ISpieler player = m_clModel.getLineUP().getPlayerByPositionID(i);
            byte tactic = m_clModel.getLineUP().getTactic4PositionID(i);

            if (player != null) {
                //TW                
                //tw
                if (i == ISpielerPosition.keeper) {
                    tw += getEffectiveSkillvalue(player, ISpieler.SKILL_TORWART);
                }

                //IV                
                //iv
                if (((i == ISpielerPosition.insideBack1) || (i == ISpielerPosition.insideBack2))
                    && (tactic == ISpielerPosition.NORMAL)) {
                    iv += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //ivOff
                if (((i == ISpielerPosition.insideBack1) || (i == ISpielerPosition.insideBack2))
                    && (tactic == ISpielerPosition.OFFENSIV)) {
                    ivOff += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //ivnA
                if (((i == ISpielerPosition.insideBack1) || (i == ISpielerPosition.insideBack2))
                    && (tactic == ISpielerPosition.NACH_AUSSEN)) {
                    ivNa += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //Zus iv
                if (tactic == ISpielerPosition.ZUS_INNENV) {
                    zIv += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //AV                
                //av
                if (((i == ISpielerPosition.leftBack) || (i == ISpielerPosition.rightBack))
                    && (tactic == ISpielerPosition.NORMAL)) {
                    av += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //avOff
                if (((i == ISpielerPosition.leftBack) || (i == ISpielerPosition.rightBack))
                    && (tactic == ISpielerPosition.OFFENSIV)) {
                    avOff += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //avZm
                if (((i == ISpielerPosition.leftBack) || (i == ISpielerPosition.rightBack))
                    && (tactic == ISpielerPosition.ZUR_MITTE)) {
                    avZm += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //av Def
                if (((i == ISpielerPosition.leftBack) || (i == ISpielerPosition.rightBack))
                    && (tactic == ISpielerPosition.DEFENSIV)) {
                    avDef += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //FL                
                //fl
                if (((i == ISpielerPosition.leftWinger) || (i == ISpielerPosition.rightWinger))
                    && (tactic == ISpielerPosition.NORMAL)) {
                    fl += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //flOff
                if (((i == ISpielerPosition.leftWinger) || (i == ISpielerPosition.rightWinger))
                    && (tactic == ISpielerPosition.OFFENSIV)) {
                    flOff += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //flZm
                if (((i == ISpielerPosition.leftWinger) || (i == ISpielerPosition.rightWinger))
                    && (tactic == ISpielerPosition.ZUR_MITTE)) {
                    flZm += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //fl def
                if (((i == ISpielerPosition.leftWinger) || (i == ISpielerPosition.rightWinger))
                    && (tactic == ISpielerPosition.DEFENSIV)) {
                    flDef += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //MF                
                //Inner MF + IMnA
                if (((i == ISpielerPosition.insideMid1) || (i == ISpielerPosition.insideMid2))
                    && ((tactic == ISpielerPosition.NORMAL)
                    || (tactic == ISpielerPosition.NACH_AUSSEN))) {
                    im += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //Zus MF
                if (tactic == ISpielerPosition.ZUS_MITTELFELD) {
                    zIm += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //Im OFF
                if (((i == ISpielerPosition.insideMid1) || (i == ISpielerPosition.insideMid2))
                    && (tactic == ISpielerPosition.OFFENSIV)) {
                    imOff += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //IM-Def
                if (((i == ISpielerPosition.insideMid1) || (i == ISpielerPosition.insideMid2))
                    && (tactic == ISpielerPosition.DEFENSIV)) {
                    imDef += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }
            }
        }

        //TW+IV
        //rating  =   P2 *  tw + iv  + P3 * zIv + P4 * ivOff + P5 * ivNa;
        rating = tw + (P2 * (iv + (P3 * zIv) + (P4 * ivOff) + (P5 * ivNa)));

        //AV
        rating += (P6 * (av + ((1 + P7) * avDef) + ((1 - P7) * avOff) + (P8 * avZm)));

        //IM
        rating += (P9 * (im + ((1 + P10) * imDef) + ((1 - P10) * imOff) + (P3 * zIm)));

        //FL
        rating += (P11 * (fl + ((1 + P12) * flDef) + ((1 - P12) * flOff) + (P13 * flZm)));

        //Trainer def/off ==> increase/decrease rating
        if (m_sTrainerTyp == 1) {
            rating *= (P1 * (1 - P14));
        } else if (m_sTrainerTyp == 0) {
            rating *= (P1 * (1 + P14));
        } else {
            rating *= P1;
        }

        // AoW? ==> reduce rating...
        if (m_sTacticTyp == plugins.IMatchDetails.TAKTIK_WINGS) {
            rating *= (1 - P15);
        }

        rating += P16;

        return rating;
    }

    /**
     * eingehen tun wieder die korrigierten/redusierten VE und PS skillsummen der verteidiger
     * (incl. subskills):
     *
     * @return TODO Missing Return Method Documentation
     */
    public float getKonterRatings() {
        float veDef = 0.0f;
        float zVeDef = 0.0f;
        float psDef = 0.0f;
        float zPsDef = 0.0f;
        float rating = 0.0f;

        /*
         *Regelkonform :
         *
             tactic=p(1)*(VEdef+p(2)*zVEdef+2*(PSdef+p(2)*zPSdef))+P(3)
             mit
               EXT PARAMETER                  PARABOLIC         MINOS ERRORS
                NO.   NAME        VALUE          ERROR      NEGATIVE      POSITIVE
                 1      P1       0.12162       0.85877E-02  -0.10103E-01   0.10303E-01
                 2      P2       0.92487       0.12716      -0.12904       0.14439
                 3      P3        1.3214       0.36766      -0.45544       0.44712
               CHISQUARE = 0.1540E+00  NPFIT =   108
         *
         *Ralf Extended
         *tactic=p(1)*(VEdef+p(2)*zVEdef+P(4)*(PSdef+p(2)*zPSdef))+P(3)
             mit
                EXT PARAMETER                  PARABOLIC         MINOS ERRORS
                NO.   NAME        VALUE          ERROR      NEGATIVE      POSITIVE
                 1      P1       0.17064       0.19289E-01  -0.25303E-01   0.26063E-01
                 2      P2       0.71829       0.12733      -0.13627       0.15365
                 3      P3       0.79669       0.45529      -0.51923       0.51296
                 4      P4        1.2573       0.18057      -0.23291       0.29052
               CHISQUARE = 0.1119E+00  NPFIT =   108
         */
        float P1 = 0.12162f;
        float P2 = 0.92487f;
        float P3 = 1.3214f;

        //        float   P4      =   1.2573f;
        for (int i = ISpielerPosition.rightBack; i < ISpielerPosition.beginnReservere; i++) {
            ISpieler player = m_clModel.getLineUP().getPlayerByPositionID(i);
            byte tactic = m_clModel.getLineUP().getTactic4PositionID(i);

            if (player != null) {
                //Verteidiger
                if (((i == ISpielerPosition.insideBack1) || (i == ISpielerPosition.insideBack2)
                    || (i == ISpielerPosition.leftBack) || (i == ISpielerPosition.rightBack))
                    && (tactic <= ISpielerPosition.ZUS_STUERMER)) {
                    veDef += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                    psDef += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                }

                if (tactic == ISpielerPosition.ZUS_INNENV) //zIv
                 {
                    zVeDef += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                    zPsDef += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                }
            }
        }

        //Regel konform
        rating = (P1 * (veDef + (P2 * zVeDef) + (2 * (psDef + (P2 * zPsDef))))) + P3;

        //Ralf Extended
        //        rating  =   P1 *  (  veDef + P2 * zVeDef + P4* ( psDef + P2 * zPsDef ) ) + P3;
        return rating;
    }

    /////////////////////////////////////////////////////////////////
    //Left Attack
    /////////////////////////////////////////////////////////////////
    public float getLeftAttackRatings() {
        float st = 0.0f;
        float stDef = 0.0f;
        float zSt = 0.0f;

        /*
           float   stPa=0.0f;
           float   stDefPa=0.0f;
           float   zStPa=0.0f;
         */

        //fl's
        float fl = 0.0f;
        float flOff = 0.0f;
        float flDef = 0.0f;
        float flZm = 0.0f;
        float flPa = 0.0f;
        float flOffPa = 0.0f;
        float flDefPa = 0.0f;
        float flZmPa = 0.0f;

        //im's
        float im = 0.0f;
        float imOff = 0.0f;
        float imDef = 0.0f;
        float zIm = 0.0f;
        float imNa = 0.0f;
        float imNaF = 0.0f;

        //av's
        float av = 0.0f;
        float avOff = 0.0f;
        float avDef = 0.0f;
        float avZm = 0.0f;

        //rating
        float rating = 0.0f;

        //was ist mit im aussen , wzm ?

        /*
           FCN=   595.7597     FROM MINOS     STATUS=PROBLEMS   20142 CALLS    21499 TOTAL
                               EDM=  0.11E-02    STRATEGY= 1      ERROR MATRIX ACCURATE
            EXT PARAMETER                  PARABOLIC         MINOS ERRORS
            NO.   NAME        VALUE          ERROR      NEGATIVE      POSITIVE
             1     P1        0.22418       0.48947E-02  -0.67772E-02   0.70835E-02
             2     P2        0.46005       0.28396E-01  -0.33801E-01   0.33749E-01
             3     P3        0.46161       0.27417E-01  -0.26794E-01   0.26906E-01
             4     P4        0.62703       0.12924E-01                 0.23651E-01
             5     P5        0.92875       0.30216E-01  -0.34427E-01   0.35313E-01
             6     P6        0.38172       0.35869E-01  -0.36938E-01   0.36610E-01
             7     P7        0.28117       0.28993E-01  -0.30937E-01   0.31854E-01
             8     P8        0.26633       0.30661E-01  -0.27690E-01   0.27144E-01
             9     P9       -0.11191       0.65787E-01  -0.65306E-01   0.64667E-01
            10     P10       0.42569       0.11226      -0.91563E-01   0.10047
            11     P11        1.1314        1.9182      -0.81705       at limit
            12     P12        1.7388        2.0330      -0.85167       at limit
            13     P13       0.26043       0.20973E-01  -0.21129E-01   0.21675E-01
            14     P14        1.1935       0.20030      -0.18479       0.20793
            15     P15       0.12548       0.40520      -0.41819       0.42005
            16     P16       0.91054E-01   0.35957E-02  -0.35950E-02   0.35990E-02
            17     P17       0.77779E-02   0.19504E-02  -0.19581E-02   0.19692E-02
            18     P18       0.63945       0.34796E-01  -0.38746E-01   0.38232E-01
           CHISQUARE = 0.7717E+00  NPFIT =   790
         **/
        float P1 = 0.22418f;
        float P2 = 0.46005f;
        float P3 = 0.46161f;
        ;

        float P4 = 0.62703f;
        float P5 = 0.92875f;
        float P6 = 0.38172f;
        float P7 = 0.28117f;
        float P8 = 0.26633f;
        float P9 = -0.11191f;
        float P10 = 0.42569f;
        float P11 = 1.1314f;
        float P12 = 1.7388f;
        float P13 = 0.26043f;
        float P14 = 1.1935f;
        float P15 = 0.12548f;
        float P16 = 0.091054f;
        float P17 = 0.0077779f;
        float P18 = 0.63945f;
        int trainer = 0; //balanced

        //Trainertyp umrechnen
        if (m_sTrainerTyp == 1) //off
         {
            trainer = 1;
        } else if (m_sTrainerTyp == 0) //def
         {
            trainer = -1;
        }

        //Spieler durchlaufen und MF-Leute Filtern
        for (int i = ISpielerPosition.keeper; i < ISpielerPosition.beginnReservere; i++) {
            ISpieler player = m_clModel.getLineUP().getPlayerByPositionID(i);
            byte tactic = m_clModel.getLineUP().getTactic4PositionID(i);

            if (player != null) {
                //st
                if (((i == ISpielerPosition.forward1) || (i == ISpielerPosition.forward2))
                    && (tactic == ISpielerPosition.NORMAL)) {
                    st += getEffectiveSkillvalue(player, ISpieler.SKILL_TORSCHUSS);

                    //stPa     += getEffectiveSkillvalue ( player, ISpieler.SKILL_PASSSPIEL  );
                }

                //ST_DEV
                if (((i == ISpielerPosition.forward1) || (i == ISpielerPosition.forward2))
                    && (tactic == ISpielerPosition.DEFENSIV)) {
                    stDef += getEffectiveSkillvalue(player, ISpieler.SKILL_TORSCHUSS);

                    //stDefPa      += getEffectiveSkillvalue ( player, ISpieler.SKILL_PASSSPIEL  );
                }

                //zus ST
                if (tactic == ISpielerPosition.ZUS_STUERMER) {
                    zSt += getEffectiveSkillvalue(player, ISpieler.SKILL_TORSCHUSS);

                    //zStPa      += getEffectiveSkillvalue ( player, ISpieler.SKILL_PASSSPIEL  );
                }

                //Inner MF + 
                if ((i == ISpielerPosition.insideMid2) && (tactic == ISpielerPosition.NORMAL)) {
                    im += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                } //IMnA

                if ((i == ISpielerPosition.insideMid2) && (tactic == ISpielerPosition.NACH_AUSSEN)) {
                    imNa += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                    imNaF += getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
                }

                //Inner MF off
                if ((i == ISpielerPosition.insideMid2) && (tactic == ISpielerPosition.OFFENSIV)) {
                    imOff += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                }

                //Zus MF
                if ((tactic == ISpielerPosition.ZUS_MITTELFELD)
                    && ((i == ISpielerPosition.leftBack) || (i == ISpielerPosition.insideBack2)
                    || (i == ISpielerPosition.forward2))) {
                    zIm += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                }

                //IM-Def
                if ((i == ISpielerPosition.insideMid2) && (tactic == ISpielerPosition.DEFENSIV)) {
                    imDef += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                }

                //FL
                if ((i == ISpielerPosition.leftWinger) && (tactic == ISpielerPosition.NORMAL)) {
                    fl += getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
                    flPa += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                }

                //FL
                if ((i == ISpielerPosition.leftWinger) && (tactic == ISpielerPosition.DEFENSIV)) {
                    flDef += getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
                    flDefPa += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                }

                //FL
                if ((i == ISpielerPosition.leftWinger) && (tactic == ISpielerPosition.OFFENSIV)) {
                    flOff += getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
                    flOffPa += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                }

                //Fl Zur Mitt
                if ((i == ISpielerPosition.leftWinger) && (tactic == ISpielerPosition.ZUR_MITTE)) {
                    flZm += getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
                    flZmPa += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                }

                //av
                if ((i == ISpielerPosition.leftBack) && (tactic == ISpielerPosition.NORMAL)) {
                    av += getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
                }

                //avOff
                if ((i == ISpielerPosition.leftBack) && (tactic == ISpielerPosition.OFFENSIV)) {
                    avOff += getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
                }

                //avZm
                if ((i == ISpielerPosition.leftBack) && (tactic == ISpielerPosition.ZUR_MITTE)) {
                    avZm += getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
                }

                //av Def
                if ((i == ISpielerPosition.leftBack) && (tactic == ISpielerPosition.DEFENSIV)) {
                    avDef += getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
                }
            }
        }

        /*
        
         * FL winger
                      sidatt=(FL+(1+p(2))*FLoff+(1-p(2))*FLdef+p(3)*FLzm)
         * ST torschuss (BOTH scorers)
                      sidatt=sidatt+p(4)*(ST+p(5)*zST+p(6)*STdef)
         * FL passing (passing of all wingers counting the same, in opp. to winger skill)
                      sidatt=sidatt+p(7)*(FLps+FLoffps+FLdefps+FLzmps)
         * MF passing (& IMnA winger)
                      sidatt=sidatt+p(8)*(IM+(1+p(9))*IMoff+(1-p(9))*IMdef+p(10)*zIM+p(11)*IMna+p(12)*IMnafl)
         * AV winger
                      sidatt=sidatt+p(13)*(AV+(1+p(14))*AVoff+max(0,(1-p(14)))*AVdef+p(15)*AVzm)
         * trainer (selbstvertrauen sehr kleinen einfluss)
                      sidatt=p(1)*sidatt*(1+p(16)*Tr)*(1+p(17)*s)+p(18)
         */
        //flWinger
        rating = (fl + ((1 + P2) * flOff) + ((1 - P2) * flDef) + (P3 * flZm));

        //Torschuss ST
        rating = rating + (P4 * (st + (P5 * zSt) + (P6 * stDef)));

        // FL passing (passing of all wingers counting the same, in opp. to winger skill)
        rating = rating + (P7 * (flPa + flOffPa + flDefPa + flZmPa));

        //MF Passing
        rating += (P8 * (im + ((1 + P9) * imOff) + ((1 - P9) * imDef) + (P10 * zIm) + (P11 * imNa)
        + (P12 * imNaF)));

        //av Winger
        rating += (P13 * (av + ((1 + P14) * avOff) + Math.max(0, (1 - P14) * avDef) + (P15 * avZm)));

        //transient + sv
        rating = (P1 * rating * (1 + (P16 * trainer)) * (1 + (P17 * (m_sSelbstvertrauen - 5))))
                 + P18;

        return rating;
    }

    /////////////////////////////////////////////////////////////////
    //Left Defense
    /////////////////////////////////////////////////////////////////
    public float getLeftDefenseRatings() {
        float iv = 0.0f;
        float ivOff = 0.0f;
        float ivDef = 0.0f;
        float ivNa = 0.0f;
        float tw = 0.0f;
        float av = 0.0f;
        float avDef = 0.0f;
        float avOff = 0.0f;
        float avZm = 0.0f;
        float im = 0.0f;
        float zIm = 0.0f;
        float imOff = 0.0f;
        float imDef = 0.0f;
        float flZm = 0.0f;
        float fl = 0.0f;
        float flOff = 0.0f;
        float flDef = 0.0f;
        float zIv = 0.0f;
        float rating = 0.0f;

        /*  FCN=   833.8134     FROM MINOS     STATUS=SUCCESSFUL 10187 CALLS    11071 TOTAL
           EDM=  0.11E-02    STRATEGY= 1      ERROR MATRIX ACCURATE
           EXT PARAMETER                  PARABOLIC         MINOS ERRORS
           NO.   NAME        VALUE          ERROR      NEGATIVE      POSITIVE
            1     P1        0.27503       0.88160E-02  -0.86216E-02   0.88400E-02
            2     P2        0.60816       0.31009E-01  -0.25434E-01   0.26279E-01
            3     P3        0.45018       0.33133E-01  -0.33260E-01   0.34052E-01
            4     P4        0.49981       0.42695E-01  -0.35092E-01   0.34890E-01
            5     P5         1.5272       0.92394E-01  -0.68772E-01   0.69605E-01
            6     P6        0.92390       0.62647E-01  -0.41465E-01   0.42709E-01
            7     P7        0.39523       0.65569E-01  -0.23189E-01   0.24306E-01
            8     P8        0.70864       0.96393E-01  -0.95985E-01   0.96259E-01
            9     P9        0.22821       0.18147E-01  -0.23549E-01   0.24071E-01
           10     P10       0.92868       0.10982      -0.11652       0.13575
           11     P11       0.39647       0.33299E-01  -0.35847E-01   0.36810E-01
           12     P12       0.56108       0.52660E-01  -0.53719E-01   0.53528E-01
           13     P13       0.61458       0.92649E-01  -0.87215E-01   0.92919E-01
           14     P14       0.11098       0.32684E-02  -0.31864E-02   0.31834E-02
           15     P15       0.13248       0.71778E-02  -0.68801E-02   0.69109E-02
           16     P16       0.76821       0.64220E-01  -0.46653E-01   0.46072E-01
         */
        float P1 = 0.27503f;
        float P2 = 0.60816f;
        float P3 = 0.45018f;
        ;

        float P4 = 0.49981f;
        float P5 = 1.5272f;
        float P6 = 0.92390f;
        float P7 = 0.39523f;
        float P8 = 0.70864f;
        float P9 = 0.22821f;
        float P10 = 0.92868f;
        float P11 = 0.39647f;
        float P12 = 0.56108f;
        float P13 = 0.61458f;
        float P14 = 0.11098f;
        float P15 = 0.13248f;
        float P16 = 0.76821f;

        //Spieler durchlaufen und MF-Leute Filtern
        for (int i = ISpielerPosition.keeper; i < ISpielerPosition.beginnReservere; i++) {
            ISpieler player = m_clModel.getLineUP().getPlayerByPositionID(i);
            byte tactic = m_clModel.getLineUP().getTactic4PositionID(i);

            if (player != null) {
                //TW                
                //tw
                if (i == ISpielerPosition.keeper) {
                    tw += getEffectiveSkillvalue(player, ISpieler.SKILL_TORWART);
                }

                //IV                
                //iv
                if ((i == ISpielerPosition.insideBack2) && (tactic == ISpielerPosition.NORMAL)) {
                    iv += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //ivOff
                if ((i == ISpielerPosition.insideBack2) && (tactic == ISpielerPosition.OFFENSIV)) {
                    ivOff += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //ivnA
                if ((i == ISpielerPosition.insideBack2) && (tactic == ISpielerPosition.NACH_AUSSEN)) {
                    ivNa += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //Zus iv (now also check side!!!)
                if ((tactic == ISpielerPosition.ZUS_INNENV)
                    && ((i == ISpielerPosition.leftWinger) || (i == ISpielerPosition.insideMid2)
                    || (i == ISpielerPosition.forward2))) {
                    zIv += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //AV                
                //av
                if ((i == ISpielerPosition.leftBack) && (tactic == ISpielerPosition.NORMAL)) {
                    av += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //avOff
                if ((i == ISpielerPosition.leftBack) && (tactic == ISpielerPosition.OFFENSIV)) {
                    avOff += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //avZm
                if ((i == ISpielerPosition.leftBack) && (tactic == ISpielerPosition.ZUR_MITTE)) {
                    avZm += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //av Def
                if ((i == ISpielerPosition.leftBack) && (tactic == ISpielerPosition.DEFENSIV)) {
                    avDef += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //FL                
                //fl
                if ((i == ISpielerPosition.leftWinger) && (tactic == ISpielerPosition.NORMAL)) {
                    fl += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //flOff
                if ((i == ISpielerPosition.leftWinger) && (tactic == ISpielerPosition.OFFENSIV)) {
                    flOff += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //flZm
                if ((i == ISpielerPosition.leftWinger) && (tactic == ISpielerPosition.ZUR_MITTE)) {
                    flZm += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //fl def
                if ((i == ISpielerPosition.leftWinger) && (tactic == ISpielerPosition.DEFENSIV)) {
                    flDef += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //MF                
                //Inner MF + IMnA
                if ((i == ISpielerPosition.insideMid2)
                    && ((tactic == ISpielerPosition.NORMAL)
                    || (tactic == ISpielerPosition.NACH_AUSSEN))) {
                    im += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //Zus MF (only if correct side!!!)
                if ((tactic == ISpielerPosition.ZUS_MITTELFELD)
                    && ((i == ISpielerPosition.leftBack) || (i == ISpielerPosition.insideBack2)
                    || (i == ISpielerPosition.forward2))) {
                    zIm += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //Im OFF
                if ((i == ISpielerPosition.insideMid2) && (tactic == ISpielerPosition.OFFENSIV)) {
                    imOff += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //IM-Def
                if ((i == ISpielerPosition.insideMid2) && (tactic == ISpielerPosition.DEFENSIV)) {
                    imDef += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }
            }
        }

        //TW+IV
        //rating  =   P2 *  tw + iv  + P3 * zIv + P4 * ivOff + P5 * ivNa;
        rating = tw + (P2 * (iv + (P3 * zIv) + (P4 * ivOff) + (P5 * ivNa)));

        //AV
        rating += (P6 * (av + ((1 + P7) * avDef) + ((1 - P7) * avOff) + (P8 * avZm)));

        //IM
        rating += (P9 * (im + ((1 + P10) * imDef) + ((1 - P10) * imOff) + (P3 * zIm)));

        //FL
        rating += (P11 * (fl + ((1 + P12) * flDef) + ((1 - P12) * flOff) + (P13 * flZm)));

        //Trainer def/off ==> increase/decrease rating
        if (m_sTrainerTyp == 1) {
            rating *= (P1 * (1 - P14));
        } else if (m_sTrainerTyp == 0) {
            rating *= (P1 * (1 + P14));
        } else {
            rating *= P1;
        }

        // AoW? ==> reduce rating...
        if (m_sTacticTyp == plugins.IMatchDetails.TAKTIK_MIDDLE) {
            rating *= (1 - P15);
        }

        rating += P16;

        return rating;
    }

    /*
     *
         MFrating = P1 * (IM + P2 * zIM + P3 * FL) + P4 * HA + P5 * PM + P6*(ST-5) + P7
    
         IM,zIM,FL sind die Kondi/Form/Erfahrungskorrigierten und DANN aufaddierten
         Spielaufbauwerte auf diesen Positionen.
    
         HA ist Home(=1)/Away(=0), WOBEI HA=0 gesetzt ist wenn DERBY=1 (!!!)
         PM ist PIC/normal/MOTS (-1/0/1)
         ST ist die Stimmung (also ST-5, da dann symmetrich zu 0)
     */
    public float getMFRatings() {
        float im = 0.0f;
        float imNa = 0.0f;
        float zIM = 0.0f;
        float fl = 0.0f;
        float flZM = 0.0f;
        float ivOff = 0.0f;
        float avOff = 0.0f;
        float stDef = 0.0f;
        float rating = 0.0f;

        /*
           Convergence when estimated distance to minimum (EDM) .LT.  0.10E+01
           FCN=   285.7141     FROM MINOS     STATUS=SUCCESSFUL  4917 CALLS     5276 TOTAL
                               EDM=  0.35E-01    STRATEGY= 1      ERROR MATRIX ACCURATE
            EXT PARAMETER                  PARABOLIC         MINOS ERRORS
            NO.   NAME        VALUE          ERROR      NEGATIVE      POSITIVE
             1     P1        0.14014       0.79085E-02  -0.35198E-02   0.39816E-02
             2     P2        0.80664       0.68793E-01  -0.42356E-01   0.40441E-01
             3     P3        0.46969       0.64360E-01  -0.41573E-01   0.40305E-01
             4     P4        0.16138       0.11052E-01  -0.10044E-01   0.10088E-01
             5     P5        0.13768       0.72481E-02  -0.72427E-02   0.72310E-02
             6     P6        0.61785E-01   0.39104E-02  -0.30704E-02   0.29710E-02
             7     P7        0.71997       0.62989E-01  -0.51732E-01   0.47573E-01
             8     P8        0.84360       0.95954E-01  -0.61052E-01   0.58930E-01
             9     P9        0.62656       0.71905E-01  -0.54870E-01   0.51446E-01
            10     P10       0.31222       0.78811E-01  -0.75858E-01   0.78806E-01
            11     P11       0.22150       0.85484E-01  -0.80850E-01   0.86182E-01
           CHISQUARE = 0.9128E+00  NPFIT =   324
         **/
        float P1 = 0.14078f;
        float P2 = 0.81098f;
        float P3 = 0.46184f;
        float P4 = 0.16080f;
        float P5 = 0.14002f;
        float P6 = 0.06238f;
        float P7 = 0.71225f;
        float P8 = 0.83222f;
        float P9 = 0.58996f;
        float P10 = 0.28758f;
        float P11 = 0.23177f;
        float P12 = 0.44779f;

        //Spieler durchlaufen und MF-Leute Filtern
        for (int i = ISpielerPosition.keeper; i < ISpielerPosition.beginnReservere; i++) {
            ISpieler player = m_clModel.getLineUP().getPlayerByPositionID(i);
            byte tactic = m_clModel.getLineUP().getTactic4PositionID(i);

            if (player != null) {
                //Inner MF
                if (((i == ISpielerPosition.insideMid1) || (i == ISpielerPosition.insideMid2))
                    && (tactic < ISpielerPosition.NACH_AUSSEN)) {
                    im += getEffectiveSkillvalue(player, ISpieler.SKILL_SPIELAUFBAU);
                }

                //ImNa
                if (((i == ISpielerPosition.insideMid1) || (i == ISpielerPosition.insideMid2))
                    && (tactic == ISpielerPosition.NACH_AUSSEN)) {
                    imNa += getEffectiveSkillvalue(player, ISpieler.SKILL_SPIELAUFBAU);
                }

                //Zus MF
                if (tactic == ISpielerPosition.ZUS_MITTELFELD) {
                    zIM += getEffectiveSkillvalue(player, ISpieler.SKILL_SPIELAUFBAU);
                }

                //FL
                if (((i == ISpielerPosition.leftWinger) || (i == ISpielerPosition.rightWinger))
                    && (tactic < ISpielerPosition.ZUR_MITTE)) {
                    fl += getEffectiveSkillvalue(player, ISpieler.SKILL_SPIELAUFBAU);
                }

                //Fl Zur Mitt
                if (((i == ISpielerPosition.leftWinger) || (i == ISpielerPosition.rightWinger))
                    && (tactic == ISpielerPosition.ZUR_MITTE)) {
                    flZM += getEffectiveSkillvalue(player, ISpieler.SKILL_SPIELAUFBAU);
                }

                //IvOFF
                if (((i == ISpielerPosition.insideBack1) || (i == ISpielerPosition.insideBack2))
                    && (tactic == ISpielerPosition.OFFENSIV)) {
                    ivOff += getEffectiveSkillvalue(player, ISpieler.SKILL_SPIELAUFBAU);
                }

                //AV-Off
                if (((i == ISpielerPosition.leftBack) || (i == ISpielerPosition.rightBack))
                    && (tactic == ISpielerPosition.OFFENSIV)) {
                    avOff += getEffectiveSkillvalue(player, ISpieler.SKILL_SPIELAUFBAU);
                }

                //ST_DEV
                if (((i == ISpielerPosition.forward1) || (i == ISpielerPosition.forward2))
                    && (tactic == ISpielerPosition.DEFENSIV)) {
                    stDef += getEffectiveSkillvalue(player, ISpieler.SKILL_SPIELAUFBAU);
                }
            }
        }

        rating = P1 * (im + (P2 * zIM) + (P3 * fl) + (P8 * flZM) + (P9 * ivOff) + (P10 * stDef)
                 + (P11 * avOff) + (P12 * imNa)) * (1 + (P4 * m_sHome)) * (1 + (P5 * m_sPM)) * (1
                 + (P6 * (m_sStimmung - 5)));

        if (m_sTacticTyp == plugins.IMatchDetails.TAKTIK_KONTER) {
            rating *= 0.93f;
        }

        rating += P7;

        return rating;
    }

    /**
     * return pluginName
     *
     * @return TODO Missing Return Method Documentation
     */
    public String getName() {
        return "RatingPrediction";
    }

    /////////////////////////////////////////////////////////////////
    //Right Attack
    /////////////////////////////////////////////////////////////////
    public float getRightAttackRatings() {
        float st = 0.0f;
        float stDef = 0.0f;
        float zSt = 0.0f;

        /*
           float   stPa=0.0f;
           float   stDefPa=0.0f;
           float   zStPa=0.0f;
         */

        //fl's
        float fl = 0.0f;
        float flOff = 0.0f;
        float flDef = 0.0f;
        float flZm = 0.0f;
        float flPa = 0.0f;
        float flOffPa = 0.0f;
        float flDefPa = 0.0f;
        float flZmPa = 0.0f;

        //im's
        float im = 0.0f;
        float imOff = 0.0f;
        float imDef = 0.0f;
        float zIm = 0.0f;
        float imNa = 0.0f;
        float imNaF = 0.0f;

        //av's
        float av = 0.0f;
        float avOff = 0.0f;
        float avDef = 0.0f;
        float avZm = 0.0f;

        //rating
        float rating = 0.0f;

        //was ist mit im aussen , wzm ?

        /*
           FCN=   595.7597     FROM MINOS     STATUS=PROBLEMS   20142 CALLS    21499 TOTAL
                               EDM=  0.11E-02    STRATEGY= 1      ERROR MATRIX ACCURATE
            EXT PARAMETER                  PARABOLIC         MINOS ERRORS
            NO.   NAME        VALUE          ERROR      NEGATIVE      POSITIVE
             1     P1        0.22418       0.48947E-02  -0.67772E-02   0.70835E-02
             2     P2        0.46005       0.28396E-01  -0.33801E-01   0.33749E-01
             3     P3        0.46161       0.27417E-01  -0.26794E-01   0.26906E-01
             4     P4        0.62703       0.12924E-01                 0.23651E-01
             5     P5        0.92875       0.30216E-01  -0.34427E-01   0.35313E-01
             6     P6        0.38172       0.35869E-01  -0.36938E-01   0.36610E-01
             7     P7        0.28117       0.28993E-01  -0.30937E-01   0.31854E-01
             8     P8        0.26633       0.30661E-01  -0.27690E-01   0.27144E-01
             9     P9       -0.11191       0.65787E-01  -0.65306E-01   0.64667E-01
            10     P10       0.42569       0.11226      -0.91563E-01   0.10047
            11     P11        1.1314        1.9182      -0.81705       at limit
            12     P12        1.7388        2.0330      -0.85167       at limit
            13     P13       0.26043       0.20973E-01  -0.21129E-01   0.21675E-01
            14     P14        1.1935       0.20030      -0.18479       0.20793
            15     P15       0.12548       0.40520      -0.41819       0.42005
            16     P16       0.91054E-01   0.35957E-02  -0.35950E-02   0.35990E-02
            17     P17       0.77779E-02   0.19504E-02  -0.19581E-02   0.19692E-02
            18     P18       0.63945       0.34796E-01  -0.38746E-01   0.38232E-01
           CHISQUARE = 0.7717E+00  NPFIT =   790
         **/
        float P1 = 0.22418f;
        float P2 = 0.46005f;
        float P3 = 0.46161f;
        ;

        float P4 = 0.62703f;
        float P5 = 0.92875f;
        float P6 = 0.38172f;
        float P7 = 0.28117f;
        float P8 = 0.26633f;
        float P9 = -0.11191f;
        float P10 = 0.42569f;
        float P11 = 1.1314f;
        float P12 = 1.7388f;
        float P13 = 0.26043f;
        float P14 = 1.1935f;
        float P15 = 0.12548f;
        float P16 = 0.091054f;
        float P17 = 0.0077779f;
        float P18 = 0.63945f;
        int trainer = 0; //balanced        

        //Trainertyp umrechnen
        if (m_sTrainerTyp == 1) //off
         {
            trainer = 1;
        } else if (m_sTrainerTyp == 0) //def
         {
            trainer = -1;
        }

        //Spieler durchlaufen und MF-Leute Filtern
        for (int i = ISpielerPosition.keeper; i < ISpielerPosition.beginnReservere; i++) {
            ISpieler player = m_clModel.getLineUP().getPlayerByPositionID(i);
            byte tactic = m_clModel.getLineUP().getTactic4PositionID(i);

            if (player != null) {
                //st
                if (((i == ISpielerPosition.forward1) || (i == ISpielerPosition.forward2))
                    && (tactic == ISpielerPosition.NORMAL)) {
                    st += getEffectiveSkillvalue(player, ISpieler.SKILL_TORSCHUSS);

                    //stPa     += getEffectiveSkillvalue ( player, ISpieler.SKILL_PASSSPIEL  );
                }

                //ST_DEV
                if (((i == ISpielerPosition.forward1) || (i == ISpielerPosition.forward2))
                    && (tactic == ISpielerPosition.DEFENSIV)) {
                    stDef += getEffectiveSkillvalue(player, ISpieler.SKILL_TORSCHUSS);

                    //stDefPa      += getEffectiveSkillvalue ( player, ISpieler.SKILL_PASSSPIEL  );
                }

                //zus ST
                if (tactic == ISpielerPosition.ZUS_STUERMER) {
                    zSt += getEffectiveSkillvalue(player, ISpieler.SKILL_TORSCHUSS);

                    //zStPa      += getEffectiveSkillvalue ( player, ISpieler.SKILL_PASSSPIEL  );
                }

                //Inner MF + 
                if ((i == ISpielerPosition.insideMid1) && (tactic == ISpielerPosition.NORMAL)) {
                    im += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                } //IMnA

                if ((i == ISpielerPosition.insideMid1) && (tactic == ISpielerPosition.NACH_AUSSEN)) {
                    imNa += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                    imNaF += getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
                }

                //Inner MF off
                if ((i == ISpielerPosition.insideMid1) && (tactic == ISpielerPosition.OFFENSIV)) {
                    imOff += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                }

                //Inner MF def
                if ((i == ISpielerPosition.insideMid1) && (tactic == ISpielerPosition.DEFENSIV)) {
                    imDef += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                }

                //Zus MF
                if ((tactic == ISpielerPosition.ZUS_MITTELFELD)
                    && ((i == ISpielerPosition.rightBack) || (i == ISpielerPosition.insideBack1)
                    || (i == ISpielerPosition.forward1))) {
                    zIm += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                }

                //FL
                if ((i == ISpielerPosition.rightWinger) && (tactic == ISpielerPosition.NORMAL)) {
                    fl += getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
                    flPa += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                }

                //FL
                if ((i == ISpielerPosition.rightWinger) && (tactic == ISpielerPosition.DEFENSIV)) {
                    flDef += getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
                    flDefPa += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                }

                //FL
                if ((i == ISpielerPosition.rightWinger) && (tactic == ISpielerPosition.OFFENSIV)) {
                    flOff += getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
                    flOffPa += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                }

                //Fl Zur Mitt
                if ((i == ISpielerPosition.rightWinger) && (tactic == ISpielerPosition.ZUR_MITTE)) {
                    flZm += getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
                    flZmPa += getEffectiveSkillvalue(player, ISpieler.SKILL_PASSSPIEL);
                }

                //av
                if ((i == ISpielerPosition.rightBack) && (tactic == ISpielerPosition.NORMAL)) {
                    av += getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
                }

                //avOff
                if ((i == ISpielerPosition.rightBack) && (tactic == ISpielerPosition.OFFENSIV)) {
                    avOff += getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
                }

                //avZm
                if ((i == ISpielerPosition.rightBack) && (tactic == ISpielerPosition.ZUR_MITTE)) {
                    avZm += getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
                }

                //av Def
                if ((i == ISpielerPosition.rightBack) && (tactic == ISpielerPosition.DEFENSIV)) {
                    avDef += getEffectiveSkillvalue(player, ISpieler.SKILL_FLUEGEL);
                }
            }
        }

        /*
        
         * FL winger
                      sidatt=(FL+(1+p(2))*FLoff+(1-p(2))*FLdef+p(3)*FLzm)
         * ST torschuss (BOTH scorers)
                      sidatt=sidatt+p(4)*(ST+p(5)*zST+p(6)*STdef)
         * FL passing (passing of all wingers counting the same, in opp. to winger skill)
                      sidatt=sidatt+p(7)*(FLps+FLoffps+FLdefps+FLzmps)
         * MF passing (& IMnA winger)
                      sidatt=sidatt+p(8)*(IM+(1+p(9))*IMoff+(1-p(9))*IMdef+p(10)*zIM+p(11)*IMna+p(12)*IMnafl)
         * AV winger
                      sidatt=sidatt+p(13)*(AV+(1+p(14))*AVoff+max(0,(1-p(14)))*AVdef+p(15)*AVzm)
         * trainer (selbstvertrauen sehr kleinen einfluss)
                      sidatt=p(1)*sidatt*(1+p(16)*Tr)*(1+p(17)*s)+p(18)
         */
        //flWinger
        rating = (fl + ((1 + P2) * flOff) + ((1 - P2) * flDef) + (P3 * flZm));

        //Torschuss ST
        rating = rating + (P4 * (st + (P5 * zSt) + (P6 * stDef)));

        // FL passing (passing of all wingers counting the same, in opp. to winger skill)
        rating = rating + (P7 * (flPa + flOffPa + flDefPa + flZmPa));

        //MF Passing
        rating += (P8 * (im + ((1 + P9) * imOff) + ((1 - P9) * imDef) + (P10 * zIm) + (P11 * imNa)
        + (P12 * imNaF)));

        //av Winger
        rating += (P13 * (av + ((1 + P14) * avOff) + Math.max(0, (1 - P14) * avDef) + (P15 * avZm)));

        //transient + sv
        rating = (P1 * rating * (1 + (P16 * trainer)) * (1 + (P17 * (m_sSelbstvertrauen - 5))))
                 + P18;

        return rating;
    }

    /////////////////////////////////////////////////////////////////
    //right Defense
    /////////////////////////////////////////////////////////////////
    public float getRightDefenseRatings() {
        float iv = 0.0f;
        float ivOff = 0.0f;
        float ivDef = 0.0f;
        float ivNa = 0.0f;
        float tw = 0.0f;
        float av = 0.0f;
        float avDef = 0.0f;
        float avOff = 0.0f;
        float avZm = 0.0f;
        float im = 0.0f;
        float zIm = 0.0f;
        float imOff = 0.0f;
        float imDef = 0.0f;
        float flZm = 0.0f;
        float fl = 0.0f;
        float flOff = 0.0f;
        float flDef = 0.0f;
        float zIv = 0.0f;
        float rating = 0.0f;

        /*  FCN=   833.8134     FROM MINOS     STATUS=SUCCESSFUL 10187 CALLS    11071 TOTAL
           EDM=  0.11E-02    STRATEGY= 1      ERROR MATRIX ACCURATE
           EXT PARAMETER                  PARABOLIC         MINOS ERRORS
           NO.   NAME        VALUE          ERROR      NEGATIVE      POSITIVE
            1     P1        0.27503       0.88160E-02  -0.86216E-02   0.88400E-02
            2     P2        0.60816       0.31009E-01  -0.25434E-01   0.26279E-01
            3     P3        0.45018       0.33133E-01  -0.33260E-01   0.34052E-01
            4     P4        0.49981       0.42695E-01  -0.35092E-01   0.34890E-01
            5     P5         1.5272       0.92394E-01  -0.68772E-01   0.69605E-01
            6     P6        0.92390       0.62647E-01  -0.41465E-01   0.42709E-01
            7     P7        0.39523       0.65569E-01  -0.23189E-01   0.24306E-01
            8     P8        0.70864       0.96393E-01  -0.95985E-01   0.96259E-01
            9     P9        0.22821       0.18147E-01  -0.23549E-01   0.24071E-01
           10     P10       0.92868       0.10982      -0.11652       0.13575
           11     P11       0.39647       0.33299E-01  -0.35847E-01   0.36810E-01
           12     P12       0.56108       0.52660E-01  -0.53719E-01   0.53528E-01
           13     P13       0.61458       0.92649E-01  -0.87215E-01   0.92919E-01
           14     P14       0.11098       0.32684E-02  -0.31864E-02   0.31834E-02
           15     P15       0.13248       0.71778E-02  -0.68801E-02   0.69109E-02
           16     P16       0.76821       0.64220E-01  -0.46653E-01   0.46072E-01
         */
        float P1 = 0.27503f;
        float P2 = 0.60816f;
        float P3 = 0.45018f;
        ;

        float P4 = 0.49981f;
        float P5 = 1.5272f;
        float P6 = 0.92390f;
        float P7 = 0.39523f;
        float P8 = 0.70864f;
        float P9 = 0.22821f;
        float P10 = 0.92868f;
        float P11 = 0.39647f;
        float P12 = 0.56108f;
        float P13 = 0.61458f;
        float P14 = 0.11098f;
        float P15 = 0.13248f;
        float P16 = 0.76821f;

        //Spieler durchlaufen und MF-Leute Filtern
        for (int i = ISpielerPosition.keeper; i < ISpielerPosition.beginnReservere; i++) {
            ISpieler player = m_clModel.getLineUP().getPlayerByPositionID(i);
            byte tactic = m_clModel.getLineUP().getTactic4PositionID(i);

            if (player != null) {
                //TW                
                //tw
                if (i == ISpielerPosition.keeper) {
                    tw += getEffectiveSkillvalue(player, ISpieler.SKILL_TORWART);
                }

                //IV                
                //iv
                if ((i == ISpielerPosition.insideBack1) && (tactic == ISpielerPosition.NORMAL)) {
                    iv += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //ivOff
                if ((i == ISpielerPosition.insideBack1) && (tactic == ISpielerPosition.OFFENSIV)) {
                    ivOff += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //ivnA
                if ((i == ISpielerPosition.insideBack1) && (tactic == ISpielerPosition.NACH_AUSSEN)) {
                    ivNa += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //Zus iv (now also check right side!)
                if ((tactic == ISpielerPosition.ZUS_INNENV)
                    && ((i == ISpielerPosition.rightWinger) || (i == ISpielerPosition.insideMid1)
                    || (i == ISpielerPosition.forward1))) {
                    zIv += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //AV                
                //av
                if ((i == ISpielerPosition.rightBack) && (tactic == ISpielerPosition.NORMAL)) {
                    av += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //avOff
                if ((i == ISpielerPosition.rightBack) && (tactic == ISpielerPosition.OFFENSIV)) {
                    avOff += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //avZm
                if ((i == ISpielerPosition.rightBack) && (tactic == ISpielerPosition.ZUR_MITTE)) {
                    avZm += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //av Def
                if ((i == ISpielerPosition.rightBack) && (tactic == ISpielerPosition.DEFENSIV)) {
                    avDef += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //FL                
                //fl
                if ((i == ISpielerPosition.rightWinger) && (tactic == ISpielerPosition.NORMAL)) {
                    fl += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //flOff
                if ((i == ISpielerPosition.rightWinger) && (tactic == ISpielerPosition.OFFENSIV)) {
                    flOff += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //flZm
                if ((i == ISpielerPosition.rightWinger) && (tactic == ISpielerPosition.ZUR_MITTE)) {
                    flZm += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //fl def
                if ((i == ISpielerPosition.rightWinger) && (tactic == ISpielerPosition.DEFENSIV)) {
                    flDef += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //MF                
                //Inner MF + IMnA
                if ((i == ISpielerPosition.insideMid1)
                    && ((tactic == ISpielerPosition.NORMAL)
                    || (tactic == ISpielerPosition.NACH_AUSSEN))) {
                    im += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //Zus MF (only count if right side!)
                if ((tactic == ISpielerPosition.ZUS_MITTELFELD)
                    && ((i == ISpielerPosition.rightBack) || (i == ISpielerPosition.insideBack1)
                    || (i == ISpielerPosition.forward1))) {
                    zIm += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //Im OFF
                if ((i == ISpielerPosition.insideMid1) && (tactic == ISpielerPosition.OFFENSIV)) {
                    imOff += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }

                //IM-Def
                if ((i == ISpielerPosition.insideMid1) && (tactic == ISpielerPosition.DEFENSIV)) {
                    imDef += getEffectiveSkillvalue(player, ISpieler.SKILL_VERTEIDIGUNG);
                }
            }
        }

        //TW+IV
        //rating  =   P2 *  tw + iv  + P3 * zIv + P4 * ivOff + P5 * ivNa;
        rating = tw + (P2 * (iv + (P3 * zIv) + (P4 * ivOff) + (P5 * ivNa)));

        //AV
        rating += (P6 * (av + ((1 + P7) * avDef) + ((1 - P7) * avOff) + (P8 * avZm)));

        //IM
        rating += (P9 * (im + ((1 + P10) * imDef) + ((1 - P10) * imOff) + (P3 * zIm)));

        //FL
        rating += (P11 * (fl + ((1 + P12) * flDef) + ((1 - P12) * flOff) + (P13 * flZm)));

        //Trainer def/off ==> increase/decrease rating
        if (m_sTrainerTyp == 1) {
            rating *= (P1 * (1 - P14));
        } else if (m_sTrainerTyp == 0) {
            rating *= (P1 * (1 + P14));
        } else {
            rating *= P1;
        }

        // AoW? ==> reduce rating...
        if (m_sTacticTyp == plugins.IMatchDetails.TAKTIK_MIDDLE) {
            rating *= (1 - P15);
        }

        rating += P16;

        return rating;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     */
    public void actionPerformed(ActionEvent e) {
        try {
            if (IPlugin.VERSION < 1.14d) {
                return;
            }

            predictRatings();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Is called by HO! to start the plugin
     *
     * @param hOMiniModel TODO Missing Constructuor Parameter Documentation
     */
    public void start(plugins.IHOMiniModel hOMiniModel) {
        //Save model in member Var
        m_clModel = hOMiniModel;

        javax.swing.JMenu ratingDumpMenu = new javax.swing.JMenu("RatingPrediction");
        javax.swing.JMenuItem ratingDumpMenuItem = new javax.swing.JMenuItem("Show Ratings");
        ratingDumpMenuItem.addActionListener(this);
        ratingDumpMenu.add(ratingDumpMenuItem);

        m_clModel.getGUI().addMenu(ratingDumpMenu);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param player TODO Missing Method Parameter Documentation
     * @param skill TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    protected float getEffectiveSkillvalue(ISpieler player, int skill) {
        //gucken ob Skillup da war
        Object[] skillup = null;
        float subskill = 0.5f;
        float effective = 0.0f;
        float skillvalue = 0.0f;

        try {
            skillup = player.getLastLevelUp(skill);

            if ((skillup != null)
                && ((Timestamp) skillup[0] != null)
                && (((Boolean) skillup[1]).booleanValue())) {
                subskill = player.getSubskill4SkillWithOffset(skill);
            } else //nachberechnung
             {
                subskill = (1 + player.getSubskill4SkillWithOffset(skill)) / 2.0f;
            }

            switch (skill) {
                case ISpieler.SKILL_TORWART:
                    skillvalue = player.getTorwart();
                    break;

                case ISpieler.SKILL_SPIELAUFBAU:
                    skillvalue = player.getSpielaufbau();
                    break;

                case ISpieler.SKILL_VERTEIDIGUNG:
                    skillvalue = player.getVerteidigung();
                    break;

                case ISpieler.SKILL_PASSSPIEL:
                    skillvalue = player.getPasspiel();
                    break;

                case ISpieler.SKILL_FLUEGEL:
                    skillvalue = player.getFluegelspiel();
                    break;

                case ISpieler.SKILL_TORSCHUSS:
                    skillvalue = player.getTorschuss();
                    break;

                case ISpieler.SKILL_STANDARDS:
                    skillvalue = player.getStandards();
                    break;

                default:
                    skillvalue = 0f;
                    break;
            }

            //korrektur
            skillvalue = skillvalue - 1 + subskill;

            //mit Kondi
            if (skill == ISpieler.SKILL_SPIELAUFBAU) {
                effective = getEffectiveSkillUsingKondi(player.getKondition(),
                                                        player.getErfahrung(), skillvalue,
                                                        player.getForm());
            } else //ohne Kondi
             {
                effective = getEffectiveWithoutKondi(player.getErfahrung(), skillvalue,
                                                     player.getForm());
            }
        } catch (Exception e) {
            System.out.println("catcht");
            e.printStackTrace();
        }

        return effective;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // MITTELFELD    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////    
    private float getEffectiveSkillUsingKondi(int kondi, int erf, float sa, int form) {
        float saEff = 0.0f;
        float kf = 0.0f;
        float ff = 0.0f;
        float eb = 0.0f;

        kf = (float) Math.pow(((float) kondi - 1.0f) / 7.0f, (m_fKF * 7.0f) / 8.0f);
        eb = (float) (2.0f * m_fErfahrung * (Math.log((float) erf) / Math.log(10.0f)));
        ff = (float) Math.pow(((float) form - 1.0f) / 7.0f, (m_fForm * 7.0f) / 8.0f);
        saEff = (ff * kf * sa) + eb;

        return saEff;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ANGRIFFsZENTRUM
    ////////////////////////////////////////////////////////////////////////////////////////////////////////    
    private float getEffectiveWithoutKondi(int erf, float skill, int form) {
        float skillEff = 0.0f;
        float ff = 0.0f;
        float eb = 0.0f;

        eb = (float) (2.0f * m_fErfahrung * (Math.log((float) erf) / Math.log(10.0f)));
        ff = (float) Math.pow(((float) form - 1.0f) / 7.0f, (m_fForm * 7.0f) / 8.0f);
        skillEff = (ff * skill) + eb;

        return skillEff;
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initBasics() {
        try {
            m_sPM = (short) m_clModel.getLineUP().getAttitude();
            m_sStimmung = (short) m_clModel.getTeam().getStimmungAsInt();

            //Heim/Ausw abfrage lass ich grad mal sein ;)
            m_sHome = m_clModel.getLineUP().getHeimspiel();

            //Selbstvertrauien
            m_sSelbstvertrauen = (short) m_clModel.getTeam().getSelbstvertrauenAsInt();

            //TrainerTyp
            m_sTrainerTyp = (short) m_clModel.getTrainer().getTrainerTyp();

            //Taktik
            m_sTacticTyp = (short) m_clModel.getLineUP().getTacticType();
        } catch (Exception e) {
            m_sPM = 0; //normal
            m_sStimmung = 4;
            m_sSelbstvertrauen = 4;
            m_sHome = 0; //kein Bonus
            m_sTrainerTyp = 2; //Balanced     
            m_sTacticTyp = plugins.IMatchDetails.TAKTIK_NORMAL;
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    private void predictRatings() {
        String ratings = "Midfield: ";

        initBasics();

        ratings += (getMFRatings() + " \n");

        //Left ABW
        ratings += ("Left Defense: " + getLeftDefenseRatings() + "\n");
        ratings += ("Left Attack: " + getLeftAttackRatings() + " \n");
        ratings += ("Right Defense: " + getRightDefenseRatings() + " \n");
        ratings += ("Right Attack: " + getRightAttackRatings() + " \n");
        ratings += ("Middle Defense: " + getCentralDefenseRatings() + " \n");
        ratings += ("Middle Attack: " + getCentralAttackRatings() + " \n");
        ratings += ("Aow/Aim: " + getAow_AimRatings() + " \n");
        ratings += ("Counter: " + "0" + " \n");
        ratings += ("Pressing: " + "0" + " \n");

        m_clModel.getHelper().showMessage(m_clModel.getGUI().getOwner4Dialog(), ratings, "Ratings",
                                          javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}
