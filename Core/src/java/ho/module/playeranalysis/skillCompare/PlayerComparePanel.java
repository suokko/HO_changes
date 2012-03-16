/*
 * Created on 19.06.2004
 */
package ho.module.playeranalysis.skillCompare;

import ho.core.datatype.CBItem;
import ho.core.gui.RefreshManager;
import ho.core.gui.comp.panel.ImagePanel;
import ho.core.model.HOVerwaltung;
import ho.core.model.UserParameter;
import ho.core.util.Helper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import plugins.IRefreshable;
import plugins.ISpieler;

/**
 * @author KickMuck
 */
public class PlayerComparePanel extends ImagePanel implements  MouseListener, ActionListener,ItemListener,IRefreshable, FocusListener {
	
	private static final long serialVersionUID = -1629490436656226196L;
	//Members for Tables
	private PlayerTable m_jTableTop = null;	//Table for all players
	private JTable m_jTableBottom = null;	//Table for all compared players
	private JTable m_jTableDetail = null;	//Table for player details
	// Members for Buttons
	private JButton m_btCompare;	//Button -> Compare
	private JButton m_btReset;		//Button -> Reset
	
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
	
	private Vector<Player> m_V_setPlayers;
	private Vector<ISpieler> m_V_allPlayers;
	private Player[] m_ar_allPlayers;
	private Player[] m_ar_setPlayers;
	
	
	private CBItem[] m_rating = Helper.EINSTUFUNG;
	
	private static int m_selectedRow;
	private int m_i_ptmTopCount;
	private int m_numberOfPlayers;
	
	private static int []newRating;
	private static int []changedRating;
	
	private boolean m_b_refresh = true;
		
	private Color lightblue = new Color (235,235,255);
	
	public PlayerComparePanel(){
		initialize();
	}
	
	/**
	 * Is called by HO! to start the plugin
	 */
	private void initialize() {             
        // get all players ans save them in an array
        getAllPlayers();
		
        setLayout (new BorderLayout());
		addFocusListener(this);
		// Register tab for refresh
		RefreshManager.instance().registerRefreshable(this);
		
		//Default values for skill selection
		newRating = new int[]{0,6,0,0,0,0,0,0,0,0,10,0};
		
		// Default values for skill changes
		changedRating = new int[]{0,0,0,0,0,0,0,0,0,0,0,0};
		
		HOVerwaltung hoV = HOVerwaltung.instance();
		// Set the labels
		m_L_Experience = new JLabel(hoV.getLanguageString("Erfahrung"));
		m_L_Form = new JLabel(hoV.getLanguageString("Form"));
		m_L_Stamina = new JLabel(hoV.getLanguageString("Kondition"));
		m_L_Keeping = new JLabel(hoV.getLanguageString("Torwart"));
		m_L_Defending = new JLabel(hoV.getLanguageString("Verteidigung"));
		m_L_Playmaking = new JLabel(hoV.getLanguageString("Spielaufbau"));
		m_L_Passing = new JLabel(hoV.getLanguageString("Passpiel"));
		m_L_Winger = new JLabel(hoV.getLanguageString("Fluegelspiel"));
		m_L_Scoring = new JLabel(hoV.getLanguageString("Torschuss"));
		m_L_SetPieces = new JLabel(hoV.getLanguageString("Standards"));
		m_L_Loyalty = new JLabel(hoV.getLanguageString("Loyalty"));
		m_L_HomeGrown = new JLabel(hoV.getLanguageString("Motherclub"));
		
		// Create table model for top table
		m_playerTableModelTop = new PlayerTableModel( m_ar_allPlayers, 1);
		TableSorter sorter = new TableSorter(m_playerTableModelTop); 
		m_jTableTop = new PlayerTable(sorter, m_playerTableModelTop);
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

		m_btCompare = new JButton(hoV.getLanguageString("Vergleichen"));
		m_btCompare.addActionListener(this);
		m_btCompare.setToolTipText(hoV.getLanguageString("ttCompare"));
		
		m_btReset = new JButton("Reset");
		m_btReset.setToolTipText(hoV.getLanguageString("Reset"));
		m_btReset.addActionListener(this);
		
		m_L_GroupBy = new JLabel(hoV.getLanguageString("vergleichen_alle"));
		m_L_Header = new JLabel(hoV.getLanguageString("setze_alle"));
		
		//Create controls
		m_CB_type = new JComboBox();
		m_CB_type.addItem(hoV.getLanguageString("gewaehlte"));
		m_CB_type.addItem(hoV.getLanguageString("Torwart"));
		m_CB_type.addItem(hoV.getLanguageString("Verteidigung"));
		m_CB_type.addItem(hoV.getLanguageString("Mittelfeld"));
		m_CB_type.addItem(hoV.getLanguageString("Fluegelspiel"));
		m_CB_type.addItem(hoV.getLanguageString("Sturm"));
		m_CB_type.addItem(hoV.getLanguageString("GruppeA"));
		m_CB_type.addItem(hoV.getLanguageString("GruppeB"));
		m_CB_type.addItem(hoV.getLanguageString("GruppeC"));
		m_CB_type.addItem(hoV.getLanguageString("GruppeD"));
		m_CB_type.addItem(hoV.getLanguageString("GruppeE"));
		m_CB_type.addItem(hoV.getLanguageString("GruppeF"));
		m_CB_type.addItem(hoV.getLanguageString("alle"));
		m_CB_type.addItemListener(this);
		
		// Experience
		m_CB_Experience = createComboBox();
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
		m_CB_Keeping = createComboBox();
		
		m_CB_Nr_Keeping = new JComboBox();
		fillComboBox(m_CB_Nr_Keeping);
		m_CB_Nr_Keeping.setSelectedIndex(0);
		m_CB_Nr_Keeping.addItemListener(this);
		// Defending
		m_CB_Defending = createComboBox();
		m_CB_Nr_Defending = new JComboBox();
		fillComboBox(m_CB_Nr_Defending);
		m_CB_Nr_Defending.setSelectedIndex(0);
		m_CB_Nr_Defending.addItemListener(this);
		// Playmaking
		m_CB_Playmaking = createComboBox();
		m_CB_Nr_Playmaking = new JComboBox();
		fillComboBox(m_CB_Nr_Playmaking);
		m_CB_Nr_Playmaking.setSelectedIndex(0);
		m_CB_Nr_Playmaking.addItemListener(this);
		// Passing
		m_CB_Passing = createComboBox();
		m_CB_Nr_Passing = new JComboBox();
		fillComboBox(m_CB_Nr_Passing);
		m_CB_Nr_Passing.setSelectedIndex(0);
		m_CB_Nr_Passing.addItemListener(this);
		// Winger
		m_CB_Winger = createComboBox();
		m_CB_Nr_Winger = new JComboBox();
		fillComboBox(m_CB_Nr_Winger);
		m_CB_Nr_Winger.setSelectedIndex(0);
		m_CB_Nr_Winger.addItemListener(this);
		// Scoring
		m_CB_Scoring = createComboBox();
		m_CB_Nr_Scoring = new JComboBox();
		fillComboBox(m_CB_Nr_Scoring);
		m_CB_Nr_Scoring.setSelectedIndex(0);
		m_CB_Nr_Scoring.addItemListener(this);
		// Set Pieces
		m_CB_SetPieces = createComboBox();
		m_CB_Nr_SetPieces = new JComboBox();
		fillComboBox(m_CB_Nr_SetPieces);
		m_CB_Nr_SetPieces.setSelectedIndex(0);
		m_CB_Nr_SetPieces.addItemListener(this);
		// Loyalty
		m_CB_Loyalty = createComboBox();
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

		int tmpBreite = UserParameter.instance().hoMainFrame_width;
		int devLocation = tmpBreite - 300;
		int devLocationBottom = tmpBreite -300;
		
		m_splitPaneTop.setDividerLocation(devLocation);
		m_splitPaneBottom.setDividerLocation(devLocationBottom);
	   
		add( m_splitPane,BorderLayout.CENTER);
	}

	private JComboBox createComboBox(){
		JComboBox box = new JComboBox(m_rating);
		box.setSelectedIndex(0);
		box.addItemListener(this);
		return box;
		
	}
	
	private void fillComboBox(JComboBox jcb, int len){
		for(int i = 0; i < len; i++) 
				jcb.addItem(m_rating[i]);
	}
	
	private void fillComboBox(JComboBox jcb){
		for(int i = 0; i < 7; i++)	
			jcb.addItem(" +" + i);
	}
	
    public void refresh()
    {
    	// only refresh if no button was pressed so that m_b_refresh doesn't get set
    	if(m_b_refresh == true)
    	{
    		m_scrollPaneTableTop.setViewportView(null);
    		//Get players
    		getAllPlayers();
    		m_playerTableModelTop = new PlayerTableModel(m_ar_allPlayers,1);
    		TableSorter sorter = new TableSorter(m_playerTableModelTop);
    		m_jTableTop = new PlayerTable(sorter,m_playerTableModelTop);
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
				int pos =  HOVerwaltung.instance().getModel().getSpieler(spielerID).getIdealPosition();
				String group =  HOVerwaltung.instance().getModel().getSpieler(spielerID).getTeamInfoSmilie();
				//System.out.println(cbType +":"+group);
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
						|| cbType == 11 && group.startsWith("F")
						)
	    		{
	    			m_playerTableModelTop.setValueAt(Boolean.TRUE,i,0);	
	    		}
	    		else
	    		{
	    			if(cbType != 0 && cbType != 12)
	    			{
	    				m_playerTableModelTop.setValueAt(Boolean.FALSE,i,0);
	    			}
	    			else if(cbType == 12)
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
    		ISpieler spieler = HOVerwaltung.instance().getModel().getSpieler(spielerID);
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
        if(e.getSource().equals(m_btCompare)) {
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
        	switch(selectedType){
        		case 0:
        			for(int i = 0; i < m_i_ptmTopCount; i++) 
                		if(((Boolean)m_playerTableModelTop.getValueAt(i,0)).booleanValue() == true)
                			fetchPlayer(((Integer)m_playerTableModelTop.getValueAt(i,m_playerTableModelTop.getColumnCount()-1)).intValue());
        			break;
        		case 1:
        			for(int i = 0; i < m_i_ptmTopCount; i++){
        				byte tmpPos = 0;
        				
        				try {
        					tmpPos = ((Float)m_playerTableModelTop.getValueAt(i,4)).byteValue();
        				}catch(Exception ex){}
                		if(tmpPos == 0 && m_playerTableModelTop.getValueAt(i,0) == Boolean.TRUE){
                			fetchPlayer(((Integer)m_playerTableModelTop.getValueAt(i,m_playerTableModelTop.getColumnCount()-1)).intValue());                		
                		}
                		else if(tmpPos == 0 && m_playerTableModelTop.getValueAt(i,0) == Boolean.FALSE){
                			m_CB_type.setSelectedIndex(0);
                		}
                	}
        			break;
        		case 2:
        			for(int i = 0; i < m_i_ptmTopCount; i++){
        				byte tmpPos = 0;
        				
        				try {
        					tmpPos = ((Float)m_playerTableModelTop.getValueAt(i,4)).byteValue();
        				} catch(Exception ex){}
                		
        				if((tmpPos > 0 && tmpPos < 8)  && m_playerTableModelTop.getValueAt(i,0) == Boolean.TRUE){
                			fetchPlayer(((Integer)m_playerTableModelTop.getValueAt(i,m_playerTableModelTop.getColumnCount()-1)).intValue());                		
                		}else if((tmpPos > 0 && tmpPos < 8) && m_playerTableModelTop.getValueAt(i,0) == Boolean.FALSE){
                			m_CB_type.setSelectedIndex(0);
                		}
                	}
        			break;
        		case 3:
        			for(int i = 0; i < m_i_ptmTopCount; i++)
                	{
        				byte tmpPos = 0;
        				
        				try
        				{
        					tmpPos = ((Float)m_playerTableModelTop.getValueAt(i,4)).byteValue();
        				}
        				catch(Exception ex){}
                		if((tmpPos > 7 && tmpPos < 12)  && m_playerTableModelTop.getValueAt(i,0) == Boolean.TRUE){
                			fetchPlayer(((Integer)m_playerTableModelTop.getValueAt(i,m_playerTableModelTop.getColumnCount()-1)).intValue());                		
                		}
                		else if((tmpPos > 7 && tmpPos < 12) && m_playerTableModelTop.getValueAt(i,0) == Boolean.FALSE) {
                			m_CB_type.setSelectedIndex(0);
                		}
                	}
        			break;
        		case 4:

        			for(int i = 0; i < m_i_ptmTopCount; i++) {
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

        		case 5:

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

        		case 6:

        			for(int i = 0; i < m_i_ptmTopCount; i++)
                	{
        				String gruppe = "";
        				
        				gruppe = m_playerTableModelTop.getValueAt(i,5).toString();
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

        		case 7:

        			for(int i = 0; i < m_i_ptmTopCount; i++)
                	{
        				String gruppe = "";
        				
        				gruppe = m_playerTableModelTop.getValueAt(i,5).toString();
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

        		case 8:

        			for(int i = 0; i < m_i_ptmTopCount; i++)
                	{
        				String gruppe = "";
        				
        				gruppe = m_playerTableModelTop.getValueAt(i,5).toString();
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

        		case 9:

        			for(int i = 0; i < m_i_ptmTopCount; i++){
        				String gruppe = "";
        				
        				gruppe = m_playerTableModelTop.getValueAt(i,5).toString();
                		if(gruppe.equals("D-Team.png") && m_playerTableModelTop.getValueAt(i,0) == Boolean.TRUE){
                			fetchPlayer(((Integer)m_playerTableModelTop.getValueAt(i,m_playerTableModelTop.getColumnCount()-1)).intValue());
                		}
                		else if(gruppe.equals("D-Team.png") && m_playerTableModelTop.getValueAt(i,0) == Boolean.FALSE)
                		{
                			m_CB_type.setSelectedIndex(0);
                		}
                	}
        			break;

        		case 10:
        			for(int i = 0; i < m_i_ptmTopCount; i++){
        				String gruppe = "";
        				
        				gruppe = m_playerTableModelTop.getValueAt(i,5).toString();
                		if(gruppe.equals("E-Team.png") && m_playerTableModelTop.getValueAt(i,0) == Boolean.TRUE){
                			fetchPlayer(((Integer)m_playerTableModelTop.getValueAt(i,m_playerTableModelTop.getColumnCount()-1)).intValue());
                		} else if(gruppe.equals("E-Team.png") && m_playerTableModelTop.getValueAt(i,0) == Boolean.FALSE){
                			m_CB_type.setSelectedIndex(0);
                		}
                	}
        			break;
        		
        		case 11:
        			for(int i = 0; i < m_i_ptmTopCount; i++){
        				fetchPlayer(((Integer)m_playerTableModelTop.getValueAt(i,m_playerTableModelTop.getColumnCount()-1)).intValue());
        			}
			}
        	
        	// Create array from a tablemodel vector
        	m_ar_setPlayers = new Player[m_V_setPlayers.size()];
        	for(int counter = 0; counter < m_ar_setPlayers.length; counter++)
        	{
        		m_ar_setPlayers[counter] = m_V_setPlayers.elementAt(counter);
        	}
        	
        	m_playerTableModelBottom = new PlayerTableModel(m_ar_setPlayers,2);
    		TableSorter sorter2 = new TableSorter(m_playerTableModelBottom); //ADDED THIS
        	m_jTableBottom = new PlayerTable(sorter2,m_playerTableModelBottom);
        	m_jTableBottom.setRowSelectionAllowed(true);
        	m_jTableBottom.addMouseListener(this);
        	
        	sorter2.setTableHeader(m_jTableBottom.getTableHeader()); //ADDED THIS
        	
        	m_scrollPaneTableBottom.setViewportView(m_jTableBottom);
    		m_scrollPaneTableBottom.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    		m_scrollPaneTableBottom.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    		
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
        	RefreshManager.instance().doReInit();
        }
    } 
    /**
     * 
     * getAllPlayers():
     * - Fetches all players via the MiniModel
     *
     */
    private void getAllPlayers()
    {
		m_V_allPlayers =  HOVerwaltung.instance().getModel().getAllSpieler();
		m_numberOfPlayers = m_V_allPlayers.size();
		m_ar_allPlayers = new Player[m_numberOfPlayers];
		for(int counter = 0; counter < m_numberOfPlayers; counter++)
		{
			m_ar_allPlayers[counter] = new Player(m_V_allPlayers.elementAt(counter));
		}
    }
    
    /**
     * fetchPlayer(int id)
     * - searches for a player with id in the player array
     */
    private void fetchPlayer(int id)
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
    
    private void resetPlayer()
    {
    	for(int i = 0; i < m_ar_allPlayers.length; i++)
    	{
    		m_ar_allPlayers[i].resetPlayers();
    	}
    }
    
    private void setDummyPlayerDetails()
    {
    	Player dummy = new Player();
    	JLabel l_SpielerName = new JLabel();
    	l_SpielerName.setPreferredSize(new Dimension(100,30));
    	l_SpielerName.setText(HOVerwaltung.instance().getLanguageString("Name"));
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
    	m_playerTableModelDetail = new PlayerTableModel(dummy);
		
		TableSorter sorter3 = new TableSorter(m_playerTableModelDetail); //ADDED THIS
		m_jTableDetail = new PlayerTable(sorter3);
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
    private void setNewRating(int er, int fo, int ko, int tw, int ve, int sa,int ps, int fl, int ts, int st, int loy, int hg)
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
    
    private void setChangeRatingBy(int er, int fo, int ko, int tw, int ve, int sa,int ps, int fl, int ts, int st, int loy, int hg) {
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
    
    private int getSelectedRow()
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
			tmpPlayer = m_V_setPlayers.elementAt(u);
			if(("" + tmpPlayer.getId()).compareTo(id)==0)
			{
				l_SpielerName.setText("" + tmpPlayer.getName());
				break;
			}
		}
		p_SpielerName.add(platzhalter,BorderLayout.WEST);
		p_SpielerName.add(l_SpielerName);
		p_PlayerDetail.add(p_SpielerName,BorderLayout.NORTH);
		m_playerTableModelDetail = new PlayerTableModel(tmpPlayer);
		
		TableSorter sorter3 = new TableSorter(m_playerTableModelDetail); //ADDED THIS
		m_jTableDetail = new PlayerTable(sorter3);
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
}
