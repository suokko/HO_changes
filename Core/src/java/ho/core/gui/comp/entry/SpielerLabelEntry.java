// %2768837177:de.hattrickorganizer.gui.templates%
package ho.core.gui.comp.entry;

import ho.core.constants.player.PlayerSpeciality;
import ho.core.gui.HOMainFrame;
import ho.core.gui.comp.renderer.HODefaultTableCellRenderer;
import ho.core.gui.theme.HOColorName;
import ho.core.gui.theme.HOIconName;
import ho.core.gui.theme.ImageUtilities;
import ho.core.gui.theme.ThemeManager;
import ho.core.model.UserParameter;
import ho.core.model.player.Spieler;
import ho.core.model.player.SpielerPosition;
import ho.core.util.Helper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 *
 */
public final class SpielerLabelEntry implements IHOTableEntry {
 
    //~ Instance fields ----------------------------------------------------------------------------

    /** Icon for playing creatively */

    //   private ImageIcon            m_clLeer                   =   new ImageIcon( new java.awt.image.BufferedImage( 14, 14, java.awt.image.BufferedImage.TYPE_INT_ARGB ) );
    private Spieler m_clPlayer;
    private JComponent m_clComponent;
    private final JLabel m_jlGroup 			= new JLabel();
    private final JLabel m_jlName			= new JLabel();
    private final JLabel m_jlSkill			= new JLabel();
    private final JLabel m_jlSpezialitaet	= new JLabel();
    private final JLabel m_jlWeatherEffect	= new JLabel();
    private SpielerPosition m_clCurrentPlayerPosition;
    private boolean m_bShowTrikot;
    private boolean m_bShowWeatherEffect = true;
    private boolean m_bCustomName = false;
    private String m_sCustomNameString = "";
    private float m_fPositionsbewertung;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Label für den Spielernamen (je nach Status)
     */
    public SpielerLabelEntry(Spieler spieler, SpielerPosition positionAktuell,
                             float positionsbewertung, boolean showTrikot, boolean showWetterwarnung) {
        m_clPlayer = spieler;
        m_clCurrentPlayerPosition = positionAktuell;
        m_fPositionsbewertung = positionsbewertung;
        m_bShowTrikot = showTrikot;
        m_bShowWeatherEffect = showWetterwarnung;
        createComponent();
    }
    
    public SpielerLabelEntry(Spieler spieler, SpielerPosition positionAktuell,
    		float positionsbewertung, boolean showTrikot, boolean showWetterwarnung, boolean customName, String customNameText) {
    	m_clPlayer = spieler;
    	m_clCurrentPlayerPosition = positionAktuell;
    	m_fPositionsbewertung = positionsbewertung;
    	m_bShowTrikot = showTrikot;
    	m_bShowWeatherEffect = showWetterwarnung;
    	m_bCustomName = customName;
    	m_sCustomNameString = customNameText;
    	createComponent();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Set the custom player name. Will only be used if m_bCustomName is true, a setting available in alternate constructor.
     */
    public void setM_sCustomNameString(String m_sCustomNameString) {
		this.m_sCustomNameString = m_sCustomNameString;
	}

	/**
     * Gibt eine passende Komponente zurück
     */
	public final JComponent getComponent(boolean isSelected) {
    	 m_clComponent.setBackground(isSelected?HODefaultTableCellRenderer.SELECTION_BG:ColorLabelEntry.BG_STANDARD);
    	 m_jlName.setForeground(isSelected?HODefaultTableCellRenderer.SELECTION_FG:getForegroundForSpieler(m_clPlayer));
    	 return m_clComponent;
    }

    public final Spieler getSpieler() {
        return m_clPlayer;
    }

    //----Zugriff----------------------------
	public final void clear() {
        m_clPlayer = null;
        m_clCurrentPlayerPosition = null;
        m_fPositionsbewertung = 0f;
        updateComponent();
    }

    /**
     * Vergleich zum Sortieren
     */
	public final int compareTo(IHOTableEntry obj) {
        if (obj instanceof SpielerLabelEntry) {
            final SpielerLabelEntry entry = (SpielerLabelEntry) obj;

            return m_clPlayer.getName().compareTo(entry.getSpieler().getName());
        }

        return 0;
    }

    //-------------------------------------------------------------    

    /**
     * Erstellt eine passende Komponente
     */
	public final void createComponent() {
        m_clComponent = new JPanel();

        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();

        m_clComponent.setLayout(layout);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.weightx = 1.0;
        constraints.gridx = 1;
        constraints.insets = new Insets(0, 0, 0, 0);
        m_jlName.setIconTextGap(1);
        layout.setConstraints(m_jlName, constraints);
        m_clComponent.add(m_jlName);

        final JPanel spezPanel = new JPanel();
        spezPanel.setDoubleBuffered(false);
        spezPanel.setLayout(new BoxLayout(spezPanel, BoxLayout.X_AXIS));
        spezPanel.setBackground(ColorLabelEntry.BG_STANDARD);
        spezPanel.setOpaque(false);

        //Wetterwarnung
        m_jlWeatherEffect.setBackground(ColorLabelEntry.BG_STANDARD);
        m_jlWeatherEffect.setOpaque(false);
        m_jlWeatherEffect.setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 0));

        spezPanel.add(m_jlWeatherEffect);

        //Spezialität
        m_jlSpezialitaet.setBackground(ColorLabelEntry.BG_STANDARD);
        m_jlSpezialitaet.setOpaque(false);

        spezPanel.add(m_jlSpezialitaet);

        //Bewertung
        m_jlSkill.setBackground(ColorLabelEntry.BG_STANDARD);
        m_jlSkill.setOpaque(false);
        m_jlSkill.setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 0));

        spezPanel.add(m_jlSkill);

        //MiniGruppe
  
        m_jlGroup.setBackground(ColorLabelEntry.BG_STANDARD);
        m_jlGroup.setVerticalAlignment(SwingConstants.BOTTOM);
        m_jlGroup.setOpaque(false);
        m_jlGroup.setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 1));
        spezPanel.add(m_jlGroup);

        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.weightx = 0.0;
        constraints.gridx = 3;
        layout.setConstraints(spezPanel, constraints);
        m_clComponent.add(spezPanel);

        if (m_clPlayer != null) {
            //Name
            
        	if (m_bCustomName == true) {
        		m_jlName.setText(m_sCustomNameString);
        	} else {
        		m_jlName.setText(m_clPlayer.getName());
        	}
        	
            m_jlName.setOpaque(false);
            m_jlName.setForeground(getForegroundForSpieler(m_clPlayer));

            //Trikot
            //&& m_clSpielerPositionAktuell != null )
            if (m_bShowTrikot) {
                m_jlName.setIcon(ImageUtilities.getImage4Position(m_clCurrentPlayerPosition,
                                                                                     m_clPlayer
                                                                                     .getTrikotnummer()));
                showGroupIcon();
            }

            //            else if ( m_bShowTrikot )
            //            {
            //                m_jlName.setIcon ( m_clLeer );
            //                m_jlGruppe.setIcon ( tools.Helper.getImageIcon4MiniGruppe ( m_clSpieler.getTeamInfoSmilie () ) );
            //            }
            
            updateDisplay(m_clPlayer);
        }

        m_clComponent.setPreferredSize(new Dimension(Helper.calcCellWidth(150),Helper.calcCellWidth(18)));
    }

	public final void updateComponent() {
        if (m_clPlayer != null) {
            m_jlName.setForeground(getForegroundForSpieler(m_clPlayer));

            //Trikot
            //&& m_clSpielerPositionAktuell != null )
            if (m_bShowTrikot) {
                m_jlName.setIcon(ImageUtilities.getImage4Position(m_clCurrentPlayerPosition,
                                                                                     m_clPlayer
                                                                                     .getTrikotnummer()));
                showGroupIcon();
            }
            updateDisplay(m_clPlayer);

        } else {
        	setEmptyLabel();
        }
    }

    /**
     * Aktualisierung des Entrys
     */
    public final void updateComponent(Spieler spieler, SpielerPosition positionAktuell,
                                      float positionsbewertung, String nameText) {
        m_clPlayer = spieler;
        m_clCurrentPlayerPosition = positionAktuell;
        m_fPositionsbewertung = positionsbewertung;
        m_sCustomNameString = nameText;

        if (m_clPlayer != null) {
            if (m_clPlayer.isOld()) {
                m_jlName.setForeground(ThemeManager.getColor(HOColorName.PLAYER_OLD_FG));//Color.GRAY);
            } else {
                m_jlName.setForeground(getForegroundForSpieler(m_clPlayer));
            }

            if (m_bCustomName == true) {
        		m_jlName.setText(m_sCustomNameString);
        	} else {
        		m_jlName.setText(m_clPlayer.getName());
        	}

            //Trikot
            //&& m_clSpielerPositionAktuell != null )
            if (m_bShowTrikot) {
                m_jlName.setIcon(ImageUtilities.getImage4Position(m_clCurrentPlayerPosition, m_clPlayer.getTrikotnummer()));
                showGroupIcon();
            }
            updateDisplay(m_clPlayer);

        } else {
        	setEmptyLabel();
        	m_jlGroup.setIcon(null);
        }

        m_clComponent.setPreferredSize(new Dimension(Helper.calcCellWidth(130),Helper.calcCellWidth(18)));  // Was 150,18 - setting lower solved lineup problem
    }
    
    
    private void showGroupIcon(){
    	String teamInfoSmilie = m_clPlayer.getTeamInfoSmilie();
        
        if(teamInfoSmilie.trim().equals("") )
        	m_jlGroup.setIcon(ImageUtilities.MINILEER);
        else
        	m_jlGroup.setIcon(ThemeManager.getScaledIcon(teamInfoSmilie, 8, 8));
    }
    private void setEmptyLabel(){
        m_jlName.setText("");
        m_jlName.setIcon(null);
        m_jlWeatherEffect.setIcon(null);
        m_jlSpezialitaet.setIcon(null);
        m_jlSkill.setText("");
    }
    
    private void updateDisplay(Spieler player){
    	// weatherEffect
    	 m_jlWeatherEffect.setIcon(null);
    	if (m_bShowWeatherEffect) {
    		int effect = PlayerSpeciality.getWeatherEffect(HOMainFrame.getWetter(),player.getSpezialitaet());
    		if(effect != 0){
    			final ImageIcon wettericon = ThemeManager.getIcon("weather.effect."+effect);
    			m_jlWeatherEffect.setIcon(wettericon);
    		}
        }
    	
    	m_jlSpezialitaet.setIcon( ThemeManager.getIcon(HOIconName.SPECIAL[player.getSpezialitaet()]));

        
        // positionValue
        if (m_bShowTrikot && (m_fPositionsbewertung != 0f)) {
            m_jlSkill.setText("(" + m_fPositionsbewertung + ")");
        } else {
            m_jlSkill.setText("");
        }
    	
    }

	//--------------static------------------------------
	public static Color getForegroundForSpieler(Spieler spieler) {
	    Color color;
	    UserParameter userParameter = ho.core.model.UserParameter.instance();
	    
	    //Auf Transfermarkt
	    if (spieler.getTransferlisted() > 0) {
	        color = userParameter.FG_TRANSFERMARKT;
	    }
	    //Verletzt
	    else if (spieler.getVerletzt() > 0) {
	        color = userParameter.FG_VERLETZT;
	    }
	    //Gesperrt
	    else if (spieler.isGesperrt()) {
	        color = userParameter.FG_GESPERRT;
	    }
	    //Angeschlagen
	    else if (spieler.getVerletzt() == 0) {
	        color = userParameter.FG_ANGESCHLAGEN;
	    }
	    //Zwei Karten
	    else if (spieler.getGelbeKarten() == 2) {
	        color = userParameter.FG_ZWEIKARTEN;
	    }
	    //Unverletzt
	    else {
	        color = userParameter.FG_STANDARD;
	    }
	
	    return color;
	}
}
