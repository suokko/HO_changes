// %3415157064:de.hattrickorganizer.gui.keepertool%
package de.hattrickorganizer.gui.keepertool;

import de.hattrickorganizer.gui.pluginWrapper.GUIPluginWrapper;
import de.hattrickorganizer.model.HOVerwaltung;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


/**
 * Main KeeperTool dialog
 *
 * @author draghetto
 */
public class KeeperToolDialog extends JDialog implements WindowListener, ActionListener {
    //~ Instance fields ----------------------------------------------------------------------------

    private JPanel cards = new JPanel(new CardLayout());
    private JRadioButton rosterButton;
    private JRadioButton scoutButton;
    private ManualPanel manualPanel;
    private ResultPanel resultPanel;
    private RosterPanel rosterPanel;
    private ScoutPanel scoutPanel;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new KeeperToolDialog object.
     *
     * @param owner the HO main frame
     */
    public KeeperToolDialog(JFrame owner) {
        super(owner, false);
        setTitle(HOVerwaltung.instance().getResource().getProperty("KeeperTool"));

        resultPanel = new ResultPanel(this);
        initComponents();

        //reload();
        setSize(new java.awt.Dimension(400, 250));
        setLocation(0, 0);

        setVisible(false);

        //setResizable(false);
        addWindowListener(this);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Action Listener, reacts to type of Keeper Selection method
     *
     * @param ae action event
     */
    public final void actionPerformed(ActionEvent ae) {
        final Object compo = ae.getSource();
        final CardLayout cLayout = (CardLayout) (cards.getLayout());
        resultPanel.reset();
        scoutPanel.reset();
        rosterPanel.reset();

        if (compo == rosterButton) {
            cLayout.show(cards, "Roster");
        } else if (compo == scoutButton) {
            cLayout.show(cards, "Scout");
        } else {
            cLayout.show(cards, "Manual");
        }
    }

    /**
     * Forces a reset of the dialog
     */
    public final void reload() {
        resultPanel.reset();
        rosterPanel.reload();
        scoutPanel.reload();
        manualPanel.reset();

        final CardLayout cLayout = (CardLayout) (cards.getLayout());
        cLayout.show(cards, "Roster");

        rosterButton.setSelected(true);
    }

    /**
     * React to Window Event
     *
     * @param windowEvent event
     */
    public void windowActivated(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * React to Window Event
     *
     * @param windowEvent event
     */
    public final void windowClosed(java.awt.event.WindowEvent windowEvent) {
        setVisible(false);
    }

    /**
     * React to Window Event
     *
     * @param windowEvent event
     */
    public final void windowClosing(java.awt.event.WindowEvent windowEvent) {
        setVisible(false);
    }

    /**
     * React to Window Event
     *
     * @param windowEvent event
     */
    public void windowDeactivated(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * React to Window Event
     *
     * @param windowEvent event
     */
    public void windowDeiconified(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * React to Window Event
     *
     * @param windowEvent event
     */
    public void windowIconified(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * React to Window Event
     *
     * @param windowEvent event
     */
    public void windowOpened(java.awt.event.WindowEvent windowEvent) {
    }

    /**
     * Initialize the GUI components
     */
    private void initComponents() {
        getContentPane().setLayout(new BorderLayout());

        final JPanel main = new JPanel(new BorderLayout());
        main.setOpaque(false);

        rosterButton = new JRadioButton(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                               .getResource()
                                                                               .getProperty("Spieleruebersicht"));
        rosterButton.setSelected(true);
        rosterButton.addActionListener(this);
        rosterButton.setOpaque(false);

        scoutButton = new JRadioButton(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                              .getResource()
                                                                              .getProperty("TransferScout"));
        scoutButton.addActionListener(this);
        scoutButton.setOpaque(false);

        final JRadioButton manualButton = new JRadioButton(de.hattrickorganizer.model.HOVerwaltung.instance()
                                                                                                  .getResource()
                                                                                                  .getProperty("Manual"));
        manualButton.addActionListener(this);
        manualButton.setOpaque(false);

        final ButtonGroup groupRadio = new ButtonGroup();
        groupRadio.add(rosterButton);
        groupRadio.add(scoutButton);
        groupRadio.add(manualButton);

        final JPanel buttonPanel = GUIPluginWrapper.instance().createImagePanel();
        buttonPanel.setLayout(new GridLayout(3, 1));
        buttonPanel.add(rosterButton);
        buttonPanel.add(scoutButton);
        buttonPanel.add(manualButton);

        main.add(buttonPanel, BorderLayout.WEST);

        rosterPanel = new RosterPanel(resultPanel);
        scoutPanel = new ScoutPanel(resultPanel);
        manualPanel = new ManualPanel(resultPanel);

        cards.add(rosterPanel, "Roster");
        cards.add(scoutPanel, "Scout");
        cards.add(manualPanel, "Manual");
        main.add(cards, BorderLayout.CENTER);

        main.add(resultPanel, BorderLayout.SOUTH);

        getContentPane().add(main, BorderLayout.CENTER);
    }
}
