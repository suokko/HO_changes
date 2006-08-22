// %1814492999:de.hattrickorganizer.gui.lineup%
/*
 * AufstellungsRatingPanel.java
 *
 * Created on 23. November 2004, 09:11
 */
package de.hattrickorganizer.gui.lineup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.hattrickorganizer.gui.templates.ColorLabelEntry;
import de.hattrickorganizer.gui.templates.RasenPanel;


/**
 * Zeigt das Rating f�r eine Aufstellung an
 *
 * @author Pirania
 */
final class AufstellungsRatingPanel extends RasenPanel {
    //~ Static fields/initializers -----------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    public static final boolean REIHENFOLGE_STURM2VERTEIDIGUNG = false;

    /** TODO Missing Parameter Documentation */
    public static final boolean REIHENFOLGE_VERTEIDIGUNG2STURM = true;

    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    double bottomcenter;

    /** TODO Missing Parameter Documentation */
    double bottomleft;

    /** TODO Missing Parameter Documentation */
    double bottomright;

    /** TODO Missing Parameter Documentation */
    double middle;

    /** TODO Missing Parameter Documentation */
    double topcenter;

    /** TODO Missing Parameter Documentation */
    double topleft;

    /** TODO Missing Parameter Documentation */
    double topright;
    private ColorLabelEntry m_clBottomCenterCompare = new ColorLabelEntry("", Color.BLACK,
                                                                          Color.WHITE,
                                                                          SwingConstants.CENTER);
    private ColorLabelEntry m_clBottomCenterMain = new ColorLabelEntry("", Color.BLACK,
                                                                       Color.WHITE,
                                                                       SwingConstants.RIGHT);
    private ColorLabelEntry m_clBottomLeftCompare = new ColorLabelEntry("", Color.BLACK,
                                                                        Color.WHITE,
                                                                        SwingConstants.CENTER);
    private ColorLabelEntry m_clBottomLeftMain = new ColorLabelEntry("", Color.BLACK, Color.WHITE,
                                                                     SwingConstants.RIGHT);
    private ColorLabelEntry m_clBottomRightCompare = new ColorLabelEntry("", Color.BLACK,
                                                                         Color.WHITE,
                                                                         SwingConstants.CENTER);
    private ColorLabelEntry m_clBottomRightMain = new ColorLabelEntry("", Color.BLACK, Color.WHITE,
                                                                      SwingConstants.RIGHT);
    private ColorLabelEntry m_clMiddleCompare = new ColorLabelEntry("", Color.BLACK, Color.WHITE,
                                                                    SwingConstants.CENTER);
    private ColorLabelEntry m_clMiddleMain = new ColorLabelEntry("", Color.BLACK, Color.WHITE,
                                                                 SwingConstants.RIGHT);
    private ColorLabelEntry m_clTopCenterCompare = new ColorLabelEntry("", Color.BLACK,
                                                                       Color.WHITE,
                                                                       SwingConstants.CENTER);
    private ColorLabelEntry m_clTopCenterMain = new ColorLabelEntry("", Color.BLACK, Color.WHITE,
                                                                    SwingConstants.RIGHT);
    private ColorLabelEntry m_clTopLeftCompare = new ColorLabelEntry("", Color.BLACK, Color.WHITE,
                                                                     SwingConstants.CENTER);
    private ColorLabelEntry m_clTopLeftMain = new ColorLabelEntry("", Color.BLACK, Color.WHITE,
                                                                  SwingConstants.RIGHT);
    private ColorLabelEntry m_clTopRightCompare = new ColorLabelEntry("", Color.BLACK, Color.WHITE,
                                                                      SwingConstants.CENTER);
    private ColorLabelEntry m_clTopRightMain = new ColorLabelEntry("", Color.BLACK, Color.WHITE,
                                                                   SwingConstants.RIGHT);
    private Dimension GROESSE = new Dimension(de.hattrickorganizer.tools.Helper.calcCellWidth(80),
                                              de.hattrickorganizer.tools.Helper.calcCellWidth(25));
    private JLabel m_clBottomCenterText = new JLabel("", SwingConstants.LEFT);
    private JLabel m_clBottomLeftText = new JLabel("", SwingConstants.LEFT);
    private JLabel m_clBottomRightText = new JLabel("", SwingConstants.LEFT);
    private JLabel m_clMiddleText = new JLabel("", SwingConstants.LEFT);
    private JLabel m_clTopCenterText = new JLabel("", SwingConstants.LEFT);
    private JLabel m_clTopLeftText = new JLabel("", SwingConstants.LEFT);
    private JLabel m_clTopRightText = new JLabel("", SwingConstants.LEFT);
    private JPanel m_clBottomCenterPanel = new JPanel(new BorderLayout());
    private JPanel m_clBottomLeftPanel = new JPanel(new BorderLayout());
    private JPanel m_clBottomRightPanel = new JPanel(new BorderLayout());
    private JPanel m_clMiddlePanel = new JPanel(new BorderLayout());
    private JPanel m_clTopCenterPanel = new JPanel(new BorderLayout());
    private JPanel m_clTopLeftPanel = new JPanel(new BorderLayout());
    private JPanel m_clTopRightPanel = new JPanel(new BorderLayout());
    private java.text.NumberFormat m_clFormat;
    private boolean m_bReihenfolge = REIHENFOLGE_STURM2VERTEIDIGUNG;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of AufstellungsRatingPanel
     */
    protected AufstellungsRatingPanel() {
        initComponents();

        if (gui.UserParameter.instance().anzahlNachkommastellen == 1) {
            m_clFormat = de.hattrickorganizer.tools.Helper.DEFAULTDEZIMALFORMAT;
        } else {
            m_clFormat = de.hattrickorganizer.tools.Helper.DEZIMALFORMAT_2STELLEN;
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     */
    public void clear() {
        m_clTopLeftText.setText("");
        m_clTopLeftMain.clear();
        m_clTopLeftCompare.clear();
        m_clTopCenterText.setText("");
        m_clTopCenterMain.clear();
        m_clTopCenterCompare.clear();
        m_clTopRightText.setText("");
        m_clTopRightMain.clear();
        m_clTopRightCompare.clear();
        m_clMiddleText.setText("");
        m_clMiddleMain.clear();
        m_clMiddleCompare.clear();
        m_clBottomLeftText.setText("");
        m_clBottomLeftMain.clear();
        m_clBottomLeftCompare.clear();
        m_clBottomCenterText.setText("");
        m_clBottomCenterMain.clear();
        m_clBottomCenterCompare.clear();
        m_clBottomRightText.setText("");
        m_clBottomRightMain.clear();
        m_clBottomRightCompare.clear();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    protected void setBottomCenter(double value) {
        m_clBottomCenterMain.setText(m_clFormat.format(value));
        m_clBottomCenterCompare.setSpezialNumber((float) (value - bottomcenter), false);
        bottomcenter = value;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param text TODO Missing Method Parameter Documentation
     */
    protected void setBottomCenterText(String text) {
        m_clBottomCenterText.setText(text);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    protected void setBottomLeft(double value) {
        m_clBottomLeftMain.setText(m_clFormat.format(value));
        m_clBottomLeftCompare.setSpezialNumber((float) (value - bottomleft), false);
        bottomleft = value;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param text TODO Missing Method Parameter Documentation
     */
    protected void setBottomLeftText(String text) {
        m_clBottomLeftText.setText(text);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    protected void setBottomRight(double value) {
        m_clBottomRightMain.setText(m_clFormat.format(value));
        m_clBottomRightCompare.setSpezialNumber((float) (value - bottomright), false);
        bottomright = value;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param text TODO Missing Method Parameter Documentation
     */
    protected void setBottomRightText(String text) {
        m_clBottomRightText.setText(text);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    protected void setMiddle(double value) {
        m_clMiddleMain.setText(m_clFormat.format(value));
        m_clMiddleCompare.setSpezialNumber((float) (value - middle), false);
        middle = value;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param text TODO Missing Method Parameter Documentation
     */
    protected void setMiddleText(String text) {
        m_clMiddleText.setText(text);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param reihenfolge TODO Missing Method Parameter Documentation
     */
    protected void setReihenfolge(boolean reihenfolge) {
        m_bReihenfolge = REIHENFOLGE_STURM2VERTEIDIGUNG;

        initToolTips();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    protected void setTopCenter(double value) {
        m_clTopCenterMain.setText(m_clFormat.format(value));
        m_clTopCenterCompare.setSpezialNumber((float) (value - topcenter), false);
        topcenter = value;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param text TODO Missing Method Parameter Documentation
     */
    protected void setTopCenterText(String text) {
        m_clTopCenterText.setText(text);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    protected void setTopLeft(double value) {
        m_clTopLeftMain.setText(m_clFormat.format(value));
        m_clTopLeftCompare.setSpezialNumber((float) (value - topleft), false);
        topleft = value;
    }

    ////////////////////////////////////////////////////////////////////////////
    protected void setTopLeftText(String text) {
        m_clTopLeftText.setText(text);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param value TODO Missing Method Parameter Documentation
     */
    protected void setTopRight(double value) {
        m_clTopRightMain.setText(m_clFormat.format(value));
        m_clTopRightCompare.setSpezialNumber((float) (value - topright), false);
        topright = value;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param text TODO Missing Method Parameter Documentation
     */
    protected void setTopRightText(String text) {
        m_clTopRightText.setText(text);
    }

    /**
     * TODO Missing Method Documentation
     */
    protected void calcColorBorders() {
        final int faktor = 60;
        double temp = 0d;
        Color tempcolor = null;
        final double durchschnitt = (topleft + topcenter + topright + middle + bottomleft
                                    + bottomcenter + bottomright) / 7d;

        //Topleft
        temp = topleft - durchschnitt;

        if (temp < 0) {
            tempcolor = new Color(Math.min(255, (int) (Math.abs(temp) * faktor)), 0, 0);
        } else {
            tempcolor = new Color(0, Math.min(255, (int) (temp * faktor)), 0);
        }

        m_clTopLeftPanel.setBorder(BorderFactory.createLineBorder(tempcolor, 2));

        //Topcenter
        temp = topcenter - durchschnitt;

        if (temp < 0) {
            tempcolor = new Color(Math.min(255, (int) (Math.abs(temp) * faktor)), 0, 0);
        } else {
            tempcolor = new Color(0, Math.min(255, (int) (temp * faktor)), 0);
        }

        m_clTopCenterPanel.setBorder(BorderFactory.createLineBorder(tempcolor, 2));

        //TopRight
        temp = topright - durchschnitt;

        if (temp < 0) {
            tempcolor = new Color(Math.min(255, (int) (Math.abs(temp) * faktor)), 0, 0);
        } else {
            tempcolor = new Color(0, Math.min(255, (int) (temp * faktor)), 0);
        }

        m_clTopRightPanel.setBorder(BorderFactory.createLineBorder(tempcolor, 2));

        //Middel
        temp = middle - durchschnitt;

        if (temp < 0) {
            tempcolor = new Color(Math.min(255, (int) (Math.abs(temp) * faktor)), 0, 0);
        } else {
            tempcolor = new Color(0, Math.min(255, (int) (temp * faktor)), 0);
        }

        m_clMiddlePanel.setBorder(BorderFactory.createLineBorder(tempcolor, 2));

        //Bottomleft
        temp = bottomleft - durchschnitt;

        if (temp < 0) {
            tempcolor = new Color(Math.min(255, (int) (Math.abs(temp) * faktor)), 0, 0);
        } else {
            tempcolor = new Color(0, Math.min(255, (int) (temp * faktor)), 0);
        }

        m_clBottomLeftPanel.setBorder(BorderFactory.createLineBorder(tempcolor, 2));

        //BottomCenter
        temp = bottomcenter - durchschnitt;

        if (temp < 0) {
            tempcolor = new Color(Math.min(255, (int) (Math.abs(temp) * faktor)), 0, 0);
        } else {
            tempcolor = new Color(0, Math.min(255, (int) (temp * faktor)), 0);
        }

        m_clBottomCenterPanel.setBorder(BorderFactory.createLineBorder(tempcolor, 2));

        //Bottomricht
        temp = bottomright - durchschnitt;

        if (temp < 0) {
            tempcolor = new Color(Math.min(255, (int) (Math.abs(temp) * faktor)), 0, 0);
        } else {
            tempcolor = new Color(0, Math.min(255, (int) (temp * faktor)), 0);
        }

        m_clBottomRightPanel.setBorder(BorderFactory.createLineBorder(tempcolor, 2));
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(1, 1, 1, 1);

        setBackground(Color.white);
        setLayout(layout);

        JComponent tempcomponent;
        JPanel temppanel;
        JPanel mainpanel;
        JPanel innerpanel;
        JPanel subpanel;

        GridBagLayout sublayout = new GridBagLayout();
        GridBagConstraints subconstraints = new GridBagConstraints();
        subconstraints.anchor = GridBagConstraints.CENTER;
        subconstraints.fill = GridBagConstraints.HORIZONTAL;
        subconstraints.weightx = 1.0;
        subconstraints.weighty = 0.0;
        subconstraints.insets = new Insets(1, 1, 1, 1);
        subpanel = new JPanel(sublayout);
        subpanel.setOpaque(false);

        //Platzhalter
        tempcomponent = new JLabel();
        tempcomponent.setFont(tempcomponent.getFont().deriveFont(tempcomponent.getFont().getSize2D()
                                                                 - 2f));
        tempcomponent.setPreferredSize(new Dimension(de.hattrickorganizer.tools.Helper
                                                     .calcCellWidth(10),
                                                     de.hattrickorganizer.tools.Helper
                                                     .calcCellWidth(2)));
        subconstraints.gridx = 1;
        subconstraints.gridy = 1;
        subconstraints.gridwidth = 1;

        sublayout.setConstraints(tempcomponent, subconstraints);
        subpanel.add(tempcomponent);

        //Top Center
        temppanel = new JPanel(new GridLayout(1, 2));
        temppanel.setOpaque(true);
        m_clTopCenterMain.setFontStyle(Font.BOLD);
        tempcomponent = m_clTopCenterMain.getComponent(false);
        tempcomponent.setOpaque(true);
        temppanel.add(tempcomponent);
        tempcomponent = m_clTopCenterCompare.getComponent(false);
        tempcomponent.setOpaque(true);
        temppanel.add(tempcomponent);

        innerpanel = new JPanel(new GridLayout(2, 1));
        innerpanel.setBackground(Color.white);
        innerpanel.setBorder(BorderFactory.createLineBorder(Color.white));
        innerpanel.add(m_clTopCenterText);
        innerpanel.add(temppanel);

        m_clTopCenterPanel.setBackground(Color.WHITE);
        m_clTopCenterText.setFont(m_clTopCenterText.getFont().deriveFont(m_clTopCenterText.getFont()
                                                                                          .getSize2D()
                                                                         - 1f));
        m_clTopCenterText.setOpaque(true);
        m_clTopCenterPanel.add(innerpanel, BorderLayout.CENTER);
        m_clTopCenterPanel.setPreferredSize(GROESSE);

        mainpanel = new JPanel(new BorderLayout());
        mainpanel.setBackground(Color.white);
        mainpanel.add(m_clTopCenterPanel, BorderLayout.CENTER);
        mainpanel.setBorder(BorderFactory.createLineBorder(Color.white));
        subconstraints.gridx = 2;
        subconstraints.gridy = 1;
        subconstraints.gridwidth = 3;
        sublayout.setConstraints(mainpanel, subconstraints);
        subpanel.add(mainpanel);

        //Platzhalter
        tempcomponent = new JLabel();
        tempcomponent.setFont(tempcomponent.getFont().deriveFont(tempcomponent.getFont().getSize2D()
                                                                 - 2f));
        tempcomponent.setPreferredSize(new Dimension(de.hattrickorganizer.tools.Helper
                                                     .calcCellWidth(10),
                                                     de.hattrickorganizer.tools.Helper
                                                     .calcCellWidth(2)));
        subconstraints.gridx = 5;
        subconstraints.gridy = 1;
        subconstraints.gridwidth = 1;

        sublayout.setConstraints(tempcomponent, subconstraints);
        subpanel.add(tempcomponent);

        constraints.gridx = 0;
        constraints.gridy = 0;
        layout.setConstraints(subpanel, constraints);
        add(subpanel);

        ////////////////////////////////////////////////////////////////////////
        sublayout = new GridBagLayout();
        subconstraints = new GridBagConstraints();
        subconstraints.anchor = GridBagConstraints.CENTER;
        subconstraints.fill = GridBagConstraints.HORIZONTAL;
        subconstraints.weightx = 1.0;
        subconstraints.weighty = 0.0;
        subconstraints.insets = new Insets(1, 1, 1, 1);
        subpanel = new JPanel(sublayout);
        subpanel.setOpaque(false);

        //Top Left
        temppanel = new JPanel(new GridLayout(1, 2));
        temppanel.setOpaque(true);
        m_clTopLeftMain.setFontStyle(Font.BOLD);
        tempcomponent = m_clTopLeftMain.getComponent(false);
        tempcomponent.setOpaque(true);
        temppanel.add(tempcomponent);
        tempcomponent = m_clTopLeftCompare.getComponent(false);
        tempcomponent.setOpaque(true);
        temppanel.add(tempcomponent);

        innerpanel = new JPanel(new GridLayout(2, 1));
        innerpanel.setBackground(Color.white);
        innerpanel.setBorder(BorderFactory.createLineBorder(Color.white));
        innerpanel.add(m_clTopLeftText);
        innerpanel.add(temppanel);

        m_clTopLeftPanel.setBackground(Color.WHITE);
        m_clTopLeftText.setFont(m_clTopLeftText.getFont().deriveFont(m_clTopLeftText.getFont()
                                                                                    .getSize2D()
                                                                     - 1f));
        m_clTopLeftText.setOpaque(true);
        m_clTopLeftPanel.add(innerpanel, BorderLayout.CENTER);
        m_clTopLeftPanel.setPreferredSize(GROESSE);

        mainpanel = new JPanel(new BorderLayout());
        mainpanel.setBackground(Color.white);
        mainpanel.add(m_clTopLeftPanel, BorderLayout.CENTER);
        mainpanel.setBorder(BorderFactory.createLineBorder(Color.white));
        subconstraints.gridx = 1;
        subconstraints.gridy = 2;
        subconstraints.gridwidth = 2;
        sublayout.setConstraints(mainpanel, subconstraints);
        subpanel.add(mainpanel);

        //Platzhalter
        tempcomponent = new JLabel();
        tempcomponent.setFont(tempcomponent.getFont().deriveFont(tempcomponent.getFont().getSize2D()
                                                                 - 2f));
        tempcomponent.setPreferredSize(new Dimension(de.hattrickorganizer.tools.Helper
                                                     .calcCellWidth(10),
                                                     de.hattrickorganizer.tools.Helper
                                                     .calcCellWidth(2)));
        subconstraints.gridx = 3;
        subconstraints.gridy = 2;
        subconstraints.gridwidth = 1;
        subconstraints.weightx = 0.0;

        sublayout.setConstraints(tempcomponent, subconstraints);
        subpanel.add(tempcomponent);

        //Top Right
        temppanel = new JPanel(new GridLayout(1, 2));
        temppanel.setOpaque(true);
        m_clTopRightMain.setFontStyle(Font.BOLD);
        tempcomponent = m_clTopRightMain.getComponent(false);
        tempcomponent.setOpaque(true);
        temppanel.add(tempcomponent);
        tempcomponent = m_clTopRightCompare.getComponent(false);
        tempcomponent.setOpaque(true);
        temppanel.add(tempcomponent);

        innerpanel = new JPanel(new GridLayout(2, 1));
        innerpanel.setBackground(Color.white);
        innerpanel.setBorder(BorderFactory.createLineBorder(Color.white));
        innerpanel.add(m_clTopRightText);
        innerpanel.add(temppanel);

        m_clTopRightPanel.setBackground(Color.WHITE);
        m_clTopRightText.setFont(m_clTopRightText.getFont().deriveFont(m_clTopRightText.getFont()
                                                                                       .getSize2D()
                                                                       - 1f));
        m_clTopRightText.setOpaque(true);
        m_clTopRightPanel.add(innerpanel, BorderLayout.CENTER);
        m_clTopRightPanel.setPreferredSize(GROESSE);

        mainpanel = new JPanel(new BorderLayout());
        mainpanel.setBackground(Color.white);
        mainpanel.add(m_clTopRightPanel, BorderLayout.CENTER);
        mainpanel.setBorder(BorderFactory.createLineBorder(Color.white));
        subconstraints.gridx = 4;
        subconstraints.gridy = 2;
        subconstraints.gridwidth = 2;
        subconstraints.weightx = 1.0;
        sublayout.setConstraints(mainpanel, subconstraints);
        subpanel.add(mainpanel);

        constraints.gridx = 0;
        constraints.gridy = 1;
        layout.setConstraints(subpanel, constraints);
        add(subpanel);

        ////////////////////////////////////////////////////////////////////////
        //Platzhalter
        tempcomponent = new JLabel();
        tempcomponent.setFont(tempcomponent.getFont().deriveFont(tempcomponent.getFont().getSize2D()
                                                                 - 2f));
        tempcomponent.setPreferredSize(new Dimension(de.hattrickorganizer.tools.Helper
                                                     .calcCellWidth(10),
                                                     de.hattrickorganizer.tools.Helper
                                                     .calcCellWidth(2)));
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 0.0;

        layout.setConstraints(tempcomponent, constraints);
        add(tempcomponent);

        ////////////////////////////////////////////////////////////////////////
        sublayout = new GridBagLayout();
        subconstraints = new GridBagConstraints();
        subconstraints.anchor = GridBagConstraints.CENTER;
        subconstraints.fill = GridBagConstraints.HORIZONTAL;
        subconstraints.weightx = 1.0;
        subconstraints.weighty = 0.0;
        subconstraints.insets = new Insets(1, 1, 1, 1);
        subpanel = new JPanel(sublayout);
        subpanel.setOpaque(false);

        //Platzhalter
        tempcomponent = new JLabel();
        tempcomponent.setFont(tempcomponent.getFont().deriveFont(tempcomponent.getFont().getSize2D()
                                                                 - 2f));
        tempcomponent.setPreferredSize(new Dimension(de.hattrickorganizer.tools.Helper
                                                     .calcCellWidth(10),
                                                     de.hattrickorganizer.tools.Helper
                                                     .calcCellWidth(2)));
        subconstraints.gridx = 1;
        subconstraints.gridy = 4;
        subconstraints.gridwidth = 1;

        sublayout.setConstraints(tempcomponent, subconstraints);
        subpanel.add(tempcomponent);

        //Middle
        temppanel = new JPanel(new GridLayout(1, 2));
        temppanel.setOpaque(true);
        m_clMiddleMain.setFontStyle(Font.BOLD);
        tempcomponent = m_clMiddleMain.getComponent(false);
        tempcomponent.setOpaque(true);
        temppanel.add(tempcomponent);
        tempcomponent = m_clMiddleCompare.getComponent(false);
        tempcomponent.setOpaque(true);
        temppanel.add(tempcomponent);

        innerpanel = new JPanel(new GridLayout(2, 1));
        innerpanel.setBackground(Color.white);
        innerpanel.setBorder(BorderFactory.createLineBorder(Color.white));
        innerpanel.add(m_clMiddleText);
        innerpanel.add(temppanel);

        m_clMiddlePanel.setBackground(Color.WHITE);
        m_clMiddleText.setFont(m_clMiddleText.getFont().deriveFont(m_clMiddleText.getFont()
                                                                                 .getSize2D() - 1f));
        m_clMiddleText.setOpaque(true);
        m_clMiddlePanel.add(innerpanel, BorderLayout.CENTER);
        m_clMiddlePanel.setPreferredSize(GROESSE);

        mainpanel = new JPanel(new BorderLayout());
        mainpanel.setBackground(Color.white);
        mainpanel.add(m_clMiddlePanel, BorderLayout.CENTER);
        mainpanel.setBorder(BorderFactory.createLineBorder(Color.white));
        subconstraints.gridx = 2;
        subconstraints.gridy = 4;
        subconstraints.gridwidth = 3;
        sublayout.setConstraints(mainpanel, subconstraints);
        subpanel.add(mainpanel);

        //Platzhalter
        tempcomponent = new JLabel();
        tempcomponent.setFont(tempcomponent.getFont().deriveFont(tempcomponent.getFont().getSize2D()
                                                                 - 2f));
        tempcomponent.setPreferredSize(new Dimension(de.hattrickorganizer.tools.Helper
                                                     .calcCellWidth(10),
                                                     de.hattrickorganizer.tools.Helper
                                                     .calcCellWidth(2)));
        subconstraints.gridx = 5;
        subconstraints.gridy = 4;
        subconstraints.gridwidth = 1;

        sublayout.setConstraints(tempcomponent, subconstraints);
        subpanel.add(tempcomponent);

        constraints.gridx = 0;
        constraints.gridy = 3;
        layout.setConstraints(subpanel, constraints);
        add(subpanel);

        ////////////////////////////////////////////////////////////////////////
        //Platzhalter
        tempcomponent = new JLabel();
        tempcomponent.setFont(tempcomponent.getFont().deriveFont(tempcomponent.getFont().getSize2D()
                                                                 - 2f));
        tempcomponent.setPreferredSize(new Dimension(de.hattrickorganizer.tools.Helper
                                                     .calcCellWidth(10),
                                                     de.hattrickorganizer.tools.Helper
                                                     .calcCellWidth(2)));
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.weightx = 0.0;

        layout.setConstraints(tempcomponent, constraints);
        add(tempcomponent);

        ////////////////////////////////////////////////////////////////////////
        sublayout = new GridBagLayout();
        subconstraints = new GridBagConstraints();
        subconstraints.anchor = GridBagConstraints.CENTER;
        subconstraints.fill = GridBagConstraints.HORIZONTAL;
        subconstraints.weightx = 1.0;
        subconstraints.weighty = 0.0;
        subconstraints.insets = new Insets(1, 1, 1, 1);
        subpanel = new JPanel(sublayout);
        subpanel.setOpaque(false);

        //Bottom Left
        temppanel = new JPanel(new GridLayout(1, 2));
        temppanel.setOpaque(true);
        m_clBottomLeftMain.setFontStyle(Font.BOLD);
        tempcomponent = m_clBottomLeftMain.getComponent(false);
        tempcomponent.setOpaque(true);
        temppanel.add(tempcomponent);
        tempcomponent = m_clBottomLeftCompare.getComponent(false);
        tempcomponent.setOpaque(true);
        temppanel.add(tempcomponent);

        innerpanel = new JPanel(new GridLayout(2, 1));
        innerpanel.setBackground(Color.white);
        innerpanel.setBorder(BorderFactory.createLineBorder(Color.white));
        innerpanel.add(m_clBottomLeftText);
        innerpanel.add(temppanel);

        m_clBottomLeftPanel.setBackground(Color.WHITE);
        m_clBottomLeftText.setFont(m_clBottomLeftText.getFont().deriveFont(m_clBottomLeftText.getFont()
                                                                                             .getSize2D()
                                                                           - 1f));
        m_clBottomLeftText.setOpaque(true);
        m_clBottomLeftPanel.add(innerpanel, BorderLayout.CENTER);
        m_clBottomLeftPanel.setPreferredSize(GROESSE);

        mainpanel = new JPanel(new BorderLayout());
        mainpanel.setBackground(Color.white);
        mainpanel.add(m_clBottomLeftPanel, BorderLayout.CENTER);
        mainpanel.setBorder(BorderFactory.createLineBorder(Color.white));
        subconstraints.gridx = 1;
        subconstraints.gridy = 6;
        subconstraints.gridwidth = 2;
        subconstraints.weightx = 1.0;
        sublayout.setConstraints(mainpanel, subconstraints);
        subpanel.add(mainpanel);

        //Platzhalter
        tempcomponent = new JLabel();
        tempcomponent.setFont(tempcomponent.getFont().deriveFont(tempcomponent.getFont().getSize2D()
                                                                 - 2f));
        tempcomponent.setPreferredSize(new Dimension(de.hattrickorganizer.tools.Helper
                                                     .calcCellWidth(10),
                                                     de.hattrickorganizer.tools.Helper
                                                     .calcCellWidth(2)));
        subconstraints.gridx = 3;
        subconstraints.gridy = 6;
        subconstraints.gridwidth = 1;
        subconstraints.weightx = 0.0;

        sublayout.setConstraints(tempcomponent, subconstraints);
        subpanel.add(tempcomponent);

        //Bottom Right
        temppanel = new JPanel(new GridLayout(1, 2));
        temppanel.setOpaque(true);
        m_clBottomRightMain.setFontStyle(Font.BOLD);
        tempcomponent = m_clBottomRightMain.getComponent(false);
        tempcomponent.setOpaque(true);
        temppanel.add(tempcomponent);
        tempcomponent = m_clBottomRightCompare.getComponent(false);
        tempcomponent.setOpaque(true);
        temppanel.add(tempcomponent);

        innerpanel = new JPanel(new GridLayout(2, 1));
        innerpanel.setBackground(Color.white);
        innerpanel.setBorder(BorderFactory.createLineBorder(Color.white));
        innerpanel.add(m_clBottomRightText);
        innerpanel.add(temppanel);

        m_clBottomRightPanel.setBackground(Color.WHITE);
        m_clBottomRightText.setFont(m_clBottomRightText.getFont().deriveFont(m_clBottomRightText.getFont()
                                                                                                .getSize2D()
                                                                             - 1f));
        m_clBottomRightText.setOpaque(true);
        m_clBottomRightPanel.add(innerpanel, BorderLayout.CENTER);
        m_clBottomRightPanel.setPreferredSize(GROESSE);

        mainpanel = new JPanel(new BorderLayout());
        mainpanel.setBackground(Color.white);
        mainpanel.add(m_clBottomRightPanel, BorderLayout.CENTER);
        mainpanel.setBorder(BorderFactory.createLineBorder(Color.white));
        subconstraints.gridx = 4;
        subconstraints.gridy = 6;
        subconstraints.gridwidth = 2;
        subconstraints.weightx = 1.0;
        sublayout.setConstraints(mainpanel, subconstraints);
        subpanel.add(mainpanel);

        constraints.gridx = 0;
        constraints.gridy = 5;
        layout.setConstraints(subpanel, constraints);
        add(subpanel);

        ////////////////////////////////////////////////////////////////////////
        sublayout = new GridBagLayout();
        subconstraints = new GridBagConstraints();
        subconstraints.anchor = GridBagConstraints.CENTER;
        subconstraints.fill = GridBagConstraints.HORIZONTAL;
        subconstraints.weightx = 1.0;
        subconstraints.weighty = 0.0;
        subconstraints.insets = new Insets(1, 1, 1, 1);
        subpanel = new JPanel(sublayout);
        subpanel.setOpaque(false);

        //Platzhalter
        tempcomponent = new JLabel();
        tempcomponent.setFont(tempcomponent.getFont().deriveFont(tempcomponent.getFont().getSize2D()
                                                                 - 2f));
        tempcomponent.setPreferredSize(new Dimension(de.hattrickorganizer.tools.Helper
                                                     .calcCellWidth(10),
                                                     de.hattrickorganizer.tools.Helper
                                                     .calcCellWidth(2)));
        subconstraints.gridx = 1;
        subconstraints.gridy = 7;
        subconstraints.gridwidth = 1;

        sublayout.setConstraints(tempcomponent, subconstraints);
        subpanel.add(tempcomponent);

        //Bottom Center
        temppanel = new JPanel(new GridLayout(1, 2));
        temppanel.setOpaque(true);
        m_clBottomCenterMain.setFontStyle(Font.BOLD);
        tempcomponent = m_clBottomCenterMain.getComponent(false);
        tempcomponent.setOpaque(true);
        temppanel.add(tempcomponent);
        tempcomponent = m_clBottomCenterCompare.getComponent(false);
        tempcomponent.setOpaque(true);
        temppanel.add(tempcomponent);

        innerpanel = new JPanel(new GridLayout(2, 1));
        innerpanel.setBackground(Color.white);
        innerpanel.setBorder(BorderFactory.createLineBorder(Color.white));
        innerpanel.add(m_clBottomCenterText);
        innerpanel.add(temppanel);

        m_clBottomCenterPanel.setBackground(Color.WHITE);
        m_clBottomCenterText.setFont(m_clBottomCenterText.getFont().deriveFont(m_clBottomCenterText.getFont()
                                                                                                   .getSize2D()
                                                                               - 1f));
        m_clBottomCenterText.setOpaque(true);
        m_clBottomCenterPanel.add(innerpanel, BorderLayout.CENTER);
        m_clBottomCenterPanel.setPreferredSize(GROESSE);

        mainpanel = new JPanel(new BorderLayout());
        mainpanel.setBackground(Color.white);
        mainpanel.add(m_clBottomCenterPanel, BorderLayout.CENTER);
        mainpanel.setBorder(BorderFactory.createLineBorder(Color.white));
        subconstraints.gridx = 2;
        subconstraints.gridy = 7;
        subconstraints.gridwidth = 3;
        sublayout.setConstraints(mainpanel, subconstraints);
        subpanel.add(mainpanel);

        //Platzhalter
        tempcomponent = new JLabel();
        tempcomponent.setFont(tempcomponent.getFont().deriveFont(tempcomponent.getFont().getSize2D()
                                                                 - 2f));
        tempcomponent.setPreferredSize(new Dimension(de.hattrickorganizer.tools.Helper
                                                     .calcCellWidth(10),
                                                     de.hattrickorganizer.tools.Helper
                                                     .calcCellWidth(2)));
        subconstraints.gridx = 5;
        subconstraints.gridy = 7;
        subconstraints.gridwidth = 1;

        sublayout.setConstraints(tempcomponent, subconstraints);
        subpanel.add(tempcomponent);

        constraints.gridx = 0;
        constraints.gridy = 6;
        layout.setConstraints(subpanel, constraints);
        add(subpanel);

        ////////////////////////////////////////////////////////////////////////
        initToolTips();

        //Alle zahlen auf 0, Default ist -oo
        m_clTopLeftCompare.setSpezialNumber(0f, false);
        m_clTopCenterCompare.setSpezialNumber(0f, false);
        m_clTopRightCompare.setSpezialNumber(0f, false);
        m_clMiddleCompare.setSpezialNumber(0f, false);
        m_clBottomLeftCompare.setSpezialNumber(0f, false);
        m_clBottomCenterCompare.setSpezialNumber(0f, false);
        m_clBottomRightCompare.setSpezialNumber(0f, false);
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initToolTips() {
        if (m_bReihenfolge == REIHENFOLGE_STURM2VERTEIDIGUNG) {
            m_clTopLeftText.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                  .getResource()
                                                                                  .getProperty("rechteAbwehrseite"));
            m_clTopLeftMain.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                  .getResource()
                                                                                  .getProperty("rechteAbwehrseite"));
            m_clTopLeftCompare.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                     .getResource()
                                                                                     .getProperty("rechteAbwehrseite"));
            m_clTopCenterText.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                    .getResource()
                                                                                    .getProperty("Abwehrzentrum"));
            m_clTopCenterMain.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                    .getResource()
                                                                                    .getProperty("Abwehrzentrum"));
            m_clTopCenterCompare.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                       .getResource()
                                                                                       .getProperty("Abwehrzentrum"));
            m_clTopRightText.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                   .getResource()
                                                                                   .getProperty("linkeAbwehrseite"));
            m_clTopRightMain.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                   .getResource()
                                                                                   .getProperty("linkeAbwehrseite"));
            m_clTopRightCompare.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                      .getResource()
                                                                                      .getProperty("linkeAbwehrseite"));
            m_clMiddleText.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                 .getResource()
                                                                                 .getProperty("MatchMittelfeld"));
            m_clMiddleMain.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                 .getResource()
                                                                                 .getProperty("MatchMittelfeld"));
            m_clMiddleCompare.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                    .getResource()
                                                                                    .getProperty("MatchMittelfeld"));
            m_clBottomLeftText.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                     .getResource()
                                                                                     .getProperty("rechteAngriffsseite"));
            m_clBottomLeftMain.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                     .getResource()
                                                                                     .getProperty("rechteAngriffsseite"));
            m_clBottomLeftCompare.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                        .getResource()
                                                                                        .getProperty("rechteAngriffsseite"));
            m_clBottomCenterText.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                       .getResource()
                                                                                       .getProperty("Angriffszentrum"));
            m_clBottomCenterMain.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                       .getResource()
                                                                                       .getProperty("Angriffszentrum"));
            m_clBottomCenterCompare.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                          .getResource()
                                                                                          .getProperty("Angriffszentrum"));
            m_clBottomRightText.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                      .getResource()
                                                                                      .getProperty("linkeAngriffsseite"));
            m_clBottomRightMain.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                      .getResource()
                                                                                      .getProperty("linkeAngriffsseite"));
            m_clBottomRightCompare.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                         .getResource()
                                                                                         .getProperty("linkeAngriffsseite"));
        } else {
            m_clTopLeftText.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                  .getResource()
                                                                                  .getProperty("linkeAngriffsseite"));
            m_clTopLeftMain.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                  .getResource()
                                                                                  .getProperty("linkeAngriffsseite"));
            m_clTopLeftCompare.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                     .getResource()
                                                                                     .getProperty("linkeAngriffsseite"));
            m_clTopCenterText.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                    .getResource()
                                                                                    .getProperty("Angriffszentrum"));
            m_clTopCenterMain.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                    .getResource()
                                                                                    .getProperty("Angriffszentrum"));
            m_clTopCenterCompare.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                       .getResource()
                                                                                       .getProperty("Angriffszentrum"));
            m_clTopRightText.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                   .getResource()
                                                                                   .getProperty("rechteAngriffsseite"));
            m_clTopRightMain.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                   .getResource()
                                                                                   .getProperty("rechteAngriffsseite"));
            m_clTopRightCompare.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                      .getResource()
                                                                                      .getProperty("rechteAngriffsseite"));
            m_clMiddleText.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                 .getResource()
                                                                                 .getProperty("MatchMittelfeld"));
            m_clMiddleMain.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                 .getResource()
                                                                                 .getProperty("MatchMittelfeld"));
            m_clMiddleCompare.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                    .getResource()
                                                                                    .getProperty("MatchMittelfeld"));
            m_clBottomLeftText.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                     .getResource()
                                                                                     .getProperty("linkeAbwehrseite"));
            m_clBottomLeftMain.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                     .getResource()
                                                                                     .getProperty("linkeAbwehrseite"));
            m_clBottomLeftCompare.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                        .getResource()
                                                                                        .getProperty("linkeAbwehrseite"));
            m_clBottomCenterText.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                       .getResource()
                                                                                       .getProperty("Abwehrzentrum"));
            m_clBottomCenterMain.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                       .getResource()
                                                                                       .getProperty("Abwehrzentrum"));
            m_clBottomCenterCompare.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                          .getResource()
                                                                                          .getProperty("Abwehrzentrum"));
            m_clBottomRightText.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                      .getResource()
                                                                                      .getProperty("rechteAbwehrseite"));
            m_clBottomRightMain.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                      .getResource()
                                                                                      .getProperty("rechteAbwehrseite"));
            m_clBottomRightCompare.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                         .getResource()
                                                                                         .getProperty("rechteAbwehrseite"));
        }
    }
}
