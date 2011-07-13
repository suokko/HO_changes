// %3087735495:de.hattrickorganizer.gui.matches%
package de.hattrickorganizer.gui.matches;

import gui.HOIconName;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

import de.hattrickorganizer.database.DBZugriff;
import de.hattrickorganizer.gui.HOMainFrame;
import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.model.HOVerwaltung;
import de.hattrickorganizer.model.matches.MatchKurzInfo;
import de.hattrickorganizer.model.matches.Matchdetails;


public class MatchberichtPanel extends ImagePanel implements ActionListener {
	
	private static final long serialVersionUID = -9014579382145462648L;
	
    //~ Instance fields ----------------------------------------------------------------------------

    private JButton m_jbMaximieren = new JButton(ThemeManager.getIcon(HOIconName.MAXLINEUP));
    private MatchKurzInfo m_clKurzInfo;
    private MatchberichtEditorPanel m_clMatchbericht = new MatchberichtEditorPanel();
    private String m_sMatchtext = "";

    public MatchberichtPanel(boolean mitButton) {

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

            m_jbMaximieren.setToolTipText(HOVerwaltung.instance().getLanguageString("tt_Matchbericht_Maximieren"));
            m_jbMaximieren.setEnabled(false);
            m_jbMaximieren.setPreferredSize(new Dimension(25, 25));
            m_jbMaximieren.addActionListener(this);
            layout.setConstraints(m_jbMaximieren, constraints);
            buttonPanel.add(m_jbMaximieren);

            add(buttonPanel, BorderLayout.SOUTH);
        }
    }

    public final void setText(String text) {
        m_sMatchtext = text;
        m_clMatchbericht.setText(text);
    }

    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        //Dialog mit Matchbericht erzeugen
        final String titel = m_clKurzInfo.getHeimName() + " - " + m_clKurzInfo.getGastName()
                             + " ( " + m_clKurzInfo.getHeimTore() + " : "
                             + m_clKurzInfo.getGastTore() + " )";
        final JDialog matchdialog = new JDialog(HOMainFrame.instance(),titel);
        matchdialog.getContentPane().setLayout(new BorderLayout());

        final MatchberichtPanel berichtpanel = new MatchberichtPanel(false);
        berichtpanel.setText(m_sMatchtext);
        matchdialog.getContentPane().add(berichtpanel, BorderLayout.CENTER);

        matchdialog.setLocation(50, 50);
        matchdialog.setSize(600, HOMainFrame.instance().getHeight() - 100);
        matchdialog.setVisible(true);
    }

    public final void clear() {
        m_clKurzInfo = null;
        m_sMatchtext = "";
        m_clMatchbericht.clear();
        m_jbMaximieren.setEnabled(false);
    }


    public final void refresh(MatchKurzInfo info) {
        m_clKurzInfo = info;

        if ((info != null)
            && info.getMatchDateAsTimestamp().before(new java.sql.Timestamp(System
                                                                            .currentTimeMillis()))) {
            final Matchdetails details = DBZugriff.instance().getMatchDetails(info.getMatchID());

            m_sMatchtext = details.getMatchreport();

            m_jbMaximieren.setEnabled(true);
        } else {
            m_jbMaximieren.setEnabled(false);

            m_sMatchtext = "";
        }

        m_clMatchbericht.setText(m_sMatchtext);
    }
}
