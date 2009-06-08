// %3935335139:de.hattrickorganizer.gui.hoFriendly%
package de.hattrickorganizer.gui.hoFriendly;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.hattrickorganizer.gui.templates.ImagePanel;
import de.hattrickorganizer.net.MyConnector;


/**
 * Dialog für IP und Port / Server oder Client
 */
public class RMIDialog extends JDialog implements ActionListener, KeyListener,
                                                  ListSelectionListener
{
    //~ Instance fields ----------------------------------------------------------------------------

    /**
	 * 
	 */
	private static final long serialVersionUID = -4219452011230336305L;
	private JButton m_jbAbbrechen;
    private JButton m_jbClient;
    private JButton m_jbRefreshList;
    private JButton m_jbServer;
    private JButton m_jbServerIP;
    private JCheckBox m_jchClientListe;
    private JCheckBox m_jchServerRegister;
    private JList m_jlClientListe;
    private JTextField m_jtfClientClientPort;
    private JTextField m_jtfClientServerIP;
    private JTextField m_jtfServerServerIP;
    private JTextField m_jtfServerServerPort;
    private String m_sServerIP = "localhost";
    private boolean m_bAbgebrochen = true;
    private boolean m_bServer;
    private int m_iPort = 1099;

    //~ Constructors -------------------------------------------------------------------------------

    /**
     * Creates a new RMIDialog object.
     *
     * @param owner TODO Missing Constructuor Parameter Documentation
     */
    public RMIDialog(JFrame owner) {
        super(owner,
              de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("HoFriendly"),
              true);

        initComponents();

        setSize(new java.awt.Dimension(480, 365));

        final Dimension size = owner.getToolkit().getScreenSize();

        if (size.width > this.getSize().width) {
            //Mittig positionieren
            this.setLocation((size.width / 2) - (this.getSize().width / 2),
                             (size.height / 2) - (this.getSize().height / 2));
        }

        setResizable(false);

        setVisible(true);
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isAbgebrochen() {
        return m_bAbgebrochen;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String getClientIP() {
        //Aus Liste
        if (m_jchClientListe.isSelected() && (m_jlClientListe.getSelectedIndex() > -1)) {
            return ((de.hattrickorganizer.net.rmiHOFriendly.ServerVerweis) m_jlClientListe
                    .getSelectedValue()).getIp();
        }
        //Direkt IP
        else {
            return m_sServerIP;
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isInternetServer() {
        return m_jchServerRegister.isSelected();
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final int getPort() {
        //Aus Liste
        if (m_jchClientListe.isSelected() && (m_jlClientListe.getSelectedIndex() > -1)) {
            return ((de.hattrickorganizer.net.rmiHOFriendly.ServerVerweis) m_jlClientListe
                    .getSelectedValue()).getPort();
        }
        //Direkt Port
        else {
            return m_iPort;
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final boolean isServer() {
        return m_bServer;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @return TODO Missing Return Method Documentation
     */
    public final String getServerIP() {
        return m_sServerIP;
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param actionEvent TODO Missing Method Parameter Documentation
     */
    public final void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(m_jbServer)) {
            m_sServerIP = m_jtfServerServerIP.getText();
            m_bServer = true;
            m_bAbgebrochen = false;
            setVisible(false);
        } else if (actionEvent.getSource().equals(m_jbClient)) {
            if (m_jchClientListe.isSelected()) {
                //TODO Aus liste!
                m_sServerIP = m_jlClientListe.getSelectedValue().toString();
            } else {
                m_sServerIP = m_jtfClientServerIP.getText();
            }

            m_bServer = false;
            m_bAbgebrochen = false;
            setVisible(false);
        } else if (actionEvent.getSource().equals(m_jbRefreshList)) {
            fillListe();
        } else if (actionEvent.getSource().equals(m_jchServerRegister)) {
            m_jtfServerServerIP.setEnabled(!m_jchServerRegister.isSelected());
        } else if (actionEvent.getSource().equals(m_jchClientListe)) {
            if (m_jchClientListe.isSelected()) {
                m_jlClientListe.setEnabled(true);
                m_jbRefreshList.setEnabled(true);
                m_jtfClientClientPort.setEnabled(false);
                m_jtfClientServerIP.setEnabled(false);
                fillListe();
            } else {
                m_jlClientListe.setEnabled(false);
                m_jbRefreshList.setEnabled(false);
                m_jtfClientClientPort.setEnabled(true);
                m_jtfClientServerIP.setEnabled(true);
            }

            checkClient();
        } else if (actionEvent.getSource().equals(m_jbServerIP)) {
            try {
                de.hattrickorganizer.tools.BrowserLauncher.openURL(MyConnector.getHOSite()+"/IPAdresse.html");
            } catch (java.io.IOException ioex) {
            }
        } else if (actionEvent.getSource().equals(m_jbAbbrechen)) {
            setVisible(false);
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param keyEvent TODO Missing Method Parameter Documentation
     */
    public void keyPressed(java.awt.event.KeyEvent keyEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param keyEvent TODO Missing Method Parameter Documentation
     */
    public final void keyReleased(java.awt.event.KeyEvent keyEvent) {
        if (keyEvent.getSource().equals(m_jtfServerServerPort)) {
            checkServer();
        } else {
            checkClient();
        }
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param keyEvent TODO Missing Method Parameter Documentation
     */
    public void keyTyped(java.awt.event.KeyEvent keyEvent) {
    }

    /**
     * TODO Missing Method Documentation
     *
     * @param e TODO Missing Method Parameter Documentation
     */
    public final void valueChanged(ListSelectionEvent e) {
        if (m_jchClientListe.isSelected()) {
            if (m_jlClientListe.getSelectedIndex() > -1) {
                m_jbClient.setEnabled(true);
            } else {
                m_jbClient.setEnabled(false);
            }
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    private void checkClient() {
        if (m_jchClientListe.isSelected()) {
            if (m_jlClientListe.getSelectedIndex() > -1) {
                m_jbClient.setEnabled(true);
            } else {
                m_jbClient.setEnabled(false);
            }
        } else {
            if ((m_jtfClientClientPort.getText().length() > 0)
                && (m_jtfClientClientPort.getText().length() < 6)) {
                try {
                    m_iPort = Integer.parseInt(m_jtfClientClientPort.getText().trim());

                    if ((m_iPort > 1024) && (m_iPort < 65565)) {
                        if (m_jtfClientServerIP.getText().length() > 0) {
                            m_jbClient.setEnabled(true);
                        } else {
                            m_jbClient.setEnabled(false);
                        }
                    }
                    //Kein akzeptabler Port
                    else {
                        m_jbClient.setEnabled(false);
                    }
                }
                //Keine Zahl
                 catch (Exception e) {
                    m_jbClient.setEnabled(false);
                }
            }
            //Keine Zeichen
            else {
                m_jbClient.setEnabled(false);
            }
        }
    }

    //------------------------------------------------
    private void checkServer() {
        if ((m_jtfServerServerPort.getText().length() > 0)
            && (m_jtfServerServerPort.getText().length() < 6)) {
            try {
                m_iPort = Integer.parseInt(m_jtfServerServerPort.getText().trim());

                if ((m_iPort > 1024) && (m_iPort < 65565)) {
                    if (m_jtfServerServerIP.getText().length() > 0) {
                        m_jbServer.setEnabled(true);
                    } else {
                        m_jbServer.setEnabled(false);
                    }
                }
                //Kein akzeptabler Port
                else {
                    m_jbServer.setEnabled(false);
                }
            }
            //Keine Zahl
             catch (Exception e) {
                m_jbServer.setEnabled(false);
            }
        }
        //Keine Zeichen
        else {
            m_jbServer.setEnabled(false);
        }
    }

    /**
     * TODO Missing Method Documentation
     */
    private void fillListe() {
        final de.hattrickorganizer.net.rmiHOFriendly.ServerVerweis[] tmp = de.hattrickorganizer.net.MyConnector.instance()
                                                                                                               .getServerList();
        final DefaultListModel model = new DefaultListModel();

        for (int i = 0; (tmp != null) && (i < tmp.length); i++) {
            model.addElement(tmp[i]);
        }

        m_jlClientListe.setModel(model);
    }

    /**
     * TODO Missing Method Documentation
     */
    private void initComponents() {
        setContentPane(new ImagePanel(null));

        //Server------------------------------------
        final JPanel serverpanel = new ImagePanel(null);

        serverpanel.setBorder(BorderFactory.createTitledBorder(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Server")));

        m_jchServerRegister = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Als_InternetServer_registrieren"));
        m_jchServerRegister.setOpaque(false);
        m_jchServerRegister.setSize(205, 25);
        m_jchServerRegister.setLocation(10, 10);
        m_jchServerRegister.addActionListener(this);
        m_jchServerRegister.setEnabled(!gui.UserParameter.instance().ProxyAktiv /*net.MyConnector.instance().isUseProxy()*/);
        serverpanel.add(m_jchServerRegister);

        JLabel label = new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("IP"));
        label.setSize(100, 25);
        label.setLocation(10, 40);
        serverpanel.add(label);

        java.net.InetAddress inetAdress = null;

        try {
            inetAdress = java.net.InetAddress.getByName(java.net.InetAddress.getLocalHost()
                                                                            .getHostAddress());
        } catch (Exception e) {
        }

        if (inetAdress != null) {
            m_jtfServerServerIP = new JTextField(inetAdress.getHostAddress());
            m_sServerIP = inetAdress.getHostAddress();
        } else {
            m_jtfServerServerIP = new JTextField("Unknown");
        }

        m_jtfServerServerIP.setSize(110, 25);
        m_jtfServerServerIP.setLocation(105, 40);
        serverpanel.add(m_jtfServerServerIP);

        label = new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Port"));
        label.setSize(100, 25);
        label.setLocation(10, 70);
        serverpanel.add(label);

        m_jtfServerServerPort = new JTextField(m_iPort + "");
        m_jtfServerServerPort.setSize(110, 25);
        m_jtfServerServerPort.setLocation(105, 70);
        m_jtfServerServerPort.addKeyListener(this);
        serverpanel.add(m_jtfServerServerPort);

        m_jbServerIP = new JButton(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("IP"));
        m_jbServerIP.setSize(205, 25);
        m_jbServerIP.setLocation(10, 255);
        m_jbServerIP.addActionListener(this);
        serverpanel.add(m_jbServerIP);

        serverpanel.setSize(225, 285);
        serverpanel.setLocation(10, 10);
        getContentPane().add(serverpanel);

        //Client-----------------------
        //Client
        final JPanel clientpanel = new ImagePanel(null);
        clientpanel.setBorder(BorderFactory.createTitledBorder(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Client")));

        m_jchClientListe = new JCheckBox(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Internetserver_auswaehlen"),
                                         false);
        m_jchClientListe.setOpaque(false);
        m_jchClientListe.addActionListener(this);
        m_jchClientListe.setSize(205, 25);
        m_jchClientListe.setLocation(10, 10);
        m_jchClientListe.setEnabled(!gui.UserParameter.instance().ProxyAktiv);
        clientpanel.add(m_jchClientListe);

        label = new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Server") + " "
                           + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("IP"));
        label.setSize(100, 25);
        label.setLocation(10, 40);
        clientpanel.add(label);

        m_jtfClientServerIP = new JTextField(m_sServerIP);
        m_jtfClientServerIP.addKeyListener(this);
        m_jtfClientServerIP.setSize(110, 25);
        m_jtfClientServerIP.setLocation(105, 40);
        clientpanel.add(m_jtfClientServerIP);

        label = new JLabel(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Port"));
        label.setSize(100, 25);
        label.setLocation(10, 70);
        clientpanel.add(label);

        m_jtfClientClientPort = new JTextField(m_iPort + "");
        m_jtfClientClientPort.addKeyListener(this);
        m_jtfClientClientPort.setSize(110, 25);
        m_jtfClientClientPort.setLocation(105, 70);
        clientpanel.add(m_jtfClientClientPort);

        m_jlClientListe = new JList();
        m_jlClientListe.setEnabled(false);
        m_jlClientListe.addListSelectionListener(this);

        final JScrollPane scrollPanel = new JScrollPane(m_jlClientListe);
        scrollPanel.setSize(205, 150);
        scrollPanel.setLocation(10, 100);
        clientpanel.add(scrollPanel);

        m_jbRefreshList = new JButton(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Refresh"));
        m_jbRefreshList.addActionListener(this);
        m_jbRefreshList.setEnabled(false);
        m_jbRefreshList.setSize(205, 25);
        m_jbRefreshList.setLocation(10, 255);
        clientpanel.add(m_jbRefreshList);

        clientpanel.setSize(225, 285);
        clientpanel.setLocation(245, 10);
        getContentPane().add(clientpanel);

        //----Button
        m_jbServer = new JButton(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Starte")
                                 + " "
                                 + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Server"));
        m_jbServer.setFont(m_jbServer.getFont().deriveFont(Font.BOLD));
        m_jbServer.addActionListener(this);
        m_jbServer.setSize(140, 30);
        m_jbServer.setLocation(10, 300);
        getContentPane().add(m_jbServer);

        m_jbAbbrechen = new JButton(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Abbrechen"));
        m_jbAbbrechen.addActionListener(this);
        m_jbAbbrechen.setSize(140, 30);
        m_jbAbbrechen.setLocation(175, 300);
        getContentPane().add(m_jbAbbrechen);

        m_jbClient = new JButton(de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Starte")
                                 + " "
                                 + de.hattrickorganizer.model.HOVerwaltung.instance().getLanguageString("Client"));
        m_jbClient.setFont(m_jbClient.getFont().deriveFont(Font.BOLD));
        m_jbClient.addActionListener(this);
        m_jbClient.setSize(140, 30);
        m_jbClient.setLocation(330, 300);
        getContentPane().add(m_jbClient);
    }
}
