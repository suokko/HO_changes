// %3348273414:de.hattrickorganizer.gui.birthday%
/*
 * GebDialog.java
 *
 * Created on 4. April 2003, 11:07
 */
package de.hattrickorganizer.gui.birthday;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.logik.GebChecker;


public class GebDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 6032212484981424415L;

    GebChecker m_clGeb;

    JButton m_clOK = new JButton();


    /**
     * Creates a new instance of GebDialog
     *
     */
    public GebDialog(JFrame owner, String bild) {
        super(owner, true);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        initComponents(bild);
        setLocation((owner.getLocation().x + (owner.getWidth() / 2)) - (this.getWidth() / 2),
                    (owner.getLocation().y + (owner.getHeight() / 2)) - (this.getHeight() / 2));
        this.setResizable(false);
        this.setVisible(true);
    }

    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(m_clOK)) {
//            if (m_clGeb.getSekunden() < 5) {
//                //böse
//                javax.swing.JOptionPane.showMessageDialog(this,
//                                                          "Was nur " + m_clGeb.getSekunden()
//                                                          + " Sekunden ?!?\n *schmoll* ;) ",
//                                                          "Ein wenig mehr Respekt bitte",
//                                                          JOptionPane.WARNING_MESSAGE);
//            } else if (m_clGeb.getSekunden() < 55) {
//                javax.swing.JOptionPane.showMessageDialog(this,
//                                                          "Das war keine Minute.\n Du solltest dich schämen! ;)",
//                                                          "Ein wenig mehr Respekt bitte",
//                                                          JOptionPane.WARNING_MESSAGE);
//            }

            m_clGeb.setDialog(null);
            this.dispose();
        }
    }

    protected final void initComponents(String bild) {
        final JLabel label = new JLabel("", SwingConstants.CENTER);

        setContentPane(new ImagePanel(new BorderLayout()));

        label.setIcon(ThemeManager.getIcon(bild));
        getContentPane().add(label, BorderLayout.CENTER);

        m_clOK.setText("Fertig");
        m_clOK.addActionListener(this);
        getContentPane().add(m_clOK, BorderLayout.SOUTH);

        setSize(250, 260);

        m_clGeb = new GebChecker();
        m_clGeb.setDialog(this);

        new Thread(m_clGeb).start();
    }
}
