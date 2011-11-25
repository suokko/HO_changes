package hoplugins;

/*
 * TSForecast.java
 *
 * Created on 01.March 2006, 11:04
 *
 *Version 1.3
 *history :
 *01.03.06  Version 0.1 Creation
 *15.03.06  Version 0.2 Basic Functionality
 *24.03.06  Version 0.3 Beautify
 *25.03.06  Version 0.4 Save settings
 *27.03.06  Version 0.5 Layoutmanager and scale
 *31.03.06  Version 0.6 Language support, improved forecats curves, season pause, icon for Cup and League matches
 *05.04.06  Version 0.7 
 *22.08.06  Version 0.91 rebuilt
 *04.09.06  Version 1.0 Only one curve, Loepicurve based on TrainerLS, Confidence scale, generated future games
 *19.02.07  Version 1.1 Switch for showing Cup and Relegation matches
 *21.02.07  Version 1.2 add tooltips to matches buttons
 *17.09.09  Version 1.3 last changes for Version 1.0
 *
 *
 */

/**
 *
 * @author  michael.roux
 */


import hoplugins.tsforecast.CheckBox;
import hoplugins.tsforecast.ConfidenceCurve;
import hoplugins.tsforecast.Curve;
import hoplugins.tsforecast.ErrorLog;
import hoplugins.tsforecast.FutureMatchBox;
import hoplugins.tsforecast.HistoryCurve;
import hoplugins.tsforecast.LoepiCurve;
import hoplugins.tsforecast.TSPanel;
import hoplugins.tsforecast.TrainerCurve;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.HyperlinkListener;

import plugins.*;

public class TSForecast extends 	WindowAdapter
    				implements	IPlugin,
    						IOfficialPlugin,
    						IRefreshable,
    						ActionListener,
    						ChangeListener,
    						ItemListener,
    						HyperlinkListener
{
  public static IHOMiniModel m_clModel 		= null;

  private JPanel m_jpMainPanel    			= null;
  private JPanel m_jpSettingsPanel			= null;
  private JPanel m_jpGamesPanel			= null;

  private JCheckBox m_jtCupMatches 			= null;
  private JCheckBox m_jtRelegationMatch         = null;

  private CheckBox m_jtHistory    			= null;
  private CheckBox m_jtLoepiHist    		= null;
  private CheckBox m_jtLoepiFore			= null;
  private CheckBox m_jtConfidence			= null;

  private TSPanel m_jpGraphics    			= null;

  private HistoryCurve m_History    		= null;
  private LoepiCurve m_LoepiForecast    		= null;
  private LoepiCurve m_LoepiHist    		= null;
  private TrainerCurve m_Trainer    		= null;
  private ConfidenceCurve m_Confidence		= null;

  public static Properties m_Configuration 	= new Properties();
  public static Properties m_Language 		= new Properties();


  public TSForecast() {
    try  {
      File file = new File( "hoplugins" + File.separator + "tsforecast" + File.separator + "tsforecast.cfg");
      if(file.exists()) {
        m_Configuration.load(new FileInputStream(file));
      }
    }
    catch(IOException ioexception) {
      ErrorLog.write(ioexception);
    }
  }

  /**
   * Is called by HO! to start the plugin
   */

  public void start(IHOMiniModel hOMiniModel)  {
    try {
      m_clModel = hOMiniModel;
      
      loadLanguageFiles();
      
      m_jpMainPanel = hOMiniModel.getGUI().createGrassPanel();
      GridBagLayout gridbaglayout = new GridBagLayout();
      m_jpMainPanel.setLayout( gridbaglayout);

      GridBagConstraints gridbagconstraints = new GridBagConstraints();
      gridbagconstraints.fill = GridBagConstraints.NONE;
      gridbagconstraints.insets = new Insets(5, 5, 5, 5);
      
      m_jpSettingsPanel = new JPanel();
      m_jpSettingsPanel.setLayout( new BoxLayout(m_jpSettingsPanel, BoxLayout.Y_AXIS));
      
      createSettingsPanel( m_jpSettingsPanel);
	createCurvesPanel( m_jpSettingsPanel);
      createGamesPanel( m_jpSettingsPanel);
      
      gridbagconstraints.gridx = 0;
      gridbagconstraints.gridheight = 2;
      gridbagconstraints.anchor = 18;
      m_jpMainPanel.add(m_jpSettingsPanel, gridbagconstraints);
      m_jpGraphics = new TSPanel();
      gridbagconstraints.gridx = 1;
      gridbagconstraints.gridy = 0;
      gridbagconstraints.fill = 1;
      gridbagconstraints.anchor = 11;
      gridbagconstraints.weightx = 1.0D;
      gridbagconstraints.weighty = 1.0D;
      gridbagconstraints.gridheight = -1;
      m_jpMainPanel.add(m_jpGraphics, gridbagconstraints);
      
      createTeamData(m_jpMainPanel, 1);
      createMenu();
      
      //add a new Panel to ho
      m_clModel.getGUI().addTab( getName() + " v" + getVersion(), m_jpMainPanel);
      m_clModel.getGUI().registerRefreshable( this);
      
      //display info in statusbar of HO
      m_clModel.getGUI().getInfoPanel().setLangInfoText( getName() + " v" + getVersion());
      m_clModel.getGUI().addMainFrameListener( this);
    }
    catch(Exception exception) {
      ErrorLog.write(exception);
    }
  }

  public String getName()       { return "TS Forecast"; }
  public int getPluginID()      { return 34; }
  public String getPluginName() { return getName() + " v" + getVersion(); }
  public double getVersion()    { return 1.01D; }

  public File[] getUnquenchableFiles() {
    String s = "hoplugins" + File.separator + "tsforecast" + File.separator;
    File afile[] = { new File( s + "tsforecast.cfg") };
    return afile;
  }


  public void refresh() {
//    ErrorLog.writeln("refresh");
    try {
      createCurves();
    }
    catch( Exception ex) {
      ErrorLog.write( ex);
    }
    createGamesPanel( m_jpSettingsPanel);
    m_jpGraphics.repaint();
  }


  public void windowClosing(WindowEvent windowevent) {
    try {
      //delete all matches and add only relevant ones

      for( Enumeration e = m_Configuration.propertyNames(); e.hasMoreElements();) {
        String s = (String)e.nextElement();
        if( s.startsWith("Match_"))
            m_Configuration.remove(s);
      }
      for( boolean flag = m_LoepiForecast.first() && m_LoepiForecast.next(); flag; flag = m_LoepiForecast.next()) {
        if(m_LoepiForecast.getAttitude() != IMatchDetails.EINSTELLUNG_UNBEKANNT) {
          m_Configuration.setProperty( "Match_" + DateFormat.getDateInstance( DateFormat.SHORT).format(m_LoepiForecast.getDate()),
                                       "" + m_LoepiForecast.getAttitude() );
        }
      }
      java.io.FileOutputStream file = new FileOutputStream( "hoplugins" + File.separator 
                                                          + "tsforecast" + File.separator 
                                                          + "tsforecast.cfg");
      m_Configuration.store( file, getName() + " Configuration File v" + getVersion());
      file.close();
    }
    catch(IOException ioexception) {
      ErrorLog.write(ioexception);
    }
  }


  public void windowOpened( WindowEvent windowevent) {
    Cursor cursor = m_jpMainPanel.getCursor();
    m_jpMainPanel.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR));
    double d = (new Double( m_Configuration.getProperty( "GeneralSpirit", "4.50"))).doubleValue();
    try { 
      m_LoepiForecast.setGeneralSpirit( d);
      m_LoepiHist.setGeneralSpirit( d);
    }
    catch( Exception ex) {
      ErrorLog.write( ex);
    }  
    m_jtCupMatches.setSelected(m_Configuration.getProperty( "ShowCupMatches", "false").equals( "true"));
    m_jtRelegationMatch.setSelected(m_Configuration.getProperty( "ShowQualificationMatch", "false").equals( "true"));
    m_jtConfidence.setSelected(m_Configuration.getProperty( "Confidence", "false").equals( "true"));
    m_jtHistory.setSelected(m_Configuration.getProperty( "History", "true").equals( "true"));
    m_jtLoepiFore.setSelected(m_Configuration.getProperty( "LoepiForecast", "false").equals( "true"));
    m_jtLoepiHist.setSelected(m_Configuration.getProperty( "LoepiHistory", "false").equals( "true"));
    m_jpMainPanel.setCursor(cursor);
  }

  public void stateChanged(ChangeEvent changeevent) {
  }

  public void actionPerformed(ActionEvent actionevent) {
    Cursor cursor = m_jpMainPanel.getCursor();
    m_jpMainPanel.setCursor(Cursor.getPredefinedCursor(3));
    try {
      if( Class.forName("javax.swing.JRadioButton").isInstance(actionevent.getSource())) {
        int iButton = Integer.parseInt(actionevent.getActionCommand().substring(1));
        
        switch(actionevent.getActionCommand().charAt(0)) {
        case 80: // 'P'
          m_LoepiForecast.setAttitude(iButton , IMatchDetails.EINSTELLUNG_PIC);
          break;
        case 77: // 'M'
          m_LoepiForecast.setAttitude(iButton , IMatchDetails.EINSTELLUNG_MOTS);
          break;
        case 78: // 'N'
        case 79: // 'O'
        default:
          m_LoepiForecast.setAttitude(iButton , IMatchDetails.EINSTELLUNG_NORMAL);
          break;
        }
        m_jpGraphics.repaint();
      } else if( actionevent.getActionCommand().equals( "About")) {
        javax.swing.JEditorPane jContent = new javax.swing.JEditorPane( "text/html",
                "<div  style=\"padding-right:45px;\"><p align=center><b>" + getPluginName() + "</b></p><br>"
                + "<p align=center>Thanks to Loepi for the algorithms.<br>"
                + "Thanks to Pausanias, Robbe, dirkgomez and chiohiro for testing.<br>"
                + "Development by Poco Moreno</p><br>"
                + "<p align=center>If you like my work and want to appreciate,<br>"
                + "buy me <a href='https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=8305893'>a beer</a>"
                + " or <a href='http://www.hattrick.org'>HT-supporter</a></p>"
                + "<br><p align=center>Thank You!</p></div>");
        jContent.setEditable(false);
        jContent.addHyperlinkListener(this);
        JOptionPane.showMessageDialog( null, jContent, getPluginName(), 1);
      } else if( actionevent.getActionCommand().equals( "Help")) {
        showHelpDialog();
      }
    }
    catch(Exception ex) {
      ErrorLog.write( ex);
    }
    m_jpMainPanel.setCursor(cursor);
  }


  public void itemStateChanged(ItemEvent itemevent) {
    try {
      if(itemevent.getSource() == m_jtCupMatches) {
        if(itemevent.getStateChange() == ItemEvent.SELECTED) {
          m_Configuration.setProperty( "ShowCupMatches", "true");
          createGamesPanel( m_jpSettingsPanel);
        } else {
          m_Configuration.setProperty( "ShowCupMatches", "false");
          createGamesPanel( m_jpSettingsPanel);
        }
      } else if(itemevent.getSource() == m_jtRelegationMatch) {
        if(itemevent.getStateChange() == ItemEvent.SELECTED) {
          m_Configuration.setProperty( "ShowQualificationMatch", "true");
          createGamesPanel( m_jpSettingsPanel);
        } else {
          m_Configuration.setProperty( "ShowQualificationMatch", "false");
          createGamesPanel( m_jpSettingsPanel);
        }
      } else if(itemevent.getSource() == m_jtHistory.getCheckBox()) {
        if(itemevent.getStateChange() == ItemEvent.SELECTED) {
          m_Configuration.setProperty( "History", "true");
          m_jpGraphics.addCurve( m_History, true);
          m_jpGraphics.addCurve( m_Trainer);
        } else {
          m_Configuration.setProperty( "History", "false");
          m_jpGraphics.removeCurve( m_History);
          m_jpGraphics.removeCurve( m_Trainer);
        }
      } else if(itemevent.getSource() == m_jtLoepiFore.getCheckBox()) {
        if(itemevent.getStateChange() == ItemEvent.SELECTED) {
          m_Configuration.setProperty( "LoepiForecast", "true");
          m_jpGraphics.addCurve( m_LoepiForecast);
        } else {
          m_Configuration.setProperty( "LoepiForecast", "false");
          m_jpGraphics.removeCurve( m_LoepiForecast);
        }
      } else if(itemevent.getSource() == m_jtLoepiHist.getCheckBox()){
        if(itemevent.getStateChange() == ItemEvent.SELECTED) {
          m_Configuration.setProperty( "LoepiHistory", "true");
          m_jpGraphics.addCurve( m_LoepiHist);
        } else {
          m_Configuration.setProperty( "LoepiHistory", "false");
          m_jpGraphics.removeCurve( m_LoepiHist);
        }
      } else if(itemevent.getSource() == m_jtConfidence.getCheckBox()) {
        if(itemevent.getStateChange() == ItemEvent.SELECTED) {
          m_Configuration.setProperty( "Confidence", "true");
          m_jpGraphics.addCurve( m_Confidence);
        } else {
          m_Configuration.setProperty( "Confidence", "false");
          m_jpGraphics.removeCurve( m_Confidence);
        }
      }
    }
    catch(Exception exception) {
      ErrorLog.write(exception);
    }

    // check whether it is necessary to draw confidence scale
    if( m_Configuration.getProperty( "Confidence").equals("false") )  {
      m_jpGraphics.showConfidenceScale( false);
    } else {
      m_jpGraphics.showConfidenceScale( true);
    }

    // check whether it is necessary to draw teamspirit scale
    if(  m_Configuration.getProperty( "LoepiHistory").equals("false")
      && m_Configuration.getProperty( "LoepiForecast").equals("false")
      && m_Configuration.getProperty( "History").equals("false") )  {
      m_jpGraphics.showTeamspiritScale( false);
    } else {
      m_jpGraphics.showTeamspiritScale( true);
    }

    m_jpGraphics.repaint();
  }


  public void hyperlinkUpdate(javax.swing.event.HyperlinkEvent e) {
    if (e.getEventType() == javax.swing.event.HyperlinkEvent.EventType.ACTIVATED) {
      try {
        String os = System.getProperty("os.name");
        if ( os != null && os.startsWith("Windows"))
          Runtime.getRuntime().exec( "rundll32 url.dll,FileProtocolHandler "+e.getURL());
        else //UNIX Mac
          Runtime.getRuntime().exec( "netscape "+e.getURL());
      } catch (Exception exc) {
        ErrorLog.write( exc);
      }
    }
  }


  //- private -------------------------------------------------------------------------

  private int createTeamData(JPanel jpanel, int gridy) throws SQLException {
    IJDBCAdapter ijdbcadapter = m_clModel.getAdapter();
    
    GridBagConstraints gridbagconstraints = new GridBagConstraints();
    gridbagconstraints.anchor = GridBagConstraints.WEST;
    gridbagconstraints.insets = new Insets(0, 5, 5, 5);
    gridbagconstraints.gridy = gridy;
    gridbagconstraints.gridx = 1;
    
    String strLabel = new String();
    
    ResultSet resultset = ijdbcadapter.executeQuery("select FUEHRUNG from SPIELER where TRAINER > 0 order by DATUM desc");
    if( resultset != null && resultset.first()) {
      strLabel += m_Language.getProperty( "FQTrainer", "Coach's leadership is")
               + m_clModel.getHelper().getNameForSkill( resultset.getInt("FUEHRUNG"), true)
               + ". ";
    }
    
    resultset = ijdbcadapter.executeQuery( "select max(FUEHRUNG) as MAXF, max(DATUM) as MAXD from SPIELER where TRAINER=0");
    resultset.first();
    resultset = ijdbcadapter.executeQuery( "select count(SPIELERID) as COUNTF from SPIELER where FUEHRUNG = "
                                         + resultset.getInt("MAXF") + " and DATUM = '"
                                         + resultset.getTimestamp("MAXD") + "' and TRAINER=0");
    resultset.first();
    if( resultset.getInt("COUNTF") > 1) {
      strLabel += m_Language.getProperty("no_teamleader", "There is no clear leader in your team. ");
    } else {
      strLabel += m_Language.getProperty("teamleader", "Your teamleader is") + " ";
      resultset = ijdbcadapter.executeQuery("select ICHARAKTER from SPIELER order by FUEHRUNG desc, DATUM desc");

      if(resultset != null && resultset.first()) {
        switch(resultset.getInt("ICHARAKTER")) {
          case 5: 
              strLabel += m_Language.getProperty("beloved_team_member", "a beloved team member") + ". ";
              break;
          case 4: 
              strLabel += m_Language.getProperty("popular", "a clown") + ". ";
              break;
          case 3: 
              strLabel += m_Language.getProperty("sympathetic", "a sympathetic guy") + ". ";
              break;
          case 2: 
              strLabel += m_Language.getProperty("pleasant", "a pleasant guy") + ". ";
              break;
          case 1: 
              strLabel += m_Language.getProperty("controversial", "a controversial person") + ". ";
              break;
          case 0: 
              strLabel += m_Language.getProperty("nasty", "a nasty fellow") + ". ";
              break;
          default:
              strLabel += "free of character. ";
              break;
        }
      }
    }
    
    resultset = ijdbcadapter.executeQuery("select PSCHYOLOGEN from VEREIN order by HRF_ID desc");
    if(resultset != null && resultset.first()) {
      strLabel += m_Language.getProperty("Staff", "Your staff includes ") 
               + resultset.getInt("PSCHYOLOGEN") + " " 
               + m_clModel.getLanguageString("Psychologen")
               + ". ";
    }
    resultset = ijdbcadapter.executeQuery("select TRAININGSINTENSITAET from TEAM order by HRF_ID desc");
    if(resultset != null && resultset.first()) {
      strLabel += m_Language.getProperty("Training", "The Training is at ") 
               + resultset.getInt("TRAININGSINTENSITAET") 
               + "% . ";
    }
    JLabel jlabel = new JLabel(strLabel, 2);
    jlabel.setOpaque(true);
    jpanel.add(jlabel, gridbagconstraints);
    return gridbagconstraints.gridy;
  }


  private void createSettingsPanel(JPanel jpanel) {
    m_jtCupMatches = new JCheckBox( m_Language.getProperty("CupMatches", "Cup matches"), false);
    m_jtCupMatches.setToolTipText( m_Language.getProperty("ShowCupMatches", "show cup matches"));
    m_jtCupMatches.setAlignmentX( 0.0F);
    m_jtCupMatches.addItemListener( this);
    jpanel.add( m_jtCupMatches);
    
    m_jtRelegationMatch = new JCheckBox( m_clModel.getLanguageString("QualifikationSpiel"), false);
    m_jtRelegationMatch.setToolTipText( m_Language.getProperty("ShowQMatch", "show qualification match"));
    m_jtRelegationMatch.setAlignmentX( 0.0F);
    m_jtRelegationMatch.addItemListener( this);
    jpanel.add( m_jtRelegationMatch);
  }
  

  private void createCurvesPanel(JPanel jpanel) throws Exception {
    createCurves();
    
    m_jtHistory = new CheckBox( m_Language.getProperty("HistoryCurve", "Team Spirit"), m_History.getColor(), false);
    m_jtHistory.setAlignmentX( 0.0F);
    m_jtHistory.addItemListener( this);
    jpanel.add( m_jtHistory);
    
    m_jtConfidence = new CheckBox( m_clModel.getLanguageString("Selbstvertrauen"), m_Confidence.getColor(), false);
    m_jtConfidence.setAlignmentX( 0.0F);
    m_jtConfidence.addItemListener( this);
    jpanel.add(m_jtConfidence);
   
    m_jtLoepiHist = new CheckBox( m_Language.getProperty( "LoepiCurve", "Loepi Curve"), m_LoepiHist.getColor(), false);
    m_jtLoepiHist.setAlignmentX( 0.0F);
    m_jtLoepiHist.addItemListener( this);
    jpanel.add( m_jtLoepiHist);
    
    m_jtLoepiFore = new CheckBox( m_Language.getProperty("TSForecast", "TS-Forecast"), m_LoepiForecast.getColor(), false);
    m_jtLoepiFore.setAlignmentX( 0.0F);
    m_jtLoepiFore.addItemListener( this);
    jpanel.add( m_jtLoepiFore);    
  }


  private void createGamesPanel( JPanel jpanel)  {
    if(m_jpGamesPanel == null) {
      m_jpGamesPanel = new JPanel();
      m_jpGamesPanel.setLayout( new GridBagLayout());
    }
    jpanel.remove( m_jpGamesPanel);
    m_jpGamesPanel.removeAll();
    
    GridBagConstraints gridbagconstraints = new GridBagConstraints();
    gridbagconstraints.gridwidth = 1;
    gridbagconstraints.anchor = GridBagConstraints.NORTHWEST;
    
    int iCmdID = 0;
    JLabel jlabel = new JLabel(" PIC  N  MOTS");
    jlabel.setOpaque( true);
    gridbagconstraints.insets = new Insets( 10, 0, 2, 0);
    gridbagconstraints.gridy = 0;
    m_jpGamesPanel.add( jlabel, gridbagconstraints);
    gridbagconstraints.insets = new Insets(0, 0, 0, 0);
    
    boolean bshowCupMatches = m_Configuration.getProperty( "ShowCupMatches", "false").equals( "true");
    boolean bshowQualMatches = m_Configuration.getProperty( "ShowQualificationMatch", "false").equals( "true");

    for( boolean flag = m_LoepiForecast.first() && m_LoepiForecast.next(); flag;) {
      if( m_LoepiForecast.getAttitude() != IMatchDetails.EINSTELLUNG_UNBEKANNT) {
        if(  m_LoepiForecast.getMatchType() == Curve.LEAGUE_MATCH 
          || (m_LoepiForecast.getMatchType() == Curve.CUP_MATCH && bshowCupMatches)
          || (m_LoepiForecast.getMatchType() == Curve.RELEGATION_MATCH && bshowQualMatches) ) {
          
          FutureMatchBox futurematchbox = new FutureMatchBox( DateFormat.getDateInstance(3).format(m_LoepiForecast.getDate()),
                                                              m_LoepiForecast.getTooltip(), iCmdID,
                                                              m_LoepiForecast.getAttitude(), m_LoepiForecast.getMatchType());
          futurematchbox.addActionListener(this);
          gridbagconstraints.gridy++;
          m_jpGamesPanel.add(futurematchbox, gridbagconstraints);
        }
        if( m_LoepiForecast.getMatchType() == Curve.RELEGATION_MATCH ) { // indicate end of season
          gridbagconstraints.gridy++;
          m_jpGamesPanel.add( new JLabel( "  " + TSForecast.m_Language.getProperty("EndOFSeason", "- end of season -")), gridbagconstraints);          
//          JToolBar.Separator s = new JToolBar.Separator( new Dimension(40,40));
//          m_jpGamesPanel.add( s, gridbagconstraints);          
        }
      }
      flag = m_LoepiForecast.next();
      iCmdID++;
    }

    m_jpGamesPanel.setAlignmentX( 0.0F);
    jpanel.add( m_jpGamesPanel);
    jpanel.revalidate();
  }


  private void createCurves() throws Exception {
    if ( m_Trainer != null && m_jpGraphics.removeCurve( m_Trainer) ) {
      m_Trainer = new TrainerCurve( m_clModel);
      m_jpGraphics.addCurve( m_Trainer);
    } else {
      m_Trainer = new TrainerCurve( m_clModel);
    }

    if ( m_History != null && m_jpGraphics.removeCurve( m_History) ) {
      m_History = new HistoryCurve( m_clModel);
      m_jpGraphics.addCurve( m_History, true);
    } else {
      m_History = new HistoryCurve( m_clModel);
    }
    m_History.setColor( Color.black);
    m_History.first();
    m_History.next();

    if ( m_Confidence != null && m_jpGraphics.removeCurve( m_Confidence) ) {
      m_Confidence = new ConfidenceCurve( m_clModel);
      m_jpGraphics.addCurve( m_Confidence);
    } else {
      m_Confidence = new ConfidenceCurve( m_clModel);
    }
    m_Confidence.setColor( Color.blue);

    if ( m_LoepiHist != null && m_jpGraphics.removeCurve( m_LoepiHist) ) {
      m_LoepiHist = new LoepiCurve( m_clModel, m_Trainer, false);
      m_jpGraphics.addCurve( m_LoepiHist);
    } else {
      m_LoepiHist = new LoepiCurve( m_clModel, m_Trainer, false);
    }
    m_LoepiHist.setSpirit( 0, m_History.getSpirit());
    m_LoepiHist.setColor( Color.orange);
    
    if ( m_LoepiForecast != null && m_jpGraphics.removeCurve( m_LoepiForecast) ) {
      m_LoepiForecast = new LoepiCurve( m_clModel, m_Trainer, true);
      m_jpGraphics.addCurve( m_LoepiForecast);
    } else {
      m_LoepiForecast = new LoepiCurve( m_clModel, m_Trainer, true);
    }
    m_LoepiForecast.setStartPoint( m_History.getLastPoint());
    m_LoepiForecast.setAttitudes( m_Configuration);
    m_LoepiForecast.forecast(0);
    m_LoepiForecast.setColor( Color.red);
  }

  
  private void showHelpDialog() {
    String s = null;
    switch(m_clModel.getHelper().getLanguageID()) {
      case 1: // '\001'
        s = "Voraussetzungen:\n"
          + "  - JRE 1.5 (Java5)\n"
          + "  - Hattrick Organizer Version 1.3 oder neuer\n"
          + "\n"
          + "Funktionalit\344t:\n"
          + "Es gibt 4 Kurven:\n"
          + "  schwarz - der tats\344chliche Verlauf der Teamstimmung bis heute\n"
          + "  blau - Verlauf des Selbstvertrauens des Teams\n"
          + "  orange - Loepikurve Simulation der Vorhersage basierend auf Loepis Funktion\n"
          + "  rot - Vorhersage der Teamstimmung, basierend auf deinem Spielplan\n"
          + "\n"
          + "Darunter eine Liste der n\344chsten Spiele. Hier kann man \n"
          + "ausw\344hlen, wie man diese Spiele in der Zukunft zu spielen gedenkt.\n"
          + "Die Vorhersage passt sich entsprechend an. Pokalspiele und das \n"
          + "Relegationsspiel k�nnen wahlweise hinzugef�gt werden.\n"
          + "\n"
          + "Probleme/Hilfe:\n"
          + "Bitte kontaktiert mich �ber Hattrick oder das HO-Forum. Am Besten schickst\n"
          + "Du einen Screenshot mit allen Kurve mit. Fehlerberichte und\n"
          + "Verbesserungsw\374nsche etc. sind nat\374rlich auch willkommen.\n"
          + "\n";
        break;
      case 2: // '\002'
      default:
        s = "Prerequisits:\n"
          + "  - JRE 1.5 (Java5)\n"
          + "  - Hattrick Organizer Version 1.3 or newer\n"
          + "\n"
          + "Functionality:\n"
          + "There are 4 curves implemented:\n"
          + "  black - the historic curve of the team spirit\n"
          + "  blue - the historic curve of the teams confidence\n"
          + "  orange - a simulation of the historic curve using Loepi's algorithm\n"
          + "  red - the forecast of the team spirit based on your plans, on how to play\n"
          + "\n"
          + "Below there is a list of all known future games till end of the season.\n"
          + "Please choose with which attitude you decide to play the next matches.\n"
          + "The forecast curve will be updated accordingly straight away.\n"
          + "Cup- and Qualification-Matches can be added to that list.\n"
          + "\n"
          + "Problems/Help:\n"
          + "Please contact me through Hattrick or the HO-Forum. A screenshot with\n"
          + "the various simulations might be helpful. I appreciate any suggestions\n"
          + "or error reports.\n"
          + "\n";
        break;
    }
    JOptionPane.showMessageDialog(null, s, getPluginName(), JOptionPane.INFORMATION_MESSAGE);
  }


  private void createMenu() {
    JMenu jmenu = new JMenu(getName());
    JMenuItem jmenuitem = new JMenuItem("Help");
    jmenuitem.addActionListener(this);
    jmenu.add(jmenuitem);
    jmenuitem = new JMenuItem("About");
    jmenuitem.addActionListener(this);
    jmenu.add(jmenuitem);
    m_clModel.getGUI().addMenu(jmenu);
  }


  private void loadLanguageFiles() throws FileNotFoundException, IOException {
    File file = new File( "hoplugins" + File.separator
                        + "tsforecast" + File.separator 
                        + "lang" + File.separator 
                        + m_clModel.getHelper().getLanguageName() 
                        + ".properties");
    if( file.exists()) {
      m_Language.load( new FileInputStream( file));
    } else {
      file = new File( "hoplugins" + File.separator 
                     + "tsforecast" + File.separator 
                     + "lang" + File.separator 
                     + "English.properties");
      if( file.exists()) {
        m_Language.load(new FileInputStream(file));
      }
    }
  }

}