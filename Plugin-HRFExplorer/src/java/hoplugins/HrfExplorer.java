/*
 * Created on 09.05.2005
 */
package hoplugins;

import hoplugins.hrfExplorer.*;

import plugins.IHOMiniModel;
import plugins.IGUI;
import plugins.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author KickMuck
 */

public class HrfExplorer implements IPlugin,IOfficialPlugin,ActionListener,ItemListener,MouseListener,IRefreshable,TableColumnModelListener
{
	// Members aus HO
	private plugins.IHOMiniModel m_clModel = null; 		//Das IHOMiniModel
	private plugins.IJDBCAdapter m_jdbcAdapter = null; 	//Adapter f�r DB-Zugriffe
	private IGUI m_gui = null; 							// Adapter f�r GUI-Elemente
	private static IHelper m_helper = null; 			// Adapter f�r Hilfsfunktionen
	private static Properties m_properties;				// Adapter f�r property-Sprachfiles
	//private static IDebugWindow debugWindow;			// Adapter f�r das DebugWindow
	private Properties m_HO_Properties;					// Adapter f�r HO-Properties
	private IBasics m_basics;							// Adapter f�r Basic-Funktionen
	private IXtraData m_xtraData;						// Adapter f�r XtraData
	private IMatchKurzInfo[] m_kurzInfo;				// Adapter f�r Spieleinfos, u.a. ob Friendly oder Liga
	
	
	private File[] m_unquenchableFiles = null;			// Member f�r die Files, die nicht gel�scht werden d�rfen
	
	private int m_pluginID = 29;						// Member f�r die PluginID

	private double m_pluginVersion = 1.03;				// Member f�r die Versionsnummer des Plugins
	
	private String m_TabName = new String();			// Name des Tabs
	private String m_PluginName = "HRFExplorer";		// Name des Plugins ohne Versionsnummer
	
	private int m_ResultSetFetchSize;					// Menge der Tupel im ResultSet
	
	private int m_Language_ID = 1;						//Nummer der gew�hlten Sprache
	// Members f�r Farben
	private Color gruen = new Color (220,255,220);
	private Color hellblau = new Color (235,235,255);
	private Color dunkelblau = new Color (220,220,255);
	private Color rot = new Color (255,200,200);
	private Color m_LineColor;
	
	//Members f�r die GUI
	private JTabbedPane m_TabPane_Details = null;
	
	private JSplitPane m_SplitPane_main = null;
	private JSplitPane m_SplitPane_top = null;
	private JSplitPane m_SplitPane_top_left = null;		// SplitPane f�r Calendar und Imports
	
	private JScrollPane m_ScrollPane_Calendar = null;
	private JScrollPane m_ScrollPane_FileTable = null;
	private JScrollPane m_ScrollPane_Details = null;
	private JScrollPane m_ScrollPane_Imports = null;	// nimmt die Tabelle f�r die zu importierenden Dateien auf
	private JScrollPane m_ScrollPane_Images = null;
	
	private JPanel m_Panel_main = null;
	private JPanel m_Panel_Calendar_main = null;
	private JPanel m_Panel_Calendar_main_north = null;
	private JPanel m_Panel_Details_main = null;
	private JPanel m_Panel_Details_north = null;
	private JPanel m_Panel_FileTable_main = null;
	private JPanel m_Panel_FileTable_main_north = null;
	private JPanel m_Panel_Imports_main = null;			// CENTER=m_ScrollPane_Imports,NORTH=m_Button_ResetImports
	
	private JButton m_Button_load_file = null;
	private JButton m_Button_delete_file = null;
	private JButton m_Button_delete_db = null;
	private JButton m_Button_Delete_Row = null;
	private JButton m_Button_ImportList = null;
	private JButton m_Button_Select_All = null;
	private JButton m_Button_reset = null;
	private JButton m_Button_help = null;
	private JButton m_Button_Month_Forward = null;
	private JButton m_Button_Month_Back = null;
	private JButton m_Button_GoTo = null;
	private JButton m_Button_ResetImports = null;
	
	private JLabel m_Label_Monat = null;
	private JLabel m_Label_DetailHeader = null;
	
	private JComboBox m_CB_year = null;
	private JComboBox m_CB_month = null;
	
	// Members f�r die Tabellen
	private JTable m_Table_Calendar = null;
	private JTable m_Table_Filelist = null;
	private JTable m_Table_Details = null;
	private JTable m_Table_Imports = null;				// Tabelle mit den Pfaden f�r Importdateien
	private JTable m_Table_Images = null;
	
	private HrfTableModel m_TableModel_Calendar = null;
	private HrfTableModel m_TableModel_Filelist = null;
	private HrfTableModel m_TableModel_Details = null;
	private HrfTableModel m_TableModel_Imports = null;
	private HrfTableModel m_ableModel_Images = null;
	
	private HrfPanelCellRenderer m_renderer = new HrfPanelCellRenderer();
	

	private static int m_int_selectedMonth;				// int f�r den Monat im Calendar-Panel (0-11)
	private static int m_int_selectedYear;				// int f�r das Jahr aus dem Calendar-Panel
	private int m_int_firstYearInDB;					// int f�r das Jahr des ersten DB-Eintrages
	private int m_int_actualYear;						// int f�r das aktuelle Jahr
	
	private int m_int_Hoehe_DetailPanels;				// H�he des Panels, in dem alle Details in der Detail-Tabelle stehen
	private int m_int_Breite_Detail_Fixed = 130;		// Breite der 1. Spalte der Detail-Tabelle
	private int m_int_Breite_Detail_Var = 140;			// Breite der weiteren Spalten der Detail-Tabelle
	private int m_int_Hoehe_Label = 16;					// H�he der Labels f�r die Details in der Detail-Tabelle
	
	private int m_int_anz_DBEintraege = 0;				// Anzahl der HRF-Files in der DB
	private int m_TeamID;								// Die TeamID
	
	// Breiten der Spalten in der jeweiligen Tabelle
	private int[] m_intAr_col_width_Filelist = {30,130,140,80,40,60,60,110,40,60};
	private int[] m_intAr_col_width_Calendar = {40,40,40,40,40,40,40,40};
	private int[] m_intAr_col_width_Details = {140};
	private int[] m_intAr_col_width_Imports = {200};
	
	private String m_Str_hrfPfad = "";					// Pfad aus UserSettings, dort werden normalerweise die hrf-files hingespeichert
	
	private String[] m_Ar_Detail_Label_fix;				// Bezeichnungen in der 1.Spalte der Detail-Tabelle
	
	
	//Variablen f�r Detailtabelle
	private Vector m_V_Details_Header;
	private Vector m_V_Details_Values;
	//Variablen f�r Filelist-Tabelle
	private Vector m_V_Filelist_Header;
	private Vector m_V_Filelist_Values;
//	Variablen f�r Calendar-Tabelle
	private Vector m_V_Calendar_Header;
	private Vector m_V_Calendar_Values;

	private static String[] m_Ar_days = new String[7];
	private static Vector m_V_months = null;
	private Vector m_V_Filelist_Keys = new Vector();
	
	private File[] m_files;
	private JFileChooser m_FileChooser_chooser;
	private GregorianCalendar m_gc;
	private ResultSet m_queryResult;
	private ResultSet m_Result_SpecialEvent;
	
	private static Hashtable m_HashTable_DayInDB = new Hashtable(40);		// KEY: Tag des gew�hlten Monats in Calendar, 		VALUE: HRF-ID f�r diesen Tag
	private Hashtable m_HashTable_Details = new Hashtable(40);			// KEY: Pfad oder Datum eines HrfDetails-Objekt, 	VALUE: das HrfDetails-Objekt
	private Hashtable m_HashTable_Details_ColHeader = new Hashtable(40);	// KEY: Datum eines HrfDetails-Objekt				VALUE: das HrfDetails-Objekt
	private Hashtable m_HashTable_Columns = new Hashtable(40);			// KEY: Spaltenname der Detailtabelle				VALUE: Vector mit dem Inhalt einer Spalte der Detailtabelle
	private static Hashtable m_HashTable_DatumKey = new Hashtable(40);	// KEY: Datum im Format YYYY-MM-DD					VALUE: Dateipfad
	private Hashtable m_HashTable_Import = new Hashtable(40);				// KEY: Pfad der Dateien aus der Importtabelle		VALUE: ---
	private static Hashtable m_HashTable_isEvent = new Hashtable(40);		// KEY: Tag des gew�hlten Monats in Calendar, 		VALUE: Matchtyp als String
	private Hashtable m_HashTable_MatchTyp = new Hashtable(40);			// KEY: Match-ID, 									VALUE: Matchtyp
	private static Hashtable m_HashTable_EventInfo = new Hashtable(40);
	private Hashtable m_HashTable_EventGUI = new Hashtable();			// KEY: Der Tag des Events							VALUE: Vector mit Zeit(sek) und Eventtyp
	
	/** 
	 * Wird von HO aufgerufen, wenn das Tab aktiviert wird
	 * @param hOMiniModel Das MiniModel, �bergeben von HO
	 */
	public void start(IHOMiniModel hOMiniModel)
	{
		m_TabName = getName();						// Name des Tabs holen  
		m_clModel = hOMiniModel;					// Das MiniModell in der MemberVariablen speichern
		m_jdbcAdapter = m_clModel.getAdapter();		// Einen Adapter f�r Datenbankquerys besorgen
		m_gui = m_clModel.getGUI();					// Adapter f�r GUI-Funktionen holen
		m_helper = m_clModel.getHelper();			// Adapter f�r Helper-Funktionen holen
		m_HO_Properties = m_clModel.getResource();	// Adapter f�r HO-Properties
		m_basics = m_clModel.getBasics();			// Adapter f�r die Basics
		m_xtraData = m_clModel.getXtraDaten();		// Adapter f�r die XtraDaten
		IRefreshable ref = this;
		
		// Debug Window einbinden
		//debugWindow = m_clModel.getGUI ().createDebugWindow ( new java.awt.Point( 100, 200 ), new java.awt.Dimension( 800,  400 ) );
	
		// Ermitteln und setzen der LanguageID
		m_Language_ID = m_helper.getLanguageID();
		
		// Aktuelles Datum ermitteln und in die Members schreiben
		m_gc = new GregorianCalendar();
		m_int_selectedMonth = m_gc.get(GregorianCalendar.MONTH);
		m_int_selectedYear = m_gc.get(GregorianCalendar.YEAR);
		m_int_actualYear = m_gc.get(GregorianCalendar.YEAR);
		
		// TeamID setzen
		m_TeamID = m_basics.getTeamId();
		
		// Matches f�r das Team holen und in die Hashtable m_HashTable_MatchTyp f�llen
		m_kurzInfo = m_clModel.getMatchesKurzInfo(m_TeamID);
		for(int ii = 0; ii < m_kurzInfo.length; ii++)
		{
			m_HashTable_MatchTyp.put(new Integer(m_kurzInfo[ii].getMatchID()),new Integer(m_kurzInfo[ii].getMatchTyp()));
		}

		//einlesen der Sprache-Files
		try
        {
			m_properties = new Properties();
            File languageFile = new File("hoplugins/hrfExplorer/sprache/" + m_clModel.getHelper().getLanguageName() + ".properties");
            if(languageFile.exists())
            {
            	m_properties.load(new FileInputStream(languageFile));
            } else
            {
                languageFile = new File("hoplugins/hrfExplorer/sprache/English.properties");
                m_properties.load(new FileInputStream(languageFile));
            }
        }
        catch(Exception e) 
		{
		}
        
        // Namen der Tage in m_Ar_days schreiben
        setTage();
        
        //F�llen der HashTable m_HashTable_EventInfo mit den Sprachenabh�ngigen W�rtern
        m_HashTable_EventInfo.put("L",m_HO_Properties.getProperty("LigaSpiel"));
        m_HashTable_EventInfo.put("F",m_HO_Properties.getProperty("FriendlySpiel"));
        m_HashTable_EventInfo.put("I",m_HO_Properties.getProperty("IntFriendlySpiel"));
        m_HashTable_EventInfo.put("P",m_HO_Properties.getProperty("PokalSpiel"));
        m_HashTable_EventInfo.put("Q",m_HO_Properties.getProperty("QualifikationSpiel"));
        m_HashTable_EventInfo.put("DB",m_properties.getProperty("ttCalDB"));
        m_HashTable_EventInfo.put("FILE",m_properties.getProperty("ttCalFile"));
        
		// Anzahl der HRF-Files in der DB ermitteln
		doSelect("SELECT COUNT(*) FROM HRF");
		try
		{
			while(m_queryResult.next())
			{
				m_int_anz_DBEintraege = m_queryResult.getInt(1);
			}
		}
		catch(SQLException sexc)
		{
			//debugWindow.append("HHHHHHHHH");
			//JDialog tmp = new JDialog(m_gui.getOwner4Dialog(),"File read error");
        	//tmp.add(new JLabel("An error occured while loading language.properties. Please report to me."));
        	//tmp.show();
		}
		
		// Jahr des ersten HRF in der DB ermitteln
		doSelect("SELECT MIN(DATUM) FROM HRF");
		try
		{
			while(m_queryResult.next())
			{
				try
				{
					String jahr = (m_queryResult.getObject(1).toString()).substring(0,4);
					m_int_firstYearInDB = Integer.parseInt(jahr);
				}
				catch(Exception e)
				{
					m_int_firstYearInDB = m_int_actualYear;
				}
			}
		}
		catch(SQLException s)
		{
		}
		
		// Ausgangspfad f�r den Start des JFileChooser ermitteln
	
		m_Str_hrfPfad = null;
		
		doSelect("SELECT CONFIG_VALUE FROM USERCONFIGURATION WHERE CONFIG_KEY ='hrfImport_HRFPath'");
		try
		{
			while(m_queryResult.next())
			{
				if(m_queryResult.wasNull())
				{
					m_Str_hrfPfad = null;
				}
				else
				{
					m_Str_hrfPfad = m_queryResult.getString(1);
				}
			}
		}
		catch(SQLException sexc)
		{
			m_Str_hrfPfad = null;
		}
        
		/*
         *Erstellen der Dummy Tabelle "Importliste"
         */
		Vector importHeader = new Vector();
		Vector importValues = new Vector();
		
		importHeader.add(m_properties.getProperty("pfad"));
		
		m_TableModel_Imports = new HrfTableModel(importHeader, importValues);
		m_Table_Imports = new HrfTable(m_clModel,m_TableModel_Imports, "import");
		
        /*
         *Erstellen der Dummy Tabelle "Filelist"
         */
        m_V_Filelist_Header = new Vector();
        m_V_Filelist_Values = new Vector();
        Vector tmpV = new Vector(); 
        
        m_V_Filelist_Header.add("");
        m_V_Filelist_Header.add(m_properties.getProperty("datname"));
        m_V_Filelist_Header.add(m_HO_Properties.getProperty("Datum"));
        m_V_Filelist_Header.add(m_properties.getProperty("tag"));
        m_V_Filelist_Header.add(m_properties.getProperty("kw"));
        m_V_Filelist_Header.add(m_HO_Properties.getProperty("Season"));
        m_V_Filelist_Header.add(m_HO_Properties.getProperty("Liga"));
        m_V_Filelist_Header.add(m_HO_Properties.getProperty("Training"));
        m_V_Filelist_Header.add("%");
        m_V_Filelist_Header.add(m_properties.getProperty("indb"));
        
        m_TableModel_Filelist = new HrfTableModel(m_V_Filelist_Header, m_V_Filelist_Values);
        m_Table_Filelist = new HrfTable(m_clModel,m_TableModel_Filelist,m_intAr_col_width_Filelist, "filelist");
        m_Table_Filelist.addMouseListener(this);
        
        /*
         *Erstellen der Dummy Tabelle "Calendar"
         */
        m_V_Calendar_Header = new Vector();
        m_V_Calendar_Values = new Vector();
        
        m_V_Calendar_Header.add(m_properties.getProperty("kw"));
        m_V_Calendar_Header.add(m_properties.getProperty("monkurz"));
        m_V_Calendar_Header.add(m_properties.getProperty("diekurz"));
        m_V_Calendar_Header.add(m_properties.getProperty("mitkurz"));
        m_V_Calendar_Header.add(m_properties.getProperty("donkurz"));
        m_V_Calendar_Header.add(m_properties.getProperty("frekurz"));
        m_V_Calendar_Header.add(m_properties.getProperty("samkurz"));
        m_V_Calendar_Header.add(m_properties.getProperty("sonkurz"));
        
        m_TableModel_Calendar = new HrfTableModel(m_V_Calendar_Header, m_V_Calendar_Values);
        m_Table_Calendar = new HrfTable(m_clModel,m_TableModel_Calendar,m_intAr_col_width_Calendar, "calendar");
        m_Table_Calendar.addMouseListener(this);
        m_Table_Calendar.setIntercellSpacing(new Dimension(2,2));
        m_Table_Calendar.setRowHeight(20);
        
        /*
         *Erstellen der Dummy Tabelle "Details"
         */
        //Vorbereiten der fixen Labelbeschriftungen
        m_Ar_Detail_Label_fix = new String[13];
        m_Ar_Detail_Label_fix[0] = m_HO_Properties.getProperty("Liga");
        m_Ar_Detail_Label_fix[1] = m_HO_Properties.getProperty("Season") + " / " + m_HO_Properties.getProperty("Spieltag");
        m_Ar_Detail_Label_fix[2] = m_HO_Properties.getProperty("Punkte") + " / " + m_HO_Properties.getProperty("Tore");
        m_Ar_Detail_Label_fix[3] = m_properties.getProperty("tabplatz");
        m_Ar_Detail_Label_fix[4] = m_HO_Properties.getProperty("Training");
        m_Ar_Detail_Label_fix[5] = m_HO_Properties.getProperty("Intensitaet");
        m_Ar_Detail_Label_fix[6] = m_HO_Properties.getProperty("CoTrainer");
        m_Ar_Detail_Label_fix[7] = m_HO_Properties.getProperty("Torwarttrainer");
        m_Ar_Detail_Label_fix[8] = m_HO_Properties.getProperty("Selbstvertrauen");
        m_Ar_Detail_Label_fix[9] = m_properties.getProperty("anzspieler");
        m_Ar_Detail_Label_fix[10] = m_HO_Properties.getProperty("Stimmung");
		m_Ar_Detail_Label_fix[11] = m_properties.getProperty("lasthrf");
		m_Ar_Detail_Label_fix[12] = m_properties.getProperty("nexthrf");
		
		m_int_Hoehe_DetailPanels = m_Ar_Detail_Label_fix.length * m_int_Hoehe_Label;	// Festlege der Gesamth�he des Detailpanels
		
        m_V_Details_Header = new Vector();
        m_V_Details_Values = new Vector();
        
        m_TableModel_Details = new HrfTableModel(m_V_Details_Header, m_V_Details_Values);
        m_Table_Details = new HrfTable(m_clModel,m_TableModel_Details,m_intAr_col_width_Details, "details");
        m_Table_Details.setRowMargin(-10);
        m_Table_Details.addMouseListener(this);
        m_Table_Details.getColumnModel().addColumnModelListener(this);
        
        // Erstellen des Panels f�r die 1. Spalte der Detailtabelle
        HrfPanel fixedPanel = new HrfPanel(m_int_Breite_Detail_Fixed,m_int_Hoehe_DetailPanels);
        fixedPanel.setLayout(new GridLayout(m_Ar_Detail_Label_fix.length,1));
        fixedPanel.addMouseListener(this);
        
        for(int ii = 0; ii < m_Ar_Detail_Label_fix.length; ii++)
        {
        	if(ii == 0 || ii%2 == 0)
        	{
        		m_LineColor = hellblau;
        	}
        	else
        	{
        		m_LineColor = dunkelblau;
        	}
        	fixedPanel.add(new HrfLabel((m_Ar_Detail_Label_fix[ii] + " :"),m_int_Breite_Detail_Fixed,m_int_Hoehe_Label,JLabel.RIGHT,m_LineColor));
        }
        
        HrfPanel emptyPanel = new HrfPanel(m_int_Breite_Detail_Fixed,m_int_Hoehe_Label + 4);
    	HrfLabel emptyLabel = new HrfLabel("",m_int_Breite_Detail_Fixed,m_int_Hoehe_Label);
    	emptyPanel.add(emptyLabel);
    	
        Vector fixedColumn = new Vector();
        fixedColumn.add(emptyPanel);
        fixedColumn.add(fixedPanel);
        
        // Die 1. Spalte zu der Tabelle hinzuf�gen
        m_TableModel_Details.addColumn(" ",fixedColumn);
        m_Table_Details.setDefaultRenderer(JPanel.class, m_renderer );
    	m_Table_Details.setRowHeight(1,m_int_Hoehe_DetailPanels);
    	m_Table_Details.setRowHeight(0,m_int_Hoehe_Label + 5);
    	TableColumn colFixed = m_Table_Details.getColumnModel().getColumn(0);
    	colFixed.setPreferredWidth(m_int_Breite_Detail_Fixed);
    	
    	/*
         *Erstellen der Tabelle "Explain"
         */
    	/*Vector explainCols = new Vector();
        Vector explainRows = new Vector();
        
        explainCols.add("");
        explainCols.add("");
        explainCols.add("");
        explainCols.add("");
        explainCols.add("");
        explainCols.add("");
        
        m_TableModel_Explain = new HrfTableModel(explainCols, explainRows);
        m_Table_Explain = new HrfTable(m_clModel,m_TableModel_Explain,m_intAr_col_width_Explain, "explain");
        
        Vector row1 = new Vector();
        row1.add("");
        row1.add(m_helper.getImageIcon4Spieltyp(IMatchLineup.TESTSPIEL));
        row1.add("");
        row1.add(m_helper.getImageIcon4Spieltyp(IMatchLineup.INT_TESTSPIEL));
        row1.add("");
        row1.add(m_helper.getImageIcon4Spieltyp(IMatchLineup.LIGASPIEL));
        Vector row2 = new Vector();
        row2.add("");
        row2.add(m_helper.getImageIcon4Spieltyp(IMatchLineup.POKALSPIEL));
        row2.add("");
        row2.add(m_helper.getImageIcon4Spieltyp(IMatchLineup.QUALISPIEL));
        row2.add("");
        row2.add(m_helper.getImageIcon4Spieltyp(IMatchLineup.LAENDERSPIEL));
        Vector row3 = new Vector();
        row3.add("");
        row3.add(m_properties.getProperty("indb"));
        row3.add("");
        row3.add("");
        row3.add("");
        row3.add(m_HO_Properties.getProperty("Datei"));
        
        m_TableModel_Explain.addRow(row1);
        m_TableModel_Explain.addRow(row2);
        m_TableModel_Explain.addRow(row3);*/
        
        //m_Table_Explain.revalidate();
        
    	Border kante = BorderFactory.createBevelBorder(BevelBorder.RAISED,hellblau,dunkelblau);
        /*****************
         *Erstellen der Buttons
         *****************/
        m_Button_load_file = new JButton(m_properties.getProperty("btLoadFile"));
        m_Button_load_file.setToolTipText(m_properties.getProperty("ttLoadFile"));
        m_Button_load_file.addActionListener(this);
        
        m_Button_delete_db = new JButton(m_properties.getProperty("btDeleteDB"));
        m_Button_delete_db.setToolTipText(m_properties.getProperty("ttDeleteDB"));
        m_Button_delete_db.addActionListener(this);
        
        m_Button_delete_file = new JButton(m_properties.getProperty("btDeleteFile"));
        m_Button_delete_file.setToolTipText(m_properties.getProperty("ttDeleteFile"));
        m_Button_delete_file.addActionListener(this);
        
        m_Button_ImportList = new JButton(m_properties.getProperty("btImport"));
        m_Button_ImportList.setToolTipText(m_properties.getProperty("ttImport"));
        m_Button_ImportList.addActionListener(this);
        
        m_Button_Select_All = new JButton(m_properties.getProperty("btSelect"));
        m_Button_Select_All.setToolTipText(m_properties.getProperty("ttSelect"));
        m_Button_Select_All.addActionListener(this);
        
        m_Button_reset = new JButton(m_properties.getProperty("btReset"));
        m_Button_reset.setToolTipText(m_properties.getProperty("ttReset"));
        m_Button_reset.addActionListener(this);
        
        m_Button_help = new JButton(m_properties.getProperty("btHelp"));
        m_Button_help.setToolTipText(m_properties.getProperty("ttHelp"));
        m_Button_help.addActionListener(this);
        
        m_Button_Delete_Row = new JButton(m_properties.getProperty("btRemove"));
        m_Button_Delete_Row.setToolTipText(m_properties.getProperty("ttRemove"));
        m_Button_Delete_Row.addActionListener(this);
        
        m_Button_GoTo = new JButton(m_properties.getProperty("btGoto"));
        m_Button_GoTo.setToolTipText(m_properties.getProperty("ttGoto"));
        m_Button_GoTo.setBackground(hellblau);
        m_Button_GoTo.addActionListener(this);
        
        m_Button_Month_Forward = new JButton();
        Image tmp_bild_left = m_helper.makeColorTransparent(new ImageIcon("hoplugins/hrfExplorer/pics/arRight.gif").getImage(),gruen);
        m_Button_Month_Forward.setIcon(new ImageIcon(tmp_bild_left));
        m_Button_Month_Forward.addActionListener(this);
        
        m_Button_Month_Back = new JButton();
        Image tmp_bild_right = m_helper.makeColorTransparent(new ImageIcon("hoplugins/hrfExplorer/pics/arLeft.gif").getImage(),gruen);
        m_Button_Month_Back.setIcon(new ImageIcon(tmp_bild_right));
        m_Button_Month_Back.addActionListener(this);
        
        m_Button_ResetImports = new JButton(m_properties.getProperty("btImports"));
        m_Button_ResetImports.setToolTipText(m_properties.getProperty("ttImports"));
        m_Button_ResetImports.addActionListener(this);
        
        /*****************
         * Erstellen der Labels
         *****************/
        m_Label_Monat = new JLabel("");
        m_Label_Monat.setFont(new Font("Verdana",Font.BOLD,10));
        m_Label_Monat.setBackground(gruen);
        m_Label_DetailHeader = new JLabel("");
        
        /*****************
         *Erstellen der ComboBoxen
         *****************/
        m_CB_year = new JComboBox();
        
        for(int ii = m_int_firstYearInDB; ii <= m_int_actualYear; ii++)
        {
        	m_CB_year.addItem("" + ii);
        }
        m_CB_year.setSelectedIndex(m_CB_year.getItemCount()-1);
        m_CB_year.setBackground(hellblau);
        m_CB_year.addItemListener(this);
        
        m_V_months = new Vector();
        setMonate();
         
        m_CB_month = new JComboBox(m_V_months);
        m_CB_month.addItemListener(this);
        m_CB_month.setSelectedIndex(m_int_selectedMonth);
        m_CB_month.setBackground(hellblau);
        
        /*****************
         * *******************
         * Erstellen des GUI
         * *******************
         *****************/

        /*****************
         * Erstellen des Hauptpanels
         *****************/
        m_Panel_main = hOMiniModel.getGUI().createImagePanel ();
        m_Panel_main.setLayout(new BorderLayout());
        
        /*****************
         * Erstellen des Calendar Bereichs
         *****************/
        m_Panel_Calendar_main = new JPanel(new BorderLayout());
        m_Panel_Calendar_main_north = new JPanel(new GridLayout(2,3));
        
        m_ScrollPane_Calendar = new JScrollPane(m_Table_Calendar);
        m_Panel_Calendar_main_north.add(m_CB_month);
        m_Panel_Calendar_main_north.add(m_CB_year);
        m_Panel_Calendar_main_north.add(m_Button_GoTo);
        m_Panel_Calendar_main_north.add(m_Button_Month_Back);
        m_Panel_Calendar_main_north.add(m_Label_Monat);
        m_Panel_Calendar_main_north.add(m_Button_Month_Forward);
        
        m_Panel_Calendar_main.add(m_Panel_Calendar_main_north,BorderLayout.NORTH);
        m_Panel_Calendar_main.add(m_ScrollPane_Calendar,BorderLayout.CENTER);
        
        /*****************
         * Erstellen des FileTable Bereichs
         *****************/
        m_Panel_FileTable_main = new JPanel(new BorderLayout());
        m_Panel_FileTable_main_north = new JPanel(new GridLayout(2,8));
        
        m_ScrollPane_FileTable = new JScrollPane(m_Table_Filelist);
        
        m_Panel_FileTable_main_north.add(m_Button_load_file);
        m_Panel_FileTable_main_north.add(m_Button_delete_file);
        m_Panel_FileTable_main_north.add(m_Button_ImportList);
        m_Panel_FileTable_main_north.add(m_Button_reset);
        m_Panel_FileTable_main_north.add(m_Button_delete_db);
        m_Panel_FileTable_main_north.add(m_Button_Delete_Row);
        m_Panel_FileTable_main_north.add(m_Button_Select_All);
        m_Panel_FileTable_main_north.add(m_Button_help);
        
        m_Panel_FileTable_main.add(m_Panel_FileTable_main_north,BorderLayout.NORTH);
        m_Panel_FileTable_main.add(m_ScrollPane_FileTable,BorderLayout.CENTER);
        
        /*****************
         * Erstellen des Detail Bereichs
         *****************/
        m_Panel_Details_main = new JPanel(new BorderLayout());
        m_Panel_Details_north = new JPanel(new BorderLayout());
        
        m_ScrollPane_Details = new JScrollPane(m_Table_Details);
        
        m_Panel_Details_main.add(m_Panel_Details_north,BorderLayout.NORTH);
        m_Panel_Details_main.add(m_ScrollPane_Details,BorderLayout.CENTER);
        m_Panel_Details_north.add(m_Label_DetailHeader,BorderLayout.NORTH);
        
        /*****************
         * Erstellen des Import Bereichs
         *****************/
        m_Panel_Imports_main = new JPanel(new BorderLayout());
        
        m_ScrollPane_Imports = new JScrollPane(m_Table_Imports);
        
        m_Panel_Imports_main.add(m_Button_ResetImports,BorderLayout.NORTH);
        m_Panel_Imports_main.add(m_ScrollPane_Imports,BorderLayout.CENTER);
        
        /*****************
         * Erstellen der SplitPanes 
         ******************/
        m_SplitPane_top_left = new JSplitPane(JSplitPane.VERTICAL_SPLIT,m_Panel_Calendar_main,m_Panel_Imports_main);
        m_SplitPane_top = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,m_SplitPane_top_left,m_Panel_FileTable_main);
		m_SplitPane_main = new JSplitPane(JSplitPane.VERTICAL_SPLIT,m_SplitPane_top,m_Panel_Details_main);
        m_SplitPane_main.setDividerLocation(400);
        m_SplitPane_top.setDividerLocation(340);
        m_SplitPane_top_left.setDividerLocation(220);
        
        m_Panel_main.add(m_SplitPane_main,BorderLayout.CENTER);
        
        createCalendarTable(m_int_selectedMonth, m_int_selectedYear);
        
        //debugWindow.setVisible ( true );
        
		m_clModel.getGUI().addTab(m_TabName,m_Panel_main);
		
		m_gui.registerRefreshable(ref);
	}
	
	// *********** Beginn allgemeiner Methoden *********************

	/******************
	 * Liefert ein ImageIcon aus HO zu einer Konstante
	 * @param bild Kurzbezeichnung f�r das gew�nschte Bild
	 * @return Gibt ein ImageIcon zur�ck
	 ******************/
	/*public static ImageIcon getBild(String bild)
	{
		ImageIcon img = m_helper.getImageIcon4Spieltyp(IMatchLineup.LIGASPIEL);
		
		return img;
	}*/
	
	/******************
	 * Schreibt die Monatsnamen in der gew�hlten Sprache in den Vector m_V_months
	 ******************/
	public void setMonate()
	{
		m_V_months.add(m_properties.getProperty("jan"));
		m_V_months.add(m_properties.getProperty("feb"));
		m_V_months.add(m_properties.getProperty("mar"));
		m_V_months.add(m_properties.getProperty("apr"));
		m_V_months.add(m_properties.getProperty("may"));
		m_V_months.add(m_properties.getProperty("jun"));
		m_V_months.add(m_properties.getProperty("jul"));
		m_V_months.add(m_properties.getProperty("aug"));
		m_V_months.add(m_properties.getProperty("sep"));
		m_V_months.add(m_properties.getProperty("oct"));
		m_V_months.add(m_properties.getProperty("nov"));
		m_V_months.add(m_properties.getProperty("dec"));
	}
	
	/******************
	 * Gibt einen Vector zur�ck, der alle Monatsnamen enth�lt
	 * @return Gibt den Vector m_V_months zur�ck
	 ******************/
	public static Vector getMonate()
	{
		return m_V_months;
	}
	
	/******************
	 * Schreibt die Tagesnamen in der gew�hlten Sprache in das Array m_Ar_days
	 ******************/
	public void setTage()
	{
		m_Ar_days[0] = m_properties.getProperty("mon");
		m_Ar_days[1] = m_properties.getProperty("die");
		m_Ar_days[2] = m_properties.getProperty("mit");
		m_Ar_days[3] = m_properties.getProperty("don");
		m_Ar_days[4] = m_properties.getProperty("fre");
		m_Ar_days[5] = m_properties.getProperty("sam");
		m_Ar_days[6] = m_properties.getProperty("son");
	}
	
	/******************
	 * Gibt ein Array zur�ck, das alle Tage als Namen enth�lt
	 * @return Gibt das Array m_Ar_days zur�ck
	 ******************/
	public static String[] getTage()
	{
		return m_Ar_days;
	}
	
	/******************
	 * Erstellt die Calendar-Tabelle
	 * @param monat Der Monat f�r den die Tabelle erstellt wird
	 * @param jahr Das Jahr f�r das die Tabelle erstellt wird
     ******************/
	public void createCalendarTable(int monat, int jahr)
	{
		String monat_Start;
		String jahr_Start;
		String monat_Ende;
		String jahr_Ende;
		
		//Monat um 1 heraufz�hlen, weil Monatsz�hlung bei 0 anf�ngt :-(
		monat++;
		if(monat < 10)
		{
			monat_Start = "0" + monat;
			jahr_Start = "" + jahr;
		}
		else
		{
			monat_Start = "" + monat;
			jahr_Start = "" + jahr;
		}
		if(monat + 1 < 10)
		{
			monat_Ende = "0" + (monat + 1);
			jahr_Ende = "" + jahr;
		}
		else if(monat +1 > 12)
		{
			monat_Ende = "01";
			jahr_Ende = "" + (jahr + 1);
		}
		else
		{
			monat_Ende = "" + (monat + 1);
			jahr_Ende = "" + jahr;
		}
		
		// Holen der HRF-ID und des Datums der Eintr�ge, die in dem gew�hlten Monat liegen
		doSelect("SELECT DATUM,HRF_ID FROM HRF where DATUM between '" + jahr_Start + "-" + monat_Start + "-01' and '" + jahr_Ende + "-" + monat_Ende + "-01'");
		//********************************************************************************************
		//Leeren der Hashtables
		m_HashTable_DayInDB.clear();
		m_HashTable_isEvent.clear();
		
		/*Erstmal die Vorarbeiten:
		 * alle ben�tigten Werte ermitteln,
		 * Label erstellen
		 */
		int akt_Monat = m_int_selectedMonth;
		int back_Monat;
		int fw_Monat;
		int anzRows = m_TableModel_Calendar.getRowCount();
		int anzCols = m_TableModel_Calendar.getColumnCount();
		if(m_int_selectedMonth - 1 < 0)
		{
			back_Monat = 11;
		}
		else
		{
			back_Monat = m_int_selectedMonth - 1;
		}
		if(m_int_selectedMonth + 1 > 11)
		{
			fw_Monat = 0;
		}
		else
		{
			fw_Monat = m_int_selectedMonth + 1;
		}
		m_Label_Monat.setText(m_V_months.get(m_int_selectedMonth).toString() + " " + m_int_selectedYear);
		m_Label_Monat.setHorizontalAlignment(JLabel.CENTER);
		m_Label_Monat.setBackground(gruen);
		
		GregorianCalendar gc = new GregorianCalendar(m_int_selectedYear,m_int_selectedMonth,1);
		int last_day = gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		
		/*
		 * Alle Werte aus dem SELECT lesen und in die Hashtable schreiben
		 */
		try
		{
			while(m_queryResult.next())
			{
				m_queryResult.getObject(1);
				
				if( m_queryResult.wasNull())
				{
					//debugWindow.append("Select war null");
				}
				else
				{
					Timestamp datum = m_queryResult.getTimestamp("DATUM");
					int id = m_queryResult.getInt("HRF_ID");
					int tag = Integer.parseInt((datum.toString()).substring(8,10));
					String strDatum = datum.toString().substring(0,19);
					if(m_HashTable_DayInDB.containsKey(new Integer(tag)))
					{
						Hashtable tmp = (Hashtable)m_HashTable_DayInDB.get(new Integer(tag));
						tmp.put(new Integer(id),strDatum);
						m_HashTable_DayInDB.put(new Integer(tag),tmp);
					}
					else
					{
						Hashtable tmp = new Hashtable();
						tmp.put(new Integer(id),strDatum);
						m_HashTable_DayInDB.put(new Integer(tag),tmp);
					}
				}
			}
		}
		catch(SQLException sexc)
		{
			//debugWindow.append("" + sexc);
		}
		
		/*
		 * Alle Spiele und Trainings f�r den gew�hlten Monat herausfinden
		 */
		GregorianCalendar cStart = new GregorianCalendar(Integer.parseInt(jahr_Start),Integer.parseInt(monat_Start)-1,1);
		Timestamp tsStart = new Timestamp(cStart.getTimeInMillis());
		GregorianCalendar cStop = new GregorianCalendar(Integer.parseInt(jahr_Ende),Integer.parseInt(monat_Ende)-1,1);
		Timestamp tsStop = new Timestamp(cStop.getTimeInMillis());
		doSelect("SELECT SPIELDATUM,MATCHID FROM MATCHDETAILS WHERE SPIELDATUM between '" + tsStart + "' AND '" + tsStop + "' AND ( GASTID = '" + m_TeamID + "' OR HEIMID = '" + m_TeamID + "' )");
		
		try
		{
			while(m_queryResult.next())
			{
				m_queryResult.getObject(1);
				
				if( m_queryResult.wasNull())
				{
					//debugWindow.append("Select war null");
				}
				else
				{
					Timestamp datum = m_queryResult.getTimestamp("SPIELDATUM");
					int match_id = m_queryResult.getInt("MATCHID");
					int tag = Integer.parseInt((datum.toString()).substring(8,10));
					String strDatum = datum.toString().substring(0,19);
					int matchTyp = ((Integer)m_HashTable_MatchTyp.get(new Integer(match_id))).intValue();

					if(matchTyp == IMatchLineup.LIGASPIEL)
					{
						m_HashTable_isEvent.put(new Integer(tag),"L");
					}
					else if(matchTyp == IMatchLineup.POKALSPIEL)
					{
						m_HashTable_isEvent.put(new Integer(tag),"P");
					}
					else if(matchTyp == IMatchLineup.TESTSPIEL || matchTyp == IMatchLineup.TESTPOKALSPIEL)
					{
						m_HashTable_isEvent.put(new Integer(tag),"F");
					}
					else if(matchTyp == IMatchLineup.INT_TESTSPIEL || matchTyp == IMatchLineup.INT_TESTCUPSPIEL)
					{
						m_HashTable_isEvent.put(new Integer(tag),"I");
					}
					else if(matchTyp == IMatchLineup.QUALISPIEL)
					{
						m_HashTable_isEvent.put(new Integer(tag),"Q");
					}
					m_kurzInfo = null;
				}
			}
		}
		catch(SQLException sexc)
		{
			//debugWindow.append("" + sexc);
		}
		/*
		 * So, jetzt beginnt der Aufbau der neuen Tabelle...
		 */
		m_TableModel_Calendar.removeAllRows();
		
		
		int tag_der_woche;
		int actual_day = 1;
		int tmp_kw = 0;
		
		while(actual_day <= last_day)
		{
			Vector tmp = new Vector();
			GregorianCalendar gc_tmp = new GregorianCalendar(m_int_selectedYear,m_int_selectedMonth,actual_day);
			tmp_kw = gc_tmp.get(GregorianCalendar.WEEK_OF_YEAR);
			tmp.add(" " + tmp_kw);
			for(int ii = 1; ii <= 7; ii++)
			{
				if((gc_tmp.get(GregorianCalendar.DAY_OF_WEEK) -1) > 0)
				{
					tag_der_woche = gc_tmp.get(GregorianCalendar.DAY_OF_WEEK) - 1;
				}
				else
				{
					tag_der_woche = 7;
				}
	
				if(ii < tag_der_woche && actual_day < 7)
				{
					tmp.add(" ");
				}
				else if(actual_day <= last_day)
				{
					tmp.add("" + actual_day);
					actual_day++;
				}
				else
				{
					tmp.add(" ");
				}
			}
			m_TableModel_Calendar.addRow(tmp);
		}
		m_TableModel_Calendar.fireTableDataChanged();
		m_ScrollPane_Calendar.doLayout();
	}
	
	/******************
	 * Erstellt die Detail-Tabelle
	 * @param hashwert Dieser Parameter ist der Key, mit dem das HrfDetails-Object aus der Hashtable m_HashTable_Details geholt wird
     ******************/
	public void createDetailTable(String hashwert)
	{
		HrfDetails selectedObject = (HrfDetails)m_HashTable_Details.get((String)hashwert);
    	if(m_HashTable_Details_ColHeader.containsKey(selectedObject.getStr_Datum()) == false)
    	{
    		m_HashTable_Details_ColHeader.put(selectedObject.getStr_Datum(),selectedObject);
			//erstellen der Detailtabelle
	    	//Label der �berschrift f�llen
	    	m_Label_DetailHeader.setText(selectedObject.getTeamName() + " (" + selectedObject.getTeamID() + ")");
	    	// Panel f�r die Detail-ScrollPane erstellen
//	    	 Array f�r die nicht fixen Details
	    	String[] objectDetails = {
	    			" " + selectedObject.getLiga(),
	    			" " + selectedObject.getSaison() + " / " + selectedObject.getSpieltag(),
	    			" " + selectedObject.getPunkte() + " / " + selectedObject.getToreFuer() + ":" + selectedObject.getToreGegen(),
	    			" " + selectedObject.getPlatz(),
	    			" " + selectedObject.getTrArt(),
	    			" " + selectedObject.getTrInt() + "%",
	    			" " + selectedObject.getAnzCoTrainer(),
	    			" " + selectedObject.getAnzTwTrainer(),
	    			" " + selectedObject.getSelbstvertrauen(),
	    			" " + selectedObject.getAnzSpieler(),
	    			" " + selectedObject.getStimmung(),
	    			" " + selectedObject.getStr_DatumVorher(),
	    			" " + selectedObject.getStr_DatumDanach()
	    	};
	    	HrfPanel teamDetails = new HrfPanel(m_int_Breite_Detail_Var,m_int_Hoehe_DetailPanels);
	    	teamDetails.setLayout(new GridLayout(objectDetails.length,1));
	    	String columnHeader = selectedObject.getStr_Datum();
	    	
	    	// Labels f�r das DetailPanel erstellen und einf�gen
	    	for(int ii = 0; ii < objectDetails.length; ii++)
	    	{
	    		if(ii == 0 || ii%2 == 0)
	        	{
	        		m_LineColor = hellblau;
	        	}
	        	else
	        	{
	        		m_LineColor = dunkelblau;
	        	}
	    		teamDetails.add(new HrfLabel(objectDetails[ii],m_int_Breite_Detail_Var,m_int_Hoehe_Label,JLabel.LEFT,m_LineColor));
	    	}
	    	// Entfernen-Panel und -Label erstellen
	    	HrfPanel entfernen = new HrfPanel(m_int_Breite_Detail_Var,m_int_Hoehe_Label + 5,rot);
	    	entfernen.setLayout(new GridLayout(1,1));
	    	HrfLabel remove = new HrfLabel(m_properties.getProperty("entf"),m_int_Breite_Detail_Var,m_int_Hoehe_Label,JLabel.CENTER,rot);
	    	remove.getInsets();
	    	entfernen.add(remove);
	    	
	    	//Vector f�r die Objekte in der Detailtabelle
	    	Vector details = new Vector();
	    	details.add(entfernen);
	    	details.add(teamDetails);
	    	
	    	m_TableModel_Details.addColumn(columnHeader,details);
	    	m_HashTable_Columns.put(columnHeader,details);
	    	
	    	int anzCols = m_TableModel_Details.getColumnCount();
	    	setDetailTableSize(anzCols);
    	}
	}

	/******************
	 * Entfernt ein Panel aus der Detailtabelle und baut diese anschliessend neu auf
     * @param colKey Name der Spalte, die entfernt werden soll
	 ******************/
	public void rebuildDetailTable(String colKey)
	{
		int anzCols = 1;
		m_TableModel_Details.setColumnCount(1);
		m_Table_Details.revalidate();
		if(colKey.equals("alle"))
		{
			m_HashTable_Columns.clear();
		}
		else
		{
			m_HashTable_Columns.remove((String)colKey);
			
			Enumeration enu = m_HashTable_Columns.keys();
			Set keys = m_HashTable_Columns.keySet();
			int menge = keys.size();
			Object[] schluessel = keys.toArray();
			int counter = 0;
			while(enu.hasMoreElements())
			{
				enu.nextElement();
				String keyString = schluessel[counter].toString();
				Vector details = (Vector)m_HashTable_Columns.get((String)keyString);
				m_TableModel_Details.addColumn(keyString,details);
		    	counter ++;
			}
			anzCols = m_TableModel_Details.getColumnCount();
		}
		setDetailTableSize(anzCols);
	}

	/******************
	 * Setzt die Breiten und H�hen f�r die Detail-Tabelle
	 * @param anzColumns Anzahl der Spalten in der Detailtabelle
	 ******************/
	public void setDetailTableSize(int anzColumns)
	{
		for(int ii = 0; ii < anzColumns; ii++)
    	{
    		TableColumn col = m_Table_Details.getColumnModel().getColumn(ii);
    		int breite;
    		if(ii == 0)
    		{
    			breite = m_int_Breite_Detail_Fixed;
    		}
    		else
    		{
    			breite = m_int_Breite_Detail_Var;
    		}
    		col.setResizable(false);
	    	col.setPreferredWidth( breite );
	    	col.setMinWidth(breite);
    	}
		m_Table_Details.setDefaultRenderer(JPanel.class, m_renderer );
    	m_Table_Details.setRowHeight(1,m_int_Hoehe_DetailPanels);
    	m_Table_Details.setRowHeight(0,m_int_Hoehe_Label + 4);
	}
	
	/******************
	 * Liefert ein boolean Ergebnis, ob f�r einen Tag ein HRF-File in der DB ist und wertet dabei die Hashtable aus
     * @param tag Der Tag, der gepr�ft werden soll
     * @return Ein boolean-Wert, der anzeigt, ob f�r den �bergebenen Tag ein DB-Eintrag existiert
	 ******************/
	public static boolean hrfForDay(int tag)
	{
		boolean return_value = false;
		if(m_HashTable_DayInDB.containsKey(new Integer(tag)))
		{
			return_value = true;
		}
		return return_value;
	}
	
	/******************
	 * Liefert ein boolean Ergebnis, ob an einem Tag ein Spiel oder Training stattgefunden hat
     * @param tag Der Tag, der gepr�ft werden soll
     * @return Ein boolean-Wert, der anzeigt, ob an dem �bergebenen Tag ein SpecialEvent (z.B. ein Spiel) stattgefunden hat.
	 ******************/
	public static boolean isSpecialEvent(int tag)
	{
		boolean return_value = false;
		if(m_HashTable_isEvent.containsKey(new Integer(tag)))
		{
			return_value = true;
		}
		return return_value;
	}
	
	/******************
	 * Liefert den Wert des SpecialEvents
     * @param tag Der Tag, f�r den das SpecialEvent geliefert werden soll
     * @return Liefert die Art des SpecialEvents f�r den �bergebenen Tag als String.
	 ******************/
	public static String getSpecialEvent(int tag)
	{
		return m_HashTable_isEvent.get(new Integer(tag)).toString();
	}
	
	/******************
	 * Liefert den Wert des SpecialEvents
     * @param tag Der Tag, f�r den das SpecialEvent geliefert werden soll
     * @return Liefert die Art des SpecialEvents f�r den �bergebenen Tag als String.
	 ******************/
	public static String getNameForEvent(String event)
	{
		String eventName = (m_HashTable_EventInfo.get(event)).toString();
		return eventName;
	}
	
	/******************
	 * Liefert die HRF-ID, die f�r den �bergebenen Tag in der DB steht
     * @param tag Der Tag, f�r den die ID geholt werden soll
	 ******************/
	/*public int getHrfID(int tag)
	{
		int return_value = 0;
		if(m_HashTable_DayInDB.containsKey(new Integer(tag)))
		{
			Object id = m_HashTable_DayInDB.get(new Integer(tag));
			return_value = new Integer(tag).intValue();
		}
		return return_value;
	}*/
	
	
	/******************
	 * Pr�ft, ob ein File f�r ein Datum eines Monats geladen ist
	 * @param tag Tag des Monats der gepr�ft werden soll
	 * @return Ein boolean-Wert, der anzeigt, ob eine gew�hlte Datei bereits in der Datenbank existiert.
	 ******************/
	public static boolean hrfAsFile(String tag)
	{
		String monat = "" + (m_int_selectedMonth + 1);
		String jahr = "" + m_int_selectedYear;
		boolean inDB = false;
		if(m_int_selectedMonth < 11 || Integer.parseInt(tag) < 10)
		{
			monat = "0" + monat;
		}
		if(Integer.parseInt(tag) < 10)
		{
			tag = "0" + tag;
		}
		String datum = jahr + "-" + monat + "-" + tag;
		if(m_HashTable_DatumKey.containsKey((String)datum))
		{
			inDB = true;
		}
		return inDB;
	}
	
	// *********** Ende allgemeiner Methoden *********************
	
	// *********** Beginn der Listener-Methoden *******************
	/*****************
     * Methode f�r die Behandlung von Mausklicks auf einen Button
     * @param e Wertet den Klick auf Buttons aus
     *****************/
	public void actionPerformed(ActionEvent e)
    {
		/*
		 * Button LoadFile
		 */ 
		if(e.getSource().equals(m_Button_load_file))
		{
			m_FileChooser_chooser = new JFileChooser();
			m_FileChooser_chooser.setMultiSelectionEnabled(true);
			m_FileChooser_chooser.setFileFilter(new HrfFilter());
			m_FileChooser_chooser.setCurrentDirectory(new File(m_Str_hrfPfad));
			
			int state = m_FileChooser_chooser.showOpenDialog(null);
			m_files = m_FileChooser_chooser.getSelectedFiles();
			
			if(m_files != null && state == JFileChooser.APPROVE_OPTION)
			{
				int anzFiles = m_files.length;
				String tmp_Datum = "";
				String tmp_Pfad = "";
				for(int ii = 0; ii < anzFiles; ii++)
				{
					HrfFileDetails tmp = new HrfFileDetails(m_files[ii].getPath(),m_clModel);
					tmp_Datum = tmp.getStr_Datum().substring(0,10);
					tmp_Pfad = tmp.getPfad();
					if(m_V_Filelist_Keys.contains(tmp_Pfad) == false)
					{
						m_TableModel_Filelist.addRow(tmp.getDatenVector());
						m_HashTable_Details.put(tmp_Pfad,tmp);
						m_HashTable_DatumKey.put(tmp_Datum,tmp_Pfad);
						m_V_Filelist_Keys.add(tmp_Pfad);
						if(Integer.parseInt(tmp_Datum.substring(5,7)) == m_int_selectedMonth + 1)
						{
							createCalendarTable(m_int_selectedMonth , m_int_selectedYear);
						}
						m_Table_Filelist.revalidate();
						m_Table_Filelist.repaint();
					}
				}
			}
		}
		/*
		 * Button ImportList
		 */
		else if(e.getSource().equals(m_Button_ImportList))
		{
			int anzRows = m_TableModel_Filelist.getRowCount();
			if(anzRows > 0)
			{
				for(int ii = 0;ii < anzRows; ii++)
				{
					Vector tmpV = (Vector)(m_TableModel_Filelist.getDataVector()).elementAt(ii);
					String dateiPfad = tmpV.elementAt(tmpV.size()-1).toString();
					//String dateiPfad = m_TableModel_Filelist.getValueAt(ii,m_TableModel_Filelist.getColumnCount()).toString();
					if(((Boolean)m_TableModel_Filelist.getValueAt(ii,0)).booleanValue() == true
							&& m_HashTable_DatumKey.containsValue(dateiPfad)
							&& m_HashTable_Import.containsKey((String)dateiPfad) == false)	
					{
						Vector tmp = new Vector();
						tmp.add(dateiPfad);
						//tmp.add(m_TableModel_Filelist.getValueAt(ii,m_TableModel_Filelist.getColumnCount()).toString());
						m_HashTable_Import.put(dateiPfad,Boolean.FALSE);
						m_TableModel_Imports.addRow(tmp);
					}
				}
				m_Table_Imports.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
				m_Table_Imports.revalidate();
				m_Table_Imports.repaint();
			}
		}
		/*
		 * Button DeleteRow
		 */
		else if(e.getSource().equals(m_Button_Delete_Row))
		{
			int anzRows = m_TableModel_Filelist.getRowCount();
			
			for(int ii = 0; ii < anzRows; ii++)
			{
				if(((Boolean)m_TableModel_Filelist.getValueAt(ii,0)).booleanValue() == true)
				{
					Vector tmpV = (Vector)(m_TableModel_Filelist.getDataVector()).elementAt(ii);
					String delete_key = tmpV.elementAt(tmpV.size()-1).toString();
					//String delete_key = "" + m_TableModel_Filelist.getValueAt(ii,m_TableModel_Filelist.getDataVector().capacity());
					String rem_DatumKey = ((HrfDetails)m_HashTable_Details.get(delete_key)).getStr_Datum();
					String rem_DatumKeyJahr = rem_DatumKey.substring(0,10);
					m_HashTable_Details.remove(delete_key);
					m_HashTable_DatumKey.remove(rem_DatumKeyJahr);
					m_HashTable_Details_ColHeader.remove(rem_DatumKey);
					m_V_Filelist_Keys.remove(delete_key);
					//m_V_Filelist_Keys.remove(m_TableModel_Filelist.getValueAt(ii,m_TableModel_Filelist.getDataVector().capacity()));
					rebuildDetailTable(rem_DatumKey);
					m_TableModel_Filelist.removeRow(ii);
					ii--;
					anzRows = m_TableModel_Filelist.getRowCount();
				}
			}
			createCalendarTable(m_int_selectedMonth , m_int_selectedYear);
		}
		/*
		 * Button DeleteFile
		 */
		else if(e.getSource().equals(m_Button_delete_file))
		{
			//Ein oder mehrere ausgew�hlte Files werden physikalisch von der Festplatte entfernt
			int anzRows = m_TableModel_Filelist.getRowCount();
			//int anzCols = m_TableModel_Filelist.getDataVector().capacity();
			
			for(int i = 0; i < anzRows; i++)
        	{
        		if(((Boolean)m_TableModel_Filelist.getValueAt(i,0)).booleanValue() == true && m_TableModel_Filelist.getValueAt(i,1).equals("---") == false)
        		{
        			Vector tmpV = (Vector)(m_TableModel_Filelist.getDataVector()).elementAt(i);
        			String deletePath = tmpV.elementAt(tmpV.size()-1).toString();
        			//String deletePath = "" + m_TableModel_Filelist.getValueAt(i,m_TableModel_Filelist.getDataVector().capacity());
        			String rem_DatumKey = ((HrfFileDetails)m_HashTable_Details.get(deletePath)).getStr_Datum().substring(0,10);
					
        			File tmp_File = new File(deletePath);
        			
        			int option = JOptionPane.showConfirmDialog(null,m_properties.getProperty("deletefile") + "\n" + deletePath,m_properties.getProperty("delFile"),JOptionPane.YES_NO_OPTION);
        			if(option == 0)
        			{
        				//L�schen der Datei von der Platte
        				tmp_File.delete();
        				//L�schen der Datei aus der Hashtable Details
        				m_HashTable_Details.remove(deletePath);
        				m_HashTable_DatumKey.remove(rem_DatumKey);
        				m_TableModel_Filelist.removeRow(i);
        				anzRows = m_TableModel_Filelist.getRowCount();
        				i--;
        			}
        		}
        	}
			createCalendarTable(m_int_selectedMonth , m_int_selectedYear);
		}
		/*
		 * Button Delete_db
		 */
		else if(e.getSource().equals(m_Button_delete_db))
		{
			// L�schen aller Informationen aus der DB die zu den ausgew�hlten Files geh�ren
			int anzRows = m_TableModel_Filelist.getRowCount();
			//int anzCols = m_TableModel_Filelist.getDataVector().capacity();
			for(int i = 0; i < anzRows; i++)
        	{
        		if(((Boolean)m_TableModel_Filelist.getValueAt(i,0)).booleanValue() == true
        				&& m_TableModel_Filelist.getValueAt(i,1).equals("---") == true)
        		{
        			//Holen der HRF_ID
        			Vector tmpV = (Vector)(m_TableModel_Filelist.getDataVector()).elementAt(i);
        			int deleteHRF_ID = ((Integer)tmpV.elementAt(tmpV.size()-1)).intValue();
        			//int deleteHRF_ID = ((Integer)(m_TableModel_Filelist.getValueAt(i,m_TableModel_Filelist.getDataVector().capacity()))).intValue();
        			
        			//Tabelle und die Z�hlwerte anpassen
        			m_TableModel_Filelist.removeRow(i);
					m_V_Filelist_Keys.remove(new Integer(deleteHRF_ID));
        			anzRows = m_TableModel_Filelist.getRowCount();
   					i--;
   					m_clModel.deleteHRF(deleteHRF_ID);
        		}
        	}
			m_gui.doRefresh();
		}
		/*
		 * Button Select_All
		 */
		else if(e.getSource().equals(m_Button_Select_All))
		{
			int anzRows = m_TableModel_Filelist.getRowCount();
			
			for(int ii = 0; ii < anzRows; ii++)
			{
				Vector tmpV = (Vector)(m_TableModel_Filelist.getDataVector()).elementAt(ii);
				String hashKey = tmpV.elementAt(tmpV.size()-1).toString();
				m_TableModel_Filelist.setValueAt(new Boolean(true),ii,0);
				createDetailTable(hashKey);
			}
		}
		/*
		 * Button Month_Back
		 */
		else if(e.getSource().equals(m_Button_Month_Back))
		{
			if((m_int_selectedMonth - 1) < 0)
			{
				m_int_selectedMonth = 11;
				m_int_selectedYear = m_int_selectedYear - 1;
			}
			else
			{
				m_int_selectedMonth = m_int_selectedMonth - 1;
			}
			createCalendarTable(m_int_selectedMonth , m_int_selectedYear);
		}
		/*
		 * Button Month_Forward
		 */
		else if(e.getSource().equals(m_Button_Month_Forward))
		{
			if((m_int_selectedMonth + 1) > 11)
			{
				m_int_selectedMonth = 0;
				m_int_selectedYear = m_int_selectedYear + 1;
			}
			else
			{
				m_int_selectedMonth = m_int_selectedMonth + 1;
			}
			createCalendarTable(m_int_selectedMonth, m_int_selectedYear);
		}
		/*
		 * Button GoTo
		 */
		else if(e.getSource().equals(m_Button_GoTo))
		{
			m_int_selectedMonth = m_CB_month.getSelectedIndex();
			m_int_selectedYear = Integer.parseInt((String)m_CB_year.getSelectedItem());
			createCalendarTable(m_CB_month.getSelectedIndex(),Integer.parseInt((String)m_CB_year.getSelectedItem()));
		}
		/*
		 * Button Reset
		 */
		else if(e.getSource().equals(m_Button_reset))
		{
			m_TableModel_Filelist.removeAllRows();
			m_HashTable_Details.clear();
			m_HashTable_Details_ColHeader.clear();
			m_HashTable_DatumKey.clear();
			m_V_Filelist_Keys.clear();
			rebuildDetailTable("alle");
		}
		else if(e.getSource().equals(m_Button_ResetImports))
		{
			m_HashTable_Import.clear();
			m_TableModel_Imports.removeAllRows();
			m_Table_Imports.revalidate();
			m_Table_Imports.repaint();
		}
		else if(e.getSource().equals(m_Button_help))
		{
			JFrame helpFrame = new JFrame("Help for plugin 'PlayerCompare'");
	        
	        JTextArea helpArea = new JTextArea();
	        Vector zeilen = new Vector();
	        for(int ii = 1; ii <= 11; ii++)
	        {
	        	helpArea.append(m_properties.getProperty(("zeile" + ii)) + "\n");
	        }
	        helpArea.append("\n\nThis plugin \u00A9 by KickMuck, Manager of Schwarz-Rot Langenbach(VI.683), home of the pirates");
	        if(m_Language_ID != 1 && m_Language_ID != 2)
	        {
	        	helpArea.append("\n" + m_properties.getProperty("UebersetzungIn") + ", thanx a lot!!!");
	        }
	        helpArea.setEditable(false);
            helpArea.setLineWrap(true);
            helpArea.setWrapStyleWord(true);
            
            JScrollPane helpSP = new JScrollPane(helpArea);
            helpSP.setPreferredSize(new Dimension(250, 500));
            helpFrame.getContentPane().add(helpSP, "Center");
            helpFrame.setSize(700, 400);
            helpFrame.show();
		}
    }
	
	/*****************
     * Methode f�r die Behandlung von �nderungen einer JComboBox
     * @param ie Das Event einer ComboBox
     *****************/
	
	public void itemStateChanged(ItemEvent ie)
	{
		if(ie.getSource().equals(m_CB_year))
		{
			m_int_selectedYear = Integer.parseInt((String)m_CB_year.getSelectedItem());
		}
		else if(ie.getSource().equals(m_CB_month))
		{
			String monat = "";
			int sel_monat = m_CB_month.getSelectedIndex();
			if(sel_monat < 10)
			{
				monat += "0";
			}
			m_int_selectedMonth = sel_monat;
		}
	}
//	 *********** Ende der Listener-Methoden *********************
//	 *************************************************************

//	*************************************************************
//	********** Beginn Mouse Events ******************************
    public void mouseClicked(MouseEvent e)
	{
    	if(e.getSource().equals(m_Table_Calendar))
    	{
    		int zeile = m_Table_Calendar.getSelectedRow();
    		int spalte = m_Table_Calendar.getSelectedColumn();
    		int tag = 0;
    		int id = 0;
    		Vector TagesIDs = new Vector();
    		if(spalte != 0)
    		{
    			Object tmpObj = m_TableModel_Calendar.getValueAt(zeile,spalte);
	    		if(!tmpObj.toString().equals(""))
	    		{
	    			tag = Integer.parseInt(tmpObj.toString());
	    			//id =((Integer)m_HashTable_DayInDB.get(new Integer(tag))).intValue();
	    			Hashtable tmp = (Hashtable)m_HashTable_DayInDB.get(new Integer(tag));
	    			Enumeration enu = tmp.keys();
	    			while(enu.hasMoreElements())
	    			{
	    				TagesIDs.add(enu.nextElement());
	    			}
	    		}
    		}
    		for(int ii = 0; ii < TagesIDs.size(); ii++)
    		{
    			id = ((Integer)TagesIDs.elementAt(ii)).intValue();
	    		if(id != 0)
	    		{
	    			//doSelect("SELECT NAME,DATUM,LIGANAME,PUNKTE,TOREFUER,TOREGEGEN,PLATZ,TEAMID,TEAMNAME,SPIELTAG,SAISON,TRAININGSINTENSITAET,TRAININGSART,ISTIMMUNG,ISELBSTVERTRAUEN,COTRAINER,TWTRAINER,FANS,HRF_ID,(SELECT COUNT(*) FROM SPIELER WHERE HRF_ID = '" + id + "') AS \"ANZAHL\" FROM HRF a, LIGA b, BASICS c, TEAM d, VEREIN e WHERE a.HRF_ID = '" + id + "' AND b.HRF_ID=a.HRF_ID AND c.HRF_ID=a.HRF_ID AND d.HRF_ID=a.HRF_ID AND e.HRF_ID=a.HRF_ID");
		    		HrfDbDetails dbDetail = new HrfDbDetails(id, m_clModel);
		    		if(m_HashTable_Details.containsKey("" + dbDetail.getHrf_ID()) == false)
		    		{
		    			m_TableModel_Filelist.addRow(dbDetail.getDatenVector());
		    			m_HashTable_Details.put("" + dbDetail.getHrf_ID(),dbDetail);
		    			m_V_Filelist_Keys.add(new Integer(id));
		    			m_Table_Filelist.revalidate();
		    			m_Table_Filelist.repaint();
		    		}
	    		}
    		}
    	}
    	
    	else if(e.getSource().equals(m_Table_Filelist))
    	{
	    	//Gew�hlte Zeile ermitteln
	    	int rowNr = m_Table_Filelist.getSelectedRow();
	    	// Key f�r die HashTable ermitteln
	    	Vector tmpV = (Vector)(m_TableModel_Filelist.getDataVector()).elementAt(rowNr);
			String hashKey = tmpV.elementAt(tmpV.size()-1).toString();
	    	//String hashKey = "" + m_TableModel_Filelist.getValueAt(rowNr,m_TableModel_Filelist.getDataVector().capacity());
	    	// Objekt aus der Hashtable holen
	    	createDetailTable(hashKey);
	    	/*HrfDetails selectedObject = (HrfDetails)m_HashTable_Details.get((String)hashKey);
	    	if(m_HashTable_Details_ColHeader.containsKey(selectedObject.getStr_Datum()) == false)
	    	{
	    		m_HashTable_Details_ColHeader.put(selectedObject.getStr_Datum(),selectedObject);
				//erstellen der Detailtabelle
		    	//Label der �berschrift f�llen
		    	m_Label_DetailHeader.setText(selectedObject.getTeamName() + " (" + selectedObject.getTeamID() + ")");
		    	// Panel f�r die Detail-ScrollPane erstellen
	//	    	 Array f�r die nicht fixen Details
		    	String[] objectDetails = {
		    			" " + selectedObject.getLiga(),
		    			" " + selectedObject.getSaison() + " / " + selectedObject.getSpieltag(),
		    			" " + selectedObject.getPunkte() + " / " + selectedObject.getToreFuer() + ":" + selectedObject.getToreGegen(),
		    			" " + selectedObject.getPlatz(),
		    			" " + selectedObject.getTrArt(),
		    			" " + selectedObject.getTrInt() + "%",
		    			" " + selectedObject.getAnzCoTrainer(),
		    			" " + selectedObject.getAnzTwTrainer(),
		    			" " + selectedObject.getSelbstvertrauen(),
		    			" " + selectedObject.getAnzSpieler(),
		    			" " + selectedObject.getStimmung(),
		    			" " + selectedObject.getStr_DatumVorher(),
		    			" " + selectedObject.getStr_DatumDanach()
		    	};
		    	HrfPanel teamDetails = new HrfPanel(m_int_Breite_Detail_Var,m_int_Hoehe_DetailPanels);
		    	teamDetails.setLayout(new GridLayout(objectDetails.length,1));
		    	String columnHeader = selectedObject.getStr_Datum();
		    	
		    	// Labels f�r das DetailPanel erstellen und einf�gen
		    	for(int ii = 0; ii < objectDetails.length; ii++)
		    	{
		    		if(ii == 0 || ii%2 == 0)
		        	{
		        		m_LineColor = hellblau;
		        	}
		        	else
		        	{
		        		m_LineColor = dunkelblau;
		        	}
		    		teamDetails.add(new HrfLabel(objectDetails[ii],m_int_Breite_Detail_Var,m_int_Hoehe_Label,JLabel.LEFT,m_LineColor));
		    	}
		    	// Entfernen-Panel und -Label erstellen
		    	HrfPanel entfernen = new HrfPanel(m_int_Breite_Detail_Var,m_int_Hoehe_Label + 5,rot);
		    	entfernen.setLayout(new GridLayout(1,1));
		    	HrfLabel remove = new HrfLabel(m_properties.getProperty("entf"),m_int_Breite_Detail_Var,m_int_Hoehe_Label,JLabel.CENTER,rot);
		    	remove.getInsets();
		    	entfernen.add(remove);
		    	
		    	//Vector f�r die Objekte in der Detailtabelle
		    	Vector details = new Vector();
		    	details.add(entfernen);
		    	details.add(teamDetails);
		    	
		    	m_TableModel_Details.addColumn(columnHeader,details);
		    	m_HashTable_Columns.put(columnHeader,details);
		    	
		    	int anzCols = m_TableModel_Details.getColumnCount();
		    	setDetailTableSize(anzCols);
	    	}*/
    	}
    	else if(e.getSource().equals(m_Table_Details))
    	{
    		int selCol = m_Table_Details.getSelectedColumn();
    		if(m_Table_Details.getSelectedRow() == 0 || selCol > 0)
    		{
    			String hashKey = m_Table_Details.getColumnModel().getColumn(selCol).getHeaderValue().toString();
    			m_HashTable_Details_ColHeader.remove(hashKey);
    			rebuildDetailTable(hashKey);
    		}
    	}
	}
	public void mouseEntered(MouseEvent e)
	{
		
	}
    public void mouseExited(MouseEvent e)
	{
		
	}
    public void mousePressed(MouseEvent e)
	{
		
	}
    public void mouseReleased(MouseEvent e)
	{
		
	}
//  ********** Ende Mouse Events ********************
//  *************************************************  
//  ********** Beginn Table Events ******************
    public void columnAdded(TableColumnModelEvent e)
	{
    	
	}
    public void columnMarginChanged(ChangeEvent e)
	{
    	
	}
    public void columnMoved(TableColumnModelEvent e)
	{
    	if(m_Table_Details.getColumnModel().getColumnIndex(" ") != 0)
    	{
    		m_Table_Details.getColumnModel().moveColumn(m_Table_Details.getColumnModel().getColumnIndex(" "),0);
    	}
	}
    public void columnRemoved(TableColumnModelEvent e)
	{
    	
	}
    public void columnSelectionChanged(ListSelectionEvent e)
	{
    	
	}
//  ********** Ende Table Events ******************
    
    //******** Start der Methoden f�r IOfficialPlugin
    
    /**
     * Liefert die Plugin-ID
     * @return Die zugewiesene ID f�r dieses Plugin
     */
    public int getPluginID()
    {
    	return m_pluginID;
    }
    
    /**
     * Liefert den Plugin-Namen
     * @return Gibt den Namen des Plugins zur�ck.
     */
    public String getPluginName()
    {
    	return m_PluginName;
    }
    
    /**
     * Liefert eine Liste der Dateien, die nicht �berschrieben werden d�rfen.
     * @return Ein Array von Files, die nicht �berschrieben werden d�rfen.
     */
    public File[] getUnquenchableFiles()
    {
    	return m_unquenchableFiles;
    }
    
    /**
     * Liefert die Plugin-Version
     * @return Die Versionsnummer als double-Wert.
     */
    public double getVersion()
    {
    	return m_pluginVersion;
    }
    
    /** 
	 * Liefert den Namen des Plugins f�r die Beschriftung des Tabs.
	 * @return Liefert den Namen des Plugins
	 */
	public String getName()
	{
		return m_PluginName + " V" + m_pluginVersion;
	}
//  ******** Start der Methoden f�r IOfficialPlugin
//	 *********** Beginn der Datenbank-Methoden *******************
	/*****************
	 * F�hrt ein query gegen die DB aus
	 * @param query
	 */
	public void doSelect(String query)
	{
		try
		{
			m_queryResult = m_jdbcAdapter.executeQuery(query);
		}
		catch(Exception e)
		{
			//debugWindow.append("FEHLER");
		}
	}
//	 *********** Ende der Datenbank-Methoden **********************
//	 **************************************************************
	//********** Beginn F�r HO notwendige Methoden ****************
	/**
	 * F�hrt ein refresh f�r das Plugin durch
	 */
	public void refresh()
    {
		doSelect("SELECT COUNT(*) FROM HRF");
		try
		{
			while(m_queryResult.next())
			{
				m_int_anz_DBEintraege = m_queryResult.getInt(1);
			}
		}
		catch(SQLException sexc)
		{
			//debugWindow.append("" + sexc);
		}
		createCalendarTable(m_int_selectedMonth , m_int_selectedYear);
		
		// Leeren der Hashtables, da
		m_HashTable_Details.clear();
		m_HashTable_DatumKey.clear();
		m_TableModel_Filelist.removeAllRows();
		
		Vector tmp_Pfade = new Vector();
		Vector tmp_IDs = new Vector();
		//debugWindow.append("Anzahl Keys in m_V_Filelist_Keys: " + m_V_Filelist_Keys.size());
		for(int ii = 0; ii < m_V_Filelist_Keys.size(); ii++)
		{
			//debugWindow.append("Nummer: " + ii);
			//debugWindow.append("Wert: " + m_V_Filelist_Keys.elementAt(ii));
			if(m_V_Filelist_Keys.elementAt(ii).getClass().equals(Integer.class))
			{
				int tmp_id = Integer.parseInt(m_V_Filelist_Keys.elementAt(ii).toString());
				HrfDbDetails dbDetail = new HrfDbDetails(tmp_id, m_clModel);
	    		if(m_HashTable_Details.containsKey("" + dbDetail.getHrf_ID()) == false)
	    		{
	    			m_TableModel_Filelist.addRow(dbDetail.getDatenVector());
	    			m_HashTable_Details.put("" + dbDetail.getHrf_ID(),dbDetail);
	    			m_Table_Filelist.revalidate();
	    			m_Table_Filelist.repaint();
	    		}
			}
			else
			{
				//debugWindow.append("Wert im else: " + m_V_Filelist_Keys.elementAt(ii));
				//HrfFileDetails tmp = new HrfFileDetails(m_files[ii].getPath(),m_clModel);
				HrfFileDetails tmp = new HrfFileDetails(m_V_Filelist_Keys.elementAt(ii).toString(),m_clModel);
				String tmp_Datum = tmp.getStr_Datum().substring(0,10);
				String tmp_Pfad = tmp.getPfad();
				m_TableModel_Filelist.addRow(tmp.getDatenVector());
				m_HashTable_Details.put(tmp_Pfad,tmp);
				m_HashTable_DatumKey.put(tmp_Datum,tmp_Pfad);
				/*if(Integer.parseInt(tmp_Datum.substring(5,7)) == m_int_selectedMonth + 1)
				{
					createCalendarTable(m_int_selectedMonth , m_int_selectedYear);
				}
				m_Table_Filelist.revalidate();
				m_Table_Filelist.repaint();*/
			}
		}
    }
	
//	********** Ende F�r HO notwendige Methoden ******************
	//***********************************************************
	//********** Beginn Debugging Methoden **********************
	public static void appendText(String test)
	{
		//debugWindow.append(test);
	}
//	********** Ende Debugging Methoden **************************
}
