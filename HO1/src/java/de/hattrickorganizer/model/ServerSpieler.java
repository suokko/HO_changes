// %421296861:de.hattrickorganizer.model%
/*
 * ServerSpieler.java
 *
 * Created on 7. Mai 2003, 07:27
 */
package de.hattrickorganizer.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import plugins.ISpielerPosition;
import de.hattrickorganizer.tools.HOLogger;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */

/*
   SchnittStelle für Spieler OBJ
   - ID
   - Stk nur für aktuelle Position
   - Anz Gelb
   - Anz Tore
   - Anz Torchancen
 */
public final class ServerSpieler implements java.io.Serializable {
    //~ Static fields/initializers -----------------------------------------------------------------

    //Posotionscodierungen! für alle Positionen auf dem Spielfeld gilt die Codierung der Spielerposition!!

    /** TODO Missing Parameter Documentation */
    public static final byte BANK_TW = -1;

    /** TODO Missing Parameter Documentation */
    public static final byte BANK_AB = -2;

    /** TODO Missing Parameter Documentation */
    public static final byte BANK_MF = -3;

    /** TODO Missing Parameter Documentation */
    public static final byte BANK_FL = -4;

    /** TODO Missing Parameter Documentation */
    public static final byte BANK_ST = -5;

    /** TODO Missing Parameter Documentation */
    public static final byte AUSGEWECHSELT = -100;

    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    private String m_sName = "";

    /** TODO Missing Parameter Documentation */
    private byte m_bAnzTorChancen;

    /** TODO Missing Parameter Documentation */
    private byte m_bAnzTore;

    /** TODO Missing Parameter Documentation */
    private byte m_bAnzVorlagen;

    /** TODO Missing Parameter Documentation */
    private byte m_bPosition = ISpielerPosition.UNBESTIMMT;

    /** TODO Missing Parameter Documentation */
    private float m_fAV_D;

    /** TODO Missing Parameter Documentation */
    private float m_fAV_I;

    /** TODO Missing Parameter Documentation */
    private float m_fAV_N;

    /** TODO Missing Parameter Documentation */
    private float m_fAV_O;

    /** TODO Missing Parameter Documentation */
    private float m_fFL_D;

    /** TODO Missing Parameter Documentation */
    private float m_fFL_I;

    /** TODO Missing Parameter Documentation */
    private float m_fFL_N;

    /** TODO Missing Parameter Documentation */
    private float m_fFL_O;

    /** TODO Missing Parameter Documentation */
    private float m_fIV_A;

    /** TODO Missing Parameter Documentation */
    private float m_fIV_N;

    /** TODO Missing Parameter Documentation */
    private float m_fIV_O;

    /** TODO Missing Parameter Documentation */
    private float m_fMF_A;

    /** TODO Missing Parameter Documentation */
    private float m_fMF_D;

    /** TODO Missing Parameter Documentation */
    private float m_fMF_N;

    /** TODO Missing Parameter Documentation */
    private float m_fMF_O;

    /** TODO Missing Parameter Documentation */
    private float m_fST;

    /** TODO Missing Parameter Documentation */
    private float m_fST_W;
    
    /** TODO Missing Parameter Documentation */
    private float m_fST_D;

    //Vorberechnete Stärken

    /** TODO Missing Parameter Documentation */
    private float m_fTW;

    //SpielStatistiken

    /** TODO Missing Parameter Documentation */
    private int m_iGelbeKarten;

    /** TODO Missing Parameter Documentation */
    private int m_iID = -1;

    //protected boolean   m_bHatGelb          =   false;

    /** TODO Missing Parameter Documentation */
    private int m_iVerletzt = -1;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of ServerSpieler
     *
     * @param player TODO Missing Constructuor Parameter Documentation
     * @param position TODO Missing Constructuor Parameter Documentation
     */
    public ServerSpieler(Spieler player, byte position) {
        final FactorObject[] allPos = FormulaFactors.instance().getAllObj();
        float stk = -1.0f;

        //Stärken übernhemen
        for (int i = 0; (allPos != null) && (i < allPos.length); i++) {
            stk = player.calcPosValue(allPos[i].getPosition(), true);
            setStk(allPos[i].getPosition(), stk);
        }

        m_iID = player.getSpielerID();
        m_sName = player.getName();
        m_bPosition = position;
    }

    /**
     * Konstruktor lädt den ServerSpieler aus einem InputStream
     *
     * @param dis Der InputStream aus dem gelesen wird
     */
    public ServerSpieler(DataInputStream dis) {
        //DataInputStream         dis         =   null;
        try {
            //Einzulesenden Strom konvertieren
            //bais = new ByteArrayInputStream(data);
            //  dis  = new DataInputStream (bais);            
            //Daten auslesen+
            m_iID = dis.readInt();
            m_iGelbeKarten = dis.readInt();
            m_bAnzTore = dis.readByte();
            m_bAnzTorChancen = dis.readByte();
            m_bAnzVorlagen = dis.readByte();

            //Kann aus Helper erstellt werden beim Laden
            m_sName = dis.readUTF();
            m_iVerletzt = dis.readInt();
            m_bPosition = dis.readByte();
            m_fTW = dis.readFloat();
            m_fIV_N = dis.readFloat();
            m_fIV_O = dis.readFloat();
            m_fIV_A = dis.readFloat();
            m_fAV_N = dis.readFloat();
            m_fAV_O = dis.readFloat();
            m_fAV_D = dis.readFloat();
            m_fAV_I = dis.readFloat();
            m_fMF_O = dis.readFloat();
            m_fMF_N = dis.readFloat();
            m_fMF_D = dis.readFloat();
            m_fMF_A = dis.readFloat();
            m_fFL_O = dis.readFloat();
            m_fFL_D = dis.readFloat();
            m_fFL_N = dis.readFloat();
            m_fFL_I = dis.readFloat();
            m_fST_D = dis.readFloat();
            m_fST_W = dis.readFloat();
            m_fST = dis.readFloat();

            //Und wieder schliessen
            // dis.close ();
        } catch (IOException ioe) {
            HOLogger.instance().log(getClass(),ioe);
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for property m_bAnzTorChancen.
     *
     * @param m_bAnzTorChancen New value of property m_bAnzTorChancen.
     */
    public final void setAnzTorChancen(byte m_bAnzTorChancen) {
        this.m_bAnzTorChancen = m_bAnzTorChancen;
    }

    /**
     * Getter for property m_bAnzTorChancen.
     *
     * @return Value of property m_bAnzTorChancen.
     */
    public final byte getAnzTorChancen() {
        return m_bAnzTorChancen;
    }

    /**
     * Setter for property m_bAnzTore.
     *
     * @param m_bAnzTore New value of property m_bAnzTore.
     */
    public final void setAnzTore(byte m_bAnzTore) {
        this.m_bAnzTore = m_bAnzTore;
    }

    /**
     * Getter for property m_bAnzTore.
     *
     * @return Value of property m_bAnzTore.
     */
    public final byte getAnzTore() {
        return m_bAnzTore;
    }

    /**
     * Setter for property m_bAnzVorlagen.
     *
     * @param m_bAnzVorlagen New value of property m_bAnzVorlagen.
     */
    public final void setAnzVorlagen(byte m_bAnzVorlagen) {
        this.m_bAnzVorlagen = m_bAnzVorlagen;
    }

    /**
     * Getter for property m_bAnzVorlagen.
     *
     * @return Value of property m_bAnzVorlagen.
     */
    public final byte getAnzVorlagen() {
        return m_bAnzVorlagen;
    }

    /**
     * Getter for property m_bHatGelb.
     *
     * @return Value of property m_bHatGelb.
     */
    public final boolean isGelbVerwarnt() {
        return m_iGelbeKarten == 1;
    }

    /**
     * Setter for property m_iGelbeKarten.
     *
     * @param m_iGelbeKarten New value of property m_iGelbeKarten.
     */
    public final void setGelbeKarten(int m_iGelbeKarten) {
        this.m_iGelbeKarten = m_iGelbeKarten;
    }

    /**
     * Getter for property m_bAnzahlGelb.
     *
     * @return Value of property m_bAnzahlGelb.
     */
    public final int getGelbeKarten() {
        return m_iGelbeKarten;
    }

    /**
     * gibt an ob der spieler gesperrt ist
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isGesperrt() {
        return (m_iGelbeKarten > 1);
    }

    /**
     * Setter for property m_iID.
     *
     * @param m_iID New value of property m_iID.
     */
    public final void setID(int m_iID) {
        this.m_iID = m_iID;
    }

    /**
     * Getter for property m_iID.
     *
     * @return Value of property m_iID.
     */
    public final int getID() {
        return m_iID;
    }

    /**
     * Setter for property m_sName.
     *
     * @param m_sName New value of property m_sName.
     */
    public final void setName(java.lang.String m_sName) {
        this.m_sName = m_sName;
    }

    /**
     * Getter for property m_sName.
     *
     * @return Value of property m_sName.
     */
    public final java.lang.String getName() {
        return m_sName;
    }

    /**
     * Setter for property m_bPosition.
     *
     * @param m_bPosition New value of property m_bPosition.
     */
    public final void setPosition(byte m_bPosition) {
        this.m_bPosition = m_bPosition;
    }

    /**
     * Getter for property m_bPosition.
     *
     * @return Value of property m_bPosition.
     */
    public final byte getPosition() {
        return m_bPosition;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param pos TODO Missing Method Parameter Documentation
     * @param stk TODO Missing Method Parameter Documentation
     */
    public final void setStk(byte pos, float stk) {
        switch (pos) {
            case ISpielerPosition.TORWART:
                m_fTW = stk;
                break;

            case ISpielerPosition.INNENVERTEIDIGER:
                m_fIV_N = stk;
                break;

            case ISpielerPosition.INNENVERTEIDIGER_OFF:
                m_fIV_O = stk;
                break;

            case ISpielerPosition.INNENVERTEIDIGER_AUS:
                m_fIV_A = stk;
                break;

            case ISpielerPosition.AUSSENVERTEIDIGER:
                m_fAV_N = stk;
                break;

            case ISpielerPosition.AUSSENVERTEIDIGER_OFF:
                m_fAV_O = stk;
                break;

            case ISpielerPosition.AUSSENVERTEIDIGER_DEF:
                m_fAV_D = stk;
                break;

            case ISpielerPosition.AUSSENVERTEIDIGER_IN:
                m_fAV_I = stk;
                break;

            case ISpielerPosition.MITTELFELD:
                m_fMF_N = stk;
                break;

            case ISpielerPosition.MITTELFELD_OFF:
                m_fMF_O = stk;
                break;

            case ISpielerPosition.MITTELFELD_DEF:
                m_fMF_D = stk;
                break;

            case ISpielerPosition.MITTELFELD_AUS:
                m_fMF_A = stk;
                break;

            case ISpielerPosition.FLUEGELSPIEL:
                m_fFL_N = stk;
                break;

            case ISpielerPosition.FLUEGELSPIEL_OFF:
                m_fFL_O = stk;
                break;

            case ISpielerPosition.FLUEGELSPIEL_DEF:
                m_fFL_D = stk;
                break;

            case ISpielerPosition.FLUEGELSPIEL_IN:
                m_fFL_I = stk;
                break;

            case ISpielerPosition.STURM:
                m_fST = stk;
                break;

            case ISpielerPosition.STURM_DEF:
                m_fST_D = stk;
                break;
            case ISpielerPosition.STURM_AUS:
                m_fST_D = stk;
                break;
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param pos TODO Missing Method Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final float getStk(byte pos) {
        float ret = 1.0f;

        switch (pos) {
            case ISpielerPosition.TORWART:
                ret = m_fTW;
                break;

            case ISpielerPosition.INNENVERTEIDIGER:
                ret = m_fIV_N;
                break;

            case ISpielerPosition.INNENVERTEIDIGER_OFF:
                ret = m_fIV_O;
                break;

            case ISpielerPosition.INNENVERTEIDIGER_AUS:
                ret = m_fIV_A;
                break;

            case ISpielerPosition.AUSSENVERTEIDIGER:
                ret = m_fAV_N;
                break;

            case ISpielerPosition.AUSSENVERTEIDIGER_OFF:
                ret = m_fAV_O;
                break;

            case ISpielerPosition.AUSSENVERTEIDIGER_DEF:
                ret = m_fAV_D;
                break;

            case ISpielerPosition.AUSSENVERTEIDIGER_IN:
                ret = m_fAV_I;
                break;

            case ISpielerPosition.MITTELFELD:
                ret = m_fMF_N;
                break;

            case ISpielerPosition.MITTELFELD_OFF:
                ret = m_fMF_O;
                break;

            case ISpielerPosition.MITTELFELD_DEF:
                ret = m_fMF_D;
                break;

            case ISpielerPosition.MITTELFELD_AUS:
                ret = m_fMF_A;
                break;

            case ISpielerPosition.FLUEGELSPIEL:
                ret = m_fFL_N;
                break;

            case ISpielerPosition.FLUEGELSPIEL_OFF:
                ret = m_fFL_O;
                break;

            case ISpielerPosition.FLUEGELSPIEL_DEF:
                ret = m_fFL_D;
                break;

            case ISpielerPosition.FLUEGELSPIEL_IN:
                ret = m_fFL_I;
                break;

            case ISpielerPosition.STURM:
                ret = m_fST;
                break;

            case ISpielerPosition.STURM_DEF:
                ret = m_fST_D;
                break;
            case ISpielerPosition.STURM_AUS:
                ret = m_fST_W;
                break;
        }

        return ret;
    }

    /**
     * Setter for property m_iVerletzt.
     *
     * @param m_iVerletzt New value of property m_iVerletzt.
     */
    public final void setVerletzt(int m_iVerletzt) {
        this.m_iVerletzt = m_iVerletzt;
    }

    /**
     * Getter for property m_iVerletzt.
     *
     * @return Value of property m_iVerletzt.
     */
    public final int getVerletzt() {
        return m_iVerletzt;
    }

    /*
       saved den Serverspieler
       @param baos Der Outputstream in den gesaved werden soll
       @return Byte Array der Daten die in den Output geschireben wurden
     */
    public final void save(DataOutputStream das) {
        //ByteArrayOutputStream   baos    =  null;
        //DataOutputStream        das     =   null;
        //Byte Array
        try {
            //Instanzen erzeugen
            //baos = new ByteArrayOutputStream();
            //  das = new DataOutputStream(baos);
            //Daten schreiben in Strom
            das.writeInt(m_iID);
            das.writeInt(m_iGelbeKarten);
            das.writeByte(m_bAnzTore);
            das.writeByte(m_bAnzTorChancen);
            das.writeByte(m_bAnzVorlagen);

            //Kann aus Helper erstellt werden beim Laden            
            das.writeUTF(m_sName);
            das.writeInt(m_iVerletzt);
            das.writeByte(m_bPosition);
            das.writeFloat(m_fTW);
            das.writeFloat(m_fIV_N);
            das.writeFloat(m_fIV_O);
            das.writeFloat(m_fIV_A);
            das.writeFloat(m_fAV_N);
            das.writeFloat(m_fAV_O);
            das.writeFloat(m_fAV_D);
            das.writeFloat(m_fAV_I);
            das.writeFloat(m_fMF_O);
            das.writeFloat(m_fMF_N);
            das.writeFloat(m_fMF_D);
            das.writeFloat(m_fMF_A);
            das.writeFloat(m_fFL_O);
            das.writeFloat(m_fFL_D);
            das.writeFloat(m_fFL_N);
            das.writeFloat(m_fFL_I);
            das.writeFloat(m_fST_D);
            das.writeFloat(m_fST_W);
            das.writeFloat(m_fST);

            //Strom konvertieren in Byte
            //data = das.baos.toByteArray();
            //Hilfsstrom schließen
            //das.close ();
            //    return data;
        } catch (IOException ioe) {
            HOLogger.instance().log(getClass(),ioe);
        }

        //return data;
    }
}
