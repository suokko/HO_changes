// %3348273414:de.hattrickorganizer.gui.birthday%
/*
 * GebDialog.java
 *
 * Created on 4. April 2003, 11:07
 */
package de.hattrickorganizer.gui.birthday;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


/**
 * DOCUMENT ME!
 *
 * @author thomas.werth
 */
public class GebDialog extends javax.swing.JDialog implements java.awt.event.ActionListener {
    //~ Instance fields ----------------------------------------------------------------------------

    /** TODO Missing Parameter Documentation */
    de.hattrickorganizer.logik.GebChecker m_clGeb;

    /** TODO Missing Parameter Documentation */
    JButton m_clOK = new JButton();

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new instance of GebDialog
     *
     * @param owner TODO Missing Constructuor Parameter Documentation
     * @param bild TODO Missing Constructuor Parameter Documentation
     */
    public GebDialog(JFrame owner, String bild) {
        super(owner, true);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        initComponents(bild);
        setLocation((owner.getLocation().x + (owner.getWidth() / 2)) - (this.getWidth() / 2),
                    (owner.getLocation().y + (owner.getHeight() / 2)) - (this.getHeight() / 2));
        this.setResizable(false);
        this.setVisible(true);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @param actionEvent TODO Missing Method Parameter Documentation
     */
    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(m_clOK)) {
            if (m_clGeb.getSekunden() < 5) {
                //böse
                javax.swing.JOptionPane.showMessageDialog(this,
                                                          "Was nur " + m_clGeb.getSekunden()
                                                          + " Sekunden ?!?\n *schmoll* ;) ",
                                                          "Ein wenig mehr Respekt bitte",
                                                          JOptionPane.WARNING_MESSAGE);
            } else if (m_clGeb.getSekunden() < 55) {
                javax.swing.JOptionPane.showMessageDialog(this,
                                                          "Das war keine Minute.\n Du solltest dich schämen! ;)",
                                                          "Ein wenig mehr Respekt bitte",
                                                          JOptionPane.WARNING_MESSAGE);
            }

            m_clGeb.setDialog(null);
            this.dispose();
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param bild TODO Missing Method Parameter Documentation
     */
    protected final void initComponents(String bild) {
        final JLabel label = new JLabel("", JLabel.CENTER);

        setContentPane(new de.hattrickorganizer.gui.templates.ImagePanel(new BorderLayout()));

        label.setIcon(new ImageIcon(de.hattrickorganizer.tools.Helper.loadImage(bild)));
        getContentPane().add(label, BorderLayout.CENTER);

        m_clOK.setText("Fertig");
        m_clOK.addActionListener(this);
        getContentPane().add(m_clOK, BorderLayout.SOUTH);

        setSize(250, 260);

        m_clGeb = new de.hattrickorganizer.logik.GebChecker();
        m_clGeb.setDialog(this);

        new Thread(m_clGeb).start();
    }
}
