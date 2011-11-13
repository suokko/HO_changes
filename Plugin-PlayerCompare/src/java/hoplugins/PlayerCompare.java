/*
 * Created on 19.06.2004
 */
package hoplugins;

import plugins.*;
import hoplugins.playerCompare.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

/**
 * @author KickMuck
 */
public class PlayerCompare implements IPlugin,IOfficialPlugin, MouseListener, ActionListener,ItemListener,IRefreshable, FocusListener
{
	
	private plugins.IHOMiniModel m_clModel = null;
	//Members for Tables
	private PlayerTable m_jTableTop = null;	//Table for all players
	private JTable m_jTableBottom = null;	//Table for all compared players
	private JTable m_jTableDetail = null;	//Table for player details
	// Members for Buttons
	private JButton m_btCompare;	//Button -> Compare
	private JButton m_btHelp;		//Button -> Help
	private JButton m_btReset;		//Button -> Reset
	private JButton m_btDebugWindow;//Button -> DebugWindow
	
	private String m_PluginName = "PlayerCompare";
	private float m_pluginVersion = 1.2f;
	private int m_pluginID = 45;
	
	private JPanel m_jpPanel;
	private JPanel m_topTablePanel;
	private JPanel m_topButtonPanel;
	private JPanel p_PlayerDetail = null;
	
	private JScrollPane m_scrollPaneTableTop;
	private JScrollPane m_scrollPaneButtons;
	private JScrollPane m_scrollPaneTableBottom;
	private JScrollPane m_scrollPanePlayer;
	private JScrollPane m_scrollPanePlayerGesamt;
	
	private JSplitPane m_splitPane;
	private JSplitPane m_splitPaneTop;
	private JSplitPane m_splitPaneBottom;
	
	private PlayerTableModel m_playerTableModelTop;
	private PlayerTableModel m_playerTableModelBottom;
	private PlayerTableModel m_playerTableModelDetail;
	
	private JLabel m_L_GroupBy;
	private JLabel m_L_Header;
	private JLabel m_L_Experience;
	private JLabel m_L_Form;
	private JLabel m_L_Stamina;
	private JLabel m_L_Keeping;
	private JLabel m_L_Defending;
	private JLabel m_L_Playmaking;
	private JLabel m_L_Passing;
	private JLabel m_L_Winger;
	private JLabel m_L_Scoring;
	private JLabel m_L_SetPieces;
	private JLabel m_L_Loyalty;
	private JLabel m_L_HomeGrown;
		
	private static JComboBox m_CB_type;
	private JComboBox m_CB_Experience;
	private JComboBox m_CB_Form;
	private JComboBox m_CB_Stamina;
	private JComboBox m_CB_Keeping;
	private JComboBox m_CB_Defending;
	private JComboBox m_CB_Playmaking;
	private JComboBox m_CB_Passing;
	private JComboBox m_CB_Winger;
	private JComboBox m_CB_Scoring;
	private JComboBox m_CB_SetPieces;
	private JComboBox m_CB_Loyalty;
	private JComboBox m_CB_Homegrown;
	
	private JComboBox m_CB_Nr_Experience;
	private JComboBox m_CB_Nr_Form;
	private JComboBox m_CB_Nr_Stamina;
	private JComboBox m_CB_Nr_Keeping;
	private JComboBox m_CB_Nr_Defending;
	private JComboBox m_CB_Nr_Playmaking;
	private JComboBox m_CB_Nr_Passing;
	private JComboBox m_CB_Nr_Winger;
	private JComboBox m_CB_Nr_Scoring;
	private JComboBox m_CB_Nr_SetPieces;
	private JComboBox m_CB_Nr_Loyalty;
	
	private static IDebugWindow debugWindow;
	
	private Vector<Player> m_V_setPlayers;
	private Vector<ISpieler> m_V_allPlayers;
	private Player[] m_ar_allPlayers;
	private Player[] m_ar_setPlayers;
	
	private String sTabName = new String();
	
	private String[] m_rating;
	
	private static int m_selectedRow;
	private int m_i_ptmTopCount;
	private int m_numberOfPlayers;
	
	private static int []newRating;
	private static int []changedRating;
	
	private boolean m_b_refresh = true;
		
	private Color lightblue = new Color (235,235,255);
	
	private static Properties m_properties;
	 
	/**
	 * Is called by HO! to start the plugin
	 */
	public void start (IHOMiniModel hOMiniModel)
	{             
		
		//Var for Name displayed in Tab
		sTabName = getName();        
		//Save model in member Var
		m_clModel = hOMiniModel;
		
		debugWindow = m_clModel.getGUI ().createDebugWindow ( new java.awt.Point( 100, 200 ), new java.awt.Dimension( 200,  100 ) );
		
		// Read the language files
		try
        {
			m_properties = new Properties();
            File languageFile = new File("hoplugins/playerCompare/sprache/" + m_clModel.getHelper().getLanguageName() + ".properties");
            if(languageFile.exists())
            {
            	m_properties.load(new FileInputStream(languageFile));
            } else
            {
                languageFile = new File("hoplugins/playerCompare/sprache/English.properties");
                m_properties.load(new FileInputStream(languageFile));
            }
        }
        catch(Exception e) 
		{
        	debugWindow.append("Textdateifehler: " + e);
		}
        
        // get all players ans save them in an array
        getAllPlayers();
		
		
		m_jpPanel = hOMiniModel.getGUI().createImagePanel ();
		m_jpPanel.setLayout (new BorderLayout());
		m_jpPanel.addFocusListener(this);
		// Register tab for refresh
		m_clModel.getGUI().registerRefreshable(this);
		
		//Default values for skill selection
		newRating = new int[]
		{
				0,6,0,0,0,0,0,0,0,0,10,0
		};
		
		// Default values for skill changes
		changedRating = new int[]
		{
				0,0,0,0,0,0,0,0,0,0,0,0
		};
		
		// Set the labels
		m_L_Experience = new JLabel(m_clModel.getLanguageString("Erfahrung"));
		m_L_Form = new JLabel(m_clModel.getLanguageString("Form"));
		m_L_Stamina = new JLabel(m_clModel.getLanguageString("Kondition"));
		m_L_Keeping = new JLabel(m_clModel.getLanguageString("Torwart"));
		m_L_Defending = new JLabel(m_clModel.getLanguageString("Verteidigung"));
		m_L_Playmaking = new JLabel(m_clModel.getLanguageString("Spielaufbau"));
		m_L_Passing = new JLabel(m_clModel.getLanguageString("Passpiel"));
		m_L_Winger = new JLabel(m_clModel.getLanguageString("Fluegelspiel"));
		m_L_Scoring = new JLabel(m_clModel.getLanguageString("Torschuss"));
		m_L_SetPieces = new JLabel(m_clModel.getLanguageString("Standards"));
		m_L_Loyalty = new JLabel(m_clModel.getLanguageString("Loyalty"));
		m_L_HomeGrown = new JLabel(m_clModel.getLanguageString("Motherclub"));
		
		//Set up standard ratings for combos
		m_rating = new String[]
		{
				"---",
				m_clModel.getLanguageString("katastrophal"),
				m_clModel.getLanguageString("erbaermlich"),
				m_clModel.getLanguageString("armselig"),
				m_clModel.getLanguageString("schwach"),
				m_clModel.getLanguageString("durchschnittlich"),
				m_clModel.getLanguageString("passabel"),
				m_clModel.getLanguageString("gut"),
				m_clModel.getLanguageString("sehr_gut"),
				m_clModel.getLanguageString("hervorragend"),
				m_clModel.getLanguageString("grossartig"),
				m_clModel.getLanguageString("brilliant"),
				m_clModel.getLanguageString("fantastisch"),
				m_clModel.getLanguageString("Weltklasse"),
				m_clModel.getLanguageString("uebernatuerlich"),
				m_clModel.getLanguageString("gigantisch"),
				m_clModel.getLanguageString("ausserirdisch"),
				m_clModel.getLanguageString("mythisch"),
				m_clModel.getLanguageString("maerchenhaft"),
				m_clModel.getLanguageString("galaktisch"),
				m_clModel.getLanguageString("goettlich"),
		};
		
		
		//************** Start Debug Window *******************************************************
		m_btDebugWindow = new JButton("DebugWindow");
        m_btDebugWindow.setToolTipText("Shows the DebugWindow");        
        m_btDebugWindow.addActionListener(this);
        debugWindow = m_clModel.getGUI().createDebugWindow(new java.awt.Point(100, 200),
        		new java.awt.Dimension(800, 400));
		//************* End Debug Window *********************************************************
		
		// Create table model for top table
		m_playerTableModelTop = new PlayerTableModel(hOMiniModel, m_ar_allPlayers, 1);
		TableSorter sorter = new TableSorter(m_playerTableModelTop); 
		m_jTableTop = new PlayerTable(sorter, m_clModel,m_playerTableModelTop);
		sorter.setTableHeader(m_jTableTop.getTableHeader());
		// Calculate number of rows in top table
		m_i_ptmTopCount = m_playerTableModelTop.getRowCount();
		m_topTablePanel = new JPanel(new GridLayout(1,2));
		m_scrollPaneTableTop = new JScrollPane(m_jTableTop);
		m_topTablePanel.add(m_scrollPaneTableTop);
		m_topButtonPanel = new JPanel();
		m_topButtonPanel.getInsets(new Insets(5,5,5,5));
		
		GridBagLayout gbl = new GridBagLayout();
	    GridBagConstraints gbc = new GridBagConstraints();
		m_topButtonPanel.setLayout(gbl);

		m_btCompare = new JButton(m_properties.getProperty("Vergleichen"));
		m_btCompare.addActionListener(this);
		m_btCompare.setToolTipText(m_properties.getProperty("ttCompare"));
		
		m_btHelp = new JButton(m_properties.getProperty("Hilfe"));
		m_btHelp.addActionListener(this);
		m_btHelp.setToolTipText(m_properties.getProperty("helpWindow"));        
        m_btHelp.setLocation(25, 300);
        m_btHelp.setSize(120, 25);  
		
		m_btReset = new JButton("Reset");
		m_btReset.setToolTipText(m_properties.getProperty("btReset"));
		m_btReset.addActionListener(this);
		
		m_L_GroupBy = new JLabel(m_properties.getProperty("vergleichen_alle"));
		m_L_Header = new JLabel(m_properties.getProperty("setze_alle"));
		
		//Create controls
		m_CB_type = new JComboBox();
		m_CB_type.addItem(m_properties.getProperty("gewaehlte"));
		m_CB_type.addItem(m_clModel.getLanguageString("Torwart"));
		m_CB_type.addItem(m_clModel.getLanguageString("Verteidigung"));
		m_CB_type.addItem(m_clModel.getLanguageString("Mittelfeld"));
		m_CB_type.addItem(m_clModel.getLanguageString("Fluegelspiel"));
		m_CB_type.addItem(m_clModel.getLanguageString("Sturm"));
		m_CB_type.addItem(m_properties.getProperty("GruppeA"));
		m_CB_type.addItem(m_properties.getProperty("GruppeB"));
		m_CB_type.addItem(m_properties.getProperty("GruppeC"));
		m_CB_type.addItem(m_properties.getProperty("GruppeD"));
		m_CB_type.addItem(m_properties.getProperty("GruppeE"));
		m_CB_type.addItem(m_properties.getProperty("alle"));
		m_CB_type.addItemListener(this);
		
		// Experience
		m_CB_Experience = new JComboBox();
		fillComboBox(m_CB_Experience, m_rating.length);
		m_CB_Experience.setSelectedIndex(0);
		m_CB_Experience.addItemListener(this);
		m_CB_Nr_Experience = new JComboBox();
		fillComboBox(m_CB_Nr_Experience);
		m_CB_Nr_Experience.setSelectedIndex(0);
		m_CB_Nr_Experience.addItemListener(this);
		// Form
		m_CB_Form = new JComboBox();
		fillComboBox(m_CB_Form,9);
		m_CB_Form.setSelectedIndex(6);
		m_CB_Form.addItemListener(this);
		m_CB_Nr_Form = new JComboBox();
		fillComboBox(m_CB_Nr_Form);
		m_CB_Nr_Form.setSelectedIndex(0);
		m_CB_Nr_Form.addItemListener(this);
		// Stamina
		m_CB_Stamina = new JComboBox();
		fillComboBox(m_CB_Stamina,10);
		m_CB_Stamina.setSelectedIndex(0);
		m_CB_Stamina.addItemListener(this);
		m_CB_Nr_Stamina = new JComboBox();
		fillComboBox(m_CB_Nr_Stamina);
		m_CB_Nr_Stamina.setSelectedIndex(0);
		m_CB_Nr_Stamina.addItemListener(this);
		// Goal Keeping
		m_CB_Keeping = new JComboBox();
		fillComboBox(m_CB_Keeping,m_rating.length);
		m_CB_Keeping.setSelectedIndex(0);
		m_CB_Keeping.addItemListener(this);
		m_CB_Nr_Keeping = new JComboBox();
		fillComboBox(m_CB_Nr_Keeping);
		m_CB_Nr_Keeping.setSelectedIndex(0);
		m_CB_Nr_Keeping.addItemListener(this);
		// Defending
		m_CB_Defending = new JComboBox();
		fillComboBox(m_CB_Defending,m_rating.length);
		m_CB_Defending.setSelectedIndex(0);
		m_CB_Defending.addItemListener(this);
		m_CB_Nr_Defending = new JComboBox();
		fillComboBox(m_CB_Nr_Defending);
		m_CB_Nr_Defending.setSelectedIndex(0);
		m_CB_Nr_Defending.addItemListener(this);
		// Playmaking
		m_CB_Playmaking = new JComboBox();
		fillComboBox(m_CB_Playmaking,m_rating.length);
		m_CB_Playmaking.setSelectedIndex(0);
		m_CB_Playmaking.addItemListener(this);
		m_CB_Nr_Playmaking = new JComboBox();
		fillComboBox(m_CB_Nr_Playmaking);
		m_CB_Nr_Playmaking.setSelectedIndex(0);
		m_CB_Nr_Playmaking.addItemListener(this);
		// Passing
		m_CB_Passing = new JComboBox();
		fillComboBox(m_CB_Passing,m_rating.length);
		m_CB_Passing.setSelectedIndex(0);
		m_CB_Passing.addItemListener(this);
		m_CB_Nr_Passing = new JComboBox();
		fillComboBox(m_CB_Nr_Passing);
		m_CB_Nr_Passing.setSelectedIndex(0);
		m_CB_Nr_Passing.addItemListener(this);
		// Winger
		m_CB_Winger = new JComboBox();
		fillComboBox(m_CB_Winger,m_rating.length);
		m_CB_Winger.setSelectedIndex(0);
		m_CB_Winger.addItemListener(this);
		m_CB_Nr_Winger = new JComboBox();
		fillComboBox(m_CB_Nr_Winger);
		m_CB_Nr_Winger.setSelectedIndex(0);
		m_CB_Nr_Winger.addItemListener(this);
		// Scoring
		m_CB_Scoring = new JComboBox();
		fillComboBox(m_CB_Scoring,m_rating.length);
		m_CB_Scoring.setSelectedIndex(0);
		m_CB_Scoring.addItemListener(this);
		m_CB_Nr_Scoring = new JComboBox();
		fillComboBox(m_CB_Nr_Scoring);
		m_CB_Nr_Scoring.setSelectedIndex(0);
		m_CB_Nr_Scoring.addItemListener(this);
		// Set Pieces
		m_CB_SetPieces = new JComboBox();
		fillComboBox(m_CB_SetPieces, m_rating.length);
		m_CB_SetPieces.setSelectedIndex(0);
		m_CB_SetPieces.addItemListener(this);
		m_CB_Nr_SetPieces = new JComboBox();
		fillComboBox(m_CB_Nr_SetPieces);
		m_CB_Nr_SetPieces.setSelectedIndex(0);
		m_CB_Nr_SetPieces.addItemListener(this);
		// Loyalty
		m_CB_Loyalty = new JComboBox();
		fillComboBox(m_CB_Loyalty, m_rating.length);
		m_CB_Loyalty.setSelectedIndex(10);
		m_CB_Loyalty.addItemListener(this);
		m_CB_Nr_Loyalty = new JComboBox();
		fillComboBox(m_CB_Nr_Loyalty);
		m_CB_Nr_Loyalty.setSelectedIndex(0);
		m_CB_Nr_Loyalty.addItemListener(this);
		// Homegrown
		m_CB_Homegrown = new JComboBox();
		m_CB_Homegrown.addItem("---");
		m_CB_Homegrown.addItem("No");
		m_CB_Homegrown.addItem("Yes");
		m_CB_Homegrown.setSelectedIndex(0);
		m_CB_Homegrown.addItemListener(this);
		
		// Add components to the layout
		gbc.insets = new Insets(5,3,10,3);
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbl.setConstraints(m_L_GroupBy, gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbl.setConstraints(m_CB_type, gbc);
		//*******************************
		gbc.gridwidth = 4;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbl.setConstraints(m_L_Header, gbc);
		gbc.gridwidth = 1;
		//*******************************
		gbc.insets = new Insets(3,3,3,3);
		gbl.setConstraints(m_L_Experience, gbc);
		gbl.setConstraints(m_CB_Experience, gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbl.setConstraints(m_CB_Nr_Experience, gbc);
		//*******
		gbc.gridwidth = 1;
		//*******************************
		gbl.setConstraints(m_L_Form, gbc);
		gbl.setConstraints(m_CB_Form, gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER; //end row
		gbl.setConstraints(m_CB_Nr_Form, gbc);
		//*******
		gbc.gridwidth = 1;
		gbl.setConstraints(m_L_Stamina, gbc);
		gbl.setConstraints(m_CB_Stamina, gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER; //end row
		gbl.setConstraints(m_CB_Nr_Stamina, gbc);
		//*******
		gbc.gridwidth = 1;
		gbl.setConstraints(m_L_Keeping, gbc);
		gbl.setConstraints(m_CB_Keeping, gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER; //end row
		gbl.setConstraints(m_CB_Nr_Keeping, gbc);
		//*******
		gbc.gridwidth = 1;
		gbl.setConstraints(m_L_Defending, gbc);
		gbl.setConstraints(m_CB_Defending, gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER; //end row
		gbl.setConstraints(m_CB_Nr_Defending, gbc);
		//*******
		gbc.gridwidth = 1;
		gbl.setConstraints(m_L_Playmaking, gbc);
		gbl.setConstraints(m_CB_Playmaking, gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER; //end row
		gbl.setConstraints(m_CB_Nr_Playmaking, gbc);
		//*******
		gbc.gridwidth = 1;
		gbl.setConstraints(m_L_Passing, gbc);
		gbl.setConstraints(m_CB_Passing, gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER; //end row
		gbl.setConstraints(m_CB_Nr_Passing, gbc);
		//*******
		gbc.gridwidth = 1;
		gbl.setConstraints(m_L_Winger, gbc);
		gbl.setConstraints(m_CB_Winger, gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER; //end row
		gbl.setConstraints(m_CB_Nr_Winger, gbc);
		//*******
		gbc.gridwidth = 1;
		gbl.setConstraints(m_L_Scoring, gbc);
		gbl.setConstraints(m_CB_Scoring, gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER; //end row
		gbl.setConstraints(m_CB_Nr_Scoring, gbc);
		//*******
		gbc.gridwidth = 1;
		gbl.setConstraints(m_L_SetPieces, gbc);
		gbl.setConstraints(m_CB_SetPieces, gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER; //end row
		gbl.setConstraints(m_CB_Nr_SetPieces, gbc);
		//*******
		gbc.gridwidth = 1;
		gbl.setConstraints(m_L_Loyalty, gbc);
		gbl.setConstraints(m_CB_Loyalty, gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER; //end row
		gbl.setConstraints(m_CB_Nr_Loyalty, gbc);
		//*******
		gbc.gridwidth = 1;
		gbl.setConstraints(m_L_HomeGrown, gbc);
		gbc.gridwidth = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER; //end row
		gbl.setConstraints(m_CB_Homegrown, gbc);
		//*******
		gbc.gridwidth = 1;
		// *******************************
		gbc.gridwidth = GridBagConstraints.REMAINDER; //end row
		gbc.insets = new Insets(10,3,0,3);
		gbl.setConstraints(m_btCompare, gbc);
		gbc.gridwidth = 1;
		// *******************************
		gbc.gridwidth = GridBagConstraints.REMAINDER; //end row
		gbc.insets = new Insets(0,3,0,3);
		gbl.setConstraints(m_btHelp, gbc);
		gbc.gridwidth = 1;
		// *******************************
		gbc.gridwidth = GridBagConstraints.REMAINDER; //end row
		gbc.insets = new Insets(0,3,0,3);
		gbl.setConstraints(m_btReset, gbc);
		// *******************************
		
		//Add Buttons to the Panel
		m_topButtonPanel.add(m_L_GroupBy);
		m_topButtonPanel.add(m_CB_type);
		m_topButtonPanel.add(m_L_Header);
		m_topButtonPanel.add(m_L_Experience);
		m_topButtonPanel.add(m_CB_Experience);
		m_topButtonPanel.add(m_CB_Nr_Experience);
		m_topButtonPanel.add(m_L_Form);
		m_topButtonPanel.add(m_CB_Form);
		m_topButtonPanel.add(m_CB_Nr_Form);
		m_topButtonPanel.add(m_L_Stamina);
		m_topButtonPanel.add(m_CB_Stamina);
		m_topButtonPanel.add(m_CB_Nr_Stamina);
		m_topButtonPanel.add(m_L_Keeping);
		m_topButtonPanel.add(m_CB_Keeping);
		m_topButtonPanel.add(m_CB_Nr_Keeping);
		m_topButtonPanel.add(m_L_Defending);
		m_topButtonPanel.add(m_CB_Defending);
		m_topButtonPanel.add(m_CB_Nr_Defending);
		m_topButtonPanel.add(m_L_Playmaking);
		m_topButtonPanel.add(m_CB_Playmaking);
		m_topButtonPanel.add(m_CB_Nr_Playmaking);
		m_topButtonPanel.add(m_L_Passing);
		m_topButtonPanel.add(m_CB_Passing);
		m_topButtonPanel.add(m_CB_Nr_Passing);
		m_topButtonPanel.add(m_L_Winger);
		m_topButtonPanel.add(m_CB_Winger);
		m_topButtonPanel.add(m_CB_Nr_Winger);
		m_topButtonPanel.add(m_L_Scoring);
		m_topButtonPanel.add(m_CB_Scoring);
		m_topButtonPanel.add(m_CB_Nr_Scoring);
		m_topButtonPanel.add(m_L_SetPieces);
		m_topButtonPanel.add(m_CB_SetPieces);
		m_topButtonPanel.add(m_CB_Nr_SetPieces);
		m_topButtonPanel.add(m_L_Loyalty);
		m_topButtonPanel.add(m_CB_Loyalty);
		m_topButtonPanel.add(m_CB_Nr_Loyalty);
		m_topButtonPanel.add(m_L_HomeGrown);
		m_topButtonPanel.add(m_CB_Homegrown);

		m_topButtonPanel.add(m_btCompare);
		m_topButtonPanel.add(m_btHelp);
		m_topButtonPanel.add(m_btReset);
		
		// Panel for the buttons
		m_scrollPaneButtons = new JScrollPane(m_topButtonPanel);
		m_scrollPaneTableBottom = new JScrollPane();
		m_scrollPanePlayer = new JScrollPane();
		m_scrollPanePlayerGesamt = new JScrollPane();
		
		p_PlayerDetail = new JPanel(new BorderLayout());
		
		setDummyPlayerDetails();
		m_splitPaneTop =new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,m_scrollPaneTableTop,m_scrollPaneButtons);
		m_splitPaneBottom = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,m_scrollPaneTableBottom,m_scrollPanePlayerGesamt);
		m_splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,m_splitPaneTop,m_splitPaneBottom);

		int tmpBreite = m_clModel.getUserSettings().hoMainFrame_width;
		int devLocation = tmpBreite - 300;
		int devLocationBottom = tmpBreite -300;
		
		m_splitPaneTop.setDividerLocation(devLocation);
		m_splitPaneBottom.setDividerLocation(devLocationBottom);
	   
		m_jpPanel.add( m_splitPane,BorderLayout.CENTER);
		
		m_clModel.getGUI().addTab(sTabName,m_jpPanel);
	}

	public void fillComboBox(JComboBox jcb, int len)
	{
		for(int i = 0; i < len; i++)
		{
			if(i>0)
			{	
				jcb.addItem(m_rating[i]+" ("+i+")");
			}
			else
			{
				jcb.addItem(m_rating[i]);
			}
		}
	}
	
	public void fillComboBox(JComboBox jcb)
	{
		for(int i = 0; i < 7; i++)
		{	
			jcb.addItem(" +" + i);
		}
	}
	
	/**
	 * appendText(String)
	 * - writes the text to the DebugWindow
	 */
	public static void appendText(String text)
	{
		debugWindow.append(text);
	}
	
    /** 
	 * Gets the name of the plugin for the Tab description
	 * @return The name of the plugin
	 */
	public String getName()
	{
		return m_PluginName;
	}
    
    public void refresh()
    {
    	// only refresh if no button was pressed so that m_b_refresh doesn't get set
    	if(m_b_refresh == true)
    	{
    		m_scrollPaneTableTop.setViewportView(null);
    		//Get players
    		getAllPlayers();
    		m_playerTableModelTop = new PlayerTableModel(m_clModel,m_ar_allPlayers,1);
    		TableSorter sorter = new TableSorter(m_playerTableModelTop);
    		m_jTableTop = new PlayerTable(sorter,m_clModel,m_playerTableModelTop);
    		sorter.setTableHeader(m_jTableTop.getTableHeader());
    		m_i_ptmTopCount = m_playerTableModelTop.getRowCount();
    		m_scrollPaneTableTop.setViewportView(m_jTableTop);
    	}
    	// If a button was pressed, m_b_refresh is set to true,
    	// so that the next update causes a refresh
    	else
    	{
    		m_b_refresh = true;
    	}
    }
    
    public void itemStateChanged(ItemEvent ie)
	{
    	if(ie.getSource().equals(m_CB_type))
    	{
    		int cbType = m_CB_type.getSelectedIndex();
	    	for(int i = 0; i < m_i_ptmTopCount; i++)
	    	{
	    		int spielerID = ((Integer)m_playerTableModelTop.getValueAt(i,m_playerTableModelTop.getColumnCount()-1)).intValue();
				int pos = m_clModel.getSpieler(spielerID).getIdealPosition();
				String group = m_clModel.getSpieler(spielerID).getTeamInfoSmilie();
	    		if(cbType == 1 && pos == 0
	    				|| cbType == 2 && (pos > 0 && pos < 8)
						|| cbType == 3 && (pos > 7 && pos < 12)
						|| cbType == 4 && (pos > 11 && pos < 16)
						|| cbType == 5 && pos > 15
						|| cbType == 6 && group.startsWith("A")
						|| cbType == 7 && group.startsWith("B")
						|| cbType == 8 && group.startsWith("C")
						|| cbType == 9 && group.startsWith("D")
						|| cbType == 10 && group.startsWith("E")
						)
	    		{
	    			PlayerCompare.appendText("Spieler Gruppe: " + group);
	    			m_playerTableModelTop.setValueAt(Boolean.TRUE,i,0);	
	    		}
	    		else
	    		{
	    			if(cbType != 0 && cbType != 11)
	    			{
	    				m_playerTableModelTop.setValueAt(Boolean.FALSE,i,0);
	    			}
	    			else if(cbType == 11)
					{
	    				m_playerTableModelTop.setValueAt(Boolean.TRUE,i,0);
					}
	    		}
	    		m_playerTableModelTop.fireTableCellUpdated(i,0);
	    		m_playerTableModelTop.fireTableRowsUpdated(i,i);
	    	}
    	}
    	else if(ie.getSource().equals(m_CB_Experience))
    	{
    		if(m_CB_Experience.getSelectedIndex() != 0)
    		{
    			m_CB_Nr_Experience.setSelectedIndex(0);
    		}
    	}
    	else if(ie.getSource().equals(m_CB_Form))
    	{
    		if(m_CB_Form.getSelectedIndex() != 0)
    		{
    			m_CB_Nr_Form.setSelectedIndex(0);
    		}
    	}
    	else if(ie.getSource().equals(m_CB_Stamina))
    	{
    		if(m_CB_Stamina.getSelectedIndex() != 0)
    		{
    			m_CB_Nr_Stamina.setSelectedIndex(0);
    		}
    	}
    	else if(ie.getSource().equals(m_CB_Keeping))
    	{
    		if(m_CB_Keeping.getSelectedIndex() != 0)
    		{
    			m_CB_Nr_Keeping.setSelectedIndex(0);
    		}
    	}
    	else if(ie.getSource().equals(m_CB_Defending))
    	{
    		if(m_CB_Defending.getSelectedIndex() != 0)
    		{
    			m_CB_Nr_Defending.setSelectedIndex(0);
    		}
    	}
    	else if(ie.getSource().equals(m_CB_Playmaking))
    	{
    		if(m_CB_Playmaking.getSelectedIndex() != 0)
    		{
    			m_CB_Nr_Playmaking.setSelectedIndex(0);
    		}
    	}
    	else if(ie.getSource().equals(m_CB_Passing))
    	{
    		if(m_CB_Passing.getSelectedIndex() != 0)
    		{
    			m_CB_Nr_Passing.setSelectedIndex(0);
    		}
    	}
    	else if(ie.getSource().equals(m_CB_Winger))
    	{
    		if(m_CB_Winger.getSelectedIndex() != 0)
    		{
    			m_CB_Nr_Winger.setSelectedIndex(0);
    		}
    	}
    	else if(ie.getSource().equals(m_CB_Scoring))
    	{
    		if(m_CB_Scoring.getSelectedIndex() != 0)
    		{
    			m_CB_Nr_Scoring.setSelectedIndex(0);
    		}
    	}
    	else if(ie.getSource().equals(m_CB_SetPieces))
    	{
    		if(m_CB_SetPieces.getSelectedIndex() != 0)
    		{
    			m_CB_Nr_SetPieces.setSelectedIndex(0);
    		}
    	}
    	else if(ie.getSource().equals(m_CB_Loyalty))
    	{
    		if(m_CB_Loyalty.getSelectedIndex() != 0)
    		{
    			m_CB_Nr_Loyalty.setSelectedIndex(0);
    		}
    	}
    	else if(ie.getSource().equals(m_CB_Nr_Experience))
    	{
    		if(m_CB_Nr_Experience.getSelectedIndex() != 0)
    		{
    			m_CB_Experience.setSelectedIndex(0);
    		}
    	}
    	else if(ie.getSource().equals(m_CB_Nr_Form))
    	{
    		if(m_CB_Nr_Form.getSelectedIndex() != 0)
    		{
    			m_CB_Form.setSelectedIndex(0);
    		}
    	}
    	else if(ie.getSource().equals(m_CB_Nr_Stamina))
    	{
    		if(m_CB_Nr_Stamina.getSelectedIndex() != 0)
    		{
    			m_CB_Stamina.setSelectedIndex(0);
    		}
    	}
    	else if(ie.getSource().equals(m_CB_Nr_Keeping))
    	{
    		if(m_CB_Nr_Keeping.getSelectedIndex() != 0)
    		{
    			m_CB_Keeping.setSelectedIndex(0);
    		}
    	}
    	else if(ie.getSource().equals(m_CB_Nr_Defending))
    	{
    		if(m_CB_Nr_Defending.getSelectedIndex() != 0)
    		{
    			m_CB_Defending.setSelectedIndex(0);
    		}
    	}
    	else if(ie.getSource().equals(m_CB_Nr_Playmaking))
    	{
    		if(m_CB_Nr_Playmaking.getSelectedIndex() != 0)
    		{
    			m_CB_Playmaking.setSelectedIndex(0);
    		}
    	}
    	else if(ie.getSource().equals(m_CB_Nr_Passing))
    	{
    		if(m_CB_Nr_Passing.getSelectedIndex() != 0)
    		{
    			m_CB_Passing.setSelectedIndex(0);
    		}
    	}
    	else if(ie.getSource().equals(m_CB_Nr_Winger))
    	{
    		if(m_CB_Nr_Winger.getSelectedIndex() != 0)
    		{
    			m_CB_Winger.setSelectedIndex(0);
    		}
    	}
    	else if(ie.getSource().equals(m_CB_Nr_Scoring))
    	{
    		if(m_CB_Nr_Scoring.getSelectedIndex() != 0)
    		{
    			m_CB_Scoring.setSelectedIndex(0);
    		}
    	}
    	else if(ie.getSource().equals(m_CB_Nr_SetPieces))
    	{
    		if(m_CB_Nr_SetPieces.getSelectedIndex() != 0)
    		{
    			m_CB_SetPieces.setSelectedIndex(0);
    		}
    	}
    }
    
    public void focusGained(FocusEvent fe)
    {
    	int spielerID;
    	int counter = 0;
    	for(int i = 0; i < m_playerTableModelTop.getRowCount(); i++)
    	{
    		spielerID = ((Integer)m_playerTableModelTop.getValueAt(i,m_playerTableModelTop.getColumnCount()-1)).intValue();
    		ISpieler spieler = m_clModel.getSpieler(spielerID);
    		m_playerTableModelTop.setValueAt(spieler.getTeamInfoSmilie(),i,5);
    		if(spieler.getTeamInfoSmilie().equals("A-Team.png") && m_CB_type.getSelectedIndex() == 6
    			|| spieler.getTeamInfoSmilie().equals("B-Team.png") && m_CB_type.getSelectedIndex() == 7
    			|| spieler.getTeamInfoSmilie().equals("C-Team.png") && m_CB_type.getSelectedIndex() == 8
    			|| spieler.getTeamInfoSmilie().equals("D-Team.png") && m_CB_type.getSelectedIndex() == 9
    			|| spieler.getTeamInfoSmilie().equals("E-Team.png") && m_CB_type.getSelectedIndex() == 10
				|| m_playerTableModelTop.getValueAt(i,0) == Boolean.TRUE
				)
    		{
    			m_playerTableModelTop.setValueAt(Boolean.TRUE,i,0);
    			counter++;
    		}
    		else
    		{
    			m_playerTableModelTop.setValueAt(Boolean.FALSE,i,0);
    		}
    	}
    	m_playerTableModelTop.fireTableDataChanged();
    	if(counter > 0)
    	{
    		actionPerformed(new ActionEvent(m_btCompare,0,""));
    	}
    }
    
    public void focusLost(FocusEvent fe) {}
    
    public void actionPerformed(ActionEvent e)
    {
    	resetPlayer();
    	setDummyPlayerDetails();
    	m_b_refresh = false;
        if(e.getSource().equals(m_btCompare))
        {
        	PlayerCompare.appendText("Compare Button gedr�ckt");
        	m_i_ptmTopCount = m_playerTableModelTop.getRowCount();
        	setNewRating(m_CB_Experience.getSelectedIndex(),
        			m_CB_Form.getSelectedIndex(),
        			m_CB_Stamina.getSelectedIndex(),
        			m_CB_Keeping.getSelectedIndex(),
        			m_CB_Defending.getSelectedIndex(),
        			m_CB_Playmaking.getSelectedIndex(),
        			m_CB_Passing.getSelectedIndex(),
        			m_CB_Winger.getSelectedIndex(),
        			m_CB_Scoring.getSelectedIndex(),
        			m_CB_SetPieces.getSelectedIndex(),
        			m_CB_Loyalty.getSelectedIndex(),
        			m_CB_Homegrown.getSelectedIndex());
        	
        	setChangeRatingBy(m_CB_Nr_Experience.getSelectedIndex(),
        			m_CB_Nr_Form.getSelectedIndex(),
        			m_CB_Nr_Stamina.getSelectedIndex(),
        			m_CB_Nr_Keeping.getSelectedIndex(),
        			m_CB_Nr_Defending.getSelectedIndex(),
        			m_CB_Nr_Playmaking.getSelectedIndex(),
        			m_CB_Nr_Passing.getSelectedIndex(),
        			m_CB_Nr_Winger.getSelectedIndex(),
        			m_CB_Nr_Scoring.getSelectedIndex(),
        			m_CB_Nr_SetPieces.getSelectedIndex(),
        			m_CB_Loyalty.getSelectedIndex(),
        			0
					);
        	
        	int selectedType = m_CB_type.getSelectedIndex();
        	m_V_setPlayers = new Vector<Player>();
        	switch(selectedType)
			{
        		case 0:
        		{
        			for(int i = 0; i < m_i_ptmTopCount; i++)
                	{
                		if(((Boolean)m_playerTableModelTop.getValueAt(i,0)).booleanValue() == true)
                		{
                			fetchPlayer(((Integer)m_playerTableModelTop.getValueAt(i,m_playerTableModelTop.getColumnCount()-1)).intValue());
                		}
                	}
        			break;
        		}
        		case 1:
        		{
        			for(int i = 0; i < m_i_ptmTopCount; i++)
                	{
        				byte tmpPos = 0;
        				
        				try
        				{
        					tmpPos = ((Float)m_playerTableModelTop.getValueAt(i,4)).byteValue();
        				}
        				catch(Exception ex){}
                		if(tmpPos == 0 && m_playerTableModelTop.getValueAt(i,0) == Boolean.TRUE)
                		{
                			fetchPlayer(((Integer)m_playerTableModelTop.getValueAt(i,m_playerTableModelTop.getColumnCount()-1)).intValue());                		
                		}
                		else if(tmpPos == 0 && m_playerTableModelTop.getValueAt(i,0) == Boolean.FALSE)
                		{
                			m_CB_type.setSelectedIndex(0);
                		}
                	}
        			break;
        		}
        		case 2:
        		{
        			for(int i = 0; i < m_i_ptmTopCount; i++)
                	{
        				byte tmpPos = 0;
        				
        				try
        				{
        					tmpPos = ((Float)m_playerTableModelTop.getValueAt(i,4)).byteValue();
        				}
        				catch(Exception ex){}
                		if((tmpPos > 0 && tmpPos < 8)  && m_playerTableModelTop.getValueAt(i,0) == Boolean.TRUE)
                		{
                			fetchPlayer(((Integer)m_playerTableModelTop.getValueAt(i,m_playerTableModelTop.getColumnCount()-1)).intValue());                		
                		}
                		else if((tmpPos > 0 && tmpPos < 8) && m_playerTableModelTop.getValueAt(i,0) == Boolean.FALSE)
                		{
                			m_CB_type.setSelectedIndex(0);
                		}
                	}
        			break;
        		}
        		case 3:
        		{
        			for(int i = 0; i < m_i_ptmTopCount; i++)
                	{
        				byte tmpPos = 0;
        				
        				try
        				{
        					tmpPos = ((Float)m_playerTableModelTop.getValueAt(i,4)).byteValue();
        				}
        				catch(Exception ex){}
                		if((tmpPos > 7 && tmpPos < 12)  && m_playerTableModelTop.getValueAt(i,0) == Boolean.TRUE)
                		{
                			fetchPlayer(((Integer)m_playerTableModelTop.getValueAt(i,m_playerTableModelTop.getColumnCount()-1)).intValue());                		
                		}
                		else if((tmpPos > 7 && tmpPos < 12) && m_playerTableModelTop.getValueAt(i,0) == Boolean.FALSE)
                		{
                			m_CB_type.setSelectedIndex(0);
                		}
                	}
        			break;
        		}
        		case 4:
        		{
        			for(int i = 0; i < m_i_ptmTopCount; i++)
                	{
        				byte tmpPos = 0;
        				
        				try
        				{
        					tmpPos = ((Float)m_playerTableModelTop.getValueAt(i,4)).byteValue();
        				}
        				catch(Exception ex){}
                		if((tmpPos > 11 && tmpPos < 16)  && m_playerTableModelTop.getValueAt(i,0) == Boolean.TRUE)
                		{
                			fetchPlayer(((Integer)m_playerTableModelTop.getValueAt(i,m_playerTableModelTop.getColumnCount()-1)).intValue());                		
                		}
                		else if((tmpPos > 11 && tmpPos < 16) && m_playerTableModelTop.getValueAt(i,0) == Boolean.FALSE)
                		{
                			m_CB_type.setSelectedIndex(0);
                		}
                	}
        			break;
        		}
        		case 5:
        		{
        			for(int i = 0; i < m_i_ptmTopCount; i++)
                	{
        				byte tmpPos = 0;
        				
        				try
        				{
        					tmpPos = ((Float)m_playerTableModelTop.getValueAt(i,4)).byteValue();
        				}
        				catch(Exception ex){}
                		if((tmpPos > 15 && tmpPos < 18) && m_playerTableModelTop.getValueAt(i,0) == Boolean.TRUE)
                		{
                			fetchPlayer(((Integer)m_playerTableModelTop.getValueAt(i,m_playerTableModelTop.getColumnCount()-1)).intValue());
                		}
                		else if((tmpPos > 15 && tmpPos < 18) && m_playerTableModelTop.getValueAt(i,0) == Boolean.FALSE)
                		{
                			m_CB_type.setSelectedIndex(0);
                		}
                	}
        			break;
        		}
        		case 6:
        		{
        			for(int i = 0; i < m_i_ptmTopCount; i++)
                	{
        				String gruppe = "";
        				
        				gruppe = m_playerTableModelTop.getValueAt(i,5).toString();
        				PlayerCompare.appendText("gruppe: "+gruppe);
                		if(gruppe.equals("A-Team.png") && m_playerTableModelTop.getValueAt(i,0) == Boolean.TRUE)
                		{
                			fetchPlayer(((Integer)m_playerTableModelTop.getValueAt(i,m_playerTableModelTop.getColumnCount()-1)).intValue());                		
                		}
                		else if(gruppe.equals("A-Team.png") && m_playerTableModelTop.getValueAt(i,0) == Boolean.FALSE)
                		{
                			m_CB_type.setSelectedIndex(0);
                		}
                	}
        			break;
        		}
        		case 7:
        		{
        			for(int i = 0; i < m_i_ptmTopCount; i++)
                	{
        				String gruppe = "";
        				
        				gruppe = m_playerTableModelTop.getValueAt(i,5).toString();
        				PlayerCompare.appendText("gruppe: "+gruppe);
                		if(gruppe.equals("B-Team.png") && m_playerTableModelTop.getValueAt(i,0) == Boolean.TRUE)
                		{
                			fetchPlayer(((Integer)m_playerTableModelTop.getValueAt(i,m_playerTableModelTop.getColumnCount()-1)).intValue());                		
                		}
                		else if(gruppe.equals("B-Team.png") && m_playerTableModelTop.getValueAt(i,0) == Boolean.FALSE)
                		{
                			m_CB_type.setSelectedIndex(0);
                		}
                	}
        			break;
        		}
        		case 8:
        		{
        			for(int i = 0; i < m_i_ptmTopCount; i++)
                	{
        				String gruppe = "";
        				
        				gruppe = m_playerTableModelTop.getValueAt(i,5).toString();
        				PlayerCompare.appendText("gruppe: "+gruppe);
                		if(gruppe.equals("C-Team.png") && m_playerTableModelTop.getValueAt(i,0) == Boolean.TRUE)
                		{
                			fetchPlayer(((Integer)m_playerTableModelTop.getValueAt(i,m_playerTableModelTop.getColumnCount()-1)).intValue());                		
                		}
                		else if(gruppe.equals("C-Team.png") && m_playerTableModelTop.getValueAt(i,0) == Boolean.FALSE)
                		{
                			m_CB_type.setSelectedIndex(0);
                		}
                	}
        			break;
        		}
        		case 9:
        		{
        			for(int i = 0; i < m_i_ptmTopCount; i++)
                	{
        				String gruppe = "";
        				
        				gruppe = m_playerTableModelTop.getValueAt(i,5).toString();
        				PlayerCompare.appendText("gruppe: "+gruppe);
                		if(gruppe.equals("D-Team.png") && m_playerTableModelTop.getValueAt(i,0) == Boolean.TRUE)
                		{
                			fetchPlayer(((Integer)m_playerTableModelTop.getValueAt(i,m_playerTableModelTop.getColumnCount()-1)).intValue());
                		}
                		else if(gruppe.equals("D-Team.png") && m_playerTableModelTop.getValueAt(i,0) == Boolean.FALSE)
                		{
                			m_CB_type.setSelectedIndex(0);
                		}
                	}
        			break;
        		}
        		case 10:
        		{
        			for(int i = 0; i < m_i_ptmTopCount; i++)
                	{
        				String gruppe = "";
        				
        				gruppe = m_playerTableModelTop.getValueAt(i,5).toString();
        				PlayerCompare.appendText("gruppe: "+gruppe);
                		if(gruppe.equals("E-Team.png") && m_playerTableModelTop.getValueAt(i,0) == Boolean.TRUE)
                		{
                			fetchPlayer(((Integer)m_playerTableModelTop.getValueAt(i,m_playerTableModelTop.getColumnCount()-1)).intValue());
                		}
                		else if(gruppe.equals("E-Team.png") && m_playerTableModelTop.getValueAt(i,0) == Boolean.FALSE)
                		{
                			m_CB_type.setSelectedIndex(0);
                		}
                	}
        			break;
        		}
        		case 11:
        		{
        			for(int i = 0; i < m_i_ptmTopCount; i++)
                	{
        				fetchPlayer(((Integer)m_playerTableModelTop.getValueAt(i,m_playerTableModelTop.getColumnCount()-1)).intValue());
        			}
        		}
			}
        	
        	// Create array from a tablemodel vector
        	m_ar_setPlayers = new Player[m_V_setPlayers.size()];
        	for(int counter = 0; counter < m_ar_setPlayers.length; counter++)
        	{
        		m_ar_setPlayers[counter] = (Player)m_V_setPlayers.elementAt(counter);
        	}
        	
        	PlayerCompare.appendText("ActionListener m_btCompare");
        	m_playerTableModelBottom = new PlayerTableModel(m_clModel,m_ar_setPlayers,2);
    		TableSorter sorter2 = new TableSorter(m_playerTableModelBottom); //ADDED THIS
        	m_jTableBottom = new PlayerTable(sorter2,m_clModel,m_playerTableModelBottom);
        	m_jTableBottom.setRowSelectionAllowed(true);
        	m_jTableBottom.addMouseListener(this);
        	
        	sorter2.setTableHeader(m_jTableBottom.getTableHeader()); //ADDED THIS
        	
        	m_scrollPaneTableBottom.setViewportView(m_jTableBottom);
    		m_scrollPaneTableBottom.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    		m_scrollPaneTableBottom.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    		
        }
        
        if ( e.getSource ().equals ( m_btDebugWindow ) )
        {
            debugWindow.setVisible ( true );
        }
        
        if(e.getSource().equals(m_btHelp))
        {
            JFrame helpFrame = new JFrame("Help for plugin 'PlayerCompare'");
            
            JTextArea helpArea = new JTextArea();
            String [] zeile = new String[14];
            if((m_clModel.getHelper().getLanguageID()) == 1)
            {
            	zeile[0]="PlayerCompare - Hilfe:\n\nDieses PlugIn soll helfen, Spieler nach pers\u00F6nlicher Auswahl, Gruppen (A - E) oder nach Position gruppiert besser vergleichen zu k\u00F6nnen.";
            	zeile[1]="\nDazu muss eine Gruppe gew\u00E4hlt werden und dann die einzelnen Skills nach Belieben ver\u00E4ndert werden.";
            	zeile[2]="\nEine Gruppe kann entweder aus selbst selektierten Spielern der Tabelle, oder nach Position bzw. Gruppe gew\u00E4hlten Spielern bestehen.";
            	zeile[3]="\nZum ver\u00E4ndern der einzelnen Skills habt ihr 2 M\u00F6glichkeiten:";
            	zeile[4]="\n1) Neben den Skills befindet sich ein DropDown Menu, in dem ihr die St\u00E4rke einstellen k\u00F6nnt, welche f\u00FCr alle gew\u00E4hlten Spieler verwendet wird (z.B. passabel, gut, usw.).";
            	zeile[5]="\n2) Rechts daneben befindet sich ein weiteres DropDown Menu, in dem die St\u00E4rke mit einem Wert ver\u00E4ndert wird (z.B. +1, +2 usw.). Das bewirkt, das bei den gew\u00E4hlten Spielern der Skill um den eingestellten Faktor erh\u00F6ht wird.";
            	zeile[6]="\nDamit kann man z.B. testen, wie stark ein oder mehrere Spieler w\u00E4ren, wenn sie im Training steigen w\u00FCrden.";
            	zeile[7]="\nPro Skill kann immer nur eine der beiden M\u00F6glichkeiten verwendet werden, es ist aber m\u00F6glich, bei verschiedenen Skills zu mischen, z.B. 'passabel' bei Form und '+1' bei Torwart.";
            	zeile[8]="\nIst bei einem Skill '---' und '+0' gew\u00E4hlt, so wird er nicht ver\u00E4ndert und fliesst mit dem Originalwert des jeweiligen Spielers in seine Berechnung ein.";
            	zeile[9]="\nDie Berechnung wird dann Aufgrund der eingestellten Skillst\u00E4rken durchgef\u00FChrt und das Ergebnis im unteren Teil angezeigt.";
            	zeile[10]="\nDie Pfeile hinter den ver\u00E4nderten Skillwerten zeigen die Ver\u00E4nderung zum Originalwert des Spielers an";
            	zeile[11]="\nWenn ein Spieler in der unteren Tabelle angeklickt wird, so werden rechts seine Werte f\u00FCr alle Positionen angezeigt, sowohl die vor der Berechnung (Original) als auch die nach der Neuberechnung (Ge\u00E4ndert).";
            	zeile[12]="\nAuf diese Weise ist es m\u00F6glich, zu sehen, ob und wie sich ein Spieler auf einer Position verbessert und verschlechtert und welche seine neue zweitbeste Position w\u00E4re.";
            	zeile[13]="\n\nSollten Fragen oder Probleme auftauchen, schickt bitte eine mail an kickmuck@gmx.de oder poste ins Forum.";
            	helpArea.append("PlayerCompare - Hilfe:\n\nDieses PlugIn soll helfen, Spieler nach pers\u00F6nlicher Auswahl, Gruppen (A - E) oder nach Position gruppiert besser vergleichen zu k\u00F6nnen.");
            	helpArea.append("\nDazu muss eine Gruppe gew\u00E4hlt werden und dann die einzelnen Skills nach Belieben ver\u00E4ndert werden.");
            	helpArea.append("\nEine Gruppe kann entweder aus selbst selektierten Spielern der Tabelle, oder nach Position bzw. Gruppe gew\u00E4hlten Spielern bestehen.");
            	helpArea.append("\nZum ver\u00E4ndern der einzelnen Skills habt ihr 2 M\u00F6glichkeiten:");
            	helpArea.append("\n1) Neben den Skills befindet sich ein DropDown Menu, in dem ihr die St\u00E4rke einstellen k\u00F6nnt, welche f\u00FCr alle gew\u00E4hlten Spieler verwendet wird (z.B. passabel, gut, usw.).");
            	helpArea.append("\n2) Rechts daneben befindet sich ein weiteres DropDown Menu, in dem die St\u00E4rke mit einem Wert ver\u00E4ndert wird (z.B. +1, +2 usw.). Das bewirkt, das bei den gew\u00E4hlten Spielern der Skill um den eingestellten Faktor erh\u00F6ht wird.");
            	helpArea.append("\nDamit kann man z.B. testen, wie stark ein oder mehrere Spieler w\u00E4ren, wenn sie im Training steigen w\u00FCrden.");
            	helpArea.append("\nPro Skill kann immer nur eine der beiden M\u00F6glichkeiten verwendet werden, es ist aber m\u00F6glich, bei verschiedenen Skills zu mischen, z.B. 'passabel' bei Form und '+1' bei Torwart.");
            	helpArea.append("\nIst bei einem Skill '---' und '+0' gew\u00E4hlt, so wird er nicht ver\u00E4ndert und fliesst mit dem Originalwert des jeweiligen Spielers in seine Berechnung ein.");
            	helpArea.append("\nDie Berechnung wird dann Aufgrund der eingestellten Skillst\u00E4rken durchgef\u00FChrt und das Ergebnis im unteren Teil angezeigt.");
            	helpArea.append("\nDie Pfeile hinter den ver\u00E4nderten Skillwerten zeigen die Ver\u00E4nderung zum Originalwert des Spielers an");
            	helpArea.append("\nWenn ein Spieler in der unteren Tabelle angeklickt wird, so werden rechts seine Werte f\u00FCr alle Positionen angezeigt, sowohl die vor der Berechnung (Original) als auch die nach der Neuberechnung (Ge\u00E4ndert).");
            	helpArea.append("\nAuf diese Weise ist es m\u00F6glich, zu sehen, ob und wie sich ein Spieler auf einer Position verbessert und verschlechtert und welche seine neue zweitbeste Position w\u00E4re.");
            	helpArea.append("\n\nSollten Fragen oder Probleme auftauchen, schickt bitte eine mail an kickmuck@gmx.de oder poste ins Forum.");
            }
            else if((m_clModel.getHelper().getLanguageID()) == 4)
            {
            	helpArea.append("Srovn\u00E1n\u00ED hr\u00E1\u00E8\u00F9 - pomoc:\n\nTento plugin ti pom\u00E1h\u00E1 srovnat hr\u00E1\u00E8e, kter\u00E9 si m\u00F9\u017Ee\u0161 seskupit jejich jednotliv\u00FDm vybr\u00E1n\u00EDm nebo v\u00FDb\u00ECrem pozice \u00E8i skupiny (A - E).");
            	helpArea.append("\nNejd\u00F8\u00EDve vyber skupinu hr\u00E1\u00E8\u00F9 (pou\u017Eij 'vybran\u00ED', skupiny nebo dle pozice), pot\u00E9 vyber dovednosti k p\u00F8epo\u00E8\u00EDt\u00E1n\u00ED.");
            	//helpArea.append("\nEine Gruppe kann entweder aus selbst selektierten Spielern der Tabelle, oder nach Position bzw. Gruppe gew\u00E4hlten Spielern bestehen.");
            	helpArea.append("\nM\u00E1\u0161 dv\u00EC mo\u017Enosti pro zm\u00ECnu hodnoty dovednost\u00ED:");
            	helpArea.append("\n1) Vedle dovednost\u00ED se nach\u00E1z\u00ED rolovac\u00ED menu, kter\u00E9 obsahuje r\u00F9zn\u00E9 hodnoty (nap\u00F8. passable), t\u00EDm zm\u00ECn\u00ED\u0161 dovednosti ka\u017Ed\u00E9ho hr\u00E1\u00E8e na vybranou hodnotu.");
            	helpArea.append("\n2) Hned vedle je druh\u00E1 rolovac\u00ED nab\u00EDdka, kde m\u00F9\u017Ee\u0161 zm\u00ECnit hodnotu dovednosti o ur\u00E8itou \u00FArove\u00F2 (+1, +2). T\u00EDm zv\u00FD\u0161\u00ED\u0161 individu\u00E1ln\u00ED hodnotu vybran\u00E9ho hr\u00E1\u00E8e o vybranou hodnotu.");
            	helpArea.append("\nToto m\u00F9\u017Ee\u0161 pou\u017E\u00EDt pro kontrolu s\u00EDly pozice, jestli\u017Ee hr\u00E1\u00E8 zlep\u0161\u00ED svou dovednost tr\u00E9ningem.");
            	helpArea.append("\nPro ka\u017Edou dovednost m\u00F9\u017Ee\u0161 pou\u017E\u00EDt pouze jednu z obou mo\u017Enost\u00ED, ale m\u00F9\u017Ee\u0161 kombinovat r\u00F9zn\u00E9 mo\u017Enosti pro r\u00F9zn\u00E9 dovednosti, nap\u00F8. 'passable' pro sk\u00F3rov\u00E1n\u00ED a '+1' pro kondici.");
            	helpArea.append("\nJestli\u017Ee je dovednost nastavena na '---' a '+0', pro p\u00F8epo\u00E8et bude br\u00E1na v \u00FAvahu jej\u00ED p\u00F9vodn\u00ED hodnota.");
            	helpArea.append("\nV\u00FDsledek p\u00F8epo\u00E8tu se zobraz\u00ED v doln\u00ED \u00E8\u00E1sti obrazovky.");
            	helpArea.append("\n\u0160ipky za nov\u00EC nastaven\u00FDmi dovednostmi zn\u00E1zor\u00F2uj\u00ED rozd\u00EDl ve srovn\u00E1n\u00ED se s\u00EDlou hr\u00E1\u00E8e s p\u00F9vodn\u00EDma hodnotama.");
            	helpArea.append("\nJestli\u017Ee vybere\u0161 jednoho z hr\u00E1\u00E8\u00F9 z doln\u00ED tabulky, na prav\u00E9 stran\u00EC uvid\u00ED\u0161 detaily pro v\u0161echny pozice p\u00F8ed (p\u00F9vodn\u00ED) a po (zm\u00ECn\u00ECn\u00E9) p\u00F8epo\u00E8tu.");
            	helpArea.append("\nNyn\u00ED vid\u00ED\u0161, kde se hr\u00E1\u00E8 zlep\u0161\u00ED a jak\u00E1 m\u00F9\u017Ee b\u00FDt jeho nov\u00E1 nejlep\u0161\u00ED \u00E8i druh\u00E1 nejlep\u0161\u00ED pozice.");
            	helpArea.append("\n\nJesli\u017Ee m\u00E1\u0161 jak\u00E9koliv probl\u00E9my \u00E8i dotazy, po\u0161li e-mail na kickmuck@gmx.de nebo pou\u017Eij diskusn\u00ED skupiny.");
            }
            else if((m_clModel.getHelper().getLanguageID()) == 7)
            {
            	helpArea.append("PlayerCompare - Guida:\n\nQuesto plugin aiuta a confrontare giocatori, raggruppati selezionandoli, scegliendoli per posizione o per Squadra (A-E).");
				helpArea.append("\nInnanzitutto bisogna selezionare un gruppo di giocatori; dal men\u00F9 in alto a destra ('Raggruppa per') scegli  'Selezionati', una Squadra (A-E) o per posizione (Portiere, Difesa,...), poi bisogna scegliere le skill da ricalcolare.");
				helpArea.append("\nCi sono due possibilit\u00E0 per cambiare il valore di una skill:");
				helpArea.append("\n1. A fianco di ciascuna skill, si trova un menu a tendina che mostra i diversi livelli (p.es. accettabile), in questo modo si cambia la skill per ogni giocatore al livello scelto.");
				helpArea.append("\n2. Accanto a destra c'\u00E8 un secondo menu a tendina con cui si pu\u00F2 cambiare il livello in base ai valori (+0, +1, +2...). In questo modo vengono aumentati i livelli individuali dei giocatori scelti della quantit\u00E0 selezionata.");
				helpArea.append("\nSi pu\u00F2 usare questa modalit\u00E0 per controllare di quanto si rafforza il giocatore allenandolo in una determinata posizione.");
				helpArea.append("\nSi pu\u00F2 usare solo una di queste due opzioni per ogni skill, ma si possono fare dei mix tra skill diverse, p.es. 'accettabile' per attacco e '+1' per resistenza.");
				helpArea.append("\nSe una skill \u00E8 impostata a '---' e '+0', nel calcolo verr\u00E0 usato il livello originale per ogni giocatore in questa skill.");
				helpArea.append("\nIl risultato del calcolo sar\u00E0 mostrato nella parte inferiore sinistra della finestra.");
				helpArea.append("\nLe frecce a fianco delle nuove skill indicano i cambiamenti rispetto ai livelli originali del giocatore.");
				helpArea.append("\nSe si seleziona un giocatore nella parte inferiore della finestra, si possono vedere i dettagli per tutte le posizioni nel quadrante in basso a destra, prima (Originale) e dopo (Modificato) il calcolo.");
            	helpArea.append("\nIn questo modo si pu\u00F2 vedere come cambier\u00E0 il giocatore e quali potrebbero essere le sue posizioni migliori.");
            	helpArea.append("\n\nIf you have any problems or questions please mail to kickmuck@gmx.de or post it in the discussion group.");
            }
            else if((m_clModel.getHelper().getLanguageID()) == 8)
			{
				helpArea.append("PlayerCompare - Help:\n\nDeze plugin helpt je met het vergelijken van spelers door te vergelijken op positie of een groep (A - E).");
				helpArea.append("\nAls eerste selecteer je een groep spelers (een team of een positie), daarna selecteer je de skill om te vergelijken.");
				helpArea.append("\nEr zijn 2 mogelijkheden om een skill te veranderen:");
				helpArea.append("\n1. Naast de vaardigeheden vind je een pulldown menu met verschillende sterktes (redelijk enz.). Dit verandert de vaardigheid voor elke speler tot de gekozen skill.");
				helpArea.append("\n2. Rechts naast het bovengenoemde is een 2e pulldown menu waarmee je de waardes van de spelers met bijv. 1 of 2 stappen kunt verhogen. Dit verhoogt de individuele waardes van de gekozen spelers.");
				helpArea.append("\nDit is te gebruiken om te kijken wat de nieuwe waardes de spelers worden als ze door training beter worden.");
				helpArea.append("\nHet is mogelijk om meerdere skills met elkaar te mixen en dan te vergelijken. Bijv. 'redelijk' voor scoren en '+1' voor conditie.");
				helpArea.append("\nAls voor een skill '---' en '+0' gebruikt wordt zal de origenele sterkte van de spelers gebruikt worden in de berekening.");
				helpArea.append("\nHet resultaat van de berekening wordt gepresenteerd onderaan het scherm.");
				helpArea.append("\nDe pijlen achter de nieuwe skills geven aan wat de speler moet stijgen in verhouding tot de huidige waardes.");
				helpArea.append("\nAls er slechts 1 speler is gekozen om te vergelijken dan zie je die speler in detail aan de rechterkant, voor (origineel) en na (nieuw) de berekening.");
            	helpArea.append("\nNu kun je zien hoe een speler beter wordt en wat dan zijn beste en 1 na beste positie zullen worden.");
            	helpArea.append("\n\nAls er problemen of vragen zijn meel me dan op kickmuck@gmx.de of plaats het in de daarvoor bedoelde fora.");
			}
            else
            {
            	helpArea.append("PlayerCompare - help:\n\nThis plugin helps you comparing players that you can group by checking them or by choosing a position or group (A - E).");
				helpArea.append("\nFirst select a group of players (use 'checked', a squad or 'by position'), then choose the skills to recalculate.");
				helpArea.append("\nYou have two possibilities for changing a skill value:");
				helpArea.append("\n1. Beside the skills, you'll find a dropdown which holds the different strengths (e.g. passable), this changes the skill for each player to the chosen strength.");
				helpArea.append("\n2. Right beside this there is a second dropdown where you can change the strength by value (+1, +2). This will increase the individual strength of the chosen player by the selected value.");
				helpArea.append("\nYou can use this for checking the position strength if the players gain strength by training.");
				helpArea.append("\nYou can only use one of the two possibilities per skill, but it is possible to mix with different skills, e.g. 'passable' for scoring and '+1' for stamina.");
				helpArea.append("\nIf a skill is set to '---' and '+0', the original strength of each player in this skill will be kept and be used for recalculation.");
				helpArea.append("\nThe result of the recalculation will be displayed at the bottom of the screen.");
				helpArea.append("\nThe Arrows behind the new set skills indicate the change compared to the players strength before recalculation.");
				helpArea.append("\nIf you choose one of the players in the bottom table, you'll see the details for all positions on the right side, before (original) and after (changed) recalculation.");
            	helpArea.append("\nNow you can see where a player will increase and what his new best or second best position might be.");
            	helpArea.append("\n\nIf you have any problems or questions please mail to kickmuck@gmx.de or post it in the discussion group.");
            }
            helpArea.append("\n\nThis plugin \u00A9 by KickMuck, Manager of Schwarz-Rot Langenbach(VI.683), home of the pirates");
            helpArea.append("\nThanx to my supporters and beta-testers lecker sissy und fireace, and to all my helpers with translation:");
            helpArea.append("\nSzykmistrz -> Polish\n/Painstorm -> Danish\nAntonio -> Italian\npejeeha -> French\n");
            
            
            helpArea.setEditable(false);
            helpArea.setLineWrap(true);
            helpArea.setWrapStyleWord(true);
            
            JScrollPane helpSP = new JScrollPane(helpArea);
            helpSP.setPreferredSize(new Dimension(250, 500));
            helpFrame.getContentPane().add(helpSP, "Center");
            helpFrame.setSize(700, 400);
            helpFrame.setVisible(true);
        }
        
        if(e.getSource().equals(m_btReset))
        {
        	setNewRating(0,0,0,0,0,0,0,0,0,0,0,0);
        	m_CB_Experience.setSelectedIndex(0);
        	m_CB_Form.setSelectedIndex(6);
        	m_CB_Stamina.setSelectedIndex(0);
        	m_CB_Keeping.setSelectedIndex(0);
        	m_CB_Defending.setSelectedIndex(0);
        	m_CB_Playmaking.setSelectedIndex(0);
        	m_CB_Passing.setSelectedIndex(0);
        	m_CB_Winger.setSelectedIndex(0);
        	m_CB_Scoring.setSelectedIndex(0);
        	m_CB_SetPieces.setSelectedIndex(0);
        	m_CB_Loyalty.setSelectedIndex(10);
        	m_CB_Homegrown.setSelectedIndex(0);
        	m_CB_type.setSelectedIndex(0);
        	
        	setChangeRatingBy(0,0,0,0,0,0,0,0,0,0,0,0);
        	m_CB_Nr_Experience.setSelectedIndex(0);
        	m_CB_Nr_Form.setSelectedIndex(0);
        	m_CB_Nr_Stamina.setSelectedIndex(0);
        	m_CB_Nr_Keeping.setSelectedIndex(0);
        	m_CB_Nr_Defending.setSelectedIndex(0);
        	m_CB_Nr_Playmaking.setSelectedIndex(0);
        	m_CB_Nr_Passing.setSelectedIndex(0);
        	m_CB_Nr_Winger.setSelectedIndex(0);
        	m_CB_Nr_Scoring.setSelectedIndex(0);
        	m_CB_Nr_SetPieces.setSelectedIndex(0);
        	m_CB_Nr_Loyalty.setSelectedIndex(0);
        	m_scrollPaneTableBottom.setViewportView(null);
        	
        	for(int i = 0; i < m_i_ptmTopCount; i++)
        	{
        		if(((Boolean)m_playerTableModelTop.getValueAt(i,0)).booleanValue() == true)
        		{
        			m_playerTableModelTop.setValueAt(Boolean.FALSE,i,0);
        		}
        	}
        	resetPlayer();
        	setDummyPlayerDetails();
        	m_clModel.getGUI().doRefresh();
        }
    } 
    /**
     * 
     * getAllPlayers():
     * - Fetches all players via the MiniModel
     *
     */
    public void getAllPlayers()
    {
		m_V_allPlayers = m_clModel.getAllSpieler();
		m_numberOfPlayers = m_V_allPlayers.size();
		m_ar_allPlayers = new Player[m_numberOfPlayers];
		for(int counter = 0; counter < m_numberOfPlayers; counter++)
		{
			m_ar_allPlayers[counter] = new Player((ISpieler)m_V_allPlayers.elementAt(counter));
		}
    }
    
    /**
     * fetchPlayer(int id)
     * - searches for a player with id in the player array
     */
    public void fetchPlayer(int id)
    {
    	for(int i = 0; i < m_ar_allPlayers.length; i++)
    	{
    		if(m_ar_allPlayers[i].getId() == id)
    		{
    			m_ar_allPlayers[i].changePlayerSkillValues(true);
    			m_V_setPlayers.addElement(m_ar_allPlayers[i]);
    		}
    	}
    }
    
    public void resetPlayer()
    {
    	for(int i = 0; i < m_ar_allPlayers.length; i++)
    	{
    		m_ar_allPlayers[i].resetPlayers();
    	}
    }
    
    public void setDummyPlayerDetails()
    {
    	Player dummy = new Player();
    	JLabel l_SpielerName = new JLabel();
    	l_SpielerName.setPreferredSize(new Dimension(100,30));
    	l_SpielerName.setText(m_properties.getProperty("spielername"));
    	l_SpielerName.setOpaque(true);
    	
    	JLabel platzhalter = new JLabel();
    	platzhalter.setText("  ");
    	
    	JPanel p_SpielerName = new JPanel(new BorderLayout());
    	p_SpielerName.setPreferredSize(new Dimension(150,30));
    	p_SpielerName.add(l_SpielerName);
    	p_SpielerName.add(platzhalter,BorderLayout.WEST);
   
    	p_PlayerDetail.removeAll();
    	p_PlayerDetail.setPreferredSize(new Dimension(150,40));
 
    	m_playerTableModelDetail = null;
    	
    	m_scrollPanePlayer.setViewportView(null);
    	m_scrollPanePlayer.validate();
    	m_playerTableModelDetail = new PlayerTableModel(m_clModel,dummy);
		
		TableSorter sorter3 = new TableSorter(m_playerTableModelDetail); //ADDED THIS
		m_jTableDetail = new PlayerTable(sorter3,m_clModel,m_playerTableModelDetail,true);
		sorter3.setTableHeader(m_jTableDetail.getTableHeader());
		m_scrollPanePlayer.setViewportView(m_jTableDetail);
		p_PlayerDetail.add(p_SpielerName,BorderLayout.NORTH);
		p_PlayerDetail.add(m_scrollPanePlayer);
		p_PlayerDetail.validate();
		m_scrollPanePlayerGesamt.setViewportView(p_PlayerDetail);
		m_scrollPanePlayerGesamt.validate();
    }
    
   
    
    /**
     * setNewStaerke()
     * - setzt die neuen Werte f�r die eingestellten Skills
     */
    public void setNewRating(int er, int fo, int ko, int tw, int ve, int sa,int ps, int fl, int ts, int st, int loy, int hg)
    {
    	newRating[0] = er;
    	newRating[1] = fo;
    	newRating[2] = ko;
    	newRating[3] = tw;
    	newRating[4] = ve;
    	newRating[5] = sa;
    	newRating[6] = ps;
    	newRating[7] = fl;
    	newRating[8] = ts;
    	newRating[9] = st;
    	newRating[10] = loy;
    	newRating[11] = hg;
    }
    
    public void setChangeRatingBy(int er, int fo, int ko, int tw, int ve, int sa,int ps, int fl, int ts, int st, int loy, int hg)
    {
    	PlayerCompare.appendText("in setChangeRatingBy()");
    	changedRating[0] = er;
    	changedRating[1] = fo;
    	changedRating[2] = ko;
    	changedRating[3] = tw;
    	changedRating[4] = ve;
    	changedRating[5] = sa;
    	changedRating[6] = ps;
    	changedRating[7] = fl;
    	changedRating[8] = ts;
    	changedRating[9] = st;
    	changedRating[10] = loy;
    	changedRating[11] = hg;
    }

    public static int[] getNewRating()
    {
    	return newRating;
    }
    
    public static int[] getChangeRatingBy()
    {
    	return changedRating;
    }
    
    public static int getSelectedTypeIndex()
    {
    	return m_CB_type.getSelectedIndex();
    }
    
    public static void setSelectedTypeIndex(int index)
    {
    	m_CB_type.setSelectedIndex(index);
    }
    
    public void setSelectedRow()
    {
    	m_selectedRow = m_jTableBottom.getSelectedRow();
    }
    
    public int getSelectedRow()
    {
    	return m_selectedRow;
    }
//	********** Begin Mouse Events ******************
    public void mouseClicked(MouseEvent e)
	{
    	JLabel l_SpielerName = new JLabel();
    	JLabel platzhalter = new JLabel("  ");
    	JPanel p_SpielerName = new JPanel(new BorderLayout());
    	l_SpielerName.setPreferredSize(new Dimension(100,30));
    	p_PlayerDetail.removeAll();
    	p_PlayerDetail.setPreferredSize(new Dimension(150,30));
    	p_PlayerDetail.setBackground(lightblue);
    	Player tmpPlayer = null;
    	m_playerTableModelDetail = null;
    	m_scrollPanePlayer.setViewportView(null);
    	m_scrollPanePlayer.validate();
    	setSelectedRow();
		int row = getSelectedRow();
		String id = "" + m_jTableBottom.getValueAt(row,m_jTableBottom.getColumnCount()-1);
		int tmpAnzahl = m_V_setPlayers.size();
		for(int u = 0; u < tmpAnzahl; u++)
		{
			tmpPlayer = (Player)m_V_setPlayers.elementAt(u);
			if(("" + tmpPlayer.getId()).compareTo(id)==0)
			{
				l_SpielerName.setText("" + tmpPlayer.getName());
				break;
			}
		}
		p_SpielerName.add(platzhalter,BorderLayout.WEST);
		p_SpielerName.add(l_SpielerName);
		p_PlayerDetail.add(p_SpielerName,BorderLayout.NORTH);
		m_playerTableModelDetail = new PlayerTableModel(m_clModel,tmpPlayer);
		
		TableSorter sorter3 = new TableSorter(m_playerTableModelDetail); //ADDED THIS
		m_jTableDetail = new PlayerTable(sorter3,m_clModel,m_playerTableModelDetail,true);
		sorter3.setTableHeader(m_jTableDetail.getTableHeader());
		m_scrollPanePlayer.setViewportView(m_jTableDetail);
		p_PlayerDetail.add(m_scrollPanePlayer);
		p_PlayerDetail.validate();
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
//  ********** End Mouse Events ******************
    
    /**
     * Liefert die Plugin-ID
     * @return Die zugewiesene ID für dieses Plugin
     */
    public int getPluginID()
    {
    	return m_pluginID;
    }
    
    /**
     * Liefert den Plugin-Namen
     * @return Gibt den Namen des Plugins zurück.
     */
    public String getPluginName()
    {
    	return m_PluginName;
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
     * Liefert eine Liste der Dateien, die nicht überschrieben werden dürfen.
     * @return Ein Array von Files, die nicht überschrieben werden dürfen.
     */
    public File[] getUnquenchableFiles()
    {
    	return null;
    }
    
    public static String getPCProperties(String prop)
    {
    	return m_properties.getProperty(prop);
    }
}
