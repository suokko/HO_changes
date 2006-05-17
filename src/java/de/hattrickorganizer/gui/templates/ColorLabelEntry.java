// %12896550:de.hattrickorganizer.gui.templates%
package de.hattrickorganizer.gui.templates;

import gui.UserParameter;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import de.hattrickorganizer.gui.model.SpielerTableRenderer;
import de.hattrickorganizer.tools.Helper;


/**
 *
 */
public class ColorLabelEntry extends TableEntry {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public static Color FG_STANDARD = gui.UserParameter.instance().FG_STANDARD;

    /** TODO Missing Parameter Documentation */
    public static Color FG_GRAU = Color.gray;

    /** TODO Missing Parameter Documentation */
    protected static Color FG_VERLETZT = gui.UserParameter.instance().FG_VERLETZT;

    /** TODO Missing Parameter Documentation */
    public static final Color FG_VERBESSERUNG = new Color(0, 200, 0);

    /** TODO Missing Parameter Documentation */
    public static final Color FG_VERSCHLECHTERUNG = new Color(200, 0, 0);

    /** TODO Missing Parameter Documentation */
    public static Color FG_TRANSFERMARKT = gui.UserParameter.instance().FG_TRANSFERMARKT;

    /** TODO Missing Parameter Documentation */
    public static final Color BG_STANDARD = Color.WHITE;

    /** TODO Missing Parameter Documentation */
    public static final Color BG_SPIELERSONDERWERTE = new Color(220, 255, 220);

    /** TODO Missing Parameter Documentation */
    public static final Color BG_SPIELEREINZELWERTE = new Color(255, 255, 200);

    /** TODO Missing Parameter Documentation */
    public static final Color BG_SPIELERPOSITONSWERTE = new Color(220, 220, 255);

    /** TODO Missing Parameter Documentation */
    public static final Color BG_SPIELERSUBPOSITONSWERTE = new Color(235, 235, 255);

    /** TODO Missing Parameter Documentation */
    public static final Color BG_FLAGGEN = new Color(222, 218, 210);
    private static java.text.NumberFormat CURRENCYFORMAT = java.text.NumberFormat
                                                           .getCurrencyInstance();

    //~ Instance fields ----------------------------------------------------------------------------

    private Color m_clBGColor = ColorLabelEntry.BG_STANDARD;
    private Color m_clFGColor = ColorLabelEntry.FG_STANDARD;
    private Font m_clFont;
    private Icon m_clIcon;
    private JComponent m_clComponent;
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
     * @param text TODO Missing Constructuor Parameter Documentation
     * @param foreground TODO Missing Constructuor Parameter Documentation
     * @param background TODO Missing Constructuor Parameter Documentation
     * @param horizontalAusrichtung TODO Missing Constructuor Parameter Documentation
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
     *
     * @param number TODO Missing Constructuor Parameter Documentation
     * @param foreground TODO Missing Constructuor Parameter Documentation
     * @param background TODO Missing Constructuor Parameter Documentation
     * @param horizontalAusrichtung TODO Missing Constructuor Parameter Documentation
     */
    public ColorLabelEntry(int number, Color foreground, Color background, int horizontalAusrichtung) {
    	this(String.valueOf(number),foreground,background,horizontalAusrichtung);
    }

    /**
     * ColorLabel nur mit Icon und sortindex
     *
     * @param icon TODO Missing Constructuor Parameter Documentation
     * @param sortindex TODO Missing Constructuor Parameter Documentation
     * @param foreground TODO Missing Constructuor Parameter Documentation
     * @param background TODO Missing Constructuor Parameter Documentation
     * @param horizontalAusrichtung TODO Missing Constructuor Parameter Documentation
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
     *
     * @param sortindex TODO Missing Constructuor Parameter Documentation
     * @param text TODO Missing Constructuor Parameter Documentation
     * @param foreground TODO Missing Constructuor Parameter Documentation
     * @param background TODO Missing Constructuor Parameter Documentation
     * @param horizontalAusrichtung TODO Missing Constructuor Parameter Documentation
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
     * @param intzahl TODO Missing Constructuor Parameter Documentation
     * @param zahl TODO Missing Constructuor Parameter Documentation
     * @param aktuell TODO Missing Constructuor Parameter Documentation
     * @param background TODO Missing Constructuor Parameter Documentation
     * @param mitText TODO Missing Constructuor Parameter Documentation
     */
    public ColorLabelEntry(int intzahl, double zahl, boolean aktuell, Color background,
                           boolean mitText) {
        if ((Math.abs(intzahl) != 0) || !mitText) {
            m_clIcon = Helper.getImageIcon4Veraenderung((int) Helper.round(intzahl, 1), aktuell);
        }

        if ((intzahl == 0) && (Math.abs(zahl) > 0.005d) && mitText) {
            //Keine negativen Subskills, Kann beim Skillup passieren
            final double zahl2 = intzahl + Math.max(0d, zahl);

            String text = "";

            if (gui.UserParameter.instance().anzahlNachkommastellen == 1) {
                text = Helper.DEFAULTDEZIMALFORMAT.format(Helper.round(zahl2,gui.UserParameter
                                                                                            .instance().anzahlNachkommastellen));
            } else {
                text = Helper.DEZIMALFORMAT_2STELLEN.format(Helper.round(zahl2,gui.UserParameter
                                                                                              .instance().anzahlNachkommastellen));
            }

            if (zahl2 > 0) {
                m_clFGColor = ColorLabelEntry.FG_VERBESSERUNG;

                m_sText = "+" + text;
            } else if (zahl2 < 0) {
                m_clFGColor = ColorLabelEntry.FG_VERSCHLECHTERUNG;
                m_sText = "" + text;
            } else {
                m_sText = "";
                m_clFGColor = ColorLabelEntry.FG_STANDARD;
            }
        }

        m_clBGColor = background;
        m_iAusrichtung = SwingConstants.RIGHT;
        createComponent();

        //m_clComponent.setFont( m_clComponent.getFont().deriveFont( m_clComponent.getFont().getSize2D() - 4f ) );
    }


    /**
     * ColorLabel zu darstellen von Veränderungen
     *
     * @param zahl TODO Missing Constructuor Parameter Documentation
     */
    public ColorLabelEntry(float zahl) {
        this(zahl, ColorLabelEntry.BG_STANDARD, false);
    }


 
    /**
     * ColorLabel zu darstellen von Veränderungen mit Hintergrundfarbe
     *
     * @param zahl TODO Missing Constructuor Parameter Documentation
     * @param bg_color TODO Missing Constructuor Parameter Documentation
     * @param currencyformat TODO Missing Constructuor Parameter Documentation
     */
    public ColorLabelEntry(float zahl, java.awt.Color bg_color, boolean currencyformat) {
        this(zahl, bg_color, currencyformat, false);
    }

    /**
     * Creates a new ColorLabelEntry object.
     *
     * @param zahl TODO Missing Constructuor Parameter Documentation
     * @param bg_color TODO Missing Constructuor Parameter Documentation
     * @param currencyformat TODO Missing Constructuor Parameter Documentation
     * @param farbeInvertieren TODO Missing Constructuor Parameter Documentation
     */
    public ColorLabelEntry(float zahl, java.awt.Color bg_color, boolean currencyformat,
                           boolean farbeInvertieren) {
        this(zahl, bg_color, currencyformat, farbeInvertieren, 1);
    }

    /**
     * ColorLabel zu darstellen von Veränderungen mit Hintergrundfarbe
     *
     * @param zahl TODO Missing Constructuor Parameter Documentation
     * @param bg_color TODO Missing Constructuor Parameter Documentation
     * @param currencyformat TODO Missing Constructuor Parameter Documentation
     * @param farbeInvertieren TODO Missing Constructuor Parameter Documentation
     * @param nachkommastellen TODO Missing Constructuor Parameter Documentation
     */
    public ColorLabelEntry(float zahl, java.awt.Color bg_color, boolean currencyformat,
                           boolean farbeInvertieren, int nachkommastellen) {
        m_dZahl = zahl;

        if (m_dZahl > 0) {
            if (currencyformat) {
                m_sText = "+" + CURRENCYFORMAT.format(m_dZahl);
            } else {
                m_sText = "+" + Helper.round(m_dZahl, nachkommastellen);
            }

            if (!farbeInvertieren) {
                m_clFGColor = ColorLabelEntry.FG_VERBESSERUNG;
            } else {
                m_clFGColor = ColorLabelEntry.FG_VERSCHLECHTERUNG;
            }
        } else if (m_dZahl < 0) {
            if (currencyformat) {
                m_sText = "" + CURRENCYFORMAT.format(m_dZahl);
            } else {
                m_sText = "" + Helper.round(m_dZahl, nachkommastellen);
            }

            if (!farbeInvertieren) {
                m_clFGColor = ColorLabelEntry.FG_VERSCHLECHTERUNG;
            } else {
                m_clFGColor = ColorLabelEntry.FG_VERBESSERUNG;
            }
        } else {
            m_sText = "";
            m_clFGColor = ColorLabelEntry.FG_STANDARD;
        }

        m_clBGColor = bg_color;
        m_iAusrichtung = SwingConstants.RIGHT;
        createComponent();
    }

    /**
     * ColorLabel zu darstellen von Geldwert mit Hintergrundfarbe, nachkommstellen sind nur für
     * nichtcurrency interessant
     *
     * @param zahl TODO Missing Constructuor Parameter Documentation
     * @param bg_color TODO Missing Constructuor Parameter Documentation
     * @param currencyformat TODO Missing Constructuor Parameter Documentation
     * @param nachkommastellen TODO Missing Constructuor Parameter Documentation
     */
    public ColorLabelEntry(double zahl, java.awt.Color bg_color, boolean currencyformat,
                           int nachkommastellen) {
        m_dZahl = zahl;

        if (currencyformat) {
            m_sText = CURRENCYFORMAT.format(zahl);
        } else {
            final java.text.NumberFormat format = java.text.NumberFormat.getInstance();
            format.setMaximumFractionDigits(nachkommastellen);
            format.setMinimumFractionDigits(nachkommastellen);

            m_sText = format.format(zahl).replace(',', '.');
        }

        m_clBGColor = bg_color;
        m_iAusrichtung = SwingConstants.RIGHT;
        createComponent();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param ausrichtung TODO Missing Method Parameter Documentation
     */
    public final void setAusrichtung(int ausrichtung) {
        m_iAusrichtung = ausrichtung;
        updateComponent();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param bgcolor TODO Missing Method Parameter Documentation
     */
    public final void setBGColor(Color bgcolor) {
        m_clBGColor = bgcolor;
        updateComponent();
    }

    /**
     * Gibt eine passende Komponente zurück
     *
     * @param isSelected TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final JComponent getComponent(boolean isSelected) {
        if (isSelected) {
            m_clComponent.setOpaque(true);
            m_clComponent.setBackground(SpielerTableRenderer.SELECTION_BG);
        } else {
            m_clComponent.setOpaque(true);
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

     /**
     * TODO Missing Method Documentation
     *
     * @param fgcolor TODO Missing Method Parameter Documentation
     */
    public final void setFGColor(Color fgcolor) {
        m_clFGColor = fgcolor;
        updateComponent();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param font TODO Missing Method Parameter Documentation
     */
    public final void setFont(Font font) {
        m_clFont = font;
        updateComponent();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param fontStyle TODO Missing Method Parameter Documentation
     */
    public final void setFontStyle(int fontStyle) {
        m_iFontStyle = fontStyle;
        updateComponent();
    }

 
    /**
     * Ändern der Grafik der Veränderung
     *
     * @param zahl TODO Missing Constructuor Parameter Documentation
     * @param aktuell TODO Missing Constructuor Parameter Documentation
     * @param mitText TODO Missing Constructuor Parameter Documentation
     */
    public final void setGrafischeVeraenderungswert(double zahl, boolean aktuell, boolean mitText) {
        m_clIcon = Helper.getImageIcon4Veraenderung((int) Helper.round(zahl, 1),aktuell);

        if (mitText) {
            setGraphicalChangeValue(zahl);
        }

        updateComponent();
    }

    /**
     * Ändern der Grafik der Veränderung
     *
     * @param intzahl TODO Missing Constructuor Parameter Documentation
     * @param zahl TODO Missing Constructuor Parameter Documentation
     * @param aktuell TODO Missing Constructuor Parameter Documentation
     * @param mitText TODO Missing Constructuor Parameter Documentation
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
    	String text = "";
    	if (gui.UserParameter.instance().anzahlNachkommastellen == 1) {
            text = Helper.DEFAULTDEZIMALFORMAT.format(Helper.round(number, gui.UserParameter
                                                                                        .instance().anzahlNachkommastellen));
        } else {
            text = Helper.DEZIMALFORMAT_2STELLEN.format(Helper.round(number,gui.UserParameter
                                                                                          .instance().anzahlNachkommastellen));
        }
    	if (number > 0) {
            m_clFGColor = ColorLabelEntry.FG_VERBESSERUNG;

            m_sText = "+" + text;
        } else if (number < 0) {
            m_clFGColor = ColorLabelEntry.FG_VERSCHLECHTERUNG;
            m_sText = "" + text;
        } else {
            m_sText = "";
            m_clFGColor = ColorLabelEntry.FG_STANDARD;
        }
    }
    /**
     * TODO Missing Method Documentation
     *
     * @param icon TODO Missing Method Parameter Documentation
     */
    public final void setIcon(Icon icon) {
        m_clIcon = icon;
        updateComponent();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param icon TODO Missing Method Parameter Documentation
     * @param imageAusrichtung TODO Missing Method Parameter Documentation
     */
    public final void setIcon(Icon icon, int imageAusrichtung) {
        m_clIcon = icon;
        m_iImageAusrichtung = imageAusrichtung;
        updateComponent();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param icon TODO Missing Method Parameter Documentation
     * @param sortindex TODO Missing Method Parameter Documentation
     */
    public final void setIconWithSort(Icon icon, double sortindex) {
        m_clIcon = icon;
        m_dZahl = sortindex;
        updateComponent();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param zahl TODO Missing Method Parameter Documentation
     * @param currencyformat TODO Missing Method Parameter Documentation
     */
    public final void setSpezialNumber(int zahl, boolean currencyformat) {
        setSpezialNumber(zahl, currencyformat, false);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param zahl TODO Missing Method Parameter Documentation
     * @param currencyformat TODO Missing Method Parameter Documentation
     * @param showZero TODO Missing Method Parameter Documentation
     */
    public final void setSpezialNumber(int zahl, boolean currencyformat, boolean showZero) {
        if (zahl > 0) {
            if (currencyformat) {
                m_sText = "+" + CURRENCYFORMAT.format(zahl);
            } else {
                m_sText = "+" + zahl;
            }

            m_clFGColor = ColorLabelEntry.FG_VERBESSERUNG;
        } else if (zahl < 0) {
            if (currencyformat) {
                m_sText = CURRENCYFORMAT.format(zahl);
            } else {
                m_sText = "" + zahl;
            }

            m_clFGColor = ColorLabelEntry.FG_VERSCHLECHTERUNG;
        } else {
            if (showZero) {
                m_sText = "0";
            } else {
                m_sText = "";
            }

            m_clFGColor = ColorLabelEntry.FG_STANDARD;
        }

        updateComponent();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param zahl TODO Missing Method Parameter Documentation
     * @param currencyformat TODO Missing Method Parameter Documentation
     */
    public final void setSpezialNumber(float zahl, boolean currencyformat) {
        final float newZahl = Helper.round(zahl,gui.UserParameter.instance().anzahlNachkommastellen);

        if (newZahl > 0) {
            if (currencyformat) {
                m_sText = "+" + CURRENCYFORMAT.format(newZahl);
            } else {
                m_sText = "+" + newZahl;
            }

            m_clFGColor = ColorLabelEntry.FG_VERBESSERUNG;
        } else if (newZahl < 0) {
            if (currencyformat) {
                m_sText = CURRENCYFORMAT.format(newZahl);
            } else {
                m_sText = "" + newZahl;
            }

            m_clFGColor = ColorLabelEntry.FG_VERSCHLECHTERUNG;
        } else {
            m_sText = "";
            m_clFGColor = ColorLabelEntry.FG_STANDARD;
        }

        updateComponent();
    }

    //----Zugriff----------------------------
    public final void setText(String text) {
        m_sText = text;
        updateComponent();
    }

    /**
     * Gibt den Text zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String getText() {
        return m_sText;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param text TODO Missing Method Parameter Documentation
     */
    public final void setToolTipText(String text) {
        m_sTooltip = text;
        updateComponent();
    }

    /**
     * Gibt die Zahl zurück
     *
     * @return TODO Missing Return Method Documentation
     */
    public final double getZahl() {
        return m_dZahl;
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void clear() {
        m_sText = "";
        m_clIcon = null;
        updateComponent();
    }

    /**
     * Vergleich zum Sortieren
     *
     * @param obj TODO Missing Constructuor Parameter Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int compareTo(Object obj) {
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
    public final void createComponent() {
        JLabel label;

        if (m_clIcon != null) {
            label = new JLabel(m_sText, m_clIcon, m_iAusrichtung);
        } else {
            label = new JLabel(m_sText, m_iAusrichtung);
        }

        label.setForeground(m_clFGColor);
        label.setToolTipText(m_sTooltip);

        m_clFont = label.getFont();

        m_clComponent = label;
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void updateComponent() {
        ((JLabel) m_clComponent).setText(m_sText);
        ((JLabel) m_clComponent).setIcon(m_clIcon);
        ((JLabel) m_clComponent).setHorizontalAlignment(m_iAusrichtung);
        ((JLabel) m_clComponent).setHorizontalTextPosition(m_iImageAusrichtung);
        ((JLabel) m_clComponent).setBackground(m_clBGColor);
        ((JLabel) m_clComponent).setForeground(m_clFGColor);
        ((JLabel) m_clComponent).setFont(m_clFont.deriveFont(m_iFontStyle));
        ((JLabel) m_clComponent).setToolTipText(m_sTooltip);
    }
}
