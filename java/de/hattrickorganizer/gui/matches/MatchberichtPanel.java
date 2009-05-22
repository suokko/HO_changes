// %3087735495:de.hattrickorganizer.gui.matches%
package de.hattrickorganizer.gui.matches;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.model.matches.MatchKurzInfo;
import de.hattrickorganizer.model.matches.Matchdetails;


/**
 * TODO Missing Class Documentation
 *
 * @author TODO Author Name
 */
public class MatchberichtPanel extends ImagePanel implements ActionListener {
    //~ Instance fields ----------------------------------------------------------------------------

    private JButton m_jbMaximieren = new JButton(new ImageIcon(de.hattrickorganizer.tools.Helper
                                                               .loadImage("gui/bilder/MaxAufstellung.png")));
    private MatchKurzInfo m_clKurzInfo;
    private MatchberichtEditorPanel m_clMatchbericht = new MatchberichtEditorPanel();
    private String m_sMatchtext = "";

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new MatchberichtPanel object.
     *
     * @param mitButton TODO Missing Constructuor Parameter Documentation
     */
    public MatchberichtPanel(boolean mitButton) {
        setBackground(Color.WHITE);

        setLayout(new BorderLayout());

        add(m_clMatchbericht, BorderLayout.CENTER);

        if (mitButton) {
            final GridBagLayout layout = new GridBagLayout();
            final GridBagConstraints constraints = new GridBagConstraints();
            constraints.anchor = GridBagConstraints.SOUTHEAST;
            constraints.fill = GridBagConstraints.NONE;
            constraints.weighty = 1.0;
            constraints.weightx = 1.0;
            constraints.insets = new Insets(4, 6, 4, 6);

            final ImagePanel buttonPanel = new ImagePanel(layout);
            buttonPanel.setBackground(Color.white);

            m_jbMaximieren.setToolTipText(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("tt_Matchbericht_Maximieren"));
            m_jbMaximieren.setEnabled(false);
            m_jbMaximieren.setPreferredSize(new Dimension(25, 25));
            m_jbMaximieren.addActionListener(this);
            layout.setConstraints(m_jbMaximieren, constraints);
            buttonPanel.add(m_jbMaximieren);

            add(buttonPanel, BorderLayout.SOUTH);
        }
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param text TODO Missing Method Parameter Documentation
     */
    public final void setText(String text) {
        m_sMatchtext = text;
        m_clMatchbericht.setText(text);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param actionEvent TODO Missing Method Parameter Documentation
     */
    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        //Dialog mit Matchbericht erzeugen
        final String titel = m_clKurzInfo.getHeimName() + " - " + m_clKurzInfo.getGastName()
                             + " ( " + m_clKurzInfo.getHeimTore() + " : "
                             + m_clKurzInfo.getGastTore() + " )";
        final JDialog matchdialog = new JDialog(de.hattrickorganizer.gui.HOMainFrame.instance(),
                                                titel);
        matchdialog.getContentPane().setLayout(new BorderLayout());

        final MatchberichtPanel berichtpanel = new MatchberichtPanel(false);
        berichtpanel.setText(m_sMatchtext);
        matchdialog.getContentPane().add(berichtpanel, BorderLayout.CENTER);

        matchdialog.setLocation(50, 50);
        matchdialog.setSize(600, de.hattrickorganizer.gui.HOMainFrame.instance().getHeight() - 100);
        matchdialog.setVisible(true);
    }

    /**
     * TODO Missing Method Documentation
     */
    public final void clear() {
        m_clKurzInfo = null;
        m_sMatchtext = "";
        m_clMatchbericht.clear();
        m_jbMaximieren.setEnabled(false);
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param info TODO Missing Method Parameter Documentation
     */
    public final void refresh(MatchKurzInfo info) {
        m_clKurzInfo = info;

        if ((info != null)
            && info.getMatchDateAsTimestamp().before(new java.sql.Timestamp(System
                                                                            .currentTimeMillis()))) {
            final Matchdetails details = de.hattrickorganizer.database.DBZugriff.instance()
                                                                                .getMatchDetails(info
                                                                                                 .getMatchID());

            m_sMatchtext = details.getMatchreport();

            m_jbMaximieren.setEnabled(true);
        } else {
            m_jbMaximieren.setEnabled(false);

            m_sMatchtext = "";
        }

        m_clMatchbericht.setText(m_sMatchtext);
    }
}
