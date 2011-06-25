// %106548303:de.hattrickorganizer.gui.login%
package de.hattrickorganizer.gui.login;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

import de.hattrickorganizer.gui.templates.RasenPanel;
import de.hattrickorganizer.gui.theme.ThemeManager;
import de.hattrickorganizer.model.HOVerwaltung;


/**
 * Dialog beim Download der HRF Datei
 */
public class LoginWaitDialog extends JWindow implements Runnable {
	
	private static final long serialVersionUID = 2737470419222145110L;
	
    //    public static boolean WAIT_AUTOPROGRESSBAR = true;
    //    public static boolean WAIT_MANUELLPROGRESSBAR;
    private JProgressBar m_jpbProgressBar;
    private boolean m_bAutoprogressbar;
    private boolean m_bEnde;

    public LoginWaitDialog(Window owner) {
        this(owner, true);
    }

    public LoginWaitDialog(Window owner, boolean autoprogress) {
        super(owner);
        m_bAutoprogressbar = autoprogress;

        //setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
        setContentPane(new RasenPanel());

        final GridBagLayout layout = new GridBagLayout();
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.weighty = 0.0;
        constraints.insets = new Insets(1, 2, 1, 1);
        getContentPane().setLayout(layout);

        final JLabel label = new JLabel(HOVerwaltung.instance().getLanguageString("BitteWarten"),
                                        SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(java.awt.Font.BOLD, 24f));
        label.setForeground(ThemeManager.getColor("ho.label.onGreen.foreground"));
        constraints.gridx = 0;
        constraints.gridy = 0;
        layout.setConstraints(label, constraints);
        getContentPane().add(label);

        m_jpbProgressBar = new JProgressBar(0, 100);
        m_jpbProgressBar.setStringPainted(!m_bAutoprogressbar);
        constraints.gridx = 0;
        constraints.gridy = 1;
        layout.setConstraints(m_jpbProgressBar, constraints);
        getContentPane().add(m_jpbProgressBar);

        setSize(200, 100);

        setLocation((owner.getLocation().x + (owner.getSize().width / 2))
                    - (this.getSize().width / 2),
                    (owner.getLocation().y + (owner.getSize().height / 2))
                    - (this.getSize().height / 2));
    }

    public final void setValue(int value) {
        m_jpbProgressBar.setValue(value);
    }

    public final int getValue() {
        return m_jpbProgressBar.getValue();
    }

    /**
     * Den ProgressbarThread beim sichtbarmachen starten und beim unsichtbarmachen beenden
     *
     */
    @Override
	public final void setVisible(boolean sichtbar) {
        if (sichtbar) {
            m_bEnde = false;
            new Thread(this).start();
        } else {
            m_bEnde = true;
        }

        super.setVisible(sichtbar);
    }

    public final void run() {
        int loops = 9;

        while (!m_bEnde) {
            if (m_bAutoprogressbar) {
                if (m_jpbProgressBar.getValue() < 100) {
                    m_jpbProgressBar.setValue(m_jpbProgressBar.getValue() + 1);
                } else {
                    m_jpbProgressBar.setValue(0);
                }
            }

            m_jpbProgressBar.paintAll(m_jpbProgressBar.getGraphics());

            if (loops >= 10) {
                this.paintAll(this.getGraphics());
                loops = 0;
            }

            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }

            loops++;

            //HOLogger.instance().log(getClass(), "Value: "+m_jpbProgressBar.getValue () + " " + m_bEnde );
        }
    }
}
