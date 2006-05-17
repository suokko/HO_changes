// %2460258502:de.hattrickorganizer.model%
/*
 * Spielbericht.java
 *
 * Created on 15. Januar 2002, 19:13
 */

//import javax.microedition.midlet.*;
package de.hattrickorganizer.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.hattrickorganizer.tools.HOLogger;


/**
 * DOCUMENT ME!
 *
 * @author tommy
 * @version
 */
public class Spielbericht implements java.io.Serializable {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    ServerTeam m_clGast;

    //!Memeber

    /** TODO Missing Parameter Documentation */
    ServerTeam m_clHeim;

    /** TODO Missing Parameter Documentation */
    byte m_bAbseitsfalleGast;

    /** TODO Missing Parameter Documentation */
    byte m_bAbseitsfalleHeim;

    /** TODO Missing Parameter Documentation */
    byte m_bAnzEckenGast;

    /** TODO Missing Parameter Documentation */
    byte m_bAnzEckenHeim;

    /** TODO Missing Parameter Documentation */
    byte m_bAnzGelbeKartenGast;

    /** TODO Missing Parameter Documentation */
    byte m_bAnzGelbeKartenHeim;

    /** TODO Missing Parameter Documentation */
    byte m_bAnzRoteKartenHeim;

    /** TODO Missing Parameter Documentation */
    byte m_bAnzWechselGast;

    /** TODO Missing Parameter Documentation */
    byte m_bAnzWechselHeim;

    /** TODO Missing Parameter Documentation */
    byte m_bAnzroteKartenGast;

    /** TODO Missing Parameter Documentation */
    byte m_bTorchancenGast;

    /** TODO Missing Parameter Documentation */
    byte m_bTorchancenHeim;

    /** TODO Missing Parameter Documentation */
    byte m_bToreGast;

    /** TODO Missing Parameter Documentation */
    byte m_bToreHeim;

    /** TODO Missing Parameter Documentation */
    byte m_bZweikampfBilanzGast;

    /** TODO Missing Parameter Documentation */
    byte m_bZweikampfBilanzHeim;

    //~ Constructors -------------------------------------------------------------------------------

    //!Konstruktor
    public Spielbericht() {
        m_bToreHeim = 0;
        m_bToreGast = 0;
        m_bTorchancenHeim = 0;
        m_bTorchancenGast = 0;
        m_clGast = null;
        m_clHeim = null;

        // m_bSpieltyp     =   Helper.LIGASPIEL;
    }

    //!Konstruktor
    public Spielbericht(ServerTeam heim, ServerTeam gast) {
        m_bToreHeim = 0;
        m_bToreGast = 0;
        m_bTorchancenHeim = 0;
        m_bTorchancenGast = 0;
        m_clGast = gast;
        m_clHeim = heim;

        //m_bSpieltyp     =   Helper.LIGASPIEL;
    }

    /**
     * Konstruktor l‰dt den Spielbericht aus einem InputStream
     *
     * @param dis Der InputStream aus dem gelesen wird
     */
    public Spielbericht(DataInputStream dis) {
        //DataInputStream         dis         =   null;
        //byte                    data[]      =   null;        
        try {
            //Einzulesenden Strom konvertieren
            //bais = new ByteArrayInputStream(data);
            //  dis  = new DataInputStream (bais);            
            //Daten auslesen
            m_clHeim = new ServerTeam(dis);
            m_clGast = new ServerTeam(dis);
            m_bToreHeim = dis.readByte();
            m_bToreGast = dis.readByte();
            m_bTorchancenHeim = dis.readByte();
            m_bTorchancenGast = dis.readByte();
            m_bZweikampfBilanzHeim = dis.readByte();
            m_bZweikampfBilanzGast = dis.readByte();
            m_bAnzWechselHeim = dis.readByte();
            m_bAnzWechselGast = dis.readByte();
            m_bAnzGelbeKartenHeim = dis.readByte();
            m_bAnzGelbeKartenGast = dis.readByte();
            m_bAnzRoteKartenHeim = dis.readByte();
            m_bAnzroteKartenGast = dis.readByte();
            m_bAnzEckenHeim = dis.readByte();
            m_bAnzEckenGast = dis.readByte();
            m_bAbseitsfalleHeim = dis.readByte();
            m_bAbseitsfalleGast = dis.readByte();

            //Und wieder schliessen
            //dis.close ();
        } catch (IOException ioe) {
            HOLogger.instance().log(getClass(),ioe);
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Setter for property m_bAbseitsfalleGast.
     *
     * @param m_bAbseitsfalleGast New value of property m_bAbseitsfalleGast.
     */
    public final void setAbseitsfalleGast(byte m_bAbseitsfalleGast) {
        this.m_bAbseitsfalleGast = m_bAbseitsfalleGast;
    }

    /**
     * Getter for property m_bAbseitsfalleGast.
     *
     * @return Value of property m_bAbseitsfalleGast.
     */
    public final byte getAbseitsfalleGast() {
        return m_bAbseitsfalleGast;
    }

    /**
     * Setter for property m_bAbseitsfalleHeim.
     *
     * @param m_bAbseitsfalleHeim New value of property m_bAbseitsfalleHeim.
     */
    public final void setAbseitsfalleHeim(byte m_bAbseitsfalleHeim) {
        this.m_bAbseitsfalleHeim = m_bAbseitsfalleHeim;
    }

    /**
     * Getter for property m_bAbseitsfalleHeim.
     *
     * @return Value of property m_bAbseitsfalleHeim.
     */
    public final byte getAbseitsfalleHeim() {
        return m_bAbseitsfalleHeim;
    }

    /**
     * Setter for property m_bAnzEckenGast.
     *
     * @param m_bAnzEckenGast New value of property m_bAnzEckenGast.
     */
    public final void setAnzEckenGast(byte m_bAnzEckenGast) {
        this.m_bAnzEckenGast = m_bAnzEckenGast;
    }

    /**
     * Getter for property m_bAnzEckenGast.
     *
     * @return Value of property m_bAnzEckenGast.
     */
    public final byte getAnzEckenGast() {
        return m_bAnzEckenGast;
    }

    /**
     * Setter for property m_bAnzEckenHeim.
     *
     * @param m_bAnzEckenHeim New value of property m_bAnzEckenHeim.
     */
    public final void setAnzEckenHeim(byte m_bAnzEckenHeim) {
        this.m_bAnzEckenHeim = m_bAnzEckenHeim;
    }

    /**
     * Getter for property m_bAnzEckenHeim.
     *
     * @return Value of property m_bAnzEckenHeim.
     */
    public final byte getAnzEckenHeim() {
        return m_bAnzEckenHeim;
    }

    /**
     * Setter for property m_bAnzGelbeKartenGast.
     *
     * @param m_bAnzGelbeKartenGast New value of property m_bAnzGelbeKartenGast.
     */
    public final void setAnzGelbeKartenGast(byte m_bAnzGelbeKartenGast) {
        this.m_bAnzGelbeKartenGast = m_bAnzGelbeKartenGast;
    }

    /**
     * Getter for property m_bAnzGelbeKartenGast.
     *
     * @return Value of property m_bAnzGelbeKartenGast.
     */
    public final byte getAnzGelbeKartenGast() {
        return m_bAnzGelbeKartenGast;
    }

    /**
     * Setter for property m_bAnzGelbeKartenHeim.
     *
     * @param m_bAnzGelbeKartenHeim New value of property m_bAnzGelbeKartenHeim.
     */
    public final void setAnzGelbeKartenHeim(byte m_bAnzGelbeKartenHeim) {
        this.m_bAnzGelbeKartenHeim = m_bAnzGelbeKartenHeim;
    }

    /**
     * Getter for property m_bAnzGelbeKartenHeim.
     *
     * @return Value of property m_bAnzGelbeKartenHeim.
     */
    public final byte getAnzGelbeKartenHeim() {
        return m_bAnzGelbeKartenHeim;
    }

    /**
     * Setter for property m_bAnzroteKartenGast.
     *
     * @param m_bAnzroteKartenGast New value of property m_bAnzroteKartenGast.
     */
    public final void setAnzRoteKartenGast(byte m_bAnzroteKartenGast) {
        this.m_bAnzroteKartenGast = m_bAnzroteKartenGast;
    }

    /**
     * Getter for property m_bAnzroteKartenGast.
     *
     * @return Value of property m_bAnzroteKartenGast.
     */
    public final byte getAnzRoteKartenGast() {
        return m_bAnzroteKartenGast;
    }

    /**
     * Setter for property m_bAnzRoteKartenHeim.
     *
     * @param m_bAnzRoteKartenHeim New value of property m_bAnzRoteKartenHeim.
     */
    public final void setAnzRoteKartenHeim(byte m_bAnzRoteKartenHeim) {
        this.m_bAnzRoteKartenHeim = m_bAnzRoteKartenHeim;
    }

    /**
     * Getter for property m_bAnzRoteKartenHeim.
     *
     * @return Value of property m_bAnzRoteKartenHeim.
     */
    public final byte getAnzRoteKartenHeim() {
        return m_bAnzRoteKartenHeim;
    }

    /**
     * Setter for property m_bAnzWechselGast.
     *
     * @param m_bAnzWechselGast New value of property m_bAnzWechselGast.
     */
    public final void setAnzWechselGast(byte m_bAnzWechselGast) {
        this.m_bAnzWechselGast = m_bAnzWechselGast;
    }

    /**
     * Getter for property m_bAnzWechselGast.
     *
     * @return Value of property m_bAnzWechselGast.
     */
    public final byte getAnzWechselGast() {
        return m_bAnzWechselGast;
    }

    /**
     * Setter for property m_bAnzWechselHeim.
     *
     * @param m_bAnzWechselHeim New value of property m_bAnzWechselHeim.
     */
    public final void setAnzWechselHeim(byte m_bAnzWechselHeim) {
        this.m_bAnzWechselHeim = m_bAnzWechselHeim;
    }

    /**
     * Getter for property m_bAnzWechselHeim.
     *
     * @return Value of property m_bAnzWechselHeim.
     */
    public final byte getAnzWechselHeim() {
        return m_bAnzWechselHeim;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getAnzahlKarten() {
        return m_bAnzroteKartenGast + m_bAnzRoteKartenHeim + m_bAnzGelbeKartenGast
               + m_bAnzGelbeKartenHeim;
    }

    /**
     * Setter for property m_bZweikampfBilanzGast.
     *
     * @param m_bZweikampfBilanzGast New value of property m_bZweikampfBilanzGast.
     */
    public final void setZweikampfBilanzGast(byte m_bZweikampfBilanzGast) {
        this.m_bZweikampfBilanzGast = m_bZweikampfBilanzGast;
    }

    /**
     * Getter for property m_bZweikampfBilanzGast.
     *
     * @return Value of property m_bZweikampfBilanzGast.
     */
    public final byte getZweikampfBilanzGast() {
        return m_bZweikampfBilanzGast;
    }

    /**
     * Setter for property m_bZweikampfBilanzHeim.
     *
     * @param m_bZweikampfBilanzHeim New value of property m_bZweikampfBilanzHeim.
     */
    public final void setZweikampfBilanzHeim(byte m_bZweikampfBilanzHeim) {
        this.m_bZweikampfBilanzHeim = m_bZweikampfBilanzHeim;
    }

    /**
     * Getter for property m_bZweikampfBilanzHeim.
     *
     * @return Value of property m_bZweikampfBilanzHeim.
     */
    public final byte getZweikampfBilanzHeim() {
        return m_bZweikampfBilanzHeim;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final ServerTeam Gast() {
        return m_clGast;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param Wert TODO Missing Method Parameter Documentation
     */
    public final void Gast(ServerTeam Wert) {
        m_clGast = Wert;
    }

    //!Accessors
    public final ServerTeam Heim() {
        return m_clHeim;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param Wert TODO Missing Method Parameter Documentation
     */
    public final void Heim(ServerTeam Wert) {
        m_clHeim = Wert;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param Wert TODO Missing Method Parameter Documentation
     */
    public final void TorchancenGast(byte Wert) {
        m_bTorchancenGast = Wert;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final byte TorchancenGast() {
        return m_bTorchancenGast;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param Wert TODO Missing Method Parameter Documentation
     */
    public final void TorchancenHeim(byte Wert) {
        m_bTorchancenHeim = Wert;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final byte TorchancenHeim() {
        return m_bTorchancenHeim;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param Wert TODO Missing Method Parameter Documentation
     */
    public final void ToreGast(byte Wert) {
        m_bToreGast = Wert;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final byte ToreGast() {
        return m_bToreGast;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param Wert TODO Missing Method Parameter Documentation
     */
    public final void ToreHeim(byte Wert) {
        m_bToreHeim = Wert;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final byte ToreHeim() {
        return m_bToreHeim;
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
        //byte[]                  data    =   null;
        try {
            //Instanzen erzeugen
            //baos = new ByteArrayOutputStream();
            //  das = new DataOutputStream(baos);
            //Daten schreiben in Strom
            m_clHeim.save(das);
            m_clGast.save(das);

            das.writeByte(m_bToreHeim);
            das.writeByte(m_bToreGast);
            das.writeByte(m_bTorchancenHeim);
            das.writeByte(m_bTorchancenGast);
            das.writeByte(m_bZweikampfBilanzHeim);
            das.writeByte(m_bZweikampfBilanzGast);
            das.writeByte(m_bAnzWechselHeim);
            das.writeByte(m_bAnzWechselGast);
            das.writeByte(m_bAnzGelbeKartenHeim);
            das.writeByte(m_bAnzGelbeKartenGast);
            das.writeByte(m_bAnzRoteKartenHeim);
            das.writeByte(m_bAnzroteKartenGast);
            das.writeByte(m_bAnzEckenHeim);
            das.writeByte(m_bAnzEckenGast);
            das.writeByte(m_bAbseitsfalleHeim);
            das.writeByte(m_bAbseitsfalleGast);

            //Strom konvertieren in Byte

            /*data = baos.toByteArray();
               //Hilfsstrom schlieﬂen
               das.close ();
            
               return data;*/
        } catch (IOException ioe) {
            HOLogger.instance().log(getClass(),ioe);
        }

        //return data;
    }
}
