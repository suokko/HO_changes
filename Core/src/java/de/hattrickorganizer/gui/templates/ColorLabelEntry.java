// %12896550:de.hattrickorganizer.gui.templates%
package de.hattrickorganizer.gui.templates;

import gui.UserParameter;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import plugins.IHOTableEntry;
import de.hattrickorganizer.gui.model.SpielerTableRenderer;
import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.tools.Helper;


/**
 *
 */
public class ColorLabelEntry extends TableEntry {
    //~ Static fields/initializers -----------------------------------------------------------------

	   public static Color FG_STANDARD = ThemeManager.getColor("tableEntry.foreground");//gui.UserParameter.instance().FG_STANDARD;
	   public static Color FG_GRAU = ThemeManager.getColor("gray"); // Color.gray
	   protected static Color FG_VERLETZT = gui.UserParameter.instance().FG_VERLETZT;
	   static final Color FG_VERBESSERUNG = new Color(0, 200, 0);
	   static final Color FG_VERSCHLECHTERUNG = new Color(200, 0, 0);
	   public static Color FG_TRANSFERMARKT = gui.UserParameter.instance().FG_TRANSFERMARKT;
	   public static final Color BG_STANDARD = ThemeManager.getColor("tableEntry.background");
	   public static final Color BG_SPIELERSONDERWERTE = ThemeManager.getColor("tableEntry.player.skill.special.background");
	   public static final Color BG_SPIELEREINZELWERTE = ThemeManager.getColor("tableEntry.player.skill.background");//new Color(255, 255, 200);
	   public static final Color BG_SPIELERPOSITONSWERTE = ThemeManager.getColor("tableEntry.player.position.background");//new Color(220, 220, 255);
	   public static final Color BG_SPIELERSUBPOSITONSWERTE = ThemeManager.getColor("tableEntry.player.subposition.background");//new Color(235, 235, 255);
	   public static final Color BG_FLAGGEN = BG_STANDARD; //new Color(222, 218, 210);

    //~ Instance fields ----------------------------------------------------------------------------

    private Color m_clBGColor = ColorLabelEntry.BG_STANDARD;
    private Color m_clFGColor = ColorLabelEntry.FG_STANDARD;
    private Font m_clFont;
    private Icon m_clIcon;
    private JLabel m_clComponent;
    private String m_sText = "";
    private String m_sTooltip = "";
    
    //Für Compareto
    private double m_dZahl = Double.NEGATIVE_INFINITY;
    private int m_iAusrichtung = SwingConstants.LEFT;
    private int m_iFontStyle = Font.PLAIN;
    private int m_iImageAusrichtung = SwingConstants.TRAILING;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * ColorLabel ohne Icon
     *
     */
    public ColorLabelEntry(String text, Color foreground, Color background,
                           int horizontalAusrichtung) {
        m_sText = text;
        m_dZahl = Double.NEGATIVE_INFINITY;
        m_iAusrichtung = horizontalAusrichtung;
        m_clFGColor = foreground;
        m_clBGColor = background;
        createComponent();
    }

    /**
     * Creates a new ColorLabelEntry object.
     */
    public ColorLabelEntry(int number, Color foreground, Color background, int horizontalAusrichtung) {
    	this(String.valueOf(number),foreground,background,horizontalAusrichtung);
    }

    /**
     * ColorLabel nur mit Icon und sortindex
     */
    public ColorLabelEntry(Icon icon, double sortindex, Color foreground, Color background,
                           int horizontalAusrichtung) {
        m_sText = "";
        m_dZahl = sortindex;
        m_clIcon = icon;
        m_iAusrichtung = horizontalAusrichtung;
        m_clFGColor = foreground;
        m_clBGColor = background;
        createComponent();
    }

    /**
     * ColorLabel nur mit Text und sortindex
     */
    public ColorLabelEntry(double sortindex, String text, Color foreground, Color background,
                           int horizontalAusrichtung) {
        m_sText = text;
        m_dZahl = sortindex;
        m_clIcon = null;
        m_iAusrichtung = horizontalAusrichtung;
        m_clFGColor = foreground;
        m_clBGColor = background;
        createComponent();
    }

    /**
     * ColorLabel mit Image zur Darstellung von Veränderungen
     *
     */
    public ColorLabelEntry(int intzahl, double zahl, boolean aktuell, Color background,
                           boolean mitText) {
        if ((Math.abs(intzahl) != 0) || !mitText) {
            m_clIcon = Helper.getImageIcon4Veraenderung((int) Helper.round(intzahl, 1), aktuell);
        }

        m_iAusrichtung = SwingConstants.RIGHT;
        m_clBGColor = background;
    	
        // Create Component first, then change the text accordingly [setValueAsText()]
        createComponent();

        if ((intzahl == 0) && (Math.abs(zahl) > 0.005d) && mitText) {
            //Keine negativen Subskills, Kann beim Skillup passieren
            final double zahl2 = intzahl + Math.max(0d, zahl);
            setValueAsText(zahl2, background, false, false, 
            		gui.UserParameter.instance().anzahlNachkommastellen, true);
        }
    }

    /**
     * ColorLabel mit Image zur Darstellung von Veränderungen (mit Text als String)
     *
     * @param changeVal 	Change value for the icon
     * @param text 			text to show 
     * @param sortVal 		value for sort
     * @param aktuell		current or old data set
     * @param background 	background color
     * @param mitText 		show the text?
     */
    public ColorLabelEntry(int changeVal, String text, double sortVal, boolean aktuell, Color background,
                           boolean mitText) {
        if ((Math.abs(changeVal) != 0) || !mitText) {
            m_clIcon = Helper.getImageIcon4Veraenderung((int) Helper.round(changeVal, 1), aktuell);
        }

        m_iAusrichtung = SwingConstants.RIGHT;
        m_clBGColor = background;
        m_dZahl = sortVal;
    	
        // Create Component first, then change the text accordingly [setValueAsText()]
        createComponent();
        
        if (mitText)
        	setText(text);
        else
        	setText("");
    }

//    /**
//     * ColorLabel zu darstellen von Veränderungen
//     *
//     */
//    public ColorLabelEntry(float zahl) {
//        this(zahl, ColorLabelEntry.BG_STANDARD, false);
//    }


 
    /**
     * ColorLabel zu darstellen von Veränderungen mit Hintergrundfarbe
     *
     */
    public ColorLabelEntry(float zahl, Color bg_color, boolean currencyformat) {
        this(zahl, bg_color, currencyformat, false,0);
    }

//    /**
//     * Creates a new ColorLabelEntry object.
//     *
//     */
//    public ColorLabelEntry(float zahl, java.awt.Color bg_color, boolean currencyformat,
//                           boolean farbeInvertieren) {
//        this(zahl, bg_color, currencyformat, farbeInvertieren, 0);
//    }

    /**
     * ColorLabel zu darstellen von Veränderungen mit Hintergrundfarbe
     *
     */
    public ColorLabelEntry(float zahl, Color bg_color, boolean currencyformat,
                           boolean farbeInvertieren, int nachkommastellen) {
    	m_iAusrichtung = SwingConstants.RIGHT;
        createComponent();
    	setValueAsText(zahl, bg_color, currencyformat, farbeInvertieren, 
    			nachkommastellen, true);
    }

    /**
     * ColorLabel zu darstellen von Geldwert mit Hintergrundfarbe, nachkommstellen sind nur für
     * nichtcurrency interessant
     *
     */
    public ColorLabelEntry(double zahl, Color bg_color, boolean currencyformat,
                           int nachkommastellen) {
        m_iAusrichtung = SwingConstants.RIGHT;
        createComponent();
        setValueAsText(zahl, bg_color, currencyformat, false, nachkommastellen, 
        		false);
    }

    /**
     * Sammelmethode, um den Wert aus 'zahl' zu formatieren und die Instanz-Felder 
     * dementsprechend füllen. 
    */
    private void setValueAsText (double zahl, Color bg_color, boolean currencyformat,
            boolean farbeInvertieren, int nachkommastellen, boolean colorAndSign) { 
        m_dZahl = zahl;

        m_sText = (m_dZahl>0&&colorAndSign?"+":"") + 
        				Helper.getNumberFormat(currencyformat, nachkommastellen).format(m_dZahl);
        	
        if (colorAndSign) {
        	if (m_dZahl > 0 && !farbeInvertieren ||
        		m_dZahl < 0 && farbeInvertieren) {
        		// Positiv
        		m_clFGColor = ColorLabelEntry.FG_VERBESSERUNG;    		
        	} else if (m_dZahl == 0) {
        		// Neutral
        		m_sText = "";
        		m_clFGColor = ColorLabelEntry.FG_STANDARD;    		
        	} else {
        		// Negativ
        		m_clFGColor = ColorLabelEntry.FG_VERSCHLECHTERUNG;
        	}
        }
        if (bg_color != null)
        	m_clBGColor = bg_color;
        updateComponent();
    }

    public final void setAusrichtung(int ausrichtung) {
        m_iAusrichtung = ausrichtung;
        updateComponent();
    }

    public final void setBGColor(Color bgcolor) {
        m_clBGColor = bgcolor;
        updateComponent();
    }

    @Override
	public final JComponent getComponent(boolean isSelected) {
        m_clComponent.setOpaque(true);
        if (isSelected) {
            m_clComponent.setBackground(SpielerTableRenderer.SELECTION_BG);
        } else {
            m_clComponent.setBackground(m_clBGColor);
        }

        return m_clComponent;
    }

    //--------------static------------------------------
    public static Color getForegroundForSpieler(plugins.ISpieler spieler) {
        Color color;
        UserParameter userParameter = gui.UserParameter.instance();
        
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

    public final void setFGColor(Color fgcolor) {
        m_clFGColor = fgcolor;
        updateComponent();
    }

    public final void setFont(Font font) {
        m_clFont = font;
        updateComponent();
    }


    public final void setFontStyle(int fontStyle) {
        m_iFontStyle = fontStyle;
        updateComponent();
    }

 
    /**
     * Ändern der Grafik der Veränderung (Für Werte ohne Sub, d.h. Form/Kondi/XP...)
     *
     */
    public final void setGrafischeVeraenderungswert(double zahl, boolean aktuell, boolean mitText) {
        m_clIcon = Helper.getImageIcon4Veraenderung((int) Helper.round(zahl, 1),aktuell);

        if (mitText) {
            setGraphicalChangeValue(zahl);
        }
        updateComponent();
    }

    /**
     * Ändern der Grafik der Veränderung (Für Werte mit Sub, d.h. z.B. die normalen Skills)
     *
      */
    public final void setGrafischeVeraenderungswert(int intzahl, double zahl, boolean aktuell,
                                                    boolean mitText) {
        m_clIcon = Helper.getImageIcon4Veraenderung((int) Helper.round(intzahl, 1),aktuell);

        if (mitText) {
            //Keine negativen Subskills, kann beim Levelup passieren
            final double zahl2 = intzahl + Math.max(0d, zahl);
            setGraphicalChangeValue(zahl2);
        }
        updateComponent();
    }

    /**
     * 
     * @param number
     */
    private final void setGraphicalChangeValue(double number){
    	setValueAsText(number, null, false, false, 
    			gui.UserParameter.instance().anzahlNachkommastellen, 
    			true);
    }
 
    public final void setIcon(Icon icon) {
        m_clIcon = icon;
        updateComponent();
    }


    public final void setIcon(Icon icon, int imageAusrichtung) {
        m_clIcon = icon;
        m_iImageAusrichtung = imageAusrichtung;
        updateComponent();
    }

    public final void setIconWithSort(Icon icon, double sortindex) {
        m_clIcon = icon;
        m_dZahl = sortindex;
        updateComponent();
    }

    public final void setSpezialNumber(int zahl, boolean currencyformat) {
        setSpezialNumber(zahl, currencyformat, false);
    }

    public final void setSpezialNumber(int zahl, boolean currencyformat, boolean showZero) {
    	setValueAsText(zahl, null, currencyformat, false, 0, true);
    	if (zahl == 0 && !showZero) {
    		m_sText = "";
    		updateComponent();
    	}
    }

    public final void setSpezialNumber(float zahl, boolean currencyformat) {
    	setValueAsText(zahl, null, currencyformat, false, 
    			gui.UserParameter.instance().anzahlNachkommastellen, 
    			true);
    }

    //----Zugriff----------------------------
    public final void setText(String text) {
        m_sText = text;
        updateComponent();
    }

    public final String getText() {
        return m_sText;
    }

    public final void setToolTipText(String text) {
        m_sTooltip = text;
        updateComponent();
    }

    public final double getZahl() {
        return m_dZahl;
    }


    @Override
	public final void clear() {
        m_sText = "";
        m_clIcon = null;
        updateComponent();
    }

    /**
     * Vergleich zum Sortieren
     *
     */
    @Override
	public final int compareTo(IHOTableEntry obj) {
        if (obj instanceof ColorLabelEntry) {
            final ColorLabelEntry entry = (ColorLabelEntry) obj;

            //Zahl?
            if (m_dZahl != Float.NEGATIVE_INFINITY) {
                final double zahl1 = m_dZahl;
                final double zahl2 = entry.getZahl();

                if (zahl1 < zahl2) {
                    return -1;
                } else if (zahl1 > zahl2) {
                    return 1;
                } else {
                    return m_sText.compareTo(entry.getText());
                }
            }
            //Not number -> String
            return m_sText.compareTo(entry.getText());
            
        }

        return 0;
    }

    //-------------------------------------------------------------    

    /**
     * Erstellt eine passende Komponente
     */
    @Override
	public final void createComponent() {
        JLabel label;

        if (m_clIcon != null) {
        	m_clComponent = new JLabel(m_sText, m_clIcon, m_iAusrichtung);
        } else {
        	m_clComponent = new JLabel(m_sText, m_iAusrichtung);
        }

        m_clComponent.setForeground(m_clFGColor);
        m_clComponent.setToolTipText(m_sTooltip);

        m_clFont = m_clComponent.getFont();
    }

    @Override
	public final void updateComponent() {
         m_clComponent.setText(m_sText);
         m_clComponent.setIcon(m_clIcon);
         m_clComponent.setHorizontalAlignment(m_iAusrichtung);
         m_clComponent.setHorizontalTextPosition(m_iImageAusrichtung);
         m_clComponent.setBackground(m_clBGColor);
         m_clComponent.setForeground(m_clFGColor);
         m_clComponent.setFont(m_clFont.deriveFont(m_iFontStyle));
         m_clComponent.setToolTipText(m_sTooltip);
    }
}
