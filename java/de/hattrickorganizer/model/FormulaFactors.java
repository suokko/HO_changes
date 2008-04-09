// %1695323697:de.hattrickorganizer.model%
/*
 * FormulaFactors.java
 *
 * Created on 17. März 2003, 15:31
 */
package de.hattrickorganizer.model;

import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import plugins.ISpielerPosition;
import de.hattrickorganizer.tools.HOLogger;
import de.hattrickorganizer.tools.xml.XMLManager;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class FormulaFactors extends Configuration {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** singelton */
    protected static FormulaFactors m_clInstance;

    /** Konstante wieviel PositionsObjekte es gibt */
    protected static final int ANZ_FAKTOROBJEKTE = 19;

    //~ Instance fields ----------------------------------------------------------------------------
    // skill factors
    public float m_fFL_Kondi_Faktor = 0.25f; // wing
    public float m_fPS_Kondi_Faktor =0;		 // passing
    public float m_fSP_Kondi_Faktor = 0.50f; // playmaking
    public float m_fST_Kondi_Faktor = 0.1f;	 // set pieces
    public float m_fTS_Kondi_Faktor = 0.2f;  // scoring
    public float m_fTW_Kondi_Faktor = 0.2f;  // goalkeeping
    public float m_fVE_Kondi_Faktor = 0.2f;  // defense

    // experience factor
    public float m_fErfahrungs_Faktor = 0.45f;

    // general form factor
    public float m_fForm_Faktor = 0.65f;

    ////////////////////////////////AV//////////////////////////////////////////
    // wingback
    FactorObject m_clAussenVerteidiger;
    // def wingback
    FactorObject m_clAussenVerteidiger_DEF;
    // wingback towards middle
    FactorObject m_clAussenVerteidiger_IN;
    // off wingback
    FactorObject m_clAussenVerteidiger_OFF;

    ////////////////////////////////AM//////////////////////////////////////////
    // normal winger
    FactorObject m_clFluegelspieler;
    // def winger
    FactorObject m_clFluegelspieler_DEF;
    // winger towards middle
    FactorObject m_clFluegelspieler_IN;
    // off winger
    FactorObject m_clFluegelspieler_OFF;

    ////////////////////////////////Central_DEF//////////////////////////////////////////
    // central defender
    FactorObject m_clInnenVerteidiger;
    // defender towards wing
    FactorObject m_clInnenVerteidiger_AUS;
    // offensive defender
    FactorObject m_clInnenVerteidiger_OFF;

    ////////////////////////////////MS//////////////////////////////////////////
    // normal foreward
    FactorObject m_clSturm;
    // defensive forward
    FactorObject m_clSturm_DEF;
    // forward towards wing
    FactorObject m_clSturm_AUS;

    ////////////////////////////////TW//////////////////////////////////////////
    // keeper
    FactorObject m_clTorwart;

    ////////////////////////////////ZM//////////////////////////////////////////
    // normal inner midfielder
    FactorObject m_clZentralesMittelfeld;
    // inner towards wing
    FactorObject m_clZentralesMittelfeld_AUS;
    // def inner
    FactorObject m_clZentralesMittelfeld_DEF;
    // off inner
    FactorObject m_clZentralesMittelfeld_OFF;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of FormulaFactors
     */
    private FormulaFactors() {
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Get the singleton FormulaFactors instance.
     */
    public static FormulaFactors instance() {
        if (m_clInstance == null) {
            m_clInstance = new FormulaFactors();
            m_clInstance.importDefaults();
        }

        return m_clInstance;
    }

    /**
     * liefert Array mit allen Objekten
     */
    public FactorObject[] getAllObj() {
        final FactorObject[] allObj = new FactorObject[ANZ_FAKTOROBJEKTE];

        allObj[0] = m_clTorwart;
        allObj[1] = m_clInnenVerteidiger;
        allObj[2] = m_clInnenVerteidiger_AUS;
        allObj[3] = m_clInnenVerteidiger_OFF;
        allObj[4] = m_clAussenVerteidiger;
        allObj[5] = m_clAussenVerteidiger_OFF;
        allObj[6] = m_clAussenVerteidiger_IN;
        allObj[7] = m_clAussenVerteidiger_DEF;
        allObj[8] = m_clFluegelspieler;
        allObj[9] = m_clFluegelspieler_DEF;
        allObj[10] = m_clFluegelspieler_OFF;
        allObj[11] = m_clFluegelspieler_IN;
        allObj[12] = m_clZentralesMittelfeld;
        allObj[13] = m_clZentralesMittelfeld_OFF;
        allObj[14] = m_clZentralesMittelfeld_DEF;
        allObj[15] = m_clZentralesMittelfeld_AUS;
        allObj[16] = m_clSturm;
        allObj[17] = m_clSturm_DEF;
        allObj[18] = m_clSturm_AUS;
        return allObj;
    }


    /**
     * Setter for property m_fForm_Faktor.
     *
     * @param erfahrungs_Faktor New value of property m_fForm_Faktor.
     */
    public void setErfahrungs_Faktor(float erfahrungs_Faktor) {
        this.m_fErfahrungs_Faktor = erfahrungs_Faktor;
    }

    /**
     * Getter for property m_fForm_Faktor.
     *
     * @return Value of property m_fForm_Faktor.
     */
    public float getErfahrungs_Faktor() {
        return m_fErfahrungs_Faktor;
    }

    /**
     * Setter for property m_fForm_Faktor.
     *
     * @param m_fForm_Faktor New value of property m_fForm_Faktor.
     */
    public void setForm_Faktor(float m_fForm_Faktor) {
        this.m_fForm_Faktor = m_fForm_Faktor;
    }

     /**
     * Getter for property m_fForm_Faktor.
     *
     * @return Value of property m_fForm_Faktor.
     */
    public float getForm_Faktor() {
        return m_fForm_Faktor;
    }

    /**
     * Import star formulas from the default XML.
     */
    public void importDefaults() {
        //vorsichtshalber vorinitialisieren.
        init();
        initKondition();

        //eigentliche Defaults lesen
        readFromXML("defaults.xml");
    }

    /**
     * Initialize member with 'hardcoded' default values.
     * Usually these values should never be used, as we read the default.xml afterwards.
     */
    public void init() {
        //                                     position,									tw,   sa,   ps,   fl,   vt,   ts,   std,  kondi
        m_clTorwart = new FactorObject(ISpielerPosition.TORWART,		      		 		10.0f,0.0f, 0.0f, 0.0f, 2.6f, 0.0f, 0.0f, 0.0f);
        m_clInnenVerteidiger = new FactorObject(ISpielerPosition.INNENVERTEIDIGER,			0.0f, 3.0f, 0.5f, 0.0f, 9.0f, 0.0f, 0.0f, 0.0f);
        m_clInnenVerteidiger_AUS = new FactorObject(ISpielerPosition.INNENVERTEIDIGER_AUS,	0.0f, 1.5f, 0.5f, 2.0f, 8.5f, 0.0f, 0.0f, 0.0f);
        m_clInnenVerteidiger_OFF = new FactorObject(ISpielerPosition.INNENVERTEIDIGER_OFF,	0.0f, 5.0f, 0.5f, 0.0f, 6.0f, 0.0f, 0.0f, 0.0f);
        m_clAussenVerteidiger_IN = new FactorObject(ISpielerPosition.AUSSENVERTEIDIGER_IN,	0.0f, 1.0f, 0.5f, 2.0f, 8.5f, 0.0f, 0.0f, 0.0f);
        m_clAussenVerteidiger_OFF = new FactorObject(ISpielerPosition.AUSSENVERTEIDIGER_OFF,0.0f, 1.5f, 1.5f, 4.0f, 6.0f, 0.0f, 0.0f, 0.0f);
        m_clAussenVerteidiger_DEF = new FactorObject(ISpielerPosition.AUSSENVERTEIDIGER_DEF,0.0f, 0.5f, 0.5f, 2.0f, 8.5f, 0.0f, 0.0f, 0.0f);
        m_clAussenVerteidiger = new FactorObject(ISpielerPosition.AUSSENVERTEIDIGER,		0.0f, 1.0f, 0.0f, 3.5f, 8.0f, 0.0f, 0.0f, 0.0f);
        m_clFluegelspieler_OFF = new FactorObject(ISpielerPosition.FLUEGELSPIEL_OFF,		0.0f, 3.0f, 2.5f, 7.0f, 1.0f, 0.0f, 0.0f, 0.0f);
        m_clFluegelspieler_DEF = new FactorObject(ISpielerPosition.FLUEGELSPIEL_DEF,		0.0f, 3.5f, 2.0f, 5.0f, 4.0f, 0.0f, 0.0f, 0.0f);
        m_clFluegelspieler_IN = new FactorObject(ISpielerPosition.FLUEGELSPIEL_IN,			0.0f, 6.0f, 2.0f, 4.0f, 2.0f, 0.0f, 0.0f, 0.0f);
        m_clFluegelspieler = new FactorObject(ISpielerPosition.FLUEGELSPIEL,				0.0f, 3.5f, 2.5f, 7.0f, 1.5f, 0.0f, 0.0f, 0.0f);
        m_clZentralesMittelfeld_OFF = new FactorObject(ISpielerPosition.MITTELFELD_OFF,		0.0f, 8.0f, 3.5f, 0.0f, 2.0f, 0.0f, 0.0f, 0.0f);
        m_clZentralesMittelfeld_DEF = new FactorObject(ISpielerPosition.MITTELFELD_DEF,		0.0f, 8.0f, 2.0f, 0.0f, 3.5f, 0.0f, 0.0f, 0.0f);
	    m_clZentralesMittelfeld_AUS = new FactorObject(ISpielerPosition.MITTELFELD_AUS,		0.0f, 6.0f, 2.0f, 5.0f, 2.0f, 0.0f, 0.0f, 0.0f);
        m_clZentralesMittelfeld = new FactorObject(ISpielerPosition.MITTELFELD,				0.0f, 8.0f, 3.0f, 0.0f, 3.0f, 0.0f, 0.0f, 0.0f);
        m_clSturm = new FactorObject(ISpielerPosition.STURM,								0.0f, 0.0f, 3.0f, 1.5f, 0.0f, 9.0f, 0.0f, 0.0f);
        m_clSturm_DEF = new FactorObject(ISpielerPosition.STURM_DEF,						0.0f, 5.0f, 3.0f, 0.0f, 0.0f, 6.0f, 0.0f, 0.0f);
        m_clSturm_AUS = new FactorObject(ISpielerPosition.STURM_AUS,						0.0f, 0.0f, 3.0f, 4.0f, 0.0f, 6.5f, 0.0f, 0.0f);
        m_fForm_Faktor = 0.65f;
        m_fErfahrungs_Faktor = 0.45f;
    }

    /**
     * Initialize member for stamina settings with 'hardcoded' default values.
     * Usually these values should never be used, as we read the default.xml afterwards.
     */
    public void initKondition() {
        m_fSP_Kondi_Faktor = 0.50f;
        m_fTW_Kondi_Faktor = 0.2f;
        m_fVE_Kondi_Faktor = 0.2f;
        m_fPS_Kondi_Faktor = 0.0f;
        m_fTS_Kondi_Faktor = 0.2f;
        m_fFL_Kondi_Faktor = 0.25f;
        m_fST_Kondi_Faktor = 0.1f;
    }

    /**
     * Read an XML file with star formula configurations.
     *
     * @param dateiname the filename of the xml config
     */
    public void readFromXML(String dateiname) {
    	final XMLManager manager = XMLManager.instance();
    	final Document doc = manager.parseFile(dateiname);
        Element ele = null;


    	if (doc == null) {
            return;
        }

        //Tabelle erstellen
    	final Element root = doc.getDocumentElement();

        try {
            //Daten füllen
            ele = (Element) root.getElementsByTagName("Form").item(0);
            m_fForm_Faktor = Float.parseFloat(manager.getFirstChildNodeValue(ele));
            ele = (Element) root.getElementsByTagName("Playmaking_Condition").item(0);
            m_fSP_Kondi_Faktor = Float.parseFloat(manager.getFirstChildNodeValue(ele));
            ele = (Element) root.getElementsByTagName("Keeper_Condition").item(0);
            m_fTW_Kondi_Faktor = Float.parseFloat(manager.getFirstChildNodeValue(ele));
            ele = (Element) root.getElementsByTagName("Defense_Condition").item(0);
            m_fVE_Kondi_Faktor = Float.parseFloat(manager.getFirstChildNodeValue(ele));
            ele = (Element) root.getElementsByTagName("Winger_Condition").item(0);
            m_fFL_Kondi_Faktor = Float.parseFloat(manager.getFirstChildNodeValue(ele));
            ele = (Element) root.getElementsByTagName("Passing_Condition").item(0);
            m_fPS_Kondi_Faktor = Float.parseFloat(manager.getFirstChildNodeValue(ele));
            ele = (Element) root.getElementsByTagName("Expierience").item(0);
            m_fErfahrungs_Faktor = Float.parseFloat(manager.getFirstChildNodeValue(ele));
            ele = (Element) root.getElementsByTagName("Standard_Condition").item(0);
            m_fST_Kondi_Faktor = Float.parseFloat(manager.getFirstChildNodeValue(ele));
            ele = (Element) root.getElementsByTagName("Scoring_Condition").item(0);
            m_fTS_Kondi_Faktor = Float.parseFloat(manager.getFirstChildNodeValue(ele));

            m_clTorwart = readObject("KEEPER", root);
            m_clInnenVerteidiger = readObject("DEFENSE", root);
            m_clInnenVerteidiger_OFF = readObject("DEFENSE_O", root);
            m_clInnenVerteidiger_AUS = readObject("DEFENSE_W", root);
            m_clAussenVerteidiger = readObject("WB", root);
            m_clAussenVerteidiger_DEF = readObject("WB_D", root);
            m_clAussenVerteidiger_OFF = readObject("WB_O", root);
            m_clAussenVerteidiger_IN = readObject("WB_M", root);
            m_clZentralesMittelfeld_DEF = readObject("MD_D", root);
            m_clZentralesMittelfeld = readObject("MD", root);
            m_clZentralesMittelfeld_OFF = readObject("MD_O", root);
            m_clZentralesMittelfeld_AUS = readObject("MD_W", root);
            m_clFluegelspieler_IN = readObject("WING_M", root);
            m_clFluegelspieler_OFF = readObject("WING_O", root);
            m_clFluegelspieler_DEF = readObject("WING_D", root);
            m_clFluegelspieler = readObject("WING", root);
            m_clSturm_DEF = readObject("FW_D", root);
            m_clSturm = readObject("FW", root);
            m_clSturm_AUS = readObject("FW_W", root);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"FormulaFactor.redxmlException gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
        }
    }

    /**
     * Read the single skill contributions for a position.
     *
     * @param tagname tag name for a position
     * @param root the XML root element
     *
     * @return the created FactorObject
     */
    public FactorObject readObject(String tagname, Element root) {
        Element ele = null;
        final FactorObject factorObject = new FactorObject(null);
        final XMLManager manager = XMLManager.instance();
        try {
            root = (Element) root.getElementsByTagName(tagname).item(0);

            //Daten füllen
            ele = (Element) root.getElementsByTagName("Position").item(0);
            factorObject.setPosition(Byte.parseByte(manager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("condition").item(0);
            factorObject.setKondition(Float.parseFloat(manager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("defense").item(0);
            factorObject.setVerteidigung(Float.parseFloat(manager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("passing").item(0);
            factorObject.setPasspiel(Float.parseFloat(manager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("playmaking").item(0);
            factorObject.setSpielaufbau(Float.parseFloat(manager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("scoring").item(0);
            factorObject.setTorschuss(Float.parseFloat(manager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("wing").item(0);
            factorObject.setFluegelspiel(Float.parseFloat(manager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("keeper").item(0);
            factorObject.setTorwart(Float.parseFloat(manager.getFirstChildNodeValue(ele)));
            ele = (Element) root.getElementsByTagName("standard").item(0);
            factorObject.setStandards(Float.parseFloat(manager.getFirstChildNodeValue(ele)));
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"FormulaFactor.redxmlException gefangen: " + e);
            HOLogger.instance().log(getClass(),e);
        }

        return factorObject;
    }

    /**
     * gesaved
     */
    public void save() {
        final FactorObject[] allFaktoren = getAllObj();

        for (int i = 0; (allFaktoren != null) && (i < allFaktoren.length); i++) {
            de.hattrickorganizer.database.DBZugriff.instance().setFaktorenFromDB(allFaktoren[i]);
        }
    }

    /**
     * Write the currently configured values into a specified file.
     */
    public void write2XML(String filename) {
        try {
            Document doc = null;
            Element ele = null;
            Element tmpEle = null;
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();

            doc = builder.newDocument();
            tmpEle = doc.createElement("FormulaFactors");
            doc.appendChild(tmpEle);
            ele = doc.createElement("Form");
            tmpEle.appendChild(ele);
            ele.appendChild(doc.createTextNode("" + m_fForm_Faktor));
            ele = doc.createElement("Expierience");
            tmpEle.appendChild(ele);
            ele.appendChild(doc.createTextNode("" + m_fErfahrungs_Faktor));

            //child2
            ele = doc.createElement("Playmaking_Condition");
            tmpEle.appendChild(ele);
            ele.appendChild(doc.createTextNode("" + m_fSP_Kondi_Faktor));

            //child2
            ele = doc.createElement("Keeper_Condition");
            tmpEle.appendChild(ele);
            ele.appendChild(doc.createTextNode("" + m_fTW_Kondi_Faktor));

            //child2
            ele = doc.createElement("Defense_Condition");
            tmpEle.appendChild(ele);
            ele.appendChild(doc.createTextNode("" + m_fVE_Kondi_Faktor));

            //child2
            ele = doc.createElement("Winger_Condition");
            tmpEle.appendChild(ele);
            ele.appendChild(doc.createTextNode("" + m_fFL_Kondi_Faktor));

            //child2
            ele = doc.createElement("Passing_Condition");
            tmpEle.appendChild(ele);
            ele.appendChild(doc.createTextNode("" + m_fPS_Kondi_Faktor));

            //child2
            ele = doc.createElement("Standard_Condition");
            tmpEle.appendChild(ele);
            ele.appendChild(doc.createTextNode("" + m_fST_Kondi_Faktor));

            //child2
            ele = doc.createElement("Scoring_Condition");
            tmpEle.appendChild(ele);
            ele.appendChild(doc.createTextNode("" + m_fTS_Kondi_Faktor));

            //Objekte schreiben
            writeFaktorObj(doc, m_clTorwart, tmpEle, "KEEPER");
            writeFaktorObj(doc, m_clInnenVerteidiger, tmpEle, "DEFENSE");
            writeFaktorObj(doc, m_clInnenVerteidiger_OFF, tmpEle, "DEFENSE_O");
            writeFaktorObj(doc, m_clInnenVerteidiger_AUS, tmpEle, "DEFENSE_W");
            writeFaktorObj(doc, m_clAussenVerteidiger, tmpEle, "WB");
            writeFaktorObj(doc, m_clAussenVerteidiger_DEF, tmpEle, "WB_D");
            writeFaktorObj(doc, m_clAussenVerteidiger_OFF, tmpEle, "WB_O");
            writeFaktorObj(doc, m_clAussenVerteidiger_IN, tmpEle, "WB_M");
            writeFaktorObj(doc, m_clZentralesMittelfeld_DEF, tmpEle, "MD_D");
            writeFaktorObj(doc, m_clZentralesMittelfeld, tmpEle, "MD");
            writeFaktorObj(doc, m_clZentralesMittelfeld_OFF, tmpEle, "MD_O");
            writeFaktorObj(doc, m_clZentralesMittelfeld_AUS, tmpEle, "MD_W");
            writeFaktorObj(doc, m_clFluegelspieler_IN, tmpEle, "WING_M");
            writeFaktorObj(doc, m_clFluegelspieler_OFF, tmpEle, "WING_O");
            writeFaktorObj(doc, m_clFluegelspieler_DEF, tmpEle, "WING_D");
            writeFaktorObj(doc, m_clFluegelspieler, tmpEle, "WING");
            writeFaktorObj(doc, m_clSturm_DEF, tmpEle, "FW_D");
			writeFaktorObj(doc, m_clSturm_AUS, tmpEle, "FW_W");
            writeFaktorObj(doc, m_clSturm, tmpEle, "FW");

            //doc.appendChild ( ele );
            XMLManager.instance().writeXML(doc, filename);
        } catch (Exception e) {
            HOLogger.instance().log(getClass(),"XMLManager.writeXML: " + e);
            HOLogger.instance().log(getClass(),e);
        }
    }

    /**
     * Add data for a single position to the XML tree.
     */
    protected void writeFaktorObj(Document doc, FactorObject obj, Element root, String tagName) {
        Element ele = null;
        Element tmpEle = null;

        tmpEle = doc.createElement(tagName);
        root.appendChild(tmpEle);
        ele = doc.createElement("Position");
        tmpEle.appendChild(ele);
        ele.appendChild(doc.createTextNode("" + obj.getPosition()));

        //Faktoren
        ele = doc.createElement("keeper");
        tmpEle.appendChild(ele);
        ele.appendChild(doc.createTextNode("" + obj.getTorwart()));
        ele = doc.createElement("defense");
        tmpEle.appendChild(ele);
        ele.appendChild(doc.createTextNode("" + obj.getVerteidigung()));
        ele = doc.createElement("passing");
        tmpEle.appendChild(ele);
        ele.appendChild(doc.createTextNode("" + obj.getPasspiel()));
        ele = doc.createElement("playmaking");
        tmpEle.appendChild(ele);
        ele.appendChild(doc.createTextNode("" + obj.getSpielaufbau()));
        ele = doc.createElement("scoring");
        tmpEle.appendChild(ele);
        ele.appendChild(doc.createTextNode("" + obj.getTorschuss()));
        ele = doc.createElement("wing");
        tmpEle.appendChild(ele);
        ele.appendChild(doc.createTextNode("" + obj.getFluegelspiel()));
        ele = doc.createElement("standard");
        tmpEle.appendChild(ele);
        ele.appendChild(doc.createTextNode("" + obj.getStandards()));
        ele = doc.createElement("condition");
        tmpEle.appendChild(ele);
        ele.appendChild(doc.createTextNode("" + obj.getKondition()));

        // Insert a comment in front of the element node
        final org.w3c.dom.Comment comment = doc.createComment("unused since HO! 1.25");
        tmpEle.insertBefore(comment, ele);
    }

    /**
     * Return a FactorObject for hoPosition
     *
     * @author Thorsten Dietz
     * @param playerPosition
     * @return
     */
    public FactorObject getPositionFactor(byte playerPosition){

    	switch (playerPosition) {
	        case ISpielerPosition.TORWART: 					return m_clTorwart;
	        case ISpielerPosition.INNENVERTEIDIGER:        	return m_clInnenVerteidiger;
	        case ISpielerPosition.INNENVERTEIDIGER_OFF:    	return m_clInnenVerteidiger_OFF;
	        case ISpielerPosition.INNENVERTEIDIGER_AUS:		return m_clInnenVerteidiger_AUS;
	        case ISpielerPosition.AUSSENVERTEIDIGER_OFF:   	return m_clAussenVerteidiger_OFF;
	        case ISpielerPosition.AUSSENVERTEIDIGER_DEF:   	return m_clAussenVerteidiger_DEF;
	        case ISpielerPosition.AUSSENVERTEIDIGER_IN:    	return m_clAussenVerteidiger_IN;
	        case ISpielerPosition.AUSSENVERTEIDIGER:       	return m_clAussenVerteidiger;
	        case ISpielerPosition.MITTELFELD_DEF:        	return m_clZentralesMittelfeld_DEF;
	        case ISpielerPosition.MITTELFELD_OFF:        	return m_clZentralesMittelfeld_OFF;
	        case ISpielerPosition.MITTELFELD_AUS:        	return m_clZentralesMittelfeld_AUS;
	        case ISpielerPosition.MITTELFELD:	        	return m_clZentralesMittelfeld;
	        case ISpielerPosition.FLUEGELSPIEL_DEF:        	return m_clFluegelspieler_DEF;
	        case ISpielerPosition.FLUEGELSPIEL_OFF:			return m_clFluegelspieler_OFF;
	        case ISpielerPosition.FLUEGELSPIEL_IN:			return m_clFluegelspieler_IN;
	        case ISpielerPosition.FLUEGELSPIEL:				return m_clFluegelspieler;
	        case ISpielerPosition.STURM_DEF:				return m_clSturm_DEF;
	        case ISpielerPosition.STURM:					return m_clSturm;
	        case ISpielerPosition.STURM_AUS:				return m_clSturm_AUS;
	        case ISpielerPosition.TRAINER:
        default:
            return null;
    }
    }

    /**
     * set factorObject for a hoPosition
     *
     * @author Thorsten Dietz
     * @param hoPosition
     * @param factorObject
     */
    public void setPositionFactor(byte pos, FactorObject factorObject){
    switch(pos){
        case ISpielerPosition.TORWART:
            m_clTorwart = factorObject;
            break;

        case ISpielerPosition.INNENVERTEIDIGER:
            m_clInnenVerteidiger = factorObject;
            break;

        case ISpielerPosition.INNENVERTEIDIGER_OFF:
            m_clInnenVerteidiger_OFF = factorObject;
            break;

        case ISpielerPosition.INNENVERTEIDIGER_AUS:
            m_clInnenVerteidiger_AUS = factorObject;
            break;

        case ISpielerPosition.AUSSENVERTEIDIGER:
            m_clAussenVerteidiger = factorObject;
            break;

        case ISpielerPosition.AUSSENVERTEIDIGER_OFF:
            m_clAussenVerteidiger_OFF = factorObject;
            break;

        case ISpielerPosition.AUSSENVERTEIDIGER_DEF:
            m_clAussenVerteidiger_DEF = factorObject;
            break;

        case ISpielerPosition.AUSSENVERTEIDIGER_IN:
            m_clAussenVerteidiger_IN = factorObject;
            break;

        case ISpielerPosition.MITTELFELD:
            m_clZentralesMittelfeld = factorObject;
            break;

        case ISpielerPosition.MITTELFELD_OFF:
            m_clZentralesMittelfeld_OFF = factorObject;
            break;

        case ISpielerPosition.MITTELFELD_DEF:
            m_clZentralesMittelfeld_DEF = factorObject;
            break;

        case ISpielerPosition.MITTELFELD_AUS:
            m_clZentralesMittelfeld_AUS = factorObject;
            break;

        case ISpielerPosition.FLUEGELSPIEL:
            m_clFluegelspieler = factorObject;
            break;

        case ISpielerPosition.FLUEGELSPIEL_OFF:
            m_clFluegelspieler_OFF = factorObject;
            break;

        case ISpielerPosition.FLUEGELSPIEL_DEF:
            m_clFluegelspieler_DEF = factorObject;
            break;

        case ISpielerPosition.FLUEGELSPIEL_IN:
            m_clFluegelspieler_IN = factorObject;
            break;

        case ISpielerPosition.STURM:
            m_clSturm = factorObject;
            break;

        case ISpielerPosition.STURM_DEF:
            m_clSturm_DEF = factorObject;
            break;

		case ISpielerPosition.STURM_AUS:
			m_clSturm_AUS = factorObject;
			break;
    }
     }
	public HashMap getValues() {
		HashMap map = new HashMap();
		map.put("m_fFL_Kondi_Faktor",String.valueOf(m_fFL_Kondi_Faktor));
		map.put("m_fPS_Kondi_Faktor",String.valueOf(m_fPS_Kondi_Faktor));
		map.put("m_fSP_Kondi_Faktor",String.valueOf(m_fSP_Kondi_Faktor));
		map.put("m_fST_Kondi_Faktor",String.valueOf(m_fST_Kondi_Faktor));
		map.put("m_fTS_Kondi_Faktor",String.valueOf(m_fTS_Kondi_Faktor));
		map.put("m_fTW_Kondi_Faktor",String.valueOf(m_fTW_Kondi_Faktor));
		map.put("m_fVE_Kondi_Faktor",String.valueOf(m_fVE_Kondi_Faktor));
		map.put("m_fErfahrungs_Faktor",String.valueOf(m_fErfahrungs_Faktor));
		map.put("m_fForm_Faktor",String.valueOf(m_fForm_Faktor));
		return map;
	}

	public void setValues(HashMap values) {
		m_fFL_Kondi_Faktor = getFloatValue(values,"m_fFL_Kondi_Faktor");
		m_fPS_Kondi_Faktor = getFloatValue(values,"m_fPS_Kondi_Faktor");
		m_fSP_Kondi_Faktor = getFloatValue(values,"m_fSP_Kondi_Faktor");
		m_fST_Kondi_Faktor = getFloatValue(values,"m_fST_Kondi_Faktor");
		m_fTS_Kondi_Faktor = getFloatValue(values,"m_fTS_Kondi_Faktor");
		m_fTW_Kondi_Faktor = getFloatValue(values,"m_fTW_Kondi_Faktor");
		m_fVE_Kondi_Faktor = getFloatValue(values,"m_fVE_Kondi_Faktor");
		m_fErfahrungs_Faktor = getFloatValue(values,"m_fErfahrungs_Faktor");
		m_fForm_Faktor = getFloatValue(values,"m_fForm_Faktor");
	}
}
